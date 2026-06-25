package com.android.systemui.media.controls.domain.pipeline.interactor;

import android.content.Context;
import com.android.internal.logging.InstanceId;
import com.android.systemui.ActivityIntentHelper;
import com.android.systemui.displays.ActivityStarter;
import com.android.systemui.media.controls.data.repository.MediaFilterRepository;
import com.android.systemui.media.controls.domain.pipeline.MediaDataProcessor;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class MediaControlInteractor_Factory {
    private final Provider activityIntentHelperProvider;
    private final Provider activityStarterProvider;
    private final Provider applicationContextProvider;
    private final Provider lockscreenUserManagerProvider;
    private final Provider mediaDataProcessorProvider;
    private final Provider repositoryProvider;

    public MediaControlInteractor_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6) {
        this.applicationContextProvider = provider;
        this.repositoryProvider = provider2;
        this.mediaDataProcessorProvider = provider3;
        this.activityStarterProvider = provider4;
        this.activityIntentHelperProvider = provider5;
        this.lockscreenUserManagerProvider = provider6;
    }

    public static MediaControlInteractor_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6) {
        return new MediaControlInteractor_Factory(provider, provider2, provider3, provider4, provider5, provider6);
    }

    public static MediaControlInteractor newInstance(Context context, InstanceId instanceId, MediaFilterRepository mediaFilterRepository, MediaDataProcessor mediaDataProcessor, ActivityStarter activityStarter, ActivityIntentHelper activityIntentHelper, NotificationLockscreenUserManager notificationLockscreenUserManager) {
        return new MediaControlInteractor(context, instanceId, mediaFilterRepository, mediaDataProcessor, activityStarter, activityIntentHelper, notificationLockscreenUserManager);
    }

    public MediaControlInteractor get(InstanceId instanceId) {
        return newInstance((Context) this.applicationContextProvider.get(), instanceId, (MediaFilterRepository) this.repositoryProvider.get(), (MediaDataProcessor) this.mediaDataProcessorProvider.get(), (ActivityStarter) this.activityStarterProvider.get(), (ActivityIntentHelper) this.activityIntentHelperProvider.get(), (NotificationLockscreenUserManager) this.lockscreenUserManagerProvider.get());
    }
}
