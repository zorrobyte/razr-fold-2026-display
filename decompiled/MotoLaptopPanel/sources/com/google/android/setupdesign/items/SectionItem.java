package com.google.android.setupdesign.items;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import com.google.android.setupdesign.R$styleable;

/* JADX INFO: loaded from: classes.dex */
public class SectionItem extends ItemGroup {
    private final Item header;

    public SectionItem(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.SudSectionItem);
        CharSequence text = typedArrayObtainStyledAttributes.getText(R$styleable.SudSectionItem_android_title);
        typedArrayObtainStyledAttributes.recycle();
        SectionHeaderItem sectionHeaderItem = new SectionHeaderItem();
        this.header = sectionHeaderItem;
        sectionHeaderItem.setTitle(text);
        sectionHeaderItem.setVisible(false);
        addChild(sectionHeaderItem);
    }

    private void refreshHeader() {
        if (this.header.isVisible()) {
            if (getCount() == 1) {
                this.header.setVisible(false);
            }
        } else {
            if (getCount() <= 0 || this.header.getTitle() == null) {
                return;
            }
            this.header.setVisible(true);
        }
    }

    @Override // com.google.android.setupdesign.items.ItemGroup
    public void addChild(ItemHierarchy itemHierarchy) {
        super.addChild(itemHierarchy);
        refreshHeader();
    }

    @Override // com.google.android.setupdesign.items.ItemGroup, com.google.android.setupdesign.items.ItemHierarchy.Observer
    public void onItemRangeInserted(ItemHierarchy itemHierarchy, int i, int i2) {
        super.onItemRangeInserted(itemHierarchy, i, i2);
        refreshHeader();
    }

    @Override // com.google.android.setupdesign.items.ItemGroup, com.google.android.setupdesign.items.ItemHierarchy.Observer
    public void onItemRangeRemoved(ItemHierarchy itemHierarchy, int i, int i2) {
        super.onItemRangeRemoved(itemHierarchy, i, i2);
        refreshHeader();
    }
}
