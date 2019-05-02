package io.soppy;

import com.alibaba.fastjson.JSONObject;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.AsciiString;
import io.netty.util.CharsetUtil;
import io.soppy.handler.JsonRequestHandler;
import io.soppy.router.RouterManager;
import io.soppy.serialize.JSONSerializer;
import io.soppy.serialize.Serializer;

import java.net.http.HttpHeaders;

public class HttpServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    private static final String FAVICON_ICO = "/favicon.ico";
    private static final AsciiString CONTENT_TYPE = AsciiString.cached("Content-Type");
    private static final AsciiString CONTENT_LENGTH = AsciiString.cached("Content-Length");
    private static final AsciiString CONNECTION = AsciiString.cached("Connection");
    private static final AsciiString KEEP_ALIVE = AsciiString.cached("keep-alive");

    private RouterManager routerManager;
    private Serializer serializer = new JSONSerializer();
    public HttpServerHandler(RouterManager routerManager) {
        this.routerManager = routerManager;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
        var handlerDesp = routerManager.getHandler(msg.uri(), msg.method());
        var handler = handlerDesp.getHandler();
        if (handlerDesp == null) {
            sendError(ctx, HttpResponseStatus.NOT_FOUND);
            return;
        }

        var result = handler.handle(msg);
        sendMessage(ctx, JSONObject.toJSONString(result));
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