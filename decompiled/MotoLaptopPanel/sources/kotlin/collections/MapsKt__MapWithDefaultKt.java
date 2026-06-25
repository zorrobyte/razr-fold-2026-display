package kotlin.collections;

import java.util.Map;
import java.util.NoSuchElementException;

/* JADX INFO: compiled from: MapWithDefault.kt */
/* JADX INFO: loaded from: classes.dex */
abstract class MapsKt__MapWithDefaultKt {
    public static final Object getOrImplicitDefaultNullable(Map map, Object obj) {
        map.getClass();
        Object obj2 = map.get(obj);
        if (obj2 != null || map.containsKey(obj)) {
            return obj2;
        }
        throw new NoSuchElementException("Key " + obj + " is missing in the map.");
    }
}
