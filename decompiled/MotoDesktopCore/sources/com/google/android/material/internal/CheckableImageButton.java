package com.google.android.material.internal;

import F.j;
import android.R;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.ImageButton;
import androidx.appcompat.R$attr;
import androidx.appcompat.widget.AppCompatImageButton;
import v.l;

/* JADX INFO: loaded from: classes.dex */
public class CheckableImageButton extends AppCompatImageButton implements Checkable {

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public static final int[] f2163d = {R.attr.state_checked};

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public boolean f2164c;

    public CheckableImageButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, R$attr.imageButtonStyle);
        l.b(this, new j(this, 2));
    }

    @Override // android.widget.Checkable
    public final boolean isChecked() {
        return this.f2164c;
    }

    @Override // android.widget.ImageView, android.view.View
    public final int[] onCreateDrawableState(int i2) {
        return this.f2164c ? ImageButton.mergeDrawableStates(super.onCreateDrawableState(i2 + 1), f2163d) : super.onCreateDrawableState(i2);
    }

    @Override // android.widget.Checkable
    public void setChecked(boolean z2) {
        if (this.f2164c != z2) {
            this.f2164c = z2;
            refreshDrawableState();
            sendAccessibilityEvent(2048);
        }
    }

    @Override // android.widget.Checkable
    public final void toggle() {
        setChecked(!this.f2164c);
    }
}
