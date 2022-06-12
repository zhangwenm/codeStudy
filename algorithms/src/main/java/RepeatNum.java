import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;

/**
 * @program: codeStudy
 * @description: https://www.nowcoder.com/practice/20ef0972485e41019e39543e8e895b7f?tpId=117&tqId=37756&rp=1&ru=/exam/oj&qru=/exam/oj&sourceUrl=%2Fexam%2Foj%3Ftab%3D%25E7%25AE%2597%25E6%25B3%2595%25E7%25AF%2587%26topicId%3D117&difficulty=undefined&judgeStatus=undefined&tags=&title=
 * @author: zwm
 * @create: 2022-04-12 15:05
 **/
public class RepeatNum {
    public static void main(String[] args) {
        int[] arr = new int[]{1,3,4,2,2};
        int res = getSum(arr);
        System.out.printf("res:"+res);
        ListNode node = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(3);
        ListNode node4 = new ListNode(3);
        node3.next = node4;
        node2.next =node3;
        node.next = node2;

        LinkedHashMap map = new LinkedHashMap();
        map.put("a",1);
        map.put("b",1);
        map.put("c",1);
        for (Object str:map.keySet()
             ) {
            System.out.println("res:"+str.toString());
        }
        LinkedHashSet<ListNode> set = new LinkedHashSet<>();
        while(node!=null){
            if(set.contains(node)){
                set.remove(node);
            }else{

                set.add(node);
            }
            node = node.next;
        }
        for (ListNode node1:set
             ) {
            System.out.println("node:"+node1.val);
        }
    }
    public static  int getSum (int[] numbers) {
        // write code here
        Map<Integer,Integer> map = new HashMap<>();

        for (int i = 0; i < numbers.length; i++) {
            while(numbers[i]!=(i+1)){
                if(numbers[i]==numbers[numbers[i]-1]){
                    return numbers[i];
                }
                swap(numbers,i,numbers[i]-1);
            }
        }
        return -1;
    }


    public static  void swap(int[] arr,int left,int right){
        if(left!=right){
            arr[left] ^= arr[right];
            arr[right] ^= arr[left];
            arr[left] ^= arr[right];
        }
    }
    }

