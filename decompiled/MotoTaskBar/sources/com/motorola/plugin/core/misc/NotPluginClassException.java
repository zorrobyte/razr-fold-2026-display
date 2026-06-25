package com.motorola.plugin.core.misc;

import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: NotPluginClassException.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class NotPluginClassException extends RuntimeException {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NotPluginClassException(String str, Throwable th) {
        super(Intrinsics.stringPlus(str, " not a valid plugin class"), th);
        str.getClass();
        th.getClass();
    }
}
