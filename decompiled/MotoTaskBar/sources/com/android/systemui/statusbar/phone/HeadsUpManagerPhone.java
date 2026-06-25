package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.graphics.Region;
import android.os.Handler;
import android.util.ArrayMap;
import android.util.Pools;
import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0;
import androidx.collection.ArraySet;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.policy.SystemBarUtils;
import com.android.systemui.res.R$dimen;
import com.android.systemui.res.R$integer;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.provider.OnReorderingAllowedListener;
import com.android.systemui.statusbar.notification.collection.provider.VisualStabilityProvider;
import com.android.systemui.statusbar.notification.collection.render.GroupMembershipManager;
import com.android.systemui.statusbar.notification.data.repository.HeadsUpRepository;
import com.android.systemui.statusbar.notification.data.repository.HeadsUpRowRepository;
import com.android.systemui.statusbar.notification.shared.NotificationsHeadsUpRefactor;
import com.android.systemui.statusbar.policy.AnimationStateHandler;
import com.android.systemui.statusbar.policy.AvalancheController;
import com.android.systemui.statusbar.policy.BaseHeadsUpManager;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.OnHeadsUpChangedListener;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.kotlin.JavaAdapter;
import com.android.systemui.util.settings.GlobalSettings;
import com.android.systemui.util.time.SystemClock;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowKt;

/* JADX INFO: loaded from: classes.dex */
public class HeadsUpManagerPhone extends BaseHeadsUpManager implements HeadsUpRepository, OnHeadsUpChangedListener {
    private AnimationStateHandler mAnimationStateHandler;
    private final HashSet mEntriesToRemoveAfterExpand;
    private final ArraySet mEntriesToRemoveWhenReorderingAllowed;
    private final Pools.Pool mEntryPool;
    public final int mExtensionTime;
    private final GroupMembershipManager mGroupMembershipManager;
    private final MutableStateFlow mHeadsUpAnimatingAway;
    private int mHeadsUpInset;
    private final MutableStateFlow mHeadsUpNotificationRows;
    private final List mHeadsUpPhoneListeners;
    private final OnReorderingAllowedListener mOnReorderingAllowedListener;
    private final HashSet mSwipedOutKeys;
    private final MutableStateFlow mTopHeadsUpRow;
    private final Region mTouchableRegion;
    private boolean mTrackingHeadsUp;
    private final VisualStabilityProvider mVisualStabilityProvider;

    public class HeadsUpEntryPhone extends BaseHeadsUpManager.HeadsUpEntry implements HeadsUpRowRepository {
        private boolean extended;
        private boolean mGutsShownPinned;
        private final MutableStateFlow mIsPinned;

        public HeadsUpEntryPhone() {
            super();
            this.mIsPinned = StateFlowKt.MutableStateFlow(Boolean.FALSE);
        }

        public HeadsUpEntryPhone(NotificationEntry notificationEntry) {
            super(notificationEntry);
            this.mIsPinned = StateFlowKt.MutableStateFlow(Boolean.FALSE);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$createRemoveRunnable$0(NotificationEntry notificationEntry) {
            if (!HeadsUpManagerPhone.this.mVisualStabilityProvider.isReorderingAllowed() && !notificationEntry.showingPulsing()) {
                HeadsUpManagerPhone.this.mEntriesToRemoveWhenReorderingAllowed.add(notificationEntry);
                HeadsUpManagerPhone.this.mVisualStabilityProvider.addTemporaryReorderingAllowedListener(HeadsUpManagerPhone.this.mOnReorderingAllowedListener);
            } else if (HeadsUpManagerPhone.this.mTrackingHeadsUp) {
                HeadsUpManagerPhone.this.mEntriesToRemoveAfterExpand.add(notificationEntry);
            } else {
                HeadsUpManagerPhone.this.removeEntry(notificationEntry.getKey());
            }
        }

        private NotificationEntry requireEntry() {
            NotificationsHeadsUpRefactor.isUnexpectedlyInLegacyMode();
            NotificationEntry notificationEntry = this.mEntry;
            notificationEntry.getClass();
            return notificationEntry;
        }

        @Override // com.android.systemui.statusbar.policy.BaseHeadsUpManager.HeadsUpEntry
        protected long calculateFinishTime() {
            return super.calculateFinishTime() + ((long) (this.extended ? HeadsUpManagerPhone.this.mExtensionTime : 0));
        }

        @Override // com.android.systemui.statusbar.policy.BaseHeadsUpManager.HeadsUpEntry
        protected Runnable createRemoveRunnable(final NotificationEntry notificationEntry) {
            return new Runnable() { // from class: com.android.systemui.statusbar.phone.HeadsUpManagerPhone$HeadsUpEntryPhone$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$createRemoveRunnable$0(notificationEntry);
                }
            };
        }

