package androidx.core.util;

import android.util.SparseArray;
import kotlin.collections.IntIterator;

/* JADX INFO: compiled from: SparseArray.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class SparseArrayKt {
    public static final IntIterator keyIterator(final SparseArray sparseArray) {
        return new IntIterator() { // from class: androidx.core.util.SparseArrayKt.keyIterator.1
            private int index;

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.index < sparseArray.size();
            }

            @Override // kotlin.collections.IntIterator
            public int nextInt() {
                SparseArray sparseArray2 = sparseArray;
                int i = this.index;
                this.index = i + 1;
                return sparseArray2.keyAt(i);
            }
        };
    }
}
