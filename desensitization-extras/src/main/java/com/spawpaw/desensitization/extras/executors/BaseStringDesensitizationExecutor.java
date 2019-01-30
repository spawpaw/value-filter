package com.spawpaw.desensitization.extras.executors;

import com.spawpaw.desensitization.core.annotations.SensitiveString;
import com.spawpaw.desensitization.core.executor.AbstractStringDesensitizationExecutor;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Map;

@Component
public class BaseStringDesensitizationExecutor extends AbstractStringDesensitizationExecutor {
    @Override
    public String desensitize(Object object, Field field, Map<Class<? extends Annotation>, ? extends Annotation> annotationMap, String value) {
        if (value == null)
            return null;
        SensitiveString sensitiveString = getMetaAnnotation(field);
        if (value.length() >= sensitiveString.preservePrefix() + sensitiveString.preserveSuffix()) {
            return value.substring(0, sensitiveString.preservePrefix() - 1)
                    + getRepeatString(sensitiveString.replacement(), value.length() - sensitiveString.preservePrefix() - sensitiveString.preserveSuffix(), sensitiveString.aggregate())
                    + value.substring(value.length() - sensitiveString.preserveSuffix(), value.length() - 1);
        } else {
            return value;
        }
    }
}
