package com.android.launcher3.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes.dex */
public abstract class ReflectUtils {
    public static Object getFieldValue(Class cls, Object obj, String str) {
        try {
            return getFieldValueOrThrow(obj, cls.getDeclaredField(str));
        } catch (Exception unused) {
            return null;
        }
    }

    public static Object getFieldValue(Object obj, String str) {
        return getFieldValue(obj.getClass(), obj, str);
    }

    public static Object getFieldValueOrThrow(Object obj, Field field) {
        if (field == null) {
            return null;
        }
        try {
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            return field.get(obj);
        } catch (Exception e) {
            throw new RuntimeException("Exception on get field value", e);
        }
    }

    public static Object invokeMethod(String str, String str2, Class[] clsArr, Object[] objArr) {
        try {
            return invokeMethodOrThrow(Class.forName(str), null, str2, clsArr, objArr);
        } catch (Exception unused) {
            return null;
        }
    }

    public static Object invokeMethodOrThrow(Class cls, Object obj, String str, Class[] clsArr, Object[] objArr) {
        try {
            Method declaredMethod = cls.getDeclaredMethod(str, clsArr);
            if (declaredMethod == null) {
                return null;
            }
            if (!declaredMethod.isAccessible()) {
                declaredMethod.setAccessible(true);
            }
            return declaredMethod.invoke(obj, objArr);
        } catch (Exception e) {
            throw new RuntimeException("Exception on invoke method", e);
        }
    }
}
