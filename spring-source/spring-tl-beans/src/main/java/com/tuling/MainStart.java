package com.tuling;

import com.tuling.compent.TulingDao;
import com.tuling.config.MainConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by xsls on 2019/7/7.
 */
public class MainStart {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:/beans/beans.xml");
		ctx.getBean("car");
		/*System.out.println(ctx.getBean(TulingDao.class).getTulingDataSource().getFlag());*/
	}

}
