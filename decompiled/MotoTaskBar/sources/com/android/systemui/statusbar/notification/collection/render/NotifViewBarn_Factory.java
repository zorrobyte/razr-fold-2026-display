package com.android.systemui.statusbar.notification.collection.render;

import dagger.internal.Factory;

/* JADX INFO: loaded from: classes.dex */
public final class NotifViewBarn_Factory implements Factory {

    abstract class InstanceHolder {
        static final NotifViewBarn_Factory INSTANCE = new NotifViewBarn_Factory();
    }

    public static NotifViewBarn_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static NotifViewBarn newInstance() {
        return new NotifViewBarn();
    }

    @Override // javax.inject.Provider
    public NotifViewBarn get() {
        return newInstance();
    }
}
