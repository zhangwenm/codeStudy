import java.util.HashMap;
import java.util.Map;

/**
 * @program: codeStudy
 * @author: zwm
 * https://www.nowcoder.com/practice/459bd355da1549fa8a49e350bf3df484?tpId=117&tqId=37797&rp=1&ru=/exam/oj&qru=/exam/oj&sourceUrl=%2Fexam%2Foj%3Ftab%3D%25E7%25AE%2597%25E6%25B3%2595%25E7%25AF%2587%26topicId%3D117&difficulty=undefined&judgeStatus=undefined&tags=&title=
 * @create: 2022-04-12 15:05
 **/
public class MaxSubArr {

    public int maxLength(int[] arr) {
        Map<Integer,Integer> map=new HashMap<>();
        int max=0;
        for(int i=0,j=0;i<arr.length;i++){
            if(map.containsKey(arr[i])){
                j=Math.max(j,map.get(arr[i])+1);
            }

            map.put(arr[i],i);

            max=Math.max(max,i-j+1);

        }
        return max;



    }

    public int FindGreatestSumOfSubArray(int[] array) {
        int sum=0;
        int max=array[0];
        for (int i = 0; i < array.length; i++) {
            sum=Math.max(array[i]+sum,array[i]);
            max=Math.max(sum,max);
        }
        return max;
    }

    }

