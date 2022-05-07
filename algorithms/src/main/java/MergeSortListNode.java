import java.util.HashMap;
import java.util.Map;

/**
 * @program: codeStudy
 * @description: TODO
 * @author: zwm
 * @create: 2022-04-12 15:05
 **/

public class MergeSortListNode {
    class ListNode {
        int val;
        ListNode next = null;

        ListNode(int val) {
            this.val = val;
        }
    }
    public ListNode Merge(ListNode list1,ListNode list2) {
        ListNode res = new ListNode(-1);
        ListNode p1=list1,p2=list2,p=res;
        while(p1!=null&&p2!=null){
            if(p1.val> p2.val){
                p.next=p2;
                p2=p2.next;
            }else{
                p.next=p1;
                p1=p1.next;
            }
            p=p.next;
        }
        if(p1==null){
            p.next=p2;
        }else{
            p.next=p1;
        }
        return res.next;
    }
    }

