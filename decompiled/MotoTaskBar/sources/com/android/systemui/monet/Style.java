package com.android.systemui.monet;

import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* JADX INFO: compiled from: ColorScheme.kt */
/* JADX INFO: loaded from: classes.dex */
public final class Style {
    private static final /* synthetic */ EnumEntries $ENTRIES;
    private static final /* synthetic */ Style[] $VALUES;
    public static final Style SPRITZ = new Style("SPRITZ", 0);
    public static final Style TONAL_SPOT = new Style("TONAL_SPOT", 1);
    public static final Style VIBRANT = new Style("VIBRANT", 2);
    public static final Style EXPRESSIVE = new Style("EXPRESSIVE", 3);
    public static final Style RAINBOW = new Style("RAINBOW", 4);
    public static final Style FRUIT_SALAD = new Style("FRUIT_SALAD", 5);
    public static final Style CONTENT = new Style("CONTENT", 6);
    public static final Style MONOCHROMATIC = new Style("MONOCHROMATIC", 7);

    private static final /* synthetic */ Style[] $values() {
        return new Style[]{SPRITZ, TONAL_SPOT, VIBRANT, EXPRESSIVE, RAINBOW, FRUIT_SALAD, CONTENT, MONOCHROMATIC};
    }

    static {
        Style[] styleArr$values = $values();
        $VALUES = styleArr$values;
        $ENTRIES = EnumEntriesKt.enumEntries(styleArr$values);
    }

    private Style(String str, int i) {
    }

    public static Style valueOf(String str) {
        return (Style) Enum.valueOf(Style.class, str);
    }

    public static Style[] values() {
        return (Style[]) $VALUES.clone();
    }
}
