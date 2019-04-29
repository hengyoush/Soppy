package io.soppy;

import com.alibaba.fastjson.JSONObject;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;
import io.soppy.router.RouterManager;

public class HttpServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
        var handler = RouterManager.getInstance().getHandler(msg.uri());
        if (handler == null) {
            sendError(ctx, HttpResponseStatus.NOT_FOUND);
            return;
        }
        var obj = handler.handle(msg);
        sendMessage(ctx, JSONObject.toJSONString(obj));
    }

    private void sendError(ChannelHandlerContext ctx, HttpResponseStatus status) {
        var resp = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status);
        resp.headers().set("Content-Type", "text/plain");
        ctx.writeAndFlush(resp).addListener(ChannelFutureListener.CLOSE);
    }

    private void sendMessage(ChannelHandlerContext ctx, String msg) {
        var resp = 
            new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, 
                                        HttpResponseStatus.OK, 
                                        Unpooled.copiedBuffer(msg, CharsetUtil.UTF_8));
        resp.headers().set("Content-Type", "text/plain");
        ctx.writeAndFlush(resp).addListener(ChannelFutureListener.CLOSE);
    }

}