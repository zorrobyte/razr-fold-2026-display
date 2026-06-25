package com.android.systemui.qs;

import com.android.internal.logging.UiEventLogger;
import com.android.internal.logging.UiEventLoggerImpl;

/* JADX INFO: compiled from: QSEvents.kt */
/* JADX INFO: loaded from: classes.dex */
public final class QSEvents {
    public static final QSEvents INSTANCE = new QSEvents();
    private static UiEventLogger qsUiEventsLogger = new UiEventLoggerImpl();

    private QSEvents() {
    }

    public final UiEventLogger getQsUiEventsLogger() {
        return qsUiEventsLogger;
    }
}
