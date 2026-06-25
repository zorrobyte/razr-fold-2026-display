package com.android.settingslib.inputmethod;

import android.content.ContentResolver;
import android.content.Context;
import android.util.SparseArray;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class InputMethodSettingValuesWrapper {
    private final ContentResolver mContentResolver;
    private final InputMethodManager mImm;
    private final ArrayList mMethodList = new ArrayList();
    private static final Object sInstanceMapLock = new Object();
    private static SparseArray sInstanceMap = new SparseArray();

    private InputMethodSettingValuesWrapper(Context context) {
        this.mContentResolver = context.getContentResolver();
        this.mImm = (InputMethodManager) context.getSystemService(InputMethodManager.class);
        refreshAllInputMethodAndSubtypes();
    }

    public static InputMethodSettingValuesWrapper getInstance(Context context) {
        int userId = context.getUserId();
        synchronized (sInstanceMapLock) {
            try {
                if (sInstanceMap.size() == 0) {
                    InputMethodSettingValuesWrapper inputMethodSettingValuesWrapper = new InputMethodSettingValuesWrapper(context);
                    sInstanceMap.put(userId, inputMethodSettingValuesWrapper);
                    return inputMethodSettingValuesWrapper;
                }
                if (sInstanceMap.indexOfKey(userId) >= 0) {
                    return (InputMethodSettingValuesWrapper) sInstanceMap.get(userId);
                }
                InputMethodSettingValuesWrapper inputMethodSettingValuesWrapper2 = new InputMethodSettingValuesWrapper(context);
                sInstanceMap.put(context.getUserId(), inputMethodSettingValuesWrapper2);
                return inputMethodSettingValuesWrapper2;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void refreshAllInputMethodAndSubtypes() {
        this.mMethodList.clear();
        List inputMethodListAsUser = this.mImm.getInputMethodListAsUser(this.mContentResolver.getUserId(), 1);
        for (int i = 0; i < inputMethodListAsUser.size(); i++) {
            InputMethodInfo inputMethodInfo = (InputMethodInfo) inputMethodListAsUser.get(i);
            if (!inputMethodInfo.isVirtualDeviceOnly()) {
                this.mMethodList.add(inputMethodInfo);
            }
        }
    }
}
