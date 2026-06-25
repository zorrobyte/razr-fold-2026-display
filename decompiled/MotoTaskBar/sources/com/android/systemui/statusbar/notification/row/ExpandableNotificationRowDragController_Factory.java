package com.android.systemui.statusbar.notification.row;

import android.content.Context;
import com.android.systemui.statusbar.notification.logging.NotificationPanelLogger;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class ExpandableNotificationRowDragController_Factory implements Factory {
    private final Provider contextProvider;
    private final Provider headsUpManagerProvider;
    private final Provider notificationPanelLoggerProvider;

    public ExpandableNotificationRowDragController_Factory(Provider provider, Provider provider2, Provider provider3) {
        this.contextProvider = provider;
        this.headsUpManagerProvider = provider2;
        this.notificationPanelLoggerProvider = provider3;
    }

    public static ExpandableNotificationRowDragController_Factory create(Provider provider, Provider provider2, Provider provider3) {
        return new ExpandableNotificationRowDragController_Factory(provider, provider2, provider3);
    }

    public static ExpandableNotificationRowDragController newInstance(Context context, HeadsUpManager headsUpManager, NotificationPanelLogger notificationPanelLogger) {
        return new ExpandableNotificationRowDragController(context, headsUpManager, notificationPanelLogger);
    }

    @Override // javax.inject.Provider
    public ExpandableNotificationRowDragController get() {
        return newInstance((Context) this.contextProvider.get(), (HeadsUpManager) this.headsUpManagerProvider.get(), (NotificationPanelLogger) this.notificationPanelLoggerProvider.get());
    }
}
