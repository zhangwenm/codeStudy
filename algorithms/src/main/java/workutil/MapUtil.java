package workutil;

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
public class MapUtil {
    private static final Set<String> modules = new HashSet(8) {{
        add("HC");
        add("HR");
        add("HT");
    }};
    public static void main(String[] args) {
        Map<String, Integer> map1 = new HashMap<>();
        map1.put("A", 1);
        map1.put("B", 2);

        Map<String, Integer> map2 = new HashMap<>();
        map2.put("A", 3);
        map2.put("B", 4);

        map1.putAll(map2);

        map1.forEach((k,v)->{
            System.out.println("key:"+k+",v:"+v);
        });
        modules.forEach(ele->{
            System.out.println("ele:"+ele);
        });

        List<String> list = null;
        list.forEach(ele->{
            System.out.println("ele:"+ele);
        });
    }
}
