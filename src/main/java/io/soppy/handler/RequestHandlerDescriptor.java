package io.soppy.handler;

import java.util.Set;

public interface RequestHandlerDescriptor {

    Set<Method.Type> supportMethods();
    RequestHandler getHandler();
    Class<?> getReqClass();
}
