package androidx.mediarouter.app;

import android.R;
import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.ProgressBar;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.ColorUtils;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.mediarouter.R$attr;
import androidx.mediarouter.R$color;
import androidx.mediarouter.R$drawable;
import androidx.mediarouter.R$style;

/* JADX INFO: loaded from: classes.dex */
abstract class MediaRouterThemeHelper {
    private static final int COLOR_DARK_ON_LIGHT_BACKGROUND_RES_ID = R$color.mr_dynamic_dialog_icon_light;

    static Context createThemedButtonContext(Context context) {
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, getRouterThemeId(context));
        int themeResource = getThemeResource(contextThemeWrapper, R$attr.mediaRouteTheme);
        return themeResource != 0 ? new ContextThemeWrapper(contextThemeWrapper, themeResource) : contextThemeWrapper;
    }

    static Context createThemedDialogContext(Context context, int i, boolean z) {
        if (i == 0) {
            i = getThemeResource(context, !z ? androidx.appcompat.R$attr.dialogTheme : androidx.appcompat.R$attr.alertDialogTheme);
        }
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, i);
        return getThemeResource(contextThemeWrapper, R$attr.mediaRouteTheme) != 0 ? new ContextThemeWrapper(contextThemeWrapper, getRouterThemeId(contextThemeWrapper)) : contextThemeWrapper;
    }

    static int createThemedDialogStyle(Context context) {
        int themeResource = getThemeResource(context, R$attr.mediaRouteTheme);
        return themeResource == 0 ? getRouterThemeId(context) : themeResource;
    }

    static int getButtonTextColor(Context context) {
        int themeColor = getThemeColor(context, 0, androidx.appcompat.R$attr.colorPrimary);
        return ColorUtils.calculateContrast(themeColor, getThemeColor(context, 0, R.attr.colorBackground)) < 3.0d ? getThemeColor(context, 0, androidx.appcompat.R$attr.colorAccent) : themeColor;
    }

    static Drawable getCheckBoxDrawableIcon(Context context) {
        return getIconByDrawableId(context, R$drawable.mr_cast_checkbox);
    }

    static int getControllerColor(Context context, int i) {
        return ColorUtils.calculateContrast(-1, getThemeColor(context, i, androidx.appcompat.R$attr.colorPrimary)) >= 3.0d ? -1 : -570425344;
    }

    static Drawable getDefaultDrawableIcon(Context context) {
        return getIconByAttrId(context, R$attr.mediaRouteDefaultIconDrawable);
    }

    static float getDisabledAlpha(Context context) {
        TypedValue typedValue = new TypedValue();
        if (context.getTheme().resolveAttribute(R.attr.disabledAlpha, typedValue, true)) {
            return typedValue.getFloat();
        }
        return 0.5f;
    }

    private static Drawable getIconByAttrId(Context context, int i) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(new int[]{i});
        Drawable drawableWrap = DrawableCompat.wrap(AppCompatResources.getDrawable(context, typedArrayObtainStyledAttributes.getResourceId(0, 0)));
        if (isLightTheme(context)) {
            DrawableCompat.setTint(drawableWrap, ContextCompat.getColor(context, COLOR_DARK_ON_LIGHT_BACKGROUND_RES_ID));
        }
        typedArrayObtainStyledAttributes.recycle();
        return drawableWrap;
    }

    private static Drawable getIconByDrawableId(Context context, int i) {
        Drawable drawableWrap = DrawableCompat.wrap(AppCompatResources.getDrawable(context, i));
        if (isLightTheme(context)) {
            DrawableCompat.setTint(drawableWrap, ContextCompat.getColor(context, COLOR_DARK_ON_LIGHT_BACKGROUND_RES_ID));
        }
        return drawableWrap;
    }

    static Drawable getMuteButtonDrawableIcon(Context context) {
        return getIconByDrawableId(context, R$drawable.mr_cast_mute_button);
    }

    private static int getRouterThemeId(Context context) {
        return isLightTheme(context) ? getControllerColor(context, 0) == -570425344 ? R$style.Theme_MediaRouter_Light : R$style.Theme_MediaRouter_Light_DarkControlPanel : getControllerColor(context, 0) == -570425344 ? R$style.Theme_MediaRouter_LightControlPanel : R$style.Theme_MediaRouter;
    }

    static Drawable getSpeakerDrawableIcon(Context context) {
        return getIconByAttrId(context, R$attr.mediaRouteSpeakerIconDrawable);
    }

    static Drawable getSpeakerGroupDrawableIcon(Context context) {
        return getIconByAttrId(context, R$attr.mediaRouteSpeakerGroupIconDrawable);
    }

    private static int getThemeColor(Context context, int i, int i2) {
        if (i != 0) {
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(i, new int[]{i2});
            int color = typedArrayObtainStyledAttributes.getColor(0, 0);
            typedArrayObtainStyledAttributes.recycle();
            if (color != 0) {
                return color;
            }
        }
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(i2, typedValue, true);
        return typedValue.resourceId != 0 ? context.getResources().getColor(typedValue.resourceId) : typedValue.data;
    }

    static int getThemeResource(Context context, int i) {
        TypedValue typedValue = new TypedValue();
        if (context.getTheme().resolveAttribute(i, typedValue, true)) {
            return typedValue.resourceId;
        }
        return 0;
    }

    static Drawable getTvDrawableIcon(Context context) {
        return getIconByAttrId(context, R$attr.mediaRouteTvIconDrawable);
    }

    private static boolean isLightTheme(Context context) {
        TypedValue typedValue = new TypedValue();
        return context.getTheme().resolveAttribute(androidx.appcompat.R$attr.isLightTheme, typedValue, true) && typedValue.data != 0;
    }

    static void setDialogBackgroundColor(Context context, Dialog dialog) {
        dialog.getWindow().getDecorView().setBackgroundColor(ContextCompat.getColor(context, isLightTheme(context) ? R$color.mr_dynamic_dialog_background_light : R$color.mr_dynamic_dialog_background_dark));
    }

    static void setIndeterminateProgressBarColor(Context context, ProgressBar progressBar) {
        if (progressBar.isIndeterminate()) {
            progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(context, isLightTheme(context) ? R$color.mr_cast_progressbar_progress_and_thumb_light : R$color.mr_cast_progressbar_progress_and_thumb_dark), PorterDuff.Mode.SRC_IN);
        }
    }

    static void setMediaControlsBackgroundColor(Context context, View view, View view2, boolean z) {
        int themeColor = getThemeColor(context, 0, androidx.appcompat.R$attr.colorPrimary);
        int themeColor2 = getThemeColor(context, 0, androidx.appcompat.R$attr.colorPrimaryDark);
        if (z && getControllerColor(context, 0) == -570425344) {
            themeColor2 = themeColor;
            themeColor = -1;
        }
        view.setBackgroundColor(themeColor);
        view2.setBackgroundColor(themeColor2);
        view.setTag(Integer.valueOf(themeColor));
        view2.setTag(Integer.valueOf(themeColor2));
    }

    static void setVolumeSliderColor(Context context, MediaRouteVolumeSlider mediaRouteVolumeSlider) {
        int color;
        int color2;
        if (isLightTheme(context)) {
            color = ContextCompat.getColor(context, R$color.mr_cast_progressbar_progress_and_thumb_light);
            color2 = ContextCompat.getColor(context, R$color.mr_cast_progressbar_background_light);
        } else {
            color = ContextCompat.getColor(context, R$color.mr_cast_progressbar_progress_and_thumb_dark);
            color2 = ContextCompat.getColor(context, R$color.mr_cast_progressbar_background_dark);
        }
        mediaRouteVolumeSlider.setColor(color, color2);
    }

    static void setVolumeSliderColor(Context context, MediaRouteVolumeSlider mediaRouteVolumeSlider, View view) {
        int controllerColor = getControllerColor(context, 0);
        if (Color.alpha(controllerColor) != 255) {
            controllerColor = ColorUtils.compositeColors(controllerColor, ((Integer) view.getTag()).intValue());
        }
        mediaRouteVolumeSlider.setColor(controllerColor);
    }
}
