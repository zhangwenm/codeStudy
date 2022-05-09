import java.util.*;

/**
 * @program: MAP+链表实现Lru
 * @description: https://www.nowcoder.com/practice/54275ddae22f475981afa2244dd448c6?tpId=117&tqId=37774&rp=1&ru=/exam/oj&qru=/exam/oj&sourceUrl=%2Fexam%2Foj%3Ftab%3D%25E7%25AE%2597%25E6%25B3%2595%25E7%25AF%2587%26topicId%3D117&difficulty=undefined&judgeStatus=undefined&tags=&title=
 * @author: zwm
 * @create: 2022-04-12 15:24
 **/
public class Stack2Queue {

    Stack<Integer> stack1 = new Stack<Integer>();
    Stack<Integer> stack2 = new Stack<Integer>();

    public void push(int node) {
        stack1.push(node);
    }

    public int pop() {
        if(stack2.isEmpty()){
            while(!stack1.isEmpty()){
                stack2.push(stack1.pop());
            }
        }
        return  stack2.pop();
    }
}
