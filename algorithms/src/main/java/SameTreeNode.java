/**
 * @author zwm
 * @dec 两个节点的最近父节点
 * @date 2022年05月25日 9:57
 */
public class SameTreeNode {
    public TreeNode helper(TreeNode root,int o1,int o2){
        if(root==null||root.val==o1||root.val==o2){
            return root;
        }
        TreeNode left = helper(root.left,o1,o2);
        TreeNode right = helper(root.right,o1,o2);
        if(left==null){
            return right;
        }
        if(right==null){
            return left;
        }
        return root;
    }
}
