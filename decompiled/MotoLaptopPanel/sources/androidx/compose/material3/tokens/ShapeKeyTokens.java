package androidx.compose.material3.tokens;

import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* JADX INFO: compiled from: ShapeKeyTokens.kt */
/* JADX INFO: loaded from: classes.dex */
public final class ShapeKeyTokens {
    private static final /* synthetic */ EnumEntries $ENTRIES;
    private static final /* synthetic */ ShapeKeyTokens[] $VALUES;
    public static final ShapeKeyTokens CornerExtraLarge = new ShapeKeyTokens("CornerExtraLarge", 0);
    public static final ShapeKeyTokens CornerExtraLargeTop = new ShapeKeyTokens("CornerExtraLargeTop", 1);
    public static final ShapeKeyTokens CornerExtraSmall = new ShapeKeyTokens("CornerExtraSmall", 2);
    public static final ShapeKeyTokens CornerExtraSmallTop = new ShapeKeyTokens("CornerExtraSmallTop", 3);
    public static final ShapeKeyTokens CornerFull = new ShapeKeyTokens("CornerFull", 4);
    public static final ShapeKeyTokens CornerLarge = new ShapeKeyTokens("CornerLarge", 5);
    public static final ShapeKeyTokens CornerLargeEnd = new ShapeKeyTokens("CornerLargeEnd", 6);
    public static final ShapeKeyTokens CornerLargeTop = new ShapeKeyTokens("CornerLargeTop", 7);
    public static final ShapeKeyTokens CornerMedium = new ShapeKeyTokens("CornerMedium", 8);
    public static final ShapeKeyTokens CornerNone = new ShapeKeyTokens("CornerNone", 9);
    public static final ShapeKeyTokens CornerSmall = new ShapeKeyTokens("CornerSmall", 10);

    private static final /* synthetic */ ShapeKeyTokens[] $values() {
        return new ShapeKeyTokens[]{CornerExtraLarge, CornerExtraLargeTop, CornerExtraSmall, CornerExtraSmallTop, CornerFull, CornerLarge, CornerLargeEnd, CornerLargeTop, CornerMedium, CornerNone, CornerSmall};
    }

    static {
        ShapeKeyTokens[] shapeKeyTokensArr$values = $values();
        $VALUES = shapeKeyTokensArr$values;
        $ENTRIES = EnumEntriesKt.enumEntries(shapeKeyTokensArr$values);
    }

    private ShapeKeyTokens(String str, int i) {
    }

    public static ShapeKeyTokens valueOf(String str) {
        return (ShapeKeyTokens) Enum.valueOf(ShapeKeyTokens.class, str);
    }

    public static ShapeKeyTokens[] values() {
        return (ShapeKeyTokens[]) $VALUES.clone();
    }
}
