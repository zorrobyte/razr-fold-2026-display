package com.google.android.setupdesign.items;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import com.google.android.setupdesign.R$layout;
import com.google.android.setupdesign.R$styleable;

/* JADX INFO: loaded from: classes.dex */
public class LottieIllustrationItem extends Item {
    private int animationId;

    public LottieIllustrationItem(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.SudIllustrationItem);
        this.animationId = typedArrayObtainStyledAttributes.getResourceId(R$styleable.SudIllustrationItem_sudAnimationId, 0);
        typedArrayObtainStyledAttributes.recycle();
    }

    @Override // com.google.android.setupdesign.items.Item
    protected int getDefaultLayoutResource() {
        return R$layout.sud_lottie_illustration_item;
    }
}
