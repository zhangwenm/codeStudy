package daliy.test;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;
@Slf4j
public class HttpTest {

    public static String cookie =
            "ticket=3f7a4f62ffed47918cd8037cb0e6a7b5";
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException, ParseException, IOException {




        isLogin();

        String hostVersion = "host%sVersion";
        String motorVersion = "motor%sVersion";
        String correlationVersion = "correlation%sVersion";
        hostVersion = String.format(hostVersion,"Hard");
        motorVersion = String.format( motorVersion,"Hard");
        correlationVersion = String.format( correlationVersion,"Hard");

        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("电饭煲开始做饭");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "白米饭";
        });

        System.out.println("我先去搞点牛奶和鸡蛋");
        System.out.println("result："+future.get(2,TimeUnit.SECONDS));
//        future.join();


        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list = list.stream().filter(ele->ele.equals(7)).collect(Collectors.toList());
        list.forEach(ele->{
            System.out.println("before ele:"+ele);
            if(ele.equals(1)){
                System.out.println("if ele:"+ele);
                return;
            }
            System.out.println("after ele:"+ele);
        });

        CountDownLatch count = new CountDownLatch(4);

//        for (int i = 0; i < 4; i++) {
//            countdown(count);
//        }
        long now = System.currentTimeMillis();
        for (int i = 0; i < 4; i++) {
            new Thread(()->{
                try{
                    Thread.sleep(2999);
                    count.countDown();
                }catch (Exception e){
                    log.error("error:",e);
                }
            }).start();
        }


        try{
            count.await(3,TimeUnit.SECONDS);
            System.out.println("over，回家 cost:"+(System.currentTimeMillis()-now));
            log.error("count value:{}",count.getCount());

        }catch (InterruptedException e){
            log.error("await error:",e);
        }
        System.out.println("end");

        test();
    }
    public static  void countdown(CountDownLatch count){

            count.countDown();

    }
    public static int fibonacci(int n) {
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        return fibonacci(n - 1) + fibonacci(n - 2);
    }
    public static int[]  quickSort(int[] arr, int left, int right) {
        if (left >= right) {
            return arr;
        }
        int index = partition(arr, left, right);
        quickSort(arr, left, index - 1);
        quickSort(arr, index + 1, right);
        return arr;

    }
    public static int partition(int[] arr, int left, int right) {
        int pivot = arr[(left + right) / 2];
        while (left <= right) {
            while (arr[left] < pivot) {
                left++;
            }
            while (arr[right] > pivot) {
                right--;
            }
            if (left <= right) {
                swap(arr, left, right);
                left++;
                right--;
            }
        }
        return left;
    }
    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    public static  void test(){
        try {
            int a = 1/0;
            System.out.println("try");
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            System.out.println("finally");
        }
    }
    public static void isLogin() throws IOException {
        HttpURLConnection conn = null;
        InputStream is = null;
        try {
            URL url = new URL("http://sim-pro.monitor.yunjichina.com.cn:19000/factory/get_hardware_info_by_ip?ip=172.22.50.70");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            //把上一步获取的cookie携带上去
            conn.setRequestProperty("cookie", cookie);
            conn.setRequestProperty("X-Forwarded-For", "172.16.176.28");
            conn.setDoInput(true);
            is = conn.getInputStream();
            byte[] b = new byte[1024];
            int len = is.read(b);
            StringBuffer sb = new StringBuffer();
            while(len != -1){
                sb.append(new String(b,0,len,"utf-8"));
                len = is.read(b);
            }
            System.out.println("read over sb:"+sb.toString());
            JSONArray ja = JSONArray.parseArray(sb.toString());
            Map<String,List<JSONObject>> map = filterDiffData(ja);
            System.out.println("read over");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }   finally{
            if(is != null){
                is.close();
            }
            if(conn != null){
                conn.disconnect();
            }

        }
        System.out.println("all close");
    }

    public static Map<String,List<JSONObject>> filterDiffData(com.alibaba.fastjson.JSONArray array) {
        Map<String,String> keyMap = new HashMap<>();
        keyMap.put("ChargeStateChanged","power");
        keyMap.put("PowerPercentChanged","power");

        keyMap.put("StartFlow","task");
        keyMap.put("EventTypeChanged","task");

        keyMap.put("PowerOn","start");
        keyMap.put("SoftShutdown","start");
        keyMap.put("HardShutdown","start");
        keyMap.put("ExcepitionShutdown","start");
        keyMap.put("NvwaOn","start");

        keyMap.put("LinkDown","api");
        keyMap.put("USBDisconnect","api");




        Map<String,List<JSONObject>> resMap = new HashMap<>();

        array.forEach(item -> {
            JSONObject jsonObject = (JSONObject)item;
            if(keyMap.containsKey(jsonObject.getString("type"))){
                List<JSONObject> list = resMap.getOrDefault(keyMap.get(jsonObject.getString("type")),new ArrayList<>());
                list.add(jsonObject);
                resMap.put(keyMap.get(jsonObject.getString("type")),list);
            }
        });
        return resMap;
    }
}
