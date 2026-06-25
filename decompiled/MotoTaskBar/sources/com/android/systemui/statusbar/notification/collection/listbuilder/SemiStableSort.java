package com.android.systemui.statusbar.notification.collection.listbuilder;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.function.ToIntFunction;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.math.MathKt;

/* JADX INFO: compiled from: SemiStableSort.kt */
/* JADX INFO: loaded from: classes.dex */
public final class SemiStableSort {
    public static final Companion Companion = new Companion(null);
    private final Lazy preallocatedWorkspace$delegate = LazyKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.SemiStableSort$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        /* JADX INFO: renamed from: invoke */
        public final Object mo2224invoke() {
            return SemiStableSort.preallocatedWorkspace_delegate$lambda$0();
        }
    });
    private final Lazy preallocatedAdditions$delegate = LazyKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.SemiStableSort$$ExternalSyntheticLambda1
        @Override // kotlin.jvm.functions.Function0
        /* JADX INFO: renamed from: invoke */
        public final Object mo2224invoke() {
            return SemiStableSort.preallocatedAdditions_delegate$lambda$1();
        }
    });
    private final Lazy preallocatedMapToIndex$delegate = LazyKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.SemiStableSort$$ExternalSyntheticLambda2
        @Override // kotlin.jvm.functions.Function0
        /* JADX INFO: renamed from: invoke */
        public final Object mo2224invoke() {
            return SemiStableSort.preallocatedMapToIndex_delegate$lambda$2();
        }
    });
    private final Lazy preallocatedMapToIndexComparator$delegate = LazyKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.SemiStableSort$$ExternalSyntheticLambda3
        @Override // kotlin.jvm.functions.Function0
        /* JADX INFO: renamed from: invoke */
        public final Object mo2224invoke() {
            return SemiStableSort.preallocatedMapToIndexComparator_delegate$lambda$3(this.f$0);
        }
    });

    /* JADX INFO: compiled from: SemiStableSort.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final void insertPreSortedElementsWithFewestMisOrderings(List list, Iterable iterable, Comparator comparator) {
            if (SemiStableSortKt.getDEBUG()) {
                System.out.println((Object) ("  To " + list + " insert " + iterable + " with fewest misordering"));
            }
            int i = 0;
            for (Object obj : iterable) {
                if (SemiStableSortKt.getDEBUG()) {
                    System.out.println((Object) ("    need to add " + obj + " to " + list + ", starting at " + i));
                }
                if (SemiStableSortKt.getDEBUG()) {
                    System.out.print((Object) "      ");
                }
                int size = list.size();
                int sign = 0;
                int i2 = 0;
                int i3 = i;
                while (i < size) {
                    sign += MathKt.getSign(comparator.compare(obj, list.get(i)));
                    if (sign > i2) {
                        i3 = i + 1;
                        i2 = sign;
                    }
                    if (SemiStableSortKt.getDEBUG()) {
                        System.out.print((Object) ("sum[" + i + "]=" + sign + ", "));
                    }
                    i++;
                }
                if (SemiStableSortKt.getDEBUG()) {
                    System.out.println((Object) ("inserting " + obj + " at " + i3));
                }
                list.add(i3, obj);
                i = i3 + 1;
            }
        }

        public final boolean isSorted(List list, Comparator comparator) {
            list.getClass();
            comparator.getClass();
            if (list.size() <= 1) {
                return true;
            }
            Iterator it = list.iterator();
            Object next = it.next();
            while (it.hasNext()) {
                Object next2 = it.next();
                if (comparator.compare(next, next2) > 0) {
                    return false;
                }
                next = next2;
            }
            return true;
        }
    }

    /* JADX INFO: compiled from: SemiStableSort.kt */
    public interface StableOrder {
        Integer getRank(Object obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ArrayList preallocatedAdditions_delegate$lambda$1() {
        return new ArrayList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Comparator preallocatedMapToIndexComparator_delegate$lambda$3(final SemiStableSort semiStableSort) {
        return Comparator.comparingInt(new ToIntFunction() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.SemiStableSort$preallocatedMapToIndexComparator$2$1
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                Integer num = (Integer) this.this$0.getPreallocatedMapToIndex().get(obj);
                if (num != null) {
                    return num.intValue();
                }
                return -1;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final HashMap preallocatedMapToIndex_delegate$lambda$2() {
        return new HashMap();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ArrayList preallocatedWorkspace_delegate$lambda$0() {
        return new ArrayList();
    }

    public final ArrayList getPreallocatedAdditions() {
        return (ArrayList) this.preallocatedAdditions$delegate.getValue();
    }

    public final HashMap getPreallocatedMapToIndex() {
        return (HashMap) this.preallocatedMapToIndex$delegate.getValue();
    }

    public final Comparator getPreallocatedMapToIndexComparator() {
        Object value = this.preallocatedMapToIndexComparator$delegate.getValue();
        value.getClass();
        return (Comparator) value;
    }

    public final ArrayList getPreallocatedWorkspace() {
        return (ArrayList) this.preallocatedWorkspace$delegate.getValue();
    }

    public final boolean sort(List list, StableOrder stableOrder, Comparator comparator) {
        list.getClass();
        stableOrder.getClass();
        comparator.getClass();
        getPreallocatedWorkspace().clear();
        ArrayList preallocatedWorkspace = getPreallocatedWorkspace();
        preallocatedWorkspace.getClass();
        boolean zSortTo = sortTo(list, stableOrder, comparator, preallocatedWorkspace);
        list.clear();
        list.addAll(preallocatedWorkspace);
        return zSortTo;
    }

    public final boolean sortTo(Iterable iterable, final StableOrder stableOrder, Comparator comparator, List list) {
        iterable.getClass();
        stableOrder.getClass();
        comparator.getClass();
        list.getClass();
        if (SemiStableSortKt.getDEBUG()) {
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(iterable, 10));
            for (Object obj : iterable) {
                arrayList.add(TuplesKt.to(obj, stableOrder.getRank(obj)));
            }
            System.out.println((Object) ("\n> START from " + arrayList));
        }
        List listSubList = list.isEmpty() ? list : null;
        if (listSubList == null) {
            listSubList = list.subList(list.size(), list.size());
        }
        for (Object obj2 : iterable) {
            if (stableOrder.getRank(obj2) != null) {
                listSubList.add(obj2);
            }
        }
        if (listSubList.size() > 1) {
            CollectionsKt.sortWith(listSubList, new Comparator() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.SemiStableSort$sortTo$$inlined$sortBy$1
                @Override // java.util.Comparator
                public final int compare(Object obj3, Object obj4) {
                    Integer rank = stableOrder.getRank(obj3);
                    rank.getClass();
                    Integer rank2 = stableOrder.getRank(obj4);
                    rank2.getClass();
                    return ComparisonsKt.compareValues(rank, rank2);
                }
            });
        }
        boolean zIsSorted = Companion.isSorted(listSubList, comparator);
        getPreallocatedAdditions().clear();
        ArrayList preallocatedAdditions = getPreallocatedAdditions();
        preallocatedAdditions.getClass();
        for (Object obj3 : iterable) {
            if (stableOrder.getRank(obj3) == null) {
                preallocatedAdditions.add(obj3);
            }
        }
        CollectionsKt.sortWith(preallocatedAdditions, comparator);
        Companion.insertPreSortedElementsWithFewestMisOrderings(listSubList, preallocatedAdditions, comparator);
        getPreallocatedAdditions().clear();
        return zIsSorted;
    }

    public final boolean stabilizeTo(Iterable iterable, final StableOrder stableOrder, List list) {
        iterable.getClass();
        stableOrder.getClass();
        list.getClass();
        List listSubList = list.isEmpty() ? list : null;
        if (listSubList == null) {
            listSubList = list.subList(list.size(), list.size());
        }
        for (Object obj : iterable) {
            if (stableOrder.getRank(obj) != null) {
                listSubList.add(obj);
            }
        }
        Comparator comparator = new Comparator() { // from class: com.android.systemui.statusbar.notification.collection.listbuilder.SemiStableSort$stabilizeTo$$inlined$compareBy$1
            @Override // java.util.Comparator
            public final int compare(Object obj2, Object obj3) {
                Integer rank = stableOrder.getRank(obj2);
                rank.getClass();
                Integer rank2 = stableOrder.getRank(obj3);
                rank2.getClass();
                return ComparisonsKt.compareValues(rank, rank2);
            }
        };
        boolean zIsSorted = Companion.isSorted(listSubList, comparator);
        if (!zIsSorted) {
            CollectionsKt.sortWith(listSubList, comparator);
        }
        if (listSubList.isEmpty()) {
            for (Object obj2 : iterable) {
                if (stableOrder.getRank(obj2) == null) {
                    listSubList.add(obj2);
                }
            }
            return zIsSorted;
        }
        getPreallocatedAdditions().clear();
        ArrayList preallocatedAdditions = getPreallocatedAdditions();
        preallocatedAdditions.getClass();
        for (Object obj3 : iterable) {
            if (stableOrder.getRank(obj3) == null) {
                preallocatedAdditions.add(obj3);
            }
        }
        if (!preallocatedAdditions.isEmpty()) {
            getPreallocatedMapToIndex().clear();
            int i = 0;
            for (Object obj4 : iterable) {
                int i2 = i + 1;
                if (i < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                getPreallocatedMapToIndex().put(obj4, Integer.valueOf(i));
                i = i2;
            }
            Comparator preallocatedMapToIndexComparator = getPreallocatedMapToIndexComparator();
            preallocatedMapToIndexComparator.getClass();
            Companion.insertPreSortedElementsWithFewestMisOrderings(listSubList, preallocatedAdditions, preallocatedMapToIndexComparator);
            getPreallocatedMapToIndex().clear();
        }
        getPreallocatedAdditions().clear();
        return zIsSorted;
    }
}
