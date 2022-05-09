## ShardingSphere
- ShardingSphere包含三个重要的产品，
  - ShardingJDBC、ShardingProxy和 ShardingSidecar。
  - 其中sidecar是针对service mesh定位的一个分库分表插件，目
    前在规划中。而我们今天学习的重点是ShardingSphere的JDBC和Proxy这两个组
    件。
  - ShardingJDBC是用来做客户端分库分表的产品
    - shardingJDBC定位为轻量级 Java 框架，在 Java 的 JDBC 层提供的额外服务。它
      使⽤客户端直连数据库，以 jar 包形式提供服务，⽆需额外部署和依赖，可理解为增
      强版的 JDBC 驱动，完全兼容 JDBC 和各种 ORM 框架。
    - ![](/studyforbat/pic/shardingjdbc.png)
  - 而ShardingProxy是用来做服务端分库分表的产品
    - ShardingProxy定位为透明化的数据库代理端，提供封装了数据库⼆进制协议的服
      务端版本，⽤于完成对异构语⾔的⽀持。⽬前提供 MySQL 和 PostgreSQL 版本，
      它可以使⽤任何兼容 MySQL/PostgreSQL 协议的访问客⼾端。
    - ![](/studyforbat/pic/shardingproxy.png)
- ShardingJDBC核心概念
  - 逻辑表：水平拆分的数据库的相同逻辑和数据结构表的总称(不存在)
  - 真实表：在分片的数据库中真实存在的物理表。
  - 数据节点：数据分片的最小单元。由数据源名称和数据表组成
  - 绑定表：分片规则一致的主表和子表。
  - 广播表：也叫公共表，指所有的分片数据源中都存在的表，表结构和表中的数据
    在每个数据库中都完全一致。例如字典表。
  - 分片键：用于分片的数据库字段，是将数据库(表)进行水平拆分的关键字段。
    SQL中若没有分片字段，将会执行全路由，性能会很差。
  - 分片算法：通过分片算法将数据进行分片，支持通过=、BETWEEN和IN分片。
    分片算法需要由应用开发者自行实现，可实现的灵活度非常高。
  - 分片策略：真正用于进行分片操作的是分片键+分片算法，也就是分片策略。在
    ShardingJDBC中一般采用基于Groovy表达式的inline分片策略，通过一个包含
    分片键的算法表达式来制定分片策略，如t_user_$->{u_id%8}标识根据u_id模
    8，分成8张表，表名称为t_user_0到t_user_7。
    - NoneShardingStrategy：不分片。这种严格来说不算是一种分片策略了。只是ShardingSphere也提供了
      这么一个配置。
    - InlineShardingStrategy：最常用的分片方式
      - 配置参数： inline.shardingColumn 分片键；inline.algorithmExpression
        分片表达式
      - 实现方式： 按照分片表达式来进行分片。
    - StandardShardingStrategy只支持单分片键的标准分片策略
      - 配置参数：standard.sharding-column 分片键；standard.precise-algorithm-class-name 
      精确分片算法类名；standard.range-algorithm-class-name 范围分片算法类名
      - 实现方式：
        - shardingColumn指定分片算法。
        - preciseAlgorithmClassName 指向一个实现了io.shardingsphere.api.algorithm.sharding.standard.PreciseShardingAlgorithm  
        接口的java类名，提供按照 = 或者 IN 逻辑的精确分片示例：
        com.roy.shardingDemo.algorithm.MyPreciseShardingAlgorithm    
        rangeAlgorithmClassName 指向一个实现了io.shardingsphere.api.algorithm.sharding.standard.RangeShardingAlgorithm接口的java类名，  
        提供按照Between 条件进行的范围分片。示例：com.roy.shardingDemo.algorithm.MyRangeShardingAlgorithm  
        - 其中精确分片算法是必须提供的，而范围分片算法则是可选的。
    - ComplexShardingStrategy：支持多分片键的复杂分片策略
      - 配置参数：complex.sharding-columns 分片键(多个);complex.algorithm-class-name 分片算法实现类。
      - 实现方式：shardingColumn指定多个分片列。  
      algorithmClassName指向一个实现了org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingAlgorithm接口的java类名。  
      提供按照多个分片列进行综合分片的算法。示例：com.roy.shardingDemo.algorithm.MyComplexKeysShardingAlgorithm
    - HintShardingStrategy 强制路由