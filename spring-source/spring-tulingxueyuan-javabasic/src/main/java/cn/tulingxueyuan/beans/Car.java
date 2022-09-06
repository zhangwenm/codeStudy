package cn.tulingxueyuan.beans;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/***
 * @Author 徐庶   QQ:1092002729
 * @Slogan 致敬大师，致敬未来的你
 */
@Component
public class Car implements BeanNameAware {
    private  String name="兰博基尼";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Autowired
    private Tank tank;

    @Override
    public String toString() {
        return "Car{" +
                "name='" + name + '\'' +
                '}';
    }

    public Car() {
        System.out.println("Car已加载");
    }

    public Car(String name) {
        this.name = name;
    }

    @Override
    public void setBeanName(String name) {
        System.out.println("Bean的名字"+name);
    }

    /*@Override
    public Car getObject() throws Exception {
        Car car = new Car();
        car.setName("大众");
        return car;
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }*/

}
