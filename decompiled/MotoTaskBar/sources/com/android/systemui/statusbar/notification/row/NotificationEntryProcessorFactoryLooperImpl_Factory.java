package com.android.systemui.statusbar.notification.row;

import android.os.Looper;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class NotificationEntryProcessorFactoryLooperImpl_Factory implements Factory {
    private final Provider mMainLooperProvider;

    public NotificationEntryProcessorFactoryLooperImpl_Factory(Provider provider) {
        this.mMainLooperProvider = provider;
    }

    public static NotificationEntryProcessorFactoryLooperImpl_Factory create(Provider provider) {
        return new NotificationEntryProcessorFactoryLooperImpl_Factory(provider);
    }

    public static NotificationEntryProcessorFactoryLooperImpl newInstance(Looper looper) {
        return new NotificationEntryProcessorFactoryLooperImpl(looper);
    }

    @Override // javax.inject.Provider
    public NotificationEntryProcessorFactoryLooperImpl get() {
        return newInstance((Looper) this.mMainLooperProvider.get());
    }
}
