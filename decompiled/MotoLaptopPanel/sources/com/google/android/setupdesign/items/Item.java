package com.google.android.setupdesign.items;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import com.google.android.setupdesign.R$layout;
import com.google.android.setupdesign.R$styleable;
import com.google.android.setupdesign.span.LinkSpan;

/* JADX INFO: loaded from: classes.dex */
public class Item extends AbstractItem implements LinkSpan.OnLinkClickListener {
    private CharSequence contentDescription;
    private boolean enabled;
    private Drawable icon;
    private int iconGravity;
    private int iconTint;
    private int layoutRes;
    private CharSequence summary;
    private CharSequence title;
    private boolean visible;

    public Item() {
        this.enabled = true;
        this.visible = true;
        this.iconTint = 0;
        this.iconGravity = 16;
        this.layoutRes = getDefaultLayoutResource();
    }

    public Item(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.enabled = true;
        this.visible = true;
        this.iconTint = 0;
        this.iconGravity = 16;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.SudItem);
        this.enabled = typedArrayObtainStyledAttributes.getBoolean(R$styleable.SudItem_android_enabled, true);
        this.icon = typedArrayObtainStyledAttributes.getDrawable(R$styleable.SudItem_android_icon);
        this.title = typedArrayObtainStyledAttributes.getText(R$styleable.SudItem_android_title);
        this.summary = typedArrayObtainStyledAttributes.getText(R$styleable.SudItem_android_summary);
        this.contentDescription = typedArrayObtainStyledAttributes.getText(R$styleable.SudItem_android_contentDescription);
        this.layoutRes = typedArrayObtainStyledAttributes.getResourceId(R$styleable.SudItem_android_layout, getDefaultLayoutResource());
        this.visible = typedArrayObtainStyledAttributes.getBoolean(R$styleable.SudItem_android_visible, true);
        this.iconTint = typedArrayObtainStyledAttributes.getColor(R$styleable.SudItem_sudIconTint, 0);
        this.iconGravity = typedArrayObtainStyledAttributes.getInt(R$styleable.SudItem_sudIconGravity, 16);
        typedArrayObtainStyledAttributes.recycle();
    }

    @Override // com.google.android.setupdesign.items.AbstractItem, com.google.android.setupdesign.items.ItemHierarchy
    public int getCount() {
        return isVisible() ? 1 : 0;
    }

    protected int getDefaultLayoutResource() {
        return R$layout.sud_items_default;
    }

    public CharSequence getTitle() {
        return this.title;
    }

    public boolean isVisible() {
        return this.visible;
    }

    @Override // com.google.android.setupdesign.span.LinkSpan.OnLinkClickListener
    public boolean onLinkClick(LinkSpan linkSpan) {
        return false;
    }

    public void setIconGravity(int i) {
        this.iconGravity = i;
    }

    public void setLayoutResource(int i) {
        this.layoutRes = i;
        notifyItemChanged();
    }

    public void setTitle(CharSequence charSequence) {
        this.title = charSequence;
        notifyItemChanged();
    }

    public void setVisible(boolean z) {
        if (this.visible == z) {
            return;
        }
        this.visible = z;
        if (z) {
            notifyItemRangeInserted(0, 1);
        } else {
            notifyItemRangeRemoved(0, 1);
        }
    }
}
