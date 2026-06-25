package com.android.systemui.statusbar.notification.collection.notifcollection;

import android.os.Handler;
import android.util.ArrayMap;
import android.util.Log;
import com.android.systemui.Dumpable;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifLifetimeExtender;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: SelfTrackingLifetimeExtender.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class SelfTrackingLifetimeExtender implements NotifLifetimeExtender, Dumpable {
    private final boolean debug;
    private NotifLifetimeExtender.OnEndLifetimeExtensionCallback mCallback;
    private boolean mEnding;
    private final ArrayMap mEntriesExtended;
    private final Handler mainHandler;
    private final String name;
    private final String tag;

    public SelfTrackingLifetimeExtender(String str, String str2, boolean z, Handler handler) {
        str.getClass();
        str2.getClass();
        handler.getClass();
        this.tag = str;
        this.name = str2;
        this.debug = z;
        this.mainHandler = handler;
        this.mEntriesExtended = new ArrayMap();
    }

    private final void warnIfEnding() {
        if (this.debug && this.mEnding) {
            Log.w(this.tag, "reentrant code while ending a lifetime extension");
        }
    }

    @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifLifetimeExtender
    public final void cancelLifetimeExtension(NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        if (this.debug) {
            String str = this.tag;
            String str2 = this.name;
            String key = notificationEntry.getKey();
            String key2 = notificationEntry.getKey();
            key2.getClass();
            Log.d(str, str2 + ".cancelLifetimeExtension(key=" + key + ") isExtending=" + isExtending(key2));
        }
        warnIfEnding();
        this.mEntriesExtended.remove(notificationEntry.getKey());
        onCanceledLifetimeExtension(notificationEntry);
    }

    public final void endLifetimeExtension(String str) {
        str.getClass();
        if (this.debug) {
            Log.d(this.tag, this.name + ".endLifetimeExtension(key=" + str + ") isExtending=" + isExtending(str));
        }
        warnIfEnding();
        this.mEnding = true;
        NotificationEntry notificationEntry = (NotificationEntry) this.mEntriesExtended.remove(str);
        if (notificationEntry != null) {
            NotifLifetimeExtender.OnEndLifetimeExtensionCallback onEndLifetimeExtensionCallback = this.mCallback;
            if (onEndLifetimeExtensionCallback == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mCallback");
                onEndLifetimeExtensionCallback = null;
            }
            onEndLifetimeExtensionCallback.onEndLifetimeExtension(this, notificationEntry);
        }
        this.mEnding = false;
    }

    public final void endLifetimeExtensionAfterDelay(final String str, long j) {
        str.getClass();
        if (this.debug) {
            Log.d(this.tag, this.name + ".endLifetimeExtensionAfterDelay(key=" + str + ", delayMillis=" + j + ") isExtending=" + isExtending(str));
        }
        if (isExtending(str)) {
            this.mainHandler.postDelayed(new Runnable() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.SelfTrackingLifetimeExtender.endLifetimeExtensionAfterDelay.1
                @Override // java.lang.Runnable
                public final void run() {
                    SelfTrackingLifetimeExtender.this.endLifetimeExtension(str);
                }
            }, j);
        }
    }

    @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifLifetimeExtender
    public final String getName() {
        return this.name;
    }

    public final boolean isExtending(String str) {
        str.getClass();
        return this.mEntriesExtended.containsKey(str);
    }

    @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifLifetimeExtender
    public final boolean maybeExtendLifetime(NotificationEntry notificationEntry, int i) {
        notificationEntry.getClass();
        boolean zQueryShouldExtendLifetime = queryShouldExtendLifetime(notificationEntry);
        if (this.debug) {
            String str = this.tag;
            String str2 = this.name;
            String key = notificationEntry.getKey();
            String key2 = notificationEntry.getKey();
            key2.getClass();
            Log.d(str, str2 + ".shouldExtendLifetime(key=" + key + ", reason=" + i + ") isExtending=" + isExtending(key2) + " shouldExtend=" + zQueryShouldExtendLifetime);
        }
        warnIfEnding();
        if (zQueryShouldExtendLifetime && this.mEntriesExtended.put(notificationEntry.getKey(), notificationEntry) == null) {
            onStartedLifetimeExtension(notificationEntry);
        }
        return zQueryShouldExtendLifetime;
    }

    public void onCanceledLifetimeExtension(NotificationEntry notificationEntry) {
        notificationEntry.getClass();
    }

    public void onStartedLifetimeExtension(NotificationEntry notificationEntry) {
        notificationEntry.getClass();
    }

    public abstract boolean queryShouldExtendLifetime(NotificationEntry notificationEntry);

    @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifLifetimeExtender
    public final void setCallback(NotifLifetimeExtender.OnEndLifetimeExtensionCallback onEndLifetimeExtensionCallback) {
        onEndLifetimeExtensionCallback.getClass();
        this.mCallback = onEndLifetimeExtensionCallback;
    }
}
