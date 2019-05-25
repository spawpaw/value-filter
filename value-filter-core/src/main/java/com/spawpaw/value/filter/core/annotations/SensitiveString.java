package com.spawpaw.value.filter.core.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface SensitiveString {
    /**
     * 将敏感字符替换为什么
     */
    String replacement() default "*";

    /**
     * 是否将连续的敏感字符进行聚合，即每个脱敏的字符都替换为一个{@link #replacement()}，还是所有连续的敏感字符替换为一个{@link #replacement()}
     */
    boolean aggregate() default false;

    /**
     * 保留前几个字符（即使字符数量不足也会保留）
     */
    int preservePrefix() default 0;

    int preserveSuffix() default 0;

    /**
     * 过滤敏感字，无论敏感字出现在哪些位置
     * 敏感词包含：
     * - 政治敏感词
     */
    boolean filterSensitiveWords() default false;
}
