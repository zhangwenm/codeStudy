package daliy.test;


import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * @author zwm
 * @desc HeartbeatMsgPhone 新电话设备心跳信息
 * @date 2023年03月07日 13:35
 */
public class HeartbeatMsg {
    /*
     * @desc 设备id
     */
    private String productId;
    /*
     * @desc 信息类型
     */
    private String type;
    /*
     * @desc cellular/none 网络状态（4G，有线，无线），错误码
     */
    private String wan;
    /*
     * @desc 如果4G信号，那么就是4G的信号强度
     */
    private Integer rssi;
    /*
     * @desc 当前4G模块连接的网络
     */
    private String rat;
    /*
     * @desc 电流
     */
    private Integer current;
    /*
     * @desc 电压
     */
    private Integer voltage;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public Integer getVoltage() {
        return voltage;
    }

    public void setVoltage(Integer voltage) {
        this.voltage = voltage;
    }

    public String getWan() {
        return wan;
    }

    public void setWan(String wan) {
        this.wan = wan;
    }

    public Integer getRssi() {
        return rssi;
    }

    public void setRssi(Integer rssi) {
        this.rssi = rssi;
    }

    public String getRat() {
        return rat;
    }

    public void setRat(String rat) {
        this.rat = rat;
    }
}
