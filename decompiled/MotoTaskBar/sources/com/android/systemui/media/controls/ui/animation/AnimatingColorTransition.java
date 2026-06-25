package com.android.systemui.media.controls.ui.animation;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import com.android.systemui.monet.ColorScheme;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: ColorSchemeTransition.kt */
/* JADX INFO: loaded from: classes.dex */
public class AnimatingColorTransition implements ValueAnimator.AnimatorUpdateListener {
    private final Function1 applyColor;
    private final ArgbEvaluator argbEvaluator;
    private int currentColor;
    private final int defaultColor;
    private final Function1 extractColor;
    private int sourceColor;
    private int targetColor;
    private final ValueAnimator valueAnimator;

    public AnimatingColorTransition(int i, Function1 function1, Function1 function12) {
        function1.getClass();
        function12.getClass();
        this.defaultColor = i;
        this.extractColor = function1;
        this.applyColor = function12;
        this.argbEvaluator = new ArgbEvaluator();
        this.valueAnimator = buildAnimator();
        this.sourceColor = i;
        this.currentColor = i;
        this.targetColor = i;
        function12.invoke(Integer.valueOf(i));
    }

    public ValueAnimator buildAnimator() {
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimatorOfFloat.setDuration(333L);
        valueAnimatorOfFloat.addUpdateListener(this);
        return valueAnimatorOfFloat;
    }

    public final int getCurrentColor() {
        return this.currentColor;
    }

    public final int getTargetColor() {
        return this.targetColor;
    }

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        valueAnimator.getClass();
        Object objEvaluate = this.argbEvaluator.evaluate(valueAnimator.getAnimatedFraction(), Integer.valueOf(this.sourceColor), Integer.valueOf(this.targetColor));
        objEvaluate.getClass();
        int iIntValue = ((Integer) objEvaluate).intValue();
        this.currentColor = iIntValue;
        this.applyColor.invoke(Integer.valueOf(iIntValue));
    }

    public boolean updateColorScheme(ColorScheme colorScheme) {
        int iIntValue = colorScheme == null ? this.defaultColor : ((Number) this.extractColor.invoke(colorScheme)).intValue();
        if (iIntValue == this.targetColor) {
            return false;
        }
        this.sourceColor = this.currentColor;
        this.targetColor = iIntValue;
        this.valueAnimator.cancel();
        this.valueAnimator.start();
        return true;
    }
}
