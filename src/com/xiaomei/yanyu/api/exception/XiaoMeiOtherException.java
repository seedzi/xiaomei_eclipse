package com.xiaomei.yanyu.api.exception;


/**
 * 应用程序异常
 *
 */
public class XiaoMeiOtherException extends Exception implements XiaoMeiException {

	private static final long serialVersionUID = 1L;

    public XiaoMeiOtherException(String message) {
        super(message);
    }

	public XiaoMeiOtherException() {
		super();
	}
	public XiaoMeiOtherException(Throwable throwable) {
		super(throwable);
	}
    
}
