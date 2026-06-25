package androidx.vectordrawable.graphics.drawable;

import android.content.Context;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import androidx.core.util.ObjectsCompat;

/* JADX INFO: loaded from: classes.dex */
public abstract class AnimationUtilsCompat {
    public static Interpolator loadInterpolator(Context context, int i) {
        Interpolator interpolatorLoadInterpolator = AnimationUtils.loadInterpolator(context, i);
        ObjectsCompat.requireNonNull(interpolatorLoadInterpolator, "Failed to parse interpolator, no start tag found");
        return interpolatorLoadInterpolator;
    }
}
