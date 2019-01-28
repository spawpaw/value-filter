package com.spawpaw.desensitization.core.executor;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;

/**
 * 抽象脱敏器
 *
 * @param <T> 支持进行脱敏的类型
 */
public abstract class AbstractDesensitizationExecutor<T> {

    /**
     * 检测本脱敏器是否支持指定字段及其值
     * 约定整个运行期仅调用本方法一次，之后使用缓存的信息来判断
     *
     * @param field 字段反射
     * @return 是否可进行脱敏
     */
    public boolean validate(Field field) {
        Class<?> superclass = getClass();
        while (superclass.getSuperclass().getSuperclass().getSuperclass() != null) {
            superclass = superclass.getSuperclass();
        }

        Class<T> entityClass = (Class<T>) ((ParameterizedType) superclass.getGenericSuperclass()).getActualTypeArguments()[0];
        return entityClass.isAssignableFrom(field.getType());
    }

    /**
     * 进行脱敏
     *
     * @param object 上下文对象
     * @param field  字段反射信息
     * @param value  要进行脱敏的值
     * @return 脱敏后的信息
     */
    public abstract T desensitize(Object object, Field field, T value);


}
