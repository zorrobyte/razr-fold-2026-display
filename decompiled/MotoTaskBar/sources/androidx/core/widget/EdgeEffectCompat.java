package androidx.core.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EdgeEffect;

/* JADX INFO: loaded from: classes.dex */
public abstract class EdgeEffectCompat {

    abstract class Api31Impl {
        public static EdgeEffect create(Context context, AttributeSet attributeSet) {
            try {
                return new EdgeEffect(context, attributeSet);
            } catch (Throwable unused) {
                return new EdgeEffect(context);
            }
        }

        public static float getDistance(EdgeEffect edgeEffect) {
            try {
                return edgeEffect.getDistance();
            } catch (Throwable unused) {
                return 0.0f;
            }
        }

        public static float onPullDistance(EdgeEffect edgeEffect, float f, float f2) {
            try {
                return edgeEffect.onPullDistance(f, f2);
            } catch (Throwable unused) {
                edgeEffect.onPull(f, f2);
                return 0.0f;
            }
        }
    }

    public static EdgeEffect create(Context context, AttributeSet attributeSet) {
        return Api31Impl.create(context, attributeSet);
    }

    public static float getDistance(EdgeEffect edgeEffect) {
        return Api31Impl.getDistance(edgeEffect);
    }

    public static float onPullDistance(EdgeEffect edgeEffect, float f, float f2) {
        return Api31Impl.onPullDistance(edgeEffect, f, f2);
    }
}
