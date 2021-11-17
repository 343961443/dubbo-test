package com.consumer.config;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.huya.skyeye.common.util.ExceptionUtil;
import com.huya.skyeye.common.util.trace.Trace;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.KeyValue;
import org.apache.commons.collections4.keyvalue.DefaultKeyValue;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * @Created by gordon
 * @Date 2021-03-03 10:59
 */
@Slf4j
@Component
public class SpringEventHelper implements ApplicationContextAware, ApplicationListener<ApplicationEvent> {

    private ApplicationContext applicationContext;



    public <T extends ApplicationEvent> void publishEvent(T event) {
        applicationContext.publishEvent(event);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public <T> T getBean(String var1, Class<T> var2) {
        return applicationContext.getBean(var1, var2);
    }

    public <T> T getBean(Class<T> var2) {
        return applicationContext.getBean(var2);
    }

    public Object getBean(String beanName) {
        return applicationContext.getBean(beanName);
    }


    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {

    }
}
