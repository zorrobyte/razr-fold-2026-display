package com.android.systemui.statusbar.notification;

import dagger.internal.Factory;

/* JADX INFO: loaded from: classes.dex */
public final class NotifPipelineFlags_Factory implements Factory {

    abstract class InstanceHolder {
        static final NotifPipelineFlags_Factory INSTANCE = new NotifPipelineFlags_Factory();
    }

    public static NotifPipelineFlags_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static NotifPipelineFlags newInstance() {
        return new NotifPipelineFlags();
    }

    @Override // javax.inject.Provider
    public NotifPipelineFlags get() {
        return newInstance();
    }
}
