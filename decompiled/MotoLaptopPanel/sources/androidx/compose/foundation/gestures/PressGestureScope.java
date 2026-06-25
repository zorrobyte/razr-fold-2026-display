package androidx.compose.foundation.gestures;

import androidx.compose.ui.unit.Density;
import kotlin.coroutines.Continuation;

/* JADX INFO: compiled from: TapGestureDetector.kt */
/* JADX INFO: loaded from: classes.dex */
public interface PressGestureScope extends Density {
    Object tryAwaitRelease(Continuation continuation);
}
