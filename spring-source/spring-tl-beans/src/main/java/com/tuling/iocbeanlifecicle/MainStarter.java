package com.tuling.iocbeanlifecicle;

import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.lang.reflect.Constructor;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xsls on 2019/8/15.
 */
public class MainStarter {

	public static void main(String[] args)   {
		// 加载spring上下文
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig.class);

		Car car =  (Car) context.getBean("car");
		System.out.println(car.getName());
	}







	/*public static void main(String[] args) {
		List<Tank> list=new ArrayList<>();
		Tank tank2=new Tank();
		tank2.setName("坦克2号");
		tank2.setIndex(2);
		list.add(tank2);

		Tank tank=new Tank();
		tank.setName("坦克1号");
		tank.setIndex(1);
		list.add(tank);

		*//*list.sort((o1, o2) -> {
			return Integer.compare(o1.getIndex(), o2.getIndex());
		});*//*

		list.forEach(tank1 -> System.out.println(tank1.getIndex()));
	}*/
}
