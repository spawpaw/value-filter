package com.spawpaw.desensitization.core;

import java.lang.annotation.Annotation;

public interface SensitiveValueWrapper<T> {

    T value();

    default <A extends Annotation> A getAnnotation(Class<A> annotationClazz) {
        try {
            System.out.println(getClass());
            return getClass().getMethod("value").getAnnotatedReturnType().getAnnotation(annotationClazz);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }
}
