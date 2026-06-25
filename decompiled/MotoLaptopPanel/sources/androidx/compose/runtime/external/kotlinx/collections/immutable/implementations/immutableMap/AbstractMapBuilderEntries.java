package androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableMap;

import java.util.Map;
import kotlin.collections.AbstractMutableSet;

/* JADX INFO: compiled from: PersistentHashMapBuilderContentViews.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractMapBuilderEntries extends AbstractMutableSet {
    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final /* bridge */ boolean contains(Object obj) {
        if (obj instanceof Map.Entry) {
            return contains((Map.Entry) obj);
        }
        return false;
    }

    public final boolean contains(Map.Entry entry) {
        if ((entry != null ? entry : null) == null) {
            return false;
        }
        return containsEntry(entry);
    }

    public abstract boolean containsEntry(Map.Entry entry);

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final /* bridge */ boolean remove(Object obj) {
        if (obj instanceof Map.Entry) {
            return remove((Map.Entry) obj);
        }
        return false;
    }

    public final boolean remove(Map.Entry entry) {
        if ((entry != null ? entry : null) == null) {
            return false;
        }
        return removeEntry(entry);
    }

    public abstract boolean removeEntry(Map.Entry entry);
}
