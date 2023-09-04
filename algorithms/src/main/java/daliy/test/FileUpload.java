package daliy.test;

import com.alibaba.fastjson.util.IOUtils;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author zwm
 * @desc FileUpload
 * @date 2023年03月22日 11:20
 */
@Slf4j
public class FileUpload {
    String secretId = "your_secret_id";
    String secretKey = "your_secret_key";
    String region = "your_region";
    String bucketName = "your_bucket_name";

    public static void main(String[] args) {
        fileUpload();
    }

    public static void fileUpload(){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");

            byte[] res = IOUtils.decodeBase64("qazwsxedcrfvtgb");
            md.update(res);
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }
            System.out.println(sb);

            String uuid = UUID.randomUUID().toString().replaceAll("-", "") ;
            String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern( "yyyyMMddHHmmss"));

            String key = String.join("_",uuid,time);
            System.out.println(key);

            String url = "1b7f5d0a7a9a46feb1192978a825f362_20230403150549";
            List<String> list = new ArrayList<>();
            list.add("qaz");
            list.add("wsx");
            list.add("edc");
            log.error("list:{}",list);

            Map<String, String> phoneMap = list.stream().filter(ele-> ele.equals("qwe") )
                    .collect(Collectors.toMap(Function.identity(),Function.identity(),(oldValue, newValue) -> newValue));


            log.error("phoneMap:{}",phoneMap);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
