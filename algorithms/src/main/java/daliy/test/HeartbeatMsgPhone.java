package daliy.test;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson2.util.BeanUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

/**
 * @author zwm
 * @desc HeartbeatMsgPhone 新电话设备心跳信息
 * @date 2023年03月07日 13:35
 */
public class HeartbeatMsgPhone {
    /*
     * @desc 设备id
     */
    private String productId;
    /*
     * @desc 信息类型
     */
    private String type;
    /*
     * @desc reqid
     */
    private String reqid;
    /*
     * @desc 运行时间，时间戳，到秒
     */
    private Long uptime;
    /*
     * @desc 时间戳，毫秒
     */
    private Long ts;
    /*
     * @desc 指令
     */
    private String command;
    /*
     * @desc 网络实体
     */
    private NetworkMsg network;
    /*
     * @desc 两路电话
     */
    private List<TelModuleMsg> fxo;
    /**
     * @desc  定时上报的网络实体信息（有线或4G）
     */
    public class NetworkMsg{
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
    /**
     * @desc  两路电话模块的电流，电压信息
     */
    public class TelModuleMsg{
        /*
         * @desc 电流
         */
        private Integer current;
        /*
         * @desc 电压
         */
        private Integer voltage;

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
    }

    public String getProductId() {
        return this.productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReqid() {
        return reqid;
    }

    public void setReqid(String reqid) {
        this.reqid = reqid;
    }

    public long getUptime() {
        return uptime;
    }

    public Long getTs() {
        return ts;
    }

    public void setTs(Long ts) {
        this.ts = ts;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public void setUptime(Long uptime) {
        this.uptime = uptime;
    }

    public NetworkMsg getNetwork() {
        return network;
    }

    public void setNetwork(NetworkMsg network) {
        this.network = network;
    }

    public List<TelModuleMsg> getFxo() {
        return fxo;
    }

    public void setFxo(List<TelModuleMsg> fxo) {
        this.fxo = fxo;
    }

    public static void main(String[] args) {
        String str = "{\n" +
                "    \"command\":\"/api/v3/phone/heartbeat\",\n" +
                "    \"deviceId\":\"qw\", \n" +
                "    \"reqid\": \"\",\n" +
                "    \"timestamp\":1,\n" +
                "    \"uptime\": 12345,\n" +
                "    \"network\":{\n" +
                "        \"wan\":\"wan\" ,\n" +
                "        \"rssi\": 3,\n" +
                "        \"rat\": \"rat\"\n" +
                "    },\n" +
                "    \"fxo\":[{\n" +
                "        \"current\":0,\n" +
                "        \"voltage\":-47\n" +
                "    },\n" +
                "    {\n" +
                "        \"current\":0,\n" +
                "        \"voltage\":0\n" +
                "    }   \n" +
                "    ]\n" +
                "}\n";
        HeartbeatMsgPhone phone = JSON.parseObject(str,HeartbeatMsgPhone.class);

        NetworkMsg net = phone.getNetwork();
        net.setRat("21323");
        Integer a = null;
        Long ts = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
        System.out.println("ts:"+ts);

    }
}
