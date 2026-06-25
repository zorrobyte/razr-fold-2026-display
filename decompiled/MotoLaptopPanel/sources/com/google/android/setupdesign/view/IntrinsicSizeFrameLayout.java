package com.google.android.setupdesign.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.FrameLayout;
import com.google.android.setupcompat.partnerconfig.PartnerConfig;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;
import com.google.android.setupcompat.util.BuildCompatUtils;
import com.google.android.setupdesign.R$attr;
import com.google.android.setupdesign.R$styleable;

/* JADX INFO: loaded from: classes.dex */
public class IntrinsicSizeFrameLayout extends FrameLayout {
    private boolean allowFullScreen;
    private int intrinsicHeight;
    private int intrinsicWidth;
    private Object lastInsets;
    private final Rect windowVisibleDisplayRect;

    public IntrinsicSizeFrameLayout(Context context) {
        super(context);
        this.intrinsicHeight = 0;
        this.intrinsicWidth = 0;
        this.windowVisibleDisplayRect = new Rect();
        this.allowFullScreen = false;
        init(context, null, 0);
    }

    public IntrinsicSizeFrameLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.intrinsicHeight = 0;
        this.intrinsicWidth = 0;
        this.windowVisibleDisplayRect = new Rect();
        this.allowFullScreen = false;
        init(context, attributeSet, R$attr.sudIntrinsicSizeFrameLayoutStyle);
    }

    public IntrinsicSizeFrameLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.intrinsicHeight = 0;
        this.intrinsicWidth = 0;
        this.windowVisibleDisplayRect = new Rect();
        this.allowFullScreen = false;
        init(context, attributeSet, i);
    }

    private int getIntrinsicMeasureSpec(int i, int i2) {
        if (i2 > 0) {
            int mode = View.MeasureSpec.getMode(i);
            int size = View.MeasureSpec.getSize(i);
            if (mode == 0) {
                return View.MeasureSpec.makeMeasureSpec(this.intrinsicHeight, 1073741824);
            }
            if (mode == Integer.MIN_VALUE) {
                return View.MeasureSpec.makeMeasureSpec(Math.min(size, this.intrinsicHeight), 1073741824);
            }
        }
        return i;
    }

    private void init(Context context, AttributeSet attributeSet, int i) {
        if (isInEditMode()) {
            return;
        }
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.SudIntrinsicSizeFrameLayout, i, 0);
        this.intrinsicHeight = typedArrayObtainStyledAttributes.getDimensionPixelSize(R$styleable.SudIntrinsicSizeFrameLayout_android_height, 0);
        this.intrinsicWidth = typedArrayObtainStyledAttributes.getDimensionPixelSize(R$styleable.SudIntrinsicSizeFrameLayout_android_width, 0);
        this.allowFullScreen = typedArrayObtainStyledAttributes.getBoolean(R$styleable.SudIntrinsicSizeFrameLayout_allowFullScreen, false);
        typedArrayObtainStyledAttributes.recycle();
        if (BuildCompatUtils.isAtLeastS()) {
            PartnerConfigHelper partnerConfigHelper = PartnerConfigHelper.get(context);
            PartnerConfig partnerConfig = PartnerConfig.CONFIG_CARD_VIEW_INTRINSIC_HEIGHT;
            if (partnerConfigHelper.isPartnerConfigAvailable(partnerConfig)) {
                this.intrinsicHeight = (int) PartnerConfigHelper.get(context).getDimension(context, partnerConfig);
            }
            PartnerConfigHelper partnerConfigHelper2 = PartnerConfigHelper.get(context);
            PartnerConfig partnerConfig2 = PartnerConfig.CONFIG_CARD_VIEW_INTRINSIC_WIDTH;
            if (partnerConfigHelper2.isPartnerConfigAvailable(partnerConfig2)) {
                this.intrinsicWidth = (int) PartnerConfigHelper.get(context).getDimension(context, partnerConfig2);
            }
        }
    }

    boolean isWindowSizeSmallerThanDisplaySize() {
        this.windowVisibleDisplayRect.set(((WindowManager) getContext().getSystemService(WindowManager.class)).getCurrentWindowMetrics().getBounds());
        Display display = getDisplay();
        if (display == null) {
            return false;
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getRealMetrics(displayMetrics);
        return this.windowVisibleDisplayRect.width() > 0 && this.windowVisibleDisplayRect.width() < displayMetrics.widthPixels;
    }

    @Override // android.view.View
    public WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        this.lastInsets = windowInsets;
        return super.onApplyWindowInsets(windowInsets);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.lastInsets == null) {
            requestApplyInsets();
        }
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        int intrinsicMeasureSpec;
        if (isWindowSizeSmallerThanDisplaySize()) {
            getWindowVisibleDisplayFrame(this.windowVisibleDisplayRect);
            intrinsicMeasureSpec = View.MeasureSpec.makeMeasureSpec(this.windowVisibleDisplayRect.width(), 1073741824);
        } else {
            intrinsicMeasureSpec = getIntrinsicMeasureSpec(i, this.intrinsicWidth);
        }
        super.onMeasure(intrinsicMeasureSpec, getIntrinsicMeasureSpec(i2, this.intrinsicHeight));
    }

    @Override // android.view.View
    public void setLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (BuildCompatUtils.isAtLeastS() && ((this.intrinsicHeight == 0 && this.intrinsicWidth == 0) || this.allowFullScreen)) {
            layoutParams.width = -1;
            layoutParams.height = -1;
            setElevation(0.0f);
        }
        super.setLayoutParams(layoutParams);
    }
}
