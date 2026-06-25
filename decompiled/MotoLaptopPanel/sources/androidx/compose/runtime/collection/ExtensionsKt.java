package androidx.compose.runtime.collection;

import androidx.collection.MutableObjectList;
import java.util.NoSuchElementException;

/* JADX INFO: compiled from: Extensions.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class ExtensionsKt {
    public static final Object removeLast(MutableObjectList mutableObjectList) {
        if (mutableObjectList.isEmpty()) {
            throw new NoSuchElementException("List is empty.");
        }
        int size = mutableObjectList.getSize() - 1;
        Object obj = mutableObjectList.get(size);
        mutableObjectList.removeAt(size);
        return obj;
    }
}
