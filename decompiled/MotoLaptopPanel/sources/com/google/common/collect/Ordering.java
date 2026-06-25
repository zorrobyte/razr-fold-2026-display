package com.google.common.collect;

import com.google.common.base.Function;
import java.util.Comparator;

/* JADX INFO: loaded from: classes.dex */
public abstract class Ordering implements Comparator {
    protected Ordering() {
    }

    public static Ordering from(Comparator comparator) {
        return comparator instanceof Ordering ? (Ordering) comparator : new ComparatorOrdering(comparator);
    }

    @Override // java.util.Comparator
    public abstract int compare(Object obj, Object obj2);

    public Ordering onResultOf(Function function) {
        return new ByFunctionOrdering(function, this);
    }
}
