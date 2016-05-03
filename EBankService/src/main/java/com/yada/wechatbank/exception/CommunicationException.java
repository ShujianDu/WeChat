package com.yada.wechatbank.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 异常处理类
 * 统一返回到ERROR页面
 * @author Tx
 */
public class CommunicationException extends RuntimeException{


	private final Logger logger = LoggerFactory.getLogger(this.getClass());


	public CommunicationException(String message, Throwable cause ,Boolean sendMail) {
		super(message, cause);
		if(sendMail){
			//TODO 发送邮件报警
		}
		logger.error(message,cause);
	}

	public CommunicationException(String message, Throwable cause) {
		super(message, cause);
		logger.error(message, cause);
	}

	public CommunicationException(String message) {
		super(message);
		logger.error(message);
	}

	public CommunicationException(String message,Boolean sendMail) {
		super(message);
		logger.error(message);
	}

}
