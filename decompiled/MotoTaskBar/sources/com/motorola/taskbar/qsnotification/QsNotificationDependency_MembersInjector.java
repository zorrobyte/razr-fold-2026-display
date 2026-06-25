package com.motorola.taskbar.qsnotification;

import dagger.Lazy;

/* JADX INFO: loaded from: classes2.dex */
public abstract class QsNotificationDependency_MembersInjector {
    public static void injectMActivityStarter(QsNotificationDependency qsNotificationDependency, Lazy lazy) {
        qsNotificationDependency.mActivityStarter = lazy;
    }

    public static void injectMAmbientState(QsNotificationDependency qsNotificationDependency, Lazy lazy) {
        qsNotificationDependency.mAmbientState = lazy;
    }

    public static void injectMGroupExpansionManager(QsNotificationDependency qsNotificationDependency, Lazy lazy) {
        qsNotificationDependency.mGroupExpansionManager = lazy;
    }

    public static void injectMGroupMembershipManager(QsNotificationDependency qsNotificationDependency, Lazy lazy) {
        qsNotificationDependency.mGroupMembershipManager = lazy;
    }

    public static void injectMNotificationRemoteInputManager(QsNotificationDependency qsNotificationDependency, Lazy lazy) {
        qsNotificationDependency.mNotificationRemoteInputManager = lazy;
    }

    public static void injectMNotificationSectionsManager(QsNotificationDependency qsNotificationDependency, Lazy lazy) {
        qsNotificationDependency.mNotificationSectionsManager = lazy;
    }

    public static void injectMQsNotificationTooltipPopupManager(QsNotificationDependency qsNotificationDependency, Lazy lazy) {
        qsNotificationDependency.mQsNotificationTooltipPopupManager = lazy;
    }

    public static void injectMSmartReplyController(QsNotificationDependency qsNotificationDependency, Lazy lazy) {
        qsNotificationDependency.mSmartReplyController = lazy;
    }
}
