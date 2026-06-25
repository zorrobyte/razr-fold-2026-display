package com.android.systemui.statusbar.notification.collection.provider;

import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.Set;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: NotificationDismissibilityProviderImpl.kt */
/* JADX INFO: loaded from: classes.dex */
public final class NotificationDismissibilityProviderImpl implements NotificationDismissibilityProvider, Dumpable {
    public static final Companion Companion = new Companion(null);
    private volatile Set nonDismissableEntryKeys;

    /* JADX INFO: compiled from: NotificationDismissibilityProviderImpl.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public NotificationDismissibilityProviderImpl(DumpManager dumpManager) {
        dumpManager.getClass();
        dumpManager.registerNormalDumpable("NotificationDismissibilityProvider", this);
        this.nonDismissableEntryKeys = SetsKt.emptySet();
    }

    public static /* synthetic */ void getNonDismissableEntryKeys$annotations() {
    }

    @Override // com.android.systemui.statusbar.notification.collection.provider.NotificationDismissibilityProvider
    public boolean isDismissable(NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        return !this.nonDismissableEntryKeys.contains(notificationEntry.getKey());
    }

    public final synchronized void update(Set set) {
        set.getClass();
        this.nonDismissableEntryKeys = CollectionsKt.toSet(set);
    }
}
