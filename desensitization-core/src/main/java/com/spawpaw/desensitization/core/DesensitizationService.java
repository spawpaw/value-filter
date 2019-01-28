package com.spawpaw.desensitization.core;

import com.spawpaw.desensitization.core.annotations.SensitiveInfo;
import com.spawpaw.desensitization.core.annotations.Sensitized;
import com.spawpaw.desensitization.core.enums.CloneLevel;
import com.spawpaw.desensitization.core.executor.AbstractDesensitizationExecutor;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.core.annotation.AnnotationUtils;

import javax.validation.groups.Default;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class DesensitizationService {
    private final BeanFactory beanFactory;
    private final DesensitizationServiceConfig desensitizationServiceConfig;

    public DesensitizationService(BeanFactory beanFactory, DesensitizationServiceConfig desensitizationServiceConfig) {
        this.beanFactory = beanFactory;
        this.desensitizationServiceConfig = desensitizationServiceConfig;
    }

    public void desensitize(Object object, CloneLevel cloneLevel, Class<?>... groups) {
        Object newObj;
        switch (cloneLevel) {
            case NO_CLONE:
                newObj = object;
                break;
            case DEEP_CLONE:
                newObj = deepClone(object);
                break;
            case SHALLOW_CLONE:
                newObj = shallowClone(object);
                break;
            default:
                throw new RuntimeException("不支持的拷贝类型，请联系作者");//除非加了clone方法，且这里有没有增加对应的处理，不然不会出现这个异常
        }
        desensitize(newObj, groups);
    }

    /**
     * 对指定对象进行脱敏
     *
     * @param object 要进行脱敏的对象
     */
    public void desensitize(Object object, Class<?>... groups) {
        if (object == null) {
            return;
        }
        try {
            for (Field declaredField : object.getClass().getDeclaredFields()) {
                declaredField.setAccessible(true);
                Object originalFieldValue = declaredField.get(object);

                Sensitized sensitized = AnnotationUtils.findAnnotation(declaredField, Sensitized.class);
                if (sensitized != null) {//递归脱敏
                    Class<?>[] intersection = getIntersection(groups, sensitized.groups());
                    if (intersection.length > 0) {
                        if (sensitized.useSameGroupsForChild()) {
                            desensitize(originalFieldValue, intersection);
                        } else {
                            desensitize(originalFieldValue, sensitized.childGroups());
                        }
                    }
                    continue;
                }
                SensitiveInfo sensitiveInfo = AnnotationUtils.findAnnotation(declaredField, SensitiveInfo.class);
                if (sensitiveInfo != null) {
                    Class<?>[] intersection = null;
                    Class<?>[] groupsOnAnnotation = null;
                    //分组
                    for (Annotation declaredAnnotation : declaredField.getDeclaredAnnotations()) {
                        if (isAnnotatedWith(declaredAnnotation, SensitiveInfo.class)) {
                            groupsOnAnnotation = (Class<?>[]) AnnotationUtils.getValue(declaredAnnotation, "groups");
                            break;
                        }
                    }
                    //如果字段上没有分组，则添加默认分组Default.class
                    if (groupsOnAnnotation == null || groupsOnAnnotation.length == 0) {
                        groupsOnAnnotation = new Class[]{Default.class};
                    }
                    if (groups.length == 0) {//如果传入的分组参数也为空，则也为其添加默认分组
                        groups = groupsOnAnnotation;
                    }
                    intersection = getIntersection(groupsOnAnnotation, groups);
                    if (intersection.length == 0) {//如果该字段不处于分组中（交集为空）则跳过该字段
                        continue;
                    }

                    AbstractDesensitizationExecutor executor = beanFactory.getBean(sensitiveInfo.executor());
                    if (executor == null) {
                        throw new NullPointerException("cannot find the executor,please check");
                    }
                    if (executor.validate(declaredField)) {
                        Object desensitizedFieldValue = executor.desensitize(object, declaredField, originalFieldValue);
                        declaredField.set(object, desensitizedFieldValue);
                    } else {
                        throw new RuntimeException("在不支持的字段上添加了脱敏注解");
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private <A extends Annotation> boolean isAnnotatedWith(Annotation annotation, Class<A> annotationType) {
        for (Annotation anno : annotation.getClass().getAnnotations()) {
            if (anno.getClass() == annotationType) {
                return true;
            } else if (isAnnotatedWith(anno, annotationType)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 求两个数组交集
     */
    private Class<?>[] getIntersection(Class<?>[] groupA, Class<?>[] groupB) {
        if (groupA == null || groupB == null || groupA.length == 0 || groupB.length == 0) {
            return new Class<?>[0];
        }
        Set<Class<?>> common = new HashSet<>();
        Set<Class<?>> setB = new HashSet<>(Arrays.asList(groupB));
        for (Class<?> clazz : groupA) {
            if (setB.contains(clazz)) {
                common.add(clazz);
            }
        }
        return common.toArray(new Class<?>[0]);
    }

    private Object shallowClone(Object v) {
        // TODO: 2019/1/27 shallowClone
        return v;
    }

    private Object deepClone(Object v) {
        // TODO: 2019/1/27 deepClone
        return v;
    }
}
