package com.android.systemui.statusbar.notification.row;

import com.android.systemui.flags.FeatureFlags;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class NotifRemoteViewsFactoryContainerImpl_Factory implements Factory {
    private final Provider bigPictureLayoutInflaterFactoryProvider;
    private final Provider featureFlagsProvider;
    private final Provider notificationViewFlipperFactoryProvider;
    private final Provider optimizedLinearLayoutFactoryProvider;
    private final Provider precomputedTextViewFactoryProvider;

    public NotifRemoteViewsFactoryContainerImpl_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        this.featureFlagsProvider = provider;
        this.precomputedTextViewFactoryProvider = provider2;
        this.bigPictureLayoutInflaterFactoryProvider = provider3;
        this.optimizedLinearLayoutFactoryProvider = provider4;
        this.notificationViewFlipperFactoryProvider = provider5;
    }

    public static NotifRemoteViewsFactoryContainerImpl_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        return new NotifRemoteViewsFactoryContainerImpl_Factory(provider, provider2, provider3, provider4, provider5);
    }

    public static NotifRemoteViewsFactoryContainerImpl newInstance(FeatureFlags featureFlags, PrecomputedTextViewFactory precomputedTextViewFactory, BigPictureLayoutInflaterFactory bigPictureLayoutInflaterFactory, NotificationOptimizedLinearLayoutFactory notificationOptimizedLinearLayoutFactory, javax.inject.Provider provider) {
        return new NotifRemoteViewsFactoryContainerImpl(featureFlags, precomputedTextViewFactory, bigPictureLayoutInflaterFactory, notificationOptimizedLinearLayoutFactory, provider);
    }

    @Override // javax.inject.Provider
    public NotifRemoteViewsFactoryContainerImpl get() {
        return newInstance((FeatureFlags) this.featureFlagsProvider.get(), (PrecomputedTextViewFactory) this.precomputedTextViewFactoryProvider.get(), (BigPictureLayoutInflaterFactory) this.bigPictureLayoutInflaterFactoryProvider.get(), (NotificationOptimizedLinearLayoutFactory) this.optimizedLinearLayoutFactoryProvider.get(), this.notificationViewFlipperFactoryProvider);
    }
}
