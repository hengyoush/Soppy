package io.soppy.test;

import io.soppy.handler.JsonRequestHandler;
import io.soppy.handler.Method;

@Method(Method.Type.POST)
public class PostJsonHandler implements JsonRequestHandler<User> {

    @Override
    public Object handle(User req) {
        return "Hello " + req.name;
    }
}
