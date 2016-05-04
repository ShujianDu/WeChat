package com.yada.wechatbank.exception;

import com.yada.wechatbank.kafka.MessageProducer;
import com.yada.wechatbank.kafka.TopicEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 异常处理类
 * Created by Tx on 2016/5/3.
 */
public class ExceptionHandler implements HandlerExceptionResolver {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MessageProducer messageProducer;

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        //可以自由处理各种异常逻辑
        if (e instanceof com.yada.wechatbank.exception.CommunicationException) {
            logger.error(e.getMessage(),e);
        }
        messageProducer.send(TopicEnum.EXCEPTION,"httpClientCommunication",e.getMessage());
        return null;
    }
}
