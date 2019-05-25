package com.spawpaw.value.filter.autoconfigure;

import com.spawpaw.value.filter.core.DesensitizationService;
import com.spawpaw.value.filter.core.DesensitizationServiceConfig;
import com.spawpaw.value.filter.core.executor.SetNullDesensitizationExecutor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(DesensitizationService.class)
@ComponentScan(basePackages = "com.spawpaw.desensitization.extras")
public class DesensitizationServiceAutoConfiguration {
    @Bean
    public SetNullDesensitizationExecutor setNullDesensitizationExecutor() {
        return new SetNullDesensitizationExecutor();
    }

    @Bean
    @ConfigurationProperties(prefix = "desensitization.service")
    public DesensitizationServiceConfig desensitizationConfig() {
        return new DesensitizationServiceConfig();
    }

    @Bean
    @ConditionalOnMissingBean(DesensitizationService.class)
    public DesensitizationService desensitizationService(ApplicationContext applicationContext, DesensitizationServiceConfig desensitizationServiceConfig) {
        return new DesensitizationService(applicationContext, desensitizationServiceConfig);
    }

}
