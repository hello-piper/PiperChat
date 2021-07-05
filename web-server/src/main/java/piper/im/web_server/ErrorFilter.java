package piper.im.web_server;

import cn.hutool.core.io.IoUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import piper.im.common.exception.IMException;
import piper.im.common.exception.IMResult;

import javax.servlet.*;
import java.io.IOException;

public class ErrorFilter implements Filter {
    private static final Logger log = LogManager.getLogger(ErrorFilter.class);

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        try {
            filterChain.doFilter(req, resp);
        } catch (IMException e) {
            log.info("filter catch IMException: {}", e.getMessage());
            IoUtil.writeUtf8(resp.getOutputStream(), true, IMResult.error(e));
        } catch (Exception e) {
            log.info("filter catch Exception: {}", e.getMessage());
            IoUtil.writeUtf8(resp.getOutputStream(), true, IMResult.error(e.getMessage()));
        }
    }
}
