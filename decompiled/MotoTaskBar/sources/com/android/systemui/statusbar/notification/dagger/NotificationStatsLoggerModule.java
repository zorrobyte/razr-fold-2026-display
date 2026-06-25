package com.android.systemui.statusbar.notification.dagger;

import com.android.systemui.Flags;
import com.android.systemui.statusbar.NotificationListener;
import com.android.systemui.statusbar.notification.collection.NotifLiveDataStore;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider;
import com.android.systemui.statusbar.notification.logging.NotificationLogger;
import com.android.systemui.statusbar.notification.logging.NotificationPanelLogger;
import com.android.systemui.statusbar.notification.stack.ui.view.NotificationRowStatsLogger;
import com.android.systemui.util.kotlin.JavaAdapter;
import java.util.Optional;
import java.util.concurrent.Executor;
import javax.inject.Provider;

/* JADX INFO: compiled from: NotificationStatsLoggerModule.kt */
/* JADX INFO: loaded from: classes.dex */
public interface NotificationStatsLoggerModule {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* JADX INFO: compiled from: NotificationStatsLoggerModule.kt */
    public final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }

        public final Optional provideLegacyLoggerOptional(NotificationListener notificationListener, Executor executor, NotifLiveDataStore notifLiveDataStore, NotificationVisibilityProvider notificationVisibilityProvider, NotifPipeline notifPipeline, JavaAdapter javaAdapter, NotificationLogger.ExpansionStateLogger expansionStateLogger, NotificationPanelLogger notificationPanelLogger) {
            if (Flags.notificationsLiveDataStoreRefactor()) {
                Optional optionalEmpty = Optional.empty();
                optionalEmpty.getClass();
                return optionalEmpty;
            }
            Optional optionalOf = Optional.of(new NotificationLogger(executor, notifLiveDataStore, notificationVisibilityProvider, notifPipeline, javaAdapter, expansionStateLogger, notificationPanelLogger));
            optionalOf.getClass();
            return optionalOf;
        }

        public final NotificationRowStatsLogger provideRowStatsLogger(Provider provider, Optional optional) {
            provider.getClass();
            optional.getClass();
            NotificationLogger notificationLogger = (NotificationLogger) optional.orElse(null);
            if (notificationLogger != null) {
                return notificationLogger;
            }
            Object obj = provider.get();
            obj.getClass();
            return (NotificationRowStatsLogger) obj;
        }

        public final Optional provideStatsLogger(Provider provider) {
            provider.getClass();
            if (Flags.notificationsLiveDataStoreRefactor()) {
                Optional optionalOf = Optional.of(provider.get());
                optionalOf.getClass();
                return optionalOf;
            }
            Optional optionalEmpty = Optional.empty();
            optionalEmpty.getClass();
            return optionalEmpty;
        }

        public final Optional provideViewModel(Provider provider) {
            provider.getClass();
            if (Flags.notificationsLiveDataStoreRefactor()) {
                Optional optionalOf = Optional.of(provider.get());
                optionalOf.getClass();
                return optionalOf;
            }
            Optional optionalEmpty = Optional.empty();
            optionalEmpty.getClass();
            return optionalEmpty;
        }
    }
}
