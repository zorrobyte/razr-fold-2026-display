package com.google.common.collect;

import java.util.ListIterator;

/* JADX INFO: loaded from: classes.dex */
public abstract class UnmodifiableListIterator extends UnmodifiableIterator implements ListIterator {
    protected UnmodifiableListIterator() {
    }

    @Override // java.util.ListIterator
    public final void add(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.ListIterator
    public final void set(Object obj) {
        throw new UnsupportedOperationException();
    }
}
