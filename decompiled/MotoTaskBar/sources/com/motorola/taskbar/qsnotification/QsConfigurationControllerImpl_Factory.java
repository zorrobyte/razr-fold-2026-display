package com.motorola.taskbar.qsnotification;

import android.content.Context;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes2.dex */
public final class QsConfigurationControllerImpl_Factory implements Factory {
    private final Provider contextProvider;

    public QsConfigurationControllerImpl_Factory(Provider provider) {
        this.contextProvider = provider;
    }

    public static QsConfigurationControllerImpl_Factory create(Provider provider) {
        return new QsConfigurationControllerImpl_Factory(provider);
    }

    public static QsConfigurationControllerImpl newInstance(Context context) {
        return new QsConfigurationControllerImpl(context);
    }

    @Override // javax.inject.Provider
    public QsConfigurationControllerImpl get() {
        return newInstance((Context) this.contextProvider.get());
    }
}
