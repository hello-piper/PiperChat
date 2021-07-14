package io.piper.common.util;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class ThreadUtil {
    public static final ScheduledExecutorService SCHEDULE_POOL = new ScheduledThreadPoolExecutor(Runtime.getRuntime().availableProcessors());
}
