package workutil.dto;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zwm
 * @desc Device
 * @date 2023年09月04日 11:17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Device {

    @ExcelIgnore
    private String noid;

    @ColumnWidth(20)
    @ExcelProperty("电话id")
    private String productId;
    @ColumnWidth(20)
    @ExcelProperty("ip")
    private String ip;
    @ColumnWidth(20)
    @ExcelProperty("地点")
    private String place;
    @ColumnWidth(20)
    @ExcelProperty("4G")
    private String netflag;
    @ColumnWidth(20)
    @ExcelProperty("固件")
    private String mirror;
    @ColumnWidth(20)
    @ExcelProperty("心跳")
    private String heart;

}
