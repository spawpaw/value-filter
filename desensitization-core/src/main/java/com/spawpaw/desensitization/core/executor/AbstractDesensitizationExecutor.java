package com.spawpaw.desensitization.core.executor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;
import java.util.Map;

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

    public void processCollections(Field field, Collection collection) {

    }

    /**
     * 进行脱敏
     *
     * @param object        上下文对象。
     * @param field         字段反射信息。可能为空，不推荐从这里获取注解
     * @param annotationMap 从所有与字段相关的地方获取到的该方法所声明的所需要的注解，以及本工具所提供的元注解
     * @param value         要进行脱敏的值
     * @return
     */
    public abstract T desensitize(Object object, Field field, Map<Class<? extends Annotation>, ? extends Annotation> annotationMap, T value);

    /**
     * 所脱敏的对象的必要注解。
     * 将会在脱敏之前从各种与对象相关的地方提取这些注解，如果有任意一个没找到，则抛出异常，不进行脱敏。
     */
    public List<Class<? extends Annotation>> necessaryAnnotations() {
        return null;
    }

    /**
     * 所脱敏的对象可以附加的注解，
     * 将会在脱敏之前从各种与对象相关的地方提取这些注解，如果没有这些注解也不会抛出异常。
     */
    public List<Class<? extends Annotation>> extraAnnotations() {
        return null;
    }

    /**
     * 本脱敏器是否会处理null值
     * 如果返回false，则不会处理null值
     */
    public boolean desensitizeForNullValues() {
        return false;
    }

}
