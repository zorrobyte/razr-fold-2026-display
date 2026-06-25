package kotlin.coroutines;

/* JADX INFO: compiled from: Continuation.kt */
/* JADX INFO: loaded from: classes.dex */
public interface Continuation {
    CoroutineContext getContext();

    void resumeWith(Object obj);
}
