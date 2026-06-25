package com.android.wm.shell.shared.animation;

import android.util.ArrayMap;
import android.util.Log;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.FlingAnimation;
import androidx.dynamicanimation.animation.FloatPropertyCompat;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import com.android.wm.shell.shared.animation.PhysicsAnimator;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: PhysicsAnimator.kt */
/* JADX INFO: loaded from: classes.dex */
public final class PhysicsAnimator {
    public static final Companion Companion = new Companion(null);
    private static Function2 onAnimatorCreated = new Function2() { // from class: com.android.wm.shell.shared.animation.PhysicsAnimator$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return PhysicsAnimator.onAnimatorCreated$lambda$8((PhysicsAnimator) obj, obj2);
        }
    };
    private Function1 cancelAction;
    private FlingConfig defaultFling;
    private SpringConfig defaultSpring;
    private final ArrayList endActions;
    private final ArrayList endListeners;
    private final ArrayMap flingAnimations;
    private final ArrayMap flingConfigs;
    private ArrayList internalListeners;
    private final ArrayMap springAnimations;
    private final ArrayMap springConfigs;
    private Function0 startAction;
    private final ArrayList updateListeners;
    private final WeakReference weakTarget;

    /* JADX INFO: compiled from: PhysicsAnimator.kt */
    public final class AnimationUpdate {
        private final float value;
        private final float velocity;

        public AnimationUpdate(float f, float f2) {
            this.value = f;
            this.velocity = f2;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof AnimationUpdate)) {
                return false;
            }
            AnimationUpdate animationUpdate = (AnimationUpdate) obj;
            return Float.compare(this.value, animationUpdate.value) == 0 && Float.compare(this.velocity, animationUpdate.velocity) == 0;
        }

        public int hashCode() {
            return (Float.hashCode(this.value) * 31) + Float.hashCode(this.velocity);
        }

        public String toString() {
            return "AnimationUpdate(value=" + this.value + ", velocity=" + this.velocity + ")";
        }
    }

    /* JADX INFO: compiled from: PhysicsAnimator.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final PhysicsAnimator getInstance(Object obj) {
            obj.getClass();
            if (!PhysicsAnimatorKt.getAnimators().containsKey(obj)) {
                PhysicsAnimator physicsAnimator = new PhysicsAnimator(obj, null);
                getOnAnimatorCreated$frameworks__base__libs__WindowManager__Shell__shared__android_common__WindowManager_Shell_shared().invoke(physicsAnimator, obj);
                PhysicsAnimatorKt.getAnimators().put(obj, physicsAnimator);
            }
            Object obj2 = PhysicsAnimatorKt.getAnimators().get(obj);
            obj2.getClass();
            return (PhysicsAnimator) obj2;
        }

        public final Function2 getOnAnimatorCreated$frameworks__base__libs__WindowManager__Shell__shared__android_common__WindowManager_Shell_shared() {
            return PhysicsAnimator.onAnimatorCreated;
        }

        public final String getReadablePropertyName(FloatPropertyCompat floatPropertyCompat) {
            floatPropertyCompat.getClass();
            return Intrinsics.areEqual(floatPropertyCompat, DynamicAnimation.TRANSLATION_X) ? "translationX" : Intrinsics.areEqual(floatPropertyCompat, DynamicAnimation.TRANSLATION_Y) ? "translationY" : Intrinsics.areEqual(floatPropertyCompat, DynamicAnimation.TRANSLATION_Z) ? "translationZ" : Intrinsics.areEqual(floatPropertyCompat, DynamicAnimation.SCALE_X) ? "scaleX" : Intrinsics.areEqual(floatPropertyCompat, DynamicAnimation.SCALE_Y) ? "scaleY" : Intrinsics.areEqual(floatPropertyCompat, DynamicAnimation.ROTATION) ? "rotation" : Intrinsics.areEqual(floatPropertyCompat, DynamicAnimation.ROTATION_X) ? "rotationX" : Intrinsics.areEqual(floatPropertyCompat, DynamicAnimation.ROTATION_Y) ? "rotationY" : Intrinsics.areEqual(floatPropertyCompat, DynamicAnimation.SCROLL_X) ? "scrollX" : Intrinsics.areEqual(floatPropertyCompat, DynamicAnimation.SCROLL_Y) ? "scrollY" : Intrinsics.areEqual(floatPropertyCompat, DynamicAnimation.ALPHA) ? "alpha" : "Custom FloatPropertyCompat instance";
        }
    }

    /* JADX INFO: compiled from: PhysicsAnimator.kt */
    public interface EndListener {
        void onAnimationEnd(Object obj, FloatPropertyCompat floatPropertyCompat, boolean z, boolean z2, float f, float f2, boolean z3);
    }

    /* JADX INFO: compiled from: PhysicsAnimator.kt */
    public final class FlingConfig {
        private float friction;
        private float max;
        private float min;
        private float startVelocity;

        public FlingConfig(float f, float f2, float f3) {
            this(f, f2, f3, 0.0f);
        }

        public FlingConfig(float f, float f2, float f3, float f4) {
            this.friction = f;
            this.min = f2;
            this.max = f3;
            this.startVelocity = f4;
        }

        public final void applyToAnimation$frameworks__base__libs__WindowManager__Shell__shared__android_common__WindowManager_Shell_shared(FlingAnimation flingAnimation) {
            flingAnimation.getClass();
            flingAnimation.setFriction(this.friction);
            flingAnimation.setMinValue(this.min);
            flingAnimation.setMaxValue(this.max);
            flingAnimation.setStartVelocity(this.startVelocity);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof FlingConfig)) {
                return false;
            }
            FlingConfig flingConfig = (FlingConfig) obj;
            return Float.compare(this.friction, flingConfig.friction) == 0 && Float.compare(this.min, flingConfig.min) == 0 && Float.compare(this.max, flingConfig.max) == 0 && Float.compare(this.startVelocity, flingConfig.startVelocity) == 0;
        }

        public final float getMax() {
            return this.max;
        }

        public final float getMin() {
            return this.min;
        }

        public int hashCode() {
            return (((((Float.hashCode(this.friction) * 31) + Float.hashCode(this.min)) * 31) + Float.hashCode(this.max)) * 31) + Float.hashCode(this.startVelocity);
        }

        public final void setMax(float f) {
            this.max = f;
        }

        public final void setMin(float f) {
            this.min = f;
        }

        public String toString() {
            return "FlingConfig(friction=" + this.friction + ", min=" + this.min + ", max=" + this.max + ", startVelocity=" + this.startVelocity + ")";
        }
    }

    /* JADX INFO: compiled from: PhysicsAnimator.kt */
    public final class InternalListener {
        private List endActions;
        private List endListeners;
        private int numPropertiesAnimating;
        private Set properties;
        private final Object target;
        final /* synthetic */ PhysicsAnimator this$0;
        private final ArrayMap undispatchedUpdates;
        private List updateListeners;

        public InternalListener(PhysicsAnimator physicsAnimator, Object obj, Set set, List list, List list2, List list3) {
            set.getClass();
            list.getClass();
            list2.getClass();
            list3.getClass();
            this.this$0 = physicsAnimator;
            this.target = obj;
            this.properties = set;
            this.updateListeners = list;
            this.endListeners = list2;
            this.endActions = list3;
            this.numPropertiesAnimating = set.size();
            this.undispatchedUpdates = new ArrayMap();
        }

        private final void maybeDispatchUpdates() {
            if (this.undispatchedUpdates.size() < this.numPropertiesAnimating || this.undispatchedUpdates.size() <= 0) {
                return;
            }
            Iterator it = this.updateListeners.iterator();
            while (it.hasNext()) {
                ((UpdateListener) it.next()).onAnimationUpdateForProperty(this.target, new ArrayMap(this.undispatchedUpdates));
            }
            this.undispatchedUpdates.clear();
        }

        public final boolean onInternalAnimationEnd$frameworks__base__libs__WindowManager__Shell__shared__android_common__WindowManager_Shell_shared(FloatPropertyCompat floatPropertyCompat, boolean z, float f, float f2, boolean z2) {
            floatPropertyCompat.getClass();
            if (!this.properties.contains(floatPropertyCompat)) {
                return false;
            }
            this.numPropertiesAnimating--;
            maybeDispatchUpdates();
            if (this.undispatchedUpdates.containsKey(floatPropertyCompat)) {
                for (UpdateListener updateListener : this.updateListeners) {
                    Object obj = this.target;
                    ArrayMap arrayMap = new ArrayMap();
                    arrayMap.put(floatPropertyCompat, this.undispatchedUpdates.get(floatPropertyCompat));
                    Unit unit = Unit.INSTANCE;
                    updateListener.onAnimationUpdateForProperty(obj, arrayMap);
                }
                this.undispatchedUpdates.remove(floatPropertyCompat);
            }
            boolean zArePropertiesAnimating = this.this$0.arePropertiesAnimating(this.properties);
            boolean z3 = !zArePropertiesAnimating;
            List list = this.endListeners;
            PhysicsAnimator physicsAnimator = this.this$0;
            Iterator it = list.iterator();
            while (it.hasNext()) {
                ((EndListener) it.next()).onAnimationEnd(this.target, floatPropertyCompat, z2, z, f, f2, z3);
                if (physicsAnimator.isPropertyAnimating(floatPropertyCompat)) {
                    return false;
                }
            }
            if (!zArePropertiesAnimating && !z) {
                Iterator it2 = this.endActions.iterator();
                while (it2.hasNext()) {
                    ((Function0) it2.next()).mo2224invoke();
                }
            }
            return z3;
        }

        public final void onInternalAnimationUpdate$frameworks__base__libs__WindowManager__Shell__shared__android_common__WindowManager_Shell_shared(FloatPropertyCompat floatPropertyCompat, float f, float f2) {
            floatPropertyCompat.getClass();
            if (this.properties.contains(floatPropertyCompat)) {
                this.undispatchedUpdates.put(floatPropertyCompat, new AnimationUpdate(f, f2));
                maybeDispatchUpdates();
            }
        }
    }

    /* JADX INFO: compiled from: PhysicsAnimator.kt */
    public final class SpringConfig {
        private float dampingRatio;
        private float finalPosition;
        private float startVelocity;
        private float stiffness;

        public SpringConfig(float f, float f2) {
            this(f, f2, 0.0f, 0.0f, 8, null);
        }

        public SpringConfig(float f, float f2, float f3, float f4) {
            this.stiffness = f;
            this.dampingRatio = f2;
            this.startVelocity = f3;
            this.finalPosition = f4;
        }

        public /* synthetic */ SpringConfig(float f, float f2, float f3, float f4, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(f, f2, (i & 4) != 0 ? 0.0f : f3, (i & 8) != 0 ? PhysicsAnimatorKt.UNSET : f4);
        }

        public final void applyToAnimation$frameworks__base__libs__WindowManager__Shell__shared__android_common__WindowManager_Shell_shared(SpringAnimation springAnimation) {
            springAnimation.getClass();
            SpringForce spring = springAnimation.getSpring();
            if (spring == null) {
                spring = new SpringForce();
            }
            spring.setStiffness(this.stiffness);
            spring.setDampingRatio(this.dampingRatio);
            spring.setFinalPosition(this.finalPosition);
            springAnimation.setSpring(spring);
            float f = this.startVelocity;
            if (f == 0.0f) {
                return;
            }
            springAnimation.setStartVelocity(f);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof SpringConfig)) {
                return false;
            }
            SpringConfig springConfig = (SpringConfig) obj;
            return Float.compare(this.stiffness, springConfig.stiffness) == 0 && Float.compare(this.dampingRatio, springConfig.dampingRatio) == 0 && Float.compare(this.startVelocity, springConfig.startVelocity) == 0 && Float.compare(this.finalPosition, springConfig.finalPosition) == 0;
        }

        public final float getDampingRatio$frameworks__base__libs__WindowManager__Shell__shared__android_common__WindowManager_Shell_shared() {
            return this.dampingRatio;
        }

        public final float getFinalPosition$frameworks__base__libs__WindowManager__Shell__shared__android_common__WindowManager_Shell_shared() {
            return this.finalPosition;
        }

        public final float getStiffness() {
            return this.stiffness;
        }

        public int hashCode() {
            return (((((Float.hashCode(this.stiffness) * 31) + Float.hashCode(this.dampingRatio)) * 31) + Float.hashCode(this.startVelocity)) * 31) + Float.hashCode(this.finalPosition);
        }

        public final void setFinalPosition$frameworks__base__libs__WindowManager__Shell__shared__android_common__WindowManager_Shell_shared(float f) {
            this.finalPosition = f;
        }

        public final void setStartVelocity$frameworks__base__libs__WindowManager__Shell__shared__android_common__WindowManager_Shell_shared(float f) {
            this.startVelocity = f;
        }

        public String toString() {
            return "SpringConfig(stiffness=" + this.stiffness + ", dampingRatio=" + this.dampingRatio + ", startVelocity=" + this.startVelocity + ", finalPosition=" + this.finalPosition + ")";
        }
    }

    /* JADX INFO: compiled from: PhysicsAnimator.kt */
    public interface UpdateListener {
        void onAnimationUpdateForProperty(Object obj, ArrayMap arrayMap);
    }

    /* JADX INFO: renamed from: com.android.wm.shell.shared.animation.PhysicsAnimator$configureDynamicAnimation$2, reason: invalid class name */
    /* JADX INFO: compiled from: PhysicsAnimator.kt */
    final class AnonymousClass2 implements DynamicAnimation.OnAnimationEndListener {
        final /* synthetic */ DynamicAnimation $anim;
        final /* synthetic */ FloatPropertyCompat $property;

        AnonymousClass2(FloatPropertyCompat floatPropertyCompat, DynamicAnimation dynamicAnimation) {
            this.$property = floatPropertyCompat;
            this.$anim = dynamicAnimation;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final boolean onAnimationEnd$lambda$0(FloatPropertyCompat floatPropertyCompat, boolean z, float f, float f2, DynamicAnimation dynamicAnimation, InternalListener internalListener) {
            internalListener.getClass();
            return internalListener.onInternalAnimationEnd$frameworks__base__libs__WindowManager__Shell__shared__android_common__WindowManager_Shell_shared(floatPropertyCompat, z, f, f2, dynamicAnimation instanceof FlingAnimation);
        }

        @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationEndListener
        public final void onAnimationEnd(DynamicAnimation dynamicAnimation, final boolean z, final float f, final float f2) {
            ArrayList internalListeners$frameworks__base__libs__WindowManager__Shell__shared__android_common__WindowManager_Shell_shared = PhysicsAnimator.this.getInternalListeners$frameworks__base__libs__WindowManager__Shell__shared__android_common__WindowManager_Shell_shared();
            final FloatPropertyCompat floatPropertyCompat = this.$property;
            final DynamicAnimation dynamicAnimation2 = this.$anim;
            CollectionsKt.removeAll((List) internalListeners$frameworks__base__libs__WindowManager__Shell__shared__android_common__WindowManager_Shell_shared, new Function1() { // from class: com.android.wm.shell.shared.animation.PhysicsAnimator$configureDynamicAnimation$2$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return Boolean.valueOf(PhysicsAnimator.AnonymousClass2.onAnimationEnd$lambda$0(floatPropertyCompat, z, f, f2, dynamicAnimation2, (PhysicsAnimator.InternalListener) obj));
                }
            });
            if (Intrinsics.areEqual(PhysicsAnimator.this.springAnimations.get(this.$property), this.$anim)) {
                PhysicsAnimator.this.springAnimations.remove(this.$property);
            }
            if (Intrinsics.areEqual(PhysicsAnimator.this.flingAnimations.get(this.$property), this.$anim)) {
                PhysicsAnimator.this.flingAnimations.remove(this.$property);
            }
        }
    }

    private PhysicsAnimator(Object obj) {
        this.weakTarget = new WeakReference(obj);
        this.springAnimations = new ArrayMap();
        this.flingAnimations = new ArrayMap();
        this.springConfigs = new ArrayMap();
        this.flingConfigs = new ArrayMap();
        this.updateListeners = new ArrayList();
        this.endListeners = new ArrayList();
        this.endActions = new ArrayList();
        this.defaultSpring = PhysicsAnimatorKt.globalDefaultSpring;
        this.defaultFling = PhysicsAnimatorKt.globalDefaultFling;
        this.internalListeners = new ArrayList();
        this.startAction = new PhysicsAnimator$startAction$1(this);
        this.cancelAction = new PhysicsAnimator$cancelAction$1(this);
    }

    public /* synthetic */ PhysicsAnimator(Object obj, DefaultConstructorMarker defaultConstructorMarker) {
        this(obj);
    }

    private final void clearAnimator() {
        this.springConfigs.clear();
        this.flingConfigs.clear();
        this.updateListeners.clear();
        this.endListeners.clear();
        this.endActions.clear();
    }

    private final DynamicAnimation configureDynamicAnimation(DynamicAnimation dynamicAnimation, final FloatPropertyCompat floatPropertyCompat) {
        dynamicAnimation.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() { // from class: com.android.wm.shell.shared.animation.PhysicsAnimator.configureDynamicAnimation.1
            @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationUpdateListener
            public final void onAnimationUpdate(DynamicAnimation dynamicAnimation2, float f, float f2) {
                int size = PhysicsAnimator.this.getInternalListeners$frameworks__base__libs__WindowManager__Shell__shared__android_common__WindowManager_Shell_shared().size();
                for (int i = 0; i < size; i++) {
                    ((InternalListener) PhysicsAnimator.this.getInternalListeners$frameworks__base__libs__WindowManager__Shell__shared__android_common__WindowManager_Shell_shared().get(i)).onInternalAnimationUpdate$frameworks__base__libs__WindowManager__Shell__shared__android_common__WindowManager_Shell_shared(floatPropertyCompat, f, f2);
                }
            }
        });
        dynamicAnimation.addEndListener(new AnonymousClass2(floatPropertyCompat, dynamicAnimation));
        return dynamicAnimation;
    }

    private final FlingAnimation getFlingAnimation(FloatPropertyCompat floatPropertyCompat, Object obj) {
        ArrayMap arrayMap = this.flingAnimations;
        Object obj2 = arrayMap.get(floatPropertyCompat);
        if (obj2 == null) {
            Object objConfigureDynamicAnimation = configureDynamicAnimation(new FlingAnimation(obj, floatPropertyCompat), floatPropertyCompat);
            objConfigureDynamicAnimation.getClass();
            obj2 = (FlingAnimation) objConfigureDynamicAnimation;
            arrayMap.put(floatPropertyCompat, obj2);
        }
        return (FlingAnimation) obj2;
    }

    public static final PhysicsAnimator getInstance(Object obj) {
        return Companion.getInstance(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final SpringAnimation getSpringAnimation(FloatPropertyCompat floatPropertyCompat, Object obj) {
        ArrayMap arrayMap = this.springAnimations;
        Object obj2 = arrayMap.get(floatPropertyCompat);
        if (obj2 == null) {
            Object objConfigureDynamicAnimation = configureDynamicAnimation(new SpringAnimation(obj, floatPropertyCompat), floatPropertyCompat);
            objConfigureDynamicAnimation.getClass();
            obj2 = (SpringAnimation) objConfigureDynamicAnimation;
            arrayMap.put(floatPropertyCompat, obj2);
        }
        return (SpringAnimation) obj2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isValidValue(float f) {
        return f < Float.MAX_VALUE && f > -3.4028235E38f;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onAnimatorCreated$lambda$8(PhysicsAnimator physicsAnimator, Object obj) {
        physicsAnimator.getClass();
        obj.getClass();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit startInternal$lambda$3(FlingConfig flingConfig, PhysicsAnimator physicsAnimator, FloatPropertyCompat floatPropertyCompat, Object obj, float f) {
        flingConfig.setMin(Math.min(f, flingConfig.getMin()));
        flingConfig.setMax(Math.max(f, flingConfig.getMax()));
        physicsAnimator.cancel(floatPropertyCompat);
        FlingAnimation flingAnimation = physicsAnimator.getFlingAnimation(floatPropertyCompat, obj);
        flingConfig.applyToAnimation$frameworks__base__libs__WindowManager__Shell__shared__android_common__WindowManager_Shell_shared(flingAnimation);
        flingAnimation.start();
        return Unit.INSTANCE;
    }

    public final PhysicsAnimator addEndListener(EndListener endListener) {
        endListener.getClass();
        this.endListeners.add(endListener);
        return this;
    }

    public final PhysicsAnimator addUpdateListener(UpdateListener updateListener) {
        updateListener.getClass();
        this.updateListeners.add(updateListener);
        return this;
    }

    public final boolean arePropertiesAnimating(Set set) {
        set.getClass();
        Set set2 = set;
        if ((set2 instanceof Collection) && set2.isEmpty()) {
            return false;
        }
        Iterator it = set2.iterator();
        while (it.hasNext()) {
            if (isPropertyAnimating((FloatPropertyCompat) it.next())) {
                return true;
            }
        }
        return false;
    }

    public final void cancel() {
        if (this.flingAnimations.size() > 0) {
            Function1 function1 = this.cancelAction;
            Set setKeySet = this.flingAnimations.keySet();
            setKeySet.getClass();
            function1.invoke(setKeySet);
        }
        if (this.springAnimations.size() > 0) {
            Function1 function12 = this.cancelAction;
            Set setKeySet2 = this.springAnimations.keySet();
            setKeySet2.getClass();
            function12.invoke(setKeySet2);
        }
    }

    public final void cancel(FloatPropertyCompat... floatPropertyCompatArr) {
        floatPropertyCompatArr.getClass();
        this.cancelAction.invoke(ArraysKt.toSet(floatPropertyCompatArr));
    }

    public final void cancelInternal$frameworks__base__libs__WindowManager__Shell__shared__android_common__WindowManager_Shell_shared(Set set) {
        set.getClass();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            FloatPropertyCompat floatPropertyCompat = (FloatPropertyCompat) it.next();
            FlingAnimation flingAnimation = (FlingAnimation) this.flingAnimations.get(floatPropertyCompat);
            if (flingAnimation != null) {
                flingAnimation.cancel();
            }
            SpringAnimation springAnimation = (SpringAnimation) this.springAnimations.get(floatPropertyCompat);
            if (springAnimation != null) {
                springAnimation.cancel();
            }
        }
    }

    public final Set getAnimatedProperties$frameworks__base__libs__WindowManager__Shell__shared__android_common__WindowManager_Shell_shared() {
        Set setKeySet = this.springConfigs.keySet();
        setKeySet.getClass();
        Set setKeySet2 = this.flingConfigs.keySet();
        setKeySet2.getClass();
        return CollectionsKt.union(setKeySet, setKeySet2);
    }

    public final ArrayList getInternalListeners$frameworks__base__libs__WindowManager__Shell__shared__android_common__WindowManager_Shell_shared() {
        return this.internalListeners;
    }

    public final boolean isPropertyAnimating(FloatPropertyCompat floatPropertyCompat) {
        floatPropertyCompat.getClass();
        SpringAnimation springAnimation = (SpringAnimation) this.springAnimations.get(floatPropertyCompat);
        if (springAnimation != null ? springAnimation.isRunning() : false) {
            return true;
        }
        FlingAnimation flingAnimation = (FlingAnimation) this.flingAnimations.get(floatPropertyCompat);
        return flingAnimation != null ? flingAnimation.isRunning() : false;
    }

    public final boolean isRunning() {
        Set setKeySet = this.springAnimations.keySet();
        setKeySet.getClass();
        Set setKeySet2 = this.flingAnimations.keySet();
        setKeySet2.getClass();
        return arePropertiesAnimating(CollectionsKt.union(setKeySet, setKeySet2));
    }

    public final PhysicsAnimator spring(FloatPropertyCompat floatPropertyCompat, float f, float f2, float f3, float f4) {
        floatPropertyCompat.getClass();
        if (PhysicsAnimatorKt.verboseLogging) {
            Log.d("PhysicsAnimator", "Springing " + Companion.getReadablePropertyName(floatPropertyCompat) + " to " + f + ".");
        }
        this.springConfigs.put(floatPropertyCompat, new SpringConfig(f3, f4, f2, f));
        return this;
    }

    public final PhysicsAnimator spring(FloatPropertyCompat floatPropertyCompat, float f, float f2, SpringConfig springConfig) {
        floatPropertyCompat.getClass();
        springConfig.getClass();
        return spring(floatPropertyCompat, f, f2, springConfig.getStiffness(), springConfig.getDampingRatio$frameworks__base__libs__WindowManager__Shell__shared__android_common__WindowManager_Shell_shared());
    }

    public final PhysicsAnimator spring(FloatPropertyCompat floatPropertyCompat, float f, SpringConfig springConfig) {
        floatPropertyCompat.getClass();
        springConfig.getClass();
        return spring(floatPropertyCompat, f, 0.0f, springConfig);
    }

    public final void start() {
        this.startAction.mo2224invoke();
    }

    public final void startInternal$frameworks__base__libs__WindowManager__Shell__shared__android_common__WindowManager_Shell_shared() {
        int i;
        Object obj;
        Object obj2 = this.weakTarget.get();
        if (obj2 == null) {
            Log.w("PhysicsAnimator", "Trying to animate a GC-ed object.");
            return;
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = getAnimatedProperties$frameworks__base__libs__WindowManager__Shell__shared__android_common__WindowManager_Shell_shared().iterator();
        while (true) {
            i = 0;
            if (!it.hasNext()) {
                break;
            }
            final FloatPropertyCompat floatPropertyCompat = (FloatPropertyCompat) it.next();
            final FlingConfig flingConfig = (FlingConfig) this.flingConfigs.get(floatPropertyCompat);
            final SpringConfig springConfig = (SpringConfig) this.springConfigs.get(floatPropertyCompat);
            final float value = floatPropertyCompat.getValue(obj2);
            if (flingConfig != null) {
                final Object obj3 = obj2;
                Function0 function0 = new Function0() { // from class: com.android.wm.shell.shared.animation.PhysicsAnimator$$ExternalSyntheticLambda1
                    @Override // kotlin.jvm.functions.Function0
                    /* JADX INFO: renamed from: invoke */
                    public final Object mo2224invoke() {
                        return PhysicsAnimator.startInternal$lambda$3(flingConfig, this, floatPropertyCompat, obj3, value);
                    }
                };
                flingConfig = flingConfig;
                obj = obj3;
                arrayList.add(function0);
            } else {
                obj = obj2;
            }
            if (springConfig != null) {
                if (flingConfig == null) {
                    SpringAnimation springAnimation = getSpringAnimation(floatPropertyCompat, obj);
                    springConfig.applyToAnimation$frameworks__base__libs__WindowManager__Shell__shared__android_common__WindowManager_Shell_shared(springAnimation);
                    if (isValidValue(springConfig.getFinalPosition$frameworks__base__libs__WindowManager__Shell__shared__android_common__WindowManager_Shell_shared())) {
                        arrayList.add(new PhysicsAnimator$startInternal$2(springAnimation));
                    } else {
                        Log.w("PhysicsAnimator", "startInternal flingConfig is null, spring final positionis invalid, finalPosition = " + springConfig.getFinalPosition$frameworks__base__libs__WindowManager__Shell__shared__android_common__WindowManager_Shell_shared());
                    }
                } else {
                    final float min = flingConfig.getMin();
                    final float max = flingConfig.getMax();
                    this.endListeners.add(0, new EndListener() { // from class: com.android.wm.shell.shared.animation.PhysicsAnimator$startInternal$3
                        @Override // com.android.wm.shell.shared.animation.PhysicsAnimator.EndListener
                        public void onAnimationEnd(Object obj4, FloatPropertyCompat floatPropertyCompat2, boolean z, boolean z2, float f, float f2, boolean z3) {
                            floatPropertyCompat2.getClass();
                            if (Intrinsics.areEqual(floatPropertyCompat2, floatPropertyCompat) && z && !z2) {
                                boolean z4 = Math.abs(f2) > 0.0f;
                                boolean z5 = min <= f && f <= max;
                                if (z4 || !z5) {
                                    springConfig.setStartVelocity$frameworks__base__libs__WindowManager__Shell__shared__android_common__WindowManager_Shell_shared(f2);
                                    if (springConfig.getFinalPosition$frameworks__base__libs__WindowManager__Shell__shared__android_common__WindowManager_Shell_shared() == PhysicsAnimatorKt.UNSET) {
                                        if (z4) {
                                            springConfig.setFinalPosition$frameworks__base__libs__WindowManager__Shell__shared__android_common__WindowManager_Shell_shared(f2 < 0.0f ? min : max);
                                        } else if (!z5) {
                                            PhysicsAnimator.SpringConfig springConfig2 = springConfig;
                                            float f3 = min;
                                            if (f >= f3) {
                                                f3 = max;
                                            }
                                            springConfig2.setFinalPosition$frameworks__base__libs__WindowManager__Shell__shared__android_common__WindowManager_Shell_shared(f3);
                                        }
                                    }
                                    if (this.isValidValue(springConfig.getFinalPosition$frameworks__base__libs__WindowManager__Shell__shared__android_common__WindowManager_Shell_shared())) {
                                        SpringAnimation springAnimation2 = this.getSpringAnimation(floatPropertyCompat, obj4);
                                        springConfig.applyToAnimation$frameworks__base__libs__WindowManager__Shell__shared__android_common__WindowManager_Shell_shared(springAnimation2);
                                        springAnimation2.start();
                                    } else {
                                        Log.w("PhysicsAnimator", "startInternal spring final position is invalid,finalPosition = " + springConfig.getFinalPosition$frameworks__base__libs__WindowManager__Shell__shared__android_common__WindowManager_Shell_shared());
                                    }
                                }
                            }
                        }
                    });
                }
            }
            obj2 = obj;
        }
        this.internalListeners.add(new InternalListener(this, obj2, getAnimatedProperties$frameworks__base__libs__WindowManager__Shell__shared__android_common__WindowManager_Shell_shared(), new ArrayList(this.updateListeners), new ArrayList(this.endListeners), new ArrayList(this.endActions)));
        int size = arrayList.size();
        while (i < size) {
            Object obj4 = arrayList.get(i);
            i++;
            ((Function0) obj4).mo2224invoke();
        }
        clearAnimator();
    }
}
