package androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableList;

import androidx.compose.runtime.PreconditionsKt;
import androidx.compose.runtime.external.kotlinx.collections.immutable.PersistentList;
import androidx.compose.runtime.external.kotlinx.collections.immutable.internal.CommonFunctionsKt;
import androidx.compose.runtime.external.kotlinx.collections.immutable.internal.ListImplementation;
import androidx.compose.runtime.external.kotlinx.collections.immutable.internal.MutabilityOwnership;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import kotlin.collections.AbstractMutableList;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.ArrayIteratorKt;
import kotlin.ranges.RangesKt;

/* JADX INFO: compiled from: PersistentVectorBuilder.kt */
/* JADX INFO: loaded from: classes.dex */
public final class PersistentVectorBuilder extends AbstractMutableList implements PersistentList.Builder {
    private MutabilityOwnership ownership = new MutabilityOwnership();
    private Object[] root;
    private int rootShift;
    private int size;
    private Object[] tail;
    private PersistentList vector;
    private Object[] vectorRoot;
    private Object[] vectorTail;

    public PersistentVectorBuilder(PersistentList persistentList, Object[] objArr, Object[] objArr2, int i) {
        this.vector = persistentList;
        this.vectorRoot = objArr;
        this.vectorTail = objArr2;
        this.rootShift = i;
        this.root = this.vectorRoot;
        this.tail = this.vectorTail;
        this.size = this.vector.size();
    }

    private final Object[] bufferFor(int i) {
        if (rootSize() <= i) {
            return this.tail;
        }
        Object[] objArr = this.root;
        objArr.getClass();
        for (int i2 = this.rootShift; i2 > 0; i2 -= 5) {
            Object[] objArr2 = objArr[UtilsKt.indexSegment(i, i2)];
            objArr2.getClass();
            objArr = objArr2;
        }
        return objArr;
    }

    private final Object[] copyToBuffer(Object[] objArr, int i, Iterator it) {
        while (i < 32 && it.hasNext()) {
            objArr[i] = it.next();
            i++;
        }
        return objArr;
    }

    private final void insertIntoRoot(Collection collection, int i, int i2, Object[][] objArr, int i3, Object[] objArr2) {
        Object[] objArr3;
        if (this.root == null) {
            throw new IllegalStateException("root is null");
        }
        int i4 = i >> 5;
        Object[] objArrShiftLeafBuffers = shiftLeafBuffers(i4, i2, objArr, i3, objArr2);
        int iRootSize = i3 - (((rootSize() >> 5) - 1) - i4);
        if (iRootSize < i3) {
            Object[] objArr4 = objArr[iRootSize];
            objArr4.getClass();
            objArr3 = objArr4;
        } else {
            objArr3 = objArr2;
        }
        splitToBuffers(collection, i, objArrShiftLeafBuffers, 32, objArr, iRootSize, objArr3);
    }

    private final Object[] insertIntoRoot(Object[] objArr, int i, int i2, Object obj, ObjectRef objectRef) {
        Object obj2;
        int iIndexSegment = UtilsKt.indexSegment(i2, i);
        if (i == 0) {
            objectRef.setValue(objArr[31]);
            Object[] objArrCopyInto = ArraysKt.copyInto(objArr, makeMutable(objArr), iIndexSegment + 1, iIndexSegment, 31);
            objArrCopyInto[iIndexSegment] = obj;
            return objArrCopyInto;
        }
        Object[] objArrMakeMutable = makeMutable(objArr);
        int i3 = i - 5;
        Object obj3 = objArrMakeMutable[iIndexSegment];
        obj3.getClass();
        objArrMakeMutable[iIndexSegment] = insertIntoRoot((Object[]) obj3, i3, i2, obj, objectRef);
        while (true) {
            iIndexSegment++;
            if (iIndexSegment >= 32 || (obj2 = objArrMakeMutable[iIndexSegment]) == null) {
                break;
            }
            obj2.getClass();
            objArrMakeMutable[iIndexSegment] = insertIntoRoot((Object[]) obj2, i3, 0, objectRef.getValue(), objectRef);
        }
        return objArrMakeMutable;
    }

