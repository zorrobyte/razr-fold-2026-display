package com.android.systemui.statusbar.notification.collection.coordinator;

import android.content.Context;
import com.android.systemui.res.R$bool;
import com.android.systemui.statusbar.notification.AssistantFeedbackController;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.listbuilder.NotifSection;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnAfterRenderEntryListener;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeRenderListListener;
import com.android.systemui.statusbar.notification.collection.provider.SectionStyleProvider;
import com.android.systemui.statusbar.notification.collection.render.NotifRowController;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: RowAppearanceCoordinator.kt */
/* JADX INFO: loaded from: classes.dex */
public final class RowAppearanceCoordinator implements Coordinator {
    private NotificationEntry entryToExpand;
    private final boolean mAlwaysExpandNonGroupedNotification;
    private AssistantFeedbackController mAssistantFeedbackController;
    private final boolean mAutoExpandFirstNotification;
    private SectionStyleProvider mSectionStyleProvider;

    public RowAppearanceCoordinator(Context context, AssistantFeedbackController assistantFeedbackController, SectionStyleProvider sectionStyleProvider) {
        context.getClass();
        assistantFeedbackController.getClass();
        sectionStyleProvider.getClass();
        this.mAssistantFeedbackController = assistantFeedbackController;
        this.mSectionStyleProvider = sectionStyleProvider;
        this.mAlwaysExpandNonGroupedNotification = context.getResources().getBoolean(R$bool.config_alwaysExpandNonGroupedNotifications);
        this.mAutoExpandFirstNotification = context.getResources().getBoolean(R$bool.config_autoExpandFirstNotification);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onAfterRenderEntry(NotificationEntry notificationEntry, NotifRowController notifRowController) {
        notifRowController.setSystemExpanded(this.mAlwaysExpandNonGroupedNotification || (this.mAutoExpandFirstNotification && Intrinsics.areEqual(notificationEntry, this.entryToExpand)));
        notifRowController.setFeedbackIcon(this.mAssistantFeedbackController.getFeedbackIcon(notificationEntry));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void onBeforeRenderList(List list) {
        NotificationEntry representativeEntry;
        ListEntry listEntry = (ListEntry) CollectionsKt.firstOrNull(list);
        NotificationEntry notificationEntry = null;
        if (listEntry != null && (representativeEntry = listEntry.getRepresentativeEntry()) != null) {
            SectionStyleProvider sectionStyleProvider = this.mSectionStyleProvider;
            NotifSection section = representativeEntry.getSection();
            section.getClass();
            if (!sectionStyleProvider.isMinimizedSection(section)) {
                notificationEntry = representativeEntry;
            }
        }
        this.entryToExpand = notificationEntry;
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        notifPipeline.getClass();
        notifPipeline.addOnBeforeRenderListListener(new OnBeforeRenderListListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.RowAppearanceCoordinator.attach.1
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeRenderListListener
            public final void onBeforeRenderList(List list) {
                list.getClass();
                RowAppearanceCoordinator.this.onBeforeRenderList(list);
            }
        });
        notifPipeline.addOnAfterRenderEntryListener(new OnAfterRenderEntryListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.RowAppearanceCoordinator.attach.2
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnAfterRenderEntryListener
            public final void onAfterRenderEntry(NotificationEntry notificationEntry, NotifRowController notifRowController) {
                notificationEntry.getClass();
                notifRowController.getClass();
                RowAppearanceCoordinator.this.onAfterRenderEntry(notificationEntry, notifRowController);
            }
        });
    }
}
