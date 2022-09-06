package com.tuling.iocbeanlifecicle;

import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.*;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.stereotype.Component;

import static org.springframework.beans.factory.support.AbstractBeanDefinition.AUTOWIRE_BY_NAME;
import static org.springframework.beans.factory.support.AbstractBeanDefinition.AUTOWIRE_BY_TYPE;

/**
 * Created by xsls on 2019/9/22.
 */
//@Component
public class MyBeanFactoryPostProcessor implements BeanDefinitionRegistryPostProcessor {
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		// 获得bean定义i
		RootBeanDefinition car = (RootBeanDefinition) beanFactory.getBeanDefinition("car");
		// 修改Class
		// car.setBeanClass(Tank.class);
		// 设置默认值 相当于xml:<property name="name" value="徐庶"/>
		// car.getPropertyValues().addPropertyValue(new PropertyValue("name","徐庶"));

		// 设置构造函数 相当于xml:<constructor-arg index="0" value="徐庶"/>
		// car.getConstructorArgumentValues().addIndexedArgumentValue(0,"徐庶2");

		// 自动装配方式
		//car.setAutowireMode(AUTOWIRE_BY_NAME);
		// 强制依赖检查：
		// car.setDependencyCheck(AbstractBeanDefinition.DEPENDENCY_CHECK_ALL);

		//car.setInitMethodName("initMethod");
	}

	 @Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		RootBeanDefinition beanDefinition=new RootBeanDefinition();
		beanDefinition.setBeanClass(Tank.class);
		registry.registerBeanDefinition("car",beanDefinition);
	}
}
