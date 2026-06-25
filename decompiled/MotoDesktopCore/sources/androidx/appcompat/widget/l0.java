package androidx.appcompat.widget;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import o.AbstractC0152a;

/* JADX INFO: loaded from: classes.dex */
public abstract class l0 {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static final ThreadLocal f1262a = new ThreadLocal();

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public static final int[] f1263b = {-16842910};

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public static final int[] f1264c = {R.attr.state_focused};

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public static final int[] f1265d = {R.attr.state_pressed};

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public static final int[] f1266e = {R.attr.state_checked};

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public static final int[] f1267f = new int[0];

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public static final int[] f1268g = new int[1];

    public static int a(Context context, int i2) {
        ColorStateList colorStateListC = c(context, i2);
        if (colorStateListC != null && colorStateListC.isStateful()) {
            return colorStateListC.getColorForState(f1263b, colorStateListC.getDefaultColor());
        }
        ThreadLocal threadLocal = f1262a;
        TypedValue typedValue = (TypedValue) threadLocal.get();
        if (typedValue == null) {
            typedValue = new TypedValue();
            threadLocal.set(typedValue);
        }
        context.getTheme().resolveAttribute(R.attr.disabledAlpha, typedValue, true);
        float f2 = typedValue.getFloat();
        int iB = b(context, i2);
        int iRound = Math.round(Color.alpha(iB) * f2);
        int i3 = AbstractC0152a.f2780a;
        if (iRound < 0 || iRound > 255) {
            throw new IllegalArgumentException("alpha must be between 0 and 255.");
        }
        return (iB & 16777215) | (iRound << 24);
    }

    public static int b(Context context, int i2) {
        int[] iArr = f1268g;
        iArr[0] = i2;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes((AttributeSet) null, iArr);
        try {
            return typedArrayObtainStyledAttributes.getColor(0, 0);
        } finally {
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x001f A[Catch: all -> 0x0027, TRY_LEAVE, TryCatch #0 {all -> 0x0027, blocks: (B:3:0x000a, B:5:0x0010, B:7:0x0016, B:10:0x001f), top: B:16:0x000a }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static android.content.res.ColorStateList c(android.content.Context r3, int r4) {
        /*
            int[] r0 = androidx.appcompat.widget.l0.f1268g
            r1 = 0
            r0[r1] = r4
            r4 = 0
            android.content.res.TypedArray r4 = r3.obtainStyledAttributes(r4, r0)
            boolean r0 = r4.hasValue(r1)     // Catch: java.lang.Throwable -> L27
            if (r0 == 0) goto L1f
            int r0 = r4.getResourceId(r1, r1)     // Catch: java.lang.Throwable -> L27
            if (r0 == 0) goto L1f
            java.lang.Object r2 = b.AbstractC0122a.f2010a     // Catch: java.lang.Throwable -> L27
            android.content.res.ColorStateList r3 = r3.getColorStateList(r0)     // Catch: java.lang.Throwable -> L27
            if (r3 == 0) goto L1f
            goto L23
        L1f:
            android.content.res.ColorStateList r3 = r4.getColorStateList(r1)     // Catch: java.lang.Throwable -> L27
        L23:
            r4.recycle()
            return r3
        L27:
            r3 = move-exception
            r4.recycle()
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.l0.c(android.content.Context, int):android.content.res.ColorStateList");
    }
}
