package androidx.compose.material3;

import androidx.compose.foundation.interaction.InteractionSourceKt;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.ui.Modifier;

/* JADX INFO: compiled from: InteractionSourceModifierNode.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class InteractionSourceModifierNodeKt {
    public static final Modifier interactionSourceData(Modifier modifier, MutableInteractionSource mutableInteractionSource) {
        if (mutableInteractionSource == null) {
            mutableInteractionSource = InteractionSourceKt.MutableInteractionSource();
        }
        return modifier.then(new InteractionSourceModifierElement(mutableInteractionSource));
    }
}
