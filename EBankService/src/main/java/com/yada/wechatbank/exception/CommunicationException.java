package com.yada.wechatbank.exception;


/**
 * 异常处理类
 * 统一返回到ERROR页面
 * @author Tx
 */
public class CommunicationException extends RuntimeException{

	public CommunicationException(String message, Throwable cause) {
		super(message, cause);
	}

	public CommunicationException(String message) {
		super(message);
	}


}
