package io.soppy;

import io.soppy.handler.Method;
import io.soppy.handler.RequestHandler;
import io.soppy.handler.RequestHandlerDescriptor;

import java.util.Optional;
import java.util.Set;

import static io.soppy.handler.Method.Type.GET;
import static io.soppy.handler.Method.Type.POST;

public class Util {

    public static RequestHandlerDescriptor getDecriptorFromHandler(RequestHandler handler) {
        Class<?> clazz = handler.getClass();
        Set<Method.Type> methods = Optional.ofNullable(clazz.getAnnotation(Method.class))
                .map(Method::value)
                .map(Set::of)
                .orElse(Set.of(GET, POST));

        return new RequestHandlerDescriptor() {
            @Override
            public Set<Method.Type> supportMethods() {
                return methods;
            }

            @Override
            public RequestHandler getHandler() {
                return handler;
            }
        };
    }
}
