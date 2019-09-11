package com.mwl.controller;

import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;

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
                System.out.println("get 方法");
            } else if (method.equalsIgnoreCase("POST")) {
                System.out.println("post 方法");
            } else {
                //TODO
            }
        }
        return null;
    }
}
