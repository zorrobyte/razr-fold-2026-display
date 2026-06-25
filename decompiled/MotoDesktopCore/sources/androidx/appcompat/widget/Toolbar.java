package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.R$attr;
import androidx.appcompat.R$styleable;
import androidx.customview.view.AbsSavedState;
import b.AbstractC0122a;
import java.util.ArrayList;
import java.util.WeakHashMap;

/* JADX INFO: loaded from: classes.dex */
public class Toolbar extends ViewGroup {

    /* JADX INFO: renamed from: A, reason: collision with root package name */
    public int f1139A;

    /* JADX INFO: renamed from: B, reason: collision with root package name */
    public boolean f1140B;

    /* JADX INFO: renamed from: C, reason: collision with root package name */
    public boolean f1141C;

    /* JADX INFO: renamed from: D, reason: collision with root package name */
    public final ArrayList f1142D;

    /* JADX INFO: renamed from: E, reason: collision with root package name */
    public final ArrayList f1143E;

    /* JADX INFO: renamed from: F, reason: collision with root package name */
    public final int[] f1144F;

    /* JADX INFO: renamed from: G, reason: collision with root package name */
    public r0 f1145G;

    /* JADX INFO: renamed from: H, reason: collision with root package name */
    public final e0.k f1146H;

    /* JADX INFO: renamed from: I, reason: collision with root package name */
    public v0 f1147I;

    /* JADX INFO: renamed from: J, reason: collision with root package name */
    public C0074k f1148J;

    /* JADX INFO: renamed from: K, reason: collision with root package name */
    public p0 f1149K;

    /* JADX INFO: renamed from: L, reason: collision with root package name */
    public androidx.appcompat.view.menu.z f1150L;

    /* JADX INFO: renamed from: M, reason: collision with root package name */
    public androidx.appcompat.view.menu.m f1151M;

    /* JADX INFO: renamed from: N, reason: collision with root package name */
    public boolean f1152N;

    /* JADX INFO: renamed from: O, reason: collision with root package name */
    public final F.e f1153O;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public ActionMenuView f1154a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public AppCompatTextView f1155b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public AppCompatTextView f1156c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public AppCompatImageButton f1157d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public AppCompatImageView f1158e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public final Drawable f1159f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public final CharSequence f1160g;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public AppCompatImageButton f1161h;

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public View f1162i;

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public Context f1163j;

    /* JADX INFO: renamed from: k, reason: collision with root package name */
    public int f1164k;

    /* JADX INFO: renamed from: l, reason: collision with root package name */
    public int f1165l;

    /* JADX INFO: renamed from: m, reason: collision with root package name */
    public int f1166m;

    /* JADX INFO: renamed from: n, reason: collision with root package name */
    public final int f1167n;

    /* JADX INFO: renamed from: o, reason: collision with root package name */
    public final int f1168o;

    /* JADX INFO: renamed from: p, reason: collision with root package name */
    public int f1169p;

    /* JADX INFO: renamed from: q, reason: collision with root package name */
    public int f1170q;
    public int r;

    /* JADX INFO: renamed from: s, reason: collision with root package name */
    public int f1171s;

    /* JADX INFO: renamed from: t, reason: collision with root package name */
    public U f1172t;

    /* JADX INFO: renamed from: u, reason: collision with root package name */
    public int f1173u;

    /* JADX INFO: renamed from: v, reason: collision with root package name */
    public int f1174v;

    /* JADX INFO: renamed from: w, reason: collision with root package name */
    public final int f1175w;

    /* JADX INFO: renamed from: x, reason: collision with root package name */
    public CharSequence f1176x;

    /* JADX INFO: renamed from: y, reason: collision with root package name */
    public CharSequence f1177y;

    /* JADX INFO: renamed from: z, reason: collision with root package name */
    public int f1178z;

    public static class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new s0();

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        public int f1179c;

