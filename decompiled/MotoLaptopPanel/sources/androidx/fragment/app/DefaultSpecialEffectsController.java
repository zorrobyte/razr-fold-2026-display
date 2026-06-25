package androidx.fragment.app;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.content.Context;
import android.graphics.Rect;
import android.support.v4.media.session.MediaControllerCompat$MediaControllerImplApi21$ExtraBinderRequestResultReceiver$$ExternalSyntheticThrowCCEIfNotNull0;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import androidx.activity.BackEventCompat;
import androidx.collection.ArrayMap;
import androidx.core.os.CancellationSignal;
import androidx.core.view.OneShotPreDrawListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewGroupCompat;
import androidx.fragment.app.DefaultSpecialEffectsController;
import androidx.fragment.app.FragmentAnim;
import androidx.fragment.app.SpecialEffectsController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Ref$ObjectRef;

/* JADX INFO: compiled from: DefaultSpecialEffectsController.kt */
/* JADX INFO: loaded from: classes.dex */
public final class DefaultSpecialEffectsController extends SpecialEffectsController {

    /* JADX INFO: compiled from: DefaultSpecialEffectsController.kt */
    final class AnimationEffect extends SpecialEffectsController.Effect {
        private final AnimationInfo animationInfo;

        public AnimationEffect(AnimationInfo animationInfo) {
            animationInfo.getClass();
            this.animationInfo = animationInfo;
        }

        public final AnimationInfo getAnimationInfo() {
            return this.animationInfo;
        }

        @Override // androidx.fragment.app.SpecialEffectsController.Effect
        public void onCancel(ViewGroup viewGroup) {
            viewGroup.getClass();
            SpecialEffectsController.Operation operation = this.animationInfo.getOperation();
            View view = operation.getFragment().mView;
            view.clearAnimation();
            viewGroup.endViewTransition(view);
            this.animationInfo.getOperation().completeEffect(this);
            if (FragmentManager.isLoggingEnabled(2)) {
                Log.v("FragmentManager", "Animation from operation " + operation + " has been cancelled.");
            }
        }

        @Override // androidx.fragment.app.SpecialEffectsController.Effect
        public void onCommit(ViewGroup viewGroup) {
            viewGroup.getClass();
            if (this.animationInfo.isVisibilityUnchanged()) {
                this.animationInfo.getOperation().completeEffect(this);
                return;
            }
            Context context = viewGroup.getContext();
            SpecialEffectsController.Operation operation = this.animationInfo.getOperation();
            View view = operation.getFragment().mView;
            AnimationInfo animationInfo = this.animationInfo;
            context.getClass();
            FragmentAnim.AnimationOrAnimator animation = animationInfo.getAnimation(context);
            if (animation == null) {
                throw new IllegalStateException("Required value was null.");
            }
            Animation animation2 = animation.animation;
            if (animation2 == null) {
                throw new IllegalStateException("Required value was null.");
            }
            if (operation.getFinalState() != SpecialEffectsController.Operation.State.REMOVED) {
                view.startAnimation(animation2);
                this.animationInfo.getOperation().completeEffect(this);
                return;
            }
            viewGroup.startViewTransition(view);
            FragmentAnim.EndViewTransitionAnimation endViewTransitionAnimation = new FragmentAnim.EndViewTransitionAnimation(animation2, viewGroup, view);
            endViewTransitionAnimation.setAnimationListener(new DefaultSpecialEffectsController$AnimationEffect$onCommit$1(operation, viewGroup, view, this));
            view.startAnimation(endViewTransitionAnimation);
            if (FragmentManager.isLoggingEnabled(2)) {
                Log.v("FragmentManager", "Animation from operation " + operation + " has started.");
            }
        }
    }

    /* JADX INFO: compiled from: DefaultSpecialEffectsController.kt */
    final class AnimationInfo extends SpecialEffectsInfo {
        private FragmentAnim.AnimationOrAnimator animation;
        private boolean isAnimLoaded;
        private final boolean isPop;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnimationInfo(SpecialEffectsController.Operation operation, boolean z) {
            super(operation);
            operation.getClass();
            this.isPop = z;
        }

        public final FragmentAnim.AnimationOrAnimator getAnimation(Context context) {
            context.getClass();
            if (this.isAnimLoaded) {
                return this.animation;
            }
            FragmentAnim.AnimationOrAnimator animationOrAnimatorLoadAnimation = FragmentAnim.loadAnimation(context, getOperation().getFragment(), getOperation().getFinalState() == SpecialEffectsController.Operation.State.VISIBLE, this.isPop);
            this.animation = animationOrAnimatorLoadAnimation;
            this.isAnimLoaded = true;
            return animationOrAnimatorLoadAnimation;
        }
    }

    /* JADX INFO: compiled from: DefaultSpecialEffectsController.kt */
    final class AnimatorEffect extends SpecialEffectsController.Effect {
        private AnimatorSet animator;
        private final AnimationInfo animatorInfo;

        public AnimatorEffect(AnimationInfo animationInfo) {
            animationInfo.getClass();
            this.animatorInfo = animationInfo;
        }

        public final AnimationInfo getAnimatorInfo() {
            return this.animatorInfo;
        }

        @Override // androidx.fragment.app.SpecialEffectsController.Effect
        public boolean isSeekingSupported() {
            return true;
        }

        @Override // androidx.fragment.app.SpecialEffectsController.Effect
        public void onCancel(ViewGroup viewGroup) {
            viewGroup.getClass();
            AnimatorSet animatorSet = this.animator;
            if (animatorSet == null) {
                this.animatorInfo.getOperation().completeEffect(this);
                return;
            }
            SpecialEffectsController.Operation operation = this.animatorInfo.getOperation();
            if (operation.isSeeking()) {
                Api26Impl.INSTANCE.reverse(animatorSet);
            } else {
                animatorSet.end();
            }
            if (FragmentManager.isLoggingEnabled(2)) {
                StringBuilder sb = new StringBuilder();
                sb.append("Animator from operation ");
                sb.append(operation);
                sb.append(" has been canceled");
                sb.append(operation.isSeeking() ? " with seeking." : ".");
                sb.append(' ');
                Log.v("FragmentManager", sb.toString());
            }
        }

        @Override // androidx.fragment.app.SpecialEffectsController.Effect
        public void onCommit(ViewGroup viewGroup) {
            viewGroup.getClass();
            SpecialEffectsController.Operation operation = this.animatorInfo.getOperation();
            AnimatorSet animatorSet = this.animator;
            if (animatorSet == null) {
                this.animatorInfo.getOperation().completeEffect(this);
                return;
            }
            animatorSet.start();
            if (FragmentManager.isLoggingEnabled(2)) {
                Log.v("FragmentManager", "Animator from operation " + operation + " has started.");
            }
        }

        @Override // androidx.fragment.app.SpecialEffectsController.Effect
        public void onProgress(BackEventCompat backEventCompat, ViewGroup viewGroup) {
            backEventCompat.getClass();
            viewGroup.getClass();
            SpecialEffectsController.Operation operation = this.animatorInfo.getOperation();
            AnimatorSet animatorSet = this.animator;
            if (animatorSet == null) {
                this.animatorInfo.getOperation().completeEffect(this);
                return;
            }
            if (operation.getFragment().mTransitioning) {
                if (FragmentManager.isLoggingEnabled(2)) {
                    Log.v("FragmentManager", "Adding BackProgressCallbacks for Animators to operation " + operation);
                }
                long j = Api24Impl.INSTANCE.totalDuration(animatorSet);
                long progress = (long) (backEventCompat.getProgress() * j);
                if (progress == 0) {
                    progress = 1;
                }
                if (progress == j) {
                    progress = j - 1;
                }
                if (FragmentManager.isLoggingEnabled(2)) {
                    Log.v("FragmentManager", "Setting currentPlayTime to " + progress + " for Animator " + animatorSet + " on operation " + operation);
                }
                Api26Impl.INSTANCE.setCurrentPlayTime(animatorSet, progress);
            }
        }

