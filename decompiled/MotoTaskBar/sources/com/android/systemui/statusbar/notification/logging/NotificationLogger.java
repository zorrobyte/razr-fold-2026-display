package com.android.systemui.statusbar.notification.logging;

import android.os.Handler;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.Trace;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.statusbar.NotificationVisibility;
import com.android.systemui.Dumpable;
import com.android.systemui.statusbar.notification.collection.NotifLiveDataStore;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider;
import com.android.systemui.statusbar.notification.shared.NotificationsLiveDataStoreRefactor;
import com.android.systemui.statusbar.notification.stack.NotificationListContainer;
import com.android.systemui.statusbar.notification.stack.ui.view.NotificationRowStatsLogger;
import com.android.systemui.util.Compile;
import com.android.systemui.util.kotlin.JavaAdapter;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes.dex */
public class NotificationLogger implements Dumpable, NotificationRowStatsLogger {
    private static final boolean DEBUG;
    protected IStatusBarService mBarService;
    private final ExpansionStateLogger mExpansionStateLogger;
    private final JavaAdapter mJavaAdapter;
    private long mLastVisibilityReportUptimeMs;
    private NotificationListContainer mListContainer;
    private final NotifLiveDataStore mNotifLiveDataStore;
    private final NotifPipeline mNotifPipeline;
    private final NotificationPanelLogger mNotificationPanelLogger;
    private final Executor mUiBgExecutor;
    private final NotificationVisibilityProvider mVisibilityProvider;
    private final ArraySet mCurrentlyVisibleNotifications = new ArraySet();
    protected Handler mHandler = new Handler();
    private final Object mDozingLock = new Object();
    private Boolean mLockscreen = null;
    private boolean mLogging = false;
    protected Runnable mVisibilityReporter = new Runnable() { // from class: com.android.systemui.statusbar.notification.logging.NotificationLogger.1
        private final ArraySet mTmpNewlyVisibleNotifications = new ArraySet();
        private final ArraySet mTmpCurrentlyVisibleNotifications = new ArraySet();
        private final ArraySet mTmpNoLongerVisibleNotifications = new ArraySet();

        @Override // java.lang.Runnable
        public void run() {
            NotificationLogger.this.mLastVisibilityReportUptimeMs = SystemClock.uptimeMillis();
            List visibleNotifications = NotificationLogger.this.getVisibleNotifications();
            int size = visibleNotifications.size();
            for (int i = 0; i < size; i++) {
                NotificationEntry notificationEntry = (NotificationEntry) visibleNotifications.get(i);
                String key = notificationEntry.getSbn().getKey();
                boolean zIsInVisibleLocation = NotificationLogger.this.mListContainer.isInVisibleLocation(notificationEntry);
                NotificationVisibility notificationVisibilityObtain = NotificationVisibility.obtain(key, i, size, zIsInVisibleLocation, NotificationLogger.getNotificationLocation(notificationEntry));
                boolean zContains = NotificationLogger.this.mCurrentlyVisibleNotifications.contains(notificationVisibilityObtain);
                if (zIsInVisibleLocation) {
                    this.mTmpCurrentlyVisibleNotifications.add(notificationVisibilityObtain);
                    if (!zContains) {
                        this.mTmpNewlyVisibleNotifications.add(notificationVisibilityObtain);
                    }
                } else {
                    notificationVisibilityObtain.recycle();
                }
            }
            this.mTmpNoLongerVisibleNotifications.addAll(NotificationLogger.this.mCurrentlyVisibleNotifications);
            this.mTmpNoLongerVisibleNotifications.removeAll(this.mTmpCurrentlyVisibleNotifications);
            NotificationLogger.this.logNotificationVisibilityChanges(this.mTmpNewlyVisibleNotifications, this.mTmpNoLongerVisibleNotifications);
            NotificationLogger notificationLogger = NotificationLogger.this;
            notificationLogger.recycleAllVisibilityObjects(notificationLogger.mCurrentlyVisibleNotifications);
            NotificationLogger.this.mCurrentlyVisibleNotifications.addAll(this.mTmpCurrentlyVisibleNotifications);
            ExpansionStateLogger expansionStateLogger = NotificationLogger.this.mExpansionStateLogger;
            ArraySet arraySet = this.mTmpCurrentlyVisibleNotifications;
            expansionStateLogger.onVisibilityChanged(arraySet, arraySet);
            Trace.traceCounter(4096L, "Notifications [Active]", size);
            Trace.traceCounter(4096L, "Notifications [Visible]", NotificationLogger.this.mCurrentlyVisibleNotifications.size());
            NotificationLogger.this.recycleAllVisibilityObjects(this.mTmpNoLongerVisibleNotifications);
            this.mTmpCurrentlyVisibleNotifications.clear();
            this.mTmpNewlyVisibleNotifications.clear();
            this.mTmpNoLongerVisibleNotifications.clear();
        }
    };

