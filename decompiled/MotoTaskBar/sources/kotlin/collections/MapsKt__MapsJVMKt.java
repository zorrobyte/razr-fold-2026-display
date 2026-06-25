package kotlin.collections;

import java.util.Collections;
import java.util.Map;
import kotlin.Pair;
import kotlin.collections.builders.MapBuilder;

/* JADX INFO: Access modifiers changed from: package-private */
/* JADX INFO: compiled from: MapsJVM.kt */
/* JADX INFO: loaded from: classes2.dex */
public abstract class MapsKt__MapsJVMKt extends MapsKt__MapWithDefaultKt {
    public static Map build(Map map) {
        map.getClass();
        return ((MapBuilder) map).build();
    }

    public static Map createMapBuilder(int i) {
        return new MapBuilder(i);
    }

    public static int mapCapacity(int i) {
        if (i < 0) {
            return i;
        }
        if (i < 3) {
            return i + 1;
        }
        if (i < 1073741824) {
            return (int) ((i / 0.75f) + 1.0f);
        }
        return Integer.MAX_VALUE;
    }

    public static Map mapOf(Pair pair) {
        pair.getClass();
        Map mapSingletonMap = Collections.singletonMap(pair.getFirst(), pair.getSecond());
        mapSingletonMap.getClass();
        return mapSingletonMap;
    }

    public static final Map toSingletonMap(Map map) {
        map.getClass();
        Map.Entry entry = (Map.Entry) map.entrySet().iterator().next();
        Map mapSingletonMap = Collections.singletonMap(entry.getKey(), entry.getValue());
        mapSingletonMap.getClass();
        return mapSingletonMap;
    }
}
