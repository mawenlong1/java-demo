package com.mwl.webflux.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * @author mawenlong
 * @date 2018/12/19
 */
@Component
public class FirstWebFilter implements WebFilter {

    private volatile int count=0;
    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
        serverWebExchange.getAttributes().put("User", "jerry");
        count++;
        System.out.println("=====拦截器====="+count);
        return webFilterChain.filter(serverWebExchange);
    }
}