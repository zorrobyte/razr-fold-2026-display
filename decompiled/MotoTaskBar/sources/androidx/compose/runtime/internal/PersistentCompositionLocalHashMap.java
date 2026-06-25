package androidx.compose.runtime.internal;

import androidx.compose.runtime.CompositionLocal;
import androidx.compose.runtime.CompositionLocalMapKt;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.ValueHolder;
import androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableMap.PersistentHashMap;
import androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableMap.PersistentHashMapBuilder;
import androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableMap.TrieNode;
import androidx.compose.runtime.external.kotlinx.collections.immutable.internal.MutabilityOwnership;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: PersistentCompositionLocalMap.kt */
/* JADX INFO: loaded from: classes.dex */
public final class PersistentCompositionLocalHashMap extends PersistentHashMap implements PersistentCompositionLocalMap {
    public static final Companion Companion = new Companion(null);
    private static final PersistentCompositionLocalHashMap Empty;

    /* JADX INFO: compiled from: PersistentCompositionLocalMap.kt */
    public final class Builder extends PersistentHashMapBuilder implements PersistentCompositionLocalMap.Builder {
        private PersistentCompositionLocalHashMap map;

        public Builder(PersistentCompositionLocalHashMap persistentCompositionLocalHashMap) {
            super(persistentCompositionLocalHashMap);
            this.map = persistentCompositionLocalHashMap;
        }

        @Override // androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableMap.PersistentHashMapBuilder
        public PersistentCompositionLocalHashMap build() {
            PersistentCompositionLocalHashMap persistentCompositionLocalHashMap;
            if (getNode$runtime_release() == this.map.getNode$runtime_release()) {
                persistentCompositionLocalHashMap = this.map;
            } else {
                setOwnership(new MutabilityOwnership());
                persistentCompositionLocalHashMap = new PersistentCompositionLocalHashMap(getNode$runtime_release(), size());
            }
            this.map = persistentCompositionLocalHashMap;
            return persistentCompositionLocalHashMap;
        }

        public /* bridge */ boolean containsKey(CompositionLocal compositionLocal) {
            return super.containsKey((Object) compositionLocal);
        }

        @Override // androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableMap.PersistentHashMapBuilder, java.util.AbstractMap, java.util.Map
        public final /* bridge */ boolean containsKey(Object obj) {
            if (obj instanceof CompositionLocal) {
                return containsKey((CompositionLocal) obj);
            }
            return false;
        }

        public /* bridge */ boolean containsValue(ValueHolder valueHolder) {
            return super.containsValue((Object) valueHolder);
        }

        @Override // java.util.AbstractMap, java.util.Map
        public final /* bridge */ boolean containsValue(Object obj) {
            if (obj instanceof ValueHolder) {
                return containsValue((ValueHolder) obj);
            }
            return false;
        }

        public /* bridge */ ValueHolder get(CompositionLocal compositionLocal) {
            return (ValueHolder) super.get((Object) compositionLocal);
        }

        @Override // androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableMap.PersistentHashMapBuilder, java.util.AbstractMap, java.util.Map
        public final /* bridge */ /* synthetic */ Object get(Object obj) {
            if (obj instanceof CompositionLocal) {
                return get((CompositionLocal) obj);
            }
            return null;
        }

        public /* bridge */ ValueHolder getOrDefault(CompositionLocal compositionLocal, ValueHolder valueHolder) {
            return (ValueHolder) super.getOrDefault((Object) compositionLocal, valueHolder);
        }

        @Override // java.util.Map
        public final /* bridge */ /* synthetic */ Object getOrDefault(Object obj, Object obj2) {
            return !(obj instanceof CompositionLocal) ? obj2 : getOrDefault((CompositionLocal) obj, (ValueHolder) obj2);
        }

        public /* bridge */ ValueHolder remove(CompositionLocal compositionLocal) {
            return (ValueHolder) super.remove((Object) compositionLocal);
        }

        @Override // androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableMap.PersistentHashMapBuilder, java.util.AbstractMap, java.util.Map
        public final /* bridge */ /* synthetic */ Object remove(Object obj) {
            if (obj instanceof CompositionLocal) {
                return remove((CompositionLocal) obj);
            }
            return null;
        }
    }

    /* JADX INFO: compiled from: PersistentCompositionLocalMap.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final PersistentCompositionLocalHashMap getEmpty() {
            return PersistentCompositionLocalHashMap.Empty;
        }
    }

    static {
        TrieNode eMPTY$runtime_release = TrieNode.Companion.getEMPTY$runtime_release();
        eMPTY$runtime_release.getClass();
        Empty = new PersistentCompositionLocalHashMap(eMPTY$runtime_release, 0);
    }

    public PersistentCompositionLocalHashMap(TrieNode trieNode, int i) {
        super(trieNode, i);
    }

    @Override // androidx.compose.runtime.PersistentCompositionLocalMap
    public Builder builder() {
        return new Builder(this);
    }

    public /* bridge */ boolean containsKey(CompositionLocal compositionLocal) {
        return super.containsKey((Object) compositionLocal);
    }

    @Override // androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableMap.PersistentHashMap, java.util.Map
    public final /* bridge */ boolean containsKey(Object obj) {
        if (obj instanceof CompositionLocal) {
            return containsKey((CompositionLocal) obj);
        }
        return false;
    }

    public /* bridge */ boolean containsValue(ValueHolder valueHolder) {
        return super.containsValue((Object) valueHolder);
    }

    @Override // kotlin.collections.AbstractMap, java.util.Map
    public final /* bridge */ boolean containsValue(Object obj) {
        if (obj instanceof ValueHolder) {
            return containsValue((ValueHolder) obj);
        }
        return false;
    }

    @Override // androidx.compose.runtime.CompositionLocalMap
    public /* bridge */ ValueHolder get(CompositionLocal compositionLocal) {
        return (ValueHolder) super.get((Object) compositionLocal);
    }

    @Override // androidx.compose.runtime.CompositionLocalMap
    public Object get(CompositionLocal compositionLocal) {
        return CompositionLocalMapKt.read(this, compositionLocal);
    }

    @Override // androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableMap.PersistentHashMap, java.util.Map
    public final /* bridge */ /* synthetic */ Object get(Object obj) {
        if (obj instanceof CompositionLocal) {
            return get((CompositionLocal) obj);
        }
        return null;
    }

    public /* bridge */ ValueHolder getOrDefault(CompositionLocal compositionLocal, ValueHolder valueHolder) {
        return (ValueHolder) super.getOrDefault((Object) compositionLocal, valueHolder);
    }

    @Override // java.util.Map
    public final /* bridge */ /* synthetic */ Object getOrDefault(Object obj, Object obj2) {
        return !(obj instanceof CompositionLocal) ? obj2 : getOrDefault((CompositionLocal) obj, (ValueHolder) obj2);
    }

    @Override // androidx.compose.runtime.PersistentCompositionLocalMap
    public PersistentCompositionLocalMap putValue(CompositionLocal compositionLocal, ValueHolder valueHolder) {
        TrieNode.ModificationResult modificationResultPut = getNode$runtime_release().put(compositionLocal.hashCode(), compositionLocal, valueHolder, 0);
        return modificationResultPut == null ? this : new PersistentCompositionLocalHashMap(modificationResultPut.getNode(), size() + modificationResultPut.getSizeDelta());
    }
}
