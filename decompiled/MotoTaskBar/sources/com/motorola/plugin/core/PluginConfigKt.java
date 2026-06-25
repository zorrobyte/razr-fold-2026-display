package com.motorola.plugin.core;

import com.motorola.plugin.core.utils.ThrowableUtilKt;
import java.util.Map;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: PluginConfig.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class PluginConfigKt {
    private static final boolean DEBUG;
    private static boolean DISABLE_PLUGIN_WHILE_CRASH = false;
    private static final Level FALLBACK_TAG_LEVEL;
    public static final String TAG_CHANNEL = "Channel.Common";
    private static final String TAG_CHANNEL_PREFIX = "Channel";
    public static final String TAG_CLASS_FILTER = "ClassFilter";
    public static final String TAG_DL_CHANNEL = "Channel.DL";
    public static final String TAG_LOCAL_CHANNEL = "Channel.Local";
    public static final String TAG_PLUGIN = "Plugin";
    public static final String TAG_PLUGIN_DISCOVERY = "PluginDiscovery";
    public static final String TAG_PLUGIN_DISCOVERY_LOCAL = "PluginDiscovery.Local";
    public static final String TAG_PLUGIN_INSTANCE_CONTAINER = "PluginContainer";
    public static final String TAG_PLUGIN_MANAGER = "PluginManager";
    public static final String TAG_REMOTE_CHANNEL = "Channel.Remote";
    private static Function3 defaultLogPrinter;
    private static Function2 defaultLogValidator;
    private static final Map minimLogLevelMap;

    /* JADX WARN: Removed duplicated region for block: B:6:0x001b  */
    static {
        /*
            java.lang.String r0 = android.os.Build.TYPE
            java.lang.String r1 = "user"
            boolean r0 = kotlin.jvm.internal.Intrinsics.areEqual(r0, r1)
            r1 = 1
            if (r0 == 0) goto L1b
            java.lang.String r0 = android.os.Build.TAGS
            r0.getClass()
            r2 = 2
            r3 = 0
            java.lang.String r4 = "intcfg"
            r5 = 0
            boolean r0 = kotlin.text.StringsKt.contains$default(r0, r4, r5, r2, r3)
            if (r0 == 0) goto L1c
        L1b:
            r5 = r1
        L1c:
            com.motorola.plugin.core.PluginConfigKt.DEBUG = r5
            com.motorola.plugin.core.PluginConfigKt.DISABLE_PLUGIN_WHILE_CRASH = r1
            com.motorola.plugin.core.Level r0 = com.motorola.plugin.core.Level.VERBOSE
            boolean r1 = getDEBUG()
            if (r1 == 0) goto L2a
            r1 = r0
            goto L2c
        L2a:
            com.motorola.plugin.core.Level r1 = com.motorola.plugin.core.Level.WARN
        L2c:
            java.lang.String r2 = "Plugin"
            kotlin.Pair r6 = kotlin.TuplesKt.to(r2, r1)
            boolean r1 = getDEBUG()
            if (r1 == 0) goto L3a
            r1 = r0
            goto L3c
        L3a:
            com.motorola.plugin.core.Level r1 = com.motorola.plugin.core.Level.INFO
        L3c:
            java.lang.String r2 = "PluginManager"
            kotlin.Pair r7 = kotlin.TuplesKt.to(r2, r1)
            boolean r1 = getDEBUG()
            if (r1 == 0) goto L4a
            r1 = r0
            goto L4c
        L4a:
            com.motorola.plugin.core.Level r1 = com.motorola.plugin.core.Level.INFO
        L4c:
            java.lang.String r2 = "PluginContainer"
            kotlin.Pair r8 = kotlin.TuplesKt.to(r2, r1)
            boolean r1 = getDEBUG()
            if (r1 == 0) goto L5a
            r1 = r0
            goto L5c
        L5a:
            com.motorola.plugin.core.Level r1 = com.motorola.plugin.core.Level.INFO
        L5c:
            java.lang.String r2 = "PluginDiscovery"
            kotlin.Pair r9 = kotlin.TuplesKt.to(r2, r1)
            boolean r1 = getDEBUG()
            if (r1 == 0) goto L6a
            r1 = r0
            goto L6c
        L6a:
            com.motorola.plugin.core.Level r1 = com.motorola.plugin.core.Level.ERROR
        L6c:
            java.lang.String r2 = "ClassFilter"
            kotlin.Pair r10 = kotlin.TuplesKt.to(r2, r1)
            boolean r1 = getDEBUG()
            if (r1 == 0) goto L7a
            r1 = r0
            goto L7c
        L7a:
            com.motorola.plugin.core.Level r1 = com.motorola.plugin.core.Level.INFO
        L7c:
            java.lang.String r2 = "Channel.Common"
            kotlin.Pair r11 = kotlin.TuplesKt.to(r2, r1)
            boolean r1 = getDEBUG()
            if (r1 == 0) goto L8a
            r1 = r0
            goto L8c
        L8a:
            com.motorola.plugin.core.Level r1 = com.motorola.plugin.core.Level.INFO
        L8c:
            java.lang.String r2 = "Channel.Remote"
            kotlin.Pair r12 = kotlin.TuplesKt.to(r2, r1)
            boolean r1 = getDEBUG()
            if (r1 == 0) goto L9a
            r1 = r0
            goto L9c
        L9a:
            com.motorola.plugin.core.Level r1 = com.motorola.plugin.core.Level.INFO
        L9c:
            java.lang.String r2 = "Channel.Local"
            kotlin.Pair r13 = kotlin.TuplesKt.to(r2, r1)
            kotlin.Pair[] r1 = new kotlin.Pair[]{r6, r7, r8, r9, r10, r11, r12, r13}
            java.util.Map r1 = kotlin.collections.MapsKt.mapOf(r1)
            com.motorola.plugin.core.PluginConfigKt.minimLogLevelMap = r1
            if (r5 == 0) goto Laf
            goto Lb1
        Laf:
            com.motorola.plugin.core.Level r0 = com.motorola.plugin.core.Level.INFO
        Lb1:
            com.motorola.plugin.core.PluginConfigKt.FALLBACK_TAG_LEVEL = r0
            com.motorola.plugin.core.PluginConfigKt$defaultLogPrinter$1 r0 = new kotlin.jvm.functions.Function3() { // from class: com.motorola.plugin.core.PluginConfigKt$defaultLogPrinter$1
                static {
                    /*
                        com.motorola.plugin.core.PluginConfigKt$defaultLogPrinter$1 r0 = new com.motorola.plugin.core.PluginConfigKt$defaultLogPrinter$1
                        r0.<init>()
                        
                        // error: 0x0005: SPUT (r0 I:com.motorola.plugin.core.PluginConfigKt$defaultLogPrinter$1) com.motorola.plugin.core.PluginConfigKt$defaultLogPrinter$1.INSTANCE com.motorola.plugin.core.PluginConfigKt$defaultLogPrinter$1
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.motorola.plugin.core.PluginConfigKt$defaultLogPrinter$1.<clinit>():void");
                }

                {
                    /*
                        r1 = this;
                        r0 = 3
                        r1.<init>(r0)
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.motorola.plugin.core.PluginConfigKt$defaultLogPrinter$1.<init>():void");
                }

                @Override // kotlin.jvm.functions.Function3
                public /* bridge */ /* synthetic */ java.lang.Object invoke(java.lang.Object r1, java.lang.Object r2, java.lang.Object r3) {
                    /*
                        r0 = this;
                        java.lang.Number r1 = (java.lang.Number) r1
                        int r1 = r1.intValue()
                        java.lang.String r2 = (java.lang.String) r2
                        java.lang.String r3 = (java.lang.String) r3
                        r0.invoke(r1, r2, r3)
                        kotlin.Unit r0 = kotlin.Unit.INSTANCE
                        return r0
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.motorola.plugin.core.PluginConfigKt$defaultLogPrinter$1.invoke(java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object");
                }

                public final void invoke(int r1, java.lang.String r2, java.lang.String r3) {
                    /*
                        r0 = this;
                        r2.getClass()
                        r3.getClass()
                        android.util.Log.println(r1, r2, r3)
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.motorola.plugin.core.PluginConfigKt$defaultLogPrinter$1.invoke(int, java.lang.String, java.lang.String):void");
                }
            }
            com.motorola.plugin.core.PluginConfigKt.defaultLogPrinter = r0
            com.motorola.plugin.core.PluginConfigKt$defaultLogValidator$1 r0 = new kotlin.jvm.functions.Function2() { // from class: com.motorola.plugin.core.PluginConfigKt$defaultLogValidator$1
                static {
                    /*
                        com.motorola.plugin.core.PluginConfigKt$defaultLogValidator$1 r0 = new com.motorola.plugin.core.PluginConfigKt$defaultLogValidator$1
                        r0.<init>()
                        
                        // error: 0x0005: SPUT (r0 I:com.motorola.plugin.core.PluginConfigKt$defaultLogValidator$1) com.motorola.plugin.core.PluginConfigKt$defaultLogValidator$1.INSTANCE com.motorola.plugin.core.PluginConfigKt$defaultLogValidator$1
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.motorola.plugin.core.PluginConfigKt$defaultLogValidator$1.<clinit>():void");
                }

                {
                    /*
                        r1 = this;
                        r0 = 2
                        r1.<init>(r0)
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.motorola.plugin.core.PluginConfigKt$defaultLogValidator$1.<init>():void");
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ java.lang.Object invoke(java.lang.Object r1, java.lang.Object r2) {
                    /*
                        r0 = this;
                        java.lang.Number r1 = (java.lang.Number) r1
                        int r1 = r1.intValue()
                        java.lang.String r2 = (java.lang.String) r2
                        boolean r0 = r0.invoke(r1, r2)
                        java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)
                        return r0
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.motorola.plugin.core.PluginConfigKt$defaultLogValidator$1.invoke(java.lang.Object, java.lang.Object):java.lang.Object");
                }

                public final boolean invoke(int r1, java.lang.String r2) {
                    /*
                        r0 = this;
                        r2.getClass()
                        boolean r0 = android.util.Log.isLoggable(r2, r1)
                        return r0
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.motorola.plugin.core.PluginConfigKt$defaultLogValidator$1.invoke(int, java.lang.String):boolean");
                }
            }
            com.motorola.plugin.core.PluginConfigKt.defaultLogValidator = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.motorola.plugin.core.PluginConfigKt.<clinit>():void");
    }

    public static final boolean getDEBUG() {
        return DEBUG;
    }

    public static final boolean getDISABLE_PLUGIN_WHILE_CRASH() {
        return DISABLE_PLUGIN_WHILE_CRASH;
    }

    private static final Level getLevel(Level level, Function0 function0) {
        return getDEBUG() ? level : (Level) function0.mo2224invoke();
    }

    static /* synthetic */ Level getLevel$default(Level level, Function0 function0, int i, Object obj) {
        if ((i & 1) != 0) {
            level = Level.VERBOSE;
        }
        return getDEBUG() ? level : (Level) function0.mo2224invoke();
    }

    public static final void injectTestLogConfig(Function2 function2, Function3 function3) {
        function2.getClass();
        function3.getClass();
        defaultLogValidator = function2;
        defaultLogPrinter = function3;
    }

    public static final void setDISABLE_PLUGIN_WHILE_CRASH(boolean z) {
        DISABLE_PLUGIN_WHILE_CRASH = z;
    }

    public static final void trace(String str, Level level, boolean z, Throwable th, boolean z2, String str2, Function0 function0) {
        String str3;
        str.getClass();
        level.getClass();
        function0.getClass();
        if (z2) {
            int level2 = level.getLevel();
            boolean zBooleanValue = ((Boolean) defaultLogValidator.invoke(Integer.valueOf(level2), str)).booleanValue();
            Level level3 = (Level) minimLogLevelMap.getOrDefault(str, FALLBACK_TAG_LEVEL);
            if (zBooleanValue || level2 >= level3.getLevel()) {
                Object objMo2224invoke = function0.mo2224invoke();
                String str4 = "";
                if (z) {
                    str3 = '[' + ((Object) Thread.currentThread().getName()) + "] ";
                } else {
                    str3 = "";
                }
                String strStringPlus = th == null ? "" : Intrinsics.stringPlus("\n ", ThrowableUtilKt.getStackTraceString(th));
                if (str2 != null && str2.length() != 0) {
                    str4 = " traceId: " + ((Object) str2) + ' ';
                }
                defaultLogPrinter.invoke(Integer.valueOf(level2), str, str3 + objMo2224invoke + strStringPlus + str4);
            }
        }
    }

    public static /* synthetic */ void trace$default(String str, Level level, boolean z, Throwable th, boolean z2, String str2, Function0 function0, int i, Object obj) {
        Function0 function02;
        String str3;
        if ((i & 1) != 0) {
            str = TAG_PLUGIN;
        }
        if ((i & 2) != 0) {
            level = Level.DEBUG;
        }
        if ((i & 4) != 0) {
            z = false;
        }
        if ((i & 8) != 0) {
            th = null;
        }
        if ((i & 16) != 0) {
            z2 = true;
        }
        if ((i & 32) != 0) {
            function02 = function0;
            str3 = null;
        } else {
            function02 = function0;
            str3 = str2;
        }
        boolean z3 = z2;
        Throwable th2 = th;
        trace(str, level, z, th2, z3, str3, function02);
    }
}
