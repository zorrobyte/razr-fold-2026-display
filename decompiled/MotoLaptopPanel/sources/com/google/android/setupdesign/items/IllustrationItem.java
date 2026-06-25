package com.google.android.setupdesign.items;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import com.google.android.setupdesign.R$layout;
import com.google.android.setupdesign.R$styleable;

/* JADX INFO: loaded from: classes.dex */
public class IllustrationItem extends Item {
    private Drawable illustration;

    public IllustrationItem(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.SudIllustrationItem);
        this.illustration = typedArrayObtainStyledAttributes.getDrawable(R$styleable.SudIllustrationItem_android_drawable);
        typedArrayObtainStyledAttributes.recycle();
    }

    @Override // com.google.android.setupdesign.items.Item
    protected int getDefaultLayoutResource() {
        return R$layout.sud_illustration_item;
    }
}
