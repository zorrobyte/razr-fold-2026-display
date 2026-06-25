package com.google.common.collect;

/* JADX INFO: loaded from: classes.dex */
public abstract class ForwardingObject {
    protected ForwardingObject() {
    }

    protected abstract Object delegate();

    public String toString() {
        return delegate().toString();
    }
}
