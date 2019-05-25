package com.spawpaw.value.filter.core;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class DesensitizationServiceConfig {
    private Boolean throwsOnFail;
    /**
     *
     */
    Set<String> illegalTypes;
}
