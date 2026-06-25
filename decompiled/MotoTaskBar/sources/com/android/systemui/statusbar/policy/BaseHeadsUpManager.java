package com.android.systemui.statusbar.policy;

import android.app.Notification;
import android.content.Context;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.os.Handler;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.res.R$integer;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.shared.NotificationsHeadsUpRefactor;
import com.android.systemui.util.ListenerSet;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.settings.GlobalSettings;
import com.android.systemui.util.time.SystemClock;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public abstract class BaseHeadsUpManager implements HeadsUpManager {
    protected int mAutoDismissTime;
    private final AvalancheController mAvalancheController;
    protected final Context mContext;
    protected DelayableExecutor mExecutor;
    protected boolean mHasPinnedNotification;
    protected int mMinimumDisplayTime;
    protected int mSnoozeLengthMs;
    private final ArrayMap mSnoozedPackages;
    protected int mStickyForSomeTimeAutoDismissTime;
    protected final SystemClock mSystemClock;
    protected int mTouchAcceptanceDelay;
    private final UiEventLogger mUiEventLogger;
    protected int mUser;
    protected final ListenerSet mListeners = new ListenerSet();
    protected final ArrayMap mHeadsUpEntryMap = new ArrayMap();

    interface FinishTimeUpdater {
        long updateAndGetTimeRemaining();
    }

    public abstract class HeadsUpEntry implements Comparable {
        private Runnable mCancelRemoveRunnable;
        public long mEarliestRemovalTime;
        public NotificationEntry mEntry;
        protected boolean mExpanded;
        public long mPostTime;
        public boolean mRemoteInputActive;
        protected Runnable mRemoveRunnable;
        public boolean mUserActionMayIndirectlyRemove;
        protected boolean mWasUnpinned;

        public HeadsUpEntry() {
            NotificationsHeadsUpRefactor.assertInLegacyMode();
        }

        public HeadsUpEntry(NotificationEntry notificationEntry) {
            setEntry(notificationEntry, createRemoveRunnable(notificationEntry));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ long lambda$removeAsSoonAsPossible$4() {
            return this.mEarliestRemovalTime - BaseHeadsUpManager.this.mSystemClock.elapsedRealtime();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$scheduleAutoRemovalCallback$3(FinishTimeUpdater finishTimeUpdater) {
            long jUpdateAndGetTimeRemaining = finishTimeUpdater.updateAndGetTimeRemaining();
            if (this.mRemoveRunnable == null) {
                Log.wtf("BaseHeadsUpManager", "scheduleAutoRemovalCallback with no callback set");
            } else {
                lambda$cancelAutoRemovalCallbacks$2();
                this.mCancelRemoveRunnable = BaseHeadsUpManager.this.mExecutor.executeDelayed(this.mRemoveRunnable, jUpdateAndGetTimeRemaining);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$updateEntry$0(boolean z) {
            long jElapsedRealtime = BaseHeadsUpManager.this.mSystemClock.elapsedRealtime();
            this.mEarliestRemovalTime = ((long) BaseHeadsUpManager.this.mMinimumDisplayTime) + jElapsedRealtime;
            if (z) {
                this.mPostTime = Math.max(this.mPostTime, jElapsedRealtime);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ long lambda$updateEntry$1() {
            return Math.max(calculateFinishTime() - BaseHeadsUpManager.this.mSystemClock.elapsedRealtime(), BaseHeadsUpManager.this.mMinimumDisplayTime);
        }

        private void setEntry(NotificationEntry notificationEntry, Runnable runnable) {
            this.mEntry = notificationEntry;
            this.mRemoveRunnable = runnable;
            this.mPostTime = calculatePostTime();
            updateEntry(true, "setEntry");
        }

        protected long calculateFinishTime() {
            return this.mPostTime + ((long) getRecommendedHeadsUpTimeoutMs(isStickyForSomeTime() ? BaseHeadsUpManager.this.mStickyForSomeTimeAutoDismissTime : BaseHeadsUpManager.this.mAvalancheController.getDurationMs(this, BaseHeadsUpManager.this.mAutoDismissTime)));
        }

        protected long calculatePostTime() {
            return BaseHeadsUpManager.this.mSystemClock.elapsedRealtime() + ((long) BaseHeadsUpManager.this.mTouchAcceptanceDelay);
        }

        /* JADX INFO: renamed from: cancelAutoRemovalCallbackInternal, reason: merged with bridge method [inline-methods] */
        public boolean lambda$cancelAutoRemovalCallbacks$2() {
            Runnable runnable = this.mCancelRemoveRunnable;
            boolean z = runnable != null;
            if (z) {
                runnable.run();
                this.mCancelRemoveRunnable = null;
            }
            return z;
        }

        public void cancelAutoRemovalCallbacks(String str) {
            Runnable runnable = new Runnable() { // from class: com.android.systemui.statusbar.policy.BaseHeadsUpManager$HeadsUpEntry$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$cancelAutoRemovalCallbacks$2();
                }
            };
            if (!BaseHeadsUpManager.this.isHeadsUpEntry(this.mEntry.getKey())) {
                runnable.run();
                return;
            }
            BaseHeadsUpManager.this.mAvalancheController.update(this, runnable, str + " cancelAutoRemovalCallbacks");
        }

        public int compareNonTimeFields(HeadsUpEntry headsUpEntry) {
            boolean zIsRowPinned = this.mEntry.isRowPinned();
            boolean zIsRowPinned2 = headsUpEntry.mEntry.isRowPinned();
            if (zIsRowPinned && !zIsRowPinned2) {
                return -1;
            }
            if (!zIsRowPinned && zIsRowPinned2) {
                return 1;
            }
            boolean zHasFullScreenIntent = BaseHeadsUpManager.this.hasFullScreenIntent(this.mEntry);
            boolean zHasFullScreenIntent2 = BaseHeadsUpManager.this.hasFullScreenIntent(headsUpEntry.mEntry);
            if (zHasFullScreenIntent && !zHasFullScreenIntent2) {
                return -1;
            }
            if (!zHasFullScreenIntent && zHasFullScreenIntent2) {
                return 1;
            }
            boolean zIsCriticalCallNotif = BaseHeadsUpManager.isCriticalCallNotif(this.mEntry);
            boolean zIsCriticalCallNotif2 = BaseHeadsUpManager.isCriticalCallNotif(headsUpEntry.mEntry);
            if (zIsCriticalCallNotif && !zIsCriticalCallNotif2) {
                return -1;
            }
            if (!zIsCriticalCallNotif && zIsCriticalCallNotif2) {
                return 1;
            }
            boolean z = this.mRemoteInputActive;
            if (!z || headsUpEntry.mRemoteInputActive) {
                return (z || !headsUpEntry.mRemoteInputActive) ? 0 : 1;
            }
            return -1;
        }

        @Override // java.lang.Comparable
        public int compareTo(HeadsUpEntry headsUpEntry) {
            int iCompareNonTimeFields = compareNonTimeFields(headsUpEntry);
            if (iCompareNonTimeFields != 0) {
                return iCompareNonTimeFields;
            }
            long j = this.mPostTime;
            long j2 = headsUpEntry.mPostTime;
            if (j > j2) {
                return -1;
            }
            if (j == j2) {
                return this.mEntry.getKey().compareTo(headsUpEntry.mEntry.getKey());
            }
            return 1;
        }

        protected abstract Runnable createRemoveRunnable(NotificationEntry notificationEntry);

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && (obj instanceof HeadsUpEntry)) {
                HeadsUpEntry headsUpEntry = (HeadsUpEntry) obj;
                NotificationEntry notificationEntry = this.mEntry;
                if (notificationEntry != null && headsUpEntry.mEntry != null) {
                    return notificationEntry.getKey().equals(headsUpEntry.mEntry.getKey());
                }
            }
            return false;
        }

        protected int getRecommendedHeadsUpTimeoutMs(int i) {
            return i;
        }

        public int hashCode() {
            NotificationEntry notificationEntry = this.mEntry;
            return notificationEntry == null ? super.hashCode() : notificationEntry.getKey().hashCode() * 31;
        }

        protected boolean isRowPinned() {
            NotificationEntry notificationEntry = this.mEntry;
            return notificationEntry != null && notificationEntry.isRowPinned();
        }

        public boolean isSticky() {
            return (this.mEntry.isRowPinned() && this.mExpanded) || this.mRemoteInputActive || BaseHeadsUpManager.this.hasFullScreenIntent(this.mEntry);
        }

        public boolean isStickyForSomeTime() {
            return this.mEntry.isStickyAndNotDemoted();
        }

        public void removeAsSoonAsPossible() {
            if (this.mRemoveRunnable != null) {
                scheduleAutoRemovalCallback(new FinishTimeUpdater() { // from class: com.android.systemui.statusbar.policy.BaseHeadsUpManager$HeadsUpEntry$$ExternalSyntheticLambda1
                    @Override // com.android.systemui.statusbar.policy.BaseHeadsUpManager.FinishTimeUpdater
                    public final long updateAndGetTimeRemaining() {
                        return this.f$0.lambda$removeAsSoonAsPossible$4();
                    }
                }, "removeAsSoonAsPossible");
            }
        }

        public void reset() {
            NotificationsHeadsUpRefactor.assertInLegacyMode();
            cancelAutoRemovalCallbacks("reset()");
            this.mEntry = null;
            this.mRemoveRunnable = null;
            this.mExpanded = false;
            this.mRemoteInputActive = false;
        }

        public void scheduleAutoRemovalCallback(final FinishTimeUpdater finishTimeUpdater, String str) {
            Runnable runnable = new Runnable() { // from class: com.android.systemui.statusbar.policy.BaseHeadsUpManager$HeadsUpEntry$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$scheduleAutoRemovalCallback$3(finishTimeUpdater);
                }
            };
            BaseHeadsUpManager.this.mAvalancheController.update(this, runnable, str + " scheduleAutoRemovalCallback");
        }

        public void setEntry(NotificationEntry notificationEntry) {
            NotificationsHeadsUpRefactor.assertInLegacyMode();
            setEntry(notificationEntry, createRemoveRunnable(notificationEntry));
        }

        public abstract void setExpanded(boolean z);

        protected void setRowPinned(boolean z) {
            NotificationEntry notificationEntry = this.mEntry;
            if (notificationEntry != null) {
                notificationEntry.setRowPinned(z);
            }
        }

        public void updateEntry(final boolean z, String str) {
            BaseHeadsUpManager.this.mAvalancheController.update(this, new Runnable() { // from class: com.android.systemui.statusbar.policy.BaseHeadsUpManager$HeadsUpEntry$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$updateEntry$0(z);
                }
            }, "updateEntry (updatePostTime)");
            if (isSticky()) {
                cancelAutoRemovalCallbacks("updateEntry (sticky)");
            } else {
                scheduleAutoRemovalCallback(new FinishTimeUpdater() { // from class: com.android.systemui.statusbar.policy.BaseHeadsUpManager$HeadsUpEntry$$ExternalSyntheticLambda3
                    @Override // com.android.systemui.statusbar.policy.BaseHeadsUpManager.FinishTimeUpdater
                    public final long updateAndGetTimeRemaining() {
                        return this.f$0.lambda$updateEntry$1();
                    }
                }, "updateEntry (not sticky)");
                BaseHeadsUpManager.this.onEntryUpdated(this);
            }
        }

        public boolean wasShownLongEnough() {
            return this.mEarliestRemovalTime < BaseHeadsUpManager.this.mSystemClock.elapsedRealtime();
        }
    }

    enum NotificationPeekEvent implements UiEventLogger.UiEventEnum {
        NOTIFICATION_PEEK(801);

        private final int mId;

        NotificationPeekEvent(int i) {
            this.mId = i;
        }

        public int getId() {
            return this.mId;
        }
    }

    public BaseHeadsUpManager(Context context, Handler handler, final GlobalSettings globalSettings, SystemClock systemClock, DelayableExecutor delayableExecutor, UiEventLogger uiEventLogger, AvalancheController avalancheController) {
        this.mExecutor = delayableExecutor;
        this.mSystemClock = systemClock;
        this.mContext = context;
        this.mUiEventLogger = uiEventLogger;
        this.mAvalancheController = avalancheController;
        Resources resources = context.getResources();
        this.mMinimumDisplayTime = resources.getInteger(R$integer.heads_up_notification_minimum_time);
        this.mStickyForSomeTimeAutoDismissTime = resources.getInteger(R$integer.sticky_heads_up_notification_time);
        this.mAutoDismissTime = resources.getInteger(R$integer.heads_up_notification_decay);
        this.mTouchAcceptanceDelay = resources.getInteger(R$integer.touch_acceptance_delay);
        this.mSnoozedPackages = new ArrayMap();
        this.mSnoozeLengthMs = globalSettings.getInt("heads_up_snooze_length_ms", resources.getInteger(R$integer.heads_up_default_snooze_length_ms));
        globalSettings.registerContentObserver(globalSettings.getUriFor("heads_up_snooze_length_ms"), false, new ContentObserver(handler) { // from class: com.android.systemui.statusbar.policy.BaseHeadsUpManager.1
            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                int i = globalSettings.getInt("heads_up_snooze_length_ms", -1);
                if (i > -1) {
                    BaseHeadsUpManager baseHeadsUpManager = BaseHeadsUpManager.this;
                    if (i != baseHeadsUpManager.mSnoozeLengthMs) {
                        baseHeadsUpManager.mSnoozeLengthMs = i;
                    }
                }
            }
        });
    }

    private boolean hasPinnedNotificationInternal() {
        Iterator it = this.mHeadsUpEntryMap.keySet().iterator();
        while (it.hasNext()) {
            if (getHeadsUpEntry((String) it.next()).mEntry.isRowPinned()) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isCriticalCallNotif(NotificationEntry notificationEntry) {
        Notification notification = notificationEntry.getSbn().getNotification();
        return (notification.isStyle(Notification.CallStyle.class) && notification.extras.getInt("android.callType") == 1) || (notificationEntry.getSbn().isOngoing() && "call".equals(notification.category));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$removeEntry$3(HeadsUpEntry headsUpEntry, String str) {
        if (headsUpEntry == null) {
            return;
        }
        NotificationEntry notificationEntry = headsUpEntry.mEntry;
        if (notificationEntry == null || !notificationEntry.isExpandAnimationRunning()) {
            notificationEntry.demoteStickyHun();
            this.mHeadsUpEntryMap.remove(str);
            onEntryRemoved(headsUpEntry);
            notificationEntry.sendAccessibilityEvent(2048);
            if (NotificationsHeadsUpRefactor.isEnabled()) {
                headsUpEntry.cancelAutoRemovalCallbacks("removeEntry");
            } else {
                headsUpEntry.reset();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showNotification$0(NotificationEntry notificationEntry, HeadsUpEntry headsUpEntry) {
        this.mHeadsUpEntryMap.put(notificationEntry.getKey(), headsUpEntry);
        onEntryAdded(headsUpEntry);
        notificationEntry.sendAccessibilityEvent(2048);
        notificationEntry.setIsHeadsUpEntry(true);
        updateNotificationInternal(notificationEntry.getKey(), true);
        notificationEntry.setInterruption();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateNotification$1(NotificationEntry notificationEntry, boolean z) {
        updateNotificationInternal(notificationEntry.getKey(), z);
    }

    private static String snoozeKey(String str, int i) {
        return i + "," + str;
    }

    private void updateNotificationInternal(String str, boolean z) {
        HeadsUpEntry headsUpEntry = (HeadsUpEntry) this.mHeadsUpEntryMap.get(str);
        if (headsUpEntry == null) {
            return;
        }
        headsUpEntry.mEntry.sendAccessibilityEvent(2048);
        if (z) {
            headsUpEntry.updateEntry(true, "updateNotification");
            setEntryPinned(headsUpEntry, shouldHeadsUpBecomePinned(headsUpEntry.mEntry));
        }
    }

    @Override // com.android.systemui.statusbar.policy.HeadsUpManager
    public void addListener(OnHeadsUpChangedListener onHeadsUpChangedListener) {
        this.mListeners.addIfAbsent(onHeadsUpChangedListener);
    }

    @Override // com.android.systemui.statusbar.policy.HeadsUpManager
    public boolean canRemoveImmediately(String str) {
        HeadsUpEntry headsUpEntry = getHeadsUpEntry(str);
        return (headsUpEntry != null && headsUpEntry.mUserActionMayIndirectlyRemove) || headsUpEntry == null || headsUpEntry.wasShownLongEnough() || headsUpEntry.mEntry.isRowDismissed();
    }

    @Override // com.android.systemui.statusbar.policy.HeadsUpManager
    public int compare(NotificationEntry notificationEntry, NotificationEntry notificationEntry2) {
        if (notificationEntry == null || notificationEntry2 == null) {
            return Boolean.compare(notificationEntry == null, notificationEntry2 == null);
        }
        HeadsUpEntry headsUpEntry = getHeadsUpEntry(notificationEntry.getKey());
        HeadsUpEntry headsUpEntry2 = getHeadsUpEntry(notificationEntry2.getKey());
        if (headsUpEntry == null || headsUpEntry2 == null) {
            return Boolean.compare(headsUpEntry == null, headsUpEntry2 == null);
        }
        return headsUpEntry.compareTo(headsUpEntry2);
    }

    protected abstract HeadsUpEntry createHeadsUpEntry(NotificationEntry notificationEntry);

    @Override // com.android.systemui.statusbar.policy.HeadsUpManager
    public long getEarliestRemovalTime(String str) {
        HeadsUpEntry headsUpEntry = (HeadsUpEntry) this.mHeadsUpEntryMap.get(str);
        if (headsUpEntry != null) {
            return Math.max(0L, headsUpEntry.mEarliestRemovalTime - this.mSystemClock.elapsedRealtime());
        }
        return 0L;
    }

    protected HeadsUpEntry getHeadsUpEntry(String str) {
        return (HeadsUpEntry) this.mHeadsUpEntryMap.get(str);
    }

    @Override // com.android.systemui.statusbar.policy.HeadsUpManager
    public NotificationEntry getTopEntry() {
        HeadsUpEntry topHeadsUpEntry = getTopHeadsUpEntry();
        if (topHeadsUpEntry != null) {
            return topHeadsUpEntry.mEntry;
        }
        return null;
    }

    protected HeadsUpEntry getTopHeadsUpEntry() {
        HeadsUpEntry headsUpEntry = null;
        if (this.mHeadsUpEntryMap.isEmpty()) {
            return null;
        }
        for (HeadsUpEntry headsUpEntry2 : this.mHeadsUpEntryMap.values()) {
            if (headsUpEntry == null || headsUpEntry2.compareTo(headsUpEntry) < 0) {
                headsUpEntry = headsUpEntry2;
            }
        }
        return headsUpEntry;
    }

    protected boolean hasFullScreenIntent(NotificationEntry notificationEntry) {
        return notificationEntry.getSbn().getNotification().fullScreenIntent != null;
    }

    @Override // com.android.systemui.statusbar.policy.HeadsUpManager
    public boolean isHeadsUpEntry(String str) {
        return this.mHeadsUpEntryMap.containsKey(str) || this.mAvalancheController.isWaiting(str);
    }

    @Override // com.android.systemui.statusbar.policy.HeadsUpManager
    public boolean isSnoozed(String str) {
        String strSnoozeKey = snoozeKey(str, this.mUser);
        Long l = (Long) this.mSnoozedPackages.get(strSnoozeKey);
        if (l == null) {
            return false;
        }
        if (l.longValue() > this.mSystemClock.elapsedRealtime()) {
            return true;
        }
        this.mSnoozedPackages.remove(strSnoozeKey);
        return false;
    }

    @Override // com.android.systemui.statusbar.policy.HeadsUpManager
    public boolean isSticky(String str) {
        HeadsUpEntry headsUpEntry = (HeadsUpEntry) this.mHeadsUpEntryMap.get(str);
        if (headsUpEntry != null) {
            return headsUpEntry.isSticky();
        }
        return false;
    }

    protected void onEntryAdded(HeadsUpEntry headsUpEntry) {
        NotificationEntry notificationEntry = headsUpEntry.mEntry;
        notificationEntry.setHeadsUp(true);
        setEntryPinned(headsUpEntry, shouldHeadsUpBecomePinned(notificationEntry));
        Iterator it = this.mListeners.iterator();
        while (it.hasNext()) {
            ((OnHeadsUpChangedListener) it.next()).onHeadsUpStateChanged(notificationEntry, true);
        }
    }

    protected void onEntryRemoved(HeadsUpEntry headsUpEntry) {
        NotificationEntry notificationEntry = headsUpEntry.mEntry;
        notificationEntry.setHeadsUp(false);
        setEntryPinned(headsUpEntry, false);
        Iterator it = this.mListeners.iterator();
        while (it.hasNext()) {
            ((OnHeadsUpChangedListener) it.next()).onHeadsUpStateChanged(notificationEntry, false);
        }
    }

    protected void onEntryUpdated(HeadsUpEntry headsUpEntry) {
    }

    @Override // com.android.systemui.statusbar.policy.HeadsUpManager
    public void releaseAllImmediately() {
        ArraySet arraySet = new ArraySet(this.mHeadsUpEntryMap.keySet());
        List waitingKeys = this.mAvalancheController.getWaitingKeys();
        Iterator it = arraySet.iterator();
        while (it.hasNext()) {
            removeEntry((String) it.next());
        }
        Iterator it2 = waitingKeys.iterator();
        while (it2.hasNext()) {
            removeEntry((String) it2.next());
        }
    }

    protected final void removeEntry(final String str) {
        final HeadsUpEntry headsUpEntry = (HeadsUpEntry) this.mHeadsUpEntryMap.get(str);
        this.mAvalancheController.delete(headsUpEntry, new Runnable() { // from class: com.android.systemui.statusbar.policy.BaseHeadsUpManager$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$removeEntry$3(headsUpEntry, str);
            }
        }, "removeEntry");
    }

    @Override // com.android.systemui.statusbar.policy.HeadsUpManager
    public boolean removeNotification(String str, boolean z) {
        if (this.mAvalancheController.isWaiting(str)) {
            removeEntry(str);
            return true;
        }
        HeadsUpEntry headsUpEntry = (HeadsUpEntry) this.mHeadsUpEntryMap.get(str);
        if (headsUpEntry == null) {
            return true;
        }
        if (z || canRemoveImmediately(str)) {
            removeEntry(str);
            return true;
        }
        headsUpEntry.removeAsSoonAsPossible();
        return false;
    }

    protected void setEntryPinned(HeadsUpEntry headsUpEntry, boolean z) {
        NotificationEntry notificationEntry = headsUpEntry.mEntry;
        if (!z) {
            headsUpEntry.mWasUnpinned = true;
        }
        if (headsUpEntry.isRowPinned() != z) {
            headsUpEntry.setRowPinned(z);
            updatePinnedMode();
            if (z && notificationEntry.getSbn() != null) {
                this.mUiEventLogger.logWithInstanceId(NotificationPeekEvent.NOTIFICATION_PEEK, notificationEntry.getSbn().getUid(), notificationEntry.getSbn().getPackageName(), notificationEntry.getSbn().getInstanceId());
            }
            for (OnHeadsUpChangedListener onHeadsUpChangedListener : this.mListeners) {
                if (z) {
                    onHeadsUpChangedListener.onHeadsUpPinned(notificationEntry);
                } else {
                    onHeadsUpChangedListener.onHeadsUpUnPinned(notificationEntry);
                }
            }
        }
    }

    @Override // com.android.systemui.statusbar.policy.HeadsUpManager
    public void setExpanded(NotificationEntry notificationEntry, boolean z) {
        HeadsUpEntry headsUpEntry = getHeadsUpEntry(notificationEntry.getKey());
        if (headsUpEntry == null || !notificationEntry.isRowPinned()) {
            return;
        }
        headsUpEntry.setExpanded(z);
    }

    @Override // com.android.systemui.statusbar.policy.HeadsUpManager
    public void setUser(int i) {
        this.mUser = i;
    }

    @Override // com.android.systemui.statusbar.policy.HeadsUpManager
    public void setUserActionMayIndirectlyRemove(NotificationEntry notificationEntry) {
        HeadsUpEntry headsUpEntry = getHeadsUpEntry(notificationEntry.getKey());
        if (headsUpEntry != null) {
            headsUpEntry.mUserActionMayIndirectlyRemove = true;
        }
    }

    protected abstract boolean shouldHeadsUpBecomePinned(NotificationEntry notificationEntry);

    @Override // com.android.systemui.statusbar.policy.HeadsUpManager
    public void showNotification(final NotificationEntry notificationEntry) {
        final HeadsUpEntry headsUpEntryCreateHeadsUpEntry = createHeadsUpEntry(notificationEntry);
        this.mAvalancheController.update(headsUpEntryCreateHeadsUpEntry, new Runnable() { // from class: com.android.systemui.statusbar.policy.BaseHeadsUpManager$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$showNotification$0(notificationEntry, headsUpEntryCreateHeadsUpEntry);
            }
        }, "showNotification");
    }

    @Override // com.android.systemui.statusbar.policy.HeadsUpManager
    public void updateNotification(final NotificationEntry notificationEntry, final boolean z) {
        this.mAvalancheController.update((HeadsUpEntry) this.mHeadsUpEntryMap.get(notificationEntry.getKey()), new Runnable() { // from class: com.android.systemui.statusbar.policy.BaseHeadsUpManager$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$updateNotification$1(notificationEntry, z);
            }
        }, "updateNotification");
    }

    protected void updatePinnedMode() {
        boolean zHasPinnedNotificationInternal = hasPinnedNotificationInternal();
        if (zHasPinnedNotificationInternal == this.mHasPinnedNotification) {
            return;
        }
        this.mHasPinnedNotification = zHasPinnedNotificationInternal;
        if (zHasPinnedNotificationInternal) {
            MetricsLogger.count(this.mContext, "note_peek", 1);
        }
        Iterator it = this.mListeners.iterator();
        while (it.hasNext()) {
            ((OnHeadsUpChangedListener) it.next()).onHeadsUpPinnedModeChanged(zHasPinnedNotificationInternal);
        }
    }
}