    private final void insertIntoTail(Object[] objArr, int i, Object obj) {
        int iTailSize = tailSize();
        Object[] objArrMakeMutable = makeMutable(this.tail);
        if (iTailSize < 32) {
            ArraysKt.copyInto(this.tail, objArrMakeMutable, i + 1, i, iTailSize);
            objArrMakeMutable[i] = obj;
            this.root = objArr;
            this.tail = objArrMakeMutable;
            this.size = size() + 1;
            return;
        }
        Object[] objArr2 = this.tail;
        Object obj2 = objArr2[31];
        ArraysKt.copyInto(objArr2, objArrMakeMutable, i + 1, i, 31);
        objArrMakeMutable[i] = obj;
        pushFilledTail(objArr, objArrMakeMutable, mutableBufferWith(obj2));
    }

    private final boolean isMutable(Object[] objArr) {
        return objArr.length == 33 && objArr[32] == this.ownership;
    }

    private final ListIterator leafBufferIterator(int i) {
        Object[] objArr = this.root;
        if (objArr == null) {
            throw new IllegalStateException("Invalid root");
        }
        int iRootSize = rootSize() >> 5;
        ListImplementation.checkPositionIndex$runtime_release(i, iRootSize);
        int i2 = this.rootShift;
        return i2 == 0 ? new SingleElementListIterator(objArr, i) : new TrieIterator(objArr, i, iRootSize, i2 / 5);
    }

    private final Object[] makeMutable(Object[] objArr) {
        return objArr == null ? mutableBuffer() : isMutable(objArr) ? objArr : ArraysKt.copyInto$default(objArr, mutableBuffer(), 0, 0, RangesKt.coerceAtMost(objArr.length, 32), 6, (Object) null);
    }

    private final Object[] makeMutableShiftingRight(Object[] objArr, int i) {
        return isMutable(objArr) ? ArraysKt.copyInto(objArr, objArr, i, 0, 32 - i) : ArraysKt.copyInto(objArr, mutableBuffer(), i, 0, 32 - i);
    }

    private final Object[] mutableBuffer() {
        Object[] objArr = new Object[33];
        objArr[32] = this.ownership;
        return objArr;
    }

    private final Object[] mutableBufferWith(Object obj) {
        Object[] objArr = new Object[33];
        objArr[0] = obj;
        objArr[32] = this.ownership;
        return objArr;
    }

    private final Object[] nullifyAfter(Object[] objArr, int i, int i2) {
        if (!(i2 >= 0)) {
            PreconditionsKt.throwIllegalArgumentException("shift should be positive");
        }
        if (i2 == 0) {
            return objArr;
        }
        int iIndexSegment = UtilsKt.indexSegment(i, i2);
        Object obj = objArr[iIndexSegment];
        obj.getClass();
        Object objNullifyAfter = nullifyAfter((Object[]) obj, i, i2 - 5);
        if (iIndexSegment < 31) {
            int i3 = iIndexSegment + 1;
            if (objArr[i3] != null) {
                if (isMutable(objArr)) {
                    ArraysKt.fill(objArr, (Object) null, i3, 32);
                }
                objArr = ArraysKt.copyInto(objArr, mutableBuffer(), 0, 0, i3);
            }
        }
        if (objNullifyAfter == objArr[iIndexSegment]) {
            return objArr;
        }
        Object[] objArrMakeMutable = makeMutable(objArr);
        objArrMakeMutable[iIndexSegment] = objNullifyAfter;
        return objArrMakeMutable;
    }

    private final Object[] pullLastBuffer(Object[] objArr, int i, int i2, ObjectRef objectRef) {
        Object[] objArrPullLastBuffer;
        int iIndexSegment = UtilsKt.indexSegment(i2 - 1, i);
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
        Object[] objArrMakeMutable = makeMutable(objArr);
        objArrMakeMutable[iIndexSegment] = objArrPullLastBuffer;
        return objArrMakeMutable;
    }

