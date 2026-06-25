package androidx.viewpager.widget;

import F.b;
import F.g;
import F.k;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
@g
public class PagerTitleStrip extends ViewGroup {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public ViewPager f1951a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public int f1952b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public float f1953c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public int f1954d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public boolean f1955e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public WeakReference f1956f;

    private static void setSingleLineAllCaps(TextView textView) {
        Context context = textView.getContext();
        b bVar = new b();
        bVar.f117a = context.getResources().getConfiguration().locale;
        textView.setTransformationMethod(bVar);
    }

    public final void a(F.a aVar, F.a aVar2) {
        if (aVar != null) {
            aVar.r(null);
            this.f1956f = null;
        }
        if (aVar2 != null) {
            aVar2.k(null);
            this.f1956f = new WeakReference(aVar2);
        }
        ViewPager viewPager = this.f1951a;
        if (viewPager == null) {
            return;
        }
        this.f1952b = -1;
        this.f1953c = -1.0f;
        b(viewPager.getCurrentItem(), aVar2);
        throw null;
    }

    public final void b(int i2, F.a aVar) {
        if (aVar != null) {
            aVar.d();
        }
        this.f1955e = true;
        if (i2 >= 1 && aVar != null) {
            aVar.f(i2 - 1);
        }
        throw null;
    }

    public void c(int i2, float f2) {
        if (i2 == this.f1952b) {
            throw null;
        }
        b(i2, this.f1951a.getAdapter());
        throw null;
    }

    public int getMinHeight() {
        Drawable background = getBackground();
        if (background != null) {
            return background.getIntrinsicHeight();
        }
        return 0;
    }

    public int getTextSpacing() {
        return this.f1954d;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        ViewParent parent = getParent();
        if (!(parent instanceof ViewPager)) {
            throw new IllegalStateException("PagerTitleStrip must be a direct child of a ViewPager.");
        }
        ViewPager viewPager = (ViewPager) parent;
        F.a adapter = viewPager.getAdapter();
        viewPager.f1979T = null;
        if (viewPager.f1980U == null) {
            viewPager.f1980U = new ArrayList();
        }
        viewPager.f1980U.add(null);
        this.f1951a = viewPager;
        WeakReference weakReference = this.f1956f;
        a(weakReference != null ? (F.a) weakReference.get() : null, adapter);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ViewPager viewPager = this.f1951a;
        if (viewPager != null) {
            a(viewPager.getAdapter(), null);
            ViewPager viewPager2 = this.f1951a;
            k kVar = viewPager2.f1979T;
            viewPager2.f1979T = null;
            ArrayList arrayList = viewPager2.f1980U;
            if (arrayList != null) {
                arrayList.remove((Object) null);
            }
            this.f1951a = null;
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        if (this.f1951a != null) {
            float f2 = this.f1953c;
            if (f2 < 0.0f) {
                f2 = 0.0f;
            }
            c(this.f1952b, f2);
        }
    }

    @Override // android.view.View
    public final void onMeasure(int i2, int i3) {
        if (View.MeasureSpec.getMode(i2) != 1073741824) {
            throw new IllegalStateException("Must measure with an exact width");
        }
        ViewGroup.getChildMeasureSpec(i3, getPaddingBottom() + getPaddingTop(), -2);
        ViewGroup.getChildMeasureSpec(i2, (int) (View.MeasureSpec.getSize(i2) * 0.2f), -2);
        throw null;
    }

    @Override // android.view.View, android.view.ViewParent
    public final void requestLayout() {
        if (this.f1955e) {
            return;
        }
        super.requestLayout();
    }

    public void setGravity(int i2) {
        requestLayout();
    }

    public void setNonPrimaryAlpha(float f2) {
        throw null;
    }

    public void setTextColor(int i2) {
        throw null;
    }

    public void setTextSpacing(int i2) {
        this.f1954d = i2;
        requestLayout();
    }
}
