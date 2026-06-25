package androidx.compose.runtime.collection;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.CollectionToArray;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;
import kotlin.jvm.internal.markers.KMutableList;

/* JADX INFO: compiled from: MutableVector.kt */
/* JADX INFO: loaded from: classes.dex */
public final class MutableVector implements RandomAccess {
    public static final int $stable = 8;
    public Object[] content;
    private List list;
    private int size;

    /* JADX INFO: compiled from: MutableVector.kt */
    final class MutableVectorList implements List, KMutableList {
        private final MutableVector vector;

        public MutableVectorList(MutableVector mutableVector) {
            this.vector = mutableVector;
        }

        @Override // java.util.List
        public void add(int i, Object obj) {
            this.vector.add(i, obj);
        }

        @Override // java.util.List, java.util.Collection
        public boolean add(Object obj) {
            return this.vector.add(obj);
        }

        @Override // java.util.List
        public boolean addAll(int i, Collection collection) {
            return this.vector.addAll(i, collection);
        }

        @Override // java.util.List, java.util.Collection
        public boolean addAll(Collection collection) {
            return this.vector.addAll(collection);
        }

        @Override // java.util.List, java.util.Collection
        public void clear() {
            this.vector.clear();
        }

        @Override // java.util.List, java.util.Collection
        public boolean contains(Object obj) {
            return this.vector.contains(obj);
        }

        @Override // java.util.List, java.util.Collection
        public boolean containsAll(Collection collection) {
            return this.vector.containsAll(collection);
        }

        @Override // java.util.List
        public Object get(int i) {
            MutableVectorKt.checkIndex(this, i);
            return this.vector.content[i];
        }

        public int getSize() {
            return this.vector.getSize();
        }

        @Override // java.util.List
        public int indexOf(Object obj) {
            return this.vector.indexOf(obj);
        }

        @Override // java.util.List, java.util.Collection
        public boolean isEmpty() {
            return this.vector.getSize() == 0;
        }

        @Override // java.util.List, java.util.Collection, java.lang.Iterable
        public Iterator iterator() {
            return new VectorListIterator(this, 0);
        }

        @Override // java.util.List
        public int lastIndexOf(Object obj) {
            return this.vector.lastIndexOf(obj);
        }

        @Override // java.util.List
        public ListIterator listIterator() {
            return new VectorListIterator(this, 0);
        }

        @Override // java.util.List
        public ListIterator listIterator(int i) {
            return new VectorListIterator(this, i);
        }

        @Override // java.util.List
        public final /* bridge */ Object remove(int i) {
            return removeAt(i);
        }

        @Override // java.util.List, java.util.Collection
        public boolean remove(Object obj) {
            return this.vector.remove(obj);
        }

        @Override // java.util.List, java.util.Collection
        public boolean removeAll(Collection collection) {
            return this.vector.removeAll(collection);
        }

        public Object removeAt(int i) {
            MutableVectorKt.checkIndex(this, i);
            return this.vector.removeAt(i);
        }

        @Override // java.util.List, java.util.Collection
        public boolean retainAll(Collection collection) {
            return this.vector.retainAll(collection);
        }

        @Override // java.util.List
        public Object set(int i, Object obj) {
            MutableVectorKt.checkIndex(this, i);
            return this.vector.set(i, obj);
        }

        @Override // java.util.List, java.util.Collection
        public final /* bridge */ int size() {
            return getSize();
        }

        @Override // java.util.List
        public List subList(int i, int i2) {
            MutableVectorKt.checkSubIndex(this, i, i2);
            return new SubList(this, i, i2);
        }

        @Override // java.util.List, java.util.Collection
        public Object[] toArray() {
            return CollectionToArray.toArray(this);
        }

        @Override // java.util.List, java.util.Collection
        public Object[] toArray(Object[] objArr) {
            return CollectionToArray.toArray(this, objArr);
        }
    }

    /* JADX INFO: compiled from: MutableVector.kt */
    final class SubList implements List, KMutableList {
        private int end;
        private final List list;
        private final int start;

        public SubList(List list, int i, int i2) {
            this.list = list;
            this.start = i;
            this.end = i2;
        }

        @Override // java.util.List
        public void add(int i, Object obj) {
            this.list.add(i + this.start, obj);
            this.end++;
        }

