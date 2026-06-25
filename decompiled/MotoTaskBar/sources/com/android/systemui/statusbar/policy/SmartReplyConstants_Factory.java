package com.android.systemui.statusbar.policy;

import android.content.Context;
import com.android.systemui.util.DeviceConfigProxy;
import dagger.internal.Factory;
import dagger.internal.Provider;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes.dex */
public final class SmartReplyConstants_Factory implements Factory {
    private final Provider contextProvider;
    private final Provider deviceConfigProvider;
    private final Provider mainExecutorProvider;

    public SmartReplyConstants_Factory(Provider provider, Provider provider2, Provider provider3) {
        this.mainExecutorProvider = provider;
        this.contextProvider = provider2;
        this.deviceConfigProvider = provider3;
    }

    public static SmartReplyConstants_Factory create(Provider provider, Provider provider2, Provider provider3) {
        return new SmartReplyConstants_Factory(provider, provider2, provider3);
    }

    public static SmartReplyConstants newInstance(Executor executor, Context context, DeviceConfigProxy deviceConfigProxy) {
        return new SmartReplyConstants(executor, context, deviceConfigProxy);
    }

    @Override // javax.inject.Provider
    public SmartReplyConstants get() {
        return newInstance((Executor) this.mainExecutorProvider.get(), (Context) this.contextProvider.get(), (DeviceConfigProxy) this.deviceConfigProvider.get());
    }
}
