package com.android.systemui.displays;

import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class ActivityStarterProxy_Factory implements Factory {
    private final Provider activityStarterDelegateProvider;
    private final Provider displayIdProvider;

    public ActivityStarterProxy_Factory(Provider provider, Provider provider2) {
        this.displayIdProvider = provider;
        this.activityStarterDelegateProvider = provider2;
    }

    public static ActivityStarterProxy_Factory create(Provider provider, Provider provider2) {
        return new ActivityStarterProxy_Factory(provider, provider2);
    }

    public static ActivityStarterProxy newInstance(int i, com.android.systemui.plugins.moto.ActivityStarter activityStarter) {
        return new ActivityStarterProxy(i, activityStarter);
    }

    @Override // javax.inject.Provider
    public ActivityStarterProxy get() {
        return newInstance(((Integer) this.displayIdProvider.get()).intValue(), (com.android.systemui.plugins.moto.ActivityStarter) this.activityStarterDelegateProvider.get());
    }
}