        @Override // androidx.fragment.app.SpecialEffectsController.Effect
        public void onStart(final ViewGroup viewGroup) {
            final AnimatorEffect animatorEffect;
            viewGroup.getClass();
            if (this.animatorInfo.isVisibilityUnchanged()) {
                return;
            }
            Context context = viewGroup.getContext();
            AnimationInfo animationInfo = this.animatorInfo;
            context.getClass();
            FragmentAnim.AnimationOrAnimator animation = animationInfo.getAnimation(context);
            this.animator = animation != null ? animation.animator : null;
            final SpecialEffectsController.Operation operation = this.animatorInfo.getOperation();
            Fragment fragment = operation.getFragment();
            final boolean z = operation.getFinalState() == SpecialEffectsController.Operation.State.GONE;
            final View view = fragment.mView;
            viewGroup.startViewTransition(view);
            AnimatorSet animatorSet = this.animator;
            if (animatorSet != null) {
                animatorEffect = this;
                animatorSet.addListener(new AnimatorListenerAdapter() { // from class: androidx.fragment.app.DefaultSpecialEffectsController$AnimatorEffect$onStart$1
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        animator.getClass();
                        viewGroup.endViewTransition(view);
                        if (z) {
                            SpecialEffectsController.Operation.State finalState = operation.getFinalState();
                            View view2 = view;
                            view2.getClass();
                            finalState.applyState(view2, viewGroup);
                        }
                        animatorEffect.getAnimatorInfo().getOperation().completeEffect(animatorEffect);
                        if (FragmentManager.isLoggingEnabled(2)) {
                            Log.v("FragmentManager", "Animator from operation " + operation + " has ended.");
                        }
                    }
                });
            } else {
                animatorEffect = this;
            }
            AnimatorSet animatorSet2 = animatorEffect.animator;
            if (animatorSet2 != null) {
                animatorSet2.setTarget(view);
            }
        }
    }

    /* JADX INFO: compiled from: DefaultSpecialEffectsController.kt */
    public final class Api24Impl {
        public static final Api24Impl INSTANCE = new Api24Impl();

        private Api24Impl() {
        }

        public final long totalDuration(AnimatorSet animatorSet) {
            animatorSet.getClass();
            return animatorSet.getTotalDuration();
        }
    }

    /* JADX INFO: compiled from: DefaultSpecialEffectsController.kt */
    public final class Api26Impl {
        public static final Api26Impl INSTANCE = new Api26Impl();

        private Api26Impl() {
        }

        public final void reverse(AnimatorSet animatorSet) {
            animatorSet.getClass();
            animatorSet.reverse();
        }

        public final void setCurrentPlayTime(AnimatorSet animatorSet, long j) {
            animatorSet.getClass();
            animatorSet.setCurrentPlayTime(j);
        }
    }

    /* JADX INFO: compiled from: DefaultSpecialEffectsController.kt */
    public abstract class SpecialEffectsInfo {
        private final SpecialEffectsController.Operation operation;

        public SpecialEffectsInfo(SpecialEffectsController.Operation operation) {
            operation.getClass();
            this.operation = operation;
        }

        public final SpecialEffectsController.Operation getOperation() {
            return this.operation;
        }

        public final boolean isVisibilityUnchanged() {
            View view = this.operation.getFragment().mView;
            SpecialEffectsController.Operation.State stateAsOperationState = view != null ? SpecialEffectsController.Operation.State.Companion.asOperationState(view) : null;
            SpecialEffectsController.Operation.State finalState = this.operation.getFinalState();
            if (stateAsOperationState == finalState) {
                return true;
            }
            SpecialEffectsController.Operation.State state = SpecialEffectsController.Operation.State.VISIBLE;
            return (stateAsOperationState == state || finalState == state) ? false : true;
        }
    }

    /* JADX INFO: compiled from: DefaultSpecialEffectsController.kt */
    final class TransitionEffect extends SpecialEffectsController.Effect {
        private Object controller;
        private final ArrayList enteringNames;
        private final ArrayList exitingNames;
        private final SpecialEffectsController.Operation firstOut;
        private final ArrayMap firstOutViews;
        private final boolean isPop;
        private final SpecialEffectsController.Operation lastIn;
        private final ArrayMap lastInViews;
        private final ArrayList sharedElementFirstOutViews;
        private final ArrayList sharedElementLastInViews;
        private final ArrayMap sharedElementNameMapping;
        private final Object sharedElementTransition;
        private final FragmentTransitionImpl transitionImpl;
        private final List transitionInfos;
        private final CancellationSignal transitionSignal;

        public TransitionEffect(List list, SpecialEffectsController.Operation operation, SpecialEffectsController.Operation operation2, FragmentTransitionImpl fragmentTransitionImpl, Object obj, ArrayList arrayList, ArrayList arrayList2, ArrayMap arrayMap, ArrayList arrayList3, ArrayList arrayList4, ArrayMap arrayMap2, ArrayMap arrayMap3, boolean z) {
            list.getClass();
            fragmentTransitionImpl.getClass();
            arrayList.getClass();
            arrayList2.getClass();
            arrayMap.getClass();
            arrayList3.getClass();
            arrayList4.getClass();
            arrayMap2.getClass();
            arrayMap3.getClass();
            this.transitionInfos = list;
            this.firstOut = operation;
            this.lastIn = operation2;
            this.transitionImpl = fragmentTransitionImpl;
            this.sharedElementTransition = obj;
            this.sharedElementFirstOutViews = arrayList;
            this.sharedElementLastInViews = arrayList2;
            this.sharedElementNameMapping = arrayMap;
            this.enteringNames = arrayList3;
            this.exitingNames = arrayList4;
            this.firstOutViews = arrayMap2;
            this.lastInViews = arrayMap3;
            this.isPop = z;
            this.transitionSignal = new CancellationSignal();
        }

        private final void captureTransitioningViews(ArrayList arrayList, View view) {
            if (!(view instanceof ViewGroup)) {
                if (arrayList.contains(view)) {
                    return;
                }
                arrayList.add(view);
                return;
            }
            ViewGroup viewGroup = (ViewGroup) view;
            if (ViewGroupCompat.isTransitionGroup(viewGroup)) {
                if (arrayList.contains(view)) {
                    return;
                }
                arrayList.add(view);
                return;
            }
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = viewGroup.getChildAt(i);
                if (childAt.getVisibility() == 0) {
                    captureTransitioningViews(arrayList, childAt);
                }
            }
        }

        private final Pair createMergedTransition(ViewGroup viewGroup, SpecialEffectsController.Operation operation, SpecialEffectsController.Operation operation2) {
            ViewGroup viewGroup2 = viewGroup;
            final SpecialEffectsController.Operation operation3 = operation;
            final SpecialEffectsController.Operation operation4 = operation2;
            View view = new View(viewGroup2.getContext());
            final Rect rect = new Rect();
            Iterator it = this.transitionInfos.iterator();
            boolean z = false;
            View view2 = null;
            while (it.hasNext()) {
                if (((TransitionInfo) it.next()).hasSharedElementTransition() && operation4 != null && operation3 != null && !this.sharedElementNameMapping.isEmpty() && this.sharedElementTransition != null) {
                    FragmentTransition.callSharedElementStartEnd(operation3.getFragment(), operation4.getFragment(), this.isPop, this.firstOutViews, true);
                    OneShotPreDrawListener.add(viewGroup2, new Runnable() { // from class: androidx.fragment.app.DefaultSpecialEffectsController$TransitionEffect$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            DefaultSpecialEffectsController.TransitionEffect.createMergedTransition$lambda$12(operation3, operation4, this);
                        }
                    });
                    this.sharedElementFirstOutViews.addAll(this.firstOutViews.values());
                    if (!this.exitingNames.isEmpty()) {
                        Object obj = this.exitingNames.get(0);
                        obj.getClass();
                        view2 = (View) this.firstOutViews.get((String) obj);
                        this.transitionImpl.setEpicenter(this.sharedElementTransition, view2);
                    }
                    this.sharedElementLastInViews.addAll(this.lastInViews.values());
                    if (!this.enteringNames.isEmpty()) {
                        Object obj2 = this.enteringNames.get(0);
                        obj2.getClass();
                        final View view3 = (View) this.lastInViews.get((String) obj2);
                        if (view3 != null) {
                            final FragmentTransitionImpl fragmentTransitionImpl = this.transitionImpl;
                            OneShotPreDrawListener.add(viewGroup2, new Runnable() { // from class: androidx.fragment.app.DefaultSpecialEffectsController$TransitionEffect$$ExternalSyntheticLambda4
                                @Override // java.lang.Runnable
                                public final void run() {
                                    fragmentTransitionImpl.getBoundsOnScreen(view3, rect);
                                }
                            });
                            z = true;
                        }
                    }
                    this.transitionImpl.setSharedElementTargets(this.sharedElementTransition, view, this.sharedElementFirstOutViews);
                    FragmentTransitionImpl fragmentTransitionImpl2 = this.transitionImpl;
                    Object obj3 = this.sharedElementTransition;
                    fragmentTransitionImpl2.scheduleRemoveTargets(obj3, null, null, null, null, obj3, this.sharedElementLastInViews);
                }
            }
            ArrayList arrayList = new ArrayList();
            Iterator it2 = this.transitionInfos.iterator();
            Object objMergeTransitionsTogether = null;
            Object objMergeTransitionsTogether2 = null;
            while (it2.hasNext()) {
                TransitionInfo transitionInfo = (TransitionInfo) it2.next();
                SpecialEffectsController.Operation operation5 = transitionInfo.getOperation();
                boolean z2 = z;
                Object objCloneTransition = this.transitionImpl.cloneTransition(transitionInfo.getTransition());
                if (objCloneTransition != null) {
                    final ArrayList arrayList2 = new ArrayList();
                    Iterator it3 = it2;
                    View view4 = operation5.getFragment().mView;
                    view4.getClass();
                    captureTransitioningViews(arrayList2, view4);
                    if (this.sharedElementTransition != null && (operation5 == operation4 || operation5 == operation3)) {
                        if (operation5 == operation4) {
                            arrayList2.removeAll(CollectionsKt.toSet(this.sharedElementFirstOutViews));
                        } else {
                            arrayList2.removeAll(CollectionsKt.toSet(this.sharedElementLastInViews));
                        }
                    }
                    if (arrayList2.isEmpty()) {
                        this.transitionImpl.addTarget(objCloneTransition, view);
                    } else {
                        this.transitionImpl.addTargets(objCloneTransition, arrayList2);
                        this.transitionImpl.scheduleRemoveTargets(objCloneTransition, objCloneTransition, arrayList2, null, null, null, null);
                        if (operation5.getFinalState() == SpecialEffectsController.Operation.State.GONE) {
                            operation5.setAwaitingContainerChanges(false);
                            ArrayList arrayList3 = new ArrayList(arrayList2);
                            arrayList3.remove(operation5.getFragment().mView);
                            this.transitionImpl.scheduleHideFragmentView(objCloneTransition, operation5.getFragment().mView, arrayList3);
                            OneShotPreDrawListener.add(viewGroup2, new Runnable() { // from class: androidx.fragment.app.DefaultSpecialEffectsController$TransitionEffect$$ExternalSyntheticLambda5
                                @Override // java.lang.Runnable
                                public final void run() {
                                    FragmentTransition.setViewVisibility(arrayList2, 4);
                                }
                            });
                        }
                    }
                    if (operation5.getFinalState() == SpecialEffectsController.Operation.State.VISIBLE) {
                        arrayList.addAll(arrayList2);
                        if (z2) {
                            this.transitionImpl.setEpicenter(objCloneTransition, rect);
                        }
                        if (FragmentManager.isLoggingEnabled(2)) {
                            Log.v("FragmentManager", "Entering Transition: " + objCloneTransition);
                            Log.v("FragmentManager", ">>>>> EnteringViews <<<<<");
                            int size = arrayList2.size();
                            int i = 0;
                            while (i < size) {
                                Object obj4 = arrayList2.get(i);
                                i++;
                                obj4.getClass();
                                Log.v("FragmentManager", "View: " + ((View) obj4));
                            }
                        }
                    } else {
                        this.transitionImpl.setEpicenter(objCloneTransition, view2);
                        if (FragmentManager.isLoggingEnabled(2)) {
                            Log.v("FragmentManager", "Exiting Transition: " + objCloneTransition);
                            Log.v("FragmentManager", ">>>>> ExitingViews <<<<<");
                            int size2 = arrayList2.size();
                            int i2 = 0;
                            while (i2 < size2) {
                                Object obj5 = arrayList2.get(i2);
                                i2++;
                                obj5.getClass();
                                Log.v("FragmentManager", "View: " + ((View) obj5));
                            }
                        }
                    }
                    if (transitionInfo.isOverlapAllowed()) {
                        objMergeTransitionsTogether = this.transitionImpl.mergeTransitionsTogether(objMergeTransitionsTogether, objCloneTransition, null);
                    } else {
                        objMergeTransitionsTogether2 = this.transitionImpl.mergeTransitionsTogether(objMergeTransitionsTogether2, objCloneTransition, null);
                    }
                    viewGroup2 = viewGroup;
                    operation3 = operation;
                    operation4 = operation2;
                    z = z2;
                    it2 = it3;
                } else {
                    viewGroup2 = viewGroup;
                    operation3 = operation;
                    operation4 = operation2;
                    z = z2;
                }
            }
            Object objMergeTransitionsInSequence = this.transitionImpl.mergeTransitionsInSequence(objMergeTransitionsTogether, objMergeTransitionsTogether2, this.sharedElementTransition);
            if (FragmentManager.isLoggingEnabled(2)) {
                Log.v("FragmentManager", "Final merged transition: " + objMergeTransitionsInSequence);
            }
            return new Pair(arrayList, objMergeTransitionsInSequence);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void createMergedTransition$lambda$12(SpecialEffectsController.Operation operation, SpecialEffectsController.Operation operation2, TransitionEffect transitionEffect) {
            FragmentTransition.callSharedElementStartEnd(operation.getFragment(), operation2.getFragment(), transitionEffect.isPop, transitionEffect.lastInViews, false);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void onCommit$lambda$11$lambda$10(SpecialEffectsController.Operation operation, TransitionEffect transitionEffect) {
            if (FragmentManager.isLoggingEnabled(2)) {
                Log.v("FragmentManager", "Transition for operation " + operation + " has completed");
            }
            operation.completeEffect(transitionEffect);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void onStart$lambda$6$lambda$4(Ref$ObjectRef ref$ObjectRef) {
            Function0 function0 = (Function0) ref$ObjectRef.element;
            if (function0 != null) {
                function0.invoke();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void onStart$lambda$6$lambda$5(SpecialEffectsController.Operation operation, TransitionEffect transitionEffect) {
            if (FragmentManager.isLoggingEnabled(2)) {
                Log.v("FragmentManager", "Transition for operation " + operation + " has completed");
            }
            operation.completeEffect(transitionEffect);
        }

        private final void runTransition(ArrayList arrayList, ViewGroup viewGroup, Function0 function0) {
            FragmentTransition.setViewVisibility(arrayList, 4);
            ArrayList arrayListPrepareSetNameOverridesReordered = this.transitionImpl.prepareSetNameOverridesReordered(this.sharedElementLastInViews);
            if (FragmentManager.isLoggingEnabled(2)) {
                Log.v("FragmentManager", ">>>>> Beginning transition <<<<<");
                Log.v("FragmentManager", ">>>>> SharedElementFirstOutViews <<<<<");
                ArrayList arrayList2 = this.sharedElementFirstOutViews;
                int size = arrayList2.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList2.get(i);
                    i++;
                    obj.getClass();
                    View view = (View) obj;
                    Log.v("FragmentManager", "View: " + view + " Name: " + ViewCompat.getTransitionName(view));
                }
                Log.v("FragmentManager", ">>>>> SharedElementLastInViews <<<<<");
                ArrayList arrayList3 = this.sharedElementLastInViews;
                int size2 = arrayList3.size();
                int i2 = 0;
                while (i2 < size2) {
                    Object obj2 = arrayList3.get(i2);
                    i2++;
                    obj2.getClass();
                    View view2 = (View) obj2;
                    Log.v("FragmentManager", "View: " + view2 + " Name: " + ViewCompat.getTransitionName(view2));
                }
            }
            function0.invoke();
            this.transitionImpl.setNameOverridesReordered(viewGroup, this.sharedElementFirstOutViews, this.sharedElementLastInViews, arrayListPrepareSetNameOverridesReordered, this.sharedElementNameMapping);
            FragmentTransition.setViewVisibility(arrayList, 0);
            this.transitionImpl.swapSharedElementTargets(this.sharedElementTransition, this.sharedElementFirstOutViews, this.sharedElementLastInViews);
        }

        public final Object getController() {
            return this.controller;
        }

        public final SpecialEffectsController.Operation getFirstOut() {
            return this.firstOut;
        }

        public final SpecialEffectsController.Operation getLastIn() {
            return this.lastIn;
        }

        public final FragmentTransitionImpl getTransitionImpl() {
            return this.transitionImpl;
        }

        public final List getTransitionInfos() {
            return this.transitionInfos;
        }

        public final boolean getTransitioning() {
            List list = this.transitionInfos;
            if (list != null && list.isEmpty()) {
                return true;
            }
            Iterator it = list.iterator();
            while (it.hasNext()) {
                if (!((TransitionInfo) it.next()).getOperation().getFragment().mTransitioning) {
                    return false;
                }
            }
            return true;
        }

        @Override // androidx.fragment.app.SpecialEffectsController.Effect
        public boolean isSeekingSupported() {
            if (!this.transitionImpl.isSeekingSupported()) {
                return false;
            }
            List<TransitionInfo> list = this.transitionInfos;
            if (list == null || !list.isEmpty()) {
                for (TransitionInfo transitionInfo : list) {
                    if (transitionInfo.getTransition() == null || !this.transitionImpl.isSeekingSupported(transitionInfo.getTransition())) {
                        return false;
                    }
                }
            }
            Object obj = this.sharedElementTransition;
            return obj == null || this.transitionImpl.isSeekingSupported(obj);
        }

        @Override // androidx.fragment.app.SpecialEffectsController.Effect
        public void onCancel(ViewGroup viewGroup) {
            viewGroup.getClass();
            this.transitionSignal.cancel();
        }

        @Override // androidx.fragment.app.SpecialEffectsController.Effect
        public void onCommit(final ViewGroup viewGroup) {
            viewGroup.getClass();
            if (!viewGroup.isLaidOut()) {
                for (TransitionInfo transitionInfo : this.transitionInfos) {
                    SpecialEffectsController.Operation operation = transitionInfo.getOperation();
                    if (FragmentManager.isLoggingEnabled(2)) {
                        Log.v("FragmentManager", "SpecialEffectsController: Container " + viewGroup + " has not been laid out. Completing operation " + operation);
                    }
                    transitionInfo.getOperation().completeEffect(this);
                }
                return;
            }
            Object obj = this.controller;
            if (obj != null) {
                FragmentTransitionImpl fragmentTransitionImpl = this.transitionImpl;
                obj.getClass();
                fragmentTransitionImpl.animateToEnd(obj);
                if (FragmentManager.isLoggingEnabled(2)) {
                    Log.v("FragmentManager", "Ending execution of operations from " + this.firstOut + " to " + this.lastIn);
                    return;
                }
                return;
            }
            Pair pairCreateMergedTransition = createMergedTransition(viewGroup, this.lastIn, this.firstOut);
            ArrayList arrayList = (ArrayList) pairCreateMergedTransition.component1();
            final Object objComponent2 = pairCreateMergedTransition.component2();
            List list = this.transitionInfos;
            ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            Iterator it = list.iterator();
            while (it.hasNext()) {
                arrayList2.add(((TransitionInfo) it.next()).getOperation());
            }
            int size = arrayList2.size();
            int i = 0;
            while (i < size) {
                Object obj2 = arrayList2.get(i);
                i++;
                final SpecialEffectsController.Operation operation2 = (SpecialEffectsController.Operation) obj2;
                this.transitionImpl.setListenerForTransitionEnd(operation2.getFragment(), objComponent2, this.transitionSignal, new Runnable() { // from class: androidx.fragment.app.DefaultSpecialEffectsController$TransitionEffect$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        DefaultSpecialEffectsController.TransitionEffect.onCommit$lambda$11$lambda$10(operation2, this);
                    }
                });
            }
            runTransition(arrayList, viewGroup, new Function0() { // from class: androidx.fragment.app.DefaultSpecialEffectsController$TransitionEffect$onCommit$4
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Object invoke() {
                    m1968invoke();
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: collision with other method in class */
                public final void m1968invoke() {
                    this.this$0.getTransitionImpl().beginDelayedTransition(viewGroup, objComponent2);
                }
            });
            if (FragmentManager.isLoggingEnabled(2)) {
                Log.v("FragmentManager", "Completed executing operations from " + this.firstOut + " to " + this.lastIn);
            }
        }

        @Override // androidx.fragment.app.SpecialEffectsController.Effect
        public void onProgress(BackEventCompat backEventCompat, ViewGroup viewGroup) {
            backEventCompat.getClass();
            viewGroup.getClass();
            Object obj = this.controller;
            if (obj != null) {
                this.transitionImpl.setCurrentPlayTime(obj, backEventCompat.getProgress());
            }
        }

        @Override // androidx.fragment.app.SpecialEffectsController.Effect
        public void onStart(final ViewGroup viewGroup) {
            viewGroup.getClass();
            if (!viewGroup.isLaidOut()) {
                Iterator it = this.transitionInfos.iterator();
                while (it.hasNext()) {
                    SpecialEffectsController.Operation operation = ((TransitionInfo) it.next()).getOperation();
                    if (FragmentManager.isLoggingEnabled(2)) {
                        Log.v("FragmentManager", "SpecialEffectsController: Container " + viewGroup + " has not been laid out. Skipping onStart for operation " + operation);
                    }
                }
                return;
            }
            if (getTransitioning() && this.sharedElementTransition != null && !isSeekingSupported()) {
                Log.i("FragmentManager", "Ignoring shared elements transition " + this.sharedElementTransition + " between " + this.firstOut + " and " + this.lastIn + " as neither fragment has set a Transition. In order to run a SharedElementTransition, you must also set either an enter or exit transition on a fragment involved in the transaction. The sharedElementTransition will run after the back gesture has been committed.");
            }
            if (isSeekingSupported() && getTransitioning()) {
                final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
                Pair pairCreateMergedTransition = createMergedTransition(viewGroup, this.lastIn, this.firstOut);
                ArrayList arrayList = (ArrayList) pairCreateMergedTransition.component1();
                final Object objComponent2 = pairCreateMergedTransition.component2();
                List list = this.transitionInfos;
                ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                Iterator it2 = list.iterator();
                while (it2.hasNext()) {
                    arrayList2.add(((TransitionInfo) it2.next()).getOperation());
                }
                int size = arrayList2.size();
                for (int i = 0; i < size; i++) {
                    final SpecialEffectsController.Operation operation2 = (SpecialEffectsController.Operation) arrayList2.get(i);
                    this.transitionImpl.setListenerForTransitionEnd(operation2.getFragment(), objComponent2, this.transitionSignal, new Runnable() { // from class: androidx.fragment.app.DefaultSpecialEffectsController$TransitionEffect$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            DefaultSpecialEffectsController.TransitionEffect.onStart$lambda$6$lambda$4(ref$ObjectRef);
                        }
                    }, new Runnable() { // from class: androidx.fragment.app.DefaultSpecialEffectsController$TransitionEffect$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            DefaultSpecialEffectsController.TransitionEffect.onStart$lambda$6$lambda$5(operation2, this);
                        }
                    });
                }
                runTransition(arrayList, viewGroup, new Function0() { // from class: androidx.fragment.app.DefaultSpecialEffectsController$TransitionEffect$onStart$4

                    /* JADX INFO: renamed from: androidx.fragment.app.DefaultSpecialEffectsController$TransitionEffect$onStart$4$2, reason: invalid class name */
                    /* JADX INFO: compiled from: DefaultSpecialEffectsController.kt */
                    final class AnonymousClass2 extends Lambda implements Function0 {
                        final /* synthetic */ ViewGroup $container;
                        final /* synthetic */ Object $mergedTransition;
                        final /* synthetic */ DefaultSpecialEffectsController.TransitionEffect this$0;

                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        AnonymousClass2(DefaultSpecialEffectsController.TransitionEffect transitionEffect, Object obj, ViewGroup viewGroup) {
                            super(0);
                            this.this$0 = transitionEffect;
                            this.$mergedTransition = obj;
                            this.$container = viewGroup;
                        }

                        /* JADX INFO: Access modifiers changed from: private */
                        public static final void invoke$lambda$2(DefaultSpecialEffectsController.TransitionEffect transitionEffect, ViewGroup viewGroup) {
                            Iterator it = transitionEffect.getTransitionInfos().iterator();
                            while (it.hasNext()) {
                                SpecialEffectsController.Operation operation = ((DefaultSpecialEffectsController.TransitionInfo) it.next()).getOperation();
                                View view = operation.getFragment().getView();
                                if (view != null) {
                                    operation.getFinalState().applyState(view, viewGroup);
                                }
                            }
                        }

                        /* JADX INFO: Access modifiers changed from: private */
                        public static final void invoke$lambda$4(DefaultSpecialEffectsController.TransitionEffect transitionEffect) {
                            if (FragmentManager.isLoggingEnabled(2)) {
                                Log.v("FragmentManager", "Transition for all operations has completed");
                            }
                            Iterator it = transitionEffect.getTransitionInfos().iterator();
                            while (it.hasNext()) {
                                ((DefaultSpecialEffectsController.TransitionInfo) it.next()).getOperation().completeEffect(transitionEffect);
                            }
                        }

                        @Override // kotlin.jvm.functions.Function0
                        public /* bridge */ /* synthetic */ Object invoke() {
                            m1971invoke();
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: invoke, reason: collision with other method in class */
                        public final void m1971invoke() {
                            List transitionInfos = this.this$0.getTransitionInfos();
                            if (transitionInfos == null || !transitionInfos.isEmpty()) {
                                Iterator it = transitionInfos.iterator();
                                while (it.hasNext()) {
                                    if (!((DefaultSpecialEffectsController.TransitionInfo) it.next()).getOperation().isSeeking()) {
                                        if (FragmentManager.isLoggingEnabled(2)) {
                                            Log.v("FragmentManager", "Completing animating immediately");
                                        }
                                        CancellationSignal cancellationSignal = new CancellationSignal();
                                        FragmentTransitionImpl transitionImpl = this.this$0.getTransitionImpl();
                                        Fragment fragment = ((DefaultSpecialEffectsController.TransitionInfo) this.this$0.getTransitionInfos().get(0)).getOperation().getFragment();
                                        Object obj = this.$mergedTransition;
                                        final DefaultSpecialEffectsController.TransitionEffect transitionEffect = this.this$0;
                                        transitionImpl.setListenerForTransitionEnd(fragment, obj, cancellationSignal, 
                                        /*  JADX ERROR: Method code generation error
                                            jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x0060: INVOKE 
                                              (r1v2 'transitionImpl' androidx.fragment.app.FragmentTransitionImpl)
                                              (r2v6 'fragment' androidx.fragment.app.Fragment)
                                              (r3v6 'obj' java.lang.Object)
                                              (r0v4 'cancellationSignal' androidx.core.os.CancellationSignal)
                                              (wrap:java.lang.Runnable:0x005d: CONSTRUCTOR (r5v1 'transitionEffect' androidx.fragment.app.DefaultSpecialEffectsController$TransitionEffect A[DONT_INLINE]) A[MD:(androidx.fragment.app.DefaultSpecialEffectsController$TransitionEffect):void (m), WRAPPED] call: androidx.fragment.app.DefaultSpecialEffectsController$TransitionEffect$onStart$4$2$$ExternalSyntheticLambda1.<init>(androidx.fragment.app.DefaultSpecialEffectsController$TransitionEffect):void type: CONSTRUCTOR)
                                             VIRTUAL call: androidx.fragment.app.FragmentTransitionImpl.setListenerForTransitionEnd(androidx.fragment.app.Fragment, java.lang.Object, androidx.core.os.CancellationSignal, java.lang.Runnable):void A[MD:(androidx.fragment.app.Fragment, java.lang.Object, androidx.core.os.CancellationSignal, java.lang.Runnable):void (m)] in method: androidx.fragment.app.DefaultSpecialEffectsController$TransitionEffect$onStart$4.2.invoke():void, file: classes.dex
                                            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:310)
                                            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:273)
                                            	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:94)
                                            	at jadx.core.dex.nodes.IBlock.generate(IBlock.java:15)
                                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                            	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                            	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:83)
                                            	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:126)
                                            	at jadx.core.dex.regions.conditions.IfRegion.generate(IfRegion.java:90)
                                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                            	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                            	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                            	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:83)
                                            	at jadx.core.codegen.RegionGen.makeLoop(RegionGen.java:226)
                                            	at jadx.core.dex.regions.loops.LoopRegion.generate(LoopRegion.java:171)
                                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                            	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                            	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:83)
                                            	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:126)
                                            	at jadx.core.dex.regions.conditions.IfRegion.generate(IfRegion.java:90)
                                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                            	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                            	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:305)
                                            	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:284)
                                            	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:412)
                                            	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:337)
                                            	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$2(ClassGen.java:303)
                                            	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
                                            	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
                                            	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                                            	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:258)
                                            Caused by: jadx.core.utils.exceptions.JadxRuntimeException: Expected class to be processed at this point, class: androidx.fragment.app.DefaultSpecialEffectsController$TransitionEffect$onStart$4$2$$ExternalSyntheticLambda1, state: NOT_LOADED
                                            	at jadx.core.dex.nodes.ClassNode.ensureProcessed(ClassNode.java:306)
                                            	at jadx.core.codegen.InsnGen.inlineAnonymousConstructor(InsnGen.java:807)
                                            	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:730)
                                            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:418)
                                            	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:145)
                                            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:121)
                                            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:108)
                                            	at jadx.core.codegen.InsnGen.generateMethodArguments(InsnGen.java:1143)
                                            	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:910)
                                            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:422)
                                            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:303)
                                            	... 35 more
                                            */
                                        /*
                                            this = this;
                                            androidx.fragment.app.DefaultSpecialEffectsController$TransitionEffect r0 = r5.this$0
                                            java.util.List r0 = r0.getTransitionInfos()
                                            java.lang.String r1 = "FragmentManager"
                                            r2 = 2
                                            if (r0 == 0) goto L12
                                            boolean r3 = r0.isEmpty()
                                            if (r3 == 0) goto L12
                                            goto L67
                                        L12:
                                            java.util.Iterator r0 = r0.iterator()
                                        L16:
                                            boolean r3 = r0.hasNext()
                                            if (r3 == 0) goto L67
                                            java.lang.Object r3 = r0.next()
                                            androidx.fragment.app.DefaultSpecialEffectsController$TransitionInfo r3 = (androidx.fragment.app.DefaultSpecialEffectsController.TransitionInfo) r3
                                            androidx.fragment.app.SpecialEffectsController$Operation r3 = r3.getOperation()
                                            boolean r3 = r3.isSeeking()
                                            if (r3 != 0) goto L16
                                            boolean r0 = androidx.fragment.app.FragmentManager.isLoggingEnabled(r2)
                                            if (r0 == 0) goto L37
                                            java.lang.String r0 = "Completing animating immediately"
                                            android.util.Log.v(r1, r0)
                                        L37:
                                            androidx.core.os.CancellationSignal r0 = new androidx.core.os.CancellationSignal
                                            r0.<init>()
                                            androidx.fragment.app.DefaultSpecialEffectsController$TransitionEffect r1 = r5.this$0
                                            androidx.fragment.app.FragmentTransitionImpl r1 = r1.getTransitionImpl()
                                            androidx.fragment.app.DefaultSpecialEffectsController$TransitionEffect r2 = r5.this$0
                                            java.util.List r2 = r2.getTransitionInfos()
                                            r3 = 0
                                            java.lang.Object r2 = r2.get(r3)
                                            androidx.fragment.app.DefaultSpecialEffectsController$TransitionInfo r2 = (androidx.fragment.app.DefaultSpecialEffectsController.TransitionInfo) r2
                                            androidx.fragment.app.SpecialEffectsController$Operation r2 = r2.getOperation()
                                            androidx.fragment.app.Fragment r2 = r2.getFragment()
                                            java.lang.Object r3 = r5.$mergedTransition
                                            androidx.fragment.app.DefaultSpecialEffectsController$TransitionEffect r5 = r5.this$0
                                            androidx.fragment.app.DefaultSpecialEffectsController$TransitionEffect$onStart$4$2$$ExternalSyntheticLambda1 r4 = new androidx.fragment.app.DefaultSpecialEffectsController$TransitionEffect$onStart$4$2$$ExternalSyntheticLambda1
                                            r4.<init>(r5)
                                            r1.setListenerForTransitionEnd(r2, r3, r0, r4)
                                            r0.cancel()
                                            return
                                        L67:
                                            boolean r0 = androidx.fragment.app.FragmentManager.isLoggingEnabled(r2)
                                            if (r0 == 0) goto L72
                                            java.lang.String r0 = "Animating to start"
                                            android.util.Log.v(r1, r0)
                                        L72:
                                            androidx.fragment.app.DefaultSpecialEffectsController$TransitionEffect r0 = r5.this$0
                                            androidx.fragment.app.FragmentTransitionImpl r0 = r0.getTransitionImpl()
                                            androidx.fragment.app.DefaultSpecialEffectsController$TransitionEffect r1 = r5.this$0
                                            java.lang.Object r1 = r1.getController()
                                            r1.getClass()
                                            androidx.fragment.app.DefaultSpecialEffectsController$TransitionEffect r2 = r5.this$0
                                            android.view.ViewGroup r5 = r5.$container
                                            androidx.fragment.app.DefaultSpecialEffectsController$TransitionEffect$onStart$4$2$$ExternalSyntheticLambda0 r3 = new androidx.fragment.app.DefaultSpecialEffectsController$TransitionEffect$onStart$4$2$$ExternalSyntheticLambda0
                                            r3.<init>(r2, r5)
                                            r0.animateToStart(r1, r3)
                                            return
                                        */
                                        throw new UnsupportedOperationException("Method not decompiled: androidx.fragment.app.DefaultSpecialEffectsController$TransitionEffect$onStart$4.AnonymousClass2.m1971invoke():void");
                                    }
                                }

                                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                {
                                    super(0);
                                }

                                @Override // kotlin.jvm.functions.Function0
                                public /* bridge */ /* synthetic */ Object invoke() {
                                    m1969invoke();
                                    return Unit.INSTANCE;
                                }

                                /* JADX INFO: renamed from: invoke, reason: collision with other method in class */
                                public final void m1969invoke() {
                                    DefaultSpecialEffectsController.TransitionEffect transitionEffect = this.this$0;
                                    transitionEffect.setController(transitionEffect.getTransitionImpl().controlDelayedTransition(viewGroup, objComponent2));
                                    boolean z = this.this$0.getController() != null;
                                    Object obj = objComponent2;
                                    ViewGroup viewGroup2 = viewGroup;
                                    if (!z) {
                                        throw new IllegalStateException(("Unable to start transition " + obj + " for container " + viewGroup2 + '.').toString());
                                    }
                                    ref$ObjectRef.element = new AnonymousClass2(this.this$0, obj, viewGroup2);
                                    if (FragmentManager.isLoggingEnabled(2)) {
                                        Log.v("FragmentManager", "Started executing operations from " + this.this$0.getFirstOut() + " to " + this.this$0.getLastIn());
                                    }
                                }
                            });
                        }
                    }

                    public final void setController(Object obj) {
                        this.controller = obj;
                    }
                }

                /* JADX INFO: compiled from: DefaultSpecialEffectsController.kt */
                final class TransitionInfo extends SpecialEffectsInfo {
                    private final boolean isOverlapAllowed;
                    private final Object sharedElementTransition;
                    private final Object transition;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    public TransitionInfo(SpecialEffectsController.Operation operation, boolean z, boolean z2) {
                        super(operation);
                        operation.getClass();
                        SpecialEffectsController.Operation.State finalState = operation.getFinalState();
                        SpecialEffectsController.Operation.State state = SpecialEffectsController.Operation.State.VISIBLE;
                        this.transition = finalState == state ? z ? operation.getFragment().getReenterTransition() : operation.getFragment().getEnterTransition() : z ? operation.getFragment().getReturnTransition() : operation.getFragment().getExitTransition();
                        this.isOverlapAllowed = operation.getFinalState() == state ? z ? operation.getFragment().getAllowReturnTransitionOverlap() : operation.getFragment().getAllowEnterTransitionOverlap() : true;
                        this.sharedElementTransition = z2 ? z ? operation.getFragment().getSharedElementReturnTransition() : operation.getFragment().getSharedElementEnterTransition() : null;
                    }

                    private final FragmentTransitionImpl getHandlingImpl(Object obj) {
                        if (obj == null) {
                            return null;
                        }
                        FragmentTransitionImpl fragmentTransitionImpl = FragmentTransition.PLATFORM_IMPL;
                        if (fragmentTransitionImpl != null && fragmentTransitionImpl.canHandle(obj)) {
                            return fragmentTransitionImpl;
                        }
                        FragmentTransitionImpl fragmentTransitionImpl2 = FragmentTransition.SUPPORT_IMPL;
                        if (fragmentTransitionImpl2 != null && fragmentTransitionImpl2.canHandle(obj)) {
                            return fragmentTransitionImpl2;
                        }
                        throw new IllegalArgumentException("Transition " + obj + " for fragment " + getOperation().getFragment() + " is not a valid framework Transition or AndroidX Transition");
                    }

                    public final FragmentTransitionImpl getHandlingImpl() {
                        FragmentTransitionImpl handlingImpl = getHandlingImpl(this.transition);
                        FragmentTransitionImpl handlingImpl2 = getHandlingImpl(this.sharedElementTransition);
                        if (handlingImpl == null || handlingImpl2 == null || handlingImpl == handlingImpl2) {
                            return handlingImpl == null ? handlingImpl2 : handlingImpl;
                        }
                        throw new IllegalArgumentException(("Mixing framework transitions and AndroidX transitions is not allowed. Fragment " + getOperation().getFragment() + " returned Transition " + this.transition + " which uses a different Transition  type than its shared element transition " + this.sharedElementTransition).toString());
                    }

                    public final Object getSharedElementTransition() {
                        return this.sharedElementTransition;
                    }

                    public final Object getTransition() {
                        return this.transition;
                    }

                    public final boolean hasSharedElementTransition() {
                        return this.sharedElementTransition != null;
                    }

                    public final boolean isOverlapAllowed() {
                        return this.isOverlapAllowed;
                    }
                }

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public DefaultSpecialEffectsController(ViewGroup viewGroup) {
                    super(viewGroup);
                    viewGroup.getClass();
                }

                private final void collectAnimEffects(List list) {
                    ArrayList arrayList = new ArrayList();
                    ArrayList arrayList2 = new ArrayList();
                    Iterator it = list.iterator();
                    while (it.hasNext()) {
                        CollectionsKt.addAll(arrayList2, ((AnimationInfo) it.next()).getOperation().getEffects$fragment_release());
                    }
                    boolean zIsEmpty = arrayList2.isEmpty();
                    Iterator it2 = list.iterator();
                    int i = 0;
                    boolean z = false;
                    while (it2.hasNext()) {
                        AnimationInfo animationInfo = (AnimationInfo) it2.next();
                        Context context = getContainer().getContext();
                        SpecialEffectsController.Operation operation = animationInfo.getOperation();
                        context.getClass();
                        FragmentAnim.AnimationOrAnimator animation = animationInfo.getAnimation(context);
                        if (animation != null) {
                            if (animation.animator == null) {
                                arrayList.add(animationInfo);
                            } else {
                                Fragment fragment = operation.getFragment();
                                if (operation.getEffects$fragment_release().isEmpty()) {
                                    if (operation.getFinalState() == SpecialEffectsController.Operation.State.GONE) {
                                        operation.setAwaitingContainerChanges(false);
                                    }
                                    operation.addEffect(new AnimatorEffect(animationInfo));
                                    z = true;
                                } else if (FragmentManager.isLoggingEnabled(2)) {
                                    Log.v("FragmentManager", "Ignoring Animator set on " + fragment + " as this Fragment was involved in a Transition.");
                                }
                            }
                        }
                    }
                    int size = arrayList.size();
                    while (i < size) {
                        Object obj = arrayList.get(i);
                        i++;
                        AnimationInfo animationInfo2 = (AnimationInfo) obj;
                        SpecialEffectsController.Operation operation2 = animationInfo2.getOperation();
                        Fragment fragment2 = operation2.getFragment();
                        if (zIsEmpty) {
                            if (!z) {
                                operation2.addEffect(new AnimationEffect(animationInfo2));
                            } else if (FragmentManager.isLoggingEnabled(2)) {
                                Log.v("FragmentManager", "Ignoring Animation set on " + fragment2 + " as Animations cannot run alongside Animators.");
                            }
                        } else if (FragmentManager.isLoggingEnabled(2)) {
                            Log.v("FragmentManager", "Ignoring Animation set on " + fragment2 + " as Animations cannot run alongside Transitions.");
                        }
                    }
                }

                private final void createTransitionEffect(List list, boolean z, SpecialEffectsController.Operation operation, SpecialEffectsController.Operation operation2) {
                    Object objWrapTransitionInSet;
                    ArrayList arrayList;
                    FragmentTransitionImpl fragmentTransitionImpl;
                    ArrayList arrayList2;
                    Pair pair;
                    ArrayList arrayList3 = new ArrayList();
                    for (Object obj : list) {
                        if (!((TransitionInfo) obj).isVisibilityUnchanged()) {
                            arrayList3.add(obj);
                        }
                    }
                    ArrayList arrayList4 = new ArrayList();
                    int size = arrayList3.size();
                    int i = 0;
                    while (i < size) {
                        Object obj2 = arrayList3.get(i);
                        i++;
                        if (((TransitionInfo) obj2).getHandlingImpl() != null) {
                            arrayList4.add(obj2);
                        }
                    }
                    int size2 = arrayList4.size();
                    FragmentTransitionImpl fragmentTransitionImpl2 = null;
                    int i2 = 0;
                    while (i2 < size2) {
                        Object obj3 = arrayList4.get(i2);
                        i2++;
                        TransitionInfo transitionInfo = (TransitionInfo) obj3;
                        FragmentTransitionImpl handlingImpl = transitionInfo.getHandlingImpl();
                        if (fragmentTransitionImpl2 != null && handlingImpl != fragmentTransitionImpl2) {
                            throw new IllegalArgumentException(("Mixing framework transitions and AndroidX transitions is not allowed. Fragment " + transitionInfo.getOperation().getFragment() + " returned Transition " + transitionInfo.getTransition() + " which uses a different Transition type than other Fragments.").toString());
                        }
                        fragmentTransitionImpl2 = handlingImpl;
                    }
                    if (fragmentTransitionImpl2 == null) {
                        return;
                    }
                    ArrayList arrayList5 = new ArrayList();
                    ArrayList arrayList6 = new ArrayList();
                    ArrayMap arrayMap = new ArrayMap();
                    ArrayList arrayList7 = new ArrayList();
                    ArrayList arrayList8 = new ArrayList();
                    ArrayMap arrayMap2 = new ArrayMap();
                    ArrayMap arrayMap3 = new ArrayMap();
                    int size3 = arrayList4.size();
                    int i3 = 0;
                    loop3: while (true) {
                        objWrapTransitionInSet = null;
                        while (i3 < size3) {
                            Object obj4 = arrayList4.get(i3);
                            i3++;
                            TransitionInfo transitionInfo2 = (TransitionInfo) obj4;
                            if (transitionInfo2.hasSharedElementTransition() && operation != null && operation2 != null) {
                                objWrapTransitionInSet = fragmentTransitionImpl2.wrapTransitionInSet(fragmentTransitionImpl2.cloneTransition(transitionInfo2.getSharedElementTransition()));
                                arrayList8 = operation2.getFragment().getSharedElementSourceNames();
                                arrayList8.getClass();
                                ArrayList sharedElementSourceNames = operation.getFragment().getSharedElementSourceNames();
                                sharedElementSourceNames.getClass();
                                ArrayList sharedElementTargetNames = operation.getFragment().getSharedElementTargetNames();
                                sharedElementTargetNames.getClass();
                                arrayList = arrayList5;
                                int size4 = sharedElementTargetNames.size();
                                fragmentTransitionImpl = fragmentTransitionImpl2;
                                int i4 = 0;
                                while (i4 < size4) {
                                    int i5 = size4;
                                    int iIndexOf = arrayList8.indexOf(sharedElementTargetNames.get(i4));
                                    ArrayList arrayList9 = sharedElementTargetNames;
                                    if (iIndexOf != -1) {
                                        arrayList8.set(iIndexOf, sharedElementSourceNames.get(i4));
                                    }
                                    i4++;
                                    size4 = i5;
                                    sharedElementTargetNames = arrayList9;
                                }
                                arrayList7 = operation2.getFragment().getSharedElementTargetNames();
                                arrayList7.getClass();
                                if (z) {
                                    operation.getFragment().getEnterTransitionCallback();
                                    operation2.getFragment().getExitTransitionCallback();
                                    pair = TuplesKt.to(null, null);
                                } else {
                                    operation.getFragment().getExitTransitionCallback();
                                    operation2.getFragment().getEnterTransitionCallback();
                                    pair = TuplesKt.to(null, null);
                                }
                                MediaControllerCompat$MediaControllerImplApi21$ExtraBinderRequestResultReceiver$$ExternalSyntheticThrowCCEIfNotNull0.m(pair.component1());
                                MediaControllerCompat$MediaControllerImplApi21$ExtraBinderRequestResultReceiver$$ExternalSyntheticThrowCCEIfNotNull0.m(pair.component2());
                                int i6 = 0;
                                for (int size5 = arrayList8.size(); i6 < size5; size5 = size5) {
                                    Object obj5 = arrayList8.get(i6);
                                    obj5.getClass();
                                    String str = (String) obj5;
                                    Object obj6 = arrayList7.get(i6);
                                    obj6.getClass();
                                    arrayMap.put(str, (String) obj6);
                                    i6++;
                                }
                                if (FragmentManager.isLoggingEnabled(2)) {
                                    Log.v("FragmentManager", ">>> entering view names <<<");
                                    arrayList2 = arrayList6;
                                    int i7 = 0;
                                    for (int size6 = arrayList7.size(); i7 < size6; size6 = size6) {
                                        Object obj7 = arrayList7.get(i7);
                                        Log.v("FragmentManager", "Name: " + ((String) obj7));
                                        i7++;
                                    }
                                    Log.v("FragmentManager", ">>> exiting view names <<<");
                                    int i8 = 0;
                                    for (int size7 = arrayList8.size(); i8 < size7; size7 = size7) {
                                        Object obj8 = arrayList8.get(i8);
                                        Log.v("FragmentManager", "Name: " + ((String) obj8));
                                        i8++;
                                    }
                                } else {
                                    arrayList2 = arrayList6;
                                }
                                View view = operation.getFragment().mView;
                                view.getClass();
                                findNamedViews(arrayMap2, view);
                                arrayMap2.retainAll(arrayList8);
                                arrayMap.retainAll(arrayMap2.keySet());
                                View view2 = operation2.getFragment().mView;
                                view2.getClass();
                                findNamedViews(arrayMap3, view2);
                                arrayMap3.retainAll(arrayList7);
                                arrayMap3.retainAll(arrayMap.values());
                                FragmentTransition.retainValues(arrayMap, arrayMap3);
                                Collection collectionKeySet = arrayMap.keySet();
                                collectionKeySet.getClass();
                                retainMatchingViews(arrayMap2, collectionKeySet);
                                Collection collectionValues = arrayMap.values();
                                collectionValues.getClass();
                                retainMatchingViews(arrayMap3, collectionValues);
                                if (arrayMap.isEmpty()) {
                                    break;
                                }
                            } else {
                                arrayList = arrayList5;
                                fragmentTransitionImpl = fragmentTransitionImpl2;
                                arrayList2 = arrayList6;
                            }
                            arrayList5 = arrayList;
                            arrayList6 = arrayList2;
                            fragmentTransitionImpl2 = fragmentTransitionImpl;
                        }
                        Log.i("FragmentManager", "Ignoring shared elements transition " + objWrapTransitionInSet + " between " + operation + " and " + operation2 + " as there are no matching elements in both the entering and exiting fragment. In order to run a SharedElementTransition, both fragments involved must have the element.");
                        arrayList.clear();
                        arrayList2.clear();
                        arrayList5 = arrayList;
                        arrayList6 = arrayList2;
                        fragmentTransitionImpl2 = fragmentTransitionImpl;
                    }
                    ArrayList arrayList10 = arrayList5;
                    FragmentTransitionImpl fragmentTransitionImpl3 = fragmentTransitionImpl2;
                    ArrayList arrayList11 = arrayList6;
                    if (objWrapTransitionInSet == null) {
                        if (arrayList4.isEmpty()) {
                            return;
                        }
                        int size8 = arrayList4.size();
                        int i9 = 0;
                        while (i9 < size8) {
                            Object obj9 = arrayList4.get(i9);
                            i9++;
                            if (((TransitionInfo) obj9).getTransition() == null) {
                            }
                        }
                        return;
                    }
                    TransitionEffect transitionEffect = new TransitionEffect(arrayList4, operation, operation2, fragmentTransitionImpl3, objWrapTransitionInSet, arrayList10, arrayList11, arrayMap, arrayList7, arrayList8, arrayMap2, arrayMap3, z);
                    int size9 = arrayList4.size();
                    int i10 = 0;
                    while (i10 < size9) {
                        Object obj10 = arrayList4.get(i10);
                        i10++;
                        ((TransitionInfo) obj10).getOperation().addEffect(transitionEffect);
                    }
                }

                private final void findNamedViews(Map map, View view) {
                    String transitionName = ViewCompat.getTransitionName(view);
                    if (transitionName != null) {
                        map.put(transitionName, view);
                    }
                    if (view instanceof ViewGroup) {
                        ViewGroup viewGroup = (ViewGroup) view;
                        int childCount = viewGroup.getChildCount();
                        for (int i = 0; i < childCount; i++) {
                            View childAt = viewGroup.getChildAt(i);
                            if (childAt.getVisibility() == 0) {
                                findNamedViews(map, childAt);
                            }
                        }
                    }
                }

                private final void retainMatchingViews(ArrayMap arrayMap, final Collection collection) {
                    Set setEntrySet = arrayMap.entrySet();
                    setEntrySet.getClass();
                    CollectionsKt.retainAll(setEntrySet, new Function1() { // from class: androidx.fragment.app.DefaultSpecialEffectsController.retainMatchingViews.1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final Boolean invoke(Map.Entry entry) {
                            entry.getClass();
                            return Boolean.valueOf(CollectionsKt.contains(collection, ViewCompat.getTransitionName((View) entry.getValue())));
                        }
                    });
                }

                private final void syncAnimations(List list) {
                    Fragment fragment = ((SpecialEffectsController.Operation) CollectionsKt.last(list)).getFragment();
                    Iterator it = list.iterator();
                    while (it.hasNext()) {
                        SpecialEffectsController.Operation operation = (SpecialEffectsController.Operation) it.next();
                        operation.getFragment().mAnimationInfo.mEnterAnim = fragment.mAnimationInfo.mEnterAnim;
                        operation.getFragment().mAnimationInfo.mExitAnim = fragment.mAnimationInfo.mExitAnim;
                        operation.getFragment().mAnimationInfo.mPopEnterAnim = fragment.mAnimationInfo.mPopEnterAnim;
                        operation.getFragment().mAnimationInfo.mPopExitAnim = fragment.mAnimationInfo.mPopExitAnim;
                    }
                }

                /* JADX WARN: Removed duplicated region for block: B:28:0x00b5  */
                @Override // androidx.fragment.app.SpecialEffectsController
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct add '--show-bad-code' argument
                */
                public void collectEffects(java.util.List r9, boolean r10) {
                    /*
                        Method dump skipped, instruction units count: 208
                        To view this dump add '--comments-level debug' option
                    */
                    throw new UnsupportedOperationException("Method not decompiled: androidx.fragment.app.DefaultSpecialEffectsController.collectEffects(java.util.List, boolean):void");
                }
            }
