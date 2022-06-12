/**
 * @program: 翻转链表
 * @description: https://www.nowcoder.com/practice/6ab1d9a29e88450685099d45c9e31e46?tpId=117&tqId=37761&rp=1&ru=/exam/oj&qru=/exam/oj&sourceUrl=%2Fexam%2Foj%3Ftab%3D%25E7%25AE%2597%25E6%25B3%2595%25E7%25AF%2587%26topicId%3D117&difficulty=undefined&judgeStatus=undefined&tags=&title=
 * @author: zwm
 * @create: 2022-04-12 15:24
 * 解法https://labuladong.github.io/algo/2/17/17/
 **/
public class 公共Node {
    static class ListNode{
        ListNode pre;
        ListNode next;
        int val;
    }
    /*
    * 双指针
    * 指针1走完链表一走链表2
    * 指针2 走完链表2走链表1
    * 节点相等说明走到共同节点，返回该节点
    *
    *
    * */
    public ListNode FindFirstCommonNode(ListNode pHead1, ListNode pHead2) {
        ListNode p1 = pHead1,p2 = pHead2;

        while(p1!=p2){
            if(p1==null){
                p1 = pHead2;
            }else{
                p1 = p1.next;
            }
            if(p2==null){
                p2 = pHead1;
            }else{
                p2 = p2.next;
            }
        }
        return  p1;
    }
}
