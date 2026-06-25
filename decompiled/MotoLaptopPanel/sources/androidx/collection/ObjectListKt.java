package androidx.collection;

/* JADX INFO: compiled from: ObjectList.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class ObjectListKt {
    private static final Object[] EmptyArray = new Object[0];
    private static final ObjectList EmptyObjectList = new MutableObjectList(0);

    public static final ObjectList emptyObjectList() {
        ObjectList objectList = EmptyObjectList;
        objectList.getClass();
        return objectList;
    }

    public static final MutableObjectList mutableObjectListOf(Object obj) {
        MutableObjectList mutableObjectList = new MutableObjectList(1);
        mutableObjectList.add(obj);
        return mutableObjectList;
    }

    public static final MutableObjectList mutableObjectListOf(Object obj, Object obj2) {
        MutableObjectList mutableObjectList = new MutableObjectList(2);
        mutableObjectList.add(obj);
        mutableObjectList.add(obj2);
        return mutableObjectList;
    }
}
