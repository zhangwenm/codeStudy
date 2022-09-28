package daliy.test;

import java.sql.ResultSet;

/**
 * @Auther: NOC
 * @Date: 2022/9/28 11:10
 * @Description:
 */
public class Circulatory {
    public static void main(String[] args) {
        getNumber();
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
}
