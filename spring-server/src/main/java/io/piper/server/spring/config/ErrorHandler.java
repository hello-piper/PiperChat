package io.piper.server.spring.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import io.piper.common.exception.IMException;
import io.piper.common.exception.IMResult;

@Order(200)
@ControllerAdvice
public class ErrorHandler {
    private static final Logger log = LogManager.getLogger(ErrorHandler.class);

    @ResponseBody
    @ExceptionHandler(IMException.class)
    @ResponseStatus(HttpStatus.OK)
    public IMResult<Void> imException(IMException e) {
        log.error("业务异常！", e);
        return IMResult.error(e);
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public IMResult<Void> exception(Exception e) {
        log.error("服务器未知异常！", e);
        return IMResult.error(e.getMessage());
    }
}
