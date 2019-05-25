package com.spawpaw.value.filter.starter.test.executor;

import com.spawpaw.desensitization.core.executor.AbstractDesensitizationExecutor;
import com.spawpaw.value.filter.starter.test.annotation.MySensitiveType;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Map;

@Component
public class MySensitiveTypeExecutor extends AbstractDesensitizationExecutor<Integer> {
    @Override
    public Integer desensitize(Object object, Field field, Map<Class<? extends Annotation>, ? extends Annotation> annotationMap, Integer value) {
        MySensitiveType mySensitiveTypeAnnotation = (MySensitiveType) annotationMap.get(MySensitiveType.class);
        if (mySensitiveTypeAnnotation == null) {
            //这个异常在本示例中永远不会抛出，因为没有将MySensitiveTypeExecutor使用到其他脱敏注解上
            //
            throw new RuntimeException(String.format(
                    "类[%s]中的字段[%s]使用了MySensitiveTypeExecutor，但没有显式或隐式地为其声明MySensitiveType注解"
                    , object.getClass().getName(), field.getName()
            ));
        }
        return value - (value % mySensitiveTypeAnnotation.round());
    }
}
