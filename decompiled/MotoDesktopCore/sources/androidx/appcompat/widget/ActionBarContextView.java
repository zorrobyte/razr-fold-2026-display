package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.R$attr;
import androidx.appcompat.R$id;
import androidx.appcompat.R$layout;
import androidx.appcompat.R$styleable;
import b.AbstractC0122a;
import java.util.WeakHashMap;

/* JADX INFO: loaded from: classes.dex */
public class ActionBarContextView extends ViewGroup {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final C0064a f871a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final Context f872b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public ActionMenuView f873c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public C0074k f874d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public int f875e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public v.m f876f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public boolean f877g;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public boolean f878h;

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public CharSequence f879i;

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public CharSequence f880j;

    /* JADX INFO: renamed from: k, reason: collision with root package name */
    public View f881k;

    /* JADX INFO: renamed from: l, reason: collision with root package name */
    public View f882l;

    /* JADX INFO: renamed from: m, reason: collision with root package name */
    public LinearLayout f883m;

    /* JADX INFO: renamed from: n, reason: collision with root package name */
    public TextView f884n;

    /* JADX INFO: renamed from: o, reason: collision with root package name */
    public TextView f885o;

    /* JADX INFO: renamed from: p, reason: collision with root package name */
    public final int f886p;

    /* JADX INFO: renamed from: q, reason: collision with root package name */
    public final int f887q;
    public boolean r;

    /* JADX INFO: renamed from: s, reason: collision with root package name */
    public final int f888s;

