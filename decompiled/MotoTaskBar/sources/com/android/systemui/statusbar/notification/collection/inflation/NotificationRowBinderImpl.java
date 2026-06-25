package com.android.systemui.statusbar.notification.collection.inflation;

import android.content.Context;
import android.view.ViewGroup;
import com.android.internal.util.NotificationMessagingUtil;
import com.android.systemui.fake.FlagsFake;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationPresenter;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.notification.NotificationClicker;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.inflation.NotifInflater;
import com.android.systemui.statusbar.notification.icon.IconManager;
import com.android.systemui.statusbar.notification.row.BigPictureIconManager;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRowController;
import com.android.systemui.statusbar.notification.row.NotifBindPipeline;
import com.android.systemui.statusbar.notification.row.NotificationRowContentBinder;
import com.android.systemui.statusbar.notification.row.RowContentBindParams;
import com.android.systemui.statusbar.notification.row.RowContentBindStage;
import com.android.systemui.statusbar.notification.row.RowInflaterTask;
import com.android.systemui.statusbar.notification.row.dagger.ExpandableNotificationRowComponent;
import com.android.systemui.statusbar.notification.row.shared.AsyncGroupHeaderViewInflation;
import com.android.systemui.statusbar.notification.row.shared.AsyncHybridViewInflation;
import com.android.systemui.statusbar.notification.stack.NotificationListContainer;
import javax.inject.Provider;

/* JADX INFO: loaded from: classes.dex */
public class NotificationRowBinderImpl implements NotificationRowBinder {
    private final Context mContext;
    private final ExpandableNotificationRowComponent.Builder mExpandableNotificationRowComponentBuilder;
    private FeatureFlags mFeatureFlags;
    private final IconManager mIconManager;
    private NotificationListContainer mListContainer;
    private final NotificationRowBinderLogger mLogger;
    private final NotificationMessagingUtil mMessagingUtil;
    private final NotifBindPipeline mNotifBindPipeline;
    private NotificationClicker mNotificationClicker;
    private final NotificationLockscreenUserManager mNotificationLockscreenUserManager;
    private final NotificationRemoteInputManager mNotificationRemoteInputManager;
    private NotificationPresenter mPresenter;
    private final RowContentBindStage mRowContentBindStage;
    private final Provider mRowInflaterTaskProvider;

    public NotificationRowBinderImpl(Context context, NotificationMessagingUtil notificationMessagingUtil, NotificationRemoteInputManager notificationRemoteInputManager, NotificationLockscreenUserManager notificationLockscreenUserManager, NotifBindPipeline notifBindPipeline, RowContentBindStage rowContentBindStage, Provider provider, ExpandableNotificationRowComponent.Builder builder, IconManager iconManager, NotificationRowBinderLogger notificationRowBinderLogger, FeatureFlags featureFlags) {
        this.mContext = context;
        this.mNotifBindPipeline = notifBindPipeline;
        this.mRowContentBindStage = rowContentBindStage;
        this.mMessagingUtil = notificationMessagingUtil;
        this.mNotificationRemoteInputManager = notificationRemoteInputManager;
        this.mNotificationLockscreenUserManager = notificationLockscreenUserManager;
        this.mRowInflaterTaskProvider = provider;
        this.mExpandableNotificationRowComponentBuilder = builder;
        this.mIconManager = iconManager;
        this.mLogger = notificationRowBinderLogger;
        this.mFeatureFlags = featureFlags;
    }

    private void bindRow(NotificationEntry notificationEntry, ExpandableNotificationRow expandableNotificationRow) {
        this.mListContainer.bindRow(expandableNotificationRow);
        this.mNotificationRemoteInputManager.bindRow(expandableNotificationRow);
        notificationEntry.setRow(expandableNotificationRow);
        this.mNotifBindPipeline.manageRow(notificationEntry, expandableNotificationRow);
        this.mPresenter.onBindRow(expandableNotificationRow);
    }

