package androidx.compose.animation.core;

import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* JADX INFO: compiled from: AnimationSpec.kt */
/* JADX INFO: loaded from: classes.dex */
public final class RepeatMode {
    private static final /* synthetic */ EnumEntries $ENTRIES;
    private static final /* synthetic */ RepeatMode[] $VALUES;
    public static final RepeatMode Restart = new RepeatMode("Restart", 0);
    public static final RepeatMode Reverse = new RepeatMode("Reverse", 1);

    private static final /* synthetic */ RepeatMode[] $values() {
        return new RepeatMode[]{Restart, Reverse};
    }

    static {
        RepeatMode[] repeatModeArr$values = $values();
        $VALUES = repeatModeArr$values;
        $ENTRIES = EnumEntriesKt.enumEntries(repeatModeArr$values);
    }

    private RepeatMode(String str, int i) {
    }

    public static RepeatMode valueOf(String str) {
        return (RepeatMode) Enum.valueOf(RepeatMode.class, str);
    }

    public static RepeatMode[] values() {
        return (RepeatMode[]) $VALUES.clone();
    }
}
