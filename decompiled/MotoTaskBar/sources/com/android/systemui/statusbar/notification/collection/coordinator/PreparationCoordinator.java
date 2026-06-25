package com.android.systemui.statusbar.notification.collection.coordinator;

import android.os.RemoteException;
import android.os.Trace;
import android.service.notification.StatusBarNotification;
import android.util.ArrayMap;
import android.util.ArraySet;
import com.android.internal.statusbar.IStatusBarService;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.inflation.BindEventManagerImpl;
import com.android.systemui.statusbar.notification.collection.inflation.NotifInflater;
import com.android.systemui.statusbar.notification.collection.inflation.NotifUiAdjustment;
import com.android.systemui.statusbar.notification.collection.inflation.NotifUiAdjustmentProvider;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeFinalizeFilterListener;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import com.android.systemui.statusbar.notification.collection.render.NotifViewBarn;
import com.android.systemui.statusbar.notification.collection.render.NotifViewController;
import com.android.systemui.statusbar.notification.row.NotifInflationErrorManager;
import com.android.systemui.statusbar.notification.row.shared.AsyncGroupHeaderViewInflation;
import com.android.systemui.statusbar.notification.row.shared.AsyncHybridViewInflation;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class PreparationCoordinator implements Coordinator {
    private final NotifUiAdjustmentProvider mAdjustmentProvider;
    private final BindEventManagerImpl mBindEventManager;
    private final int mChildBindCutoff;
    private final ArraySet mInflatingNotifs;
    private final ArrayMap mInflationAdjustments;
    private final NotifInflationErrorManager.NotifInflationErrorListener mInflationErrorListener;
    private final ArrayMap mInflationStates;
    private final PreparationCoordinatorLogger mLogger;
    private final long mMaxGroupInflationDelay;
    private final NotifCollectionListener mNotifCollectionListener;
    private final NotifInflationErrorManager mNotifErrorManager;
    private final NotifInflater mNotifInflater;
    private final NotifFilter mNotifInflatingFilter;
    private final NotifFilter mNotifInflationErrorFilter;
    private final IStatusBarService mStatusBarService;
    private final NotifViewBarn mViewBarn;

    public PreparationCoordinator(PreparationCoordinatorLogger preparationCoordinatorLogger, NotifInflater notifInflater, NotifInflationErrorManager notifInflationErrorManager, NotifViewBarn notifViewBarn, NotifUiAdjustmentProvider notifUiAdjustmentProvider, IStatusBarService iStatusBarService, BindEventManagerImpl bindEventManagerImpl) {
        this(preparationCoordinatorLogger, notifInflater, notifInflationErrorManager, notifViewBarn, notifUiAdjustmentProvider, iStatusBarService, bindEventManagerImpl, 9, 500L);
    }

    PreparationCoordinator(PreparationCoordinatorLogger preparationCoordinatorLogger, NotifInflater notifInflater, NotifInflationErrorManager notifInflationErrorManager, NotifViewBarn notifViewBarn, NotifUiAdjustmentProvider notifUiAdjustmentProvider, IStatusBarService iStatusBarService, BindEventManagerImpl bindEventManagerImpl, int i, long j) {
        this.mInflationStates = new ArrayMap();
        this.mInflationAdjustments = new ArrayMap();
        this.mInflatingNotifs = new ArraySet();
        this.mNotifCollectionListener = new NotifCollectionListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.PreparationCoordinator.1
            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public void onEntryCleanUp(NotificationEntry notificationEntry) {
                PreparationCoordinator.this.mInflationStates.remove(notificationEntry);
                PreparationCoordinator.this.mViewBarn.removeViewForEntry(notificationEntry);
                PreparationCoordinator.this.mInflationAdjustments.remove(notificationEntry);
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public void onEntryInit(NotificationEntry notificationEntry) {
                PreparationCoordinator.this.mInflationStates.put(notificationEntry, 0);
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public void onEntryRemoved(NotificationEntry notificationEntry, int i2) {
                PreparationCoordinator.this.abortInflation(notificationEntry, "entryRemoved reason=" + i2);
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public void onEntryUpdated(NotificationEntry notificationEntry) {
                PreparationCoordinator.this.abortInflation(notificationEntry, "entryUpdated");
                int inflationState = PreparationCoordinator.this.getInflationState(notificationEntry);
                if (inflationState == 1) {
                    PreparationCoordinator.this.mInflationStates.put(notificationEntry, 2);
                } else if (inflationState == -1) {
                    PreparationCoordinator.this.mInflationStates.put(notificationEntry, 0);
                }
            }
        };
        this.mNotifInflationErrorFilter = new NotifFilter("PreparationCoordinatorInflationError") { // from class: com.android.systemui.statusbar.notification.collection.coordinator.PreparationCoordinator.2
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter
            public boolean shouldFilterOut(NotificationEntry notificationEntry, long j2) {
                return PreparationCoordinator.this.getInflationState(notificationEntry) == -1;
            }
        };
        this.mNotifInflatingFilter = new NotifFilter("PreparationCoordinatorInflating") { // from class: com.android.systemui.statusbar.notification.collection.coordinator.PreparationCoordinator.3
            private final Map mIsDelayedGroupCache = new ArrayMap();

            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.Pluggable
            public void onCleanup() {
                this.mIsDelayedGroupCache.clear();
            }

            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter
            public boolean shouldFilterOut(NotificationEntry notificationEntry, long j2) {
                GroupEntry parent = notificationEntry.getParent();
                parent.getClass();
                Boolean boolValueOf = (Boolean) this.mIsDelayedGroupCache.get(parent);
                if (boolValueOf == null) {
                    boolValueOf = Boolean.valueOf(PreparationCoordinator.this.shouldWaitForGroupToInflate(parent, j2));
                    this.mIsDelayedGroupCache.put(parent, boolValueOf);
                }
                return !PreparationCoordinator.this.isInflated(notificationEntry) || boolValueOf.booleanValue();
            }
        };
        this.mInflationErrorListener = new NotifInflationErrorManager.NotifInflationErrorListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.PreparationCoordinator.4
            @Override // com.android.systemui.statusbar.notification.row.NotifInflationErrorManager.NotifInflationErrorListener
            public void onNotifInflationError(NotificationEntry notificationEntry, Exception exc) {
                PreparationCoordinator.this.mViewBarn.removeViewForEntry(notificationEntry);
                PreparationCoordinator.this.mInflationStates.put(notificationEntry, -1);
                try {
                    StatusBarNotification sbn = notificationEntry.getSbn();
                    PreparationCoordinator.this.mStatusBarService.onNotificationError(sbn.getPackageName(), sbn.getTag(), sbn.getId(), sbn.getUid(), sbn.getInitialPid(), exc.getMessage(), sbn.getUser().getIdentifier());
                } catch (RemoteException unused) {
                }
                PreparationCoordinator.this.mNotifInflationErrorFilter.invalidateList("onNotifInflationError for " + NotificationUtils.logKey(notificationEntry));
            }

            @Override // com.android.systemui.statusbar.notification.row.NotifInflationErrorManager.NotifInflationErrorListener
            public void onNotifInflationErrorCleared(NotificationEntry notificationEntry) {
                PreparationCoordinator.this.mNotifInflationErrorFilter.invalidateList("onNotifInflationErrorCleared for " + NotificationUtils.logKey(notificationEntry));
            }
        };
        this.mLogger = preparationCoordinatorLogger;
        this.mNotifInflater = notifInflater;
        this.mNotifErrorManager = notifInflationErrorManager;
        this.mViewBarn = notifViewBarn;
        this.mAdjustmentProvider = notifUiAdjustmentProvider;
        this.mStatusBarService = iStatusBarService;
        this.mChildBindCutoff = i;
        this.mMaxGroupInflationDelay = j;
        this.mBindEventManager = bindEventManagerImpl;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void abortInflation(NotificationEntry notificationEntry, String str) {
        boolean zAbortInflation = this.mNotifInflater.abortInflation(notificationEntry);
        boolean zRemove = this.mInflatingNotifs.remove(notificationEntry);
        if (zAbortInflation || zRemove) {
            this.mLogger.logInflationAborted(notificationEntry, str);
        }
    }

    private void freeNotifViews(NotificationEntry notificationEntry, String str) {
        this.mLogger.logFreeNotifViews(notificationEntry, str);
        this.mViewBarn.removeViewForEntry(notificationEntry);
        this.mNotifInflater.releaseViews(notificationEntry);
        this.mInflationStates.put(notificationEntry, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getInflationState(NotificationEntry notificationEntry) {
        Integer num = (Integer) this.mInflationStates.get(notificationEntry);
        num.getClass();
        return num.intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void inflateAllRequiredViews(List list) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            ListEntry listEntry = (ListEntry) list.get(i);
            if (listEntry instanceof GroupEntry) {
                inflateRequiredGroupViews((GroupEntry) listEntry);
            } else {
                inflateRequiredNotifViews((NotificationEntry) listEntry);
            }
        }
    }

    private void inflateEntry(NotificationEntry notificationEntry, NotifUiAdjustment notifUiAdjustment, String str) {
        Trace.beginSection("PrepCoord.inflateEntry");
        abortInflation(notificationEntry, str);
        this.mInflationAdjustments.put(notificationEntry, notifUiAdjustment);
        this.mInflatingNotifs.add(notificationEntry);
        this.mNotifInflater.inflateViews(notificationEntry, getInflaterParams(notifUiAdjustment, str), new PreparationCoordinator$$ExternalSyntheticLambda0(this));
        Trace.endSection();
    }

    private void inflateRequiredGroupViews(GroupEntry groupEntry) {
        NotificationEntry summary = groupEntry.getSummary();
        if (summary != null && AsyncGroupHeaderViewInflation.isEnabled()) {
            summary.markAsGroupSummary();
        }
        List children = groupEntry.getChildren();
        inflateRequiredNotifViews(summary);
        for (int i = 0; i < children.size(); i++) {
            NotificationEntry notificationEntry = (NotificationEntry) children.get(i);
            if (AsyncHybridViewInflation.isEnabled()) {
                notificationEntry.markAsGroupChild();
            }
            if (i < this.mChildBindCutoff) {
                inflateRequiredNotifViews(notificationEntry);
            } else {
                if (this.mInflatingNotifs.contains(notificationEntry)) {
                    abortInflation(notificationEntry, "Past last visible group child");
                }
                if (isInflated(notificationEntry)) {
                    freeNotifViews(notificationEntry, "Past last visible group child");
                }
            }
        }
    }

    private void inflateRequiredNotifViews(NotificationEntry notificationEntry) {
        NotifUiAdjustment notifUiAdjustmentCalculateAdjustment = this.mAdjustmentProvider.calculateAdjustment(notificationEntry);
        if (this.mInflatingNotifs.contains(notificationEntry)) {
            if (needToReinflate(notificationEntry, notifUiAdjustmentCalculateAdjustment, "Inflating notification has no adjustments")) {
                inflateEntry(notificationEntry, notifUiAdjustmentCalculateAdjustment, "adjustment changed while inflating");
                return;
            }
            return;
        }
        int iIntValue = ((Integer) this.mInflationStates.get(notificationEntry)).intValue();
        if (iIntValue == -1) {
            if (needToReinflate(notificationEntry, notifUiAdjustmentCalculateAdjustment, null)) {
                inflateEntry(notificationEntry, notifUiAdjustmentCalculateAdjustment, "adjustment changed after error");
            }
        } else {
            if (iIntValue == 0) {
                inflateEntry(notificationEntry, notifUiAdjustmentCalculateAdjustment, "entryAdded");
                return;
            }
            if (iIntValue != 1) {
                if (iIntValue != 2) {
                    return;
                }
                rebind(notificationEntry, notifUiAdjustmentCalculateAdjustment, "entryUpdated");
            } else if (needToReinflate(notificationEntry, notifUiAdjustmentCalculateAdjustment, "Fully inflated notification has no adjustments")) {
                rebind(notificationEntry, notifUiAdjustmentCalculateAdjustment, "adjustment changed after inflated");
            }
        }
    }

    private boolean isBeyondGroupInitializationWindow(GroupEntry groupEntry, long j) {
        return j - groupEntry.getCreationTime() > this.mMaxGroupInflationDelay;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isInflated(NotificationEntry notificationEntry) {
        int inflationState = getInflationState(notificationEntry);
        return inflationState == 1 || inflationState == 2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$attach$0() {
        this.mNotifInflatingFilter.invalidateList("adjustmentProviderChanged");
    }

    private boolean needToReinflate(NotificationEntry notificationEntry, NotifUiAdjustment notifUiAdjustment, String str) {
        NotifUiAdjustment notifUiAdjustment2 = (NotifUiAdjustment) this.mInflationAdjustments.get(notificationEntry);
        if (notifUiAdjustment2 != null) {
            return NotifUiAdjustment.needReinflate(notifUiAdjustment2, notifUiAdjustment);
        }
        if (str == null) {
            return true;
        }
        throw new IllegalStateException(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onInflationFinished(NotificationEntry notificationEntry, NotifViewController notifViewController) {
        this.mLogger.logNotifInflated(notificationEntry);
        this.mInflatingNotifs.remove(notificationEntry);
        this.mViewBarn.registerViewForEntry(notificationEntry, notifViewController);
        this.mInflationStates.put(notificationEntry, 1);
        this.mBindEventManager.notifyViewBound(notificationEntry);
        this.mNotifInflatingFilter.invalidateList("onInflationFinished for " + NotificationUtils.logKey(notificationEntry));
    }

    private void rebind(NotificationEntry notificationEntry, NotifUiAdjustment notifUiAdjustment, String str) {
        this.mInflationAdjustments.put(notificationEntry, notifUiAdjustment);
        this.mInflatingNotifs.add(notificationEntry);
        this.mNotifInflater.rebindViews(notificationEntry, getInflaterParams(notifUiAdjustment, str), new PreparationCoordinator$$ExternalSyntheticLambda0(this));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean shouldWaitForGroupToInflate(GroupEntry groupEntry, long j) {
        if (groupEntry != GroupEntry.ROOT_ENTRY && !groupEntry.wasAttachedInPreviousPass()) {
            if (isBeyondGroupInitializationWindow(groupEntry, j)) {
                this.mLogger.logGroupInflationTookTooLong(groupEntry);
                return false;
            }
            if (groupEntry.getSummary() != null && !isInflated(groupEntry.getSummary())) {
                this.mLogger.logDelayingGroupRelease(groupEntry, groupEntry.getSummary());
                return true;
            }
            for (NotificationEntry notificationEntry : groupEntry.getChildren()) {
                if (this.mInflatingNotifs.contains(notificationEntry) && !notificationEntry.wasAttachedInPreviousPass()) {
                    this.mLogger.logDelayingGroupRelease(groupEntry, notificationEntry);
                    return true;
                }
            }
            this.mLogger.logDoneWaitingForGroupInflation(groupEntry);
        }
        return false;
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        this.mNotifErrorManager.addInflationErrorListener(this.mInflationErrorListener);
        this.mAdjustmentProvider.addDirtyListener(new Runnable() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.PreparationCoordinator$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$attach$0();
            }
        });
        notifPipeline.addCollectionListener(this.mNotifCollectionListener);
        notifPipeline.addOnBeforeFinalizeFilterListener(new OnBeforeFinalizeFilterListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.PreparationCoordinator$$ExternalSyntheticLambda2
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeFinalizeFilterListener
            public final void onBeforeFinalizeFilter(List list) {
                this.f$0.inflateAllRequiredViews(list);
            }
        });
        notifPipeline.addFinalizeFilter(this.mNotifInflationErrorFilter);
        notifPipeline.addFinalizeFilter(this.mNotifInflatingFilter);
    }

    NotifInflater.Params getInflaterParams(NotifUiAdjustment notifUiAdjustment, String str) {
        return new NotifInflater.Params(notifUiAdjustment.isMinimized(), str, notifUiAdjustment.isSnoozeEnabled(), notifUiAdjustment.isChildInGroup(), notifUiAdjustment.isGroupSummary(), notifUiAdjustment.getNeedsRedaction());
    }
}
