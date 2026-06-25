package com.motorola.plugin.sdk.annotations;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* JADX INFO: loaded from: classes2.dex */
@Repeatable(Requirements.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface Requires {
    Class target();

    int version();
}
