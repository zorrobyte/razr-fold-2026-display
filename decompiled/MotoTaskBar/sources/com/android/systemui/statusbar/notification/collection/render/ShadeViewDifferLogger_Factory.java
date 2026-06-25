package com.android.systemui.statusbar.notification.collection.render;

import com.android.systemui.log.LogBuffer;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class ShadeViewDifferLogger_Factory implements Factory {
    private final Provider bufferProvider;

    public ShadeViewDifferLogger_Factory(Provider provider) {
        this.bufferProvider = provider;
    }

    public static ShadeViewDifferLogger_Factory create(Provider provider) {
        return new ShadeViewDifferLogger_Factory(provider);
    }

    public static ShadeViewDifferLogger newInstance(LogBuffer logBuffer) {
        return new ShadeViewDifferLogger(logBuffer);
    }

    @Override // javax.inject.Provider
    public ShadeViewDifferLogger get() {
        return newInstance((LogBuffer) this.bufferProvider.get());
    }
}
