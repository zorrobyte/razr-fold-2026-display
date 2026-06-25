package com.motorola.taskbar.shortcut.record;

import android.content.Context;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes2.dex */
public final class RecordManager_Factory implements Factory {
    private final Provider contextProvider;

    public RecordManager_Factory(Provider provider) {
        this.contextProvider = provider;
    }

    public static RecordManager_Factory create(Provider provider) {
        return new RecordManager_Factory(provider);
    }

    public static RecordManager newInstance(Context context) {
        return new RecordManager(context);
    }

    @Override // javax.inject.Provider
    public RecordManager get() {
        return newInstance((Context) this.contextProvider.get());
    }
}
