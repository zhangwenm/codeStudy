package com.tuling.circulardependencies;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/***
 * @Author 徐庶   QQ:1092002729
 * @Slogan 致敬大师，致敬未来的你
 */
public class MainStart {

    public static void main(String[] args) throws Exception {

        // 已经加载循环依赖
        String beanName="com.tuling.circulardependencies.InstanceA";

        getBean(beanName);

        //ApplicationContext 已经加载spring容器

        InstanceA a= (InstanceA) getBean(beanName);

        a.say();
    }


    // 一级缓存 单例池   成熟态Bean
    private static Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);

    // 二级缓存   纯净态Bean (存储不完整的Bean用于解决循环依赖中多线程读取一级缓存的脏数据)
    // 所以当有了三级缓存后，它还一定要存在，  因为它要存储的 aop创建的动态代理对象,  不可能重复创建
    private static Map<String, Object> earlySingletonObjects = new ConcurrentHashMap<>(256);

    // 三级缓存
    private static Map<String, ObjectFactory> factoryEarlySingletonObjects = new ConcurrentHashMap<>(256);


    // 标识当前是不是循环依赖   如果正在创建并且从一级缓存中没有拿到是不是说明是依赖
    private static Set<String> singletonsCurrentlyInCreation =
            Collections.newSetFromMap(new ConcurrentHashMap<>(16));

    /**
     * 创建Bean
     * @param beanName
     * @return
     */
    private static Object getBean(String beanName) throws Exception {

        Class<?> beanClass = Class.forName(beanName);


        Object bean=getSingleton(beanName);

        if(bean!=null){
            return bean;
        }


        // 开始创建Bean

        singletonsCurrentlyInCreation.add(beanName);

        // 1.实例化
        Object beanInstanc = beanClass.newInstance();

        ObjectFactory factory= () -> {
            JdkProxyBeanPostProcessor beanPostProcessor=new JdkProxyBeanPostProcessor();
            return beanPostProcessor.getEarlyBeanReference(bean,beanName);
        };
        factoryEarlySingletonObjects.put(beanName,factory);
        //  只是循环依赖才创建动态代理？   //创建动态代理

        // Spring 为了解决 aop下面循环依赖会在这个地方创建动态代理 Proxy.newProxyInstance
        // Spring 是不会将aop的代码跟ioc写在一起
        // 不能直接将Proxy存入二级缓存中
        // 是不是所有的Bean都存在循环依赖  当存在循环依赖才去调用aop的后置处理器创建动态代理

        // 存入二级缓存
       // earlySingletonObjects.put(beanName,beanInstanc);

        // 2.属性赋值 解析Autowired
        // 拿到所有的属性名
        Field[] declaredFields = beanClass.getDeclaredFields();


        // 循环所有属性
        for (Field declaredField : declaredFields) {
            // 从属性上拿到@Autowired
            Autowired annotation = declaredField.getAnnotation(Autowired.class);

            // 说明属性上面有@Autowired
            if(annotation!=null){
                Class<?> type = declaredField.getType();

                //com.tuling.circulardependencies.InstanceB
                getBean(type.getName());
            }

        }


        // 3.初始化 (省略）
        // 创建动态代理

        // 存入到一级缓存
        singletonObjects.put(beanName,beanInstanc);
        return beanInstanc;
    }

    private  static Object getSingleton(String beanName){
        Object bean = singletonObjects.get(beanName);
        // 如果一级缓存没有拿到  是不是就说明当前是循环依赖创建
        if(bean==null && singletonsCurrentlyInCreation.contains(beanName)){
              // 调用bean的后置处理器创建动态代理


            bean=earlySingletonObjects.get(beanName);
            if(bean==null){
                ObjectFactory factory = factoryEarlySingletonObjects.get(beanName);
                factory.getObject();

            }

        }
        return bean;
    }

    private  static  Object getEarlyBeanReference(String beanName, Object bean){

        JdkProxyBeanPostProcessor beanPostProcessor=new JdkProxyBeanPostProcessor();
         return beanPostProcessor.getEarlyBeanReference(bean,beanName);
    }

}
