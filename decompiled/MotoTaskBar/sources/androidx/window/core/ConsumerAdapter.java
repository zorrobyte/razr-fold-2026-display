package androidx.window.core;

import android.app.Activity;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;
import kotlin.reflect.KClasses;

/* JADX INFO: compiled from: ConsumerAdapter.kt */
/* JADX INFO: loaded from: classes.dex */
public final class ConsumerAdapter {
    private final ClassLoader loader;

    /* JADX INFO: compiled from: ConsumerAdapter.kt */
    final class ConsumerHandler implements InvocationHandler {
        private final KClass clazz;
        private final Function1 consumer;

        public ConsumerHandler(KClass kClass, Function1 function1) {
            kClass.getClass();
            function1.getClass();
            this.clazz = kClass;
            this.consumer = function1;
        }

        private final boolean isAccept(Method method, Object[] objArr) {
            return Intrinsics.areEqual(method.getName(), "accept") && objArr != null && objArr.length == 1;
        }

        private final boolean isEquals(Method method, Object[] objArr) {
            return Intrinsics.areEqual(method.getName(), "equals") && method.getReturnType().equals(Boolean.TYPE) && objArr != null && objArr.length == 1;
        }

        private final boolean isHashCode(Method method, Object[] objArr) {
            return Intrinsics.areEqual(method.getName(), "hashCode") && method.getReturnType().equals(Integer.TYPE) && objArr == null;
        }

        private final boolean isToString(Method method, Object[] objArr) {
            return Intrinsics.areEqual(method.getName(), "toString") && method.getReturnType().equals(String.class) && objArr == null;
        }

        @Override // java.lang.reflect.InvocationHandler
        public Object invoke(Object obj, Method method, Object[] objArr) {
            obj.getClass();
            method.getClass();
            if (isAccept(method, objArr)) {
                invokeAccept(KClasses.cast(this.clazz, objArr != null ? objArr[0] : null));
                return Unit.INSTANCE;
            }
            if (isEquals(method, objArr)) {
                return Boolean.valueOf(obj == (objArr != null ? objArr[0] : null));
            }
            if (isHashCode(method, objArr)) {
                return Integer.valueOf(this.consumer.hashCode());
            }
            if (isToString(method, objArr)) {
                return this.consumer.toString();
            }
            throw new UnsupportedOperationException("Unexpected method call object:" + obj + ", method: " + method + ", args: " + objArr);
        }

        public final void invokeAccept(Object obj) {
            obj.getClass();
            this.consumer.invoke(obj);
        }
    }

    /* JADX INFO: compiled from: ConsumerAdapter.kt */
    public interface Subscription {
        void dispose();
    }

    public ConsumerAdapter(ClassLoader classLoader) {
        classLoader.getClass();
        this.loader = classLoader;
    }

    private final Object buildConsumer(KClass kClass, Function1 function1) {
        Object objNewProxyInstance = Proxy.newProxyInstance(this.loader, new Class[]{unsafeConsumerClass()}, new ConsumerHandler(kClass, function1));
        objNewProxyInstance.getClass();
        return objNewProxyInstance;
    }

    private final Class unsafeConsumerClass() throws ClassNotFoundException {
        Class<?> clsLoadClass = this.loader.loadClass("java.util.function.Consumer");
        clsLoadClass.getClass();
        return clsLoadClass;
    }

    public final Class consumerClassOrNull$window_release() {
        try {
            return unsafeConsumerClass();
        } catch (ClassNotFoundException unused) {
            return null;
        }
    }

    public final Subscription createSubscription(final Object obj, KClass kClass, String str, String str2, Activity activity, Function1 function1) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        obj.getClass();
        kClass.getClass();
        str.getClass();
        str2.getClass();
        activity.getClass();
        function1.getClass();
        final Object objBuildConsumer = buildConsumer(kClass, function1);
        obj.getClass().getMethod(str, Activity.class, unsafeConsumerClass()).invoke(obj, activity, objBuildConsumer);
        final Method method = obj.getClass().getMethod(str2, unsafeConsumerClass());
        return new Subscription() { // from class: androidx.window.core.ConsumerAdapter.createSubscription.1
            @Override // androidx.window.core.ConsumerAdapter.Subscription
            public void dispose() throws IllegalAccessException, InvocationTargetException {
                method.invoke(obj, objBuildConsumer);
            }
        };
    }
}
