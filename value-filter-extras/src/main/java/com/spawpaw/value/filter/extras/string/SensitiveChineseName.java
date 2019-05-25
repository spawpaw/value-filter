package com.spawpaw.value.filter.extras.string;

import com.spawpaw.value.filter.core.annotations.SensitiveInfo;
import com.spawpaw.value.filter.core.annotations.SensitiveString;
import com.spawpaw.value.filter.extras.executors.SensitiveChineseNameDesensitizationExecutor;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@SensitiveInfo(executor = SensitiveChineseNameDesensitizationExecutor.class)
@SensitiveString()
@Target({
        ElementType.FIELD/*字段*/
        , ElementType.ANNOTATION_TYPE/*注解*/
        , ElementType.LOCAL_VARIABLE/*方法内变量*/
        , ElementType.TYPE_USE/*泛型声明，如List<@SensitiveInfo() String>*/
})
@Inherited
public @interface SensitiveChineseName {
}
