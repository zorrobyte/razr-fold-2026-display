package androidx.compose.foundation.text.modifiers;

import androidx.compose.runtime.RememberObserver;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.layout.LayoutCoordinates;
import androidx.compose.ui.text.TextLayoutResult;

/* JADX INFO: compiled from: SelectionController.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class SelectionController implements RememberObserver {
    public abstract void draw(DrawScope drawScope);

    public abstract Modifier getModifier();

    public abstract void updateGlobalPosition(LayoutCoordinates layoutCoordinates);

    public abstract void updateTextLayout(TextLayoutResult textLayoutResult);
}
