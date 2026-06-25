package dagger.internal;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes2.dex */
abstract class AbstractMapFactory implements Factory {
    private final Map contributingMap;

    public abstract class Builder {
        final LinkedHashMap map;

        Builder(int i) {
            this.map = DaggerCollections.newLinkedHashMapWithExpectedSize(i);
        }

        Builder put(Object obj, Provider provider) {
            LinkedHashMap linkedHashMap = this.map;
            obj.getClass();
            provider.getClass();
            linkedHashMap.put(obj, provider);
            return this;
        }
    }

    AbstractMapFactory(Map map) {
        this.contributingMap = Collections.unmodifiableMap(map);
    }

    final Map contributingMap() {
        return this.contributingMap;
    }
}
