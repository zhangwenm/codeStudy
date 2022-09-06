package com.tuling.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.tuling.component.MyImportBeanDefinitionRegistrar;
import com.tuling.component.MyImportSelector;
import com.tuling.entity.User;
import com.tuling.mapper.UserMapper;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.io.IOException;
import java.lang.reflect.Proxy;

/**
 * Created by xsls
 */
@Configuration
@MapperScan(basePackages = {"com.tuling.mapper"})
@ComponentScan(basePackages = {"com.tuling"})
@Repository
public class MyBatisConfig {    // =====>   spring.xml


	/**
	 * <bean class="com.alibaba.druid.pool.DruidDataSource" id="dataSource">  </bean>
	 *
	 * <bean class="org.mybatis.spring.SqlSessionFactoryBean" id="sqlSessionFactory">
	 *     datasource
	 *     mapper文件的路径
	 *     别名
	 *
	 *   </bean>
	 *
	 *   <mapper-scan basePackage=""/>
	 *   SqlSessionFactoryBean   创建的过程会初始化  sqlsession 数据源  事务  mapperProxyFactory
	 * @return
	 * @throws IOException
	 */
	@Bean    // =====  >    <bean  class="org.mybatis.spring.SqlSessionFactoryBean">
	public SqlSessionFactoryBean sqlSessionFactory( ) throws IOException {
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		factoryBean.setDataSource(dataSource());
		// 设置 MyBatis 配置文件路径
		factoryBean.setConfigLocation(new ClassPathResource("mybatis/mybatis-config.xml"));
		// 设置 SQL 映射文件路径
		factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/*.xml"));
		factoryBean.setTypeAliases(User.class);

		return factoryBean;


	}

    public DataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/mybatis_example");
        return dataSource;
    }

    /*@Bean
	public UserMapper userMapper(){
		return (UserMapper) Proxy.newProxyInstance(UserMapper.class.getClassLoader(), new Class[] { UserMapper.class }, new MyMapperProxy());
	}*/

	/*@Bean("userMapper2")
    public UserMapper userMapper(){
    	return (UserMapper) Proxy.newProxyInstance(UserMapper.class.getClassLoader(), new Class[]{UserMapper.class}, (proxy, method, args1) -> {
			// 查询数据库...   mybatis
			System.out.println("查询");
			return new User();
		});
	}*/
}
