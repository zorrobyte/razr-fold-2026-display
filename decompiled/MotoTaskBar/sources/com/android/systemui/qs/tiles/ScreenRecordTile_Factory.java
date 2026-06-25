package com.android.systemui.qs.tiles;

import android.os.Handler;
import android.os.Looper;
import com.android.systemui.displays.ActivityStarter;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.tiles.controller.ScreenRecordQSTileController;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class ScreenRecordTile_Factory implements Factory {
    private final Provider activityStarterProvider;
    private final Provider backgroundLooperProvider;
    private final Provider controllerProvider;
    private final Provider hostProvider;
    private final Provider mainHandlerProvider;

    public ScreenRecordTile_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        this.hostProvider = provider;
        this.backgroundLooperProvider = provider2;
        this.mainHandlerProvider = provider3;
        this.activityStarterProvider = provider4;
        this.controllerProvider = provider5;
    }

    public static ScreenRecordTile_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        return new ScreenRecordTile_Factory(provider, provider2, provider3, provider4, provider5);
    }

    public static ScreenRecordTile newInstance(QSHost qSHost, Looper looper, Handler handler, ActivityStarter activityStarter, ScreenRecordQSTileController screenRecordQSTileController) {
        return new ScreenRecordTile(qSHost, looper, handler, activityStarter, screenRecordQSTileController);
    }

    @Override // javax.inject.Provider
    public ScreenRecordTile get() {
        return newInstance((QSHost) this.hostProvider.get(), (Looper) this.backgroundLooperProvider.get(), (Handler) this.mainHandlerProvider.get(), (ActivityStarter) this.activityStarterProvider.get(), (ScreenRecordQSTileController) this.controllerProvider.get());
    }
}
