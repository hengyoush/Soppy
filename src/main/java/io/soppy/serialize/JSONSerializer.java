package io.soppy.serialize;

import com.alibaba.fastjson.JSONObject;

public class JSONSerializer implements Serializer {
    private static final Serializer instance = new JSONSerializer();

    public static Serializer getInstance() {
        return instance;
    }

    @Override
    public byte[] serialize(Object obj) {
        return JSONObject.toJSONBytes(obj);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return JSONObject.parseObject(bytes, clazz);
    }
}
