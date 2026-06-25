package com.android.systemui.statusbar.notification.stack;

import com.android.systemui.dump.DumpManager;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class NotificationRoundnessManager_Factory implements Factory {
    private final Provider dumpManagerProvider;

    public NotificationRoundnessManager_Factory(Provider provider) {
        this.dumpManagerProvider = provider;
    }

    public static NotificationRoundnessManager_Factory create(Provider provider) {
        return new NotificationRoundnessManager_Factory(provider);
    }

    public static NotificationRoundnessManager newInstance(DumpManager dumpManager) {
        return new NotificationRoundnessManager(dumpManager);
    }

    @Override // javax.inject.Provider
    public NotificationRoundnessManager get() {
        return newInstance((DumpManager) this.dumpManagerProvider.get());
    }
}
