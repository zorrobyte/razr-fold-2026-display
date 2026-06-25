package androidx.compose.ui.draw;

import androidx.compose.ui.node.ModifierNodeElement;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: DrawModifier.kt */
/* JADX INFO: loaded from: classes.dex */
final class DrawBehindElement extends ModifierNodeElement {
    private final Function1 onDraw;

    public DrawBehindElement(Function1 function1) {
        this.onDraw = function1;
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public DrawBackgroundModifier create() {
        return new DrawBackgroundModifier(this.onDraw);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof DrawBehindElement) && this.onDraw == ((DrawBehindElement) obj).onDraw;
    }

    public int hashCode() {
        return this.onDraw.hashCode();
    }

    @Override // androidx.compose.ui.node.ModifierNodeElement
    public void update(DrawBackgroundModifier drawBackgroundModifier) {
        drawBackgroundModifier.setOnDraw(this.onDraw);
    }
}