    private final void pullLastBufferFromRoot(Object[] objArr, int i, int i2) {
        if (i2 == 0) {
            this.root = null;
            if (objArr == null) {
                objArr = new Object[0];
            }
            this.tail = objArr;
            this.size = i;
            this.rootShift = i2;
            return;
        }
        ObjectRef objectRef = new ObjectRef(null);
        objArr.getClass();
        Object[] objArrPullLastBuffer = pullLastBuffer(objArr, i2, i, objectRef);
        objArrPullLastBuffer.getClass();
        Object value = objectRef.getValue();
        value.getClass();
        this.tail = (Object[]) value;
        this.size = i;
        if (objArrPullLastBuffer[1] == null) {
            this.root = (Object[]) objArrPullLastBuffer[0];
            this.rootShift = i2 - 5;
        } else {
            this.root = objArrPullLastBuffer;
            this.rootShift = i2;
        }
    }

    private final Object[] pushBuffers(Object[] objArr, int i, int i2, Iterator it) {
        if (!it.hasNext()) {
            PreconditionsKt.throwIllegalArgumentException("invalid buffersIterator");
        }
        if (!(i2 >= 0)) {
            PreconditionsKt.throwIllegalArgumentException("negative shift");
        }
        if (i2 == 0) {
            return (Object[]) it.next();
        }
        Object[] objArrMakeMutable = makeMutable(objArr);
        int iIndexSegment = UtilsKt.indexSegment(i, i2);
        int i3 = i2 - 5;
        objArrMakeMutable[iIndexSegment] = pushBuffers((Object[]) objArrMakeMutable[iIndexSegment], i, i3, it);
        while (true) {
            iIndexSegment++;
            if (iIndexSegment >= 32 || !it.hasNext()) {
                break;
            }
            objArrMakeMutable[iIndexSegment] = pushBuffers((Object[]) objArrMakeMutable[iIndexSegment], 0, i3, it);
        }
        return objArrMakeMutable;
    }

    private final Object[] pushBuffersIncreasingHeightIfNeeded(Object[] objArr, int i, Object[][] objArr2) {
        Iterator it = ArrayIteratorKt.iterator(objArr2);
        int i2 = i >> 5;
        int i3 = this.rootShift;
        Object[] objArrPushBuffers = i2 < (1 << i3) ? pushBuffers(objArr, i, i3, it) : makeMutable(objArr);
        while (it.hasNext()) {
            this.rootShift += 5;
            objArrPushBuffers = mutableBufferWith(objArrPushBuffers);
            int i4 = this.rootShift;
            pushBuffers(objArrPushBuffers, 1 << i4, i4, it);
        }
        return objArrPushBuffers;
    }

    private final void pushFilledTail(Object[] objArr, Object[] objArr2, Object[] objArr3) {
        int size = size() >> 5;
        int i = this.rootShift;
        if (size > (1 << i)) {
            this.root = pushTail(mutableBufferWith(objArr), objArr2, this.rootShift + 5);
            this.tail = objArr3;
            this.rootShift += 5;
            this.size = size() + 1;
            return;
        }
        if (objArr == null) {
            this.root = objArr2;
            this.tail = objArr3;
            this.size = size() + 1;
        } else {
            this.root = pushTail(objArr, objArr2, i);
            this.tail = objArr3;
            this.size = size() + 1;
        }
    }

