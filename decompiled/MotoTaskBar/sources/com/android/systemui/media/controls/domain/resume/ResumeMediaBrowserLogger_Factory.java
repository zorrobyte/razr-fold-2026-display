package com.android.systemui.media.controls.domain.resume;

import com.android.systemui.log.LogBuffer;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class ResumeMediaBrowserLogger_Factory implements Factory {
    private final Provider bufferProvider;

    public ResumeMediaBrowserLogger_Factory(Provider provider) {
        this.bufferProvider = provider;
    }

    public static ResumeMediaBrowserLogger_Factory create(Provider provider) {
        return new ResumeMediaBrowserLogger_Factory(provider);
    }

    public static ResumeMediaBrowserLogger newInstance(LogBuffer logBuffer) {
        return new ResumeMediaBrowserLogger(logBuffer);
    }

    @Override // javax.inject.Provider
    public ResumeMediaBrowserLogger get() {
        return newInstance((LogBuffer) this.bufferProvider.get());
    }
}
