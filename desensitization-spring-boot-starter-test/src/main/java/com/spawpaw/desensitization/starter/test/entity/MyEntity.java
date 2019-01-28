package com.spawpaw.desensitization.starter.test.entity;

import com.spawpaw.desensitization.starter.test.annotation.MySensitiveType;
import lombok.Data;

@Data
public class MyEntity {
    //将数字模1000
    @MySensitiveType(round = 1000)
    Integer num;
}
