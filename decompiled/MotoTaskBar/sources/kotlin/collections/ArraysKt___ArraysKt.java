package kotlin.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.ArrayIteratorKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;

/* JADX INFO: Access modifiers changed from: package-private */
/* JADX INFO: compiled from: _Arrays.kt */
/* JADX INFO: loaded from: classes2.dex */
public abstract class ArraysKt___ArraysKt extends ArraysKt___ArraysJvmKt {
    public static Iterable asIterable(Object[] objArr) {
        objArr.getClass();
        return objArr.length == 0 ? CollectionsKt__CollectionsKt.emptyList() : new ArraysKt___ArraysKt$asIterable$$inlined$Iterable$1(objArr);
    }

    public static Sequence asSequence(final Object[] objArr) {
        objArr.getClass();
        return objArr.length == 0 ? SequencesKt.emptySequence() : new Sequence() { // from class: kotlin.collections.ArraysKt___ArraysKt$asSequence$$inlined$Sequence$1
            @Override // kotlin.sequences.Sequence
            public Iterator iterator() {
                return ArrayIteratorKt.iterator(objArr);
            }
        };
    }

    public static boolean contains(int[] iArr, int i) {
        iArr.getClass();
        return indexOf(iArr, i) >= 0;
    }

    public static boolean contains(Object[] objArr, Object obj) {
        objArr.getClass();
        return indexOf(objArr, obj) >= 0;
    }

    public static List drop(Object[] objArr, int i) {
        objArr.getClass();
        if (i >= 0) {
            return takeLast(objArr, RangesKt.coerceAtLeast(objArr.length - i, 0));
        }
        throw new IllegalArgumentException(("Requested element count " + i + " is less than zero.").toString());
    }

    public static List filterNotNull(Object[] objArr) {
        objArr.getClass();
        return (List) filterNotNullTo(objArr, new ArrayList());
    }

    public static final Collection filterNotNullTo(Object[] objArr, Collection collection) {
        objArr.getClass();
        collection.getClass();
        for (Object obj : objArr) {
            if (obj != null) {
                collection.add(obj);
            }
        }
        return collection;
    }

    public static Object first(Object[] objArr) {
        objArr.getClass();
        if (objArr.length != 0) {
            return objArr[0];
        }
        throw new NoSuchElementException("Array is empty.");
    }

    public static int getLastIndex(int[] iArr) {
        iArr.getClass();
        return iArr.length - 1;
    }

    public static int getLastIndex(long[] jArr) {
        jArr.getClass();
        return jArr.length - 1;
    }

    public static final int getLastIndex(Object[] objArr) {
        objArr.getClass();
        return objArr.length - 1;
    }

    public static Object getOrNull(Object[] objArr, int i) {
        objArr.getClass();
        if (i < 0 || i >= objArr.length) {
            return null;
        }
        return objArr[i];
    }

    public static final int indexOf(int[] iArr, int i) {
        iArr.getClass();
        int length = iArr.length;
        for (int i2 = 0; i2 < length; i2++) {
            if (i == iArr[i2]) {
                return i2;
            }
        }
        return -1;
    }

    public static int indexOf(Object[] objArr, Object obj) {
        objArr.getClass();
        int i = 0;
        if (obj == null) {
            int length = objArr.length;
            while (i < length) {
                if (objArr[i] == null) {
                    return i;
                }
                i++;
            }
            return -1;
        }
        int length2 = objArr.length;
        while (i < length2) {
            if (Intrinsics.areEqual(obj, objArr[i])) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public static char single(char[] cArr) {
        cArr.getClass();
        int length = cArr.length;
        if (length == 0) {
            throw new NoSuchElementException("Array is empty.");
        }
        if (length == 1) {
            return cArr[0];
        }
        throw new IllegalArgumentException("Array has more than one element.");
    }

    public static Object singleOrNull(Object[] objArr) {
        objArr.getClass();
        if (objArr.length == 1) {
            return objArr[0];
        }
        return null;
    }

    public static final List takeLast(Object[] objArr, int i) {
        objArr.getClass();
        if (i < 0) {
            throw new IllegalArgumentException(("Requested element count " + i + " is less than zero.").toString());
        }
        if (i == 0) {
            return CollectionsKt__CollectionsKt.emptyList();
        }
        int length = objArr.length;
        if (i >= length) {
            return toList(objArr);
        }
        if (i == 1) {
            return CollectionsKt__CollectionsJVMKt.listOf(objArr[length - 1]);
        }
        ArrayList arrayList = new ArrayList(i);
        for (int i2 = length - i; i2 < length; i2++) {
            arrayList.add(objArr[i2]);
        }
        return arrayList;
    }

    public static final Collection toCollection(Object[] objArr, Collection collection) {
        objArr.getClass();
        collection.getClass();
        for (Object obj : objArr) {
            collection.add(obj);
        }
        return collection;
    }

    public static List toList(Object[] objArr) {
        objArr.getClass();
        int length = objArr.length;
        return length != 0 ? length != 1 ? toMutableList(objArr) : CollectionsKt__CollectionsJVMKt.listOf(objArr[0]) : CollectionsKt__CollectionsKt.emptyList();
    }

    public static List toMutableList(int[] iArr) {
        iArr.getClass();
        ArrayList arrayList = new ArrayList(iArr.length);
        for (int i : iArr) {
            arrayList.add(Integer.valueOf(i));
        }
        return arrayList;
    }

    public static List toMutableList(Object[] objArr) {
        objArr.getClass();
        return new ArrayList(CollectionsKt__CollectionsKt.asCollection(objArr));
    }

    public static Set toSet(Object[] objArr) {
        objArr.getClass();
        int length = objArr.length;
        return length != 0 ? length != 1 ? (Set) toCollection(objArr, new LinkedHashSet(MapsKt__MapsJVMKt.mapCapacity(objArr.length))) : SetsKt__SetsJVMKt.setOf(objArr[0]) : SetsKt__SetsKt.emptySet();
    }

    public static Iterable withIndex(final Object[] objArr) {
        objArr.getClass();
        return new IndexingIterable(new Function0() { // from class: kotlin.collections.ArraysKt___ArraysKt$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return ArrayIteratorKt.iterator(objArr);
            }
        });
    }
}
