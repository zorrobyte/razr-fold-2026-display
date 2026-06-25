package androidx.compose.ui.input.rotary;

import androidx.compose.ui.Modifier;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: RotaryInputModifier.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class RotaryInputModifierKt {
    public static final Modifier onRotaryScrollEvent(Modifier modifier, Function1 function1) {
        return modifier.then(new RotaryInputElement(function1, null));
    }
}
