package com.android.systemui.statusbar.notification.stack;

import com.android.systemui.flags.FeatureFlags;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class NotificationTargetsHelper_Factory implements Factory {
    private final Provider featureFlagsProvider;

    public NotificationTargetsHelper_Factory(Provider provider) {
        this.featureFlagsProvider = provider;
    }

    public static NotificationTargetsHelper_Factory create(Provider provider) {
        return new NotificationTargetsHelper_Factory(provider);
    }

    public static NotificationTargetsHelper newInstance(FeatureFlags featureFlags) {
        return new NotificationTargetsHelper(featureFlags);
    }

    @Override // javax.inject.Provider
    public NotificationTargetsHelper get() {
        return newInstance((FeatureFlags) this.featureFlagsProvider.get());
    }
}
