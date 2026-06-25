package androidx.transition;

/* JADX INFO: loaded from: classes.dex */
public interface TransitionSeekController {
    void animateToEnd();

    void animateToStart(Runnable runnable);

    long getDurationMillis();

    boolean isReady();

    void setCurrentPlayTimeMillis(long j);
}
