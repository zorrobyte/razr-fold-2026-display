package androidx.collection;

import androidx.collection.internal.RuntimeHelpersKt;
import java.util.List;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: ObjectList.kt */
/* JADX INFO: loaded from: classes.dex */
public final class MutableObjectList extends ObjectList {
    public MutableObjectList(int i) {
        super(i, null);
    }

    public /* synthetic */ MutableObjectList(int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 16 : i);
    }

    public final boolean add(Object obj) {
        int i = this._size + 1;
        Object[] objArr = this.content;
        if (objArr.length < i) {
            resizeStorage(i, objArr);
        }
        Object[] objArr2 = this.content;
        int i2 = this._size;
        objArr2[i2] = obj;
        this._size = i2 + 1;
        return true;
    }

    public final boolean addAll(ObjectList objectList) {
        objectList.getClass();
        int i = this._size;
        plusAssign(objectList);
        return i != this._size;
    }

    public final boolean addAll(List list) {
        list.getClass();
        int i = this._size;
        plusAssign(list);
        return i != this._size;
    }

    public final void clear() {
        ArraysKt.fill(this.content, (Object) null, 0, this._size);
        this._size = 0;
    }

    public final void plusAssign(ObjectList objectList) {
        objectList.getClass();
        if (objectList.isEmpty()) {
            return;
        }
        int i = this._size + objectList._size;
        Object[] objArr = this.content;
        if (objArr.length < i) {
            resizeStorage(i, objArr);
        }
        ArraysKt.copyInto(objectList.content, this.content, this._size, 0, objectList._size);
        this._size += objectList._size;
    }

    public final void plusAssign(List list) {
        list.getClass();
        if (list.isEmpty()) {
            return;
        }
        int i = this._size;
        int size = list.size() + i;
        Object[] objArr = this.content;
        if (objArr.length < size) {
            resizeStorage(size, objArr);
        }
        Object[] objArr2 = this.content;
        int size2 = list.size();
        for (int i2 = 0; i2 < size2; i2++) {
            objArr2[i2 + i] = list.get(i2);
        }
        this._size += list.size();
    }

    public final boolean remove(Object obj) {
        int iIndexOf = indexOf(obj);
        if (iIndexOf < 0) {
            return false;
        }
        removeAt(iIndexOf);
        return true;
    }

    public final Object removeAt(int i) {
        if (i < 0 || i >= this._size) {
            throwIndexOutOfBoundsExclusiveException$collection(i);
        }
        Object[] objArr = this.content;
        Object obj = objArr[i];
        int i2 = this._size;
        if (i != i2 - 1) {
            ArraysKt.copyInto(objArr, objArr, i, i + 1, i2);
        }
        int i3 = this._size - 1;
        this._size = i3;
        objArr[i3] = null;
        return obj;
    }

    public final void removeRange(int i, int i2) {
        int i3;
        if (i < 0 || i > (i3 = this._size) || i2 < 0 || i2 > i3) {
            RuntimeHelpersKt.throwIndexOutOfBoundsException("Start (" + i + ") and end (" + i2 + ") must be in 0.." + this._size);
        }
        if (i2 < i) {
            RuntimeHelpersKt.throwIllegalArgumentException("Start (" + i + ") is more than end (" + i2 + ')');
        }
        if (i2 != i) {
            int i4 = this._size;
            if (i2 < i4) {
                Object[] objArr = this.content;
                ArraysKt.copyInto(objArr, objArr, i, i2, i4);
            }
            int i5 = this._size;
            int i6 = i5 - (i2 - i);
            ArraysKt.fill(this.content, (Object) null, i6, i5);
            this._size = i6;
        }
    }

    public final void resizeStorage(int i, Object[] objArr) {
        objArr.getClass();
        int length = objArr.length;
        this.content = ArraysKt.copyInto(objArr, new Object[Math.max(i, (length * 3) / 2)], 0, 0, length);
    }

    public final Object set(int i, Object obj) {
        if (i < 0 || i >= this._size) {
            throwIndexOutOfBoundsExclusiveException$collection(i);
        }
        Object[] objArr = this.content;
        Object obj2 = objArr[i];
        objArr[i] = obj;
        return obj2;
    }
}
