package androidx.window.embedding;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.IBinder;
import android.view.WindowMetrics;
import androidx.window.SafeWindowExtensionsProvider;
import androidx.window.WindowSdkExtensions;
import androidx.window.core.ConsumerAdapter;
import androidx.window.extensions.WindowExtensions;
import androidx.window.extensions.core.util.function.Consumer;
import androidx.window.extensions.core.util.function.Predicate;
import androidx.window.extensions.embedding.ActivityEmbeddingComponent;
import androidx.window.extensions.embedding.ActivityRule;
import androidx.window.extensions.embedding.ActivityStack;
import androidx.window.extensions.embedding.AnimationBackground;
import androidx.window.extensions.embedding.AnimationParams;
import androidx.window.extensions.embedding.DividerAttributes;
import androidx.window.extensions.embedding.EmbeddedActivityWindowInfo;
import androidx.window.extensions.embedding.SplitAttributes;
import androidx.window.extensions.embedding.SplitAttributesCalculatorParams;
import androidx.window.extensions.embedding.SplitInfo;
import androidx.window.extensions.embedding.SplitPairRule;
import androidx.window.extensions.embedding.SplitPinRule;
import androidx.window.extensions.embedding.SplitPlaceholderRule;
import androidx.window.extensions.embedding.SplitRule;
import androidx.window.extensions.embedding.WindowAttributes;
import androidx.window.extensions.layout.WindowLayoutInfo;
import androidx.window.reflection.ReflectionUtils;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.concurrent.Executor;
import kotlin.jvm.functions.Function0;

/* JADX INFO: compiled from: SafeActivityEmbeddingComponentProvider.kt */
/* JADX INFO: loaded from: classes.dex */
public final class SafeActivityEmbeddingComponentProvider {
    private final ConsumerAdapter consumerAdapter;
    private final ClassLoader loader;
    private final SafeWindowExtensionsProvider safeWindowExtensionsProvider;
    private final WindowExtensions windowExtensions;

    public SafeActivityEmbeddingComponentProvider(ClassLoader classLoader, ConsumerAdapter consumerAdapter, WindowExtensions windowExtensions) {
        classLoader.getClass();
        consumerAdapter.getClass();
        windowExtensions.getClass();
        this.loader = classLoader;
        this.consumerAdapter = consumerAdapter;
        this.windowExtensions = windowExtensions;
        this.safeWindowExtensionsProvider = new SafeWindowExtensionsProvider(classLoader);
    }

    private final boolean canUseActivityEmbeddingComponent() {
        if (!isActivityEmbeddingComponentAccessible$window_release()) {
            return false;
        }
        int extensionVersion = WindowSdkExtensions.Companion.getInstance().getExtensionVersion();
        if (extensionVersion == 1) {
            return hasValidVendorApiLevel1$window_release();
        }
        if (extensionVersion == 2) {
            return hasValidVendorApiLevel2$window_release();
        }
        if (3 <= extensionVersion && extensionVersion < 5) {
            return hasValidVendorApiLevel3$window_release();
        }
        if (extensionVersion == 5) {
            return hasValidVendorApiLevel5$window_release();
        }
        if (extensionVersion == 6) {
            return hasValidVendorApiLevel6$window_release();
        }
        if (extensionVersion == 7) {
            return hasValidVendorApiLevel7$window_release();
        }
        if (8 > extensionVersion || extensionVersion > Integer.MAX_VALUE) {
            return false;
        }
        return hasValidVendorApiLevel8$window_release();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Class getActivityEmbeddingComponentClass() throws ClassNotFoundException {
        Class<?> clsLoadClass = this.loader.loadClass("androidx.window.extensions.embedding.ActivityEmbeddingComponent");
        clsLoadClass.getClass();
        return clsLoadClass;
    }

    private final boolean isActivityEmbeddingComponentValid() {
        return ReflectionUtils.validateReflection$window_release("WindowExtensions#getActivityEmbeddingComponent is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider.isActivityEmbeddingComponentValid.1
            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() throws NoSuchMethodException, ClassNotFoundException {
                boolean z = false;
                Class[] clsArr = new Class[0];
                Method method = SafeActivityEmbeddingComponentProvider.this.safeWindowExtensionsProvider.getWindowExtensionsClass$window_release().getMethod("getActivityEmbeddingComponent", null);
                Class activityEmbeddingComponentClass = SafeActivityEmbeddingComponentProvider.this.getActivityEmbeddingComponentClass();
                ReflectionUtils reflectionUtils = ReflectionUtils.INSTANCE;
                method.getClass();
                if (reflectionUtils.isPublic$window_release(method) && reflectionUtils.doesReturn$window_release(method, activityEmbeddingComponentClass)) {
                    z = true;
                }
                return Boolean.valueOf(z);
            }
        });
    }