        /* JADX INFO: renamed from: d, reason: collision with root package name */
        public boolean f1180d;

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.f1179c = parcel.readInt();
            this.f1180d = parcel.readInt() != 0;
        }

        @Override // androidx.customview.view.AbsSavedState, android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i2) {
            super.writeToParcel(parcel, i2);
            parcel.writeInt(this.f1179c);
            parcel.writeInt(this.f1180d ? 1 : 0);
        }
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public Toolbar(Context context, AttributeSet attributeSet) {
        int i2 = R$attr.toolbarStyle;
        super(context, attributeSet, i2);
        this.f1175w = 8388627;
        this.f1142D = new ArrayList();
        this.f1143E = new ArrayList();
        this.f1144F = new int[2];
        this.f1146H = new e0.k(this);
        this.f1153O = new F.e(6, this);
        f0.b bVarM = f0.b.m(getContext(), attributeSet, R$styleable.Toolbar, i2, 0);
        int i3 = R$styleable.Toolbar_titleTextAppearance;
        TypedArray typedArray = (TypedArray) bVarM.f2538c;
        this.f1165l = typedArray.getResourceId(i3, 0);
        this.f1166m = typedArray.getResourceId(R$styleable.Toolbar_subtitleTextAppearance, 0);
        this.f1175w = typedArray.getInteger(R$styleable.Toolbar_android_gravity, 8388627);
        this.f1167n = typedArray.getInteger(R$styleable.Toolbar_buttonGravity, 48);
        int dimensionPixelOffset = typedArray.getDimensionPixelOffset(R$styleable.Toolbar_titleMargin, 0);
        dimensionPixelOffset = typedArray.hasValue(R$styleable.Toolbar_titleMargins) ? typedArray.getDimensionPixelOffset(R$styleable.Toolbar_titleMargins, dimensionPixelOffset) : dimensionPixelOffset;
        this.f1171s = dimensionPixelOffset;
        this.r = dimensionPixelOffset;
        this.f1170q = dimensionPixelOffset;
        this.f1169p = dimensionPixelOffset;
        int dimensionPixelOffset2 = typedArray.getDimensionPixelOffset(R$styleable.Toolbar_titleMarginStart, -1);
        if (dimensionPixelOffset2 >= 0) {
            this.f1169p = dimensionPixelOffset2;
        }
        int dimensionPixelOffset3 = typedArray.getDimensionPixelOffset(R$styleable.Toolbar_titleMarginEnd, -1);
        if (dimensionPixelOffset3 >= 0) {
            this.f1170q = dimensionPixelOffset3;
        }
        int dimensionPixelOffset4 = typedArray.getDimensionPixelOffset(R$styleable.Toolbar_titleMarginTop, -1);
        if (dimensionPixelOffset4 >= 0) {
            this.r = dimensionPixelOffset4;
        }
        int dimensionPixelOffset5 = typedArray.getDimensionPixelOffset(R$styleable.Toolbar_titleMarginBottom, -1);
        if (dimensionPixelOffset5 >= 0) {
            this.f1171s = dimensionPixelOffset5;
        }
        this.f1168o = typedArray.getDimensionPixelSize(R$styleable.Toolbar_maxButtonHeight, -1);
        int dimensionPixelOffset6 = typedArray.getDimensionPixelOffset(R$styleable.Toolbar_contentInsetStart, Integer.MIN_VALUE);
        int dimensionPixelOffset7 = typedArray.getDimensionPixelOffset(R$styleable.Toolbar_contentInsetEnd, Integer.MIN_VALUE);
        int dimensionPixelSize = typedArray.getDimensionPixelSize(R$styleable.Toolbar_contentInsetLeft, 0);
        int dimensionPixelSize2 = typedArray.getDimensionPixelSize(R$styleable.Toolbar_contentInsetRight, 0);
        c();
        U u2 = this.f1172t;
        u2.f1188h = false;
        if (dimensionPixelSize != Integer.MIN_VALUE) {
            u2.f1185e = dimensionPixelSize;
            u2.f1181a = dimensionPixelSize;
        }
        if (dimensionPixelSize2 != Integer.MIN_VALUE) {
            u2.f1186f = dimensionPixelSize2;
            u2.f1182b = dimensionPixelSize2;
        }
        if (dimensionPixelOffset6 != Integer.MIN_VALUE || dimensionPixelOffset7 != Integer.MIN_VALUE) {
            u2.a(dimensionPixelOffset6, dimensionPixelOffset7);
        }
        this.f1173u = typedArray.getDimensionPixelOffset(R$styleable.Toolbar_contentInsetStartWithNavigation, Integer.MIN_VALUE);
        this.f1174v = typedArray.getDimensionPixelOffset(R$styleable.Toolbar_contentInsetEndWithActions, Integer.MIN_VALUE);
        this.f1159f = bVarM.f(R$styleable.Toolbar_collapseIcon);
        this.f1160g = typedArray.getText(R$styleable.Toolbar_collapseContentDescription);
        CharSequence text = typedArray.getText(R$styleable.Toolbar_title);
        if (!TextUtils.isEmpty(text)) {
            setTitle(text);
        }
        CharSequence text2 = typedArray.getText(R$styleable.Toolbar_subtitle);
        if (!TextUtils.isEmpty(text2)) {
            setSubtitle(text2);
        }
        this.f1163j = getContext();
        setPopupTheme(typedArray.getResourceId(R$styleable.Toolbar_popupTheme, 0));
        Drawable drawableF = bVarM.f(R$styleable.Toolbar_navigationIcon);
        if (drawableF != null) {
            setNavigationIcon(drawableF);
        }
        CharSequence text3 = typedArray.getText(R$styleable.Toolbar_navigationContentDescription);
        if (!TextUtils.isEmpty(text3)) {
            setNavigationContentDescription(text3);
        }
        Drawable drawableF2 = bVarM.f(R$styleable.Toolbar_logo);
        if (drawableF2 != null) {
            setLogo(drawableF2);
        }
        CharSequence text4 = typedArray.getText(R$styleable.Toolbar_logoDescription);
        if (!TextUtils.isEmpty(text4)) {
            setLogoDescription(text4);
        }
        if (typedArray.hasValue(R$styleable.Toolbar_titleTextColor)) {
            setTitleTextColor(typedArray.getColor(R$styleable.Toolbar_titleTextColor, -1));
        }
        if (typedArray.hasValue(R$styleable.Toolbar_subtitleTextColor)) {
            setSubtitleTextColor(typedArray.getColor(R$styleable.Toolbar_subtitleTextColor, -1));
        }
        bVarM.n();
    }

    public static q0 g() {
        q0 q0Var = new q0(-2, -2);
        q0Var.f1290b = 0;
        q0Var.f1289a = 8388627;
        return q0Var;
    }

    private MenuInflater getMenuInflater() {
        return new d.i(getContext());
    }

    public static q0 h(ViewGroup.LayoutParams layoutParams) {
        boolean z2 = layoutParams instanceof q0;
        if (z2) {
            q0 q0Var = (q0) layoutParams;
            q0 q0Var2 = new q0(q0Var);
            q0Var2.f1290b = 0;
            q0Var2.f1290b = q0Var.f1290b;
            return q0Var2;
        }
        if (z2) {
            q0 q0Var3 = new q0((q0) layoutParams);
            q0Var3.f1290b = 0;
            return q0Var3;
        }
        if (!(layoutParams instanceof ViewGroup.MarginLayoutParams)) {
            q0 q0Var4 = new q0(layoutParams);
            q0Var4.f1290b = 0;
            return q0Var4;
        }
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
        q0 q0Var5 = new q0(marginLayoutParams);
        q0Var5.f1290b = 0;
        ((ViewGroup.MarginLayoutParams) q0Var5).leftMargin = marginLayoutParams.leftMargin;
        ((ViewGroup.MarginLayoutParams) q0Var5).topMargin = marginLayoutParams.topMargin;
        ((ViewGroup.MarginLayoutParams) q0Var5).rightMargin = marginLayoutParams.rightMargin;
        ((ViewGroup.MarginLayoutParams) q0Var5).bottomMargin = marginLayoutParams.bottomMargin;
        return q0Var5;
    }

    public static int k(View view) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        return marginLayoutParams.getMarginEnd() + marginLayoutParams.getMarginStart();
    }

    public static int l(View view) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        return marginLayoutParams.topMargin + marginLayoutParams.bottomMargin;
    }

    public final void a(ArrayList arrayList, int i2) {
        WeakHashMap weakHashMap = v.l.f2836a;
        boolean z2 = getLayoutDirection() == 1;
        int childCount = getChildCount();
        int absoluteGravity = Gravity.getAbsoluteGravity(i2, getLayoutDirection());
        arrayList.clear();
        if (!z2) {
            for (int i3 = 0; i3 < childCount; i3++) {
                View childAt = getChildAt(i3);
                q0 q0Var = (q0) childAt.getLayoutParams();
                if (q0Var.f1290b == 0 && s(childAt) && i(q0Var.f1289a) == absoluteGravity) {
                    arrayList.add(childAt);
                }
            }
            return;
        }
        for (int i4 = childCount - 1; i4 >= 0; i4--) {
            View childAt2 = getChildAt(i4);
            q0 q0Var2 = (q0) childAt2.getLayoutParams();
            if (q0Var2.f1290b == 0 && s(childAt2) && i(q0Var2.f1289a) == absoluteGravity) {
                arrayList.add(childAt2);
            }
        }
    }

    public final void b(View view, boolean z2) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        q0 q0VarG = layoutParams == null ? g() : !checkLayoutParams(layoutParams) ? h(layoutParams) : (q0) layoutParams;
        q0VarG.f1290b = 1;
        if (!z2 || this.f1162i == null) {
            addView(view, q0VarG);
        } else {
            view.setLayoutParams(q0VarG);
            this.f1143E.add(view);
        }
    }

    public final void c() {
        if (this.f1172t == null) {
            U u2 = new U();
            u2.f1181a = 0;
            u2.f1182b = 0;
            u2.f1183c = Integer.MIN_VALUE;
            u2.f1184d = Integer.MIN_VALUE;
            u2.f1185e = 0;
            u2.f1186f = 0;
            u2.f1187g = false;
            u2.f1188h = false;
            this.f1172t = u2;
        }
    }

    @Override // android.view.ViewGroup
    public final boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return super.checkLayoutParams(layoutParams) && (layoutParams instanceof q0);
    }

    public final void d() {
        e();
        ActionMenuView actionMenuView = this.f1154a;
        if (actionMenuView.f918p == null) {
            androidx.appcompat.view.menu.o oVar = (androidx.appcompat.view.menu.o) actionMenuView.getMenu();
            if (this.f1149K == null) {
                this.f1149K = new p0(this);
            }
            this.f1154a.setExpandedActionViewsExclusive(true);
            oVar.b(this.f1149K, this.f1163j);
        }
    }

    public final void e() {
        if (this.f1154a == null) {
            ActionMenuView actionMenuView = new ActionMenuView(getContext(), null);
            this.f1154a = actionMenuView;
            actionMenuView.setPopupTheme(this.f1164k);
            this.f1154a.setOnMenuItemClickListener(this.f1146H);
            ActionMenuView actionMenuView2 = this.f1154a;
            androidx.appcompat.view.menu.z zVar = this.f1150L;
            androidx.appcompat.view.menu.m mVar = this.f1151M;
            actionMenuView2.f922u = zVar;
            actionMenuView2.f923v = mVar;
            q0 q0VarG = g();
            q0VarG.f1289a = (this.f1167n & 112) | 8388613;
            this.f1154a.setLayoutParams(q0VarG);
            b(this.f1154a, false);
        }
    }

    public final void f() {
        if (this.f1157d == null) {
            this.f1157d = new AppCompatImageButton(getContext(), null, R$attr.toolbarNavigationButtonStyle);
            q0 q0VarG = g();
            q0VarG.f1289a = (this.f1167n & 112) | 8388611;
            this.f1157d.setLayoutParams(q0VarG);
        }
    }

    @Override // android.view.ViewGroup
    public final /* bridge */ /* synthetic */ ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return g();
    }

    @Override // android.view.ViewGroup
    public final ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        Context context = getContext();
        q0 q0Var = new q0(context, attributeSet);
        q0Var.f1289a = 0;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.ActionBarLayout);
        q0Var.f1289a = typedArrayObtainStyledAttributes.getInt(R$styleable.ActionBarLayout_android_layout_gravity, 0);
        typedArrayObtainStyledAttributes.recycle();
        q0Var.f1290b = 0;
        return q0Var;
    }

    @Override // android.view.ViewGroup
    public final /* bridge */ /* synthetic */ ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return h(layoutParams);
    }

    public int getContentInsetEnd() {
        U u2 = this.f1172t;
        if (u2 != null) {
            return u2.f1187g ? u2.f1181a : u2.f1182b;
        }
        return 0;
    }

    public int getContentInsetEndWithActions() {
        int i2 = this.f1174v;
        return i2 != Integer.MIN_VALUE ? i2 : getContentInsetEnd();
    }

    public int getContentInsetLeft() {
        U u2 = this.f1172t;
        if (u2 != null) {
            return u2.f1181a;
        }
        return 0;
    }

    public int getContentInsetRight() {
        U u2 = this.f1172t;
        if (u2 != null) {
            return u2.f1182b;
        }
        return 0;
    }

    public int getContentInsetStart() {
        U u2 = this.f1172t;
        if (u2 != null) {
            return u2.f1187g ? u2.f1182b : u2.f1181a;
        }
        return 0;
    }

    public int getContentInsetStartWithNavigation() {
        int i2 = this.f1173u;
        return i2 != Integer.MIN_VALUE ? i2 : getContentInsetStart();
    }

    public int getCurrentContentInsetEnd() {
        androidx.appcompat.view.menu.o oVar;
        ActionMenuView actionMenuView = this.f1154a;
        return (actionMenuView == null || (oVar = actionMenuView.f918p) == null || !oVar.hasVisibleItems()) ? getContentInsetEnd() : Math.max(getContentInsetEnd(), Math.max(this.f1174v, 0));
    }

    public int getCurrentContentInsetLeft() {
        WeakHashMap weakHashMap = v.l.f2836a;
        return getLayoutDirection() == 1 ? getCurrentContentInsetEnd() : getCurrentContentInsetStart();
    }

    public int getCurrentContentInsetRight() {
        WeakHashMap weakHashMap = v.l.f2836a;
        return getLayoutDirection() == 1 ? getCurrentContentInsetStart() : getCurrentContentInsetEnd();
    }

    public int getCurrentContentInsetStart() {
        return getNavigationIcon() != null ? Math.max(getContentInsetStart(), Math.max(this.f1173u, 0)) : getContentInsetStart();
    }

    public Drawable getLogo() {
        AppCompatImageView appCompatImageView = this.f1158e;
        if (appCompatImageView != null) {
            return appCompatImageView.getDrawable();
        }
        return null;
    }

    public CharSequence getLogoDescription() {
        AppCompatImageView appCompatImageView = this.f1158e;
        if (appCompatImageView != null) {
            return appCompatImageView.getContentDescription();
        }
        return null;
    }

    public Menu getMenu() {
        d();
        return this.f1154a.getMenu();
    }

    public CharSequence getNavigationContentDescription() {
        AppCompatImageButton appCompatImageButton = this.f1157d;
        if (appCompatImageButton != null) {
            return appCompatImageButton.getContentDescription();
        }
        return null;
    }

    public Drawable getNavigationIcon() {
        AppCompatImageButton appCompatImageButton = this.f1157d;
        if (appCompatImageButton != null) {
            return appCompatImageButton.getDrawable();
        }
        return null;
    }

    public C0074k getOuterActionMenuPresenter() {
        return this.f1148J;
    }

    public Drawable getOverflowIcon() {
        d();
        return this.f1154a.getOverflowIcon();
    }

    public Context getPopupContext() {
        return this.f1163j;
    }

    public int getPopupTheme() {
        return this.f1164k;
    }

    public CharSequence getSubtitle() {
        return this.f1177y;
    }

    public CharSequence getTitle() {
        return this.f1176x;
    }

    public int getTitleMarginBottom() {
        return this.f1171s;
    }

    public int getTitleMarginEnd() {
        return this.f1170q;
    }

    public int getTitleMarginStart() {
        return this.f1169p;
    }

    public int getTitleMarginTop() {
        return this.r;
    }

    public F getWrapper() {
        if (this.f1147I == null) {
            this.f1147I = new v0(this, true);
        }
        return this.f1147I;
    }

    public final int i(int i2) {
        WeakHashMap weakHashMap = v.l.f2836a;
        int layoutDirection = getLayoutDirection();
        int absoluteGravity = Gravity.getAbsoluteGravity(i2, layoutDirection) & 7;
        return (absoluteGravity == 1 || absoluteGravity == 3 || absoluteGravity == 5) ? absoluteGravity : layoutDirection == 1 ? 5 : 3;
    }

    public final int j(View view, int i2) {
        q0 q0Var = (q0) view.getLayoutParams();
        int measuredHeight = view.getMeasuredHeight();
        int i3 = i2 > 0 ? (measuredHeight - i2) / 2 : 0;
        int i4 = q0Var.f1289a & 112;
        if (i4 != 16 && i4 != 48 && i4 != 80) {
            i4 = this.f1175w & 112;
        }
        if (i4 == 48) {
            return getPaddingTop() - i3;
        }
        if (i4 == 80) {
            return (((getHeight() - getPaddingBottom()) - measuredHeight) - ((ViewGroup.MarginLayoutParams) q0Var).bottomMargin) - i3;
        }
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int height = getHeight();
        int iMax = (((height - paddingTop) - paddingBottom) - measuredHeight) / 2;
        int i5 = ((ViewGroup.MarginLayoutParams) q0Var).topMargin;
        if (iMax < i5) {
            iMax = i5;
        } else {
            int i6 = (((height - paddingBottom) - measuredHeight) - iMax) - paddingTop;
            int i7 = ((ViewGroup.MarginLayoutParams) q0Var).bottomMargin;
            if (i6 < i7) {
                iMax = Math.max(0, iMax - (i7 - i6));
            }
        }
        return paddingTop + iMax;
    }

    public final boolean m(View view) {
        return view.getParent() == this || this.f1143E.contains(view);
    }

    public final boolean n() {
        C0074k c0074k;
        ActionMenuView actionMenuView = this.f1154a;
        return (actionMenuView == null || (c0074k = actionMenuView.f921t) == null || !c0074k.k()) ? false : true;
    }

    public final int o(View view, int i2, int i3, int[] iArr) {
        q0 q0Var = (q0) view.getLayoutParams();
        int i4 = ((ViewGroup.MarginLayoutParams) q0Var).leftMargin - iArr[0];
        int iMax = Math.max(0, i4) + i2;
        iArr[0] = Math.max(0, -i4);
        int iJ = j(view, i3);
        int measuredWidth = view.getMeasuredWidth();
        view.layout(iMax, iJ, iMax + measuredWidth, view.getMeasuredHeight() + iJ);
        return measuredWidth + ((ViewGroup.MarginLayoutParams) q0Var).rightMargin + iMax;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks(this.f1153O);
    }

    @Override // android.view.View
    public final boolean onHoverEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 9) {
            this.f1141C = false;
        }
        if (!this.f1141C) {
            boolean zOnHoverEvent = super.onHoverEvent(motionEvent);
            if (actionMasked == 9 && !zOnHoverEvent) {
                this.f1141C = true;
            }
        }
        if (actionMasked == 10 || actionMasked == 3) {
            this.f1141C = false;
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:107:0x029b A[LOOP:0: B:106:0x0299->B:107:0x029b, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:110:0x02b8 A[LOOP:1: B:109:0x02b6->B:110:0x02b8, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:113:0x02d6 A[LOOP:2: B:112:0x02d4->B:113:0x02d6, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:117:0x0317  */
    /* JADX WARN: Removed duplicated region for block: B:122:0x0325 A[LOOP:3: B:121:0x0323->B:122:0x0325, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0062  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0079  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00b6  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00cd  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00ea  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0101  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0106  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x011e  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x012b  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x012d  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0130  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0134  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0137  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x016a  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x01a2  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x01b1  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x0222  */
    @Override // android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void onLayout(boolean r19, int r20, int r21, int r22, int r23) {
        /*
            Method dump skipped, instruction units count: 822
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.Toolbar.onLayout(boolean, int, int, int, int):void");
    }

    @Override // android.view.View
    public final void onMeasure(int i2, int i3) {
        int iK;
        int iMax;
        int iCombineMeasuredStates;
        int iK2;
        int iL;
        int iCombineMeasuredStates2;
        int iMax2;
        boolean zA = y0.a(this);
        int i4 = !zA ? 1 : 0;
        int i5 = 0;
        if (s(this.f1157d)) {
            r(this.f1157d, i2, 0, i3, this.f1168o);
            iK = k(this.f1157d) + this.f1157d.getMeasuredWidth();
            iMax = Math.max(0, l(this.f1157d) + this.f1157d.getMeasuredHeight());
            iCombineMeasuredStates = View.combineMeasuredStates(0, this.f1157d.getMeasuredState());
        } else {
            iK = 0;
            iMax = 0;
            iCombineMeasuredStates = 0;
        }
        if (s(this.f1161h)) {
            r(this.f1161h, i2, 0, i3, this.f1168o);
            iK = k(this.f1161h) + this.f1161h.getMeasuredWidth();
            iMax = Math.max(iMax, l(this.f1161h) + this.f1161h.getMeasuredHeight());
            iCombineMeasuredStates = View.combineMeasuredStates(iCombineMeasuredStates, this.f1161h.getMeasuredState());
        }
        int currentContentInsetStart = getCurrentContentInsetStart();
        int iMax3 = Math.max(currentContentInsetStart, iK);
        int iMax4 = Math.max(0, currentContentInsetStart - iK);
        int[] iArr = this.f1144F;
        iArr[zA ? 1 : 0] = iMax4;
        if (s(this.f1154a)) {
            r(this.f1154a, i2, iMax3, i3, this.f1168o);
            iK2 = k(this.f1154a) + this.f1154a.getMeasuredWidth();
            iMax = Math.max(iMax, l(this.f1154a) + this.f1154a.getMeasuredHeight());
            iCombineMeasuredStates = View.combineMeasuredStates(iCombineMeasuredStates, this.f1154a.getMeasuredState());
        } else {
            iK2 = 0;
        }
        int currentContentInsetEnd = getCurrentContentInsetEnd();
        int iMax5 = iMax3 + Math.max(currentContentInsetEnd, iK2);
        iArr[i4] = Math.max(0, currentContentInsetEnd - iK2);
        if (s(this.f1162i)) {
            iMax5 += q(this.f1162i, i2, iMax5, i3, 0, iArr);
            iMax = Math.max(iMax, l(this.f1162i) + this.f1162i.getMeasuredHeight());
            iCombineMeasuredStates = View.combineMeasuredStates(iCombineMeasuredStates, this.f1162i.getMeasuredState());
        }
        if (s(this.f1158e)) {
            iMax5 += q(this.f1158e, i2, iMax5, i3, 0, iArr);
            iMax = Math.max(iMax, l(this.f1158e) + this.f1158e.getMeasuredHeight());
            iCombineMeasuredStates = View.combineMeasuredStates(iCombineMeasuredStates, this.f1158e.getMeasuredState());
        }
        int childCount = getChildCount();
        for (int i6 = 0; i6 < childCount; i6++) {
            View childAt = getChildAt(i6);
            if (((q0) childAt.getLayoutParams()).f1290b == 0 && s(childAt)) {
                iMax5 += q(childAt, i2, iMax5, i3, 0, iArr);
                iMax = Math.max(iMax, l(childAt) + childAt.getMeasuredHeight());
                iCombineMeasuredStates = View.combineMeasuredStates(iCombineMeasuredStates, childAt.getMeasuredState());
            }
        }
        int i7 = this.r + this.f1171s;
        int i8 = this.f1169p + this.f1170q;
        if (s(this.f1155b)) {
            q(this.f1155b, i2, iMax5 + i8, i3, i7, iArr);
            int iK3 = k(this.f1155b) + this.f1155b.getMeasuredWidth();
            iL = l(this.f1155b) + this.f1155b.getMeasuredHeight();
            iCombineMeasuredStates2 = View.combineMeasuredStates(iCombineMeasuredStates, this.f1155b.getMeasuredState());
            iMax2 = iK3;
        } else {
            iL = 0;
            iCombineMeasuredStates2 = iCombineMeasuredStates;
            iMax2 = 0;
        }
        if (s(this.f1156c)) {
            iMax2 = Math.max(iMax2, q(this.f1156c, i2, iMax5 + i8, i3, iL + i7, iArr));
            iL += l(this.f1156c) + this.f1156c.getMeasuredHeight();
            iCombineMeasuredStates2 = View.combineMeasuredStates(iCombineMeasuredStates2, this.f1156c.getMeasuredState());
        }
        int iMax6 = Math.max(iMax, iL);
        int paddingRight = getPaddingRight() + getPaddingLeft();
        int paddingBottom = getPaddingBottom() + getPaddingTop() + iMax6;
        int iResolveSizeAndState = View.resolveSizeAndState(Math.max(paddingRight + iMax5 + iMax2, getSuggestedMinimumWidth()), i2, (-16777216) & iCombineMeasuredStates2);
        int iResolveSizeAndState2 = View.resolveSizeAndState(Math.max(paddingBottom, getSuggestedMinimumHeight()), i3, iCombineMeasuredStates2 << 16);
        if (!this.f1152N) {
            i5 = iResolveSizeAndState2;
            break;
        }
        int childCount2 = getChildCount();
        for (int i9 = 0; i9 < childCount2; i9++) {
            View childAt2 = getChildAt(i9);
            if (s(childAt2) && childAt2.getMeasuredWidth() > 0 && childAt2.getMeasuredHeight() > 0) {
                i5 = iResolveSizeAndState2;
                break;
            }
        }
        setMeasuredDimension(iResolveSizeAndState, i5);
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        MenuItem menuItemFindItem;
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.f1465a);
        ActionMenuView actionMenuView = this.f1154a;
        androidx.appcompat.view.menu.o oVar = actionMenuView != null ? actionMenuView.f918p : null;
        int i2 = savedState.f1179c;
        if (i2 != 0 && this.f1149K != null && oVar != null && (menuItemFindItem = oVar.findItem(i2)) != null) {
            menuItemFindItem.expandActionView();
        }
        if (savedState.f1180d) {
            F.e eVar = this.f1153O;
            removeCallbacks(eVar);
            post(eVar);
        }
    }

    @Override // android.view.View
    public final void onRtlPropertiesChanged(int i2) {
        super.onRtlPropertiesChanged(i2);
        c();
        U u2 = this.f1172t;
        boolean z2 = i2 == 1;
        if (z2 == u2.f1187g) {
            return;
        }
        u2.f1187g = z2;
        if (!u2.f1188h) {
            u2.f1181a = u2.f1185e;
            u2.f1182b = u2.f1186f;
            return;
        }
        if (z2) {
            int i3 = u2.f1184d;
            if (i3 == Integer.MIN_VALUE) {
                i3 = u2.f1185e;
            }
            u2.f1181a = i3;
            int i4 = u2.f1183c;
            if (i4 == Integer.MIN_VALUE) {
                i4 = u2.f1186f;
            }
            u2.f1182b = i4;
            return;
        }
        int i5 = u2.f1183c;
        if (i5 == Integer.MIN_VALUE) {
            i5 = u2.f1185e;
        }
        u2.f1181a = i5;
        int i6 = u2.f1184d;
        if (i6 == Integer.MIN_VALUE) {
            i6 = u2.f1186f;
        }
        u2.f1182b = i6;
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        androidx.appcompat.view.menu.q qVar;
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        p0 p0Var = this.f1149K;
        if (p0Var != null && (qVar = p0Var.f1281b) != null) {
            savedState.f1179c = qVar.f811a;
        }
        savedState.f1180d = n();
        return savedState;
    }

    @Override // android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.f1140B = false;
        }
        if (!this.f1140B) {
            boolean zOnTouchEvent = super.onTouchEvent(motionEvent);
            if (actionMasked == 0 && !zOnTouchEvent) {
                this.f1140B = true;
            }
        }
        if (actionMasked == 1 || actionMasked == 3) {
            this.f1140B = false;
        }
        return true;
    }

    public final int p(View view, int i2, int i3, int[] iArr) {
        q0 q0Var = (q0) view.getLayoutParams();
        int i4 = ((ViewGroup.MarginLayoutParams) q0Var).rightMargin - iArr[1];
        int iMax = i2 - Math.max(0, i4);
        iArr[1] = Math.max(0, -i4);
        int iJ = j(view, i3);
        int measuredWidth = view.getMeasuredWidth();
        view.layout(iMax - measuredWidth, iJ, iMax, view.getMeasuredHeight() + iJ);
        return iMax - (measuredWidth + ((ViewGroup.MarginLayoutParams) q0Var).leftMargin);
    }

    public final int q(View view, int i2, int i3, int i4, int i5, int[] iArr) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        int i6 = marginLayoutParams.leftMargin - iArr[0];
        int i7 = marginLayoutParams.rightMargin - iArr[1];
        int iMax = Math.max(0, i7) + Math.max(0, i6);
        iArr[0] = Math.max(0, -i6);
        iArr[1] = Math.max(0, -i7);
        view.measure(ViewGroup.getChildMeasureSpec(i2, getPaddingRight() + getPaddingLeft() + iMax + i3, marginLayoutParams.width), ViewGroup.getChildMeasureSpec(i4, getPaddingBottom() + getPaddingTop() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin + i5, marginLayoutParams.height));
        return view.getMeasuredWidth() + iMax;
    }

    public final void r(View view, int i2, int i3, int i4, int i5) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        int childMeasureSpec = ViewGroup.getChildMeasureSpec(i2, getPaddingRight() + getPaddingLeft() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin + i3, marginLayoutParams.width);
        int childMeasureSpec2 = ViewGroup.getChildMeasureSpec(i4, getPaddingBottom() + getPaddingTop() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin, marginLayoutParams.height);
        int mode = View.MeasureSpec.getMode(childMeasureSpec2);
        if (mode != 1073741824 && i5 >= 0) {
            if (mode != 0) {
                i5 = Math.min(View.MeasureSpec.getSize(childMeasureSpec2), i5);
            }
            childMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(i5, 1073741824);
        }
        view.measure(childMeasureSpec, childMeasureSpec2);
    }

    public final boolean s(View view) {
        return (view == null || view.getParent() != this || view.getVisibility() == 8) ? false : true;
    }

    public void setCollapsible(boolean z2) {
        this.f1152N = z2;
        requestLayout();
    }

    public void setContentInsetEndWithActions(int i2) {
        if (i2 < 0) {
            i2 = Integer.MIN_VALUE;
        }
        if (i2 != this.f1174v) {
            this.f1174v = i2;
            if (getNavigationIcon() != null) {
                requestLayout();
            }
        }
    }

    public void setContentInsetStartWithNavigation(int i2) {
        if (i2 < 0) {
            i2 = Integer.MIN_VALUE;
        }
        if (i2 != this.f1173u) {
            this.f1173u = i2;
            if (getNavigationIcon() != null) {
                requestLayout();
            }
        }
    }

    public void setLogo(int i2) {
        setLogo(AbstractC0122a.a(getContext(), i2));
    }

    public void setLogo(Drawable drawable) {
        if (drawable != null) {
            if (this.f1158e == null) {
                this.f1158e = new AppCompatImageView(getContext());
            }
            if (!m(this.f1158e)) {
                b(this.f1158e, true);
            }
        } else {
            AppCompatImageView appCompatImageView = this.f1158e;
            if (appCompatImageView != null && m(appCompatImageView)) {
                removeView(this.f1158e);
                this.f1143E.remove(this.f1158e);
            }
        }
        AppCompatImageView appCompatImageView2 = this.f1158e;
        if (appCompatImageView2 != null) {
            appCompatImageView2.setImageDrawable(drawable);
        }
    }

    public void setLogoDescription(int i2) {
        setLogoDescription(getContext().getText(i2));
    }

    public void setLogoDescription(CharSequence charSequence) {
        if (!TextUtils.isEmpty(charSequence) && this.f1158e == null) {
            this.f1158e = new AppCompatImageView(getContext());
        }
        AppCompatImageView appCompatImageView = this.f1158e;
        if (appCompatImageView != null) {
            appCompatImageView.setContentDescription(charSequence);
        }
    }

    public void setNavigationContentDescription(int i2) {
        setNavigationContentDescription(i2 != 0 ? getContext().getText(i2) : null);
    }

    public void setNavigationContentDescription(CharSequence charSequence) {
        if (!TextUtils.isEmpty(charSequence)) {
            f();
        }
        AppCompatImageButton appCompatImageButton = this.f1157d;
        if (appCompatImageButton != null) {
            appCompatImageButton.setContentDescription(charSequence);
        }
    }

    public void setNavigationIcon(int i2) {
        setNavigationIcon(AbstractC0122a.a(getContext(), i2));
    }

    public void setNavigationIcon(Drawable drawable) {
        if (drawable != null) {
            f();
            if (!m(this.f1157d)) {
                b(this.f1157d, true);
            }
        } else {
            AppCompatImageButton appCompatImageButton = this.f1157d;
            if (appCompatImageButton != null && m(appCompatImageButton)) {
                removeView(this.f1157d);
                this.f1143E.remove(this.f1157d);
            }
        }
        AppCompatImageButton appCompatImageButton2 = this.f1157d;
        if (appCompatImageButton2 != null) {
            appCompatImageButton2.setImageDrawable(drawable);
        }
    }

    public void setNavigationOnClickListener(View.OnClickListener onClickListener) {
        f();
        this.f1157d.setOnClickListener(onClickListener);
    }

    public void setOnMenuItemClickListener(r0 r0Var) {
        this.f1145G = r0Var;
    }

    public void setOverflowIcon(Drawable drawable) {
        d();
        this.f1154a.setOverflowIcon(drawable);
    }

    public void setPopupTheme(int i2) {
        if (this.f1164k != i2) {
            this.f1164k = i2;
            if (i2 == 0) {
                this.f1163j = getContext();
            } else {
                this.f1163j = new ContextThemeWrapper(getContext(), i2);
            }
        }
    }

    public void setSubtitle(int i2) {
        setSubtitle(getContext().getText(i2));
    }

    public void setSubtitle(CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence)) {
            AppCompatTextView appCompatTextView = this.f1156c;
            if (appCompatTextView != null && m(appCompatTextView)) {
                removeView(this.f1156c);
                this.f1143E.remove(this.f1156c);
            }
        } else {
            if (this.f1156c == null) {
                Context context = getContext();
                AppCompatTextView appCompatTextView2 = new AppCompatTextView(context, null);
                this.f1156c = appCompatTextView2;
                appCompatTextView2.setSingleLine();
                this.f1156c.setEllipsize(TextUtils.TruncateAt.END);
                int i2 = this.f1166m;
                if (i2 != 0) {
                    this.f1156c.setTextAppearance(context, i2);
                }
                int i3 = this.f1139A;
                if (i3 != 0) {
                    this.f1156c.setTextColor(i3);
                }
            }
            if (!m(this.f1156c)) {
                b(this.f1156c, true);
            }
        }
        AppCompatTextView appCompatTextView3 = this.f1156c;
        if (appCompatTextView3 != null) {
            appCompatTextView3.setText(charSequence);
        }
        this.f1177y = charSequence;
    }

    public void setSubtitleTextColor(int i2) {
        this.f1139A = i2;
        AppCompatTextView appCompatTextView = this.f1156c;
        if (appCompatTextView != null) {
            appCompatTextView.setTextColor(i2);
        }
    }

    public void setTitle(int i2) {
        setTitle(getContext().getText(i2));
    }

    public void setTitle(CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence)) {
            AppCompatTextView appCompatTextView = this.f1155b;
            if (appCompatTextView != null && m(appCompatTextView)) {
                removeView(this.f1155b);
                this.f1143E.remove(this.f1155b);
            }
        } else {
            if (this.f1155b == null) {
                Context context = getContext();
                AppCompatTextView appCompatTextView2 = new AppCompatTextView(context, null);
                this.f1155b = appCompatTextView2;
                appCompatTextView2.setSingleLine();
                this.f1155b.setEllipsize(TextUtils.TruncateAt.END);
                int i2 = this.f1165l;
                if (i2 != 0) {
                    this.f1155b.setTextAppearance(context, i2);
                }
                int i3 = this.f1178z;
                if (i3 != 0) {
                    this.f1155b.setTextColor(i3);
                }
            }
            if (!m(this.f1155b)) {
                b(this.f1155b, true);
            }
        }
        AppCompatTextView appCompatTextView3 = this.f1155b;
        if (appCompatTextView3 != null) {
            appCompatTextView3.setText(charSequence);
        }
        this.f1176x = charSequence;
    }

    public void setTitleMarginBottom(int i2) {
        this.f1171s = i2;
        requestLayout();
    }

    public void setTitleMarginEnd(int i2) {
        this.f1170q = i2;
        requestLayout();
    }

    public void setTitleMarginStart(int i2) {
        this.f1169p = i2;
        requestLayout();
    }

    public void setTitleMarginTop(int i2) {
        this.r = i2;
        requestLayout();
    }

    public void setTitleTextColor(int i2) {
        this.f1178z = i2;
        AppCompatTextView appCompatTextView = this.f1155b;
        if (appCompatTextView != null) {
            appCompatTextView.setTextColor(i2);
        }
    }

    public final boolean t() {
        C0074k c0074k;
        ActionMenuView actionMenuView = this.f1154a;
        return (actionMenuView == null || (c0074k = actionMenuView.f921t) == null || !c0074k.l()) ? false : true;
    }
}
