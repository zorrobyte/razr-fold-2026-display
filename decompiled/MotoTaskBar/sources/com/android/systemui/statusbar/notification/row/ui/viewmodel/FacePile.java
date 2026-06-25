package com.android.systemui.statusbar.notification.row.ui.viewmodel;

import android.graphics.drawable.Drawable;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: SingleLineViewModel.kt */
/* JADX INFO: loaded from: classes.dex */
public final class FacePile extends ConversationAvatar {
    private final int bottomBackgroundColor;
    private final Drawable bottomIconDrawable;
    private final Drawable topIconDrawable;

    public FacePile(Drawable drawable, Drawable drawable2, int i) {
        super(null);
        this.topIconDrawable = drawable;
        this.bottomIconDrawable = drawable2;
        this.bottomBackgroundColor = i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FacePile)) {
            return false;
        }
        FacePile facePile = (FacePile) obj;
        return Intrinsics.areEqual(this.topIconDrawable, facePile.topIconDrawable) && Intrinsics.areEqual(this.bottomIconDrawable, facePile.bottomIconDrawable) && this.bottomBackgroundColor == facePile.bottomBackgroundColor;
    }

    public final int getBottomBackgroundColor() {
        return this.bottomBackgroundColor;
    }

    public final Drawable getBottomIconDrawable() {
        return this.bottomIconDrawable;
    }

    public final Drawable getTopIconDrawable() {
        return this.topIconDrawable;
    }

    public int hashCode() {
        Drawable drawable = this.topIconDrawable;
        int iHashCode = (drawable == null ? 0 : drawable.hashCode()) * 31;
        Drawable drawable2 = this.bottomIconDrawable;
        return ((iHashCode + (drawable2 != null ? drawable2.hashCode() : 0)) * 31) + Integer.hashCode(this.bottomBackgroundColor);
    }

    public String toString() {
        return "FacePile(topIconDrawable=" + this.topIconDrawable + ", bottomIconDrawable=" + this.bottomIconDrawable + ", bottomBackgroundColor=" + this.bottomBackgroundColor + ")";
    }
}
