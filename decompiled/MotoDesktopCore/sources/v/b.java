package v;

import android.os.Bundle;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import w.C0163b;

/* JADX INFO: loaded from: classes.dex */
public abstract class b {

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public static final View.AccessibilityDelegate f2827b = new View.AccessibilityDelegate();

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final a f2828a = new a(this);

    public static void e(View view, int i2) {
        f2827b.sendAccessibilityEvent(view, i2);
    }

    public void a(View view, AccessibilityEvent accessibilityEvent) {
        f2827b.onInitializeAccessibilityEvent(view, accessibilityEvent);
    }

    public abstract void b(View view, C0163b c0163b);

    public void c(View view, AccessibilityEvent accessibilityEvent) {
        f2827b.onPopulateAccessibilityEvent(view, accessibilityEvent);
    }

    public boolean d(View view, int i2, Bundle bundle) {
        return f2827b.performAccessibilityAction(view, i2, bundle);
    }
}
