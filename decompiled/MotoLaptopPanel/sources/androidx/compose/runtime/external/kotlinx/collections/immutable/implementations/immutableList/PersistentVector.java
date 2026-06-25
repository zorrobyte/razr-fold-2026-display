package androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableList;

import androidx.compose.runtime.PreconditionsKt;
import androidx.compose.runtime.external.kotlinx.collections.immutable.PersistentList;
import androidx.compose.runtime.external.kotlinx.collections.immutable.internal.CommonFunctionsKt;
import androidx.compose.runtime.external.kotlinx.collections.immutable.internal.ListImplementation;
import java.util.Arrays;
import java.util.ListIterator;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function1;
import kotlin.ranges.RangesKt;

/* JADX INFO: compiled from: PersistentVector.kt */
/* JADX INFO: loaded from: classes.dex */
public final class PersistentVector extends AbstractPersistentList implements PersistentList {
    private final Object[] root;
    private final int rootShift;
    private final int size;
    private final Object[] tail;

    public PersistentVector(Object[] objArr, Object[] objArr2, int i, int i2) {
        this.root = objArr;
        this.tail = objArr2;
        this.size = i;
        this.rootShift = i2;
        if (!(size() > 32)) {
            PreconditionsKt.throwIllegalArgumentException("Trie-based persistent vector should have at least 33 elements, got " + size());
        }
        CommonFunctionsKt.m650assert(size() - UtilsKt.rootSize(size()) <= RangesKt.coerceAtMost(objArr2.length, 32));
    }

    private final Object[] bufferFor(int i) {
        if (rootSize() <= i) {
            return this.tail;
        }
        Object[] objArr = this.root;
        for (int i2 = this.rootShift; i2 > 0; i2 -= 5) {
            Object[] objArr2 = objArr[UtilsKt.indexSegment(i, i2)];
            objArr2.getClass();
            objArr = objArr2;
        }
        return objArr;
    }

    private final Object[] insertIntoRoot(Object[] objArr, int i, int i2, Object obj, ObjectRef objectRef) {
        Object[] objArrCopyOf;
        int iIndexSegment = UtilsKt.indexSegment(i2, i);
        if (i == 0) {
            if (iIndexSegment == 0) {
                objArrCopyOf = new Object[32];
            } else {
                objArrCopyOf = Arrays.copyOf(objArr, 32);
                objArrCopyOf.getClass();
            }
            ArraysKt.copyInto(objArr, objArrCopyOf, iIndexSegment + 1, iIndexSegment, 31);
            objectRef.setValue(objArr[31]);
            objArrCopyOf[iIndexSegment] = obj;
            return objArrCopyOf;
        }
        Object[] objArrCopyOf2 = Arrays.copyOf(objArr, 32);
        objArrCopyOf2.getClass();
        int i3 = i - 5;
        Object obj2 = objArr[iIndexSegment];
        obj2.getClass();
        objArrCopyOf2[iIndexSegment] = insertIntoRoot((Object[]) obj2, i3, i2, obj, objectRef);
        while (true) {
            iIndexSegment++;
            if (iIndexSegment >= 32 || objArrCopyOf2[iIndexSegment] == null) {
                break;
            }
            Object obj3 = objArr[iIndexSegment];
            obj3.getClass();
            objArrCopyOf2[iIndexSegment] = insertIntoRoot((Object[]) obj3, i3, 0, objectRef.getValue(), objectRef);
        }
        return objArrCopyOf2;
    }

    private final PersistentVector insertIntoTail(Object[] objArr, int i, Object obj) {
        int size = size() - rootSize();
        Object[] objArrCopyOf = Arrays.copyOf(this.tail, 32);
        objArrCopyOf.getClass();
        if (size < 32) {
            ArraysKt.copyInto(this.tail, objArrCopyOf, i + 1, i, size);
            objArrCopyOf[i] = obj;
            return new PersistentVector(objArr, objArrCopyOf, size() + 1, this.rootShift);
        }
        Object[] objArr2 = this.tail;
        Object obj2 = objArr2[31];
        ArraysKt.copyInto(objArr2, objArrCopyOf, i + 1, i, size - 1);
        objArrCopyOf[i] = obj;
        return pushFilledTail(objArr, objArrCopyOf, UtilsKt.presizedBufferWith(obj2));
    }

