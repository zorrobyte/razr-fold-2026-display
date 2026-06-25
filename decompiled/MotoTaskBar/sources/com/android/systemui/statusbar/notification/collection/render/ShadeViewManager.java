package com.android.systemui.statusbar.notification.collection.render;

import android.content.Context;
import android.os.Trace;
import android.view.View;
import com.android.app.tracing.TraceUtilsKt;
import com.android.systemui.statusbar.notification.NotificationSectionsFeatureManager;
import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.provider.SectionHeaderVisibilityProvider;
import com.android.systemui.statusbar.notification.stack.NotificationListContainer;
import java.util.List;
import kotlin.Unit;

/* JADX INFO: compiled from: ShadeViewManager.kt */
/* JADX INFO: loaded from: classes.dex */
public final class ShadeViewManager {
    private final RootNodeController rootController;
    private final NodeSpecBuilder specBuilder;
    private final NotifStackController stackController;
    private final NotifViewBarn viewBarn;
    private final ShadeViewDiffer viewDiffer;
    private final ShadeViewManager$viewRenderer$1 viewRenderer;

    /* JADX WARN: Type inference failed for: r7v2, types: [com.android.systemui.statusbar.notification.collection.render.ShadeViewManager$viewRenderer$1] */
    public ShadeViewManager(Context context, NotificationListContainer notificationListContainer, NotifStackController notifStackController, MediaContainerController mediaContainerController, NotificationSectionsFeatureManager notificationSectionsFeatureManager, SectionHeaderVisibilityProvider sectionHeaderVisibilityProvider, NodeSpecBuilderLogger nodeSpecBuilderLogger, ShadeViewDifferLogger shadeViewDifferLogger, NotifViewBarn notifViewBarn) {
        context.getClass();
        notificationListContainer.getClass();
        notifStackController.getClass();
        mediaContainerController.getClass();
        notificationSectionsFeatureManager.getClass();
        sectionHeaderVisibilityProvider.getClass();
        nodeSpecBuilderLogger.getClass();
        shadeViewDifferLogger.getClass();
        notifViewBarn.getClass();
        this.stackController = notifStackController;
        this.viewBarn = notifViewBarn;
        RootNodeController rootNodeController = new RootNodeController(notificationListContainer, new View(context));
        this.rootController = rootNodeController;
        this.specBuilder = new NodeSpecBuilder(mediaContainerController, notificationSectionsFeatureManager, sectionHeaderVisibilityProvider, notifViewBarn, nodeSpecBuilderLogger);
        this.viewDiffer = new ShadeViewDiffer(rootNodeController, shadeViewDifferLogger);
        this.viewRenderer = new NotifViewRenderer() { // from class: com.android.systemui.statusbar.notification.collection.render.ShadeViewManager$viewRenderer$1
            @Override // com.android.systemui.statusbar.notification.collection.render.NotifViewRenderer
            public NotifGroupController getGroupController(GroupEntry groupEntry) {
                groupEntry.getClass();
                NotifViewBarn notifViewBarn2 = this.this$0.viewBarn;
                NotificationEntry summary = groupEntry.getSummary();
                if (summary != null) {
                    return notifViewBarn2.requireGroupController(summary);
                }
                throw new IllegalStateException(("No Summary: " + groupEntry).toString());
            }

            @Override // com.android.systemui.statusbar.notification.collection.render.NotifViewRenderer
            public NotifRowController getRowController(NotificationEntry notificationEntry) {
                notificationEntry.getClass();
                return this.this$0.viewBarn.requireRowController(notificationEntry);
            }

            @Override // com.android.systemui.statusbar.notification.collection.render.NotifViewRenderer
            public NotifStackController getStackController() {
                return this.this$0.stackController;
            }

            @Override // com.android.systemui.statusbar.notification.collection.render.NotifViewRenderer
            public void onRenderList(List list) {
                list.getClass();
                ShadeViewManager shadeViewManager = this.this$0;
                boolean zIsEnabled = Trace.isEnabled();
                if (zIsEnabled) {
                    TraceUtilsKt.beginSlice("ShadeViewManager.onRenderList");
                }
                try {
                    shadeViewManager.viewDiffer.applySpec(shadeViewManager.specBuilder.buildNodeSpec(shadeViewManager.rootController, list));
                    Unit unit = Unit.INSTANCE;
                } finally {
                    if (zIsEnabled) {
                        TraceUtilsKt.endSlice();
                    }
                }
            }
        };
    }

    public final void attach(RenderStageManager renderStageManager) {
        renderStageManager.getClass();
        renderStageManager.setViewRenderer(this.viewRenderer);
    }
}
