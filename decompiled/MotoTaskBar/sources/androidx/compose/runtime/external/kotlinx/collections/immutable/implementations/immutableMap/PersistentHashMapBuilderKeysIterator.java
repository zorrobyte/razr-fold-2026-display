package androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableMap;

/* JADX INFO: compiled from: PersistentHashMapBuilderContentIterators.kt */
/* JADX INFO: loaded from: classes.dex */
public final class PersistentHashMapBuilderKeysIterator extends PersistentHashMapBuilderBaseIterator {
    /* JADX WARN: Illegal instructions before constructor call */
    public PersistentHashMapBuilderKeysIterator(PersistentHashMapBuilder persistentHashMapBuilder) {
        TrieNodeBaseIterator[] trieNodeBaseIteratorArr = new TrieNodeBaseIterator[8];
        for (int i = 0; i < 8; i++) {
            trieNodeBaseIteratorArr[i] = new TrieNodeKeysIterator();
        }
        super(persistentHashMapBuilder, trieNodeBaseIteratorArr);
    }
}
