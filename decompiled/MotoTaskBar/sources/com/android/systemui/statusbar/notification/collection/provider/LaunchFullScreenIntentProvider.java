package com.android.systemui.statusbar.notification.collection.provider;

import android.util.Log;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.util.ListenerSet;
import java.util.Iterator;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: LaunchFullScreenIntentProvider.kt */
/* JADX INFO: loaded from: classes.dex */
public final class LaunchFullScreenIntentProvider {
    public static final Companion Companion = new Companion(null);
    private final ListenerSet listeners = new ListenerSet();

    /* JADX INFO: compiled from: LaunchFullScreenIntentProvider.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: compiled from: LaunchFullScreenIntentProvider.kt */
    public interface Listener {
        void onFullScreenIntentRequested(NotificationEntry notificationEntry);
    }

    public final void launchFullScreenIntent(NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        if (this.listeners.isEmpty()) {
            Log.wtf("LaunchFullScreenIntentProvider", "no listeners found when launchFullScreenIntent requested");
        }
        Iterator it = this.listeners.iterator();
        while (it.hasNext()) {
            ((Listener) it.next()).onFullScreenIntentRequested(notificationEntry);
        }
    }

    public final void registerListener(Listener listener) {
        listener.getClass();
        this.listeners.addIfAbsent(listener);
    }
}
