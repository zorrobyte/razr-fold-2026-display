package com.android.systemui.shared.system;

import android.app.AppGlobals;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.IPackageManager;
import android.content.pm.ResolveInfo;
import android.os.RemoteException;
import android.os.UserHandle;

/* JADX INFO: loaded from: classes.dex */
public class PackageManagerWrapper {
    private static final PackageManagerWrapper sInstance = new PackageManagerWrapper();
    private static final IPackageManager mIPackageManager = AppGlobals.getPackageManager();

    private PackageManagerWrapper() {
    }

    public static PackageManagerWrapper getInstance() {
        return sInstance;
    }

    public ActivityInfo getActivityInfo(ComponentName componentName, int i) {
        try {
            return mIPackageManager.getActivityInfo(componentName, 128L, i);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResolveInfo resolveActivity(Intent intent, int i) {
        try {
            return mIPackageManager.resolveIntent(intent, intent.resolveTypeIfNeeded(AppGlobals.getInitialApplication().getContentResolver()), i, UserHandle.getCallingUserId());
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }
}
