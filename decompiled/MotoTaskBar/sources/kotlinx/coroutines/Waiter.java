package kotlinx.coroutines;

import kotlinx.coroutines.internal.Segment;

/* JADX INFO: compiled from: Waiter.kt */
/* JADX INFO: loaded from: classes2.dex */
public interface Waiter {
    void invokeOnCancellation(Segment segment, int i);
}
