package androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableMap;

import java.util.Iterator;
import java.util.NoSuchElementException;
import kotlin.jvm.internal.markers.KMappedMarker;

/* JADX INFO: compiled from: PersistentHashMapContentIterators.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class PersistentHashMapBaseIterator implements Iterator, KMappedMarker {
    private boolean hasNext = true;
    private final TrieNodeBaseIterator[] path;
    private int pathLastIndex;

    public PersistentHashMapBaseIterator(TrieNode trieNode, TrieNodeBaseIterator[] trieNodeBaseIteratorArr) {
        this.path = trieNodeBaseIteratorArr;
        trieNodeBaseIteratorArr[0].reset(trieNode.getBuffer$runtime_release(), trieNode.entryCount$runtime_release() * 2);
        this.pathLastIndex = 0;
        ensureNextEntryIsReady();
    }

    private final void checkHasNext() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
    }

    private final void ensureNextEntryIsReady() {
        if (this.path[this.pathLastIndex].hasNextKey()) {
            return;
        }
        for (int i = this.pathLastIndex; -1 < i; i--) {
            int iMoveToNextNodeWithData = moveToNextNodeWithData(i);
            if (iMoveToNextNodeWithData == -1 && this.path[i].hasNextNode()) {
                this.path[i].moveToNextNode();
                iMoveToNextNodeWithData = moveToNextNodeWithData(i);
            }
            if (iMoveToNextNodeWithData != -1) {
                this.pathLastIndex = iMoveToNextNodeWithData;
                return;
            }
            if (i > 0) {
                this.path[i - 1].moveToNextNode();
            }
            this.path[i].reset(TrieNode.Companion.getEMPTY$runtime_release().getBuffer$runtime_release(), 0);
        }
        this.hasNext = false;
    }

    private final int moveToNextNodeWithData(int i) {
        if (this.path[i].hasNextKey()) {
            return i;
        }
        if (!this.path[i].hasNextNode()) {
            return -1;
        }
        TrieNode trieNodeCurrentNode = this.path[i].currentNode();
        if (i == 6) {
            this.path[i + 1].reset(trieNodeCurrentNode.getBuffer$runtime_release(), trieNodeCurrentNode.getBuffer$runtime_release().length);
        } else {
            this.path[i + 1].reset(trieNodeCurrentNode.getBuffer$runtime_release(), trieNodeCurrentNode.entryCount$runtime_release() * 2);
        }
        return moveToNextNodeWithData(i + 1);
    }

    protected final Object currentKey() {
        checkHasNext();
        return this.path[this.pathLastIndex].currentKey();
    }

    protected final TrieNodeBaseIterator[] getPath() {
        return this.path;
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        return this.hasNext;
    }

    @Override // java.util.Iterator
    public Object next() {
        checkHasNext();
        Object next = this.path[this.pathLastIndex].next();
        ensureNextEntryIsReady();
        return next;
    }

    @Override // java.util.Iterator
    public void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    protected final void setPathLastIndex(int i) {
        this.pathLastIndex = i;
    }
}
