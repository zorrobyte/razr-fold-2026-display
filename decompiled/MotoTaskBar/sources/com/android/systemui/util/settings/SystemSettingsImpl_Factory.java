package com.android.systemui.util.settings;

import android.content.ContentResolver;
import com.android.systemui.settings.UserTracker;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class SystemSettingsImpl_Factory implements Factory {
    private final Provider contentResolverProvider;
    private final Provider userTrackerProvider;

    public SystemSettingsImpl_Factory(Provider provider, Provider provider2) {
        this.contentResolverProvider = provider;
        this.userTrackerProvider = provider2;
    }

    public static SystemSettingsImpl_Factory create(Provider provider, Provider provider2) {
        return new SystemSettingsImpl_Factory(provider, provider2);
    }

    public static SystemSettingsImpl newInstance(ContentResolver contentResolver, UserTracker userTracker) {
        return new SystemSettingsImpl(contentResolver, userTracker);
    }

    @Override // javax.inject.Provider
    public SystemSettingsImpl get() {
        return newInstance((ContentResolver) this.contentResolverProvider.get(), (UserTracker) this.userTrackerProvider.get());
    }
}
