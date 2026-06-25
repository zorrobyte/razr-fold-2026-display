package k;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.coordinatorlayout.R$styleable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: renamed from: k.e, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0146e extends ViewGroup.MarginLayoutParams {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public AbstractC0143b f2746a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public boolean f2747b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final int f2748c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public int f2749d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public final int f2750e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public final int f2751f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public final int f2752g;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public int f2753h;

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public int f2754i;

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public int f2755j;

    /* JADX INFO: renamed from: k, reason: collision with root package name */
    public View f2756k;

    /* JADX INFO: renamed from: l, reason: collision with root package name */
    public View f2757l;

    /* JADX INFO: renamed from: m, reason: collision with root package name */
    public boolean f2758m;

    /* JADX INFO: renamed from: n, reason: collision with root package name */
    public boolean f2759n;

    /* JADX INFO: renamed from: o, reason: collision with root package name */
    public boolean f2760o;

    /* JADX INFO: renamed from: p, reason: collision with root package name */
    public boolean f2761p;

    /* JADX INFO: renamed from: q, reason: collision with root package name */
    public final Rect f2762q;

    public C0146e() {
        super(-2, -2);
        this.f2747b = false;
        this.f2748c = 0;
        this.f2749d = 0;
        this.f2750e = -1;
        this.f2751f = -1;
        this.f2752g = 0;
        this.f2753h = 0;
        this.f2762q = new Rect();
    }

    public C0146e(Context context, AttributeSet attributeSet) {
        AbstractC0143b abstractC0143b;
        super(context, attributeSet);
        this.f2747b = false;
        this.f2748c = 0;
        this.f2749d = 0;
        this.f2750e = -1;
        this.f2751f = -1;
        this.f2752g = 0;
        this.f2753h = 0;
        this.f2762q = new Rect();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.CoordinatorLayout_Layout);
        this.f2748c = typedArrayObtainStyledAttributes.getInteger(R$styleable.CoordinatorLayout_Layout_android_layout_gravity, 0);
        this.f2751f = typedArrayObtainStyledAttributes.getResourceId(R$styleable.CoordinatorLayout_Layout_layout_anchor, -1);
        this.f2749d = typedArrayObtainStyledAttributes.getInteger(R$styleable.CoordinatorLayout_Layout_layout_anchorGravity, 0);
        this.f2750e = typedArrayObtainStyledAttributes.getInteger(R$styleable.CoordinatorLayout_Layout_layout_keyline, -1);
        this.f2752g = typedArrayObtainStyledAttributes.getInt(R$styleable.CoordinatorLayout_Layout_layout_insetEdge, 0);
        this.f2753h = typedArrayObtainStyledAttributes.getInt(R$styleable.CoordinatorLayout_Layout_layout_dodgeInsetEdges, 0);
        boolean zHasValue = typedArrayObtainStyledAttributes.hasValue(R$styleable.CoordinatorLayout_Layout_layout_behavior);
        this.f2747b = zHasValue;
        if (zHasValue) {
            String string = typedArrayObtainStyledAttributes.getString(R$styleable.CoordinatorLayout_Layout_layout_behavior);
            String str = CoordinatorLayout.f1368s;
            if (TextUtils.isEmpty(string)) {
                abstractC0143b = null;
            } else {
                if (string.startsWith(".")) {
                    string = context.getPackageName() + string;
                } else if (string.indexOf(46) < 0) {
                    String str2 = CoordinatorLayout.f1368s;
                    if (!TextUtils.isEmpty(str2)) {
                        string = str2 + '.' + string;
                    }
                }
                try {
                    ThreadLocal threadLocal = CoordinatorLayout.f1370u;
                    Map map = (Map) threadLocal.get();
                    if (map == null) {
                        map = new HashMap();
                        threadLocal.set(map);
                    }
                    Constructor<?> constructor = (Constructor) map.get(string);
                    if (constructor == null) {
                        constructor = context.getClassLoader().loadClass(string).getConstructor(CoordinatorLayout.f1369t);
                        constructor.setAccessible(true);
                        map.put(string, constructor);
                    }
                    abstractC0143b = (AbstractC0143b) constructor.newInstance(context, attributeSet);
                } catch (Exception e2) {
                    throw new RuntimeException("Could not inflate Behavior subclass " + string, e2);
                }
            }
            this.f2746a = abstractC0143b;
        }
        typedArrayObtainStyledAttributes.recycle();
        AbstractC0143b abstractC0143b2 = this.f2746a;
        if (abstractC0143b2 != null) {
            abstractC0143b2.c(this);
        }
    }

    public C0146e(ViewGroup.LayoutParams layoutParams) {
        super(layoutParams);
        this.f2747b = false;
        this.f2748c = 0;
        this.f2749d = 0;
        this.f2750e = -1;
        this.f2751f = -1;
        this.f2752g = 0;
        this.f2753h = 0;
        this.f2762q = new Rect();
    }

    public C0146e(ViewGroup.MarginLayoutParams marginLayoutParams) {
        super(marginLayoutParams);
        this.f2747b = false;
        this.f2748c = 0;
        this.f2749d = 0;
        this.f2750e = -1;
        this.f2751f = -1;
        this.f2752g = 0;
        this.f2753h = 0;
        this.f2762q = new Rect();
    }

    public C0146e(C0146e c0146e) {
        super((ViewGroup.MarginLayoutParams) c0146e);
        this.f2747b = false;
        this.f2748c = 0;
        this.f2749d = 0;
        this.f2750e = -1;
        this.f2751f = -1;
        this.f2752g = 0;
        this.f2753h = 0;
        this.f2762q = new Rect();
    }

    public final boolean a(int i2) {
        if (i2 == 0) {
            return this.f2759n;
        }
        if (i2 != 1) {
            return false;
        }
        return this.f2760o;
    }
}
