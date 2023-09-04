package daliy.test;

import com.alibaba.fastjson.JSONObject;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author zwm
 * @desc DateTest
 * @date 2023年08月28日 18:52
 */
public class DateTest {
    public static void main(String[] args) {
        JSONObject res = new JSONObject();
        res.put("code", LocalDateTime.now());
        String net = res.toJSONString();
        JSONObject res1 = JSONObject.parseObject(net);
        String date = res1.getString("code");

        DateTimeFormatter dateTimeFormatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime2 = LocalDateTime.parse("2020-05-20 20:20:20", dateTimeFormatter1);
        System.out.println("字符串转换locaDateTime==" + dateTime2);
        System.out.println("yyyy-MM-dd HH:mm:ss length==" + date.substring(0,19));
        //计算时间差
        Duration duration = Duration.between(LocalDateTime.now(),  LocalDateTime.parse(date.substring(0,19),dateTimeFormatter1) );
        System.out.println("2020-05-20 20:20:20与当前时间差：" + duration.toHours());
    }
}
