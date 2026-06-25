package com.android.systemui.statusbar.notification.stack;

import android.util.SparseArray;
import android.view.View;
import com.android.systemui.statusbar.notification.NotificationSectionsFeatureManager;
import com.android.systemui.statusbar.notification.SourceType;
import com.android.systemui.statusbar.notification.collection.render.MediaContainerController;
import com.android.systemui.statusbar.notification.collection.render.SectionHeaderController;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.stack.StackScrollAlgorithm;
import com.android.systemui.statusbar.policy.ConfigurationController;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.collections.Grouping;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;

/* JADX INFO: compiled from: NotificationSectionsManager.kt */
/* JADX INFO: loaded from: classes.dex */
public final class NotificationSectionsManager implements StackScrollAlgorithm.SectionProvider {
    public static final Companion Companion = new Companion(null);
    private static final SourceType SECTION = SourceType.Companion.from("Section");
    private final SectionHeaderController alertingHeaderController;
    private final ConfigurationController configurationController;
    private final NotificationSectionsManager$configurationListener$1 configurationListener;
    private final SectionHeaderController incomingHeaderController;
    private boolean initialized;
    private final MediaContainerController mediaContainerController;
    private final NotificationRoundnessManager notificationRoundnessManager;
    private NotificationStackScrollLayout parent;
    private final SectionHeaderController peopleHeaderController;
    private final NotificationSectionsFeatureManager sectionsFeatureManager;
    private final SectionHeaderController silentHeaderController;

    /* JADX INFO: compiled from: NotificationSectionsManager.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: compiled from: NotificationSectionsManager.kt */
    abstract class SectionBounds {

        /* JADX INFO: compiled from: NotificationSectionsManager.kt */
        public final class Many extends SectionBounds {
            private final ExpandableView first;
            private final ExpandableView last;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public Many(ExpandableView expandableView, ExpandableView expandableView2) {
                super(null);
                expandableView.getClass();
                expandableView2.getClass();
                this.first = expandableView;
                this.last = expandableView2;
            }

            public static /* synthetic */ Many copy$default(Many many, ExpandableView expandableView, ExpandableView expandableView2, int i, Object obj) {
                if ((i & 1) != 0) {
                    expandableView = many.first;
                }
                if ((i & 2) != 0) {
                    expandableView2 = many.last;
                }
                return many.copy(expandableView, expandableView2);
            }

            public final Many copy(ExpandableView expandableView, ExpandableView expandableView2) {
                expandableView.getClass();
                expandableView2.getClass();
                return new Many(expandableView, expandableView2);
            }

