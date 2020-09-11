package org.example;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 定时续约并上报信息
 *
 * @author piper
 * @date 2020/9/11 13:33
 */
public class RenewTask extends TimerTask {

    private static final Timer timer = new Timer();

    public RenewTask() {
        timer.scheduleAtFixedRate(this, 3000, 10000);
    }

    @Override
    public void run() {
        System.out.println("定时汇报状态给网关机");
    }
}
