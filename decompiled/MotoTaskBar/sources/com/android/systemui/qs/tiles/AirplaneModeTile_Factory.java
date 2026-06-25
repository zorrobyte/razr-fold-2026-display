package com.android.systemui.qs.tiles;

import android.os.Handler;
import android.os.Looper;
import com.android.systemui.displays.ActivityStarter;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.tiles.controller.AirplaneQSTileController;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class AirplaneModeTile_Factory implements Factory {
    private final Provider activityStarterProvider;
    private final Provider backgroundLooperProvider;
    private final Provider controllerProvider;
    private final Provider hostProvider;
    private final Provider mainHandlerProvider;

    public AirplaneModeTile_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        this.hostProvider = provider;
        this.backgroundLooperProvider = provider2;
        this.mainHandlerProvider = provider3;
        this.activityStarterProvider = provider4;
        this.controllerProvider = provider5;
    }

    public static AirplaneModeTile_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        return new AirplaneModeTile_Factory(provider, provider2, provider3, provider4, provider5);
    }

    public static AirplaneModeTile newInstance(QSHost qSHost, Looper looper, Handler handler, ActivityStarter activityStarter, AirplaneQSTileController airplaneQSTileController) {
        return new AirplaneModeTile(qSHost, looper, handler, activityStarter, airplaneQSTileController);
    }

    @Override // javax.inject.Provider
    public AirplaneModeTile get() {
        return newInstance((QSHost) this.hostProvider.get(), (Looper) this.backgroundLooperProvider.get(), (Handler) this.mainHandlerProvider.get(), (ActivityStarter) this.activityStarterProvider.get(), (AirplaneQSTileController) this.controllerProvider.get());
    }
}
