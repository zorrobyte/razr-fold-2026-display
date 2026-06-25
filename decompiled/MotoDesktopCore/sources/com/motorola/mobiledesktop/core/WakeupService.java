package com.motorola.mobiledesktop.core;

import X.v0;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

/* JADX INFO: loaded from: classes.dex */
public class WakeupService extends Service {
    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        v0.a("WakeupService", "onBind");
        return new Binder();
    }

    @Override // android.app.Service
    public final void onCreate() {
        super.onCreate();
        v0.a("WakeupService", "onCreate");
    }

    @Override // android.app.Service
    public final void onDestroy() {
        super.onDestroy();
        v0.a("WakeupService", "onDestroy");
    }

    @Override // android.app.Service
    public final void onRebind(Intent intent) {
        super.onRebind(intent);
        v0.a("WakeupService", "onRebind");
    }

    @Override // android.app.Service
    public final int onStartCommand(Intent intent, int i2, int i3) {
        v0.a("WakeupService", "onStartCommand: intent = " + intent);
        return 1;
    }

    @Override // android.app.Service
    public final boolean onUnbind(Intent intent) {
        v0.a("WakeupService", "onUnbind");
        return super.onUnbind(intent);
    }
}
