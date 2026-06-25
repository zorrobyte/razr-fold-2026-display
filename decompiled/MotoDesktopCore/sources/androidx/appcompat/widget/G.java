package androidx.appcompat.widget;

import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.ScaleDrawable;
import c.AbstractC0123a;
import p.AbstractC0156b;
import p.InterfaceC0155a;

/* JADX INFO: loaded from: classes.dex */
public abstract class G {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static final Rect f992a = new Rect();

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public static final Class f993b;

    static {
        try {
            f993b = Class.forName("android.graphics.Insets");
        } catch (ClassNotFoundException unused) {
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static boolean a(Drawable drawable) {
        if (!(drawable instanceof DrawableContainer)) {
            if (drawable instanceof InterfaceC0155a) {
                ((AbstractC0156b) ((InterfaceC0155a) drawable)).getClass();
                return a(null);
            }
            if (drawable instanceof AbstractC0123a) {
                return a(((AbstractC0123a) drawable).f2015a);
            }
            if (drawable instanceof ScaleDrawable) {
                return a(((ScaleDrawable) drawable).getDrawable());
            }
            return true;
        }
        Drawable.ConstantState constantState = drawable.getConstantState();
        if (!(constantState instanceof DrawableContainer.DrawableContainerState)) {
            return true;
        }
        for (Drawable drawable2 : ((DrawableContainer.DrawableContainerState) constantState).getChildren()) {
            if (!a(drawable2)) {
                return false;
            }
        }
        return true;
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0069  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static android.graphics.Rect b(android.graphics.drawable.Drawable r11) {
        /*
            java.lang.Class r0 = androidx.appcompat.widget.G.f993b
            if (r0 == 0) goto L99
            boolean r1 = r11 instanceof p.InterfaceC0155a     // Catch: java.lang.Exception -> L92
            if (r1 == 0) goto L11
            p.a r11 = (p.InterfaceC0155a) r11     // Catch: java.lang.Exception -> L92
            p.b r11 = (p.AbstractC0156b) r11     // Catch: java.lang.Exception -> L92
            r1 = 0
            r11.getClass()     // Catch: java.lang.Exception -> L92
            r11 = r1
        L11:
            java.lang.Class r1 = r11.getClass()     // Catch: java.lang.Exception -> L92
            java.lang.String r2 = "getOpticalInsets"
            r3 = 0
            java.lang.reflect.Method r1 = r1.getMethod(r2, r3)     // Catch: java.lang.Exception -> L92
            java.lang.Object r11 = r1.invoke(r11, r3)     // Catch: java.lang.Exception -> L92
            if (r11 == 0) goto L99
            android.graphics.Rect r1 = new android.graphics.Rect     // Catch: java.lang.Exception -> L92
            r1.<init>()     // Catch: java.lang.Exception -> L92
            java.lang.reflect.Field[] r0 = r0.getFields()     // Catch: java.lang.Exception -> L92
            int r2 = r0.length     // Catch: java.lang.Exception -> L92
            r3 = 0
            r4 = r3
        L2e:
            if (r4 >= r2) goto L91
            r5 = r0[r4]     // Catch: java.lang.Exception -> L92
            java.lang.String r6 = r5.getName()     // Catch: java.lang.Exception -> L92
            int r7 = r6.hashCode()     // Catch: java.lang.Exception -> L92
            r8 = 3
            r9 = 2
            r10 = 1
            switch(r7) {
                case -1383228885: goto L5f;
                case 115029: goto L55;
                case 3317767: goto L4b;
                case 108511772: goto L41;
                default: goto L40;
            }     // Catch: java.lang.Exception -> L92
        L40:
            goto L69
        L41:
            java.lang.String r7 = "right"
            boolean r6 = r6.equals(r7)     // Catch: java.lang.Exception -> L92
            if (r6 == 0) goto L69
            r6 = r9
            goto L6a
        L4b:
            java.lang.String r7 = "left"
            boolean r6 = r6.equals(r7)     // Catch: java.lang.Exception -> L92
            if (r6 == 0) goto L69
            r6 = r3
            goto L6a
        L55:
            java.lang.String r7 = "top"
            boolean r6 = r6.equals(r7)     // Catch: java.lang.Exception -> L92
            if (r6 == 0) goto L69
            r6 = r10
            goto L6a
        L5f:
            java.lang.String r7 = "bottom"
            boolean r6 = r6.equals(r7)     // Catch: java.lang.Exception -> L92
            if (r6 == 0) goto L69
            r6 = r8
            goto L6a
        L69:
            r6 = -1
        L6a:
            if (r6 == 0) goto L88
            if (r6 == r10) goto L81
            if (r6 == r9) goto L7a
            if (r6 == r8) goto L73
            goto L8e
        L73:
            int r5 = r5.getInt(r11)     // Catch: java.lang.Exception -> L92
            r1.bottom = r5     // Catch: java.lang.Exception -> L92
            goto L8e
        L7a:
            int r5 = r5.getInt(r11)     // Catch: java.lang.Exception -> L92
            r1.right = r5     // Catch: java.lang.Exception -> L92
            goto L8e
        L81:
            int r5 = r5.getInt(r11)     // Catch: java.lang.Exception -> L92
            r1.top = r5     // Catch: java.lang.Exception -> L92
            goto L8e
        L88:
            int r5 = r5.getInt(r11)     // Catch: java.lang.Exception -> L92
            r1.left = r5     // Catch: java.lang.Exception -> L92
        L8e:
            int r4 = r4 + 1
            goto L2e
        L91:
            return r1
        L92:
            java.lang.String r11 = "DrawableUtils"
            java.lang.String r0 = "Couldn't obtain the optical insets. Ignoring."
            android.util.Log.e(r11, r0)
        L99:
            android.graphics.Rect r11 = androidx.appcompat.widget.G.f992a
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.G.b(android.graphics.drawable.Drawable):android.graphics.Rect");
    }

    public static PorterDuff.Mode c(int i2, PorterDuff.Mode mode) {
        if (i2 == 3) {
            return PorterDuff.Mode.SRC_OVER;
        }
        if (i2 == 5) {
            return PorterDuff.Mode.SRC_IN;
        }
        if (i2 == 9) {
            return PorterDuff.Mode.SRC_ATOP;
        }
        switch (i2) {
            case 14:
                return PorterDuff.Mode.MULTIPLY;
            case 15:
                return PorterDuff.Mode.SCREEN;
            case 16:
                return PorterDuff.Mode.ADD;
            default:
                return mode;
        }
    }
}
