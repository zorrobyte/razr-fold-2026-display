package com.google.android.material.internal;

import android.widget.ImageButton;

/* JADX INFO: loaded from: classes.dex */
public class VisibilityAwareImageButton extends ImageButton {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public int f2184a;

    public final void a(int i2, boolean z2) {
        super.setVisibility(i2);
        if (z2) {
            this.f2184a = i2;
        }
    }

    public final int getUserSetVisibility() {
        return this.f2184a;
    }

    @Override // android.widget.ImageView, android.view.View
    public void setVisibility(int i2) {
        a(i2, true);
    }
}
