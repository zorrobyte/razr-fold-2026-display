package androidx.core.widget;

import android.os.Bundle;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.ScrollView;
import w.C0163b;

/* JADX INFO: loaded from: classes.dex */
public final class c extends v.b {
    @Override // v.b
    public final void a(View view, AccessibilityEvent accessibilityEvent) {
        super.a(view, accessibilityEvent);
        NestedScrollView nestedScrollView = (NestedScrollView) view;
        accessibilityEvent.setClassName(ScrollView.class.getName());
        accessibilityEvent.setScrollable(nestedScrollView.getScrollRange() > 0);
        accessibilityEvent.setScrollX(nestedScrollView.getScrollX());
        accessibilityEvent.setScrollY(nestedScrollView.getScrollY());
        accessibilityEvent.setMaxScrollX(nestedScrollView.getScrollX());
        accessibilityEvent.setMaxScrollY(nestedScrollView.getScrollRange());
    }

    @Override // v.b
    public final void b(View view, C0163b c0163b) {
        int scrollRange;
        View.AccessibilityDelegate accessibilityDelegate = v.b.f2827b;
        AccessibilityNodeInfo accessibilityNodeInfo = c0163b.f2840a;
        accessibilityDelegate.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
        NestedScrollView nestedScrollView = (NestedScrollView) view;
        accessibilityNodeInfo.setClassName("android.widget.ScrollView");
        if (!nestedScrollView.isEnabled() || (scrollRange = nestedScrollView.getScrollRange()) <= 0) {
            return;
        }
        accessibilityNodeInfo.setScrollable(true);
        if (nestedScrollView.getScrollY() > 0) {
            c0163b.a(8192);
        }
        if (nestedScrollView.getScrollY() < scrollRange) {
            c0163b.a(4096);
        }
    }

    @Override // v.b
    public final boolean d(View view, int i2, Bundle bundle) {
        if (v.b.f2827b.performAccessibilityAction(view, i2, bundle)) {
            return true;
        }
        NestedScrollView nestedScrollView = (NestedScrollView) view;
        if (!nestedScrollView.isEnabled()) {
            return false;
        }
        if (i2 == 4096) {
            int iMin = Math.min(nestedScrollView.getScrollY() + ((nestedScrollView.getHeight() - nestedScrollView.getPaddingBottom()) - nestedScrollView.getPaddingTop()), nestedScrollView.getScrollRange());
            if (iMin == nestedScrollView.getScrollY()) {
                return false;
            }
            nestedScrollView.s(0 - nestedScrollView.getScrollX(), iMin - nestedScrollView.getScrollY());
            return true;
        }
        if (i2 != 8192) {
            return false;
        }
        int iMax = Math.max(nestedScrollView.getScrollY() - ((nestedScrollView.getHeight() - nestedScrollView.getPaddingBottom()) - nestedScrollView.getPaddingTop()), 0);
        if (iMax == nestedScrollView.getScrollY()) {
            return false;
        }
        nestedScrollView.s(0 - nestedScrollView.getScrollX(), iMax - nestedScrollView.getScrollY());
        return true;
    }
}
