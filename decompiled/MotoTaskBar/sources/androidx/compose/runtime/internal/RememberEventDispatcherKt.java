package androidx.compose.runtime.internal;

import androidx.collection.MutableIntList;
import java.util.List;

/* JADX INFO: compiled from: RememberEventDispatcher.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class RememberEventDispatcherKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final void swap(MutableIntList mutableIntList, int i, int i2) {
        int i3 = mutableIntList.get(i);
        mutableIntList.set(i, mutableIntList.get(i2));
        mutableIntList.set(i2, i3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void swap(List list, int i, int i2) {
        Object obj = list.get(i);
        list.set(i, list.get(i2));
        list.set(i2, obj);
    }
}
