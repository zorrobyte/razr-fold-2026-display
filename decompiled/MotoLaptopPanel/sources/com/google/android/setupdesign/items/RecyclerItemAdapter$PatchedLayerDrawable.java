package com.google.android.setupdesign.items;

import android.graphics.Rect;
import android.graphics.drawable.LayerDrawable;

/* JADX INFO: loaded from: classes.dex */
class RecyclerItemAdapter$PatchedLayerDrawable extends LayerDrawable {
    @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
    public boolean getPadding(Rect rect) {
        if (super.getPadding(rect)) {
            return (rect.left == 0 && rect.top == 0 && rect.right == 0 && rect.bottom == 0) ? false : true;
        }
        return false;
    }
}
