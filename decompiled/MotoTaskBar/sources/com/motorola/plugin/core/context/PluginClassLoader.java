package com.motorola.plugin.core.context;

import android.content.pm.ApplicationInfo;
import android.text.TextUtils;
import com.motorola.plugin.core.Level;
import com.motorola.plugin.core.PluginConfigKt;
import com.motorola.plugin.core.utils.HiddenApiKt;
import dalvik.system.PathClassLoader;
import java.io.File;
import java.util.ArrayList;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: PluginClassLoader.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class PluginClassLoader extends PathClassLoader {
    private static final boolean DEBUG = false;
    private static final boolean DEBUG_LOADING_OR_FAILED_LOAD_LOG = false;
    public static final Factory Factory = new Factory(null);

    /* JADX INFO: compiled from: PluginClassLoader.kt */
    public final class Factory {
        private Factory() {
        }

        public /* synthetic */ Factory(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final PluginClassLoader create(final ApplicationInfo applicationInfo, ClassLoader classLoader) {
            Object objM2707constructorimpl;
            applicationInfo.getClass();
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            try {
                Result.Companion companion = Result.Companion;
                HiddenApiKt.makePaths(applicationInfo, true, arrayList, arrayList2);
                objM2707constructorimpl = Result.m2707constructorimpl(Unit.INSTANCE);
            } catch (Throwable th) {
                Result.Companion companion2 = Result.Companion;
                objM2707constructorimpl = Result.m2707constructorimpl(ResultKt.createFailure(th));
            }
            final Throwable thM2709exceptionOrNullimpl = Result.m2709exceptionOrNullimpl(objM2707constructorimpl);
            if (thM2709exceptionOrNullimpl != null) {
                PluginConfigKt.trace$default(null, Level.ERROR, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.context.PluginClassLoader$Factory$create$result$2$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* JADX INFO: renamed from: invoke */
                    public final Object mo2224invoke() {
                        return "create plugin class loader failed for [" + ((Object) applicationInfo.packageName) + "] due to " + thM2709exceptionOrNullimpl;
                    }
                }, 61, null);
            }
            if (Result.m2711isFailureimpl(objM2707constructorimpl)) {
                return null;
            }
            String str = File.pathSeparator;
            String strJoin = TextUtils.join(str, arrayList);
            String strJoin2 = TextUtils.join(str, arrayList2);
            strJoin.getClass();
            return create(strJoin, strJoin2, classLoader);
        }

        public final PluginClassLoader create(String str, String str2, ClassLoader classLoader) {
            str.getClass();
            return new PluginClassLoader(str, str2, classLoader);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PluginClassLoader(String str, String str2, ClassLoader classLoader) {
        super(str, str2, classLoader);
        str.getClass();
    }

    @Override // dalvik.system.BaseDexClassLoader, java.lang.ClassLoader
    protected Class findClass(final String str) throws ClassNotFoundException {
        str.getClass();
        try {
            PluginConfigKt.trace$default(PluginConfigKt.TAG_CLASS_FILTER, Level.VERBOSE, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.context.PluginClassLoader.findClass.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                /* JADX INFO: renamed from: invoke */
                public final Object mo2224invoke() {
                    return Intrinsics.stringPlus("Plugin loading -> ", str);
                }
            }, 44, null);
            Class<?> clsFindClass = super.findClass(str);
            PluginConfigKt.trace$default(PluginConfigKt.TAG_CLASS_FILTER, Level.INFO, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.context.PluginClassLoader.findClass.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                /* JADX INFO: renamed from: invoke */
                public final Object mo2224invoke() {
                    return Intrinsics.stringPlus("Plugin loaded -> ", str);
                }
            }, 44, null);
            clsFindClass.getClass();
            return clsFindClass;
        } catch (ClassNotFoundException e) {
            PluginConfigKt.trace$default(PluginConfigKt.TAG_CLASS_FILTER, Level.WARN, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.context.PluginClassLoader.findClass.3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                /* JADX INFO: renamed from: invoke */
                public final Object mo2224invoke() {
                    return Intrinsics.stringPlus("Plugin load failed -> ", str);
                }
            }, 44, null);
            throw e;
        }
    }

    @Override // java.lang.ClassLoader
    protected Class loadClass(final String str, boolean z) {
        str.getClass();
        PluginConfigKt.trace$default(PluginConfigKt.TAG_CLASS_FILTER, Level.VERBOSE, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.context.PluginClassLoader.loadClass.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return Intrinsics.stringPlus("Start loading class: ", str);
            }
        }, 44, null);
        Class clsLoadClass = super.loadClass(str, z);
        clsLoadClass.getClass();
        return clsLoadClass;
    }
}
