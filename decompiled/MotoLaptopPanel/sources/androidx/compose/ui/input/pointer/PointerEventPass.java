package androidx.compose.ui.input.pointer;

import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* JADX INFO: compiled from: PointerEvent.kt */
/* JADX INFO: loaded from: classes.dex */
public final class PointerEventPass {
    private static final /* synthetic */ EnumEntries $ENTRIES;
    private static final /* synthetic */ PointerEventPass[] $VALUES;
    public static final PointerEventPass Initial = new PointerEventPass("Initial", 0);
    public static final PointerEventPass Main = new PointerEventPass("Main", 1);
    public static final PointerEventPass Final = new PointerEventPass("Final", 2);

    private static final /* synthetic */ PointerEventPass[] $values() {
        return new PointerEventPass[]{Initial, Main, Final};
    }

    static {
        PointerEventPass[] pointerEventPassArr$values = $values();
        $VALUES = pointerEventPassArr$values;
        $ENTRIES = EnumEntriesKt.enumEntries(pointerEventPassArr$values);
    }

    private PointerEventPass(String str, int i) {
    }

    public static PointerEventPass valueOf(String str) {
        return (PointerEventPass) Enum.valueOf(PointerEventPass.class, str);
    }

    public static PointerEventPass[] values() {
        return (PointerEventPass[]) $VALUES.clone();
    }
}
