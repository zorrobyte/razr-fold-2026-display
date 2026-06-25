package o;

import android.graphics.Color;

/* JADX INFO: renamed from: o.a, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractC0152a {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static final /* synthetic */ int f2780a = 0;

    static {
        new ThreadLocal();
    }

    public static int a(int i2, int i3) {
        int iAlpha = Color.alpha(i3);
        int iAlpha2 = Color.alpha(i2);
        int i4 = 255 - (((255 - iAlpha2) * (255 - iAlpha)) / 255);
        return Color.argb(i4, b(Color.red(i2), iAlpha2, Color.red(i3), iAlpha, i4), b(Color.green(i2), iAlpha2, Color.green(i3), iAlpha, i4), b(Color.blue(i2), iAlpha2, Color.blue(i3), iAlpha, i4));
    }

    public static int b(int i2, int i3, int i4, int i5, int i6) {
        if (i6 == 0) {
            return 0;
        }
        return (((255 - i3) * (i4 * i5)) + ((i2 * 255) * i3)) / (i6 * 255);
    }
}
