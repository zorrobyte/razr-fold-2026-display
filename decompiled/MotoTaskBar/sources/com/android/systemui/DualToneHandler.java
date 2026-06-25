package com.android.systemui;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.view.ContextThemeWrapper;
import com.motorola.taskbar.R$attr;
import com.motorola.taskbar.util.Utils;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: DualToneHandler.kt */
/* JADX INFO: loaded from: classes.dex */
public final class DualToneHandler {
    private Color darkColor;
    private Color lightColor;

    /* JADX INFO: compiled from: DualToneHandler.kt */
    final class Color {
        private final int background;
        private final int fill;
        private final int single;

        public Color(int i, int i2, int i3) {
            this.single = i;
            this.background = i2;
            this.fill = i3;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Color)) {
                return false;
            }
            Color color = (Color) obj;
            return this.single == color.single && this.background == color.background && this.fill == color.fill;
        }

        public final int getBackground() {
            return this.background;
        }

        public final int getFill() {
            return this.fill;
        }

        public final int getSingle() {
            return this.single;
        }

        public int hashCode() {
            return (((Integer.hashCode(this.single) * 31) + Integer.hashCode(this.background)) * 31) + Integer.hashCode(this.fill);
        }

        public String toString() {
            return "Color(single=" + this.single + ", background=" + this.background + ", fill=" + this.fill + ")";
        }
    }

    public DualToneHandler(Context context) {
        context.getClass();
        setColorsFromContext(context);
    }

    private final int getColorForDarkIntensity(float f, int i, int i2) {
        Object objEvaluate = ArgbEvaluator.getInstance().evaluate(f, Integer.valueOf(i), Integer.valueOf(i2));
        objEvaluate.getClass();
        return ((Integer) objEvaluate).intValue();
    }

    public final int getBackgroundColor(float f) {
        Color color = this.lightColor;
        Color color2 = null;
        if (color == null) {
            Intrinsics.throwUninitializedPropertyAccessException("lightColor");
            color = null;
        }
        int background = color.getBackground();
        Color color3 = this.darkColor;
        if (color3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("darkColor");
        } else {
            color2 = color3;
        }
        return getColorForDarkIntensity(f, background, color2.getBackground());
    }

    public final int getFillColor(float f) {
        Color color = this.lightColor;
        Color color2 = null;
        if (color == null) {
            Intrinsics.throwUninitializedPropertyAccessException("lightColor");
            color = null;
        }
        int fill = color.getFill();
        Color color3 = this.darkColor;
        if (color3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("darkColor");
        } else {
            color2 = color3;
        }
        return getColorForDarkIntensity(f, fill, color2.getFill());
    }

    public final int getSingleColor(float f) {
        Color color = this.lightColor;
        Color color2 = null;
        if (color == null) {
            Intrinsics.throwUninitializedPropertyAccessException("lightColor");
            color = null;
        }
        int single = color.getSingle();
        Color color3 = this.darkColor;
        if (color3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("darkColor");
        } else {
            color2 = color3;
        }
        return getColorForDarkIntensity(f, single, color2.getSingle());
    }

    public final void setColorsFromContext(Context context) {
        context.getClass();
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, Utils.getThemeAttr(context, R$attr.darkIconTheme));
        ContextThemeWrapper contextThemeWrapper2 = new ContextThemeWrapper(context, Utils.getThemeAttr(context, R$attr.lightIconTheme));
        int i = R$attr.singleToneColor;
        int colorAttrDefaultColor = Utils.getColorAttrDefaultColor(contextThemeWrapper, i);
        int i2 = R$attr.backgroundColor;
        int colorAttrDefaultColor2 = Utils.getColorAttrDefaultColor(contextThemeWrapper, i2);
        int i3 = R$attr.fillColor;
        this.darkColor = new Color(colorAttrDefaultColor, colorAttrDefaultColor2, Utils.getColorAttrDefaultColor(contextThemeWrapper, i3));
        this.lightColor = new Color(Utils.getColorAttrDefaultColor(contextThemeWrapper2, i), Utils.getColorAttrDefaultColor(contextThemeWrapper2, i2), Utils.getColorAttrDefaultColor(contextThemeWrapper2, i3));
    }
}
