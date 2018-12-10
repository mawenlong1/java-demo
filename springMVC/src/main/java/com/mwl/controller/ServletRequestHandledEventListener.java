package com.mwl.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.ServletRequestHandledEvent;

/**
 * @author mawenlong
 * @date 2018/12/09
 */
@Component
public class ServletRequestHandledEventListener implements ApplicationListener<ServletRequestHandledEvent> {
    final static Log log =  LogFactory.getLog(ServletRequestHandledEventListener.class);
    public void onApplicationEvent(ServletRequestHandledEvent event) {
        log.info("======发布事件：======"+event.getDescription());
    }
}
