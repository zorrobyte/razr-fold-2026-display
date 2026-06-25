package androidx.compose.ui.focus;

import android.view.FocusFinder;
import android.view.View;
import android.view.ViewGroup;
import androidx.compose.ui.focus.FocusDirection;
import androidx.compose.ui.focus.FocusInteropUtils;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.platform.AndroidComposeView;
import androidx.compose.ui.unit.LayoutDirection;

/* JADX INFO: compiled from: FocusInteropUtils.android.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class FocusInteropUtils_androidKt {
    public static final Rect calculateBoundingRectRelativeTo(View view, View view2) {
        FocusInteropUtils.Companion companion = FocusInteropUtils.Companion;
        view.getLocationInWindow(companion.getTempCoordinates());
        int i = companion.getTempCoordinates()[0];
        int i2 = companion.getTempCoordinates()[1];
        view2.getLocationInWindow(companion.getTempCoordinates());
        float f = i - companion.getTempCoordinates()[0];
        float f2 = i2 - companion.getTempCoordinates()[1];
        return new Rect(f, f2, view.getWidth() + f, view.getHeight() + f2);
    }

    public static final boolean requestInteropFocus(View view, Integer num, android.graphics.Rect rect) {
        if (num == null) {
            return view.requestFocus();
        }
        if (!(view instanceof ViewGroup)) {
            return view.requestFocus(num.intValue(), rect);
        }
        ViewGroup viewGroup = (ViewGroup) view;
        if (viewGroup.isFocused()) {
            return true;
        }
        if ((!viewGroup.isFocusable() || view.hasFocus()) && !(view instanceof AndroidComposeView)) {
            if (rect != null) {
                View viewFindNextFocusFromRect = FocusFinder.getInstance().findNextFocusFromRect(viewGroup, rect, num.intValue());
                return viewFindNextFocusFromRect != null ? viewFindNextFocusFromRect.requestFocus(num.intValue(), rect) : view.requestFocus(num.intValue(), rect);
            }
            View viewFindNextFocus = FocusFinder.getInstance().findNextFocus(viewGroup, view.hasFocus() ? view.findFocus() : null, num.intValue());
            return viewFindNextFocus != null ? viewFindNextFocus.requestFocus(num.intValue()) : view.requestFocus(num.intValue());
        }
        return view.requestFocus(num.intValue(), rect);
    }

    /* JADX INFO: renamed from: toAndroidFocusDirection-3ESFkO8, reason: not valid java name */
    public static final Integer m699toAndroidFocusDirection3ESFkO8(int i) {
        FocusDirection.Companion companion = FocusDirection.Companion;
        if (FocusDirection.m687equalsimpl0(i, companion.m698getUpdhqQ8s())) {
            return 33;
        }
        if (FocusDirection.m687equalsimpl0(i, companion.m691getDowndhqQ8s())) {
            return 130;
        }
        if (FocusDirection.m687equalsimpl0(i, companion.m694getLeftdhqQ8s())) {
            return 17;
        }
        if (FocusDirection.m687equalsimpl0(i, companion.m697getRightdhqQ8s())) {
            return 66;
        }
        if (FocusDirection.m687equalsimpl0(i, companion.m695getNextdhqQ8s())) {
            return 2;
        }
        return FocusDirection.m687equalsimpl0(i, companion.m696getPreviousdhqQ8s()) ? 1 : null;
    }

    public static final FocusDirection toFocusDirection(int i) {
        if (i == 1) {
            return FocusDirection.m684boximpl(FocusDirection.Companion.m696getPreviousdhqQ8s());
        }
        if (i == 2) {
            return FocusDirection.m684boximpl(FocusDirection.Companion.m695getNextdhqQ8s());
        }
        if (i == 17) {
            return FocusDirection.m684boximpl(FocusDirection.Companion.m694getLeftdhqQ8s());
        }
        if (i == 33) {
            return FocusDirection.m684boximpl(FocusDirection.Companion.m698getUpdhqQ8s());
        }
        if (i == 66) {
            return FocusDirection.m684boximpl(FocusDirection.Companion.m697getRightdhqQ8s());
        }
        if (i != 130) {
            return null;
        }
        return FocusDirection.m684boximpl(FocusDirection.Companion.m691getDowndhqQ8s());
    }

    public static final LayoutDirection toLayoutDirection(int i) {
        if (i == 0) {
            return LayoutDirection.Ltr;
        }
        if (i != 1) {
            return null;
        }
        return LayoutDirection.Rtl;
    }
}
