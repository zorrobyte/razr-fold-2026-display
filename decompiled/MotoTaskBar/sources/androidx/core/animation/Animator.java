package androidx.core.animation;

import androidx.core.animation.AnimationHandler;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public abstract class Animator implements Cloneable {
    ArrayList mListeners = null;
    ArrayList mPauseListeners = null;
    ArrayList mUpdateListeners = null;
    boolean mPaused = false;

    public interface AnimatorListener {
        void onAnimationCancel(Animator animator);

        void onAnimationEnd(Animator animator);

        default void onAnimationEnd(Animator animator, boolean z) {
            onAnimationEnd(animator);
        }

        void onAnimationRepeat(Animator animator);

        void onAnimationStart(Animator animator);

        default void onAnimationStart(Animator animator, boolean z) {
            onAnimationStart(animator);
        }
    }

    public interface AnimatorUpdateListener {
        void onAnimationUpdate(Animator animator);
    }

    static void addAnimationCallback(AnimationHandler.AnimationFrameCallback animationFrameCallback) {
        AnimationHandler.getInstance().addAnimationFrameCallback(animationFrameCallback);
    }

    static void removeAnimationCallback(AnimationHandler.AnimationFrameCallback animationFrameCallback) {
        AnimationHandler.getInstance().removeCallback(animationFrameCallback);
    }

    public void addListener(AnimatorListener animatorListener) {
        if (this.mListeners == null) {
            this.mListeners = new ArrayList();
        }
        this.mListeners.add(animatorListener);
    }

    public void addUpdateListener(AnimatorUpdateListener animatorUpdateListener) {
        if (this.mUpdateListeners == null) {
            this.mUpdateListeners = new ArrayList();
        }
        this.mUpdateListeners.add(animatorUpdateListener);
    }

    public abstract void cancel();

    public Animator clone() {
        try {
            Animator animator = (Animator) super.clone();
            if (this.mListeners != null) {
                animator.mListeners = new ArrayList(this.mListeners);
            }
            if (this.mPauseListeners != null) {
                animator.mPauseListeners = new ArrayList(this.mPauseListeners);
            }
            return animator;
        } catch (CloneNotSupportedException unused) {
            throw new AssertionError();
        }
    }

    public abstract void end();

    public abstract long getStartDelay();

    public abstract long getTotalDuration();

    abstract boolean isInitialized();

    public abstract boolean isStarted();

    abstract boolean pulseAnimationFrame(long j);

    public void removeListener(AnimatorListener animatorListener) {
        ArrayList arrayList = this.mListeners;
        if (arrayList == null) {
            return;
        }
        arrayList.remove(animatorListener);
        if (this.mListeners.size() == 0) {
            this.mListeners = null;
        }
    }

    abstract void reverse();

    public abstract Animator setDuration(long j);

    public abstract void setInterpolator(Interpolator interpolator);

    public abstract void setStartDelay(long j);

    abstract void skipToEndValue(boolean z);

    public abstract void start();

    abstract void startWithoutPulsing(boolean z);
}
