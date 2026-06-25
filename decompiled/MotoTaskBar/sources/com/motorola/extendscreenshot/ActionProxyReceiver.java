package com.motorola.extendscreenshot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/* JADX INFO: loaded from: classes.dex */
public class ActionProxyReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        Intent intent2;
        String action = intent.getAction();
        action.hashCode();
        if (action.equals("ACTION.share_img") && (intent2 = (Intent) intent.getParcelableExtra("intent")) != null) {
            context.startActivity(intent2);
        }
    }
}
