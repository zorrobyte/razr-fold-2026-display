package androidx.compose.material3.tokens;

import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.font.GenericFontFamily;

/* JADX INFO: compiled from: TypographyTokens.kt */
/* JADX INFO: loaded from: classes.dex */
public final class TypographyTokens {
    private static final TextStyle BodyLarge;
    private static final TextStyle BodyLargeEmphasized;
    private static final TextStyle BodyMedium;
    private static final TextStyle BodyMediumEmphasized;
    private static final TextStyle BodySmall;
    private static final TextStyle BodySmallEmphasized;
    private static final TextStyle DisplayLarge;
    private static final TextStyle DisplayLargeEmphasized;
    private static final TextStyle DisplayMedium;
    private static final TextStyle DisplayMediumEmphasized;
    private static final TextStyle DisplaySmall;
    private static final TextStyle DisplaySmallEmphasized;
    private static final TextStyle HeadlineLarge;
    private static final TextStyle HeadlineLargeEmphasized;
    private static final TextStyle HeadlineMedium;
    private static final TextStyle HeadlineMediumEmphasized;
    private static final TextStyle HeadlineSmall;
    private static final TextStyle HeadlineSmallEmphasized;
    public static final TypographyTokens INSTANCE = new TypographyTokens();
    private static final TextStyle LabelLarge;
    private static final TextStyle LabelLargeEmphasized;
    private static final TextStyle LabelMedium;
    private static final TextStyle LabelMediumEmphasized;
    private static final TextStyle LabelSmall;
    private static final TextStyle LabelSmallEmphasized;
    private static final TextStyle TitleLarge;
    private static final TextStyle TitleLargeEmphasized;
    private static final TextStyle TitleMedium;
    private static final TextStyle TitleMediumEmphasized;
    private static final TextStyle TitleSmall;
    private static final TextStyle TitleSmallEmphasized;

