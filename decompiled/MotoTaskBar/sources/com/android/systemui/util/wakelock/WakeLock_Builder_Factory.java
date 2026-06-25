package com.android.systemui.util.wakelock;

import android.content.Context;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class WakeLock_Builder_Factory implements Factory {
    private final Provider contextProvider;
    private final Provider loggerProvider;

    public WakeLock_Builder_Factory(Provider provider, Provider provider2) {
        this.contextProvider = provider;
        this.loggerProvider = provider2;
    }

    public static WakeLock_Builder_Factory create(Provider provider, Provider provider2) {
        return new WakeLock_Builder_Factory(provider, provider2);
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.systemui.util.wakelock.WakeLock$Builder] */
    public static WakeLock$Builder newInstance(final Context context, final WakeLockLogger wakeLockLogger) {
        return new Object(context, wakeLockLogger) { // from class: com.android.systemui.util.wakelock.WakeLock$Builder
            private final Context mContext;
            private final WakeLockLogger mLogger;
            private int mLevelsAndFlags = 1;
            private long mMaxTimeout = 20000;

            {
                this.mContext = context;
                this.mLogger = wakeLockLogger;
            }
        };
    }

    @Override // javax.inject.Provider
    public WakeLock$Builder get() {
        return newInstance((Context) this.contextProvider.get(), (WakeLockLogger) this.loggerProvider.get());
    }
}
