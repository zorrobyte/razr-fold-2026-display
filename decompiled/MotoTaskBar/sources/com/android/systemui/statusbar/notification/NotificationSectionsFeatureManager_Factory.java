package com.android.systemui.statusbar.notification;

import android.content.Context;
import com.android.systemui.util.DeviceConfigProxy;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class NotificationSectionsFeatureManager_Factory implements Factory {
    private final Provider contextProvider;
    private final Provider proxyProvider;

    public NotificationSectionsFeatureManager_Factory(Provider provider, Provider provider2) {
        this.proxyProvider = provider;
        this.contextProvider = provider2;
    }

    public static NotificationSectionsFeatureManager_Factory create(Provider provider, Provider provider2) {
        return new NotificationSectionsFeatureManager_Factory(provider, provider2);
    }

    public static NotificationSectionsFeatureManager newInstance(DeviceConfigProxy deviceConfigProxy, Context context) {
        return new NotificationSectionsFeatureManager(deviceConfigProxy, context);
    }

    @Override // javax.inject.Provider
    public NotificationSectionsFeatureManager get() {
        return newInstance((DeviceConfigProxy) this.proxyProvider.get(), (Context) this.contextProvider.get());
    }
}
