package com.android.systemui.qs.tileimpl;

import dagger.internal.Factory;
import dagger.internal.Provider;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class QSFactoryImpl_Factory implements Factory {
    private final Provider tileMapProvider;

    public QSFactoryImpl_Factory(Provider provider) {
        this.tileMapProvider = provider;
    }

    public static QSFactoryImpl_Factory create(Provider provider) {
        return new QSFactoryImpl_Factory(provider);
    }

    public static QSFactoryImpl newInstance(Map map) {
        return new QSFactoryImpl(map);
    }

    @Override // javax.inject.Provider
    public QSFactoryImpl get() {
        return newInstance((Map) this.tileMapProvider.get());
    }
}
