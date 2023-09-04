package workutil.dto;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
/**
 * @author zwm
 * @desc EmployeeReadListener
 * @date 2023年09月04日 11:18
 */

import java.util.ArrayList;
import java.util.List;

public class EmployeeReadListener extends AnalysisEventListener<Device> {

    //员工集合
    private static List<Device> employeeList = new ArrayList<>();

    // 每读一样，会调用该invoke方法一次
    @Override
    public void invoke(Device data, AnalysisContext context) {
        employeeList.add(data);
        System.out.println("解析到一条数据：" + data);
    }

    // 全部读完之后，会调用该方法
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        System.out.println("全部解析完成");
    }

    /**
     * 返回读取到的员工集合
     * @return
     */
    public static List<Device> getStudentList() {
        return employeeList;
    }
}