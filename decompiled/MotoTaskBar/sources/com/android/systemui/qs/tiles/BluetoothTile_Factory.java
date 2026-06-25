package com.android.systemui.qs.tiles;

import android.os.Handler;
import android.os.Looper;
import com.android.systemui.displays.ActivityStarter;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.tiles.controller.BluetoothQSTileController;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class BluetoothTile_Factory implements Factory {
    private final Provider activityStarterProvider;
    private final Provider backgroundLooperProvider;
    private final Provider controllerProvider;
    private final Provider hostProvider;
    private final Provider mainHandlerProvider;

    public BluetoothTile_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        this.hostProvider = provider;
        this.backgroundLooperProvider = provider2;
        this.mainHandlerProvider = provider3;
        this.activityStarterProvider = provider4;
        this.controllerProvider = provider5;
    }

    public static BluetoothTile_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        return new BluetoothTile_Factory(provider, provider2, provider3, provider4, provider5);
    }

    public static BluetoothTile newInstance(QSHost qSHost, Looper looper, Handler handler, ActivityStarter activityStarter, BluetoothQSTileController bluetoothQSTileController) {
        return new BluetoothTile(qSHost, looper, handler, activityStarter, bluetoothQSTileController);
    }

    @Override // javax.inject.Provider
    public BluetoothTile get() {
        return newInstance((QSHost) this.hostProvider.get(), (Looper) this.backgroundLooperProvider.get(), (Handler) this.mainHandlerProvider.get(), (ActivityStarter) this.activityStarterProvider.get(), (BluetoothQSTileController) this.controllerProvider.get());
    }
}
