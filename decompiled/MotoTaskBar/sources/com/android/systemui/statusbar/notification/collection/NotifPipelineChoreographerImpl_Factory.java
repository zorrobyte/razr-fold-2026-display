package com.android.systemui.statusbar.notification.collection;

import android.view.Choreographer;
import com.android.systemui.util.concurrency.DelayableExecutor;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class NotifPipelineChoreographerImpl_Factory implements Factory {
    private final Provider executorProvider;
    private final Provider viewChoreographerProvider;

    public NotifPipelineChoreographerImpl_Factory(Provider provider, Provider provider2) {
        this.viewChoreographerProvider = provider;
        this.executorProvider = provider2;
    }

    public static NotifPipelineChoreographerImpl_Factory create(Provider provider, Provider provider2) {
        return new NotifPipelineChoreographerImpl_Factory(provider, provider2);
    }

    public static NotifPipelineChoreographerImpl newInstance(Choreographer choreographer, DelayableExecutor delayableExecutor) {
        return new NotifPipelineChoreographerImpl(choreographer, delayableExecutor);
    }

    @Override // javax.inject.Provider
    public NotifPipelineChoreographerImpl get() {
        return newInstance((Choreographer) this.viewChoreographerProvider.get(), (DelayableExecutor) this.executorProvider.get());
    }
}
