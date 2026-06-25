package androidx.collection;

import androidx.collection.internal.RuntimeHelpersKt;
import kotlin.collections.ArraysKt;

/* JADX INFO: compiled from: CircularArray.kt */
/* JADX INFO: loaded from: classes.dex */
public final class CircularArray {
    private int capacityBitmask;
    private Object[] elements;
    private int head;
    private int tail;

    public CircularArray(int i) {
        if (!(i >= 1)) {
            RuntimeHelpersKt.throwIllegalArgumentException("capacity must be >= 1");
        }
        if (!(i <= 1073741824)) {
            RuntimeHelpersKt.throwIllegalArgumentException("capacity must be <= 2^30");
        }
        i = Integer.bitCount(i) != 1 ? Integer.highestOneBit(i - 1) << 1 : i;
        this.capacityBitmask = i - 1;
        this.elements = new Object[i];
    }

    private final void doubleCapacity() {
        Object[] objArr = this.elements;
        int length = objArr.length;
        int i = this.head;
        int i2 = length - i;
        int i3 = length << 1;
        if (i3 < 0) {
            throw new RuntimeException("Max array capacity exceeded");
        }
        Object[] objArr2 = new Object[i3];
        ArraysKt.copyInto(objArr, objArr2, 0, i, length);
        ArraysKt.copyInto(this.elements, objArr2, i2, 0, this.head);
        this.elements = objArr2;
        this.head = 0;
        this.tail = length;
        this.capacityBitmask = i3 - 1;
    }

    public final void addFirst(Object obj) {
        int i = (this.head - 1) & this.capacityBitmask;
        this.head = i;
        this.elements[i] = obj;
        if (i == this.tail) {
            doubleCapacity();
        }
    }

    public final void addLast(Object obj) {
        Object[] objArr = this.elements;
        int i = this.tail;
        objArr[i] = obj;
        int i2 = this.capacityBitmask & (i + 1);
        this.tail = i2;
        if (i2 == this.head) {
            doubleCapacity();
        }
    }

    public final void clear() {
        removeFromStart(size());
    }

    public final Object get(int i) {
        if (i < 0 || i >= size()) {
            CollectionPlatformUtils collectionPlatformUtils = CollectionPlatformUtils.INSTANCE;
            throw new ArrayIndexOutOfBoundsException();
        }
        Object obj = this.elements[this.capacityBitmask & (this.head + i)];
        obj.getClass();
        return obj;
    }

    public final void removeFromEnd(int i) {
        if (i <= 0) {
            return;
        }
        if (i > size()) {
            CollectionPlatformUtils collectionPlatformUtils = CollectionPlatformUtils.INSTANCE;
            throw new ArrayIndexOutOfBoundsException();
        }
        int i2 = this.tail;
        int i3 = i < i2 ? i2 - i : 0;
        for (int i4 = i3; i4 < i2; i4++) {
            this.elements[i4] = null;
        }
        int i5 = this.tail;
        int i6 = i5 - i3;
        int i7 = i - i6;
        this.tail = i5 - i6;
        if (i7 > 0) {
            int length = this.elements.length;
            this.tail = length;
            int i8 = length - i7;
            for (int i9 = i8; i9 < length; i9++) {
                this.elements[i9] = null;
            }
            this.tail = i8;
        }
    }

    public final void removeFromStart(int i) {
        if (i <= 0) {
            return;
        }
        if (i > size()) {
            CollectionPlatformUtils collectionPlatformUtils = CollectionPlatformUtils.INSTANCE;
            throw new ArrayIndexOutOfBoundsException();
        }
        int length = this.elements.length;
        int i2 = this.head;
        if (i < length - i2) {
            length = i2 + i;
        }
        while (i2 < length) {
            this.elements[i2] = null;
            i2++;
        }
        int i3 = this.head;
        int i4 = length - i3;
        int i5 = i - i4;
        this.head = this.capacityBitmask & (i3 + i4);
        if (i5 > 0) {
            for (int i6 = 0; i6 < i5; i6++) {
                this.elements[i6] = null;
            }
            this.head = i5;
        }
    }

    public final int size() {
        return this.capacityBitmask & (this.tail - this.head);
    }
}
