package com.android.systemui.qs.tiles;

import android.os.Handler;
import android.os.Looper;
import com.android.systemui.displays.ActivityStarter;
import com.android.systemui.qs.QSHost;
import com.motorola.extendscreenshot.ScreenshotController;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class ScreenshotTile_Factory implements Factory {
    private final Provider activityStarterProvider;
    private final Provider backgroundLooperProvider;
    private final Provider hostProvider;
    private final Provider mainHandlerProvider;
    private final Provider screenshotControllerProvider;

    public ScreenshotTile_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        this.hostProvider = provider;
        this.backgroundLooperProvider = provider2;
        this.mainHandlerProvider = provider3;
        this.screenshotControllerProvider = provider4;
        this.activityStarterProvider = provider5;
    }

    public static ScreenshotTile_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5) {
        return new ScreenshotTile_Factory(provider, provider2, provider3, provider4, provider5);
    }

    public static ScreenshotTile newInstance(QSHost qSHost, Looper looper, Handler handler, ScreenshotController screenshotController, ActivityStarter activityStarter) {
        return new ScreenshotTile(qSHost, looper, handler, screenshotController, activityStarter);
    }

    @Override // javax.inject.Provider
    public ScreenshotTile get() {
        return newInstance((QSHost) this.hostProvider.get(), (Looper) this.backgroundLooperProvider.get(), (Handler) this.mainHandlerProvider.get(), (ScreenshotController) this.screenshotControllerProvider.get(), (ActivityStarter) this.activityStarterProvider.get());
    }
}
