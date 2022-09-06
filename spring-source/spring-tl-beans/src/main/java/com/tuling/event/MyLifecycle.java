package com.tuling.event;

import org.springframework.context.Lifecycle;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

/***
 * @Author 徐庶   QQ:1092002729
 * @Slogan 致敬大师，致敬未来的你
 */
//@Component
public class MyLifecycle implements /*SmartLifecycle*/ Lifecycle {
    private boolean started = false;
    public boolean isRunning() {
        return started;
    }
    public void start() {
        System.err.println("MyLifecycle starting");
        started = true;
    }
    public void stop() {
        System.err.println("MyLifecycle stopping");
        started = false;
    }

    /*@Override
    public boolean isAutoStartup() {
        return true;
    }

    @Override
    public void stop(Runnable callback) {
        System.out.println("????");
    }

    @Override
    public int getPhase() {
        return 0;
    }*/
}
