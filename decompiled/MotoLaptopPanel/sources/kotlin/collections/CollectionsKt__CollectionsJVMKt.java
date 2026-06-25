package kotlin.collections;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import kotlin.collections.builders.ListBuilder;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: Access modifiers changed from: package-private */
/* JADX INFO: compiled from: CollectionsJVM.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class CollectionsKt__CollectionsJVMKt {
    public static List build(List list) {
        list.getClass();
        return ((ListBuilder) list).build();
    }

    public static final Object[] copyToArrayOfAny(Object[] objArr, boolean z) {
        objArr.getClass();
        if (z && Intrinsics.areEqual(objArr.getClass(), Object[].class)) {
            return objArr;
        }
        Object[] objArrCopyOf = Arrays.copyOf(objArr, objArr.length, Object[].class);
        objArrCopyOf.getClass();
        return objArrCopyOf;
    }

    public static List createListBuilder() {
        return new ListBuilder(0, 1, null);
    }

    public static List createListBuilder(int i) {
        return new ListBuilder(i);
    }

    public static List listOf(Object obj) {
        List listSingletonList = Collections.singletonList(obj);
        listSingletonList.getClass();
        return listSingletonList;
    }

    public static Object[] terminateCollectionToArray(int i, Object[] objArr) {
        objArr.getClass();
        if (i < objArr.length) {
            objArr[i] = null;
        }
        return objArr;
    }
}
