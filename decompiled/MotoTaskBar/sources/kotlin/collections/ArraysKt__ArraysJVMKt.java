package kotlin.collections;

import java.lang.reflect.Array;

/* JADX INFO: compiled from: ArraysJVM.kt */
/* JADX INFO: loaded from: classes2.dex */
abstract class ArraysKt__ArraysJVMKt {
    public static final Object[] arrayOfNulls(Object[] objArr, int i) {
        objArr.getClass();
        Object objNewInstance = Array.newInstance(objArr.getClass().getComponentType(), i);
        objNewInstance.getClass();
        return (Object[]) objNewInstance;
    }

    public static final void copyOfRangeToIndexCheck(int i, int i2) {
        if (i <= i2) {
            return;
        }
        throw new IndexOutOfBoundsException("toIndex (" + i + ") is greater than size (" + i2 + ").");
    }
}
