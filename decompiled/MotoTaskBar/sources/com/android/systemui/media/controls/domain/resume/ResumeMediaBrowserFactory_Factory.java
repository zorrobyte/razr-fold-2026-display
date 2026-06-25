package com.android.systemui.media.controls.domain.resume;

import android.content.Context;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class ResumeMediaBrowserFactory_Factory implements Factory {
    private final Provider browserFactoryProvider;
    private final Provider contextProvider;
    private final Provider loggerProvider;

    public ResumeMediaBrowserFactory_Factory(Provider provider, Provider provider2, Provider provider3) {
        this.contextProvider = provider;
        this.browserFactoryProvider = provider2;
        this.loggerProvider = provider3;
    }

    public static ResumeMediaBrowserFactory_Factory create(Provider provider, Provider provider2, Provider provider3) {
        return new ResumeMediaBrowserFactory_Factory(provider, provider2, provider3);
    }

    public static ResumeMediaBrowserFactory newInstance(Context context, MediaBrowserFactory mediaBrowserFactory, ResumeMediaBrowserLogger resumeMediaBrowserLogger) {
        return new ResumeMediaBrowserFactory(context, mediaBrowserFactory, resumeMediaBrowserLogger);
    }

    @Override // javax.inject.Provider
    public ResumeMediaBrowserFactory get() {
        return newInstance((Context) this.contextProvider.get(), (MediaBrowserFactory) this.browserFactoryProvider.get(), (ResumeMediaBrowserLogger) this.loggerProvider.get());
    }
}
