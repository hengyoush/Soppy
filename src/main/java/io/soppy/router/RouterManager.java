package io.soppy.router;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.soppy.handler.RequestHandler;

public class RouterManager {

    private Map<String, RequestHandler> routers = new ConcurrentHashMap<>();

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
        routers.putIfAbsent(uri, handler);
        if (routers.get(uri) != handler) {
            throw new IllegalArgumentException("重复注册路由uri: " + uri);
        }
    }

    public RequestHandler getHandler(String uri) {
        return routers.get(uri);
    }
 }