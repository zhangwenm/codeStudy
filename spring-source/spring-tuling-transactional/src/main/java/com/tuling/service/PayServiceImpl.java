package com.tuling.service;

import com.tuling.dao.AccountInfoDao;
import com.tuling.dao.ProductInfoDao;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * Created by xsls on 2019/6/17.
 */
@Component
@Transactional(rollbackFor = Exception.class)
public class PayServiceImpl implements PayService {

    @Autowired
    private AccountInfoDao accountInfoDao;

    @Autowired
    private ProductInfoDao productInfoDao;

    public void pay(String accountId, double money) {
        //查询余额
        double blance = accountInfoDao.qryBlanceByUserId(accountId);

        //余额不足正常逻辑
        if(new BigDecimal(blance).compareTo(new BigDecimal(money))<0) {
            throw new RuntimeException("余额不足");
        }


        //更新余额
         int retVal = accountInfoDao.updateAccountBlance(accountId,money);

        //库存-1
        //updateProductStore(1);
        ((PayService)AopContext.currentProxy()).updateProductStore(1);

        System.out.println(1/0);

    }

    @Transactional(propagation = Propagation.NESTED)
    public void updateProductStore(Integer productId) {
        try{
            productInfoDao.updateProductInfo(productId);

        }
        catch (Exception e) {
            throw new RuntimeException("内部异常");
        }
    }


}
