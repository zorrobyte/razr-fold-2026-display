package com.android.systemui.media.controls.ui.drawable;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.MathUtils;
import android.view.View;
import com.android.app.animation.Interpolators;
import com.android.internal.graphics.ColorUtils;
import com.android.systemui.res.R$styleable;
import java.util.ArrayList;
import kotlin.jvm.internal.Intrinsics;
import org.xmlpull.v1.XmlPullParser;

/* JADX INFO: compiled from: IlluminationDrawable.kt */
/* JADX INFO: loaded from: classes.dex */
public final class IlluminationDrawable extends Drawable {
    private ValueAnimator backgroundAnimation;
    private int backgroundColor;
    private float cornerRadius;
    private float highlight;
    private int highlightColor;
    private int[] themeAttrs;
    private float cornerRadiusOverride = -1.0f;
    private float[] tmpHsl = {0.0f, 0.0f, 0.0f};
    private Paint paint = new Paint();
    private final ArrayList lightSources = new ArrayList();

    private final void animateBackground() {
        ColorUtils.colorToHSL(this.backgroundColor, this.tmpHsl);
        float[] fArr = this.tmpHsl;
        float f = fArr[2];
        float f2 = this.highlight;
        fArr[2] = MathUtils.constrain(f < 1.0f - f2 ? f + f2 : f - f2, 0.0f, 1.0f);
        final int color = this.paint.getColor();
        final int i = this.highlightColor;
        final int iHSLToColor = ColorUtils.HSLToColor(this.tmpHsl);
        ValueAnimator valueAnimator = this.backgroundAnimation;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimatorOfFloat.setDuration(370L);
        valueAnimatorOfFloat.setInterpolator(Interpolators.FAST_OUT_LINEAR_IN);
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.media.controls.ui.drawable.IlluminationDrawable$animateBackground$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                valueAnimator2.getClass();
                Object animatedValue = valueAnimator2.getAnimatedValue();
                animatedValue.getClass();
                float fFloatValue = ((Float) animatedValue).floatValue();
                this.this$0.paint.setColor(ColorUtils.blendARGB(color, this.this$0.backgroundColor, fFloatValue));
                this.this$0.highlightColor = ColorUtils.blendARGB(i, iHSLToColor, fFloatValue);
                ArrayList arrayList = this.this$0.lightSources;
                IlluminationDrawable illuminationDrawable = this.this$0;
                int size = arrayList.size();
                int i2 = 0;
                while (i2 < size) {
                    Object obj = arrayList.get(i2);
                    i2++;
                    ((LightSourceDrawable) obj).setHighlightColor(illuminationDrawable.highlightColor);
                }
                this.this$0.invalidateSelf();
            }
        });
        valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.media.controls.ui.drawable.IlluminationDrawable$animateBackground$1$2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                animator.getClass();
                this.this$0.backgroundAnimation = null;
            }
        });
        valueAnimatorOfFloat.start();
        this.backgroundAnimation = valueAnimatorOfFloat;
    }

    private final void registerLightSource(LightSourceDrawable lightSourceDrawable) {
        lightSourceDrawable.setAlpha(this.paint.getAlpha());
        this.lightSources.add(lightSourceDrawable);
    }

    private final void setBackgroundColor(int i) {
        if (i == this.backgroundColor) {
            return;
        }
        this.backgroundColor = i;
        animateBackground();
    }

    private final void updateStateFromTypedArray(TypedArray typedArray) {
        int i = R$styleable.IlluminationDrawable_cornerRadius;
        if (typedArray.hasValue(i)) {
            this.cornerRadius = typedArray.getDimension(i, getCornerRadius());
        }
        if (typedArray.hasValue(R$styleable.IlluminationDrawable_highlight)) {
            this.highlight = typedArray.getInteger(r0, 0) / 100.0f;
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void applyTheme(Resources.Theme theme) {
        theme.getClass();
        super.applyTheme(theme);
        int[] iArr = this.themeAttrs;
        if (iArr != null) {
            TypedArray typedArrayResolveAttributes = theme.resolveAttributes(iArr, R$styleable.IlluminationDrawable);
            typedArrayResolveAttributes.getClass();
            updateStateFromTypedArray(typedArrayResolveAttributes);
            typedArrayResolveAttributes.recycle();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public boolean canApplyTheme() {
        int[] iArr = this.themeAttrs;
        if (iArr != null) {
            iArr.getClass();
            if (iArr.length > 0) {
                return true;
            }
        }
        return super.canApplyTheme();
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        canvas.getClass();
        canvas.drawRoundRect(0.0f, 0.0f, getBounds().width(), getBounds().height(), getCornerRadius(), getCornerRadius(), this.paint);
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        return this.paint.getAlpha();
    }

    public final float getCornerRadius() {
        float f = this.cornerRadiusOverride;
        return f >= 0.0f ? f : this.cornerRadius;
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -2;
    }

    @Override // android.graphics.drawable.Drawable
    public void getOutline(Outline outline) {
        outline.getClass();
        outline.setRoundRect(getBounds(), getCornerRadius());
    }

    @Override // android.graphics.drawable.Drawable
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) {
        resources.getClass();
        xmlPullParser.getClass();
        attributeSet.getClass();
        TypedArray typedArrayObtainAttributes = Drawable.obtainAttributes(resources, theme, attributeSet, R$styleable.IlluminationDrawable);
        typedArrayObtainAttributes.getClass();
        this.themeAttrs = typedArrayObtainAttributes.extractThemeAttrs();
        updateStateFromTypedArray(typedArrayObtainAttributes);
        typedArrayObtainAttributes.recycle();
    }

    public final void registerLightSource(View view) {
        view.getClass();
        if (view.getBackground() instanceof LightSourceDrawable) {
            Drawable background = view.getBackground();
            background.getClass();
            registerLightSource((LightSourceDrawable) background);
        } else if (view.getForeground() instanceof LightSourceDrawable) {
            Drawable foreground = view.getForeground();
            foreground.getClass();
            registerLightSource((LightSourceDrawable) foreground);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        if (i == this.paint.getAlpha()) {
            return;
        }
        this.paint.setAlpha(i);
        invalidateSelf();
        ArrayList arrayList = this.lightSources;
        int size = arrayList.size();
        int i2 = 0;
        while (i2 < size) {
            Object obj = arrayList.get(i2);
            i2++;
            ((LightSourceDrawable) obj).setAlpha(i);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        throw new UnsupportedOperationException("Color filters are not supported");
    }

    public final void setCornerRadius(float f) {
        this.cornerRadius = f;
    }

    public final void setCornerRadiusOverride(Float f) {
        this.cornerRadiusOverride = f != null ? f.floatValue() : -1.0f;
    }

    @Override // android.graphics.drawable.Drawable
    public void setTintList(ColorStateList colorStateList) {
        super.setTintList(colorStateList);
        colorStateList.getClass();
        setBackgroundColor(colorStateList.getDefaultColor());
    }

    public void setXfermode(Xfermode xfermode) {
        if (Intrinsics.areEqual(xfermode, this.paint.getXfermode())) {
            return;
        }
        this.paint.setXfermode(xfermode);
        invalidateSelf();
    }
}
