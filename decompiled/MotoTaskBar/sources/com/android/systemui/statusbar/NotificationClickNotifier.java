package com.android.systemui.statusbar;

import android.app.Notification;
import android.os.RemoteException;
import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.statusbar.NotificationVisibility;
import com.android.systemui.util.Assert;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;

/* JADX INFO: compiled from: NotificationClickNotifier.kt */
/* JADX INFO: loaded from: classes.dex */
public final class NotificationClickNotifier {
    private final Executor backgroundExecutor;
    private final IStatusBarService barService;
    private final List listeners;
    private final Executor mainExecutor;

    public NotificationClickNotifier(IStatusBarService iStatusBarService, Executor executor, Executor executor2) {
        iStatusBarService.getClass();
        executor.getClass();
        executor2.getClass();
        this.barService = iStatusBarService;
        this.mainExecutor = executor;
        this.backgroundExecutor = executor2;
        this.listeners = new ArrayList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void notifyListenersAboutInteraction(String str) {
        Iterator it = this.listeners.iterator();
        while (it.hasNext()) {
            ((NotificationInteractionListener) it.next()).onNotificationInteraction(str);
        }
    }

    public final void addNotificationInteractionListener(NotificationInteractionListener notificationInteractionListener) {
        notificationInteractionListener.getClass();
        Assert.isMainThread();
        this.listeners.add(notificationInteractionListener);
    }

    public final IStatusBarService getBarService() {
        return this.barService;
    }

    public final void onNotificationActionClick(final String str, final int i, final Notification.Action action, final NotificationVisibility notificationVisibility, final boolean z) {
        str.getClass();
        action.getClass();
        notificationVisibility.getClass();
        this.backgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.NotificationClickNotifier.onNotificationActionClick.1
            @Override // java.lang.Runnable
            public final void run() {
                try {
                    NotificationClickNotifier.this.getBarService().onNotificationActionClick(str, i, action, notificationVisibility, z);
                } catch (RemoteException unused) {
                }
                NotificationClickNotifier.this.notifyListenersAboutInteraction(str);
            }
        });
    }

    public final void onNotificationClick(final String str, NotificationVisibility notificationVisibility) {
        str.getClass();
        notificationVisibility.getClass();
        try {
            this.barService.onNotificationClick(str, notificationVisibility);
        } catch (RemoteException unused) {
        }
        this.mainExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.NotificationClickNotifier.onNotificationClick.1
            @Override // java.lang.Runnable
            public final void run() {
                NotificationClickNotifier.this.notifyListenersAboutInteraction(str);
            }
        });
    }
}
