package com.spawpaw.desensitization.extras.string;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface SensitiveFixedLengthString {
    boolean[] mask();
}
