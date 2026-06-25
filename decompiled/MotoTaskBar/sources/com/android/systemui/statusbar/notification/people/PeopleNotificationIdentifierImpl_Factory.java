package com.android.systemui.statusbar.notification.people;

import com.android.systemui.statusbar.notification.collection.render.GroupMembershipManager;
import dagger.internal.Factory;
import dagger.internal.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class PeopleNotificationIdentifierImpl_Factory implements Factory {
    private final Provider groupManagerProvider;
    private final Provider personExtractorProvider;

    public PeopleNotificationIdentifierImpl_Factory(Provider provider, Provider provider2) {
        this.personExtractorProvider = provider;
        this.groupManagerProvider = provider2;
    }

    public static PeopleNotificationIdentifierImpl_Factory create(Provider provider, Provider provider2) {
        return new PeopleNotificationIdentifierImpl_Factory(provider, provider2);
    }

    public static PeopleNotificationIdentifierImpl newInstance(NotificationPersonExtractor notificationPersonExtractor, GroupMembershipManager groupMembershipManager) {
        return new PeopleNotificationIdentifierImpl(notificationPersonExtractor, groupMembershipManager);
    }

    @Override // javax.inject.Provider
    public PeopleNotificationIdentifierImpl get() {
        return newInstance((NotificationPersonExtractor) this.personExtractorProvider.get(), (GroupMembershipManager) this.groupManagerProvider.get());
    }
}
