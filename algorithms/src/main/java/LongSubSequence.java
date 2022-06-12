import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: codeStudy
  *https://www.nowcoder.com/practice/f33f5adc55f444baa0e0ca87ad8a6aac?tpId=117&tqId=37799&rp=1&ru=/exam/oj&qru=/exam/oj&sourceUrl=%2Fexam%2Foj%3Ftab%3D%25E7%25AE%2597%25E6%25B3%2595%25E7%25AF%2587%26topicId%3D117&difficulty=undefined&judgeStatus=undefined&tags=&title= * @author: zwm
 * @create: 2022-04-12 15:05
 **/
public class LongSubSequence {
    int[][] record;
    public int LCS (String str1, String str2) {
        // write code here
        int m=str1.length();
        int n=str2.length();
        record = new int[m][n];
        for (int[] row:record
             ) {
            Arrays.fill(row,-1);
        }
    return dp(  str1,  0,  str2,  0);
    }
    public int dp(String str1,int m,String str2,int n){
        if(str1.length()==m||str2.length()==n){
            return 0;
        }
        if(record[m][n]!=-1){
            return record[m][n];
        }
        if(str1.charAt(m)==str2.charAt(n)){
            record[m][n]=1+dp(str1,m+1,str2,n+1);
        }else{
            record[m][n]=Math.max(dp(str1,m+1,str2,n),dp(str1,m,str2,n+1));
        }
        return record[m][n];
    }


    public String LCSStr (String str1, String str2) {
        // write code here
        int maxIen = 0;
        int [][] record = new int[str1.length()+1][str2.length()+1];
        int maxIndex=0;
        for (int i = 0; i < str1.length(); i++) {

            for (int j = 0; j < str2.length(); j++) {
                if(str1.charAt(i)==str2.charAt(j)){
                    record[i+1][j+1] = 1+record[i][j];
                    if(record[i+1][j+1]>maxIen){
                        maxIen = record[i+1][j+1];
                        maxIndex = i;
                    }
                }else{
                    record[i+1][j+1] = 0;
                }

            }
        }
        return str1.substring(maxIndex-maxIen+1,maxIndex+1);
    }
    }

