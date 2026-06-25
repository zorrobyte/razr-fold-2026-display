package com.android.systemui.animation;

import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.RemoteException;
import android.util.Log;
import android.view.IRemoteAnimationFinishedCallback;
import android.view.IRemoteAnimationRunner;
import android.view.RemoteAnimationTarget;
import android.view.SurfaceControl;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import android.window.WindowAnimationState;
import com.android.app.animation.Interpolators;
import com.android.systemui.animation.ActivityTransitionAnimator;
import com.android.systemui.animation.TransitionAnimator;
import com.motorola.plugin.core.utils.TimeoutRemoteCaller;
import java.util.concurrent.Executor;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: compiled from: ActivityTransitionAnimator.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class ActivityTransitionAnimator {
    private static final long ANIMATION_DELAY_NAV_FADE_IN;
    public static final Companion Companion = new Companion(null);
    public static final boolean DEBUG_TRANSITION_ANIMATION;
    private static final TransitionAnimator.Timings DIALOG_TIMINGS;
    private static final TransitionAnimator.Interpolators INTERPOLATORS;
    private static final Interpolator NAV_FADE_IN_INTERPOLATOR;
    private static final PathInterpolator NAV_FADE_OUT_INTERPOLATOR;
    private static final TransitionAnimator.Interpolators SPRING_INTERPOLATORS;
    private static final TransitionAnimator.SpringTimings SPRING_TIMINGS;
    public static final TransitionAnimator.Timings TIMINGS;

    /* JADX INFO: compiled from: ActivityTransitionAnimator.kt */
    public final class AnimationDelegate {
        private TransitionAnimator.Animation animation;
        private boolean cancelled;
        private final Listener listener;
        private Runnable onLongTimeout;
        private Runnable onTimeout;
        private boolean timedOut;
        private final Handler timeoutHandler;

        public AnimationDelegate(Executor executor, Controller controller, Callback callback, Listener listener, TransitionAnimator transitionAnimator, boolean z, boolean z2) {
            executor.getClass();
            controller.getClass();
            callback.getClass();
            throw null;
        }

        private final RemoteAnimationTarget findTargetWindowIfPossible(RemoteAnimationTarget[] remoteAnimationTargetArr) {
            if (remoteAnimationTargetArr == null) {
                return null;
            }
            throw null;
        }

        private final void invoke(IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
            try {
                iRemoteAnimationFinishedCallback.onAnimationFinished();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        private final void removeTimeouts() {
            Handler handler = this.timeoutHandler;
            if (handler != null) {
                handler.removeCallbacks(this.onTimeout);
                this.timeoutHandler.removeCallbacks(this.onLongTimeout);
            }
        }

        private final RemoteAnimationTarget setUpAnimation(RemoteAnimationTarget[] remoteAnimationTargetArr, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
            removeTimeouts();
            if (this.timedOut) {
                if (iRemoteAnimationFinishedCallback != null) {
                    invoke(iRemoteAnimationFinishedCallback);
                    return null;
                }
            } else if (!this.cancelled) {
                RemoteAnimationTarget remoteAnimationTargetFindTargetWindowIfPossible = findTargetWindowIfPossible(remoteAnimationTargetArr);
                if (remoteAnimationTargetFindTargetWindowIfPossible != null) {
                    return remoteAnimationTargetFindTargetWindowIfPossible;
                }
                Log.i("ActivityTransitionAnimator", "Aborting the animation as no window is opening");
                if (iRemoteAnimationFinishedCallback != null) {
                    invoke(iRemoteAnimationFinishedCallback);
                }
                if (ActivityTransitionAnimator.DEBUG_TRANSITION_ANIMATION) {
                    Log.d("ActivityTransitionAnimator", "Calling controller.onTransitionAnimationCancelled() [no window opening]");
                }
                Controller.onTransitionAnimationCancelled$default(null, null, 1, null);
                Listener listener = this.listener;
                if (listener != null) {
                    listener.onTransitionAnimationCancelled();
                }
            }
            return null;
        }

        private final void takeOverAnimationInternal(RemoteAnimationTarget remoteAnimationTarget, WindowAnimationState windowAnimationState, SurfaceControl.Transaction transaction, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
            throw null;
        }

        public void onAnimationCancelled() {
            removeTimeouts();
            if (this.timedOut) {
                return;
            }
            Log.i("ActivityTransitionAnimator", "Remote animation was cancelled");
            this.cancelled = true;
            TransitionAnimator.Animation animation = this.animation;
            if (animation != null) {
                animation.cancel();
            }
            if (ActivityTransitionAnimator.DEBUG_TRANSITION_ANIMATION) {
                Log.d("ActivityTransitionAnimator", "Calling controller.onTransitionAnimationCancelled() [remote animation cancelled]");
            }
            Controller.onTransitionAnimationCancelled$default(null, null, 1, null);
            Listener listener = this.listener;
            if (listener != null) {
                listener.onTransitionAnimationCancelled();
            }
        }

        public void onAnimationStart(int i, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
            if (setUpAnimation(remoteAnimationTargetArr, iRemoteAnimationFinishedCallback) != null) {
                throw null;
            }
        }

        public final void postTimeouts$frameworks__base__packages__SystemUI__animation__android_common__PlatformAnimationLib() {
            Handler handler = this.timeoutHandler;
            if (handler != null) {
                handler.postDelayed(this.onTimeout, 1000L);
                this.timeoutHandler.postDelayed(this.onLongTimeout, TimeoutRemoteCaller.DEFAULT_CALL_TIMEOUT_MILLIS);
            }
        }

        public final void takeOverAnimation$frameworks__base__packages__SystemUI__animation__android_common__PlatformAnimationLib(RemoteAnimationTarget[] remoteAnimationTargetArr, WindowAnimationState[] windowAnimationStateArr, SurfaceControl.Transaction transaction, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
            windowAnimationStateArr.getClass();
            transaction.getClass();
            RemoteAnimationTarget upAnimation = setUpAnimation(remoteAnimationTargetArr, iRemoteAnimationFinishedCallback);
            if (upAnimation == null) {
                return;
            }
            remoteAnimationTargetArr.getClass();
            takeOverAnimationInternal(upAnimation, windowAnimationStateArr[ArraysKt.indexOf(remoteAnimationTargetArr, upAnimation)], transaction, iRemoteAnimationFinishedCallback);
        }
    }

    /* JADX INFO: compiled from: ActivityTransitionAnimator.kt */
    public interface Callback {
    }

    /* JADX INFO: compiled from: ActivityTransitionAnimator.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: compiled from: ActivityTransitionAnimator.kt */
    public interface Controller {
        public static final Companion Companion = Companion.$$INSTANCE;

        /* JADX INFO: compiled from: ActivityTransitionAnimator.kt */
        public final class Companion {
            static final /* synthetic */ Companion $$INSTANCE = new Companion();

            private Companion() {
            }
        }

        static /* synthetic */ void onTransitionAnimationCancelled$default(Controller controller, Boolean bool, int i, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: onTransitionAnimationCancelled");
            }
            if ((i & 1) != 0) {
                bool = null;
            }
            controller.onTransitionAnimationCancelled(bool);
        }

        void onTransitionAnimationCancelled(Boolean bool);
    }

    /* JADX INFO: compiled from: ActivityTransitionAnimator.kt */
    public abstract class ControllerFactory {
    }

    /* JADX INFO: compiled from: ActivityTransitionAnimator.kt */
    public final class DelegatingAnimationCompletionListener implements Listener {
        private boolean cancelled;
        private final Listener delegate;
        private final Function0 onAnimationComplete;

        public DelegatingAnimationCompletionListener(ActivityTransitionAnimator activityTransitionAnimator, Listener listener, Function0 function0) {
            function0.getClass();
            this.delegate = listener;
            this.onAnimationComplete = function0;
        }

        @Override // com.android.systemui.animation.ActivityTransitionAnimator.Listener
        public void onTransitionAnimationCancelled() {
            this.cancelled = true;
            Listener listener = this.delegate;
            if (listener != null) {
                listener.onTransitionAnimationCancelled();
            }
            this.onAnimationComplete.mo2224invoke();
        }
    }

    /* JADX INFO: compiled from: ActivityTransitionAnimator.kt */
    public interface Listener {
        void onTransitionAnimationCancelled();
    }

    /* JADX INFO: compiled from: ActivityTransitionAnimator.kt */
    public final class Runner extends IRemoteAnimationRunner.Stub {
        private Controller controller;
        private final Function1 controllerFactory;
        private AnimationDelegate delegate;
        private final Listener listener;
        private final CoroutineScope scope;

        private final void createDelegate(Controller controller) {
            this.delegate = new AnimationDelegate(ActivityTransitionAnimator.access$getMainExecutor$p(null), controller, null, new DelegatingAnimationCompletionListener(null, this.listener, new ActivityTransitionAnimator$Runner$createDelegate$1(this)), null, ActivityTransitionAnimator.access$getDisableWmTimeout$p(null), ActivityTransitionAnimator.access$getSkipReparentTransaction$p(null));
        }

        public static /* synthetic */ void getDelegate$annotations() {
        }

        private final void initAndRun(IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback, Function1 function1) {
            Controller controller = this.controller;
            Function1 function12 = this.controllerFactory;
            if (TransitionAnimator.Companion.getDEBUG$frameworks__base__packages__SystemUI__animation__android_common__PlatformAnimationLib()) {
                Log.d("ActivityTransitionAnimator", "onAnimationStart:");
            }
            if (controller != null) {
                maybeSetUp(controller);
                if (startAnimation(function1) || iRemoteAnimationFinishedCallback == null) {
                    return;
                }
                iRemoteAnimationFinishedCallback.onAnimationFinished();
                return;
            }
            if (function12 == null) {
                if (iRemoteAnimationFinishedCallback != null) {
                    iRemoteAnimationFinishedCallback.onAnimationFinished();
                }
            } else {
                CoroutineScope coroutineScope = this.scope;
                if (coroutineScope != null) {
                    BuildersKt__Builders_commonKt.launch$default(coroutineScope, null, null, new ActivityTransitionAnimator$Runner$initAndRun$1(iRemoteAnimationFinishedCallback, this, function12, function1, null), 3, null);
                }
            }
        }

        private final void maybeSetUp(Controller controller) {
            if (this.delegate != null) {
                return;
            }
            createDelegate(controller);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit onAnimationStart$lambda$1(int i, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback, AnimationDelegate animationDelegate) {
            animationDelegate.getClass();
            animationDelegate.onAnimationStart(i, remoteAnimationTargetArr, remoteAnimationTargetArr2, remoteAnimationTargetArr3, iRemoteAnimationFinishedCallback);
            return Unit.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final java.lang.Object setUp(kotlin.jvm.functions.Function1 r5, kotlin.coroutines.Continuation r6) {
            /*
                r4 = this;
                boolean r0 = r6 instanceof com.android.systemui.animation.ActivityTransitionAnimator$Runner$setUp$1
                if (r0 == 0) goto L13
                r0 = r6
                com.android.systemui.animation.ActivityTransitionAnimator$Runner$setUp$1 r0 = (com.android.systemui.animation.ActivityTransitionAnimator$Runner$setUp$1) r0
                int r1 = r0.label
                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                r3 = r1 & r2
                if (r3 == 0) goto L13
                int r1 = r1 - r2
                r0.label = r1
                goto L18
            L13:
                com.android.systemui.animation.ActivityTransitionAnimator$Runner$setUp$1 r0 = new com.android.systemui.animation.ActivityTransitionAnimator$Runner$setUp$1
                r0.<init>(r4, r6)
            L18:
                java.lang.Object r6 = r0.result
                java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r2 = r0.label
                r3 = 1
                if (r2 == 0) goto L35
                if (r2 != r3) goto L2d
                java.lang.Object r4 = r0.L$0
                com.android.systemui.animation.ActivityTransitionAnimator$Runner r4 = (com.android.systemui.animation.ActivityTransitionAnimator.Runner) r4
                kotlin.ResultKt.throwOnFailure(r6)
                goto L43
            L2d:
                java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                r4.<init>(r5)
                throw r4
            L35:
                kotlin.ResultKt.throwOnFailure(r6)
                r0.L$0 = r4
                r0.label = r3
                java.lang.Object r6 = r5.invoke(r0)
                if (r6 != r1) goto L43
                return r1
            L43:
                androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(r6)
                r5 = 0
                r4.createDelegate(r5)
                kotlin.Unit r4 = kotlin.Unit.INSTANCE
                return r4
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.animation.ActivityTransitionAnimator.Runner.setUp(kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final boolean startAnimation(final Function1 function1) {
            final AnimationDelegate animationDelegate = this.delegate;
            if (animationDelegate != null) {
                ActivityTransitionAnimator.access$getMainExecutor$p(null).execute(new Runnable() { // from class: com.android.systemui.animation.ActivityTransitionAnimator$Runner$startAnimation$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        function1.invoke(animationDelegate);
                    }
                });
                return true;
            }
            Log.i("ActivityTransitionAnimator", "startAnimation called after completion");
            return false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit takeOverAnimation$lambda$2(RemoteAnimationTarget[] remoteAnimationTargetArr, WindowAnimationState[] windowAnimationStateArr, SurfaceControl.Transaction transaction, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback, AnimationDelegate animationDelegate) {
            animationDelegate.getClass();
            animationDelegate.takeOverAnimation$frameworks__base__packages__SystemUI__animation__android_common__PlatformAnimationLib(remoteAnimationTargetArr, windowAnimationStateArr, transaction, iRemoteAnimationFinishedCallback);
            return Unit.INSTANCE;
        }

        public final void dispose() {
            ActivityTransitionAnimator.access$getMainExecutor$p(null).execute(new Runnable() { // from class: com.android.systemui.animation.ActivityTransitionAnimator$Runner$dispose$1
                @Override // java.lang.Runnable
                public final void run() {
                    this.this$0.setDelegate(null);
                    this.this$0.controller = null;
                }
            });
        }

        public void onAnimationCancelled() {
            final AnimationDelegate animationDelegate = this.delegate;
            if (animationDelegate != null) {
                ActivityTransitionAnimator.access$getMainExecutor$p(null).execute(new Runnable() { // from class: com.android.systemui.animation.ActivityTransitionAnimator$Runner$onAnimationCancelled$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        animationDelegate.onAnimationCancelled();
                    }
                });
            } else {
                Log.wtf("ActivityTransitionAnimator", "onAnimationCancelled called after completion");
            }
        }

        public void onAnimationStart(final int i, final RemoteAnimationTarget[] remoteAnimationTargetArr, final RemoteAnimationTarget[] remoteAnimationTargetArr2, final RemoteAnimationTarget[] remoteAnimationTargetArr3, final IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
            initAndRun(iRemoteAnimationFinishedCallback, new Function1() { // from class: com.android.systemui.animation.ActivityTransitionAnimator$Runner$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return ActivityTransitionAnimator.Runner.onAnimationStart$lambda$1(i, remoteAnimationTargetArr, remoteAnimationTargetArr2, remoteAnimationTargetArr3, iRemoteAnimationFinishedCallback, (ActivityTransitionAnimator.AnimationDelegate) obj);
                }
            });
        }

        public final void postTimeouts() {
            Controller controller = this.controller;
            if (controller != null) {
                maybeSetUp(controller);
            }
            AnimationDelegate animationDelegate = this.delegate;
            if (animationDelegate != null) {
                animationDelegate.postTimeouts$frameworks__base__packages__SystemUI__animation__android_common__PlatformAnimationLib();
            }
        }

        public final void setDelegate(AnimationDelegate animationDelegate) {
            this.delegate = animationDelegate;
        }

        public final void takeOverAnimation(final RemoteAnimationTarget[] remoteAnimationTargetArr, final WindowAnimationState[] windowAnimationStateArr, final SurfaceControl.Transaction transaction, final IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
            windowAnimationStateArr.getClass();
            transaction.getClass();
            TransitionAnimator.Companion.assertLongLivedReturnAnimations();
            initAndRun(iRemoteAnimationFinishedCallback, new Function1() { // from class: com.android.systemui.animation.ActivityTransitionAnimator$Runner$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return ActivityTransitionAnimator.Runner.takeOverAnimation$lambda$2(remoteAnimationTargetArr, windowAnimationStateArr, transaction, iRemoteAnimationFinishedCallback, (ActivityTransitionAnimator.AnimationDelegate) obj);
                }
            });
        }
    }

    /* JADX INFO: compiled from: ActivityTransitionAnimator.kt */
    public abstract class TransitionCookie extends Binder {
    }

    static {
        TransitionAnimator.Timings timings = new TransitionAnimator.Timings(500L, 0L, 150L, 150L, 183L);
        TIMINGS = timings;
        SPRING_TIMINGS = new TransitionAnimator.SpringTimings(0.0f, 0.8f, 0.85f, 0.135f);
        DIALOG_TIMINGS = TransitionAnimator.Timings.copy$default(timings, 0L, 0L, 200L, 200L, 0L, 19, null);
        Interpolator interpolator = Interpolators.EMPHASIZED;
        interpolator.getClass();
        Interpolator interpolator2 = Interpolators.EMPHASIZED_COMPLEMENT;
        interpolator2.getClass();
        Interpolator interpolator3 = Interpolators.LINEAR_OUT_SLOW_IN;
        interpolator3.getClass();
        TransitionAnimator.Interpolators interpolators = new TransitionAnimator.Interpolators(interpolator, interpolator2, interpolator3, new PathInterpolator(0.0f, 0.0f, 0.6f, 1.0f));
        INTERPOLATORS = interpolators;
        Interpolator interpolator4 = Interpolators.DECELERATE_1_5;
        interpolator4.getClass();
        Interpolator interpolator5 = Interpolators.SLOW_OUT_LINEAR_IN;
        interpolator5.getClass();
        SPRING_INTERPOLATORS = TransitionAnimator.Interpolators.copy$default(interpolators, null, null, interpolator4, interpolator5, 3, null);
        DEBUG_TRANSITION_ANIMATION = Build.IS_DEBUGGABLE;
        ANIMATION_DELAY_NAV_FADE_IN = timings.getTotalDuration() - 266;
        NAV_FADE_IN_INTERPOLATOR = Interpolators.STANDARD_DECELERATE;
        NAV_FADE_OUT_INTERPOLATOR = new PathInterpolator(0.2f, 0.0f, 1.0f, 1.0f);
    }

    public static final /* synthetic */ boolean access$getDisableWmTimeout$p(ActivityTransitionAnimator activityTransitionAnimator) {
        throw null;
    }

    public static final /* synthetic */ Executor access$getMainExecutor$p(ActivityTransitionAnimator activityTransitionAnimator) {
        throw null;
    }

    public static final /* synthetic */ boolean access$getSkipReparentTransaction$p(ActivityTransitionAnimator activityTransitionAnimator) {
        throw null;
    }
}
