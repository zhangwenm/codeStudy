import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: codeStudy
 * @description: TODO
 * @author: zwm
 * @create: 2022-04-12 15:05
 **/
public class ThreadExtend {
        public Map map = new HashMap<>();

        public static  final InheritableThreadLocal<String> inheritableThreadLocal = new InheritableThreadLocal<>();
        public static final ThreadLocal<String> threadLocal = new ThreadLocal<>();
        public static void main(String[] args) {
            inheritableThreadLocal.set("Inheritable hello");
            threadLocal.set("hello");
            System.out.printf("111"+Thread.currentThread().getName());
            new Thread(()->{
                System.out.printf("111"+Thread.currentThread().getName());
                System.out.println(String.format("子线程可继承值：%s",inheritableThreadLocal.get()));
                System.out.println(String.format("子线程值：%s",threadLocal.get()));
                new Thread(()->{
                    System.out.println(String.format("孙子线程可继承值：%s",inheritableThreadLocal.get()));
                    System.out.println(String.format("孙子线程值：%s",threadLocal.get()));
                }).start();

            }).start();


        }
    }

