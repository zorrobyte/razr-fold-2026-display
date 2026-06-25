package com.android.systemui.statusbar.notification.shared;

import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ActiveNotificationModel.kt */
/* JADX INFO: loaded from: classes.dex */
public final class ActiveNotificationGroupModel extends ActiveNotificationEntryModel {
    private final List children;
    private final String key;
    private final ActiveNotificationModel summary;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ActiveNotificationGroupModel(String str, ActiveNotificationModel activeNotificationModel, List list) {
        super(null);
        str.getClass();
        activeNotificationModel.getClass();
        list.getClass();
        this.key = str;
        this.summary = activeNotificationModel;
        this.children = list;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ActiveNotificationGroupModel)) {
            return false;
        }
        ActiveNotificationGroupModel activeNotificationGroupModel = (ActiveNotificationGroupModel) obj;
        return Intrinsics.areEqual(this.key, activeNotificationGroupModel.key) && Intrinsics.areEqual(this.summary, activeNotificationGroupModel.summary) && Intrinsics.areEqual(this.children, activeNotificationGroupModel.children);
    }

    public final List getChildren() {
        return this.children;
    }

    public final String getKey() {
        return this.key;
    }

    public final ActiveNotificationModel getSummary() {
        return this.summary;
    }

    public int hashCode() {
        return (((this.key.hashCode() * 31) + this.summary.hashCode()) * 31) + this.children.hashCode();
    }

    public String toString() {
        return "ActiveNotificationGroupModel(key=" + this.key + ", summary=" + this.summary + ", children=" + this.children + ")";
    }
}
