package com.android.systemui.statusbar.notification.stack;

import androidx.core.view.ViewGroupKt;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.statusbar.notification.Roundable;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import java.util.ArrayList;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;

/* JADX INFO: compiled from: NotificationTargetsHelper.kt */
/* JADX INFO: loaded from: classes.dex */
public final class NotificationTargetsHelper {
    public NotificationTargetsHelper(FeatureFlags featureFlags) {
        featureFlags.getClass();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean findRoundableTargets$lambda$0(ExpandableView expandableView) {
        expandableView.getClass();
        return expandableView.getVisibility() == 0;
    }

    public final RoundableTargets findRoundableTargets(ExpandableNotificationRow expandableNotificationRow, NotificationStackScrollLayout notificationStackScrollLayout, NotificationSectionsManager notificationSectionsManager) {
        Roundable notificationHeaderWrapper;
        ExpandableView expandableView;
        expandableNotificationRow.getClass();
        notificationStackScrollLayout.getClass();
        notificationSectionsManager.getClass();
        ExpandableNotificationRow notificationParent = expandableNotificationRow.getNotificationParent();
        ExpandableView expandableView2 = null;
        NotificationChildrenContainer childrenContainer = notificationParent != null ? notificationParent.getChildrenContainer() : null;
        Sequence sequenceFilter = SequencesKt.filter(ViewGroupKt.getChildren(notificationStackScrollLayout), new Function1() { // from class: com.android.systemui.statusbar.notification.stack.NotificationTargetsHelper$findRoundableTargets$$inlined$filterIsInstance$1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(Object obj) {
                return Boolean.valueOf(obj instanceof ExpandableView);
            }
        });
        sequenceFilter.getClass();
        List list = SequencesKt.toList(SequencesKt.filter(sequenceFilter, new Function1() { // from class: com.android.systemui.statusbar.notification.stack.NotificationTargetsHelper$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Boolean.valueOf(NotificationTargetsHelper.findRoundableTargets$lambda$0((ExpandableView) obj));
            }
        }));
        if (notificationParent == null || childrenContainer == null) {
            int iIndexOf = list.indexOf(expandableNotificationRow);
            ExpandableView expandableView3 = (ExpandableView) CollectionsKt.getOrNull(list, iIndexOf - 1);
            notificationHeaderWrapper = (expandableView3 == null || notificationSectionsManager.beginsSection(expandableNotificationRow, expandableView3)) ? null : expandableView3;
            ExpandableView expandableView4 = (ExpandableView) CollectionsKt.getOrNull(list, iIndexOf + 1);
            if (expandableView4 != null && !notificationSectionsManager.beginsSection(expandableView4, expandableNotificationRow)) {
                expandableView2 = expandableView4;
            }
            expandableView = expandableView2;
        } else {
            List attachedChildren = childrenContainer.getAttachedChildren();
            attachedChildren.getClass();
            ArrayList arrayList = new ArrayList();
            for (Object obj : attachedChildren) {
                ExpandableNotificationRow expandableNotificationRow2 = (ExpandableNotificationRow) obj;
                expandableNotificationRow2.getClass();
                if (expandableNotificationRow2.getVisibility() == 0) {
                    arrayList.add(obj);
                }
            }
            int iIndexOf2 = arrayList.indexOf(expandableNotificationRow);
            notificationHeaderWrapper = (ExpandableNotificationRow) CollectionsKt.getOrNull(arrayList, iIndexOf2 - 1);
            if (notificationHeaderWrapper == null) {
                notificationHeaderWrapper = childrenContainer.getNotificationHeaderWrapper();
            }
            expandableView = (ExpandableNotificationRow) CollectionsKt.getOrNull(arrayList, iIndexOf2 + 1);
            if (expandableView == null) {
                expandableView = (ExpandableView) CollectionsKt.getOrNull(list, list.indexOf(notificationParent) + 1);
            }
        }
        return new RoundableTargets(notificationHeaderWrapper, expandableNotificationRow, expandableView);
    }
}
