package androidx.collection.internal;

import java.util.LinkedHashMap;
import java.util.Set;

/* JADX INFO: compiled from: LruHashMap.jvm.kt */
/* JADX INFO: loaded from: classes.dex */
public final class LruHashMap {
    private final LinkedHashMap map;

    public LruHashMap(int i, float f) {
        this.map = new LinkedHashMap(i, f, true);
    }

    public final Object get(Object obj) {
        obj.getClass();
        return this.map.get(obj);
    }

    public final Set getEntries() {
        Set setEntrySet = this.map.entrySet();
        setEntrySet.getClass();
        return setEntrySet;
    }

    public final boolean isEmpty() {
        return this.map.isEmpty();
    }

    public final Object put(Object obj, Object obj2) {
        obj.getClass();
        obj2.getClass();
        return this.map.put(obj, obj2);
    }

    public final Object remove(Object obj) {
        obj.getClass();
        return this.map.remove(obj);
    }
}
