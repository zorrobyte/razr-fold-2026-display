package kotlin.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Pair;

/* JADX INFO: Access modifiers changed from: package-private */
/* JADX INFO: compiled from: Maps.kt */
/* JADX INFO: loaded from: classes2.dex */
public abstract class MapsKt__MapsKt extends MapsKt__MapsJVMKt {
    public static Map emptyMap() {
        EmptyMap emptyMap = EmptyMap.INSTANCE;
        emptyMap.getClass();
        return emptyMap;
    }

    public static Object getValue(Map map, Object obj) {
        map.getClass();
        return MapsKt__MapWithDefaultKt.getOrImplicitDefaultNullable(map, obj);
    }

    public static Map mapOf(Pair... pairArr) {
        pairArr.getClass();
        return pairArr.length > 0 ? toMap(pairArr, new LinkedHashMap(MapsKt__MapsJVMKt.mapCapacity(pairArr.length))) : emptyMap();
    }

    public static Map mutableMapOf(Pair... pairArr) {
        pairArr.getClass();
        LinkedHashMap linkedHashMap = new LinkedHashMap(MapsKt__MapsJVMKt.mapCapacity(pairArr.length));
        putAll(linkedHashMap, pairArr);
        return linkedHashMap;
    }

    public static final Map optimizeReadOnlyMap(Map map) {
        map.getClass();
        int size = map.size();
        return size != 0 ? size != 1 ? map : MapsKt__MapsJVMKt.toSingletonMap(map) : emptyMap();
    }

    public static final void putAll(Map map, Iterable iterable) {
        map.getClass();
        iterable.getClass();
        Iterator it = iterable.iterator();
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            map.put(pair.component1(), pair.component2());
        }
    }

    public static final void putAll(Map map, Pair[] pairArr) {
        map.getClass();
        pairArr.getClass();
        for (Pair pair : pairArr) {
            map.put(pair.component1(), pair.component2());
        }
    }

    public static Map toMap(Iterable iterable) {
        iterable.getClass();
        if (!(iterable instanceof Collection)) {
            return optimizeReadOnlyMap(toMap(iterable, new LinkedHashMap()));
        }
        Collection collection = (Collection) iterable;
        int size = collection.size();
        if (size == 0) {
            return emptyMap();
        }
        if (size != 1) {
            return toMap(iterable, new LinkedHashMap(MapsKt__MapsJVMKt.mapCapacity(collection.size())));
        }
        return MapsKt__MapsJVMKt.mapOf((Pair) (iterable instanceof List ? ((List) iterable).get(0) : collection.iterator().next()));
    }

    public static final Map toMap(Iterable iterable, Map map) {
        iterable.getClass();
        map.getClass();
        putAll(map, iterable);
        return map;
    }

    public static Map toMap(Map map) {
        map.getClass();
        int size = map.size();
        return size != 0 ? size != 1 ? toMutableMap(map) : MapsKt__MapsJVMKt.toSingletonMap(map) : emptyMap();
    }

    public static final Map toMap(Pair[] pairArr, Map map) {
        pairArr.getClass();
        map.getClass();
        putAll(map, pairArr);
        return map;
    }

    public static Map toMutableMap(Map map) {
        map.getClass();
        return new LinkedHashMap(map);
    }
}
