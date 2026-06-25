package kotlin.collections;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* JADX INFO: Access modifiers changed from: package-private */
/* JADX INFO: compiled from: MutableCollectionsJVM.kt */
/* JADX INFO: loaded from: classes2.dex */
public abstract class CollectionsKt__MutableCollectionsJVMKt extends CollectionsKt__IteratorsKt {
    public static void sort(List list) {
        list.getClass();
        if (list.size() > 1) {
            Collections.sort(list);
        }
    }

    public static void sortWith(List list, Comparator comparator) {
        list.getClass();
        comparator.getClass();
        if (list.size() > 1) {
            Collections.sort(list, comparator);
        }
    }
}
