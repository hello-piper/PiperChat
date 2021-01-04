package piper.im.web_server;

/**
 * 权重和连接数对应关系
 * 权重5 online<30w
 * 权重4 30w<online<40w
 * 权重3 40w<online<46w
 * 权重2 46w<online<50w
 * 权重1 online>50w
 */
public class WeightOnlineConfig {

    /**
     * 根据连接数获取权重
     */
    public static Integer getWeightBYOnline(Integer online) {
        if (online < 300000) {
            return 5;
        }
        if (online < 400000) {
            return 4;
        }
        if (online < 460000) {
            return 3;
        }
        if (online < 500000) {
            return 2;
        }
        return 1;
    }
}
