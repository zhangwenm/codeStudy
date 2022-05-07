import java.util.HashMap;
import java.util.Map;

/**
 * @program: codeStudy
 * @description: TODO
 * @author: zwm
 * @create: 2022-04-12 15:05
 * 参考链接：https://blog.csdn.net/MoreWindows/article/details/6684558
 **/
public class MaxLengthArr {
    public static void main(String[] args) {
        String str1 = new String("hello");
        String str="hello";
        System.out.print(str == str1);
    }

    public int getMaxLengthArr(int[] arr){
        if(arr==null||arr.length<1){
            return arr.length;
        }
        int res = 0;
        Map<Integer,Integer> map = new HashMap<>();
        for(int left = 0, right=0;right<arr.length-1;right++){
            if(map.containsKey(arr[right])){
                map.put(arr[right],map.get(arr[right])+1);
            }else{
                map.put(arr[right],1);
            }
            while(map.get(arr[right])>1){
                map.put(arr[left],map.get(arr[left++])-1);
            }
            res = Math.max(res,right-left+1);
        }
return res;
    }
}
