package com.spawpaw.desensitization.starter.test;

import com.spawpaw.desensitization.core.DesensitizationService;
import com.spawpaw.desensitization.core.DesensitizationServiceConfig;
import com.spawpaw.desensitization.core.enums.CloneLevel;
import com.spawpaw.desensitization.extras.executors.BaseStringDesensitizationExecutor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class DesensitizationStarterTestApplicationTests {
    @Autowired
    DesensitizationService desensitizationService;

    @Autowired
    BaseStringDesensitizationExecutor baseStringDesensitizationExecutor;

    @Autowired
    DesensitizationServiceConfig desensitizationServiceConfig;

    @Test
    public void contextLoads() {
        Assert.assertNotNull("should not be null!", desensitizationService);
        Assert.assertNotNull("should not be null!", baseStringDesensitizationExecutor);
        Assert.assertNotNull("should not be null", desensitizationServiceConfig);
        log.info("all components has been created");

    }


    @Test
    public void functionTest() {
        User user = new User();
        user.setChineseName("马云");
        user.setIdCard("320322199001011236");
        desensitizationService.desensitize(user, CloneLevel.NO_CLONE);
        System.out.println(user);
    }


}

