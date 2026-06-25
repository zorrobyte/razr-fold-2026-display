package com.android.systemui.statusbar.notification.row;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.BindStage;
import com.android.systemui.statusbar.notification.row.NotificationRowContentBinder;

/* JADX INFO: loaded from: classes.dex */
public class RowContentBindStage extends BindStage {
    private final NotificationRowContentBinder mBinder;
    private final RowContentBindStageLogger mLogger;
    private final NotifInflationErrorManager mNotifInflationErrorManager;

    RowContentBindStage(NotificationRowContentBinder notificationRowContentBinder, NotifInflationErrorManager notifInflationErrorManager, RowContentBindStageLogger rowContentBindStageLogger) {
        this.mBinder = notificationRowContentBinder;
        this.mNotifInflationErrorManager = notifInflationErrorManager;
        this.mLogger = rowContentBindStageLogger;
    }

    @Override // com.android.systemui.statusbar.notification.row.BindStage
    protected void abortStage(NotificationEntry notificationEntry, ExpandableNotificationRow expandableNotificationRow) {
        if (this.mBinder.cancelBind(notificationEntry, expandableNotificationRow)) {
            this.mLogger.logAbortStageCancelledBind(notificationEntry);
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.BindStage
    protected void executeStage(NotificationEntry notificationEntry, ExpandableNotificationRow expandableNotificationRow, final BindStage.StageCallback stageCallback) {
        RowContentBindParams rowContentBindParams = (RowContentBindParams) getStageParams(notificationEntry);
        this.mLogger.logExecutingStage(notificationEntry, rowContentBindParams);
        int contentViews = rowContentBindParams.getContentViews();
        int dirtyContentViews = rowContentBindParams.getDirtyContentViews() & contentViews;
        this.mBinder.unbindContent(notificationEntry, expandableNotificationRow, contentViews ^ 127);
        NotificationRowContentBinder.BindParams bindParams = new NotificationRowContentBinder.BindParams();
        bindParams.isMinimized = rowContentBindParams.useMinimized();
        bindParams.usesIncreasedHeight = rowContentBindParams.useIncreasedHeight();
        bindParams.usesIncreasedHeadsUpHeight = rowContentBindParams.useIncreasedHeadsUpHeight();
        boolean zNeedsReinflation = rowContentBindParams.needsReinflation();
        NotificationRowContentBinder.InflationCallback inflationCallback = new NotificationRowContentBinder.InflationCallback() { // from class: com.android.systemui.statusbar.notification.row.RowContentBindStage.1
            @Override // com.android.systemui.statusbar.notification.row.NotificationRowContentBinder.InflationCallback
            public void handleInflationException(NotificationEntry notificationEntry2, Exception exc) {
                if (notificationEntry2.isDeskHeadsUp()) {
                    return;
                }
                RowContentBindStage.this.mNotifInflationErrorManager.setInflationError(notificationEntry2, exc);
            }

            @Override // com.android.systemui.statusbar.notification.row.NotificationRowContentBinder.InflationCallback
            public void onAsyncInflationFinished(NotificationEntry notificationEntry2) {
                if (!notificationEntry2.isDeskHeadsUp()) {
                    RowContentBindStage.this.mNotifInflationErrorManager.clearInflationError(notificationEntry2);
                }
                ((RowContentBindParams) RowContentBindStage.this.getStageParams(notificationEntry2)).clearDirtyContentViews();
                stageCallback.onStageFinished(notificationEntry2);
            }
        };
        this.mBinder.cancelBind(notificationEntry, expandableNotificationRow);
        this.mBinder.bindContent(notificationEntry, expandableNotificationRow, dirtyContentViews, bindParams, zNeedsReinflation, inflationCallback);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.systemui.statusbar.notification.row.BindStage
    public RowContentBindParams newStageParams() {
        return new RowContentBindParams();
    }
}