    private final Object[] pullLastBuffer(Object[] objArr, int i, int i2, ObjectRef objectRef) {
        Object[] objArrPullLastBuffer;
        int iIndexSegment = UtilsKt.indexSegment(i2, i);
        if (i == 5) {
            objectRef.setValue(objArr[iIndexSegment]);
            objArrPullLastBuffer = null;
        } else {
            Object obj = objArr[iIndexSegment];
            obj.getClass();
            objArrPullLastBuffer = pullLastBuffer((Object[]) obj, i - 5, i2, objectRef);
        }
        if (objArrPullLastBuffer == null && iIndexSegment == 0) {
            return null;
        }
        Object[] objArrCopyOf = Arrays.copyOf(objArr, 32);
        objArrCopyOf.getClass();
        objArrCopyOf[iIndexSegment] = objArrPullLastBuffer;
        return objArrCopyOf;
    }

    private final PersistentList pullLastBufferFromRoot(Object[] objArr, int i, int i2) {
        if (i2 == 0) {
            if (objArr.length == 33) {
                objArr = Arrays.copyOf(objArr, 32);
                objArr.getClass();
            }
            return new SmallPersistentVector(objArr);
        }
        ObjectRef objectRef = new ObjectRef(null);
        Object[] objArrPullLastBuffer = pullLastBuffer(objArr, i2, i - 1, objectRef);
        objArrPullLastBuffer.getClass();
        Object value = objectRef.getValue();
        value.getClass();
        Object[] objArr2 = (Object[]) value;
        if (objArrPullLastBuffer[1] != null) {
            return new PersistentVector(objArrPullLastBuffer, objArr2, i, i2);
        }
        Object obj = objArrPullLastBuffer[0];
        obj.getClass();
        return new PersistentVector((Object[]) obj, objArr2, i, i2 - 5);
    }

    private final PersistentVector pushFilledTail(Object[] objArr, Object[] objArr2, Object[] objArr3) {
        int size = size() >> 5;
        int i = this.rootShift;
        if (size <= (1 << i)) {
            return new PersistentVector(pushTail(objArr, i, objArr2), objArr3, size() + 1, this.rootShift);
        }
        Object[] objArrPresizedBufferWith = UtilsKt.presizedBufferWith(objArr);
        int i2 = this.rootShift + 5;
        return new PersistentVector(pushTail(objArrPresizedBufferWith, i2, objArr2), objArr3, size() + 1, i2);
    }

    private final Object[] pushTail(Object[] objArr, int i, Object[] objArr2) {
        Object[] objArrCopyOf;
        int iIndexSegment = UtilsKt.indexSegment(size() - 1, i);
        if (objArr != null) {
            objArrCopyOf = Arrays.copyOf(objArr, 32);
            objArrCopyOf.getClass();
        } else {
            objArrCopyOf = new Object[32];
        }
        if (i == 5) {
            objArrCopyOf[iIndexSegment] = objArr2;
            return objArrCopyOf;
        }
        objArrCopyOf[iIndexSegment] = pushTail((Object[]) objArrCopyOf[iIndexSegment], i - 5, objArr2);
        return objArrCopyOf;
    }

    private final Object[] removeFromRootAt(Object[] objArr, int i, int i2, ObjectRef objectRef) {
        Object[] objArrCopyOf;
        int iIndexSegment = UtilsKt.indexSegment(i2, i);
        if (i == 0) {
            if (iIndexSegment == 0) {
                objArrCopyOf = new Object[32];
            } else {
                objArrCopyOf = Arrays.copyOf(objArr, 32);
                objArrCopyOf.getClass();
            }
            ArraysKt.copyInto(objArr, objArrCopyOf, iIndexSegment, iIndexSegment + 1, 32);
            objArrCopyOf[31] = objectRef.getValue();
            objectRef.setValue(objArr[iIndexSegment]);
            return objArrCopyOf;
        }
        int iIndexSegment2 = objArr[31] == null ? UtilsKt.indexSegment(rootSize() - 1, i) : 31;
        Object[] objArrCopyOf2 = Arrays.copyOf(objArr, 32);
        objArrCopyOf2.getClass();
        int i3 = i - 5;
        int i4 = iIndexSegment + 1;
        if (i4 <= iIndexSegment2) {
            while (true) {
                Object obj = objArrCopyOf2[iIndexSegment2];
                obj.getClass();
                objArrCopyOf2[iIndexSegment2] = removeFromRootAt((Object[]) obj, i3, 0, objectRef);
                if (iIndexSegment2 == i4) {
                    break;
                }
                iIndexSegment2--;
            }
        }
        Object obj2 = objArrCopyOf2[iIndexSegment];
        obj2.getClass();
        objArrCopyOf2[iIndexSegment] = removeFromRootAt((Object[]) obj2, i3, i2, objectRef);
        return objArrCopyOf2;
    }

