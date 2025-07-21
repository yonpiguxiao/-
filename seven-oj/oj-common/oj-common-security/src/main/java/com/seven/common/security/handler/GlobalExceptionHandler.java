package com.seven.common.security.handler;
import com.seven.common.core.domain.R;
import com.seven.common.core.enums.ResultCode;
import com.seven.common.security.exception.ServiceException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
/**
 * 全局异常处理器
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler
{
    /**
     * 请求⽅式不⽀持
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public R<?>
    handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e, HttpServletRequest request)
    {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',不⽀持'{}'请求", requestURI, e.getMethod());
        return R.fail(ResultCode.ERROR);
    }
    /**
     * 拦截运⾏时异常
     */
    @ExceptionHandler(ServiceException.class)
    public R<?> handleServiceException(ServiceException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        ResultCode resultCode = e.getResultCode();
        log.error("请求地址'{}',发⽣业务异常:{}", requestURI, e.getResultCode().getMsg());
        return R.fail(resultCode);
    }
    /**
     * 拦截运⾏时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public R<?> handleRuntimeException(RuntimeException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发⽣运行时异常.", requestURI, e);
        return R.fail(ResultCode.ERROR);
    }
    /**
     * 系统异常
     */
    @ExceptionHandler(Exception.class)
    public R<?> handleException(Exception e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发⽣异常.", requestURI, e);
        return R.fail(ResultCode.ERROR);
    }
}