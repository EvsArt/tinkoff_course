package edu.hw11;

import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;

@Slf4j
public class Task1 {

    public String getString() {

        DynamicType.Unloaded<Object> unloadedType = new ByteBuddy()
            .subclass(Object.class)
            .method(ElementMatchers.isToString())
            .intercept(FixedValue.value("Hello, ByteBuddy!"))
            .make();

        Class<?> dynamicType = unloadedType.load(getClass().getClassLoader()).getLoaded();
        Object dynamicObject;
        try {
            dynamicObject = dynamicType.getDeclaredConstructors()[0].newInstance();
        } catch (ReflectiveOperationException | ArrayIndexOutOfBoundsException e) {
            log.error("Error with initializing object by dynamic type!");
            throw new RuntimeException(e);
        }
        return dynamicObject.toString();
    }

}
