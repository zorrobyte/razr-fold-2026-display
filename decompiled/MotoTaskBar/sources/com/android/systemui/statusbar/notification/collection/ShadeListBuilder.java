package com.android.systemui.statusbar.notification.collection;

import android.os.Trace;
import android.service.notification.StatusBarNotification;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0;
import com.android.internal.util.Preconditions;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.NotificationInteractionTracker;
import com.android.systemui.statusbar.notification.NotifPipelineFlags;
import com.android.systemui.statusbar.notification.collection.listbuilder.NotifSection;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeFinalizeFilterListener;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeRenderListListener;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeSortListener;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeTransformGroupsListener;
import com.android.systemui.statusbar.notification.collection.listbuilder.PipelineState;
import com.android.systemui.statusbar.notification.collection.listbuilder.SemiStableSort;
import com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderHelper;
import com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderLogger;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.DefaultNotifStabilityManager;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.Invalidator;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifComparator;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifPromoter;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifStabilityManager;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.Pluggable;
import com.android.systemui.statusbar.notification.collection.notifcollection.CollectionReadyForBuildListener;
import com.android.systemui.util.Assert;
import com.android.systemui.util.NamedListenerSet;
import com.android.systemui.util.time.SystemClock;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* JADX INFO: loaded from: classes.dex */
public class ShadeListBuilder implements Dumpable {
    private static final NotifSectioner DEFAULT_SECTIONER = new NotifSectioner("UnknownSection", 0) { // from class: com.android.systemui.statusbar.notification.collection.ShadeListBuilder.2
        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
        public boolean isInSection(ListEntry listEntry) {
            return true;
        }
    };
    public static final int MAX_CONSECUTIVE_REENTRANT_REBUILDS = 3;
    private Collection mAllEntries;
    private final boolean mAlwaysLogList;
    private final NotifPipelineChoreographer mChoreographer;
    private int mConsecutiveReentrantRebuilds;
    private final DumpManager mDumpManager;
    private NotifPipelineFlags mFlags;
    private final Comparator mGroupChildrenComparator;
    private final NotificationInteractionTracker mInteractionTracker;
    private int mIterationCount;
    private final ShadeListBuilderLogger mLogger;
    private final List mNotifComparators;
    private final List mNotifFinalizeFilters;
    private final List mNotifPreGroupFilters;
    private final List mNotifPromoters;
    private final List mNotifSections;
    private NotifStabilityManager mNotifStabilityManager;
    private final NamedListenerSet mOnBeforeFinalizeFilterListeners;
    private final NamedListenerSet mOnBeforeRenderListListeners;
    private final NamedListenerSet mOnBeforeSortListeners;
    private final NamedListenerSet mOnBeforeTransformGroupsListeners;
    private OnRenderListListener mOnRenderListListener;
    private Collection mPendingEntries;
    private List mReadOnlyNewNotifList;
    private List mReadOnlyNotifList;
    private final CollectionReadyForBuildListener mReadyForBuildListener;
    private final SystemClock mSystemClock;
    private final Comparator mTopLevelComparator;
    private final ArrayList mTempSectionMembers = new ArrayList();
    private List mNotifList = new ArrayList();
    private List mNewNotifList = new ArrayList();
    private final SemiStableSort mSemiStableSort = new SemiStableSort();
    private final SemiStableSort.StableOrder mStableOrder = new SemiStableSort.StableOrder() { // from class: com.android.systemui.statusbar.notification.collection.ShadeListBuilder$$ExternalSyntheticLambda0
        @Override // com.android.systemui.statusbar.notification.collection.listbuilder.SemiStableSort.StableOrder
        public final Integer getRank(Object obj) {
            return this.f$0.getStableOrderRank((ListEntry) obj);
        }
    };
    private final PipelineState mPipelineState = new PipelineState();
    private final Map mGroups = new ArrayMap();

    public interface OnRenderListListener {
        void onRenderList(List list);
    }

    public static /* synthetic */ boolean $r8$lambda$LZ69XfxpMpZuwwtUT0FtdYFBygM(GroupEntry groupEntry) {
        return groupEntry.getSummary() == null && groupEntry.getChildren().isEmpty();
    }

    public static /* synthetic */ int $r8$lambda$LvJFEoWs3EDyv3MeTgNfwMTnpWE(NotificationEntry notificationEntry, NotificationEntry notificationEntry2) {
        int iCompare = Integer.compare(notificationEntry.getRepresentativeEntry().getRanking().getRank(), notificationEntry2.getRepresentativeEntry().getRanking().getRank());
        return iCompare != 0 ? iCompare : Long.compare(notificationEntry.getRepresentativeEntry().getSbn().getNotification().when, notificationEntry2.getRepresentativeEntry().getSbn().getNotification().when) * (-1);
    }

