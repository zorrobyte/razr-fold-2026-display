package com.motorola.smartconnect.core.proxy;

import android.content.Context;
import android.os.IBinder;

/* JADX INFO: loaded from: classes.dex */
public abstract class BaseProxy {
    protected Context context;

    public BaseProxy(Context context) {
        this.context = context.getApplicationContext();
    }

    public abstract IBinder onBinder();

    public abstract void onCreate();

    public abstract void onDestroy();
}
