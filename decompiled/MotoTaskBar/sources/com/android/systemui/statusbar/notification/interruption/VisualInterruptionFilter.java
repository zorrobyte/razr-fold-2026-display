package com.android.systemui.statusbar.notification.interruption;

import com.android.internal.logging.UiEventLogger;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.interruption.VisualInterruptionSuppressor;
import java.util.Set;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: VisualInterruptionSuppressor.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class VisualInterruptionFilter implements VisualInterruptionSuppressor {
    private final VisualInterruptionSuppressor.EventLogData eventLogData;
    private final String reason;
    private final Set types;
    private final UiEventLogger.UiEventEnum uiEventId;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public VisualInterruptionFilter(Set set, String str) {
        this(set, str, null, null, 8, null);
        set.getClass();
        str.getClass();
    }

    public VisualInterruptionFilter(Set set, String str, UiEventLogger.UiEventEnum uiEventEnum, VisualInterruptionSuppressor.EventLogData eventLogData) {
        set.getClass();
        str.getClass();
        this.types = set;
        this.reason = str;
        this.uiEventId = uiEventEnum;
        this.eventLogData = eventLogData;
    }

    public /* synthetic */ VisualInterruptionFilter(Set set, String str, UiEventLogger.UiEventEnum uiEventEnum, VisualInterruptionSuppressor.EventLogData eventLogData, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(set, str, (i & 4) != 0 ? null : uiEventEnum, (i & 8) != 0 ? null : eventLogData);
    }

    @Override // com.android.systemui.statusbar.notification.interruption.VisualInterruptionSuppressor
    public VisualInterruptionSuppressor.EventLogData getEventLogData() {
        return this.eventLogData;
    }

    @Override // com.android.systemui.statusbar.notification.interruption.VisualInterruptionSuppressor
    public String getReason() {
        return this.reason;
    }

    public Set getTypes() {
        return this.types;
    }

    @Override // com.android.systemui.statusbar.notification.interruption.VisualInterruptionSuppressor
    public UiEventLogger.UiEventEnum getUiEventId() {
        return this.uiEventId;
    }

    public abstract boolean shouldSuppress(NotificationEntry notificationEntry);
}
