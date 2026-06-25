package com.google.common.util.concurrent;

import com.google.common.util.concurrent.AbstractFuture;

/* JADX INFO: loaded from: classes.dex */
public final class SettableFuture extends AbstractFuture.TrustedFuture {
    private SettableFuture() {
    }

    public static SettableFuture create() {
        return new SettableFuture();
    }

    @Override // com.google.common.util.concurrent.AbstractFuture
    public boolean set(Object obj) {
        return super.set(obj);
    }
}
