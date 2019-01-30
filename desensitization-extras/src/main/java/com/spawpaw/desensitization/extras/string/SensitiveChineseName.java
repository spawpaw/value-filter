package com.spawpaw.desensitization.extras.string;

import com.spawpaw.desensitization.core.annotations.SensitiveInfo;
import com.spawpaw.desensitization.core.annotations.SensitiveString;
import com.spawpaw.desensitization.extras.executors.SensitiveChineseNameDesensitizationExecutor;

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
