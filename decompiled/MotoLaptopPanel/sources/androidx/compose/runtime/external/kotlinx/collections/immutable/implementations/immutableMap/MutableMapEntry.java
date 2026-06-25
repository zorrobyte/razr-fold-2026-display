package androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableMap;

import java.util.Map;
import kotlin.jvm.internal.markers.KMappedMarker;

/* JADX INFO: compiled from: PersistentHashMapBuilderContentIterators.kt */
/* JADX INFO: loaded from: classes.dex */
final class MutableMapEntry extends MapEntry implements Map.Entry, KMappedMarker {
    private final PersistentHashMapBuilderEntriesIterator parentIterator;
    private Object value;

    public MutableMapEntry(PersistentHashMapBuilderEntriesIterator persistentHashMapBuilderEntriesIterator, Object obj, Object obj2) {
        super(obj, obj2);
        this.parentIterator = persistentHashMapBuilderEntriesIterator;
        this.value = obj2;
    }

    @Override // androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableMap.MapEntry, java.util.Map.Entry
    public Object getValue() {
        return this.value;
    }

    @Override // androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableMap.MapEntry, java.util.Map.Entry
    public Object setValue(Object obj) {
        Object value = getValue();
        setValue(obj);
        this.parentIterator.setValue(getKey(), obj);
        return value;
    }

    @Override // java.util.Map.Entry
    public void setValue(Object obj) {
        this.value = obj;
    }
}
