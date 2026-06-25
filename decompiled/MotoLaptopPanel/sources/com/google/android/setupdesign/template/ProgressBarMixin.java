package com.google.android.setupdesign.template;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ProgressBar;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.setupcompat.internal.TemplateLayout;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;
import com.google.android.setupcompat.template.Mixin;
import com.google.android.setupdesign.R$dimen;
import com.google.android.setupdesign.R$id;
import com.google.android.setupdesign.R$styleable;
import com.google.android.setupdesign.util.HeaderAreaStyler;
import com.google.android.setupdesign.util.PartnerStyleHelper;

/* JADX INFO: loaded from: classes.dex */
public class ProgressBarMixin implements Mixin {
    private ColorStateList color;
    private final boolean isGlifExpressiveEnabled;
    private final TemplateLayout templateLayout;
    private final boolean useBottomProgressBar;

    public ProgressBarMixin(TemplateLayout templateLayout, AttributeSet attributeSet, int i) {
        this.templateLayout = templateLayout;
        boolean z = false;
        if (attributeSet != null) {
            TypedArray typedArrayObtainStyledAttributes = templateLayout.getContext().obtainStyledAttributes(attributeSet, R$styleable.SudProgressBarMixin, i, 0);
            int i2 = R$styleable.SudProgressBarMixin_sudUseBottomProgressBar;
            boolean z2 = typedArrayObtainStyledAttributes.hasValue(i2) ? typedArrayObtainStyledAttributes.getBoolean(i2, false) : false;
            typedArrayObtainStyledAttributes.recycle();
            setShown(false);
            z = z2;
        }
        this.useBottomProgressBar = z;
        this.isGlifExpressiveEnabled = PartnerConfigHelper.isGlifExpressiveEnabled(templateLayout.getContext());
    }

    protected View getProgressBar() {
        if (peekProgressBar() == null) {
            if (this.isGlifExpressiveEnabled) {
                ViewStub viewStub = (ViewStub) this.templateLayout.findManagedViewById(R$id.sud_glif_progress_indicator_stub);
                if (viewStub != null) {
                    viewStub.inflate();
                }
            } else if (!this.useBottomProgressBar) {
                ViewStub viewStub2 = (ViewStub) this.templateLayout.findManagedViewById(R$id.sud_layout_progress_stub);
                if (viewStub2 != null) {
                    viewStub2.inflate();
                }
                setColor(this.color);
            }
        }
        return peekProgressBar();
    }

    public ProgressBar peekProgressBar() {
        if (this.isGlifExpressiveEnabled) {
            return (LinearProgressIndicator) this.templateLayout.findManagedViewById(R$id.sud_layout_progress_indicator);
        }
        return (ProgressBar) this.templateLayout.findManagedViewById(this.useBottomProgressBar ? R$id.sud_glif_progress_bar : R$id.sud_layout_progress);
    }

    public void setColor(ColorStateList colorStateList) {
        this.color = colorStateList;
        ProgressBar progressBarPeekProgressBar = peekProgressBar();
        if (progressBarPeekProgressBar != null) {
            progressBarPeekProgressBar.setIndeterminateTintList(colorStateList);
            progressBarPeekProgressBar.setProgressBackgroundTintList(colorStateList);
        }
    }

    public void setShown(boolean z) {
        if (z) {
            View progressBar = getProgressBar();
            if (progressBar != null) {
                progressBar.setVisibility(0);
                return;
            }
            return;
        }
        ProgressBar progressBarPeekProgressBar = peekProgressBar();
        if (progressBarPeekProgressBar != null) {
            progressBarPeekProgressBar.setVisibility(this.useBottomProgressBar ? 4 : 8);
        }
    }

    public void tryApplyPartnerCustomizationStyle() {
        ProgressBar progressBarPeekProgressBar = peekProgressBar();
        if (!this.useBottomProgressBar || progressBarPeekProgressBar == null) {
            return;
        }
        if (PartnerStyleHelper.isPartnerHeavyThemeLayout(this.templateLayout)) {
            HeaderAreaStyler.applyPartnerCustomizationProgressBarStyle(progressBarPeekProgressBar);
            return;
        }
        Context context = progressBarPeekProgressBar.getContext();
        ViewGroup.LayoutParams layoutParams = progressBarPeekProgressBar.getLayoutParams();
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
            marginLayoutParams.setMargins(marginLayoutParams.leftMargin, (int) context.getResources().getDimension(R$dimen.sud_progress_bar_margin_top), marginLayoutParams.rightMargin, (int) context.getResources().getDimension(R$dimen.sud_progress_bar_margin_bottom));
        }
    }
}
