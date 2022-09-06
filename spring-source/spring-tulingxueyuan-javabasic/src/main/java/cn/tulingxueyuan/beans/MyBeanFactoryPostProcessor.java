package cn.tulingxueyuan.beans;

import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.stereotype.Component;

import static org.springframework.beans.factory.support.AbstractBeanDefinition.AUTOWIRE_BY_NAME;

/***
 * @Author 徐庶   QQ:1092002729
 * @Slogan 致敬大师，致敬未来的你
 */
@Component
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        GenericBeanDefinition car = (GenericBeanDefinition) beanFactory.getBeanDefinition("car");
       // car.setScope("prototype");

        // 设置实例化的构造器
      /*ConstructorArgumentValues values=new ConstructorArgumentValues();
        values.addIndexedArgumentValue(0,"测试");
        car.setConstructorArgumentValues(values);*/
      //设置属性
        /*MutablePropertyValues propertyValues=new MutablePropertyValues();
        propertyValues.addPropertyValue("tank",new Tank());
        car.setPropertyValues(propertyValues);*/

        // 设置自动注入的类型
        // car.setAutowireMode(AUTOWIRE_BY_NAME);
    }
}
