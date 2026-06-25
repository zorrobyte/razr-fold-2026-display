package androidx.compose.material3.tokens;

/* JADX INFO: compiled from: IconButtonTokens.kt */
/* JADX INFO: loaded from: classes.dex */
public final class StandardIconButtonTokens {
    private static final ColorSchemeKeyTokens Color;
    private static final ColorSchemeKeyTokens FocusedColor;
    private static final ColorSchemeKeyTokens HoveredColor;
    private static final ColorSchemeKeyTokens PressedColor;
    private static final ColorSchemeKeyTokens SelectedColor;
    private static final ColorSchemeKeyTokens SelectedFocusedColor;
    private static final ColorSchemeKeyTokens SelectedHoveredColor;
    private static final ColorSchemeKeyTokens SelectedPressedColor;
    private static final ColorSchemeKeyTokens UnselectedColor;
    private static final ColorSchemeKeyTokens UnselectedFocusedColor;
    private static final ColorSchemeKeyTokens UnselectedHoveredColor;
    private static final ColorSchemeKeyTokens UnselectedPressedColor;
    public static final StandardIconButtonTokens INSTANCE = new StandardIconButtonTokens();
    private static final ColorSchemeKeyTokens DisabledColor = ColorSchemeKeyTokens.OnSurface;
    private static final float DisabledOpacity = 0.38f;

    static {
        ColorSchemeKeyTokens colorSchemeKeyTokens = ColorSchemeKeyTokens.OnSurfaceVariant;
        FocusedColor = colorSchemeKeyTokens;
        HoveredColor = colorSchemeKeyTokens;
        Color = colorSchemeKeyTokens;
        PressedColor = colorSchemeKeyTokens;
        ColorSchemeKeyTokens colorSchemeKeyTokens2 = ColorSchemeKeyTokens.Primary;
        SelectedFocusedColor = colorSchemeKeyTokens2;
        SelectedHoveredColor = colorSchemeKeyTokens2;
        SelectedColor = colorSchemeKeyTokens2;
        SelectedPressedColor = colorSchemeKeyTokens2;
        UnselectedFocusedColor = colorSchemeKeyTokens;
        UnselectedHoveredColor = colorSchemeKeyTokens;
        UnselectedColor = colorSchemeKeyTokens;
        UnselectedPressedColor = colorSchemeKeyTokens;
    }

    private StandardIconButtonTokens() {
    }

    public final float getDisabledOpacity() {
        return DisabledOpacity;
    }
}
