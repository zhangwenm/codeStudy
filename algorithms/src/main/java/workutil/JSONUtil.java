package workutil;

import com.alibaba.fastjson.JSONObject;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * @author zwm
 * @desc JSONUtil
 * @date 2023年09月07日 18:20
 */
public class JSONUtil {
    public static void main(String[] args) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("low", 1);
        jsonObject.put("high", 2);
        System.out.println(jsonObject.toJSONString());
        jsonObject.put("command", "phoneStatus");

        jsonObject.keySet().forEach(e->{
            String value = jsonObject.getString(e);
            //计算时间差

            jsonObject.put(e, 1);
//            jsonObject.put(e+"1", 1);

        });
    }
}
