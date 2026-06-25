package androidx.collection;

/* JADX INFO: compiled from: ObjectIntMap.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class ObjectIntMapKt {
    private static final MutableObjectIntMap EmptyObjectIntMap = new MutableObjectIntMap(0);

    public static final ObjectIntMap emptyObjectIntMap() {
        MutableObjectIntMap mutableObjectIntMap = EmptyObjectIntMap;
        mutableObjectIntMap.getClass();
        return mutableObjectIntMap;
    }

    public static final MutableObjectIntMap mutableObjectIntMapOf() {
        return new MutableObjectIntMap(0, 1, null);
    }
}
