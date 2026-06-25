package com.android.systemui.statusbar.notification.interruption;

import android.util.ArrayMap;
import androidx.core.os.CancellationSignal;
import com.android.internal.util.NotificationMessagingUtil;
import com.android.systemui.statusbar.NotificationPresenter;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.NotifBindPipeline;
import com.android.systemui.statusbar.notification.row.RowContentBindParams;
import com.android.systemui.statusbar.notification.row.RowContentBindStage;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class HeadsUpViewBinder {
    private final HeadsUpViewBinderLogger mLogger;
    private final NotificationMessagingUtil mNotificationMessagingUtil;
    private NotificationPresenter mNotificationPresenter;
    private final Map mOngoingBindCallbacks = new ArrayMap();
    private final RowContentBindStage mStage;

    HeadsUpViewBinder(NotificationMessagingUtil notificationMessagingUtil, RowContentBindStage rowContentBindStage, HeadsUpViewBinderLogger headsUpViewBinderLogger) {
        this.mNotificationMessagingUtil = notificationMessagingUtil;
        this.mStage = rowContentBindStage;
        this.mLogger = headsUpViewBinderLogger;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$bindHeadsUpView$0(NotificationEntry notificationEntry, RowContentBindParams rowContentBindParams, NotifBindPipeline.BindCallback bindCallback, NotificationEntry notificationEntry2) {
        this.mLogger.entryBoundSuccessfully(notificationEntry);
        notificationEntry2.getRow().setUsesIncreasedHeadsUpHeight(rowContentBindParams.useIncreasedHeadsUpHeight());
        this.mOngoingBindCallbacks.remove(notificationEntry);
        if (bindCallback != null) {
            bindCallback.onBindFinished(notificationEntry2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$unbindHeadsUpView$1(NotificationEntry notificationEntry) {
        this.mLogger.entryUnbound(notificationEntry);
    }

    public void abortBindCallback(NotificationEntry notificationEntry) {
        CancellationSignal cancellationSignal = (CancellationSignal) this.mOngoingBindCallbacks.remove(notificationEntry);
        if (cancellationSignal != null) {
            this.mLogger.currentOngoingBindingAborted(notificationEntry);
            cancellationSignal.cancel();
        }
    }

    public void bindHeadsUpView(final NotificationEntry notificationEntry, final NotifBindPipeline.BindCallback bindCallback) {
        final RowContentBindParams rowContentBindParams = (RowContentBindParams) this.mStage.getStageParams(notificationEntry);
        rowContentBindParams.setUseIncreasedHeadsUpHeight(this.mNotificationMessagingUtil.isImportantMessaging(notificationEntry.getSbn(), notificationEntry.getImportance()) && !this.mNotificationPresenter.isPresenterFullyCollapsed());
        rowContentBindParams.requireContentViews(4);
        CancellationSignal cancellationSignalRequestRebind = this.mStage.requestRebind(notificationEntry, new NotifBindPipeline.BindCallback() { // from class: com.android.systemui.statusbar.notification.interruption.HeadsUpViewBinder$$ExternalSyntheticLambda1
            @Override // com.android.systemui.statusbar.notification.row.NotifBindPipeline.BindCallback
            public final void onBindFinished(NotificationEntry notificationEntry2) {
                this.f$0.lambda$bindHeadsUpView$0(notificationEntry, rowContentBindParams, bindCallback, notificationEntry2);
            }
        });
        abortBindCallback(notificationEntry);
        this.mLogger.startBindingHun(notificationEntry);
        this.mOngoingBindCallbacks.put(notificationEntry, cancellationSignalRequestRebind);
    }

    public void setPresenter(NotificationPresenter notificationPresenter) {
        this.mNotificationPresenter = notificationPresenter;
    }

    public void unbindHeadsUpView(NotificationEntry notificationEntry) {
        abortBindCallback(notificationEntry);
        RowContentBindParams rowContentBindParams = (RowContentBindParams) this.mStage.tryGetStageParams(notificationEntry);
        if (rowContentBindParams == null) {
            this.mLogger.entryBindStageParamsNullOnUnbind(notificationEntry);
            return;
        }
        rowContentBindParams.markContentViewsFreeable(4);
        this.mLogger.entryContentViewMarkedFreeable(notificationEntry);
        this.mStage.requestRebind(notificationEntry, new NotifBindPipeline.BindCallback() { // from class: com.android.systemui.statusbar.notification.interruption.HeadsUpViewBinder$$ExternalSyntheticLambda0
            @Override // com.android.systemui.statusbar.notification.row.NotifBindPipeline.BindCallback
            public final void onBindFinished(NotificationEntry notificationEntry2) {
                this.f$0.lambda$unbindHeadsUpView$1(notificationEntry2);
            }
        });
    }
}
