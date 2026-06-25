package kotlin.collections;

import java.util.LinkedHashSet;
import java.util.Set;

/* JADX INFO: Access modifiers changed from: package-private */
/* JADX INFO: compiled from: Sets.kt */
/* JADX INFO: loaded from: classes2.dex */
public abstract class SetsKt__SetsKt extends SetsKt__SetsJVMKt {
    public static Set emptySet() {
        return EmptySet.INSTANCE;
    }

    public static Set mutableSetOf(Object... objArr) {
        objArr.getClass();
        return (Set) ArraysKt___ArraysKt.toCollection(objArr, new LinkedHashSet(MapsKt__MapsJVMKt.mapCapacity(objArr.length)));
    }

    public static final Set optimizeReadOnlySet(Set set) {
        set.getClass();
        int size = set.size();
        return size != 0 ? size != 1 ? set : SetsKt__SetsJVMKt.setOf(set.iterator().next()) : emptySet();
    }

    public static Set setOf(Object... objArr) {
        objArr.getClass();
        return ArraysKt___ArraysKt.toSet(objArr);
    }
}
