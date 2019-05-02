package io.soppy.serialize;

public interface Serializer {

    byte[] serialize(Object obj);

    <T> T deserialize(Class<T> clazz, byte[] bytes);
}
