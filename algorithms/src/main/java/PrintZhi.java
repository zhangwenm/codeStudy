import java.util.*;

/**
 * @program: codeStudy
*https://www.nowcoder.com/practice/91b69814117f4e8097390d107d2efbe0?tpId=117&tqId=37722&rp=1&ru=/exam/oj&qru=/exam/oj&sourceUrl=%2Fexam%2Foj%3Ftab%3D%25E7%25AE%2597%25E6%25B3%2595%25E7%25AF%2587%26topicId%3D117&difficulty=undefined&judgeStatus=undefined&tags=&title=
 * @author: zwm
 * @create: 2022-04-12 15:05
 **/
public class PrintZhi {
    class TreeNode{
         TreeNode left;
         TreeNode right;
        int val;
        TreeNode(int value,  TreeNode left,  TreeNode right){
            this.val = value;
            this.left = left;
            this.right = right;
        }
    }
    public ArrayList<ArrayList<Integer>> Print(TreeNode pRoot) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        if(pRoot==null){
            return res;
        }
        Queue<TreeNode> tmp = new ArrayDeque<>();
        tmp.offer(pRoot);
        boolean start = true;
        while(!tmp.isEmpty()){
            ArrayList<Integer> row = new ArrayList<>();
            int n =  tmp.size();
            for (int i = 0; i < n; i++) {
                TreeNode node  = tmp.poll();
                row.add(node.val);
                if(node.left!=null){
                    tmp.offer(node.left);
                }
                if(node.right!=null){
                    tmp.offer(node.right);
                }

            }
            if(!start){
                Collections.reverse(row);
            }
            res.add(row);
            start= !start;
        }
        return res;
    }
    }