        @Override // com.android.systemui.statusbar.notification.data.repository.HeadsUpRowRepository
        public Object getElementKey() {
            return requireEntry().getRow();
        }

        @Override // com.android.systemui.statusbar.notification.data.repository.HeadsUpRowRepository
        public StateFlow isPinned() {
            return this.mIsPinned;
        }

        @Override // com.android.systemui.statusbar.policy.BaseHeadsUpManager.HeadsUpEntry
        public boolean isSticky() {
            return super.isSticky() || this.mGutsShownPinned;
        }

        @Override // com.android.systemui.statusbar.policy.BaseHeadsUpManager.HeadsUpEntry
        public void reset() {
            super.reset();
            this.mGutsShownPinned = false;
            this.extended = false;
        }

        @Override // com.android.systemui.statusbar.policy.BaseHeadsUpManager.HeadsUpEntry
        public void setExpanded(boolean z) {
            if (this.mExpanded == z) {
                return;
            }
            this.mExpanded = z;
            if (z) {
                cancelAutoRemovalCallbacks("setExpanded(true)");
            } else {
                updateEntry(false, "setExpanded(false)");
            }
        }

        @Override // com.android.systemui.statusbar.policy.BaseHeadsUpManager.HeadsUpEntry
        protected void setRowPinned(boolean z) {
            super.setRowPinned(z);
            this.mIsPinned.setValue(Boolean.valueOf(z));
        }

        @Override // com.android.systemui.statusbar.policy.BaseHeadsUpManager.HeadsUpEntry
        public void updateEntry(boolean z, String str) {
            super.updateEntry(z, str);
            if (HeadsUpManagerPhone.this.mEntriesToRemoveAfterExpand.contains(this.mEntry)) {
                HeadsUpManagerPhone.this.mEntriesToRemoveAfterExpand.remove(this.mEntry);
            }
            if (HeadsUpManagerPhone.this.mEntriesToRemoveWhenReorderingAllowed.contains(this.mEntry)) {
                HeadsUpManagerPhone.this.mEntriesToRemoveWhenReorderingAllowed.remove(this.mEntry);
            }
        }
    }

