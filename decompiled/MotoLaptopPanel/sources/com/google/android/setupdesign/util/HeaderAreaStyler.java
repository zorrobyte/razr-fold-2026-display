package com.google.android.setupdesign.util;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.VectorDrawable;
import android.os.Build;
import android.util.Log;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;
import com.google.android.setupcompat.partnerconfig.PartnerConfig;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;
import com.google.android.setupcompat.util.BuildCompatUtils;
import com.google.android.setupdesign.R$dimen;
import com.google.android.setupdesign.util.TextViewPartnerStyler;

/* JADX INFO: loaded from: classes.dex */
public abstract class HeaderAreaStyler {
    static final String WARN_TO_USE_DRAWABLE = "To achieve scaling icon in SetupDesign lib, should use vector drawable icon from ";

    public static void applyPartnerCustomizationAccountStyle(ImageView imageView, TextView textView, LinearLayout linearLayout) {
        if (imageView == null || textView == null) {
            return;
        }
        Context context = imageView.getContext();
        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
            marginLayoutParams.setMargins(marginLayoutParams.leftMargin, marginLayoutParams.topMargin, (int) PartnerConfigHelper.get(context).getDimension(context, PartnerConfig.CONFIG_ACCOUNT_AVATAR_MARGIN_END), marginLayoutParams.bottomMargin);
        }
        imageView.setMaxHeight((int) PartnerConfigHelper.get(context).getDimension(context, PartnerConfig.CONFIG_ACCOUNT_AVATAR_SIZE, context.getResources().getDimension(R$dimen.sud_account_avatar_max_height)));
        textView.setTextSize(0, (int) PartnerConfigHelper.get(context).getDimension(context, PartnerConfig.CONFIG_ACCOUNT_NAME_TEXT_SIZE, context.getResources().getDimension(R$dimen.sud_account_name_text_size)));
        Typeface typefaceCreate = Typeface.create(PartnerConfigHelper.get(context).getString(context, PartnerConfig.CONFIG_ACCOUNT_NAME_FONT_FAMILY), 0);
        if (typefaceCreate != null) {
            textView.setTypeface(typefaceCreate);
        }
        linearLayout.setGravity(PartnerStyleHelper.getLayoutGravity(linearLayout.getContext()));
    }

    public static void applyPartnerCustomizationBackButtonStyle(FrameLayout frameLayout) {
        if (frameLayout == null) {
            return;
        }
        Context context = frameLayout.getContext();
        ViewGroup.LayoutParams layoutParams = frameLayout.getLayoutParams();
        int dimension = (int) context.getResources().getDimension(R$dimen.sud_glif_expressive_back_button_height);
        int partnerConfigDimension = getPartnerConfigDimension(context, PartnerConfig.CONFIG_ICON_SIZE, 0);
        int i = partnerConfigDimension > dimension ? partnerConfigDimension - dimension : 0;
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
        int partnerConfigDimension2 = getPartnerConfigDimension(context, PartnerConfig.CONFIG_ICON_MARGIN_TOP, marginLayoutParams.topMargin);
        if (i != 0) {
            partnerConfigDimension2 += i / 2;
        }
        if (partnerConfigDimension2 != marginLayoutParams.topMargin) {
            FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(-2, -2);
            layoutParams2.setMargins(marginLayoutParams.leftMargin, partnerConfigDimension2, marginLayoutParams.rightMargin, marginLayoutParams.bottomMargin);
            frameLayout.setLayoutParams(layoutParams2);
        }
    }

    public static void applyPartnerCustomizationDescriptionHeavyStyle(TextView textView) {
        if (textView == null) {
            return;
        }
        TextViewPartnerStyler.applyPartnerCustomizationStyle(textView, new TextViewPartnerStyler.TextPartnerConfigs(PartnerConfig.CONFIG_DESCRIPTION_TEXT_COLOR, PartnerConfig.CONFIG_DESCRIPTION_LINK_TEXT_COLOR, PartnerConfig.CONFIG_DESCRIPTION_TEXT_SIZE, PartnerConfig.CONFIG_DESCRIPTION_FONT_FAMILY, PartnerConfig.CONFIG_DESCRIPTION_FONT_WEIGHT, PartnerConfig.CONFIG_DESCRIPTION_LINK_FONT_FAMILY, PartnerConfig.CONFIG_DESCRIPTION_TEXT_MARGIN_TOP, PartnerConfig.CONFIG_DESCRIPTION_TEXT_MARGIN_BOTTOM, PartnerStyleHelper.getLayoutGravity(textView.getContext())));
    }

    public static void applyPartnerCustomizationHeaderAreaStyle(ViewGroup viewGroup) {
        if (viewGroup == null) {
            return;
        }
        Context context = viewGroup.getContext();
        viewGroup.setBackgroundColor(PartnerConfigHelper.get(context).getColor(context, PartnerConfig.CONFIG_HEADER_AREA_BACKGROUND_COLOR));
        PartnerConfigHelper partnerConfigHelper = PartnerConfigHelper.get(context);
        PartnerConfig partnerConfig = PartnerConfig.CONFIG_HEADER_CONTAINER_MARGIN_BOTTOM;
        if (partnerConfigHelper.isPartnerConfigAvailable(partnerConfig)) {
            ViewGroup.LayoutParams layoutParams = viewGroup.getLayoutParams();
            if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
                marginLayoutParams.setMargins(marginLayoutParams.leftMargin, marginLayoutParams.topMargin, marginLayoutParams.rightMargin, (int) PartnerConfigHelper.get(context).getDimension(context, partnerConfig));
                viewGroup.setLayoutParams(layoutParams);
            }
        }
    }

    public static void applyPartnerCustomizationHeaderStyle(TextView textView) {
        if (textView == null) {
            return;
        }
        TextViewPartnerStyler.applyPartnerCustomizationStyle(textView, new TextViewPartnerStyler.TextPartnerConfigs(PartnerConfig.CONFIG_HEADER_TEXT_COLOR, null, PartnerConfig.CONFIG_HEADER_TEXT_SIZE, PartnerConfig.CONFIG_HEADER_FONT_FAMILY, PartnerConfig.CONFIG_HEADER_FONT_WEIGHT, null, PartnerConfig.CONFIG_HEADER_TEXT_MARGIN_TOP, PartnerConfig.CONFIG_HEADER_TEXT_MARGIN_BOTTOM, PartnerConfig.CONFIG_HEADER_FONT_VARIATION_SETTINGS, PartnerStyleHelper.getLayoutGravity(textView.getContext())));
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0064  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static void applyPartnerCustomizationIconStyle(android.widget.ImageView r4, android.widget.FrameLayout r5) {
        /*
            if (r4 == 0) goto L8e
            if (r5 != 0) goto L6
            goto L8e
        L6:
            android.content.Context r0 = r4.getContext()
            int r1 = com.google.android.setupdesign.util.PartnerStyleHelper.getLayoutGravity(r0)
            if (r1 == 0) goto L19
            boolean r2 = com.google.android.setupcompat.partnerconfig.PartnerConfigHelper.isGlifExpressiveEnabled(r0)
            if (r2 != 0) goto L19
            setGravity(r4, r1)
        L19:
            com.google.android.setupcompat.partnerconfig.PartnerConfigHelper r1 = com.google.android.setupcompat.partnerconfig.PartnerConfigHelper.get(r0)
            com.google.android.setupcompat.partnerconfig.PartnerConfig r2 = com.google.android.setupcompat.partnerconfig.PartnerConfig.CONFIG_ICON_SIZE
            boolean r1 = r1.isPartnerConfigAvailable(r2)
            if (r1 == 0) goto L64
            checkImageType(r4)
            android.view.ViewGroup$LayoutParams r1 = r4.getLayoutParams()
            com.google.android.setupcompat.partnerconfig.PartnerConfigHelper r3 = com.google.android.setupcompat.partnerconfig.PartnerConfigHelper.get(r0)
            float r2 = r3.getDimension(r0, r2)
            int r2 = (int) r2
            r1.height = r2
            r2 = -2
            r1.width = r2
            android.widget.ImageView$ScaleType r2 = android.widget.ImageView.ScaleType.FIT_CENTER
            r4.setScaleType(r2)
            android.graphics.drawable.Drawable r4 = r4.getDrawable()
            if (r4 == 0) goto L64
            int r2 = r4.getIntrinsicWidth()
            int r4 = r4.getIntrinsicHeight()
            int r4 = r4 * 2
            if (r2 <= r4) goto L64
            android.content.res.Resources r4 = r0.getResources()
            int r2 = com.google.android.setupdesign.R$dimen.sud_horizontal_icon_height
            float r4 = r4.getDimension(r2)
            int r4 = (int) r4
            int r2 = r1.height
            if (r2 <= r4) goto L64
            int r2 = r2 - r4
            r1.height = r4
            goto L65
        L64:
            r2 = 0
        L65:
            android.view.ViewGroup$LayoutParams r4 = r5.getLayoutParams()
            com.google.android.setupcompat.partnerconfig.PartnerConfigHelper r5 = com.google.android.setupcompat.partnerconfig.PartnerConfigHelper.get(r0)
            com.google.android.setupcompat.partnerconfig.PartnerConfig r1 = com.google.android.setupcompat.partnerconfig.PartnerConfig.CONFIG_ICON_MARGIN_TOP
            boolean r5 = r5.isPartnerConfigAvailable(r1)
            if (r5 == 0) goto L8e
            boolean r5 = r4 instanceof android.view.ViewGroup.MarginLayoutParams
            if (r5 == 0) goto L8e
            android.view.ViewGroup$MarginLayoutParams r4 = (android.view.ViewGroup.MarginLayoutParams) r4
            com.google.android.setupcompat.partnerconfig.PartnerConfigHelper r5 = com.google.android.setupcompat.partnerconfig.PartnerConfigHelper.get(r0)
            float r5 = r5.getDimension(r0, r1)
            int r5 = (int) r5
            int r5 = r5 + r2
            int r0 = r4.leftMargin
            int r1 = r4.rightMargin
            int r2 = r4.bottomMargin
            r4.setMargins(r0, r5, r1, r2)
        L8e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.setupdesign.util.HeaderAreaStyler.applyPartnerCustomizationIconStyle(android.widget.ImageView, android.widget.FrameLayout):void");
    }

    public static void applyPartnerCustomizationProgressBarStyle(ProgressBar progressBar) {
        if (progressBar == null) {
            return;
        }
        Context context = progressBar.getContext();
        ViewGroup.LayoutParams layoutParams = progressBar.getLayoutParams();
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
            int dimension = marginLayoutParams.topMargin;
            PartnerConfigHelper partnerConfigHelper = PartnerConfigHelper.get(context);
            PartnerConfig partnerConfig = PartnerConfig.CONFIG_PROGRESS_BAR_MARGIN_TOP;
            if (partnerConfigHelper.isPartnerConfigAvailable(partnerConfig)) {
                dimension = (int) PartnerConfigHelper.get(context).getDimension(context, partnerConfig, context.getResources().getDimension(R$dimen.sud_progress_bar_margin_top));
            }
            int dimension2 = marginLayoutParams.bottomMargin;
            PartnerConfigHelper partnerConfigHelper2 = PartnerConfigHelper.get(context);
            PartnerConfig partnerConfig2 = PartnerConfig.CONFIG_PROGRESS_BAR_MARGIN_BOTTOM;
            if (partnerConfigHelper2.isPartnerConfigAvailable(partnerConfig2)) {
                dimension2 = (int) PartnerConfigHelper.get(context).getDimension(context, partnerConfig2, context.getResources().getDimension(R$dimen.sud_progress_bar_margin_bottom));
            }
            if (dimension == marginLayoutParams.topMargin && dimension2 == marginLayoutParams.bottomMargin) {
                return;
            }
            marginLayoutParams.setMargins(marginLayoutParams.leftMargin, dimension, marginLayoutParams.rightMargin, dimension2);
        }
    }

    private static void checkImageType(final ImageView imageView) {
        imageView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() { // from class: com.google.android.setupdesign.util.HeaderAreaStyler.1
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public boolean onPreDraw() {
                imageView.getViewTreeObserver().removeOnPreDrawListener(this);
                if (!BuildCompatUtils.isAtLeastS() || imageView.getDrawable() == null || (imageView.getDrawable() instanceof VectorDrawable) || (imageView.getDrawable() instanceof VectorDrawableCompat)) {
                    return true;
                }
                String str = Build.TYPE;
                if (!str.equals("userdebug") && !str.equals("eng")) {
                    return true;
                }
                Log.w("HeaderAreaStyler", HeaderAreaStyler.WARN_TO_USE_DRAWABLE + imageView.getContext().getPackageName());
                return true;
            }
        });
    }

    private static int getPartnerConfigDimension(Context context, PartnerConfig partnerConfig, int i) {
        return PartnerConfigHelper.get(context).isPartnerConfigAvailable(partnerConfig) ? (int) PartnerConfigHelper.get(context).getDimension(context, partnerConfig) : i;
    }

    private static void setGravity(ImageView imageView, int i) {
        if (imageView.getLayoutParams() instanceof FrameLayout.LayoutParams) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) imageView.getLayoutParams();
            layoutParams.gravity = i;
            imageView.setLayoutParams(layoutParams);
        }
    }
}
