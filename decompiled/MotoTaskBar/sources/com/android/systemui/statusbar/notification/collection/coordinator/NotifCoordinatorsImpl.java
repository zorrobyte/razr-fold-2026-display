package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.Flags;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner;
import com.android.systemui.statusbar.notification.collection.provider.SectionStyleProvider;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: NotifCoordinators.kt */
/* JADX INFO: loaded from: classes.dex */
public final class NotifCoordinatorsImpl implements NotifCoordinators {
    public static final Companion Companion = new Companion(null);
    private final List mCoordinators;
    private final List mCoreCoordinators;
    private final List mOrderedSections;

    /* JADX INFO: compiled from: NotifCoordinators.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public NotifCoordinatorsImpl(SectionStyleProvider sectionStyleProvider, DataStoreCoordinator dataStoreCoordinator, HideLocallyDismissedNotifsCoordinator hideLocallyDismissedNotifsCoordinator, HideNotifsForOtherUsersCoordinator hideNotifsForOtherUsersCoordinator, RankingCoordinator rankingCoordinator, ColorizedFgsCoordinator colorizedFgsCoordinator, DeviceProvisionedCoordinator deviceProvisionedCoordinator, HeadsUpCoordinator headsUpCoordinator, ConversationCoordinator conversationCoordinator, GroupCountCoordinator groupCountCoordinator, GroupWhenCoordinator groupWhenCoordinator, MediaCoordinator mediaCoordinator, PreparationCoordinator preparationCoordinator, RemoteInputCoordinator remoteInputCoordinator, RowAlertTimeCoordinator rowAlertTimeCoordinator, RowAppearanceCoordinator rowAppearanceCoordinator, StackCoordinator stackCoordinator, ShadeEventCoordinator shadeEventCoordinator, ViewConfigCoordinator viewConfigCoordinator, VisualStabilityCoordinator visualStabilityCoordinator, SensitiveContentCoordinator sensitiveContentCoordinator, DismissibilityCoordinator dismissibilityCoordinator, NotificationStatsLoggerCoordinator notificationStatsLoggerCoordinator) {
        sectionStyleProvider.getClass();
        dataStoreCoordinator.getClass();
        hideLocallyDismissedNotifsCoordinator.getClass();
        hideNotifsForOtherUsersCoordinator.getClass();
        rankingCoordinator.getClass();
        colorizedFgsCoordinator.getClass();
        deviceProvisionedCoordinator.getClass();
        headsUpCoordinator.getClass();
        conversationCoordinator.getClass();
        groupCountCoordinator.getClass();
        groupWhenCoordinator.getClass();
        mediaCoordinator.getClass();
        preparationCoordinator.getClass();
        remoteInputCoordinator.getClass();
        rowAlertTimeCoordinator.getClass();
        rowAppearanceCoordinator.getClass();
        stackCoordinator.getClass();
        shadeEventCoordinator.getClass();
        viewConfigCoordinator.getClass();
        visualStabilityCoordinator.getClass();
        sensitiveContentCoordinator.getClass();
        dismissibilityCoordinator.getClass();
        notificationStatsLoggerCoordinator.getClass();
        ArrayList arrayList = new ArrayList();
        this.mCoreCoordinators = arrayList;
        ArrayList arrayList2 = new ArrayList();
        this.mCoordinators = arrayList2;
        ArrayList arrayList3 = new ArrayList();
        this.mOrderedSections = arrayList3;
        arrayList.add(dataStoreCoordinator);
        arrayList2.add(hideLocallyDismissedNotifsCoordinator);
        arrayList2.add(hideNotifsForOtherUsersCoordinator);
        arrayList2.add(rankingCoordinator);
        arrayList2.add(colorizedFgsCoordinator);
        arrayList2.add(deviceProvisionedCoordinator);
        arrayList2.add(conversationCoordinator);
        arrayList2.add(groupCountCoordinator);
        arrayList2.add(groupWhenCoordinator);
        arrayList2.add(mediaCoordinator);
        arrayList2.add(rowAlertTimeCoordinator);
        arrayList2.add(rowAppearanceCoordinator);
        arrayList2.add(stackCoordinator);
        arrayList2.add(shadeEventCoordinator);
        arrayList2.add(viewConfigCoordinator);
        arrayList2.add(visualStabilityCoordinator);
        arrayList2.add(sensitiveContentCoordinator);
        arrayList2.add(headsUpCoordinator);
        arrayList2.add(preparationCoordinator);
        arrayList2.add(remoteInputCoordinator);
        arrayList2.add(dismissibilityCoordinator);
        if (Flags.notificationsLiveDataStoreRefactor()) {
            arrayList2.add(notificationStatsLoggerCoordinator);
        }
        NotifSectioner sectioner = colorizedFgsCoordinator.getSectioner();
        sectioner.getClass();
        arrayList3.add(sectioner);
        arrayList3.add(conversationCoordinator.getPeopleAlertingSectioner());
        NotifSectioner alertingSectioner = rankingCoordinator.getAlertingSectioner();
        alertingSectioner.getClass();
        arrayList3.add(alertingSectioner);
        NotifSectioner silentSectioner = rankingCoordinator.getSilentSectioner();
        silentSectioner.getClass();
        arrayList3.add(silentSectioner);
        NotifSectioner minimizedSectioner = rankingCoordinator.getMinimizedSectioner();
        minimizedSectioner.getClass();
        arrayList3.add(minimizedSectioner);
        sectionStyleProvider.setMinimizedSections(SetsKt.setOf(rankingCoordinator.getMinimizedSectioner()));
        sectionStyleProvider.setSilentSections(CollectionsKt.listOf((Object[]) new NotifSectioner[]{rankingCoordinator.getSilentSectioner(), rankingCoordinator.getMinimizedSectioner()}));
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        notifPipeline.getClass();
        Iterator it = this.mCoreCoordinators.iterator();
        while (it.hasNext()) {
            ((CoreCoordinator) it.next()).attach(notifPipeline);
        }
        Iterator it2 = this.mCoordinators.iterator();
        while (it2.hasNext()) {
            ((Coordinator) it2.next()).attach(notifPipeline);
        }
        notifPipeline.setSections(this.mOrderedSections);
    }
}
