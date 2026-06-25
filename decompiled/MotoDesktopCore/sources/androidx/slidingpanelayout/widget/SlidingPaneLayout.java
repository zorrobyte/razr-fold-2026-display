package androidx.slidingpanelayout.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import androidx.customview.view.AbsSavedState;
import java.util.WeakHashMap;
import v.l;

/* JADX INFO: loaded from: classes.dex */
public class SlidingPaneLayout extends ViewGroup {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public int f1921a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public int f1922b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public Drawable f1923c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public Drawable f1924d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public boolean f1925e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public View f1926f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public int f1927g;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public float f1928h;

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public float f1929i;

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public boolean f1930j;

    /* JADX INFO: renamed from: k, reason: collision with root package name */
    public boolean f1931k;

    public static class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new c();

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        public boolean f1932c;

        public SavedState(Parcel parcel) {
            super(parcel, null);
            this.f1932c = parcel.readInt() != 0;
        }

        @Override // androidx.customview.view.AbsSavedState, android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i2) {
            super.writeToParcel(parcel, i2);
            parcel.writeInt(this.f1932c ? 1 : 0);
        }
    }

    public final boolean a() {
        WeakHashMap weakHashMap = l.f2836a;
        return getLayoutDirection() == 1;
    }

    public final void b() {
        if (this.f1925e) {
            boolean zA = a();
            a aVar = (a) this.f1926f.getLayoutParams();
            if (zA) {
                getPaddingRight();
                int i2 = ((ViewGroup.MarginLayoutParams) aVar).rightMargin;
                this.f1926f.getWidth();
                getWidth();
            } else {
                getPaddingLeft();
                int i3 = ((ViewGroup.MarginLayoutParams) aVar).leftMargin;
            }
            this.f1926f.getTop();
            throw null;
        }
    }

    @Override // android.view.ViewGroup
    public final boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return (layoutParams instanceof a) && super.checkLayoutParams(layoutParams);
    }

    @Override // android.view.View
    public final void computeScroll() {
        throw null;
    }

    @Override // android.view.View
    public final void draw(Canvas canvas) {
        int i2;
        int right;
        super.draw(canvas);
        Drawable drawable = a() ? this.f1924d : this.f1923c;
        View childAt = getChildCount() > 1 ? getChildAt(1) : null;
        if (childAt == null || drawable == null) {
            return;
        }
        int top = childAt.getTop();
        int bottom = childAt.getBottom();
        int intrinsicWidth = drawable.getIntrinsicWidth();
        if (a()) {
            right = childAt.getRight();
            i2 = intrinsicWidth + right;
        } else {
            int left = childAt.getLeft();
            int i3 = left - intrinsicWidth;
            i2 = left;
            right = i3;
        }
        drawable.setBounds(right, top, i2, bottom);
        drawable.draw(canvas);
    }

    @Override // android.view.ViewGroup
    public final boolean drawChild(Canvas canvas, View view, long j2) {
        a aVar = (a) view.getLayoutParams();
        int iSave = canvas.save();
        if (!this.f1925e || aVar.f1935b || this.f1926f == null) {
            boolean zDrawChild = super.drawChild(canvas, view, j2);
            canvas.restoreToCount(iSave);
            return zDrawChild;
        }
        canvas.getClipBounds(null);
        if (a()) {
            throw null;
        }
        throw null;
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateDefaultLayoutParams() {
        a aVar = new a(-1, -1);
        aVar.f1934a = 0.0f;
        return aVar;
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        Context context = getContext();
        a aVar = new a(context, attributeSet);
        aVar.f1934a = 0.0f;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, a.f1933c);
        aVar.f1934a = typedArrayObtainStyledAttributes.getFloat(0, 0.0f);
        typedArrayObtainStyledAttributes.recycle();
        return aVar;
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            a aVar = new a((ViewGroup.MarginLayoutParams) layoutParams);
            aVar.f1934a = 0.0f;
            return aVar;
        }
        a aVar2 = new a(layoutParams);
        aVar2.f1934a = 0.0f;
        return aVar2;
    }

    public int getCoveredFadeColor() {
        return this.f1922b;
    }

    public int getParallaxDistance() {
        return this.f1927g;
    }

    public int getSliderFadeColor() {
        return this.f1921a;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.f1931k = true;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.f1931k = true;
        throw null;
    }

    @Override // android.view.ViewGroup
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (!this.f1925e && actionMasked == 0 && getChildCount() > 1 && getChildAt(1) != null) {
            motionEvent.getX();
            motionEvent.getY();
            throw null;
        }
        if (!this.f1925e || actionMasked == 3 || actionMasked == 1) {
            throw null;
        }
        if (actionMasked == 0) {
            float x2 = motionEvent.getX();
            float y2 = motionEvent.getY();
            this.f1928h = x2;
            this.f1929i = y2;
            throw null;
        }
        if (actionMasked != 2) {
            throw null;
        }
        float x3 = motionEvent.getX();
        float y3 = motionEvent.getY();
        Math.abs(x3 - this.f1928h);
        Math.abs(y3 - this.f1929i);
        throw null;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        if (!a()) {
            throw null;
        }
        throw null;
    }

    /* JADX WARN: Removed duplicated region for block: B:38:0x00ae A[PHI: r14
      0x00ae: PHI (r14v2 float) = (r14v1 float), (r14v3 float) binds: [B:34:0x00a5, B:36:0x00aa] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:71:0x0133  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void onMeasure(int r19, int r20) {
        /*
            Method dump skipped, instruction units count: 528
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.slidingpanelayout.widget.SlidingPaneLayout.onMeasure(int, int):void");
    }

    @Override // android.view.View
    public final void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.f1465a);
        if (savedState.f1932c) {
            if (this.f1931k) {
                this.f1930j = true;
            } else {
                b();
            }
        } else if (this.f1931k) {
            this.f1930j = false;
        } else {
            b();
        }
        this.f1930j = savedState.f1932c;
    }

    @Override // android.view.View
    public final Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        boolean z2 = this.f1925e;
        savedState.f1932c = z2 ? !z2 : this.f1930j;
        return savedState;
    }

    @Override // android.view.View
    public final void onSizeChanged(int i2, int i3, int i4, int i5) {
        super.onSizeChanged(i2, i3, i4, i5);
        if (i2 != i4) {
            this.f1931k = true;
        }
    }

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.f1925e) {
            throw null;
        }
        return super.onTouchEvent(motionEvent);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final void requestChildFocus(View view, View view2) {
        super.requestChildFocus(view, view2);
        if (isInTouchMode() || this.f1925e) {
            return;
        }
        this.f1930j = view == this.f1926f;
    }

    public void setCoveredFadeColor(int i2) {
        this.f1922b = i2;
    }

    public void setPanelSlideListener(b bVar) {
    }

    public void setParallaxDistance(int i2) {
        this.f1927g = i2;
        requestLayout();
    }

    @Deprecated
    public void setShadowDrawable(Drawable drawable) {
        setShadowDrawableLeft(drawable);
    }

    public void setShadowDrawableLeft(Drawable drawable) {
        this.f1923c = drawable;
    }

    public void setShadowDrawableRight(Drawable drawable) {
        this.f1924d = drawable;
    }

    @Deprecated
    public void setShadowResource(int i2) {
        setShadowDrawable(getResources().getDrawable(i2));
    }

    public void setShadowResourceLeft(int i2) {
        setShadowDrawableLeft(getContext().getDrawable(i2));
    }

    public void setShadowResourceRight(int i2) {
        setShadowDrawableRight(getContext().getDrawable(i2));
    }

    public void setSliderFadeColor(int i2) {
        this.f1921a = i2;
    }
}
