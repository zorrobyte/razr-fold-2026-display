package androidx.compose.ui.input.pointer;

import kotlin.coroutines.Continuation;

/* JADX INFO: compiled from: SuspendingPointerInputFilter.kt */
/* JADX INFO: loaded from: classes.dex */
public interface PointerInputEventHandler {
    Object invoke(PointerInputScope pointerInputScope, Continuation continuation);
}
