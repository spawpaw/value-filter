package com.spawpaw.desensitization.core.test;

import org.junit.Test;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

public class GenericTest {
    @Test
    public void test() {
        GenericType dervied = new Dervied();
        TypeVariable<? extends Class<? extends Type>>[] typeParameters = dervied.getClass().getGenericSuperclass().getClass().getTypeParameters();

        for (TypeVariable<? extends Class<? extends Type>> typeParameter : typeParameters) {
            System.out.println(typeParameter);
        }

    }


    static class GenericType<T> {

    }

    static class Dervied extends GenericType<String> {

    }
}
