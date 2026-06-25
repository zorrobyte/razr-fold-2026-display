package com.android.systemui.statusbar.notification.row.ui.viewmodel;

import android.graphics.drawable.Drawable;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: SingleLineViewModel.kt */
/* JADX INFO: loaded from: classes.dex */
public final class SingleIcon extends ConversationAvatar {
    private final Drawable iconDrawable;

    public SingleIcon(Drawable drawable) {
        super(null);
        this.iconDrawable = drawable;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof SingleIcon) && Intrinsics.areEqual(this.iconDrawable, ((SingleIcon) obj).iconDrawable);
    }

    public final Drawable getIconDrawable() {
        return this.iconDrawable;
    }

    public int hashCode() {
        Drawable drawable = this.iconDrawable;
        if (drawable == null) {
            return 0;
        }
        return drawable.hashCode();
    }

    public String toString() {
        return "SingleIcon(iconDrawable=" + this.iconDrawable + ")";
    }
}
