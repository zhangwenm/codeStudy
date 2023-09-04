package daliy.test;


import java.util.LinkedList;

/**
 * 链表反转
 * 输入链表头节点
 * 返回新链表头节点
 */
public class Nc78ListReverse {


}

class NodeList{
    public Node head;
    public static void main(String[] args) {
        NodeList nodeList = new NodeList();
        nodeList.insert(1);
        nodeList.insert(2);
        nodeList.insert(3);
        System.out.println(nodeList);
        System.out.println(nodeList.head);
    }
    public NodeList() {
    }
    public void insert(int a){
        Node newNode = new Node(a);
        newNode.next = head;
        this.head = newNode;
    }

    @Override
    public String toString() {
        StringBuilder sb=new StringBuilder("{");
        while (this.head!=null){
            sb.append(head);
            if (head.next!=null){
            sb.append(",");
            }
            head = head.next;
        }
        sb.append("}");

        return sb.toString();
    }

    class Node{
        public Node next;
        public int val;
        public Node(int val){
            this.val=val;
        }
        public Node(){
        }
        public  boolean hasNext(Node node){
            return node.next!=null;
        }

        @Override
        public String toString() {
            return val+"";
        }
    }
}