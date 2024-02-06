package edu.hw10.task1;

import edu.hw10.task1.annotation.Max;
import edu.hw10.task1.annotation.Min;
import edu.hw10.task1.annotation.NotNull;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RandomObjectGenerator {

    private final RandomGenerator generator = new RandomGenerator();

    public <T> T nextObject(Class<T> clazz) {

        Constructor<?> constructor = Arrays.stream(clazz.getConstructors())
            .findFirst()
            .orElseThrow(() -> {
                log.error("No one public constructor was found in {}", clazz);
                return new RuntimeException();
            });

        Class<?>[] types = constructor.getParameterTypes();
        Annotation[][] annotations = constructor.getParameterAnnotations();

        Object[] args = getRandomArguments(types, annotations);

        try {
            return (T) constructor.newInstance(args);
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
            log.error("Unable to instance an object of {} with arguments {}", clazz, Arrays.toString(args));
            throw new RuntimeException(e);
        }

    }

    public <T> T nextObject(Class<T> clazz, String fabricMethodName) {
        Method method = Arrays.stream(clazz.getMethods())
            .filter(it -> it.getName().equals(fabricMethodName))
            .filter(it -> it.getReturnType().equals(clazz))
            .findFirst()
            .orElseThrow(() -> {
                log.error("No one fabric method with this name was found in {}", clazz);
                return new RuntimeException();
            });

        Class<?>[] types = method.getParameterTypes();
        Annotation[][] annotations = method.getParameterAnnotations();

        Object[] args = getRandomArguments(types, annotations);

        try {
            return (T) method.invoke(null, args);
        } catch (InvocationTargetException | IllegalAccessException e) {
            log.error(
                "Unable to instance an object of {} by method {} with arguments {}",
                clazz,
                method.getName(),
                Arrays.toString(args)
            );
            throw new RuntimeException(e);
        }
    }

    private Object[] getRandomArguments(Class<?>[] types, Annotation[][] annotations) {

        Object[] args = new Object[types.length];

        for (int i = 0; i < args.length; i++) {
            String min = null;
            String max = null;
            boolean isNotNull = false;
            Annotation[] paramAnnotations = annotations[i];
            for (Annotation anno : paramAnnotations) {
                if (anno.annotationType().equals(NotNull.class)) {
                    isNotNull = true;
                } else if (anno.annotationType().equals(Min.class)) {
                    min = ((Min) anno).value();
                } else if (anno.annotationType().equals(Max.class)) {
                    max = ((Max) anno).value();
                }
            }

            if (isNotNull && !generator.getSupportedTypes().containsKey(types[i])) {
                args[i] = nextObject(types[i]);
            } else {
                args[i] = generator.getRandomValueOf(types[i], min, max);
            }
        }

        return args;

    }

}
