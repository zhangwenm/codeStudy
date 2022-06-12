## Elasticsearch

#### 分布式搜索引擎，基于lucene框架 
- lucene
  - 只能在java中使用
  - 代码繁杂
  - 不支持集群
  - 索引和应用在同一机器
- ES 配合三方分词器（最粗粒度，最细粒度）
  - 描述
    - 一个分布式的实时文档存储，每个字段可以被索引与搜索。
    - 一个分布式实时分析搜索引擎。
    - 能胜任上百个服务节点的扩展，并支持 PB 级别的结构化或者非结构化数据。
  - INDEX(database数据库)，相似文档的集合
    - 滚动索引
      - 别名滚动指向新创建的索引
      - 创建一个名字为logs-0000001 、别名为logs_write 的索引：
        - ```PUT /logs-000001
          {
          "aliases": {
          "logs_write": {}
          }
          }
        - 添加1000个文档到索引logs-000001，然后设置别名滚动的条件
        - ```POST /logs_write/_rollover
          {
          "conditions": {
          "max_age":   "7d",
          "max_docs":  1000,
          "max_size":  "5gb"
          }
          }
        - 如果别名logs_write指向的索引是7天前（含）创建的或索引的文档数>=1000或索引的大小>= 5gb，  
        则会创建一个新索引 logs-000002，并把别名logs_writer指向新创建的logs-000002索引
    - 索引拆分
      - 先设置索引只读：
      - 然后拆分
      - 只有在创建时指定了index.number_of_routing_shards 的索引才可以进行拆分，ES7开始将不再有这个限制。
    - mapping（文档结构）支持搜索的字符串字段
    - type（表）
    - Documnet（行）
    - field字段
      - 字段类型 datatypes
        - Core Datatypes     核心类型
        - ```string    
          text and keyword  
          Numeric datatypes  
          long, integer, short, byte, double, float, half_float, scaled_float  
          Date datatype    
          date  
          Boolean datatype  
          boolean  
          Binary datatype  
          binary  
          Range datatypes       范围  
          integer_range, float_range, long_range, double_range, date_range
        - 3.2 Complex datatypes 复合类型
        - ``
          Array datatype
          数组就是多值，不需要专门的类型
          Object datatype
          object ：表示值为一个JSON 对象
          Nested datatype
          nested：for arrays of JSON objects（表示值为JSON对象数组 ）``
        - Geo datatypes  地理数据类型
        - ```Geo-point datatype
          geo_point：for lat/lon points  （经纬坐标点）
          Geo-Shape datatype
          geo_shape：for complex shapes like polygons （形状表示）
        - Specialised datatypes 特别的类型
        - ```IP datatype
          ip：for IPv4 and IPv6 addresses
          Completion datatype
          completion：to provide auto-complete suggestions
          Token count datatype
          token_count：to count the number of tokens in a string
          mapper-murmur3
          murmur3：to compute hashes of values at index-time and store them in the index
          Percolator type
          Accepts queries from the query-dsl
          join datatype
          Defines parent/child relation for documents within the same index
        - 字段定义属性介绍
          - ```analyzer   指定分词器
               normalizer   指定标准化器
               boost        指定权重值
               coerce      强制类型转换
               copy_to    值复制给另一字段
               doc_values  是否存储docValues
               dynamic
               enabled    字段是否可用
               fielddata
               eager_global_ordinals
               format    指定时间值的格式
               ignore_above
               ignore_malformed
               index_options
               index
               fields
               norms
               null_value
               position_increment_gap
               properties
               search_analyzer
               similarity
               store
               term_vector
- 倒排索引
  - 存储的的时候进行关键词切分，建立关键词与文档的对应关系表，称之为倒排索引（反向索引）
  - 分词（位置，出现次数）
  - 排序
- DSL查询
  - query DSL
  - filter DSL
- query DSL 结果不会放进缓存
  - term 不会进行分词
  - match 模糊匹配 会对关键字分词
- 文档映射
  - 动态映射：不需要mapping，自动根据字段识别类型
  - 静态映射：先定义好字段类型、分词器等 
  - copy_to：定义映射字段时，指定复制到另外一个字段下实现一个字段的组合
  - index：属性用来配置是否开启分词建立对应关系
  - store：是否存储文档数据
  - String 包含text keyword
    - text会进行分词，不能排序和聚合（支持搜索的字符串设置为text字段进行分词器属性设置，支持模糊匹配）
    - keyword不会进分词用于索引结构化内容的字段，例如电子邮件地址，主机名，状态代码，邮政编码或标签。  
    它们通常用于过滤，排序，和聚合。Keyword 字段只能按其确切值进行搜索。
  - 修改静态映射：命令行将原索引数据导入新索引，重命名新索引
