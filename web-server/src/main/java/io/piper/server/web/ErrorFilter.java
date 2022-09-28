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
package io.piper.server.web;

import cn.hutool.core.io.IoUtil;
import io.piper.common.exception.IMException;
import io.piper.common.exception.IMResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * ErrorFilter
 *
 * @author piper
 */
public class ErrorFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(ErrorFilter.class);

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException {
        try {
            filterChain.doFilter(req, resp);
        } catch (IMException e) {
            log.error("业务异常", e);
            IoUtil.writeUtf8(resp.getOutputStream(), true, IMResult.error(e));
        } catch (Exception e) {
            log.error("服务器错误", e);
            IoUtil.writeUtf8(resp.getOutputStream(), true, IMResult.error(e.getMessage()));
        }
    }
}
