import java.util.HashMap;
import java.util.Map;

/**
 *https://www.nowcoder.com/practice/11ae12e8c6fe48f883cad618c2e81475?tpId=117&tqId=37842&rp=1&ru=/exam/oj&qru=/exam/oj&sourceUrl=%2Fexam%2Foj%3Ftab%3D%25E7%25AE%2597%25E6%25B3%2595%25E7%25AF%2587%26topicId%3D117&difficulty=undefined&judgeStatus=undefined&tags=&title=
 *
 * @author: zwm
 * @create: 2022-04-12 15:05
 **/
public class SumBigerNum {

    public String solve (String s, String t) {
        // write code here
        if(s.length()<=0){
            return t;
        }
        if(t.length()<=0){
            return t;
        }
        if(s.length()<t.length()){
            String temp = s;
            s=t;
            t=temp;
        }
        int cal=0;
        char[] res = new char[s.length()];
        for (int i = s.length()-1; i >=0 ; i--) {
            int tmp = s.charAt(i)-'0'+cal;
            int j = i-s.length()+t.length();
            if(j>=0){
                tmp+=t.charAt(j)-'0';
            }
            cal = tmp/10;
            tmp=tmp%10;
            res[i] = (char)(tmp+'0');
        }
        String out = String.valueOf(res);
        if(cal>0){
            out = '1'+out;
        }
        return out;
    }
    }

