package com.google.common.collect;

/* JADX INFO: loaded from: classes.dex */
public abstract class Collections2 {
    static StringBuilder newStringBuilderForCollection(int i) {
        CollectPreconditions.checkNonnegative(i, "size");
        return new StringBuilder((int) Math.min(((long) i) * 8, 1073741824L));
    }
}
