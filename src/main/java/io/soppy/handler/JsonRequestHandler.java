package io.soppy.handler;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import io.netty.handler.codec.http.FullHttpRequest;

@FunctionalInterface
public interface JsonRequestHandler<T> extends RequestHandler {
    @Override
    default Object handle(FullHttpRequest fullHttpRequest) {
        T req = JSONObject.parseObject(
                fullHttpRequest.content().toString(),
                new TypeReference<>() {});
        return handle(req);
    }

    Object handle(T req);
}
