package com.flexone.catchwiseserver.cache;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
public class CoordKeyGenerator implements KeyGenerator {

    public static final String KEY_GENERATOR_NAME = "coordKeyGenerator";

    @Override
    public Object generate(Object target, Method method, Object... params) {
        return (int) params[0] + ":" + (int) params[1];
    }
}
