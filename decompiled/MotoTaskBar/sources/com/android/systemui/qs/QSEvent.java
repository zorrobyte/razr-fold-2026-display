package com.android.systemui.qs;

import com.android.internal.logging.UiEventLogger;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* JADX INFO: compiled from: QSEvents.kt */
/* JADX INFO: loaded from: classes.dex */
public final class QSEvent implements UiEventLogger.UiEventEnum {
    private static final /* synthetic */ EnumEntries $ENTRIES;
    private static final /* synthetic */ QSEvent[] $VALUES;
    private final int _id;
    public static final QSEvent QS_ACTION_CLICK = new QSEvent("QS_ACTION_CLICK", 0, 387);
    public static final QSEvent QS_ACTION_SECONDARY_CLICK = new QSEvent("QS_ACTION_SECONDARY_CLICK", 1, 388);
    public static final QSEvent QS_ACTION_LONG_PRESS = new QSEvent("QS_ACTION_LONG_PRESS", 2, 389);
    public static final QSEvent QS_PANEL_EXPANDED = new QSEvent("QS_PANEL_EXPANDED", 3, 390);
    public static final QSEvent QS_PANEL_COLLAPSED = new QSEvent("QS_PANEL_COLLAPSED", 4, 391);
    public static final QSEvent QS_TILE_VISIBLE = new QSEvent("QS_TILE_VISIBLE", 5, 392);
    public static final QSEvent QQS_PANEL_EXPANDED = new QSEvent("QQS_PANEL_EXPANDED", 6, 393);
    public static final QSEvent QQS_PANEL_COLLAPSED = new QSEvent("QQS_PANEL_COLLAPSED", 7, 394);
    public static final QSEvent QQS_TILE_VISIBLE = new QSEvent("QQS_TILE_VISIBLE", 8, 395);

    private static final /* synthetic */ QSEvent[] $values() {
        return new QSEvent[]{QS_ACTION_CLICK, QS_ACTION_SECONDARY_CLICK, QS_ACTION_LONG_PRESS, QS_PANEL_EXPANDED, QS_PANEL_COLLAPSED, QS_TILE_VISIBLE, QQS_PANEL_EXPANDED, QQS_PANEL_COLLAPSED, QQS_TILE_VISIBLE};
    }

    static {
        QSEvent[] qSEventArr$values = $values();
        $VALUES = qSEventArr$values;
        $ENTRIES = EnumEntriesKt.enumEntries(qSEventArr$values);
    }

    private QSEvent(String str, int i, int i2) {
        this._id = i2;
    }

    public static QSEvent valueOf(String str) {
        return (QSEvent) Enum.valueOf(QSEvent.class, str);
    }

    public static QSEvent[] values() {
        return (QSEvent[]) $VALUES.clone();
    }

    public int getId() {
        return this._id;
    }
}
