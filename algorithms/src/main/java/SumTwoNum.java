import org.apache.logging.log4j.core.util.FileUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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
    }

