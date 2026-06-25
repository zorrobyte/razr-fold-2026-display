package com.android.systemui.statusbar.notification.row;

import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import java.util.Set;
import javax.inject.Provider;
import kotlin.collections.SetsKt;

/* JADX INFO: compiled from: NotifRemoteViewsFactoryContainer.kt */
/* JADX INFO: loaded from: classes.dex */
public final class NotifRemoteViewsFactoryContainerImpl implements NotifRemoteViewsFactoryContainer {
    private final Set factories;

    public NotifRemoteViewsFactoryContainerImpl(FeatureFlags featureFlags, PrecomputedTextViewFactory precomputedTextViewFactory, BigPictureLayoutInflaterFactory bigPictureLayoutInflaterFactory, NotificationOptimizedLinearLayoutFactory notificationOptimizedLinearLayoutFactory, Provider provider) {
        featureFlags.getClass();
        precomputedTextViewFactory.getClass();
        bigPictureLayoutInflaterFactory.getClass();
        notificationOptimizedLinearLayoutFactory.getClass();
        provider.getClass();
        Set setCreateSetBuilder = SetsKt.createSetBuilder();
        setCreateSetBuilder.add(precomputedTextViewFactory);
        if (featureFlags.isEnabled(Flags.BIGPICTURE_NOTIFICATION_LAZY_LOADING)) {
            setCreateSetBuilder.add(bigPictureLayoutInflaterFactory);
        }
        setCreateSetBuilder.add(notificationOptimizedLinearLayoutFactory);
        Object obj = provider.get();
        obj.getClass();
        setCreateSetBuilder.add(obj);
        this.factories = SetsKt.build(setCreateSetBuilder);
    }

    @Override // com.android.systemui.statusbar.notification.row.NotifRemoteViewsFactoryContainer
    public Set getFactories() {
        return this.factories;
    }
}
