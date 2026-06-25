package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.UserHandle;

/* JADX INFO: loaded from: classes.dex */
public interface CentralSurfaces {
    static PackageManager getPackageManagerForUser(Context context, int i) {
        if (i >= 0) {
            try {
                context = context.createPackageContextAsUser(context.getPackageName(), 4, new UserHandle(i));
            } catch (PackageManager.NameNotFoundException unused) {
            }
        }
        return context.getPackageManager();
    }
}
