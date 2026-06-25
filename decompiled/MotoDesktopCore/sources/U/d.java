package U;

import android.os.Handler;
import android.os.Looper;
import android.view.accessibility.AccessibilityNodeProvider;

/* JADX INFO: loaded from: classes.dex */
public final class d {

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public static d f242b;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final Object f243a;

    public d() {
        this.f243a = new Object();
        new Handler(Looper.getMainLooper(), new c(0, this));
    }

    public d(AccessibilityNodeProvider accessibilityNodeProvider) {
        this.f243a = accessibilityNodeProvider;
    }
}
