package androidx.compose.ui.state;

import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* JADX INFO: compiled from: ToggleableState.kt */
/* JADX INFO: loaded from: classes.dex */
public final class ToggleableState {
    private static final /* synthetic */ EnumEntries $ENTRIES;
    private static final /* synthetic */ ToggleableState[] $VALUES;
    public static final ToggleableState On = new ToggleableState("On", 0);
    public static final ToggleableState Off = new ToggleableState("Off", 1);
    public static final ToggleableState Indeterminate = new ToggleableState("Indeterminate", 2);

    private static final /* synthetic */ ToggleableState[] $values() {
        return new ToggleableState[]{On, Off, Indeterminate};
    }

    static {
        ToggleableState[] toggleableStateArr$values = $values();
        $VALUES = toggleableStateArr$values;
        $ENTRIES = EnumEntriesKt.enumEntries(toggleableStateArr$values);
    }

    private ToggleableState(String str, int i) {
    }

    public static ToggleableState valueOf(String str) {
        return (ToggleableState) Enum.valueOf(ToggleableState.class, str);
    }

    public static ToggleableState[] values() {
        return (ToggleableState[]) $VALUES.clone();
    }
}
