package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner;
import com.android.systemui.statusbar.notification.collection.provider.HighPriorityProvider;
import com.android.systemui.statusbar.notification.collection.render.NodeController;
import com.android.systemui.statusbar.notification.collection.render.SectionHeaderController;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class RankingCoordinator implements Coordinator {
    private final NodeController mAlertingHeaderController;
    private boolean mHasMinimizedEntries;
    private boolean mHasSilentEntries;
    private final HighPriorityProvider mHighPriorityProvider;
    private final NotifSectioner mMinimizedNotifSectioner;
    private final SectionHeaderController mSilentHeaderController;
    private final NodeController mSilentNodeController;
    private final NotifSectioner mSilentNotifSectioner;
    private final NotifSectioner mAlertingNotifSectioner = new NotifSectioner("Alerting", 5) { // from class: com.android.systemui.statusbar.notification.collection.coordinator.RankingCoordinator.1
        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        public NodeController getHeaderNodeController() {
            return null;
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        public boolean isInSection(ListEntry listEntry) {
            return RankingCoordinator.this.mHighPriorityProvider.isHighPriority(listEntry);
        }
    };
    private final NotifFilter mSuspendedFilter = new NotifFilter(this, "IsSuspendedFilter") { // from class: com.android.systemui.statusbar.notification.collection.coordinator.RankingCoordinator.4
        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter
        public boolean shouldFilterOut(NotificationEntry notificationEntry, long j) {
            return notificationEntry.getRanking().isSuspended();
        }
    };
    private final NotifFilter mDndVisualEffectsFilter = new NotifFilter(this, "DndSuppressingVisualEffects") { // from class: com.android.systemui.statusbar.notification.collection.coordinator.RankingCoordinator.5
        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter
        public boolean shouldFilterOut(NotificationEntry notificationEntry, long j) {
            return notificationEntry.shouldSuppressNotificationList();
        }
    };

    public RankingCoordinator(HighPriorityProvider highPriorityProvider, NodeController nodeController, SectionHeaderController sectionHeaderController, NodeController nodeController2) {
        int i = 6;
        this.mSilentNotifSectioner = new NotifSectioner("Silent", i) { // from class: com.android.systemui.statusbar.notification.collection.coordinator.RankingCoordinator.2
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
            public NodeController getHeaderNodeController() {
                return RankingCoordinator.this.mSilentNodeController;
            }

            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
            public boolean isInSection(ListEntry listEntry) {
                return (RankingCoordinator.this.mHighPriorityProvider.isHighPriority(listEntry) || listEntry.getRepresentativeEntry().isAmbient()) ? false : true;
            }

            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
            public void onEntriesUpdated(List list) {
                int i2 = 0;
                RankingCoordinator.this.mHasSilentEntries = false;
                while (true) {
                    if (i2 >= list.size()) {
                        break;
                    }
                    if (((ListEntry) list.get(i2)).getRepresentativeEntry().getSbn().isClearable()) {
                        RankingCoordinator.this.mHasSilentEntries = true;
                        break;
                    }
                    i2++;
                }
                RankingCoordinator.this.mSilentHeaderController.setClearSectionEnabled(RankingCoordinator.this.mHasMinimizedEntries | RankingCoordinator.this.mHasSilentEntries);
            }
        };
        this.mMinimizedNotifSectioner = new NotifSectioner("Minimized", i) { // from class: com.android.systemui.statusbar.notification.collection.coordinator.RankingCoordinator.3
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
            public NodeController getHeaderNodeController() {
                return RankingCoordinator.this.mSilentNodeController;
            }

            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
            public boolean isInSection(ListEntry listEntry) {
                return !RankingCoordinator.this.mHighPriorityProvider.isHighPriority(listEntry) && listEntry.getRepresentativeEntry().isAmbient();
            }

            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
            public void onEntriesUpdated(List list) {
                int i2 = 0;
                RankingCoordinator.this.mHasMinimizedEntries = false;
                while (true) {
                    if (i2 >= list.size()) {
                        break;
                    }
                    if (((ListEntry) list.get(i2)).getRepresentativeEntry().getSbn().isClearable()) {
                        RankingCoordinator.this.mHasMinimizedEntries = true;
                        break;
                    }
                    i2++;
                }
                RankingCoordinator.this.mSilentHeaderController.setClearSectionEnabled(RankingCoordinator.this.mHasMinimizedEntries | RankingCoordinator.this.mHasSilentEntries);
            }
        };
        this.mHighPriorityProvider = highPriorityProvider;
        this.mAlertingHeaderController = nodeController;
        this.mSilentNodeController = nodeController2;
        this.mSilentHeaderController = sectionHeaderController;
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        notifPipeline.addPreGroupFilter(this.mSuspendedFilter);
        notifPipeline.addPreGroupFilter(this.mDndVisualEffectsFilter);
    }

    public NotifSectioner getAlertingSectioner() {
        return this.mAlertingNotifSectioner;
    }

    public NotifSectioner getMinimizedSectioner() {
        return this.mMinimizedNotifSectioner;
    }

    public NotifSectioner getSilentSectioner() {
        return this.mSilentNotifSectioner;
    }
}
