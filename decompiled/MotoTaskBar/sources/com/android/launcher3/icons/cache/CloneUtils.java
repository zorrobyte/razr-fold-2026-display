package com.android.launcher3.icons.cache;

import android.content.Context;
import android.content.pm.LauncherApps;
import android.content.pm.LauncherUserInfo;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.ArrayMap;

/* JADX INFO: loaded from: classes.dex */
public abstract class CloneUtils {
    private static final ArrayMap sCloneUserSparseArray = new ArrayMap();
    public static final ArrayMap sWorkUserSparseArray = new ArrayMap();

    public static synchronized boolean isCloneAppUserProfile(UserHandle userHandle, Context context) {
        ArrayMap arrayMap = sCloneUserSparseArray;
        if (arrayMap.indexOfKey(userHandle) >= 0) {
            return ((Boolean) arrayMap.get(userHandle)).booleanValue();
        }
        LauncherUserInfo launcherUserInfo = ((LauncherApps) context.getSystemService(LauncherApps.class)).getLauncherUserInfo(userHandle);
        if (launcherUserInfo == null || !TextUtils.equals(launcherUserInfo.getUserType(), "android.os.usertype.profile.CLONE")) {
            arrayMap.put(userHandle, new Boolean(false));
            return false;
        }
        arrayMap.put(userHandle, new Boolean(true));
        return true;
    }
}
