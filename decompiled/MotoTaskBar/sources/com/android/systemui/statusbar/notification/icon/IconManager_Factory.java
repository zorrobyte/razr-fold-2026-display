package com.android.systemui.statusbar.notification.icon;

import android.content.pm.LauncherApps;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import dagger.internal.Factory;
import dagger.internal.Provider;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: loaded from: classes.dex */
public final class IconManager_Factory implements Factory {
    private final Provider applicationCoroutineScopeProvider;
    private final Provider bgCoroutineContextProvider;
    private final Provider iconBuilderProvider;
    private final Provider launcherAppsProvider;
    private final Provider mainCoroutineContextProvider;
    private final Provider notifCollectionProvider;

    public IconManager_Factory(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6) {
        this.notifCollectionProvider = provider;
        this.launcherAppsProvider = provider2;
        this.iconBuilderProvider = provider3;
        this.applicationCoroutineScopeProvider = provider4;
        this.bgCoroutineContextProvider = provider5;
        this.mainCoroutineContextProvider = provider6;
    }

    public static IconManager_Factory create(Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6) {
        return new IconManager_Factory(provider, provider2, provider3, provider4, provider5, provider6);
    }

    public static IconManager newInstance(CommonNotifCollection commonNotifCollection, LauncherApps launcherApps, IconBuilder iconBuilder, CoroutineScope coroutineScope, CoroutineContext coroutineContext, CoroutineContext coroutineContext2) {
        return new IconManager(commonNotifCollection, launcherApps, iconBuilder, coroutineScope, coroutineContext, coroutineContext2);
    }

    @Override // javax.inject.Provider
    public IconManager get() {
        return newInstance((CommonNotifCollection) this.notifCollectionProvider.get(), (LauncherApps) this.launcherAppsProvider.get(), (IconBuilder) this.iconBuilderProvider.get(), (CoroutineScope) this.applicationCoroutineScopeProvider.get(), (CoroutineContext) this.bgCoroutineContextProvider.get(), (CoroutineContext) this.mainCoroutineContextProvider.get());
    }
}
