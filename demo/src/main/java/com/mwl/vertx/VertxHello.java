package com.mwl.vertx;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerResponse;

/**
 * @author mawenlong
 * @date 2019/04/30
 */
public class VertxHello {
    public static void main(String[] args) {
        Vertx.vertx().createHttpServer().requestHandler(request -> {
            // This handler gets called for each request that arrives on the server
            HttpServerResponse response = request.response();
            response.putHeader("content-type", "text/plain");
            // Write to the response and end it
            response.end("Hello World!");
        }).listen(8080);
    }
}
