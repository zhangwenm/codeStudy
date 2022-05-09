import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * @program: 最小的K个数
 * @description: https://www.nowcoder.com/practice/6a296eb82cf844ca8539b57c23e6e9bf?tpId=117&tqId=37765&rp=1&ru=/exam/oj&qru=/exam/oj&sourceUrl=%2Fexam%2Foj%3Ftab%3D%25E7%25AE%2597%25E6%25B3%2595%25E7%25AF%2587%26topicId%3D117&difficulty=undefined&judgeStatus=undefined&tags=&title=
 * @author: zwm
 * @create: 2022-04-12 15:05
 **/
public class SmallK {
    /*
    * 最小的k个数
    *
    * */
    public ArrayList<Integer> GetLeastNumbers_Solution(int [] input, int k) {
        ArrayList<Integer> res = new ArrayList<>();
        if(input==null||input.length==0||k==0){
            return res;
        }
        PriorityQueue<Integer> queue = new PriorityQueue<>((a,b)->b.compareTo(a));
        for(int i=0;i<k;i++){
            queue.add(input[i]);
        }
        for(int j=k;j<input.length;j++){
            if(queue.peek()>input[j]){
                queue.poll();
                queue.offer(input[j]);
            }
        }
        while(!queue.isEmpty()){
            res.add(queue.poll());
        }
        return res;
    }


    /*
    * 第k大
    *
    *https://www.nowcoder.com/practice/e016ad9b7f0b45048c58a9f27ba618bf?tpId=117&tqId=37791&rp=1&ru=/exam/oj&qru=/exam/oj&sourceUrl=%2Fexam%2Foj%3Ftab%3D%25E7%25AE%2597%25E6%25B3%2595%25E7%25AF%2587%26topicId%3D117&difficulty=undefined&judgeStatus=undefined&tags=&title=
    * */
    public int findKth(int[] a, int n, int K) {
        return  quickSort(a,0,n-1,K);
    }

    public int quickSort(int[] a, int start, int end,int k) {
        // write code here
        int mid = partition(  a,  start ,  end);
        if(k==mid-start+1){
            return a[mid];
        }else if(k<mid-start+1){
            return  quickSort(a,start,mid-1,k);
        }else{
            return  quickSort(a,mid+1,end,k-(mid-start+1));
        }
    }
    public int partition(int[] arr,int left ,int right){
        int temp = arr[left];
        int start = left,end = right;
        while(start<end){
            while(start<end&&temp>arr[end]){
                end--;
            }
            if(start<end){
                arr[start] = arr[end];
                start++;
            }
            while(start<end&&temp<=arr[start]){
                start++;
            }
            if(start<end){
                arr[end] = arr[start];
                end--;
            }
        }
        arr[start] = temp;
        return start;
    }



    }