    /* JADX WARN: Illegal instructions before constructor call */
    public ActionBarContextView(Context context, AttributeSet attributeSet) {
        int resourceId;
        int i2 = R$attr.actionModeStyle;
        super(context, attributeSet, i2);
        this.f871a = new C0064a(this);
        TypedValue typedValue = new TypedValue();
        if (!context.getTheme().resolveAttribute(R$attr.actionBarPopupTheme, typedValue, true) || typedValue.resourceId == 0) {
            this.f872b = context;
        } else {
            this.f872b = new ContextThemeWrapper(context, typedValue.resourceId);
        }
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.ActionMode, i2, 0);
        int i3 = R$styleable.ActionMode_background;
        Drawable drawable = (!typedArrayObtainStyledAttributes.hasValue(i3) || (resourceId = typedArrayObtainStyledAttributes.getResourceId(i3, 0)) == 0) ? typedArrayObtainStyledAttributes.getDrawable(i3) : AbstractC0122a.a(context, resourceId);
        WeakHashMap weakHashMap = v.l.f2836a;
        setBackground(drawable);
        this.f886p = typedArrayObtainStyledAttributes.getResourceId(R$styleable.ActionMode_titleTextStyle, 0);
        this.f887q = typedArrayObtainStyledAttributes.getResourceId(R$styleable.ActionMode_subtitleTextStyle, 0);
        this.f875e = typedArrayObtainStyledAttributes.getLayoutDimension(R$styleable.ActionMode_height, 0);
        this.f888s = typedArrayObtainStyledAttributes.getResourceId(R$styleable.ActionMode_closeItemLayout, R$layout.abc_action_mode_close_item_material);
        typedArrayObtainStyledAttributes.recycle();
    }

    public static int e(View view, int i2, int i3) {
        view.measure(View.MeasureSpec.makeMeasureSpec(i2, Integer.MIN_VALUE), i3);
        return Math.max(0, i2 - view.getMeasuredWidth());
    }

    public static int f(int i2, int i3, int i4, View view, boolean z2) {
        int measuredWidth = view.getMeasuredWidth();
        int measuredHeight = view.getMeasuredHeight();
        int i5 = ((i4 - measuredHeight) / 2) + i3;
        if (z2) {
            view.layout(i2 - measuredWidth, i5, i2, measuredHeight + i5);
        } else {
            view.layout(i2, i5, i2 + measuredWidth, measuredHeight + i5);
        }
        return z2 ? -measuredWidth : measuredWidth;
    }

    public final void c(d.b bVar) {
        View view = this.f881k;
        if (view == null) {
            View viewInflate = LayoutInflater.from(getContext()).inflate(this.f888s, (ViewGroup) this, false);
            this.f881k = viewInflate;
            addView(viewInflate);
        } else if (view.getParent() == null) {
            addView(this.f881k);
        }
        this.f881k.findViewById(R$id.action_mode_close_button).setOnClickListener(new K.c(3, bVar));
        androidx.appcompat.view.menu.o oVarD = bVar.d();
        C0074k c0074k = this.f874d;
        if (c0074k != null) {
            c0074k.j();
            C0069f c0069f = c0074k.f1244u;
            if (c0069f != null && c0069f.b()) {
                c0069f.f854j.dismiss();
            }
        }
        C0074k c0074k2 = new C0074k(getContext());
        this.f874d = c0074k2;
        c0074k2.f1236l = true;
        c0074k2.f1237m = true;
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-2, -1);
        oVarD.b(this.f874d, this.f872b);
        C0074k c0074k3 = this.f874d;
        androidx.appcompat.view.menu.C c2 = c0074k3.f1232h;
        if (c2 == null) {
            androidx.appcompat.view.menu.C c3 = (androidx.appcompat.view.menu.C) c0074k3.f1228d.inflate(c0074k3.f1230f, (ViewGroup) this, false);
            c0074k3.f1232h = c3;
            c3.b(c0074k3.f1227c);
            c0074k3.f();
        }
        androidx.appcompat.view.menu.C c4 = c0074k3.f1232h;
        if (c2 != c4) {
            ((ActionMenuView) c4).setPresenter(c0074k3);
        }
        ActionMenuView actionMenuView = (ActionMenuView) c4;
        this.f873c = actionMenuView;
        WeakHashMap weakHashMap = v.l.f2836a;
        actionMenuView.setBackground(null);
        addView(this.f873c, layoutParams);
    }

    public final void d() {
        if (this.f883m == null) {
            LayoutInflater.from(getContext()).inflate(R$layout.abc_action_bar_title_item, this);
            LinearLayout linearLayout = (LinearLayout) getChildAt(getChildCount() - 1);
            this.f883m = linearLayout;
            this.f884n = (TextView) linearLayout.findViewById(R$id.action_bar_title);
            this.f885o = (TextView) this.f883m.findViewById(R$id.action_bar_subtitle);
            int i2 = this.f886p;
            if (i2 != 0) {
                this.f884n.setTextAppearance(getContext(), i2);
            }
            int i3 = this.f887q;
            if (i3 != 0) {
                this.f885o.setTextAppearance(getContext(), i3);
            }
        }
        this.f884n.setText(this.f879i);
        this.f885o.setText(this.f880j);
        boolean z2 = !TextUtils.isEmpty(this.f879i);
        boolean z3 = !TextUtils.isEmpty(this.f880j);
        this.f885o.setVisibility(z3 ? 0 : 8);
        this.f883m.setVisibility((z2 || z3) ? 0 : 8);
        if (this.f883m.getParent() == null) {
            addView(this.f883m);
        }
    }

    @Override // android.view.View
    /* JADX INFO: renamed from: g, reason: merged with bridge method [inline-methods] */
    public final void setVisibility(int i2) {
        if (i2 != getVisibility()) {
            v.m mVar = this.f876f;
            if (mVar != null) {
                mVar.b();
            }
            super.setVisibility(i2);
        }
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new ViewGroup.MarginLayoutParams(-1, -2);
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new ViewGroup.MarginLayoutParams(getContext(), attributeSet);
    }

    public int getAnimatedVisibility() {
        return this.f876f != null ? this.f871a.f1199b : getVisibility();
    }

    public int getContentHeight() {
        return this.f875e;
    }

    public CharSequence getSubtitle() {
        return this.f880j;
    }

    public CharSequence getTitle() {
        return this.f879i;
    }

    public final v.m h(int i2, long j2) {
        v.m mVar = this.f876f;
        if (mVar != null) {
            mVar.b();
        }
        C0064a c0064a = this.f871a;
        if (i2 != 0) {
            v.m mVarA = v.l.a(this);
            mVarA.a(0.0f);
            mVarA.c(j2);
            c0064a.f1200c.f876f = mVarA;
            c0064a.f1199b = i2;
            mVarA.d(c0064a);
            return mVarA;
        }
        if (getVisibility() != 0) {
            setAlpha(0.0f);
        }
        v.m mVarA2 = v.l.a(this);
        mVarA2.a(1.0f);
        mVarA2.c(j2);
        c0064a.f1200c.f876f = mVarA2;
        c0064a.f1199b = i2;
        mVarA2.d(c0064a);
        return mVarA2;
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(null, R$styleable.ActionBar, R$attr.actionBarStyle, 0);
        setContentHeight(typedArrayObtainStyledAttributes.getLayoutDimension(R$styleable.ActionBar_height, 0));
        typedArrayObtainStyledAttributes.recycle();
        C0074k c0074k = this.f874d;
        if (c0074k != null) {
            Configuration configuration2 = c0074k.f1226b.getResources().getConfiguration();
            int i2 = configuration2.screenWidthDp;
            int i3 = configuration2.screenHeightDp;
            c0074k.f1240p = (configuration2.smallestScreenWidthDp > 600 || i2 > 600 || (i2 > 960 && i3 > 720) || (i2 > 720 && i3 > 960)) ? 5 : (i2 >= 500 || (i2 > 640 && i3 > 480) || (i2 > 480 && i3 > 640)) ? 4 : i2 >= 360 ? 3 : 2;
            androidx.appcompat.view.menu.o oVar = c0074k.f1227c;
            if (oVar != null) {
                oVar.p(true);
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        C0074k c0074k = this.f874d;
        if (c0074k != null) {
            c0074k.j();
            C0069f c0069f = this.f874d.f1244u;
            if (c0069f == null || !c0069f.b()) {
                return;
            }
            c0069f.f854j.dismiss();
        }
    }

    @Override // android.view.View
    public final boolean onHoverEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 9) {
            this.f878h = false;
        }
        if (!this.f878h) {
            boolean zOnHoverEvent = super.onHoverEvent(motionEvent);
            if (actionMasked == 9 && !zOnHoverEvent) {
                this.f878h = true;
            }
        }
        if (actionMasked == 10 || actionMasked == 3) {
            this.f878h = false;
        }
        return true;
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        if (accessibilityEvent.getEventType() != 32) {
            super.onInitializeAccessibilityEvent(accessibilityEvent);
            return;
        }
        accessibilityEvent.setSource(this);
        accessibilityEvent.setClassName(getClass().getName());
        accessibilityEvent.setPackageName(getContext().getPackageName());
        accessibilityEvent.setContentDescription(this.f879i);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        boolean zA = y0.a(this);
        int paddingRight = zA ? (i4 - i2) - getPaddingRight() : getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingTop2 = ((i5 - i3) - getPaddingTop()) - getPaddingBottom();
        View view = this.f881k;
        if (view != null && view.getVisibility() != 8) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.f881k.getLayoutParams();
            int i6 = zA ? marginLayoutParams.rightMargin : marginLayoutParams.leftMargin;
            int i7 = zA ? marginLayoutParams.leftMargin : marginLayoutParams.rightMargin;
            int i8 = zA ? paddingRight - i6 : paddingRight + i6;
            int iF = f(i8, paddingTop, paddingTop2, this.f881k, zA) + i8;
            paddingRight = zA ? iF - i7 : iF + i7;
        }
        LinearLayout linearLayout = this.f883m;
        if (linearLayout != null && this.f882l == null && linearLayout.getVisibility() != 8) {
            paddingRight += f(paddingRight, paddingTop, paddingTop2, this.f883m, zA);
        }
        View view2 = this.f882l;
        if (view2 != null) {
            f(paddingRight, paddingTop, paddingTop2, view2, zA);
        }
        int paddingLeft = zA ? getPaddingLeft() : (i4 - i2) - getPaddingRight();
        ActionMenuView actionMenuView = this.f873c;
        if (actionMenuView != null) {
            f(paddingLeft, paddingTop, paddingTop2, actionMenuView, !zA);
        }
    }

    @Override // android.view.View
    public final void onMeasure(int i2, int i3) {
        if (View.MeasureSpec.getMode(i2) != 1073741824) {
            throw new IllegalStateException(getClass().getSimpleName().concat(" can only be used with android:layout_width=\"match_parent\" (or fill_parent)"));
        }
        if (View.MeasureSpec.getMode(i3) == 0) {
            throw new IllegalStateException(getClass().getSimpleName().concat(" can only be used with android:layout_height=\"wrap_content\""));
        }
        int size = View.MeasureSpec.getSize(i2);
        int size2 = this.f875e;
        if (size2 <= 0) {
            size2 = View.MeasureSpec.getSize(i3);
        }
        int paddingBottom = getPaddingBottom() + getPaddingTop();
        int paddingLeft = (size - getPaddingLeft()) - getPaddingRight();
        int iMin = size2 - paddingBottom;
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(iMin, Integer.MIN_VALUE);
        View view = this.f881k;
        if (view != null) {
            int iE = e(view, paddingLeft, iMakeMeasureSpec);
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.f881k.getLayoutParams();
            paddingLeft = iE - (marginLayoutParams.leftMargin + marginLayoutParams.rightMargin);
        }
        ActionMenuView actionMenuView = this.f873c;
        if (actionMenuView != null && actionMenuView.getParent() == this) {
            paddingLeft = e(this.f873c, paddingLeft, iMakeMeasureSpec);
        }
        LinearLayout linearLayout = this.f883m;
        if (linearLayout != null && this.f882l == null) {
            if (this.r) {
                this.f883m.measure(View.MeasureSpec.makeMeasureSpec(0, 0), iMakeMeasureSpec);
                int measuredWidth = this.f883m.getMeasuredWidth();
                boolean z2 = measuredWidth <= paddingLeft;
                if (z2) {
                    paddingLeft -= measuredWidth;
                }
                this.f883m.setVisibility(z2 ? 0 : 8);
            } else {
                paddingLeft = e(linearLayout, paddingLeft, iMakeMeasureSpec);
            }
        }
        View view2 = this.f882l;
        if (view2 != null) {
            ViewGroup.LayoutParams layoutParams = view2.getLayoutParams();
            int i4 = layoutParams.width;
            int i5 = i4 != -2 ? 1073741824 : Integer.MIN_VALUE;
            if (i4 >= 0) {
                paddingLeft = Math.min(i4, paddingLeft);
            }
            int i6 = layoutParams.height;
            int i7 = i6 == -2 ? Integer.MIN_VALUE : 1073741824;
            if (i6 >= 0) {
                iMin = Math.min(i6, iMin);
            }
            this.f882l.measure(View.MeasureSpec.makeMeasureSpec(paddingLeft, i5), View.MeasureSpec.makeMeasureSpec(iMin, i7));
        }
        if (this.f875e > 0) {
            setMeasuredDimension(size, size2);
            return;
        }
        int childCount = getChildCount();
        int i8 = 0;
        for (int i9 = 0; i9 < childCount; i9++) {
            int measuredHeight = getChildAt(i9).getMeasuredHeight() + paddingBottom;
            if (measuredHeight > i8) {
                i8 = measuredHeight;
            }
        }
        setMeasuredDimension(size, i8);
    }

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.f877g = false;
        }
        if (!this.f877g) {
            boolean zOnTouchEvent = super.onTouchEvent(motionEvent);
            if (actionMasked == 0 && !zOnTouchEvent) {
                this.f877g = true;
            }
        }
        if (actionMasked == 1 || actionMasked == 3) {
            this.f877g = false;
        }
        return true;
    }

    public void setContentHeight(int i2) {
        this.f875e = i2;
    }

    public void setCustomView(View view) {
        LinearLayout linearLayout;
        View view2 = this.f882l;
        if (view2 != null) {
            removeView(view2);
        }
        this.f882l = view;
        if (view != null && (linearLayout = this.f883m) != null) {
            removeView(linearLayout);
            this.f883m = null;
        }
        if (view != null) {
            addView(view);
        }
        requestLayout();
    }

    public void setSubtitle(CharSequence charSequence) {
        this.f880j = charSequence;
        d();
    }

    public void setTitle(CharSequence charSequence) {
        this.f879i = charSequence;
        d();
    }

    public void setTitleOptional(boolean z2) {
        if (z2 != this.r) {
            requestLayout();
        }
        this.r = z2;
    }

    @Override // android.view.ViewGroup
    public final boolean shouldDelayChildPressedState() {
        return false;
    }
}
