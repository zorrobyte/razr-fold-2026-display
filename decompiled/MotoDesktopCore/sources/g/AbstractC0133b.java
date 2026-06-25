package g;

import android.graphics.drawable.Drawable;

/* JADX INFO: renamed from: g.b, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractC0133b extends Drawable {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static final double f2551a = Math.cos(Math.toRadians(45.0d));

    public static float a(float f2, float f3, boolean z2) {
        if (!z2) {
            return f2;
        }
        return (float) (((1.0d - f2551a) * ((double) f3)) + ((double) f2));
    }

    public static float b(float f2, float f3, boolean z2) {
        if (!z2) {
            return f2 * 1.5f;
        }
        return (float) (((1.0d - f2551a) * ((double) f3)) + ((double) (f2 * 1.5f)));
    }
}
