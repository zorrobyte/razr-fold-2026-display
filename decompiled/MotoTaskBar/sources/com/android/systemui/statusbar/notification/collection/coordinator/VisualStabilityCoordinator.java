package com.android.systemui.statusbar.notification.collection.coordinator;

import android.util.Log;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.notification.VisibilityLocationProvider;
import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifStabilityManager;
import com.android.systemui.statusbar.notification.collection.provider.VisualStabilityProvider;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.android.systemui.util.Compile;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.kotlin.JavaAdapter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class VisualStabilityCoordinator implements Coordinator, Dumpable {
    protected static final long ALLOW_SECTION_CHANGE_TIMEOUT = 500;
    public static final boolean DEBUG;
    private final DelayableExecutor mDelayableExecutor;
    private boolean mFullyDozed;
    private final HeadsUpManager mHeadsUpManager;
    private final JavaAdapter mJavaAdapter;
    private boolean mPulsing;
    private boolean mReorderingAllowed;
    private final VisibilityLocationProvider mVisibilityLocationProvider;
    private final VisualStabilityProvider mVisualStabilityProvider;
    private boolean mCommunalShowing = false;
    private boolean mPipelineRunAllowed = true;
    private boolean mIsSuppressingPipelineRun = false;
    private boolean mIsSuppressingGroupChange = false;
    private final Set mEntriesWithSuppressedSectionChange = new HashSet();
    private boolean mIsSuppressingEntryReorder = false;
    private Map mEntriesThatCanChangeSection = new HashMap();
    private final NotifStabilityManager mNotifStabilityManager = new NotifStabilityManager("VisualStabilityCoordinator") { // from class: com.android.systemui.statusbar.notification.collection.coordinator.VisualStabilityCoordinator.1
        private boolean canMoveForHeadsUp(NotificationEntry notificationEntry) {
            return (notificationEntry == null || !VisualStabilityCoordinator.this.mHeadsUpManager.isHeadsUpEntry(notificationEntry.getKey()) || VisualStabilityCoordinator.this.mVisibilityLocationProvider.isInVisibleLocation(notificationEntry)) ? false : true;
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifStabilityManager
        public boolean isEntryReorderingAllowed(ListEntry listEntry) {
            return VisualStabilityCoordinator.this.mReorderingAllowed || canMoveForHeadsUp(listEntry.getRepresentativeEntry());
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifStabilityManager
        public boolean isEveryChangeAllowed() {
            return VisualStabilityCoordinator.this.mReorderingAllowed;
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifStabilityManager
        public boolean isGroupChangeAllowed(NotificationEntry notificationEntry) {
            boolean z = VisualStabilityCoordinator.this.mReorderingAllowed || canMoveForHeadsUp(notificationEntry);
            VisualStabilityCoordinator.this.mIsSuppressingGroupChange |= !z;
            return z;
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifStabilityManager
        public boolean isGroupPruneAllowed(GroupEntry groupEntry) {
            boolean z = VisualStabilityCoordinator.this.mReorderingAllowed;
            VisualStabilityCoordinator.this.mIsSuppressingGroupChange |= !z;
            return z;
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifStabilityManager
        public boolean isPipelineRunAllowed() {
            VisualStabilityCoordinator.this.mIsSuppressingPipelineRun |= !VisualStabilityCoordinator.this.mPipelineRunAllowed;
            return VisualStabilityCoordinator.this.mPipelineRunAllowed;
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifStabilityManager
        public boolean isSectionChangeAllowed(NotificationEntry notificationEntry) {
            boolean z = VisualStabilityCoordinator.this.mReorderingAllowed || canMoveForHeadsUp(notificationEntry) || VisualStabilityCoordinator.this.mEntriesThatCanChangeSection.containsKey(notificationEntry.getKey());
            if (!z) {
                VisualStabilityCoordinator.this.mEntriesWithSuppressedSectionChange.add(notificationEntry.getKey());
            }
            return z;
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifStabilityManager
        public void onBeginRun() {
            VisualStabilityCoordinator.this.mIsSuppressingPipelineRun = false;
            VisualStabilityCoordinator.this.mIsSuppressingGroupChange = false;
            VisualStabilityCoordinator.this.mEntriesWithSuppressedSectionChange.clear();
            VisualStabilityCoordinator.this.mIsSuppressingEntryReorder = false;
        }

        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifStabilityManager
        public void onEntryReorderSuppressed() {
            VisualStabilityCoordinator.this.mIsSuppressingEntryReorder = true;
        }
    };

    static {
        DEBUG = Compile.IS_DEBUG && Log.isLoggable("VisualStability", 2);
    }

    public VisualStabilityCoordinator(DelayableExecutor delayableExecutor, DumpManager dumpManager, HeadsUpManager headsUpManager, JavaAdapter javaAdapter, VisibilityLocationProvider visibilityLocationProvider, VisualStabilityProvider visualStabilityProvider) {
        this.mHeadsUpManager = headsUpManager;
        this.mJavaAdapter = javaAdapter;
        this.mVisibilityLocationProvider = visibilityLocationProvider;
        this.mVisualStabilityProvider = visualStabilityProvider;
        this.mDelayableExecutor = delayableExecutor;
        dumpManager.registerDumpable(this);
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        this.mFullyDozed = false;
        this.mPulsing = false;
        notifPipeline.setVisualStabilityManager(this.mNotifStabilityManager);
    }
}
