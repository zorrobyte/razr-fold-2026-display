package androidx.compose.foundation;

import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* JADX INFO: compiled from: MutatorMutex.kt */
/* JADX INFO: loaded from: classes.dex */
public final class MutatePriority {
    private static final /* synthetic */ EnumEntries $ENTRIES;
    private static final /* synthetic */ MutatePriority[] $VALUES;
    public static final MutatePriority Default = new MutatePriority("Default", 0);
    public static final MutatePriority UserInput = new MutatePriority("UserInput", 1);
    public static final MutatePriority PreventUserInput = new MutatePriority("PreventUserInput", 2);

    private static final /* synthetic */ MutatePriority[] $values() {
        return new MutatePriority[]{Default, UserInput, PreventUserInput};
    }

    static {
        MutatePriority[] mutatePriorityArr$values = $values();
        $VALUES = mutatePriorityArr$values;
        $ENTRIES = EnumEntriesKt.enumEntries(mutatePriorityArr$values);
    }

    private MutatePriority(String str, int i) {
    }

    public static MutatePriority valueOf(String str) {
        return (MutatePriority) Enum.valueOf(MutatePriority.class, str);
    }

    public static MutatePriority[] values() {
        return (MutatePriority[]) $VALUES.clone();
    }
}
