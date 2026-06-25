package androidx.compose.runtime;

import java.util.ArrayList;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: Stack.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class Stack {
    /* JADX INFO: renamed from: clear-impl, reason: not valid java name */
    public static final void m48clearimpl(ArrayList arrayList) {
        arrayList.clear();
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static ArrayList m49constructorimpl(ArrayList arrayList) {
        return arrayList;
    }

    /* JADX INFO: renamed from: constructor-impl$default, reason: not valid java name */
    public static /* synthetic */ ArrayList m50constructorimpl$default(ArrayList arrayList, int i, DefaultConstructorMarker defaultConstructorMarker) {
        if ((i & 1) != 0) {
            arrayList = new ArrayList();
        }
        return m49constructorimpl(arrayList);
    }

    /* JADX INFO: renamed from: getSize-impl, reason: not valid java name */
    public static final int m51getSizeimpl(ArrayList arrayList) {
        return arrayList.size();
    }

    /* JADX INFO: renamed from: isEmpty-impl, reason: not valid java name */
    public static final boolean m52isEmptyimpl(ArrayList arrayList) {
        return arrayList.isEmpty();
    }

    /* JADX INFO: renamed from: isNotEmpty-impl, reason: not valid java name */
    public static final boolean m53isNotEmptyimpl(ArrayList arrayList) {
        return !m52isEmptyimpl(arrayList);
    }

    /* JADX INFO: renamed from: peek-impl, reason: not valid java name */
    public static final Object m54peekimpl(ArrayList arrayList) {
        return arrayList.get(m51getSizeimpl(arrayList) - 1);
    }

    /* JADX INFO: renamed from: peek-impl, reason: not valid java name */
    public static final Object m55peekimpl(ArrayList arrayList, int i) {
        return arrayList.get(i);
    }

    /* JADX INFO: renamed from: pop-impl, reason: not valid java name */
    public static final Object m56popimpl(ArrayList arrayList) {
        return arrayList.remove(m51getSizeimpl(arrayList) - 1);
    }

    /* JADX INFO: renamed from: push-impl, reason: not valid java name */
    public static final boolean m57pushimpl(ArrayList arrayList, Object obj) {
        return arrayList.add(obj);
    }

    /* JADX INFO: renamed from: toArray-impl, reason: not valid java name */
    public static final Object[] m58toArrayimpl(ArrayList arrayList) {
        int size = arrayList.size();
        Object[] objArr = new Object[size];
        for (int i = 0; i < size; i++) {
            objArr[i] = arrayList.get(i);
        }
        return objArr;
    }
}
