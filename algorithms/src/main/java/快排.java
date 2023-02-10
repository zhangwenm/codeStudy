import java.util.Random;

/**
 * @author zwm
 * @dec
 * @date 2023年02月09日 13:09
 */
public class 快排 {
    public void swap(int[] arr ,int left ,int right){
        if(left!=right){
            arr[left] ^= arr[right];
            arr[right] ^= arr[left];
            arr[left] ^= arr[right];
        }

    }
    Random random = new Random();
    public int randomArr(int[] arr,int left,int right){
        int num = random.nextInt(right-left+1)+left;
        swap(arr,left,num);
        return partition(arr,left,right);
    }

    public int partition(int[] arr,int left ,int right){
        int lo = left;
        int hi = right;
        int k = arr[lo];
        while(lo<hi){
            while(lo<hi&&k<arr[hi]){
                hi--;
            }
            if(lo<hi){
                arr[lo]=arr[hi];
                lo++;
            }
        }
        while(lo<hi){
            while(lo<hi&&arr[lo]<=k){
                lo++;
            }
            if(lo<hi){
                arr[hi]=arr[lo];
                hi--;
            }
        }
        arr[lo] = k;
        return lo;
    }
public void mySort(int[] arr){

quickSort(arr,0,arr.length-1);
}
    public void quickSort(int[] arr,int left,int right){
        if(left<right){
            int mid = randomArr(arr,left,right);
            quickSort(arr,left,mid-1);
            quickSort(arr,mid+1,right);
        }

    }
    public static void main(String[] args) {
        Integer a = new Integer(3);
        Integer b= new Integer(3);;
        System.out.printf("aaza:"+(a==b));
    }
}
