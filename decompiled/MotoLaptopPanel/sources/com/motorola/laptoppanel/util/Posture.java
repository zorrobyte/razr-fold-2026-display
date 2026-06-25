package com.motorola.laptoppanel.util;

import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* JADX INFO: compiled from: PostureMonitor.kt */
/* JADX INFO: loaded from: classes.dex */
public final class Posture {
    private static final /* synthetic */ EnumEntries $ENTRIES;
    private static final /* synthetic */ Posture[] $VALUES;
    public static final Posture LAPTOP = new Posture("LAPTOP", 0);
    public static final Posture BOOK = new Posture("BOOK", 1);
    public static final Posture UNKNOWN = new Posture("UNKNOWN", 2);

    private static final /* synthetic */ Posture[] $values() {
        return new Posture[]{LAPTOP, BOOK, UNKNOWN};
    }

    static {
        Posture[] postureArr$values = $values();
        $VALUES = postureArr$values;
        $ENTRIES = EnumEntriesKt.enumEntries(postureArr$values);
    }

    private Posture(String str, int i) {
    }

    public static Posture valueOf(String str) {
        return (Posture) Enum.valueOf(Posture.class, str);
    }

    public static Posture[] values() {
        return (Posture[]) $VALUES.clone();
    }
}
