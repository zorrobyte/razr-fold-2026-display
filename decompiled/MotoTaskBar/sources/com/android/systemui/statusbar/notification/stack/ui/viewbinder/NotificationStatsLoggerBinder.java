package com.android.systemui.statusbar.notification.stack.ui.viewbinder;

import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.ui.view.NotificationStatsLogger;
import com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationLoggerViewModel;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

/* JADX INFO: compiled from: NotificationStatsLoggerBinder.kt */
/* JADX INFO: loaded from: classes.dex */
public final class NotificationStatsLoggerBinder {
    public static final NotificationStatsLoggerBinder INSTANCE = new NotificationStatsLoggerBinder();

    private NotificationStatsLoggerBinder() {
    }

    public final Object bindLogger(NotificationStackScrollLayout notificationStackScrollLayout, NotificationStatsLogger notificationStatsLogger, NotificationLoggerViewModel notificationLoggerViewModel, Continuation continuation) {
        return Unit.INSTANCE;
    }
}