    public class ExpansionStateLogger {
        private final Executor mUiBgExecutor;
        private final Map mExpansionStates = new ArrayMap();
        private final Map mLoggedExpansionState = new ArrayMap();
        IStatusBarService mBarService = IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar"));

        class State {
            Boolean mIsExpanded;
            Boolean mIsUserAction;
            Boolean mIsVisible;
            NotificationVisibility.NotificationLocation mLocation;

            private State() {
            }

            private State(State state) {
                this.mIsUserAction = state.mIsUserAction;
                this.mIsExpanded = state.mIsExpanded;
                this.mIsVisible = state.mIsVisible;
                this.mLocation = state.mLocation;
            }

            /* JADX INFO: Access modifiers changed from: private */
            public boolean isFullySet() {
                return (this.mIsUserAction == null || this.mIsExpanded == null || this.mIsVisible == null || this.mLocation == null) ? false : true;
            }
        }

        public ExpansionStateLogger(Executor executor) {
            this.mUiBgExecutor = executor;
        }

        private State getState(String str) {
            State state = (State) this.mExpansionStates.get(str);
            if (state != null) {
                return state;
            }
            State state2 = new State();
            this.mExpansionStates.put(str, state2);
            return state2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$maybeNotifyOnNotificationExpansionChanged$0(String str, State state) {
            try {
                this.mBarService.onNotificationExpansionChanged(str, state.mIsUserAction.booleanValue(), state.mIsExpanded.booleanValue(), state.mLocation.ordinal());
            } catch (RemoteException e) {
                Log.e("NotificationLogger", "Failed to call onNotificationExpansionChanged: ", e);
            }
        }

        private void maybeNotifyOnNotificationExpansionChanged(final String str, State state) {
            if (state.isFullySet() && state.mIsVisible.booleanValue()) {
                Boolean bool = (Boolean) this.mLoggedExpansionState.get(str);
                if (bool != null || state.mIsExpanded.booleanValue()) {
                    if (bool == null || !Objects.equals(state.mIsExpanded, bool)) {
                        this.mLoggedExpansionState.put(str, state.mIsExpanded);
                        final State state2 = new State(state);
                        this.mUiBgExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.notification.logging.NotificationLogger$ExpansionStateLogger$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$maybeNotifyOnNotificationExpansionChanged$0(str, state2);
                            }
                        });
                    }
                }
            }
        }

        void onEntryRemoved(String str) {
            this.mExpansionStates.remove(str);
            this.mLoggedExpansionState.remove(str);
        }

        void onEntryUpdated(String str) {
            this.mLoggedExpansionState.remove(str);
        }

        void onExpansionChanged(String str, boolean z, boolean z2, NotificationVisibility.NotificationLocation notificationLocation) {
            State state = getState(str);
            state.mIsUserAction = Boolean.valueOf(z);
            state.mIsExpanded = Boolean.valueOf(z2);
            state.mLocation = notificationLocation;
            maybeNotifyOnNotificationExpansionChanged(str, state);
        }

        void onVisibilityChanged(Collection collection, Collection collection2) {
            NotificationVisibility[] notificationVisibilityArrCloneVisibilitiesAsArr = NotificationLogger.cloneVisibilitiesAsArr(collection);
            NotificationVisibility[] notificationVisibilityArrCloneVisibilitiesAsArr2 = NotificationLogger.cloneVisibilitiesAsArr(collection2);
            for (NotificationVisibility notificationVisibility : notificationVisibilityArrCloneVisibilitiesAsArr) {
                State state = getState(notificationVisibility.key);
                state.mIsVisible = Boolean.TRUE;
                state.mLocation = notificationVisibility.location;
                maybeNotifyOnNotificationExpansionChanged(notificationVisibility.key, state);
            }
            for (NotificationVisibility notificationVisibility2 : notificationVisibilityArrCloneVisibilitiesAsArr2) {
                getState(notificationVisibility2.key).mIsVisible = Boolean.FALSE;
            }
        }
    }

    public interface OnChildLocationsChangedListener {
        void onChildLocationsChanged();
    }

    static {
        DEBUG = Compile.IS_DEBUG && Log.isLoggable("NotificationLogger", 3);
    }

    public NotificationLogger(Executor executor, NotifLiveDataStore notifLiveDataStore, NotificationVisibilityProvider notificationVisibilityProvider, NotifPipeline notifPipeline, JavaAdapter javaAdapter, ExpansionStateLogger expansionStateLogger, NotificationPanelLogger notificationPanelLogger) {
        NotificationsLiveDataStoreRefactor.assertInLegacyMode();
        this.mUiBgExecutor = executor;
        this.mNotifLiveDataStore = notifLiveDataStore;
        this.mVisibilityProvider = notificationVisibilityProvider;
        this.mNotifPipeline = notifPipeline;
        this.mBarService = IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar"));
        this.mExpansionStateLogger = expansionStateLogger;
        this.mNotificationPanelLogger = notificationPanelLogger;
        this.mJavaAdapter = javaAdapter;
        registerNewPipelineListener();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static NotificationVisibility[] cloneVisibilitiesAsArr(Collection collection) {
        NotificationVisibility[] notificationVisibilityArr = new NotificationVisibility[collection.size()];
        Iterator it = collection.iterator();
        int i = 0;
        while (it.hasNext()) {
            NotificationVisibility notificationVisibility = (NotificationVisibility) it.next();
            if (notificationVisibility != null) {
                notificationVisibilityArr[i] = notificationVisibility.clone();
            }
            i++;
        }
        return notificationVisibilityArr;
    }

    private static NotificationVisibility.NotificationLocation convertNotificationLocation(int i) {
        return i != 1 ? i != 2 ? i != 4 ? i != 8 ? i != 16 ? i != 64 ? NotificationVisibility.NotificationLocation.LOCATION_UNKNOWN : NotificationVisibility.NotificationLocation.LOCATION_GONE : NotificationVisibility.NotificationLocation.LOCATION_BOTTOM_STACK_HIDDEN : NotificationVisibility.NotificationLocation.LOCATION_BOTTOM_STACK_PEEKING : NotificationVisibility.NotificationLocation.LOCATION_MAIN_AREA : NotificationVisibility.NotificationLocation.LOCATION_HIDDEN_TOP : NotificationVisibility.NotificationLocation.LOCATION_FIRST_HEADS_UP;
    }

    public static NotificationVisibility.NotificationLocation getNotificationLocation(NotificationEntry notificationEntry) {
        return (notificationEntry == null || notificationEntry.getRow() == null || notificationEntry.getRow().getViewState() == null) ? NotificationVisibility.NotificationLocation.LOCATION_UNKNOWN : convertNotificationLocation(notificationEntry.getRow().getViewState().location);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List getVisibleNotifications() {
        return (List) this.mNotifLiveDataStore.getActiveNotifList().getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$logNotificationVisibilityChanges$0(NotificationVisibility[] notificationVisibilityArr, NotificationVisibility[] notificationVisibilityArr2) {
        try {
            this.mBarService.onNotificationVisibilityChanged(notificationVisibilityArr, notificationVisibilityArr2);
        } catch (RemoteException unused) {
        }
        int length = notificationVisibilityArr.length;
        if (length > 0) {
            String[] strArr = new String[length];
            for (int i = 0; i < length; i++) {
                strArr[i] = notificationVisibilityArr[i].key;
            }
        }
        recycleAllVisibilityObjects(notificationVisibilityArr);
        recycleAllVisibilityObjects(notificationVisibilityArr2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void logNotificationVisibilityChanges(Collection collection, Collection collection2) {
        if (collection.isEmpty() && collection2.isEmpty()) {
            return;
        }
        final NotificationVisibility[] notificationVisibilityArrCloneVisibilitiesAsArr = cloneVisibilitiesAsArr(collection);
        final NotificationVisibility[] notificationVisibilityArrCloneVisibilitiesAsArr2 = cloneVisibilitiesAsArr(collection2);
        this.mUiBgExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.notification.logging.NotificationLogger$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$logNotificationVisibilityChanges$0(notificationVisibilityArrCloneVisibilitiesAsArr, notificationVisibilityArrCloneVisibilitiesAsArr2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void recycleAllVisibilityObjects(ArraySet arraySet) {
        int size = arraySet.size();
        for (int i = 0; i < size; i++) {
            ((NotificationVisibility) arraySet.valueAt(i)).recycle();
        }
        arraySet.clear();
    }

    private void recycleAllVisibilityObjects(NotificationVisibility[] notificationVisibilityArr) {
        for (NotificationVisibility notificationVisibility : notificationVisibilityArr) {
            if (notificationVisibility != null) {
                notificationVisibility.recycle();
            }
        }
    }

    private void registerNewPipelineListener() {
        this.mNotifPipeline.addCollectionListener(new NotifCollectionListener() { // from class: com.android.systemui.statusbar.notification.logging.NotificationLogger.2
            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public void onEntryRemoved(NotificationEntry notificationEntry, int i) {
                NotificationLogger.this.mExpansionStateLogger.onEntryRemoved(notificationEntry.getKey());
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public void onEntryUpdated(NotificationEntry notificationEntry, boolean z) {
                NotificationLogger.this.mExpansionStateLogger.onEntryUpdated(notificationEntry.getKey());
            }
        });
    }

    public Runnable getVisibilityReporter() {
        return this.mVisibilityReporter;
    }

    void onChildLocationsChanged() {
        if (this.mHandler.hasCallbacks(this.mVisibilityReporter)) {
            return;
        }
        this.mHandler.postAtTime(this.mVisibilityReporter, this.mLastVisibilityReportUptimeMs + 500);
    }

    @Override // com.android.systemui.statusbar.notification.stack.ui.view.NotificationRowStatsLogger
    public void onNotificationExpansionChanged(String str, boolean z, int i, boolean z2) {
        this.mExpansionStateLogger.onExpansionChanged(str, z2, z, this.mVisibilityProvider.getLocation(str));
    }

    public void setUpWithContainer(NotificationListContainer notificationListContainer) {
        this.mListContainer = notificationListContainer;
        if (this.mLogging) {
            notificationListContainer.setChildLocationsChangedListener(new OnChildLocationsChangedListener() { // from class: com.android.systemui.statusbar.notification.logging.NotificationLogger$$ExternalSyntheticLambda1
                @Override // com.android.systemui.statusbar.notification.logging.NotificationLogger.OnChildLocationsChangedListener
                public final void onChildLocationsChanged() {
                    this.f$0.onChildLocationsChanged();
                }
            });
        }
    }

    public void setVisibilityReporter(Runnable runnable) {
        this.mVisibilityReporter = runnable;
    }
}
