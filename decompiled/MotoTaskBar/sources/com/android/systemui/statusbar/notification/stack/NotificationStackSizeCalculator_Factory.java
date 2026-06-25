package com.android.systemui.statusbar.notification.stack;

import android.content.res.Resources;
import com.android.systemui.media.controls.domain.pipeline.MediaDataManager;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class NotificationStackSizeCalculator_Factory implements Factory {
    private final Provider mediaDataManagerProvider;
    private final Provider resourcesProvider;

    public NotificationStackSizeCalculator_Factory(Provider provider, Provider provider2) {
        this.mediaDataManagerProvider = provider;
        this.resourcesProvider = provider2;
    }

    public static NotificationStackSizeCalculator_Factory create(Provider provider, Provider provider2) {
        return new NotificationStackSizeCalculator_Factory(provider, provider2);
    }

    public static NotificationStackSizeCalculator newInstance(MediaDataManager mediaDataManager, Resources resources) {
        return new NotificationStackSizeCalculator(mediaDataManager, resources);
    }

    @Override // javax.inject.Provider
    public NotificationStackSizeCalculator get() {
        return newInstance((MediaDataManager) this.mediaDataManagerProvider.get(), (Resources) this.resourcesProvider.get());
    }
}
