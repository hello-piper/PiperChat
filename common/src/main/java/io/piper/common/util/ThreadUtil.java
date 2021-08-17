/*
 * Copyright (c) 2020-2030 The Piper(https://github.com/hello-piper)
 *
 * The PiperChat is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 *
 * http://license.coscl.org.cn/MulanPSL2
 *
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND, EITHER EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT, MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 */
package io.piper.common.util;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.concurrent.*;

/**
 * ThreadUtil
 *
 * @author piper
 */
public final class ThreadUtil {
    public static final int cores = Runtime.getRuntime().availableProcessors();
    public static final ScheduledExecutorService SCHEDULE_POOL = new ScheduledThreadPoolExecutor(cores);
    public static final ThreadPoolExecutor THREAD_POOL = new ThreadPoolExecutor(cores, cores * 10,
            0L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(10000), new DefaultThreadFactory());
    public static final ListeningExecutorService LISTENING_POOL = MoreExecutors.listeningDecorator(THREAD_POOL);
}
