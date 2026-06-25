package androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableList;

import androidx.compose.runtime.external.kotlinx.collections.immutable.PersistentList;

/* JADX INFO: compiled from: Utils.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class UtilsKt {
    public static final int indexSegment(int i, int i2) {
        return (i >> i2) & 31;
    }

    public static final PersistentList persistentVectorOf() {
        return SmallPersistentVector.Companion.getEMPTY();
    }

    public static final Object[] presizedBufferWith(Object obj) {
        Object[] objArr = new Object[32];
        objArr[0] = obj;
        return objArr;
    }

    public static final int rootSize(int i) {
        return (i - 1) & (-32);
    }
}
