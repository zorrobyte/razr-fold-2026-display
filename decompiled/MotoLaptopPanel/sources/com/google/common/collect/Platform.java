package com.google.common.collect;

import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
abstract class Platform {
    static Object[] copy(Object[] objArr, int i, int i2, Object[] objArr2) {
        return Arrays.copyOfRange(objArr, i, i2, objArr2.getClass());
    }

    static Object[] newArray(Object[] objArr, int i) {
        if (objArr.length != 0) {
            objArr = Arrays.copyOf(objArr, 0);
        }
        return Arrays.copyOf(objArr, i);
    }
}
