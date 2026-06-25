package com.google.android.setupcompat.template;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import com.google.android.setupcompat.PartnerCustomizationLayout;
import com.google.android.setupcompat.R$id;
import com.google.android.setupcompat.R$styleable;
import com.google.android.setupcompat.partnerconfig.PartnerConfig;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;
import com.google.android.setupcompat.view.StatusBarBackgroundLayout;

/* JADX INFO: loaded from: classes.dex */
public class StatusBarMixin implements Mixin {
    private final View decorView;
    private LinearLayout linearLayout;
    private final PartnerCustomizationLayout partnerCustomizationLayout;
    private StatusBarBackgroundLayout statusBarLayout;

    public StatusBarMixin(PartnerCustomizationLayout partnerCustomizationLayout, Window window, AttributeSet attributeSet, int i) {
        this.partnerCustomizationLayout = partnerCustomizationLayout;
        View viewFindManagedViewById = partnerCustomizationLayout.findManagedViewById(R$id.suc_layout_status);
        if (viewFindManagedViewById == null) {
            throw new NullPointerException("sucLayoutStatus cannot be null in StatusBarMixin");
        }
        if (viewFindManagedViewById instanceof StatusBarBackgroundLayout) {
            this.statusBarLayout = (StatusBarBackgroundLayout) viewFindManagedViewById;
        } else {
            this.linearLayout = (LinearLayout) viewFindManagedViewById;
        }
        this.decorView = window.getDecorView();
        window.setStatusBarColor(0);
        TypedArray typedArrayObtainStyledAttributes = partnerCustomizationLayout.getContext().obtainStyledAttributes(attributeSet, R$styleable.SucStatusBarMixin, i, 0);
        setLightStatusBar(typedArrayObtainStyledAttributes.getBoolean(R$styleable.SucStatusBarMixin_sucLightStatusBar, isLightStatusBar()));
        setStatusBarBackground(typedArrayObtainStyledAttributes.getDrawable(R$styleable.SucStatusBarMixin_sucStatusBarBackground));
        typedArrayObtainStyledAttributes.recycle();
    }

    public boolean isLightStatusBar() {
        return (this.decorView.getSystemUiVisibility() & 8192) == 8192;
    }

    public void setLightStatusBar(boolean z) {
        if (this.partnerCustomizationLayout.shouldApplyPartnerResource()) {
            Context context = this.partnerCustomizationLayout.getContext();
            z = PartnerConfigHelper.get(context).getBoolean(context, PartnerConfig.CONFIG_LIGHT_STATUS_BAR, false);
        }
        if (z) {
            View view = this.decorView;
            view.setSystemUiVisibility(view.getSystemUiVisibility() | 8192);
        } else {
            View view2 = this.decorView;
            view2.setSystemUiVisibility(view2.getSystemUiVisibility() & (-8193));
        }
    }

    public void setStatusBarBackground(Drawable drawable) {
        if (this.partnerCustomizationLayout.shouldApplyPartnerResource() && !this.partnerCustomizationLayout.useFullDynamicColor()) {
            Context context = this.partnerCustomizationLayout.getContext();
            drawable = PartnerConfigHelper.get(context).getDrawable(context, PartnerConfig.CONFIG_STATUS_BAR_BACKGROUND);
        }
        StatusBarBackgroundLayout statusBarBackgroundLayout = this.statusBarLayout;
        if (statusBarBackgroundLayout == null) {
            this.linearLayout.setBackgroundDrawable(drawable);
        } else {
            statusBarBackgroundLayout.setStatusBarBackground(drawable);
        }
    }
}
