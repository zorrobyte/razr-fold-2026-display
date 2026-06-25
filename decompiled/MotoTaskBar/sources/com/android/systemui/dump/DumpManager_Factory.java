package com.android.systemui.dump;

import dagger.internal.Factory;

/* JADX INFO: loaded from: classes.dex */
public final class DumpManager_Factory implements Factory {

    abstract class InstanceHolder {
        static final DumpManager_Factory INSTANCE = new DumpManager_Factory();
    }

    public static DumpManager_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static DumpManager newInstance() {
        return new DumpManager();
    }

    @Override // javax.inject.Provider
    public DumpManager get() {
        return newInstance();
    }
}
