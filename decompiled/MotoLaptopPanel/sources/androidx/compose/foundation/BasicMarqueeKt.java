package androidx.compose.foundation;

import androidx.compose.animation.core.AnimationSpec;
import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.animation.core.EasingKt;
import androidx.compose.animation.core.StartOffset;
import androidx.compose.animation.core.TweenSpec;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.unit.Density;

/* JADX INFO: compiled from: BasicMarquee.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class BasicMarqueeKt {
    /* JADX INFO: renamed from: basicMarquee-1Mj1MLw, reason: not valid java name */
    public static final Modifier m82basicMarquee1Mj1MLw(Modifier modifier, int i, int i2, int i3, int i4, MarqueeSpacing marqueeSpacing, float f) {
        return modifier.then(new MarqueeModifierElement(i, i2, i3, i4, marqueeSpacing, f, null));
    }

    /* JADX INFO: renamed from: basicMarquee-1Mj1MLw$default, reason: not valid java name */
    public static /* synthetic */ Modifier m83basicMarquee1Mj1MLw$default(Modifier modifier, int i, int i2, int i3, int i4, MarqueeSpacing marqueeSpacing, float f, int i5, Object obj) {
        if ((i5 & 1) != 0) {
            i = MarqueeDefaults.INSTANCE.getIterations();
        }
        if ((i5 & 2) != 0) {
            i2 = MarqueeAnimationMode.Companion.m113getImmediatelyZbEOnfQ();
        }
        if ((i5 & 4) != 0) {
            i3 = MarqueeDefaults.INSTANCE.getRepeatDelayMillis();
        }
        if ((i5 & 8) != 0) {
            i4 = MarqueeAnimationMode.m109equalsimpl0(i2, MarqueeAnimationMode.Companion.m113getImmediatelyZbEOnfQ()) ? i3 : 0;
        }
        if ((i5 & 16) != 0) {
            marqueeSpacing = MarqueeDefaults.INSTANCE.getSpacing();
        }
        if ((i5 & 32) != 0) {
            f = MarqueeDefaults.INSTANCE.m115getVelocityD9Ej5fM();
        }
        return m82basicMarquee1Mj1MLw(modifier, i, i2, i3, i4, marqueeSpacing, f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: createMarqueeAnimationSpec-Z4HSEVQ, reason: not valid java name */
    public static final AnimationSpec m84createMarqueeAnimationSpecZ4HSEVQ(int i, float f, int i2, int i3, float f2, Density density) {
        TweenSpec tweenSpecVelocityBasedTween = velocityBasedTween(Math.abs(density.mo146toPx0680j_4(f2)), f, i3);
        long jM51constructorimpl$default = StartOffset.m51constructorimpl$default((-i3) + i2, 0, 2, null);
        return i == Integer.MAX_VALUE ? AnimationSpecKt.m44infiniteRepeatable9IiC70o$default(tweenSpecVelocityBasedTween, null, jM51constructorimpl$default, 2, null) : AnimationSpecKt.m46repeatable91I0pcU$default(i, tweenSpecVelocityBasedTween, null, jM51constructorimpl$default, 4, null);
    }

    private static final TweenSpec velocityBasedTween(float f, float f2, int i) {
        return AnimationSpecKt.tween((int) Math.ceil(f2 / (f / 1000.0f)), i, EasingKt.getLinearEasing());
    }
}
