package com.android.systemui.statusbar.notification.collection.notifcollection;

import android.service.notification.NotificationListenerService;
import android.util.ArrayMap;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;

/* JADX INFO: compiled from: NotifCollectionInconsistencyTracker.kt */
/* JADX INFO: loaded from: classes.dex */
public final class NotifCollectionInconsistencyTracker {
    private boolean attached;
    private Function0 coalescedKeySetAccessor;
    private Function0 collectedKeySetAccessor;
    private final NotifCollectionLogger logger;
    private Set missingNotifications;
    private Set notificationsWithoutRankings;

    public NotifCollectionInconsistencyTracker(NotifCollectionLogger notifCollectionLogger) {
        notifCollectionLogger.getClass();
        this.logger = notifCollectionLogger;
        this.notificationsWithoutRankings = SetsKt.emptySet();
        this.missingNotifications = SetsKt.emptySet();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean logNewMissingNotifications$lambda$0(Set set, String str) {
        return !set.contains(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean logNewMissingNotifications$lambda$1(Set set, String str) {
        return !set.contains(str);
    }

    public final void attach(Function0 function0, Function0 function02) {
        function0.getClass();
        function02.getClass();
        if (this.attached) {
            throw new RuntimeException("attach() called twice");
        }
        this.attached = true;
        this.collectedKeySetAccessor = function0;
        this.coalescedKeySetAccessor = function02;
    }

    public final void logNewInconsistentRankings(ArrayMap arrayMap, NotificationListenerService.RankingMap rankingMap) {
        Set setEmptySet;
        rankingMap.getClass();
        maybeLogInconsistentRankings(this.notificationsWithoutRankings, arrayMap != null ? arrayMap : MapsKt.emptyMap(), rankingMap);
        if (arrayMap == null || (setEmptySet = arrayMap.keySet()) == null) {
            setEmptySet = SetsKt.emptySet();
        }
        this.notificationsWithoutRankings = setEmptySet;
    }

    public final void logNewMissingNotifications(NotificationListenerService.RankingMap rankingMap) {
        rankingMap.getClass();
        Function0 function0 = this.collectedKeySetAccessor;
        Function0 function02 = null;
        if (function0 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("collectedKeySetAccessor");
            function0 = null;
        }
        final Set set = (Set) function0.mo2224invoke();
        Function0 function03 = this.coalescedKeySetAccessor;
        if (function03 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("coalescedKeySetAccessor");
        } else {
            function02 = function03;
        }
        final Set set2 = (Set) function02.mo2224invoke();
        String[] orderedKeys = rankingMap.getOrderedKeys();
        orderedKeys.getClass();
        Set set3 = SequencesKt.toSet(SequencesKt.filter(SequencesKt.filter(ArraysKt.asSequence(orderedKeys), new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionInconsistencyTracker$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Boolean.valueOf(NotifCollectionInconsistencyTracker.logNewMissingNotifications$lambda$0(set, (String) obj));
            }
        }), new Function1() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionInconsistencyTracker$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Boolean.valueOf(NotifCollectionInconsistencyTracker.logNewMissingNotifications$lambda$1(set2, (String) obj));
            }
        }));
        maybeLogMissingNotifications(this.missingNotifications, set3);
        this.missingNotifications = set3;
    }

    public final void maybeLogInconsistentRankings(Set set, Map map, NotificationListenerService.RankingMap rankingMap) {
        set.getClass();
        map.getClass();
        rankingMap.getClass();
        if ((set.isEmpty() && map.isEmpty()) || Intrinsics.areEqual(set, map.keySet())) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = set.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            String str = (String) it.next();
            String str2 = !map.containsKey(str) ? str : null;
            String[] orderedKeys = rankingMap.getOrderedKeys();
            orderedKeys.getClass();
            String str3 = ArraysKt.contains(orderedKeys, str) ? str2 : null;
            if (str3 != null) {
                arrayList.add(str3);
            }
        }
        List listSorted = CollectionsKt.sorted(arrayList);
        if (!listSorted.isEmpty()) {
            this.logger.logRecoveredRankings(listSorted, map.size());
        }
        ArrayList arrayList2 = new ArrayList();
        for (Map.Entry entry : map.entrySet()) {
            String str4 = (String) entry.getKey();
            NotificationEntry notificationEntry = (NotificationEntry) entry.getValue();
            if (set.contains(str4)) {
                notificationEntry = null;
            }
            if (notificationEntry != null) {
                arrayList2.add(notificationEntry);
            }
        }
        List listSortedWith = CollectionsKt.sortedWith(arrayList2, new Comparator() { // from class: com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionInconsistencyTracker$maybeLogInconsistentRankings$$inlined$sortedBy$1
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return ComparisonsKt.compareValues(((NotificationEntry) obj).getKey(), ((NotificationEntry) obj2).getKey());
            }
        });
        if (listSortedWith.isEmpty()) {
            return;
        }
        this.logger.logMissingRankings(listSortedWith, map.size(), rankingMap);
    }

    public final void maybeLogMissingNotifications(Set set, Set set2) {
        set.getClass();
        set2.getClass();
        if ((set.isEmpty() && set2.isEmpty()) || Intrinsics.areEqual(set, set2)) {
            return;
        }
        List listSorted = CollectionsKt.sorted(SetsKt.minus(set, set2));
        if (!listSorted.isEmpty()) {
            this.logger.logFoundNotifications(listSorted, set2.size());
        }
        List listSorted2 = CollectionsKt.sorted(SetsKt.minus(set2, set));
        if (listSorted2.isEmpty()) {
            return;
        }
        this.logger.logMissingNotifications(listSorted2, set2.size());
    }
}
