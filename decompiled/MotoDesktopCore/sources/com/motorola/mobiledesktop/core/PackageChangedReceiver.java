package com.motorola.mobiledesktop.core;

import X.Q0;
import X.v0;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/* JADX INFO: loaded from: classes.dex */
public class PackageChangedReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        if (intent != null) {
            String action = intent.getAction();
            if (!"android.intent.action.PACKAGE_CHANGED".equals(action)) {
                if ("android.intent.action.PACKAGE_FULLY_REMOVED".equals(action) && intent.getData() != null && "com.motorola.mobiledesktop".equals(intent.getData().getSchemeSpecificPart())) {
                    v0.a("PackageChangedReceiver", "receive ACTION_PACKAGE_FULLY_REMOVED");
                    Q0.c(context);
                    return;
                }
                return;
            }
            String[] stringArrayExtra = intent.getStringArrayExtra("android.intent.extra.changed_component_name_list");
            if (stringArrayExtra == null || stringArrayExtra.length == 0) {
                return;
            }
            for (String str : stringArrayExtra) {
                if ("com.motorola.mobiledesktop".equals(str)) {
                    v0.a("PackageChangedReceiver", "receive ACTION_PACKAGE_CHANGED");
                    Q0.c(context);
                }
            }
        }
    }
}
