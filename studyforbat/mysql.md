## Mysql

#### 事务及其ACID属性
- 事务是由一组SQL语句组成的逻辑处理单元,事务具有以下4个属性,通常简称为事务的ACID属性。
  - 原子性(Atomicity) ：事务是一个原子操作单元,其对数据的修改,要么全都执行,要么全都不执行。
  - 一致性(Consistent) ：在事务开始和完成时,数据都必须保持一致状态。这意味着所有相关的数据规
    则都必须应用于事务的修改,以保持数据的完整性。
  - 隔离性(Isolation) ：数据库系统提供一定的隔离机制,保证事务在不受外部并发操作影响的“独
    立”环境执行。这意味着事务处理过程中的中间状态对外部是不可见的,反之亦然。
  - 持久性(Durable) ：事务完成之后,它对于数据的修改是永久性的,即使出现系统故障也能够保持。
- 并发事务处理带来的问题
  - 更新丢失(Lost Update)或脏写：最后的更新覆盖了由其他事务所做的更新。
  - 脏读（Dirty Reads）：：事务A读取到了事务B已经修改但尚未提交的数据
  - 不可重读（Non-Repeatable Reads）：一个事务在读取某些数据后的某个时间，再次读取以前读过的数据，却发现其读出的数据已经发生了改
    变、或某些记录已经被删除了！这种现象就叫做“不可重复读。事务A内部的相同查询语句在不同时刻读出的结果不一致，不符合隔离性
  - 幻读（Phantom Reads）：一个事务按相同的查询条件重新读取以前检索过的数据，却发现其他事务插入了满足其查询条件的新数
    据，这种现象就称为“幻读”。事务A读取到了事务B提交的新增数据，不符合隔离性
- 隔离级别
  - ![](/studyforbat/pic/isolation.png)
  - Mysql默认的事务隔离级别是可重复读，用Spring开发程序时，如果不设置隔离级别默认用Mysql设置的隔
    离级别，如果Spring设置了就用已经设置的隔离级别
- 锁分类
  - 从性能上分为乐观锁(用版本对比来实现)和悲观锁
  - 从对数据库操作的类型分，分为读锁和写锁(都属于悲观锁)
    - 读锁（共享锁，S锁(Shared)）：针对同一份数据，多个读操作可以同时进行而不会互相影响
    - 写锁（排它锁，X锁(eXclusive)）：当前写操作没有完成前，它会阻断其他写锁和读锁
  - 从对数据操作的粒度分，分为表锁和行锁
  - 表锁;每次操作锁住整张表。开销小，加锁快；不会出现死锁；锁定粒度大，发生锁冲突的概率最高，并发度最低；
    一般用在整表数据迁移的场景。
    - 1、对MyISAM表的读操作(加读锁) ,不会阻寒其他进程对同一表的读请求,但会阻赛对同一表的写请求。只有当
      读锁释放后,才会执行其它进程的写操作。
      2、对MylSAM表的写操作(加写锁) ,会阻塞其他进程对同一表的读和写操作,只有当写锁释放后,才会执行其它进
      程的读写操作
  - 行锁：每次操作锁住一行数据。开销大，加锁慢；会出现死锁；锁定粒度最小，发生锁冲突的概率最低，并发度最
    高。
    - InnoDB与MYISAM的最大不同有两点：
      - nnoDB支持事务（TRANSACTION）
      - InnoDB支持行级锁
    - 一个session开启事务更新不提交，另一个session更新同一条记录会阻塞，更新不同记录不会阻塞
  - 总结：MyISAM在执行查询语句SELECT前，会自动给涉及的所有表加读锁,在执行update、insert、delete操作会自
    动给涉及的表加写锁。
    InnoDB在执行查询语句SELECT时(非串行隔离级别)，不会加锁。但是update、insert、delete操作会加行
    锁。
    简而言之，就是读锁会阻塞写，但是不会阻塞读。而写锁则会把读和写都阻塞。
####Rabbitmq
- 组成
  - 生产者、虚拟机、交换机、消费者
- amqp协议 erlang开发
- 简单模式
- 工作队列模式
- 发布订阅模式
- 生产者
  - confirm
    - 不能到达返回false，到达交换机返回ture
  - return
    - 参数设置成true
    - 到达不了队列回调
