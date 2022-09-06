package daliy.test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class HttpTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("电饭煲开始做饭");
            try {
                TimeUnit.SECONDS.sleep(6);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "白米饭";
        });

        System.out.println("我先去搞点牛奶和鸡蛋");
        System.out.println("result："+future.get(2,TimeUnit.SECONDS));
//        future.join();
    }
}
