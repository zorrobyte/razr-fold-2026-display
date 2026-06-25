package androidx.compose.ui.text;

import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: TextLinkStyles.kt */
/* JADX INFO: loaded from: classes.dex */
public final class TextLinkStyles {
    private final SpanStyle focusedStyle;
    private final SpanStyle hoveredStyle;
    private final SpanStyle pressedStyle;
    private final SpanStyle style;

    public TextLinkStyles(SpanStyle spanStyle, SpanStyle spanStyle2, SpanStyle spanStyle3, SpanStyle spanStyle4) {
        this.style = spanStyle;
        this.focusedStyle = spanStyle2;
        this.hoveredStyle = spanStyle3;
        this.pressedStyle = spanStyle4;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof TextLinkStyles)) {
            return false;
        }
        TextLinkStyles textLinkStyles = (TextLinkStyles) obj;
        return Intrinsics.areEqual(this.style, textLinkStyles.style) && Intrinsics.areEqual(this.focusedStyle, textLinkStyles.focusedStyle) && Intrinsics.areEqual(this.hoveredStyle, textLinkStyles.hoveredStyle) && Intrinsics.areEqual(this.pressedStyle, textLinkStyles.pressedStyle);
    }

    public final SpanStyle getFocusedStyle() {
        return this.focusedStyle;
    }

    public final SpanStyle getHoveredStyle() {
        return this.hoveredStyle;
    }

    public final SpanStyle getPressedStyle() {
        return this.pressedStyle;
    }

    public final SpanStyle getStyle() {
        return this.style;
    }

    public int hashCode() {
        SpanStyle spanStyle = this.style;
        int iHashCode = (spanStyle != null ? spanStyle.hashCode() : 0) * 31;
        SpanStyle spanStyle2 = this.focusedStyle;
        int iHashCode2 = (iHashCode + (spanStyle2 != null ? spanStyle2.hashCode() : 0)) * 31;
        SpanStyle spanStyle3 = this.hoveredStyle;
        int iHashCode3 = (iHashCode2 + (spanStyle3 != null ? spanStyle3.hashCode() : 0)) * 31;
        SpanStyle spanStyle4 = this.pressedStyle;
        return iHashCode3 + (spanStyle4 != null ? spanStyle4.hashCode() : 0);
    }
}
