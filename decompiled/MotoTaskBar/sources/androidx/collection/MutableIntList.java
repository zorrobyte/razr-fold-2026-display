package androidx.collection;

import androidx.collection.internal.RuntimeHelpersKt;
import java.util.Arrays;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: IntList.kt */
/* JADX INFO: loaded from: classes.dex */
public final class MutableIntList extends IntList {
    public MutableIntList(int i) {
        super(i, null);
    }

    public /* synthetic */ MutableIntList(int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 16 : i);
    }

    public final boolean add(int i) {
        ensureCapacity(this._size + 1);
        int[] iArr = this.content;
        int i2 = this._size;
        iArr[i2] = i;
        this._size = i2 + 1;
        return true;
    }

    public final boolean addAll(int i, int[] iArr) {
        iArr.getClass();
        if (i < 0 || i > this._size) {
            RuntimeHelpersKt.throwIndexOutOfBoundsException("");
        }
        if (iArr.length == 0) {
            return false;
        }
        ensureCapacity(this._size + iArr.length);
        int[] iArr2 = this.content;
        int i2 = this._size;
        if (i != i2) {
            ArraysKt.copyInto(iArr2, iArr2, iArr.length + i, i, i2);
        }
        ArraysKt.copyInto$default(iArr, iArr2, i, 0, 0, 12, (Object) null);
        this._size += iArr.length;
        return true;
    }

    public final void ensureCapacity(int i) {
        int[] iArr = this.content;
        if (iArr.length < i) {
            int[] iArrCopyOf = Arrays.copyOf(iArr, Math.max(i, (iArr.length * 3) / 2));
            iArrCopyOf.getClass();
            this.content = iArrCopyOf;
        }
    }

    public final int removeAt(int i) {
        if (i < 0 || i >= this._size) {
            RuntimeHelpersKt.throwIndexOutOfBoundsException("Index must be between 0 and size");
        }
        int[] iArr = this.content;
        int i2 = iArr[i];
        int i3 = this._size;
        if (i != i3 - 1) {
            ArraysKt.copyInto(iArr, iArr, i, i + 1, i3);
        }
        this._size--;
        return i2;
    }

    public final int set(int i, int i2) {
        if (i < 0 || i >= this._size) {
            RuntimeHelpersKt.throwIndexOutOfBoundsException("Index must be between 0 and size");
        }
        int[] iArr = this.content;
        int i3 = iArr[i];
        iArr[i] = i2;
        return i3;
    }
}
