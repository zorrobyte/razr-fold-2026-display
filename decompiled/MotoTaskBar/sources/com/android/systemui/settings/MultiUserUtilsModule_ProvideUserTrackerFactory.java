package com.android.systemui.settings;

import android.app.IActivityManager;
import android.content.Context;
import android.os.Handler;
import android.os.UserManager;
import com.android.systemui.dump.DumpManager;
import dagger.internal.Factory;
import dagger.internal.Provider;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: loaded from: classes.dex */
public final class MultiUserUtilsModule_ProvideUserTrackerFactory implements Factory {
    private final Provider appScopeProvider;
    private final Provider backgroundDispatcherProvider;
    private final Provider contextProvider;
    private final Provider dumpManagerProvider;
    private final Provider handlerProvider;
    private final Provider iActivityManagerProvider;
    private final Provider userManagerProvider;

    public MultiUserUtilsModule_ProvideUserTrackerFactory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7) {
        this.contextProvider = provider;
        this.userManagerProvider = provider2;
        this.iActivityManagerProvider = provider3;
        this.dumpManagerProvider = provider4;
        this.appScopeProvider = provider5;
        this.backgroundDispatcherProvider = provider6;
        this.handlerProvider = provider7;
    }

    public static MultiUserUtilsModule_ProvideUserTrackerFactory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7) {
        return new MultiUserUtilsModule_ProvideUserTrackerFactory(provider, provider2, provider3, provider4, provider5, provider6, provider7);
    }

    public static UserTracker provideUserTracker(Context context, UserManager userManager, IActivityManager iActivityManager, DumpManager dumpManager, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, Handler handler) {
        UserTracker userTrackerProvideUserTracker = MultiUserUtilsModule.provideUserTracker(context, userManager, iActivityManager, dumpManager, coroutineScope, coroutineDispatcher, handler);
        userTrackerProvideUserTracker.getClass();
        return userTrackerProvideUserTracker;
    }

    @Override // javax.inject.Provider
    public UserTracker get() {
        return provideUserTracker((Context) this.contextProvider.get(), (UserManager) this.userManagerProvider.get(), (IActivityManager) this.iActivityManagerProvider.get(), (DumpManager) this.dumpManagerProvider.get(), (CoroutineScope) this.appScopeProvider.get(), (CoroutineDispatcher) this.backgroundDispatcherProvider.get(), (Handler) this.handlerProvider.get());
    }
}
