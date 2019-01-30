package com.spawpaw.desensitization.core.annotations;


import com.spawpaw.desensitization.core.executor.AbstractDesensitizationExecutor;
import com.spawpaw.desensitization.core.executor.SetNullDesensitizationExecutor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记指定字段包含敏感数据，需要进行脱敏，并指定脱敏器
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({
        ElementType.FIELD/*字段*/
        , ElementType.ANNOTATION_TYPE/*注解*/
        , ElementType.LOCAL_VARIABLE/*方法内变量*/
        , ElementType.TYPE_USE/*泛型声明，如List<@SensitiveInfo() String>*/
})
public @interface SensitiveInfo {
    /**
     * 使用哪种脱敏器进行脱敏
     */
    Class<? extends AbstractDesensitizationExecutor> executor() default SetNullDesensitizationExecutor.class;

}
