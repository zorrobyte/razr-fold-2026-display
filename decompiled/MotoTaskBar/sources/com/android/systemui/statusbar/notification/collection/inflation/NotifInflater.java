package com.android.systemui.statusbar.notification.collection.inflation;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.render.NotifViewController;

/* JADX INFO: compiled from: NotifInflater.kt */
/* JADX INFO: loaded from: classes.dex */
public interface NotifInflater {

    /* JADX INFO: compiled from: NotifInflater.kt */
    public interface InflationCallback {
        void onInflationFinished(NotificationEntry notificationEntry, NotifViewController notifViewController);
    }

    /* JADX INFO: compiled from: NotifInflater.kt */
    public final class Params {
        private final boolean isChildInGroup;
        private final boolean isGroupSummary;
        private final boolean isMinimized;
        private final boolean needsRedaction;
        private final String reason;
        private final boolean showSnooze;

        public Params(boolean z, String str, boolean z2, boolean z3, boolean z4, boolean z5) {
            str.getClass();
            this.isMinimized = z;
            this.reason = str;
            this.showSnooze = z2;
            this.isChildInGroup = z3;
            this.isGroupSummary = z4;
            this.needsRedaction = z5;
        }

        public final boolean getNeedsRedaction() {
            return this.needsRedaction;
        }

        public final String getReason() {
            return this.reason;
        }

        public final boolean getShowSnooze() {
            return this.showSnooze;
        }

        public final boolean isChildInGroup() {
            return this.isChildInGroup;
        }

        public final boolean isGroupSummary() {
            return this.isGroupSummary;
        }

        public final boolean isMinimized() {
            return this.isMinimized;
        }
    }

    boolean abortInflation(NotificationEntry notificationEntry);

    void inflateViews(NotificationEntry notificationEntry, Params params, InflationCallback inflationCallback);

    void rebindViews(NotificationEntry notificationEntry, Params params, InflationCallback inflationCallback);

    void releaseViews(NotificationEntry notificationEntry);
}
