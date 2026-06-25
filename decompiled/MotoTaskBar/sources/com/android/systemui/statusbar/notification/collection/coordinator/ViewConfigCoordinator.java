package com.android.systemui.statusbar.notification.collection.coordinator;

import android.os.Trace;
import android.util.Log;
import com.android.app.tracing.TraceUtilsKt;
import com.android.internal.widget.MessagingGroup;
import com.android.internal.widget.MessagingMessage;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.notification.ColorUpdateLogger;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.Compile;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: ViewConfigCoordinator.kt */
/* JADX INFO: loaded from: classes.dex */
public final class ViewConfigCoordinator implements Coordinator, ConfigurationController.ConfigurationListener {
    public static final Companion Companion = new Companion(null);
    private static final boolean DEBUG;
    private final ColorUpdateLogger colorUpdateLogger;
    private final ConfigurationController mConfigurationController;
    private boolean mDispatchUiModeChangeOnUserSwitched;
    private boolean mIsSwitchingUser;
    private final NotificationLockscreenUserManager mLockscreenUserManager;
    private NotifPipeline mPipeline;
    private boolean mReinflateNotificationsOnUserSwitched;
    private final ViewConfigCoordinator$mUserChangedListener$1 mUserChangedListener;

    /* JADX INFO: compiled from: ViewConfigCoordinator.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        DEBUG = Compile.IS_DEBUG && Log.isLoggable("ViewConfigCoordinator", 3);
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.statusbar.notification.collection.coordinator.ViewConfigCoordinator$mUserChangedListener$1] */
    public ViewConfigCoordinator(ConfigurationController configurationController, NotificationLockscreenUserManager notificationLockscreenUserManager, ColorUpdateLogger colorUpdateLogger) {
        configurationController.getClass();
        notificationLockscreenUserManager.getClass();
        colorUpdateLogger.getClass();
        this.mConfigurationController = configurationController;
        this.mLockscreenUserManager = notificationLockscreenUserManager;
        this.colorUpdateLogger = colorUpdateLogger;
        this.mUserChangedListener = new NotificationLockscreenUserManager.UserChangedListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.ViewConfigCoordinator$mUserChangedListener$1
            @Override // com.android.systemui.statusbar.NotificationLockscreenUserManager.UserChangedListener
            public void onUserChanged(int i) {
                ColorUpdateLogger.logTriggerEvent$default(this.this$0.colorUpdateLogger, "VCC.mUserChangedListener.onUserChanged()", null, 2, null);
                if (ViewConfigCoordinator.DEBUG) {
                    Log.d("ViewConfigCoordinator", "ViewConfigCoordinator.onUserChanged(userId=" + i + ")");
                }
                this.this$0.applyChangesOnUserSwitched();
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void applyChangesOnUserSwitched() {
        ColorUpdateLogger.logEvent$default(this.colorUpdateLogger, "VCC.applyChangesOnUserSwitched()", null, 2, null);
        if (this.mReinflateNotificationsOnUserSwitched) {
            updateNotificationsOnDensityOrFontScaleChanged();
            this.mReinflateNotificationsOnUserSwitched = false;
        }
        if (this.mDispatchUiModeChangeOnUserSwitched) {
            updateNotificationsOnUiModeChanged();
            this.mDispatchUiModeChangeOnUserSwitched = false;
        }
    }

    private final void updateNotificationsOnDensityOrFontScaleChanged() {
        Collection allNotifs;
        ColorUpdateLogger.logEvent$default(this.colorUpdateLogger, "VCC.updateNotificationsOnDensityOrFontScaleChanged()", null, 2, null);
        NotifPipeline notifPipeline = this.mPipeline;
        if (notifPipeline == null || (allNotifs = notifPipeline.getAllNotifs()) == null) {
            return;
        }
        Iterator it = allNotifs.iterator();
        while (it.hasNext()) {
            ((NotificationEntry) it.next()).onDensityOrFontScaleChanged();
        }
    }

    private final void updateNotificationsOnUiModeChanged() {
        Collection allNotifs;
        this.colorUpdateLogger.logEvent("VCC.updateNotificationsOnUiModeChanged()", "mode=" + this.mConfigurationController.getNightModeName());
        if (DEBUG) {
            Log.d("ViewConfigCoordinator", "ViewConfigCoordinator.updateNotificationsOnUiModeChanged()");
        }
        boolean zIsEnabled = Trace.isEnabled();
        if (zIsEnabled) {
            TraceUtilsKt.beginSlice("updateNotifOnUiModeChanged");
        }
        try {
            NotifPipeline notifPipeline = this.mPipeline;
            if (notifPipeline != null && (allNotifs = notifPipeline.getAllNotifs()) != null) {
                Iterator it = allNotifs.iterator();
                while (it.hasNext()) {
                    ExpandableNotificationRow row = ((NotificationEntry) it.next()).getRow();
                    if (row != null) {
                        row.onUiModeChanged();
                    }
                }
                Unit unit = Unit.INSTANCE;
            }
            if (zIsEnabled) {
                TraceUtilsKt.endSlice();
            }
        } catch (Throwable th) {
            if (zIsEnabled) {
                TraceUtilsKt.endSlice();
            }
            throw th;
        }
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        notifPipeline.getClass();
        this.mPipeline = notifPipeline;
        this.mLockscreenUserManager.addUserChangedListener(this.mUserChangedListener);
        this.mConfigurationController.addCallback(this);
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public void onDensityOrFontScaleChanged() {
        ColorUpdateLogger.logTriggerEvent$default(this.colorUpdateLogger, "VCC.onDensityOrFontScaleChanged()", null, 2, null);
        MessagingMessage.dropCache();
        MessagingGroup.dropCache();
        if (this.mIsSwitchingUser) {
            this.mReinflateNotificationsOnUserSwitched = true;
        } else {
            updateNotificationsOnDensityOrFontScaleChanged();
        }
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public void onThemeChanged() {
        ColorUpdateLogger.logTriggerEvent$default(this.colorUpdateLogger, "VCC.onThemeChanged()", null, 2, null);
        onDensityOrFontScaleChanged();
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public void onUiModeChanged() {
        ColorUpdateLogger.logTriggerEvent$default(this.colorUpdateLogger, "VCC.onUiModeChanged()", null, 2, null);
        if (this.mIsSwitchingUser) {
            this.mDispatchUiModeChangeOnUserSwitched = true;
        } else {
            updateNotificationsOnUiModeChanged();
        }
    }
}
