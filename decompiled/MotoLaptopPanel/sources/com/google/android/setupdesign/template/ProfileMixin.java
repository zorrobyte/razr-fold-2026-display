package com.google.android.setupdesign.template;

import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.setupcompat.internal.TemplateLayout;
import com.google.android.setupcompat.template.Mixin;
import com.google.android.setupdesign.R$id;
import com.google.android.setupdesign.util.HeaderAreaStyler;
import com.google.android.setupdesign.util.LayoutStyler;
import com.google.android.setupdesign.util.PartnerStyleHelper;

/* JADX INFO: loaded from: classes.dex */
public class ProfileMixin implements Mixin {
    private final TemplateLayout templateLayout;

    public ProfileMixin(TemplateLayout templateLayout, AttributeSet attributeSet, int i) {
        this.templateLayout = templateLayout;
    }

    private ImageView getAccountAvatarView() {
        return (ImageView) this.templateLayout.findManagedViewById(R$id.sud_account_avatar);
    }

    private TextView getAccountNameView() {
        return (TextView) this.templateLayout.findManagedViewById(R$id.sud_account_name);
    }

    private LinearLayout getContainerView() {
        return (LinearLayout) this.templateLayout.findManagedViewById(R$id.sud_layout_profile);
    }

    public void tryApplyPartnerCustomizationStyle() {
        if (PartnerStyleHelper.shouldApplyPartnerResource(this.templateLayout)) {
            ImageView accountAvatarView = getAccountAvatarView();
            TextView accountNameView = getAccountNameView();
            LinearLayout containerView = getContainerView();
            LayoutStyler.applyPartnerCustomizationExtraPaddingStyle(this.templateLayout.findManagedViewById(R$id.sud_layout_header));
            HeaderAreaStyler.applyPartnerCustomizationAccountStyle(accountAvatarView, accountNameView, containerView);
        }
    }
}
