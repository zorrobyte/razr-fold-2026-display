package com.motorola.plugin.core.context;

import com.motorola.plugin.core.Level;
import com.motorola.plugin.core.PluginConfigKt;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: SharedInPluginClassLoader.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class SharedInPluginClassLoader extends ClassLoader {
    private static final boolean DEBUG = false;
    public static final Companion Companion = new Companion(null);
    private static final List CLASSES_MUST_LOAD_IN_SHARED_LOADER_SCOPE = CollectionsKt.emptyList();

    /* JADX INFO: compiled from: SharedInPluginClassLoader.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private final Function1 ifStartWith(final String str) {
            return new Function1() { // from class: com.motorola.plugin.core.context.SharedInPluginClassLoader$Companion$ifStartWith$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                    return Boolean.valueOf(invoke((String) obj));
                }

                public final boolean invoke(String str2) {
                    str2.getClass();
                    return StringsKt.startsWith$default(str2, str, false, 2, (Object) null);
                }
            };
        }
    }

    public SharedInPluginClassLoader(ClassLoader classLoader) {
        super(classLoader);
    }

    @Override // java.lang.ClassLoader
    protected Class loadClass(final String str, boolean z) throws ClassNotFoundException {
        str.getClass();
        final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
        Class<?> clsFindLoadedClass = findLoadedClass(str);
        ref$ObjectRef.element = clsFindLoadedClass;
        if (clsFindLoadedClass != null) {
            PluginConfigKt.trace$default(PluginConfigKt.TAG_CLASS_FILTER, Level.INFO, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.context.SharedInPluginClassLoader.loadClass.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                /* JADX INFO: renamed from: invoke */
                public final Object mo2224invoke() {
                    return Intrinsics.stringPlus("Shared cached -> ", str);
                }
            }, 44, null);
            if (z) {
                resolveClass((Class) ref$ObjectRef.element);
            }
            Object obj = ref$ObjectRef.element;
            obj.getClass();
            return (Class) obj;
        }
        if (getParent() != null) {
            try {
                Class<?> clsLoadClass = getParent().loadClass(str);
                ref$ObjectRef.element = clsLoadClass;
                clsLoadClass.getClass();
                Class<?> cls = clsLoadClass;
                return clsLoadClass;
            } catch (ClassNotFoundException unused) {
            }
        }
        if (ref$ObjectRef.element == null) {
            List list = CLASSES_MUST_LOAD_IN_SHARED_LOADER_SCOPE;
            if (!list.isEmpty() && (list == null || !list.isEmpty())) {
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    if (((Boolean) ((Function1) it.next()).invoke(str)).booleanValue()) {
                        try {
                            PluginConfigKt.trace$default(PluginConfigKt.TAG_CLASS_FILTER, Level.VERBOSE, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.context.SharedInPluginClassLoader.loadClass.3
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(0);
                                }

                                @Override // kotlin.jvm.functions.Function0
                                /* JADX INFO: renamed from: invoke */
                                public final Object mo2224invoke() {
                                    return Intrinsics.stringPlus("Shared loading -> ", str);
                                }
                            }, 44, null);
                            ref$ObjectRef.element = findClass(str);
                            PluginConfigKt.trace$default(PluginConfigKt.TAG_CLASS_FILTER, Level.INFO, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.context.SharedInPluginClassLoader.loadClass.4
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(0);
                                }

                                @Override // kotlin.jvm.functions.Function0
                                /* JADX INFO: renamed from: invoke */
                                public final Object mo2224invoke() {
                                    return "Shared loaded -> " + str + " by " + ((Class) ref$ObjectRef.element).getClassLoader();
                                }
                            }, 44, null);
                        } catch (ClassNotFoundException e) {
                            PluginConfigKt.trace$default(PluginConfigKt.TAG_CLASS_FILTER, Level.WARN, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.context.SharedInPluginClassLoader.loadClass.5
                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(0);
                                }

                                @Override // kotlin.jvm.functions.Function0
                                /* JADX INFO: renamed from: invoke */
                                public final Object mo2224invoke() {
                                    return Intrinsics.stringPlus("Shared load failed -> ", str);
                                }
                            }, 44, null);
                            throw e;
                        }
                    }
                }
            }
            throw new ClassNotFoundException(str);
        }
        Object obj2 = ref$ObjectRef.element;
        obj2.getClass();
        return (Class) obj2;
    }
}
