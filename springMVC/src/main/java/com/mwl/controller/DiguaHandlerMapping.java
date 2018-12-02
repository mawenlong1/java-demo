package com.mwl.controller;

import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;

/**
 * @author mawenlong
 * @date 2018/11/28
 */
public class DiguaHandlerMapping implements HandlerMapping {

    public HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {
        String url = request.getRequestURI();
        String method = request.getMethod();
        if (url.startsWith("/tudou")) {
            if (method.equalsIgnoreCase("GET")) {

            } else if (method.equalsIgnoreCase("POST")) {

            } else {

            }
        }
        return null;
    }
}