- 消费者
  - 自动ack
  - 手动ack
- 消费端限流 prefetch（手动ack）
- 死信
  - 到期
  - 队列满
  - 消费者拒绝接收，并且不重回回队列
  - 超时未被消费
- TTl+死信队列实现延时队列

- 消费堆积
  - 消费者宕机
  - 消费能力不足
  - 发送量大
- 方案
  - 上线更多的消费者
  - 批量读取
#### Rocketmq（只有java客户端） master主节点，slave只负责备份，不处理请求
- 组成
  - nameserver注册中心
  - broker实例，主从
  - producer
  - consummer
- 发送方式
  - 单向（发送后就不再关注），无返回值
  - 异步（mq处理完请求会进行回调）
  - 同步（发送完等待响应）
- 消费
  - 主动拉（新旧两种，旧的方法自己管理offset，目前使用）
  - 推
  - 顺序消息（底层通过加锁syncronize）
    - 只能保证局部有序
      - 购买、付款、奖励
  - 延迟消息（开源版只能设置固定的几个值），底层通过发往另外一个topic，定时取消息发送（会用到CAS，保证只有一个线程拉取）
  - 批量发送 （消息体大小1~4M），不能是延迟、事务消息
  - 过滤消息（一个topic多个tag，把过滤上推至broker，不满足条件的不在推送。支持生产、消费的sql过滤）
  - 事务消息，只和发送者有关 默认回查15次（性能会有所下降）
    - 本地事务与消息的一致性
    - 只能保证分布式事务的一部分
- ACL
- 消息轨迹
  - 生产、消费端开启
#####  Rocketmq 概念
- topic分片存储于不同的broker
- 生产者组：事务回查机制中需要找到生产者
- broker集群
  - 低版本不支持高可用，4.5.0以后才支持
  - Dledger高可用集群，每个节点的id都是-1
    - 接管broker的Commitlog消息存储
    - 从集群中选举master（raft算法）
      - leader，follower，candidate（没有leader时都是candidate）
      - termid，myid。类似zookeeper
      - 一轮未选举出leader时，会随机休眠再次选举
    - 完成master节点往slave节点的消息同步
- namesrever 注册中心，相互之间无通信
- 配置删除消息时间

#### 消息存盘
- 磁盘顺序写
- 零拷贝
  - mmap，内存映射，rocketmq采用机制
  - sendfile
- commitlog 生产者发的消息
- consummerqueue 消费逻辑队列
- 索引文件
- 刷盘
  - 同步
  - 异步，先刷cache，再落盘（每个500ms）
- 负载均衡
  - 生产者：轮询
  - 消费者
    - 集群模式 queue与对应的consummer建立长连接
      - 平均分
      - 轮询分
    - 广播模式
- 消息重试 默认16次 间隔时间逐渐加长
- 将超过重试阈值的消息发往默认创建的死信队列
- 一个死信队列对应一个消费者组
- 私信队列默认禁读禁写
- 消息幂等
- #### kafka（分区，区别于其他mq）
- 分布式，支持多分区，多副本基于zookeeper协调的消息系统
- 消息 默认保留一周
  - 单播 每个消息只能被消费者组中的一个消费者消费
  - 多播 把消费者放到不同的消费者组
  -offset以消费者组为单位
  - topic 逻辑单元
    - partition分区，每个分区对应一个commitlog，message按顺序存储在commitlog中，有一个唯一标识（partition内）
    ，即offset
  - 消费者offset由消费者维护
  - 副本：主副本，从副本，收发都有主副本负责
  - ISR：存活并且和主副本保持通信的节点
  - 一个分区只能被同一消费者组的一个消费者消费，但是可以被别的组消费者同时消费
  - producer发送的时候可以指定分区，也可以不指定。不指定会用hash算法取模指定一个分区
  - 发送方式
    - 同步
    - 异步，有回调
- 机械硬盘顺序读写堪比ssd随机读写，千兆网卡需要除以8（？）
- kafka不会修改也不会删除文件，保证了顺序读写
- 批量发送，压缩
- 零拷贝 mmap+sendfile
- kafka集群中有一个或者多个borker，其中一个broker会被选举为controller
- cotroller作用
  - 当某个分区主副本出现故障时，由它负责选举新的主副本
  - 当检测到某个分区的ISR出现变化时，也由它通知所有的broker更新元数据
  - 当为某个topic增加分区时，同样也由它负责让心分区为其他节点感知到
