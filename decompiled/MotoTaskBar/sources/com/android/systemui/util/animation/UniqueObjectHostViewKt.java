package com.android.systemui.util.animation;

import android.view.View;
import com.android.systemui.res.R$id;

/* JADX INFO: compiled from: UniqueObjectHostView.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class UniqueObjectHostViewKt {
    public static final boolean getRequiresRemeasuring(View view) {
        view.getClass();
        Object tag = view.getTag(R$id.requires_remeasuring);
        if (tag != null) {
            return tag.equals(Boolean.TRUE);
        }
        return false;
    }

    public static final void setRequiresRemeasuring(View view, boolean z) {
        view.getClass();
        view.setTag(R$id.requires_remeasuring, Boolean.valueOf(z));
    }
}
