package com.android.systemui.statusbar.notification.interruption;

import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* JADX INFO: compiled from: VisualInterruptionSuppressor.kt */
/* JADX INFO: loaded from: classes.dex */
public final class VisualInterruptionType {
    private static final /* synthetic */ EnumEntries $ENTRIES;
    private static final /* synthetic */ VisualInterruptionType[] $VALUES;
    public static final VisualInterruptionType PEEK = new VisualInterruptionType("PEEK", 0);
    public static final VisualInterruptionType PULSE = new VisualInterruptionType("PULSE", 1);
    public static final VisualInterruptionType BUBBLE = new VisualInterruptionType("BUBBLE", 2);

    private static final /* synthetic */ VisualInterruptionType[] $values() {
        return new VisualInterruptionType[]{PEEK, PULSE, BUBBLE};
    }

    static {
        VisualInterruptionType[] visualInterruptionTypeArr$values = $values();
        $VALUES = visualInterruptionTypeArr$values;
        $ENTRIES = EnumEntriesKt.enumEntries(visualInterruptionTypeArr$values);
    }

    private VisualInterruptionType(String str, int i) {
    }

    public static VisualInterruptionType valueOf(String str) {
        return (VisualInterruptionType) Enum.valueOf(VisualInterruptionType.class, str);
    }

    public static VisualInterruptionType[] values() {
        return (VisualInterruptionType[]) $VALUES.clone();
    }
}
