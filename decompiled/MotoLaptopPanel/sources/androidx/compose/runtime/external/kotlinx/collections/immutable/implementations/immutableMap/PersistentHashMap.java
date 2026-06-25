package androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableMap;

import androidx.compose.runtime.external.kotlinx.collections.immutable.ImmutableCollection;
import androidx.compose.runtime.external.kotlinx.collections.immutable.ImmutableSet;
import androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableMap.TrieNode;
import java.util.Map;
import java.util.Set;
import kotlin.collections.AbstractMap;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.markers.KMappedMarker;

/* JADX INFO: compiled from: PersistentHashMap.kt */
/* JADX INFO: loaded from: classes.dex */
public class PersistentHashMap extends AbstractMap implements Map, KMappedMarker {
    private final TrieNode node;
    private final int size;
    public static final Companion Companion = new Companion(null);
    public static final int $stable = 8;
    private static final PersistentHashMap EMPTY = new PersistentHashMap(TrieNode.Companion.getEMPTY$runtime_release(), 0);

    /* JADX INFO: compiled from: PersistentHashMap.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final PersistentHashMap emptyOf$runtime_release() {
            PersistentHashMap persistentHashMap = PersistentHashMap.EMPTY;
            persistentHashMap.getClass();
            return persistentHashMap;
        }
    }

    public PersistentHashMap(TrieNode trieNode, int i) {
        this.node = trieNode;
        this.size = i;
    }

    private final ImmutableSet createEntries() {
        return new PersistentHashMapEntries(this);
    }

    @Override // java.util.Map
    public boolean containsKey(Object obj) {
        return this.node.containsKey(obj != null ? obj.hashCode() : 0, obj, 0);
    }

    @Override // java.util.Map
    public Object get(Object obj) {
        return this.node.get(obj != null ? obj.hashCode() : 0, obj, 0);
    }

    @Override // kotlin.collections.AbstractMap
    public final Set getEntries() {
        return createEntries();
    }

    @Override // kotlin.collections.AbstractMap
    public ImmutableSet getKeys() {
        return new PersistentHashMapKeys(this);
    }

    public final TrieNode getNode$runtime_release() {
        return this.node;
    }

    @Override // kotlin.collections.AbstractMap
    public int getSize() {
        return this.size;
    }

    @Override // kotlin.collections.AbstractMap
    public ImmutableCollection getValues() {
        return new PersistentHashMapValues(this);
    }

    @Override // kotlin.collections.AbstractMap, java.util.Map
    public PersistentHashMap put(Object obj, Object obj2) {
        TrieNode.ModificationResult modificationResultPut = this.node.put(obj != null ? obj.hashCode() : 0, obj, obj2, 0);
        return modificationResultPut == null ? this : new PersistentHashMap(modificationResultPut.getNode(), size() + modificationResultPut.getSizeDelta());
    }

    @Override // kotlin.collections.AbstractMap, java.util.Map
    public PersistentHashMap remove(Object obj) {
        TrieNode trieNodeRemove = this.node.remove(obj != null ? obj.hashCode() : 0, obj, 0);
        return this.node == trieNodeRemove ? this : trieNodeRemove == null ? Companion.emptyOf$runtime_release() : new PersistentHashMap(trieNodeRemove, size() - 1);
    }
}
