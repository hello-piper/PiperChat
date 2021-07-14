/*
 * Copyright 2020 The PiperChat
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
package io.piper.server.web;

import cn.hutool.core.io.IoUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import io.piper.common.exception.IMException;
import io.piper.common.exception.IMResult;

import javax.servlet.*;
import java.io.IOException;

/**
 * ErrorFilter
 *
 * @author piper
 */
public class ErrorFilter implements Filter {
    private static final Logger log = LogManager.getLogger(ErrorFilter.class);

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        try {
            filterChain.doFilter(req, resp);
        } catch (IMException e) {
            e.printStackTrace();
            IoUtil.writeUtf8(resp.getOutputStream(), true, IMResult.error(e));
        } catch (Exception e) {
            e.printStackTrace();
            IoUtil.writeUtf8(resp.getOutputStream(), true, IMResult.error(e.getMessage()));
        }
    }
}
