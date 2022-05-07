import java.util.*;

/**
 * @program: MAP+链表实现Lru
 * @description: TODO
 * @author: zwm
 * @create: 2022-04-12 15:24
 **/
public class Branch2Tree {

     class TreeNode{
         TreeNode left;
         TreeNode right;
         int val;
         TreeNode(int value,TreeNode left,TreeNode right){
            this.val = value;
            this.left = left;
            this.right = right;
        }
    }
    public int[][] threeOrders (TreeNode root) {
        // write code here

        List<Integer> pre = new ArrayList<>();
        List<Integer> mid = new ArrayList<>();
        List<Integer> after = new ArrayList<>();
        preOrder(root,pre);
        midOrder(root,mid);
        afterOrder(root,after);
        int[][] res = new int[3][pre.size()];
        for (int i=0;i<pre.size();i++){
            res[0][i] = pre.get(i);
            res[1][i] = mid.get(i);
            res[2][i] = after.get(i);
        }
        return res;
    }
    /*
    * 前序
    *
    * */

    public void preOrder(TreeNode treeNode, List<Integer> list){
        if(treeNode==null){
            return;
        }
        list.add(treeNode.val);
        preOrder(treeNode.left,list);
        preOrder(treeNode.right,list);
    }
    /*
     * zhong序
     *
     * */

    public void midOrder(TreeNode treeNode, List<Integer> list){
        if(treeNode==null){
            return;
        }
        midOrder(treeNode.left,list);
        list.add(treeNode.val);
        midOrder(treeNode.right,list);
    }
    /*
     * 前序
     *
     * */

    public void afterOrder(TreeNode treeNode, List<Integer> list){
        if(treeNode==null){
            return;
        }
        afterOrder(treeNode.left,list);
        afterOrder(treeNode.right,list);
        list.add(treeNode.val);
    }
    /*
     * 层序
     *
     * */

    public List<List<Integer>> levelOrder(TreeNode root){
        List<List<Integer>> res = new ArrayList<>();
        if(root==null){
            return res;
        }
        Queue<TreeNode> q = new ArrayDeque<>();
        q.add(root);

        while(!q.isEmpty()){
            List<Integer> row = new ArrayList<>();
            int n = q.size();
            for (int i= 0;i<n;i++){
                TreeNode node = q.poll();
                row.add(node.val);
                if(node.left!=null){
                    q.add(node.left);
                }
                if(node.right!=null){
                    q.add(node.right);
                }
            }
            res.add(row);
        }
        return res;
    }
}
