package kotlin.collections;

import java.util.Collection;

/* JADX INFO: Access modifiers changed from: package-private */
/* JADX INFO: compiled from: Iterables.kt */
/* JADX INFO: loaded from: classes2.dex */
public abstract class CollectionsKt__IterablesKt extends CollectionsKt__CollectionsKt {
    public static int collectionSizeOrDefault(Iterable iterable, int i) {
        iterable.getClass();
        return iterable instanceof Collection ? ((Collection) iterable).size() : i;
    }
}
