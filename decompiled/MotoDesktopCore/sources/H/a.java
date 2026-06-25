package H;

import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

/* JADX INFO: loaded from: classes.dex */
public abstract class a {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static final z.a f142a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public static final z.a f143b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public static final z.a f144c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public static final DecelerateInterpolator f145d;

    static {
        new LinearInterpolator();
        f142a = new z.a(1);
        f143b = new z.a(0);
        f144c = new z.a(2);
        f145d = new DecelerateInterpolator();
    }
}
