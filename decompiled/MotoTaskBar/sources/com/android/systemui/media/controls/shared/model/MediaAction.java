package com.android.systemui.media.controls.shared.model;

import android.graphics.drawable.Drawable;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: MediaData.kt */
/* JADX INFO: loaded from: classes.dex */
public final class MediaAction {
    private final Runnable action;
    private final Drawable background;
    private final CharSequence contentDescription;
    private final Drawable icon;
    private final Integer rebindId;

    public MediaAction(Drawable drawable, Runnable runnable, CharSequence charSequence, Drawable drawable2, Integer num) {
        this.icon = drawable;
        this.action = runnable;
        this.contentDescription = charSequence;
        this.background = drawable2;
        this.rebindId = num;
    }

    public /* synthetic */ MediaAction(Drawable drawable, Runnable runnable, CharSequence charSequence, Drawable drawable2, Integer num, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(drawable, runnable, charSequence, drawable2, (i & 16) != 0 ? null : num);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MediaAction)) {
            return false;
        }
        MediaAction mediaAction = (MediaAction) obj;
        return Intrinsics.areEqual(this.icon, mediaAction.icon) && Intrinsics.areEqual(this.action, mediaAction.action) && Intrinsics.areEqual(this.contentDescription, mediaAction.contentDescription) && Intrinsics.areEqual(this.background, mediaAction.background) && Intrinsics.areEqual(this.rebindId, mediaAction.rebindId);
    }

    public final Runnable getAction() {
        return this.action;
    }

    public final Drawable getBackground() {
        return this.background;
    }

    public final CharSequence getContentDescription() {
        return this.contentDescription;
    }

    public final Drawable getIcon() {
        return this.icon;
    }

    public final Integer getRebindId() {
        return this.rebindId;
    }

    public int hashCode() {
        Drawable drawable = this.icon;
        int iHashCode = (drawable == null ? 0 : drawable.hashCode()) * 31;
        Runnable runnable = this.action;
        int iHashCode2 = (iHashCode + (runnable == null ? 0 : runnable.hashCode())) * 31;
        CharSequence charSequence = this.contentDescription;
        int iHashCode3 = (iHashCode2 + (charSequence == null ? 0 : charSequence.hashCode())) * 31;
        Drawable drawable2 = this.background;
        int iHashCode4 = (iHashCode3 + (drawable2 == null ? 0 : drawable2.hashCode())) * 31;
        Integer num = this.rebindId;
        return iHashCode4 + (num != null ? num.hashCode() : 0);
    }

    public String toString() {
        Drawable drawable = this.icon;
        Runnable runnable = this.action;
        CharSequence charSequence = this.contentDescription;
        return "MediaAction(icon=" + drawable + ", action=" + runnable + ", contentDescription=" + ((Object) charSequence) + ", background=" + this.background + ", rebindId=" + this.rebindId + ")";
    }
}
