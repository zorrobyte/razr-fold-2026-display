package com.android.systemui.dagger;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class DependencyProvider_ProvideDisplayMetricsFactory implements Factory {
    private final Provider contextProvider;
    private final DependencyProvider module;
    private final Provider windowManagerProvider;

    public DependencyProvider_ProvideDisplayMetricsFactory(DependencyProvider dependencyProvider, Provider provider, Provider provider2) {
        this.module = dependencyProvider;
        this.contextProvider = provider;
        this.windowManagerProvider = provider2;
    }

    public static DependencyProvider_ProvideDisplayMetricsFactory create(DependencyProvider dependencyProvider, Provider provider, Provider provider2) {
        return new DependencyProvider_ProvideDisplayMetricsFactory(dependencyProvider, provider, provider2);
    }

    public static DisplayMetrics provideDisplayMetrics(DependencyProvider dependencyProvider, Context context, WindowManager windowManager) {
        DisplayMetrics displayMetricsProvideDisplayMetrics = dependencyProvider.provideDisplayMetrics(context, windowManager);
        displayMetricsProvideDisplayMetrics.getClass();
        return displayMetricsProvideDisplayMetrics;
    }

    @Override // javax.inject.Provider
    public DisplayMetrics get() {
        return provideDisplayMetrics(this.module, (Context) this.contextProvider.get(), (WindowManager) this.windowManagerProvider.get());
    }
}
