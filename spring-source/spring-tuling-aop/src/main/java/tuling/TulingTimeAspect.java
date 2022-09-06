package tuling;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Created by xsls on 2019/6/10.
 */
/*@Aspect
@Order(0)
@Component*/
public class TulingTimeAspect {


    @Pointcut("execution(* tuling.TulingCalculate.*(..))")
    public void pointCut(){};

    long begin=0;

    @Before(value = "pointCut()")
    public void methodBefore() throws Throwable {
        begin = System.currentTimeMillis();
    }

    @After(value = "pointCut()")
    public void methodAfter(JoinPoint joinPoint) {
        long end = System.currentTimeMillis();
        String methodName = joinPoint.getSignature().getName();
        System.out.println("执行方法【"+methodName+"】耗时"+(end-begin));
    }


}
