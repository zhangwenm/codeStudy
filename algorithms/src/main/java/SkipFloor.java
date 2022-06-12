import java.util.HashMap;
import java.util.Map;

/**
 * @program: codeStudy
 * @description: https://www.nowcoder.com/practice/8c82a5b80378478f9484d87d1c5f12a4?tpId=117&tqId=37764&rp=1&ru=/exam/oj&qru=/exam/oj&sourceUrl=%2Fexam%2Foj%3Ftab%3D%25E7%25AE%2597%25E6%25B3%2595%25E7%25AF%2587%26topicId%3D117&difficulty=undefined&judgeStatus=undefined&tags=&title=
 * @author: zwm
 * @create: 2022-04-12 15:05
 *
 * 跳台阶，每次跳一个或者两个，到达n阶有几种跳法
 *跳到n阶的方法为跳到n-1和n-2的方法之和
 *
 **/
public class SkipFloor {
    int[] dp;
    public int jumpFloor(int target) {
        dp= new int[target+1];
    return getMaxStep(target);

    }
    public int getMaxStep(int target){
        if(target<3){
            return target;
        }
        if(dp[target]>0){
            return dp[target];
        }
        dp[target] = getMaxStep(target-1)+getMaxStep(target-2);
        return dp[target];
    }
}

