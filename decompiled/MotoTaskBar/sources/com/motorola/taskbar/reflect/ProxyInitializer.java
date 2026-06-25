package com.motorola.taskbar.reflect;

import android.util.Log;
import com.motorola.taskbar.reflect.android.os.UserManagerR;
import com.motorola.taskbar.reflect.com.android.internal.RR;
import com.motorola.taskbar.util.DebugConfig;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Stream;

/* JADX INFO: loaded from: classes2.dex */
public final class ProxyInitializer {
    private static final boolean DEBUG = DebugConfig.DEBUG_COMMON;
    private static final String IS_INITIALIZED = "IS_INITIALIZED";
    private static final String TAG = "ProxyInitializer";

    private ProxyInitializer() {
    }

    public static boolean initialize() {
        return ((Boolean) Stream.of((Object[]) new Class[]{RR.class, UserManagerR.class}).map(new Function() { // from class: com.motorola.taskbar.reflect.ProxyInitializer$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Boolean.valueOf(ProxyInitializer.isInitialized((Class) obj));
            }
        }).reduce(new BinaryOperator() { // from class: com.motorola.taskbar.reflect.ProxyInitializer$$ExternalSyntheticLambda1
            @Override // java.util.function.BiFunction
            public final Object apply(Object obj, Object obj2) {
                return Boolean.valueOf(Boolean.logicalAnd(((Boolean) obj).booleanValue(), ((Boolean) obj2).booleanValue()));
            }
        }).get()).booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isInitialized(Class cls) {
        boolean z;
        try {
            z = cls.getDeclaredField(IS_INITIALIZED).getBoolean(null);
        } catch (IllegalAccessException | NoSuchFieldException unused) {
            Log.e(TAG, cls.getName() + " must have " + IS_INITIALIZED + " field.");
            z = false;
        }
        if (!z) {
            Log.e(TAG, "Proxy initialization FAILED at: " + cls.getName());
        }
        return z;
    }
}