            public boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof Many)) {
                    return false;
                }
                Many many = (Many) obj;
                return Intrinsics.areEqual(this.first, many.first) && Intrinsics.areEqual(this.last, many.last);
            }

            public final ExpandableView getFirst() {
                return this.first;
            }

            public final ExpandableView getLast() {
                return this.last;
            }

            public int hashCode() {
                return (this.first.hashCode() * 31) + this.last.hashCode();
            }

            public String toString() {
                return "Many(first=" + this.first + ", last=" + this.last + ")";
            }
        }

        /* JADX INFO: compiled from: NotificationSectionsManager.kt */
        public final class None extends SectionBounds {
            public static final None INSTANCE = new None();

            private None() {
                super(null);
            }
        }

        /* JADX INFO: compiled from: NotificationSectionsManager.kt */
        public final class One extends SectionBounds {
            private final ExpandableView lone;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public One(ExpandableView expandableView) {
                super(null);
                expandableView.getClass();
                this.lone = expandableView;
            }

            public boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                return (obj instanceof One) && Intrinsics.areEqual(this.lone, ((One) obj).lone);
            }

            public final ExpandableView getLone() {
                return this.lone;
            }

            public int hashCode() {
                return this.lone.hashCode();
            }

            public String toString() {
                return "One(lone=" + this.lone + ")";
            }
        }

        private SectionBounds() {
        }

        public /* synthetic */ SectionBounds(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private final boolean setFirstAndLastVisibleChildren(NotificationSection notificationSection, ExpandableView expandableView, ExpandableView expandableView2) {
            return notificationSection.setFirstVisibleChild(expandableView) || notificationSection.setLastVisibleChild(expandableView2);
        }

        public final SectionBounds addNotif(ExpandableView expandableView) {
            expandableView.getClass();
            if (this instanceof None) {
                return new One(expandableView);
            }
            if (this instanceof One) {
                return new Many(((One) this).getLone(), expandableView);
            }
            if (this instanceof Many) {
                return Many.copy$default((Many) this, null, expandableView, 1, null);
            }
            throw new NoWhenBranchMatchedException();
        }

        public final boolean updateSection(NotificationSection notificationSection) {
            notificationSection.getClass();
            if (this instanceof None) {
                return setFirstAndLastVisibleChildren(notificationSection, null, null);
            }
            if (this instanceof One) {
                One one = (One) this;
                return setFirstAndLastVisibleChildren(notificationSection, one.getLone(), one.getLone());
            }
            if (!(this instanceof Many)) {
                throw new NoWhenBranchMatchedException();
            }
            Many many = (Many) this;
            return setFirstAndLastVisibleChildren(notificationSection, many.getFirst(), many.getLast());
        }
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.statusbar.notification.stack.NotificationSectionsManager$configurationListener$1] */
    public NotificationSectionsManager(ConfigurationController configurationController, NotificationSectionsFeatureManager notificationSectionsFeatureManager, MediaContainerController mediaContainerController, NotificationRoundnessManager notificationRoundnessManager, SectionHeaderController sectionHeaderController, SectionHeaderController sectionHeaderController2, SectionHeaderController sectionHeaderController3, SectionHeaderController sectionHeaderController4) {
        configurationController.getClass();
        notificationSectionsFeatureManager.getClass();
        mediaContainerController.getClass();
        notificationRoundnessManager.getClass();
        sectionHeaderController.getClass();
        sectionHeaderController2.getClass();
        sectionHeaderController3.getClass();
        sectionHeaderController4.getClass();
        this.configurationController = configurationController;
        this.sectionsFeatureManager = notificationSectionsFeatureManager;
        this.mediaContainerController = mediaContainerController;
        this.notificationRoundnessManager = notificationRoundnessManager;
        this.incomingHeaderController = sectionHeaderController;
        this.peopleHeaderController = sectionHeaderController2;
        this.alertingHeaderController = sectionHeaderController3;
        this.silentHeaderController = sectionHeaderController4;
        this.configurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.statusbar.notification.stack.NotificationSectionsManager$configurationListener$1
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public void onLocaleListChanged() {
                this.this$0.reinflateViews();
            }
        };
    }

    public static /* synthetic */ void getAlertingHeaderView$annotations() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Integer getBucket(View view) {
        if (view == getSilentHeaderView()) {
            return 6;
        }
        if (view == getIncomingHeaderView()) {
            return 2;
        }
        if (view == getMediaControlsView()) {
            return 1;
        }
        if (view == getPeopleHeaderView()) {
            return 4;
        }
        if (view == getAlertingHeaderView()) {
            return 5;
        }
        if (view instanceof ExpandableNotificationRow) {
            return Integer.valueOf(((ExpandableNotificationRow) view).getEntry().getBucket());
        }
        return null;
    }

    public static /* synthetic */ void getIncomingHeaderView$annotations() {
    }

    public static /* synthetic */ void getMediaControlsView$annotations() {
    }

    public static /* synthetic */ void getPeopleHeaderView$annotations() {
    }

    public static /* synthetic */ void getSilentHeaderView$annotations() {
    }

    @Override // com.android.systemui.statusbar.notification.stack.StackScrollAlgorithm.SectionProvider
    public boolean beginsSection(View view, View view2) {
        view.getClass();
        return view == getSilentHeaderView() || view == getMediaControlsView() || view == getPeopleHeaderView() || view == getAlertingHeaderView() || view == getIncomingHeaderView() || !Intrinsics.areEqual(getBucket(view), getBucket(view2));
    }

    public final NotificationSection[] createSectionsForBuckets() {
        int[] notificationBuckets = this.sectionsFeatureManager.getNotificationBuckets();
        ArrayList arrayList = new ArrayList(notificationBuckets.length);
        for (int i : notificationBuckets) {
            NotificationStackScrollLayout notificationStackScrollLayout = this.parent;
            if (notificationStackScrollLayout == null) {
                Intrinsics.throwUninitializedPropertyAccessException("parent");
                notificationStackScrollLayout = null;
            }
            arrayList.add(new NotificationSection(notificationStackScrollLayout, i));
        }
        return (NotificationSection[]) arrayList.toArray(new NotificationSection[0]);
    }

    public final SectionHeaderView getAlertingHeaderView() {
        return this.alertingHeaderController.getHeaderView();
    }

    public final SectionHeaderView getIncomingHeaderView() {
        return this.incomingHeaderController.getHeaderView();
    }

    public final MediaContainerView getMediaControlsView() {
        return this.mediaContainerController.getMediaContainerView();
    }

    public final SectionHeaderView getPeopleHeaderView() {
        return this.peopleHeaderController.getHeaderView();
    }

    public final SectionHeaderView getSilentHeaderView() {
        return this.silentHeaderController.getHeaderView();
    }

    public final void initialize(NotificationStackScrollLayout notificationStackScrollLayout) {
        notificationStackScrollLayout.getClass();
        if (this.initialized) {
            throw new IllegalStateException("NotificationSectionsManager already initialized");
        }
        this.initialized = true;
        this.parent = notificationStackScrollLayout;
        reinflateViews();
        this.configurationController.addCallback(this.configurationListener);
    }

    public final void reinflateViews() {
        SectionHeaderController sectionHeaderController = this.silentHeaderController;
        NotificationStackScrollLayout notificationStackScrollLayout = this.parent;
        NotificationStackScrollLayout notificationStackScrollLayout2 = null;
        if (notificationStackScrollLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("parent");
            notificationStackScrollLayout = null;
        }
        sectionHeaderController.reinflateView(notificationStackScrollLayout);
        SectionHeaderController sectionHeaderController2 = this.alertingHeaderController;
        NotificationStackScrollLayout notificationStackScrollLayout3 = this.parent;
        if (notificationStackScrollLayout3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("parent");
            notificationStackScrollLayout3 = null;
        }
        sectionHeaderController2.reinflateView(notificationStackScrollLayout3);
        SectionHeaderController sectionHeaderController3 = this.peopleHeaderController;
        NotificationStackScrollLayout notificationStackScrollLayout4 = this.parent;
        if (notificationStackScrollLayout4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("parent");
            notificationStackScrollLayout4 = null;
        }
        sectionHeaderController3.reinflateView(notificationStackScrollLayout4);
        SectionHeaderController sectionHeaderController4 = this.incomingHeaderController;
        NotificationStackScrollLayout notificationStackScrollLayout5 = this.parent;
        if (notificationStackScrollLayout5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("parent");
            notificationStackScrollLayout5 = null;
        }
        sectionHeaderController4.reinflateView(notificationStackScrollLayout5);
        MediaContainerController mediaContainerController = this.mediaContainerController;
        NotificationStackScrollLayout notificationStackScrollLayout6 = this.parent;
        if (notificationStackScrollLayout6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("parent");
        } else {
            notificationStackScrollLayout2 = notificationStackScrollLayout6;
        }
        mediaContainerController.reinflateView(notificationStackScrollLayout2);
    }

    public final void setHeaderForegroundColors(int i, int i2) {
        SectionHeaderView peopleHeaderView = getPeopleHeaderView();
        if (peopleHeaderView != null) {
            peopleHeaderView.setForegroundColors(i, i2);
        }
        SectionHeaderView silentHeaderView = getSilentHeaderView();
        if (silentHeaderView != null) {
            silentHeaderView.setForegroundColors(i, i2);
        }
        SectionHeaderView alertingHeaderView = getAlertingHeaderView();
        if (alertingHeaderView != null) {
            alertingHeaderView.setForegroundColors(i, i2);
        }
    }

    public final boolean updateFirstAndLastViewsForAllSections(NotificationSection[] notificationSectionArr, List list) {
        notificationSectionArr.getClass();
        list.getClass();
        final Sequence sequenceAsSequence = CollectionsKt.asSequence(list);
        Grouping grouping = new Grouping() { // from class: com.android.systemui.statusbar.notification.stack.NotificationSectionsManager$updateFirstAndLastViewsForAllSections$$inlined$groupingBy$1
            @Override // kotlin.collections.Grouping
            public Object keyOf(Object obj) {
                Integer bucket = this.getBucket((ExpandableView) obj);
                if (bucket != null) {
                    return bucket;
                }
                throw new IllegalArgumentException("Cannot find section bucket for view");
            }

            @Override // kotlin.collections.Grouping
            public Iterator sourceIterator() {
                return sequenceAsSequence.iterator();
            }
        };
        SectionBounds.None none = SectionBounds.None.INSTANCE;
        int length = notificationSectionArr.length;
        SparseArray sparseArray = length < 0 ? new SparseArray() : new SparseArray(length);
        Iterator itSourceIterator = grouping.sourceIterator();
        while (itSourceIterator.hasNext()) {
            Object next = itSourceIterator.next();
            int iIntValue = ((Number) grouping.keyOf(next)).intValue();
            Object obj = sparseArray.get(iIntValue);
            if (obj == null) {
                obj = none;
            }
            sparseArray.put(iIntValue, ((SectionBounds) obj).addNotif((ExpandableView) next));
        }
        ArrayList arrayList = new ArrayList();
        for (NotificationSection notificationSection : notificationSectionArr) {
            ExpandableView firstVisibleChild = notificationSection.getFirstVisibleChild();
            if (firstVisibleChild != null) {
                arrayList.add(firstVisibleChild);
            }
        }
        Set mutableSet = CollectionsKt.toMutableSet(CollectionsKt.toSet(arrayList));
        ArrayList arrayList2 = new ArrayList();
        for (NotificationSection notificationSection2 : notificationSectionArr) {
            ExpandableView lastVisibleChild = notificationSection2.getLastVisibleChild();
            if (lastVisibleChild != null) {
                arrayList2.add(lastVisibleChild);
            }
        }
        Set mutableSet2 = CollectionsKt.toMutableSet(CollectionsKt.toSet(arrayList2));
        boolean z = false;
        for (NotificationSection notificationSection3 : notificationSectionArr) {
            SectionBounds sectionBounds = (SectionBounds) sparseArray.get(notificationSection3.getBucket());
            if (sectionBounds == null) {
                sectionBounds = SectionBounds.None.INSTANCE;
            }
            z = sectionBounds.updateSection(notificationSection3) || z;
        }
        ArrayList arrayList3 = new ArrayList();
        for (NotificationSection notificationSection4 : notificationSectionArr) {
            ExpandableView firstVisibleChild2 = notificationSection4.getFirstVisibleChild();
            if (firstVisibleChild2 != null) {
                arrayList3.add(firstVisibleChild2);
            }
        }
        ArrayList arrayList4 = new ArrayList();
        for (NotificationSection notificationSection5 : notificationSectionArr) {
            ExpandableView lastVisibleChild2 = notificationSection5.getLastVisibleChild();
            if (lastVisibleChild2 != null) {
                arrayList4.add(lastVisibleChild2);
            }
        }
        int size = arrayList3.size();
        int i = 0;
        while (i < size) {
            Object obj2 = arrayList3.get(i);
            i++;
            ExpandableView expandableView = (ExpandableView) obj2;
            if (!mutableSet.remove(expandableView)) {
                expandableView.requestTopRoundness(1.0f, SECTION, expandableView.isShown() && !this.notificationRoundnessManager.isAnimatedChild(expandableView));
            }
        }
        int size2 = arrayList4.size();
        int i2 = 0;
        while (i2 < size2) {
            Object obj3 = arrayList4.get(i2);
            i2++;
            ExpandableView expandableView2 = (ExpandableView) obj3;
            if (!mutableSet2.remove(expandableView2)) {
                expandableView2.requestBottomRoundness(1.0f, SECTION, expandableView2.isShown() && !this.notificationRoundnessManager.isAnimatedChild(expandableView2));
            }
        }
        Iterator it = mutableSet.iterator();
        while (it.hasNext()) {
            ((ExpandableView) it.next()).requestTopRoundness(0.0f, SECTION);
        }
        Iterator it2 = mutableSet2.iterator();
        while (it2.hasNext()) {
            ((ExpandableView) it2.next()).requestBottomRoundness(0.0f, SECTION);
        }
        return z;
    }
}