        @Override // java.util.List, java.util.Collection
        public boolean add(Object obj) {
            List list = this.list;
            int i = this.end;
            this.end = i + 1;
            list.add(i, obj);
            return true;
        }

        @Override // java.util.List
        public boolean addAll(int i, Collection collection) {
            this.list.addAll(i + this.start, collection);
            int size = collection.size();
            this.end += size;
            return size > 0;
        }

        @Override // java.util.List, java.util.Collection
        public boolean addAll(Collection collection) {
            this.list.addAll(this.end, collection);
            int size = collection.size();
            this.end += size;
            return size > 0;
        }

        @Override // java.util.List, java.util.Collection
        public void clear() {
            int i = this.end - 1;
            int i2 = this.start;
            if (i2 <= i) {
                while (true) {
                    this.list.remove(i);
                    if (i == i2) {
                        break;
                    } else {
                        i--;
                    }
                }
            }
            this.end = this.start;
        }

        @Override // java.util.List, java.util.Collection
        public boolean contains(Object obj) {
            int i = this.end;
            for (int i2 = this.start; i2 < i; i2++) {
                if (Intrinsics.areEqual(this.list.get(i2), obj)) {
                    return true;
                }
            }
            return false;
        }

        @Override // java.util.List, java.util.Collection
        public boolean containsAll(Collection collection) {
            Iterator it = collection.iterator();
            while (it.hasNext()) {
                if (!contains(it.next())) {
                    return false;
                }
            }
            return true;
        }

        @Override // java.util.List
        public Object get(int i) {
            MutableVectorKt.checkIndex(this, i);
            return this.list.get(i + this.start);
        }

        public int getSize() {
            return this.end - this.start;
        }

        @Override // java.util.List
        public int indexOf(Object obj) {
            int i = this.end;
            for (int i2 = this.start; i2 < i; i2++) {
                if (Intrinsics.areEqual(this.list.get(i2), obj)) {
                    return i2 - this.start;
                }
            }
            return -1;
        }

        @Override // java.util.List, java.util.Collection
        public boolean isEmpty() {
            return this.end == this.start;
        }

        @Override // java.util.List, java.util.Collection, java.lang.Iterable
        public Iterator iterator() {
            return new VectorListIterator(this, 0);
        }

        @Override // java.util.List
        public int lastIndexOf(Object obj) {
            int i = this.end - 1;
            int i2 = this.start;
            if (i2 > i) {
                return -1;
            }
            while (!Intrinsics.areEqual(this.list.get(i), obj)) {
                if (i == i2) {
                    return -1;
                }
                i--;
            }
            return i - this.start;
        }

        @Override // java.util.List
        public ListIterator listIterator() {
            return new VectorListIterator(this, 0);
        }

        @Override // java.util.List
        public ListIterator listIterator(int i) {
            return new VectorListIterator(this, i);
        }

        @Override // java.util.List
        public final /* bridge */ Object remove(int i) {
            return removeAt(i);
        }

        @Override // java.util.List, java.util.Collection
        public boolean remove(Object obj) {
            int i = this.end;
            for (int i2 = this.start; i2 < i; i2++) {
                if (Intrinsics.areEqual(this.list.get(i2), obj)) {
                    this.list.remove(i2);
                    this.end--;
                    return true;
                }
            }
            return false;
        }

        @Override // java.util.List, java.util.Collection
        public boolean removeAll(Collection collection) {
            int i = this.end;
            Iterator it = collection.iterator();
            while (it.hasNext()) {
                remove(it.next());
            }
            return i != this.end;
        }

        public Object removeAt(int i) {
            MutableVectorKt.checkIndex(this, i);
            this.end--;
            return this.list.remove(i + this.start);
        }

        @Override // java.util.List, java.util.Collection
        public boolean retainAll(Collection collection) {
            int i = this.end;
            int i2 = i - 1;
            int i3 = this.start;
            if (i3 <= i2) {
                while (true) {
                    if (!collection.contains(this.list.get(i2))) {
                        this.list.remove(i2);
                        this.end--;
                    }
                    if (i2 == i3) {
                        break;
                    }
                    i2--;
                }
            }
            return i != this.end;
        }

