package com.tuling.javabasic;

import com.tuling.iocbeanlifecicle.Tank;
import org.springframework.stereotype.Component;

/***
 * @Author 徐庶   QQ:1092002729
 * @Slogan 致敬大师，致敬未来的你
 */
@Component
public class Car  {

    private String name;

    public void setTank2(Tank tank) {
        this.tank = tank;
    }

    public Tank getTank() {
        return tank;
    }

    private Tank tank;

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public Car() {
        System.out.println("构造方法....");
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                '}';
    }

}
