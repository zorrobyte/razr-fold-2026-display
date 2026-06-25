package androidx.window.reflection;

import android.util.Log;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import kotlin.jvm.functions.Function0;

/* JADX INFO: compiled from: ReflectionUtils.kt */
/* JADX INFO: loaded from: classes.dex */
public final class ReflectionUtils {
    public static final ReflectionUtils INSTANCE = new ReflectionUtils();

    private ReflectionUtils() {
    }

    public static final boolean validateReflection$window_release(String str, Function0 function0) {
        str.getClass();
        function0.getClass();
        try {
            boolean zBooleanValue = ((Boolean) function0.invoke()).booleanValue();
            if (!zBooleanValue) {
                Log.e("ReflectionGuard", str);
            }
            return zBooleanValue;
        } catch (ClassNotFoundException unused) {
            Log.e("ReflectionGuard", "ClassNotFound: " + str);
            return false;
        } catch (NoSuchFieldException unused2) {
            Log.e("ReflectionGuard", "NoSuchField: " + str);
            return false;
        } catch (NoSuchMethodException unused3) {
            Log.e("ReflectionGuard", "NoSuchMethod: " + str);
            return false;
        }
    }

    public final boolean checkIsPresent$window_release(Function0 function0) {
        function0.getClass();
        try {
            function0.invoke();
            return true;
        } catch (ClassNotFoundException | NoClassDefFoundError unused) {
            return false;
        }
    }

    public final boolean doesReturn$window_release(Method method, Class cls) {
        method.getClass();
        cls.getClass();
        return method.getReturnType().equals(cls);
    }

    public final boolean isPublic$window_release(Constructor constructor) {
        constructor.getClass();
        return Modifier.isPublic(constructor.getModifiers());
    }

    public final boolean isPublic$window_release(Field field) {
        field.getClass();
        return Modifier.isPublic(field.getModifiers());
    }

    public final boolean isPublic$window_release(Method method) {
        method.getClass();
        return Modifier.isPublic(method.getModifiers());
    }
}
