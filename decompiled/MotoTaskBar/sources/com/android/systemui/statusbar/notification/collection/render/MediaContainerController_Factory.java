package com.android.systemui.statusbar.notification.collection.render;

import android.view.LayoutInflater;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class MediaContainerController_Factory implements Factory {
    private final Provider layoutInflaterProvider;

    public MediaContainerController_Factory(Provider provider) {
        this.layoutInflaterProvider = provider;
    }

    public static MediaContainerController_Factory create(Provider provider) {
        return new MediaContainerController_Factory(provider);
    }

    public static MediaContainerController newInstance(LayoutInflater layoutInflater) {
        return new MediaContainerController(layoutInflater);
    }

    @Override // javax.inject.Provider
    public MediaContainerController get() {
        return newInstance((LayoutInflater) this.layoutInflaterProvider.get());
    }
}
