package com.tuling.circulardependencies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xsls on 2019/5/29.
 */
public class MainClass {


    public static void main(String[] args) throws Exception {
        //创建IOC容器
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(MainConfig.class);

        InstanceB instanceB = (InstanceB) ctx.getBean("instanceB");
        instanceB.getInstanceA().say();




        // 函数接口怎么玩的

       // 声明函数接口
       /* ObjectFactory callback = () -> {
            // 实现主要业务逻辑
            System.out.println("回调!");
            return new Object();
        };

        System.out.println("-----------------------------------");

        otherFunction(callback);*/
    }

    private static void otherFunction(ObjectFactory callback) {

        // 进行回调
        callback.getObject();
    }

}