    private final PersistentList removeFromTailAt(Object[] objArr, int i, int i2, int i3) {
        int size = size() - i;
        CommonFunctionsKt.m650assert(i3 < size);
        if (size == 1) {
            return pullLastBufferFromRoot(objArr, i, i2);
        }
        Object[] objArrCopyOf = Arrays.copyOf(this.tail, 32);
        objArrCopyOf.getClass();
        int i4 = size - 1;
        if (i3 < i4) {
            ArraysKt.copyInto(this.tail, objArrCopyOf, i3, i3 + 1, size);
        }
        objArrCopyOf[i4] = null;
        return new PersistentVector(objArr, objArrCopyOf, (i + size) - 1, i2);
    }

    private final int rootSize() {
        return UtilsKt.rootSize(size());
    }

    private final Object[] setInRoot(Object[] objArr, int i, int i2, Object obj) {
        int iIndexSegment = UtilsKt.indexSegment(i2, i);
        Object[] objArrCopyOf = Arrays.copyOf(objArr, 32);
        objArrCopyOf.getClass();
        if (i == 0) {
            objArrCopyOf[iIndexSegment] = obj;
            return objArrCopyOf;
        }
        Object obj2 = objArrCopyOf[iIndexSegment];
        obj2.getClass();
        objArrCopyOf[iIndexSegment] = setInRoot((Object[]) obj2, i - 5, i2, obj);
        return objArrCopyOf;
    }

    @Override // java.util.List, androidx.compose.runtime.external.kotlinx.collections.immutable.PersistentList
    public PersistentList add(int i, Object obj) {
        ListImplementation.checkPositionIndex$runtime_release(i, size());
        if (i == size()) {
            return add(obj);
        }
        int iRootSize = rootSize();
        if (i >= iRootSize) {
            return insertIntoTail(this.root, i - iRootSize, obj);
        }
        ObjectRef objectRef = new ObjectRef(null);
        return insertIntoTail(insertIntoRoot(this.root, this.rootShift, i, obj, objectRef), 0, objectRef.getValue());
    }

    @Override // java.util.Collection, java.util.List, androidx.compose.runtime.external.kotlinx.collections.immutable.PersistentList
    public PersistentList add(Object obj) {
        int size = size() - rootSize();
        if (size >= 32) {
            return pushFilledTail(this.root, this.tail, UtilsKt.presizedBufferWith(obj));
        }
        Object[] objArrCopyOf = Arrays.copyOf(this.tail, 32);
        objArrCopyOf.getClass();
        objArrCopyOf[size] = obj;
        return new PersistentVector(this.root, objArrCopyOf, size() + 1, this.rootShift);
    }

    @Override // androidx.compose.runtime.external.kotlinx.collections.immutable.PersistentList
    public PersistentVectorBuilder builder() {
        return new PersistentVectorBuilder(this, this.root, this.tail, this.rootShift);
    }

    @Override // kotlin.collections.AbstractList, java.util.List
    public Object get(int i) {
        ListImplementation.checkElementIndex$runtime_release(i, size());
        return bufferFor(i)[i & 31];
    }

    @Override // kotlin.collections.AbstractCollection
    public int getSize() {
        return this.size;
    }

    @Override // kotlin.collections.AbstractList, java.util.List
    public ListIterator listIterator(int i) {
        ListImplementation.checkPositionIndex$runtime_release(i, size());
        return new PersistentVectorIterator(this.root, this.tail, i, size(), (this.rootShift / 5) + 1);
    }

    @Override // androidx.compose.runtime.external.kotlinx.collections.immutable.PersistentList
    public PersistentList removeAll(Function1 function1) {
        PersistentVectorBuilder persistentVectorBuilderBuilder = builder();
        persistentVectorBuilderBuilder.removeAllWithPredicate(function1);
        return persistentVectorBuilderBuilder.build();
    }

    @Override // androidx.compose.runtime.external.kotlinx.collections.immutable.PersistentList
    public PersistentList removeAt(int i) {
        ListImplementation.checkElementIndex$runtime_release(i, size());
        int iRootSize = rootSize();
        return i >= iRootSize ? removeFromTailAt(this.root, iRootSize, this.rootShift, i - iRootSize) : removeFromTailAt(removeFromRootAt(this.root, this.rootShift, i, new ObjectRef(this.tail[0])), iRootSize, this.rootShift, 0);
    }

    @Override // kotlin.collections.AbstractList, java.util.List, androidx.compose.runtime.external.kotlinx.collections.immutable.PersistentList
    public PersistentList set(int i, Object obj) {
        ListImplementation.checkElementIndex$runtime_release(i, size());
        if (rootSize() > i) {
            return new PersistentVector(setInRoot(this.root, this.rootShift, i, obj), this.tail, size(), this.rootShift);
        }
        Object[] objArrCopyOf = Arrays.copyOf(this.tail, 32);
        objArrCopyOf.getClass();
        objArrCopyOf[i & 31] = obj;
        return new PersistentVector(this.root, objArrCopyOf, size(), this.rootShift);
    }
}
