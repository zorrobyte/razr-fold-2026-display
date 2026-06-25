package androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableMap;

import androidx.compose.runtime.external.kotlinx.collections.immutable.internal.CommonFunctionsKt;
import java.util.Iterator;
import kotlin.jvm.internal.markers.KMappedMarker;

/* JADX INFO: compiled from: PersistentHashMapContentIterators.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class TrieNodeBaseIterator implements Iterator, KMappedMarker {
    private Object[] buffer = TrieNode.Companion.getEMPTY$runtime_release().getBuffer$runtime_release();
    private int dataSize;
    private int index;

    public final Object currentKey() {
        CommonFunctionsKt.m650assert(hasNextKey());
        return this.buffer[this.index];
    }

    public final TrieNode currentNode() {
        CommonFunctionsKt.m650assert(hasNextNode());
        Object obj = this.buffer[this.index];
        obj.getClass();
        return (TrieNode) obj;
    }

    protected final Object[] getBuffer() {
        return this.buffer;
    }

    protected final int getIndex() {
        return this.index;
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        return hasNextKey();
    }

    public final boolean hasNextKey() {
        return this.index < this.dataSize;
    }

    public final boolean hasNextNode() {
        CommonFunctionsKt.m650assert(this.index >= this.dataSize);
        return this.index < this.buffer.length;
    }

    public final void moveToNextKey() {
        CommonFunctionsKt.m650assert(hasNextKey());
        this.index += 2;
    }

    public final void moveToNextNode() {
        CommonFunctionsKt.m650assert(hasNextNode());
        this.index++;
    }

    @Override // java.util.Iterator
    public void remove() {
        throw new UnsupportedOperationException("Operation is not supported for read-only collection");
    }

    public final void reset(Object[] objArr, int i) {
        reset(objArr, i, 0);
    }

    public final void reset(Object[] objArr, int i, int i2) {
        this.buffer = objArr;
        this.dataSize = i;
        this.index = i2;
    }

    protected final void setIndex(int i) {
        this.index = i;
    }
}
