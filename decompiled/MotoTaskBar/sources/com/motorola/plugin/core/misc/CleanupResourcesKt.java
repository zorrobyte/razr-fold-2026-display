package com.motorola.plugin.core.misc;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.motorola.plugin.core.ExtensionsKt;
import com.motorola.plugin.core.Level;
import com.motorola.plugin.core.PluginConfigKt;
import com.motorola.plugin.core.context.PluginPackage;
import com.motorola.plugin.core.discovery.PluginInfo;
import com.motorola.plugin.core.utils.HiddenApiKt;
import java.io.Closeable;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: CleanupResources.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class CleanupResourcesKt {
    private static final void cleanupAndroidResources(final PluginPackage pluginPackage) {
        Object objM2707constructorimpl;
        try {
            Result.Companion companion = Result.Companion;
            Message messageObtain = Message.obtain();
            messageObtain.what = 133;
            messageObtain.arg1 = 0;
            messageObtain.obj = new String[]{pluginPackage.getPluginId().getId()};
            ExtensionsKt.sendToActivityThread(messageObtain);
            Message messageObtain2 = Message.obtain((Handler) null, new Runnable() { // from class: com.motorola.plugin.core.misc.CleanupResourcesKt$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    CleanupResourcesKt.m2219cleanupAndroidResources$lambda27$lambda26(pluginPackage);
                }
            });
            messageObtain2.getClass();
            objM2707constructorimpl = Result.m2707constructorimpl(Boolean.valueOf(ExtensionsKt.sendToActivityThread(messageObtain2)));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            objM2707constructorimpl = Result.m2707constructorimpl(ResultKt.createFailure(th));
        }
        Throwable thM2709exceptionOrNullimpl = Result.m2709exceptionOrNullimpl(objM2707constructorimpl);
        if (thM2709exceptionOrNullimpl != null) {
            PluginConfigKt.trace$default(PluginConfigKt.TAG_CLASS_FILTER, Level.ERROR, false, thM2709exceptionOrNullimpl, false, null, new Function0() { // from class: com.motorola.plugin.core.misc.CleanupResourcesKt$cleanupAndroidResources$2$1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                /* JADX INFO: renamed from: invoke */
                public final Object mo2224invoke() {
                    return Intrinsics.stringPlus("failed to cleanup resources for ", pluginPackage);
                }
            }, 52, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: cleanupAndroidResources$lambda-27$lambda-26, reason: not valid java name */
    public static final void m2219cleanupAndroidResources$lambda27$lambda26(final PluginPackage pluginPackage) {
        pluginPackage.getClass();
        PluginConfigKt.trace$default(PluginConfigKt.TAG_CLASS_FILTER, Level.VERBOSE, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.misc.CleanupResourcesKt$cleanupAndroidResources$1$2$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return Intrinsics.stringPlus("cleanup resources success for ", pluginPackage);
            }
        }, 60, null);
    }

    private static final void cleanupAndroidX(ClassLoader classLoader) {
        Object objM2707constructorimpl;
        Object obj;
        try {
            Result.Companion companion = Result.Companion;
            Field declaredField = classLoader.loadClass("androidx.recyclerview.widget.GapWorker").getDeclaredField("sGapWorker");
            declaredField.setAccessible(true);
            obj = declaredField.get(null);
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            objM2707constructorimpl = Result.m2707constructorimpl(ResultKt.createFailure(th));
        }
        if (obj == null) {
            throw new NullPointerException("null cannot be cast to non-null type java.lang.ThreadLocal<*>");
        }
        ((ThreadLocal) obj).remove();
        PluginConfigKt.trace$default(PluginConfigKt.TAG_CLASS_FILTER, Level.VERBOSE, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.misc.CleanupResourcesKt$cleanupAndroidX$1$3
            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return "cleanup gap worker";
            }
        }, 60, null);
        objM2707constructorimpl = Result.m2707constructorimpl(Unit.INSTANCE);
        Throwable thM2709exceptionOrNullimpl = Result.m2709exceptionOrNullimpl(objM2707constructorimpl);
        if (thM2709exceptionOrNullimpl == null || (thM2709exceptionOrNullimpl instanceof ClassNotFoundException)) {
            return;
        }
        PluginConfigKt.trace$default(PluginConfigKt.TAG_CLASS_FILTER, Level.WARN, false, thM2709exceptionOrNullimpl, false, null, new Function0() { // from class: com.motorola.plugin.core.misc.CleanupResourcesKt$cleanupAndroidX$2$1
            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return "failed to cleanup gap worker";
            }
        }, 52, null);
    }

    private static final void cleanupKotlinX(ClassLoader classLoader) {
        Object objM2707constructorimpl;
        Object objM2707constructorimpl2;
        Object objM2707constructorimpl3;
        try {
            Result.Companion companion = Result.Companion;
            Class[] clsArr = new Class[0];
            Object objInvoke = classLoader.loadClass("kotlinx.coroutines.Dispatchers").getDeclaredMethod("getDefault", null).invoke(null, null);
            Class[] clsArr2 = new Class[0];
            Object objInvoke2 = objInvoke.getClass().getMethod("getExecutor", null).invoke(objInvoke, null);
            if (objInvoke2 instanceof Closeable) {
                ((Closeable) objInvoke2).close();
            }
            PluginConfigKt.trace$default(PluginConfigKt.TAG_CLASS_FILTER, Level.VERBOSE, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.misc.CleanupResourcesKt$cleanupKotlinX$1$1
                @Override // kotlin.jvm.functions.Function0
                /* JADX INFO: renamed from: invoke */
                public final Object mo2224invoke() {
                    return "cleanup dispatchers";
                }
            }, 60, null);
            objM2707constructorimpl = Result.m2707constructorimpl(Unit.INSTANCE);
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            objM2707constructorimpl = Result.m2707constructorimpl(ResultKt.createFailure(th));
        }
        Throwable thM2709exceptionOrNullimpl = Result.m2709exceptionOrNullimpl(objM2707constructorimpl);
        if (thM2709exceptionOrNullimpl != null && !(thM2709exceptionOrNullimpl instanceof ClassNotFoundException)) {
            PluginConfigKt.trace$default(PluginConfigKt.TAG_CLASS_FILTER, Level.WARN, false, thM2709exceptionOrNullimpl, false, null, new Function0() { // from class: com.motorola.plugin.core.misc.CleanupResourcesKt$cleanupKotlinX$2$1
                @Override // kotlin.jvm.functions.Function0
                /* JADX INFO: renamed from: invoke */
                public final Object mo2224invoke() {
                    return "failed to cleanup dispatchers";
                }
            }, 52, null);
        }
        try {
            Class<?> clsLoadClass = classLoader.loadClass("kotlinx.coroutines.ThreadLocalEventLoop");
            Class[] clsArr3 = new Class[0];
            Object objInvoke3 = clsLoadClass.getDeclaredMethod("getEventLoop$kotlinx_coroutines_core", null).invoke(clsLoadClass.getDeclaredField("INSTANCE").get(null), null);
            Class[] clsArr4 = new Class[0];
            Method declaredMethod = objInvoke3.getClass().getSuperclass().getDeclaredMethod("shutdown", null);
            declaredMethod.setAccessible(true);
            declaredMethod.invoke(objInvoke3, null);
            PluginConfigKt.trace$default(PluginConfigKt.TAG_CLASS_FILTER, Level.VERBOSE, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.misc.CleanupResourcesKt$cleanupKotlinX$3$2
                @Override // kotlin.jvm.functions.Function0
                /* JADX INFO: renamed from: invoke */
                public final Object mo2224invoke() {
                    return "cleanup event loop";
                }
            }, 60, null);
            objM2707constructorimpl2 = Result.m2707constructorimpl(Unit.INSTANCE);
        } catch (Throwable th2) {
            Result.Companion companion3 = Result.Companion;
            objM2707constructorimpl2 = Result.m2707constructorimpl(ResultKt.createFailure(th2));
        }
        Throwable thM2709exceptionOrNullimpl2 = Result.m2709exceptionOrNullimpl(objM2707constructorimpl2);
        if (thM2709exceptionOrNullimpl2 != null && !(thM2709exceptionOrNullimpl2 instanceof ClassNotFoundException)) {
            PluginConfigKt.trace$default(PluginConfigKt.TAG_CLASS_FILTER, Level.WARN, false, thM2709exceptionOrNullimpl2, false, null, new Function0() { // from class: com.motorola.plugin.core.misc.CleanupResourcesKt$cleanupKotlinX$4$1
                @Override // kotlin.jvm.functions.Function0
                /* JADX INFO: renamed from: invoke */
                public final Object mo2224invoke() {
                    return "failed to cleanup event loop";
                }
            }, 52, null);
        }
        try {
            Class<?> clsLoadClass2 = classLoader.loadClass("kotlinx.coroutines.DefaultExecutor");
            clsLoadClass2.getDeclaredMethod("shutdown", Long.TYPE).invoke(clsLoadClass2.getDeclaredField("INSTANCE").get(null), 1000L);
            PluginConfigKt.trace$default(PluginConfigKt.TAG_CLASS_FILTER, Level.VERBOSE, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.misc.CleanupResourcesKt$cleanupKotlinX$5$1
                @Override // kotlin.jvm.functions.Function0
                /* JADX INFO: renamed from: invoke */
                public final Object mo2224invoke() {
                    return "cleanup defaultExecutor";
                }
            }, 60, null);
            objM2707constructorimpl3 = Result.m2707constructorimpl(Unit.INSTANCE);
        } catch (Throwable th3) {
            Result.Companion companion4 = Result.Companion;
            objM2707constructorimpl3 = Result.m2707constructorimpl(ResultKt.createFailure(th3));
        }
        Throwable thM2709exceptionOrNullimpl3 = Result.m2709exceptionOrNullimpl(objM2707constructorimpl3);
        if (thM2709exceptionOrNullimpl3 == null || (thM2709exceptionOrNullimpl3 instanceof ClassNotFoundException)) {
            return;
        }
        PluginConfigKt.trace$default(PluginConfigKt.TAG_CLASS_FILTER, Level.WARN, false, thM2709exceptionOrNullimpl3, false, null, new Function0() { // from class: com.motorola.plugin.core.misc.CleanupResourcesKt$cleanupKotlinX$6$1
            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return "failed to cleanup defaultExecutor";
            }
        }, 52, null);
    }

    private static final void cleanupParcel(ClassLoader classLoader) {
        Boolean boolClearCreatorsRefInParcel = HiddenApiKt.clearCreatorsRefInParcel(classLoader);
        if (boolClearCreatorsRefInParcel == null) {
            PluginConfigKt.trace$default(PluginConfigKt.TAG_CLASS_FILTER, Level.VERBOSE, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.misc.CleanupResourcesKt.cleanupParcel.1
                @Override // kotlin.jvm.functions.Function0
                /* JADX INFO: renamed from: invoke */
                public final Object mo2224invoke() {
                    return "failed to cleanup parcel";
                }
            }, 60, null);
        } else if (boolClearCreatorsRefInParcel.booleanValue()) {
            PluginConfigKt.trace$default(PluginConfigKt.TAG_CLASS_FILTER, Level.VERBOSE, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.misc.CleanupResourcesKt.cleanupParcel.2
                @Override // kotlin.jvm.functions.Function0
                /* JADX INFO: renamed from: invoke */
                public final Object mo2224invoke() {
                    return "cleanup parcel";
                }
            }, 60, null);
        }
        Boolean boolClearPairedCreatorsRefInParcel = HiddenApiKt.clearPairedCreatorsRefInParcel(classLoader);
        if (boolClearPairedCreatorsRefInParcel == null) {
            PluginConfigKt.trace$default(PluginConfigKt.TAG_CLASS_FILTER, Level.VERBOSE, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.misc.CleanupResourcesKt.cleanupParcel.3
                @Override // kotlin.jvm.functions.Function0
                /* JADX INFO: renamed from: invoke */
                public final Object mo2224invoke() {
                    return "failed to cleanup parcel2";
                }
            }, 60, null);
        } else if (boolClearPairedCreatorsRefInParcel.booleanValue()) {
            PluginConfigKt.trace$default(PluginConfigKt.TAG_CLASS_FILTER, Level.VERBOSE, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.misc.CleanupResourcesKt.cleanupParcel.4
                @Override // kotlin.jvm.functions.Function0
                /* JADX INFO: renamed from: invoke */
                public final Object mo2224invoke() {
                    return "cleanup parcel2";
                }
            }, 60, null);
        }
    }

    public static final void cleanupPluginInfo(final PluginInfo pluginInfo) {
        pluginInfo.getClass();
        cleanupTemporaryResources(pluginInfo.getPluginPackage(), pluginInfo.getClassLoader());
        final long jCurrentTimeMillis = System.currentTimeMillis();
        Level level = Level.VERBOSE;
        PluginConfigKt.trace$default(PluginConfigKt.TAG_CLASS_FILTER, level, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.misc.CleanupResourcesKt$cleanupPluginInfo$1$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return Intrinsics.stringPlus("cleanup plugin info for ", pluginInfo.getPluginPackage());
            }
        }, 60, null);
        cleanupUnRecoverableResources(pluginInfo.getClassLoader());
        cleanupAndroidResources(pluginInfo.getPluginPackage());
        PluginConfigKt.trace$default(PluginConfigKt.TAG_CLASS_FILTER, level, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.misc.CleanupResourcesKt$cleanupPluginInfo$1$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return "cleanup plugin info done, time cost " + (System.currentTimeMillis() - jCurrentTimeMillis) + " ms";
            }
        }, 60, null);
    }

    public static final void cleanupTemporaryResources(final PluginPackage pluginPackage, ClassLoader classLoader) {
        pluginPackage.getClass();
        classLoader.getClass();
        final long jCurrentTimeMillis = System.currentTimeMillis();
        Level level = Level.VERBOSE;
        PluginConfigKt.trace$default(PluginConfigKt.TAG_CLASS_FILTER, level, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.misc.CleanupResourcesKt$cleanupTemporaryResources$1$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return Intrinsics.stringPlus("cleanup temporary resources for ", pluginPackage);
            }
        }, 60, null);
        cleanupParcel(classLoader);
        cleanupAndroidX(classLoader);
        cleanupThreadLocals(classLoader);
        PluginConfigKt.trace$default(PluginConfigKt.TAG_CLASS_FILTER, level, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.misc.CleanupResourcesKt$cleanupTemporaryResources$1$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return "cleanup temporary resources done, time cost " + (System.currentTimeMillis() - jCurrentTimeMillis) + " ms";
            }
        }, 60, null);
    }

    private static final void cleanupThreadLocals(final ClassLoader classLoader) {
        Object objM2707constructorimpl;
        Field declaredField;
        try {
            Result.Companion companion = Result.Companion;
            ThreadGroup threadGroup = Looper.getMainLooper().getThread().getThreadGroup();
            Unit unit = null;
            if (threadGroup != null) {
                int iActiveCount = threadGroup.activeCount();
                Thread[] threadArr = new Thread[iActiveCount];
                threadGroup.enumerate(threadArr, true);
                ArrayList arrayList = new ArrayList();
                for (int i = 0; i < iActiveCount; i++) {
                    Thread thread = threadArr[i];
                    if (thread != null) {
                        arrayList.add(thread);
                    }
                }
                int size = arrayList.size();
                int i2 = 0;
                while (i2 < size) {
                    Object obj = arrayList.get(i2);
                    i2++;
                    final Thread thread2 = (Thread) obj;
                    Field fieldFindDeclaredFieldRecursively$default = ExtensionsKt.findDeclaredFieldRecursively$default(thread2.getClass(), "threadLocals", false, 2, null);
                    if (fieldFindDeclaredFieldRecursively$default != null) {
                        fieldFindDeclaredFieldRecursively$default.setAccessible(true);
                        Object obj2 = fieldFindDeclaredFieldRecursively$default.get(thread2);
                        if (obj2 != null) {
                            Field declaredField2 = obj2.getClass().getDeclaredField("table");
                            declaredField2.setAccessible(true);
                            WeakReference[] weakReferenceArr = (WeakReference[]) declaredField2.get(obj2);
                            if (weakReferenceArr != null) {
                                for (WeakReference weakReference : weakReferenceArr) {
                                    if (weakReference == null || (declaredField = weakReference.getClass().getDeclaredField("value")) == null) {
                                        declaredField = null;
                                    } else {
                                        declaredField.setAccessible(true);
                                    }
                                    final Object obj3 = declaredField == null ? null : declaredField.get(weakReference);
                                    if (Intrinsics.areEqual(obj3 == null ? null : obj3.getClass().getClassLoader(), classLoader)) {
                                        PluginConfigKt.trace$default(PluginConfigKt.TAG_CLASS_FILTER, Level.VERBOSE, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.misc.CleanupResourcesKt$cleanupThreadLocals$1$1$2$2$1$1
                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                            {
                                                super(0);
                                            }

                                            @Override // kotlin.jvm.functions.Function0
                                            /* JADX INFO: renamed from: invoke */
                                            public final Object mo2224invoke() {
                                                return "found leaked class [" + obj3 + "] in thread [" + thread2 + "] with loader " + classLoader;
                                            }
                                        }, 60, null);
                                        declaredField.set(weakReference, null);
                                        ThreadLocal threadLocal = (ThreadLocal) weakReference.get();
                                        if (threadLocal != null) {
                                            Method declaredMethod = obj2.getClass().getDeclaredMethod("remove", ThreadLocal.class);
                                            declaredMethod.setAccessible(true);
                                            declaredMethod.invoke(obj2, threadLocal);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                unit = Unit.INSTANCE;
            }
            objM2707constructorimpl = Result.m2707constructorimpl(unit);
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            objM2707constructorimpl = Result.m2707constructorimpl(ResultKt.createFailure(th));
        }
        Throwable thM2709exceptionOrNullimpl = Result.m2709exceptionOrNullimpl(objM2707constructorimpl);
        if (thM2709exceptionOrNullimpl != null) {
            PluginConfigKt.trace$default(PluginConfigKt.TAG_CLASS_FILTER, Level.WARN, false, thM2709exceptionOrNullimpl, false, null, new Function0() { // from class: com.motorola.plugin.core.misc.CleanupResourcesKt$cleanupThreadLocals$2$1
                @Override // kotlin.jvm.functions.Function0
                /* JADX INFO: renamed from: invoke */
                public final Object mo2224invoke() {
                    return "failed to cleanup thread";
                }
            }, 52, null);
        }
    }

    private static final void cleanupUnRecoverableResources(ClassLoader classLoader) {
        cleanupKotlinX(classLoader);
    }
}
