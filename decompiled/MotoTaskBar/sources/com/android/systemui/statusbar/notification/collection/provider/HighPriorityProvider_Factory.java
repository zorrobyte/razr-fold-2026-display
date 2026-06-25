package com.android.systemui.statusbar.notification.collection.provider;

import com.android.systemui.statusbar.notification.collection.render.GroupMembershipManager;
import com.android.systemui.statusbar.notification.people.PeopleNotificationIdentifier;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class HighPriorityProvider_Factory implements Factory {
    private final Provider groupManagerProvider;
    private final Provider peopleNotificationIdentifierProvider;

    public HighPriorityProvider_Factory(Provider provider, Provider provider2) {
        this.peopleNotificationIdentifierProvider = provider;
        this.groupManagerProvider = provider2;
    }

    public static HighPriorityProvider_Factory create(Provider provider, Provider provider2) {
        return new HighPriorityProvider_Factory(provider, provider2);
    }

    public static HighPriorityProvider newInstance(PeopleNotificationIdentifier peopleNotificationIdentifier, GroupMembershipManager groupMembershipManager) {
        return new HighPriorityProvider(peopleNotificationIdentifier, groupMembershipManager);
    }

    @Override // javax.inject.Provider
    public HighPriorityProvider get() {
        return newInstance((PeopleNotificationIdentifier) this.peopleNotificationIdentifierProvider.get(), (GroupMembershipManager) this.groupManagerProvider.get());
    }
}
