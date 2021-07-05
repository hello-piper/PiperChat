package piper.im.web_server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;

public class ErrorFilter implements Filter {
    private static final Logger log = LogManager.getLogger(ErrorFilter.class);

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        try {
            filterChain.doFilter(req, resp);
        } catch (Exception e) {
            log.info("filter catch exception: {}", e.getMessage());
            PrintWriter writer = resp.getWriter();
            writer.print(e.getMessage());
            writer.close();
        }
    }
}