        @Override // java.util.List
        public Object set(int i, Object obj) {
            MutableVectorKt.checkIndex(this, i);
            return this.list.set(i + this.start, obj);
        }

        @Override // java.util.List, java.util.Collection
        public final /* bridge */ int size() {
            return getSize();
        }

        @Override // java.util.List
        public List subList(int i, int i2) {
            MutableVectorKt.checkSubIndex(this, i, i2);
            return new SubList(this, i, i2);
        }

        @Override // java.util.List, java.util.Collection
        public Object[] toArray() {
            return CollectionToArray.toArray(this);
        }

        @Override // java.util.List, java.util.Collection
        public Object[] toArray(Object[] objArr) {
            return CollectionToArray.toArray(this, objArr);
        }
    }

    /* JADX INFO: compiled from: MutableVector.kt */
    final class VectorListIterator implements ListIterator, KMappedMarker {
        private int index;
        private final List list;

        public VectorListIterator(List list, int i) {
            this.list = list;
            this.index = i;
        }

        @Override // java.util.ListIterator
        public void add(Object obj) {
            this.list.add(this.index, obj);
            this.index++;
        }

        @Override // java.util.ListIterator, java.util.Iterator
        public boolean hasNext() {
            return this.index < this.list.size();
        }

        @Override // java.util.ListIterator
        public boolean hasPrevious() {
            return this.index > 0;
        }

        @Override // java.util.ListIterator, java.util.Iterator
        public Object next() {
            List list = this.list;
            int i = this.index;
            this.index = i + 1;
            return list.get(i);
        }

        @Override // java.util.ListIterator
        public int nextIndex() {
            return this.index;
        }

        @Override // java.util.ListIterator
        public Object previous() {
            int i = this.index - 1;
            this.index = i;
            return this.list.get(i);
        }

        @Override // java.util.ListIterator
        public int previousIndex() {
            return this.index - 1;
        }

        @Override // java.util.ListIterator, java.util.Iterator
        public void remove() {
            int i = this.index - 1;
            this.index = i;
            this.list.remove(i);
        }

        @Override // java.util.ListIterator
        public void set(Object obj) {
            this.list.set(this.index, obj);
        }
    }

    public MutableVector(Object[] objArr, int i) {
        this.content = objArr;
        this.size = i;
    }

    public final void add(int i, Object obj) {
        int i2 = this.size + 1;
        if (this.content.length < i2) {
            resizeStorage(i2);
        }
        Object[] objArr = this.content;
        int i3 = this.size;
        if (i != i3) {
            System.arraycopy(objArr, i, objArr, i + 1, i3 - i);
        }
        objArr[i] = obj;
        this.size++;
    }

    public final boolean add(Object obj) {
        int i = this.size + 1;
        if (this.content.length < i) {
            resizeStorage(i);
        }
        Object[] objArr = this.content;
        int i2 = this.size;
        objArr[i2] = obj;
        this.size = i2 + 1;
        return true;
    }

    public final boolean addAll(int i, MutableVector mutableVector) {
        int i2 = mutableVector.size;
        if (i2 == 0) {
            return false;
        }
        int i3 = this.size + i2;
        if (this.content.length < i3) {
            resizeStorage(i3);
        }
        Object[] objArr = this.content;
        int i4 = this.size;
        if (i != i4) {
            System.arraycopy(objArr, i, objArr, i + i2, i4 - i);
        }
        System.arraycopy(mutableVector.content, 0, objArr, i, i2);
        this.size += i2;
        return true;
    }

