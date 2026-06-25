package com.android.systemui.statusbar.notification.collection.inflation;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.util.ListenerSet;

/* JADX INFO: compiled from: BindEventManager.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class BindEventManager {
    private final ListenerSet listeners = new ListenerSet();

    /* JADX INFO: compiled from: BindEventManager.kt */
    public interface Listener {
        void onViewBound(NotificationEntry notificationEntry);
    }

    public final boolean addListener(Listener listener) {
        listener.getClass();
        return this.listeners.addIfAbsent(listener);
    }

    protected final ListenerSet getListeners() {
        return this.listeners;
    }
}
