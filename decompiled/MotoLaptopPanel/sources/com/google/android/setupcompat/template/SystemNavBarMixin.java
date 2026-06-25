package com.google.android.setupcompat.template;

import android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Window;
import com.google.android.setupcompat.PartnerCustomizationLayout;
import com.google.android.setupcompat.R$styleable;
import com.google.android.setupcompat.internal.TemplateLayout;
import com.google.android.setupcompat.partnerconfig.PartnerConfig;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;

/* JADX INFO: loaded from: classes.dex */
public class SystemNavBarMixin implements Mixin {
    final boolean applyPartnerResources;
    private int sucSystemNavBarBackgroundColor = 0;
    private final TemplateLayout templateLayout;
    final boolean useFullDynamicColor;
    private final Window windowOfActivity;

    public SystemNavBarMixin(TemplateLayout templateLayout, Window window) {
        boolean z = false;
        this.templateLayout = templateLayout;
        this.windowOfActivity = window;
        boolean z2 = templateLayout instanceof PartnerCustomizationLayout;
        this.applyPartnerResources = z2 && ((PartnerCustomizationLayout) templateLayout).shouldApplyPartnerResource();
        if (z2 && ((PartnerCustomizationLayout) templateLayout).useFullDynamicColor()) {
            z = true;
        }
        this.useFullDynamicColor = z;
    }

    public void applyPartnerCustomizations(AttributeSet attributeSet, int i) {
        TypedArray typedArrayObtainStyledAttributes = this.templateLayout.getContext().obtainStyledAttributes(attributeSet, R$styleable.SucSystemNavBarMixin, i, 0);
        int color = typedArrayObtainStyledAttributes.getColor(R$styleable.SucSystemNavBarMixin_sucSystemNavBarBackgroundColor, 0);
        this.sucSystemNavBarBackgroundColor = color;
        setSystemNavBarBackground(color);
        setLightSystemNavBar(typedArrayObtainStyledAttributes.getBoolean(R$styleable.SucSystemNavBarMixin_sucLightSystemNavBar, isLightSystemNavBar()));
        TypedArray typedArrayObtainStyledAttributes2 = this.templateLayout.getContext().obtainStyledAttributes(new int[]{R.attr.navigationBarDividerColor});
        setSystemNavBarDividerColor(typedArrayObtainStyledAttributes.getColor(R$styleable.SucSystemNavBarMixin_sucSystemNavBarDividerColor, typedArrayObtainStyledAttributes2.getColor(0, 0)));
        typedArrayObtainStyledAttributes2.recycle();
        typedArrayObtainStyledAttributes.recycle();
    }

    public boolean isLightSystemNavBar() {
        Window window = this.windowOfActivity;
        return window == null || (window.getDecorView().getSystemUiVisibility() & 16) == 16;
    }

    public void setLightSystemNavBar(boolean z) {
        if (this.windowOfActivity != null) {
            if (this.applyPartnerResources) {
                Context context = this.templateLayout.getContext();
                z = PartnerConfigHelper.get(context).getBoolean(context, PartnerConfig.CONFIG_LIGHT_NAVIGATION_BAR, false);
            }
            if (z) {
                this.windowOfActivity.getDecorView().setSystemUiVisibility(this.windowOfActivity.getDecorView().getSystemUiVisibility() | 16);
            } else {
                this.windowOfActivity.getDecorView().setSystemUiVisibility(this.windowOfActivity.getDecorView().getSystemUiVisibility() & (-17));
            }
        }
    }

    public void setSystemNavBarBackground(int i) {
        if (this.windowOfActivity != null) {
            if (this.applyPartnerResources && !this.useFullDynamicColor) {
                Context context = this.templateLayout.getContext();
                i = PartnerConfigHelper.get(context).getColor(context, PartnerConfig.CONFIG_NAVIGATION_BAR_BG_COLOR);
            }
            this.windowOfActivity.setNavigationBarColor(i);
        }
    }

    public void setSystemNavBarDividerColor(int i) {
        if (this.windowOfActivity != null) {
            if (this.applyPartnerResources) {
                Context context = this.templateLayout.getContext();
                PartnerConfigHelper partnerConfigHelper = PartnerConfigHelper.get(context);
                PartnerConfig partnerConfig = PartnerConfig.CONFIG_NAVIGATION_BAR_DIVIDER_COLOR;
                if (partnerConfigHelper.isPartnerConfigAvailable(partnerConfig)) {
                    i = PartnerConfigHelper.get(context).getColor(context, partnerConfig);
                }
            }
            this.windowOfActivity.setNavigationBarDividerColor(i);
        }
    }
}
