package com.google.android.setupdesign.template;

import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;
import com.google.android.setupcompat.internal.TemplateLayout;
import com.google.android.setupcompat.template.Mixin;
import com.google.android.setupdesign.R$id;
import com.google.android.setupdesign.R$styleable;
import com.google.android.setupdesign.util.HeaderAreaStyler;
import com.google.android.setupdesign.util.PartnerStyleHelper;

/* JADX INFO: loaded from: classes.dex */
public class DescriptionMixin implements Mixin {
    private final TemplateLayout templateLayout;

    public DescriptionMixin(TemplateLayout templateLayout, AttributeSet attributeSet, int i) {
        this.templateLayout = templateLayout;
        TypedArray typedArrayObtainStyledAttributes = templateLayout.getContext().obtainStyledAttributes(attributeSet, R$styleable.SudDescriptionMixin, i, 0);
        CharSequence text = typedArrayObtainStyledAttributes.getText(R$styleable.SudDescriptionMixin_sudDescriptionText);
        if (text != null) {
            setText(text);
        }
        ColorStateList colorStateList = typedArrayObtainStyledAttributes.getColorStateList(R$styleable.SudDescriptionMixin_sudDescriptionTextColor);
        if (colorStateList != null) {
            setTextColor(colorStateList);
        }
        typedArrayObtainStyledAttributes.recycle();
    }

    public TextView getTextView() {
        return (TextView) this.templateLayout.findManagedViewById(R$id.sud_layout_subtitle);
    }

    public void setText(CharSequence charSequence) {
        TextView textView = getTextView();
        if (textView != null) {
            textView.setText(charSequence);
            setVisibility(0);
        }
    }

    public void setTextColor(ColorStateList colorStateList) {
        TextView textView = getTextView();
        if (textView != null) {
            textView.setTextColor(colorStateList);
        }
    }

    public void setVisibility(int i) {
        TextView textView = getTextView();
        if (textView != null) {
            textView.setVisibility(i);
        }
    }

    public void tryApplyPartnerCustomizationStyle() {
        TextView textView = (TextView) this.templateLayout.findManagedViewById(R$id.sud_layout_subtitle);
        if (textView == null || !PartnerStyleHelper.shouldApplyPartnerResource(this.templateLayout)) {
            return;
        }
        HeaderAreaStyler.applyPartnerCustomizationDescriptionHeavyStyle(textView);
    }
}
