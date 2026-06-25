package com.android.systemui.statusbar;

import android.content.Context;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class RemoteInputNotificationRebuilder_Factory implements Factory {
    private final Provider contextProvider;

    public RemoteInputNotificationRebuilder_Factory(Provider provider) {
        this.contextProvider = provider;
    }

    public static RemoteInputNotificationRebuilder_Factory create(Provider provider) {
        return new RemoteInputNotificationRebuilder_Factory(provider);
    }

    public static RemoteInputNotificationRebuilder newInstance(Context context) {
        return new RemoteInputNotificationRebuilder(context);
    }

    @Override // javax.inject.Provider
    public RemoteInputNotificationRebuilder get() {
        return newInstance((Context) this.contextProvider.get());
    }
}
