package com.spawpaw.desensitization.extras.string;

import com.spawpaw.desensitization.core.annotations.SensitiveInfo;
import com.spawpaw.desensitization.core.annotations.SensitiveString;
import com.spawpaw.desensitization.extras.executors.BaseStringDesensitizationExecutor;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SensitiveString(preservePrefix = 3, preserveSuffix = 4)
@SensitiveInfo(executor = BaseStringDesensitizationExecutor.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface SensitiveChineseIdCard {
}
