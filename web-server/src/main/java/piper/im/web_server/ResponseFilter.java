package piper.im.web_server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import java.io.IOException;

public class ResponseFilter implements Filter {
    private static final Logger log = LogManager.getLogger(ResponseFilter.class);

    @Override
    public void init(FilterConfig filterConfig) {
        log.info("filter name {}", filterConfig.getFilterName());
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        filterChain.doFilter(req, resp);
        log.info("filter response");
    }
}
