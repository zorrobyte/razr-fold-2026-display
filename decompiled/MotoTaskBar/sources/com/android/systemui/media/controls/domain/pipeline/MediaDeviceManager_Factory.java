package com.android.systemui.media.controls.domain.pipeline;

import android.content.Context;
import com.motorola.taskbar.media.DesktopFocusAudioOutputMonitor;
import dagger.internal.Factory;
import dagger.internal.Provider;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes.dex */
public final class MediaDeviceManager_Factory implements Factory {
    private final Provider bgExecutorProvider;
    private final Provider contextProvider;
    private final Provider desktopFocusAudioOutputMonitorProvider;
    private final Provider fgExecutorProvider;

    public MediaDeviceManager_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        this.contextProvider = provider;
        this.fgExecutorProvider = provider2;
        this.bgExecutorProvider = provider3;
        this.desktopFocusAudioOutputMonitorProvider = provider4;
    }

    public static MediaDeviceManager_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4) {
        return new MediaDeviceManager_Factory(provider, provider2, provider3, provider4);
    }

    public static MediaDeviceManager newInstance(Context context, Executor executor, Executor executor2, DesktopFocusAudioOutputMonitor desktopFocusAudioOutputMonitor) {
        return new MediaDeviceManager(context, executor, executor2, desktopFocusAudioOutputMonitor);
    }

    @Override // javax.inject.Provider
    public MediaDeviceManager get() {
        return newInstance((Context) this.contextProvider.get(), (Executor) this.fgExecutorProvider.get(), (Executor) this.bgExecutorProvider.get(), (DesktopFocusAudioOutputMonitor) this.desktopFocusAudioOutputMonitorProvider.get());
    }
}
