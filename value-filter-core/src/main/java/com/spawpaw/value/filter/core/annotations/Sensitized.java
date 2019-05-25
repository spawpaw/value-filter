package com.spawpaw.value.filter.core.annotations;

/**
 * 用于递归脱敏
 */
public @interface Sensitized {
    /**
     * 所注释的字段所属的脱敏组
     */
    Class<?>[] groups() default {};

    /**
     * 所注释的字段的内部字段属于哪些脱敏组
     */
    Class<?>[] childGroups() default {};

    /**
     * 使childGroups和groups相同
     */
    boolean useSameGroupsForChild() default true;
}
