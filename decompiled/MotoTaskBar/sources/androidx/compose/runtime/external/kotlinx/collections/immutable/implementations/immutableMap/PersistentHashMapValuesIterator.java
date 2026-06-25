package androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableMap;

/* JADX INFO: compiled from: PersistentHashMapContentIterators.kt */
/* JADX INFO: loaded from: classes.dex */
public final class PersistentHashMapValuesIterator extends PersistentHashMapBaseIterator {
    /* JADX WARN: Illegal instructions before constructor call */
    public PersistentHashMapValuesIterator(TrieNode trieNode) {
        TrieNodeBaseIterator[] trieNodeBaseIteratorArr = new TrieNodeBaseIterator[8];
        for (int i = 0; i < 8; i++) {
            trieNodeBaseIteratorArr[i] = new TrieNodeValuesIterator();
        }
        super(trieNode, trieNodeBaseIteratorArr);
    }
}
