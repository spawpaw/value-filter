package com.spawpaw.value.filter.extras.string;

import com.spawpaw.value.filter.core.annotations.SensitiveInfo;
import com.spawpaw.value.filter.core.annotations.SensitiveString;
import com.spawpaw.value.filter.extras.executors.BaseStringDesensitizationExecutor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@SensitiveString(preservePrefix = 3, preserveSuffix = 4)
@SensitiveInfo(executor = BaseStringDesensitizationExecutor.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({
        ElementType.FIELD/*字段*/
        , ElementType.ANNOTATION_TYPE/*注解*/
        , ElementType.LOCAL_VARIABLE/*方法内变量*/
        , ElementType.TYPE_USE/*泛型声明，如List<@SensitiveInfo() String>*/
})
public @interface SensitiveChineseIdCard {
}
