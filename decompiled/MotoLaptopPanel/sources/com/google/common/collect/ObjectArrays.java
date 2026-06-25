package com.google.common.collect;

/* JADX INFO: loaded from: classes.dex */
public abstract class ObjectArrays {
    static Object checkElementNotNull(Object obj, int i) {
        if (obj != null) {
            return obj;
        }
        throw new NullPointerException("at index " + i);
    }

    static Object[] checkElementsNotNull(Object... objArr) {
        checkElementsNotNull(objArr, objArr.length);
        return objArr;
    }

    static Object[] checkElementsNotNull(Object[] objArr, int i) {
        for (int i2 = 0; i2 < i; i2++) {
            checkElementNotNull(objArr[i2], i2);
        }
        return objArr;
    }

    public static Object[] newArray(Object[] objArr, int i) {
        return Platform.newArray(objArr, i);
    }
}
