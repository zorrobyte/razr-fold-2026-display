package com.android.systemui.statusbar.notification.collection.listbuilder.pluggable;

import com.android.systemui.statusbar.notification.collection.ListEntry;
import java.util.Comparator;

/* JADX INFO: loaded from: classes.dex */
public abstract class NotifComparator extends Pluggable implements Comparator {
    protected NotifComparator(String str) {
        super(str);
    }

    public abstract int compare(ListEntry listEntry, ListEntry listEntry2);
}
