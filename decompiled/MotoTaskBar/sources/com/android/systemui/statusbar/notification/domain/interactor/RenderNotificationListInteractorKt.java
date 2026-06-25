package com.android.systemui.statusbar.notification.domain.interactor;

import android.graphics.drawable.Icon;
import com.android.systemui.statusbar.notification.collection.provider.SectionStyleProvider;
import com.android.systemui.statusbar.notification.data.repository.ActiveNotificationsStore;
import com.android.systemui.statusbar.notification.shared.ActiveNotificationGroupModel;
import com.android.systemui.statusbar.notification.shared.ActiveNotificationModel;
import java.util.List;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: RenderNotificationListInteractor.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class RenderNotificationListInteractorKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final ActiveNotificationsStore buildActiveNotificationsStore(ActiveNotificationsStore activeNotificationsStore, SectionStyleProvider sectionStyleProvider, Function1 function1) {
        ActiveNotificationsStoreBuilder activeNotificationsStoreBuilder = new ActiveNotificationsStoreBuilder(activeNotificationsStore, sectionStyleProvider);
        function1.invoke(activeNotificationsStoreBuilder);
        return activeNotificationsStoreBuilder.build();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ActiveNotificationGroupModel createOrReuse(ActiveNotificationsStore activeNotificationsStore, String str, ActiveNotificationModel activeNotificationModel, List list) {
        ActiveNotificationGroupModel activeNotificationGroupModel = (ActiveNotificationGroupModel) activeNotificationsStore.getGroups().get(str);
        if (activeNotificationGroupModel != null) {
            if (!isCurrent(activeNotificationGroupModel, str, activeNotificationModel, list)) {
                activeNotificationGroupModel = null;
            }
            if (activeNotificationGroupModel != null) {
                return activeNotificationGroupModel;
            }
        }
        return new ActiveNotificationGroupModel(str, activeNotificationModel, list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ActiveNotificationModel createOrReuse(ActiveNotificationsStore activeNotificationsStore, String str, String str2, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, Icon icon, Icon icon2, Icon icon3, int i, String str3, Integer num, boolean z7, int i2) {
        ActiveNotificationModel activeNotificationModel = (ActiveNotificationModel) activeNotificationsStore.getIndividuals().get(str);
        if (activeNotificationModel != null) {
            if (!isCurrent(activeNotificationModel, str, str2, z, z2, z3, z4, z5, z6, icon, icon2, icon3, i, str3, num, z7, i2)) {
                activeNotificationModel = null;
            }
            if (activeNotificationModel != null) {
                return activeNotificationModel;
            }
        }
        return new ActiveNotificationModel(str, str2, z, z2, z3, z4, z5, z6, icon, icon2, icon3, i, str3, num, z7, i2);
    }

    private static final boolean isCurrent(ActiveNotificationGroupModel activeNotificationGroupModel, String str, ActiveNotificationModel activeNotificationModel, List list) {
        return Intrinsics.areEqual(str, activeNotificationGroupModel.getKey()) && Intrinsics.areEqual(activeNotificationModel, activeNotificationGroupModel.getSummary()) && Intrinsics.areEqual(list, activeNotificationGroupModel.getChildren());
    }

    private static final boolean isCurrent(ActiveNotificationModel activeNotificationModel, String str, String str2, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, Icon icon, Icon icon2, Icon icon3, int i, String str3, Integer num, boolean z7, int i2) {
        return Intrinsics.areEqual(str, activeNotificationModel.getKey()) && Intrinsics.areEqual(str2, activeNotificationModel.getGroupKey()) && z == activeNotificationModel.isAmbient() && z2 == activeNotificationModel.isRowDismissed() && z3 == activeNotificationModel.isSilent() && z4 == activeNotificationModel.isLastMessageFromReply() && z5 == activeNotificationModel.isSuppressedFromStatusBar() && z6 == activeNotificationModel.isPulsing() && Intrinsics.areEqual(icon, activeNotificationModel.getAodIcon()) && Intrinsics.areEqual(icon2, activeNotificationModel.getShelfIcon()) && Intrinsics.areEqual(icon3, activeNotificationModel.getStatusBarIcon()) && i == activeNotificationModel.getUid() && Intrinsics.areEqual(num, activeNotificationModel.getInstanceId()) && z7 == activeNotificationModel.isGroupSummary() && Intrinsics.areEqual(str3, activeNotificationModel.getPackageName()) && i2 == activeNotificationModel.getBucket();
    }
}
