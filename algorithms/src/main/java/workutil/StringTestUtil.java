package workutil;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author zwm
 * @desc MapUtil
 * @date 2023年09月20日 20:23
 */
public class StringTestUtil {

    public static void main(String[] args) {
        String str = "WTHT1234";
        System.out.println("res:"+ StringUtils.substring(str,  str.length()-4, str.length()));
    }
}
