/**
 * @program: codeStudy
 * @description: https://www.nowcoder.com/practice/d8b6b4358f774294a89de2a6ac4d9337?tpId=117&tqId=37735&rp=1&ru=/exam/oj&qru=/exam/oj&sourceUrl=%2Fexam%2Foj%3Ftab%3D%25E7%25AE%2597%25E6%25B3%2595%25E7%25AF%2587%26topicId%3D117&difficulty=undefined&judgeStatus=undefined&tags=&title=
 * @author: zwm
 * @create: 2022-04-12 15:05
 **/

public class MergeSortArr {
    public void merge(int A[], int m, int B[], int n) {
        int p1 = 0, p2 = 0;
        //新开一个M+n大小的数组
        int[] sorted = new int[m + n];
        int cur;
        //循环选择
        while (p1 < m || p2 < n) {
            if (p1 == m) {
                cur = B[p2++];
            } else if (p2 == n) {
                cur = A[p1++];
            } else if (A[p1] < B[p2]) {
                cur = A[p1++];
            } else {
                cur = B[p2++];
            }
            sorted[p1 + p2 - 1] = cur;
        }
        //移动
        for (int i = 0; i != m + n; ++i) {
            A[i] = sorted[i];
        }
    }
    }

