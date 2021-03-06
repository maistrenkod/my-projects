package ru.skillbench.tasks.javaapi.reflect;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class ReflectorImpl implements Reflector {

    Class<?> clazz1;

    public void setClass(Class<?> clazz) {
        clazz1 = clazz;
    }

    public Stream<String> getMethodNames(Class<?>... paramTypes) {
        if (clazz1 == null) throw new NullPointerException("NullPointerException");
        Method [] methods = clazz1.getMethods();
        ArrayList<Class<?>> a = new ArrayList<>();
        a.addAll(Arrays.asList(paramTypes));
        ArrayList<String> b = new ArrayList<>();
        for (Method i:methods) {
            if (a.containsAll(Arrays.asList(i.getParameterTypes()))){
                b.add(i.getName());
            }
        }
        Stream<String> str = b.stream();
        return str;
    }

    public Stream<Field> getAllDeclaredFields() {
        if (clazz1 == null) throw new NullPointerException("NullPointerException");
        Field [] fields = clazz1.getDeclaredFields();
        Predicate<Field> notstatic = x-> x.getModifiers() != Modifier.STATIC;
        Stream<Field> fieldStream = Arrays.stream(fields);
        fieldStream.filter(notstatic);
        return fieldStream;
    }

    public Object getFieldValue(Object target, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        return null;
    }

    public Object getMethodResult(Object constructorParam, String methodName, Object... methodParams) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        return  clazz1.getConstructor().newInstance(constructorParam);
    }
}
