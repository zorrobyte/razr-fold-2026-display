package com.android.systemui.statusbar.phone;

import com.android.systemui.displays.ActivityStarter;

/* JADX INFO: loaded from: classes.dex */
public class KeyguardDismissUtil {
    private final ActivityStarter mActivityStarter;

    public KeyguardDismissUtil(ActivityStarter activityStarter) {
        this.mActivityStarter = activityStarter;
    }

    public void executeWhenUnlocked(ActivityStarter.OnDismissAction onDismissAction, boolean z, boolean z2) {
        this.mActivityStarter.dismissKeyguardThenExecute(onDismissAction, null, z2);
    }
}
