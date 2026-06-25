package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.appcompat.R$id;
import androidx.appcompat.R$styleable;
import java.util.WeakHashMap;

/* JADX INFO: loaded from: classes.dex */
public class ActionBarContainer extends FrameLayout {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public boolean f861a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public ScrollingTabContainerView f862b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public View f863c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public View f864d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public Drawable f865e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public Drawable f866f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public Drawable f867g;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public final boolean f868h;

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public boolean f869i;

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public final int f870j;

    public ActionBarContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        C0065b c0065b = new C0065b(this);
        WeakHashMap weakHashMap = v.l.f2836a;
        setBackground(c0065b);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.ActionBar);
        this.f865e = typedArrayObtainStyledAttributes.getDrawable(R$styleable.ActionBar_background);
        this.f866f = typedArrayObtainStyledAttributes.getDrawable(R$styleable.ActionBar_backgroundStacked);
        this.f870j = typedArrayObtainStyledAttributes.getDimensionPixelSize(R$styleable.ActionBar_height, -1);
        boolean z2 = true;
        if (getId() == R$id.split_action_bar) {
            this.f868h = true;
            this.f867g = typedArrayObtainStyledAttributes.getDrawable(R$styleable.ActionBar_backgroundSplit);
        }
        typedArrayObtainStyledAttributes.recycle();
        if (!this.f868h ? this.f865e != null || this.f866f != null : this.f867g != null) {
            z2 = false;
        }
        setWillNotDraw(z2);
    }

    public static int a(View view) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
        return view.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void drawableStateChanged() {
        super.drawableStateChanged();
        Drawable drawable = this.f865e;
        if (drawable != null && drawable.isStateful()) {
            this.f865e.setState(getDrawableState());
        }
        Drawable drawable2 = this.f866f;
        if (drawable2 != null && drawable2.isStateful()) {
            this.f866f.setState(getDrawableState());
        }
        Drawable drawable3 = this.f867g;
        if (drawable3 == null || !drawable3.isStateful()) {
            return;
        }
        this.f867g.setState(getDrawableState());
    }

    public View getTabContainer() {
        return this.f862b;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        Drawable drawable = this.f865e;
        if (drawable != null) {
            drawable.jumpToCurrentState();
        }
        Drawable drawable2 = this.f866f;
        if (drawable2 != null) {
            drawable2.jumpToCurrentState();
        }
        Drawable drawable3 = this.f867g;
        if (drawable3 != null) {
            drawable3.jumpToCurrentState();
        }
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.f863c = findViewById(R$id.action_bar);
        this.f864d = findViewById(R$id.action_context_bar);
    }

    @Override // android.view.View
    public final boolean onHoverEvent(MotionEvent motionEvent) {
        super.onHoverEvent(motionEvent);
        return true;
    }

    @Override // android.view.ViewGroup
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return this.f861a || super.onInterceptTouchEvent(motionEvent);
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0048 A[PHI: r0
      0x0048: PHI (r0v8 boolean) = (r0v1 boolean), (r0v1 boolean), (r0v0 boolean) binds: [B:31:0x00a5, B:33:0x00a9, B:15:0x0039] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void onLayout(boolean r5, int r6, int r7, int r8, int r9) {
        /*
            r4 = this;
            super.onLayout(r5, r6, r7, r8, r9)
            androidx.appcompat.widget.ScrollingTabContainerView r5 = r4.f862b
            r7 = 8
            r9 = 1
            r0 = 0
            if (r5 == 0) goto L13
            int r1 = r5.getVisibility()
            if (r1 == r7) goto L13
            r1 = r9
            goto L14
        L13:
            r1 = r0
        L14:
            if (r5 == 0) goto L33
            int r2 = r5.getVisibility()
            if (r2 == r7) goto L33
            int r7 = r4.getMeasuredHeight()
            android.view.ViewGroup$LayoutParams r2 = r5.getLayoutParams()
            android.widget.FrameLayout$LayoutParams r2 = (android.widget.FrameLayout.LayoutParams) r2
            int r3 = r5.getMeasuredHeight()
            int r3 = r7 - r3
            int r2 = r2.bottomMargin
            int r3 = r3 - r2
            int r7 = r7 - r2
            r5.layout(r6, r3, r8, r7)
        L33:
            boolean r6 = r4.f868h
            if (r6 == 0) goto L4b
            android.graphics.drawable.Drawable r5 = r4.f867g
            if (r5 == 0) goto L48
            int r6 = r4.getMeasuredWidth()
            int r7 = r4.getMeasuredHeight()
            r5.setBounds(r0, r0, r6, r7)
            goto Lbe
        L48:
            r9 = r0
            goto Lbe
        L4b:
            android.graphics.drawable.Drawable r6 = r4.f865e
            if (r6 == 0) goto La3
            android.view.View r6 = r4.f863c
            int r6 = r6.getVisibility()
            if (r6 != 0) goto L75
            android.graphics.drawable.Drawable r6 = r4.f865e
            android.view.View r7 = r4.f863c
            int r7 = r7.getLeft()
            android.view.View r8 = r4.f863c
            int r8 = r8.getTop()
            android.view.View r0 = r4.f863c
            int r0 = r0.getRight()
            android.view.View r2 = r4.f863c
            int r2 = r2.getBottom()
            r6.setBounds(r7, r8, r0, r2)
            goto La2
        L75:
            android.view.View r6 = r4.f864d
            if (r6 == 0) goto L9d
            int r6 = r6.getVisibility()
            if (r6 != 0) goto L9d
            android.graphics.drawable.Drawable r6 = r4.f865e
            android.view.View r7 = r4.f864d
            int r7 = r7.getLeft()
            android.view.View r8 = r4.f864d
            int r8 = r8.getTop()
            android.view.View r0 = r4.f864d
            int r0 = r0.getRight()
            android.view.View r2 = r4.f864d
            int r2 = r2.getBottom()
            r6.setBounds(r7, r8, r0, r2)
            goto La2
        L9d:
            android.graphics.drawable.Drawable r6 = r4.f865e
            r6.setBounds(r0, r0, r0, r0)
        La2:
            r0 = r9
        La3:
            r4.f869i = r1
            if (r1 == 0) goto L48
            android.graphics.drawable.Drawable r6 = r4.f866f
            if (r6 == 0) goto L48
            int r7 = r5.getLeft()
            int r8 = r5.getTop()
            int r0 = r5.getRight()
            int r5 = r5.getBottom()
            r6.setBounds(r7, r8, r0, r5)
        Lbe:
            if (r9 == 0) goto Lc3
            r4.invalidate()
        Lc3:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.ActionBarContainer.onLayout(boolean, int, int, int, int):void");
    }

    @Override // android.widget.FrameLayout, android.view.View
    public final void onMeasure(int i2, int i3) {
        int iA;
        int i4;
        if (this.f863c == null && View.MeasureSpec.getMode(i3) == Integer.MIN_VALUE && (i4 = this.f870j) >= 0) {
            i3 = View.MeasureSpec.makeMeasureSpec(Math.min(i4, View.MeasureSpec.getSize(i3)), Integer.MIN_VALUE);
        }
        super.onMeasure(i2, i3);
        if (this.f863c == null) {
            return;
        }
        int mode = View.MeasureSpec.getMode(i3);
        ScrollingTabContainerView scrollingTabContainerView = this.f862b;
        if (scrollingTabContainerView == null || scrollingTabContainerView.getVisibility() == 8 || mode == 1073741824) {
            return;
        }
        View view = this.f863c;
        if (view == null || view.getVisibility() == 8 || view.getMeasuredHeight() == 0) {
            View view2 = this.f864d;
            iA = (view2 == null || view2.getVisibility() == 8 || view2.getMeasuredHeight() == 0) ? 0 : a(this.f864d);
        } else {
            iA = a(this.f863c);
        }
        setMeasuredDimension(getMeasuredWidth(), Math.min(a(this.f862b) + iA, mode == Integer.MIN_VALUE ? View.MeasureSpec.getSize(i3) : Integer.MAX_VALUE));
    }

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        super.onTouchEvent(motionEvent);
        return true;
    }

    public void setPrimaryBackground(Drawable drawable) {
        Drawable drawable2 = this.f865e;
        if (drawable2 != null) {
            drawable2.setCallback(null);
            unscheduleDrawable(this.f865e);
        }
        this.f865e = drawable;
        if (drawable != null) {
            drawable.setCallback(this);
            View view = this.f863c;
            if (view != null) {
                this.f865e.setBounds(view.getLeft(), this.f863c.getTop(), this.f863c.getRight(), this.f863c.getBottom());
            }
        }
        boolean z2 = false;
        if (!this.f868h ? !(this.f865e != null || this.f866f != null) : this.f867g == null) {
            z2 = true;
        }
        setWillNotDraw(z2);
        invalidate();
    }

    public void setSplitBackground(Drawable drawable) {
        Drawable drawable2;
        Drawable drawable3 = this.f867g;
        if (drawable3 != null) {
            drawable3.setCallback(null);
            unscheduleDrawable(this.f867g);
        }
        this.f867g = drawable;
        boolean z2 = this.f868h;
        boolean z3 = false;
        if (drawable != null) {
            drawable.setCallback(this);
            if (z2 && (drawable2 = this.f867g) != null) {
                drawable2.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
            }
        }
        if (!z2 ? !(this.f865e != null || this.f866f != null) : this.f867g == null) {
            z3 = true;
        }
        setWillNotDraw(z3);
        invalidate();
    }

    public void setStackedBackground(Drawable drawable) {
        Drawable drawable2;
        Drawable drawable3 = this.f866f;
        if (drawable3 != null) {
            drawable3.setCallback(null);
            unscheduleDrawable(this.f866f);
        }
        this.f866f = drawable;
        if (drawable != null) {
            drawable.setCallback(this);
            if (this.f869i && (drawable2 = this.f866f) != null) {
                drawable2.setBounds(this.f862b.getLeft(), this.f862b.getTop(), this.f862b.getRight(), this.f862b.getBottom());
            }
        }
        boolean z2 = false;
        if (!this.f868h ? !(this.f865e != null || this.f866f != null) : this.f867g == null) {
            z2 = true;
        }
        setWillNotDraw(z2);
        invalidate();
    }

    public void setTabContainer(ScrollingTabContainerView scrollingTabContainerView) {
        ScrollingTabContainerView scrollingTabContainerView2 = this.f862b;
        if (scrollingTabContainerView2 != null) {
            removeView(scrollingTabContainerView2);
        }
        this.f862b = scrollingTabContainerView;
        if (scrollingTabContainerView != null) {
            addView(scrollingTabContainerView);
            ViewGroup.LayoutParams layoutParams = scrollingTabContainerView.getLayoutParams();
            layoutParams.width = -1;
            layoutParams.height = -2;
            scrollingTabContainerView.setAllowCollapse(false);
        }
    }

    public void setTransitioning(boolean z2) {
        this.f861a = z2;
        setDescendantFocusability(z2 ? 393216 : 262144);
    }

    @Override // android.view.View
    public void setVisibility(int i2) {
        super.setVisibility(i2);
        boolean z2 = i2 == 0;
        Drawable drawable = this.f865e;
        if (drawable != null) {
            drawable.setVisible(z2, false);
        }
        Drawable drawable2 = this.f866f;
        if (drawable2 != null) {
            drawable2.setVisible(z2, false);
        }
        Drawable drawable3 = this.f867g;
        if (drawable3 != null) {
            drawable3.setVisible(z2, false);
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final ActionMode startActionModeForChild(View view, ActionMode.Callback callback) {
        return null;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final ActionMode startActionModeForChild(View view, ActionMode.Callback callback, int i2) {
        if (i2 != 0) {
            return super.startActionModeForChild(view, callback, i2);
        }
        return null;
    }

    @Override // android.view.View
    public final boolean verifyDrawable(Drawable drawable) {
        Drawable drawable2 = this.f865e;
        boolean z2 = this.f868h;
        return (drawable == drawable2 && !z2) || (drawable == this.f866f && this.f869i) || ((drawable == this.f867g && z2) || super.verifyDrawable(drawable));
    }
}
