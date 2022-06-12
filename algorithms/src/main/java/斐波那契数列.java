import java.util.Arrays;

/**
 * @program: codeStudy
 * @description: https://www.nowcoder.com/practice/c6c7742f5ba7442aada113136ddea0c3?tpId=117&tqId=37760&rp=1&ru=/exam/oj&qru=/exam/oj&sourceUrl=%2Fexam%2Foj%3Ftab%3D%25E7%25AE%2597%25E6%25B3%2595%25E7%25AF%2587%26topicId%3D117&difficulty=undefined&judgeStatus=undefined&tags=&title=
 * @author: zwm
 * @create: 2022-04-12 15:05
 **/
public class 斐波那契数列 {

    public int Fibonacci(int n) {
        int[] arr  = new int[n];
        Arrays.fill(arr,1);
        return  getNumber(n,arr);
    }
    public int getNumber(int n,int[] arr){

       if(n<3 || arr[n]!=1){
           return arr[n];
       }
       return getNumber(n-1,arr)+getNumber(n-2,arr);
    }

    public static void main(String[] args) {
        int n= 5/2;
        System.out.printf("sss:"+n);
    }

    }