    static {
        TextStyle defaultTextStyle = TypographyTokensKt.getDefaultTextStyle();
        TypeScaleTokens typeScaleTokens = TypeScaleTokens.INSTANCE;
        GenericFontFamily bodyLargeFont = typeScaleTokens.getBodyLargeFont();
        BodyLarge = TextStyle.m1602copyp1EtxEg$default(defaultTextStyle, 0L, typeScaleTokens.m488getBodyLargeSizeXSAIIZE(), typeScaleTokens.getBodyLargeWeight(), null, null, bodyLargeFont, null, typeScaleTokens.m489getBodyLargeTrackingXSAIIZE(), null, null, null, 0L, null, null, null, 0, 0, typeScaleTokens.m487getBodyLargeLineHeightXSAIIZE(), null, null, null, 0, 0, null, 16645977, null);
        TextStyle defaultTextStyle2 = TypographyTokensKt.getDefaultTextStyle();
        GenericFontFamily bodyMediumFont = typeScaleTokens.getBodyMediumFont();
        BodyMedium = TextStyle.m1602copyp1EtxEg$default(defaultTextStyle2, 0L, typeScaleTokens.m494getBodyMediumSizeXSAIIZE(), typeScaleTokens.getBodyMediumWeight(), null, null, bodyMediumFont, null, typeScaleTokens.m495getBodyMediumTrackingXSAIIZE(), null, null, null, 0L, null, null, null, 0, 0, typeScaleTokens.m493getBodyMediumLineHeightXSAIIZE(), null, null, null, 0, 0, null, 16645977, null);
        TextStyle defaultTextStyle3 = TypographyTokensKt.getDefaultTextStyle();
        GenericFontFamily bodySmallFont = typeScaleTokens.getBodySmallFont();
        BodySmall = TextStyle.m1602copyp1EtxEg$default(defaultTextStyle3, 0L, typeScaleTokens.m500getBodySmallSizeXSAIIZE(), typeScaleTokens.getBodySmallWeight(), null, null, bodySmallFont, null, typeScaleTokens.m501getBodySmallTrackingXSAIIZE(), null, null, null, 0L, null, null, null, 0, 0, typeScaleTokens.m499getBodySmallLineHeightXSAIIZE(), null, null, null, 0, 0, null, 16645977, null);
        TextStyle defaultTextStyle4 = TypographyTokensKt.getDefaultTextStyle();
        GenericFontFamily displayLargeFont = typeScaleTokens.getDisplayLargeFont();
        DisplayLarge = TextStyle.m1602copyp1EtxEg$default(defaultTextStyle4, 0L, typeScaleTokens.m506getDisplayLargeSizeXSAIIZE(), typeScaleTokens.getDisplayLargeWeight(), null, null, displayLargeFont, null, typeScaleTokens.m507getDisplayLargeTrackingXSAIIZE(), null, null, null, 0L, null, null, null, 0, 0, typeScaleTokens.m505getDisplayLargeLineHeightXSAIIZE(), null, null, null, 0, 0, null, 16645977, null);
        TextStyle defaultTextStyle5 = TypographyTokensKt.getDefaultTextStyle();
        GenericFontFamily displayMediumFont = typeScaleTokens.getDisplayMediumFont();
        DisplayMedium = TextStyle.m1602copyp1EtxEg$default(defaultTextStyle5, 0L, typeScaleTokens.m512getDisplayMediumSizeXSAIIZE(), typeScaleTokens.getDisplayMediumWeight(), null, null, displayMediumFont, null, typeScaleTokens.m513getDisplayMediumTrackingXSAIIZE(), null, null, null, 0L, null, null, null, 0, 0, typeScaleTokens.m511getDisplayMediumLineHeightXSAIIZE(), null, null, null, 0, 0, null, 16645977, null);
        TextStyle defaultTextStyle6 = TypographyTokensKt.getDefaultTextStyle();
        GenericFontFamily displaySmallFont = typeScaleTokens.getDisplaySmallFont();
        DisplaySmall = TextStyle.m1602copyp1EtxEg$default(defaultTextStyle6, 0L, typeScaleTokens.m518getDisplaySmallSizeXSAIIZE(), typeScaleTokens.getDisplaySmallWeight(), null, null, displaySmallFont, null, typeScaleTokens.m519getDisplaySmallTrackingXSAIIZE(), null, null, null, 0L, null, null, null, 0, 0, typeScaleTokens.m517getDisplaySmallLineHeightXSAIIZE(), null, null, null, 0, 0, null, 16645977, null);
        TextStyle defaultTextStyle7 = TypographyTokensKt.getDefaultTextStyle();
        GenericFontFamily headlineLargeFont = typeScaleTokens.getHeadlineLargeFont();
        HeadlineLarge = TextStyle.m1602copyp1EtxEg$default(defaultTextStyle7, 0L, typeScaleTokens.m524getHeadlineLargeSizeXSAIIZE(), typeScaleTokens.getHeadlineLargeWeight(), null, null, headlineLargeFont, null, typeScaleTokens.m525getHeadlineLargeTrackingXSAIIZE(), null, null, null, 0L, null, null, null, 0, 0, typeScaleTokens.m523getHeadlineLargeLineHeightXSAIIZE(), null, null, null, 0, 0, null, 16645977, null);
        TextStyle defaultTextStyle8 = TypographyTokensKt.getDefaultTextStyle();
        GenericFontFamily headlineMediumFont = typeScaleTokens.getHeadlineMediumFont();
        HeadlineMedium = TextStyle.m1602copyp1EtxEg$default(defaultTextStyle8, 0L, typeScaleTokens.m530getHeadlineMediumSizeXSAIIZE(), typeScaleTokens.getHeadlineMediumWeight(), null, null, headlineMediumFont, null, typeScaleTokens.m531getHeadlineMediumTrackingXSAIIZE(), null, null, null, 0L, null, null, null, 0, 0, typeScaleTokens.m529getHeadlineMediumLineHeightXSAIIZE(), null, null, null, 0, 0, null, 16645977, null);
        TextStyle defaultTextStyle9 = TypographyTokensKt.getDefaultTextStyle();
        GenericFontFamily headlineSmallFont = typeScaleTokens.getHeadlineSmallFont();
        HeadlineSmall = TextStyle.m1602copyp1EtxEg$default(defaultTextStyle9, 0L, typeScaleTokens.m536getHeadlineSmallSizeXSAIIZE(), typeScaleTokens.getHeadlineSmallWeight(), null, null, headlineSmallFont, null, typeScaleTokens.m537getHeadlineSmallTrackingXSAIIZE(), null, null, null, 0L, null, null, null, 0, 0, typeScaleTokens.m535getHeadlineSmallLineHeightXSAIIZE(), null, null, null, 0, 0, null, 16645977, null);
        TextStyle defaultTextStyle10 = TypographyTokensKt.getDefaultTextStyle();
        GenericFontFamily labelLargeFont = typeScaleTokens.getLabelLargeFont();
        LabelLarge = TextStyle.m1602copyp1EtxEg$default(defaultTextStyle10, 0L, typeScaleTokens.m542getLabelLargeSizeXSAIIZE(), typeScaleTokens.getLabelLargeWeight(), null, null, labelLargeFont, null, typeScaleTokens.m543getLabelLargeTrackingXSAIIZE(), null, null, null, 0L, null, null, null, 0, 0, typeScaleTokens.m541getLabelLargeLineHeightXSAIIZE(), null, null, null, 0, 0, null, 16645977, null);
        TextStyle defaultTextStyle11 = TypographyTokensKt.getDefaultTextStyle();
        GenericFontFamily labelMediumFont = typeScaleTokens.getLabelMediumFont();
        LabelMedium = TextStyle.m1602copyp1EtxEg$default(defaultTextStyle11, 0L, typeScaleTokens.m548getLabelMediumSizeXSAIIZE(), typeScaleTokens.getLabelMediumWeight(), null, null, labelMediumFont, null, typeScaleTokens.m549getLabelMediumTrackingXSAIIZE(), null, null, null, 0L, null, null, null, 0, 0, typeScaleTokens.m547getLabelMediumLineHeightXSAIIZE(), null, null, null, 0, 0, null, 16645977, null);
        TextStyle defaultTextStyle12 = TypographyTokensKt.getDefaultTextStyle();
        GenericFontFamily labelSmallFont = typeScaleTokens.getLabelSmallFont();
        LabelSmall = TextStyle.m1602copyp1EtxEg$default(defaultTextStyle12, 0L, typeScaleTokens.m554getLabelSmallSizeXSAIIZE(), typeScaleTokens.getLabelSmallWeight(), null, null, labelSmallFont, null, typeScaleTokens.m555getLabelSmallTrackingXSAIIZE(), null, null, null, 0L, null, null, null, 0, 0, typeScaleTokens.m553getLabelSmallLineHeightXSAIIZE(), null, null, null, 0, 0, null, 16645977, null);
        TextStyle defaultTextStyle13 = TypographyTokensKt.getDefaultTextStyle();
        GenericFontFamily titleLargeFont = typeScaleTokens.getTitleLargeFont();
        TitleLarge = TextStyle.m1602copyp1EtxEg$default(defaultTextStyle13, 0L, typeScaleTokens.m560getTitleLargeSizeXSAIIZE(), typeScaleTokens.getTitleLargeWeight(), null, null, titleLargeFont, null, typeScaleTokens.m561getTitleLargeTrackingXSAIIZE(), null, null, null, 0L, null, null, null, 0, 0, typeScaleTokens.m559getTitleLargeLineHeightXSAIIZE(), null, null, null, 0, 0, null, 16645977, null);
        TextStyle defaultTextStyle14 = TypographyTokensKt.getDefaultTextStyle();
        GenericFontFamily titleMediumFont = typeScaleTokens.getTitleMediumFont();
        TitleMedium = TextStyle.m1602copyp1EtxEg$default(defaultTextStyle14, 0L, typeScaleTokens.m566getTitleMediumSizeXSAIIZE(), typeScaleTokens.getTitleMediumWeight(), null, null, titleMediumFont, null, typeScaleTokens.m567getTitleMediumTrackingXSAIIZE(), null, null, null, 0L, null, null, null, 0, 0, typeScaleTokens.m565getTitleMediumLineHeightXSAIIZE(), null, null, null, 0, 0, null, 16645977, null);
        TextStyle defaultTextStyle15 = TypographyTokensKt.getDefaultTextStyle();
        GenericFontFamily titleSmallFont = typeScaleTokens.getTitleSmallFont();
        TitleSmall = TextStyle.m1602copyp1EtxEg$default(defaultTextStyle15, 0L, typeScaleTokens.m572getTitleSmallSizeXSAIIZE(), typeScaleTokens.getTitleSmallWeight(), null, null, titleSmallFont, null, typeScaleTokens.m573getTitleSmallTrackingXSAIIZE(), null, null, null, 0L, null, null, null, 0, 0, typeScaleTokens.m571getTitleSmallLineHeightXSAIIZE(), null, null, null, 0, 0, null, 16645977, null);
        TextStyle defaultTextStyle16 = TypographyTokensKt.getDefaultTextStyle();
        GenericFontFamily bodyLargeEmphasizedFont = typeScaleTokens.getBodyLargeEmphasizedFont();
        BodyLargeEmphasized = TextStyle.m1602copyp1EtxEg$default(defaultTextStyle16, 0L, typeScaleTokens.m485getBodyLargeEmphasizedSizeXSAIIZE(), typeScaleTokens.getBodyLargeEmphasizedWeight(), null, null, bodyLargeEmphasizedFont, null, typeScaleTokens.m486getBodyLargeEmphasizedTrackingXSAIIZE(), null, null, null, 0L, null, null, null, 0, 0, typeScaleTokens.m484getBodyLargeEmphasizedLineHeightXSAIIZE(), null, null, null, 0, 0, null, 16645977, null);
        TextStyle defaultTextStyle17 = TypographyTokensKt.getDefaultTextStyle();
        GenericFontFamily bodyMediumEmphasizedFont = typeScaleTokens.getBodyMediumEmphasizedFont();
        BodyMediumEmphasized = TextStyle.m1602copyp1EtxEg$default(defaultTextStyle17, 0L, typeScaleTokens.m491getBodyMediumEmphasizedSizeXSAIIZE(), typeScaleTokens.getBodyMediumEmphasizedWeight(), null, null, bodyMediumEmphasizedFont, null, typeScaleTokens.m492getBodyMediumEmphasizedTrackingXSAIIZE(), null, null, null, 0L, null, null, null, 0, 0, typeScaleTokens.m490getBodyMediumEmphasizedLineHeightXSAIIZE(), null, null, null, 0, 0, null, 16645977, null);
        TextStyle defaultTextStyle18 = TypographyTokensKt.getDefaultTextStyle();
        GenericFontFamily bodySmallEmphasizedFont = typeScaleTokens.getBodySmallEmphasizedFont();
        BodySmallEmphasized = TextStyle.m1602copyp1EtxEg$default(defaultTextStyle18, 0L, typeScaleTokens.m497getBodySmallEmphasizedSizeXSAIIZE(), typeScaleTokens.getBodySmallEmphasizedWeight(), null, null, bodySmallEmphasizedFont, null, typeScaleTokens.m498getBodySmallEmphasizedTrackingXSAIIZE(), null, null, null, 0L, null, null, null, 0, 0, typeScaleTokens.m496getBodySmallEmphasizedLineHeightXSAIIZE(), null, null, null, 0, 0, null, 16645977, null);
        TextStyle defaultTextStyle19 = TypographyTokensKt.getDefaultTextStyle();
        GenericFontFamily displayLargeEmphasizedFont = typeScaleTokens.getDisplayLargeEmphasizedFont();
        DisplayLargeEmphasized = TextStyle.m1602copyp1EtxEg$default(defaultTextStyle19, 0L, typeScaleTokens.m503getDisplayLargeEmphasizedSizeXSAIIZE(), typeScaleTokens.getDisplayLargeEmphasizedWeight(), null, null, displayLargeEmphasizedFont, null, typeScaleTokens.m504getDisplayLargeEmphasizedTrackingXSAIIZE(), null, null, null, 0L, null, null, null, 0, 0, typeScaleTokens.m502getDisplayLargeEmphasizedLineHeightXSAIIZE(), null, null, null, 0, 0, null, 16645977, null);
        TextStyle defaultTextStyle20 = TypographyTokensKt.getDefaultTextStyle();
        GenericFontFamily displayMediumEmphasizedFont = typeScaleTokens.getDisplayMediumEmphasizedFont();
        DisplayMediumEmphasized = TextStyle.m1602copyp1EtxEg$default(defaultTextStyle20, 0L, typeScaleTokens.m509getDisplayMediumEmphasizedSizeXSAIIZE(), typeScaleTokens.getDisplayMediumEmphasizedWeight(), null, null, displayMediumEmphasizedFont, null, typeScaleTokens.m510getDisplayMediumEmphasizedTrackingXSAIIZE(), null, null, null, 0L, null, null, null, 0, 0, typeScaleTokens.m508getDisplayMediumEmphasizedLineHeightXSAIIZE(), null, null, null, 0, 0, null, 16645977, null);
        TextStyle defaultTextStyle21 = TypographyTokensKt.getDefaultTextStyle();
        GenericFontFamily displaySmallEmphasizedFont = typeScaleTokens.getDisplaySmallEmphasizedFont();
        DisplaySmallEmphasized = TextStyle.m1602copyp1EtxEg$default(defaultTextStyle21, 0L, typeScaleTokens.m515getDisplaySmallEmphasizedSizeXSAIIZE(), typeScaleTokens.getDisplaySmallEmphasizedWeight(), null, null, displaySmallEmphasizedFont, null, typeScaleTokens.m516getDisplaySmallEmphasizedTrackingXSAIIZE(), null, null, null, 0L, null, null, null, 0, 0, typeScaleTokens.m514getDisplaySmallEmphasizedLineHeightXSAIIZE(), null, null, null, 0, 0, null, 16645977, null);
        TextStyle defaultTextStyle22 = TypographyTokensKt.getDefaultTextStyle();
        GenericFontFamily headlineLargeEmphasizedFont = typeScaleTokens.getHeadlineLargeEmphasizedFont();
        HeadlineLargeEmphasized = TextStyle.m1602copyp1EtxEg$default(defaultTextStyle22, 0L, typeScaleTokens.m521getHeadlineLargeEmphasizedSizeXSAIIZE(), typeScaleTokens.getHeadlineLargeEmphasizedWeight(), null, null, headlineLargeEmphasizedFont, null, typeScaleTokens.m522getHeadlineLargeEmphasizedTrackingXSAIIZE(), null, null, null, 0L, null, null, null, 0, 0, typeScaleTokens.m520getHeadlineLargeEmphasizedLineHeightXSAIIZE(), null, null, null, 0, 0, null, 16645977, null);
        TextStyle defaultTextStyle23 = TypographyTokensKt.getDefaultTextStyle();
        GenericFontFamily headlineMediumEmphasizedFont = typeScaleTokens.getHeadlineMediumEmphasizedFont();
        HeadlineMediumEmphasized = TextStyle.m1602copyp1EtxEg$default(defaultTextStyle23, 0L, typeScaleTokens.m527getHeadlineMediumEmphasizedSizeXSAIIZE(), typeScaleTokens.getHeadlineMediumEmphasizedWeight(), null, null, headlineMediumEmphasizedFont, null, typeScaleTokens.m528getHeadlineMediumEmphasizedTrackingXSAIIZE(), null, null, null, 0L, null, null, null, 0, 0, typeScaleTokens.m526getHeadlineMediumEmphasizedLineHeightXSAIIZE(), null, null, null, 0, 0, null, 16645977, null);
        TextStyle defaultTextStyle24 = TypographyTokensKt.getDefaultTextStyle();
        GenericFontFamily headlineSmallEmphasizedFont = typeScaleTokens.getHeadlineSmallEmphasizedFont();
        HeadlineSmallEmphasized = TextStyle.m1602copyp1EtxEg$default(defaultTextStyle24, 0L, typeScaleTokens.m533getHeadlineSmallEmphasizedSizeXSAIIZE(), typeScaleTokens.getHeadlineSmallEmphasizedWeight(), null, null, headlineSmallEmphasizedFont, null, typeScaleTokens.m534getHeadlineSmallEmphasizedTrackingXSAIIZE(), null, null, null, 0L, null, null, null, 0, 0, typeScaleTokens.m532getHeadlineSmallEmphasizedLineHeightXSAIIZE(), null, null, null, 0, 0, null, 16645977, null);
        TextStyle defaultTextStyle25 = TypographyTokensKt.getDefaultTextStyle();
        GenericFontFamily labelLargeEmphasizedFont = typeScaleTokens.getLabelLargeEmphasizedFont();
        LabelLargeEmphasized = TextStyle.m1602copyp1EtxEg$default(defaultTextStyle25, 0L, typeScaleTokens.m539getLabelLargeEmphasizedSizeXSAIIZE(), typeScaleTokens.getLabelLargeEmphasizedWeight(), null, null, labelLargeEmphasizedFont, null, typeScaleTokens.m540getLabelLargeEmphasizedTrackingXSAIIZE(), null, null, null, 0L, null, null, null, 0, 0, typeScaleTokens.m538getLabelLargeEmphasizedLineHeightXSAIIZE(), null, null, null, 0, 0, null, 16645977, null);
        TextStyle defaultTextStyle26 = TypographyTokensKt.getDefaultTextStyle();
        GenericFontFamily labelMediumEmphasizedFont = typeScaleTokens.getLabelMediumEmphasizedFont();
        LabelMediumEmphasized = TextStyle.m1602copyp1EtxEg$default(defaultTextStyle26, 0L, typeScaleTokens.m545getLabelMediumEmphasizedSizeXSAIIZE(), typeScaleTokens.getLabelMediumEmphasizedWeight(), null, null, labelMediumEmphasizedFont, null, typeScaleTokens.m546getLabelMediumEmphasizedTrackingXSAIIZE(), null, null, null, 0L, null, null, null, 0, 0, typeScaleTokens.m544getLabelMediumEmphasizedLineHeightXSAIIZE(), null, null, null, 0, 0, null, 16645977, null);
        TextStyle defaultTextStyle27 = TypographyTokensKt.getDefaultTextStyle();
        GenericFontFamily labelSmallEmphasizedFont = typeScaleTokens.getLabelSmallEmphasizedFont();
        LabelSmallEmphasized = TextStyle.m1602copyp1EtxEg$default(defaultTextStyle27, 0L, typeScaleTokens.m551getLabelSmallEmphasizedSizeXSAIIZE(), typeScaleTokens.getLabelSmallEmphasizedWeight(), null, null, labelSmallEmphasizedFont, null, typeScaleTokens.m552getLabelSmallEmphasizedTrackingXSAIIZE(), null, null, null, 0L, null, null, null, 0, 0, typeScaleTokens.m550getLabelSmallEmphasizedLineHeightXSAIIZE(), null, null, null, 0, 0, null, 16645977, null);
        TextStyle defaultTextStyle28 = TypographyTokensKt.getDefaultTextStyle();
        GenericFontFamily titleLargeEmphasizedFont = typeScaleTokens.getTitleLargeEmphasizedFont();
        TitleLargeEmphasized = TextStyle.m1602copyp1EtxEg$default(defaultTextStyle28, 0L, typeScaleTokens.m557getTitleLargeEmphasizedSizeXSAIIZE(), typeScaleTokens.getTitleLargeEmphasizedWeight(), null, null, titleLargeEmphasizedFont, null, typeScaleTokens.m558getTitleLargeEmphasizedTrackingXSAIIZE(), null, null, null, 0L, null, null, null, 0, 0, typeScaleTokens.m556getTitleLargeEmphasizedLineHeightXSAIIZE(), null, null, null, 0, 0, null, 16645977, null);
        TextStyle defaultTextStyle29 = TypographyTokensKt.getDefaultTextStyle();
        GenericFontFamily titleMediumEmphasizedFont = typeScaleTokens.getTitleMediumEmphasizedFont();
        TitleMediumEmphasized = TextStyle.m1602copyp1EtxEg$default(defaultTextStyle29, 0L, typeScaleTokens.m563getTitleMediumEmphasizedSizeXSAIIZE(), typeScaleTokens.getTitleMediumEmphasizedWeight(), null, null, titleMediumEmphasizedFont, null, typeScaleTokens.m564getTitleMediumEmphasizedTrackingXSAIIZE(), null, null, null, 0L, null, null, null, 0, 0, typeScaleTokens.m562getTitleMediumEmphasizedLineHeightXSAIIZE(), null, null, null, 0, 0, null, 16645977, null);
        TextStyle defaultTextStyle30 = TypographyTokensKt.getDefaultTextStyle();
        GenericFontFamily titleSmallEmphasizedFont = typeScaleTokens.getTitleSmallEmphasizedFont();
        TitleSmallEmphasized = TextStyle.m1602copyp1EtxEg$default(defaultTextStyle30, 0L, typeScaleTokens.m569getTitleSmallEmphasizedSizeXSAIIZE(), typeScaleTokens.getTitleSmallEmphasizedWeight(), null, null, titleSmallEmphasizedFont, null, typeScaleTokens.m570getTitleSmallEmphasizedTrackingXSAIIZE(), null, null, null, 0L, null, null, null, 0, 0, typeScaleTokens.m568getTitleSmallEmphasizedLineHeightXSAIIZE(), null, null, null, 0, 0, null, 16645977, null);
    }

    private TypographyTokens() {
    }

    public final TextStyle getBodyLarge() {
        return BodyLarge;
    }

    public final TextStyle getBodyMedium() {
        return BodyMedium;
    }

    public final TextStyle getBodySmall() {
        return BodySmall;
    }

    public final TextStyle getDisplayLarge() {
        return DisplayLarge;
    }

    public final TextStyle getDisplayMedium() {
        return DisplayMedium;
    }

    public final TextStyle getDisplaySmall() {
        return DisplaySmall;
    }

    public final TextStyle getHeadlineLarge() {
        return HeadlineLarge;
    }

    public final TextStyle getHeadlineMedium() {
        return HeadlineMedium;
    }

    public final TextStyle getHeadlineSmall() {
        return HeadlineSmall;
    }

    public final TextStyle getLabelLarge() {
        return LabelLarge;
    }

    public final TextStyle getLabelMedium() {
        return LabelMedium;
    }

    public final TextStyle getLabelSmall() {
        return LabelSmall;
    }

    public final TextStyle getTitleLarge() {
        return TitleLarge;
    }

    public final TextStyle getTitleMedium() {
        return TitleMedium;
    }

    public final TextStyle getTitleSmall() {
        return TitleSmall;
    }
}
