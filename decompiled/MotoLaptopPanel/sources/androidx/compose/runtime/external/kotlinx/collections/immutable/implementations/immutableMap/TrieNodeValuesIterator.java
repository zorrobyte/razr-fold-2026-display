package androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableMap;

import androidx.compose.runtime.external.kotlinx.collections.immutable.internal.CommonFunctionsKt;

/* JADX INFO: compiled from: PersistentHashMapContentIterators.kt */
/* JADX INFO: loaded from: classes.dex */
public final class TrieNodeValuesIterator extends TrieNodeBaseIterator {
    @Override // java.util.Iterator
    public Object next() {
        CommonFunctionsKt.m650assert(hasNextKey());
        setIndex(getIndex() + 2);
        return getBuffer()[getIndex() - 1];
    }
}
