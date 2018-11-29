package com.mwl.io;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author mawenlong
 * @date 2018/11/28
 */
public class NettyHttpServer {
    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
//                 .option(ChannelOption.SO_BACKLOG, 1024)
                 .channel(NioServerSocketChannel.class)
                 .childHandler(new ChannelInitializer<SocketChannel>() {
                     @Override
                     protected void initChannel(SocketChannel ch) throws Exception {
//                         ch.pipeline().addLast("http-decoder", new HttpRequestDecoder());
//                         ch.pipeline().addLast("http-aggregator",new HttpObjectAggregator(65535));//将多个消息转化成一个
//                         ch.pipeline().addLast("http-encoder", new HttpResponseEncoder());
//                         ch.pipeline().addLast("http-chunked", new ChunkedWriteHandler());//解决大码流的问题
                         ch.pipeline().addLast("http-server", new HttpHandler());
                     }
                 });
        ChannelFuture future = bootstrap.bind(8888);
        try {
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
}
