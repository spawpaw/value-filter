package com.spawpaw.desensitization.core;

import com.spawpaw.desensitization.core.annotations.SensitiveInfo;
import com.spawpaw.desensitization.core.annotations.Sensitized;
import com.spawpaw.desensitization.core.enums.CloneLevel;
import com.spawpaw.desensitization.core.executor.AbstractDesensitizationExecutor;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.core.annotation.AnnotationUtils;

import javax.validation.groups.Default;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.util.*;

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
            case JSON_CLONE:
                newObj = jsonClone(object);
                break;
            default:
                throw new RuntimeException("不支持的拷贝类型，请联系作者");//除非加了clone类型，且这里又没有增加对应的处理，不然不会出现这个异常
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
        //如果对象类被加上了注解
        Map<Class<? extends Annotation>, Annotation> annotationMap = new HashMap<>();
        SensitiveInfo sensitiveInfo = AnnotationUtils.findAnnotation(object.getClass(), SensitiveInfo.class);
        if (sensitiveInfo != null) {//此时只需要从该类上获取注解
            AbstractDesensitizationExecutor executor = beanFactory.getBean(sensitiveInfo.executor());
            findAnnotationsForExecutor(object.getClass(), annotationMap, executor);
            executor.desensitize(object, null, annotationMap, object);
            annotationMap.put(SensitiveInfo.class, sensitiveInfo);
            return;
        }
        //如果对象是SensitiveValueWrapper
        if (object instanceof SensitiveValueWrapper) {
            SensitiveInfo annotation = (SensitiveInfo) ((SensitiveValueWrapper) object).getAnnotation(SensitiveInfo.class);
            if (annotation == null) {
                throw new RuntimeException("没有为wrapper设置注解");
            } else {
                annotationMap.put(SensitiveInfo.class, sensitiveInfo);
            }
        }

        try {
            for (Field declaredField : object.getClass().getDeclaredFields()) {
                declaredField.setAccessible(true);
                Object originalFieldValue = declaredField.get(object);

                //递归脱敏
                Sensitized sensitized = AnnotationUtils.findAnnotation(declaredField, Sensitized.class);
                if (sensitized != null) {
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
                sensitiveInfo = AnnotationUtils.findAnnotation(declaredField, SensitiveInfo.class);
                if (sensitiveInfo != null) {
                    Class<?>[] groupsOnAnnotation = null;
                    //分组检查
                    for (Annotation declaredAnnotation : declaredField.getDeclaredAnnotations()) {
                        if (isAnnotatedWith(declaredAnnotation, SensitiveInfo.class)) {
                            groupsOnAnnotation = (Class<?>[]) AnnotationUtils.getValue(declaredAnnotation, "groups");
                            break;
                        }
                    }
                    if (!hasIntersection(groupsOnAnnotation, groups)) {//如果该字段不处于分组中（交集为空）则跳过该字段
                        continue;
                    }

                    //获取脱敏器
                    AbstractDesensitizationExecutor executor = beanFactory.getBean(sensitiveInfo.executor());
                    if (executor == null) {
                        throw new NullPointerException("cannot find the executor,please check");
                    }

                    //获取相关注解
                    findAnnotationsForExecutor(declaredField, annotationMap, executor);

                    //开始脱敏
                    if (executor.validate(declaredField)) {
                        if (originalFieldValue != null || executor.desensitizeForNullValues()) {
                            Object desensitizedFieldValue = executor.desensitize(object, declaredField, annotationMap, originalFieldValue);
                            declaredField.set(object, desensitizedFieldValue);
                        }
                    } else {
                        throw new RuntimeException("在不支持的字段上添加了脱敏注解");
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void findAnnotationsForExecutor(AnnotatedElement object, Map<Class<? extends Annotation>, Annotation> annotationMap, AbstractDesensitizationExecutor executor) {
        if (executor.necessaryAnnotations() != null) {
            findAnnotationsFromClass(object, annotationMap, executor.necessaryAnnotations(), true);
        }
        if (executor.extraAnnotations() != null) {
            findAnnotationsFromClass(object, annotationMap, executor.necessaryAnnotations(), false);
        }
    }

    private void findAnnotationsFromClass(AnnotatedElement annotatedElement, Map<Class<? extends Annotation>, Annotation> annotationMap, List<Class<Annotation>> extraAnnotations, boolean necessary) {
        for (Object extraAnnotation : extraAnnotations) {
            Annotation annotation = AnnotationUtils.findAnnotation(annotatedElement, (Class<? extends Annotation>) extraAnnotation);
            if (necessary && annotation == null) {
                throw new RuntimeException(String.format("类%s上缺少注解%s", annotatedElement, extraAnnotation));
            }
            annotationMap.put((Class<Annotation>) extraAnnotation, annotation);
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
     * 判断两个分组是否有交集
     */
    private boolean hasIntersection(Class<?>[] groupA, Class<?>[] groupB) {
        return getIntersection(groupA, groupB).length > 0;
    }

    /**
     * 求两个分组的交集，如果某一个组是空组，则为其加上默认的元素Default.class
     */
    private Class<?>[] getIntersection(Class<?>[] groupA, Class<?>[] groupB) {
        if (groupA == null || groupA.length == 0) {
            groupA = new Class<?>[]{Default.class};
        }
        if (groupB == null || groupB.length == 0) {
            groupB = new Class<?>[]{Default.class};
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

    private Object jsonClone(Object v) {
        // TODO: 2019/1/27 jsonClone
        return v;
    }
}
