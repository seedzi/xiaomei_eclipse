package com.xiaomei.yanyu.api.exception;

/**
 * json解析异常类
 */
public class XiaoMeiJSONException extends Exception implements XiaoMeiException {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public XiaoMeiJSONException(Throwable cause) {
    	super(cause);
    }

    public XiaoMeiJSONException(String detailMessage) {
        super(detailMessage);
    }

    public XiaoMeiJSONException(String message, Throwable cause) {
        super(message, cause);
    }
}
