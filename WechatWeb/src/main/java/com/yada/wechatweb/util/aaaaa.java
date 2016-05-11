package com.yada.wechatweb.util;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;


/**
 * Created by Tx on 2016/5/10.
 */
public class aaaaa extends PropertyPlaceholderConfigurer {

    @Override
    protected String convertProperty(String propertyName, String propertyValue) {
        System.out.println("-------" + propertyName + "---------------------------"+propertyValue);
        return super.convertProperty(propertyName, propertyValue);
    }


}
