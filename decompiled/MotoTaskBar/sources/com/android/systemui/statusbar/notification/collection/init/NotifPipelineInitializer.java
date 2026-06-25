package com.android.systemui.statusbar.notification.collection.init;

import android.util.Log;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.NotificationListener;
import com.android.systemui.statusbar.notification.collection.NotifCollection;
import com.android.systemui.statusbar.notification.collection.NotifInflaterImpl;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.ShadeListBuilder;
import com.android.systemui.statusbar.notification.collection.coalescer.GroupCoalescer;
import com.android.systemui.statusbar.notification.collection.coordinator.NotifCoordinators;
import com.android.systemui.statusbar.notification.collection.inflation.NotificationRowBinderImpl;
import com.android.systemui.statusbar.notification.collection.render.NotifStackController;
import com.android.systemui.statusbar.notification.collection.render.RenderStageManager;
import com.android.systemui.statusbar.notification.collection.render.ShadeViewManager;
import com.android.systemui.statusbar.notification.collection.render.ShadeViewManagerFactory;
import com.android.systemui.statusbar.notification.stack.NotificationListContainer;

/* JADX INFO: loaded from: classes.dex */
public class NotifPipelineInitializer implements Dumpable {
    private final DumpManager mDumpManager;
    private final GroupCoalescer mGroupCoalescer;
    private final ShadeListBuilder mListBuilder;
    private final NotifCollection mNotifCollection;
    private final NotifInflaterImpl mNotifInflater;
    private final NotifCoordinators mNotifPluggableCoordinators;
    private NotificationListener mNotificationService;
    private final NotifPipeline mPipelineWrapper;
    private final RenderStageManager mRenderStageManager;
    private ShadeViewManager mShadeViewManager;
    private final ShadeViewManagerFactory mShadeViewManagerFactory;

    public NotifPipelineInitializer(NotifPipeline notifPipeline, GroupCoalescer groupCoalescer, NotifCollection notifCollection, ShadeListBuilder shadeListBuilder, RenderStageManager renderStageManager, NotifCoordinators notifCoordinators, NotifInflaterImpl notifInflaterImpl, DumpManager dumpManager, ShadeViewManagerFactory shadeViewManagerFactory) {
        this.mPipelineWrapper = notifPipeline;
        this.mGroupCoalescer = groupCoalescer;
        this.mNotifCollection = notifCollection;
        this.mListBuilder = shadeListBuilder;
        this.mRenderStageManager = renderStageManager;
        this.mNotifPluggableCoordinators = notifCoordinators;
        this.mDumpManager = dumpManager;
        this.mNotifInflater = notifInflaterImpl;
        this.mShadeViewManagerFactory = shadeViewManagerFactory;
    }

    public void initialize(NotificationListener notificationListener, NotificationRowBinderImpl notificationRowBinderImpl, NotificationListContainer notificationListContainer, NotifStackController notifStackController) {
        this.mDumpManager.registerDumpable("NotifPipeline", this);
        this.mNotificationService = notificationListener;
        this.mNotifInflater.setRowBinder(notificationRowBinderImpl);
        this.mNotifPluggableCoordinators.attach(this.mPipelineWrapper);
        ShadeViewManager shadeViewManagerCreate = this.mShadeViewManagerFactory.create(notificationListContainer, notifStackController);
        this.mShadeViewManager = shadeViewManagerCreate;
        shadeViewManagerCreate.attach(this.mRenderStageManager);
        this.mRenderStageManager.attach(this.mListBuilder);
        this.mListBuilder.attach(this.mNotifCollection);
        this.mNotifCollection.attach(this.mGroupCoalescer);
        this.mGroupCoalescer.attach(this.mNotificationService);
        Log.d("NotifPipeline", "Notif pipeline initialized. rendering=true");
    }
}
