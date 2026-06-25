package com.android.systemui.plugins.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/* JADX INFO: compiled from: ProtectedInterface.kt */
/* JADX INFO: loaded from: classes.dex */
@Target({ElementType.TYPE})
@Repeatable(Container.class)
@Retention(RetentionPolicy.CLASS)
public @interface GeneratedImport {

    /* JADX INFO: compiled from: ProtectedInterface.kt */
    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.CLASS)
    public @interface Container {
        GeneratedImport[] value();
    }

    String extraImport();
}
