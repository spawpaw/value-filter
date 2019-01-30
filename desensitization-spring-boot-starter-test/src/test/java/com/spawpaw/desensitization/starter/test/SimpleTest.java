package com.spawpaw.desensitization.starter.test;

import com.spawpaw.desensitization.core.SensitiveValueWrapper;
import com.spawpaw.desensitization.extras.string.SensitiveChineseName;
import org.junit.Test;

public class SimpleTest {

    @Test
    public void clazz() {
        System.out.println(String.class.toString());
        System.out.println(Integer.class.toString());
        System.out.println(Byte.class.toString());
        System.out.println(Long.class.toString());
    }

    @Test
    public void test() throws ClassNotFoundException, NoSuchMethodException {
        String name = "川普";
        SensitiveValueWrapper<String> sensitiveValueWrapper = new SensitiveValueWrapper<String>() {
            @SensitiveChineseName
            @Override
            public String value() {
                return name;
            }
        };
        SensitiveChineseName annotation = sensitiveValueWrapper.getAnnotation(SensitiveChineseName.class);
        System.out.println(annotation);
    }
}
