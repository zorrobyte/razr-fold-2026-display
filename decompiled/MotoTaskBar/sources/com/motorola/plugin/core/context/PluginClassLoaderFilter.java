package com.motorola.plugin.core.context;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: PluginClassLoaderFilter.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class PluginClassLoaderFilter extends ClassLoader {
    public static final Companion Companion = new Companion(null);
    private static final boolean DEBUG = false;
    private final ClassLoader hostClassLoader;
    private final String[] mPackages;

    /* JADX INFO: compiled from: PluginClassLoaderFilter.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PluginClassLoaderFilter(ClassLoader classLoader, String... strArr) {
        super(ClassLoader.getSystemClassLoader());
        classLoader.getClass();
        strArr.getClass();
        this.hostClassLoader = classLoader;
        this.mPackages = strArr;
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x005c, code lost:
    
        if (r6 != null) goto L18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x005e, code lost:
    
        com.motorola.plugin.core.PluginConfigKt.trace$default(com.motorola.plugin.core.PluginConfigKt.TAG_CLASS_FILTER, com.motorola.plugin.core.Level.VERBOSE, false, null, false, null, new com.motorola.plugin.core.context.PluginClassLoaderFilter.AnonymousClass4(), 44, null);
        r0 = super.loadClass(r17, r18);
        com.motorola.plugin.core.PluginConfigKt.trace$default(com.motorola.plugin.core.PluginConfigKt.TAG_CLASS_FILTER, com.motorola.plugin.core.Level.INFO, false, null, false, null, new com.motorola.plugin.core.context.PluginClassLoaderFilter.AnonymousClass5(), 44, null);
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0088, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x0089, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x008a, code lost:
    
        com.motorola.plugin.core.PluginConfigKt.trace$default(com.motorola.plugin.core.PluginConfigKt.TAG_CLASS_FILTER, com.motorola.plugin.core.Level.WARN, false, null, false, null, new com.motorola.plugin.core.context.PluginClassLoaderFilter.AnonymousClass6(), 44, null);
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x009d, code lost:
    
        throw r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x009e, code lost:
    
        return r6;
     */
    @Override // java.lang.ClassLoader
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected java.lang.Class loadClass(final java.lang.String r17, boolean r18) throws java.lang.ClassNotFoundException {
        /*
            r16 = this;
            r0 = r16
            r1 = r17
            r1.getClass()
            java.lang.String[] r2 = r0.mPackages
            int r3 = r2.length
            r4 = 0
            r5 = r4
        Lc:
            r6 = 0
            if (r5 >= r3) goto L5c
            r7 = r2[r5]
            int r5 = r5 + 1
            r8 = 2
            boolean r6 = kotlin.text.StringsKt.startsWith$default(r1, r7, r4, r8, r6)
            if (r6 == 0) goto Lc
            java.lang.String r7 = "ClassFilter"
            com.motorola.plugin.core.Level r8 = com.motorola.plugin.core.Level.VERBOSE     // Catch: java.lang.ClassNotFoundException -> L47
            com.motorola.plugin.core.context.PluginClassLoaderFilter$loadClass$1 r13 = new com.motorola.plugin.core.context.PluginClassLoaderFilter$loadClass$1     // Catch: java.lang.ClassNotFoundException -> L47
            r13.<init>()     // Catch: java.lang.ClassNotFoundException -> L47
            r14 = 44
            r15 = 0
            r9 = 0
            r10 = 0
            r11 = 0
            r12 = 0
            com.motorola.plugin.core.PluginConfigKt.trace$default(r7, r8, r9, r10, r11, r12, r13, r14, r15)     // Catch: java.lang.ClassNotFoundException -> L47
            java.lang.ClassLoader r2 = r0.hostClassLoader     // Catch: java.lang.ClassNotFoundException -> L47
            java.lang.Class r6 = r2.loadClass(r1)     // Catch: java.lang.ClassNotFoundException -> L47
            java.lang.String r7 = "ClassFilter"
            com.motorola.plugin.core.Level r8 = com.motorola.plugin.core.Level.INFO     // Catch: java.lang.ClassNotFoundException -> L47
            com.motorola.plugin.core.context.PluginClassLoaderFilter$loadClass$2 r13 = new com.motorola.plugin.core.context.PluginClassLoaderFilter$loadClass$2     // Catch: java.lang.ClassNotFoundException -> L47
            r13.<init>()     // Catch: java.lang.ClassNotFoundException -> L47
            r14 = 44
            r15 = 0
            r9 = 0
            r10 = 0
            r11 = 0
            r12 = 0
            com.motorola.plugin.core.PluginConfigKt.trace$default(r7, r8, r9, r10, r11, r12, r13, r14, r15)     // Catch: java.lang.ClassNotFoundException -> L47
            goto L5c
        L47:
            r0 = move-exception
            com.motorola.plugin.core.Level r2 = com.motorola.plugin.core.Level.WARN
            com.motorola.plugin.core.context.PluginClassLoaderFilter$loadClass$3 r7 = new com.motorola.plugin.core.context.PluginClassLoaderFilter$loadClass$3
            r7.<init>()
            r8 = 44
            r9 = 0
            java.lang.String r1 = "ClassFilter"
            r3 = 0
            r4 = 0
            r5 = 0
            r6 = 0
            com.motorola.plugin.core.PluginConfigKt.trace$default(r1, r2, r3, r4, r5, r6, r7, r8, r9)
            throw r0
        L5c:
            if (r6 != 0) goto L9e
            java.lang.String r7 = "ClassFilter"
            com.motorola.plugin.core.Level r8 = com.motorola.plugin.core.Level.VERBOSE     // Catch: java.lang.ClassNotFoundException -> L89
            com.motorola.plugin.core.context.PluginClassLoaderFilter$loadClass$4 r13 = new com.motorola.plugin.core.context.PluginClassLoaderFilter$loadClass$4     // Catch: java.lang.ClassNotFoundException -> L89
            r13.<init>()     // Catch: java.lang.ClassNotFoundException -> L89
            r14 = 44
            r15 = 0
            r9 = 0
            r10 = 0
            r11 = 0
            r12 = 0
            com.motorola.plugin.core.PluginConfigKt.trace$default(r7, r8, r9, r10, r11, r12, r13, r14, r15)     // Catch: java.lang.ClassNotFoundException -> L89
            java.lang.Class r0 = super.loadClass(r17, r18)     // Catch: java.lang.ClassNotFoundException -> L89
            java.lang.String r2 = "ClassFilter"
            com.motorola.plugin.core.Level r3 = com.motorola.plugin.core.Level.INFO     // Catch: java.lang.ClassNotFoundException -> L89
            com.motorola.plugin.core.context.PluginClassLoaderFilter$loadClass$5 r8 = new com.motorola.plugin.core.context.PluginClassLoaderFilter$loadClass$5     // Catch: java.lang.ClassNotFoundException -> L89
            r8.<init>()     // Catch: java.lang.ClassNotFoundException -> L89
            r9 = 44
            r10 = 0
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            com.motorola.plugin.core.PluginConfigKt.trace$default(r2, r3, r4, r5, r6, r7, r8, r9, r10)     // Catch: java.lang.ClassNotFoundException -> L89
            return r0
        L89:
            r0 = move-exception
            com.motorola.plugin.core.Level r2 = com.motorola.plugin.core.Level.WARN
            com.motorola.plugin.core.context.PluginClassLoaderFilter$loadClass$6 r7 = new com.motorola.plugin.core.context.PluginClassLoaderFilter$loadClass$6
            r7.<init>()
            r8 = 44
            r9 = 0
            java.lang.String r1 = "ClassFilter"
            r3 = 0
            r4 = 0
            r5 = 0
            r6 = 0
            com.motorola.plugin.core.PluginConfigKt.trace$default(r1, r2, r3, r4, r5, r6, r7, r8, r9)
            throw r0
        L9e:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.motorola.plugin.core.context.PluginClassLoaderFilter.loadClass(java.lang.String, boolean):java.lang.Class");
    }
}
