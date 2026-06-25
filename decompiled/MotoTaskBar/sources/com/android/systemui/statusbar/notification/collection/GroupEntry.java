package com.android.systemui.statusbar.notification.collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class GroupEntry extends ListEntry {
    public static final GroupEntry ROOT_ENTRY = new GroupEntry("<root>", 0);
    private final List mChildren;
    private NotificationEntry mSummary;
    private final List mUnmodifiableChildren;

    GroupEntry(String str, long j) {
        super(str, j);
        ArrayList arrayList = new ArrayList();
        this.mChildren = arrayList;
        this.mUnmodifiableChildren = Collections.unmodifiableList(arrayList);
    }

    void addChild(NotificationEntry notificationEntry) {
        this.mChildren.add(notificationEntry);
    }

    void clearChildren() {
        this.mChildren.clear();
    }

    public List getChildren() {
        return this.mUnmodifiableChildren;
    }

    List getRawChildren() {
        return this.mChildren;
    }

    @Override // com.android.systemui.statusbar.notification.collection.ListEntry
    public NotificationEntry getRepresentativeEntry() {
        return this.mSummary;
    }

    public NotificationEntry getSummary() {
        return this.mSummary;
    }

    void setSummary(NotificationEntry notificationEntry) {
        this.mSummary = notificationEntry;
    }
}
