package com.motorola.plugin.core.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.os.Parcel;
import java.lang.Thread;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.WrongMethodTypeException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.TypeIntrinsics;

/* JADX INFO: compiled from: HiddenApi.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class HiddenApiKt {
    private static final Lazy makePathsMethod$delegate = LazyKt.lazy(new Function0() { // from class: com.motorola.plugin.core.utils.HiddenApiKt$makePathsMethod$2
        @Override // kotlin.jvm.functions.Function0
        /* JADX INFO: renamed from: invoke */
        public /* bridge */ /* synthetic */ Object mo2224invoke() {
            return Result.m2706boximpl(m2226invoked1pmJ48());
        }

        /* JADX INFO: renamed from: invoke-d1pmJ48, reason: not valid java name */
        public final Object m2226invoked1pmJ48() {
            try {
                Result.Companion companion = Result.Companion;
                return Result.m2707constructorimpl(MethodHandles.lookup().findStatic(Class.forName("android.app.LoadedApk"), "makePaths", MethodType.methodType(Void.TYPE, Class.forName("android.app.ActivityThread"), Boolean.TYPE, ApplicationInfo.class, List.class, List.class)));
            } catch (Throwable th) {
                Result.Companion companion2 = Result.Companion;
                return Result.m2707constructorimpl(ResultKt.createFailure(th));
            }
        }
    });
    private static final Lazy createApplicationContextMethod$delegate = LazyKt.lazy(new Function0() { // from class: com.motorola.plugin.core.utils.HiddenApiKt$createApplicationContextMethod$2
        @Override // kotlin.jvm.functions.Function0
        /* JADX INFO: renamed from: invoke */
        public final MethodHandle mo2224invoke() {
            try {
                return MethodHandles.lookup().findVirtual(Context.class, "createApplicationContext", MethodType.methodType(Context.class, ApplicationInfo.class, Integer.TYPE));
            } catch (IllegalAccessException | NoSuchMethodException unused) {
                return null;
            }
        }
    });
    private static final Lazy getUncaughtExceptionPreHandlerMethod$delegate = LazyKt.lazy(new Function0() { // from class: com.motorola.plugin.core.utils.HiddenApiKt$getUncaughtExceptionPreHandlerMethod$2
        @Override // kotlin.jvm.functions.Function0
        /* JADX INFO: renamed from: invoke */
        public final MethodHandle mo2224invoke() {
            try {
                return MethodHandles.lookup().findStatic(Thread.class, "getUncaughtExceptionPreHandler", MethodType.methodType(Thread.UncaughtExceptionHandler.class));
            } catch (IllegalAccessException | NoSuchMethodException unused) {
                return null;
            }
        }
    });
    private static final Lazy setUncaughtExceptionPreHandlerMethod$delegate = LazyKt.lazy(new Function0() { // from class: com.motorola.plugin.core.utils.HiddenApiKt$setUncaughtExceptionPreHandlerMethod$2
        @Override // kotlin.jvm.functions.Function0
        /* JADX INFO: renamed from: invoke */
        public final MethodHandle mo2224invoke() {
            try {
                return MethodHandles.lookup().findStatic(Thread.class, "setUncaughtExceptionPreHandler", MethodType.methodType((Class<?>) Void.TYPE, (Class<?>) Thread.UncaughtExceptionHandler.class));
            } catch (IllegalAccessException | NoSuchMethodException unused) {
                return null;
            }
        }
    });
    private static final Lazy primaryCpuAbiField$delegate = LazyKt.lazy(new Function0() { // from class: com.motorola.plugin.core.utils.HiddenApiKt$primaryCpuAbiField$2
        @Override // kotlin.jvm.functions.Function0
        /* JADX INFO: renamed from: invoke */
        public final Field mo2224invoke() {
            try {
                Field declaredField = ApplicationInfo.class.getDeclaredField("primaryCpuAbi");
                declaredField.setAccessible(true);
                return declaredField;
            } catch (IllegalAccessException | NoSuchFieldException unused) {
                return null;
            }
        }
    });
    private static final Lazy systemPropertiesGetStringMethod$delegate = LazyKt.lazy(new Function0() { // from class: com.motorola.plugin.core.utils.HiddenApiKt$systemPropertiesGetStringMethod$2
        @Override // kotlin.jvm.functions.Function0
        /* JADX INFO: renamed from: invoke */
        public final MethodHandle mo2224invoke() {
            try {
                return MethodHandles.lookup().findStatic(Class.forName("android.os.SystemProperties"), "get", MethodType.methodType(String.class, String.class, String.class));
            } catch (IllegalAccessException | NoSuchMethodException unused) {
                return null;
            }
        }
    });
    private static final Lazy activityThreadHandler$delegate = LazyKt.lazy(new Function0() { // from class: com.motorola.plugin.core.utils.HiddenApiKt.activityThreadHandler.2
        @Override // kotlin.jvm.functions.Function0
        /* JADX INFO: renamed from: invoke */
        public final Handler mo2224invoke() {
            try {
                Class<?> cls = Class.forName("android.app.ActivityThread");
                Class[] clsArr = new Class[0];
                Object objInvoke = cls.getDeclaredMethod("currentActivityThread", null).invoke(null, null);
                Field declaredField = cls.getDeclaredField("mH");
                declaredField.setAccessible(true);
                Object obj = declaredField.get(objInvoke);
                if (obj != null) {
                    return (Handler) obj;
                }
                throw new NullPointerException("null cannot be cast to non-null type android.os.Handler");
            } catch (IllegalAccessException | NoSuchFieldException | NoSuchMethodException unused) {
                return null;
            }
        }
    });
    private static final Lazy applicationInfoOverlayPaths$delegate = LazyKt.lazy(new Function0() { // from class: com.motorola.plugin.core.utils.HiddenApiKt$applicationInfoOverlayPaths$2
        @Override // kotlin.jvm.functions.Function0
        /* JADX INFO: renamed from: invoke */
        public final Field mo2224invoke() {
            try {
                Field declaredField = ApplicationInfo.class.getDeclaredField("overlayPaths");
                declaredField.setAccessible(true);
                return declaredField;
            } catch (IllegalAccessException | NoSuchFieldException unused) {
                return null;
            }
        }
    });
    private static final Lazy applicationInfoResourceDirs$delegate = LazyKt.lazy(new Function0() { // from class: com.motorola.plugin.core.utils.HiddenApiKt$applicationInfoResourceDirs$2
        @Override // kotlin.jvm.functions.Function0
        /* JADX INFO: renamed from: invoke */
        public final Field mo2224invoke() {
            try {
                Field declaredField = ApplicationInfo.class.getDeclaredField("resourceDirs");
                declaredField.setAccessible(true);
                return declaredField;
            } catch (IllegalAccessException | NoSuchFieldException unused) {
                return null;
            }
        }
    });
    private static final Lazy parcelCreatorsFiled$delegate = LazyKt.lazy(new Function0() { // from class: com.motorola.plugin.core.utils.HiddenApiKt$parcelCreatorsFiled$2
        @Override // kotlin.jvm.functions.Function0
        /* JADX INFO: renamed from: invoke */
        public final Field mo2224invoke() {
            try {
                Field declaredField = Parcel.class.getDeclaredField("mCreators");
                declaredField.setAccessible(true);
                return declaredField;
            } catch (IllegalAccessException | NoSuchFieldException unused) {
                return null;
            }
        }
    });
    private static final Lazy parcelPairedCreatorsFiled$delegate = LazyKt.lazy(new Function0() { // from class: com.motorola.plugin.core.utils.HiddenApiKt$parcelPairedCreatorsFiled$2
        @Override // kotlin.jvm.functions.Function0
        /* JADX INFO: renamed from: invoke */
        public final Field mo2224invoke() {
            try {
                Field declaredField = Parcel.class.getDeclaredField("sPairedCreators");
                declaredField.setAccessible(true);
                return declaredField;
            } catch (IllegalAccessException | NoSuchFieldException unused) {
                return null;
            }
        }
    });

    public static final Handler activityThreadHandler() {
        return getActivityThreadHandler();
    }

    public static final Boolean clearCreatorsRefInParcel(ClassLoader classLoader) {
        classLoader.getClass();
        Field parcelCreatorsFiled = getParcelCreatorsFiled();
        Object obj = parcelCreatorsFiled == null ? null : parcelCreatorsFiled.get(null);
        Map map = TypeIntrinsics.isMutableMap(obj) ? (Map) obj : null;
        if (map == null) {
            return null;
        }
        return Boolean.valueOf(TypeIntrinsics.asMutableMap(map).remove(classLoader) != null);
    }

    public static final Boolean clearPairedCreatorsRefInParcel(ClassLoader classLoader) {
        classLoader.getClass();
        Field parcelPairedCreatorsFiled = getParcelPairedCreatorsFiled();
        Object obj = parcelPairedCreatorsFiled == null ? null : parcelPairedCreatorsFiled.get(null);
        Map map = TypeIntrinsics.isMutableMap(obj) ? (Map) obj : null;
        if (map == null) {
            return null;
        }
        return Boolean.valueOf(TypeIntrinsics.asMutableMap(map).remove(classLoader) != null);
    }

    public static final Context createApplicationContextExt(Context context, ApplicationInfo applicationInfo, int i) throws PackageManager.NameNotFoundException {
        context.getClass();
        applicationInfo.getClass();
        MethodHandle createApplicationContextMethod = getCreateApplicationContextMethod();
        Context context2 = (Context) (createApplicationContextMethod == null ? null : invokeSafely(createApplicationContextMethod, context, applicationInfo, Integer.valueOf(i)));
        if (context2 != null) {
            return context2;
        }
        throw new PackageManager.NameNotFoundException();
    }

    private static final Handler getActivityThreadHandler() {
        return (Handler) activityThreadHandler$delegate.getValue();
    }

    private static final Field getApplicationInfoOverlayPaths() {
        return (Field) applicationInfoOverlayPaths$delegate.getValue();
    }

    private static final Field getApplicationInfoResourceDirs() {
        return (Field) applicationInfoResourceDirs$delegate.getValue();
    }

    private static final MethodHandle getCreateApplicationContextMethod() {
        return (MethodHandle) createApplicationContextMethod$delegate.getValue();
    }

    private static final MethodHandle getGetUncaughtExceptionPreHandlerMethod() {
        return (MethodHandle) getUncaughtExceptionPreHandlerMethod$delegate.getValue();
    }

    private static final Object getMakePathsMethod() {
        return ((Result) makePathsMethod$delegate.getValue()).m2714unboximpl();
    }

    public static final List getOverlayPathsExt(ApplicationInfo applicationInfo) {
        applicationInfo.getClass();
        Field applicationInfoOverlayPaths = getApplicationInfoOverlayPaths();
        Object obj = applicationInfoOverlayPaths == null ? null : applicationInfoOverlayPaths.get(applicationInfo);
        String[] strArr = obj instanceof String[] ? (String[]) obj : null;
        if (strArr == null) {
            return null;
        }
        return ArraysKt.toList(strArr);
    }

    private static final Field getParcelCreatorsFiled() {
        return (Field) parcelCreatorsFiled$delegate.getValue();
    }

    private static final Field getParcelPairedCreatorsFiled() {
        return (Field) parcelPairedCreatorsFiled$delegate.getValue();
    }

    private static final Field getPrimaryCpuAbiField() {
        return (Field) primaryCpuAbiField$delegate.getValue();
    }

    public static final List getResourceDirsExt(ApplicationInfo applicationInfo) {
        applicationInfo.getClass();
        Field applicationInfoResourceDirs = getApplicationInfoResourceDirs();
        Object obj = applicationInfoResourceDirs == null ? null : applicationInfoResourceDirs.get(applicationInfo);
        String[] strArr = obj instanceof String[] ? (String[]) obj : null;
        if (strArr == null) {
            return null;
        }
        return ArraysKt.toList(strArr);
    }

    private static final MethodHandle getSetUncaughtExceptionPreHandlerMethod() {
        return (MethodHandle) setUncaughtExceptionPreHandlerMethod$delegate.getValue();
    }

    public static final String getSysProp(String str, String str2) {
        str.getClass();
        MethodHandle systemPropertiesGetStringMethod = getSystemPropertiesGetStringMethod();
        Object objInvokeSafely = systemPropertiesGetStringMethod == null ? null : invokeSafely(systemPropertiesGetStringMethod, str, str2);
        if (objInvokeSafely instanceof String) {
            return (String) objInvokeSafely;
        }
        return null;
    }

    public static /* synthetic */ String getSysProp$default(String str, String str2, int i, Object obj) {
        if ((i & 1) != 0) {
            str2 = null;
        }
        return getSysProp(str, str2);
    }

    private static final MethodHandle getSystemPropertiesGetStringMethod() {
        return (MethodHandle) systemPropertiesGetStringMethod$delegate.getValue();
    }

    public static final Thread.UncaughtExceptionHandler getUncaughtExceptionPreHandlerExt() {
        MethodHandle getUncaughtExceptionPreHandlerMethod = getGetUncaughtExceptionPreHandlerMethod();
        return (Thread.UncaughtExceptionHandler) (getUncaughtExceptionPreHandlerMethod == null ? null : invokeSafely(getUncaughtExceptionPreHandlerMethod, new Object[0]));
    }

    private static final Object invokeSafely(MethodHandle methodHandle, Object... objArr) {
        try {
            return objArr.length == 0 ? (Object) methodHandle.invoke(new Object[0]) : methodHandle.invokeWithArguments(Arrays.copyOf(objArr, objArr.length));
        } catch (ClassCastException | WrongMethodTypeException | InvocationTargetException unused) {
            return null;
        }
    }

    public static final void makePaths(ApplicationInfo applicationInfo, boolean z, List list, List list2) {
        String property;
        applicationInfo.getClass();
        list.getClass();
        list.clear();
        String str = applicationInfo.sourceDir;
        str.getClass();
        list.add(str);
        String[] strArr = applicationInfo.splitSourceDirs;
        if (strArr != null) {
            CollectionsKt.addAll(list, strArr);
        }
        if (list2 != null) {
            list2.clear();
        }
        if (list2 != null) {
            String str2 = applicationInfo.nativeLibraryDir;
            str2.getClass();
            list2.add(str2);
        }
        Field primaryCpuAbiField = getPrimaryCpuAbiField();
        Object obj = primaryCpuAbiField == null ? null : primaryCpuAbiField.get(applicationInfo);
        if (obj != null) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                String str3 = (String) it.next();
                if (list2 != null) {
                    list2.add(str3 + "!/lib/" + obj);
                }
            }
        }
        if (!z || (property = System.getProperty("java.library.path")) == null || list2 == null) {
            return;
        }
        list2.add(property);
    }

    public static final boolean sendMessage2ActivityThread(Message message) {
        message.getClass();
        Handler activityThreadHandler = getActivityThreadHandler();
        if (activityThreadHandler == null) {
            return false;
        }
        return activityThreadHandler.sendMessage(message);
    }

    public static final void setOverlayPathsExt(ApplicationInfo applicationInfo, List list) throws IllegalAccessException {
        String[] strArr;
        applicationInfo.getClass();
        Field applicationInfoOverlayPaths = getApplicationInfoOverlayPaths();
        if (applicationInfoOverlayPaths == null) {
            return;
        }
        if (list == null) {
            strArr = null;
        } else {
            Object[] array = list.toArray(new String[0]);
            if (array == null) {
                throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T>");
            }
            strArr = (String[]) array;
        }
        applicationInfoOverlayPaths.set(applicationInfo, strArr);
    }

    public static final void setResourceDirsExt(ApplicationInfo applicationInfo, List list) throws IllegalAccessException {
        String[] strArr;
        applicationInfo.getClass();
        Field applicationInfoResourceDirs = getApplicationInfoResourceDirs();
        if (applicationInfoResourceDirs == null) {
            return;
        }
        if (list == null) {
            strArr = null;
        } else {
            Object[] array = list.toArray(new String[0]);
            if (array == null) {
                throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T>");
            }
            strArr = (String[]) array;
        }
        applicationInfoResourceDirs.set(applicationInfo, strArr);
    }

    public static final void setUncaughtExceptionPreHandlerExt(Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
        uncaughtExceptionHandler.getClass();
        MethodHandle setUncaughtExceptionPreHandlerMethod = getSetUncaughtExceptionPreHandlerMethod();
        if (setUncaughtExceptionPreHandlerMethod == null) {
            return;
        }
        invokeSafely(setUncaughtExceptionPreHandlerMethod, uncaughtExceptionHandler);
    }
}
