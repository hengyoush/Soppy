package io.soppy.handler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Method {
    Method.Type[] method() default {Type.GET};

    enum Type {
        GET, POST
    }
}
