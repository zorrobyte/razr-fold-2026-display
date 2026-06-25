package com.android.systemui.statusbar.notification.icon;

import android.content.Context;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class IconBuilder_Factory implements Factory {
    private final Provider contextProvider;

    public IconBuilder_Factory(Provider provider) {
        this.contextProvider = provider;
    }

    public static IconBuilder_Factory create(Provider provider) {
        return new IconBuilder_Factory(provider);
    }

    public static IconBuilder newInstance(Context context) {
        return new IconBuilder(context);
    }

    @Override // javax.inject.Provider
    public IconBuilder get() {
        return newInstance((Context) this.contextProvider.get());
    }
}
