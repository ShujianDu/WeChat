package com.yada.wechatbank.exception;

import com.yada.wechatbank.kafka.MessageProducer;
import com.yada.wechatbank.kafka.TopicEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 异常处理类
 * Created by Tx on 2016/5/3.
 */
public class ExceptionHandler extends SimpleMappingExceptionResolver {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MessageProducer messageProducer;

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        logger.error(e.getMessage(),e);
        messageProducer.send(TopicEnum.EXCEPTION, "httpClientCommunication", e.getMessage());
        return super.resolveException(httpServletRequest, httpServletResponse,o,e);
    }
}
