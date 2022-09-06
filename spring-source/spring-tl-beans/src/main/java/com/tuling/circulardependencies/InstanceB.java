package com.tuling.circulardependencies;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by xsls on 2019/5/29.
 */
@Component
public class InstanceB  {


    @Autowired
    private InstanceA instanceA;


    public InstanceA getInstanceA() {
        return instanceA;
    }


    public void setInstanceA(InstanceA instanceA) {
        this.instanceA = instanceA;
    }

    public InstanceB(InstanceA instanceA) {
        this.instanceA = instanceA;
    }


    public InstanceB() {
        System.out.println("InstanceB实例化");
    }

}
