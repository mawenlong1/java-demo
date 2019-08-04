package com.mwl.io;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;

/**
 * @author mawenlong
 * @date 2018/11/28
 */
public class HttpHandler extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg)
            throws Exception {

        byte[] buffer = new byte[msg.readableBytes()];
        //注意buffer的长度必须和msg.readableBytes()一样，否则报错，这是netty规定的
        msg.readBytes(buffer);
        String message = new String(buffer, Charset.forName("utf-8"));
        System.out.println("服务端接收到的消息：" + message);
        // 响应接受的消息
        ByteBuf responseMessage = Unpooled.copiedBuffer(message + "--", Charset.forName("utf-8"));
        ctx.writeAndFlush(responseMessage);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
