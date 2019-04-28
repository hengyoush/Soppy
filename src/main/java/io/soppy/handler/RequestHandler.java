package io.soppy.handler;

import io.netty.handler.codec.http.FullHttpRequest;

@FunctionalInterface
public interface RequestHandler {
    Object handle(FullHttpRequest fullHttpRequest);
}