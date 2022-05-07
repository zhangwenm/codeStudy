import org.apache.logging.log4j.core.util.FileUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @program: codeStudy
 * @description: TODO
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

