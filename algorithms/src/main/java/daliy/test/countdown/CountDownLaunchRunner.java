package daliy.test.countdown;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author zwm
 * @desc CountDownLaunchRunner
 * @date 2023年05月04日 14:56
 */
public class CountDownLaunchRunner {public static void main(String[] args) throws InterruptedException {
    long now = System.currentTimeMillis();
    CountDownLatch countDownLatch = new CountDownLatch(2);

    new Thread(new SeeDoctorTask(countDownLatch)).start();
    new Thread(new QueueTask(countDownLatch)).start();
    //等待线程池中的2个任务执行完毕，否则一直
    countDownLatch.await(4, TimeUnit.SECONDS);
    System.out.println("count:"+countDownLatch.getCount());
    System.out.println("over，回家 cost:"+(System.currentTimeMillis()-now));
}

}
