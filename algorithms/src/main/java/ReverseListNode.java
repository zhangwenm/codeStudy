import java.util.List;
import java.util.Random;

/**
 * @program: 翻转链表
 * @description: https://www.nowcoder.com/practice/75e878df47f24fdc9dc3e400ec6058ca?tpId=117&tqId=37777&rp=1&ru=/exam/oj&qru=/exam/oj&sourceUrl=%2Fexam%2Foj%3Ftab%3D%25E7%25AE%2597%25E6%25B3%2595%25E7%25AF%2587%26topicId%3D117&difficulty=undefined&judgeStatus=undefined&tags=&title=
 * @author: zwm
 * @create: 2022-04-12 15:24
 * 解法https://labuladong.github.io/algo/2/17/17/
 **/
public class ReverseListNode {
    static class ListNode{
        ListNode pre;
        ListNode next;
        int val;
    }
    static class ListSingleNode{
        ListNode next;
        int val;
    }
    public static ListNode digui(ListNode head){
        if(head == null || head.next == null){
            return head;
        }
        ListNode last = digui(head.next);
        head.next.next=head;
        head.next=null;
        return last;
    }
    static ListNode pre ;
    public static ListNode reverseN(ListNode head,int n){
        if(head == null || head.next == null){
            return head;
        }
        if(n==1){
            pre=head.next;
            return head;
        }
        ListNode last = reverseN(head.next,n-1);
        head.next.next = head;
        head.next = pre;
        return last;
    }
    public static ListNode reverseMN(ListNode head,int m,int n){
        if(head == null || head.next == null){
            return head;
        }
        if(m==1){
            return reverseN(head,n);
        }
        head.next = reverseMN(head.next,m-1,n-1);
        return head;
    }

    public ListNode reverseMNO(ListNode a, ListNode b){
        ListNode pre ,cur,nxt;
        pre=null;
        cur=a;
        nxt=a;
        while(cur!=b){
            nxt = cur.next;
            cur.next=pre;
            pre=cur;
            cur=nxt;
        }
        return pre;
    }

    /*
    * https://www.nowcoder.com/practice/b49c3dc907814e9bbfa8437c251b028e?tpId=117&tqId=37746&rp=1&ru=/exam/oj&qru=/exam/oj&sourceUrl=%2Fexam%2Foj%3Ftab%3D%25E7%25AE%2597%25E6%25B3%2595%25E7%25AF%2587%26topicId%3D117&difficulty=undefined&judgeStatus=undefined&tags=&title=
    * */
    public ListNode reverseGroup(ListNode head,int k){
        if(head==null){
            return head;
        }
        ListNode a,b;
        a=head;
        b=head;
        for (int i = 0; i < k; i++) {
            if(b==null){
                return head;
            }
            b=b.next;
        }
        ListNode newHead=reverseMNO(a,b);
        a.next=reverseGroup(b,k);

        return newHead;
    }
    public static ListNode reverseNode(ListNode head){
        if(head == null || head.next == null){
            return head;
        }
        ListNode pre = head;
        ListNode cur = head.next;
        head.next = null;
        while(cur!=null){
            ListNode tmp = cur.next;
            cur.next = pre;
            pre.pre = cur;
            pre  = cur;
            cur = tmp;
        }
        return pre;
    }
    /*
    * 双指针解法
    * 1.pre 指针指向当前节点(初始时为头结点)，cur指向当前节点下一个节点
    * 2.pre.next初始化为空
    * 3.当cur不为空时循环
    *   1.取出cur的下一个节点付给临时变量tmp
    *   2.cur.next指向pre
    *   3.pre指向cur
    *   4.cur指向tmp
    * 4.返回pre
    * */
    public static ListNode reverseSingleNode(ListNode head){
        if(head == null || head.next == null){
            return head;
        }
        ListNode pre = head;
        ListNode cur = head.next;
        head.next = null;
        while(cur!=null){
            ListNode tmp = cur.next;
            cur.next = pre;
            pre  = cur;
            cur = tmp;
        }
        return pre;
    }

    public ListNode reverseSingle(ListNode head){
        if(head == null || head.next == null){
            return head;
        }
        ListNode pre = head;
        ListNode cur =head.next;
        pre.next = null;
        while(cur!=null){
            ListNode tmp = cur.next;
            cur.next = pre;
            pre = cur;
            cur = tmp;
        }
        return pre;
    }

    public static void main(String[] args) {
        ListNode node1 = new ListNode();
        node1.val = 1;
        ListNode node2 = new ListNode();
        node2.val = 2;
        ListNode node3 = new ListNode();
        node3.val = 3;
        ListNode node4 = new ListNode();
        node4.val = 4;

        node1.next = node2;

        node2.next = node3;
        node2.pre = node1;

        node3.next = node4;
        node3.pre = node2;

        node4.pre = node3;

        ListNode demo = reverseNode(node1);
        System.out.println(demo.toString());
        int a= 3;
        int b= 5;
        a ^=b;
        b ^= a;
        a ^= b;
        System.out.println("a:"+a);
        System.out.println("b:"+b);


    }

}
