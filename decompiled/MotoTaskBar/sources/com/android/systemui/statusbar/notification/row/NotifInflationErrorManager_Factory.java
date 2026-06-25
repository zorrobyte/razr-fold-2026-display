package com.android.systemui.statusbar.notification.row;

import dagger.internal.Factory;

/* JADX INFO: loaded from: classes.dex */
public final class NotifInflationErrorManager_Factory implements Factory {

    abstract class InstanceHolder {
        static final NotifInflationErrorManager_Factory INSTANCE = new NotifInflationErrorManager_Factory();
    }

    public static NotifInflationErrorManager_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static NotifInflationErrorManager newInstance() {
        return new NotifInflationErrorManager();
    }

    @Override // javax.inject.Provider
    public NotifInflationErrorManager get() {
        return newInstance();
    }
}
