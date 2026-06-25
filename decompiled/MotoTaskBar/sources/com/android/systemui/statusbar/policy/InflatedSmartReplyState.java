package com.android.systemui.statusbar.policy;

import com.android.systemui.statusbar.policy.SmartReplyView;
import java.util.List;
import kotlin.collections.CollectionsKt;

/* JADX INFO: compiled from: InflatedSmartReplyState.kt */
/* JADX INFO: loaded from: classes.dex */
public final class InflatedSmartReplyState {
    private final boolean hasPhishingAction;
    private final SmartReplyView.SmartActions smartActions;
    private final SmartReplyView.SmartReplies smartReplies;
    private final SuppressedActions suppressedActions;

    /* JADX INFO: compiled from: InflatedSmartReplyState.kt */
    public final class SuppressedActions {
        private final List suppressedActionIndices;

        public SuppressedActions(List list) {
            list.getClass();
            this.suppressedActionIndices = list;
        }

        public final List getSuppressedActionIndices() {
            return this.suppressedActionIndices;
        }
    }

    public InflatedSmartReplyState(SmartReplyView.SmartReplies smartReplies, SmartReplyView.SmartActions smartActions, SuppressedActions suppressedActions, boolean z) {
        this.smartReplies = smartReplies;
        this.smartActions = smartActions;
        this.suppressedActions = suppressedActions;
        this.hasPhishingAction = z;
    }

    public final boolean getHasPhishingAction() {
        return this.hasPhishingAction;
    }

    public final SmartReplyView.SmartActions getSmartActions() {
        return this.smartActions;
    }

    public final List getSmartActionsList() {
        List list;
        SmartReplyView.SmartActions smartActions = this.smartActions;
        return (smartActions == null || (list = smartActions.actions) == null) ? CollectionsKt.emptyList() : list;
    }

    public final SmartReplyView.SmartReplies getSmartReplies() {
        return this.smartReplies;
    }

    public final List getSmartRepliesList() {
        List list;
        SmartReplyView.SmartReplies smartReplies = this.smartReplies;
        return (smartReplies == null || (list = smartReplies.choices) == null) ? CollectionsKt.emptyList() : list;
    }

    public final List getSuppressedActionIndices() {
        List suppressedActionIndices;
        SuppressedActions suppressedActions = this.suppressedActions;
        return (suppressedActions == null || (suppressedActionIndices = suppressedActions.getSuppressedActionIndices()) == null) ? CollectionsKt.emptyList() : suppressedActionIndices;
    }
}
