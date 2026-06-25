package com.google.android.setupcompat.template;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.util.StateSet;
import android.view.ViewGroup;
import android.widget.Button;
import com.google.android.setupcompat.internal.FooterButtonPartnerConfig;
import com.google.android.setupcompat.internal.Preconditions;
import com.google.android.setupcompat.partnerconfig.PartnerConfig;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public abstract class FooterButtonStyleUtils {
    private static final HashMap defaultTextColor = new HashMap();

    static void applyButtonPartnerResources(Context context, Button button, boolean z, boolean z2, FooterButtonPartnerConfig footerButtonPartnerConfig) {
        saveButtonDefaultTextColor(button);
        if (!z) {
            if (button.isEnabled()) {
                updateButtonTextEnabledColorWithPartnerConfig(context, button, footerButtonPartnerConfig.getButtonTextColorConfig());
            } else {
                updateButtonTextDisabledColorWithPartnerConfig(context, button, footerButtonPartnerConfig.getButtonDisableTextColorConfig());
            }
            updateButtonBackgroundWithPartnerConfig(context, button, footerButtonPartnerConfig.getButtonBackgroundConfig(), footerButtonPartnerConfig.getButtonDisableAlphaConfig(), footerButtonPartnerConfig.getButtonDisableBackgroundConfig());
        }
        updateButtonRippleColorWithPartnerConfig(context, button, z, footerButtonPartnerConfig.getButtonTextColorConfig(), footerButtonPartnerConfig.getButtonRippleColorAlphaConfig());
        updateButtonMarginStartWithPartnerConfig(context, button, footerButtonPartnerConfig.getButtonMarginStartConfig());
        updateButtonTextSizeWithPartnerConfig(context, button, footerButtonPartnerConfig.getButtonTextSizeConfig());
        updateButtonMinHeightWithPartnerConfig(context, button, footerButtonPartnerConfig.getButtonMinHeightConfig());
        updateButtonTypeFaceWithPartnerConfig(context, button, footerButtonPartnerConfig.getButtonTextTypeFaceConfig(), footerButtonPartnerConfig.getButtonTextWeightConfig(), footerButtonPartnerConfig.getButtonTextStyleConfig());
        updateButtonRadiusWithPartnerConfig(context, button, footerButtonPartnerConfig.getButtonRadiusConfig());
        updateButtonIconWithPartnerConfig(context, button, footerButtonPartnerConfig.getButtonIconConfig(), z2);
    }

    static void clearSavedDefaultTextColor() {
        defaultTextColor.clear();
    }

    private static int convertRgbToArgb(int i, float f) {
        return Color.argb((int) (f * 255.0f), Color.red(i), Color.green(i), Color.blue(i));
    }

    private static ColorStateList getButtonDefaultTextCorlor(Button button) {
        HashMap map = defaultTextColor;
        if (map.containsKey(Integer.valueOf(button.getId()))) {
            return (ColorStateList) map.get(Integer.valueOf(button.getId()));
        }
        throw new IllegalStateException("There is no saved default color for button");
    }

    public static GradientDrawable getGradientDrawable(Button button) {
        Drawable background = button.getBackground();
        if (background instanceof InsetDrawable) {
            return (GradientDrawable) ((LayerDrawable) ((InsetDrawable) background).getDrawable()).getDrawable(0);
        }
        if (!(background instanceof RippleDrawable)) {
            return null;
        }
        RippleDrawable rippleDrawable = (RippleDrawable) background;
        return rippleDrawable.getDrawable(0) instanceof GradientDrawable ? (GradientDrawable) rippleDrawable.getDrawable(0) : (GradientDrawable) ((InsetDrawable) rippleDrawable.getDrawable(0)).getDrawable();
    }

    static RippleDrawable getRippleDrawable(Button button) {
        Drawable background = button.getBackground();
        if (background instanceof InsetDrawable) {
            return (RippleDrawable) ((InsetDrawable) background).getDrawable();
        }
        if (background instanceof RippleDrawable) {
            return (RippleDrawable) background;
        }
        return null;
    }

    private static void saveButtonDefaultTextColor(Button button) {
        defaultTextColor.put(Integer.valueOf(button.getId()), button.getTextColors());
    }

    private static void setButtonIcon(Button button, Drawable drawable, boolean z) {
        Drawable drawable2;
        if (button == null) {
            return;
        }
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        }
        if (z) {
            drawable2 = drawable;
            drawable = null;
        } else {
            drawable2 = null;
        }
        button.setCompoundDrawablesRelative(drawable, null, drawable2, null);
    }

    static void updateButtonBackground(Button button, int i) {
        button.getBackground().mutate().setColorFilter(i, PorterDuff.Mode.SRC_ATOP);
    }

    static void updateButtonBackgroundTintList(Context context, Button button, int i, float f, int i2) {
        int[] iArr = {-16842910};
        int[] iArr2 = new int[0];
        if (i != 0) {
            if (f <= 0.0f) {
                TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(new int[]{R.attr.disabledAlpha});
                f = typedArrayObtainStyledAttributes.getFloat(0, 0.26f);
                typedArrayObtainStyledAttributes.recycle();
            }
            if (i2 == 0) {
                i2 = i;
            }
            ColorStateList colorStateList = new ColorStateList(new int[][]{iArr, iArr2}, new int[]{convertRgbToArgb(i2, f), i});
            button.getBackground().mutate().setState(new int[0]);
            button.refreshDrawableState();
            button.setBackgroundTintList(colorStateList);
        }
    }

    static void updateButtonBackgroundWithPartnerConfig(Context context, Button button, PartnerConfig partnerConfig, PartnerConfig partnerConfig2, PartnerConfig partnerConfig3) {
        Preconditions.checkArgument(true, "Update button background only support on sdk Q or higher");
        updateButtonBackgroundTintList(context, button, PartnerConfigHelper.get(context).getColor(context, partnerConfig), PartnerConfigHelper.get(context).getFraction(context, partnerConfig2, 0.0f), PartnerConfigHelper.get(context).getColor(context, partnerConfig3));
    }

    static void updateButtonIconWithPartnerConfig(Context context, Button button, PartnerConfig partnerConfig, boolean z) {
        if (button == null) {
            return;
        }
        setButtonIcon(button, partnerConfig != null ? PartnerConfigHelper.get(context).getDrawable(context, partnerConfig) : null, z);
    }

    static void updateButtonMarginStartWithPartnerConfig(Context context, Button button, PartnerConfig partnerConfig) {
        ViewGroup.LayoutParams layoutParams = button.getLayoutParams();
        if (PartnerConfigHelper.get(context).isPartnerConfigAvailable(partnerConfig) && (layoutParams instanceof ViewGroup.MarginLayoutParams)) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
            marginLayoutParams.setMargins((int) PartnerConfigHelper.get(context).getDimension(context, partnerConfig), marginLayoutParams.topMargin, marginLayoutParams.rightMargin, marginLayoutParams.bottomMargin);
        }
    }

    static void updateButtonMinHeightWithPartnerConfig(Context context, Button button, PartnerConfig partnerConfig) {
        if (PartnerConfigHelper.get(context).isPartnerConfigAvailable(partnerConfig)) {
            float dimension = PartnerConfigHelper.get(context).getDimension(context, partnerConfig);
            if (dimension > 0.0f) {
                button.setMinHeight((int) dimension);
            }
        }
    }

    static void updateButtonRadiusWithPartnerConfig(Context context, Button button, PartnerConfig partnerConfig) {
        float dimension = PartnerConfigHelper.get(context).getDimension(context, partnerConfig);
        if (PartnerConfigHelper.isGlifExpressiveEnabled(context) && (button instanceof MaterialFooterActionButton)) {
            ((MaterialFooterActionButton) button).setCornerRadius((int) dimension);
            return;
        }
        GradientDrawable gradientDrawable = getGradientDrawable(button);
        if (gradientDrawable != null) {
            gradientDrawable.setCornerRadius(dimension);
        }
    }

    private static void updateButtonRippleColor(Context context, Button button, int i, float f) {
        RippleDrawable rippleDrawable = getRippleDrawable(button);
        if (rippleDrawable == null) {
            return;
        }
        int[] iArr = {R.attr.state_pressed};
        int[] iArr2 = {R.attr.state_focused};
        int iConvertRgbToArgb = convertRgbToArgb(i, f);
        ColorStateList colorStateList = new ColorStateList(new int[][]{iArr, iArr2, StateSet.NOTHING}, new int[]{iConvertRgbToArgb, iConvertRgbToArgb, 0});
        if (PartnerConfigHelper.isGlifExpressiveEnabled(context) && (button instanceof MaterialFooterActionButton)) {
            ((MaterialFooterActionButton) button).setRippleColor(colorStateList);
        } else {
            rippleDrawable.setColor(colorStateList);
        }
    }

    static void updateButtonRippleColorWithPartnerConfig(Context context, Button button, boolean z, PartnerConfig partnerConfig, PartnerConfig partnerConfig2) {
        updateButtonRippleColor(context, button, z ? button.getTextColors().getDefaultColor() : PartnerConfigHelper.get(context).getColor(context, partnerConfig), PartnerConfigHelper.get(context).getFraction(context, partnerConfig2));
    }

    static void updateButtonTextDisableDefaultColor(Button button, ColorStateList colorStateList) {
        button.setTextColor(colorStateList);
    }

    static void updateButtonTextDisabledColor(Button button, int i) {
        if (i != 0) {
            button.setTextColor(ColorStateList.valueOf(i));
        }
    }

    static void updateButtonTextDisabledColorWithPartnerConfig(Context context, Button button, PartnerConfig partnerConfig) {
        if (PartnerConfigHelper.get(context).isPartnerConfigAvailable(partnerConfig)) {
            updateButtonTextDisabledColor(button, PartnerConfigHelper.get(context).getColor(context, partnerConfig));
        } else {
            updateButtonTextDisableDefaultColor(button, getButtonDefaultTextCorlor(button));
        }
    }

    static void updateButtonTextEnabledColor(Button button, int i) {
        if (i != 0) {
            button.setTextColor(ColorStateList.valueOf(i));
        }
    }

    static void updateButtonTextEnabledColorWithPartnerConfig(Context context, Button button, PartnerConfig partnerConfig) {
        updateButtonTextEnabledColor(button, PartnerConfigHelper.get(context).getColor(context, partnerConfig));
    }

    static void updateButtonTextSizeWithPartnerConfig(Context context, Button button, PartnerConfig partnerConfig) {
        float dimension = PartnerConfigHelper.get(context).getDimension(context, partnerConfig);
        if (dimension > 0.0f) {
            button.setTextSize(0, dimension);
        }
    }

    static void updateButtonTypeFaceWithPartnerConfig(Context context, Button button, PartnerConfig partnerConfig, PartnerConfig partnerConfig2, PartnerConfig partnerConfig3) {
        String string = PartnerConfigHelper.get(context).getString(context, partnerConfig);
        int integer = PartnerConfigHelper.get(context).isPartnerConfigAvailable(partnerConfig3) ? PartnerConfigHelper.get(context).getInteger(context, partnerConfig3, 0) : 0;
        Typeface typefaceCreate = (PartnerConfigHelper.isFontWeightEnabled(context) && PartnerConfigHelper.get(context).isPartnerConfigAvailable(partnerConfig2)) ? Typeface.create(Typeface.create(string, integer), PartnerConfigHelper.get(context).getInteger(context, partnerConfig2, 400), false) : Typeface.create(string, integer);
        if (typefaceCreate != null) {
            button.setTypeface(typefaceCreate);
        }
    }
}
