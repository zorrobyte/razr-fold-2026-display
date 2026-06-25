package com.android.systemui.statusbar.notification.row;

import android.util.ArrayMap;
import android.util.Log;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public abstract class BindStage extends BindRequester {
    private Map mContentParams = new ArrayMap();

    interface StageCallback {
        void onStageFinished(NotificationEntry notificationEntry);
    }

    protected abstract void abortStage(NotificationEntry notificationEntry, ExpandableNotificationRow expandableNotificationRow);

    final void createStageParams(NotificationEntry notificationEntry) {
        this.mContentParams.put(notificationEntry, newStageParams());
    }

    final void deleteStageParams(NotificationEntry notificationEntry) {
        this.mContentParams.remove(notificationEntry);
    }

    protected abstract void executeStage(NotificationEntry notificationEntry, ExpandableNotificationRow expandableNotificationRow, StageCallback stageCallback);

    public final Object getStageParams(NotificationEntry notificationEntry) {
        Object obj = this.mContentParams.get(notificationEntry);
        if (obj != null) {
            return obj;
        }
        Log.wtf("BindStage", String.format("Entry does not have any stage parameters. key: %s", notificationEntry.getKey()));
        return newStageParams();
    }

    protected abstract Object newStageParams();

    public final Object tryGetStageParams(NotificationEntry notificationEntry) {
        return this.mContentParams.get(notificationEntry);
    }
}
