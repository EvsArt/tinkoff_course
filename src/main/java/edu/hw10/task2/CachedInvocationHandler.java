package edu.hw10.task2;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CachedInvocationHandler implements InvocationHandler {

    private final Object original;
    private final Map<Object, Object> cachedValues = new HashMap<>();

    public <T> CachedInvocationHandler(T object) {
        this.original = object;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) {

        Annotation cacheAnno = Arrays.stream(method.getAnnotations())
            .filter(it -> it.annotationType().equals(Cache.class)).findFirst().orElse(null);

        if (Objects.nonNull(cacheAnno) && cachedValues.containsKey(Arrays.hashCode(objects))) {
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
        if (Objects.nonNull(cacheAnno)) {
            cachedValues.put(Arrays.hashCode(objects), res);
        }
        return res;
    }
}
