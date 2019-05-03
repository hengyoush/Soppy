package io.soppy.handler;

import io.netty.handler.codec.http.FullHttpRequest;
import io.soppy.serialize.JSONSerializer;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

@FunctionalInterface
public interface JsonRequestHandler<T> extends RequestHandler {
    @Override
    @SuppressWarnings("unchecked")
    default Object handle(FullHttpRequest fullHttpRequest) {
        Type type = ((ParameterizedType) this.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0];
        T req = null;
        byte[] bytes = new byte[fullHttpRequest.content().readableBytes()];
        fullHttpRequest.content().getBytes(0, bytes);
        try {
            req = JSONSerializer.getInstance().deserialize((Class<T>) Class.forName(type.getTypeName()), bytes);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return handle(req);
    }

    Object handle(T req);
}
