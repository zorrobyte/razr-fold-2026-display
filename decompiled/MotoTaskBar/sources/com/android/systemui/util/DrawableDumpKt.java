package com.android.systemui.util;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.util.Log;
import java.lang.reflect.Field;
import kotlin.jvm.internal.Reflection;

/* JADX INFO: compiled from: DrawableDump.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class DrawableDumpKt {
    public static final String getSolidColor(Drawable drawable) {
        if (!Compile.IS_DEBUG) {
            return drawable == null ? "null" : "?";
        }
        ColorStateList solidColors = getSolidColors(drawable);
        return ColorUtilKt.hexColorString(solidColors != null ? Integer.valueOf(solidColors.getDefaultColor()) : null);
    }

    private static final ColorStateList getSolidColors(Drawable drawable) {
        if (!(drawable instanceof GradientDrawable)) {
            if (!(drawable instanceof LayerDrawable)) {
                if (drawable instanceof DrawableWrapper) {
                    return getSolidColors(((DrawableWrapper) drawable).getDrawable());
                }
                return null;
            }
            LayerDrawable layerDrawable = (LayerDrawable) drawable;
            int numberOfLayers = layerDrawable.getNumberOfLayers();
            for (int i = 0; i < numberOfLayers; i++) {
                ColorStateList solidColors = getSolidColors(layerDrawable.getDrawable(i));
                if (solidColors != null) {
                    return solidColors;
                }
            }
            return null;
        }
        Drawable.ConstantState constantState = drawable.getConstantState();
        if (constantState == null) {
            return null;
        }
        Class<?> cls = constantState.getClass();
        try {
            Field declaredField = cls.getDeclaredField("mSolidColors");
            declaredField.setAccessible(true);
            return (ColorStateList) declaredField.get(constantState);
        } catch (Exception e) {
            Log.w("DrawableDump", "Missing " + cls.getSimpleName() + ".mSolidColors: " + Reflection.getOrCreateKotlinClass(ColorStateList.class).getSimpleName(), e);
            return null;
        }
    }
}
