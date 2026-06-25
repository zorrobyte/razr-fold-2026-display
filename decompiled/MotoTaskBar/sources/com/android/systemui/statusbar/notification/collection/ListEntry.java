package com.android.systemui.statusbar.notification.collection;

import com.android.systemui.statusbar.notification.collection.listbuilder.NotifSection;

/* JADX INFO: loaded from: classes.dex */
public abstract class ListEntry {
    private final long mCreationTime;
    private final String mKey;
    private final ListAttachState mPreviousAttachState = ListAttachState.create();
    private final ListAttachState mAttachState = ListAttachState.create();

    protected ListEntry(String str, long j) {
        this.mKey = str;
        this.mCreationTime = j;
    }

    void beginNewAttachState() {
        this.mPreviousAttachState.clone(this.mAttachState);
        this.mAttachState.reset();
    }

    ListAttachState getAttachState() {
        return this.mAttachState;
    }

    public long getCreationTime() {
        return this.mCreationTime;
    }

    public String getKey() {
        return this.mKey;
    }

    public GroupEntry getParent() {
        return this.mAttachState.getParent();
    }

    ListAttachState getPreviousAttachState() {
        return this.mPreviousAttachState;
    }

    public abstract NotificationEntry getRepresentativeEntry();

    public NotifSection getSection() {
        return this.mAttachState.getSection();
    }

    public int getSectionIndex() {
        if (this.mAttachState.getSection() != null) {
            return this.mAttachState.getSection().getIndex();
        }
        return -1;
    }

    void setParent(GroupEntry groupEntry) {
        this.mAttachState.setParent(groupEntry);
    }

    public boolean wasAttachedInPreviousPass() {
        return getPreviousAttachState().getParent() != null;
    }
}
