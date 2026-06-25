package com.android.systemui.plugins.clocks;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;

/* JADX INFO: compiled from: ContextExt.kt */
/* JADX INFO: loaded from: classes.dex */
public final class ContextExt {
    public static final int $stable = 0;
    public static final ContextExt INSTANCE = new ContextExt();

    private ContextExt() {
    }

    public final int getDimen(Context context, String str) {
        context.getClass();
        str.getClass();
        int identifier = context.getResources().getIdentifier(str, "dimen", context.getPackageName());
        if (identifier == 0) {
            return 0;
        }
        return context.getResources().getDimensionPixelSize(identifier);
    }

    public final int getId(Context context, String str) throws PackageManager.NameNotFoundException {
        context.getClass();
        str.getClass();
        Resources resourcesForApplication = context.getPackageManager().getResourcesForApplication(context.getPackageName());
        resourcesForApplication.getClass();
        return resourcesForApplication.getIdentifier(str, "id", context.getPackageName());
    }
}
