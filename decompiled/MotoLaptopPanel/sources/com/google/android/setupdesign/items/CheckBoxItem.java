package com.google.android.setupdesign.items;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.CompoundButton;
import com.google.android.setupdesign.R$layout;
import com.google.android.setupdesign.R$styleable;

/* JADX INFO: loaded from: classes.dex */
public class CheckBoxItem extends Item implements CompoundButton.OnCheckedChangeListener {
    private boolean checked;

    public CheckBoxItem(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.checked = false;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.SudCheckBoxItem);
        this.checked = typedArrayObtainStyledAttributes.getBoolean(R$styleable.SudCheckBoxItem_android_checked, false);
        typedArrayObtainStyledAttributes.recycle();
    }

    @Override // com.google.android.setupdesign.items.Item
    protected int getDefaultLayoutResource() {
        return R$layout.sud_items_check_box;
    }

    @Override // android.widget.CompoundButton.OnCheckedChangeListener
    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        this.checked = z;
    }
}
