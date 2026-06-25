package com.google.android.setupdesign.template;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import com.google.android.setupcompat.R$styleable;
import com.google.android.setupcompat.internal.TemplateLayout;
import com.google.android.setupcompat.partnerconfig.PartnerConfig;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;
import com.google.android.setupcompat.template.Mixin;
import com.google.android.setupdesign.R$id;
import com.google.android.setupdesign.util.HeaderAreaStyler;
import com.google.android.setupdesign.util.LayoutStyler;
import com.google.android.setupdesign.util.PartnerStyleHelper;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class HeaderMixin implements Mixin {
    private int defaultLineHeight;
    private float defaultTextSize;
    private float headerAutoSizeLineExtraSpacingInPx;
    private int headerAutoSizeMaxLineOfMaxSize;
    private float headerAutoSizeMaxTextSizeInPx;
    private float headerAutoSizeMinTextSizeInPx;
    private final TemplateLayout templateLayout;
    boolean autoTextSizeEnabled = false;
    ArrayList titlePreDrawListeners = new ArrayList();

    public HeaderMixin(TemplateLayout templateLayout, AttributeSet attributeSet, int i) {
        this.defaultTextSize = 0.0f;
        this.defaultLineHeight = 0;
        this.templateLayout = templateLayout;
        TypedArray typedArrayObtainStyledAttributes = templateLayout.getContext().obtainStyledAttributes(attributeSet, R$styleable.SucHeaderMixin, i, 0);
        CharSequence text = typedArrayObtainStyledAttributes.getText(R$styleable.SucHeaderMixin_sucHeaderText);
        ColorStateList colorStateList = typedArrayObtainStyledAttributes.getColorStateList(R$styleable.SucHeaderMixin_sucHeaderTextColor);
        typedArrayObtainStyledAttributes.recycle();
        if (getTextView() != null) {
            this.defaultTextSize = getTextView().getTextSize();
            this.defaultLineHeight = getTextView().getLineHeight();
        }
        tryUpdateAutoTextSizeFlagWithPartnerConfig();
        if (text != null) {
            setText(text);
        }
        if (colorStateList != null) {
            setTextColor(colorStateList);
        }
    }

    private void autoAdjustTextSize(final TextView textView) {
        if (textView == null) {
            return;
        }
        textView.setTextSize(0, this.headerAutoSizeMaxTextSizeInPx);
        this.defaultTextSize = textView.getTextSize();
        textView.setLineHeight(Math.round(this.headerAutoSizeLineExtraSpacingInPx + this.headerAutoSizeMaxTextSizeInPx));
        this.defaultLineHeight = textView.getLineHeight();
        textView.setMaxLines(6);
        ViewTreeObserver.OnPreDrawListener onPreDrawListener = new ViewTreeObserver.OnPreDrawListener() { // from class: com.google.android.setupdesign.template.HeaderMixin.1
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public boolean onPreDraw() {
                textView.getViewTreeObserver().removeOnPreDrawListener(this);
                if (textView.getLineCount() <= HeaderMixin.this.headerAutoSizeMaxLineOfMaxSize) {
                    return true;
                }
                textView.setTextSize(0, HeaderMixin.this.headerAutoSizeMinTextSizeInPx);
                textView.setLineHeight(Math.round(HeaderMixin.this.headerAutoSizeLineExtraSpacingInPx + HeaderMixin.this.headerAutoSizeMinTextSizeInPx));
                textView.invalidate();
                return false;
            }
        };
        textView.getViewTreeObserver().addOnPreDrawListener(onPreDrawListener);
        this.titlePreDrawListeners.add(onPreDrawListener);
    }

    private void tryUpdateAutoTextConfigWithPartnerConfig() {
        Context context = this.templateLayout.getContext();
        PartnerConfigHelper partnerConfigHelper = PartnerConfigHelper.get(context);
        PartnerConfig partnerConfig = PartnerConfig.CONFIG_HEADER_AUTO_SIZE_MAX_TEXT_SIZE;
        if (partnerConfigHelper.isPartnerConfigAvailable(partnerConfig)) {
            this.headerAutoSizeMaxTextSizeInPx = PartnerConfigHelper.get(context).getDimension(context, partnerConfig);
        }
        PartnerConfigHelper partnerConfigHelper2 = PartnerConfigHelper.get(context);
        PartnerConfig partnerConfig2 = PartnerConfig.CONFIG_HEADER_AUTO_SIZE_MIN_TEXT_SIZE;
        if (partnerConfigHelper2.isPartnerConfigAvailable(partnerConfig2)) {
            this.headerAutoSizeMinTextSizeInPx = PartnerConfigHelper.get(context).getDimension(context, partnerConfig2);
        }
        PartnerConfigHelper partnerConfigHelper3 = PartnerConfigHelper.get(context);
        PartnerConfig partnerConfig3 = PartnerConfig.CONFIG_HEADER_AUTO_SIZE_LINE_SPACING_EXTRA;
        if (partnerConfigHelper3.isPartnerConfigAvailable(partnerConfig3)) {
            this.headerAutoSizeLineExtraSpacingInPx = PartnerConfigHelper.get(context).getDimension(context, partnerConfig3);
        }
        PartnerConfigHelper partnerConfigHelper4 = PartnerConfigHelper.get(context);
        PartnerConfig partnerConfig4 = PartnerConfig.CONFIG_HEADER_AUTO_SIZE_MAX_LINE_OF_MAX_SIZE;
        if (partnerConfigHelper4.isPartnerConfigAvailable(partnerConfig4)) {
            this.headerAutoSizeMaxLineOfMaxSize = PartnerConfigHelper.get(context).getInteger(context, partnerConfig4, 0);
        }
        if (this.headerAutoSizeMaxLineOfMaxSize >= 1) {
            float f = this.headerAutoSizeMinTextSizeInPx;
            if (f > 0.0f && this.headerAutoSizeMaxTextSizeInPx >= f) {
                return;
            }
        }
        Log.w("HeaderMixin", "Invalid configs, disable auto text size.");
        this.autoTextSizeEnabled = false;
    }

    private void tryUpdateAutoTextSizeFlagWithPartnerConfig() {
        Context context = this.templateLayout.getContext();
        if (!PartnerStyleHelper.shouldApplyPartnerResource(this.templateLayout)) {
            this.autoTextSizeEnabled = false;
            return;
        }
        PartnerConfigHelper partnerConfigHelper = PartnerConfigHelper.get(context);
        PartnerConfig partnerConfig = PartnerConfig.CONFIG_HEADER_AUTO_SIZE_ENABLED;
        if (partnerConfigHelper.isPartnerConfigAvailable(partnerConfig)) {
            this.autoTextSizeEnabled = PartnerConfigHelper.get(context).getBoolean(context, partnerConfig, this.autoTextSizeEnabled);
        }
        if (this.autoTextSizeEnabled) {
            tryUpdateAutoTextConfigWithPartnerConfig();
        }
    }

    public TextView getTextView() {
        return (TextView) this.templateLayout.findManagedViewById(R$id.suc_layout_title);
    }

    public void setText(CharSequence charSequence) {
        TextView textView = getTextView();
        if (textView != null) {
            if (this.autoTextSizeEnabled) {
                autoAdjustTextSize(textView);
            }
            textView.setText(charSequence);
        }
    }

    public void setTextColor(ColorStateList colorStateList) {
        TextView textView = getTextView();
        if (textView != null) {
            textView.setTextColor(colorStateList);
        }
    }

    public void tryApplyPartnerCustomizationStyle() {
        TextView textView = (TextView) this.templateLayout.findManagedViewById(R$id.suc_layout_title);
        if (PartnerStyleHelper.shouldApplyPartnerResource(this.templateLayout)) {
            View viewFindManagedViewById = this.templateLayout.findManagedViewById(R$id.sud_layout_header);
            LayoutStyler.applyPartnerCustomizationExtraPaddingStyle(viewFindManagedViewById);
            HeaderAreaStyler.applyPartnerCustomizationHeaderStyle(textView);
            HeaderAreaStyler.applyPartnerCustomizationHeaderAreaStyle((ViewGroup) viewFindManagedViewById);
        }
        tryUpdateAutoTextSizeFlagWithPartnerConfig();
        if (this.autoTextSizeEnabled) {
            autoAdjustTextSize(textView);
        }
    }
}
