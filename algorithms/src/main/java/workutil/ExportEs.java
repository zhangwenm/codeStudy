package workutil;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.spi.json.JacksonJsonProvider;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import workutil.dto.Device;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zwm
 * @desc ExportEs
 * @date 2023年09月04日 11:12
 */
@Slf4j
public class ExportEs {
    public static void main(String[] args) throws IOException {

//        System.out.println( 9/10 +"%");
        File theFile = new File("/Users/admin/Downloads/4.0");

        List<Device> list = new ArrayList<>();

        LineIterator it = null;
        try {
            it = FileUtils.lineIterator(theFile, "UTF-8");
            while (it.hasNext()) {
                String line = it.nextLine();
                String [] str = line.split(",");
//                System.out.println(str[0]+"-"   +str[1]);

//                String jsonString = "{\"query\": \n{\n    \n    \"bool\": {\n      \"must\": [\n        { \"match\": { \"productId\": \"2000446b6ca03ca0\" } },\n        \n        { \"match\": { \"hardwareVersion\": \"4.0\" } },\n        { \"match\": { \"type\": \"PHONE\" } }\n      ]\n    }\n  }\n}";
//                DocumentContext context = JsonPath.parse(jsonString);
//
//                // 修改 JSON 数据
//                context.set("$.query.bool.must[0].match.productId", str[0]);
//
//                // 获取修改后的 JSON 数据字符串
//                String updatedJson = context.jsonString();
//
//                // 打印修改后的 JSON 数据
////                System.out.println(updatedJson);
//
//
//
//                OkHttpClient client = new OkHttpClient().newBuilder()
//                        .build();
//                MediaType mediaType = MediaType.parse("application/json");
//                RequestBody body = RequestBody.create(mediaType, updatedJson);
//                Request request = new Request.Builder()
//                        .url("https://baidu.com.cn/device.info.*/doc/_search")
//                        .method("POST", body)
//                        .addHeader("Content-Type", "application/json")
//                        .addHeader("Authorization", "Basic YWRtaW46eXVuMTdqaTE4")
//                        .build();
//                Response response = client.newCall(request).execute();
//
//                JSONObject esRes = JSON.parseObject(response.body().string());
//                JSONObject device = esRes.getJSONObject("hits").getJSONArray("hits").getJSONObject(0).getJSONObject("_source");
//                if(device.getDouble("packUsed").equals(0.0)){
//
//                    log.error("id:{},use{}" ,str[0],device.getDouble("packUsed"));
//                }
                Device device1 = new Device();
                device1.setProductId(str[0]);
                list.add(device1);

                // do something with line
            }
        } catch (Exception e){
           e.printStackTrace();
        } finally {
            LineIterator.closeQuietly(it);
        }



        // 这里 需要指定写用哪个class去读，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        EasyExcel.write("/Users/admin/Downloads/4.0.xlsx",Device.class).sheet("模板").doWrite(list);

    }
}
