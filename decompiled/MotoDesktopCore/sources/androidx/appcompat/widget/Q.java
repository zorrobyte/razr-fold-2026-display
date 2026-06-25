package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import androidx.appcompat.R$styleable;
import b.AbstractC0122a;
import java.lang.reflect.Method;
import java.util.WeakHashMap;

/* JADX INFO: loaded from: classes.dex */
public class Q implements androidx.appcompat.view.menu.E {

    /* JADX INFO: renamed from: A, reason: collision with root package name */
    public static final Method f1044A;

    /* JADX INFO: renamed from: B, reason: collision with root package name */
    public static final Method f1045B;

    /* JADX INFO: renamed from: z, reason: collision with root package name */
    public static final Method f1046z;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final Context f1047a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public ListAdapter f1048b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public I f1049c;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public int f1052f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public int f1053g;

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public boolean f1055i;

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public boolean f1056j;

    /* JADX INFO: renamed from: k, reason: collision with root package name */
    public boolean f1057k;

    /* JADX INFO: renamed from: n, reason: collision with root package name */
    public F.l f1060n;

    /* JADX INFO: renamed from: o, reason: collision with root package name */
    public View f1061o;

    /* JADX INFO: renamed from: p, reason: collision with root package name */
    public AdapterView.OnItemClickListener f1062p;

    /* JADX INFO: renamed from: u, reason: collision with root package name */
    public final Handler f1066u;

    /* JADX INFO: renamed from: w, reason: collision with root package name */
    public Rect f1068w;

    /* JADX INFO: renamed from: x, reason: collision with root package name */
    public boolean f1069x;

    /* JADX INFO: renamed from: y, reason: collision with root package name */
    public final C0083u f1070y;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public final int f1050d = -2;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public int f1051e = -2;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public final int f1054h = 1002;

    /* JADX INFO: renamed from: l, reason: collision with root package name */
    public int f1058l = 0;

    /* JADX INFO: renamed from: m, reason: collision with root package name */
    public final int f1059m = Integer.MAX_VALUE;

    /* JADX INFO: renamed from: q, reason: collision with root package name */
    public final N f1063q = new N(this, 1);
    public final P r = new P(this);

    /* JADX INFO: renamed from: s, reason: collision with root package name */
    public final O f1064s = new O(this);

    /* JADX INFO: renamed from: t, reason: collision with root package name */
    public final N f1065t = new N(this, 0);

    /* JADX INFO: renamed from: v, reason: collision with root package name */
    public final Rect f1067v = new Rect();

    static {
        try {
            f1046z = PopupWindow.class.getDeclaredMethod("setClipToScreenEnabled", Boolean.TYPE);
        } catch (NoSuchMethodException unused) {
            Log.i("ListPopupWindow", "Could not find method setClipToScreenEnabled() on PopupWindow. Oh well.");
        }
        try {
            f1044A = PopupWindow.class.getDeclaredMethod("getMaxAvailableHeight", View.class, Integer.TYPE, Boolean.TYPE);
        } catch (NoSuchMethodException unused2) {
            Log.i("ListPopupWindow", "Could not find method getMaxAvailableHeight(View, int, boolean) on PopupWindow. Oh well.");
        }
        try {
            f1045B = PopupWindow.class.getDeclaredMethod("setEpicenterBounds", Rect.class);
        } catch (NoSuchMethodException unused3) {
            Log.i("ListPopupWindow", "Could not find method setEpicenterBounds(Rect) on PopupWindow. Oh well.");
        }
    }

