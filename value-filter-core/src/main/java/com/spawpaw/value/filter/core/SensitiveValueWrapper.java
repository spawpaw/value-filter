package com.spawpaw.value.filter.core;

import java.lang.annotation.Annotation;

public abstract class SensitiveValueWrapper<T> {
    private T desensitizedValue;

    public abstract T value();

    public T getDesensitizedValue() {
        return desensitizedValue;
    }

    public void setDesensitizedValue(T v) {
        this.desensitizedValue = v;
    }

    public <A extends Annotation> A getAnnotation(Class<A> annotationClazz) {
        try {
            System.out.println(getClass());
            return getClass().getMethod("value").getAnnotatedReturnType().getAnnotation(annotationClazz);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }
}
