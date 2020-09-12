package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 定时续约并上报信息
 *
 * @author piper
 * @date 2020/9/11 13:33
 */
public class RenewTask extends TimerTask {

    private final Logger logger = LogManager.getLogger(RenewTask.class);

    private static final Timer timer = new Timer();

    public RenewTask() {
        timer.scheduleAtFixedRate(this, 1000, 5000);
    }

    @Override
    public void run() {
        logger.info("定时汇报状态给网关机");
    }
}
