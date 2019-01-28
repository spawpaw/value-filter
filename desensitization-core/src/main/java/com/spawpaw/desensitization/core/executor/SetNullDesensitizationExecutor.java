package com.spawpaw.desensitization.core.executor;

import java.lang.reflect.Field;

/**
 * 置null脱敏器
 */
public class SetNullDesensitizationExecutor extends AbstractDesensitizationExecutor<Object> {

    @Override
    public Object desensitize(Object object, Field field, Object value) {
        return null;
    }
}
