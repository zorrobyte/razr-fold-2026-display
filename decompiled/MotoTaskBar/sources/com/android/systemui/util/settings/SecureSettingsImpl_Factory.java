package com.android.systemui.util.settings;

import android.content.ContentResolver;
import com.android.systemui.settings.UserTracker;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class SecureSettingsImpl_Factory implements Factory {
    private final Provider contentResolverProvider;
    private final Provider userTrackerProvider;

    public SecureSettingsImpl_Factory(Provider provider, Provider provider2) {
        this.contentResolverProvider = provider;
        this.userTrackerProvider = provider2;
    }

    public static SecureSettingsImpl_Factory create(Provider provider, Provider provider2) {
        return new SecureSettingsImpl_Factory(provider, provider2);
    }

    public static SecureSettingsImpl newInstance(ContentResolver contentResolver, UserTracker userTracker) {
        return new SecureSettingsImpl(contentResolver, userTracker);
    }

    @Override // javax.inject.Provider
    public SecureSettingsImpl get() {
        return newInstance((ContentResolver) this.contentResolverProvider.get(), (UserTracker) this.userTrackerProvider.get());
    }
}
