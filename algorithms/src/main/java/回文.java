import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * @program: 最小的K个数
 *  @author: zwm
 * @create: 2022-04-12 15:05
 **/
public class 回文 {
    public int getLongestPalindrome (String A) {
        // write code here、
        int maxLength = 1;
        for (int i = 0; i < A.length()-1; i++) {
            maxLength = Math.max(maxLength,Math.max(getLength(A,i,i),getLength(A,i,i+1)));
        }
        return maxLength;
    }

    public int getLength(String str,int i,int j){

        while(i>=0&&j<str.length()&&str.charAt(i)==str.charAt(j)){
            i--;
            j++;
        }
        return j-i-1;
    }



    }

