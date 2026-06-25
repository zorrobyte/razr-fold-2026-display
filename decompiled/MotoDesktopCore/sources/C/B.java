package C;

import android.graphics.Matrix;
import android.util.Log;
import android.view.View;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes.dex */
public final class B extends Y.r {

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public static Method f7h;

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public static boolean f8i;

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public static Method f9j;

    /* JADX INFO: renamed from: k, reason: collision with root package name */
    public static boolean f10k;

    /* JADX INFO: renamed from: l, reason: collision with root package name */
    public static Method f11l;

    /* JADX INFO: renamed from: m, reason: collision with root package name */
    public static boolean f12m;

    public final void s0(View view, Matrix matrix) {
        if (!f8i) {
            try {
                Method declaredMethod = View.class.getDeclaredMethod("transformMatrixToGlobal", Matrix.class);
                f7h = declaredMethod;
                declaredMethod.setAccessible(true);
            } catch (NoSuchMethodException e2) {
                Log.i("ViewUtilsApi21", "Failed to retrieve transformMatrixToGlobal method", e2);
            }
            f8i = true;
        }
        Method method = f7h;
        if (method != null) {
            try {
                method.invoke(view, matrix);
            } catch (IllegalAccessException unused) {
            } catch (InvocationTargetException e3) {
                throw new RuntimeException(e3.getCause());
            }
        }
    }

    public final void t0(View view, Matrix matrix) {
        if (!f10k) {
            try {
                Method declaredMethod = View.class.getDeclaredMethod("transformMatrixToLocal", Matrix.class);
                f9j = declaredMethod;
                declaredMethod.setAccessible(true);
            } catch (NoSuchMethodException e2) {
                Log.i("ViewUtilsApi21", "Failed to retrieve transformMatrixToLocal method", e2);
            }
            f10k = true;
        }
        Method method = f9j;
        if (method != null) {
            try {
                method.invoke(view, matrix);
            } catch (IllegalAccessException unused) {
            } catch (InvocationTargetException e3) {
                throw new RuntimeException(e3.getCause());
            }
        }
    }
}
