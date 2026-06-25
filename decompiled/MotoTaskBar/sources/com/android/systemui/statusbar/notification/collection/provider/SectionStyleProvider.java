package com.android.systemui.statusbar.notification.collection.provider;

import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.listbuilder.NotifSection;
import java.util.Collection;
import java.util.Set;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: SectionStyleProvider.kt */
/* JADX INFO: loaded from: classes.dex */
public final class SectionStyleProvider {
    private final HighPriorityProvider highPriorityProvider;
    private Set lowPrioritySections;
    private Set silentSections;

    public SectionStyleProvider(HighPriorityProvider highPriorityProvider) {
        highPriorityProvider.getClass();
        this.highPriorityProvider = highPriorityProvider;
    }

    public static /* synthetic */ boolean isMinimized$default(SectionStyleProvider sectionStyleProvider, ListEntry listEntry, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = true;
        }
        return sectionStyleProvider.isMinimized(listEntry, z);
    }

    public static /* synthetic */ boolean isSilent$default(SectionStyleProvider sectionStyleProvider, ListEntry listEntry, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = true;
        }
        return sectionStyleProvider.isSilent(listEntry, z);
    }

    public final boolean isMinimized(ListEntry listEntry, boolean z) {
        listEntry.getClass();
        NotifSection section = listEntry.getSection();
        return section == null ? z : isMinimizedSection(section);
    }

    public final boolean isMinimizedSection(NotifSection notifSection) {
        notifSection.getClass();
        Set set = this.lowPrioritySections;
        if (set == null) {
            Intrinsics.throwUninitializedPropertyAccessException("lowPrioritySections");
            set = null;
        }
        return set.contains(notifSection.getSectioner());
    }

    public final boolean isSilent(ListEntry listEntry, boolean z) {
        listEntry.getClass();
        NotifSection section = listEntry.getSection();
        if (section == null) {
            return z;
        }
        NotifSection section2 = listEntry.getSection();
        return (section2 == null || section2.getBucket() != 4) ? isSilentSection(section) : !this.highPriorityProvider.isHighPriorityConversation(listEntry);
    }

    public final boolean isSilentSection(NotifSection notifSection) {
        notifSection.getClass();
        Set set = this.silentSections;
        if (set == null) {
            Intrinsics.throwUninitializedPropertyAccessException("silentSections");
            set = null;
        }
        return set.contains(notifSection.getSectioner());
    }

    public final void setMinimizedSections(Collection collection) {
        collection.getClass();
        this.lowPrioritySections = CollectionsKt.toSet(collection);
    }

    public final void setSilentSections(Collection collection) {
        collection.getClass();
        this.silentSections = CollectionsKt.toSet(collection);
    }
}
