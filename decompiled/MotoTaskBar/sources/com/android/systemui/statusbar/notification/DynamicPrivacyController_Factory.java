package com.android.systemui.statusbar.notification;

import dagger.internal.Factory;

/* JADX INFO: loaded from: classes.dex */
public final class DynamicPrivacyController_Factory implements Factory {

    abstract class InstanceHolder {
        static final DynamicPrivacyController_Factory INSTANCE = new DynamicPrivacyController_Factory();
    }

    public static DynamicPrivacyController_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static DynamicPrivacyController newInstance() {
        return new DynamicPrivacyController();
    }

    @Override // javax.inject.Provider
    public DynamicPrivacyController get() {
        return newInstance();
    }
}
