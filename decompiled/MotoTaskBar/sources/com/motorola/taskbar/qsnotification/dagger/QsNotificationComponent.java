package com.motorola.taskbar.qsnotification.dagger;

import android.content.Context;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import com.android.internal.statusbar.IStatusBarService;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.media.controls.domain.pipeline.MediaDataManager;
import com.android.systemui.statusbar.NotificationClickNotifier;
import com.android.systemui.statusbar.NotificationMediaManager;
import com.android.systemui.statusbar.SmartReplyController;
import com.android.systemui.statusbar.notification.collection.NotifCollection;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.motorola.taskbar.R$style;
import com.motorola.taskbar.qsnotification.QsConfigurationControllerImpl;
import com.motorola.taskbar.qsnotification.QsNotificationCentralSurfaces;
import com.motorola.taskbar.qsnotification.QsNotificationDependency;
import com.motorola.taskbar.util.Utils;

/* JADX INFO: loaded from: classes2.dex */
public interface QsNotificationComponent {

    public interface Builder {
        QsNotificationComponent build();

        Builder setDisplayId(int i);
    }

    public interface NotificationExtraModule {
        static NotificationMediaManager provideNotificationMediaManager(Context context, NotificationVisibilityProvider notificationVisibilityProvider, NotifPipeline notifPipeline, NotifCollection notifCollection, MediaDataManager mediaDataManager, DumpManager dumpManager) {
            return new NotificationMediaManager(context, notificationVisibilityProvider, notifPipeline, notifCollection, mediaDataManager, dumpManager);
        }

        static SmartReplyController provideSmartReplyController(DumpManager dumpManager, NotificationVisibilityProvider notificationVisibilityProvider, IStatusBarService iStatusBarService, NotificationClickNotifier notificationClickNotifier) {
            return new SmartReplyController(dumpManager, notificationVisibilityProvider, iStatusBarService, notificationClickNotifier);
        }

        static LayoutInflater providerLayoutInflater(Context context) {
            return LayoutInflater.from(context);
        }
    }

    public abstract class QsNotificationBaseModule {
        static Context provideContext(Context context, int i) {
            return new ContextThemeWrapper(Utils.createDisplayContext(context, i).createWindowContext(2041, null), R$style.Theme_SystemUI_Notification);
        }

        static int provideDisplayId(int i) {
            return i;
        }

        static ConfigurationController provideQsConfigurationController(QsConfigurationControllerImpl qsConfigurationControllerImpl) {
            return qsConfigurationControllerImpl;
        }
    }

    QsNotificationDependency.DependencyInjector createDependency();

    QsNotificationCentralSurfaces getQsNotificationCentralSurfaces();
}
