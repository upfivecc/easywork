package org.easywork.console.trigger.http;

import org.easywork.common.exception.BizException;
import org.easywork.common.rest.result.Result;
import org.easywork.common.rest.result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.List;

@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public Result<?> handleException(Exception e) {
        if (e instanceof BizException ex) {
            // token过期是正常情况,不打印
            if(!ex.getCode().equals(ResultCode.INVALID_TOKEN.getCode())){
                log.error("全局异常捕获:msg:{}", ex.getMessage(), e);
            }
            return Result.error(ex.getCode(), ex.getMessage());
        } else if (e instanceof UndeclaredThrowableException) {
            BizException ex = (BizException) e.getCause();
            log.error("全局异常捕获:msg:{}", ex.getMessage(), e);
            return Result.error(ex.getCode(), ex.getMessage());
        } else {
            log.error("全局异常捕获:msg:{}", e.getMessage(), e);
            return Result.error(ResultCode.PARAM_INVALID);
        }
    }

    /**
     * 处理请求参数格式错误 @RequestBody上validate失败后抛出的异常
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.OK)
    public Result<?> handleValidationExceptionHandler(MethodArgumentNotValidException exception) {
        BindingResult bindResult = exception.getBindingResult();
        String msg;
        if (bindResult.hasErrors()) {
            msg = bindResult.getAllErrors().get(0).getDefaultMessage();
            assert msg != null;
            if (msg.contains("NumberFormatException")) {
                msg = "参数类型错误！";
            }
        } else {
            msg = "系统繁忙，请稍后重试...";
        }
        return Result.error(ResultCode.PARAM_INVALID, msg);
    }


    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.OK)
    public Result<?> handleBindException(BindException e) {
        // 抛出异常可能不止一个 输出为一个List集合
        List<ObjectError> errors = e.getAllErrors();
        // 取第一个异常
        ObjectError error = errors.get(0);
        // 获取异常信息
        String errorMsg = error.getDefaultMessage();
        return Result.error(ResultCode.PARAM_INVALID, errorMsg);
    }

}
