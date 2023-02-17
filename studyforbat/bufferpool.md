## bufferpool  

#### 缓存的重要性
Innodb中，无论是存储用户数据的索引（包括聚簇索引和二级索引），还是系统数据，都是以数据页的形式存储在表空间。  而表空间则是  
Innodb文件系统中一个或多个实际文件的抽象。当Innodb接收到客户端请求某个页中的数据时，就会将该页  的数据全部加载到内存中。也  
就是说即使我们请求页中的一条数据，也会将整个页号中的数据加载到内存中，这样就可以进  行快速访问了。 处理完以后并不会快速释放  
掉，这样当再有请求访问该页的数据时，就可以省去磁盘IO的开销。    

##### Bufferpool
mysql服务器在启动的时候会向系统申请一块连续的内存空间，这块连续的内存空间就是bufferpool。可以通过innodb_buffer_pool_size
进行设置。  

#### Bufferpool组成  
buffer pool中默认的缓存页大小和磁盘中默认的页大小是一样的，都是16kb。Innodb为每个缓存页都设置了控制信息，包含该页所属的表  
空间编号、页号、缓存页在bufferpool中的地址、链表节点信息以及其他控制信息。这部分数据占用内存空间就是控制块。控制信息和页是  
一一对应的，他们都被放到bufferpool中。其中控制块被放到bufferpool的前边，缓存页被放到后边。如果剩余的空间不足以存放一对控制块  
和内存页，则会在控制块存放区域和缓存页存放区中间形成碎片。如图    
- ![bufferpool](https://github.com/zhangwenm/codeStudy/blob/master/studyforbat/pic/bufferpool.png)    
每个控制块约占缓存页大小的5%，我们设置的innodb_buffer_pool_size并不包含这块区域的内存，因此innodb再向操作系统申请连续的内存  
空间时，这块内存空间一般会比innodb_buffer_pool_size大5%。  

#### free链表
当从磁盘读取数据页时，为了能将数据放到尚未使用缓存页中，Innodb将所有空闲缓存页对应的控制块作为一个节点放到一个双向链表中。如图   
- ![freelistnode!](https://github.com/zhangwenm/codeStudy/blob/master/studyforbat/pic/freelistnode.png )  
基节点占用的空间并不在申请的那块连续内存中，而是单独申请的一块40字节大小的空间。这样每当从磁盘加载页到bufferpool时，就会从free链表  
中取出一个控制块并填充上表空间、页编号等信息，然后从链表中移除。  

#### 缓存页的哈希处理  
Innodb以表空间号+页编号作为key，缓存页作为value创建了一张哈希表。当访问某个页的数据时，先根据表空间号+页编号从哈希表中查找，如果有  
对应的缓存页则可以直接使用；如果没有则从free链表中取一个空闲缓存页的控制块，并将磁盘中的页加载该控制块对应的缓存页。  

#### flush链表  
当我们修改了缓存页的数据后，缓存页中的数据就和磁盘页中的数据不一致，这些页就是脏页。这些修改过的缓存页并不会立即被刷新到磁盘，而是在  
某个时间点被刷到磁盘。为了管理这些脏页，Innodb创建了flush链表，结构和free链表相基本相同。   
- ![flushlistnode!](https://github.com/zhangwenm/codeStudy/blob/master/studyforbat/pic/flushlistnode.png "flsuh链表")  

##### LRU管理
bufferpool的内存空间是有限的，为了合理的释放缓存页，Innodb维护了一个已使用的控制块的LRU链表。并将还链表分为两部分  
- 存放热数据的young区域  
- 存放冷数据的old区域，默认情况下占总lru链表的37%    
- ![LRU!](https://github.com/zhangwenm/codeStudy/blob/master/studyforbat/pic/lrulistnode.png "LRU链表")  
这样做的目的：  
- 防止预读时将可能不使用数据放到缓存页，从而影响热数据的读取。第一次被加载到缓存页的数据的控制块将会放到old区域的头部，如果后续不使用的  
话就会从old区域移除。  
    - 线性预读：当对某个区顺序读取的次数超过设定值时，就会将下一区域所有其的页面的数据异步加载到缓存页  
    - 随即预读：当缓存页中某个区中顺序存放的页数超过13时，不管是不是顺序读取，就会异步的将该区所有其的页面的数据加载到缓存页    
- 防止全表扫描时将使用频率低的数据放到缓存页，从而影响热数据的读取。当对old区域某个控制块的缓存页第一次访问时，会在控制块中记录下此次访  
时间，当再次访问时如果时间间隔在规定的某个时间间隔内，就不会被移到young区域，否则将 它移动到young区域的头部。  
（很明显在一次全表扫描的过程中，多次访问一个页面中的时间不会超过1s）  

#### 刷脏页到磁盘
后台会有专门的线程每隔一段时间把脏页刷新到磁盘，从而不影响用户请求。主要分为两种：  
- 从LRU链表的冷数据区域刷新一部分到磁盘  
- 从flush链表中刷新一部分到磁盘
有时候如果后台线程刷脏页缓慢，导致bufferpool中没有可用空间时，就会看LRU尾部是否有可以释放的未修改页，如果有的话就释放掉；没有的话就会将  
LRU尾部的一个脏页同步刷新到磁盘。  
有时候系统特别繁忙时，也会出现用户线程批量的从flush链表中刷脏页的情况。


#### 多bufferpool实例  
为了降低多线程并发高的情况下，单实例的压力，所以当单个buffer pool特别大的时候可以拆分成若干个小的bufferpool。bufferpool大小小于1G时，即  
使通过参数设置多实例，设置也不会生效，Innodb会自动改为1。Innodb鼓励在大于或等于1G的时候进行拆分。结构如图  
- ![multipool!](https://github.com/zhangwenm/codeStudy/blob/master/studyforbat/pic/mutipool.png "多实例")   

####  innodb_buffer_pool_chunk_size  
每次调整bufferpool的时候都需要重新申请一块连续的内存，然后将旧的buffer pool的数据拷贝过去，这样极其耗费性能。为了解决这个问题，Innodb将  
bufferpool以chunk为单位向系统申请空间，也就是说chunk似乎buffferpool的基本组成单位。 
- ![chunk!](https://github.com/zhangwenm/codeStudy/blob/master/studyforbat/pic/chunk.png "chunk")   
chunk的大小只能在服务器启动的时候进行设置，不允许再运行时进行修改。chunksize的大小不包含控制块的那部分空间，所以申请空间的时候一般会多申请  
5%，用于存储控制块。  

#### bufferpool存储其他信息  
bufferpool还可以存储锁信息以及自适应哈希等信息。  
- 查看bufferpool：SHOW ENGINE INNODB STATUS\G  
- 参数：  
Total memory allocated： 代表Buffer Pool向操作系统申请的连续内存空间大小， 包括全部控制块、 缓存页、 以及碎片的大小。  
Dictionary memory allocated： 为数据字典信息分配的内存空间大小， 注意这个内存空间和Buffer Pool没啥关系， 不包括在Total memory allocated中。  
Buffer pool size： 代表该Buffer Pool可以容纳多少缓存页， 注意， 单位是页！  
Free buffers： 代表当前Buffer Pool还有多少空闲缓存页， 也就是free链表中还有多少个节点。  
Database pages： 代表LRU链表中的页的数量， 包含young和old两个区域的节点数量。  
Old database pages： 代表LRU链表old区域的节点数量。  
Modified db pages： 代表脏页数量， 也就是flush链表中节点的数量。  
Pending reads： 正在等待从磁盘上加载到Buffer Pool中的页面数量。  
当准备从磁盘中加载某个页面时， 会先为这个页面在Buffer Pool中分配一个缓存页以及它对应的控制块， 然后把这个控制块添加到LRU的old区域的头部但是这  
个时候真正的磁盘页并没有被加载进来， Pending reads的值会跟着加1。    
Pending writes LRU： 即将从LRU链表中刷新到磁盘中的页面数量。    
Pending writes flush list： 即将从flush链表中刷新到磁盘中的页面数量。    
Pending writes single page： 即将以单个页面的形式刷新到磁盘中的页面数量。    
Pages made young： 代表LRU链表中曾经从old区域移动到young区域头部的节点数量。这里需要注意， 一个节点每次只有从old区域移动到young区域头部时才  
会将Pages made young的值加1， 也就是说如果该节点本来就在young区域， 由于它符合在young区域1/4后边的要求， 下一次访问这个页面时也会将它移动到  
young区域头部， 但这个过程并不会导致Pages made young的值加1。
Page made not young： 在将innodb_old_blocks_time设置的值大于0时， 首次访问或者后续访问某个处在old区域的节点时由于不符合时间间隔的限制而不能  
将其移动到young区域头部时， Page made not young的值会加1。  
这里需要注意， 对于处在young区域的节点， 如果由于它在young区域的1/4处而导致它没有被移动到young区域头部， 这样的访问并不会将Page made notyoung  
的值加1。    
youngs/s： 代表每秒从old区域被移动到young区域头部的节点数量。  
non-youngs/s： 代表每秒由于不满足时间限制而不能从old区域移动到young区域头部的节点数量。    
Pages read、 created、 written： 代表读取， 创建， 写入了多少页。 后边跟着读取、 创建、 写入的速率。    
Buffer pool hit rate： 表示在过去某段时间， 平均访问1000次页面， 有多少次该页面已经被缓存到Buffer Pool了。    
young-making rate： 表示在过去某段时间， 平均访问1000次页面， 有多少次访问使页面移动到young区域的头部了。    
需要大家注意的一点是， 这里统计的将页面移动到young区域的头部次数不仅仅包含从old区域移动到young区域头部的次数， 还包括从young区域移动到young区域  
头部的次数（访问某个young区域的节点， 只要该节点在young区域的1/4处往后， 就会把它移动到young区域的头部） 。  
not (young-making rate)： 表示在过去某段时间， 平均访问1000次页面， 有多少次访问没有使页面移动到young区域的头部。需要大家注意的一点是，这里统计  
的没有将页面移动到young区域的头部次数不仅仅包含因为设置了innodb_old_blocks_time系统变量而导致访问了old区域中的节点但没把它们移动到young区域的次  
数， 还包含因为该节点在young区域的前1/4处而没有被移动到young区域头部的次数。  
LRU len： 代表LRU链表中节点的数量。  
unzip_LRU： 代表unzip_LRU链表中节点的数量（由于我们没有具体唠叨过这个链表， 现在可以忽略它的值） 。  
I/O sum： 最近50s读取磁盘页的总数。  
I/O cur： 现在正在读取的磁盘页数量。  
I/O unzip sum： 最近50s解压的页面数量。  
I/O unzip cur： 正在解压的页面数量。  
