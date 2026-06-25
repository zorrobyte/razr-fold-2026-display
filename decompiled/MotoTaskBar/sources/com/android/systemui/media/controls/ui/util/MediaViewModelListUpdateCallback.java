package com.android.systemui.media.controls.ui.util;

import androidx.recyclerview.widget.ListUpdateCallback;
import java.util.List;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;

/* JADX INFO: compiled from: MediaViewModelListUpdateCallback.kt */
/* JADX INFO: loaded from: classes.dex */
public final class MediaViewModelListUpdateCallback implements ListUpdateCallback {

    /* JADX INFO: renamed from: new, reason: not valid java name */
    private final List f6new;
    private final List old;
    private final Function2 onAdded;
    private final Function3 onMoved;
    private final Function1 onRemoved;
    private final Function1 onUpdated;

    public MediaViewModelListUpdateCallback(List list, List list2, Function2 function2, Function1 function1, Function1 function12, Function3 function3) {
        list.getClass();
        list2.getClass();
        function2.getClass();
        function1.getClass();
        function12.getClass();
        function3.getClass();
        this.old = list;
        this.f6new = list2;
        this.onAdded = function2;
        this.onUpdated = function1;
        this.onRemoved = function12;
        this.onMoved = function3;
    }

    @Override // androidx.recyclerview.widget.ListUpdateCallback
    public void onChanged(int i, int i2, Object obj) {
        int i3 = i2 + i;
        while (i < i3) {
            this.onUpdated.invoke(this.f6new.get(i));
            i++;
        }
    }

    @Override // androidx.recyclerview.widget.ListUpdateCallback
    public void onInserted(int i, int i2) {
        int i3 = i2 + i;
        while (i < i3) {
            this.onAdded.invoke(this.f6new.get(i), Integer.valueOf(i));
            i++;
        }
    }

    @Override // androidx.recyclerview.widget.ListUpdateCallback
    public void onMoved(int i, int i2) {
        this.onMoved.invoke(this.old.get(i), Integer.valueOf(i), Integer.valueOf(i2));
    }

    @Override // androidx.recyclerview.widget.ListUpdateCallback
    public void onRemoved(int i, int i2) {
        int i3 = i2 + i;
        while (i < i3) {
            this.onRemoved.invoke(this.old.get(i));
            i++;
        }
    }
}
