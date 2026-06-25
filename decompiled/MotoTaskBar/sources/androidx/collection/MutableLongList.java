package androidx.collection;

import androidx.collection.internal.RuntimeHelpersKt;
import java.util.Arrays;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: LongList.kt */
/* JADX INFO: loaded from: classes.dex */
public final class MutableLongList extends LongList {
    public MutableLongList(int i) {
        super(i, null);
    }

    public /* synthetic */ MutableLongList(int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 16 : i);
    }

    public final boolean add(long j) {
        ensureCapacity(this._size + 1);
        long[] jArr = this.content;
        int i = this._size;
        jArr[i] = j;
        this._size = i + 1;
        return true;
    }

    public final boolean addAll(int i, long[] jArr) {
        jArr.getClass();
        if (i < 0 || i > this._size) {
            RuntimeHelpersKt.throwIndexOutOfBoundsException("");
        }
        if (jArr.length == 0) {
            return false;
        }
        ensureCapacity(this._size + jArr.length);
        long[] jArr2 = this.content;
        int i2 = this._size;
        if (i != i2) {
            ArraysKt.copyInto(jArr2, jArr2, jArr.length + i, i, i2);
        }
        ArraysKt.copyInto$default(jArr, jArr2, i, 0, 0, 12, (Object) null);
        this._size += jArr.length;
        return true;
    }

    public final void clear() {
        this._size = 0;
    }

    public final void ensureCapacity(int i) {
        long[] jArr = this.content;
        if (jArr.length < i) {
            long[] jArrCopyOf = Arrays.copyOf(jArr, Math.max(i, (jArr.length * 3) / 2));
            jArrCopyOf.getClass();
            this.content = jArrCopyOf;
        }
    }

    public final long removeAt(int i) {
        if (i < 0 || i >= this._size) {
            RuntimeHelpersKt.throwIndexOutOfBoundsException("Index must be between 0 and size");
        }
        long[] jArr = this.content;
        long j = jArr[i];
        int i2 = this._size;
        if (i != i2 - 1) {
            ArraysKt.copyInto(jArr, jArr, i, i + 1, i2);
        }
        this._size--;
        return j;
    }

    public final void removeRange(int i, int i2) {
        int i3;
        if (i < 0 || i > (i3 = this._size) || i2 < 0 || i2 > i3) {
            RuntimeHelpersKt.throwIndexOutOfBoundsException("Index must be between 0 and size");
        }
        if (i2 < i) {
            RuntimeHelpersKt.throwIllegalArgumentException("The end index must be < start index");
        }
        if (i2 != i) {
            int i4 = this._size;
            if (i2 < i4) {
                long[] jArr = this.content;
                ArraysKt.copyInto(jArr, jArr, i, i2, i4);
            }
            this._size -= i2 - i;
        }
    }
}
