package com.google.android.setupdesign.items;

import android.content.Context;
import android.util.AttributeSet;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class ButtonBarItem extends AbstractItem {
    private final ArrayList buttons;
    private boolean visible;

    public ButtonBarItem(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.buttons = new ArrayList();
        this.visible = true;
    }

    @Override // com.google.android.setupdesign.items.AbstractItem, com.google.android.setupdesign.items.ItemHierarchy
    public int getCount() {
        return isVisible() ? 1 : 0;
    }

    public boolean isVisible() {
        return this.visible;
    }
}
