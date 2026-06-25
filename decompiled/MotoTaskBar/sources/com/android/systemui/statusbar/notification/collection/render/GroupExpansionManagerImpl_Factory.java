package com.android.systemui.statusbar.notification.collection.render;

import com.android.systemui.dump.DumpManager;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class GroupExpansionManagerImpl_Factory implements Factory {
    private final Provider dumpManagerProvider;
    private final Provider groupMembershipManagerProvider;

    public GroupExpansionManagerImpl_Factory(Provider provider, Provider provider2) {
        this.dumpManagerProvider = provider;
        this.groupMembershipManagerProvider = provider2;
    }

    public static GroupExpansionManagerImpl_Factory create(Provider provider, Provider provider2) {
        return new GroupExpansionManagerImpl_Factory(provider, provider2);
    }

    public static GroupExpansionManagerImpl newInstance(DumpManager dumpManager, GroupMembershipManager groupMembershipManager) {
        return new GroupExpansionManagerImpl(dumpManager, groupMembershipManager);
    }

    @Override // javax.inject.Provider
    public GroupExpansionManagerImpl get() {
        return newInstance((DumpManager) this.dumpManagerProvider.get(), (GroupMembershipManager) this.groupMembershipManagerProvider.get());
    }
}
