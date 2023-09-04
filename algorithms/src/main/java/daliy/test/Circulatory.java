package daliy.test;

import org.apache.logging.log4j.core.util.Assert;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * @Auther: NOC
 * @Date: 2022/9/28 11:10
 * @Description:
 */
public class Circulatory {
    public static void main(String[] args) {
        String str = null;
        Integer num=0;
        addNumber(num++);
        System.out.println("num:"+num);
        BigDecimal b1 = new BigDecimal(10.0);
        String indexName = String.format("%sdevice.info.yunfan/doc", "123");
        System.out.println(indexName);
        Assert.requireNonEmpty(str,"str is empty");
        System.out.println("%:"+4/3);
    }

    public static int getNumber(){
        int[] arr = new int[]{1,2,3};
        int i = 0;

        int var3;
        label51: {
            if (i==arr.length-1) {
                var3 = arr[i];
                i++;
                break label51;
            }

            var3 = 0;
            System.out.println("inner:"+var3);
            return var3;
        }
        System.out.println("end:"+var3);
        return var3;
    }
    public static  void addNumber(Integer num){
        System.out.println("inner num:"+num);
    }
}
