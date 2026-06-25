package androidx.constraintlayout.widget;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import i.C0138a;
import i.C0139b;
import j.C0141b;
import j.d;

/* JADX INFO: loaded from: classes.dex */
public class ConstraintLayout extends ViewGroup {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public int f1360a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public int f1361b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public int f1362c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public int f1363d;

    @Override // android.view.ViewGroup
    public final void addView(View view, int i2, ViewGroup.LayoutParams layoutParams) {
        super.addView(view, i2, layoutParams);
    }

    @Override // android.view.ViewGroup
    public final boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof C0141b;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void dispatchDraw(Canvas canvas) {
        Object tag;
        super.dispatchDraw(canvas);
        if (isInEditMode()) {
            int childCount = getChildCount();
            float width = getWidth();
            float height = getHeight();
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = getChildAt(i2);
                if (childAt.getVisibility() != 8 && (tag = childAt.getTag()) != null && (tag instanceof String)) {
                    String[] strArrSplit = ((String) tag).split(",");
                    if (strArrSplit.length == 4) {
                        int i3 = Integer.parseInt(strArrSplit[0]);
                        int i4 = Integer.parseInt(strArrSplit[1]);
                        int i5 = Integer.parseInt(strArrSplit[2]);
                        int i6 = (int) ((i3 / 1080.0f) * width);
                        int i7 = (int) ((i4 / 1920.0f) * height);
                        Paint paint = new Paint();
                        paint.setColor(-65536);
                        float f2 = i6;
                        float f3 = i7;
                        float f4 = i6 + ((int) ((i5 / 1080.0f) * width));
                        canvas.drawLine(f2, f3, f4, f3, paint);
                        float f5 = i7 + ((int) ((Integer.parseInt(strArrSplit[3]) / 1920.0f) * height));
                        canvas.drawLine(f4, f3, f4, f5, paint);
                        canvas.drawLine(f4, f5, f2, f5, paint);
                        canvas.drawLine(f2, f5, f2, f3, paint);
                        paint.setColor(-16711936);
                        canvas.drawLine(f2, f3, f4, f5, paint);
                        canvas.drawLine(f2, f5, f4, f3, paint);
                    }
                }
            }
        }
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new C0141b();
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new C0141b(getContext(), attributeSet);
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new C0141b(layoutParams);
    }

    public int getMaxHeight() {
        return this.f1363d;
    }

    public int getMaxWidth() {
        return this.f1362c;
    }

    public int getMinHeight() {
        return this.f1361b;
    }

    public int getMinWidth() {
        return this.f1360a;
    }

    public int getOptimizationLevel() {
        throw null;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        View content;
        int childCount = getChildCount();
        boolean zIsInEditMode = isInEditMode();
        for (int i6 = 0; i6 < childCount; i6++) {
            View childAt = getChildAt(i6);
            C0141b c0141b = (C0141b) childAt.getLayoutParams();
            C0138a c0138a = c0141b.f2653X;
            if ((childAt.getVisibility() != 8 || c0141b.f2651U || c0141b.V || zIsInEditMode) && !c0141b.f2652W) {
                c0138a.getClass();
                int iB = c0138a.b();
                int iA = c0138a.a();
                childAt.layout(0, 0, iB, iA);
                if ((childAt instanceof Placeholder) && (content = ((Placeholder) childAt).getContent()) != null) {
                    content.setVisibility(0);
                    content.layout(0, 0, iB, iA);
                }
            }
        }
        throw null;
    }

    @Override // android.view.View
    public final void onMeasure(int i2, int i3) {
        System.currentTimeMillis();
        View.MeasureSpec.getMode(i2);
        View.MeasureSpec.getSize(i2);
        View.MeasureSpec.getMode(i3);
        View.MeasureSpec.getSize(i3);
        getPaddingLeft();
        getPaddingTop();
        throw null;
    }

    @Override // android.view.ViewGroup
    public final void onViewAdded(View view) {
        super.onViewAdded(view);
        C0138a c0138a = (view == this || view == null) ? null : ((C0141b) view.getLayoutParams()).f2653X;
        if ((view instanceof Guideline) && !(c0138a instanceof C0139b)) {
            C0141b c0141b = (C0141b) view.getLayoutParams();
            C0139b c0139b = new C0139b();
            c0141b.f2653X = c0139b;
            c0141b.f2651U = true;
            c0139b.c(c0141b.f2648R);
        }
        if (!(view instanceof ConstraintHelper)) {
            view.getId();
            throw null;
        }
        ((ConstraintHelper) view).getClass();
        ((C0141b) view.getLayoutParams()).V = true;
        throw null;
    }

    @Override // android.view.ViewGroup
    public final void onViewRemoved(View view) {
        super.onViewRemoved(view);
        view.getId();
        throw null;
    }

    @Override // android.view.ViewGroup, android.view.ViewManager
    public final void removeView(View view) {
        super.removeView(view);
    }

    @Override // android.view.View, android.view.ViewParent
    public final void requestLayout() {
        super.requestLayout();
    }

    public void setConstraintSet(d dVar) {
    }

    @Override // android.view.View
    public void setId(int i2) {
        getId();
        throw null;
    }

    public void setMaxHeight(int i2) {
        if (i2 == this.f1363d) {
            return;
        }
        this.f1363d = i2;
        requestLayout();
    }

    public void setMaxWidth(int i2) {
        if (i2 == this.f1362c) {
            return;
        }
        this.f1362c = i2;
        requestLayout();
    }

    public void setMinHeight(int i2) {
        if (i2 == this.f1361b) {
            return;
        }
        this.f1361b = i2;
        requestLayout();
    }

    public void setMinWidth(int i2) {
        if (i2 == this.f1360a) {
            return;
        }
        this.f1360a = i2;
        requestLayout();
    }

    public void setOptimizationLevel(int i2) {
        throw null;
    }

    @Override // android.view.ViewGroup
    public final boolean shouldDelayChildPressedState() {
        return false;
    }
}
