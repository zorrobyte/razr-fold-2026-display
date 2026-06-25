package com.android.systemui.statusbar.notification.interruption;

import com.android.internal.logging.UiEventLogger;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: VisualInterruptionSuppressor.kt */
/* JADX INFO: loaded from: classes.dex */
public interface VisualInterruptionSuppressor {

    /* JADX INFO: compiled from: VisualInterruptionSuppressor.kt */
    public final class EventLogData {
        private final String description;
        private final String number;

        public EventLogData(String str, String str2) {
            str.getClass();
            str2.getClass();
            this.number = str;
            this.description = str2;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof EventLogData)) {
                return false;
            }
            EventLogData eventLogData = (EventLogData) obj;
            return Intrinsics.areEqual(this.number, eventLogData.number) && Intrinsics.areEqual(this.description, eventLogData.description);
        }

        public int hashCode() {
            return (this.number.hashCode() * 31) + this.description.hashCode();
        }

        public String toString() {
            return "EventLogData(number=" + this.number + ", description=" + this.description + ")";
        }
    }

    EventLogData getEventLogData();

    String getReason();

    UiEventLogger.UiEventEnum getUiEventId();

    default void start() {
    }
}