    public Q(Context context, AttributeSet attributeSet, int i2, int i3) {
        int resourceId;
        this.f1047a = context;
        this.f1066u = new Handler(context.getMainLooper());
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.ListPopupWindow, i2, i3);
        this.f1052f = typedArrayObtainStyledAttributes.getDimensionPixelOffset(R$styleable.ListPopupWindow_android_dropDownHorizontalOffset, 0);
        int dimensionPixelOffset = typedArrayObtainStyledAttributes.getDimensionPixelOffset(R$styleable.ListPopupWindow_android_dropDownVerticalOffset, 0);
        this.f1053g = dimensionPixelOffset;
        if (dimensionPixelOffset != 0) {
            this.f1055i = true;
        }
        typedArrayObtainStyledAttributes.recycle();
        C0083u c0083u = new C0083u(context, attributeSet, i2, i3);
        TypedArray typedArrayObtainStyledAttributes2 = context.obtainStyledAttributes(attributeSet, R$styleable.PopupWindow, i2, i3);
        if (typedArrayObtainStyledAttributes2.hasValue(R$styleable.PopupWindow_overlapAnchor)) {
            c0083u.setOverlapAnchor(typedArrayObtainStyledAttributes2.getBoolean(R$styleable.PopupWindow_overlapAnchor, false));
        }
        int i4 = R$styleable.PopupWindow_android_popupBackground;
        c0083u.setBackgroundDrawable((!typedArrayObtainStyledAttributes2.hasValue(i4) || (resourceId = typedArrayObtainStyledAttributes2.getResourceId(i4, 0)) == 0) ? typedArrayObtainStyledAttributes2.getDrawable(i4) : AbstractC0122a.a(context, resourceId));
        typedArrayObtainStyledAttributes2.recycle();
        this.f1070y = c0083u;
        c0083u.setInputMethodMode(1);
    }

    public I a(Context context, boolean z2) {
        return new I(context, z2);
    }

    public void c(ListAdapter listAdapter) {
        F.l lVar = this.f1060n;
        if (lVar == null) {
            this.f1060n = new F.l(2, this);
        } else {
            ListAdapter listAdapter2 = this.f1048b;
            if (listAdapter2 != null) {
                listAdapter2.unregisterDataSetObserver(lVar);
            }
        }
        this.f1048b = listAdapter;
        if (listAdapter != null) {
            listAdapter.registerDataSetObserver(this.f1060n);
        }
        I i2 = this.f1049c;
        if (i2 != null) {
            i2.setAdapter(this.f1048b);
        }
    }

    @Override // androidx.appcompat.view.menu.E
    public final void dismiss() {
        C0083u c0083u = this.f1070y;
        c0083u.dismiss();
        c0083u.setContentView(null);
        this.f1049c = null;
        this.f1066u.removeCallbacks(this.f1063q);
    }

    public final void e(int i2) {
        Drawable background = this.f1070y.getBackground();
        if (background == null) {
            this.f1051e = i2;
            return;
        }
        Rect rect = this.f1067v;
        background.getPadding(rect);
        this.f1051e = rect.left + rect.right + i2;
    }

    @Override // androidx.appcompat.view.menu.E
    public final I getListView() {
        return this.f1049c;
    }

    @Override // androidx.appcompat.view.menu.E
    public final boolean isShowing() {
        return this.f1070y.isShowing();
    }

    @Override // androidx.appcompat.view.menu.E
    public void show() {
        int i2;
        int iIntValue;
        int paddingBottom;
        I i3;
        I i4 = this.f1049c;
        C0083u c0083u = this.f1070y;
        Context context = this.f1047a;
        if (i4 == null) {
            I iA = a(context, !this.f1069x);
            this.f1049c = iA;
            iA.setAdapter(this.f1048b);
            this.f1049c.setOnItemClickListener(this.f1062p);
            this.f1049c.setFocusable(true);
            this.f1049c.setFocusableInTouchMode(true);
            this.f1049c.setOnItemSelectedListener(new Y.z(1, this));
            this.f1049c.setOnScrollListener(this.f1064s);
            c0083u.setContentView(this.f1049c);
        }
        Drawable background = c0083u.getBackground();
        Rect rect = this.f1067v;
        if (background != null) {
            background.getPadding(rect);
            int i5 = rect.top;
            i2 = rect.bottom + i5;
            if (!this.f1055i) {
                this.f1053g = -i5;
            }
        } else {
            rect.setEmpty();
            i2 = 0;
        }
        boolean z2 = c0083u.getInputMethodMode() == 2;
        View view = this.f1061o;
        int i6 = this.f1053g;
        Method method = f1044A;
        if (method != null) {
            try {
                iIntValue = ((Integer) method.invoke(c0083u, view, Integer.valueOf(i6), Boolean.valueOf(z2))).intValue();
            } catch (Exception unused) {
                Log.i("ListPopupWindow", "Could not call getMaxAvailableHeightMethod(View, int, boolean) on PopupWindow. Using the public version.");
                iIntValue = c0083u.getMaxAvailableHeight(view, i6);
            }
        } else {
            iIntValue = c0083u.getMaxAvailableHeight(view, i6);
        }
        int i7 = this.f1050d;
        if (i7 == -1) {
            paddingBottom = iIntValue + i2;
        } else {
            int i8 = this.f1051e;
            int iA2 = this.f1049c.a(i8 != -2 ? i8 != -1 ? View.MeasureSpec.makeMeasureSpec(i8, 1073741824) : View.MeasureSpec.makeMeasureSpec(context.getResources().getDisplayMetrics().widthPixels - (rect.left + rect.right), 1073741824) : View.MeasureSpec.makeMeasureSpec(context.getResources().getDisplayMetrics().widthPixels - (rect.left + rect.right), Integer.MIN_VALUE), iIntValue);
            paddingBottom = iA2 + (iA2 > 0 ? this.f1049c.getPaddingBottom() + this.f1049c.getPaddingTop() + i2 : 0);
        }
        boolean z3 = this.f1070y.getInputMethodMode() == 2;
        c0083u.setWindowLayoutType(this.f1054h);
        if (c0083u.isShowing()) {
            View view2 = this.f1061o;
            WeakHashMap weakHashMap = v.l.f2836a;
            if (view2.isAttachedToWindow()) {
                int width = this.f1051e;
                if (width == -1) {
                    width = -1;
                } else if (width == -2) {
                    width = this.f1061o.getWidth();
                }
                if (i7 == -1) {
                    i7 = z3 ? paddingBottom : -1;
                    if (z3) {
                        c0083u.setWidth(this.f1051e == -1 ? -1 : 0);
                        c0083u.setHeight(0);
                    } else {
                        c0083u.setWidth(this.f1051e == -1 ? -1 : 0);
                        c0083u.setHeight(-1);
                    }
                } else if (i7 == -2) {
                    i7 = paddingBottom;
                }
                c0083u.setOutsideTouchable(true);
                c0083u.update(this.f1061o, this.f1052f, this.f1053g, width < 0 ? -1 : width, i7 < 0 ? -1 : i7);
                return;
            }
            return;
        }
        int width2 = this.f1051e;
        if (width2 == -1) {
            width2 = -1;
        } else if (width2 == -2) {
            width2 = this.f1061o.getWidth();
        }
        if (i7 == -1) {
            i7 = -1;
        } else if (i7 == -2) {
            i7 = paddingBottom;
        }
        c0083u.setWidth(width2);
        c0083u.setHeight(i7);
        Method method2 = f1046z;
        if (method2 != null) {
            try {
                method2.invoke(c0083u, Boolean.TRUE);
            } catch (Exception unused2) {
                Log.i("ListPopupWindow", "Could not call setClipToScreenEnabled() on PopupWindow. Oh well.");
            }
        }
        c0083u.setOutsideTouchable(true);
        c0083u.setTouchInterceptor(this.r);
        if (this.f1057k) {
            c0083u.setOverlapAnchor(this.f1056j);
        }
        Method method3 = f1045B;
        if (method3 != null) {
            try {
                method3.invoke(c0083u, this.f1068w);
            } catch (Exception e2) {
                Log.e("ListPopupWindow", "Could not invoke setEpicenterBounds on PopupWindow", e2);
            }
        }
        c0083u.showAsDropDown(this.f1061o, this.f1052f, this.f1053g, this.f1058l);
        this.f1049c.setSelection(-1);
        if ((!this.f1069x || this.f1049c.isInTouchMode()) && (i3 = this.f1049c) != null) {
            i3.setListSelectionHidden(true);
            i3.requestLayout();
        }
        if (this.f1069x) {
            return;
        }
        this.f1066u.post(this.f1065t);
    }
}
