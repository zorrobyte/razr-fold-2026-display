package com.google.android.setupdesign.items;

import android.content.Context;
import android.util.AttributeSet;
import com.google.android.setupdesign.R$layout;

/* JADX INFO: loaded from: classes.dex */
public class NonActionableItem extends Item {
    public NonActionableItem(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // com.google.android.setupdesign.items.Item
    protected int getDefaultLayoutResource() {
        return R$layout.sud_non_actionable_items_default;
    }
}
