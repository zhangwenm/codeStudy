## Netty 

#### 异步的RPC通讯框架
- netty对jdk自带nio进行了封装，将客户端断线重连、网络闪断、心跳处理。半包读写、网络拥塞和异常流的处理等
- 高性能、吞吐量更高、延迟更低、减少资源消耗
- 线程模型
  ![](/studyforbat/pic/netty_thread.png)
- BossGroup负责接受客户端的连接
- WorkGroup专门负责网络读写
- 类型都是NioEventLoopGroup，包含多个事件循环线程（NioEventLoop）的事件循环线程组
- 每个事件循环线程都是一个selector，用于监听注册在上边的socketchannel的网络通讯
- Boss NioEventLoop：
  - 处理accept事件，与client建立连接，生成NioSocketChannel
  - 将NioSocketChannel注册到work NioEventLoop上的selector
  - 处理任务队列的任务
- work NioEventLoop
  - 轮询注册到selector上的niosocketchannel上的读、写事件
  - 处理io读写事件
  - 处理任务队列的任务
  - 每个work NioEventLoop处理niosocketchannel上的业务时，会使用管道，管道中维护了很多handler处理器来处理channel的数据
#### 长、短链接
- 短连接：数据发送完以后就关闭
- 长连接：发送完业务数据后会发送一些检测数据，保证链接有效存活
