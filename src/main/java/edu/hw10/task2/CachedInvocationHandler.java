package edu.hw10.task2;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CachedInvocationHandler implements InvocationHandler {

    private final Object original;
    Boolean isNeedToCache = null;
    Boolean isPersist = null;
    private Map<Object, Object> cachedValues;

    public <T> CachedInvocationHandler(T object) {
        this.original = object;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) {

        if (isNeedToCache == null) {
            Annotation cacheAnno = Arrays.stream(method.getAnnotations())
                .filter(it -> it.annotationType().equals(Cache.class)).findFirst().orElse(null);
            isNeedToCache = cacheAnno != null;
            if (isNeedToCache) {
                isPersist = ((Cache) cacheAnno).persist();
                if (!isPersist) {
                    cachedValues = new HashMap<>();
                } else {
                    try {
                        cachedValues = new DiskMap(Constants.STORAGE_PATH);
                    } catch (IOException e) {
                        log.error("Unable to create disk map by path {}", Constants.STORAGE_PATH);
                        throw new RuntimeException(e);
                    }
                }
            }
        }

        if (isNeedToCache && cachedValues.containsKey(Arrays.hashCode(objects))) {
            return cachedValues.get(Arrays.hashCode(objects));
        }

        Method originalMethod = Arrays.stream(original.getClass().getMethods())
            .filter(it -> it.getName().equals(method.getName()))
            .filter(it -> Arrays.equals(it.getParameterTypes(), method.getParameterTypes()))
            .findFirst().get();
        Object res;
        try {
            res = originalMethod.invoke(original, objects);
        } catch (IllegalAccessException | InvocationTargetException e) {
            log.error(
                "Unable to invoke a method {} with arguments {}",
                originalMethod.getName(),
                Arrays.toString(objects)
            );
            throw new RuntimeException(e);
        }
        if (isNeedToCache) {
            cachedValues.put(Arrays.hashCode(objects), res);
        }
        return res;
    }
}
