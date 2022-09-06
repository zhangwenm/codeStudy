package com.tuling.event;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/***
 * @Author 徐庶   QQ:1092002729
 * @Slogan 致敬大师，致敬未来的你
 */
@Component
@Lazy
public class ContextRefreshedEventListener  implements ApplicationListener<ContextRefreshedEvent> {



    //@Async
    //@EventListener(ContextRefreshedEvent.class)
    public void onApplicationEvent(ContextRefreshedEvent event)  {
        if(event.getApplicationContext().getParent() == null)//root application context 没有parent，他就是老大.
        {
            //Thread.sleep(5000);
            //需要执行的逻辑代码，当spring容器初始化完成后就会执行该方法。
            System.out.println("\n\n\n\n\n______________\n\n\n加载了\n\n_________\n\n");
        }

    }

}
