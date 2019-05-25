package com.spawpaw.value.filter.starter.test.entity;

import com.spawpaw.value.filter.starter.test.annotation.MySensitiveType;
import lombok.Data;

@Data
public class MyEntity {
    //将数字模1000
    @MySensitiveType(round = 1000)
    Integer num;
}
