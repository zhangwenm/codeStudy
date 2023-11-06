package workutil;

import com.google.common.collect.Lists;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zwm
 * @desc DateUtils
 * @date 2023年10月11日 13:39
 */
public class DateUtils {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String timestampToString(Long date) {
        Instant instant = Instant.ofEpochMilli(date);

        // 使用 ZoneId 定义时区，这里使用默认时区
        ZoneId zoneId = ZoneId.systemDefault();

        // 将 Instant 转换为 LocalDateTime
        LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();

        // 使用 DateTimeFormatter 格式化日期时间
        String res  = localDateTime.format(formatter);
        return res;
    }
    private static final Map<String, List<String>> types = new HashMap(16) {{
        put("SCQS", Lists.newArrayList("router", "mcu_fw"));
        put("SC", Lists.newArrayList("deb_package", "sys_package"));
        put("WT", Lists.newArrayList("deb_package", "sys_package"));
    }};
    public static void main(String[] args) {
        LocalDate startDay = LocalDate.of(2023, 10, 1);
        LocalDate endDay = LocalDate.of(2023, 10, 1);
        daysTwoDate(startDay, endDay);

        List<String> modules = getModules("SC", "QS");

        System.out.println(modules);


    }


    public static List<String> getModules(String type, String subtype) {
        // 使用 ZoneId 来指定时区
        return types.getOrDefault(type+subtype,types.get(type));
    }

    public static List<LocalDate> daysTwoDate(LocalDate start, LocalDate end) {
        // 使用 ZoneId 来指定时区



        LocalDate startDay = LocalDate.of(start.getYear(), start.getMonth(), start.getDayOfMonth());
        LocalDate endDay = LocalDate.of(end.getYear(), end.getMonth(), end.getDayOfMonth());

        LocalDate currentDay = startDay;

        List<LocalDate> res = Lists.newArrayList();

        while (!currentDay.isAfter(endDay)) {
            res.add(currentDay);
            currentDay = currentDay.plusDays(1);
        }
        return res;
    }
}
