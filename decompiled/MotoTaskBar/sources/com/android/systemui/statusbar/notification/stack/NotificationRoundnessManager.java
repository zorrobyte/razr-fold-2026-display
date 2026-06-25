package com.android.systemui.statusbar.notification.stack;

import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.notification.Roundable;
import com.android.systemui.statusbar.notification.SourceType;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import java.util.HashSet;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
public class NotificationRoundnessManager implements Dumpable {
    private static final SourceType DISMISS_ANIMATION = SourceType.from("DismissAnimation");
    private HashSet mAnimatedChildren;
    private final DumpManager mDumpManager;
    private boolean mIsClearAllInProgress;
    private ExpandableView mSwipedView = null;
    private Roundable mViewBeforeSwipedView = null;
    private Roundable mViewAfterSwipedView = null;

    NotificationRoundnessManager(DumpManager dumpManager) {
        this.mDumpManager = dumpManager;
        dumpManager.registerDumpable("NotificationRoundnessManager", this);
    }

    public boolean isAnimatedChild(ExpandableView expandableView) {
        return this.mAnimatedChildren.contains(expandableView);
    }

    public boolean isClearAllInProgress() {
        return this.mIsClearAllInProgress;
    }

    public void setAnimatedChildren(HashSet hashSet) {
        this.mAnimatedChildren = hashSet;
    }

    void setClearAllInProgress(boolean z) {
        this.mIsClearAllInProgress = z;
    }

    void setViewsAffectedBySwipe(Roundable roundable, ExpandableView expandableView, Roundable roundable2) {
        HashSet hashSet = new HashSet();
        Roundable roundable3 = this.mViewBeforeSwipedView;
        if (roundable3 != null) {
            hashSet.add(roundable3);
        }
        ExpandableView expandableView2 = this.mSwipedView;
        if (expandableView2 != null) {
            hashSet.add(expandableView2);
        }
        Roundable roundable4 = this.mViewAfterSwipedView;
        if (roundable4 != null) {
            hashSet.add(roundable4);
        }
        this.mViewBeforeSwipedView = roundable;
        if (roundable != null) {
            hashSet.remove(roundable);
            roundable.requestRoundness(0.0f, 1.0f, DISMISS_ANIMATION);
        }
        this.mSwipedView = expandableView;
        if (expandableView != null) {
            hashSet.remove(expandableView);
            expandableView.requestRoundness(1.0f, 1.0f, DISMISS_ANIMATION);
        }
        this.mViewAfterSwipedView = roundable2;
        if (roundable2 != null) {
            hashSet.remove(roundable2);
            roundable2.requestRoundness(1.0f, 0.0f, DISMISS_ANIMATION);
        }
        Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            ((Roundable) it.next()).requestRoundnessReset(DISMISS_ANIMATION);
        }
    }
}
