package workutil;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.StringUtils;
import workutil.dto.Device;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zwm
 * @desc ExportEs
 * @date 2023年09月04日 11:12
 */
@Slf4j
public class ExportFromEs {
    public static void main(String[] args) throws IOException {


        List<Device> list = new ArrayList<>();

        try {


                String jsonString = " \"{\\n     \\\"size\\\": 10000,  \\n  \\\"from\\\": 10000 ,\\n    \\\"query\\\":{\\n        \\\"bool\\\":{\\n            \\\"must\\\":[\\n              \\n                {\\n                    \\\"match\\\":{\\n                        \\\"hardwareVersion\\\":\\\"2.0\\\"\\n                    }\\n                },\\n                {\\n                    \\\"match\\\":{\\n                        \\\"type\\\":\\\"PHONE\\\"\\n                    }\\n                }\\n            ]\\n        }\\n    }\\n}\\n\\n\\n\\n\");";
//                DocumentContext context = JsonPath.parse(jsonString);

                // 修改 JSON 数据
//                context.set("$.query.bool.must[0].match.hardwareVersion","2.0");
//                context.set("$.query.bool.must[0].match.type","PHONE");
//                // 获取修改后的 JSON 数据字符串
//                String updatedJson = context.jsonString();

                // 打印修改后的 JSON 数据
//                System.out.println(updatedJson);



            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "{\n     \"size\": 20000,  \n  \"from\": 0 ,\n    \"query\":{\n        \"bool\":{\n            \"must\":[\n              \n                {\n                    \"match\":{\n                        \"hardwareVersion\":\"2.0\"\n                    }\n                },\n                {\n                    \"match\":{\n                        \"type\":\"PHONE\"\n                    }\n                }\n            ]\n        }\n    }\n}\n\n\n\n");
            Request request = new Request.Builder()
                    .url("https://esnew.yunjichina.com.cn/device.info.phone/doc/_search")
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Basic YWRtaW46eXVuMTdqaTE4")
                    .build();
            Response response = client.newCall(request).execute();

                JSONObject esRes = JSON.parseObject(response.body().string());
                JSONArray devices = esRes.getJSONObject("hits").getJSONArray("hits");
                devices.forEach(item -> {
                    JSONObject jsonObject = ((JSONObject)item).getJSONObject("_source");
                    String productId = jsonObject.getString("productId");
                    String phoneIp = jsonObject.getString("phoneIp");
                    String placeName = jsonObject.getString("placeName");
                    String iccid = jsonObject.getString("iccid");

                    Long ts = jsonObject.getLong("ts");

                    String date = "";
                    if (ts != null) date = DateUtils.timestampToString(ts);

                    Device device = new Device();
                    device.setProductId(productId);
                    device.setIp(phoneIp);
                    device.setPlace(placeName);
                    device.setNetflag("Y");
                    if(StringUtils.isBlank(iccid)){
                        device.setNetflag("N");
                    }
                    device.setHeart(date);
                    list.add(device);
                });
                // do something with line
        } catch (Exception e){
           e.printStackTrace();
        }
        // 这里 需要指定写用哪个class去读，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        EasyExcel.write("/Users/admin/Downloads/phone2.xlsx",Device.class).sheet("模板").doWrite(list);

    }
}