    private void cancelRunningJobs(ExpandableNotificationRow expandableNotificationRow) {
        BigPictureIconManager bigPictureIconManager;
        if (expandableNotificationRow == null || (bigPictureIconManager = expandableNotificationRow.getBigPictureIconManager()) == null) {
            return;
        }
        bigPictureIconManager.cancelJobs();
    }

    private void inflateContentViews(final NotificationEntry notificationEntry, NotifInflater.Params params, final ExpandableNotificationRow expandableNotificationRow, final NotificationRowContentBinder.InflationCallback inflationCallback) {
        final boolean zIsImportantMessaging = this.mMessagingUtil.isImportantMessaging(notificationEntry.getSbn(), notificationEntry.getImportance());
        final boolean zIsMinimized = params.isMinimized();
        expandableNotificationRow.setShowSnooze(params.getShowSnooze());
        RowContentBindParams rowContentBindParams = (RowContentBindParams) this.mRowContentBindStage.getStageParams(notificationEntry);
        rowContentBindParams.requireContentViews(1);
        rowContentBindParams.requireContentViews(2);
        rowContentBindParams.setUseIncreasedCollapsedHeight(zIsImportantMessaging);
        rowContentBindParams.setUseMinimized(zIsMinimized);
        if (!FlagsFake.screenshareNotificationHiding() ? this.mNotificationLockscreenUserManager.needsRedaction(notificationEntry) : params.getNeedsRedaction()) {
            rowContentBindParams.markContentViewsFreeable(8);
        } else {
            rowContentBindParams.requireContentViews(8);
        }
        if (AsyncHybridViewInflation.isEnabled()) {
            if (params.isChildInGroup()) {
                rowContentBindParams.requireContentViews(16);
            } else {
                rowContentBindParams.markContentViewsFreeable(16);
            }
        }
        if (AsyncGroupHeaderViewInflation.isEnabled()) {
            if (params.isGroupSummary()) {
                rowContentBindParams.requireContentViews(32);
                if (zIsMinimized) {
                    rowContentBindParams.requireContentViews(64);
                }
            } else {
                rowContentBindParams.markContentViewsFreeable(32);
                rowContentBindParams.markContentViewsFreeable(64);
            }
        }
        rowContentBindParams.rebindAllContentViews();
        this.mLogger.logRequestingRebind(notificationEntry, params);
        this.mRowContentBindStage.requestRebind(notificationEntry, new NotifBindPipeline.BindCallback() { // from class: com.android.systemui.statusbar.notification.collection.inflation.NotificationRowBinderImpl$$ExternalSyntheticLambda1
            @Override // com.android.systemui.statusbar.notification.row.NotifBindPipeline.BindCallback
            public final void onBindFinished(NotificationEntry notificationEntry2) {
                this.f$0.lambda$inflateContentViews$1(notificationEntry, expandableNotificationRow, zIsImportantMessaging, zIsMinimized, inflationCallback, notificationEntry2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$inflateContentViews$1(NotificationEntry notificationEntry, ExpandableNotificationRow expandableNotificationRow, boolean z, boolean z2, NotificationRowContentBinder.InflationCallback inflationCallback, NotificationEntry notificationEntry2) {
        this.mLogger.logRebindComplete(notificationEntry);
        expandableNotificationRow.setUsesIncreasedCollapsedHeight(z);
        expandableNotificationRow.setIsMinimized(z2);
        if (inflationCallback != null) {
            inflationCallback.onAsyncInflationFinished(notificationEntry2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$inflateViews$0(NotificationEntry notificationEntry, NotifInflater.Params params, NotificationRowContentBinder.InflationCallback inflationCallback, ExpandableNotificationRow expandableNotificationRow) {
        this.mLogger.logInflatedRow(notificationEntry);
        ExpandableNotificationRowComponent expandableNotificationRowComponentBuild = this.mExpandableNotificationRowComponentBuilder.expandableNotificationRow(expandableNotificationRow).notificationEntry(notificationEntry).onExpandClickListener(this.mPresenter).build();
        ExpandableNotificationRowController expandableNotificationRowController = expandableNotificationRowComponentBuild.getExpandableNotificationRowController();
        expandableNotificationRowController.init(notificationEntry);
        notificationEntry.setRowController(expandableNotificationRowController);
        maybeSetBigPictureIconManager(expandableNotificationRow, expandableNotificationRowComponentBuild);
        bindRow(notificationEntry, expandableNotificationRow);
        updateRow(notificationEntry, expandableNotificationRow);
        inflateContentViews(notificationEntry, params, expandableNotificationRow, inflationCallback);
    }

    private void maybeSetBigPictureIconManager(ExpandableNotificationRow expandableNotificationRow, ExpandableNotificationRowComponent expandableNotificationRowComponent) {
        if (this.mFeatureFlags.isEnabled(Flags.BIGPICTURE_NOTIFICATION_LAZY_LOADING)) {
            expandableNotificationRow.setBigPictureIconManager(expandableNotificationRowComponent.getBigPictureIconManager());
        }
    }

    private void updateRow(NotificationEntry notificationEntry, ExpandableNotificationRow expandableNotificationRow) {
        int i = notificationEntry.targetSdk;
        expandableNotificationRow.setLegacy(i >= 9 && i < 21);
        NotificationClicker notificationClicker = this.mNotificationClicker;
        notificationClicker.getClass();
        notificationClicker.register(expandableNotificationRow, notificationEntry.getSbn());
    }

    @Override // com.android.systemui.statusbar.notification.collection.inflation.NotificationRowBinder
    public void inflateViews(final NotificationEntry notificationEntry, final NotifInflater.Params params, final NotificationRowContentBinder.InflationCallback inflationCallback) {
        ViewGroup viewParentForNotification = this.mListContainer.getViewParentForNotification(notificationEntry);
        if (!notificationEntry.rowExists()) {
            this.mLogger.logCreatingRow(notificationEntry, params);
            this.mIconManager.createIcons(notificationEntry);
            this.mLogger.logInflatingRow(notificationEntry);
            ((RowInflaterTask) this.mRowInflaterTaskProvider.get()).inflate(this.mContext, viewParentForNotification, notificationEntry, new RowInflaterTask.RowInflationFinishedListener() { // from class: com.android.systemui.statusbar.notification.collection.inflation.NotificationRowBinderImpl$$ExternalSyntheticLambda0
                @Override // com.android.systemui.statusbar.notification.row.RowInflaterTask.RowInflationFinishedListener
                public final void onInflationFinished(ExpandableNotificationRow expandableNotificationRow) {
                    this.f$0.lambda$inflateViews$0(notificationEntry, params, inflationCallback, expandableNotificationRow);
                }
            });
            return;
        }
        this.mLogger.logUpdatingRow(notificationEntry, params);
        this.mIconManager.updateIcons(notificationEntry, false);
        ExpandableNotificationRow row = notificationEntry.getRow();
        row.reset();
        updateRow(notificationEntry, row);
        inflateContentViews(notificationEntry, params, row, inflationCallback);
    }

    public void releaseViews(NotificationEntry notificationEntry) {
        if (!notificationEntry.rowExists()) {
            this.mLogger.logNotReleasingViewsRowDoesntExist(notificationEntry);
            return;
        }
        this.mLogger.logReleasingViews(notificationEntry);
        cancelRunningJobs(notificationEntry.getRow());
        RowContentBindParams rowContentBindParams = (RowContentBindParams) this.mRowContentBindStage.getStageParams(notificationEntry);
        rowContentBindParams.markContentViewsFreeable(1);
        rowContentBindParams.markContentViewsFreeable(2);
        rowContentBindParams.markContentViewsFreeable(8);
        if (AsyncHybridViewInflation.isEnabled()) {
            rowContentBindParams.markContentViewsFreeable(16);
        }
        this.mRowContentBindStage.requestRebind(notificationEntry, null);
    }

    public void setNotificationClicker(NotificationClicker notificationClicker) {
        this.mNotificationClicker = notificationClicker;
    }

    public void setUpWithPresenter(NotificationPresenter notificationPresenter, NotificationListContainer notificationListContainer) {
        this.mPresenter = notificationPresenter;
        this.mListContainer = notificationListContainer;
        this.mIconManager.attach();
    }
}
