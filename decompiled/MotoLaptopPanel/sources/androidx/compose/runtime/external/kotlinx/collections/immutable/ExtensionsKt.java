package androidx.compose.runtime.external.kotlinx.collections.immutable;

import androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableList.UtilsKt;
import androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.persistentOrderedSet.PersistentOrderedSet;

/* JADX INFO: compiled from: extensions.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class ExtensionsKt {
    public static final PersistentList persistentListOf() {
        return UtilsKt.persistentVectorOf();
    }

    public static final PersistentSet persistentSetOf() {
        return PersistentOrderedSet.Companion.emptyOf$runtime_release();
    }
}
