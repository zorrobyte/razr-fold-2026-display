package androidx.core.animation;

import android.view.Choreographer;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
class AnimationHandler {
    public static final ThreadLocal sAnimationHandler = new ThreadLocal();
    private static AnimationHandler sTestHandler = null;
    private final ArrayList mAnimationCallbacks = new ArrayList();
    boolean mListDirty = false;
    private final AnimationFrameCallbackProvider mProvider;

    interface AnimationFrameCallback {
        boolean doAnimationFrame(long j);
    }

    interface AnimationFrameCallbackProvider {
        void onNewCallbackAdded(AnimationFrameCallback animationFrameCallback);

        void postFrameCallback();
    }

    class FrameCallbackProvider16 implements AnimationFrameCallbackProvider, Choreographer.FrameCallback {
        FrameCallbackProvider16() {
        }

        @Override // android.view.Choreographer.FrameCallback
        public void doFrame(long j) {
            AnimationHandler.this.onAnimationFrame(j / 1000000);
        }

        @Override // androidx.core.animation.AnimationHandler.AnimationFrameCallbackProvider
        public void onNewCallbackAdded(AnimationFrameCallback animationFrameCallback) {
        }

        @Override // androidx.core.animation.AnimationHandler.AnimationFrameCallbackProvider
        public void postFrameCallback() {
            Choreographer.getInstance().postFrameCallback(this);
        }
    }

    AnimationHandler(AnimationFrameCallbackProvider animationFrameCallbackProvider) {
        if (animationFrameCallbackProvider == null) {
            this.mProvider = new FrameCallbackProvider16();
        } else {
            this.mProvider = animationFrameCallbackProvider;
        }
    }

    private void cleanUpList() {
        if (this.mListDirty) {
            for (int size = this.mAnimationCallbacks.size() - 1; size >= 0; size--) {
                if (this.mAnimationCallbacks.get(size) == null) {
                    this.mAnimationCallbacks.remove(size);
                }
            }
            this.mListDirty = false;
        }
    }

    private void doAnimationFrame(long j) {
        for (int i = 0; i < this.mAnimationCallbacks.size(); i++) {
            AnimationFrameCallback animationFrameCallback = (AnimationFrameCallback) this.mAnimationCallbacks.get(i);
            if (animationFrameCallback != null) {
                animationFrameCallback.doAnimationFrame(j);
            }
        }
        cleanUpList();
    }

    public static AnimationHandler getInstance() {
        AnimationHandler animationHandler = sTestHandler;
        if (animationHandler != null) {
            return animationHandler;
        }
        ThreadLocal threadLocal = sAnimationHandler;
        if (threadLocal.get() == null) {
            threadLocal.set(new AnimationHandler(null));
        }
        return (AnimationHandler) threadLocal.get();
    }

    void addAnimationFrameCallback(AnimationFrameCallback animationFrameCallback) {
        if (this.mAnimationCallbacks.size() == 0) {
            this.mProvider.postFrameCallback();
        }
        if (!this.mAnimationCallbacks.contains(animationFrameCallback)) {
            this.mAnimationCallbacks.add(animationFrameCallback);
        }
        this.mProvider.onNewCallbackAdded(animationFrameCallback);
    }

    void autoCancelBasedOn(ObjectAnimator objectAnimator) {
        for (int size = this.mAnimationCallbacks.size() - 1; size >= 0; size--) {
            AnimationFrameCallback animationFrameCallback = (AnimationFrameCallback) this.mAnimationCallbacks.get(size);
            if (animationFrameCallback != null && objectAnimator.shouldAutoCancel(animationFrameCallback)) {
                ((Animator) this.mAnimationCallbacks.get(size)).cancel();
            }
        }
    }

    void onAnimationFrame(long j) {
        doAnimationFrame(j);
        if (this.mAnimationCallbacks.size() > 0) {
            this.mProvider.postFrameCallback();
        }
    }

    public void removeCallback(AnimationFrameCallback animationFrameCallback) {
        int iIndexOf = this.mAnimationCallbacks.indexOf(animationFrameCallback);
        if (iIndexOf >= 0) {
            this.mAnimationCallbacks.set(iIndexOf, null);
            this.mListDirty = true;
        }
    }
}