    private final Object[] pushTail(Object[] objArr, Object[] objArr2, int i) {
        int iIndexSegment = UtilsKt.indexSegment(size() - 1, i);
        Object[] objArrMakeMutable = makeMutable(objArr);
        if (i == 5) {
            objArrMakeMutable[iIndexSegment] = objArr2;
            return objArrMakeMutable;
        }
        objArrMakeMutable[iIndexSegment] = pushTail((Object[]) objArrMakeMutable[iIndexSegment], objArr2, i - 5);
        return objArrMakeMutable;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final int recyclableRemoveAll(Function1 function1, Object[] objArr, int i, int i2, ObjectRef objectRef, List list, List list2) {
        if (isMutable(objArr)) {
            list.add(objArr);
        }
        Object value = objectRef.getValue();
        value.getClass();
        Object[] objArr2 = (Object[]) value;
        Object[] objArrMutableBuffer = objArr2;
        for (int i3 = 0; i3 < i; i3++) {
            Object obj = objArr[i3];
            if (!((Boolean) function1.invoke(obj)).booleanValue()) {
                if (i2 == 32) {
                    objArrMutableBuffer = !list.isEmpty() ? (Object[]) list.remove(list.size() - 1) : mutableBuffer();
                    i2 = 0;
                }
                objArrMutableBuffer[i2] = obj;
                i2++;
            }
        }
        objectRef.setValue(objArrMutableBuffer);
        if (objArr2 != objectRef.getValue()) {
            list2.add(objArr2);
        }
        return i2;
    }

    private final int removeAll(Function1 function1, Object[] objArr, int i, ObjectRef objectRef) {
        Object[] objArrMakeMutable = objArr;
        int i2 = i;
        boolean z = false;
        for (int i3 = 0; i3 < i; i3++) {
            Object obj = objArr[i3];
            if (((Boolean) function1.invoke(obj)).booleanValue()) {
                if (!z) {
                    objArrMakeMutable = makeMutable(objArr);
                    z = true;
                    i2 = i3;
                }
            } else if (z) {
                objArrMakeMutable[i2] = obj;
                i2++;
            }
        }
        objectRef.setValue(objArrMakeMutable);
        return i2;
    }

    private final boolean removeAll(Function1 function1) {
        Object[] objArrPushBuffers;
        int iTailSize = tailSize();
        ObjectRef objectRef = new ObjectRef(null);
        if (this.root == null) {
            return removeAllFromTail(function1, iTailSize, objectRef) != iTailSize;
        }
        ListIterator listIteratorLeafBufferIterator = leafBufferIterator(0);
        int iRemoveAll = 32;
        while (iRemoveAll == 32 && listIteratorLeafBufferIterator.hasNext()) {
            iRemoveAll = removeAll(function1, (Object[]) listIteratorLeafBufferIterator.next(), 32, objectRef);
        }
        if (iRemoveAll == 32) {
            CommonFunctionsKt.m650assert(!listIteratorLeafBufferIterator.hasNext());
            int iRemoveAllFromTail = removeAllFromTail(function1, iTailSize, objectRef);
            if (iRemoveAllFromTail == 0) {
                pullLastBufferFromRoot(this.root, size(), this.rootShift);
            }
            return iRemoveAllFromTail != iTailSize;
        }
        int iPreviousIndex = listIteratorLeafBufferIterator.previousIndex() << 5;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        int iRecyclableRemoveAll = iRemoveAll;
        while (listIteratorLeafBufferIterator.hasNext()) {
            iRecyclableRemoveAll = recyclableRemoveAll(function1, (Object[]) listIteratorLeafBufferIterator.next(), 32, iRecyclableRemoveAll, objectRef, arrayList2, arrayList);
        }
        int iRecyclableRemoveAll2 = recyclableRemoveAll(function1, this.tail, iTailSize, iRecyclableRemoveAll, objectRef, arrayList2, arrayList);
        Object value = objectRef.getValue();
        value.getClass();
        Object[] objArr = (Object[]) value;
        ArraysKt.fill(objArr, (Object) null, iRecyclableRemoveAll2, 32);
        if (arrayList.isEmpty()) {
            objArrPushBuffers = this.root;
            objArrPushBuffers.getClass();
        } else {
            objArrPushBuffers = pushBuffers(this.root, iPreviousIndex, this.rootShift, arrayList.iterator());
        }
        int size = iPreviousIndex + (arrayList.size() << 5);
        this.root = retainFirst(objArrPushBuffers, size);
        this.tail = objArr;
        this.size = size + iRecyclableRemoveAll2;
        return true;
    }

    private final int removeAllFromTail(Function1 function1, int i, ObjectRef objectRef) {
        int iRemoveAll = removeAll(function1, this.tail, i, objectRef);
        if (iRemoveAll == i) {
            CommonFunctionsKt.m650assert(objectRef.getValue() == this.tail);
            return i;
        }
        Object value = objectRef.getValue();
        value.getClass();
        Object[] objArr = (Object[]) value;
        ArraysKt.fill(objArr, (Object) null, iRemoveAll, i);
        this.tail = objArr;
        this.size = size() - (i - iRemoveAll);
        return iRemoveAll;
    }

    private final Object[] removeFromRootAt(Object[] objArr, int i, int i2, ObjectRef objectRef) {
        int iIndexSegment = UtilsKt.indexSegment(i2, i);
        if (i == 0) {
            Object obj = objArr[iIndexSegment];
            Object[] objArrCopyInto = ArraysKt.copyInto(objArr, makeMutable(objArr), iIndexSegment, iIndexSegment + 1, 32);
            objArrCopyInto[31] = objectRef.getValue();
            objectRef.setValue(obj);
            return objArrCopyInto;
        }
        int iIndexSegment2 = objArr[31] == null ? UtilsKt.indexSegment(rootSize() - 1, i) : 31;
        Object[] objArrMakeMutable = makeMutable(objArr);
        int i3 = i - 5;
        int i4 = iIndexSegment + 1;
        if (i4 <= iIndexSegment2) {
            while (true) {
                Object obj2 = objArrMakeMutable[iIndexSegment2];
                obj2.getClass();
                objArrMakeMutable[iIndexSegment2] = removeFromRootAt((Object[]) obj2, i3, 0, objectRef);
                if (iIndexSegment2 == i4) {
                    break;
                }
                iIndexSegment2--;
            }
        }
        Object obj3 = objArrMakeMutable[iIndexSegment];
        obj3.getClass();
        objArrMakeMutable[iIndexSegment] = removeFromRootAt((Object[]) obj3, i3, i2, objectRef);
        return objArrMakeMutable;
    }

    private final Object removeFromTailAt(Object[] objArr, int i, int i2, int i3) {
        int size = size() - i;
        CommonFunctionsKt.m650assert(i3 < size);
        if (size == 1) {
            Object obj = this.tail[0];
            pullLastBufferFromRoot(objArr, i, i2);
            return obj;
        }
        Object[] objArr2 = this.tail;
        Object obj2 = objArr2[i3];
        Object[] objArrCopyInto = ArraysKt.copyInto(objArr2, makeMutable(objArr2), i3, i3 + 1, size);
        objArrCopyInto[size - 1] = null;
        this.root = objArr;
        this.tail = objArrCopyInto;
        this.size = (i + size) - 1;
        this.rootShift = i2;
        return obj2;
    }

    private final Object[] retainFirst(Object[] objArr, int i) {
        if (!((i & 31) == 0)) {
            PreconditionsKt.throwIllegalArgumentException("invalid size");
        }
        if (i == 0) {
            this.rootShift = 0;
            return null;
        }
        int i2 = i - 1;
        while (true) {
            int i3 = this.rootShift;
            if ((i2 >> i3) != 0) {
                return nullifyAfter(objArr, i2, i3);
            }
            this.rootShift = i3 - 5;
            Object[] objArr2 = objArr[0];
            objArr2.getClass();
            objArr = objArr2;
        }
    }

    private final int rootSize() {
        if (size() <= 32) {
            return 0;
        }
        return UtilsKt.rootSize(size());
    }

    private final Object[] setInRoot(Object[] objArr, int i, int i2, Object obj, ObjectRef objectRef) {
        int iIndexSegment = UtilsKt.indexSegment(i2, i);
        Object[] objArrMakeMutable = makeMutable(objArr);
        if (i != 0) {
            Object obj2 = objArrMakeMutable[iIndexSegment];
            obj2.getClass();
            objArrMakeMutable[iIndexSegment] = setInRoot((Object[]) obj2, i - 5, i2, obj, objectRef);
            return objArrMakeMutable;
        }
        if (objArrMakeMutable != objArr) {
            ((AbstractList) this).modCount++;
        }
        objectRef.setValue(objArrMakeMutable[iIndexSegment]);
        objArrMakeMutable[iIndexSegment] = obj;
        return objArrMakeMutable;
    }

    private final Object[] shiftLeafBuffers(int i, int i2, Object[][] objArr, int i3, Object[] objArr2) {
        if (this.root == null) {
            throw new IllegalStateException("root is null");
        }
        ListIterator listIteratorLeafBufferIterator = leafBufferIterator(rootSize() >> 5);
        while (listIteratorLeafBufferIterator.previousIndex() != i) {
            Object[] objArr3 = (Object[]) listIteratorLeafBufferIterator.previous();
            ArraysKt.copyInto(objArr3, objArr2, 0, 32 - i2, 32);
            objArr2 = makeMutableShiftingRight(objArr3, i2);
            i3--;
            objArr[i3] = objArr2;
        }
        return (Object[]) listIteratorLeafBufferIterator.previous();
    }

    private final void splitToBuffers(Collection collection, int i, Object[] objArr, int i2, Object[][] objArr2, int i3, Object[] objArr3) {
        Object[] objArrMutableBuffer;
        if (!(i3 >= 1)) {
            PreconditionsKt.throwIllegalArgumentException("requires at least one nullBuffer");
        }
        Object[] objArrMakeMutable = makeMutable(objArr);
        objArr2[0] = objArrMakeMutable;
        int i4 = i & 31;
        int size = ((i + collection.size()) - 1) & 31;
        int i5 = (i2 - i4) + size;
        if (i5 < 32) {
            ArraysKt.copyInto(objArrMakeMutable, objArr3, size + 1, i4, i2);
        } else {
            int i6 = i5 - 31;
            if (i3 == 1) {
                objArrMutableBuffer = objArrMakeMutable;
            } else {
                objArrMutableBuffer = mutableBuffer();
                i3--;
                objArr2[i3] = objArrMutableBuffer;
            }
            int i7 = i2 - i6;
            ArraysKt.copyInto(objArrMakeMutable, objArr3, 0, i7, i2);
            ArraysKt.copyInto(objArrMakeMutable, objArrMutableBuffer, size + 1, i4, i7);
            objArr3 = objArrMutableBuffer;
        }
        Iterator it = collection.iterator();
        copyToBuffer(objArrMakeMutable, i4, it);
        for (int i8 = 1; i8 < i3; i8++) {
            objArr2[i8] = copyToBuffer(mutableBuffer(), 0, it);
        }
        copyToBuffer(objArr3, 0, it);
    }

    private final int tailSize() {
        return tailSize(size());
    }

    private final int tailSize(int i) {
        return i <= 32 ? i : i - UtilsKt.rootSize(i);
    }

    @Override // java.util.AbstractList, java.util.List
    public void add(int i, Object obj) {
        ListImplementation.checkPositionIndex$runtime_release(i, size());
        if (i == size()) {
            add(obj);
            return;
        }
        ((AbstractList) this).modCount++;
        int iRootSize = rootSize();
        if (i >= iRootSize) {
            insertIntoTail(this.root, i - iRootSize, obj);
            return;
        }
        ObjectRef objectRef = new ObjectRef(null);
        Object[] objArr = this.root;
        objArr.getClass();
        insertIntoTail(insertIntoRoot(objArr, this.rootShift, i, obj, objectRef), 0, objectRef.getValue());
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean add(Object obj) {
        ((AbstractList) this).modCount++;
        int iTailSize = tailSize();
        if (iTailSize < 32) {
            Object[] objArrMakeMutable = makeMutable(this.tail);
            objArrMakeMutable[iTailSize] = obj;
            this.tail = objArrMakeMutable;
            this.size = size() + 1;
        } else {
            pushFilledTail(this.root, this.tail, mutableBufferWith(obj));
        }
        return true;
    }

    @Override // java.util.AbstractList, java.util.List
    public boolean addAll(int i, Collection collection) {
        PersistentVectorBuilder persistentVectorBuilder;
        Collection collection2;
        Object[] objArrCopyInto;
        Object[][] objArr;
        ListImplementation.checkPositionIndex$runtime_release(i, size());
        if (i == size()) {
            return addAll(collection);
        }
        if (collection.isEmpty()) {
            return false;
        }
        ((AbstractList) this).modCount++;
        int i2 = (i >> 5) << 5;
        int size = (((size() - i2) + collection.size()) - 1) / 32;
        if (size == 0) {
            CommonFunctionsKt.m650assert(i >= rootSize());
            int i3 = i & 31;
            int size2 = ((i + collection.size()) - 1) & 31;
            Object[] objArr2 = this.tail;
            Object[] objArrCopyInto2 = ArraysKt.copyInto(objArr2, makeMutable(objArr2), size2 + 1, i3, tailSize());
            copyToBuffer(objArrCopyInto2, i3, collection.iterator());
            this.tail = objArrCopyInto2;
            this.size = size() + collection.size();
            return true;
        }
        Object[][] objArr3 = new Object[size][];
        int iTailSize = tailSize();
        int iTailSize2 = tailSize(size() + collection.size());
        if (i >= rootSize()) {
            objArrCopyInto = mutableBuffer();
            objArr = objArr3;
            persistentVectorBuilder = this;
            collection2 = collection;
            persistentVectorBuilder.splitToBuffers(collection2, i, this.tail, iTailSize, objArr, size, objArrCopyInto);
        } else {
            persistentVectorBuilder = this;
            collection2 = collection;
            if (iTailSize2 > iTailSize) {
                int i4 = iTailSize2 - iTailSize;
                Object[] objArrMakeMutableShiftingRight = persistentVectorBuilder.makeMutableShiftingRight(persistentVectorBuilder.tail, i4);
                persistentVectorBuilder.insertIntoRoot(collection2, i, i4, objArr3, size, objArrMakeMutableShiftingRight);
                objArr = objArr3;
                objArrCopyInto = objArrMakeMutableShiftingRight;
            } else {
                int i5 = iTailSize - iTailSize2;
                objArrCopyInto = ArraysKt.copyInto(persistentVectorBuilder.tail, persistentVectorBuilder.mutableBuffer(), 0, i5, iTailSize);
                int i6 = 32 - i5;
                Object[] objArrMakeMutableShiftingRight2 = persistentVectorBuilder.makeMutableShiftingRight(persistentVectorBuilder.tail, i6);
                int i7 = size - 1;
                objArr3[i7] = objArrMakeMutableShiftingRight2;
                persistentVectorBuilder.insertIntoRoot(collection2, i, i6, objArr3, i7, objArrMakeMutableShiftingRight2);
                collection2 = collection2;
                objArr = objArr3;
                persistentVectorBuilder = persistentVectorBuilder;
            }
        }
        persistentVectorBuilder.root = persistentVectorBuilder.pushBuffersIncreasingHeightIfNeeded(persistentVectorBuilder.root, i2, objArr);
        persistentVectorBuilder.tail = objArrCopyInto;
        persistentVectorBuilder.size = persistentVectorBuilder.size() + collection2.size();
        return true;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean addAll(Collection collection) {
        if (collection.isEmpty()) {
            return false;
        }
        ((AbstractList) this).modCount++;
        int iTailSize = tailSize();
        Iterator it = collection.iterator();
        if (32 - iTailSize >= collection.size()) {
            this.tail = copyToBuffer(makeMutable(this.tail), iTailSize, it);
            this.size = size() + collection.size();
        } else {
            int size = ((collection.size() + iTailSize) - 1) / 32;
            Object[][] objArr = new Object[size][];
            objArr[0] = copyToBuffer(makeMutable(this.tail), iTailSize, it);
            for (int i = 1; i < size; i++) {
                objArr[i] = copyToBuffer(mutableBuffer(), 0, it);
            }
            this.root = pushBuffersIncreasingHeightIfNeeded(this.root, rootSize(), objArr);
            this.tail = copyToBuffer(mutableBuffer(), 0, it);
            this.size = size() + collection.size();
        }
        return true;
    }

    @Override // androidx.compose.runtime.external.kotlinx.collections.immutable.PersistentList.Builder
    public PersistentList build() {
        PersistentList persistentVector;
        if (this.root == this.vectorRoot && this.tail == this.vectorTail) {
            persistentVector = this.vector;
        } else {
            this.ownership = new MutabilityOwnership();
            Object[] objArr = this.root;
            this.vectorRoot = objArr;
            Object[] objArr2 = this.tail;
            this.vectorTail = objArr2;
            if (objArr != null) {
                Object[] objArr3 = this.root;
                objArr3.getClass();
                persistentVector = new PersistentVector(objArr3, this.tail, size(), this.rootShift);
            } else if (objArr2.length == 0) {
                persistentVector = UtilsKt.persistentVectorOf();
            } else {
                Object[] objArrCopyOf = Arrays.copyOf(this.tail, size());
                objArrCopyOf.getClass();
                persistentVector = new SmallPersistentVector(objArrCopyOf);
            }
        }
        this.vector = persistentVector;
        return persistentVector;
    }

    @Override // java.util.AbstractList, java.util.List
    public Object get(int i) {
        ListImplementation.checkElementIndex$runtime_release(i, size());
        return bufferFor(i)[i & 31];
    }

    public final int getModCount$runtime_release() {
        return ((AbstractList) this).modCount;
    }

    public final Object[] getRoot$runtime_release() {
        return this.root;
    }

    public final int getRootShift$runtime_release() {
        return this.rootShift;
    }

    @Override // kotlin.collections.AbstractMutableList
    public int getSize() {
        return this.size;
    }

    public final Object[] getTail$runtime_release() {
        return this.tail;
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.List
    public Iterator iterator() {
        return listIterator();
    }

    @Override // java.util.AbstractList, java.util.List
    public ListIterator listIterator() {
        return listIterator(0);
    }

    @Override // java.util.AbstractList, java.util.List
    public ListIterator listIterator(int i) {
        ListImplementation.checkPositionIndex$runtime_release(i, size());
        return new PersistentVectorMutableIterator(this, i);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean removeAll(final Collection collection) {
        return removeAllWithPredicate(new Function1() { // from class: androidx.compose.runtime.external.kotlinx.collections.immutable.implementations.immutableList.PersistentVectorBuilder.removeAll.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(Object obj) {
                return Boolean.valueOf(collection.contains(obj));
            }
        });
    }

    public final boolean removeAllWithPredicate(Function1 function1) {
        boolean zRemoveAll = removeAll(function1);
        if (zRemoveAll) {
            ((AbstractList) this).modCount++;
        }
        return zRemoveAll;
    }

    @Override // kotlin.collections.AbstractMutableList
    public Object removeAt(int i) {
        ListImplementation.checkElementIndex$runtime_release(i, size());
        ((AbstractList) this).modCount++;
        int iRootSize = rootSize();
        if (i >= iRootSize) {
            return removeFromTailAt(this.root, iRootSize, this.rootShift, i - iRootSize);
        }
        ObjectRef objectRef = new ObjectRef(this.tail[0]);
        Object[] objArr = this.root;
        objArr.getClass();
        removeFromTailAt(removeFromRootAt(objArr, this.rootShift, i, objectRef), iRootSize, this.rootShift, 0);
        return objectRef.getValue();
    }

    @Override // java.util.AbstractList, java.util.List
    public Object set(int i, Object obj) {
        ListImplementation.checkElementIndex$runtime_release(i, size());
        if (rootSize() > i) {
            ObjectRef objectRef = new ObjectRef(null);
            Object[] objArr = this.root;
            objArr.getClass();
            this.root = setInRoot(objArr, this.rootShift, i, obj, objectRef);
            return objectRef.getValue();
        }
        Object[] objArrMakeMutable = makeMutable(this.tail);
        if (objArrMakeMutable != this.tail) {
            ((AbstractList) this).modCount++;
        }
        int i2 = i & 31;
        Object obj2 = objArrMakeMutable[i2];
        objArrMakeMutable[i2] = obj;
        this.tail = objArrMakeMutable;
        return obj2;
    }
}
