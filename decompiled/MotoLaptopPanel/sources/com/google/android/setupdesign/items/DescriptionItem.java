package com.google.android.setupdesign.items;

import android.content.Context;
import android.util.AttributeSet;

/* JADX INFO: loaded from: classes.dex */
@Deprecated
public class DescriptionItem extends Item {
    private boolean partnerDescriptionHeavyStyle;
    private boolean partnerDescriptionLightStyle;

    public DescriptionItem(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.partnerDescriptionHeavyStyle = false;
        this.partnerDescriptionLightStyle = false;
    }
}
