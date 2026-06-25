package com.android.systemui.statusbar.notification.domain.interactor;

import android.graphics.drawable.Icon;
import android.util.ArrayMap;
import com.android.internal.logging.InstanceId;
import com.android.systemui.statusbar.StatusBarIconView;
import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.provider.SectionStyleProvider;
import com.android.systemui.statusbar.notification.data.repository.ActiveNotificationsStore;
import com.android.systemui.statusbar.notification.shared.ActiveNotificationModel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.collections.CollectionsKt;

/* JADX INFO: compiled from: RenderNotificationListInteractor.kt */
/* JADX INFO: loaded from: classes.dex */
final class ActiveNotificationsStoreBuilder {
    private final ActiveNotificationsStore.Builder builder;
    private final ActiveNotificationsStore existingModels;
    private final SectionStyleProvider sectionStyleProvider;

    public ActiveNotificationsStoreBuilder(ActiveNotificationsStore activeNotificationsStore, SectionStyleProvider sectionStyleProvider) {
        activeNotificationsStore.getClass();
        sectionStyleProvider.getClass();
        this.existingModels = activeNotificationsStore;
        this.sectionStyleProvider = sectionStyleProvider;
        this.builder = new ActiveNotificationsStore.Builder();
    }

    private final ActiveNotificationModel toModel(NotificationEntry notificationEntry) {
        ActiveNotificationsStore activeNotificationsStore = this.existingModels;
        String key = notificationEntry.getKey();
        key.getClass();
        String groupKey = notificationEntry.getSbn().getGroupKey();
        boolean zIsMinimized$default = SectionStyleProvider.isMinimized$default(this.sectionStyleProvider, notificationEntry, false, 2, null);
        boolean zIsRowDismissed = notificationEntry.isRowDismissed();
        boolean zIsSilent$default = SectionStyleProvider.isSilent$default(this.sectionStyleProvider, notificationEntry, false, 2, null);
        boolean zIsLastMessageFromReply = notificationEntry.isLastMessageFromReply();
        Integer numValueOf = null;
        boolean zShouldSuppressStatusBar = notificationEntry.shouldSuppressStatusBar();
        boolean zShowingPulsing = notificationEntry.showingPulsing();
        StatusBarIconView aodIcon = notificationEntry.getIcons().getAodIcon();
        Icon sourceIcon = aodIcon != null ? aodIcon.getSourceIcon() : null;
        StatusBarIconView shelfIcon = notificationEntry.getIcons().getShelfIcon();
        Icon sourceIcon2 = shelfIcon != null ? shelfIcon.getSourceIcon() : null;
        StatusBarIconView statusBarIcon = notificationEntry.getIcons().getStatusBarIcon();
        Icon sourceIcon3 = statusBarIcon != null ? statusBarIcon.getSourceIcon() : null;
        int uid = notificationEntry.getSbn().getUid();
        String packageName = notificationEntry.getSbn().getPackageName();
        packageName.getClass();
        InstanceId instanceId = notificationEntry.getSbn().getInstanceId();
        if (instanceId != null) {
            numValueOf = Integer.valueOf(instanceId.getId());
        }
        return RenderNotificationListInteractorKt.createOrReuse(activeNotificationsStore, key, groupKey, zIsMinimized$default, zIsRowDismissed, zIsSilent$default, zIsLastMessageFromReply, zShouldSuppressStatusBar, zShowingPulsing, sourceIcon, sourceIcon2, sourceIcon3, uid, packageName, numValueOf, notificationEntry.getSbn().getNotification().isGroupSummary(), notificationEntry.getBucket());
    }

    public final void addListEntry(ListEntry listEntry) {
        listEntry.getClass();
        if (!(listEntry instanceof GroupEntry)) {
            NotificationEntry representativeEntry = listEntry.getRepresentativeEntry();
            if (representativeEntry != null) {
                this.builder.addIndividualNotif(toModel(representativeEntry));
                return;
            }
            return;
        }
        GroupEntry groupEntry = (GroupEntry) listEntry;
        NotificationEntry summary = groupEntry.getSummary();
        if (summary != null) {
            ActiveNotificationModel model = toModel(summary);
            List<NotificationEntry> children = groupEntry.getChildren();
            children.getClass();
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(children, 10));
            for (NotificationEntry notificationEntry : children) {
                notificationEntry.getClass();
                arrayList.add(toModel(notificationEntry));
            }
            ActiveNotificationsStore.Builder builder = this.builder;
            ActiveNotificationsStore activeNotificationsStore = this.existingModels;
            String key = groupEntry.getKey();
            key.getClass();
            builder.addNotifGroup(RenderNotificationListInteractorKt.createOrReuse(activeNotificationsStore, key, model, arrayList));
        }
    }

    public final ActiveNotificationsStore build() {
        return this.builder.build();
    }

    public final Map flatMapToRankingsMap(List list) {
        list.getClass();
        ArrayMap arrayMap = new ArrayMap();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ListEntry listEntry = (ListEntry) it.next();
            if (listEntry instanceof NotificationEntry) {
                NotificationEntry representativeEntry = ((NotificationEntry) listEntry).getRepresentativeEntry();
                if (representativeEntry != null) {
                    arrayMap.put(representativeEntry.getKey(), Integer.valueOf(representativeEntry.getRanking().getRank()));
                }
            } else if (listEntry instanceof GroupEntry) {
                GroupEntry groupEntry = (GroupEntry) listEntry;
                NotificationEntry summary = groupEntry.getSummary();
                if (summary != null) {
                    arrayMap.put(summary.getKey(), Integer.valueOf(summary.getRanking().getRank()));
                }
                for (NotificationEntry notificationEntry : groupEntry.getChildren()) {
                    arrayMap.put(notificationEntry.getKey(), Integer.valueOf(notificationEntry.getRanking().getRank()));
                }
            }
        }
        return arrayMap;
    }

    public final void setRankingsMap(List list) {
        list.getClass();
        this.builder.setRankingsMap(flatMapToRankingsMap(list));
    }
}
