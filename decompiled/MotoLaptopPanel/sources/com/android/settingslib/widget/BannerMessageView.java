package com.android.settingslib.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.TouchDelegate;
import android.view.View;
import android.widget.LinearLayout;
import com.android.settingslib.widget.preference.banner.R$id;
import com.android.settingslib.widget.theme.R$dimen;

/* JADX INFO: loaded from: classes.dex */
public class BannerMessageView extends LinearLayout {
    private Rect mTouchTargetForDismissButton;

    public BannerMessageView(Context context) {
        super(context);
    }

    public BannerMessageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public BannerMessageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public BannerMessageView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    private void setupIncreaseTouchTargetForDismissButton() {
        if (this.mTouchTargetForDismissButton != null) {
            return;
        }
        View viewFindViewById = findViewById(R$id.top_row);
        View viewFindViewById2 = findViewById(R$id.banner_dismiss_btn);
        if (viewFindViewById == null || viewFindViewById2 == null || viewFindViewById2.getVisibility() != 0) {
            return;
        }
        int dimensionPixelSize = getResources().getDimensionPixelSize(R$dimen.settingslib_preferred_minimum_touch_target);
        int width = viewFindViewById2.getWidth();
        int height = viewFindViewById2.getHeight();
        int i = width < dimensionPixelSize ? dimensionPixelSize - width : 0;
        int i2 = height < dimensionPixelSize ? dimensionPixelSize - height : 0;
        Rect rect = new Rect();
        viewFindViewById2.getHitRect(rect);
        Rect rect2 = new Rect();
        viewFindViewById.getHitRect(rect2);
        Rect rect3 = new Rect();
        this.mTouchTargetForDismissButton = rect3;
        int i3 = rect2.left + rect.left;
        rect3.left = i3;
        int i4 = rect2.left + rect.right;
        rect3.right = i4;
        int i5 = rect2.top + rect.top;
        rect3.top = i5;
        int i6 = rect2.top + rect.bottom;
        rect3.bottom = i6;
        rect3.left = i3 - (i % 2 == 1 ? (i / 2) + 1 : i / 2);
        rect3.top = i5 - (i2 % 2 == 1 ? (i2 / 2) + 1 : i2 / 2);
        rect3.right = i4 + (i / 2);
        rect3.bottom = i6 + (i2 / 2);
        setTouchDelegate(new TouchDelegate(this.mTouchTargetForDismissButton, viewFindViewById2));
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        setupIncreaseTouchTargetForDismissButton();
    }
}
