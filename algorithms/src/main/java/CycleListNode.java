import java.util.HashMap;
import java.util.Map;

/**
 * @program: codeStudy
 * @description: https://www.nowcoder.com/practice/20ef0972485e41019e39543e8e895b7f?tpId=117&tqId=37756&rp=1&ru=/exam/oj&qru=/exam/oj&sourceUrl=%2Fexam%2Foj%3Ftab%3D%25E7%25AE%2597%25E6%25B3%2595%25E7%25AF%2587%26topicId%3D117&difficulty=undefined&judgeStatus=undefined&tags=&title=
 * @author: zwm
 * @create: 2022-04-12 15:05
 **/
public class CycleListNode {

    public boolean hasCycle(ReverseListNode.ListNode head) {
        if(head==null){
            return false;
        }

        ReverseListNode.ListNode slow=head;
        ReverseListNode.ListNode fast=head;
        while(fast!=null&&fast.next!=null){

            fast=fast.next.next;
            slow=slow.next;
            if(fast==slow){
                return true;
            }
        }
        return false;


    }
    }

