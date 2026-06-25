package com.android.systemui.dump;

import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* JADX INFO: compiled from: DumpManager.kt */
/* JADX INFO: loaded from: classes.dex */
public final class DumpPriority {
    private static final /* synthetic */ EnumEntries $ENTRIES;
    private static final /* synthetic */ DumpPriority[] $VALUES;
    public static final DumpPriority CRITICAL = new DumpPriority("CRITICAL", 0);
    public static final DumpPriority NORMAL = new DumpPriority("NORMAL", 1);

    private static final /* synthetic */ DumpPriority[] $values() {
        return new DumpPriority[]{CRITICAL, NORMAL};
    }

    static {
        DumpPriority[] dumpPriorityArr$values = $values();
        $VALUES = dumpPriorityArr$values;
        $ENTRIES = EnumEntriesKt.enumEntries(dumpPriorityArr$values);
    }

    private DumpPriority(String str, int i) {
    }

    public static DumpPriority valueOf(String str) {
        return (DumpPriority) Enum.valueOf(DumpPriority.class, str);
    }

    public static DumpPriority[] values() {
        return (DumpPriority[]) $VALUES.clone();
    }
}
