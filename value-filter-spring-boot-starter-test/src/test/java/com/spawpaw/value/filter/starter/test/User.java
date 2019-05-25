package com.spawpaw.value.filter.starter.test;

import com.spawpaw.desensitization.extras.string.SensitiveChineseIdCard;
import com.spawpaw.desensitization.extras.string.SensitiveChineseName;
import lombok.Data;

import java.util.List;

@Data
public class User {
    @SensitiveChineseName
    private String chineseName;
    @SensitiveChineseIdCard
    private String idCard;
    @SensitiveChineseName
    private List<String> friends;
}
