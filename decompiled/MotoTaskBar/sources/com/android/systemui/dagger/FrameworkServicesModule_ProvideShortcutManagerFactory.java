package com.android.systemui.dagger;

import android.content.Context;
import android.content.pm.ShortcutManager;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class FrameworkServicesModule_ProvideShortcutManagerFactory implements Factory {
    private final Provider contextProvider;

    public FrameworkServicesModule_ProvideShortcutManagerFactory(Provider provider) {
        this.contextProvider = provider;
    }

    public static FrameworkServicesModule_ProvideShortcutManagerFactory create(Provider provider) {
        return new FrameworkServicesModule_ProvideShortcutManagerFactory(provider);
    }

    public static ShortcutManager provideShortcutManager(Context context) {
        ShortcutManager shortcutManagerProvideShortcutManager = FrameworkServicesModule.provideShortcutManager(context);
        shortcutManagerProvideShortcutManager.getClass();
        return shortcutManagerProvideShortcutManager;
    }

    @Override // javax.inject.Provider
    public ShortcutManager get() {
        return provideShortcutManager((Context) this.contextProvider.get());
    }
}
