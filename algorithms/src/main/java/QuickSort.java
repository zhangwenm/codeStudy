import java.util.Random;

/**
 * @program: codeStudy
 * @description: TODO
 * @author: zwm
 * @create: 2022-04-12 15:05
 * 参考链接：https://blog.csdn.net/MoreWindows/article/details/6684558
 **/
public class QuickSort {
    public static void main(String[] args) {
        String str1 = new String("hello");
        String str="hello";
        System.out.print(str == str1);
    }

    public int partion(int[] arr,int start,int end){
        int left = start,right = end;
        int k = arr[left];
        while(left < right){
            while(left < right && k <= arr[right]){
                right--;
            }
            if(left < right){
                arr[left] = arr[right];
                left++;
            }
            while(left < right && arr[left]<k){
                left++;
            }
            if(left<right){
                arr[right] = arr[left];
                right--;
            }
        }
        arr[left] = k;
        return left;
    }
    Random random = new Random();
    public int randomeNum(int[] arr,int left ,int right){
        int num = random.nextInt(right-left+1)+right;
        swap(  arr,  num,  left);
        return partion(arr,left,right);
    }
    public void swap(int[] arr,int left,int right){
        if(left!=right){
            arr[left] ^= arr[right];
            arr[right] ^= arr[left];
            arr[left] ^= arr[right];
        }
    }
    public void quickSort(int[] arr,int start,int end ){
        if(start< end){

            int mid = randomeNum(arr,start,end);
            quickSort(arr,start,mid-1);
            quickSort(arr,mid+1,end);
        }
    }
}
