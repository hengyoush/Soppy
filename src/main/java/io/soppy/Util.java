package io.soppy;

import io.soppy.handler.JsonRequestHandler;
import io.soppy.handler.Method;
import io.soppy.handler.RequestHandler;
import io.soppy.handler.RequestHandlerDescriptor;

import java.lang.reflect.ParameterizedType;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static io.soppy.handler.Method.Type.GET;
import static io.soppy.handler.Method.Type.POST;

public class Util {

    public static RequestHandlerDescriptor getDecriptorFromHandler(RequestHandler handler) {
        Class<?> clazz = handler.getClass();
        Set<Method.Type> methods = Optional.ofNullable(clazz.getAnnotation(Method.class))
                .map(Method::value)
                .map(Set::of)
                .orElse(Set.of(GET, POST));
        final Class<?> paramType;
        if (handler instanceof JsonRequestHandler) {
            paramType =
                    Stream.of(handler.getClass().getGenericInterfaces())
                    .filter(type -> type instanceof ParameterizedType
                            && type.getTypeName().contains("JsonRequestHandler"))
                    .map(ParameterizedType.class::cast)
                    .map(ParameterizedType::getActualTypeArguments)
                    .map(arr -> (Class<?>)arr[0]).findFirst().get();
        } else
            paramType = null;

        return new RequestHandlerDescriptor() {
            @Override
            public Set<Method.Type> supportMethods() {
                return methods;
            }

            @Override
            public RequestHandler getHandler() {
                return handler;
            }

            @Override
            public Class<?> getReqClass() {
                return paramType;
            }
        };
    }

}
