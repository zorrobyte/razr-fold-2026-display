package com.android.systemui.statusbar.notification.collection.listbuilder;

import com.android.systemui.statusbar.notification.collection.ListEntry;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ShadeListBuilderHelper.kt */
/* JADX INFO: loaded from: classes.dex */
public final class ShadeListBuilderHelper {
    public static final ShadeListBuilderHelper INSTANCE = new ShadeListBuilderHelper();

    private ShadeListBuilderHelper() {
    }

    public final Iterable getSectionSubLists(List list) {
        list.getClass();
        ArrayList arrayList = new ArrayList();
        int size = list.size();
        Integer num = null;
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            Integer numValueOf = Integer.valueOf(((ListEntry) list.get(i2)).getSectionIndex());
            if (num == null) {
                num = numValueOf;
            } else if (!Intrinsics.areEqual(num, numValueOf)) {
                if (i2 - i >= 1) {
                    arrayList.add(list.subList(i, i2));
                }
                i = i2;
                num = numValueOf;
            }
        }
        if (size - i >= 1) {
            arrayList.add(list.subList(i, size));
        }
        return arrayList;
    }
}
