package androidx.compose.foundation.text.selection;

import androidx.compose.ui.graphics.Color;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: TextSelectionColors.kt */
/* JADX INFO: loaded from: classes.dex */
public final class TextSelectionColors {
    private final long backgroundColor;
    private final long handleColor;

    private TextSelectionColors(long j, long j2) {
        this.handleColor = j;
        this.backgroundColor = j2;
    }

    public /* synthetic */ TextSelectionColors(long j, long j2, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, j2);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TextSelectionColors)) {
            return false;
        }
        TextSelectionColors textSelectionColors = (TextSelectionColors) obj;
        return Color.m882equalsimpl0(this.handleColor, textSelectionColors.handleColor) && Color.m882equalsimpl0(this.backgroundColor, textSelectionColors.backgroundColor);
    }

    public int hashCode() {
        return (Color.m888hashCodeimpl(this.handleColor) * 31) + Color.m888hashCodeimpl(this.backgroundColor);
    }

    public String toString() {
        return "SelectionColors(selectionHandleColor=" + ((Object) Color.m889toStringimpl(this.handleColor)) + ", selectionBackgroundColor=" + ((Object) Color.m889toStringimpl(this.backgroundColor)) + ')';
    }
}
