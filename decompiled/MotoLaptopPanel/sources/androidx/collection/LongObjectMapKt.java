package androidx.collection;

/* JADX INFO: compiled from: LongObjectMap.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class LongObjectMapKt {
    private static final MutableLongObjectMap EmptyLongObjectMap = new MutableLongObjectMap(0);

    public static final MutableLongObjectMap mutableLongObjectMapOf() {
        return new MutableLongObjectMap(0, 1, null);
    }
}
