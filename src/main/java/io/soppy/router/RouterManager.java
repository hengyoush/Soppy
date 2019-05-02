package io.soppy.router;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.soppy.HandlerNotFoundException;
import io.soppy.Util;
import io.soppy.handler.Method;
import io.soppy.handler.RequestHandler;
import io.soppy.handler.RequestHandlerDescriptor;

public class RouterManager {

    private Map<String, RequestHandlerDescriptor> routers = new ConcurrentHashMap<>();

    private RouterManager () {}

    public static RouterManager getInstance() {
        return RouterManager.RouterManagerHolder.instance;
    }

    private static class RouterManagerHolder {
        private static RouterManager instance = new RouterManager();
    }

    public void register(String uri, RequestHandler handler) {
        if (routers.containsKey(uri)) {
            throw new IllegalArgumentException("已存在路由uri: " + uri);
        }
        var descriptor = Util.getDecriptorFromHandler(handler);
        routers.putIfAbsent(uri, descriptor);
        if (routers.get(uri).getHandler() != handler) {
            throw new IllegalArgumentException("重复注册路由uri: " + uri);
        }
    }

    public RequestHandlerDescriptor getHandler(String uri, HttpMethod method) {
        Objects.requireNonNull(uri);
        Objects.requireNonNull(method);

        var handler =
                Optional.ofNullable(routers.get(uri))
                .orElseThrow(() -> new HandlerNotFoundException(uri));
        Method.Type methodLocal = Method.Type.valueOf(method.name());
        if (!handler.supportMethods().contains(methodLocal)) {
            throw new HandlerNotFoundException(uri);
        }
        return handler;
    }
 }