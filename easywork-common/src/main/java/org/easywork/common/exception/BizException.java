package org.easywork.common.exception;

import org.easywork.common.rest.result.ResultCode;
import lombok.Getter;

import java.io.Serial;

/**
 * 业务异常
 *
 * @author: upfive
 * @version: 1.0.0
 * @date: 2025/8/20 16:29
 */
@Getter
public class BizException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -4578923642700250084L;

    protected String code;
    protected String message;

    public BizException() {
        super(ResultCode.SYSTEM_ERROR.getMsg());
        this.code = ResultCode.SYSTEM_ERROR.getCode();
        this.message = ResultCode.SYSTEM_ERROR.getMsg();
    }

    public BizException(ResultCode resultCode) {
        super(resultCode.getMsg());
        this.code = resultCode.getCode();
        this.message = resultCode.getMsg();
    }
}
