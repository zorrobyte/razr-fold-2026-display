package com.android.launcher3.icons.cache;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.SparseBooleanArray;
import com.android.launcher3.util.ReflectUtils;

/* JADX INFO: loaded from: classes.dex */
public abstract class VaultUtils {
    private static final SparseBooleanArray sSparseArray = new SparseBooleanArray();

    public static Drawable getSecureVaultAppIcon(Context context, String str) {
        Object objInvokeMethod = ReflectUtils.invokeMethod("com.motorola.internal.security.MotoSecurityUtils", "getSecureVaultAppIcon", new Class[]{Context.class, String.class}, new Object[]{context, str});
        if (objInvokeMethod instanceof Drawable) {
            return (Drawable) objInvokeMethod;
        }
        return null;
    }

    public static boolean isVaultProfile(Context context, int i) {
        SparseBooleanArray sparseBooleanArray = sSparseArray;
        if (sparseBooleanArray.indexOfKey(i) >= 0) {
            return sparseBooleanArray.get(i);
        }
        Object objInvokeMethod = ReflectUtils.invokeMethod("com.motorola.internal.security.MotoSecurityUtils", "isVaultProfileUserId", new Class[]{Context.class, Integer.TYPE}, new Object[]{context, Integer.valueOf(i)});
        if (!(objInvokeMethod instanceof Boolean)) {
            return false;
        }
        boolean zBooleanValue = ((Boolean) objInvokeMethod).booleanValue();
        sparseBooleanArray.put(i, zBooleanValue);
        return zBooleanValue;
    }
}
