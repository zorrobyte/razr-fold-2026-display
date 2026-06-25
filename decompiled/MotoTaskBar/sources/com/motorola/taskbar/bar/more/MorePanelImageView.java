package com.motorola.taskbar.bar.more;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.android.systemui.statusbar.policy.KeyButtonDrawable;

/* JADX INFO: loaded from: classes2.dex */
public class MorePanelImageView extends ImageView {
    private float mDarkIntensity;

    public MorePanelImageView(Context context) {
        super(context);
    }

    public MorePanelImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public MorePanelImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public MorePanelImageView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    public void setDarkIntensity(float f) {
        this.mDarkIntensity = f;
        Drawable drawable = getDrawable();
        if (drawable != null) {
            ((KeyButtonDrawable) drawable).setDarkIntensity(f);
            invalidate();
        }
    }

    @Override // android.widget.ImageView
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        if (drawable == null) {
            return;
        }
        ((KeyButtonDrawable) drawable).setDarkIntensity(this.mDarkIntensity);
    }
}
