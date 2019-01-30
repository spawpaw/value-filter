package com.spawpaw.desensitization.starter.test;

import com.spawpaw.desensitization.core.DesensitizationService;
import com.spawpaw.desensitization.core.DesensitizationServiceConfig;
import com.spawpaw.desensitization.core.SensitiveValueWrapper;
import com.spawpaw.desensitization.core.enums.CloneLevel;
import com.spawpaw.desensitization.extras.executors.BaseStringDesensitizationExecutor;
import com.spawpaw.desensitization.extras.string.SensitiveChineseName;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

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


    /**
     * 测试直接加在字段上的注解
     */
    @Test
    public void testField() {
        User user = new User();
        user.setChineseName("李云龙");
        user.setIdCard("320322199001011236");
        desensitizationService.desensitize(user, CloneLevel.NO_CLONE);
        Assert.assertEquals("李*龙", user.getChineseName());
        Assert.assertEquals("320***********1236", user.getIdCard());
        log.info("{}", user);
    }

    /**
     * 测试加在泛型类型上的注解
     */
    @Test
    public void testFieldGeneric() {
        User user = new User();
        user.setFriends(Arrays.asList(
                "刘备",
                "诸葛亮",
                "张飞",
                "关羽"
        ));
        desensitizationService.desensitize(user);

        for (String friend : user.getFriends()) {
            Assert.assertTrue(friend.contains("*"));
        }
        log.info("{}", user);
    }

    /**
     * 测试加在泛型类型上的注解
     */
    @Test
    public void testWrapper() {
        String name = "张飞";
        SensitiveValueWrapper<String> sensitiveValueWrapper = new SensitiveValueWrapper<String>() {
            @SensitiveChineseName
            @Override
            public String value() {
                return name;
            }
        };


        desensitizationService.desensitize(sensitiveValueWrapper);
        log.info("{}", sensitiveValueWrapper.getDesensitizedValue());
    }


}

