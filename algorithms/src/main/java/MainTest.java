/**
 * @author zwm
 * @dec
 * @date 2022年05月25日 14:51
 */
public class MainTest {

    boolean crossOrNot(ListNode a,ListNode b){

        if(a==null||b==null){
            return false;
        }

        while(a!=null||b!=null){
            if(a.val == b.val){
                return true;
            }
            if(a==null){
                a = b;
            }else{
                a= a.next;
            }
            if(b==null){
                b = a;
            }else{
                b= b.next;
            }
        }
        return  false;
    }


}
