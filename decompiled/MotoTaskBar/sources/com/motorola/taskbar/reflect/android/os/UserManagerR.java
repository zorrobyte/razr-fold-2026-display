package com.motorola.taskbar.reflect.android.os;

import android.os.UserManager;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public class UserManagerR {
    public static final boolean IS_INITIALIZED;
    private static final String TAG = "UserManagerR";
    private static Method sGetAppCloneProfiles;

    static {
        try {
            Class[] clsArr = new Class[0];
            sGetAppCloneProfiles = UserManager.class.getDeclaredMethod("getAppCloneProfiles", null);
        } catch (NoSuchMethodException unused) {
            Log.w(TAG, "No getAppCloneProfiles method");
        }
        IS_INITIALIZED = true;
    }

    public static List getAppCloneProfiles(UserManager userManager) {
        ArrayList arrayList = new ArrayList();
        Method method = sGetAppCloneProfiles;
        if (method != null) {
            try {
                return (List) method.invoke(userManager, null);
            } catch (IllegalAccessException | InvocationTargetException unused) {
                Log.e(TAG, "Unable to invoke getAppCloneProfiles");
            }
        }
        return arrayList;
    }
}
