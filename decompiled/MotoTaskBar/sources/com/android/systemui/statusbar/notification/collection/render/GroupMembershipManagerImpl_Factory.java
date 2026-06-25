package com.android.systemui.statusbar.notification.collection.render;

import dagger.internal.Factory;

/* JADX INFO: loaded from: classes.dex */
public final class GroupMembershipManagerImpl_Factory implements Factory {

    abstract class InstanceHolder {
        static final GroupMembershipManagerImpl_Factory INSTANCE = new GroupMembershipManagerImpl_Factory();
    }

    public static GroupMembershipManagerImpl_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static GroupMembershipManagerImpl newInstance() {
        return new GroupMembershipManagerImpl();
    }

    @Override // javax.inject.Provider
    public GroupMembershipManagerImpl get() {
        return newInstance();
    }
}
