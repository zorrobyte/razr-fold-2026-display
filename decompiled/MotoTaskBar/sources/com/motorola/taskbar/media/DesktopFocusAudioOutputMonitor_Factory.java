package com.motorola.taskbar.media;

import android.content.Context;
import com.android.systemui.settings.UserTracker;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes2.dex */
public final class DesktopFocusAudioOutputMonitor_Factory implements Factory {
    private final Provider contextProvider;
    private final Provider userTrackerProvider;

    public DesktopFocusAudioOutputMonitor_Factory(Provider provider, Provider provider2) {
        this.contextProvider = provider;
        this.userTrackerProvider = provider2;
    }

    public static DesktopFocusAudioOutputMonitor_Factory create(Provider provider, Provider provider2) {
        return new DesktopFocusAudioOutputMonitor_Factory(provider, provider2);
    }

    public static DesktopFocusAudioOutputMonitor newInstance(Context context, UserTracker userTracker) {
        return new DesktopFocusAudioOutputMonitor(context, userTracker);
    }

    @Override // javax.inject.Provider
    public DesktopFocusAudioOutputMonitor get() {
        return newInstance((Context) this.contextProvider.get(), (UserTracker) this.userTrackerProvider.get());
    }
}
