package com.android.systemui.statusbar.notification.row;

import android.util.ArrayMap;
import android.util.ArraySet;
import androidx.core.os.CancellationSignal;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import com.android.systemui.statusbar.notification.row.BindRequester;
import com.android.systemui.statusbar.notification.row.BindStage;
import com.android.systemui.statusbar.notification.row.NotifBindPipeline;
import com.android.systemui.statusbar.policy.CallbackController;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes.dex */
public final class NotifBindPipeline {
    private final NotifCollectionListener mCollectionListener;
    private final NotifBindPipelineLogger mLogger;
    private BindStage mStage;
    private final Processor mStartProcessor;
    private final Map mBindEntries = new ArrayMap();
    private final List mScratchCallbacksList = new ArrayList();

    public interface BindCallback {
        void onBindFinished(NotificationEntry notificationEntry);
    }

    class BindEntry {
        public final Set callbacks;
        public boolean invalidated;
        public ExpandableNotificationRow row;

        private BindEntry(NotifBindPipeline notifBindPipeline) {
            this.callbacks = new ArraySet();
        }
    }

    NotifBindPipeline(CommonNotifCollection commonNotifCollection, NotifBindPipelineLogger notifBindPipelineLogger, NotificationEntryProcessorFactory notificationEntryProcessorFactory) {
        NotifCollectionListener notifCollectionListener = new NotifCollectionListener() { // from class: com.android.systemui.statusbar.notification.row.NotifBindPipeline.1
            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public void onEntryCleanUp(NotificationEntry notificationEntry) {
                ExpandableNotificationRow expandableNotificationRow = ((BindEntry) NotifBindPipeline.this.mBindEntries.remove(notificationEntry)).row;
                if (expandableNotificationRow != null) {
                    NotifBindPipeline.this.mStage.abortStage(notificationEntry, expandableNotificationRow);
                }
                NotifBindPipeline.this.mStage.deleteStageParams(notificationEntry);
                NotifBindPipeline.this.mStartProcessor.cancel(notificationEntry);
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public void onEntryInit(NotificationEntry notificationEntry) {
                NotifBindPipeline.this.mBindEntries.put(notificationEntry, new BindEntry());
                NotifBindPipeline.this.mStage.createStageParams(notificationEntry);
            }
        };
        this.mCollectionListener = notifCollectionListener;
        commonNotifCollection.addCollectionListener(notifCollectionListener);
        this.mLogger = notifBindPipelineLogger;
        this.mStartProcessor = notificationEntryProcessorFactory.create(new Consumer() { // from class: com.android.systemui.statusbar.notification.row.NotifBindPipeline$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.startPipeline((NotificationEntry) obj);
            }
        });
    }

    private BindEntry getBindEntry(NotificationEntry notificationEntry) {
        return (BindEntry) this.mBindEntries.get(notificationEntry);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onBindRequested(NotificationEntry notificationEntry, CancellationSignal cancellationSignal, final BindCallback bindCallback) {
        BindEntry bindEntry = getBindEntry(notificationEntry);
        if (bindEntry == null) {
            return;
        }
        bindEntry.invalidated = true;
        if (bindCallback != null) {
            final Set set = bindEntry.callbacks;
            set.add(bindCallback);
            cancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener() { // from class: com.android.systemui.statusbar.notification.row.NotifBindPipeline$$ExternalSyntheticLambda3
                @Override // androidx.core.os.CancellationSignal.OnCancelListener
                public final void onCancel() {
                    set.remove(bindCallback);
                }
            });
        }
        requestPipelineRun(notificationEntry);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: onPipelineComplete, reason: merged with bridge method [inline-methods] */
    public void lambda$startPipeline$1(NotificationEntry notificationEntry) {
        BindEntry bindEntry = getBindEntry(notificationEntry);
        Set set = bindEntry.callbacks;
        this.mLogger.logFinishedPipeline(notificationEntry, set.size());
        bindEntry.invalidated = false;
        this.mScratchCallbacksList.addAll(set);
        set.clear();
        for (int i = 0; i < this.mScratchCallbacksList.size(); i++) {
            ((BindCallback) this.mScratchCallbacksList.get(i)).onBindFinished(notificationEntry);
        }
        this.mScratchCallbacksList.clear();
    }

    private void requestPipelineRun(NotificationEntry notificationEntry) {
        this.mLogger.logRequestPipelineRun(notificationEntry);
        ExpandableNotificationRow expandableNotificationRow = getBindEntry(notificationEntry).row;
        if (expandableNotificationRow == null) {
            this.mLogger.logRequestPipelineRowNotSet(notificationEntry);
        } else {
            this.mStage.abortStage(notificationEntry, expandableNotificationRow);
            this.mStartProcessor.request(notificationEntry);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startPipeline(NotificationEntry notificationEntry) {
        this.mLogger.logStartPipeline(notificationEntry);
        if (this.mStage == null) {
            throw new IllegalStateException("No stage was ever set on the pipeline");
        }
        this.mStage.executeStage(notificationEntry, ((BindEntry) this.mBindEntries.get(notificationEntry)).row, new BindStage.StageCallback() { // from class: com.android.systemui.statusbar.notification.row.NotifBindPipeline$$ExternalSyntheticLambda2
            @Override // com.android.systemui.statusbar.notification.row.BindStage.StageCallback
            public final void onStageFinished(NotificationEntry notificationEntry2) {
                this.f$0.lambda$startPipeline$1(notificationEntry2);
            }
        });
    }

    public void attach(CallbackController callbackController) {
        callbackController.addCallback(this.mCollectionListener);
    }

    public void detach(CallbackController callbackController) {
        callbackController.removeCallback(this.mCollectionListener);
    }

    public void manageRow(NotificationEntry notificationEntry, ExpandableNotificationRow expandableNotificationRow) {
        this.mLogger.logManagedRow(notificationEntry);
        this.mLogger.logManagedRow(notificationEntry);
        BindEntry bindEntry = getBindEntry(notificationEntry);
        if (bindEntry == null) {
            return;
        }
        bindEntry.row = expandableNotificationRow;
        if (bindEntry.invalidated) {
            requestPipelineRun(notificationEntry);
        }
    }

    public void setStage(BindStage bindStage) {
        this.mLogger.logStageSet(bindStage.getClass().getName());
        this.mStage = bindStage;
        bindStage.setBindRequestListener(new BindRequester.BindRequestListener() { // from class: com.android.systemui.statusbar.notification.row.NotifBindPipeline$$ExternalSyntheticLambda1
            @Override // com.android.systemui.statusbar.notification.row.BindRequester.BindRequestListener
            public final void onBindRequest(NotificationEntry notificationEntry, CancellationSignal cancellationSignal, NotifBindPipeline.BindCallback bindCallback) {
                this.f$0.onBindRequested(notificationEntry, cancellationSignal, bindCallback);
            }
        });
    }
}
