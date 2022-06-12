import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Stack;

/**
 * @program: 翻转链表
 * @description: https://www.nowcoder.com/practice/c56f6c70fb3f4849bc56e33ff2a50b6b?tpId=117&tqId=37814&rp=1&ru=/exam/oj&qru=/exam/oj&sourceUrl=%2Fexam%2Foj%3Ftab%3D%25E7%25AE%2597%25E6%25B3%2595%25E7%25AF%2587%26topicId%3D117&difficulty=undefined&judgeStatus=undefined&tags=&title=
 * @author: zwm
 * @create: 2022-04-12 15:24
 * 解法https://labuladong.github.io/algo/2/17/17/
 **/
public class 相加Node {
    /*
    * 双指针
    * 指针1走完链表一走链表2
    * 指针2 走完链表2走链表1
    * 节点相等说明走到共同节点，返回该节点
    *
    *
    * */
    public ListNode addInList (ListNode head1, ListNode head2) {
        // write code here
        Stack<Integer> queue1 = new Stack<>();
        Stack<Integer> queue2 = new Stack<>();
        addQueue(head1,queue1);
        addQueue(head2,queue2);
        int cal = 0;
        ListNode newHead;
        ListNode head = null;
        while(!queue1.isEmpty()||!queue2.isEmpty()||cal!=0){
            int num1 = queue1.isEmpty()?0:queue1.pop();
            int num2 = queue2.isEmpty()?0:queue2.pop();
            int temp = (num1+num2+cal)%10;
            cal = (num1+num2+cal)/10;
            newHead = new ListNode(temp);
            newHead.next = head;
            head = newHead;
        }
        return head;
    }

    public void addQueue (ListNode head1, Stack queue) {
        // write code here
        while(head1!=null){
            queue.add(head1.val);
            head1 = head1.next;
        }

    }






}
