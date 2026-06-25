package com.android.systemui.statusbar.notification.interruption;

import android.app.Notification;
import android.app.NotificationChannel;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.util.settings.SystemSettings;
import com.android.systemui.util.time.SystemClock;
import kotlin.collections.SetsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: CommonVisualInterruptionSuppressors.kt */
/* JADX INFO: loaded from: classes.dex */
public final class AvalancheSuppressor extends VisualInterruptionFilter {
    private final String TAG;
    private final AvalancheProvider avalancheProvider;
    private String reason;
    private final SystemClock systemClock;
    private final SystemSettings systemSettings;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* JADX INFO: compiled from: CommonVisualInterruptionSuppressors.kt */
    public final class State {
        private static final /* synthetic */ EnumEntries $ENTRIES;
        private static final /* synthetic */ State[] $VALUES;
        public static final State ALLOW_CONVERSATION_AFTER_AVALANCHE = new State("ALLOW_CONVERSATION_AFTER_AVALANCHE", 0);
        public static final State ALLOW_HIGH_PRIORITY_CONVERSATION_ANY_TIME = new State("ALLOW_HIGH_PRIORITY_CONVERSATION_ANY_TIME", 1);
        public static final State ALLOW_CALLSTYLE = new State("ALLOW_CALLSTYLE", 2);
        public static final State ALLOW_CATEGORY_REMINDER = new State("ALLOW_CATEGORY_REMINDER", 3);
        public static final State ALLOW_CATEGORY_EVENT = new State("ALLOW_CATEGORY_EVENT", 4);
        public static final State ALLOW_FSI_WITH_PERMISSION_ON = new State("ALLOW_FSI_WITH_PERMISSION_ON", 5);
        public static final State ALLOW_COLORIZED = new State("ALLOW_COLORIZED", 6);
        public static final State SUPPRESS = new State("SUPPRESS", 7);

        private static final /* synthetic */ State[] $values() {
            return new State[]{ALLOW_CONVERSATION_AFTER_AVALANCHE, ALLOW_HIGH_PRIORITY_CONVERSATION_ANY_TIME, ALLOW_CALLSTYLE, ALLOW_CATEGORY_REMINDER, ALLOW_CATEGORY_EVENT, ALLOW_FSI_WITH_PERMISSION_ON, ALLOW_COLORIZED, SUPPRESS};
        }

        static {
            State[] stateArr$values = $values();
            $VALUES = stateArr$values;
            $ENTRIES = EnumEntriesKt.enumEntries(stateArr$values);
        }

        private State(String str, int i) {
        }

        public static State valueOf(String str) {
            return (State) Enum.valueOf(State.class, str);
        }

        public static State[] values() {
            return (State[]) $VALUES.clone();
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AvalancheSuppressor(AvalancheProvider avalancheProvider, SystemClock systemClock, SystemSettings systemSettings) {
        super(SetsKt.setOf((Object[]) new VisualInterruptionType[]{VisualInterruptionType.PEEK, VisualInterruptionType.PULSE}), "avalanche");
        avalancheProvider.getClass();
        systemClock.getClass();
        systemSettings.getClass();
        this.avalancheProvider = avalancheProvider;
        this.systemClock = systemClock;
        this.systemSettings = systemSettings;
        this.TAG = "AvalancheSuppressor";
        this.reason = "avalanche";
    }

    private final State calculateState(NotificationEntry notificationEntry) {
        if (notificationEntry.getRanking().isConversation() && notificationEntry.getSbn().getNotification().when > this.avalancheProvider.getStartTime()) {
            return State.ALLOW_CONVERSATION_AFTER_AVALANCHE;
        }
        NotificationChannel channel = notificationEntry.getChannel();
        return (channel == null || !channel.isImportantConversation()) ? notificationEntry.getSbn().getNotification().isStyle(Notification.CallStyle.class) ? State.ALLOW_CALLSTYLE : Intrinsics.areEqual(notificationEntry.getSbn().getNotification().category, "reminder") ? State.ALLOW_CATEGORY_REMINDER : Intrinsics.areEqual(notificationEntry.getSbn().getNotification().category, "event") ? State.ALLOW_CATEGORY_EVENT : notificationEntry.getSbn().getNotification().fullScreenIntent != null ? State.ALLOW_FSI_WITH_PERMISSION_ON : notificationEntry.getSbn().getNotification().isColorized() ? State.ALLOW_COLORIZED : State.SUPPRESS : State.ALLOW_HIGH_PRIORITY_CONVERSATION_ANY_TIME;
    }

    private final boolean isCooldownEnabled() {
        return this.systemSettings.getInt("notification_cooldown_enabled", 1) == 1;
    }

    @Override // com.android.systemui.statusbar.notification.interruption.VisualInterruptionFilter, com.android.systemui.statusbar.notification.interruption.VisualInterruptionSuppressor
    public String getReason() {
        return this.reason;
    }

    protected void setReason(String str) {
        str.getClass();
        this.reason = str;
    }

    @Override // com.android.systemui.statusbar.notification.interruption.VisualInterruptionFilter
    public boolean shouldSuppress(NotificationEntry notificationEntry) {
        notificationEntry.getClass();
        if (!isCooldownEnabled()) {
            setReason("FALSE avalanche cooldown setting DISABLED");
            return false;
        }
        long jCurrentTimeMillis = this.systemClock.currentTimeMillis() - this.avalancheProvider.getStartTime();
        if (jCurrentTimeMillis >= this.avalancheProvider.getTimeoutMs()) {
            setReason("FALSE avalanche event TIMED OUT. " + (jCurrentTimeMillis / ((long) 1000)) + " seconds since last avalanche");
            return false;
        }
        State stateCalculateState = calculateState(notificationEntry);
        if (stateCalculateState == State.SUPPRESS) {
            return true;
        }
        setReason("FALSE avalanche IN ALLOWLIST: " + stateCalculateState);
        return false;
    }
}
