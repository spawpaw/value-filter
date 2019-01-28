package com.spawpaw.desensitization.starter.test;

import com.spawpaw.desensitization.extras.string.SensitiveChineseIdCard;
import com.spawpaw.desensitization.extras.string.SensitiveChineseName;
import lombok.Data;

@Data
public class User {
    @SensitiveChineseName
    private String chineseName;
    @SensitiveChineseIdCard
    private String idCard;
}
