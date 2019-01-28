package com.spawpaw.desensitization.extras.string;

import com.spawpaw.desensitization.core.annotations.SensitiveInfo;
import com.spawpaw.desensitization.core.annotations.SensitiveString;
import com.spawpaw.desensitization.extras.executors.SensitiveChineseNameDesensitizationExecutor;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@SensitiveInfo(executor = SensitiveChineseNameDesensitizationExecutor.class)
@SensitiveString()
public @interface SensitiveChineseName {
}
