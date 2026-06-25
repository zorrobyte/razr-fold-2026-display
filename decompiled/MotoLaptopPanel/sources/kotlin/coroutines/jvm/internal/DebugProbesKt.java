package kotlin.coroutines.jvm.internal;

import kotlin.coroutines.Continuation;

/* JADX INFO: compiled from: DebugProbes.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class DebugProbesKt {
    public static final Continuation probeCoroutineCreated(Continuation continuation) {
        continuation.getClass();
        return continuation;
    }

    public static final void probeCoroutineResumed(Continuation continuation) {
        continuation.getClass();
    }

    public static final void probeCoroutineSuspended(Continuation continuation) {
        continuation.getClass();
    }
}