- 分页
  - from + size 浅分页
    - "浅"分页可以理解为简单意义上的分页。它的原理很简单，就是查询前20条数据，然后截断前10条，只返回10-20的数据。  
    这样其实白白浪费了前10条的查询。
    - 因为es是基于分片的，假设有5个分片，from=100，size=10。则会根据排序规则从5个分片中各取回110条数据数据，  
    然后汇总成550条数据后选择最后面的10条数据。
  - scroll 深分页
    - scroll 类似于sql中的cursor，使用scroll，每次只能获取一页的内容，然后会返回一个scroll_id。根据返回的这  
    个scroll_id可以不断地获取下一页的内容，所以scroll并不适用于有跳页的情景。
    - scroll=5m表示设置scroll_id保留5分钟可用。
      使用scroll必须要将from设置为0。
      size决定后面每次调用_search搜索返回的数量
    - scroll删除
      - 根据官方文档的说法，scroll的搜索上下文会在scroll的保留时间截止后自动清除，但是我们知道scroll是非常消耗资源的，  
      所以一个建议就是当不需要了scroll数据的时候，尽可能快的把scroll_id显式删除掉。
  - search_after 深分页
    - scroll 的方式，官方的建议不用于实时的请求（一般用于数据导出），因为每一个 scroll_id 不仅会占用大量的资源，而且会生  
    成历史快照，对于数据的变更不会反映到快照上。
    - search_after 分页的方式是根据上一页的最后一条数据来确定下一页的位置，同时在分页请求的过程中，如果有索引数据的增删改查，  
    这些变更也会实时的反映到游标上。但是需要注意，因为每一页的数据依赖于上一页最后一条数据，所以无法跳页请求。
    - 为了找到每一页最后一条数据，每个文档必须有一个全局唯一值，官方推荐使用 _uid 作为全局唯一值，其实使用业务层的 id 也可以。
    - 使用search_after必须要设置from=0。
      这里我使用timestamp和_id作为唯一值排序。
      我们在返回的最后一条数据里拿到sort属性的值传入到search_after。
#### 架构
- master，集群中只有一个master
  - 管理索引、分配分片
  - 负责切换 primary shard 和 replica shard 身份等
  - 维护元数据
  - 管理集群节点状态
  - 不负责写入和查询
- DataNode
  - 数据写入、检索，内存要大一些
  - 分片、副本。高版本默认一个
- 一个索引可以分为多个分片，每个分片又可以设置多个副本
  - 分片
    - 每个分片都有主分片
    - 主分片和从分片不在同一节点
    - 分片允许水平分割/扩展内容容量
    - 允许在分片上做分布式、并行的操作，提高吞吐率、
    - 将数据分片是为了提高可处理数据的容量和易于进行水平扩展，为分片做副本是为了提高集群的稳定性和提高并发量。
    - 不能改变分片数量
  - 副本
    - 容灾
    - 扩展搜索/吞吐量，搜索可以再所有的副本上并行进行
    - 可以动态的改变副本数量
- 乐观并发控制
  - 高版本用的seqno控制，低版本用version控制
- 写入流程
  - 选择任意一个DataNode发送请求，该节点成为协调节点
  - 计算得到要写入的分片 shard = hash(routing) % number_of_primary_shards
    routing 是一个可变值，默认是文档的 _id
  - 协调节点将请求路由到主分片所在的节点，处理写请求写入索引库并同步到其他副本
  - 主分片和从分片都处理好文档，返回client
    ![](/studyforbat/pic/es_write.png)
- 检索原理
  - client发起请求，某个DataNode收到请求成为协调节点
  - 协调节点将请求广播到数据节点，这些数据节点的分片会处理查询请求
  - 每个分片进行数据查询，将查询到符合条件的数据放入一个优先队列，并将这些数据的文档ID、节点信息、分片信息  
  返回个协调节点
  - 协调节点将结果汇总、全局排序
  - 协调节点向包含这些文档ID的分片发送请求，收到数据后返回给客户端
    ![](/studyforbat/pic/es_read.png) 
