package androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableMap;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;

/* JADX INFO: compiled from: PersistentHashMapBuilderContentIterators.kt */
/* JADX INFO: loaded from: classes.dex */
public class PersistentHashMapBuilderBaseIterator extends PersistentHashMapBaseIterator implements Iterator, KMappedMarker {
    private final PersistentHashMapBuilder builder;
    private int expectedModCount;
    private Object lastIteratedKey;
    private boolean nextWasInvoked;

    public PersistentHashMapBuilderBaseIterator(PersistentHashMapBuilder persistentHashMapBuilder, TrieNodeBaseIterator[] trieNodeBaseIteratorArr) {
        super(persistentHashMapBuilder.getNode$runtime_release(), trieNodeBaseIteratorArr);
        this.builder = persistentHashMapBuilder;
        this.expectedModCount = persistentHashMapBuilder.getModCount$runtime_release();
    }

    private final void checkForComodification() {
        if (this.builder.getModCount$runtime_release() != this.expectedModCount) {
            throw new ConcurrentModificationException();
        }
    }

    private final void checkNextWasInvoked() {
        if (!this.nextWasInvoked) {
            throw new IllegalStateException();
        }
    }

    private final void resetPath(int i, TrieNode trieNode, Object obj, int i2) {
        int i3 = i2 * 5;
        if (i3 > 30) {
            getPath()[i2].reset(trieNode.getBuffer$runtime_release(), trieNode.getBuffer$runtime_release().length, 0);
            while (!Intrinsics.areEqual(getPath()[i2].currentKey(), obj)) {
                getPath()[i2].moveToNextKey();
            }
            setPathLastIndex(i2);
            return;
        }
        int iIndexSegment = 1 << TrieNodeKt.indexSegment(i, i3);
        if (trieNode.hasEntryAt$runtime_release(iIndexSegment)) {
            getPath()[i2].reset(trieNode.getBuffer$runtime_release(), trieNode.entryCount$runtime_release() * 2, trieNode.entryKeyIndex$runtime_release(iIndexSegment));
            setPathLastIndex(i2);
        } else {
            int iNodeIndex$runtime_release = trieNode.nodeIndex$runtime_release(iIndexSegment);
            TrieNode trieNodeNodeAtIndex$runtime_release = trieNode.nodeAtIndex$runtime_release(iNodeIndex$runtime_release);
            getPath()[i2].reset(trieNode.getBuffer$runtime_release(), trieNode.entryCount$runtime_release() * 2, iNodeIndex$runtime_release);
            resetPath(i, trieNodeNodeAtIndex$runtime_release, obj, i2 + 1);
        }
    }

    @Override // androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableMap.PersistentHashMapBaseIterator, java.util.Iterator
    public Object next() {
        checkForComodification();
        this.lastIteratedKey = currentKey();
        this.nextWasInvoked = true;
        return super.next();
    }

    @Override // androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableMap.PersistentHashMapBaseIterator, java.util.Iterator
    public void remove() {
        checkNextWasInvoked();
        if (hasNext()) {
            Object objCurrentKey = currentKey();
            TypeIntrinsics.asMutableMap(this.builder).remove(this.lastIteratedKey);
            resetPath(objCurrentKey != null ? objCurrentKey.hashCode() : 0, this.builder.getNode$runtime_release(), objCurrentKey, 0);
        } else {
            TypeIntrinsics.asMutableMap(this.builder).remove(this.lastIteratedKey);
        }
        this.lastIteratedKey = null;
        this.nextWasInvoked = false;
        this.expectedModCount = this.builder.getModCount$runtime_release();
    }

    public final void setValue(Object obj, Object obj2) {
        if (this.builder.containsKey(obj)) {
            if (hasNext()) {
                Object objCurrentKey = currentKey();
                this.builder.put(obj, obj2);
                resetPath(objCurrentKey != null ? objCurrentKey.hashCode() : 0, this.builder.getNode$runtime_release(), objCurrentKey, 0);
            } else {
                this.builder.put(obj, obj2);
            }
            this.expectedModCount = this.builder.getModCount$runtime_release();
        }
    }
}
