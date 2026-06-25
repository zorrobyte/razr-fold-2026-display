package androidx.compose.material3.tokens;

import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* JADX INFO: compiled from: TypographyKeyTokens.kt */
/* JADX INFO: loaded from: classes.dex */
public final class TypographyKeyTokens {
    private static final /* synthetic */ EnumEntries $ENTRIES;
    private static final /* synthetic */ TypographyKeyTokens[] $VALUES;
    public static final TypographyKeyTokens BodyLarge = new TypographyKeyTokens("BodyLarge", 0);
    public static final TypographyKeyTokens BodyMedium = new TypographyKeyTokens("BodyMedium", 1);
    public static final TypographyKeyTokens BodySmall = new TypographyKeyTokens("BodySmall", 2);
    public static final TypographyKeyTokens DisplayLarge = new TypographyKeyTokens("DisplayLarge", 3);
    public static final TypographyKeyTokens DisplayMedium = new TypographyKeyTokens("DisplayMedium", 4);
    public static final TypographyKeyTokens DisplaySmall = new TypographyKeyTokens("DisplaySmall", 5);
    public static final TypographyKeyTokens HeadlineLarge = new TypographyKeyTokens("HeadlineLarge", 6);
    public static final TypographyKeyTokens HeadlineMedium = new TypographyKeyTokens("HeadlineMedium", 7);
    public static final TypographyKeyTokens HeadlineSmall = new TypographyKeyTokens("HeadlineSmall", 8);
    public static final TypographyKeyTokens LabelLarge = new TypographyKeyTokens("LabelLarge", 9);
    public static final TypographyKeyTokens LabelMedium = new TypographyKeyTokens("LabelMedium", 10);
    public static final TypographyKeyTokens LabelSmall = new TypographyKeyTokens("LabelSmall", 11);
    public static final TypographyKeyTokens TitleLarge = new TypographyKeyTokens("TitleLarge", 12);
    public static final TypographyKeyTokens TitleMedium = new TypographyKeyTokens("TitleMedium", 13);
    public static final TypographyKeyTokens TitleSmall = new TypographyKeyTokens("TitleSmall", 14);
    public static final TypographyKeyTokens BodyLargeEmphasized = new TypographyKeyTokens("BodyLargeEmphasized", 15);
    public static final TypographyKeyTokens BodyMediumEmphasized = new TypographyKeyTokens("BodyMediumEmphasized", 16);
    public static final TypographyKeyTokens BodySmallEmphasized = new TypographyKeyTokens("BodySmallEmphasized", 17);
    public static final TypographyKeyTokens DisplayLargeEmphasized = new TypographyKeyTokens("DisplayLargeEmphasized", 18);
    public static final TypographyKeyTokens DisplayMediumEmphasized = new TypographyKeyTokens("DisplayMediumEmphasized", 19);
    public static final TypographyKeyTokens DisplaySmallEmphasized = new TypographyKeyTokens("DisplaySmallEmphasized", 20);
    public static final TypographyKeyTokens HeadlineLargeEmphasized = new TypographyKeyTokens("HeadlineLargeEmphasized", 21);
    public static final TypographyKeyTokens HeadlineMediumEmphasized = new TypographyKeyTokens("HeadlineMediumEmphasized", 22);
    public static final TypographyKeyTokens HeadlineSmallEmphasized = new TypographyKeyTokens("HeadlineSmallEmphasized", 23);
    public static final TypographyKeyTokens LabelLargeEmphasized = new TypographyKeyTokens("LabelLargeEmphasized", 24);
    public static final TypographyKeyTokens LabelMediumEmphasized = new TypographyKeyTokens("LabelMediumEmphasized", 25);
    public static final TypographyKeyTokens LabelSmallEmphasized = new TypographyKeyTokens("LabelSmallEmphasized", 26);
    public static final TypographyKeyTokens TitleLargeEmphasized = new TypographyKeyTokens("TitleLargeEmphasized", 27);
    public static final TypographyKeyTokens TitleMediumEmphasized = new TypographyKeyTokens("TitleMediumEmphasized", 28);
    public static final TypographyKeyTokens TitleSmallEmphasized = new TypographyKeyTokens("TitleSmallEmphasized", 29);

    private static final /* synthetic */ TypographyKeyTokens[] $values() {
        return new TypographyKeyTokens[]{BodyLarge, BodyMedium, BodySmall, DisplayLarge, DisplayMedium, DisplaySmall, HeadlineLarge, HeadlineMedium, HeadlineSmall, LabelLarge, LabelMedium, LabelSmall, TitleLarge, TitleMedium, TitleSmall, BodyLargeEmphasized, BodyMediumEmphasized, BodySmallEmphasized, DisplayLargeEmphasized, DisplayMediumEmphasized, DisplaySmallEmphasized, HeadlineLargeEmphasized, HeadlineMediumEmphasized, HeadlineSmallEmphasized, LabelLargeEmphasized, LabelMediumEmphasized, LabelSmallEmphasized, TitleLargeEmphasized, TitleMediumEmphasized, TitleSmallEmphasized};
    }

    static {
        TypographyKeyTokens[] typographyKeyTokensArr$values = $values();
        $VALUES = typographyKeyTokensArr$values;
        $ENTRIES = EnumEntriesKt.enumEntries(typographyKeyTokensArr$values);
    }

    private TypographyKeyTokens(String str, int i) {
    }

    public static TypographyKeyTokens valueOf(String str) {
        return (TypographyKeyTokens) Enum.valueOf(TypographyKeyTokens.class, str);
    }

    public static TypographyKeyTokens[] values() {
        return (TypographyKeyTokens[]) $VALUES.clone();
    }
}
