package piper.im.common.load_banlance;

/**
 * 权重和连接数对应关系
 * 权重5 online<3W
 * 权重4 3w<online<5w
 * 权重3 5w<online<7w
 * 权重2 7w<online<9w
 * 权重1 online>9w
 */
public class WeightOnlineConfig {

    /**
     * 根据连接数获取权重
     */
    public static int getWeightBYOnline(Integer online) {
        if (online < 30000) {
            return 5;
        }
        if (online < 50000) {
            return 4;
        }
        if (online < 70000) {
            return 3;
        }
        if (online < 90000) {
            return 2;
        }
        return 1;
    }
}