    public HeadsUpManagerPhone(Context context, GroupMembershipManager groupMembershipManager, VisualStabilityProvider visualStabilityProvider, ConfigurationController configurationController, Handler handler, GlobalSettings globalSettings, SystemClock systemClock, DelayableExecutor delayableExecutor, UiEventLogger uiEventLogger, JavaAdapter javaAdapter, AvalancheController avalancheController) {
        super(context, handler, globalSettings, systemClock, delayableExecutor, uiEventLogger, avalancheController);
        this.mHeadsUpPhoneListeners = new ArrayList();
        this.mTopHeadsUpRow = StateFlowKt.MutableStateFlow(null);
        this.mHeadsUpNotificationRows = StateFlowKt.MutableStateFlow(new HashSet());
        this.mHeadsUpAnimatingAway = StateFlowKt.MutableStateFlow(Boolean.FALSE);
        this.mSwipedOutKeys = new HashSet();
        this.mEntriesToRemoveAfterExpand = new HashSet();
        this.mEntriesToRemoveWhenReorderingAllowed = new ArraySet();
        this.mTouchableRegion = new Region();
        this.mEntryPool = new Pools.Pool() { // from class: com.android.systemui.statusbar.phone.HeadsUpManagerPhone.1
            private Stack mPoolObjects = new Stack();

            /* JADX INFO: renamed from: acquire, reason: merged with bridge method [inline-methods] */
            public HeadsUpEntryPhone m2083acquire() {
                NotificationsHeadsUpRefactor.assertInLegacyMode();
                return !this.mPoolObjects.isEmpty() ? (HeadsUpEntryPhone) this.mPoolObjects.pop() : HeadsUpManagerPhone.this.new HeadsUpEntryPhone();
            }

            public boolean release(HeadsUpEntryPhone headsUpEntryPhone) {
                NotificationsHeadsUpRefactor.assertInLegacyMode();
                this.mPoolObjects.push(headsUpEntryPhone);
                return true;
            }
        };
        this.mOnReorderingAllowedListener = new OnReorderingAllowedListener() { // from class: com.android.systemui.statusbar.phone.HeadsUpManagerPhone$$ExternalSyntheticLambda0
        };
        this.mExtensionTime = this.mContext.getResources().getInteger(R$integer.ambient_notification_extension_time);
        this.mGroupMembershipManager = groupMembershipManager;
        this.mVisualStabilityProvider = visualStabilityProvider;
        updateResources();
        configurationController.addCallback(new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.statusbar.phone.HeadsUpManagerPhone.2
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public void onDensityOrFontScaleChanged() {
                HeadsUpManagerPhone.this.updateResources();
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public void onThemeChanged() {
                HeadsUpManagerPhone.this.updateResources();
            }
        });
    }

    private HeadsUpEntryPhone getHeadsUpEntryPhone(String str) {
        return (HeadsUpEntryPhone) this.mHeadsUpEntryMap.get(str);
    }

    private ArrayMap getHeadsUpEntryPhoneMap() {
        return this.mHeadsUpEntryMap;
    }

    private HeadsUpEntryPhone getTopHeadsUpEntryPhone() {
        return NotificationsHeadsUpRefactor.isEnabled() ? (HeadsUpEntryPhone) this.mTopHeadsUpRow.getValue() : (HeadsUpEntryPhone) getTopHeadsUpEntry();
    }