    public ShadeListBuilder(DumpManager dumpManager, NotifPipelineChoreographer notifPipelineChoreographer, NotifPipelineFlags notifPipelineFlags, NotificationInteractionTracker notificationInteractionTracker, ShadeListBuilderLogger shadeListBuilderLogger, SystemClock systemClock) {
        List list = Collections.EMPTY_LIST;
        this.mAllEntries = list;
        this.mPendingEntries = null;
        this.mIterationCount = 0;
        this.mNotifPreGroupFilters = new ArrayList();
        this.mNotifPromoters = new ArrayList();
        this.mNotifFinalizeFilters = new ArrayList();
        this.mNotifComparators = new ArrayList();
        this.mNotifSections = new ArrayList();
        this.mOnBeforeTransformGroupsListeners = new NamedListenerSet();
        this.mOnBeforeSortListeners = new NamedListenerSet();
        this.mOnBeforeFinalizeFilterListeners = new NamedListenerSet();
        this.mOnBeforeRenderListListeners = new NamedListenerSet();
        this.mReadOnlyNotifList = Collections.unmodifiableList(this.mNotifList);
        this.mReadOnlyNewNotifList = Collections.unmodifiableList(this.mNewNotifList);
        this.mConsecutiveReentrantRebuilds = 0;
        this.mReadyForBuildListener = new CollectionReadyForBuildListener() { // from class: com.android.systemui.statusbar.notification.collection.ShadeListBuilder.1
            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.CollectionReadyForBuildListener
            public void onBuildList(Collection collection, String str) {
                Assert.isMainThread();
                ShadeListBuilder.this.mPendingEntries = new ArrayList(collection);
                ShadeListBuilder.this.mLogger.logOnBuildList(str);
                ShadeListBuilder.this.rebuildListIfBefore(1);
            }
        };
        this.mTopLevelComparator = new Comparator() { // from class: com.android.systemui.statusbar.notification.collection.ShadeListBuilder$$ExternalSyntheticLambda1
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return this.f$0.lambda$new$2((ListEntry) obj, (ListEntry) obj2);
            }
        };
        this.mGroupChildrenComparator = new Comparator() { // from class: com.android.systemui.statusbar.notification.collection.ShadeListBuilder$$ExternalSyntheticLambda2
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return ShadeListBuilder.$r8$lambda$LvJFEoWs3EDyv3MeTgNfwMTnpWE((NotificationEntry) obj, (NotificationEntry) obj2);
            }
        };
        this.mSystemClock = systemClock;
        this.mLogger = shadeListBuilderLogger;
        this.mFlags = notifPipelineFlags;
        this.mAlwaysLogList = notifPipelineFlags.isDevLoggingEnabled();
        this.mInteractionTracker = notificationInteractionTracker;
        this.mChoreographer = notifPipelineChoreographer;
        this.mDumpManager = dumpManager;
        setSectioners(list);
    }

    private void addGroupsWithChildrenLostToFiltering(Set set) {
        for (ListEntry listEntry : this.mAllEntries) {
            StatusBarNotification sbn = listEntry.getRepresentativeEntry().getSbn();
            if (sbn.isGroup() && !sbn.getNotification().isGroupSummary() && listEntry.getAttachState().getExcludingFilter() != null) {
                set.add(sbn.getGroupKey());
            }
        }
    }

    private void addGroupsWithChildrenLostToPromotion(List list, Set set) {
        for (int i = 0; i < list.size(); i++) {
            ListEntry listEntry = (ListEntry) list.get(i);
            if (listEntry.getAttachState().getPromoter() != null) {
                set.add(listEntry.getRepresentativeEntry().getSbn().getGroupKey());
            }
        }
    }

    private void annulAddition(ListEntry listEntry) {
        listEntry.getAttachState().detach();
    }

    private void annulAddition(ListEntry listEntry, List list) {
        if (listEntry.getParent() == null) {
            throw new IllegalStateException("Cannot nullify addition of " + listEntry.getKey() + ": no parent.");
        }
        if (listEntry.getParent() == GroupEntry.ROOT_ENTRY && list.contains(listEntry)) {
            throw new IllegalStateException("Cannot nullify addition of " + listEntry.getKey() + ": it's still in the shade list.");
        }
        if (listEntry instanceof GroupEntry) {
            GroupEntry groupEntry = (GroupEntry) listEntry;
            if (groupEntry.getSummary() != null) {
                throw new IllegalStateException("Cannot nullify group " + groupEntry.getKey() + ": summary is not null");
            }
            if (!groupEntry.getChildren().isEmpty()) {
                throw new IllegalStateException("Cannot nullify group " + groupEntry.getKey() + ": still has children");
            }
        } else if ((listEntry instanceof NotificationEntry) && (listEntry == listEntry.getParent().getSummary() || listEntry.getParent().getChildren().contains(listEntry))) {
            throw new IllegalStateException("Cannot nullify addition of child " + listEntry.getKey() + ": it's still attached to its parent.");
        }
        annulAddition(listEntry);
    }

    private boolean applyFilters(NotificationEntry notificationEntry, long j, List list) {
        NotifFilter notifFilterFindRejectingFilter = findRejectingFilter(notificationEntry, j, list);
        notificationEntry.getAttachState().setExcludingFilter(notifFilterFindRejectingFilter);
        if (notifFilterFindRejectingFilter != null) {
            notificationEntry.resetInitializationTime();
        }
        return notifFilterFindRejectingFilter != null;
    }

    private void applyNewNotifList() {
        this.mNotifList.clear();
        List list = this.mNotifList;
        this.mNotifList = this.mNewNotifList;
        this.mNewNotifList = list;
        List list2 = this.mReadOnlyNotifList;
        this.mReadOnlyNotifList = this.mReadOnlyNewNotifList;
        this.mReadOnlyNewNotifList = list2;
    }

    private NotifSection applySections(ListEntry listEntry) {
        NotifSection notifSectionFindSection = findSection(listEntry);
        ListAttachState previousAttachState = listEntry.getPreviousAttachState();
        if (listEntry.wasAttachedInPreviousPass() && notifSectionFindSection != previousAttachState.getSection() && !getStabilityManager().isSectionChangeAllowed(listEntry.getRepresentativeEntry())) {
            listEntry.getAttachState().getSuppressedChanges().setSection(notifSectionFindSection);
            notifSectionFindSection = previousAttachState.getSection();
        }
        setEntrySection(listEntry, notifSectionFindSection);
        return notifSectionFindSection;
    }

    private boolean applyTopLevelPromoters(NotificationEntry notificationEntry) {
        NotifPromoter notifPromoterFindPromoter = findPromoter(notificationEntry);
        notificationEntry.getAttachState().setPromoter(notifPromoterFindPromoter);
        return notifPromoterFindPromoter != null;
    }

    private void assignIndexes(List list) {
        if (list.size() == 0) {
            return;
        }
        NotifSection section = ((ListEntry) list.get(0)).getSection();
        section.getClass();
        int i = 0;
        for (int i2 = 0; i2 < list.size(); i2++) {
            ListEntry listEntry = (ListEntry) list.get(i2);
            NotifSection section2 = listEntry.getSection();
            section2.getClass();
            if (section2.getIndex() != section.getIndex()) {
                i = 0;
                section = section2;
            }
            int i3 = i + 1;
            listEntry.getAttachState().setStableIndex(i);
            if (listEntry instanceof GroupEntry) {
                GroupEntry groupEntry = (GroupEntry) listEntry;
                NotificationEntry summary = groupEntry.getSummary();
                if (summary != null) {
                    summary.getAttachState().setStableIndex(i3);
                    i3 = i + 2;
                }
                Iterator it = groupEntry.getChildren().iterator();
                while (it.hasNext()) {
                    ((NotificationEntry) it.next()).getAttachState().setStableIndex(i3);
                    i3++;
                }
            }
            i = i3;
        }
    }

    private void assignSections() {
        Trace.beginSection("ShadeListBuilder.assignSections");
        for (ListEntry listEntry : this.mNotifList) {
            NotifSection notifSectionApplySections = applySections(listEntry);
            if (listEntry instanceof GroupEntry) {
                Iterator it = ((GroupEntry) listEntry).getChildren().iterator();
                while (it.hasNext()) {
                    setEntrySection((NotificationEntry) it.next(), notifSectionApplySections);
                }
            }
        }
        Trace.endSection();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void buildList() {
        Trace.beginSection("ShadeListBuilder.buildList");
        this.mPipelineState.requireIsBefore(1);
        Collection collection = this.mPendingEntries;
        if (collection != null) {
            this.mAllEntries = collection;
            this.mPendingEntries = null;
        }
        if (!this.mNotifStabilityManager.isPipelineRunAllowed()) {
            this.mLogger.logPipelineRunSuppressed();
            Trace.endSection();
            return;
        }
        this.mPipelineState.setState(1);
        this.mPipelineState.incrementTo(2);
        resetNotifs();
        onBeginRun();
        this.mPipelineState.incrementTo(3);
        filterNotifs(this.mAllEntries, this.mNotifList, this.mNotifPreGroupFilters);
        this.mPipelineState.incrementTo(4);
        groupNotifs(this.mNotifList, this.mNewNotifList);
        applyNewNotifList();
        pruneIncompleteGroups(this.mNotifList);
        dispatchOnBeforeTransformGroups(this.mReadOnlyNotifList);
        this.mPipelineState.incrementTo(5);
        promoteNotifs(this.mNotifList);
        pruneIncompleteGroups(this.mNotifList);
        this.mPipelineState.incrementTo(6);
        stabilizeGroupingNotifs(this.mNotifList);
        dispatchOnBeforeSort(this.mReadOnlyNotifList);
        this.mPipelineState.incrementTo(7);
        assignSections();
        notifySectionEntriesUpdated();
        sortListAndGroups();
        dispatchOnBeforeFinalizeFilter(this.mReadOnlyNotifList);
        this.mPipelineState.incrementTo(8);
        filterNotifs(this.mNotifList, this.mNewNotifList, this.mNotifFinalizeFilters);
        applyNewNotifList();
        pruneIncompleteGroups(this.mNotifList);
        this.mPipelineState.incrementTo(9);
        logChanges();
        freeEmptyGroups();
        cleanupPluggables();
        dispatchOnBeforeRenderList(this.mReadOnlyNotifList);
        Trace.beginSection("ShadeListBuilder.onRenderList");
        OnRenderListListener onRenderListListener = this.mOnRenderListListener;
        if (onRenderListListener != null) {
            onRenderListListener.onRenderList(this.mReadOnlyNotifList);
        }
        Trace.endSection();
        Trace.beginSection("ShadeListBuilder.logEndBuildList");
        this.mLogger.logEndBuildList(this.mIterationCount, this.mReadOnlyNotifList.size(), countChildren(this.mReadOnlyNotifList), !this.mNotifStabilityManager.isEveryChangeAllowed());
        if (this.mAlwaysLogList || this.mIterationCount % 10 == 0) {
            Trace.beginSection("ShadeListBuilder.logFinalList");
            this.mLogger.logFinalList(this.mNotifList);
            Trace.endSection();
        }
        Trace.endSection();
        this.mPipelineState.setState(0);
        this.mIterationCount++;
        Trace.endSection();
    }

    private void callOnCleanup(List list) {
        for (int i = 0; i < list.size(); i++) {
            ((Pluggable) list.get(i)).onCleanup();
        }
    }

    private void cleanupPluggables() {
        Trace.beginSection("ShadeListBuilder.cleanupPluggables");
        callOnCleanup(this.mNotifPreGroupFilters);
        callOnCleanup(this.mNotifPromoters);
        callOnCleanup(this.mNotifFinalizeFilters);
        callOnCleanup(this.mNotifComparators);
        for (int i = 0; i < this.mNotifSections.size(); i++) {
            NotifSection notifSection = (NotifSection) this.mNotifSections.get(i);
            notifSection.getSectioner().onCleanup();
            NotifComparator comparator = notifSection.getComparator();
            if (comparator != null) {
                comparator.onCleanup();
            }
        }
        callOnCleanup(List.of(getStabilityManager()));
        Trace.endSection();
    }

    private static int countChildren(List list) {
        int size = 0;
        for (int i = 0; i < list.size(); i++) {
            ListEntry listEntry = (ListEntry) list.get(i);
            if (listEntry instanceof GroupEntry) {
                size += ((GroupEntry) listEntry).getChildren().size();
            }
        }
        return size;
    }

    private void dispatchOnBeforeFinalizeFilter(final List list) {
        Trace.beginSection("ShadeListBuilder.dispatchOnBeforeFinalizeFilter");
        this.mOnBeforeFinalizeFilterListeners.forEachTraced(new Consumer() { // from class: com.android.systemui.statusbar.notification.collection.ShadeListBuilder$$ExternalSyntheticLambda11
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((OnBeforeFinalizeFilterListener) obj).onBeforeFinalizeFilter(list);
            }
        });
        Trace.endSection();
    }

    private void dispatchOnBeforeRenderList(final List list) {
        Trace.beginSection("ShadeListBuilder.dispatchOnBeforeRenderList");
        this.mOnBeforeRenderListListeners.forEachTraced(new Consumer() { // from class: com.android.systemui.statusbar.notification.collection.ShadeListBuilder$$ExternalSyntheticLambda9
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((OnBeforeRenderListListener) obj).onBeforeRenderList(list);
            }
        });
        Trace.endSection();
    }

    private void dispatchOnBeforeSort(final List list) {
        Trace.beginSection("ShadeListBuilder.dispatchOnBeforeSort");
        this.mOnBeforeSortListeners.forEachTraced(new Consumer() { // from class: com.android.systemui.statusbar.notification.collection.ShadeListBuilder$$ExternalSyntheticLambda8
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                List list2 = list;
                ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                ((OnBeforeSortListener) null).onBeforeSort(list2);
            }
        });
        Trace.endSection();
    }

    private void dispatchOnBeforeTransformGroups(final List list) {
        Trace.beginSection("ShadeListBuilder.dispatchOnBeforeTransformGroups");
        this.mOnBeforeTransformGroupsListeners.forEachTraced(new Consumer() { // from class: com.android.systemui.statusbar.notification.collection.ShadeListBuilder$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((OnBeforeTransformGroupsListener) obj).onBeforeTransformGroups(list);
            }
        });
        Trace.endSection();
    }

    private void filterNotifs(Collection collection, List list, List list2) {
        Trace.beginSection("ShadeListBuilder.filterNotifs");
        long jUptimeMillis = this.mSystemClock.uptimeMillis();
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            ListEntry listEntry = (ListEntry) it.next();
            if (listEntry instanceof GroupEntry) {
                GroupEntry groupEntry = (GroupEntry) listEntry;
                NotificationEntry representativeEntry = groupEntry.getRepresentativeEntry();
                if (applyFilters(representativeEntry, jUptimeMillis, list2)) {
                    groupEntry.setSummary(null);
                    annulAddition(representativeEntry);
                }
                List rawChildren = groupEntry.getRawChildren();
                for (int size = rawChildren.size() - 1; size >= 0; size--) {
                    NotificationEntry notificationEntry = (NotificationEntry) rawChildren.get(size);
                    if (applyFilters(notificationEntry, jUptimeMillis, list2)) {
                        rawChildren.remove(notificationEntry);
                        annulAddition(notificationEntry);
                    }
                }
                list.add(groupEntry);
            } else if (applyFilters((NotificationEntry) listEntry, jUptimeMillis, list2)) {
                annulAddition(listEntry);
            } else {
                list.add(listEntry);
            }
        }
        Trace.endSection();
    }

    private NotifPromoter findPromoter(NotificationEntry notificationEntry) {
        for (int i = 0; i < this.mNotifPromoters.size(); i++) {
            NotifPromoter notifPromoter = (NotifPromoter) this.mNotifPromoters.get(i);
            if (notifPromoter.shouldPromoteToTopLevel(notificationEntry)) {
                return notifPromoter;
            }
        }
        return null;
    }

    private static NotifFilter findRejectingFilter(NotificationEntry notificationEntry, long j, List list) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            NotifFilter notifFilter = (NotifFilter) list.get(i);
            if (notifFilter.shouldFilterOut(notificationEntry, j)) {
                return notifFilter;
            }
        }
        return null;
    }

    private NotifSection findSection(ListEntry listEntry) {
        for (int i = 0; i < this.mNotifSections.size(); i++) {
            NotifSection notifSection = (NotifSection) this.mNotifSections.get(i);
            if (notifSection.getSectioner().isInSection(listEntry)) {
                return notifSection;
            }
        }
        throw new RuntimeException("Missing default sectioner!");
    }

    private void freeEmptyGroups() {
        Trace.beginSection("ShadeListBuilder.freeEmptyGroups");
        this.mGroups.values().removeIf(new Predicate() { // from class: com.android.systemui.statusbar.notification.collection.ShadeListBuilder$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ShadeListBuilder.$r8$lambda$LZ69XfxpMpZuwwtUT0FtdYFBygM((GroupEntry) obj);
            }
        });
        Trace.endSection();
    }

    private Set getGroupsWithChildrenLostToStability(List list) {
        if (getStabilityManager().isEveryChangeAllowed()) {
            return Collections.EMPTY_SET;
        }
        ArraySet arraySet = new ArraySet();
        for (int i = 0; i < list.size(); i++) {
            GroupEntry parent = ((ListEntry) list.get(i)).getAttachState().getSuppressedChanges().getParent();
            if (parent != null) {
                arraySet.add(parent.getKey());
            }
        }
        return arraySet;
    }

    private NotifComparator getSectionComparator(ListEntry listEntry, ListEntry listEntry2) {
        NotifSection section = listEntry.getSection();
        if (section != listEntry2.getSection()) {
            throw new RuntimeException("Entry ordering should only be done within sections");
        }
        if (section != null) {
            return section.getComparator();
        }
        return null;
    }

    private Iterable getSectionSubLists(List list) {
        return ShadeListBuilderHelper.INSTANCE.getSectionSubLists(list);
    }

    private NotifStabilityManager getStabilityManager() {
        NotifStabilityManager notifStabilityManager = this.mNotifStabilityManager;
        return notifStabilityManager == null ? DefaultNotifStabilityManager.INSTANCE : notifStabilityManager;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Integer getStableOrderRank(ListEntry listEntry) {
        int stableIndex;
        if (getStabilityManager().isEntryReorderingAllowed(listEntry) || listEntry.getAttachState().getSectionIndex() != listEntry.getPreviousAttachState().getSectionIndex() || (stableIndex = listEntry.getPreviousAttachState().getStableIndex()) == -1) {
            return null;
        }
        return Integer.valueOf(stableIndex);
    }

    private void groupNotifs(List list, List list2) {
        Trace.beginSection("ShadeListBuilder.groupNotifs");
        Iterator it = list.iterator();
        while (it.hasNext()) {
            NotificationEntry notificationEntry = (NotificationEntry) ((ListEntry) it.next());
            if (notificationEntry.getSbn().isGroup()) {
                String groupKey = notificationEntry.getSbn().getGroupKey();
                GroupEntry groupEntry = (GroupEntry) this.mGroups.get(groupKey);
                if (groupEntry == null) {
                    groupEntry = new GroupEntry(groupKey, this.mSystemClock.uptimeMillis());
                    this.mGroups.put(groupKey, groupEntry);
                }
                if (groupEntry.getParent() == null) {
                    groupEntry.setParent(GroupEntry.ROOT_ENTRY);
                    list2.add(groupEntry);
                }
                notificationEntry.setParent(groupEntry);
                if (notificationEntry.getSbn().getNotification().isGroupSummary()) {
                    NotificationEntry summary = groupEntry.getSummary();
                    if (summary == null) {
                        groupEntry.setSummary(notificationEntry);
                    } else {
                        this.mLogger.logDuplicateSummary(this.mIterationCount, groupEntry, summary, notificationEntry);
                        if (notificationEntry.getSbn().getPostTime() > summary.getSbn().getPostTime()) {
                            groupEntry.setSummary(notificationEntry);
                            annulAddition(summary, list2);
                        } else {
                            annulAddition(notificationEntry, list2);
                        }
                    }
                } else {
                    groupEntry.addChild(notificationEntry);
                }
            } else {
                String key = notificationEntry.getKey();
                if (this.mGroups.containsKey(key)) {
                    this.mLogger.logDuplicateTopLevelKey(this.mIterationCount, key);
                } else {
                    notificationEntry.setParent(GroupEntry.ROOT_ENTRY);
                    list2.add(notificationEntry);
                }
            }
        }
        Trace.endSection();
    }

    public static boolean isSorted(List list, Comparator comparator) {
        if (list.size() <= 1) {
            return true;
        }
        Iterator it = list.iterator();
        Object next = it.next();
        while (it.hasNext()) {
            Object next2 = it.next();
            if (comparator.compare(next, next2) > 0) {
                return false;
            }
            next = next2;
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$new$2(ListEntry listEntry, ListEntry listEntry2) {
        int iCompare;
        int iCompare2 = Integer.compare(listEntry.getSectionIndex(), listEntry2.getSectionIndex());
        if (iCompare2 != 0) {
            return iCompare2;
        }
        NotifComparator sectionComparator = getSectionComparator(listEntry, listEntry2);
        if (sectionComparator != null && (iCompare = sectionComparator.compare(listEntry, listEntry2)) != 0) {
            return iCompare;
        }
        for (int i = 0; i < this.mNotifComparators.size(); i++) {
            int iCompare3 = ((NotifComparator) this.mNotifComparators.get(i)).compare(listEntry, listEntry2);
            if (iCompare3 != 0) {
                return iCompare3;
            }
        }
        int iCompare4 = Integer.compare(listEntry.getRepresentativeEntry().getRanking().getRank(), listEntry2.getRepresentativeEntry().getRanking().getRank());
        return iCompare4 != 0 ? iCompare4 : Long.compare(listEntry.getRepresentativeEntry().getSbn().getNotification().when, listEntry2.getRepresentativeEntry().getSbn().getNotification().when) * (-1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$promoteNotifs$0(List list, NotificationEntry notificationEntry) {
        boolean zApplyTopLevelPromoters = applyTopLevelPromoters(notificationEntry);
        if (zApplyTopLevelPromoters) {
            notificationEntry.setParent(GroupEntry.ROOT_ENTRY);
            list.add(notificationEntry);
        }
        return zApplyTopLevelPromoters;
    }

    private void logAttachStateChanges(ListEntry listEntry) {
        ListAttachState attachState = listEntry.getAttachState();
        ListAttachState previousAttachState = listEntry.getPreviousAttachState();
        if (Objects.equals(attachState, previousAttachState)) {
            return;
        }
        this.mLogger.logEntryAttachStateChanged(this.mIterationCount, listEntry, previousAttachState.getParent(), attachState.getParent());
        if (attachState.getParent() != previousAttachState.getParent()) {
            this.mLogger.logParentChanged(this.mIterationCount, previousAttachState.getParent(), attachState.getParent());
        }
        GroupEntry parent = attachState.getSuppressedChanges().getParent();
        GroupEntry parent2 = previousAttachState.getSuppressedChanges().getParent();
        if (parent != null && (parent2 == null || !parent2.getKey().equals(parent.getKey()))) {
            this.mLogger.logParentChangeSuppressedStarted(this.mIterationCount, parent, attachState.getParent());
        }
        if (parent2 != null && parent == null) {
            this.mLogger.logParentChangeSuppressedStopped(this.mIterationCount, parent2, previousAttachState.getParent());
        }
        if (attachState.getSuppressedChanges().getSection() != null) {
            this.mLogger.logSectionChangeSuppressed(this.mIterationCount, attachState.getSuppressedChanges().getSection(), attachState.getSection());
        }
        if (attachState.getSuppressedChanges().getWasPruneSuppressed()) {
            this.mLogger.logGroupPruningSuppressed(this.mIterationCount, attachState.getParent());
        }
        if (!Objects.equals(attachState.getGroupPruneReason(), previousAttachState.getGroupPruneReason())) {
            this.mLogger.logPrunedReasonChanged(this.mIterationCount, previousAttachState.getGroupPruneReason(), attachState.getGroupPruneReason());
        }
        if (attachState.getExcludingFilter() != previousAttachState.getExcludingFilter()) {
            this.mLogger.logFilterChanged(this.mIterationCount, previousAttachState.getExcludingFilter(), attachState.getExcludingFilter());
        }
        boolean z = attachState.getParent() == null && previousAttachState.getParent() != null;
        if (!z && attachState.getPromoter() != previousAttachState.getPromoter()) {
            this.mLogger.logPromoterChanged(this.mIterationCount, previousAttachState.getPromoter(), attachState.getPromoter());
        }
        if (z || attachState.getSection() == previousAttachState.getSection()) {
            return;
        }
        this.mLogger.logSectionChanged(this.mIterationCount, previousAttachState.getSection(), attachState.getSection());
    }

    private void logChanges() {
        Trace.beginSection("ShadeListBuilder.logChanges");
        Iterator it = this.mAllEntries.iterator();
        while (it.hasNext()) {
            logAttachStateChanges((NotificationEntry) it.next());
        }
        Iterator it2 = this.mGroups.values().iterator();
        while (it2.hasNext()) {
            logAttachStateChanges((GroupEntry) it2.next());
        }
        Trace.endSection();
    }

    private boolean maybeSuppressGroupChange(NotificationEntry notificationEntry, List list) {
        GroupEntry parent;
        GroupEntry parent2 = notificationEntry.getPreviousAttachState().getParent();
        if (parent2 == null || parent2 == (parent = notificationEntry.getParent())) {
            return false;
        }
        GroupEntry groupEntry = GroupEntry.ROOT_ENTRY;
        if ((parent2 != groupEntry && parent2.getParent() == null) || getStabilityManager().isGroupChangeAllowed(notificationEntry.getRepresentativeEntry())) {
            return false;
        }
        notificationEntry.getAttachState().getSuppressedChanges().setParent(parent);
        notificationEntry.setParent(parent2);
        if (parent2 == groupEntry) {
            list.add(notificationEntry);
            return true;
        }
        parent2.addChild(notificationEntry);
        if (this.mGroups.containsKey(parent2.getKey())) {
            return true;
        }
        this.mGroups.put(parent2.getKey(), parent2);
        return true;
    }

    private void notifySectionEntriesUpdated() {
        Trace.beginSection("ShadeListBuilder.notifySectionEntriesUpdated");
        this.mTempSectionMembers.clear();
        for (NotifSection notifSection : this.mNotifSections) {
            for (ListEntry listEntry : this.mNotifList) {
                if (notifSection == listEntry.getSection()) {
                    this.mTempSectionMembers.add(listEntry);
                }
            }
            Trace.beginSection(notifSection.getLabel());
            notifSection.getSectioner().onEntriesUpdated(this.mTempSectionMembers);
            Trace.endSection();
            this.mTempSectionMembers.clear();
        }
        Trace.endSection();
    }

    private void onBeginRun() {
        getStabilityManager().onBeginRun();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onFinalizeFilterInvalidated(NotifFilter notifFilter, String str) {
        Assert.isMainThread();
        this.mLogger.logFinalizeFilterInvalidated(notifFilter, this.mPipelineState.getState(), str);
        rebuildListIfBefore(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onNotifComparatorInvalidated(NotifComparator notifComparator, String str) {
        Assert.isMainThread();
        this.mLogger.logNotifComparatorInvalidated(notifComparator, this.mPipelineState.getState(), str);
        rebuildListIfBefore(7);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onNotifSectionInvalidated(NotifSectioner notifSectioner, String str) {
        Assert.isMainThread();
        this.mLogger.logNotifSectionInvalidated(notifSectioner, this.mPipelineState.getState(), str);
        rebuildListIfBefore(7);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onPreGroupFilterInvalidated(NotifFilter notifFilter, String str) {
        Assert.isMainThread();
        this.mLogger.logPreGroupFilterInvalidated(notifFilter, this.mPipelineState.getState(), str);
        rebuildListIfBefore(3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onPreRenderInvalidated(Invalidator invalidator, String str) {
        Assert.isMainThread();
        this.mLogger.logPreRenderInvalidated(invalidator, this.mPipelineState.getState(), str);
        rebuildListIfBefore(9);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onPromoterInvalidated(NotifPromoter notifPromoter, String str) {
        Assert.isMainThread();
        this.mLogger.logPromoterInvalidated(notifPromoter, this.mPipelineState.getState(), str);
        rebuildListIfBefore(5);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onReorderingAllowedInvalidated(NotifStabilityManager notifStabilityManager, String str) {
        Assert.isMainThread();
        this.mLogger.logReorderingAllowedInvalidated(notifStabilityManager, this.mPipelineState.getState(), str);
        rebuildListIfBefore(4);
    }

    private void promoteNotifs(final List list) {
        Trace.beginSection("ShadeListBuilder.promoteNotifs");
        for (int i = 0; i < list.size(); i++) {
            ListEntry listEntry = (ListEntry) list.get(i);
            if (listEntry instanceof GroupEntry) {
                ((GroupEntry) listEntry).getRawChildren().removeIf(new Predicate() { // from class: com.android.systemui.statusbar.notification.collection.ShadeListBuilder$$ExternalSyntheticLambda10
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return this.f$0.lambda$promoteNotifs$0(list, (NotificationEntry) obj);
                    }
                });
            }
        }
        Trace.endSection();
    }

    private void pruneGroupAtIndexAndPromoteAnyChildren(List list, GroupEntry groupEntry, int i) {
        Preconditions.checkState(((ListEntry) list.remove(i)) == groupEntry);
        List rawChildren = groupEntry.getRawChildren();
        boolean z = groupEntry.getSummary() != null;
        if (z) {
            NotificationEntry summary = groupEntry.getSummary();
            groupEntry.setSummary(null);
            annulAddition(summary, list);
            summary.getAttachState().setGroupPruneReason("SUMMARY with too few children @ " + this.mPipelineState.getStateName());
        }
        if (!rawChildren.isEmpty()) {
            String str = z ? "CHILD with " + (rawChildren.size() - 1) + " siblings @ " + this.mPipelineState.getStateName() : "CHILD with no summary @ " + this.mPipelineState.getStateName();
            for (int i2 = 0; i2 < rawChildren.size(); i2++) {
                NotificationEntry notificationEntry = (NotificationEntry) rawChildren.get(i2);
                notificationEntry.setParent(GroupEntry.ROOT_ENTRY);
                ListAttachState attachState = notificationEntry.getAttachState();
                str.getClass();
                attachState.setGroupPruneReason(str);
            }
            list.addAll(i, rawChildren);
            rawChildren.clear();
        }
        annulAddition(groupEntry, list);
    }

    private void pruneGroupAtIndexAndPromoteSummary(List list, GroupEntry groupEntry, int i) {
        Preconditions.checkArgument(groupEntry.getChildren().isEmpty(), "group should have no children");
        NotificationEntry summary = groupEntry.getSummary();
        summary.setParent(GroupEntry.ROOT_ENTRY);
        Preconditions.checkState(((ListEntry) list.set(i, summary)) == groupEntry);
        groupEntry.setSummary(null);
        annulAddition(groupEntry, list);
        summary.getAttachState().setGroupPruneReason("SUMMARY with no children @ " + this.mPipelineState.getStateName());
    }

    private void pruneIncompleteGroups(List list) {
        Trace.beginSection("ShadeListBuilder.pruneIncompleteGroups");
        Set groupsWithChildrenLostToStability = getGroupsWithChildrenLostToStability(list);
        ArraySet arraySet = new ArraySet(groupsWithChildrenLostToStability);
        addGroupsWithChildrenLostToFiltering(arraySet);
        addGroupsWithChildrenLostToPromotion(list, arraySet);
        for (int size = list.size() - 1; size >= 0; size--) {
            ListEntry listEntry = (ListEntry) list.get(size);
            if (listEntry instanceof GroupEntry) {
                GroupEntry groupEntry = (GroupEntry) listEntry;
                List rawChildren = groupEntry.getRawChildren();
                boolean z = groupEntry.getSummary() != null;
                if (z && rawChildren.size() == 0) {
                    if (arraySet.contains(groupEntry.getKey())) {
                        pruneGroupAtIndexAndPromoteAnyChildren(list, groupEntry, size);
                    } else {
                        pruneGroupAtIndexAndPromoteSummary(list, groupEntry, size);
                    }
                } else if (!z) {
                    pruneGroupAtIndexAndPromoteAnyChildren(list, groupEntry, size);
                } else if (rawChildren.size() < 2) {
                    Preconditions.checkState(z, "group must have summary at this point");
                    Preconditions.checkState(!rawChildren.isEmpty(), "empty group should have been promoted");
                    if (groupsWithChildrenLostToStability.contains(groupEntry.getKey())) {
                        groupEntry.getAttachState().getSuppressedChanges().setWasPruneSuppressed(true);
                    } else if (!groupEntry.wasAttachedInPreviousPass() || getStabilityManager().isGroupPruneAllowed(groupEntry)) {
                        pruneGroupAtIndexAndPromoteAnyChildren(list, groupEntry, size);
                    } else {
                        Preconditions.checkState(!rawChildren.isEmpty(), "empty group should have been pruned");
                        groupEntry.getAttachState().getSuppressedChanges().setWasPruneSuppressed(true);
                    }
                }
            }
        }
        Trace.endSection();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void rebuildListIfBefore(int i) {
        int state = this.mPipelineState.getState();
        if (state == 0) {
            scheduleRebuild(false, i);
        } else {
            if (i > state) {
                return;
            }
            scheduleRebuild(true, i);
        }
    }

    private void resetNotifs() {
        for (GroupEntry groupEntry : this.mGroups.values()) {
            groupEntry.beginNewAttachState();
            groupEntry.clearChildren();
            groupEntry.setSummary(null);
        }
        Iterator it = this.mAllEntries.iterator();
        while (it.hasNext()) {
            ((NotificationEntry) it.next()).beginNewAttachState();
        }
        this.mNotifList.clear();
    }

    private void scheduleRebuild(boolean z, int i) {
        if (!z) {
            this.mConsecutiveReentrantRebuilds = 0;
            this.mChoreographer.schedule();
            return;
        }
        int state = this.mPipelineState.getState();
        IllegalStateException illegalStateException = new IllegalStateException("Reentrant notification pipeline rebuild of state " + PipelineState.getStateName(i) + " while pipeline in state " + PipelineState.getStateName(state) + ".");
        int i2 = this.mConsecutiveReentrantRebuilds + 1;
        this.mConsecutiveReentrantRebuilds = i2;
        if (i2 > 3) {
            Log.e("ShadeListBuilder", "Crashing after more than 3 consecutive reentrant notification pipeline rebuilds.", illegalStateException);
            throw illegalStateException;
        }
        Log.wtf("ShadeListBuilder", "Allowing " + this.mConsecutiveReentrantRebuilds + " consecutive reentrant notification pipeline rebuild(s).", illegalStateException);
        this.mChoreographer.schedule();
    }

    private void setEntrySection(ListEntry listEntry, NotifSection notifSection) {
        listEntry.getAttachState().setSection(notifSection);
        NotificationEntry representativeEntry = listEntry.getRepresentativeEntry();
        if (representativeEntry != null) {
            representativeEntry.getAttachState().setSection(notifSection);
            if (notifSection != null) {
                representativeEntry.setBucket(notifSection.getBucket());
            }
        }
    }

    private boolean sortGroupChildren(List list) {
        if (!getStabilityManager().isEveryChangeAllowed()) {
            return this.mSemiStableSort.sort(list, this.mStableOrder, this.mGroupChildrenComparator);
        }
        list.sort(this.mGroupChildrenComparator);
        return true;
    }

    private void sortListAndGroups() {
        Trace.beginSection("ShadeListBuilder.sortListAndGroups");
        sortWithSemiStableSort();
        Trace.endSection();
    }

    private void sortWithSemiStableSort() {
        boolean zStabilizeTo = true;
        for (ListEntry listEntry : this.mNotifList) {
            if (listEntry instanceof GroupEntry) {
                zStabilizeTo &= sortGroupChildren(((GroupEntry) listEntry).getRawChildren());
            }
        }
        this.mNotifList.sort(this.mTopLevelComparator);
        if (!getStabilityManager().isEveryChangeAllowed()) {
            Iterator it = getSectionSubLists(this.mNotifList).iterator();
            while (it.hasNext()) {
                zStabilizeTo &= this.mSemiStableSort.stabilizeTo((List) it.next(), this.mStableOrder, this.mNewNotifList);
            }
            applyNewNotifList();
        }
        assignIndexes(this.mNotifList);
        if (zStabilizeTo) {
            return;
        }
        getStabilityManager().onEntryReorderSuppressed();
    }

    private void stabilizeGroupingNotifs(List list) {
        if (getStabilityManager().isEveryChangeAllowed()) {
            return;
        }
        Trace.beginSection("ShadeListBuilder.stabilizeGroupingNotifs");
        int i = 0;
        while (i < list.size()) {
            ListEntry listEntry = (ListEntry) list.get(i);
            if (listEntry instanceof GroupEntry) {
                GroupEntry groupEntry = (GroupEntry) listEntry;
                List rawChildren = groupEntry.getRawChildren();
                int i2 = 0;
                while (i2 < groupEntry.getChildren().size()) {
                    if (maybeSuppressGroupChange((NotificationEntry) rawChildren.get(i2), list)) {
                        rawChildren.remove(i2);
                        i2--;
                    }
                    i2++;
                }
            } else if (maybeSuppressGroupChange(listEntry.getRepresentativeEntry(), list)) {
                list.remove(i);
                i--;
            }
            i++;
        }
        Trace.endSection();
    }

    void addFinalizeFilter(NotifFilter notifFilter) {
        Assert.isMainThread();
        this.mPipelineState.requireState(0);
        this.mNotifFinalizeFilters.add(notifFilter);
        notifFilter.setInvalidationListener(new Pluggable.PluggableListener() { // from class: com.android.systemui.statusbar.notification.collection.ShadeListBuilder$$ExternalSyntheticLambda15
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.Pluggable.PluggableListener
            public final void onPluggableInvalidated(Object obj, String str) {
                this.f$0.onFinalizeFilterInvalidated((NotifFilter) obj, str);
            }
        });
    }

    void addOnBeforeFinalizeFilterListener(OnBeforeFinalizeFilterListener onBeforeFinalizeFilterListener) {
        Assert.isMainThread();
        this.mPipelineState.requireState(0);
        this.mOnBeforeFinalizeFilterListeners.addIfAbsent(onBeforeFinalizeFilterListener);
    }

    void addOnBeforeRenderListListener(OnBeforeRenderListListener onBeforeRenderListListener) {
        Assert.isMainThread();
        this.mPipelineState.requireState(0);
        this.mOnBeforeRenderListListeners.addIfAbsent(onBeforeRenderListListener);
    }

    void addOnBeforeTransformGroupsListener(OnBeforeTransformGroupsListener onBeforeTransformGroupsListener) {
        Assert.isMainThread();
        this.mPipelineState.requireState(0);
        this.mOnBeforeTransformGroupsListeners.addIfAbsent(onBeforeTransformGroupsListener);
    }

    void addPreGroupFilter(NotifFilter notifFilter) {
        Assert.isMainThread();
        this.mPipelineState.requireState(0);
        this.mNotifPreGroupFilters.add(notifFilter);
        notifFilter.setInvalidationListener(new Pluggable.PluggableListener() { // from class: com.android.systemui.statusbar.notification.collection.ShadeListBuilder$$ExternalSyntheticLambda14
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.Pluggable.PluggableListener
            public final void onPluggableInvalidated(Object obj, String str) {
                this.f$0.onPreGroupFilterInvalidated((NotifFilter) obj, str);
            }
        });
    }

    void addPreRenderInvalidator(Invalidator invalidator) {
        Assert.isMainThread();
        this.mPipelineState.requireState(0);
        invalidator.setInvalidationListener(new Pluggable.PluggableListener() { // from class: com.android.systemui.statusbar.notification.collection.ShadeListBuilder$$ExternalSyntheticLambda12
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.Pluggable.PluggableListener
            public final void onPluggableInvalidated(Object obj, String str) {
                this.f$0.onPreRenderInvalidated((Invalidator) obj, str);
            }
        });
    }

    void addPromoter(NotifPromoter notifPromoter) {
        Assert.isMainThread();
        this.mPipelineState.requireState(0);
        this.mNotifPromoters.add(notifPromoter);
        notifPromoter.setInvalidationListener(new Pluggable.PluggableListener() { // from class: com.android.systemui.statusbar.notification.collection.ShadeListBuilder$$ExternalSyntheticLambda16
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.Pluggable.PluggableListener
            public final void onPluggableInvalidated(Object obj, String str) {
                this.f$0.onPromoterInvalidated((NotifPromoter) obj, str);
            }
        });
    }

    public void attach(NotifCollection notifCollection) {
        Assert.isMainThread();
        this.mDumpManager.registerDumpable("ShadeListBuilder", this);
        notifCollection.addCollectionListener(this.mInteractionTracker);
        notifCollection.setBuildListener(this.mReadyForBuildListener);
        this.mChoreographer.addOnEvalListener(new Runnable() { // from class: com.android.systemui.statusbar.notification.collection.ShadeListBuilder$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.buildList();
            }
        });
    }

    void setNotifStabilityManager(NotifStabilityManager notifStabilityManager) {
        Assert.isMainThread();
        this.mPipelineState.requireState(0);
        if (this.mNotifStabilityManager == null) {
            this.mNotifStabilityManager = notifStabilityManager;
            notifStabilityManager.setInvalidationListener(new Pluggable.PluggableListener() { // from class: com.android.systemui.statusbar.notification.collection.ShadeListBuilder$$ExternalSyntheticLambda13
                @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.Pluggable.PluggableListener
                public final void onPluggableInvalidated(Object obj, String str) {
                    this.f$0.onReorderingAllowedInvalidated((NotifStabilityManager) obj, str);
                }
            });
            return;
        }
        throw new IllegalStateException("Attempting to set the NotifStabilityManager more than once. There should only be one visual stability manager. Manager is being set by " + this.mNotifStabilityManager.getName() + " and " + notifStabilityManager.getName());
    }

    public void setOnRenderListListener(OnRenderListListener onRenderListListener) {
        Assert.isMainThread();
        this.mPipelineState.requireState(0);
        this.mOnRenderListListener = onRenderListListener;
    }

    void setSectioners(List list) {
        Assert.isMainThread();
        this.mPipelineState.requireState(0);
        this.mNotifSections.clear();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            NotifSectioner notifSectioner = (NotifSectioner) it.next();
            NotifSection notifSection = new NotifSection(notifSectioner, this.mNotifSections.size());
            NotifComparator comparator = notifSection.getComparator();
            this.mNotifSections.add(notifSection);
            notifSectioner.setInvalidationListener(new Pluggable.PluggableListener() { // from class: com.android.systemui.statusbar.notification.collection.ShadeListBuilder$$ExternalSyntheticLambda4
                @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.Pluggable.PluggableListener
                public final void onPluggableInvalidated(Object obj, String str) {
                    this.f$0.onNotifSectionInvalidated((NotifSectioner) obj, str);
                }
            });
            if (comparator != null) {
                comparator.setInvalidationListener(new Pluggable.PluggableListener() { // from class: com.android.systemui.statusbar.notification.collection.ShadeListBuilder$$ExternalSyntheticLambda5
                    @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.Pluggable.PluggableListener
                    public final void onPluggableInvalidated(Object obj, String str) {
                        this.f$0.onNotifComparatorInvalidated((NotifComparator) obj, str);
                    }
                });
            }
        }
        List list2 = this.mNotifSections;
        list2.add(new NotifSection(DEFAULT_SECTIONER, list2.size()));
        ArraySet arraySet = new ArraySet();
        int bucket = this.mNotifSections.size() > 0 ? ((NotifSection) this.mNotifSections.get(0)).getBucket() : 0;
        for (NotifSection notifSection2 : this.mNotifSections) {
            if (bucket != notifSection2.getBucket() && arraySet.contains(Integer.valueOf(notifSection2.getBucket()))) {
                throw new IllegalStateException("setSectioners with non contiguous sections " + notifSection2.getLabel() + " has an already seen bucket");
            }
            bucket = notifSection2.getBucket();
            arraySet.add(Integer.valueOf(bucket));
        }
    }
}
