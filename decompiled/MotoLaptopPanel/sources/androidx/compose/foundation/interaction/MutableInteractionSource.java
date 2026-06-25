package androidx.compose.foundation.interaction;

import kotlin.coroutines.Continuation;

/* JADX INFO: compiled from: InteractionSource.kt */
/* JADX INFO: loaded from: classes.dex */
public interface MutableInteractionSource extends InteractionSource {
    Object emit(Interaction interaction, Continuation continuation);

    boolean tryEmit(Interaction interaction);
}