    private void updateHeadsUpFlow() {
        this.mHeadsUpNotificationRows.setValue(new HashSet(getHeadsUpEntryPhoneMap().values()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateResources() {
        this.mHeadsUpInset = SystemBarUtils.getStatusBarHeight(this.mContext) + this.mContext.getResources().getDimensionPixelSize(R$dimen.heads_up_status_bar_padding);
    }

    private void updateTopHeadsUpFlow() {
        this.mTopHeadsUpRow.setValue((HeadsUpRowRepository) getTopHeadsUpEntry());
    }

    @Override // com.android.systemui.statusbar.policy.HeadsUpManager
    public void addSwipedOutNotification(String str) {
        this.mSwipedOutKeys.add(str);
    }

    @Override // com.android.systemui.statusbar.policy.BaseHeadsUpManager, com.android.systemui.statusbar.policy.HeadsUpManager
    public boolean canRemoveImmediately(String str) {
        if (this.mSwipedOutKeys.contains(str)) {
            this.mSwipedOutKeys.remove(str);
            return true;
        }
        HeadsUpEntryPhone headsUpEntryPhone = getHeadsUpEntryPhone(str);
        return headsUpEntryPhone == null || headsUpEntryPhone != getTopHeadsUpEntryPhone() || super.canRemoveImmediately(str);
    }

    @Override // com.android.systemui.statusbar.policy.BaseHeadsUpManager
    protected BaseHeadsUpManager.HeadsUpEntry createHeadsUpEntry(NotificationEntry notificationEntry) {
        if (NotificationsHeadsUpRefactor.isEnabled()) {
            return new HeadsUpEntryPhone(notificationEntry);
        }
        HeadsUpEntryPhone headsUpEntryPhone = (HeadsUpEntryPhone) this.mEntryPool.acquire();
        headsUpEntryPhone.setEntry(notificationEntry);
        return headsUpEntryPhone;
    }

    @Override // com.android.systemui.statusbar.notification.data.repository.HeadsUpRepository
    public Flow getActiveHeadsUpRows() {
        return this.mHeadsUpNotificationRows;
    }

    @Override // com.android.systemui.statusbar.notification.data.repository.HeadsUpRepository
    public Flow getTopHeadsUpRow() {
        return this.mTopHeadsUpRow;
    }

    @Override // com.android.systemui.statusbar.notification.data.repository.HeadsUpRepository
    public StateFlow isHeadsUpAnimatingAway() {
        return this.mHeadsUpAnimatingAway;
    }

    @Override // com.android.systemui.statusbar.policy.HeadsUpManager
    public boolean isTrackingHeadsUp() {
        return this.mTrackingHeadsUp;
    }

    @Override // com.android.systemui.statusbar.policy.BaseHeadsUpManager
    protected void onEntryAdded(BaseHeadsUpManager.HeadsUpEntry headsUpEntry) {
        super.onEntryAdded(headsUpEntry);
        updateTopHeadsUpFlow();
        updateHeadsUpFlow();
    }

    @Override // com.android.systemui.statusbar.policy.BaseHeadsUpManager
    protected void onEntryRemoved(BaseHeadsUpManager.HeadsUpEntry headsUpEntry) {
        super.onEntryRemoved(headsUpEntry);
        if (!NotificationsHeadsUpRefactor.isEnabled()) {
            this.mEntryPool.release((HeadsUpEntryPhone) headsUpEntry);
        }
        updateTopHeadsUpFlow();
        updateHeadsUpFlow();
    }

    @Override // com.android.systemui.statusbar.policy.BaseHeadsUpManager
    protected void onEntryUpdated(BaseHeadsUpManager.HeadsUpEntry headsUpEntry) {
        super.onEntryUpdated(headsUpEntry);
        updateTopHeadsUpFlow();
    }

    @Override // com.android.systemui.statusbar.policy.HeadsUpManager
    public void setAnimationStateHandler(AnimationStateHandler animationStateHandler) {
        this.mAnimationStateHandler = animationStateHandler;
    }

    @Override // com.android.systemui.statusbar.notification.data.repository.HeadsUpRepository
    public void setHeadsUpAnimatingAway(boolean z) {
        if (z != ((Boolean) this.mHeadsUpAnimatingAway.getValue()).booleanValue()) {
            Iterator it = this.mHeadsUpPhoneListeners.iterator();
            if (it.hasNext()) {
                ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(it.next());
                throw null;
            }
            this.mHeadsUpAnimatingAway.setValue(Boolean.valueOf(z));
        }
    }

    @Override // com.android.systemui.statusbar.policy.HeadsUpManager
    public void setRemoteInputActive(NotificationEntry notificationEntry, boolean z) {
        HeadsUpEntryPhone headsUpEntryPhone = getHeadsUpEntryPhone(notificationEntry.getKey());
        if (headsUpEntryPhone == null || headsUpEntryPhone.mRemoteInputActive == z) {
            return;
        }
        headsUpEntryPhone.mRemoteInputActive = z;
        if (z) {
            headsUpEntryPhone.cancelAutoRemovalCallbacks("setRemoteInputActive(true)");
        } else {
            headsUpEntryPhone.updateEntry(false, "setRemoteInputActive(false)");
        }
        onEntryUpdated(headsUpEntryPhone);
    }

    @Override // com.android.systemui.statusbar.policy.BaseHeadsUpManager
    protected boolean shouldHeadsUpBecomePinned(NotificationEntry notificationEntry) {
        return true;
    }
}
