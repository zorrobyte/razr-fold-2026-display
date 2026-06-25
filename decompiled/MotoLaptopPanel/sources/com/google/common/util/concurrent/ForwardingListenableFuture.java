package com.google.common.util.concurrent;

import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes.dex */
public abstract class ForwardingListenableFuture extends ForwardingFuture implements ListenableFuture {

    public abstract class SimpleForwardingListenableFuture extends ForwardingListenableFuture {
        private final ListenableFuture delegate;

        protected SimpleForwardingListenableFuture(ListenableFuture listenableFuture) {
            listenableFuture.getClass();
            this.delegate = listenableFuture;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.common.util.concurrent.ForwardingFuture, com.google.common.collect.ForwardingObject
        public final ListenableFuture delegate() {
            return this.delegate;
        }
    }

    protected ForwardingListenableFuture() {
    }

    @Override // com.google.common.util.concurrent.ListenableFuture
    public void addListener(Runnable runnable, Executor executor) {
        delegate().addListener(runnable, executor);
    }

    @Override // com.google.common.util.concurrent.ForwardingFuture, com.google.common.collect.ForwardingObject
    protected abstract ListenableFuture delegate();
}
