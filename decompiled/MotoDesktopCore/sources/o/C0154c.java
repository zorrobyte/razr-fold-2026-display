package o;

import Y.r;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.fonts.FontVariationAxis;
import android.util.Log;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;

/* JADX INFO: renamed from: o.c, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0154c extends r {

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public final Class f2783h;

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public final Constructor f2784i;

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public final Method f2785j;

    /* JADX INFO: renamed from: k, reason: collision with root package name */
    public final Method f2786k;

    /* JADX INFO: renamed from: l, reason: collision with root package name */
    public final Method f2787l;

    /* JADX INFO: renamed from: m, reason: collision with root package name */
    public final Method f2788m;

    /* JADX INFO: renamed from: n, reason: collision with root package name */
    public final Method f2789n;

    public C0154c() throws NoSuchMethodException {
        Method methodZ0;
        Constructor<?> constructor;
        Method methodY0;
        Method method;
        Method method2;
        Method method3;
        Class<?> cls = null;
        try {
            Class<?> cls2 = Class.forName("android.graphics.FontFamily");
            constructor = cls2.getConstructor(null);
            methodY0 = y0(cls2);
            Class cls3 = Integer.TYPE;
            method = cls2.getMethod("addFontFromBuffer", ByteBuffer.class, cls3, FontVariationAxis[].class, cls3, cls3);
            method2 = cls2.getMethod("freeze", null);
            method3 = cls2.getMethod("abortCreation", null);
            methodZ0 = z0(cls2);
            cls = cls2;
        } catch (ClassNotFoundException | NoSuchMethodException e2) {
            Log.e("TypefaceCompatApi26Impl", "Unable to collect necessary methods for class ".concat(e2.getClass().getName()), e2);
            methodZ0 = null;
            constructor = null;
            methodY0 = null;
            method = null;
            method2 = null;
            method3 = null;
        }
        this.f2783h = cls;
        this.f2784i = constructor;
        this.f2785j = methodY0;
        this.f2786k = method;
        this.f2787l = method2;
        this.f2788m = method3;
        this.f2789n = methodZ0;
    }

    public static Method y0(Class cls) {
        Class cls2 = Integer.TYPE;
        return cls.getMethod("addFontFromAssetManager", AssetManager.class, String.class, cls2, Boolean.TYPE, cls2, cls2, cls2, FontVariationAxis[].class);
    }

    @Override // Y.r
    public final Typeface l(Context context, n.b bVar, Resources resources, int i2) {
        if (!w0()) {
            return super.l(context, bVar, resources, i2);
        }
        Object objX0 = x0();
        for (n.c cVar : bVar.f2770a) {
            if (!t0(context, objX0, cVar.f2771a, cVar.f2775e, cVar.f2772b, cVar.f2773c ? 1 : 0, FontVariationAxis.fromFontVariationSettings(cVar.f2774d))) {
                s0(objX0);
                return null;
            }
        }
        if (v0(objX0)) {
            return u0(objX0);
        }
        return null;
    }

    @Override // Y.r
    public final Typeface m(Context context, Resources resources, int i2, String str, int i3) {
        if (!w0()) {
            return super.m(context, resources, i2, str, i3);
        }
        Object objX0 = x0();
        if (!t0(context, objX0, str, 0, -1, -1, null)) {
            s0(objX0);
            return null;
        }
        if (v0(objX0)) {
            return u0(objX0);
        }
        return null;
    }

    public final void s0(Object obj) {
        try {
            this.f2788m.invoke(obj, null);
        } catch (IllegalAccessException | InvocationTargetException e2) {
            throw new RuntimeException(e2);
        }
    }

    public final boolean t0(Context context, Object obj, String str, int i2, int i3, int i4, FontVariationAxis[] fontVariationAxisArr) {
        try {
            return ((Boolean) this.f2785j.invoke(obj, context.getAssets(), str, 0, Boolean.FALSE, Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4), fontVariationAxisArr)).booleanValue();
        } catch (IllegalAccessException | InvocationTargetException e2) {
            throw new RuntimeException(e2);
        }
    }

    public final Typeface u0(Object obj) {
        try {
            Object objNewInstance = Array.newInstance((Class<?>) this.f2783h, 1);
            Array.set(objNewInstance, 0, obj);
            return (Typeface) this.f2789n.invoke(null, objNewInstance, "sans-serif", -1, -1);
        } catch (IllegalAccessException | InvocationTargetException e2) {
            throw new RuntimeException(e2);
        }
    }

    public final boolean v0(Object obj) {
        try {
            return ((Boolean) this.f2787l.invoke(obj, null)).booleanValue();
        } catch (IllegalAccessException | InvocationTargetException e2) {
            throw new RuntimeException(e2);
        }
    }

    public final boolean w0() {
        Method method = this.f2785j;
        if (method == null) {
            Log.w("TypefaceCompatApi26Impl", "Unable to collect necessary private methods. Fallback to legacy implementation.");
        }
        return method != null;
    }

    public final Object x0() {
        try {
            return this.f2784i.newInstance(null);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e2) {
            throw new RuntimeException(e2);
        }
    }

    public final Method z0(Class cls) throws NoSuchMethodException {
        Class<?> cls2 = Array.newInstance((Class<?>) cls, 1).getClass();
        Class cls3 = Integer.TYPE;
        Method declaredMethod = Typeface.class.getDeclaredMethod("createFromFamiliesWithDefault", cls2, String.class, cls3, cls3);
        declaredMethod.setAccessible(true);
        return declaredMethod;
    }
}
