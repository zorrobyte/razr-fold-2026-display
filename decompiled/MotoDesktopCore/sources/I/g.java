package I;

import android.graphics.Rect;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.app.AbstractC0054a;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.appbar.AppBarLayout;
import java.util.WeakHashMap;
import k.AbstractC0143b;
import k.C0146e;
import v.l;
import v.o;

/* JADX INFO: loaded from: classes.dex */
public abstract class g extends h {

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final Rect f173c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public final Rect f174d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public int f175e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public int f176f;

    public g() {
        this.f173c = new Rect();
        this.f174d = new Rect();
        this.f175e = 0;
    }

    public g(int i2) {
        super(0);
        this.f173c = new Rect();
        this.f174d = new Rect();
        this.f175e = 0;
    }

    @Override // k.AbstractC0143b
    public final boolean g(CoordinatorLayout coordinatorLayout, View view, int i2, int i3, int i4) {
        AppBarLayout appBarLayoutV;
        int i5 = view.getLayoutParams().height;
        if ((i5 != -1 && i5 != -2) || (appBarLayoutV = AppBarLayout.ScrollingViewBehavior.v(coordinatorLayout.i(view))) == null) {
            return false;
        }
        WeakHashMap weakHashMap = l.f2836a;
        if (appBarLayoutV.getFitsSystemWindows() && !view.getFitsSystemWindows()) {
            view.setFitsSystemWindows(true);
            if (view.getFitsSystemWindows()) {
                view.requestLayout();
                return true;
            }
        }
        int size = View.MeasureSpec.getSize(i4);
        if (size == 0) {
            size = coordinatorLayout.getHeight();
        }
        coordinatorLayout.q(view, i2, i3, View.MeasureSpec.makeMeasureSpec(appBarLayoutV.getTotalScrollRange() + (size - appBarLayoutV.getMeasuredHeight()), i5 == -1 ? 1073741824 : Integer.MIN_VALUE));
        return true;
    }

    @Override // I.h
    public final void t(CoordinatorLayout coordinatorLayout, View view, int i2) {
        AppBarLayout appBarLayoutV = AppBarLayout.ScrollingViewBehavior.v(coordinatorLayout.i(view));
        if (appBarLayoutV == null) {
            coordinatorLayout.p(view, i2);
            this.f175e = 0;
            return;
        }
        C0146e c0146e = (C0146e) view.getLayoutParams();
        int paddingLeft = coordinatorLayout.getPaddingLeft() + ((ViewGroup.MarginLayoutParams) c0146e).leftMargin;
        int bottom = appBarLayoutV.getBottom() + ((ViewGroup.MarginLayoutParams) c0146e).topMargin;
        int width = (coordinatorLayout.getWidth() - coordinatorLayout.getPaddingRight()) - ((ViewGroup.MarginLayoutParams) c0146e).rightMargin;
        int bottom2 = ((appBarLayoutV.getBottom() + coordinatorLayout.getHeight()) - coordinatorLayout.getPaddingBottom()) - ((ViewGroup.MarginLayoutParams) c0146e).bottomMargin;
        Rect rect = this.f173c;
        rect.set(paddingLeft, bottom, width, bottom2);
        o lastWindowInsets = coordinatorLayout.getLastWindowInsets();
        if (lastWindowInsets != null) {
            WeakHashMap weakHashMap = l.f2836a;
            if (coordinatorLayout.getFitsSystemWindows() && !view.getFitsSystemWindows()) {
                rect.left = lastWindowInsets.a() + rect.left;
                rect.right -= lastWindowInsets.b();
            }
        }
        int i3 = c0146e.f2748c;
        if (i3 == 0) {
            i3 = 8388659;
        }
        int i4 = i3;
        int measuredWidth = view.getMeasuredWidth();
        int measuredHeight = view.getMeasuredHeight();
        Rect rect2 = this.f174d;
        Gravity.apply(i4, measuredWidth, measuredHeight, rect, rect2, i2);
        int iU = u(appBarLayoutV);
        view.layout(rect2.left, rect2.top - iU, rect2.right, rect2.bottom - iU);
        this.f175e = rect2.top - appBarLayoutV.getBottom();
    }

    public final int u(View view) {
        int i2;
        if (this.f176f == 0) {
            return 0;
        }
        float f2 = 0.0f;
        if (view instanceof AppBarLayout) {
            AppBarLayout appBarLayout = (AppBarLayout) view;
            int totalScrollRange = appBarLayout.getTotalScrollRange();
            int downNestedPreScrollRange = appBarLayout.getDownNestedPreScrollRange();
            AbstractC0143b abstractC0143b = ((C0146e) appBarLayout.getLayoutParams()).f2746a;
            int iV = abstractC0143b instanceof AppBarLayout.BaseBehavior ? ((AppBarLayout.BaseBehavior) abstractC0143b).v() : 0;
            if ((downNestedPreScrollRange == 0 || totalScrollRange + iV > downNestedPreScrollRange) && (i2 = totalScrollRange - downNestedPreScrollRange) != 0) {
                f2 = (iV / i2) + 1.0f;
            }
        }
        int i3 = this.f176f;
        return AbstractC0054a.g((int) (f2 * i3), 0, i3);
    }
}
