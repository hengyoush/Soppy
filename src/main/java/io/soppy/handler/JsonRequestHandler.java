package io.soppy.handler;

import com.alibaba.fastjson.JSONObject;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.CharsetUtil;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

@FunctionalInterface
public interface JsonRequestHandler<T> extends RequestHandler {
    @Override
    default Object handle(FullHttpRequest fullHttpRequest) {
        String jsonStr = fullHttpRequest.content().toString(CharsetUtil.UTF_8);
        Type type = ((ParameterizedType) this.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0];
        T req = JSONObject.parseObject(
                jsonStr,
                type);
        return handle(req);
    }

    Object handle(T req);
}