- es准时数据库
  - 当数据写入到es分片时，会先写入到内存中，然后通过内存的buffer生成一个segment并刷到文件系统缓存中  
  之后文件才能被检索，默认每秒刷一次。在内存和磁盘之间是文件系统缓存。
  - 当达到默认的时间（1 秒钟）或者内存的数据达到一定量时，会触发一次刷新（Refresh），将内存中的数据生  
  成到一个新的段上并缓存到文件缓存系统 上，稍后再被刷新到磁盘中并生成提交点。
  - 这里的内存使用的是 ES 的 JVM 内存，而文件缓存系统使用的是操作系统的内存。
  - 为了避免丢失数据，Elasticsearch 添加了事务日志（Translog，5s刷一次盘），事务日志记录了所有还没有持久化到磁盘的数据。
    - 一个新文档被索引之后，先被写入到内存中，但是为了防止数据的丢失，会追加一份数据到事务日志中。
    - 不断有新的文档被写入到内存，同时也都会记录到事务日志中。这时新数据还不能被检索和查询
    - 当达到默认的刷新时间或内存中的数据达到一定量后，会触发一次  Refresh，将内存中的数据以一个新段形式刷新到  
    文件缓存系统中并清空内存。这时虽然新段未被提交到磁盘，但是可以提供文档的检索功能且不能被修改。
    - 随着新文档索引不断被写入，当日志数据大小超过 512M 或者时间超过 30 分钟时，会触发一次 Flush。
    - 存中的数据被写入到一个新段同时被写入到文件缓存系统，文件系统缓存中数据通过 Fsync 刷新到磁盘中，  
    生成提交点，日志文件被删除，创建一个空的新日志。
  - 段合并
    - 由于自动刷新流程每秒会创建一个新的段 ，这样会导致短时间内的段数量暴增。而段数目太多会带来较大的麻烦。
    - 每一个段都会消耗文件句柄、内存和 CPU 运行周期。更重要的是，每个搜索请求都必须轮流检查每个段然后合并查询结果，所以段越多，搜索也就越慢。
    - Elasticsearch 通过在后台定期进行段合并来解决这个问题。小的段被合并到大的段，然后这些大的段再被合并到更大的段。
    - 段合并的时候会将那些旧的已删除文档从文件系统中清除。被删除的文档不会被拷贝到新的大段中。合并的过程中不会中断索引和搜索。
    - 段合并在进行索引和搜索时会自动进行，合并进程选择一小部分大小相似的段，并且在后台将它们合并到更大的段中，这些段既可以是未提交（未刷盘）的也可以是已提交（已刷盘）的。
    - 合并结束后老的段会被删除，新的段被 Flush 到磁盘，同时写入一个包含新段且排除旧的和较小的段的新提交点，新的段被打开可以用来搜索。
    - 段合并的计算量庞大， 而且还要吃掉大量磁盘 I/O，段合并会拖累写入速率，如果任其发展会影响搜索性能。
    - Elasticsearch 在默认情况下会对合并流程进行资源限制，所以搜索仍然有足够的资源很好地执行。
- 调整配置参数
  - 给每个文档指定有序的具有压缩良好的序列模式 ID，避免随机的 UUID-4 这样的 ID，这样的 ID 压缩比很低，会明显拖慢 Lucene。
  - 对于那些不需要聚合和排序的索引字段禁用 Doc values。Doc Values 是有序的基于 document=>field value 的映射列表。
    对于非text字段类型，doc_values默认情况下是打开的
  - 不需要做模糊检索的字段使用 Keyword 类型代替 Text 类型，这样可以避免在建立索引前对这些文本进行分词。
  - 如果你的搜索结果不需要近实时的准确度，考虑把每个索引的 index.refresh_interval 改到 30s 。
  - 如果你是在做大批量导入，导入期间你可以通过设置这个值为 -1 关掉刷新，还可以通过设置 index.number_of_replicas: 0 关闭副本。  
  别忘记在完工的时候重新开启它。
  - 避免深度分页查询建议使用 Scroll 进行分页查询。普通分页查询时，会创建一个 from+size 的空优先队列，每个分片会返回 from+size   
  条数据，默认只包含文档 ID 和得分 Score 给协调节点。
  - 如果有 N 个分片，则协调节点再对（from+size）×n 条数据进行二次排序，然后选择需要被取回的文档。当 from 很大时，排序过程会变得很沉重，占用 CPU 资源严重。
  - 减少映射字段，只提供需要检索，聚合或排序的字段。其他字段可存在其他存储设备上，例如 Hbase，在 ES 中得到结果后再去 Hbase 查询这些字段。
  - 创建索引和查询时指定路由 Routing 值，这样可以精确到具体的分片查询，提升查询效率。路由的选择需要注意数据的分布均衡。
- 不同节点介紹
  - 当节点node.master：false node.data: false时，该节点只能作为路由节点，用来协调主节点和数据节点，路由请求
  - 数据节点主要是存储索引数据的节点，并对文档进行操作，对cpu、内存、IO等要求较高
- 生产设置建议
  - 设置三台或以上的主节点，维护整个集群的状态。再根据数据量设置一批数据节点，负责存储数据。如果用户请求比较频繁的话  
  ，数据节点压力比较大，可以适当设置一些client节点，负责负载均衡请求转发等
