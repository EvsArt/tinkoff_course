package edu.hw10.task2;

import java.lang.reflect.Proxy;

public final class CacheProxy {

    private CacheProxy() {
    }

    public static <T> T create(T object) {
        return (T) Proxy.newProxyInstance(
            object.getClass().getClassLoader(),
            object.getClass().getInterfaces(),
            new CachedInvocationHandler(object)
        );
    }

}
