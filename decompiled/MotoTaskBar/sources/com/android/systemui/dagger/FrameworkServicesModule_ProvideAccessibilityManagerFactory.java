package com.android.systemui.dagger;

import android.content.Context;
import android.view.accessibility.AccessibilityManager;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class FrameworkServicesModule_ProvideAccessibilityManagerFactory implements Factory {
    private final Provider contextProvider;

    public FrameworkServicesModule_ProvideAccessibilityManagerFactory(Provider provider) {
        this.contextProvider = provider;
    }

    public static FrameworkServicesModule_ProvideAccessibilityManagerFactory create(Provider provider) {
        return new FrameworkServicesModule_ProvideAccessibilityManagerFactory(provider);
    }

    public static AccessibilityManager provideAccessibilityManager(Context context) {
        AccessibilityManager accessibilityManagerProvideAccessibilityManager = FrameworkServicesModule.provideAccessibilityManager(context);
        accessibilityManagerProvideAccessibilityManager.getClass();
        return accessibilityManagerProvideAccessibilityManager;
    }

    @Override // javax.inject.Provider
    public AccessibilityManager get() {
        return provideAccessibilityManager((Context) this.contextProvider.get());
    }
}
