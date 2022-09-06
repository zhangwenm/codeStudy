package com.tuling;

import com.tuling.iocbeanlifecicle.Car;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;

/***
 * @Author 徐庶   QQ:1092002729
 * @Slogan 致敬大师，致敬未来的你
 */
public class IOCTest {
    @Test
    public void testiocApi(){
       DefaultListableBeanFactory beanFactory=new DefaultListableBeanFactory();

        GenericBeanDefinition beanDefinition=new GenericBeanDefinition();
        beanDefinition.setBeanClass(Car.class);

        beanFactory.registerBeanDefinition("car",beanDefinition);
        beanFactory.getBean("car");
    }
}
