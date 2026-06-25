package com.android.systemui;

import android.app.PendingIntent;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class ActivityIntentHelper {
    private final Context mContext;
    private final PackageManager mPm;

    public ActivityIntentHelper(Context context) {
        this.mContext = context;
        this.mPm = context.getPackageManager();
    }

    public ActivityInfo getPendingTargetActivityInfo(PendingIntent pendingIntent, int i, boolean z) {
        int i2 = !z ? 852096 : 65664;
        List listQueryIntentComponents = pendingIntent.queryIntentComponents(i2);
        if (listQueryIntentComponents.size() == 0) {
            return null;
        }
        if (listQueryIntentComponents.size() == 1) {
            return ((ResolveInfo) listQueryIntentComponents.get(0)).activityInfo;
        }
        ResolveInfo resolveInfoResolveActivityAsUser = this.mPm.resolveActivityAsUser(pendingIntent.getIntent(), i2, i);
        if (resolveInfoResolveActivityAsUser == null || wouldLaunchResolverActivity(resolveInfoResolveActivityAsUser, listQueryIntentComponents)) {
            return null;
        }
        return resolveInfoResolveActivityAsUser.activityInfo;
    }

    public boolean wouldLaunchResolverActivity(ResolveInfo resolveInfo, List list) {
        for (int i = 0; i < list.size(); i++) {
            ResolveInfo resolveInfo2 = (ResolveInfo) list.get(i);
            if (resolveInfo2.activityInfo.name.equals(resolveInfo.activityInfo.name) && resolveInfo2.activityInfo.packageName.equals(resolveInfo.activityInfo.packageName)) {
                return false;
            }
        }
        return true;
    }

    public boolean wouldPendingLaunchResolverActivity(PendingIntent pendingIntent, int i) {
        return getPendingTargetActivityInfo(pendingIntent, i, false) == null;
    }
}
