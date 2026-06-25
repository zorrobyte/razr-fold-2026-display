package com.android.systemui.statusbar.policy;

import java.util.List;

/* JADX INFO: compiled from: InflatedSmartReplyViewHolder.kt */
/* JADX INFO: loaded from: classes.dex */
public final class InflatedSmartReplyViewHolder {
    private final SmartReplyView smartReplyView;
    private final List smartSuggestionButtons;

    public InflatedSmartReplyViewHolder(SmartReplyView smartReplyView, List list) {
        this.smartReplyView = smartReplyView;
        this.smartSuggestionButtons = list;
    }

    public final SmartReplyView getSmartReplyView() {
        return this.smartReplyView;
    }

    public final List getSmartSuggestionButtons() {
        return this.smartSuggestionButtons;
    }
}
