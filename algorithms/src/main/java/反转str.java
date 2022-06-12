import java.util.HashMap;
import java.util.Map;

/**
 * @program: codeStudy
 * @description:https://www.nowcoder.com/practice/c3a6afee325e472386a1c4eb1ef987f3?tpId=117&tqId=37827&rp=1&ru=/exam/oj&qru=/exam/oj&sourceUrl=%2Fexam%2Foj%3Ftab%3D%25E7%25AE%2597%25E6%25B3%2595%25E7%25AF%2587%26topicId%3D117&difficulty=undefined&judgeStatus=undefined&tags=&title=
 * @author: zwm
 * @create: 2022-04-12 15:05
 **/
public class 反转str {

    public String solve (String str) {
        // write code here
        if(str==null||str.length()<2){
            return str;
        }
        char[] res = str.toCharArray();
        int left = 0;
        int right = res.length-1;
        while(left<right){
            swap(res,left++,right--);
        }
    return String.valueOf(res);
    }
    public void swap(char[] res,int left,int right){
        res[right]^=res[left];
        res[left]^=res[right];
        res[right]^=res[left];
    }
    }

