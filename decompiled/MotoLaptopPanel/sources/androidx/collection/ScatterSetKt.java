package androidx.collection;

/* JADX INFO: compiled from: ScatterSet.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class ScatterSetKt {
    private static final MutableScatterSet EmptyScatterSet = new MutableScatterSet(0);

    public static final MutableScatterSet mutableScatterSetOf() {
        return new MutableScatterSet(0, 1, null);
    }

    public static final MutableScatterSet mutableScatterSetOf(Object obj, Object obj2) {
        MutableScatterSet mutableScatterSet = new MutableScatterSet(2);
        mutableScatterSet.plusAssign(obj);
        mutableScatterSet.plusAssign(obj2);
        return mutableScatterSet;
    }
}
