## ReentrantLock：

1. 内部类Sync继承自AbstractQueuedSynchronizer，AbstractQueuedSynchronizer继承自AbstractOwnableSynchronizer

   AOS内部属性exclusiveOwnerThread：独占锁状态下持有锁的线程

   AQS内部有一个同步队列CLH，为双向链表，由Node节点组成，node节点有以重要属性

   1. EXCLUSIVE（独占）SHARED（共享）
   2. CANCELLED：代表出现异常，中断引起的，需要废弃结束，SIGNAL：可被唤醒，CONDITION：条件等待，PROPAGATE：传播，是信号量状态标识（waitStatus）的几种状态
   3. prev前向指针，next后驱节点
   4. thread 
   5. state 同步器状态
   6. head头节点
   7. tail尾节点

2. 分为公平锁（FairSync）和非公平锁（NonfairSync），都通过继承sync来实现

   * 公平锁枷锁过程  

   ​    1. lock() --> acquire(1)(该方法为AQS内部方法) 尝试获取锁  

``` java
    public final void acquire(int arg) {
        //获取锁失败并且入队成功则中断当前线程
        if (!tryAcquire(arg) &&
            acquireQueued(addWaiter(Node.EXCLUSIVE), arg))
            selfInterrupt();
    }  
```

​          2. tryAquire由FairSync实现

   ``` java
      protected final boolean tryAcquire(int acquires) {
          //当前线程
          final Thread current = Thread.currentThread();
          //获取同步器状态，如果为0说明锁还未被其他线程持有
          int c = getState();
          if (c == 0) {
          //hasQueuedPredecessors:如果同步队列为空或者队列第一个待唤醒的线程为当前线程，返回false
          //compareAndSetState:将同步器状态设置为1（unsafe的cas，底层为汇编原子指令cmpchg），
          //setExclusiveOwnerThread：将持有锁的线程设置为的当前线程，返回true（返回true则上层方法不进入中断逻辑） 
              if (!hasQueuedPredecessors() &&
                  compareAndSetState(0, acquires)) {
                  setExclusiveOwnerThread(current);
                  return true;
              }
          }
          //如果锁被持有，则判断持有锁的线程是否为当前线程
          else if (current == getExclusiveOwnerThread()) {
              //持有锁的线程为当前线程,则将status加1
              //此处逻辑执行时，为获得所得线程，所以状态更新不用CAS
              int nextc = c + acquires;
              if (nextc < 0)
                  throw new Error("Maximum lock count exceeded");
              setState(nextc);
              return true;
          }
          return false;
      }
   ```

  3. 获取锁失败时acquire方法逻辑中，执行acquireQueued(addWaiter(Node.EXCLUSIVE), arg)) 入队逻辑

  * addWaiter(Node.EXCLUSIVE)
  ``` java
       private Node addWaiter(Node mode) {
           Node node = new Node(Thread.currentThread(), mode);
           // Try the fast path of enq; backup to full enq on failure
           Node pred = tail;
           //当队列不为空时，将封装当前线程的node节点插入尾部，并返回node
           if (pred != null) {
               node.prev = pred;
               if (compareAndSetTail(pred, node)) {
                   pred.next = node;
                   return node;
               }
           }
           //队列为空时
           enq(node);
           //返回封装有当前线程的node
           return node;
       }
       //enq(node)执行逻辑;
       private Node enq(final Node node) {
               for (;;) {
                   Node t = tail;
                   if (t == null) { // Must initialize
                   //第一次进来初始同步队列（此时头尾节点为同一个节点，节点中线程为null）
                   //初始化完成后继续循环
                       if (compareAndSetHead(new Node()))
                           tail = head;
                   } else {
                   //第二次进来时，将封装当前线程的node设置为尾节点，此时尾节点中线程为当前线程
                       node.prev = t;
                       if (compareAndSetTail(t, node)) {
                           t.next = node;
                           return t;
                       }
                   }
               }
       }
       //acquireQueued(final Node node, int arg)执行逻辑（即真正入队逻辑）;
           final boolean acquireQueued(final Node node, int arg) {
               boolean failed = true;
               try {
                   boolean interrupted = false;
                   for (;;) {
                       final Node p = node.predecessor();
                       //如果前一个节点为head则尝试获取锁
                       if (p == head && tryAcquire(arg)) {
                       //获取成功将当前节点设置为头结点（pred以及thread为null）
                           setHead(node);
                           //原头结点从链表中剔除
                           p.next = null; // help GC
                           failed = false;
                           //返回
                           return interrupted;
                       }
                       //如果当前节点不是头结点或者获取锁失败
                       //第一次返回false不阻塞：如果节点成为队列中第一个节点则会再次尝试获取锁
                       if (shouldParkAfterFailedAcquire(p, node) &&
                           parkAndCheckInterrupt())
                           interrupted = true;
                   }
               } finally {
                    if (failed)
                       cancelAcquire(node);
               }
           }
           //执行shouldParkAfterFailedAcquire(p, node)
           //p为当前节点的前驱结点，node当前节点
           private static boolean shouldParkAfterFailedAcquire(Node pred, Node node) {
               int ws = pred.waitStatus;
               //获取前驱结点的waitStatus，如果为-1返回true直接阻塞
               if (ws == Node.SIGNAL)
                   return true;
               if (ws > 0) {
                   do {
                       node.prev = pred = pred.prev;
                   } while (pred.waitStatus > 0);
                   pred.next = node;
               } else {
               //初始化时waitStatus为0，将其设置为-1
                   compareAndSetWaitStatus(pred, ws, Node.SIGNAL);
               }
               //返回false会再次进入此方法，则返回true进行阻塞
               return false;
           }
           
       ```