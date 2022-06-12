import java.util.LinkedList;
import java.util.Queue;

public class MaxLengthTreeNode {
    int res = 0;
    public int getMaxLength (TreeNode root) {
        if(root ==null){
            return  0;
        }
        int left =getMaxLength(root.left);
        int right = getMaxLength(root.right);
        res = Math.max(res,left+right);
        return 1+Math.max(left,right);
    }


    public int minDepth(TreeNode root){
        if(root==null){
            return 0;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        int deepth=1;
        queue.offer(root);
        while(!queue.isEmpty()){
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                if(node.left==null&&node.right==null){
                    return deepth;
                }
                if(node.left!=null){
                    queue.offer(node.left);
                }
                if(node.right!=null){
                    queue.offer(node.right);
                }
            }
            deepth++;
        }
        return deepth;
    }
}
