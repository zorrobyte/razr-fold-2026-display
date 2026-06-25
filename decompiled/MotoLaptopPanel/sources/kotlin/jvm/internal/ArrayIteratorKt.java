package kotlin.jvm.internal;

import java.util.Iterator;

/* JADX INFO: compiled from: ArrayIterator.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class ArrayIteratorKt {
    public static final Iterator iterator(Object[] objArr) {
        objArr.getClass();
        return new ArrayIterator(objArr);
    }
}
