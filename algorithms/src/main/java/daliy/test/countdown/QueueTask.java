package daliy.test.countdown;

import java.util.concurrent.CountDownLatch;

/**
 * @author zwm
 * @desc QueueTask
 * @date 2023年05月04日 14:56
 */
public class QueueTask implements Runnable {

    private CountDownLatch countDownLatch;

    public QueueTask(CountDownLatch countDownLatch){
        this.countDownLatch = countDownLatch;
    }
    public void run() {
        try {
            System.out.println("开始在医院药房排队买药....");
            Thread.sleep(5000);
            System.out.println("排队成功，可以开始缴费买药");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            if (countDownLatch != null)
                countDownLatch.countDown();
        }
    }
}

