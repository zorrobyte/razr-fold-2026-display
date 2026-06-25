package androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableMap;

import androidx.compose.runtime.external.kotlinx.collections.immutable.internal.DeltaCounter;
import androidx.compose.runtime.external.kotlinx.collections.immutable.internal.MutabilityOwnership;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import kotlin.collections.AbstractMutableMap;
import kotlin.jvm.internal.markers.KMutableMap;

/* JADX INFO: compiled from: PersistentHashMapBuilder.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class PersistentHashMapBuilder extends AbstractMutableMap implements Map, KMutableMap {
    private PersistentHashMap map;
    private int modCount;
    private TrieNode node;
    private Object operationResult;
    private MutabilityOwnership ownership = new MutabilityOwnership();
    private int size;

    public PersistentHashMapBuilder(PersistentHashMap persistentHashMap) {
        this.map = persistentHashMap;
        this.node = this.map.getNode$runtime_release();
        this.size = this.map.size();
    }

    public abstract PersistentHashMap build();

    @Override // java.util.AbstractMap, java.util.Map
    public void clear() {
        TrieNode eMPTY$runtime_release = TrieNode.Companion.getEMPTY$runtime_release();
        eMPTY$runtime_release.getClass();
        this.node = eMPTY$runtime_release;
        setSize(0);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public boolean containsKey(Object obj) {
        return this.node.containsKey(obj != null ? obj.hashCode() : 0, obj, 0);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Object get(Object obj) {
        return this.node.get(obj != null ? obj.hashCode() : 0, obj, 0);
    }

    @Override // kotlin.collections.AbstractMutableMap
    public Set getEntries() {
        return new PersistentHashMapBuilderEntries(this);
    }

    @Override // kotlin.collections.AbstractMutableMap
    public Set getKeys() {
        return new PersistentHashMapBuilderKeys(this);
    }

    public final int getModCount$runtime_release() {
        return this.modCount;
    }

    public final TrieNode getNode$runtime_release() {
        return this.node;
    }

    public final MutabilityOwnership getOwnership() {
        return this.ownership;
    }

    @Override // kotlin.collections.AbstractMutableMap
    public int getSize() {
        return this.size;
    }

    @Override // kotlin.collections.AbstractMutableMap
    public Collection getValues() {
        return new PersistentHashMapBuilderValues(this);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Object put(Object obj, Object obj2) {
        this.operationResult = null;
        this.node = this.node.mutablePut(obj != null ? obj.hashCode() : 0, obj, obj2, 0, this);
        return this.operationResult;
    }

    @Override // java.util.AbstractMap, java.util.Map
    public void putAll(Map map) {
        PersistentHashMap persistentHashMapBuild = map instanceof PersistentHashMap ? (PersistentHashMap) map : null;
        if (persistentHashMapBuild == null) {
            PersistentHashMapBuilder persistentHashMapBuilder = map instanceof PersistentHashMapBuilder ? (PersistentHashMapBuilder) map : null;
            persistentHashMapBuild = persistentHashMapBuilder != null ? persistentHashMapBuilder.build() : null;
        }
        if (persistentHashMapBuild == null) {
            super.putAll(map);
            return;
        }
        DeltaCounter deltaCounter = new DeltaCounter(0, 1, null);
        int size = size();
        TrieNode trieNode = this.node;
        TrieNode node$runtime_release = persistentHashMapBuild.getNode$runtime_release();
        node$runtime_release.getClass();
        this.node = trieNode.mutablePutAll(node$runtime_release, 0, deltaCounter, this);
        int size2 = (persistentHashMapBuild.size() + size) - deltaCounter.getCount();
        if (size != size2) {
            setSize(size2);
        }
    }

    @Override // java.util.AbstractMap, java.util.Map
    public Object remove(Object obj) {
        this.operationResult = null;
        TrieNode trieNodeMutableRemove = this.node.mutableRemove(obj != null ? obj.hashCode() : 0, obj, 0, this);
        if (trieNodeMutableRemove == null) {
            trieNodeMutableRemove = TrieNode.Companion.getEMPTY$runtime_release();
            trieNodeMutableRemove.getClass();
        }
        this.node = trieNodeMutableRemove;
        return this.operationResult;
    }

    @Override // java.util.Map
    public final boolean remove(Object obj, Object obj2) {
        int size = size();
        TrieNode trieNodeMutableRemove = this.node.mutableRemove(obj != null ? obj.hashCode() : 0, obj, obj2, 0, this);
        if (trieNodeMutableRemove == null) {
            trieNodeMutableRemove = TrieNode.Companion.getEMPTY$runtime_release();
            trieNodeMutableRemove.getClass();
        }
        this.node = trieNodeMutableRemove;
        return size != size();
    }

    public final void setModCount$runtime_release(int i) {
        this.modCount = i;
    }

    public final void setOperationResult$runtime_release(Object obj) {
        this.operationResult = obj;
    }

    protected final void setOwnership(MutabilityOwnership mutabilityOwnership) {
        this.ownership = mutabilityOwnership;
    }

    public void setSize(int i) {
        this.size = i;
        this.modCount++;
    }
}
