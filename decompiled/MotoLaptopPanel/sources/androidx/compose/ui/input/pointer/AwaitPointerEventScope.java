package androidx.compose.ui.input.pointer;

import androidx.compose.ui.platform.ViewConfiguration;
import androidx.compose.ui.unit.Density;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function2;

/* JADX INFO: compiled from: SuspendingPointerInputFilter.kt */
/* JADX INFO: loaded from: classes.dex */
public interface AwaitPointerEventScope extends Density {
    static /* synthetic */ Object awaitPointerEvent$default(AwaitPointerEventScope awaitPointerEventScope, PointerEventPass pointerEventPass, Continuation continuation, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: awaitPointerEvent");
        }
        if ((i & 1) != 0) {
            pointerEventPass = PointerEventPass.Main;
        }
        return awaitPointerEventScope.awaitPointerEvent(pointerEventPass, continuation);
    }

    Object awaitPointerEvent(PointerEventPass pointerEventPass, Continuation continuation);

    PointerEvent getCurrentEvent();

    /* JADX INFO: renamed from: getExtendedTouchPadding-NH-jbRc, reason: not valid java name */
    long mo1197getExtendedTouchPaddingNHjbRc();

    /* JADX INFO: renamed from: getSize-YbymL2g, reason: not valid java name */
    long mo1198getSizeYbymL2g();

    ViewConfiguration getViewConfiguration();

    Object withTimeout(long j, Function2 function2, Continuation continuation);

    Object withTimeoutOrNull(long j, Function2 function2, Continuation continuation);
}
