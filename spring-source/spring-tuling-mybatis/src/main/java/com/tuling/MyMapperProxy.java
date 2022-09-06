package com.tuling;

import com.tuling.entity.User;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/***
 * @Author 徐庶   QQ:1092002729
 * @Slogan 致敬大师，致敬未来的你
 */
public class MyMapperProxy implements InvocationHandler {
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("查询数据库");
	// 拿到mybatis一些api  sqlsessionfacotry   configuration
		return new User();
	}
}
