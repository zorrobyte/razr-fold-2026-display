package com.android.systemui.statusbar.notification.init;

import com.android.systemui.Flags;
import com.android.systemui.statusbar.NotificationListener;
import com.android.systemui.statusbar.NotificationMediaManager;
import com.android.systemui.statusbar.NotificationPresenter;
import com.android.systemui.statusbar.notification.AnimatedImageNotificationManager;
import com.android.systemui.statusbar.notification.NotificationActivityStarter;
import com.android.systemui.statusbar.notification.NotificationClicker;
import com.android.systemui.statusbar.notification.collection.NotifLiveDataStore;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.TargetSdkResolver;
import com.android.systemui.statusbar.notification.collection.inflation.NotificationRowBinderImpl;
import com.android.systemui.statusbar.notification.collection.init.NotifPipelineInitializer;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import com.android.systemui.statusbar.notification.collection.render.NotifStackController;
import com.android.systemui.statusbar.notification.interruption.HeadsUpViewBinder;
import com.android.systemui.statusbar.notification.logging.NotificationLogger;
import com.android.systemui.statusbar.notification.row.NotifBindPipelineInitializer;
import com.android.systemui.statusbar.notification.stack.NotificationListContainer;
import dagger.Lazy;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.Consumer;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: NotificationsControllerImpl.kt */
/* JADX INFO: loaded from: classes.dex */
public final class NotificationsControllerImpl implements NotificationsController {
    private final AnimatedImageNotificationManager animatedImageNotificationManager;
    private final NotificationClicker.Builder clickerBuilder;
    private final Lazy commonNotifCollection;
    private final HeadsUpViewBinder headsUpViewBinder;
    private final NotifBindPipelineInitializer notifBindPipelineInitializer;
    private final NotifLiveDataStore notifLiveDataStore;
    private final Lazy notifPipeline;
    private final Lazy notifPipelineInitializer;
    private final NotificationListener notificationListener;
    private final Optional notificationLoggerOptional;
    private final NotificationRowBinderImpl notificationRowBinder;
    private final NotificationMediaManager notificationsMediaManager;
    private final TargetSdkResolver targetSdkResolver;

    public NotificationsControllerImpl(NotificationListener notificationListener, Lazy lazy, Lazy lazy2, NotifLiveDataStore notifLiveDataStore, TargetSdkResolver targetSdkResolver, Lazy lazy3, NotifBindPipelineInitializer notifBindPipelineInitializer, Optional optional, NotificationRowBinderImpl notificationRowBinderImpl, NotificationMediaManager notificationMediaManager, HeadsUpViewBinder headsUpViewBinder, NotificationClicker.Builder builder, AnimatedImageNotificationManager animatedImageNotificationManager) {
        notificationListener.getClass();
        lazy.getClass();
        lazy2.getClass();
        notifLiveDataStore.getClass();
        targetSdkResolver.getClass();
        lazy3.getClass();
        notifBindPipelineInitializer.getClass();
        optional.getClass();
        notificationRowBinderImpl.getClass();
        notificationMediaManager.getClass();
        headsUpViewBinder.getClass();
        builder.getClass();
        animatedImageNotificationManager.getClass();
        this.notificationListener = notificationListener;
        this.commonNotifCollection = lazy;
        this.notifPipeline = lazy2;
        this.notifLiveDataStore = notifLiveDataStore;
        this.targetSdkResolver = targetSdkResolver;
        this.notifPipelineInitializer = lazy3;
        this.notifBindPipelineInitializer = notifBindPipelineInitializer;
        this.notificationLoggerOptional = optional;
        this.notificationRowBinder = notificationRowBinderImpl;
        this.notificationsMediaManager = notificationMediaManager;
        this.headsUpViewBinder = headsUpViewBinder;
        this.clickerBuilder = builder;
        this.animatedImageNotificationManager = animatedImageNotificationManager;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit initialize$lambda$0(NotificationListContainer notificationListContainer, NotificationLogger notificationLogger) {
        notificationLogger.getClass();
        notificationLogger.setUpWithContainer(notificationListContainer);
        return Unit.INSTANCE;
    }

    @Override // com.android.systemui.statusbar.notification.init.NotificationsController
    public void initialize(NotificationPresenter notificationPresenter, final NotificationListContainer notificationListContainer, NotifStackController notifStackController, NotificationActivityStarter notificationActivityStarter) {
        notificationPresenter.getClass();
        notificationListContainer.getClass();
        notifStackController.getClass();
        notificationActivityStarter.getClass();
        this.notificationListener.registerAsSystemService();
        ((NotifPipeline) this.notifPipeline.get()).addCollectionListener(new NotifCollectionListener() { // from class: com.android.systemui.statusbar.notification.init.NotificationsControllerImpl.initialize.1
            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public void onEntryRemoved(NotificationEntry notificationEntry, int i) {
                notificationEntry.getClass();
                notificationListContainer.cleanUpViewStateForEntry(notificationEntry);
            }
        });
        this.notificationRowBinder.setNotificationClicker(this.clickerBuilder.build(notificationActivityStarter));
        this.notificationRowBinder.setUpWithPresenter(notificationPresenter, notificationListContainer);
        this.headsUpViewBinder.setPresenter(notificationPresenter);
        this.notifBindPipelineInitializer.initialize();
        this.animatedImageNotificationManager.bind();
        ((NotifPipelineInitializer) this.notifPipelineInitializer.get()).initialize(this.notificationListener, this.notificationRowBinder, notificationListContainer, notifStackController);
        TargetSdkResolver targetSdkResolver = this.targetSdkResolver;
        Object obj = this.notifPipeline.get();
        obj.getClass();
        targetSdkResolver.initialize((CommonNotifCollection) obj);
        this.notificationsMediaManager.setUpWithPresenter(notificationPresenter);
        if (Flags.notificationsLiveDataStoreRefactor()) {
            return;
        }
        Optional optional = this.notificationLoggerOptional;
        final Function1 function1 = new Function1() { // from class: com.android.systemui.statusbar.notification.init.NotificationsControllerImpl$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj2) {
                return NotificationsControllerImpl.initialize$lambda$0(notificationListContainer, (NotificationLogger) obj2);
            }
        };
        optional.ifPresent(new Consumer(function1) { // from class: com.android.systemui.statusbar.notification.init.NotificationsControllerImpl$sam$java_util_function_Consumer$0
            private final /* synthetic */ Function1 function;

            {
                function1.getClass();
                this.function = function1;
            }

            @Override // java.util.function.Consumer
            public final /* synthetic */ void accept(Object obj2) {
                this.function.invoke(obj2);
            }
        });
    }

    @Override // com.android.systemui.statusbar.notification.init.NotificationsController
    public void resetUserExpandedStates() {
        Iterator it = ((CommonNotifCollection) this.commonNotifCollection.get()).getAllNotifs().iterator();
        while (it.hasNext()) {
            ((NotificationEntry) it.next()).resetUserExpansion();
        }
    }
}
