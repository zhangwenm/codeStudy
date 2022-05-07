import java.util.*;

/**
 * @program: MAP+链表实现Lru
 * @description: TODO
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