    private final boolean isActivityStackGetActivityStackTokenValid() {
        return ReflectionUtils.validateReflection$window_release("ActivityStack#getActivityToken is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider.isActivityStackGetActivityStackTokenValid.1
            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() throws NoSuchMethodException {
                boolean z = false;
                Class[] clsArr = new Class[0];
                Method method = androidx.window.extensions.embedding.ActivityStack.class.getMethod("getActivityStackToken", null);
                ReflectionUtils reflectionUtils = ReflectionUtils.INSTANCE;
                method.getClass();
                if (reflectionUtils.isPublic$window_release(method) && reflectionUtils.doesReturn$window_release(method, ActivityStack.Token.class)) {
                    z = true;
                }
                return Boolean.valueOf(z);
            }
        });
    }

    private final boolean isClassActivityRuleBuilderLevel1Valid() {
        return ReflectionUtils.validateReflection$window_release("Class ActivityRule.Builder is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider.isClassActivityRuleBuilderLevel1Valid.1
            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() throws NoSuchMethodException {
                Method method = ActivityRule.Builder.class.getMethod("setShouldAlwaysExpand", Boolean.TYPE);
                ReflectionUtils reflectionUtils = ReflectionUtils.INSTANCE;
                method.getClass();
                return Boolean.valueOf(reflectionUtils.isPublic$window_release(method) && reflectionUtils.doesReturn$window_release(method, ActivityRule.Builder.class));
            }
        });
    }

    private final boolean isClassActivityRuleBuilderLevel2Valid() {
        return ReflectionUtils.validateReflection$window_release("Class ActivityRule.Builder is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider.isClassActivityRuleBuilderLevel2Valid.1
            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() throws NoSuchMethodException {
                boolean z = false;
                Constructor declaredConstructor = ActivityRule.Builder.class.getDeclaredConstructor(Predicate.class, Predicate.class);
                Method method = ActivityRule.Builder.class.getMethod("setTag", String.class);
                ReflectionUtils reflectionUtils = ReflectionUtils.INSTANCE;
                declaredConstructor.getClass();
                if (reflectionUtils.isPublic$window_release(declaredConstructor)) {
                    method.getClass();
                    if (reflectionUtils.isPublic$window_release(method) && reflectionUtils.doesReturn$window_release(method, ActivityRule.Builder.class)) {
                        z = true;
                    }
                }
                return Boolean.valueOf(z);
            }
        });
    }

    private final boolean isClassActivityRuleValid() {
        return ReflectionUtils.validateReflection$window_release("Class ActivityRule is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider.isClassActivityRuleValid.1
            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() throws NoSuchMethodException {
                boolean z = false;
                Class[] clsArr = new Class[0];
                Method method = ActivityRule.class.getMethod("shouldAlwaysExpand", null);
                ReflectionUtils reflectionUtils = ReflectionUtils.INSTANCE;
                method.getClass();
                if (reflectionUtils.isPublic$window_release(method) && reflectionUtils.doesReturn$window_release(method, Boolean.TYPE)) {
                    z = true;
                }
                return Boolean.valueOf(z);
            }
        });
    }

    private final boolean isClassActivityStackTokenValid() {
        return ReflectionUtils.validateReflection$window_release("Class ActivityStack.Token is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider.isClassActivityStackTokenValid.1
            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() throws NoSuchFieldException, NoSuchMethodException {
                boolean z = false;
                Class[] clsArr = new Class[0];
                Method method = ActivityStack.Token.class.getMethod("toBundle", null);
                Method method2 = ActivityStack.Token.class.getMethod("readFromBundle", Bundle.class);
                Method method3 = ActivityStack.Token.class.getMethod("createFromBinder", IBinder.class);
                Field declaredField = ActivityStack.Token.class.getDeclaredField("INVALID_ACTIVITY_STACK_TOKEN");
                ReflectionUtils reflectionUtils = ReflectionUtils.INSTANCE;
                method.getClass();
                if (reflectionUtils.isPublic$window_release(method) && reflectionUtils.doesReturn$window_release(method, Bundle.class)) {
                    method2.getClass();
                    if (reflectionUtils.isPublic$window_release(method2) && reflectionUtils.doesReturn$window_release(method2, ActivityStack.Token.class)) {
                        method3.getClass();
                        if (reflectionUtils.isPublic$window_release(method3) && reflectionUtils.doesReturn$window_release(method3, ActivityStack.Token.class)) {
                            declaredField.getClass();
                            if (reflectionUtils.isPublic$window_release(declaredField)) {
                                z = true;
                            }
                        }
                    }
                }
                return Boolean.valueOf(z);
            }
        });
    }

    private final boolean isClassAnimationBackgroundValid() {
        return ReflectionUtils.validateReflection$window_release("Class AnimationBackground is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider.isClassAnimationBackgroundValid.1
            /* JADX WARN: Removed duplicated region for block: B:21:0x0083  */
            @Override // kotlin.jvm.functions.Function0
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct add '--show-bad-code' argument
            */
            public final java.lang.Boolean invoke() throws java.lang.NoSuchFieldException, java.lang.NoSuchMethodException {
                /*
                    r11 = this;
                    java.lang.Class<androidx.window.extensions.embedding.AnimationBackground> r11 = androidx.window.extensions.embedding.AnimationBackground.class
                    java.lang.Class<androidx.window.extensions.embedding.AnimationBackground$ColorBackground> r0 = androidx.window.extensions.embedding.AnimationBackground.ColorBackground.class
                    r1 = 1
                    java.lang.Class[] r2 = new java.lang.Class[r1]
                    java.lang.Class r3 = java.lang.Integer.TYPE
                    r4 = 0
                    r2[r4] = r3
                    java.lang.String r5 = "createColorBackground"
                    java.lang.reflect.Method r2 = r11.getMethod(r5, r2)
                    java.lang.String r5 = "ANIMATION_BACKGROUND_DEFAULT"
                    java.lang.reflect.Field r5 = r11.getDeclaredField(r5)
                    java.lang.Class[] r6 = new java.lang.Class[r4]
                    java.lang.String r6 = "getColor"
                    r7 = 0
                    java.lang.reflect.Method r6 = r0.getMethod(r6, r7)
                    java.lang.Class<androidx.window.extensions.embedding.SplitAttributes> r8 = androidx.window.extensions.embedding.SplitAttributes.class
                    java.lang.Class[] r9 = new java.lang.Class[r4]
                    java.lang.String r9 = "getAnimationBackground"
                    java.lang.reflect.Method r7 = r8.getMethod(r9, r7)
                    java.lang.Class<androidx.window.extensions.embedding.SplitAttributes$Builder> r8 = androidx.window.extensions.embedding.SplitAttributes.Builder.class
                    java.lang.Class[] r9 = new java.lang.Class[r1]
                    java.lang.Class<androidx.window.extensions.embedding.AnimationBackground> r10 = androidx.window.extensions.embedding.AnimationBackground.class
                    r9[r4] = r10
                    java.lang.String r10 = "setAnimationBackground"
                    java.lang.reflect.Method r8 = r8.getMethod(r10, r9)
                    androidx.window.reflection.ReflectionUtils r9 = androidx.window.reflection.ReflectionUtils.INSTANCE
                    r2.getClass()
                    boolean r10 = r9.isPublic$window_release(r2)
                    if (r10 == 0) goto L83
                    boolean r0 = r9.doesReturn$window_release(r2, r0)
                    if (r0 == 0) goto L83
                    r5.getClass()
                    boolean r0 = r9.isPublic$window_release(r5)
                    if (r0 == 0) goto L83
                    r6.getClass()
                    boolean r0 = r9.isPublic$window_release(r6)
                    if (r0 == 0) goto L83
                    boolean r0 = r9.doesReturn$window_release(r6, r3)
                    if (r0 == 0) goto L83
                    r7.getClass()
                    boolean r0 = r9.isPublic$window_release(r7)
                    if (r0 == 0) goto L83
                    boolean r11 = r9.doesReturn$window_release(r7, r11)
                    if (r11 == 0) goto L83
                    r8.getClass()
                    boolean r11 = r9.isPublic$window_release(r8)
                    if (r11 == 0) goto L83
                    java.lang.Class<androidx.window.extensions.embedding.SplitAttributes$Builder> r11 = androidx.window.extensions.embedding.SplitAttributes.Builder.class
                    boolean r11 = r9.doesReturn$window_release(r8, r11)
                    if (r11 == 0) goto L83
                    goto L84
                L83:
                    r1 = r4
                L84:
                    java.lang.Boolean r11 = java.lang.Boolean.valueOf(r1)
                    return r11
                */
                throw new UnsupportedOperationException("Method not decompiled: androidx.window.embedding.SafeActivityEmbeddingComponentProvider.C00721.invoke():java.lang.Boolean");
            }
        });
    }

    private final boolean isClassAnimationParamsBuilderValid() {
        return ReflectionUtils.validateReflection$window_release("Class AnimationParams.Builder is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider.isClassAnimationParamsBuilderValid.1
            /* JADX WARN: Removed duplicated region for block: B:19:0x0077  */
            @Override // kotlin.jvm.functions.Function0
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct add '--show-bad-code' argument
            */
            public final java.lang.Boolean invoke() throws java.lang.NoSuchMethodException {
                /*
                    r7 = this;
                    java.lang.Class<androidx.window.extensions.embedding.AnimationParams$Builder> r7 = androidx.window.extensions.embedding.AnimationParams.Builder.class
                    r0 = 1
                    java.lang.Class[] r1 = new java.lang.Class[r0]
                    java.lang.Class<androidx.window.extensions.embedding.AnimationBackground> r2 = androidx.window.extensions.embedding.AnimationBackground.class
                    r3 = 0
                    r1[r3] = r2
                    java.lang.String r2 = "setAnimationBackground"
                    java.lang.reflect.Method r1 = r7.getMethod(r2, r1)
                    java.lang.Class[] r2 = new java.lang.Class[r0]
                    java.lang.Class r4 = java.lang.Integer.TYPE
                    r2[r3] = r4
                    java.lang.String r5 = "setOpenAnimationResId"
                    java.lang.reflect.Method r2 = r7.getMethod(r5, r2)
                    java.lang.Class[] r5 = new java.lang.Class[r0]
                    r5[r3] = r4
                    java.lang.String r6 = "setCloseAnimationResId"
                    java.lang.reflect.Method r5 = r7.getMethod(r6, r5)
                    java.lang.Class[] r6 = new java.lang.Class[r0]
                    r6[r3] = r4
                    java.lang.String r4 = "setChangeAnimationResId"
                    java.lang.reflect.Method r7 = r7.getMethod(r4, r6)
                    androidx.window.reflection.ReflectionUtils r4 = androidx.window.reflection.ReflectionUtils.INSTANCE
                    r1.getClass()
                    boolean r6 = r4.isPublic$window_release(r1)
                    if (r6 == 0) goto L77
                    java.lang.Class<androidx.window.extensions.embedding.AnimationParams$Builder> r6 = androidx.window.extensions.embedding.AnimationParams.Builder.class
                    boolean r1 = r4.doesReturn$window_release(r1, r6)
                    if (r1 == 0) goto L77
                    r2.getClass()
                    boolean r1 = r4.isPublic$window_release(r2)
                    if (r1 == 0) goto L77
                    java.lang.Class<androidx.window.extensions.embedding.AnimationParams$Builder> r1 = androidx.window.extensions.embedding.AnimationParams.Builder.class
                    boolean r1 = r4.doesReturn$window_release(r2, r1)
                    if (r1 == 0) goto L77
                    r5.getClass()
                    boolean r1 = r4.isPublic$window_release(r5)
                    if (r1 == 0) goto L77
                    java.lang.Class<androidx.window.extensions.embedding.AnimationParams$Builder> r1 = androidx.window.extensions.embedding.AnimationParams.Builder.class
                    boolean r1 = r4.doesReturn$window_release(r5, r1)
                    if (r1 == 0) goto L77
                    r7.getClass()
                    boolean r1 = r4.isPublic$window_release(r7)
                    if (r1 == 0) goto L77
                    java.lang.Class<androidx.window.extensions.embedding.AnimationParams$Builder> r1 = androidx.window.extensions.embedding.AnimationParams.Builder.class
                    boolean r7 = r4.doesReturn$window_release(r7, r1)
                    if (r7 == 0) goto L77
                    goto L78
                L77:
                    r0 = r3
                L78:
                    java.lang.Boolean r7 = java.lang.Boolean.valueOf(r0)
                    return r7
                */
                throw new UnsupportedOperationException("Method not decompiled: androidx.window.embedding.SafeActivityEmbeddingComponentProvider.C00731.invoke():java.lang.Boolean");
            }
        });
    }

    private final boolean isClassAnimationParamsValid() {
        return ReflectionUtils.validateReflection$window_release("Class AnimationParams is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider.isClassAnimationParamsValid.1
            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() throws NoSuchFieldException, NoSuchMethodException {
                Field declaredField = AnimationParams.class.getDeclaredField("DEFAULT_ANIMATION_RESOURCES_ID");
                boolean z = false;
                Class[] clsArr = new Class[0];
                Method method = AnimationParams.class.getMethod("getAnimationBackground", null);
                Class[] clsArr2 = new Class[0];
                Method method2 = AnimationParams.class.getMethod("getOpenAnimationResId", null);
                Class[] clsArr3 = new Class[0];
                Method method3 = AnimationParams.class.getMethod("getCloseAnimationResId", null);
                Class[] clsArr4 = new Class[0];
                Method method4 = AnimationParams.class.getMethod("getChangeAnimationResId", null);
                ReflectionUtils reflectionUtils = ReflectionUtils.INSTANCE;
                declaredField.getClass();
                if (reflectionUtils.isPublic$window_release(declaredField)) {
                    method.getClass();
                    if (reflectionUtils.isPublic$window_release(method) && reflectionUtils.doesReturn$window_release(method, AnimationBackground.class)) {
                        method2.getClass();
                        if (reflectionUtils.isPublic$window_release(method2)) {
                            Class cls = Integer.TYPE;
                            if (reflectionUtils.doesReturn$window_release(method2, cls)) {
                                method3.getClass();
                                if (reflectionUtils.isPublic$window_release(method3) && reflectionUtils.doesReturn$window_release(method3, cls)) {
                                    method4.getClass();
                                    if (reflectionUtils.isPublic$window_release(method4) && reflectionUtils.doesReturn$window_release(method4, cls)) {
                                        z = true;
                                    }
                                }
                            }
                        }
                    }
                }
                return Boolean.valueOf(z);
            }
        });
    }

    private final boolean isClassDividerAttributesBuilderValid() {
        return ReflectionUtils.validateReflection$window_release("Class DividerAttributes.Builder is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider.isClassDividerAttributesBuilderValid.1
            /* JADX WARN: Removed duplicated region for block: B:23:0x009b  */
            @Override // kotlin.jvm.functions.Function0
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct add '--show-bad-code' argument
            */
            public final java.lang.Boolean invoke() throws java.lang.NoSuchMethodException {
                /*
                    r9 = this;
                    java.lang.Class<androidx.window.extensions.embedding.DividerAttributes$Builder> r9 = androidx.window.extensions.embedding.DividerAttributes.Builder.class
                    r0 = 1
                    java.lang.Class[] r1 = new java.lang.Class[r0]
                    java.lang.Class r2 = java.lang.Integer.TYPE
                    r3 = 0
                    r1[r3] = r2
                    java.lang.reflect.Constructor r1 = r9.getDeclaredConstructor(r1)
                    java.lang.Class[] r4 = new java.lang.Class[r0]
                    java.lang.Class<androidx.window.extensions.embedding.DividerAttributes> r5 = androidx.window.extensions.embedding.DividerAttributes.class
                    r4[r3] = r5
                    java.lang.reflect.Constructor r4 = r9.getDeclaredConstructor(r4)
                    java.lang.Class[] r5 = new java.lang.Class[r0]
                    r5[r3] = r2
                    java.lang.String r6 = "setWidthDp"
                    java.lang.reflect.Method r5 = r9.getMethod(r6, r5)
                    java.lang.Class[] r6 = new java.lang.Class[r0]
                    java.lang.Class r7 = java.lang.Float.TYPE
                    r6[r3] = r7
                    java.lang.String r8 = "setPrimaryMinRatio"
                    java.lang.reflect.Method r6 = r9.getMethod(r8, r6)
                    java.lang.Class[] r8 = new java.lang.Class[r0]
                    r8[r3] = r7
                    java.lang.String r7 = "setPrimaryMaxRatio"
                    java.lang.reflect.Method r7 = r9.getMethod(r7, r8)
                    java.lang.Class[] r8 = new java.lang.Class[r0]
                    r8[r3] = r2
                    java.lang.String r2 = "setDividerColor"
                    java.lang.reflect.Method r9 = r9.getMethod(r2, r8)
                    androidx.window.reflection.ReflectionUtils r2 = androidx.window.reflection.ReflectionUtils.INSTANCE
                    r1.getClass()
                    boolean r1 = r2.isPublic$window_release(r1)
                    if (r1 == 0) goto L9b
                    r4.getClass()
                    boolean r1 = r2.isPublic$window_release(r4)
                    if (r1 == 0) goto L9b
                    r5.getClass()
                    boolean r1 = r2.isPublic$window_release(r5)
                    if (r1 == 0) goto L9b
                    java.lang.Class<androidx.window.extensions.embedding.DividerAttributes$Builder> r1 = androidx.window.extensions.embedding.DividerAttributes.Builder.class
                    boolean r1 = r2.doesReturn$window_release(r5, r1)
                    if (r1 == 0) goto L9b
                    r6.getClass()
                    boolean r1 = r2.isPublic$window_release(r6)
                    if (r1 == 0) goto L9b
                    java.lang.Class<androidx.window.extensions.embedding.DividerAttributes$Builder> r1 = androidx.window.extensions.embedding.DividerAttributes.Builder.class
                    boolean r1 = r2.doesReturn$window_release(r6, r1)
                    if (r1 == 0) goto L9b
                    r7.getClass()
                    boolean r1 = r2.isPublic$window_release(r7)
                    if (r1 == 0) goto L9b
                    java.lang.Class<androidx.window.extensions.embedding.DividerAttributes$Builder> r1 = androidx.window.extensions.embedding.DividerAttributes.Builder.class
                    boolean r1 = r2.doesReturn$window_release(r7, r1)
                    if (r1 == 0) goto L9b
                    r9.getClass()
                    boolean r1 = r2.isPublic$window_release(r9)
                    if (r1 == 0) goto L9b
                    java.lang.Class<androidx.window.extensions.embedding.DividerAttributes$Builder> r1 = androidx.window.extensions.embedding.DividerAttributes.Builder.class
                    boolean r9 = r2.doesReturn$window_release(r9, r1)
                    if (r9 == 0) goto L9b
                    goto L9c
                L9b:
                    r0 = r3
                L9c:
                    java.lang.Boolean r9 = java.lang.Boolean.valueOf(r0)
                    return r9
                */
                throw new UnsupportedOperationException("Method not decompiled: androidx.window.embedding.SafeActivityEmbeddingComponentProvider.C00751.invoke():java.lang.Boolean");
            }
        });
    }

    private final boolean isClassDividerAttributesValid() {
        return ReflectionUtils.validateReflection$window_release("Class DividerAttributes is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider.isClassDividerAttributesValid.1
            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() throws NoSuchMethodException {
                boolean z = false;
                Class[] clsArr = new Class[0];
                Method method = androidx.window.extensions.embedding.DividerAttributes.class.getMethod("getDividerType", null);
                Class[] clsArr2 = new Class[0];
                Method method2 = androidx.window.extensions.embedding.DividerAttributes.class.getMethod("getWidthDp", null);
                Class[] clsArr3 = new Class[0];
                Method method3 = androidx.window.extensions.embedding.DividerAttributes.class.getMethod("getPrimaryMinRatio", null);
                Class[] clsArr4 = new Class[0];
                Method method4 = androidx.window.extensions.embedding.DividerAttributes.class.getMethod("getPrimaryMaxRatio", null);
                Class[] clsArr5 = new Class[0];
                Method method5 = androidx.window.extensions.embedding.DividerAttributes.class.getMethod("getDividerColor", null);
                ReflectionUtils reflectionUtils = ReflectionUtils.INSTANCE;
                method.getClass();
                if (reflectionUtils.isPublic$window_release(method)) {
                    Class cls = Integer.TYPE;
                    if (reflectionUtils.doesReturn$window_release(method, cls)) {
                        method2.getClass();
                        if (reflectionUtils.isPublic$window_release(method2) && reflectionUtils.doesReturn$window_release(method2, cls)) {
                            method3.getClass();
                            if (reflectionUtils.isPublic$window_release(method3)) {
                                Class cls2 = Float.TYPE;
                                if (reflectionUtils.doesReturn$window_release(method3, cls2)) {
                                    method4.getClass();
                                    if (reflectionUtils.isPublic$window_release(method4) && reflectionUtils.doesReturn$window_release(method4, cls2)) {
                                        method5.getClass();
                                        if (reflectionUtils.isPublic$window_release(method5) && reflectionUtils.doesReturn$window_release(method5, cls)) {
                                            z = true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                return Boolean.valueOf(z);
            }
        });
    }

    private final boolean isClassEmbeddedActivityWindowInfoValid() {
        return ReflectionUtils.validateReflection$window_release("Class EmbeddedActivityWindowInfo is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider.isClassEmbeddedActivityWindowInfoValid.1
            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() throws NoSuchMethodException {
                boolean z = false;
                Class[] clsArr = new Class[0];
                Method method = EmbeddedActivityWindowInfo.class.getMethod("getActivity", null);
                Class[] clsArr2 = new Class[0];
                Method method2 = EmbeddedActivityWindowInfo.class.getMethod("isEmbedded", null);
                Class[] clsArr3 = new Class[0];
                Method method3 = EmbeddedActivityWindowInfo.class.getMethod("getTaskBounds", null);
                Class[] clsArr4 = new Class[0];
                Method method4 = EmbeddedActivityWindowInfo.class.getMethod("getActivityStackBounds", null);
                ReflectionUtils reflectionUtils = ReflectionUtils.INSTANCE;
                method.getClass();
                if (reflectionUtils.isPublic$window_release(method) && reflectionUtils.doesReturn$window_release(method, Activity.class)) {
                    method2.getClass();
                    if (reflectionUtils.isPublic$window_release(method2) && reflectionUtils.doesReturn$window_release(method2, Boolean.TYPE)) {
                        method3.getClass();
                        if (reflectionUtils.isPublic$window_release(method3) && reflectionUtils.doesReturn$window_release(method3, Rect.class)) {
                            method4.getClass();
                            if (reflectionUtils.isPublic$window_release(method4) && reflectionUtils.doesReturn$window_release(method4, Rect.class)) {
                                z = true;
                            }
                        }
                    }
                }
                return Boolean.valueOf(z);
            }
        });
    }

    private final boolean isClassEmbeddingConfigurationBuilderApi5Valid() {
        return ReflectionUtils.validateReflection$window_release("Class EmbeddingConfiguration.Builder is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider.isClassEmbeddingConfigurationBuilderApi5Valid.1
            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() throws NoSuchMethodException {
                Method method = EmbeddingConfiguration$Builder.class.getMethod("setDimAreaBehavior", EmbeddingConfiguration$DimAreaBehavior.class);
                ReflectionUtils reflectionUtils = ReflectionUtils.INSTANCE;
                method.getClass();
                return Boolean.valueOf(reflectionUtils.isPublic$window_release(method) && reflectionUtils.doesReturn$window_release(method, EmbeddingConfiguration$Builder.class));
            }
        });
    }

    private final boolean isClassEmbeddingConfigurationBuilderApi8Valid() {
        return ReflectionUtils.validateReflection$window_release("Class EmbeddingConfiguration.Builder is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider.isClassEmbeddingConfigurationBuilderApi8Valid.1
            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() throws NoSuchMethodException {
                Method method = EmbeddingConfiguration$Builder.class.getMethod("setAutoSaveEmbeddingState", Boolean.TYPE);
                ReflectionUtils reflectionUtils = ReflectionUtils.INSTANCE;
                method.getClass();
                return Boolean.valueOf(reflectionUtils.isPublic$window_release(method) && reflectionUtils.doesReturn$window_release(method, EmbeddingConfiguration$Builder.class));
            }
        });
    }

    private final boolean isClassEmbeddingRuleValid() {
        return ReflectionUtils.validateReflection$window_release("Class EmbeddingRule is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider.isClassEmbeddingRuleValid.1
            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() throws NoSuchMethodException {
                boolean z = false;
                Class[] clsArr = new Class[0];
                Method method = EmbeddingRule.class.getMethod("getTag", null);
                ReflectionUtils reflectionUtils = ReflectionUtils.INSTANCE;
                method.getClass();
                if (reflectionUtils.isPublic$window_release(method) && reflectionUtils.doesReturn$window_release(method, String.class)) {
                    z = true;
                }
                return Boolean.valueOf(z);
            }
        });
    }

    private final boolean isClassSplitAttributesCalculatorParamsValid() {
        return ReflectionUtils.validateReflection$window_release("Class SplitAttributesCalculatorParams is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider.isClassSplitAttributesCalculatorParamsValid.1
            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() throws NoSuchMethodException {
                boolean z = false;
                Class[] clsArr = new Class[0];
                Method method = SplitAttributesCalculatorParams.class.getMethod("getParentWindowMetrics", null);
                Class[] clsArr2 = new Class[0];
                Method method2 = SplitAttributesCalculatorParams.class.getMethod("getParentConfiguration", null);
                Class[] clsArr3 = new Class[0];
                Method method3 = SplitAttributesCalculatorParams.class.getMethod("getDefaultSplitAttributes", null);
                Class[] clsArr4 = new Class[0];
                Method method4 = SplitAttributesCalculatorParams.class.getMethod("areDefaultConstraintsSatisfied", null);
                Class[] clsArr5 = new Class[0];
                Method method5 = SplitAttributesCalculatorParams.class.getMethod("getParentWindowLayoutInfo", null);
                Class[] clsArr6 = new Class[0];
                Method method6 = SplitAttributesCalculatorParams.class.getMethod("getSplitRuleTag", null);
                ReflectionUtils reflectionUtils = ReflectionUtils.INSTANCE;
                method.getClass();
                if (reflectionUtils.isPublic$window_release(method) && reflectionUtils.doesReturn$window_release(method, WindowMetrics.class)) {
                    method2.getClass();
                    if (reflectionUtils.isPublic$window_release(method2) && reflectionUtils.doesReturn$window_release(method2, Configuration.class)) {
                        method3.getClass();
                        if (reflectionUtils.isPublic$window_release(method3) && reflectionUtils.doesReturn$window_release(method3, androidx.window.extensions.embedding.SplitAttributes.class)) {
                            method4.getClass();
                            if (reflectionUtils.isPublic$window_release(method4) && reflectionUtils.doesReturn$window_release(method4, Boolean.TYPE)) {
                                method5.getClass();
                                if (reflectionUtils.isPublic$window_release(method5) && reflectionUtils.doesReturn$window_release(method5, WindowLayoutInfo.class)) {
                                    method6.getClass();
                                    if (reflectionUtils.isPublic$window_release(method6) && reflectionUtils.doesReturn$window_release(method6, String.class)) {
                                        z = true;
                                    }
                                }
                            }
                        }
                    }
                }
                return Boolean.valueOf(z);
            }
        });
    }

    private final boolean isClassSplitAttributesValid() {
        return ReflectionUtils.validateReflection$window_release("Class SplitAttributes is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider.isClassSplitAttributesValid.1
            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() throws NoSuchMethodException {
                boolean z = false;
                Class[] clsArr = new Class[0];
                Method method = androidx.window.extensions.embedding.SplitAttributes.class.getMethod("getLayoutDirection", null);
                Class[] clsArr2 = new Class[0];
                Method method2 = androidx.window.extensions.embedding.SplitAttributes.class.getMethod("getSplitType", null);
                Method method3 = SplitAttributes.Builder.class.getMethod("setSplitType", SplitAttributes.SplitType.class);
                Class cls = Integer.TYPE;
                Method method4 = SplitAttributes.Builder.class.getMethod("setLayoutDirection", cls);
                ReflectionUtils reflectionUtils = ReflectionUtils.INSTANCE;
                method.getClass();
                if (reflectionUtils.isPublic$window_release(method) && reflectionUtils.doesReturn$window_release(method, cls)) {
                    method2.getClass();
                    if (reflectionUtils.isPublic$window_release(method2) && reflectionUtils.doesReturn$window_release(method2, SplitAttributes.SplitType.class)) {
                        method3.getClass();
                        if (reflectionUtils.isPublic$window_release(method3)) {
                            method4.getClass();
                            if (reflectionUtils.isPublic$window_release(method4)) {
                                z = true;
                            }
                        }
                    }
                }
                return Boolean.valueOf(z);
            }
        });
    }

    private final boolean isClassSplitInfoTokenValid() {
        return ReflectionUtils.validateReflection$window_release("SplitInfo.Token is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider.isClassSplitInfoTokenValid.1
            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() throws NoSuchMethodException {
                Method method = SplitInfo.Token.class.getMethod("createFromBinder", IBinder.class);
                ReflectionUtils reflectionUtils = ReflectionUtils.INSTANCE;
                method.getClass();
                return Boolean.valueOf(reflectionUtils.isPublic$window_release(method) && reflectionUtils.doesReturn$window_release(method, SplitInfo.Token.class));
            }
        });
    }

    private final boolean isClassSplitInfoValid() {
        return ReflectionUtils.validateReflection$window_release("Class SplitInfo is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider.isClassSplitInfoValid.1
            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() throws NoSuchMethodException {
                boolean z = false;
                Class[] clsArr = new Class[0];
                Method method = androidx.window.extensions.embedding.SplitInfo.class.getMethod("getPrimaryActivityStack", null);
                Class[] clsArr2 = new Class[0];
                Method method2 = androidx.window.extensions.embedding.SplitInfo.class.getMethod("getSecondaryActivityStack", null);
                Class[] clsArr3 = new Class[0];
                Method method3 = androidx.window.extensions.embedding.SplitInfo.class.getMethod("getSplitRatio", null);
                ReflectionUtils reflectionUtils = ReflectionUtils.INSTANCE;
                method.getClass();
                if (reflectionUtils.isPublic$window_release(method) && reflectionUtils.doesReturn$window_release(method, androidx.window.extensions.embedding.ActivityStack.class)) {
                    method2.getClass();
                    if (reflectionUtils.isPublic$window_release(method2) && reflectionUtils.doesReturn$window_release(method2, androidx.window.extensions.embedding.ActivityStack.class)) {
                        method3.getClass();
                        if (reflectionUtils.isPublic$window_release(method3) && reflectionUtils.doesReturn$window_release(method3, Float.TYPE)) {
                            z = true;
                        }
                    }
                }
                return Boolean.valueOf(z);
            }
        });
    }

    private final boolean isClassSplitPairRuleBuilderLevel1Valid() {
        return ReflectionUtils.validateReflection$window_release("Class SplitPairRule.Builder is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider.isClassSplitPairRuleBuilderLevel1Valid.1
            /* JADX WARN: Removed duplicated region for block: B:11:0x0041  */
            @Override // kotlin.jvm.functions.Function0
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct add '--show-bad-code' argument
            */
            public final java.lang.Boolean invoke() throws java.lang.NoSuchMethodException {
                /*
                    r5 = this;
                    java.lang.Class<androidx.window.extensions.embedding.SplitPairRule$Builder> r5 = androidx.window.extensions.embedding.SplitPairRule.Builder.class
                    r0 = 1
                    java.lang.Class[] r1 = new java.lang.Class[r0]
                    java.lang.Class r2 = java.lang.Float.TYPE
                    r3 = 0
                    r1[r3] = r2
                    java.lang.String r2 = "setSplitRatio"
                    java.lang.reflect.Method r1 = r5.getMethod(r2, r1)
                    java.lang.Class[] r2 = new java.lang.Class[r0]
                    java.lang.Class r4 = java.lang.Integer.TYPE
                    r2[r3] = r4
                    java.lang.String r4 = "setLayoutDirection"
                    java.lang.reflect.Method r5 = r5.getMethod(r4, r2)
                    androidx.window.reflection.ReflectionUtils r2 = androidx.window.reflection.ReflectionUtils.INSTANCE
                    r1.getClass()
                    boolean r4 = r2.isPublic$window_release(r1)
                    if (r4 == 0) goto L41
                    java.lang.Class<androidx.window.extensions.embedding.SplitPairRule$Builder> r4 = androidx.window.extensions.embedding.SplitPairRule.Builder.class
                    boolean r1 = r2.doesReturn$window_release(r1, r4)
                    if (r1 == 0) goto L41
                    r5.getClass()
                    boolean r1 = r2.isPublic$window_release(r5)
                    if (r1 == 0) goto L41
                    java.lang.Class<androidx.window.extensions.embedding.SplitPairRule$Builder> r1 = androidx.window.extensions.embedding.SplitPairRule.Builder.class
                    boolean r5 = r2.doesReturn$window_release(r5, r1)
                    if (r5 == 0) goto L41
                    goto L42
                L41:
                    r0 = r3
                L42:
                    java.lang.Boolean r5 = java.lang.Boolean.valueOf(r0)
                    return r5
                */
                throw new UnsupportedOperationException("Method not decompiled: androidx.window.embedding.SafeActivityEmbeddingComponentProvider.C00851.invoke():java.lang.Boolean");
            }
        });
    }

    private final boolean isClassSplitPairRuleBuilderLevel2Valid() {
        return ReflectionUtils.validateReflection$window_release("Class SplitPairRule.Builder is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider.isClassSplitPairRuleBuilderLevel2Valid.1
            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() throws NoSuchMethodException {
                boolean z = false;
                Constructor declaredConstructor = SplitPairRule.Builder.class.getDeclaredConstructor(Predicate.class, Predicate.class, Predicate.class);
                Method method = SplitPairRule.Builder.class.getMethod("setDefaultSplitAttributes", androidx.window.extensions.embedding.SplitAttributes.class);
                Method method2 = SplitPairRule.Builder.class.getMethod("setTag", String.class);
                ReflectionUtils reflectionUtils = ReflectionUtils.INSTANCE;
                declaredConstructor.getClass();
                if (reflectionUtils.isPublic$window_release(declaredConstructor)) {
                    method.getClass();
                    if (reflectionUtils.isPublic$window_release(method) && reflectionUtils.doesReturn$window_release(method, SplitPairRule.Builder.class)) {
                        method2.getClass();
                        if (reflectionUtils.isPublic$window_release(method2) && reflectionUtils.doesReturn$window_release(method2, SplitPairRule.Builder.class)) {
                            z = true;
                        }
                    }
                }
                return Boolean.valueOf(z);
            }
        });
    }

    private final boolean isClassSplitPairRuleValid() {
        return ReflectionUtils.validateReflection$window_release("Class SplitPairRule is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider.isClassSplitPairRuleValid.1
            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() throws NoSuchMethodException {
                boolean z = false;
                Class[] clsArr = new Class[0];
                Method method = SplitPairRule.class.getMethod("getFinishPrimaryWithSecondary", null);
                Class[] clsArr2 = new Class[0];
                Method method2 = SplitPairRule.class.getMethod("getFinishSecondaryWithPrimary", null);
                Class[] clsArr3 = new Class[0];
                Method method3 = SplitPairRule.class.getMethod("shouldClearTop", null);
                ReflectionUtils reflectionUtils = ReflectionUtils.INSTANCE;
                method.getClass();
                if (reflectionUtils.isPublic$window_release(method)) {
                    Class cls = Integer.TYPE;
                    if (reflectionUtils.doesReturn$window_release(method, cls)) {
                        method2.getClass();
                        if (reflectionUtils.isPublic$window_release(method2) && reflectionUtils.doesReturn$window_release(method2, cls)) {
                            method3.getClass();
                            if (reflectionUtils.isPublic$window_release(method3) && reflectionUtils.doesReturn$window_release(method3, Boolean.TYPE)) {
                                z = true;
                            }
                        }
                    }
                }
                return Boolean.valueOf(z);
            }
        });
    }

    private final boolean isClassSplitPlaceholderRuleBuilderLevel1Valid() {
        return ReflectionUtils.validateReflection$window_release("Class SplitPlaceholderRule.Builder is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider.isClassSplitPlaceholderRuleBuilderLevel1Valid.1
            /* JADX WARN: Removed duplicated region for block: B:19:0x0079  */
            @Override // kotlin.jvm.functions.Function0
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct add '--show-bad-code' argument
            */
            public final java.lang.Boolean invoke() throws java.lang.NoSuchMethodException {
                /*
                    r7 = this;
                    java.lang.Class<androidx.window.extensions.embedding.SplitPlaceholderRule$Builder> r7 = androidx.window.extensions.embedding.SplitPlaceholderRule.Builder.class
                    r0 = 1
                    java.lang.Class[] r1 = new java.lang.Class[r0]
                    java.lang.Class r2 = java.lang.Float.TYPE
                    r3 = 0
                    r1[r3] = r2
                    java.lang.String r2 = "setSplitRatio"
                    java.lang.reflect.Method r1 = r7.getMethod(r2, r1)
                    java.lang.Class[] r2 = new java.lang.Class[r0]
                    java.lang.Class r4 = java.lang.Integer.TYPE
                    r2[r3] = r4
                    java.lang.String r5 = "setLayoutDirection"
                    java.lang.reflect.Method r2 = r7.getMethod(r5, r2)
                    java.lang.Class[] r5 = new java.lang.Class[r0]
                    java.lang.Class r6 = java.lang.Boolean.TYPE
                    r5[r3] = r6
                    java.lang.String r6 = "setSticky"
                    java.lang.reflect.Method r5 = r7.getMethod(r6, r5)
                    java.lang.Class[] r6 = new java.lang.Class[r0]
                    r6[r3] = r4
                    java.lang.String r4 = "setFinishPrimaryWithSecondary"
                    java.lang.reflect.Method r7 = r7.getMethod(r4, r6)
                    androidx.window.reflection.ReflectionUtils r4 = androidx.window.reflection.ReflectionUtils.INSTANCE
                    r1.getClass()
                    boolean r6 = r4.isPublic$window_release(r1)
                    if (r6 == 0) goto L79
                    java.lang.Class<androidx.window.extensions.embedding.SplitPlaceholderRule$Builder> r6 = androidx.window.extensions.embedding.SplitPlaceholderRule.Builder.class
                    boolean r1 = r4.doesReturn$window_release(r1, r6)
                    if (r1 == 0) goto L79
                    r2.getClass()
                    boolean r1 = r4.isPublic$window_release(r2)
                    if (r1 == 0) goto L79
                    java.lang.Class<androidx.window.extensions.embedding.SplitPlaceholderRule$Builder> r1 = androidx.window.extensions.embedding.SplitPlaceholderRule.Builder.class
                    boolean r1 = r4.doesReturn$window_release(r2, r1)
                    if (r1 == 0) goto L79
                    r5.getClass()
                    boolean r1 = r4.isPublic$window_release(r5)
                    if (r1 == 0) goto L79
                    java.lang.Class<androidx.window.extensions.embedding.SplitPlaceholderRule$Builder> r1 = androidx.window.extensions.embedding.SplitPlaceholderRule.Builder.class
                    boolean r1 = r4.doesReturn$window_release(r5, r1)
                    if (r1 == 0) goto L79
                    r7.getClass()
                    boolean r1 = r4.isPublic$window_release(r7)
                    if (r1 == 0) goto L79
                    java.lang.Class<androidx.window.extensions.embedding.SplitPlaceholderRule$Builder> r1 = androidx.window.extensions.embedding.SplitPlaceholderRule.Builder.class
                    boolean r7 = r4.doesReturn$window_release(r7, r1)
                    if (r7 == 0) goto L79
                    goto L7a
                L79:
                    r0 = r3
                L7a:
                    java.lang.Boolean r7 = java.lang.Boolean.valueOf(r0)
                    return r7
                */
                throw new UnsupportedOperationException("Method not decompiled: androidx.window.embedding.SafeActivityEmbeddingComponentProvider.C00881.invoke():java.lang.Boolean");
            }
        });
    }

    private final boolean isClassSplitPlaceholderRuleBuilderLevel2Valid() {
        return ReflectionUtils.validateReflection$window_release("Class SplitPlaceholderRule.Builder is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider.isClassSplitPlaceholderRuleBuilderLevel2Valid.1
            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() throws NoSuchMethodException {
                boolean z = false;
                Constructor declaredConstructor = SplitPlaceholderRule.Builder.class.getDeclaredConstructor(Intent.class, Predicate.class, Predicate.class, Predicate.class);
                Method method = SplitPlaceholderRule.Builder.class.getMethod("setDefaultSplitAttributes", androidx.window.extensions.embedding.SplitAttributes.class);
                Method method2 = SplitPlaceholderRule.Builder.class.getMethod("setFinishPrimaryWithPlaceholder", Integer.TYPE);
                Method method3 = SplitPlaceholderRule.Builder.class.getMethod("setTag", String.class);
                ReflectionUtils reflectionUtils = ReflectionUtils.INSTANCE;
                declaredConstructor.getClass();
                if (reflectionUtils.isPublic$window_release(declaredConstructor)) {
                    method.getClass();
                    if (reflectionUtils.isPublic$window_release(method) && reflectionUtils.doesReturn$window_release(method, SplitPlaceholderRule.Builder.class)) {
                        method2.getClass();
                        if (reflectionUtils.isPublic$window_release(method2) && reflectionUtils.doesReturn$window_release(method2, SplitPlaceholderRule.Builder.class)) {
                            method3.getClass();
                            if (reflectionUtils.isPublic$window_release(method3) && reflectionUtils.doesReturn$window_release(method3, SplitPlaceholderRule.Builder.class)) {
                                z = true;
                            }
                        }
                    }
                }
                return Boolean.valueOf(z);
            }
        });
    }

    private final boolean isClassSplitPlaceholderRuleValid() {
        return ReflectionUtils.validateReflection$window_release("Class SplitPlaceholderRule is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider.isClassSplitPlaceholderRuleValid.1
            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() throws NoSuchMethodException {
                boolean z = false;
                Class[] clsArr = new Class[0];
                Method method = SplitPlaceholderRule.class.getMethod("getPlaceholderIntent", null);
                Class[] clsArr2 = new Class[0];
                Method method2 = SplitPlaceholderRule.class.getMethod("isSticky", null);
                Class[] clsArr3 = new Class[0];
                Method method3 = SplitPlaceholderRule.class.getMethod("getFinishPrimaryWithSecondary", null);
                ReflectionUtils reflectionUtils = ReflectionUtils.INSTANCE;
                method.getClass();
                if (reflectionUtils.isPublic$window_release(method) && reflectionUtils.doesReturn$window_release(method, Intent.class)) {
                    method2.getClass();
                    if (reflectionUtils.isPublic$window_release(method2) && reflectionUtils.doesReturn$window_release(method2, Boolean.TYPE)) {
                        method3.getClass();
                        if (reflectionUtils.isPublic$window_release(method3) && reflectionUtils.doesReturn$window_release(method3, Integer.TYPE)) {
                            z = true;
                        }
                    }
                }
                return Boolean.valueOf(z);
            }
        });
    }

    private final boolean isClassSplitTypeValid() {
        return ReflectionUtils.validateReflection$window_release("Class SplitAttributes.SplitType is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider.isClassSplitTypeValid.1
            /* JADX WARN: Removed duplicated region for block: B:21:0x008a  */
            @Override // kotlin.jvm.functions.Function0
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct add '--show-bad-code' argument
            */
            public final java.lang.Boolean invoke() throws java.lang.NoSuchMethodException {
                /*
                    r10 = this;
                    java.lang.Class<androidx.window.extensions.embedding.SplitAttributes$SplitType$RatioSplitType> r10 = androidx.window.extensions.embedding.SplitAttributes.SplitType.RatioSplitType.class
                    r0 = 1
                    java.lang.Class[] r1 = new java.lang.Class[r0]
                    java.lang.Class r2 = java.lang.Float.TYPE
                    r3 = 0
                    r1[r3] = r2
                    java.lang.reflect.Constructor r1 = r10.getDeclaredConstructor(r1)
                    java.lang.Class[] r4 = new java.lang.Class[r3]
                    java.lang.String r4 = "getRatio"
                    r5 = 0
                    java.lang.reflect.Method r4 = r10.getMethod(r4, r5)
                    java.lang.Class[] r6 = new java.lang.Class[r3]
                    java.lang.String r6 = "splitEqually"
                    java.lang.reflect.Method r10 = r10.getMethod(r6, r5)
                    java.lang.Class<androidx.window.extensions.embedding.SplitAttributes$SplitType$HingeSplitType> r6 = androidx.window.extensions.embedding.SplitAttributes.SplitType.HingeSplitType.class
                    java.lang.Class[] r7 = new java.lang.Class[r0]
                    java.lang.Class<androidx.window.extensions.embedding.SplitAttributes$SplitType> r8 = androidx.window.extensions.embedding.SplitAttributes.SplitType.class
                    r7[r3] = r8
                    java.lang.reflect.Constructor r7 = r6.getDeclaredConstructor(r7)
                    java.lang.Class[] r8 = new java.lang.Class[r3]
                    java.lang.String r8 = "getFallbackSplitType"
                    java.lang.reflect.Method r6 = r6.getMethod(r8, r5)
                    java.lang.Class<androidx.window.extensions.embedding.SplitAttributes$SplitType$ExpandContainersSplitType> r8 = androidx.window.extensions.embedding.SplitAttributes.SplitType.ExpandContainersSplitType.class
                    java.lang.Class[] r9 = new java.lang.Class[r3]
                    java.lang.reflect.Constructor r5 = r8.getDeclaredConstructor(r5)
                    androidx.window.reflection.ReflectionUtils r8 = androidx.window.reflection.ReflectionUtils.INSTANCE
                    r1.getClass()
                    boolean r1 = r8.isPublic$window_release(r1)
                    if (r1 == 0) goto L8a
                    r4.getClass()
                    boolean r1 = r8.isPublic$window_release(r4)
                    if (r1 == 0) goto L8a
                    boolean r1 = r8.doesReturn$window_release(r4, r2)
                    if (r1 == 0) goto L8a
                    r7.getClass()
                    boolean r1 = r8.isPublic$window_release(r7)
                    if (r1 == 0) goto L8a
                    r10.getClass()
                    boolean r1 = r8.isPublic$window_release(r10)
                    if (r1 == 0) goto L8a
                    java.lang.Class<androidx.window.extensions.embedding.SplitAttributes$SplitType$RatioSplitType> r1 = androidx.window.extensions.embedding.SplitAttributes.SplitType.RatioSplitType.class
                    boolean r10 = r8.doesReturn$window_release(r10, r1)
                    if (r10 == 0) goto L8a
                    r6.getClass()
                    boolean r10 = r8.isPublic$window_release(r6)
                    if (r10 == 0) goto L8a
                    java.lang.Class<androidx.window.extensions.embedding.SplitAttributes$SplitType> r10 = androidx.window.extensions.embedding.SplitAttributes.SplitType.class
                    boolean r10 = r8.doesReturn$window_release(r6, r10)
                    if (r10 == 0) goto L8a
                    r5.getClass()
                    boolean r10 = r8.isPublic$window_release(r5)
                    if (r10 == 0) goto L8a
                    goto L8b
                L8a:
                    r0 = r3
                L8b:
                    java.lang.Boolean r10 = java.lang.Boolean.valueOf(r0)
                    return r10
                */
                throw new UnsupportedOperationException("Method not decompiled: androidx.window.embedding.SafeActivityEmbeddingComponentProvider.C00911.invoke():java.lang.Boolean");
            }
        });
    }

    private final boolean isClassWindowAttributesValid() {
        return ReflectionUtils.validateReflection$window_release("Class WindowAttributes is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider.isClassWindowAttributesValid.1
            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() throws NoSuchMethodException {
                boolean z = false;
                Class[] clsArr = new Class[0];
                Method method = WindowAttributes.class.getMethod("getDimAreaBehavior", null);
                Class[] clsArr2 = new Class[0];
                Method method2 = androidx.window.extensions.embedding.SplitAttributes.class.getMethod("getWindowAttributes", null);
                Method method3 = SplitAttributes.Builder.class.getMethod("setWindowAttributes", WindowAttributes.class);
                ReflectionUtils reflectionUtils = ReflectionUtils.INSTANCE;
                method.getClass();
                if (reflectionUtils.isPublic$window_release(method) && reflectionUtils.doesReturn$window_release(method, Integer.TYPE)) {
                    method2.getClass();
                    if (reflectionUtils.isPublic$window_release(method2) && reflectionUtils.doesReturn$window_release(method2, WindowAttributes.class)) {
                        method3.getClass();
                        if (reflectionUtils.isPublic$window_release(method3) && reflectionUtils.doesReturn$window_release(method3, SplitAttributes.Builder.class)) {
                            z = true;
                        }
                    }
                }
                return Boolean.valueOf(z);
            }
        });
    }

    private final boolean isMethodClearEmbeddedActivityWindowInfoCallbackValid() {
        return ReflectionUtils.validateReflection$window_release("ActivityEmbeddingComponent#clearEmbeddedActivityWindowInfoCallback is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider.isMethodClearEmbeddedActivityWindowInfoCallbackValid.1
            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() throws NoSuchMethodException {
                Class[] clsArr = new Class[0];
                Method method = SafeActivityEmbeddingComponentProvider.this.getActivityEmbeddingComponentClass().getMethod("clearEmbeddedActivityWindowInfoCallback", null);
                ReflectionUtils reflectionUtils = ReflectionUtils.INSTANCE;
                method.getClass();
                return Boolean.valueOf(reflectionUtils.isPublic$window_release(method));
            }
        });
    }

    private final boolean isMethodClearSplitInfoCallbackValid() {
        return ReflectionUtils.validateReflection$window_release("ActivityEmbeddingComponent#clearSplitInfoCallback is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider.isMethodClearSplitInfoCallbackValid.1
            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() throws NoSuchMethodException {
                Class[] clsArr = new Class[0];
                Method method = SafeActivityEmbeddingComponentProvider.this.getActivityEmbeddingComponentClass().getMethod("clearSplitInfoCallback", null);
                ReflectionUtils reflectionUtils = ReflectionUtils.INSTANCE;
                method.getClass();
                return Boolean.valueOf(reflectionUtils.isPublic$window_release(method));
            }
        });
    }

    private final boolean isMethodGetAnimationParamsValid() {
        return ReflectionUtils.validateReflection$window_release("SplitAttributes#getAnimationParams is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider.isMethodGetAnimationParamsValid.1
            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() throws NoSuchMethodException {
                boolean z = false;
                Class[] clsArr = new Class[0];
                Method method = androidx.window.extensions.embedding.SplitAttributes.class.getMethod("getAnimationParams", null);
                ReflectionUtils reflectionUtils = ReflectionUtils.INSTANCE;
                method.getClass();
                if (reflectionUtils.isPublic$window_release(method) && reflectionUtils.doesReturn$window_release(method, AnimationParams.class)) {
                    z = true;
                }
                return Boolean.valueOf(z);
            }
        });
    }

    private final boolean isMethodGetDefaultSplitAttributesValid() {
        return ReflectionUtils.validateReflection$window_release("SplitRule#getDefaultSplitAttributes is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider.isMethodGetDefaultSplitAttributesValid.1
            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() throws NoSuchMethodException {
                boolean z = false;
                Class[] clsArr = new Class[0];
                Method method = SplitRule.class.getMethod("getDefaultSplitAttributes", null);
                ReflectionUtils reflectionUtils = ReflectionUtils.INSTANCE;
                method.getClass();
                if (reflectionUtils.isPublic$window_release(method) && reflectionUtils.doesReturn$window_release(method, androidx.window.extensions.embedding.SplitAttributes.class)) {
                    z = true;
                }
                return Boolean.valueOf(z);
            }
        });
    }

    private final boolean isMethodGetDividerAttributesValid() {
        return ReflectionUtils.validateReflection$window_release("SplitAttributes#getDividerAttributes is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider.isMethodGetDividerAttributesValid.1
            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() throws NoSuchMethodException {
                boolean z = false;
                Class[] clsArr = new Class[0];
                Method method = androidx.window.extensions.embedding.SplitAttributes.class.getMethod("getDividerAttributes", null);
                ReflectionUtils reflectionUtils = ReflectionUtils.INSTANCE;
                method.getClass();
                if (reflectionUtils.isPublic$window_release(method) && reflectionUtils.doesReturn$window_release(method, androidx.window.extensions.embedding.DividerAttributes.class)) {
                    z = true;
                }
                return Boolean.valueOf(z);
            }
        });
    }

    private final boolean isMethodGetEmbeddedActivityWindowInfoValid() {
        return ReflectionUtils.validateReflection$window_release("ActivityEmbeddingComponent#getEmbeddedActivityWindowInfo is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider.isMethodGetEmbeddedActivityWindowInfoValid.1
            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() throws NoSuchMethodException {
                Method method = SafeActivityEmbeddingComponentProvider.this.getActivityEmbeddingComponentClass().getMethod("getEmbeddedActivityWindowInfo", Activity.class);
                ReflectionUtils reflectionUtils = ReflectionUtils.INSTANCE;
                method.getClass();
                return Boolean.valueOf(reflectionUtils.isPublic$window_release(method) && reflectionUtils.doesReturn$window_release(method, EmbeddedActivityWindowInfo.class));
            }
        });
    }

    private final boolean isMethodGetFinishPrimaryWithPlaceholderValid() {
        return ReflectionUtils.validateReflection$window_release("SplitPlaceholderRule#getFinishPrimaryWithPlaceholder is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider.isMethodGetFinishPrimaryWithPlaceholderValid.1
            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() throws NoSuchMethodException {
                boolean z = false;
                Class[] clsArr = new Class[0];
                Method method = SplitPlaceholderRule.class.getMethod("getFinishPrimaryWithPlaceholder", null);
                ReflectionUtils reflectionUtils = ReflectionUtils.INSTANCE;
                method.getClass();
                if (reflectionUtils.isPublic$window_release(method) && reflectionUtils.doesReturn$window_release(method, Integer.TYPE)) {
                    z = true;
                }
                return Boolean.valueOf(z);
            }
        });
    }

    private final boolean isMethodGetLayoutDirectionValid() {
        return ReflectionUtils.validateReflection$window_release("SplitRule#getLayoutDirection is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider.isMethodGetLayoutDirectionValid.1
            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() throws NoSuchMethodException {
                boolean z = false;
                Class[] clsArr = new Class[0];
                Method method = SplitRule.class.getMethod("getLayoutDirection", null);
                ReflectionUtils reflectionUtils = ReflectionUtils.INSTANCE;
                method.getClass();
                if (reflectionUtils.isPublic$window_release(method) && reflectionUtils.doesReturn$window_release(method, Integer.TYPE)) {
                    z = true;
                }
                return Boolean.valueOf(z);
            }
        });
    }

    private final boolean isMethodGetSplitAttributesValid() {
        return ReflectionUtils.validateReflection$window_release("SplitInfo#getSplitAttributes is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider.isMethodGetSplitAttributesValid.1
            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() throws NoSuchMethodException {
                boolean z = false;
                Class[] clsArr = new Class[0];
                Method method = androidx.window.extensions.embedding.SplitInfo.class.getMethod("getSplitAttributes", null);
                ReflectionUtils reflectionUtils = ReflectionUtils.INSTANCE;
                method.getClass();
                if (reflectionUtils.isPublic$window_release(method) && reflectionUtils.doesReturn$window_release(method, androidx.window.extensions.embedding.SplitAttributes.class)) {
                    z = true;
                }
                return Boolean.valueOf(z);
            }
        });
    }

    private final boolean isMethodGetSplitInfoTokenValid() {
        return ReflectionUtils.validateReflection$window_release("SplitInfo#getSplitInfoToken is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider.isMethodGetSplitInfoTokenValid.1
            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() throws NoSuchMethodException {
                boolean z = false;
                Class[] clsArr = new Class[0];
                Method method = androidx.window.extensions.embedding.SplitInfo.class.getMethod("getSplitInfoToken", null);
                ReflectionUtils reflectionUtils = ReflectionUtils.INSTANCE;
                method.getClass();
                if (reflectionUtils.isPublic$window_release(method) && reflectionUtils.doesReturn$window_release(method, SplitInfo.Token.class)) {
                    z = true;
                }
                return Boolean.valueOf(z);
            }
        });
    }

    private final boolean isMethodGetSplitRatioValid() {
        return ReflectionUtils.validateReflection$window_release("SplitRule#getSplitRatio is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider.isMethodGetSplitRatioValid.1
            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() throws NoSuchMethodException {
                boolean z = false;
                Class[] clsArr = new Class[0];
                Method method = SplitRule.class.getMethod("getSplitRatio", null);
                ReflectionUtils reflectionUtils = ReflectionUtils.INSTANCE;
                method.getClass();
                if (reflectionUtils.isPublic$window_release(method) && reflectionUtils.doesReturn$window_release(method, Float.TYPE)) {
                    z = true;
                }
                return Boolean.valueOf(z);
            }
        });
    }

    private final boolean isMethodInvalidateTopVisibleSplitAttributesValid() {
        return ReflectionUtils.validateReflection$window_release("#invalidateTopVisibleSplitAttributes is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider.isMethodInvalidateTopVisibleSplitAttributesValid.1
            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() throws NoSuchMethodException {
                Class[] clsArr = new Class[0];
                Method method = SafeActivityEmbeddingComponentProvider.this.getActivityEmbeddingComponentClass().getMethod("invalidateTopVisibleSplitAttributes", null);
                ReflectionUtils reflectionUtils = ReflectionUtils.INSTANCE;
                method.getClass();
                return Boolean.valueOf(reflectionUtils.isPublic$window_release(method));
            }
        });
    }

    private final boolean isMethodIsActivityEmbeddedValid() {
        return ReflectionUtils.validateReflection$window_release("ActivityEmbeddingComponent#isActivityEmbedded is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider.isMethodIsActivityEmbeddedValid.1
            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() throws NoSuchMethodException {
                Method method = SafeActivityEmbeddingComponentProvider.this.getActivityEmbeddingComponentClass().getMethod("isActivityEmbedded", Activity.class);
                ReflectionUtils reflectionUtils = ReflectionUtils.INSTANCE;
                method.getClass();
                return Boolean.valueOf(reflectionUtils.isPublic$window_release(method) && reflectionUtils.doesReturn$window_release(method, Boolean.TYPE));
            }
        });
    }

    private final boolean isMethodIsDraggingToFullscreenAllowedValid() {
        return ReflectionUtils.validateReflection$window_release("DividerAttributes#isDraggingToFullscreenAllowed is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider.isMethodIsDraggingToFullscreenAllowedValid.1
            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() throws NoSuchMethodException {
                boolean z = false;
                Class[] clsArr = new Class[0];
                Method method = androidx.window.extensions.embedding.DividerAttributes.class.getMethod("isDraggingToFullscreenAllowed", null);
                ReflectionUtils reflectionUtils = ReflectionUtils.INSTANCE;
                method.getClass();
                if (reflectionUtils.isPublic$window_release(method) && reflectionUtils.doesReturn$window_release(method, Boolean.TYPE)) {
                    z = true;
                }
                return Boolean.valueOf(z);
            }
        });
    }

    private final boolean isMethodPinUnpinTopActivityStackValid() {
        return ReflectionUtils.validateReflection$window_release("#pin(unPin)TopActivityStack is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider.isMethodPinUnpinTopActivityStackValid.1
            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() throws NoSuchMethodException, ClassNotFoundException {
                boolean z = false;
                Class[] clsArr = new Class[0];
                Method method = SplitPinRule.class.getMethod("isSticky", null);
                Class activityEmbeddingComponentClass = SafeActivityEmbeddingComponentProvider.this.getActivityEmbeddingComponentClass();
                Class<?> cls = Integer.TYPE;
                Method method2 = activityEmbeddingComponentClass.getMethod("pinTopActivityStack", cls, SplitPinRule.class);
                Method method3 = SafeActivityEmbeddingComponentProvider.this.getActivityEmbeddingComponentClass().getMethod("unpinTopActivityStack", cls);
                ReflectionUtils reflectionUtils = ReflectionUtils.INSTANCE;
                method.getClass();
                if (reflectionUtils.isPublic$window_release(method)) {
                    Class cls2 = Boolean.TYPE;
                    if (reflectionUtils.doesReturn$window_release(method, cls2)) {
                        method2.getClass();
                        if (reflectionUtils.isPublic$window_release(method2) && reflectionUtils.doesReturn$window_release(method2, cls2)) {
                            method3.getClass();
                            if (reflectionUtils.isPublic$window_release(method3)) {
                                z = true;
                            }
                        }
                    }
                }
                return Boolean.valueOf(z);
            }
        });
    }

    private final boolean isMethodRegisterActivityStackCallbackValid() {
        return ReflectionUtils.validateReflection$window_release("registerActivityStackCallback is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider.isMethodRegisterActivityStackCallbackValid.1
            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() throws NoSuchMethodException {
                Method method = SafeActivityEmbeddingComponentProvider.this.getActivityEmbeddingComponentClass().getMethod("registerActivityStackCallback", Executor.class, Consumer.class);
                ReflectionUtils reflectionUtils = ReflectionUtils.INSTANCE;
                method.getClass();
                return Boolean.valueOf(reflectionUtils.isPublic$window_release(method));
            }
        });
    }

    private final boolean isMethodSetAnimationParamsValid() {
        return ReflectionUtils.validateReflection$window_release("SplitAttributes#setAnimationParams is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider.isMethodSetAnimationParamsValid.1
            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() throws NoSuchMethodException {
                Method method = SplitAttributes.Builder.class.getMethod("setAnimationParams", AnimationParams.class);
                ReflectionUtils reflectionUtils = ReflectionUtils.INSTANCE;
                method.getClass();
                return Boolean.valueOf(reflectionUtils.isPublic$window_release(method) && reflectionUtils.doesReturn$window_release(method, SplitAttributes.Builder.class));
            }
        });
    }

    private final boolean isMethodSetDividerAttributesValid() {
        return ReflectionUtils.validateReflection$window_release("SplitAttributes#setDividerAttributes is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider.isMethodSetDividerAttributesValid.1
            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() throws NoSuchMethodException {
                Method method = SplitAttributes.Builder.class.getMethod("setDividerAttributes", androidx.window.extensions.embedding.DividerAttributes.class);
                ReflectionUtils reflectionUtils = ReflectionUtils.INSTANCE;
                method.getClass();
                return Boolean.valueOf(reflectionUtils.isPublic$window_release(method) && reflectionUtils.doesReturn$window_release(method, SplitAttributes.Builder.class));
            }
        });
    }

    private final boolean isMethodSetDraggingToFullscreenAllowedValid() {
        return ReflectionUtils.validateReflection$window_release("DividerAttributes.Builder#setDraggingToFullscreenAllowed is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider.isMethodSetDraggingToFullscreenAllowedValid.1
            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() throws NoSuchMethodException {
                Method method = DividerAttributes.Builder.class.getMethod("setDraggingToFullscreenAllowed", Boolean.TYPE);
                ReflectionUtils reflectionUtils = ReflectionUtils.INSTANCE;
                method.getClass();
                return Boolean.valueOf(reflectionUtils.isPublic$window_release(method) && reflectionUtils.doesReturn$window_release(method, DividerAttributes.Builder.class));
            }
        });
    }

    private final boolean isMethodSetEmbeddedActivityWindowInfoCallbackValid() {
        return ReflectionUtils.validateReflection$window_release("ActivityEmbeddingComponent#setEmbeddedActivityWindowInfoCallback is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider.isMethodSetEmbeddedActivityWindowInfoCallbackValid.1
            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() throws NoSuchMethodException {
                Method method = SafeActivityEmbeddingComponentProvider.this.getActivityEmbeddingComponentClass().getMethod("setEmbeddedActivityWindowInfoCallback", Executor.class, Consumer.class);
                ReflectionUtils reflectionUtils = ReflectionUtils.INSTANCE;
                method.getClass();
                return Boolean.valueOf(reflectionUtils.isPublic$window_release(method));
            }
        });
    }

    private final boolean isMethodSetEmbeddingRulesValid() {
        return ReflectionUtils.validateReflection$window_release("ActivityEmbeddingComponent#setEmbeddingRules is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider.isMethodSetEmbeddingRulesValid.1
            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() throws NoSuchMethodException {
                Method method = SafeActivityEmbeddingComponentProvider.this.getActivityEmbeddingComponentClass().getMethod("setEmbeddingRules", Set.class);
                ReflectionUtils reflectionUtils = ReflectionUtils.INSTANCE;
                method.getClass();
                return Boolean.valueOf(reflectionUtils.isPublic$window_release(method));
            }
        });
    }

    private final boolean isMethodSetSplitInfoCallbackJavaConsumerValid() {
        return ReflectionUtils.validateReflection$window_release("ActivityEmbeddingComponent#setSplitInfoCallback is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider.isMethodSetSplitInfoCallbackJavaConsumerValid.1
            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() throws NoSuchMethodException {
                Class<?> clsConsumerClassOrNull$window_release = SafeActivityEmbeddingComponentProvider.this.consumerAdapter.consumerClassOrNull$window_release();
                if (clsConsumerClassOrNull$window_release == null) {
                    return Boolean.FALSE;
                }
                Method method = SafeActivityEmbeddingComponentProvider.this.getActivityEmbeddingComponentClass().getMethod("setSplitInfoCallback", clsConsumerClassOrNull$window_release);
                ReflectionUtils reflectionUtils = ReflectionUtils.INSTANCE;
                method.getClass();
                return Boolean.valueOf(reflectionUtils.isPublic$window_release(method));
            }
        });
    }

    private final boolean isMethodSetSplitInfoCallbackWindowConsumerValid() {
        return ReflectionUtils.validateReflection$window_release("ActivityEmbeddingComponent#setSplitInfoCallback is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider.isMethodSetSplitInfoCallbackWindowConsumerValid.1
            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() throws NoSuchMethodException {
                Method method = SafeActivityEmbeddingComponentProvider.this.getActivityEmbeddingComponentClass().getMethod("setSplitInfoCallback", Consumer.class);
                ReflectionUtils reflectionUtils = ReflectionUtils.INSTANCE;
                method.getClass();
                return Boolean.valueOf(reflectionUtils.isPublic$window_release(method));
            }
        });
    }

    private final boolean isMethodSplitAttributesCalculatorValid() {
        return ReflectionUtils.validateReflection$window_release("ActivityEmbeddingComponent#setSplitAttributesCalculator is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider.isMethodSplitAttributesCalculatorValid.1
            /* JADX WARN: Removed duplicated region for block: B:7:0x0038  */
            @Override // kotlin.jvm.functions.Function0
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct add '--show-bad-code' argument
            */
            public final java.lang.Boolean invoke() throws java.lang.NoSuchMethodException {
                /*
                    r5 = this;
                    androidx.window.embedding.SafeActivityEmbeddingComponentProvider r0 = androidx.window.embedding.SafeActivityEmbeddingComponentProvider.this
                    java.lang.Class r0 = androidx.window.embedding.SafeActivityEmbeddingComponentProvider.access$getActivityEmbeddingComponentClass(r0)
                    r1 = 1
                    java.lang.Class[] r2 = new java.lang.Class[r1]
                    java.lang.Class<androidx.window.extensions.core.util.function.Function> r3 = androidx.window.extensions.core.util.function.Function.class
                    r4 = 0
                    r2[r4] = r3
                    java.lang.String r3 = "setSplitAttributesCalculator"
                    java.lang.reflect.Method r0 = r0.getMethod(r3, r2)
                    androidx.window.embedding.SafeActivityEmbeddingComponentProvider r5 = androidx.window.embedding.SafeActivityEmbeddingComponentProvider.this
                    java.lang.Class r5 = androidx.window.embedding.SafeActivityEmbeddingComponentProvider.access$getActivityEmbeddingComponentClass(r5)
                    java.lang.Class[] r2 = new java.lang.Class[r4]
                    r2 = 0
                    java.lang.String r3 = "clearSplitAttributesCalculator"
                    java.lang.reflect.Method r5 = r5.getMethod(r3, r2)
                    androidx.window.reflection.ReflectionUtils r2 = androidx.window.reflection.ReflectionUtils.INSTANCE
                    r0.getClass()
                    boolean r0 = r2.isPublic$window_release(r0)
                    if (r0 == 0) goto L38
                    r5.getClass()
                    boolean r5 = r2.isPublic$window_release(r5)
                    if (r5 == 0) goto L38
                    goto L39
                L38:
                    r1 = r4
                L39:
                    java.lang.Boolean r5 = java.lang.Boolean.valueOf(r1)
                    return r5
                */
                throw new UnsupportedOperationException("Method not decompiled: androidx.window.embedding.SafeActivityEmbeddingComponentProvider.C01161.invoke():java.lang.Boolean");
            }
        });
    }

    private final boolean isMethodSplitInfoGetTokenValid() {
        return ReflectionUtils.validateReflection$window_release("SplitInfo#getToken is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider.isMethodSplitInfoGetTokenValid.1
            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() throws NoSuchMethodException {
                boolean z = false;
                Class[] clsArr = new Class[0];
                Method method = androidx.window.extensions.embedding.SplitInfo.class.getMethod("getToken", null);
                ReflectionUtils reflectionUtils = ReflectionUtils.INSTANCE;
                method.getClass();
                if (reflectionUtils.isPublic$window_release(method) && reflectionUtils.doesReturn$window_release(method, IBinder.class)) {
                    z = true;
                }
                return Boolean.valueOf(z);
            }
        });
    }

    private final boolean isMethodUnregisterActivityStackCallbackValid() {
        return ReflectionUtils.validateReflection$window_release("unregisterActivityStackCallback is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider.isMethodUnregisterActivityStackCallbackValid.1
            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() throws NoSuchMethodException {
                Method method = SafeActivityEmbeddingComponentProvider.this.getActivityEmbeddingComponentClass().getMethod("unregisterActivityStackCallback", Consumer.class);
                ReflectionUtils reflectionUtils = ReflectionUtils.INSTANCE;
                method.getClass();
                return Boolean.valueOf(reflectionUtils.isPublic$window_release(method));
            }
        });
    }

    private final boolean isMethodUpdateSplitAttributesValid() {
        return ReflectionUtils.validateReflection$window_release("#updateSplitAttributes is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider.isMethodUpdateSplitAttributesValid.1
            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() throws NoSuchMethodException {
                Method method = SafeActivityEmbeddingComponentProvider.this.getActivityEmbeddingComponentClass().getMethod("updateSplitAttributes", IBinder.class, androidx.window.extensions.embedding.SplitAttributes.class);
                ReflectionUtils reflectionUtils = ReflectionUtils.INSTANCE;
                method.getClass();
                return Boolean.valueOf(reflectionUtils.isPublic$window_release(method));
            }
        });
    }

    private final boolean isMethodUpdateSplitAttributesWithTokenValid() {
        return ReflectionUtils.validateReflection$window_release("updateSplitAttributes is not valid", new Function0() { // from class: androidx.window.embedding.SafeActivityEmbeddingComponentProvider.isMethodUpdateSplitAttributesWithTokenValid.1
            @Override // kotlin.jvm.functions.Function0
            public final Boolean invoke() throws NoSuchMethodException {
                Method method = SafeActivityEmbeddingComponentProvider.this.getActivityEmbeddingComponentClass().getMethod("updateSplitAttributes", SplitInfo.Token.class, androidx.window.extensions.embedding.SplitAttributes.class);
                ReflectionUtils reflectionUtils = ReflectionUtils.INSTANCE;
                method.getClass();
                return Boolean.valueOf(reflectionUtils.isPublic$window_release(method));
            }
        });
    }

    public final ActivityEmbeddingComponent getActivityEmbeddingComponent() {
        if (!canUseActivityEmbeddingComponent()) {
            return null;
        }
        try {
            return this.windowExtensions.getActivityEmbeddingComponent();
        } catch (UnsupportedOperationException unused) {
            return null;
        }
    }

    public final boolean hasValidVendorApiLevel1$window_release() {
        return isMethodSetEmbeddingRulesValid() && isMethodIsActivityEmbeddedValid() && isMethodSetSplitInfoCallbackJavaConsumerValid() && isMethodGetSplitRatioValid() && isMethodGetLayoutDirectionValid() && isClassActivityRuleValid() && isClassActivityRuleBuilderLevel1Valid() && isClassSplitInfoValid() && isClassSplitPairRuleValid() && isClassSplitPairRuleBuilderLevel1Valid() && isClassSplitPlaceholderRuleValid() && isClassSplitPlaceholderRuleBuilderLevel1Valid();
    }

    public final boolean hasValidVendorApiLevel2$window_release() {
        return hasValidVendorApiLevel1$window_release() && isMethodSetSplitInfoCallbackWindowConsumerValid() && isMethodClearSplitInfoCallbackValid() && isMethodSplitAttributesCalculatorValid() && isMethodGetSplitAttributesValid() && isMethodGetFinishPrimaryWithPlaceholderValid() && isMethodGetDefaultSplitAttributesValid() && isClassActivityRuleBuilderLevel2Valid() && isClassEmbeddingRuleValid() && isClassSplitAttributesValid() && isClassSplitAttributesCalculatorParamsValid() && isClassSplitTypeValid() && isClassSplitPairRuleBuilderLevel2Valid() && isClassSplitPlaceholderRuleBuilderLevel2Valid();
    }

    public final boolean hasValidVendorApiLevel3$window_release() {
        return hasValidVendorApiLevel2$window_release() && isMethodInvalidateTopVisibleSplitAttributesValid() && isMethodUpdateSplitAttributesValid() && isMethodSplitInfoGetTokenValid();
    }

    public final boolean hasValidVendorApiLevel5$window_release() {
        return hasValidVendorApiLevel3$window_release() && isActivityStackGetActivityStackTokenValid() && isMethodRegisterActivityStackCallbackValid() && isMethodUnregisterActivityStackCallbackValid() && isMethodPinUnpinTopActivityStackValid() && isMethodUpdateSplitAttributesWithTokenValid() && isMethodGetSplitInfoTokenValid() && isClassAnimationBackgroundValid() && isClassActivityStackTokenValid() && isClassWindowAttributesValid() && isClassSplitInfoTokenValid() && isClassEmbeddingConfigurationBuilderApi5Valid();
    }

    public final boolean hasValidVendorApiLevel6$window_release() {
        return hasValidVendorApiLevel5$window_release() && isMethodGetEmbeddedActivityWindowInfoValid() && isMethodSetEmbeddedActivityWindowInfoCallbackValid() && isMethodClearEmbeddedActivityWindowInfoCallbackValid() && isMethodGetDividerAttributesValid() && isMethodSetDividerAttributesValid() && isClassEmbeddedActivityWindowInfoValid() && isClassDividerAttributesValid() && isClassDividerAttributesBuilderValid();
    }

    public final boolean hasValidVendorApiLevel7$window_release() {
        return hasValidVendorApiLevel6$window_release() && isMethodGetAnimationParamsValid() && isMethodSetAnimationParamsValid() && isMethodIsDraggingToFullscreenAllowedValid() && isMethodSetDraggingToFullscreenAllowedValid() && isClassAnimationParamsValid() && isClassAnimationParamsBuilderValid();
    }

    public final boolean hasValidVendorApiLevel8$window_release() {
        return hasValidVendorApiLevel7$window_release() && isClassEmbeddingConfigurationBuilderApi8Valid();
    }

    public final boolean isActivityEmbeddingComponentAccessible$window_release() {
        return this.safeWindowExtensionsProvider.isWindowExtensionsValid$window_release() && isActivityEmbeddingComponentValid();
    }
}
