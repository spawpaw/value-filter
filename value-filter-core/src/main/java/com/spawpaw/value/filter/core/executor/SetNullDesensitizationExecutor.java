package com.spawpaw.value.filter.core.executor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Map;

/**
 * 置null脱敏器
 */
public class SetNullDesensitizationExecutor extends AbstractDesensitizationExecutor<Object> {
    @Override
    public Object desensitize(Object object, Field field, Map<Class<? extends Annotation>, ? extends Annotation> annotationMap, Object value) {
        return null;
    }
}
