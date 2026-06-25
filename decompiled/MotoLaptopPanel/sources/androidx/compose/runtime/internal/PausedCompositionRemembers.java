package androidx.compose.runtime.internal;

import androidx.compose.runtime.RememberObserver;
import androidx.compose.runtime.collection.MutableVector;

/* JADX INFO: compiled from: RememberEventDispatcher.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class PausedCompositionRemembers implements RememberObserver {
    public abstract MutableVector getPausedRemembers();
}
