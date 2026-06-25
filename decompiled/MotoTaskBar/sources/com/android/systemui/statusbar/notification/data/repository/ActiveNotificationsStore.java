package com.android.systemui.statusbar.notification.data.repository;

import com.android.systemui.statusbar.notification.shared.ActiveNotificationEntryModel;
import com.android.systemui.statusbar.notification.shared.ActiveNotificationGroupModel;
import com.android.systemui.statusbar.notification.shared.ActiveNotificationModel;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ActiveNotificationListRepository.kt */
/* JADX INFO: loaded from: classes.dex */
public final class ActiveNotificationsStore {
    private final Map groups;
    private final Map individuals;
    private final Map rankingsMap;
    private final List renderList;

    /* JADX INFO: compiled from: ActiveNotificationListRepository.kt */
    public final class Builder {
        private final Map groups = new LinkedHashMap();
        private final Map individuals = new LinkedHashMap();
        private final List renderList = new ArrayList();
        private Map rankingsMap = MapsKt.emptyMap();

        public final void addIndividualNotif(ActiveNotificationModel activeNotificationModel) {
            activeNotificationModel.getClass();
            this.renderList.add(new Key.Individual(activeNotificationModel.getKey()));
            this.individuals.put(activeNotificationModel.getKey(), activeNotificationModel);
        }

        public final void addNotifGroup(ActiveNotificationGroupModel activeNotificationGroupModel) {
            activeNotificationGroupModel.getClass();
            this.renderList.add(new Key.Group(activeNotificationGroupModel.getKey()));
            this.groups.put(activeNotificationGroupModel.getKey(), activeNotificationGroupModel);
            this.individuals.put(activeNotificationGroupModel.getSummary().getKey(), activeNotificationGroupModel.getSummary());
            for (ActiveNotificationModel activeNotificationModel : activeNotificationGroupModel.getChildren()) {
                this.individuals.put(activeNotificationModel.getKey(), activeNotificationModel);
            }
        }

        public final ActiveNotificationsStore build() {
            return new ActiveNotificationsStore(this.groups, this.individuals, this.renderList, this.rankingsMap);
        }

        public final void setRankingsMap(Map map) {
            map.getClass();
            this.rankingsMap = MapsKt.toMap(map);
        }
    }

    /* JADX INFO: compiled from: ActiveNotificationListRepository.kt */
    public abstract class Key {

        /* JADX INFO: compiled from: ActiveNotificationListRepository.kt */
        public final class Group extends Key {
            private final String key;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public Group(String str) {
                super(null);
                str.getClass();
                this.key = str;
            }

            public boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                return (obj instanceof Group) && Intrinsics.areEqual(this.key, ((Group) obj).key);
            }

            public final String getKey() {
                return this.key;
            }

            public int hashCode() {
                return this.key.hashCode();
            }

            public String toString() {
                return "Group(key=" + this.key + ")";
            }
        }

        /* JADX INFO: compiled from: ActiveNotificationListRepository.kt */
        public final class Individual extends Key {
            private final String key;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public Individual(String str) {
                super(null);
                str.getClass();
                this.key = str;
            }

            public boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                return (obj instanceof Individual) && Intrinsics.areEqual(this.key, ((Individual) obj).key);
            }

            public final String getKey() {
                return this.key;
            }

            public int hashCode() {
                return this.key.hashCode();
            }

            public String toString() {
                return "Individual(key=" + this.key + ")";
            }
        }

        private Key() {
        }

        public /* synthetic */ Key(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public ActiveNotificationsStore(Map map, Map map2, List list, Map map3) {
        map.getClass();
        map2.getClass();
        list.getClass();
        map3.getClass();
        this.groups = map;
        this.individuals = map2;
        this.renderList = list;
        this.rankingsMap = map3;
    }

    public /* synthetic */ ActiveNotificationsStore(Map map, Map map2, List list, Map map3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? MapsKt.emptyMap() : map, (i & 2) != 0 ? MapsKt.emptyMap() : map2, (i & 4) != 0 ? CollectionsKt.emptyList() : list, (i & 8) != 0 ? MapsKt.emptyMap() : map3);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ActiveNotificationsStore)) {
            return false;
        }
        ActiveNotificationsStore activeNotificationsStore = (ActiveNotificationsStore) obj;
        return Intrinsics.areEqual(this.groups, activeNotificationsStore.groups) && Intrinsics.areEqual(this.individuals, activeNotificationsStore.individuals) && Intrinsics.areEqual(this.renderList, activeNotificationsStore.renderList) && Intrinsics.areEqual(this.rankingsMap, activeNotificationsStore.rankingsMap);
    }

    public final ActiveNotificationEntryModel get(Key key) {
        key.getClass();
        if (key instanceof Key.Group) {
            return (ActiveNotificationEntryModel) this.groups.get(((Key.Group) key).getKey());
        }
        if (key instanceof Key.Individual) {
            return (ActiveNotificationEntryModel) this.individuals.get(((Key.Individual) key).getKey());
        }
        throw new NoWhenBranchMatchedException();
    }

    public final Map getGroups() {
        return this.groups;
    }

    public final Map getIndividuals() {
        return this.individuals;
    }

    public final Map getRankingsMap() {
        return this.rankingsMap;
    }

    public final List getRenderList() {
        return this.renderList;
    }

    public int hashCode() {
        return (((((this.groups.hashCode() * 31) + this.individuals.hashCode()) * 31) + this.renderList.hashCode()) * 31) + this.rankingsMap.hashCode();
    }

    public String toString() {
        return "ActiveNotificationsStore(groups=" + this.groups + ", individuals=" + this.individuals + ", renderList=" + this.renderList + ", rankingsMap=" + this.rankingsMap + ")";
    }
}
