package com.android.systemui.statusbar.notification;

import android.content.Context;
import android.os.Handler;
import com.android.systemui.util.DeviceConfigProxy;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class AssistantFeedbackController_Factory implements Factory {
    private final Provider contextProvider;
    private final Provider handlerProvider;
    private final Provider proxyProvider;

    public AssistantFeedbackController_Factory(Provider provider, Provider provider2, Provider provider3) {
        this.handlerProvider = provider;
        this.contextProvider = provider2;
        this.proxyProvider = provider3;
    }

    public static AssistantFeedbackController_Factory create(Provider provider, Provider provider2, Provider provider3) {
        return new AssistantFeedbackController_Factory(provider, provider2, provider3);
    }

    public static AssistantFeedbackController newInstance(Handler handler, Context context, DeviceConfigProxy deviceConfigProxy) {
        return new AssistantFeedbackController(handler, context, deviceConfigProxy);
    }

    @Override // javax.inject.Provider
    public AssistantFeedbackController get() {
        return newInstance((Handler) this.handlerProvider.get(), (Context) this.contextProvider.get(), (DeviceConfigProxy) this.proxyProvider.get());
    }
}
