/**
 * @program: 翻转链表
 * @description: https://www.nowcoder.com/practice/6ab1d9a29e88450685099d45c9e31e46?tpId=117&tqId=37761&rp=1&ru=/exam/oj&qru=/exam/oj&sourceUrl=%2Fexam%2Foj%3Ftab%3D%25E7%25AE%2597%25E6%25B3%2595%25E7%25AF%2587%26topicId%3D117&difficulty=undefined&judgeStatus=undefined&tags=&title=
 * @author: zwm
 * @create: 2022-04-12 15:24
 *
 **/
public class RevRepeNode {
    /*
    * 双指针
    * 指针1走完链表一走链表2
    * 指针2 走完链表2走链表1
    * 节点相等说明走到共同节点，返回该节点
    *
    *
    * */
    public ListNode revNode(ListNode head) {
        if(head==null||head.next==null){
            return head;
        }
        ListNode dump = new ListNode(-1);
        dump.next = head;
        ListNode cur = dump;
        while(cur.next!=null&&cur.next.next!=null){
            if(cur.next.val==cur.next.next.val){
                int num = cur.next.val;
                while(cur.next!=null&&cur.next.val==num){
                    cur.next=cur.next.next;
                }

            }else{
                cur = cur.next;
            }
        }
        return dump.next;
    }
}
