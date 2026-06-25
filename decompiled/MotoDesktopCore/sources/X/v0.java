package X;

import android.os.Build;
import android.os.SystemProperties;
import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
public abstract class v0 {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static final boolean f334a = Build.IS_DEBUGGABLE;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public static final boolean f335b = SystemProperties.getBoolean("ro.product.is_production", true);

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public static final boolean f336c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public static final boolean f337d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public static final boolean f338e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public static final boolean f339f;

    static {
        f(2);
        f336c = f(3);
        f337d = f(4);
        f338e = f(5);
        f339f = f(6);
    }

    public static void a(String str, String str2) {
        if (f336c) {
            Log.d("MotoDesktopCore", d(str, str2));
        }
    }

    public static void b(String str, String str2) {
        if (f339f) {
            Log.e("MotoDesktopCore", d(str, str2));
        }
    }

    public static void c(String str, String str2, Exception exc) {
        if (f339f) {
            Log.e("MotoDesktopCore", d(str, str2), exc);
        }
    }

    public static String d(String str, String str2) {
        return "[ " + str + " ] - " + str2;
    }

    public static void e(String str, String str2) {
        if (f337d) {
            Log.i("MotoDesktopCore", d(str, str2));
        }
    }

    public static boolean f(int i2) {
        return (f334a || !f335b) ? i2 >= 3 : Log.isLoggable("MotoDesktopCore", i2);
    }

    public static void g(String str, String str2) {
        if (f338e) {
            Log.w("MotoDesktopCore", d(str, str2));
        }
    }

    public static void h(String str, String str2, Exception exc) {
        if (f338e) {
            Log.w("MotoDesktopCore", d(str, str2), exc);
        }
    }
}
