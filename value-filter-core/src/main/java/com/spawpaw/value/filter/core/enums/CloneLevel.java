package com.spawpaw.value.filter.core.enums;

public enum CloneLevel {
    /**
     * 不拷贝
     */
    NO_CLONE,
    /**
     * 浅拷贝
     */
    SHALLOW_CLONE,
    /**
     * 深拷贝
     */
    DEEP_CLONE,
    /**
     * 先使用json序列化，再反序列化
     */
    JSON_CLONE,

}
