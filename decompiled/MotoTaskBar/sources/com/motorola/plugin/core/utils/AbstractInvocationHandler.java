package com.motorola.plugin.core.utils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: AbstractInvocationHandler.kt */
/* JADX INFO: loaded from: classes2.dex */
public abstract class AbstractInvocationHandler implements InvocationHandler {
    public static final Companion Companion = new Companion(null);
    private static final Object[] NO_ARGS = new Object[0];

    /* JADX INFO: compiled from: AbstractInvocationHandler.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final boolean isProxyOfSameInterfaces(Object obj, Class cls) {
            if (cls.isInstance(obj)) {
                return true;
            }
            return Proxy.isProxyClass(obj.getClass()) && Arrays.equals(obj.getClass().getInterfaces(), cls.getInterfaces());
        }
    }

    protected abstract Object handleInvocation(Object obj, Method method, Object[] objArr) throws Throwable;

    @Override // java.lang.reflect.InvocationHandler
    public Object invoke(Object obj, Method method, Object[] objArr) throws Throwable {
        obj.getClass();
        method.getClass();
        if (objArr == null) {
            objArr = NO_ARGS;
        }
        boolean z = true;
        boolean z2 = objArr.length == 0;
        String name = method.getName();
        if (z2 && Intrinsics.areEqual(name, "hashCode")) {
            return Integer.valueOf(hashCode());
        }
        if (objArr.length != 1 || !Intrinsics.areEqual(name, "equals") || method.getParameterTypes()[0] != Object.class) {
            return (z2 && Intrinsics.areEqual(name, "toString")) ? toString() : handleInvocation(obj, method, objArr);
        }
        Object obj2 = objArr[0];
        if (obj2 == null || (obj != obj2 && (!Companion.isProxyOfSameInterfaces(obj2, obj.getClass()) || !Intrinsics.areEqual(this, Proxy.getInvocationHandler(obj2))))) {
            z = false;
        }
        return Boolean.valueOf(z);
    }
}
