package com.android.systemui.statusbar.notification.collection.notifcollection;

import android.os.RemoteException;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.notification.NotificationUtilsKt;
import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.NotifCollection;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.ArrayList;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: NotifCollectionLogger.kt */
/* JADX INFO: loaded from: classes.dex */
public final class NotifCollectionLogger {
    private final LogBuffer buffer;

    public NotifCollectionLogger(LogBuffer logBuffer) {
        logBuffer.getClass();
        this.buffer = logBuffer;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logCancelLocalDismissalNotDismissedNotif$lambda$89(NotificationEntry notificationEntry, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logCancelLocalDismissalNotDismissedNotif$lambda$90(LogMessage logMessage) {
        logMessage.getClass();
        return "CANCEL LOCAL DISMISS Not Dismissed " + logMessage.getStr1();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logDismissAll$lambda$16(int i, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setInt1(i);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logDismissAll$lambda$17(LogMessage logMessage) {
        logMessage.getClass();
        return "DISMISS ALL notifications for user " + logMessage.getInt1();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logDismissAlreadyDismissedNotif$lambda$73(NotificationEntry notificationEntry, int i, int i2, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        logMessage.setInt1(i);
        logMessage.setInt2(i2);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logDismissAlreadyDismissedNotif$lambda$74(LogMessage logMessage) {
        logMessage.getClass();
        return "DISMISS Already Dismissed " + logMessage.getStr1() + " (" + logMessage.getInt1() + "/" + logMessage.getInt2() + ")";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logDismissAlreadyParentDismissedNotif$lambda$75(NotificationEntry notificationEntry, int i, int i2, LogMessage logMessage) {
        String logKey;
        NotificationEntry summary;
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        logMessage.setInt1(i);
        logMessage.setInt2(i2);
        GroupEntry parent = notificationEntry.getParent();
        if (parent == null || (summary = parent.getSummary()) == null || (logKey = NotificationUtilsKt.getLogKey(summary)) == null) {
            logKey = "(null)";
        }
        logMessage.setStr2(logKey);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logDismissAlreadyParentDismissedNotif$lambda$76(LogMessage logMessage) {
        logMessage.getClass();
        return "DISMISS Already Parent-Dismissed " + logMessage.getStr1() + " (" + logMessage.getInt1() + "/" + logMessage.getInt2() + ") with summary " + logMessage.getStr2();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logDismissNonExistentNotif$lambda$12(NotificationEntry notificationEntry, int i, int i2, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        logMessage.setInt1(i);
        logMessage.setInt2(i2);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logDismissNonExistentNotif$lambda$13(LogMessage logMessage) {
        logMessage.getClass();
        return "DISMISS Non Existent " + logMessage.getStr1() + " (" + logMessage.getInt1() + "/" + logMessage.getInt2() + ")";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logEntryBeingExtendedNotInCollection$lambda$55(NotificationEntry notificationEntry, NotifLifetimeExtender notifLifetimeExtender, String str, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        logMessage.setStr2(notifLifetimeExtender.getName());
        logMessage.setStr3(str);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logEntryBeingExtendedNotInCollection$lambda$56(LogMessage logMessage) {
        logMessage.getClass();
        return "While ending lifetime extension by " + logMessage.getStr2() + " of " + logMessage.getStr1() + ", entry in collection is " + logMessage.getStr3();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logFoundNotifications$lambda$43(int i, List list, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setInt1(i);
        logMessage.setInt2(list.size());
        logMessage.setStr1(CollectionsKt.joinToString$default(list, null, null, null, 0, null, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda52
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logFoundNotifications$lambda$43$lambda$42((String) obj);
            }
        }, 31, null));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CharSequence logFoundNotifications$lambda$43$lambda$42(String str) {
        str.getClass();
        String strLogKey = NotificationUtilsKt.logKey(str);
        return strLogKey != null ? strLogKey : "null";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logFoundNotifications$lambda$44(LogMessage logMessage) {
        logMessage.getClass();
        return "Collection missing " + logMessage.getInt1() + " entries in ranking update. Just found " + logMessage.getInt2() + ": " + logMessage.getStr1();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logFutureDismissalAlreadyCancelledByServer$lambda$65(NotifCollection.FutureDismissal futureDismissal, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(futureDismissal.getLabel());
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logFutureDismissalAlreadyCancelledByServer$lambda$66(LogMessage logMessage) {
        logMessage.getClass();
        return "Ignoring: entry already cancelled by server: " + logMessage.getStr1();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logFutureDismissalDismissing$lambda$69(NotifCollection.FutureDismissal futureDismissal, String str, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(futureDismissal.getLabel());
        logMessage.setStr2(str);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logFutureDismissalDismissing$lambda$70(LogMessage logMessage) {
        logMessage.getClass();
        return "Dismissing " + logMessage.getStr2() + " for: " + logMessage.getStr1();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logFutureDismissalDoubleCancelledByServer$lambda$61(NotifCollection.FutureDismissal futureDismissal, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(futureDismissal.getLabel());
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logFutureDismissalDoubleCancelledByServer$lambda$62(LogMessage logMessage) {
        logMessage.getClass();
        return "System server double cancelled: " + logMessage.getStr1();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logFutureDismissalDoubleRun$lambda$63(NotifCollection.FutureDismissal futureDismissal, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(futureDismissal.getLabel());
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logFutureDismissalDoubleRun$lambda$64(LogMessage logMessage) {
        logMessage.getClass();
        return "Double run: " + logMessage.getStr1();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logFutureDismissalGotSystemServerCancel$lambda$67(NotifCollection.FutureDismissal futureDismissal, int i, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(futureDismissal.getLabel());
        logMessage.setInt1(i);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logFutureDismissalGotSystemServerCancel$lambda$68(LogMessage logMessage) {
        logMessage.getClass();
        return "SystemServer cancelled: " + logMessage.getStr1() + " reason=" + NotifCollectionLoggerKt.cancellationReasonDebugString(logMessage.getInt1());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logFutureDismissalMismatchedEntry$lambda$71(NotifCollection.FutureDismissal futureDismissal, String str, NotificationEntry notificationEntry, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(futureDismissal.getLabel());
        logMessage.setStr2(str);
        logMessage.setStr3(NotificationUtilsKt.getLogKey(notificationEntry));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logFutureDismissalMismatchedEntry$lambda$72(LogMessage logMessage) {
        logMessage.getClass();
        return "Mismatch: current " + logMessage.getStr2() + " is " + logMessage.getStr3() + " for: " + logMessage.getStr1();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logFutureDismissalRegistered$lambda$59(NotifCollection.FutureDismissal futureDismissal, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(futureDismissal.getLabel());
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logFutureDismissalRegistered$lambda$60(LogMessage logMessage) {
        logMessage.getClass();
        return "Registered: " + logMessage.getStr1();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logFutureDismissalReused$lambda$57(NotifCollection.FutureDismissal futureDismissal, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(futureDismissal.getLabel());
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logFutureDismissalReused$lambda$58(LogMessage logMessage) {
        logMessage.getClass();
        return "Reusing existing registration: " + logMessage.getStr1();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logLifetimeExtended$lambda$49(NotificationEntry notificationEntry, NotifLifetimeExtender notifLifetimeExtender, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        logMessage.setStr2(notifLifetimeExtender.getName());
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logLifetimeExtended$lambda$50(LogMessage logMessage) {
        logMessage.getClass();
        return "LIFETIME EXTENDED: " + logMessage.getStr1() + " by " + logMessage.getStr2();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logLifetimeExtensionEnded$lambda$51(NotificationEntry notificationEntry, NotifLifetimeExtender notifLifetimeExtender, int i, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        logMessage.setStr2(notifLifetimeExtender.getName());
        logMessage.setInt1(i);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logLifetimeExtensionEnded$lambda$52(LogMessage logMessage) {
        logMessage.getClass();
        return "LIFETIME EXTENSION ENDED for " + logMessage.getStr1() + " by '" + logMessage.getStr2() + "'; " + logMessage.getInt1() + " remaining extensions";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logLocallyDismissAlreadyDismissedChild$lambda$85(NotificationEntry notificationEntry, NotificationEntry notificationEntry2, int i, int i2, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        logMessage.setStr2(NotificationUtilsKt.getLogKey(notificationEntry2));
        logMessage.setInt1(i);
        logMessage.setInt2(i2);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logLocallyDismissAlreadyDismissedChild$lambda$86(LogMessage logMessage) {
        logMessage.getClass();
        return "LOCALLY DISMISS Already Dismissed Child " + logMessage.getStr1() + " of parent " + logMessage.getStr2() + " (" + logMessage.getInt1() + "/" + logMessage.getInt2() + ")";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logLocallyDismissAlreadyDismissedNotif$lambda$81(NotificationEntry notificationEntry, int i, int i2, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        logMessage.setInt1(i);
        logMessage.setInt2(i2);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logLocallyDismissAlreadyDismissedNotif$lambda$82(LogMessage logMessage) {
        logMessage.getClass();
        return "LOCALLY DISMISS Already Dismissed " + logMessage.getStr1() + " (" + logMessage.getInt1() + "/" + logMessage.getInt2() + ")";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logLocallyDismissAlreadyParentDismissedChild$lambda$87(NotificationEntry notificationEntry, NotificationEntry notificationEntry2, int i, int i2, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        logMessage.setStr2(NotificationUtilsKt.getLogKey(notificationEntry2));
        logMessage.setInt1(i);
        logMessage.setInt2(i2);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logLocallyDismissAlreadyParentDismissedChild$lambda$88(LogMessage logMessage) {
        logMessage.getClass();
        return "LOCALLY DISMISS Already Parent-Dismissed Child " + logMessage.getStr1() + " of parent " + logMessage.getStr2() + " (" + logMessage.getInt1() + "/" + logMessage.getInt2() + ")";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logLocallyDismissAlreadyParentDismissedNotif$lambda$83(NotificationEntry notificationEntry, int i, int i2, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        logMessage.setInt1(i);
        logMessage.setInt2(i2);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logLocallyDismissAlreadyParentDismissedNotif$lambda$84(LogMessage logMessage) {
        logMessage.getClass();
        return "LOCALLY DISMISS Already Dismissed " + logMessage.getStr1() + " (" + logMessage.getInt1() + "/" + logMessage.getInt2() + ")";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logLocallyDismissMismatchedEntry$lambda$79(NotificationEntry notificationEntry, int i, int i2, NotificationEntry notificationEntry2, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        logMessage.setInt1(i);
        logMessage.setInt2(i2);
        logMessage.setStr2(Integer.toHexString(notificationEntry.hashCode()));
        logMessage.setStr3(Integer.toHexString(notificationEntry2.hashCode()));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logLocallyDismissMismatchedEntry$lambda$80(LogMessage logMessage) {
        logMessage.getClass();
        return "LOCALLY DISMISS Mismatch " + logMessage.getStr1() + " (" + logMessage.getInt1() + "/" + logMessage.getInt2() + "): dismissing @" + logMessage.getStr2() + " but stored @" + logMessage.getStr3();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logLocallyDismissNonExistentNotif$lambda$77(NotificationEntry notificationEntry, int i, int i2, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        logMessage.setInt1(i);
        logMessage.setInt2(i2);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logLocallyDismissNonExistentNotif$lambda$78(LogMessage logMessage) {
        logMessage.getClass();
        return "LOCALLY DISMISS Non Existent " + logMessage.getStr1() + " (" + logMessage.getInt1() + "/" + logMessage.getInt2() + ")";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logLocallyDismissed$lambda$10(NotificationEntry notificationEntry, int i, int i2, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        logMessage.setInt1(i);
        logMessage.setInt2(i2);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logLocallyDismissed$lambda$11(LogMessage logMessage) {
        logMessage.getClass();
        return "LOCALLY DISMISSED " + logMessage.getStr1() + " (" + logMessage.getInt1() + "/" + logMessage.getInt2() + ")";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logLocallyDismissedAlreadyCanceledEntry$lambda$18(NotificationEntry notificationEntry, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logLocallyDismissedAlreadyCanceledEntry$lambda$19(LogMessage logMessage) {
        logMessage.getClass();
        return "LOCALLY DISMISSED Already Canceled " + logMessage.getStr1() + ". Trying to remove.";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logLocallyDismissedChild$lambda$14(NotificationEntry notificationEntry, NotificationEntry notificationEntry2, int i, int i2, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        logMessage.setStr2(NotificationUtilsKt.getLogKey(notificationEntry2));
        logMessage.setInt1(i);
        logMessage.setInt2(i2);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logLocallyDismissedChild$lambda$15(LogMessage logMessage) {
        logMessage.getClass();
        return "LOCALLY DISMISSED CHILD (inferred): " + logMessage.getStr1() + " of parent " + logMessage.getStr2() + " (" + logMessage.getInt1() + "/" + logMessage.getInt2() + ")";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logMissingNotifications$lambda$40(int i, List list, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setInt1(i);
        logMessage.setInt2(list.size());
        logMessage.setStr1(CollectionsKt.joinToString$default(list, null, null, null, 0, null, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda60
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logMissingNotifications$lambda$40$lambda$39((String) obj);
            }
        }, 31, null));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CharSequence logMissingNotifications$lambda$40$lambda$39(String str) {
        str.getClass();
        String strLogKey = NotificationUtilsKt.logKey(str);
        return strLogKey != null ? strLogKey : "null";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logMissingNotifications$lambda$41(LogMessage logMessage) {
        logMessage.getClass();
        return "Collection missing " + logMessage.getInt1() + " entries in ranking update. Just lost " + logMessage.getInt2() + ": " + logMessage.getStr1();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logMissingRankings$lambda$31(int i, List list, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setInt1(i);
        logMessage.setInt2(list.size());
        logMessage.setStr1(CollectionsKt.joinToString$default(list, null, null, null, 0, null, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda59
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logMissingRankings$lambda$31$lambda$30((NotificationEntry) obj);
            }
        }, 31, null));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CharSequence logMissingRankings$lambda$31$lambda$30(NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        String logKey = NotificationUtilsKt.getLogKey(notificationEntry);
        return logKey != null ? logKey : "null";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logMissingRankings$lambda$32(LogMessage logMessage) {
        logMessage.getClass();
        return "Ranking update is missing ranking for " + logMessage.getInt1() + " entries (" + logMessage.getInt2() + " new): " + logMessage.getStr1();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logMissingRankings$lambda$34(NotificationListenerService.RankingMap rankingMap, LogMessage logMessage) {
        logMessage.getClass();
        String[] orderedKeys = rankingMap.getOrderedKeys();
        orderedKeys.getClass();
        ArrayList arrayList = new ArrayList(orderedKeys.length);
        for (String str : orderedKeys) {
            String strLogKey = NotificationUtilsKt.logKey(str);
            if (strLogKey == null) {
                strLogKey = "null";
            }
            arrayList.add(strLogKey);
        }
        logMessage.setStr1(arrayList.toString());
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logMissingRankings$lambda$35(LogMessage logMessage) {
        logMessage.getClass();
        return "Ranking map contents: " + logMessage.getStr1();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logNoNotificationToRemoveWithKey$lambda$28(StatusBarNotification statusBarNotification, int i, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(statusBarNotification));
        logMessage.setInt1(i);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logNoNotificationToRemoveWithKey$lambda$29(LogMessage logMessage) {
        logMessage.getClass();
        return "No notification to remove with key " + logMessage.getStr1() + " reason=" + NotifCollectionLoggerKt.cancellationReasonDebugString(logMessage.getInt1());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logNotifClearAllDismissalIntercepted$lambda$22(NotificationEntry notificationEntry, int i, int i2, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        logMessage.setInt1(i);
        logMessage.setInt2(i2);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logNotifClearAllDismissalIntercepted$lambda$23(LogMessage logMessage) {
        logMessage.getClass();
        return "CLEAR ALL DISMISSAL INTERCEPTED " + logMessage.getStr1() + " (" + logMessage.getInt1() + "/" + logMessage.getInt2() + ")";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logNotifDismissedIntercepted$lambda$20(NotificationEntry notificationEntry, int i, int i2, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        logMessage.setInt1(i);
        logMessage.setInt2(i2);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logNotifDismissedIntercepted$lambda$21(LogMessage logMessage) {
        logMessage.getClass();
        return "DISMISS INTERCEPTED " + logMessage.getStr1() + " (" + logMessage.getInt1() + "/" + logMessage.getInt2() + ")";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logNotifGroupPosted$lambda$2(String str, int i, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.logKey(str));
        logMessage.setInt1(i);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logNotifGroupPosted$lambda$3(LogMessage logMessage) {
        logMessage.getClass();
        return "POSTED GROUP " + logMessage.getStr1() + " (" + logMessage.getInt1() + " events)";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logNotifInternalUpdate$lambda$24(NotificationEntry notificationEntry, String str, String str2, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        logMessage.setStr2(str);
        logMessage.setStr3(str2);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logNotifInternalUpdate$lambda$25(LogMessage logMessage) {
        logMessage.getClass();
        return "UPDATED INTERNALLY " + logMessage.getStr1() + " BY " + logMessage.getStr2() + " BECAUSE " + logMessage.getStr3();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logNotifInternalUpdateFailed$lambda$26(StatusBarNotification statusBarNotification, String str, String str2, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(statusBarNotification));
        logMessage.setStr2(str);
        logMessage.setStr3(str2);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logNotifInternalUpdateFailed$lambda$27(LogMessage logMessage) {
        logMessage.getClass();
        return "FAILED INTERNAL UPDATE " + logMessage.getStr1() + " BY " + logMessage.getStr2() + " BECAUSE " + logMessage.getStr3();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logNotifPosted$lambda$0(NotificationEntry notificationEntry, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logNotifPosted$lambda$1(LogMessage logMessage) {
        logMessage.getClass();
        return "POSTED " + logMessage.getStr1();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logNotifReleased$lambda$8(NotificationEntry notificationEntry, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logNotifReleased$lambda$9(LogMessage logMessage) {
        logMessage.getClass();
        return "RELEASED " + logMessage.getStr1();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logNotifRemoved$lambda$6(StatusBarNotification statusBarNotification, int i, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(statusBarNotification));
        logMessage.setInt1(i);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logNotifRemoved$lambda$7(LogMessage logMessage) {
        logMessage.getClass();
        return "REMOVED " + logMessage.getStr1() + " reason=" + NotifCollectionLoggerKt.cancellationReasonDebugString(logMessage.getInt1());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logNotifUpdated$lambda$4(NotificationEntry notificationEntry, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logNotifUpdated$lambda$5(LogMessage logMessage) {
        logMessage.getClass();
        return "UPDATED " + logMessage.getStr1();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logRecoveredRankings$lambda$37(int i, List list, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setInt1(i);
        logMessage.setInt1(list.size());
        logMessage.setStr1(CollectionsKt.joinToString$default(list, null, null, null, 0, null, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda75
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logRecoveredRankings$lambda$37$lambda$36((String) obj);
            }
        }, 31, null));
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CharSequence logRecoveredRankings$lambda$37$lambda$36(String str) {
        str.getClass();
        String strLogKey = NotificationUtilsKt.logKey(str);
        return strLogKey != null ? strLogKey : "null";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logRecoveredRankings$lambda$38(LogMessage logMessage) {
        logMessage.getClass();
        return "Ranking update now contains rankings for " + logMessage.getInt1() + " previously inconsistent entries: " + logMessage.getStr1();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logRemoteExceptionOnClearAllNotifications$lambda$47(RemoteException remoteException, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(remoteException.toString());
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logRemoteExceptionOnClearAllNotifications$lambda$48(LogMessage logMessage) {
        logMessage.getClass();
        return "RemoteException while attempting to clear all notifications:\n" + logMessage.getStr1();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit logRemoteExceptionOnNotificationClear$lambda$45(NotificationEntry notificationEntry, int i, int i2, RemoteException remoteException, LogMessage logMessage) {
        logMessage.getClass();
        logMessage.setStr1(NotificationUtilsKt.getLogKey(notificationEntry));
        logMessage.setInt1(i);
        logMessage.setInt2(i2);
        logMessage.setStr2(remoteException.toString());
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String logRemoteExceptionOnNotificationClear$lambda$46(LogMessage logMessage) {
        logMessage.getClass();
        return "RemoteException while attempting to clear " + logMessage.getStr1() + " (" + logMessage.getInt1() + "/" + logMessage.getInt2() + "):\n" + logMessage.getStr2();
    }

    public final void logCancelLocalDismissalNotDismissedNotif(final NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        LogBuffer.log$default(this.buffer, "NotifCollection", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda76
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logCancelLocalDismissalNotDismissedNotif$lambda$89(notificationEntry, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda77
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logCancelLocalDismissalNotDismissedNotif$lambda$90((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logDismissAll(final int i) {
        LogBuffer.log$default(this.buffer, "NotifCollection", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda42
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logDismissAll$lambda$16(i, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda43
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logDismissAll$lambda$17((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logDismissAlreadyDismissedNotif(final NotificationEntry notificationEntry, final int i, final int i2) {
        notificationEntry.getClass();
        LogBuffer.log$default(this.buffer, "NotifCollection", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda73
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logDismissAlreadyDismissedNotif$lambda$73(notificationEntry, i, i2, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda74
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logDismissAlreadyDismissedNotif$lambda$74((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logDismissAlreadyParentDismissedNotif(final NotificationEntry notificationEntry, final int i, final int i2) {
        notificationEntry.getClass();
        LogBuffer.log$default(this.buffer, "NotifCollection", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda55
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logDismissAlreadyParentDismissedNotif$lambda$75(notificationEntry, i, i2, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda56
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logDismissAlreadyParentDismissedNotif$lambda$76((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logDismissNonExistentNotif(final NotificationEntry notificationEntry, final int i, final int i2) {
        notificationEntry.getClass();
        LogBuffer.log$default(this.buffer, "NotifCollection", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda78
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logDismissNonExistentNotif$lambda$12(notificationEntry, i, i2, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda79
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logDismissNonExistentNotif$lambda$13((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logEntryBeingExtendedNotInCollection(final NotificationEntry notificationEntry, final NotifLifetimeExtender notifLifetimeExtender, final String str) {
        notificationEntry.getClass();
        notifLifetimeExtender.getClass();
        str.getClass();
        LogBuffer.log$default(this.buffer, "NotifCollection", LogLevel.WARNING, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logEntryBeingExtendedNotInCollection$lambda$55(notificationEntry, notifLifetimeExtender, str, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda7
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logEntryBeingExtendedNotInCollection$lambda$56((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logFoundNotifications(final List list, final int i) {
        list.getClass();
        LogBuffer.log$default(this.buffer, "NotifCollection", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda48
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logFoundNotifications$lambda$43(i, list, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda49
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logFoundNotifications$lambda$44((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logFutureDismissalAlreadyCancelledByServer(final NotifCollection.FutureDismissal futureDismissal) {
        futureDismissal.getClass();
        LogBuffer.log$default(this.buffer, "NotifCollection", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logFutureDismissalAlreadyCancelledByServer$lambda$65(futureDismissal, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logFutureDismissalAlreadyCancelledByServer$lambda$66((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logFutureDismissalDismissing(final NotifCollection.FutureDismissal futureDismissal, final String str) {
        futureDismissal.getClass();
        str.getClass();
        LogBuffer.log$default(this.buffer, "NotifCollection", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logFutureDismissalDismissing$lambda$69(futureDismissal, str, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logFutureDismissalDismissing$lambda$70((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logFutureDismissalDoubleCancelledByServer(final NotifCollection.FutureDismissal futureDismissal) {
        futureDismissal.getClass();
        LogBuffer.log$default(this.buffer, "NotifCollection", LogLevel.WARNING, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda82
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logFutureDismissalDoubleCancelledByServer$lambda$61(futureDismissal, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda83
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logFutureDismissalDoubleCancelledByServer$lambda$62((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logFutureDismissalDoubleRun(final NotifCollection.FutureDismissal futureDismissal) {
        futureDismissal.getClass();
        LogBuffer.log$default(this.buffer, "NotifCollection", LogLevel.WARNING, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda86
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logFutureDismissalDoubleRun$lambda$63(futureDismissal, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda87
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logFutureDismissalDoubleRun$lambda$64((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logFutureDismissalGotSystemServerCancel(final NotifCollection.FutureDismissal futureDismissal, final int i) {
        futureDismissal.getClass();
        LogBuffer.log$default(this.buffer, "NotifCollection", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda84
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logFutureDismissalGotSystemServerCancel$lambda$67(futureDismissal, i, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda85
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logFutureDismissalGotSystemServerCancel$lambda$68((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logFutureDismissalMismatchedEntry(final NotifCollection.FutureDismissal futureDismissal, final String str, final NotificationEntry notificationEntry) {
        futureDismissal.getClass();
        str.getClass();
        LogBuffer.log$default(this.buffer, "NotifCollection", LogLevel.WARNING, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda8
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logFutureDismissalMismatchedEntry$lambda$71(futureDismissal, str, notificationEntry, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda9
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logFutureDismissalMismatchedEntry$lambda$72((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logFutureDismissalRegistered(final NotifCollection.FutureDismissal futureDismissal) {
        futureDismissal.getClass();
        LogBuffer.log$default(this.buffer, "NotifCollection", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda71
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logFutureDismissalRegistered$lambda$59(futureDismissal, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda72
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logFutureDismissalRegistered$lambda$60((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logFutureDismissalReused(final NotifCollection.FutureDismissal futureDismissal) {
        futureDismissal.getClass();
        LogBuffer.log$default(this.buffer, "NotifCollection", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda65
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logFutureDismissalReused$lambda$57(futureDismissal, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda66
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logFutureDismissalReused$lambda$58((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logLifetimeExtended(final NotificationEntry notificationEntry, final NotifLifetimeExtender notifLifetimeExtender) {
        notificationEntry.getClass();
        notifLifetimeExtender.getClass();
        LogBuffer.log$default(this.buffer, "NotifCollection", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda40
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logLifetimeExtended$lambda$49(notificationEntry, notifLifetimeExtender, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda41
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logLifetimeExtended$lambda$50((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logLifetimeExtensionEnded(final NotificationEntry notificationEntry, final NotifLifetimeExtender notifLifetimeExtender, final int i) {
        notificationEntry.getClass();
        notifLifetimeExtender.getClass();
        LogBuffer.log$default(this.buffer, "NotifCollection", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logLifetimeExtensionEnded$lambda$51(notificationEntry, notifLifetimeExtender, i, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logLifetimeExtensionEnded$lambda$52((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logLocallyDismissAlreadyDismissedChild(final NotificationEntry notificationEntry, final NotificationEntry notificationEntry2, final int i, final int i2) {
        notificationEntry.getClass();
        notificationEntry2.getClass();
        LogBuffer.log$default(this.buffer, "NotifCollection", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda22
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logLocallyDismissAlreadyDismissedChild$lambda$85(notificationEntry, notificationEntry2, i, i2, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda23
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logLocallyDismissAlreadyDismissedChild$lambda$86((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logLocallyDismissAlreadyDismissedNotif(final NotificationEntry notificationEntry, final int i, final int i2) {
        notificationEntry.getClass();
        LogBuffer.log$default(this.buffer, "NotifCollection", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda16
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logLocallyDismissAlreadyDismissedNotif$lambda$81(notificationEntry, i, i2, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda17
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logLocallyDismissAlreadyDismissedNotif$lambda$82((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logLocallyDismissAlreadyParentDismissedChild(final NotificationEntry notificationEntry, final NotificationEntry notificationEntry2, final int i, final int i2) {
        notificationEntry.getClass();
        notificationEntry2.getClass();
        LogBuffer.log$default(this.buffer, "NotifCollection", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda14
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logLocallyDismissAlreadyParentDismissedChild$lambda$87(notificationEntry, notificationEntry2, i, i2, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda15
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logLocallyDismissAlreadyParentDismissedChild$lambda$88((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logLocallyDismissAlreadyParentDismissedNotif(final NotificationEntry notificationEntry, final int i, final int i2) {
        notificationEntry.getClass();
        LogBuffer.log$default(this.buffer, "NotifCollection", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda46
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logLocallyDismissAlreadyParentDismissedNotif$lambda$83(notificationEntry, i, i2, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda47
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logLocallyDismissAlreadyParentDismissedNotif$lambda$84((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logLocallyDismissMismatchedEntry(final NotificationEntry notificationEntry, final int i, final int i2, final NotificationEntry notificationEntry2) {
        notificationEntry.getClass();
        notificationEntry2.getClass();
        LogBuffer.log$default(this.buffer, "NotifCollection", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda30
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logLocallyDismissMismatchedEntry$lambda$79(notificationEntry, i, i2, notificationEntry2, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda31
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logLocallyDismissMismatchedEntry$lambda$80((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logLocallyDismissNonExistentNotif(final NotificationEntry notificationEntry, final int i, final int i2) {
        notificationEntry.getClass();
        LogBuffer.log$default(this.buffer, "NotifCollection", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda24
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logLocallyDismissNonExistentNotif$lambda$77(notificationEntry, i, i2, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda25
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logLocallyDismissNonExistentNotif$lambda$78((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logLocallyDismissed(final NotificationEntry notificationEntry, final int i, final int i2) {
        notificationEntry.getClass();
        LogBuffer.log$default(this.buffer, "NotifCollection", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda44
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logLocallyDismissed$lambda$10(notificationEntry, i, i2, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda45
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logLocallyDismissed$lambda$11((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logLocallyDismissedAlreadyCanceledEntry(final NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        LogBuffer.log$default(this.buffer, "NotifCollection", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda26
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logLocallyDismissedAlreadyCanceledEntry$lambda$18(notificationEntry, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda27
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logLocallyDismissedAlreadyCanceledEntry$lambda$19((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logLocallyDismissedChild(final NotificationEntry notificationEntry, final NotificationEntry notificationEntry2, final int i, final int i2) {
        notificationEntry.getClass();
        notificationEntry2.getClass();
        LogBuffer.log$default(this.buffer, "NotifCollection", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda38
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logLocallyDismissedChild$lambda$14(notificationEntry, notificationEntry2, i, i2, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda39
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logLocallyDismissedChild$lambda$15((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logMissingNotifications(final List list, final int i) {
        list.getClass();
        LogBuffer.log$default(this.buffer, "NotifCollection", LogLevel.WARNING, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda18
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logMissingNotifications$lambda$40(i, list, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda19
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logMissingNotifications$lambda$41((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logMissingRankings(final List list, final int i, final NotificationListenerService.RankingMap rankingMap) {
        list.getClass();
        rankingMap.getClass();
        LogBuffer.log$default(this.buffer, "NotifCollection", LogLevel.WARNING, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda32
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logMissingRankings$lambda$31(i, list, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda33
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logMissingRankings$lambda$32((LogMessage) obj);
            }
        }, null, 16, null);
        LogBuffer.log$default(this.buffer, "NotifCollection", LogLevel.DEBUG, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda34
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logMissingRankings$lambda$34(rankingMap, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda35
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logMissingRankings$lambda$35((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logNoNotificationToRemoveWithKey(final StatusBarNotification statusBarNotification, final int i) {
        statusBarNotification.getClass();
        LogBuffer.log$default(this.buffer, "NotifCollection", LogLevel.ERROR, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda61
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logNoNotificationToRemoveWithKey$lambda$28(statusBarNotification, i, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda62
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logNoNotificationToRemoveWithKey$lambda$29((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logNotifClearAllDismissalIntercepted(final NotificationEntry notificationEntry, final int i, final int i2) {
        notificationEntry.getClass();
        LogBuffer.log$default(this.buffer, "NotifCollection", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda20
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logNotifClearAllDismissalIntercepted$lambda$22(notificationEntry, i, i2, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda21
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logNotifClearAllDismissalIntercepted$lambda$23((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logNotifDismissedIntercepted(final NotificationEntry notificationEntry, final int i, final int i2) {
        notificationEntry.getClass();
        LogBuffer.log$default(this.buffer, "NotifCollection", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda57
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logNotifDismissedIntercepted$lambda$20(notificationEntry, i, i2, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda58
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logNotifDismissedIntercepted$lambda$21((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logNotifGroupPosted(final String str, final int i) {
        str.getClass();
        LogBuffer.log$default(this.buffer, "NotifCollection", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda80
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logNotifGroupPosted$lambda$2(str, i, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda81
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logNotifGroupPosted$lambda$3((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logNotifInternalUpdate(final NotificationEntry notificationEntry, final String str, final String str2) {
        notificationEntry.getClass();
        str.getClass();
        str2.getClass();
        LogBuffer.log$default(this.buffer, "NotifCollection", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda10
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logNotifInternalUpdate$lambda$24(notificationEntry, str, str2, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda11
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logNotifInternalUpdate$lambda$25((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logNotifInternalUpdateFailed(final StatusBarNotification statusBarNotification, final String str, final String str2) {
        statusBarNotification.getClass();
        str.getClass();
        str2.getClass();
        LogBuffer.log$default(this.buffer, "NotifCollection", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda12
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logNotifInternalUpdateFailed$lambda$26(statusBarNotification, str, str2, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda13
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logNotifInternalUpdateFailed$lambda$27((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logNotifPosted(final NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        LogBuffer.log$default(this.buffer, "NotifCollection", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda53
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logNotifPosted$lambda$0(notificationEntry, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda54
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logNotifPosted$lambda$1((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logNotifReleased(final NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        LogBuffer.log$default(this.buffer, "NotifCollection", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda28
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logNotifReleased$lambda$8(notificationEntry, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda29
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logNotifReleased$lambda$9((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logNotifRemoved(final StatusBarNotification statusBarNotification, final int i) {
        statusBarNotification.getClass();
        LogBuffer.log$default(this.buffer, "NotifCollection", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda69
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logNotifRemoved$lambda$6(statusBarNotification, i, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda70
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logNotifRemoved$lambda$7((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logNotifUpdated(final NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        LogBuffer.log$default(this.buffer, "NotifCollection", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda63
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logNotifUpdated$lambda$4(notificationEntry, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda64
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logNotifUpdated$lambda$5((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logRecoveredRankings(final List list, final int i) {
        list.getClass();
        LogBuffer.log$default(this.buffer, "NotifCollection", LogLevel.INFO, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda36
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logRecoveredRankings$lambda$37(i, list, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda37
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logRecoveredRankings$lambda$38((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logRemoteExceptionOnClearAllNotifications(final RemoteException remoteException) {
        remoteException.getClass();
        LogBuffer.log$default(this.buffer, "NotifCollection", LogLevel.WTF, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda50
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logRemoteExceptionOnClearAllNotifications$lambda$47(remoteException, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda51
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logRemoteExceptionOnClearAllNotifications$lambda$48((LogMessage) obj);
            }
        }, null, 16, null);
    }

    public final void logRemoteExceptionOnNotificationClear(final NotificationEntry notificationEntry, final int i, final int i2, final RemoteException remoteException) {
        notificationEntry.getClass();
        remoteException.getClass();
        LogBuffer.log$default(this.buffer, "NotifCollection", LogLevel.WTF, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda67
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logRemoteExceptionOnNotificationClear$lambda$45(notificationEntry, i, i2, remoteException, (LogMessage) obj);
            }
        }, new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger$$ExternalSyntheticLambda68
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return NotifCollectionLogger.logRemoteExceptionOnNotificationClear$lambda$46((LogMessage) obj);
            }
        }, null, 16, null);
    }
}
