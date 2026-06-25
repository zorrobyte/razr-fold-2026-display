package androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableMap;

/* JADX INFO: compiled from: PersistentHashMapBuilderContentIterators.kt */
/* JADX INFO: loaded from: classes.dex */
public final class PersistentHashMapBuilderValuesIterator extends PersistentHashMapBuilderBaseIterator {
    /* JADX WARN: Illegal instructions before constructor call */
    public PersistentHashMapBuilderValuesIterator(PersistentHashMapBuilder persistentHashMapBuilder) {
        TrieNodeBaseIterator[] trieNodeBaseIteratorArr = new TrieNodeBaseIterator[8];
        for (int i = 0; i < 8; i++) {
            trieNodeBaseIteratorArr[i] = new TrieNodeValuesIterator();
        }
        super(persistentHashMapBuilder, trieNodeBaseIteratorArr);
    }
}
