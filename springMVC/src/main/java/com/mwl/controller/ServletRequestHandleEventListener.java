package com.mwl.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.ServletRequestHandledEvent;

/**
 * @author mawenlong
 * @date 2018/11/28
 */
@Component
public class ServletRequestHandleEventListener implements ApplicationListener<ServletRequestHandledEvent> {
    private final Log logger = LogFactory.getLog(ServletRequestHandleEventListener.class);

    @Override
    public void onApplicationEvent(ServletRequestHandledEvent event) {
        logger.info(event.getDescription());
    }
}
