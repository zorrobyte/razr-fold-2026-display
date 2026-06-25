package androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableMap;

/* JADX INFO: compiled from: PersistentHashMapContentIterators.kt */
/* JADX INFO: loaded from: classes.dex */
public final class PersistentHashMapEntriesIterator extends PersistentHashMapBaseIterator {
    /* JADX WARN: Illegal instructions before constructor call */
    public PersistentHashMapEntriesIterator(TrieNode trieNode) {
        TrieNodeBaseIterator[] trieNodeBaseIteratorArr = new TrieNodeBaseIterator[8];
        for (int i = 0; i < 8; i++) {
            trieNodeBaseIteratorArr[i] = new TrieNodeEntriesIterator();
        }
        super(trieNode, trieNodeBaseIteratorArr);
    }
}
