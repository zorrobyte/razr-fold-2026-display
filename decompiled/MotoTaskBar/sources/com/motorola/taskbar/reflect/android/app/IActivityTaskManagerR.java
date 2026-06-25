package com.motorola.taskbar.reflect.android.app;

import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes2.dex */
public class IActivityTaskManagerR {
    private static final String METHOD_MOVE_TASK_TO_BACK = "moveTaskToBack";
    private static final String TAG = "IActivityTaskManagerR";
    private final Object mIActivityTaskManager;
    private Method mMethodMoveTaskToBack;

    public IActivityTaskManagerR(Object obj) {
        this.mIActivityTaskManager = obj;
        Class<?> cls = obj.getClass();
        try {
            this.mMethodMoveTaskToBack = cls.getDeclaredMethod(METHOD_MOVE_TASK_TO_BACK, Integer.TYPE);
        } catch (NoSuchMethodException unused) {
            Log.e(TAG, "Unable to initialize class " + cls.getSimpleName());
        }
    }

    public boolean moveTaskToBack(int i) {
        Method method = this.mMethodMoveTaskToBack;
        if (method == null) {
            Log.w(TAG, "class not initialized or no service");
            return false;
        }
        try {
            return ((Boolean) method.invoke(this.mIActivityTaskManager, Integer.valueOf(i))).booleanValue();
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException unused) {
            Log.e(TAG, "unable to invoke moveTaskToBack");
            return false;
        }
    }
}
