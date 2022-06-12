import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * @program: 翻转链表
 * @description: https://www.nowcoder.com/practice/65cfde9e5b9b4cf2b6bafa5f3ef33fa6?tpId=117&tqId=37747&rp=1&ru=/exam/oj&qru=/exam/oj&sourceUrl=%2Fexam%2Foj%3Ftab%3D%25E7%25AE%2597%25E6%25B3%2595%25E7%25AF%2587%26topicId%3D117&difficulty=undefined&judgeStatus=undefined&tags=&title=
 * @author: zwm
 * @create: 2022-04-12 15:24
 * 解法https://labuladong.github.io/algo/2/17/17/
 **/
public class MergeKNode {
    /*
    * 双指针
    * 指针1走完链表一走链表2
    * 指针2 走完链表2走链表1
    * 节点相等说明走到共同节点，返回该节点
    *
    *
    * */
    public ListNode mergeKLists(ArrayList<ListNode> lists) {

        PriorityQueue<ListNode> queue = new PriorityQueue<>((a,b)->(a.val-b.val));

        ListNode dump = new ListNode(-1);
        ListNode p = dump;


        for(ListNode head:lists){
            if(head!=null){
                queue.add(head);

            }
        }
        while(!queue.isEmpty()){
            ListNode node = queue.poll();
            p.next = node;
            if(node != null&&node.next != null){
                queue.add(node.next);
            }
            p = p.next;
        }
        return  dump.next;
    }
}
