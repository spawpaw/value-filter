package com.spawpaw.value.filter.core.test;


import org.assertj.core.api.Condition;
import org.assertj.core.api.WritableAssertionInfo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.annotation.AliasFor;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AnnotationUtilTest {
    @Test
    public void testAnnotationUtil() {
        AnnotationUtils.synthesizeAnnotation(Human.class);
        AnnotationUtils.synthesizeAnnotation(Woman.class);

        Human annotation = AnnotationUtils.findAnnotation(TestWomen.class, Human.class);
        Map<String, Object> annotationAttributes = AnnotationUtils.getAnnotationAttributes(null, annotation, true, true);
        System.out.println(annotation);
        System.out.println(annotationAttributes);
        System.out.println(AnnotationUtils.getValue(annotation, "age"));
        System.out.println(AnnotationUtils.getValue(annotation, "gender"));

    }

    public static void main(String[] args) {
        String valueToTest = "kjl;l";
        try {
            assertThat(valueToTest)
                    .as("不能为空")
                    .isNotEmpty()

                    .as("不能包含非法字符")
                    .matches("[a-zA-Z0-9_]");

        } catch (Error e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testAssertion() {

    }

    @Retention(RetentionPolicy.RUNTIME)
    public @interface Human {
        String gender() default "";

        int age() default -1;
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Human(gender = "female")
    public @interface Woman {
        String gender() default "female";

        int age() default -1;
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Human(gender = "male")
    public @interface Man {
        int age();
    }

    @Man(age = 22)
    public static class TestMan {

    }

    @Woman(age = 18)
    public static class TestWomen {

    }
}