    public final boolean addAll(int i, Collection collection) {
        int i2 = 0;
        if (collection.isEmpty()) {
            return false;
        }
        int size = collection.size();
        int i3 = this.size + size;
        if (this.content.length < i3) {
            resizeStorage(i3);
        }
        Object[] objArr = this.content;
        int i4 = this.size;
        if (i != i4) {
            System.arraycopy(objArr, i, objArr, i + size, i4 - i);
        }
        for (Object obj : collection) {
            int i5 = i2 + 1;
            if (i2 < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            objArr[i2 + i] = obj;
            i2 = i5;
        }
        this.size += size;
        return true;
    }

    public final boolean addAll(int i, List list) {
        if (list.isEmpty()) {
            return false;
        }
        int size = list.size();
        int i2 = this.size + size;
        if (this.content.length < i2) {
            resizeStorage(i2);
        }
        Object[] objArr = this.content;
        int i3 = this.size;
        if (i != i3) {
            System.arraycopy(objArr, i, objArr, i + size, i3 - i);
        }
        int size2 = list.size();
        for (int i4 = 0; i4 < size2; i4++) {
            objArr[i + i4] = list.get(i4);
        }
        this.size += size;
        return true;
    }

    public final boolean addAll(Collection collection) {
        return addAll(this.size, collection);
    }

    public final List asMutableList() {
        List list = this.list;
        if (list != null) {
            return list;
        }
        MutableVectorList mutableVectorList = new MutableVectorList(this);
        this.list = mutableVectorList;
        return mutableVectorList;
    }

    public final void clear() {
        Object[] objArr = this.content;
        int i = this.size;
        for (int i2 = 0; i2 < i; i2++) {
            objArr[i2] = null;
        }
        this.size = 0;
    }

    public final boolean contains(Object obj) {
        int size = getSize() - 1;
        if (size >= 0) {
            for (int i = 0; !Intrinsics.areEqual(this.content[i], obj); i++) {
                if (i != size) {
                }
            }
            return true;
        }
        return false;
    }

    public final boolean containsAll(Collection collection) {
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            if (!contains(it.next())) {
                return false;
            }
        }
        return true;
    }

    public final int getSize() {
        return this.size;
    }

    public final int indexOf(Object obj) {
        Object[] objArr = this.content;
        int i = this.size;
        for (int i2 = 0; i2 < i; i2++) {
            if (Intrinsics.areEqual(obj, objArr[i2])) {
                return i2;
            }
        }
        return -1;
    }

    public final int lastIndexOf(Object obj) {
        Object[] objArr = this.content;
        for (int i = this.size - 1; i >= 0; i--) {
            if (Intrinsics.areEqual(obj, objArr[i])) {
                return i;
            }
        }
        return -1;
    }

    public final boolean remove(Object obj) {
        int iIndexOf = indexOf(obj);
        if (iIndexOf < 0) {
            return false;
        }
        removeAt(iIndexOf);
        return true;
    }

    public final boolean removeAll(Collection collection) {
        if (collection.isEmpty()) {
            return false;
        }
        int i = this.size;
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            remove(it.next());
        }
        return i != this.size;
    }

    public final Object removeAt(int i) {
        Object[] objArr = this.content;
        Object obj = objArr[i];
        if (i != getSize() - 1) {
            int i2 = i + 1;
            System.arraycopy(objArr, i2, objArr, i, this.size - i2);
        }
        int i3 = this.size - 1;
        this.size = i3;
        objArr[i3] = null;
        return obj;
    }

    public final void removeRange(int i, int i2) {
        if (i2 > i) {
            int i3 = this.size;
            if (i2 < i3) {
                Object[] objArr = this.content;
                System.arraycopy(objArr, i2, objArr, i, i3 - i2);
            }
            int i4 = this.size - (i2 - i);
            int size = getSize() - 1;
            if (i4 <= size) {
                int i5 = i4;
                while (true) {
                    this.content[i5] = null;
                    if (i5 == size) {
                        break;
                    } else {
                        i5++;
                    }
                }
            }
            this.size = i4;
        }
    }

    public final void resizeStorage(int i) {
        Object[] objArr = this.content;
        int length = objArr.length;
        Object[] objArr2 = new Object[Math.max(i, length * 2)];
        System.arraycopy(objArr, 0, objArr2, 0, length);
        this.content = objArr2;
    }

    public final boolean retainAll(Collection collection) {
        int i = this.size;
        for (int size = getSize() - 1; -1 < size; size--) {
            if (!collection.contains(this.content[size])) {
                removeAt(size);
            }
        }
        return i != this.size;
    }

    public final Object set(int i, Object obj) {
        Object[] objArr = this.content;
        Object obj2 = objArr[i];
        objArr[i] = obj;
        return obj2;
    }

    public final void setSize(int i) {
        this.size = i;
    }

    public final void sortWith(Comparator comparator) {
        ArraysKt.sortWith(this.content, comparator, 0, this.size);
    }
}
