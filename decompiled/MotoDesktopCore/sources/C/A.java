package C;

import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes.dex */
public abstract class A {

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public static Field f4b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public static boolean f5c;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static final B f3a = new B();

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public static final C0002c f6d = new C0002c(Float.class, "translationAlpha", 5);

    static {
        new C0002c(Rect.class, "clipBounds", 6);
    }

    public static void a(View view, int i2, int i3, int i4, int i5) {
        if (!B.f12m) {
            try {
                Class cls = Integer.TYPE;
                Method declaredMethod = View.class.getDeclaredMethod("setLeftTopRightBottom", cls, cls, cls, cls);
                B.f11l = declaredMethod;
                declaredMethod.setAccessible(true);
            } catch (NoSuchMethodException e2) {
                Log.i("ViewUtilsApi22", "Failed to retrieve setLeftTopRightBottom method", e2);
            }
            B.f12m = true;
        }
        Method method = B.f11l;
        if (method != null) {
            try {
                method.invoke(view, Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4), Integer.valueOf(i5));
            } catch (IllegalAccessException unused) {
            } catch (InvocationTargetException e3) {
                throw new RuntimeException(e3.getCause());
            }
        }
    }

    public static void b(View view, float f2) {
        if (!Y.r.f431e) {
            try {
                Method declaredMethod = View.class.getDeclaredMethod("setTransitionAlpha", Float.TYPE);
                Y.r.f430d = declaredMethod;
                declaredMethod.setAccessible(true);
            } catch (NoSuchMethodException e2) {
                Log.i("ViewUtilsApi19", "Failed to retrieve setTransitionAlpha method", e2);
            }
            Y.r.f431e = true;
        }
        Method method = Y.r.f430d;
        if (method == null) {
            view.setAlpha(f2);
            return;
        }
        try {
            method.invoke(view, Float.valueOf(f2));
        } catch (IllegalAccessException unused) {
        } catch (InvocationTargetException e3) {
            throw new RuntimeException(e3.getCause());
        }
    }

    public static void c(View view, int i2) {
        if (!f5c) {
            try {
                Field declaredField = View.class.getDeclaredField("mViewFlags");
                f4b = declaredField;
                declaredField.setAccessible(true);
            } catch (NoSuchFieldException unused) {
                Log.i("ViewUtils", "fetchViewFlagsField: ");
            }
            f5c = true;
        }
        Field field = f4b;
        if (field != null) {
            try {
                f4b.setInt(view, i2 | (field.getInt(view) & (-13)));
            } catch (IllegalAccessException unused2) {
            }
        }
    }
}
