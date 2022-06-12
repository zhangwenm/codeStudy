import org.apache.logging.log4j.core.util.FileUtils;

import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @program: codeStudy
 * @description: https://www.nowcoder.com/practice/20ef0972485e41019e39543e8e895b7f?tpId=117&tqId=37756&rp=1&ru=/exam/oj&qru=/exam/oj&sourceUrl=%2Fexam%2Foj%3Ftab%3D%25E7%25AE%2597%25E6%25B3%2595%25E7%25AF%2587%26topicId%3D117&difficulty=undefined&judgeStatus=undefined&tags=&title=
 * @author: zwm
 * @create: 2022-04-12 15:05
 **/
public class SumTwoNum {

    public int[] twoSum (int[] numbers, int target) {
        // write code here
        Map<Integer,Integer> map = new HashMap<>();

        for (int i = 0; i < numbers.length; i++) {
            if(map.containsKey(target-numbers[i])){
                return new int[]{map.get(target-numbers[i])+1,i+1};
            }else{
                map.put(numbers[i],i);
            }
        }
        throw new IllegalStateException("error");
    }
    
    public List<List<Integer>> getRes(int[] arr,int k,int start,int target){
        List<List<Integer>> res = new ArrayList<>();
        
        if(k<2){
            return res;
        }
        int lo=start;
        int hi=arr.length-1;
        if(k==2){
            while(lo<hi){
                int left = arr[lo];
                int right = arr[hi];
                int sum = left+right;
                if(sum>target){
                    while(lo<hi&&arr[hi]==right) hi--;
                }else if(sum<target){
                    while(lo<hi&&arr[lo]==left) lo++;
                }else{
                    List<Integer> tmp = new ArrayList<>();
                    tmp.add(arr[lo]);
                    tmp.add(arr[hi]);
                    while(lo<hi&&arr[lo]==left) lo++;
                    while(lo<hi&&arr[hi]==right) hi--;
                }
            }
        }else{
            for (int i = start; i < arr.length; i++) {
                List<List<Integer>> sub = getRes(arr,k-1,i+1,target-arr[i]);
                for (List<Integer> item:sub
                     ) {
                    item.add(arr[i]);
                    res.add(item);
                }
                while(i<arr.length-1&&arr[i]==arr[i+1]){
                    i++;
                }
            }
        }
        return res;
    }

    
    }

