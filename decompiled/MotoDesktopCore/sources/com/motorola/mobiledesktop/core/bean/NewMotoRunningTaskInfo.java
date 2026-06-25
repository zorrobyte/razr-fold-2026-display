package com.motorola.mobiledesktop.core.bean;

import Y.r;
import android.app.ActivityManager;
import android.content.Intent;

/* JADX INFO: loaded from: classes.dex */
public class NewMotoRunningTaskInfo extends MotoRunningTaskInfo {
    public String pkgAndUserId;

    @Override // com.motorola.mobiledesktop.core.bean.MotoRunningTaskInfo
    public void setRunningTaskInfo(ActivityManager.RunningTaskInfo runningTaskInfo) {
        super.setRunningTaskInfo(runningTaskInfo);
        Intent intent = runningTaskInfo.baseIntent;
        if (intent == null || intent.getComponent() == null) {
            return;
        }
        this.pkgAndUserId = r.k(this.userId, intent.getComponent().getPackageName());
    }
}
