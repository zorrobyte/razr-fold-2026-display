package androidx.window.layout;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import androidx.window.SafeWindowExtensionsProvider;
import androidx.window.core.ConsumerAdapter;
import androidx.window.core.ExtensionsUtil;
import androidx.window.extensions.WindowExtensionsProvider;
import androidx.window.extensions.core.util.function.Consumer;
import androidx.window.extensions.layout.WindowLayoutComponent;
import androidx.window.reflection.ReflectionUtils;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;

/* JADX INFO: compiled from: SafeWindowLayoutComponentProvider.kt */
/* JADX INFO: loaded from: classes.dex */
public final class SafeWindowLayoutComponentProvider {
    private final ConsumerAdapter consumerAdapter;
    private final ClassLoader loader;
    private final SafeWindowExtensionsProvider safeWindowExtensionsProvider;

    public SafeWindowLayoutComponentProvider(ClassLoader classLoader, ConsumerAdapter consumerAdapter) {
        classLoader.getClass();
        consumerAdapter.getClass();
        this.loader = classLoader;
        this.consumerAdapter = consumerAdapter;
        this.safeWindowExtensionsProvider = new SafeWindowExtensionsProvider(classLoader);
    }

    private final boolean canUseWindowLayoutComponent() {
        int safeVendorApiLevel;
        if (isWindowLayoutComponentAccessible$window_release() && (safeVendorApiLevel = ExtensionsUtil.INSTANCE.getSafeVendorApiLevel()) >= 1) {
            return safeVendorApiLevel == 1 ? hasValidVendorApiLevel1$window_release() : safeVendorApiLevel < 5 ? hasValidVendorApiLevel2$window_release() : hasValidVendorApiLevel6$window_release();
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Class getDisplayFoldFeatureClass() throws ClassNotFoundException {
        Class<?> clsLoadClass = this.loader.loadClass("androidx.window.extensions.layout.DisplayFoldFeature");
        clsLoadClass.getClass();
        return clsLoadClass;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Class getFoldingFeatureClass() throws ClassNotFoundException {
        Class<?> clsLoadClass = this.loader.loadClass("androidx.window.extensions.layout.FoldingFeature");
        clsLoadClass.getClass();
        return clsLoadClass;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Class getSupportedWindowFeaturesClass() throws ClassNotFoundException {
        Class<?> clsLoadClass = this.loader.loadClass("androidx.window.extensions.layout.SupportedWindowFeatures");
        clsLoadClass.getClass();
        return clsLoadClass;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Class getWindowLayoutComponentClass() throws ClassNotFoundException {
        Class<?> clsLoadClass = this.loader.loadClass("androidx.window.extensions.layout.WindowLayoutComponent");
        clsLoadClass.getClass();
        return clsLoadClass;
    }

    private final boolean isDisplayFoldFeatureValid() {
        return ReflectionUtils.validateReflection$window_release("DisplayFoldFeature is not valid", new Function0() { // from class: androidx.window.layout.SafeWindowLayoutComponentProvider.isDisplayFoldFeatureValid.1
            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Boolean mo2224invoke() throws NoSuchMethodException, ClassNotFoundException {
                Class displayFoldFeatureClass = SafeWindowLayoutComponentProvider.this.getDisplayFoldFeatureClass();
                boolean z = false;
                Class[] clsArr = new Class[0];
                Method method = displayFoldFeatureClass.getMethod("getType", null);
                Class<?> cls = Integer.TYPE;
                Method method2 = displayFoldFeatureClass.getMethod("hasProperty", cls);
                Method method3 = displayFoldFeatureClass.getMethod("hasProperties", int[].class);
                ReflectionUtils reflectionUtils = ReflectionUtils.INSTANCE;
                method.getClass();
                if (reflectionUtils.isPublic$window_release(method) && reflectionUtils.doesReturn$window_release(method, cls)) {
                    method2.getClass();
                    if (reflectionUtils.isPublic$window_release(method2)) {
                        Class cls2 = Boolean.TYPE;
                        if (reflectionUtils.doesReturn$window_release(method2, cls2)) {
                            method3.getClass();
                            if (reflectionUtils.isPublic$window_release(method3) && reflectionUtils.doesReturn$window_release(method3, cls2)) {
                                z = true;
                            }
                        }
                    }
                }
                return Boolean.valueOf(z);
            }
        });
    }

    private final boolean isFoldingFeatureValid() {
        return ReflectionUtils.validateReflection$window_release("FoldingFeature class is not valid", new Function0() { // from class: androidx.window.layout.SafeWindowLayoutComponentProvider.isFoldingFeatureValid.1
            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Boolean mo2224invoke() throws NoSuchMethodException, ClassNotFoundException {
                Class foldingFeatureClass = SafeWindowLayoutComponentProvider.this.getFoldingFeatureClass();
                boolean z = false;
                Class[] clsArr = new Class[0];
                Method method = foldingFeatureClass.getMethod("getBounds", null);
                Class[] clsArr2 = new Class[0];
                Method method2 = foldingFeatureClass.getMethod("getType", null);
                Class[] clsArr3 = new Class[0];
                Method method3 = foldingFeatureClass.getMethod("getState", null);
                ReflectionUtils reflectionUtils = ReflectionUtils.INSTANCE;
                method.getClass();
                if (reflectionUtils.doesReturn$window_release(method, Reflection.getOrCreateKotlinClass(Rect.class)) && reflectionUtils.isPublic$window_release(method)) {
                    method2.getClass();
                    Class cls = Integer.TYPE;
                    if (reflectionUtils.doesReturn$window_release(method2, Reflection.getOrCreateKotlinClass(cls)) && reflectionUtils.isPublic$window_release(method2)) {
                        method3.getClass();
                        if (reflectionUtils.doesReturn$window_release(method3, Reflection.getOrCreateKotlinClass(cls)) && reflectionUtils.isPublic$window_release(method3)) {
                            z = true;
                        }
                    }
                }
                return Boolean.valueOf(z);
            }
        });
    }

    private final boolean isGetSupportedWindowFeaturesValid() {
        return ReflectionUtils.validateReflection$window_release("WindowLayoutComponent#getSupportedWindowFeatures is not valid", new Function0() { // from class: androidx.window.layout.SafeWindowLayoutComponentProvider.isGetSupportedWindowFeaturesValid.1
            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Boolean mo2224invoke() throws NoSuchMethodException {
                boolean z = false;
                Class[] clsArr = new Class[0];
                Method method = SafeWindowLayoutComponentProvider.this.getWindowLayoutComponentClass().getMethod("getSupportedWindowFeatures", null);
                ReflectionUtils reflectionUtils = ReflectionUtils.INSTANCE;
                method.getClass();
                if (reflectionUtils.isPublic$window_release(method) && reflectionUtils.doesReturn$window_release(method, SafeWindowLayoutComponentProvider.this.getSupportedWindowFeaturesClass())) {
                    z = true;
                }
                return Boolean.valueOf(z);
            }
        });
    }

    private final boolean isMethodWindowLayoutInfoListenerJavaConsumerValid() {
        return ReflectionUtils.validateReflection$window_release("WindowLayoutComponent#addWindowLayoutInfoListener(" + Activity.class.getName() + ", java.util.function.Consumer) is not valid", new Function0() { // from class: androidx.window.layout.SafeWindowLayoutComponentProvider.isMethodWindowLayoutInfoListenerJavaConsumerValid.1
            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Boolean mo2224invoke() throws NoSuchMethodException, ClassNotFoundException {
                Class<?> clsConsumerClassOrNull$window_release = SafeWindowLayoutComponentProvider.this.consumerAdapter.consumerClassOrNull$window_release();
                if (clsConsumerClassOrNull$window_release == null) {
                    return Boolean.FALSE;
                }
                Class windowLayoutComponentClass = SafeWindowLayoutComponentProvider.this.getWindowLayoutComponentClass();
                boolean z = false;
                Method method = windowLayoutComponentClass.getMethod("addWindowLayoutInfoListener", Activity.class, clsConsumerClassOrNull$window_release);
                Method method2 = windowLayoutComponentClass.getMethod("removeWindowLayoutInfoListener", clsConsumerClassOrNull$window_release);
                ReflectionUtils reflectionUtils = ReflectionUtils.INSTANCE;
                method.getClass();
                if (reflectionUtils.isPublic$window_release(method)) {
                    method2.getClass();
                    if (reflectionUtils.isPublic$window_release(method2)) {
                        z = true;
                    }
                }
                return Boolean.valueOf(z);
            }
        });
    }

    private final boolean isMethodWindowLayoutInfoListenerWindowConsumerValid() {
        return ReflectionUtils.validateReflection$window_release("WindowLayoutComponent#addWindowLayoutInfoListener(" + Context.class.getName() + ", androidx.window.extensions.core.util.function.Consumer) is not valid", new Function0() { // from class: androidx.window.layout.SafeWindowLayoutComponentProvider.isMethodWindowLayoutInfoListenerWindowConsumerValid.1
            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Boolean mo2224invoke() throws NoSuchMethodException, ClassNotFoundException {
                Class windowLayoutComponentClass = SafeWindowLayoutComponentProvider.this.getWindowLayoutComponentClass();
                boolean z = false;
                Method method = windowLayoutComponentClass.getMethod("addWindowLayoutInfoListener", Context.class, Consumer.class);
                Method method2 = windowLayoutComponentClass.getMethod("removeWindowLayoutInfoListener", Consumer.class);
                ReflectionUtils reflectionUtils = ReflectionUtils.INSTANCE;
                method.getClass();
                if (reflectionUtils.isPublic$window_release(method)) {
                    method2.getClass();
                    if (reflectionUtils.isPublic$window_release(method2)) {
                        z = true;
                    }
                }
                return Boolean.valueOf(z);
            }
        });
    }

    private final boolean isSupportedWindowFeaturesValid() {
        return ReflectionUtils.validateReflection$window_release("SupportedWindowFeatures is not valid", new Function0() { // from class: androidx.window.layout.SafeWindowLayoutComponentProvider.isSupportedWindowFeaturesValid.1
            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Boolean mo2224invoke() throws NoSuchMethodException {
                boolean z = false;
                Class[] clsArr = new Class[0];
                Method method = SafeWindowLayoutComponentProvider.this.getSupportedWindowFeaturesClass().getMethod("getDisplayFoldFeatures", null);
                Type genericReturnType = method.getGenericReturnType();
                genericReturnType.getClass();
                Type type = ((ParameterizedType) genericReturnType).getActualTypeArguments()[0];
                type.getClass();
                Class cls = (Class) type;
                ReflectionUtils reflectionUtils = ReflectionUtils.INSTANCE;
                if (reflectionUtils.isPublic$window_release(method) && reflectionUtils.doesReturn$window_release(method, List.class) && Intrinsics.areEqual(cls, SafeWindowLayoutComponentProvider.this.getDisplayFoldFeatureClass())) {
                    z = true;
                }
                return Boolean.valueOf(z);
            }
        });
    }

    private final boolean isWindowLayoutProviderValid() {
        return ReflectionUtils.validateReflection$window_release("WindowExtensions#getWindowLayoutComponent is not valid", new Function0() { // from class: androidx.window.layout.SafeWindowLayoutComponentProvider.isWindowLayoutProviderValid.1
            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Boolean mo2224invoke() throws NoSuchMethodException, ClassNotFoundException {
                boolean z = false;
                Class[] clsArr = new Class[0];
                Method method = SafeWindowLayoutComponentProvider.this.safeWindowExtensionsProvider.getWindowExtensionsClass$window_release().getMethod("getWindowLayoutComponent", null);
                Class windowLayoutComponentClass = SafeWindowLayoutComponentProvider.this.getWindowLayoutComponentClass();
                ReflectionUtils reflectionUtils = ReflectionUtils.INSTANCE;
                method.getClass();
                if (reflectionUtils.isPublic$window_release(method) && reflectionUtils.doesReturn$window_release(method, windowLayoutComponentClass)) {
                    z = true;
                }
                return Boolean.valueOf(z);
            }
        });
    }

    public final WindowLayoutComponent getWindowLayoutComponent() {
        if (!canUseWindowLayoutComponent()) {
            return null;
        }
        try {
            return WindowExtensionsProvider.getWindowExtensions().getWindowLayoutComponent();
        } catch (UnsupportedOperationException unused) {
            return null;
        }
    }

    public final boolean hasValidVendorApiLevel1$window_release() {
        return isMethodWindowLayoutInfoListenerJavaConsumerValid();
    }

    public final boolean hasValidVendorApiLevel2$window_release() {
        return hasValidVendorApiLevel1$window_release() && isMethodWindowLayoutInfoListenerWindowConsumerValid();
    }

    public final boolean hasValidVendorApiLevel6$window_release() {
        return hasValidVendorApiLevel2$window_release() && isDisplayFoldFeatureValid() && isSupportedWindowFeaturesValid() && isGetSupportedWindowFeaturesValid();
    }

    public final boolean isWindowLayoutComponentAccessible$window_release() {
        return this.safeWindowExtensionsProvider.isWindowExtensionsValid$window_release() && isWindowLayoutProviderValid() && isFoldingFeatureValid();
    }
}
