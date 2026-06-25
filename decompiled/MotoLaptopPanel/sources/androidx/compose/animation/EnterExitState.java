package androidx.compose.animation;

import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* JADX INFO: compiled from: AnimatedVisibility.kt */
/* JADX INFO: loaded from: classes.dex */
public final class EnterExitState {
    private static final /* synthetic */ EnumEntries $ENTRIES;
    private static final /* synthetic */ EnterExitState[] $VALUES;
    public static final EnterExitState PreEnter = new EnterExitState("PreEnter", 0);
    public static final EnterExitState Visible = new EnterExitState("Visible", 1);
    public static final EnterExitState PostExit = new EnterExitState("PostExit", 2);

    private static final /* synthetic */ EnterExitState[] $values() {
        return new EnterExitState[]{PreEnter, Visible, PostExit};
    }

    static {
        EnterExitState[] enterExitStateArr$values = $values();
        $VALUES = enterExitStateArr$values;
        $ENTRIES = EnumEntriesKt.enumEntries(enterExitStateArr$values);
    }

    private EnterExitState(String str, int i) {
    }

    public static EnterExitState valueOf(String str) {
        return (EnterExitState) Enum.valueOf(EnterExitState.class, str);
    }

    public static EnterExitState[] values() {
        return (EnterExitState[]) $VALUES.clone();
    }
}
