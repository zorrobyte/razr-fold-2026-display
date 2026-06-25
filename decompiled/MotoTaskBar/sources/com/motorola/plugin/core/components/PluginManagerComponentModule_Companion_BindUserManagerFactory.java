package com.motorola.plugin.core.components;

import android.content.Context;
import android.os.UserManager;
import dagger.internal.Factory;
import javax.inject.Provider;

/* JADX INFO: loaded from: classes2.dex */
public final class PluginManagerComponentModule_Companion_BindUserManagerFactory implements Factory {
    private final Provider contextProvider;

    public PluginManagerComponentModule_Companion_BindUserManagerFactory(Provider provider) {
        this.contextProvider = provider;
    }

    public static UserManager bindUserManager(Context context) {
        UserManager userManagerBindUserManager = PluginManagerComponentModule.Companion.bindUserManager(context);
        userManagerBindUserManager.getClass();
        return userManagerBindUserManager;
    }

    public static PluginManagerComponentModule_Companion_BindUserManagerFactory create(Provider provider) {
        return new PluginManagerComponentModule_Companion_BindUserManagerFactory(provider);
    }

    @Override // javax.inject.Provider
    public UserManager get() {
        return bindUserManager((Context) this.contextProvider.get());
    }
}
