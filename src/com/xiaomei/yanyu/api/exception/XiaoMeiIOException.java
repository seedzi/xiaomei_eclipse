package com.xiaomei.yanyu.api.exception;

import java.io.IOException;

/**
 * IOException IO操作异常类
 */
public class XiaoMeiIOException extends IOException implements XiaoMeiException {

	private static final long serialVersionUID = 1L;

	public XiaoMeiIOException() {
    }

    public XiaoMeiIOException(Throwable cause) {
        super(cause);
    }

    public XiaoMeiIOException(String detailMessage) {
        super(detailMessage);
    }

    public XiaoMeiIOException(String message, Throwable cause) {
        super(message, cause);
    }
}
