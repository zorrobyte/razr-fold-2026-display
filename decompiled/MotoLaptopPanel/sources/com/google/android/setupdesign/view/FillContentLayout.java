package com.google.android.setupdesign.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.google.android.setupcompat.partnerconfig.PartnerConfig;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;
import com.google.android.setupdesign.R$attr;
import com.google.android.setupdesign.R$styleable;

/* JADX INFO: loaded from: classes.dex */
public class FillContentLayout extends FrameLayout {
    private int maxHeight;
    private int maxWidth;

    public FillContentLayout(Context context) {
        this(context, null);
    }

    public FillContentLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R$attr.sudFillContentLayoutStyle);
    }

    public FillContentLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context, attributeSet, i);
    }

    private static int getMaxSizeMeasureSpec(int i, int i2, int i3) {
        int iMax = Math.max(0, i - i2);
        if (i3 >= 0) {
            return View.MeasureSpec.makeMeasureSpec(i3, 1073741824);
        }
        if (i3 == -1) {
            return View.MeasureSpec.makeMeasureSpec(iMax, 1073741824);
        }
        if (i3 == -2) {
            return View.MeasureSpec.makeMeasureSpec(iMax, Integer.MIN_VALUE);
        }
        return 0;
    }

    private void init(Context context, AttributeSet attributeSet, int i) {
        if (isInEditMode()) {
            return;
        }
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.SudFillContentLayout, i, 0);
        PartnerConfigHelper partnerConfigHelper = PartnerConfigHelper.get(context);
        PartnerConfig partnerConfig = PartnerConfig.CONFIG_ILLUSTRATION_MAX_HEIGHT;
        if (partnerConfigHelper.isPartnerConfigAvailable(partnerConfig)) {
            this.maxHeight = (int) PartnerConfigHelper.get(context).getDimension(context, partnerConfig);
        } else {
            this.maxHeight = typedArrayObtainStyledAttributes.getDimensionPixelSize(R$styleable.SudFillContentLayout_android_maxHeight, -1);
        }
        PartnerConfigHelper partnerConfigHelper2 = PartnerConfigHelper.get(context);
        PartnerConfig partnerConfig2 = PartnerConfig.CONFIG_ILLUSTRATION_MAX_WIDTH;
        if (partnerConfigHelper2.isPartnerConfigAvailable(partnerConfig2)) {
            this.maxWidth = (int) PartnerConfigHelper.get(context).getDimension(context, partnerConfig2);
        } else {
            this.maxWidth = typedArrayObtainStyledAttributes.getDimensionPixelSize(R$styleable.SudFillContentLayout_android_maxWidth, -1);
        }
        typedArrayObtainStyledAttributes.recycle();
    }

    private void measureIllustrationChild(View view, int i, int i2) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        view.measure(getMaxSizeMeasureSpec(Math.min(this.maxWidth, i), getPaddingLeft() + getPaddingRight() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin, marginLayoutParams.width), getMaxSizeMeasureSpec(Math.min(this.maxHeight, i2), getPaddingTop() + getPaddingBottom() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin, marginLayoutParams.height));
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        setMeasuredDimension(FrameLayout.getDefaultSize(getSuggestedMinimumWidth(), i), FrameLayout.getDefaultSize(getSuggestedMinimumHeight(), i2));
        int childCount = getChildCount();
        for (int i3 = 0; i3 < childCount; i3++) {
            measureIllustrationChild(getChildAt(i3), getMeasuredWidth(), getMeasuredHeight());
        }
    }
}
