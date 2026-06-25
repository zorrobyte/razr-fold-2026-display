package com.android.systemui.statusbar.notification.stack;

import android.content.res.Resources;
import android.view.ViewConfiguration;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.statusbar.notification.stack.NotificationSwipeHelper;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class NotificationSwipeHelper_Builder_Factory implements Factory {
    private final Provider dumpManagerProvider;
    private final Provider featureFlagsProvider;
    private final Provider notificationRoundnessManagerProvider;
    private final Provider resourcesProvider;
    private final Provider viewConfigurationProvider;

    public NotificationSwipeHelper_Builder_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        this.resourcesProvider = provider;
        this.viewConfigurationProvider = provider2;
        this.dumpManagerProvider = provider3;
        this.featureFlagsProvider = provider4;
        this.notificationRoundnessManagerProvider = provider5;
    }

    public static NotificationSwipeHelper_Builder_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        return new NotificationSwipeHelper_Builder_Factory(provider, provider2, provider3, provider4, provider5);
    }

    public static NotificationSwipeHelper.Builder newInstance(Resources resources, ViewConfiguration viewConfiguration, DumpManager dumpManager, FeatureFlags featureFlags, NotificationRoundnessManager notificationRoundnessManager) {
        return new NotificationSwipeHelper.Builder(resources, viewConfiguration, dumpManager, featureFlags, notificationRoundnessManager);
    }

    @Override // javax.inject.Provider
    public NotificationSwipeHelper.Builder get() {
        return newInstance((Resources) this.resourcesProvider.get(), (ViewConfiguration) this.viewConfigurationProvider.get(), (DumpManager) this.dumpManagerProvider.get(), (FeatureFlags) this.featureFlagsProvider.get(), (NotificationRoundnessManager) this.notificationRoundnessManagerProvider.get());
    }
}
