package com.google.android.setupdesign.items;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import com.google.android.setupdesign.R$style;
import com.google.android.setupdesign.R$styleable;

/* JADX INFO: loaded from: classes.dex */
public class ButtonItem extends AbstractItem implements View.OnClickListener {
    private boolean enabled;
    private Drawable icon;
    private CharSequence text;
    private int theme;

    public ButtonItem(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.enabled = true;
        int i = R$style.SudButtonItem;
        this.theme = i;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.SudButtonItem);
        this.enabled = typedArrayObtainStyledAttributes.getBoolean(R$styleable.SudButtonItem_android_enabled, true);
        this.text = typedArrayObtainStyledAttributes.getText(R$styleable.SudButtonItem_android_text);
        this.theme = typedArrayObtainStyledAttributes.getResourceId(R$styleable.SudButtonItem_android_theme, i);
        this.icon = typedArrayObtainStyledAttributes.getDrawable(R$styleable.SudButtonItem_android_icon);
        typedArrayObtainStyledAttributes.recycle();
    }

    @Override // com.google.android.setupdesign.items.AbstractItem, com.google.android.setupdesign.items.ItemHierarchy
    public int getCount() {
        return 0;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
    }
}
