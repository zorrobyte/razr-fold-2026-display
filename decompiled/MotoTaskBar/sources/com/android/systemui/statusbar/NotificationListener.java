package com.android.systemui.statusbar;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.RemoteException;
import android.os.UserHandle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import com.android.systemui.util.Compile;
import com.android.systemui.util.time.SystemClock;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes.dex */
public class NotificationListener extends NotificationListenerService {
    private static final boolean DEBUG = Compile.IS_DEBUG;
    private final Context mContext;
    private final Executor mMainExecutor;
    private final NotificationManager mNotificationManager;
    private final SystemClock mSystemClock;
    private final ArrayList mSettingsListeners = new ArrayList();
    private final Deque mRankingMapQueue = new ConcurrentLinkedDeque();
    private final Runnable mDispatchRankingUpdateRunnable = new Runnable() { // from class: com.android.systemui.statusbar.NotificationListener$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
            this.f$0.dispatchRankingUpdate();
        }
    };
    private long mSkippingRankingUpdatesSince = -1;
    private boolean mRegistered = false;
    private NotificationHandlerProxy mNotificationHandlerProxy = new NotificationHandlerProxy();

    public interface NotificationHandler {
        default void onNotificationChannelModified(String str, UserHandle userHandle, NotificationChannel notificationChannel, int i) {
        }

        void onNotificationPosted(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap);

        void onNotificationRankingUpdate(NotificationListenerService.RankingMap rankingMap);

        void onNotificationRemoved(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap, int i);

        void onNotificationsInitialized();
    }

    class NotificationHandlerProxy implements NotificationHandler {
        private final List mNotificationHandlers;
        private final Map mNotificationMap;

        private NotificationHandlerProxy() {
            this.mNotificationHandlers = new ArrayList();
            this.mNotificationMap = new HashMap();
        }

        public void addNotificationHandler(NotificationHandler notificationHandler) {
            if (this.mNotificationHandlers.contains(notificationHandler)) {
                throw new IllegalArgumentException("Listener is already added");
            }
            this.mNotificationHandlers.add(notificationHandler);
            if (this.mNotificationMap.isEmpty()) {
                return;
            }
            NotificationListenerService.RankingMap currentRanking = NotificationListener.this.getCurrentRanking();
            ArrayList arrayList = new ArrayList();
            Iterator it = this.mNotificationMap.values().iterator();
            while (it.hasNext()) {
                arrayList.add(NotificationListener.getRankingOrTemporaryStandIn(currentRanking, ((StatusBarNotification) it.next()).getKey()));
            }
            NotificationListenerService.RankingMap rankingMap = new NotificationListenerService.RankingMap((NotificationListenerService.Ranking[]) arrayList.toArray(new NotificationListenerService.Ranking[0]));
            Iterator it2 = this.mNotificationMap.values().iterator();
            while (it2.hasNext()) {
                notificationHandler.onNotificationPosted((StatusBarNotification) it2.next(), rankingMap);
            }
            notificationHandler.onNotificationsInitialized();
        }

        @Override // com.android.systemui.statusbar.NotificationListener.NotificationHandler
        public void onNotificationChannelModified(String str, UserHandle userHandle, NotificationChannel notificationChannel, int i) {
            Iterator it = this.mNotificationHandlers.iterator();
            while (it.hasNext()) {
                ((NotificationHandler) it.next()).onNotificationChannelModified(str, userHandle, notificationChannel, i);
            }
        }

        @Override // com.android.systemui.statusbar.NotificationListener.NotificationHandler
        public void onNotificationPosted(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap) {
            this.mNotificationMap.put(statusBarNotification.getKey(), statusBarNotification);
            Iterator it = this.mNotificationHandlers.iterator();
            while (it.hasNext()) {
                ((NotificationHandler) it.next()).onNotificationPosted(statusBarNotification, rankingMap);
            }
        }

        @Override // com.android.systemui.statusbar.NotificationListener.NotificationHandler
        public void onNotificationRankingUpdate(NotificationListenerService.RankingMap rankingMap) {
            Iterator it = this.mNotificationHandlers.iterator();
            while (it.hasNext()) {
                ((NotificationHandler) it.next()).onNotificationRankingUpdate(rankingMap);
            }
        }

        @Override // com.android.systemui.statusbar.NotificationListener.NotificationHandler
        public void onNotificationRemoved(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap, int i) {
            this.mNotificationMap.remove(statusBarNotification.getKey());
            Iterator it = this.mNotificationHandlers.iterator();
            while (it.hasNext()) {
                ((NotificationHandler) it.next()).onNotificationRemoved(statusBarNotification, rankingMap, i);
            }
        }

        @Override // com.android.systemui.statusbar.NotificationListener.NotificationHandler
        public void onNotificationsInitialized() {
            Iterator it = this.mNotificationHandlers.iterator();
            while (it.hasNext()) {
                ((NotificationHandler) it.next()).onNotificationsInitialized();
            }
        }

        public void removeNotificationHandle(NotificationHandler notificationHandler) {
            this.mNotificationHandlers.remove(notificationHandler);
        }
    }

    public NotificationListener(Context context, NotificationManager notificationManager, SystemClock systemClock, Executor executor) {
        this.mContext = context;
        this.mNotificationManager = notificationManager;
        this.mSystemClock = systemClock;
        this.mMainExecutor = executor;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchRankingUpdate() {
        boolean z = DEBUG;
        if (z) {
            Log.d("NotificationListener", "dispatchRankingUpdate");
        }
        NotificationListenerService.RankingMap rankingMap = (NotificationListenerService.RankingMap) this.mRankingMapQueue.pollFirst();
        if (rankingMap == null) {
            Log.wtf("NotificationListener", "mRankingMapQueue was empty!");
        }
        if (!this.mRankingMapQueue.isEmpty()) {
            long jElapsedRealtime = this.mSystemClock.elapsedRealtime();
            if (this.mSkippingRankingUpdatesSince == -1) {
                this.mSkippingRankingUpdatesSince = jElapsedRealtime;
            }
            if (jElapsedRealtime - this.mSkippingRankingUpdatesSince < 500) {
                if (z) {
                    Log.d("NotificationListener", "Skipping dispatch of onNotificationRankingUpdate() -- " + this.mRankingMapQueue.size() + " more updates already in the queue.");
                    return;
                }
                return;
            }
            if (z) {
                Log.d("NotificationListener", "Proceeding with dispatch of onNotificationRankingUpdate() -- " + this.mRankingMapQueue.size() + " more updates already in the queue.");
            }
        }
        this.mSkippingRankingUpdatesSince = -1L;
        this.mNotificationHandlerProxy.onNotificationRankingUpdate(rankingMap);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static NotificationListenerService.Ranking getRankingOrTemporaryStandIn(NotificationListenerService.RankingMap rankingMap, String str) {
        NotificationListenerService.Ranking ranking = new NotificationListenerService.Ranking();
        if (!rankingMap.getRanking(str, ranking)) {
            ranking.populate(str, 0, false, 0, 0, 0, null, null, null, new ArrayList(), new ArrayList(), false, 0, false, 0L, false, new ArrayList(), new ArrayList(), false, false, false, null, 0, false, 0, false, null);
        }
        return ranking;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addNotificationHandler$0(NotificationHandler notificationHandler) {
        this.mNotificationHandlerProxy.addNotificationHandler(notificationHandler);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onListenerConnected$2(StatusBarNotification[] statusBarNotificationArr, NotificationListenerService.RankingMap rankingMap) {
        ArrayList arrayList = new ArrayList();
        for (StatusBarNotification statusBarNotification : statusBarNotificationArr) {
            arrayList.add(getRankingOrTemporaryStandIn(rankingMap, statusBarNotification.getKey()));
        }
        NotificationListenerService.RankingMap rankingMap2 = new NotificationListenerService.RankingMap((NotificationListenerService.Ranking[]) arrayList.toArray(new NotificationListenerService.Ranking[0]));
        for (StatusBarNotification statusBarNotification2 : statusBarNotificationArr) {
            this.mNotificationHandlerProxy.onNotificationPosted(statusBarNotification2, rankingMap2);
        }
        this.mNotificationHandlerProxy.onNotificationsInitialized();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onNotificationChannelModified$5(String str, UserHandle userHandle, NotificationChannel notificationChannel, int i) {
        this.mNotificationHandlerProxy.onNotificationChannelModified(str, userHandle, notificationChannel, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onNotificationPosted$3(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap) {
        this.mNotificationHandlerProxy.onNotificationPosted(statusBarNotification, rankingMap);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onNotificationRemoved$4(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap, int i) {
        this.mNotificationHandlerProxy.onNotificationRemoved(statusBarNotification, rankingMap, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$removeNotificationHandle$1(NotificationHandler notificationHandler) {
        this.mNotificationHandlerProxy.removeNotificationHandle(notificationHandler);
    }

    public void addNotificationHandler(final NotificationHandler notificationHandler) {
        this.mMainExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.NotificationListener$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$addNotificationHandler$0(notificationHandler);
            }
        });
    }

    @Override // android.service.notification.NotificationListenerService
    public void onListenerConnected() {
        final StatusBarNotification[] activeNotifications = getActiveNotifications();
        if (activeNotifications == null) {
            Log.w("NotificationListener", "onListenerConnected unable to get active notifications.");
            return;
        }
        if (DEBUG) {
            Log.d("NotificationListener", "onListenerConnected, notifications size: " + activeNotifications.length);
        }
        final NotificationListenerService.RankingMap currentRanking = getCurrentRanking();
        this.mMainExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.NotificationListener$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onListenerConnected$2(activeNotifications, currentRanking);
            }
        });
        onSilentStatusBarIconsVisibilityChanged(this.mNotificationManager.shouldHideSilentStatusBarIcons());
    }

    @Override // android.service.notification.NotificationListenerService
    public void onNotificationChannelModified(final String str, final UserHandle userHandle, final NotificationChannel notificationChannel, final int i) {
        if (DEBUG) {
            Log.d("NotificationListener", "onNotificationChannelModified");
        }
        this.mMainExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.NotificationListener$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onNotificationChannelModified$5(str, userHandle, notificationChannel, i);
            }
        });
    }

    @Override // android.service.notification.NotificationListenerService
    public void onNotificationPosted(final StatusBarNotification statusBarNotification, final NotificationListenerService.RankingMap rankingMap) {
        if (DEBUG) {
            Log.d("NotificationListener", "onNotificationPosted: " + statusBarNotification);
        }
        if (statusBarNotification != null) {
            this.mMainExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.NotificationListener$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onNotificationPosted$3(statusBarNotification, rankingMap);
                }
            });
        }
    }

    @Override // android.service.notification.NotificationListenerService
    public void onNotificationRankingUpdate(NotificationListenerService.RankingMap rankingMap) {
        if (DEBUG) {
            Log.d("NotificationListener", "onRankingUpdate");
        }
        if (rankingMap != null) {
            this.mRankingMapQueue.addLast(rankingMap);
            this.mMainExecutor.execute(this.mDispatchRankingUpdateRunnable);
        }
    }

    @Override // android.service.notification.NotificationListenerService
    public void onNotificationRemoved(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap) {
        onNotificationRemoved(statusBarNotification, rankingMap, 0);
    }

    @Override // android.service.notification.NotificationListenerService
    public void onNotificationRemoved(final StatusBarNotification statusBarNotification, final NotificationListenerService.RankingMap rankingMap, final int i) {
        if (DEBUG) {
            Log.d("NotificationListener", "onNotificationRemoved: " + statusBarNotification + " reason: " + i);
        }
        if (statusBarNotification != null) {
            this.mMainExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.NotificationListener$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onNotificationRemoved$4(statusBarNotification, rankingMap, i);
                }
            });
        }
    }

    @Override // android.service.notification.NotificationListenerService
    public void onSilentStatusBarIconsVisibilityChanged(boolean z) {
    }

    public void registerAsSystemService() {
        if (this.mRegistered) {
            return;
        }
        try {
            registerAsSystemService(this.mContext, new ComponentName(this.mContext.getPackageName(), getClass().getCanonicalName()), -1);
            this.mRegistered = true;
        } catch (RemoteException e) {
            Log.e("NotificationListener", "Unable to register notification listener", e);
        }
    }

    public void removeNotificationHandle(final NotificationHandler notificationHandler) {
        this.mMainExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.NotificationListener$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$removeNotificationHandle$1(notificationHandler);
            }
        });
    }

    public void unregisterAsSystemService() {
        this.mRegistered = false;
        super.unregisterAsSystemService();
    }
}
