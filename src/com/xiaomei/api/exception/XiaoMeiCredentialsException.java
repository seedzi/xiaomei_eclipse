package com.xiaomei.api.exception;


import java.io.IOException;

/**
 *
 * 此异常表示没有通过验证的异常 401
 */
public class XiaoMeiCredentialsException extends IOException implements XiaoMeiException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -5047770794055560752L;

	public XiaoMeiCredentialsException() {
    }

    public XiaoMeiCredentialsException(Throwable cause) {
        super(cause);
    }

    public XiaoMeiCredentialsException(String detailMessage) {
        super(detailMessage);
    }

    public XiaoMeiCredentialsException(String message, Throwable cause) {
        super(message, cause);
    }
}