- controller选举机制
  - 集群启动时会向注册中心常见controller节点，zookeeper能保证只有一个broker能创建成功
  - 创建成功的broker成为controller
  - cotroller出现故障时，同样经过上边的步骤选举出新的controller
- partition副本的选举机制
  - controller节点感知到leader副本出现故障后，会从ISR中那第一个副本作为主副本
    - 因为第一个是被最先放进ISR的，理论上同步的数据最新
    - 如果设置了ISR副本都挂以后可以从外部选leader，虽然可以提高可用性，但是选出的主副本可能同步的数据不是最新
    - 进入ISR的条件
      - 副本节点不能产生分区，要保持和zookeeper的绘画以及跟主副本的网络连通
      - 副本能复制leader副本上的写操作，并且不能落后太多。如果超过设置的时间没有和主副本同步过一次的话，会被移出
      ISR
- 消费者消费消息的offset机制
  - consumer会定期将自己消费的offset提交给内部的topic：_cosumer_offset,提交的时候key为consumerGroupId+topic+分区号  
  ，value就是当前offset。kafka会定期清理_consumer_offsets里的offset，保留最新的那条
  - kafka默认给_consumer_offsets设置50个分区，可以通过加机器提高并发
- rebalance机制：当消费者组或者消费者分区发生变化时，会发生rebalance。只针对未指定分区的消费。rebalance过程中消费者不能进行  
消费
  - 消费者组消费者变化
  - 动态给topic增加了分区
  - 消费者组订阅了更多的分区
- rebalance过程
  - consumergroup会选择一个broker作为组协调器，来监控消费组成员的心跳以及判断是否宕机，然后开启rebalance，消费组成员启动的时候  
  会向集群的某个节点发送findCondinatorRequest请求来找到组协调器并建立网络连接
    - consumer offset提交分区：consumerGroupId+topic+分区号，该分区的主副本所在的broker即为组协调器
  - 消费者发起joinConsumerGroup请求，第一个加入到gruop的被选为消费组协调器（leader），把consumer group的情况发送过去，由它制定分区方案
  - 消费组协调器制定好分区方案后发送给组协调器，心跳检测时由组协调器发送给各个消费者，他们会根据指定分区的leader broker进行网络连接及消息消费
- rebalance策略
- 三种策略
  - range 默认
  - round_robin
  - sticky
    - 初始分配与round_robin类似
    - rebalance时
      - 分区分配尽可能均匀
      - 尽可能与上次的分配保持一致
      - 当冲突时，第一个目标优先于第二个目标
- producer发布消息
  ![](/studyforbat/pic/kafka_rebalance.png)
  - push方式退给broker，apeend到partion中，顺序写磁盘
  - partition选择方式
    - 指定partition
    - 未指定partition但制定了key，通过对key的value hash选择partition
    - 都未指定，轮询
- 消息写入流程
  ![](/studyforbat/pic/kafka_produce.png)
  - producer找到partition的leader发送消息
  - leader写入本地log
  - follower从leader拉取消息写入本地log发送ack
  - leader收到ISR中所有follower的ack后增加HW(高水位，最后commit的offset)，并向producer发送ack
  - 既不会像同步复制那样影响吞吐率，有也不会像异步那样丢数据
  - HW(水位)和LEO(log_end_offset)
    - partion的ISR中最小的LEO作为HW,consumer最多消费到HW位置，每个副本都有HW，有其自身维护。新写入leader的  
    消息不能被消费者消费，需要等到ISR中所有副本都更新完HW并且自己也更新过HW后才能被消费。对于来自内部broker的读请求  
    没有HW的限制
      ![](/studyforbat/pic/kafka_hw.png)
- 日志分段存储
  - 一个分区的消息存储在topic+分区号文件夹下，消息在分区内是分段存储的，每个段的消息存储在不同的log里，一个段位  
  的log文件最大为1G，方便加载到内存
  - offset索引文件：kafka每发4K（可配置）的消息就会记录一条当前消息的offset到index文件中
  - 时间索引文件：kafka每发4K（可配置）的消息就会记录一条当前消息发送的时间戳和offset到timeindex文件中
  - 一个日志段文件满了就会开启一个新的日志段文件