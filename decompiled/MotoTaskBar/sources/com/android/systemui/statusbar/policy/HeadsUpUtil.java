package com.android.systemui.statusbar.policy;

import android.util.Log;
import android.view.View;
import com.android.systemui.res.R$id;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.util.Compile;

/* JADX INFO: loaded from: classes.dex */
public abstract class HeadsUpUtil {
    private static final boolean LOG_DEBUG;
    private static final int TAG_CLICKED_NOTIFICATION = R$id.is_clicked_heads_up_tag;

    static {
        LOG_DEBUG = Compile.IS_DEBUG && Log.isLoggable("HeadsUpUtil", 3);
    }

    private static String getViewKey(View view) {
        if (!(view instanceof ExpandableNotificationRow)) {
            return "(not a row)";
        }
        NotificationEntry entry = ((ExpandableNotificationRow) view).getEntry();
        if (entry == null) {
            return "(null entry)";
        }
        String key = entry.getKey();
        return key == null ? "(null key)" : key;
    }

    public static boolean isClickedHeadsUpNotification(View view) {
        Boolean bool = (Boolean) view.getTag(TAG_CLICKED_NOTIFICATION);
        return bool != null && bool.booleanValue();
    }

    private static void logTagClickedNotificationChanged(View view, boolean z) {
        if (view == null || z == isClickedHeadsUpNotification(view)) {
            return;
        }
        Log.d("HeadsUpUtil", getViewKey(view) + ": TAG_CLICKED_NOTIFICATION set to " + z);
    }

    public static void setNeedsHeadsUpDisappearAnimationAfterClick(View view, boolean z) {
        if (LOG_DEBUG) {
            logTagClickedNotificationChanged(view, z);
        }
        view.setTag(TAG_CLICKED_NOTIFICATION, z ? Boolean.TRUE : null);
    }
}
