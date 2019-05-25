package com.spawpaw.value.filter.core.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.math.RoundingMode;

/**
 * 数字类型的敏感数据
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface SensitiveNumber {

    /**
     * 取整方式，默认不取整
     */
    RoundingMode roundingMode() default RoundingMode.UNNECESSARY;
}
