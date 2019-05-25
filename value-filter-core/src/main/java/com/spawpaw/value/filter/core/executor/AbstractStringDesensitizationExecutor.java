package com.spawpaw.value.filter.core.executor;

import com.spawpaw.value.filter.core.annotations.SensitiveString;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Field;

public abstract class AbstractStringDesensitizationExecutor extends AbstractDesensitizationExecutor<String> {
    protected String getRepeatString(String strToRepeat, int times, boolean aggregate) {
        StringBuilder stringBuilder = new StringBuilder();
        if (aggregate) {
            stringBuilder.append(strToRepeat);
        } else {
            for (int i = 0; i < times; i++) {
                stringBuilder.append(strToRepeat);
            }
        }
        return stringBuilder.toString();
    }

    protected SensitiveString getMetaAnnotation(Field field) {
        SensitiveString sensitiveString = AnnotationUtils.findAnnotation(field, SensitiveString.class);
        if (sensitiveString == null) {
            throw new RuntimeException(String.format(
                    "错误的用法, 应显式或隐式地为字段【%s】添加@SensitiveString注解。"
                    , field.getName()
            ));
        }
        return sensitiveString;
    }
}