#### 选举
- 正常情况下，集群中只有一个Leader，其他节点全是Follower。Follower 都是被动接收请求，从不主动发送任何请求。Candidate   
候选人，候补者；应试者  是从Follower到Leader的中间状态。
Raft中引入任期(term) 的概念，每个term内最多只有一个Leader。term 在Raft算法中充当逻辑时钟的作用。服务器之间通信的时候会携带这个term，  
如果节点发现消息中的term小于自己的term，则拒绝这个消息；如果大于本节点的term，则更新自己的term。如果一个Candidate或者Leader   
发现自己的任期过期了，它会立即回到Follower状态。
- es选举实现
  - 1、 Elasticsearch 的选主是 ZenDiscovery 模块负责的，主要包含 Ping（节点之间通过这个 RPC 来发现彼此）和 Unicast（单播模块包含一个主机列表以控制哪些节点需要 ping 通）这两部分 
  - 2、对所有可以成为 master 的节点（node.master: true）根据 nodeId 字典排序，每次选举每个节点都把自己所知道节点排一次序，然后选出第一个（第 0 位）节点，暂且认为它是 master 节点。 
  - 3、如果对某个节点的投票数达到一定的值（可以成为 master 节点数 n/2+1）并且该节点自己也选举自己，那这个节点就是 master。否则重新选举一直到满足上述条件。 
  - 4、master 节点的职责主要包括集群、节点和索引的管理，不负责文档级别的管理；
Raft选举流程为：
- 增加当前节点本地的current term，切换到Candidate状态；
- 当前节点投自己一票，并且并行给其他节点发送RequestVote RPC (让大家投他) ；
  - 然后等待其他节点的响应，会有如下三种结果：
  - 如果接收到大多数服务器的选票，那么就变成Leader。成为Leader后，向其他节点发送心跳消息来确定自己的地位并阻止新的选举。
  - 如果收到了别人的投票请求，且别人的term比自己的大，那么候选者退化为Follower；
  - 如果选举过程超时，再次发起一轮选举；
- ES实现Raft算法选主流程
  - ES实现中，候选人不先投自己，而是直接并行发起RequestVote，这相当于候选人有投票给其他候选人的机会。  
  这样的好处是可以在一定程度上避免3个节点同时成为候选人时，都投自己，无法成功选主的情况。

- ES不限制每个节点在某个term上只能投一票， 节点可以投多票，这样会产生选出多个主的情况：
  - Node2被选为主，收到的投票为：Node2、 Node3；
  - Node3被选为主，收到的投票为：Node3、 Node1；
  - 对于这种情况，ES的处理是让最后当选的Leader成功，作为Leader。如果收到RequestVote请求，  
  他会无条件退出Leader状态。在本例中，Node2先被选为主，随后他收到Node3的RequestVote请求，  
  那么他退出Leader状态，切换为CANDIDATE，并同意向发起RequestVote候选人投票。因此最终Node3成功当选为Leader。 
#### 动态维护参选节点列表
- 在此之前，我们讨论的前提是在集群节点数量不变的情况下，现在考虑下集群扩容、缩容、节点临时或永久离线时是如何处理的。  
- 在7.x之前的版本中，用户需要手工配置minimum_master_nodes, 来明确告诉集群过半节点数应该是多少，并在集群扩缩容时调整他。现在，集群可以自行维护。
- 在取消了discovery.zen.minimum_master_nodes 配置后，现在的做法不再记录“quorum”法定数量的具体数值，取而代之的是记录一个节点列表，  
这个列表中保存
- 所有具备master资格的节点(有些情况下不是这样，例如集群原本只有1个节点，当增加到2个的时候，这个列表维持不变，因为如果变成2，  
当集群任意节点离线，都会导致无法选主。这时如果再增加一个节点，集群变成3个，这个列表中就会更新为3个节点)，称为VotingConfiguration，  
他会持久化到集群状态中。
- 在节点加入或离开集群之后，Elasticsearch 会自动对VotingConfiguration 做出相应的更改，以确保集群具有尽可能高的弹性。  
在从集群中删除更多节点之前，等待这个调整完成是很重要的。你不能一次性停止半数或更多的节点。(感觉大面积缩容时候这个操作就比较感人了，一部分一部分缩)。  
默认情况下，ES自动维护VotingConfiguration。有新节点加入的时候比较好办，但是当有节点离开的时候，他可能是暂时的重启，也可能是永久下线。  
你也可以人工维护VotingConfiguration，配置项为：cluster.auto_shrink_voting_configuration，当你选择人工维护时，有节点永久下线，  
需要通过voting exclusions API将节点排除出去。如果使用默认的自动维护VotingConfiguration，也可以使用voting exclusions API来排除  
节点，例如一次性下线半数以上的节点。
- 如果在维护VotingConfiguration时发现节点数量为偶数，ES会将其中一个排除在外，保证VotingConfiguration是奇数。因为当是偶数的情况下，  
网络分区将集群划分为大小相等的两部分，那么两个子集群都无法达到“多数”的条件。