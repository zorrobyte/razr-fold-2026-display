package j;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.R$styleable;
import com.motorola.mobiledesktop.core.uinput.EventType;
import i.C0138a;
import i.C0139b;

/* JADX INFO: renamed from: j.b, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public class C0141b extends ViewGroup.MarginLayoutParams {

    /* JADX INFO: renamed from: A, reason: collision with root package name */
    public final float f2631A;

    /* JADX INFO: renamed from: B, reason: collision with root package name */
    public final String f2632B;

    /* JADX INFO: renamed from: C, reason: collision with root package name */
    public final int f2633C;

    /* JADX INFO: renamed from: D, reason: collision with root package name */
    public final float f2634D;

    /* JADX INFO: renamed from: E, reason: collision with root package name */
    public final float f2635E;

    /* JADX INFO: renamed from: F, reason: collision with root package name */
    public final int f2636F;

    /* JADX INFO: renamed from: G, reason: collision with root package name */
    public final int f2637G;

    /* JADX INFO: renamed from: H, reason: collision with root package name */
    public int f2638H;

    /* JADX INFO: renamed from: I, reason: collision with root package name */
    public int f2639I;

    /* JADX INFO: renamed from: J, reason: collision with root package name */
    public final int f2640J;

    /* JADX INFO: renamed from: K, reason: collision with root package name */
    public final int f2641K;

    /* JADX INFO: renamed from: L, reason: collision with root package name */
    public final int f2642L;

    /* JADX INFO: renamed from: M, reason: collision with root package name */
    public final int f2643M;

    /* JADX INFO: renamed from: N, reason: collision with root package name */
    public final float f2644N;

    /* JADX INFO: renamed from: O, reason: collision with root package name */
    public final float f2645O;

    /* JADX INFO: renamed from: P, reason: collision with root package name */
    public final int f2646P;

    /* JADX INFO: renamed from: Q, reason: collision with root package name */
    public final int f2647Q;

    /* JADX INFO: renamed from: R, reason: collision with root package name */
    public final int f2648R;

    /* JADX INFO: renamed from: S, reason: collision with root package name */
    public boolean f2649S;

    /* JADX INFO: renamed from: T, reason: collision with root package name */
    public boolean f2650T;

    /* JADX INFO: renamed from: U, reason: collision with root package name */
    public boolean f2651U;
    public boolean V;

    /* JADX INFO: renamed from: W, reason: collision with root package name */
    public boolean f2652W;

    /* JADX INFO: renamed from: X, reason: collision with root package name */
    public C0138a f2653X;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public int f2654a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public int f2655b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public float f2656c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public final int f2657d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public final int f2658e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public final int f2659f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public final int f2660g;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public final int f2661h;

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public final int f2662i;

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public final int f2663j;

    /* JADX INFO: renamed from: k, reason: collision with root package name */
    public final int f2664k;

    /* JADX INFO: renamed from: l, reason: collision with root package name */
    public final int f2665l;

    /* JADX INFO: renamed from: m, reason: collision with root package name */
    public final int f2666m;

    /* JADX INFO: renamed from: n, reason: collision with root package name */
    public final int f2667n;

    /* JADX INFO: renamed from: o, reason: collision with root package name */
    public final float f2668o;

    /* JADX INFO: renamed from: p, reason: collision with root package name */
    public final int f2669p;

    /* JADX INFO: renamed from: q, reason: collision with root package name */
    public final int f2670q;
    public final int r;

    /* JADX INFO: renamed from: s, reason: collision with root package name */
    public final int f2671s;

    /* JADX INFO: renamed from: t, reason: collision with root package name */
    public final int f2672t;

    /* JADX INFO: renamed from: u, reason: collision with root package name */
    public final int f2673u;

    /* JADX INFO: renamed from: v, reason: collision with root package name */
    public final int f2674v;

    /* JADX INFO: renamed from: w, reason: collision with root package name */
    public final int f2675w;

    /* JADX INFO: renamed from: x, reason: collision with root package name */
    public final int f2676x;

    /* JADX INFO: renamed from: y, reason: collision with root package name */
    public final int f2677y;

    /* JADX INFO: renamed from: z, reason: collision with root package name */
    public final float f2678z;

    public C0141b() {
        super(-2, -2);
        this.f2654a = -1;
        this.f2655b = -1;
        this.f2656c = -1.0f;
        this.f2657d = -1;
        this.f2658e = -1;
        this.f2659f = -1;
        this.f2660g = -1;
        this.f2661h = -1;
        this.f2662i = -1;
        this.f2663j = -1;
        this.f2664k = -1;
        this.f2665l = -1;
        this.f2666m = -1;
        this.f2667n = 0;
        this.f2668o = 0.0f;
        this.f2669p = -1;
        this.f2670q = -1;
        this.r = -1;
        this.f2671s = -1;
        this.f2672t = -1;
        this.f2673u = -1;
        this.f2674v = -1;
        this.f2675w = -1;
        this.f2676x = -1;
        this.f2677y = -1;
        this.f2678z = 0.5f;
        this.f2631A = 0.5f;
        this.f2632B = null;
        this.f2633C = 1;
        this.f2634D = -1.0f;
        this.f2635E = -1.0f;
        this.f2636F = 0;
        this.f2637G = 0;
        this.f2638H = 0;
        this.f2639I = 0;
        this.f2640J = 0;
        this.f2641K = 0;
        this.f2642L = 0;
        this.f2643M = 0;
        this.f2644N = 1.0f;
        this.f2645O = 1.0f;
        this.f2646P = -1;
        this.f2647Q = -1;
        this.f2648R = -1;
        this.f2649S = false;
        this.f2650T = false;
        this.f2651U = false;
        this.V = false;
        this.f2652W = false;
        this.f2653X = new C0138a();
    }

    public C0141b(Context context, AttributeSet attributeSet) {
        int i2;
        super(context, attributeSet);
        this.f2654a = -1;
        this.f2655b = -1;
        this.f2656c = -1.0f;
        this.f2657d = -1;
        this.f2658e = -1;
        this.f2659f = -1;
        this.f2660g = -1;
        this.f2661h = -1;
        this.f2662i = -1;
        this.f2663j = -1;
        this.f2664k = -1;
        this.f2665l = -1;
        this.f2666m = -1;
        this.f2667n = 0;
        this.f2668o = 0.0f;
        this.f2669p = -1;
        this.f2670q = -1;
        this.r = -1;
        this.f2671s = -1;
        this.f2672t = -1;
        this.f2673u = -1;
        this.f2674v = -1;
        this.f2675w = -1;
        this.f2676x = -1;
        this.f2677y = -1;
        this.f2678z = 0.5f;
        this.f2631A = 0.5f;
        this.f2632B = null;
        this.f2633C = 1;
        this.f2634D = -1.0f;
        this.f2635E = -1.0f;
        this.f2636F = 0;
        this.f2637G = 0;
        this.f2638H = 0;
        this.f2639I = 0;
        this.f2640J = 0;
        this.f2641K = 0;
        this.f2642L = 0;
        this.f2643M = 0;
        this.f2644N = 1.0f;
        this.f2645O = 1.0f;
        this.f2646P = -1;
        this.f2647Q = -1;
        this.f2648R = -1;
        this.f2649S = false;
        this.f2650T = false;
        this.f2651U = false;
        this.V = false;
        this.f2652W = false;
        this.f2653X = new C0138a();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.ConstraintLayout_Layout);
        int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
        for (int i3 = 0; i3 < indexCount; i3++) {
            int index = typedArrayObtainStyledAttributes.getIndex(i3);
            int i4 = AbstractC0140a.f2630a.get(index);
            switch (i4) {
                case 1:
                    this.f2648R = typedArrayObtainStyledAttributes.getInt(index, this.f2648R);
                    break;
                case 2:
                    int resourceId = typedArrayObtainStyledAttributes.getResourceId(index, this.f2666m);
                    this.f2666m = resourceId;
                    if (resourceId == -1) {
                        this.f2666m = typedArrayObtainStyledAttributes.getInt(index, -1);
                    }
                    break;
                case 3:
                    this.f2667n = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.f2667n);
                    break;
                case EventType.EVENT_MSC /* 4 */:
                    float f2 = typedArrayObtainStyledAttributes.getFloat(index, this.f2668o) % 360.0f;
                    this.f2668o = f2;
                    if (f2 < 0.0f) {
                        this.f2668o = (360.0f - f2) % 360.0f;
                    }
                    break;
                case EventType.EVENT_SW /* 5 */:
                    this.f2654a = typedArrayObtainStyledAttributes.getDimensionPixelOffset(index, this.f2654a);
                    break;
                case 6:
                    this.f2655b = typedArrayObtainStyledAttributes.getDimensionPixelOffset(index, this.f2655b);
                    break;
                case 7:
                    this.f2656c = typedArrayObtainStyledAttributes.getFloat(index, this.f2656c);
                    break;
                case 8:
                    int resourceId2 = typedArrayObtainStyledAttributes.getResourceId(index, this.f2657d);
                    this.f2657d = resourceId2;
                    if (resourceId2 == -1) {
                        this.f2657d = typedArrayObtainStyledAttributes.getInt(index, -1);
                    }
                    break;
                case 9:
                    int resourceId3 = typedArrayObtainStyledAttributes.getResourceId(index, this.f2658e);
                    this.f2658e = resourceId3;
                    if (resourceId3 == -1) {
                        this.f2658e = typedArrayObtainStyledAttributes.getInt(index, -1);
                    }
                    break;
                case 10:
                    int resourceId4 = typedArrayObtainStyledAttributes.getResourceId(index, this.f2659f);
                    this.f2659f = resourceId4;
                    if (resourceId4 == -1) {
                        this.f2659f = typedArrayObtainStyledAttributes.getInt(index, -1);
                    }
                    break;
                case 11:
                    int resourceId5 = typedArrayObtainStyledAttributes.getResourceId(index, this.f2660g);
                    this.f2660g = resourceId5;
                    if (resourceId5 == -1) {
                        this.f2660g = typedArrayObtainStyledAttributes.getInt(index, -1);
                    }
                    break;
                case 12:
                    int resourceId6 = typedArrayObtainStyledAttributes.getResourceId(index, this.f2661h);
                    this.f2661h = resourceId6;
                    if (resourceId6 == -1) {
                        this.f2661h = typedArrayObtainStyledAttributes.getInt(index, -1);
                    }
                    break;
                case 13:
                    int resourceId7 = typedArrayObtainStyledAttributes.getResourceId(index, this.f2662i);
                    this.f2662i = resourceId7;
                    if (resourceId7 == -1) {
                        this.f2662i = typedArrayObtainStyledAttributes.getInt(index, -1);
                    }
                    break;
                case 14:
                    int resourceId8 = typedArrayObtainStyledAttributes.getResourceId(index, this.f2663j);
                    this.f2663j = resourceId8;
                    if (resourceId8 == -1) {
                        this.f2663j = typedArrayObtainStyledAttributes.getInt(index, -1);
                    }
                    break;
                case 15:
                    int resourceId9 = typedArrayObtainStyledAttributes.getResourceId(index, this.f2664k);
                    this.f2664k = resourceId9;
                    if (resourceId9 == -1) {
                        this.f2664k = typedArrayObtainStyledAttributes.getInt(index, -1);
                    }
                    break;
                case 16:
                    int resourceId10 = typedArrayObtainStyledAttributes.getResourceId(index, this.f2665l);
                    this.f2665l = resourceId10;
                    if (resourceId10 == -1) {
                        this.f2665l = typedArrayObtainStyledAttributes.getInt(index, -1);
                    }
                    break;
                case EventType.EVENT_LED /* 17 */:
                    int resourceId11 = typedArrayObtainStyledAttributes.getResourceId(index, this.f2669p);
                    this.f2669p = resourceId11;
                    if (resourceId11 == -1) {
                        this.f2669p = typedArrayObtainStyledAttributes.getInt(index, -1);
                    }
                    break;
                case EventType.EVENT_SND /* 18 */:
                    int resourceId12 = typedArrayObtainStyledAttributes.getResourceId(index, this.f2670q);
                    this.f2670q = resourceId12;
                    if (resourceId12 == -1) {
                        this.f2670q = typedArrayObtainStyledAttributes.getInt(index, -1);
                    }
                    break;
                case 19:
                    int resourceId13 = typedArrayObtainStyledAttributes.getResourceId(index, this.r);
                    this.r = resourceId13;
                    if (resourceId13 == -1) {
                        this.r = typedArrayObtainStyledAttributes.getInt(index, -1);
                    }
                    break;
                case EventType.EVENT_REP /* 20 */:
                    int resourceId14 = typedArrayObtainStyledAttributes.getResourceId(index, this.f2671s);
                    this.f2671s = resourceId14;
                    if (resourceId14 == -1) {
                        this.f2671s = typedArrayObtainStyledAttributes.getInt(index, -1);
                    }
                    break;
                case EventType.EVENT_FF /* 21 */:
                    this.f2672t = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.f2672t);
                    break;
                case EventType.EVENT_PWR /* 22 */:
                    this.f2673u = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.f2673u);
                    break;
                case EventType.EVENT_FF_STATUS /* 23 */:
                    this.f2674v = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.f2674v);
                    break;
                case 24:
                    this.f2675w = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.f2675w);
                    break;
                case 25:
                    this.f2676x = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.f2676x);
                    break;
                case 26:
                    this.f2677y = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.f2677y);
                    break;
                case 27:
                    this.f2649S = typedArrayObtainStyledAttributes.getBoolean(index, this.f2649S);
                    break;
                case 28:
                    this.f2650T = typedArrayObtainStyledAttributes.getBoolean(index, this.f2650T);
                    break;
                case 29:
                    this.f2678z = typedArrayObtainStyledAttributes.getFloat(index, this.f2678z);
                    break;
                case 30:
                    this.f2631A = typedArrayObtainStyledAttributes.getFloat(index, this.f2631A);
                    break;
                case 31:
                    int i5 = typedArrayObtainStyledAttributes.getInt(index, 0);
                    this.f2638H = i5;
                    if (i5 == 1) {
                        Log.e("ConstraintLayout", "layout_constraintWidth_default=\"wrap\" is deprecated.\nUse layout_width=\"WRAP_CONTENT\" and layout_constrainedWidth=\"true\" instead.");
                    }
                    break;
                case 32:
                    int i6 = typedArrayObtainStyledAttributes.getInt(index, 0);
                    this.f2639I = i6;
                    if (i6 == 1) {
                        Log.e("ConstraintLayout", "layout_constraintHeight_default=\"wrap\" is deprecated.\nUse layout_height=\"WRAP_CONTENT\" and layout_constrainedHeight=\"true\" instead.");
                    }
                    break;
                case 33:
                    try {
                        this.f2640J = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.f2640J);
                    } catch (Exception unused) {
                        if (typedArrayObtainStyledAttributes.getInt(index, this.f2640J) == -2) {
                            this.f2640J = -2;
                        }
                    }
                    break;
                case 34:
                    try {
                        this.f2642L = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.f2642L);
                    } catch (Exception unused2) {
                        if (typedArrayObtainStyledAttributes.getInt(index, this.f2642L) == -2) {
                            this.f2642L = -2;
                        }
                    }
                    break;
                case 35:
                    this.f2644N = Math.max(0.0f, typedArrayObtainStyledAttributes.getFloat(index, this.f2644N));
                    break;
                case 36:
                    try {
                        this.f2641K = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.f2641K);
                    } catch (Exception unused3) {
                        if (typedArrayObtainStyledAttributes.getInt(index, this.f2641K) == -2) {
                            this.f2641K = -2;
                        }
                    }
                    break;
                case 37:
                    try {
                        this.f2643M = typedArrayObtainStyledAttributes.getDimensionPixelSize(index, this.f2643M);
                    } catch (Exception unused4) {
                        if (typedArrayObtainStyledAttributes.getInt(index, this.f2643M) == -2) {
                            this.f2643M = -2;
                        }
                    }
                    break;
                case 38:
                    this.f2645O = Math.max(0.0f, typedArrayObtainStyledAttributes.getFloat(index, this.f2645O));
                    break;
                default:
                    switch (i4) {
                        case 44:
                            String string = typedArrayObtainStyledAttributes.getString(index);
                            this.f2632B = string;
                            this.f2633C = -1;
                            if (string != null) {
                                int length = string.length();
                                int iIndexOf = this.f2632B.indexOf(44);
                                if (iIndexOf <= 0 || iIndexOf >= length - 1) {
                                    i2 = 0;
                                } else {
                                    String strSubstring = this.f2632B.substring(0, iIndexOf);
                                    if (strSubstring.equalsIgnoreCase("W")) {
                                        this.f2633C = 0;
                                    } else if (strSubstring.equalsIgnoreCase("H")) {
                                        this.f2633C = 1;
                                    }
                                    i2 = iIndexOf + 1;
                                }
                                int iIndexOf2 = this.f2632B.indexOf(58);
                                if (iIndexOf2 < 0 || iIndexOf2 >= length - 1) {
                                    String strSubstring2 = this.f2632B.substring(i2);
                                    if (strSubstring2.length() > 0) {
                                        Float.parseFloat(strSubstring2);
                                    }
                                } else {
                                    String strSubstring3 = this.f2632B.substring(i2, iIndexOf2);
                                    String strSubstring4 = this.f2632B.substring(iIndexOf2 + 1);
                                    if (strSubstring3.length() > 0 && strSubstring4.length() > 0) {
                                        try {
                                            float f3 = Float.parseFloat(strSubstring3);
                                            float f4 = Float.parseFloat(strSubstring4);
                                            if (f3 > 0.0f && f4 > 0.0f) {
                                                if (this.f2633C == 1) {
                                                    Math.abs(f4 / f3);
                                                } else {
                                                    Math.abs(f3 / f4);
                                                }
                                            }
                                        } catch (NumberFormatException unused5) {
                                        }
                                    }
                                }
                            }
                            break;
                        case 45:
                            this.f2634D = typedArrayObtainStyledAttributes.getFloat(index, this.f2634D);
                            break;
                        case 46:
                            this.f2635E = typedArrayObtainStyledAttributes.getFloat(index, this.f2635E);
                            break;
                        case 47:
                            this.f2636F = typedArrayObtainStyledAttributes.getInt(index, 0);
                            break;
                        case 48:
                            this.f2637G = typedArrayObtainStyledAttributes.getInt(index, 0);
                            break;
                        case 49:
                            this.f2646P = typedArrayObtainStyledAttributes.getDimensionPixelOffset(index, this.f2646P);
                            break;
                        case 50:
                            this.f2647Q = typedArrayObtainStyledAttributes.getDimensionPixelOffset(index, this.f2647Q);
                            break;
                    }
                    break;
            }
        }
        typedArrayObtainStyledAttributes.recycle();
        a();
    }

    public C0141b(ViewGroup.LayoutParams layoutParams) {
        super(layoutParams);
        this.f2654a = -1;
        this.f2655b = -1;
        this.f2656c = -1.0f;
        this.f2657d = -1;
        this.f2658e = -1;
        this.f2659f = -1;
        this.f2660g = -1;
        this.f2661h = -1;
        this.f2662i = -1;
        this.f2663j = -1;
        this.f2664k = -1;
        this.f2665l = -1;
        this.f2666m = -1;
        this.f2667n = 0;
        this.f2668o = 0.0f;
        this.f2669p = -1;
        this.f2670q = -1;
        this.r = -1;
        this.f2671s = -1;
        this.f2672t = -1;
        this.f2673u = -1;
        this.f2674v = -1;
        this.f2675w = -1;
        this.f2676x = -1;
        this.f2677y = -1;
        this.f2678z = 0.5f;
        this.f2631A = 0.5f;
        this.f2632B = null;
        this.f2633C = 1;
        this.f2634D = -1.0f;
        this.f2635E = -1.0f;
        this.f2636F = 0;
        this.f2637G = 0;
        this.f2638H = 0;
        this.f2639I = 0;
        this.f2640J = 0;
        this.f2641K = 0;
        this.f2642L = 0;
        this.f2643M = 0;
        this.f2644N = 1.0f;
        this.f2645O = 1.0f;
        this.f2646P = -1;
        this.f2647Q = -1;
        this.f2648R = -1;
        this.f2649S = false;
        this.f2650T = false;
        this.f2651U = false;
        this.V = false;
        this.f2652W = false;
        this.f2653X = new C0138a();
    }

    public final void a() {
        this.f2651U = false;
        int i2 = ((ViewGroup.MarginLayoutParams) this).width;
        if (i2 == -2 && this.f2649S) {
            this.f2638H = 1;
        }
        int i3 = ((ViewGroup.MarginLayoutParams) this).height;
        if (i3 == -2 && this.f2650T) {
            this.f2639I = 1;
        }
        if ((i2 == 0 || i2 == -1) && i2 == 0 && this.f2638H == 1) {
            ((ViewGroup.MarginLayoutParams) this).width = -2;
            this.f2649S = true;
        }
        if ((i3 == 0 || i3 == -1) && i3 == 0 && this.f2639I == 1) {
            ((ViewGroup.MarginLayoutParams) this).height = -2;
            this.f2650T = true;
        }
        if (this.f2656c == -1.0f && this.f2654a == -1 && this.f2655b == -1) {
            return;
        }
        this.f2651U = true;
        if (!(this.f2653X instanceof C0139b)) {
            this.f2653X = new C0139b();
        }
        ((C0139b) this.f2653X).c(this.f2648R);
    }

    @Override // android.view.ViewGroup.MarginLayoutParams, android.view.ViewGroup.LayoutParams
    public final void resolveLayoutDirection(int i2) {
        int i3 = ((ViewGroup.MarginLayoutParams) this).leftMargin;
        int i4 = ((ViewGroup.MarginLayoutParams) this).rightMargin;
        super.resolveLayoutDirection(i2);
        getLayoutDirection();
        if (this.r == -1 && this.f2671s == -1 && this.f2670q == -1 && this.f2669p == -1) {
            if (this.f2659f != -1) {
                if (((ViewGroup.MarginLayoutParams) this).rightMargin <= 0 && i4 > 0) {
                    ((ViewGroup.MarginLayoutParams) this).rightMargin = i4;
                }
            } else if (this.f2660g != -1 && ((ViewGroup.MarginLayoutParams) this).rightMargin <= 0 && i4 > 0) {
                ((ViewGroup.MarginLayoutParams) this).rightMargin = i4;
            }
            if (this.f2657d != -1) {
                if (((ViewGroup.MarginLayoutParams) this).leftMargin > 0 || i3 <= 0) {
                    return;
                }
                ((ViewGroup.MarginLayoutParams) this).leftMargin = i3;
                return;
            }
            if (this.f2658e == -1 || ((ViewGroup.MarginLayoutParams) this).leftMargin > 0 || i3 <= 0) {
                return;
            }
            ((ViewGroup.MarginLayoutParams) this).leftMargin = i3;
        }
    }
}
