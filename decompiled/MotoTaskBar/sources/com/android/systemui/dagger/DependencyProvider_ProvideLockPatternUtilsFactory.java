package com.android.systemui.dagger;

import android.content.Context;
import com.android.internal.widget.LockPatternUtils;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class DependencyProvider_ProvideLockPatternUtilsFactory implements Factory {
    private final Provider contextProvider;
    private final DependencyProvider module;

    public DependencyProvider_ProvideLockPatternUtilsFactory(DependencyProvider dependencyProvider, Provider provider) {
        this.module = dependencyProvider;
        this.contextProvider = provider;
    }

    public static DependencyProvider_ProvideLockPatternUtilsFactory create(DependencyProvider dependencyProvider, Provider provider) {
        return new DependencyProvider_ProvideLockPatternUtilsFactory(dependencyProvider, provider);
    }

    public static LockPatternUtils provideLockPatternUtils(DependencyProvider dependencyProvider, Context context) {
        LockPatternUtils lockPatternUtilsProvideLockPatternUtils = dependencyProvider.provideLockPatternUtils(context);
        lockPatternUtilsProvideLockPatternUtils.getClass();
        return lockPatternUtilsProvideLockPatternUtils;
    }

    @Override // javax.inject.Provider
    public LockPatternUtils get() {
        return provideLockPatternUtils(this.module, (Context) this.contextProvider.get());
    }
}
