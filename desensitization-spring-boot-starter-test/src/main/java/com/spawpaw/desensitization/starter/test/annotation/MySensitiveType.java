package com.spawpaw.desensitization.starter.test.annotation;

import com.spawpaw.desensitization.core.annotations.SensitiveInfo;
import com.spawpaw.desensitization.starter.test.executor.MySensitiveTypeExecutor;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@SensitiveInfo(executor = MySensitiveTypeExecutor.class)
public @interface MySensitiveType {
    /**
     * 将所注解的数字模这个数
     */
    int round() default 0;

    /**
     * 分组。
     * 辅助字段，帮助根据情景进行脱敏，可以加这个字段，也可以不加
     */
    Class<?>[] groups() default {};
}
