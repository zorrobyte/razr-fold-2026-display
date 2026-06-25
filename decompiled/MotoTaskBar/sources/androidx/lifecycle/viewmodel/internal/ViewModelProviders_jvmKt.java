package androidx.lifecycle.viewmodel.internal;

import kotlin.reflect.KClass;

/* JADX INFO: compiled from: ViewModelProviders.jvm.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class ViewModelProviders_jvmKt {
    public static final String getCanonicalName(KClass kClass) {
        kClass.getClass();
        return kClass.getQualifiedName();
    }
}
