package com.android.systemui.statusbar.notification.logging;

import com.android.systemui.statusbar.notification.logging.NotificationLogger;
import dagger.internal.Factory;
import dagger.internal.Provider;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes.dex */
public final class NotificationLogger_ExpansionStateLogger_Factory implements Factory {
    private final Provider uiBgExecutorProvider;

    public NotificationLogger_ExpansionStateLogger_Factory(Provider provider) {
        this.uiBgExecutorProvider = provider;
    }

    public static NotificationLogger_ExpansionStateLogger_Factory create(Provider provider) {
        return new NotificationLogger_ExpansionStateLogger_Factory(provider);
    }

    public static NotificationLogger.ExpansionStateLogger newInstance(Executor executor) {
        return new NotificationLogger.ExpansionStateLogger(executor);
    }

    @Override // javax.inject.Provider
    public NotificationLogger.ExpansionStateLogger get() {
        return newInstance((Executor) this.uiBgExecutorProvider.get());
    }
}
