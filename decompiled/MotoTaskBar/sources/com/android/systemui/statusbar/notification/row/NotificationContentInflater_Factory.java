package com.android.systemui.statusbar.notification.row;

import com.android.systemui.media.controls.util.MediaFeatureFlag;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.notification.ConversationNotificationProcessor;
import com.android.systemui.statusbar.notification.row.NotifLayoutInflaterFactory;
import com.android.systemui.statusbar.policy.SmartReplyStateInflater;
import dagger.internal.Factory;
import dagger.internal.Provider;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes.dex */
public final class NotificationContentInflater_Factory implements Factory {
    private final Provider conversationProcessorProvider;
    private final Provider headsUpStyleProvider;
    private final Provider inflationExecutorProvider;
    private final Provider loggerProvider;
    private final Provider mediaFeatureFlagProvider;
    private final Provider notifLayoutInflaterFactoryProvider;
    private final Provider remoteInputManagerProvider;
    private final Provider remoteViewCacheProvider;
    private final Provider smartRepliesInflaterProvider;

    public NotificationContentInflater_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9) {
        this.remoteViewCacheProvider = provider;
        this.remoteInputManagerProvider = provider2;
        this.conversationProcessorProvider = provider3;
        this.mediaFeatureFlagProvider = provider4;
        this.inflationExecutorProvider = provider5;
        this.smartRepliesInflaterProvider = provider6;
        this.notifLayoutInflaterFactoryProvider = provider7;
        this.headsUpStyleProvider = provider8;
        this.loggerProvider = provider9;
    }

    public static NotificationContentInflater_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7, Provider provider8, Provider provider9) {
        return new NotificationContentInflater_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9);
    }

    public static NotificationContentInflater newInstance(NotifRemoteViewCache notifRemoteViewCache, NotificationRemoteInputManager notificationRemoteInputManager, ConversationNotificationProcessor conversationNotificationProcessor, MediaFeatureFlag mediaFeatureFlag, Executor executor, SmartReplyStateInflater smartReplyStateInflater, NotifLayoutInflaterFactory.Provider provider, HeadsUpStyleProvider headsUpStyleProvider, NotificationContentInflaterLogger notificationContentInflaterLogger) {
        return new NotificationContentInflater(notifRemoteViewCache, notificationRemoteInputManager, conversationNotificationProcessor, mediaFeatureFlag, executor, smartReplyStateInflater, provider, headsUpStyleProvider, notificationContentInflaterLogger);
    }

    @Override // javax.inject.Provider
    public NotificationContentInflater get() {
        return newInstance((NotifRemoteViewCache) this.remoteViewCacheProvider.get(), (NotificationRemoteInputManager) this.remoteInputManagerProvider.get(), (ConversationNotificationProcessor) this.conversationProcessorProvider.get(), (MediaFeatureFlag) this.mediaFeatureFlagProvider.get(), (Executor) this.inflationExecutorProvider.get(), (SmartReplyStateInflater) this.smartRepliesInflaterProvider.get(), (NotifLayoutInflaterFactory.Provider) this.notifLayoutInflaterFactoryProvider.get(), (HeadsUpStyleProvider) this.headsUpStyleProvider.get(), (NotificationContentInflaterLogger) this.loggerProvider.get());
    }
}
