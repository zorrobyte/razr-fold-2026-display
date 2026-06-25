package com.motorola.taskbar.qsnotification.dagger;

import android.content.Context;
import android.view.LayoutInflater;
import com.motorola.taskbar.qsnotification.dagger.QsNotificationComponent;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes2.dex */
public final class QsNotificationComponent_NotificationExtraModule_ProviderLayoutInflaterFactory implements Factory {
    private final Provider contextProvider;

    public QsNotificationComponent_NotificationExtraModule_ProviderLayoutInflaterFactory(Provider provider) {
        this.contextProvider = provider;
    }

    public static QsNotificationComponent_NotificationExtraModule_ProviderLayoutInflaterFactory create(Provider provider) {
        return new QsNotificationComponent_NotificationExtraModule_ProviderLayoutInflaterFactory(provider);
    }

    public static LayoutInflater providerLayoutInflater(Context context) {
        LayoutInflater layoutInflaterProviderLayoutInflater = QsNotificationComponent.NotificationExtraModule.providerLayoutInflater(context);
        layoutInflaterProviderLayoutInflater.getClass();
        return layoutInflaterProviderLayoutInflater;
    }

    @Override // javax.inject.Provider
    public LayoutInflater get() {
        return providerLayoutInflater((Context) this.contextProvider.get());
    }
}
