package com.spawpaw.desensitization.starter.test;


import com.spawpaw.desensitization.core.DesensitizationService;
import com.spawpaw.desensitization.starter.test.entity.MyEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class MySensitiveTypeTest {
    @Autowired
    DesensitizationService desensitizationService;

    @Test
    public void test() {
        MyEntity myEntity = new MyEntity();
        myEntity.setNum(123456);
        desensitizationService.desensitize(myEntity);
        System.out.println(myEntity);
    }
}
