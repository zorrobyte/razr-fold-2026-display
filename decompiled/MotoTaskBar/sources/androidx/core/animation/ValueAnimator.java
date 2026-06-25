package androidx.core.animation;

import android.os.Looper;
import android.util.AndroidRuntimeException;
import android.util.Log;
import android.view.animation.AnimationUtils;
import androidx.core.animation.AnimationHandler;
import androidx.core.animation.Animator;
import java.util.ArrayList;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public class ValueAnimator extends Animator implements AnimationHandler.AnimationFrameCallback {
    private static final Interpolator sDefaultInterpolator = new AccelerateDecelerateInterpolator();
    private static float sDurationScale = 1.0f;
    private long mPauseTime;
    private boolean mReversing;
    PropertyValuesHolder[] mValues;
    HashMap mValuesMap;
    long mStartTime = -1;
    float mSeekFraction = -1.0f;
    private boolean mResumed = false;
    private float mOverallFraction = 0.0f;
    private float mCurrentFraction = 0.0f;
    private long mLastFrameTime = -1;
    private boolean mRunning = false;
    private boolean mStarted = false;
    private boolean mStartListenersCalled = false;
    boolean mInitialized = false;
    private boolean mAnimationEndRequested = false;
    private long mDuration = 300;
    private long mStartDelay = 0;
    private int mRepeatCount = 0;
    private int mRepeatMode = 1;
    private boolean mSelfPulse = true;
    private boolean mSuppressSelfPulseRequested = false;
    private Interpolator mInterpolator = sDefaultInterpolator;
    private float mDurationScale = -1.0f;
    String mAnimTraceName = null;

    private void addAnimationCallback() {
        if (this.mSelfPulse) {
            Animator.addAnimationCallback(this);
        }
    }

    private float clampFraction(float f) {
        if (f < 0.0f) {
            return 0.0f;
        }
        return this.mRepeatCount != -1 ? Math.min(f, r2 + 1) : f;
    }

    private void endAnimation() {
        ArrayList arrayList;
        if (this.mAnimationEndRequested) {
            return;
        }
        removeAnimationCallback();
        this.mAnimationEndRequested = true;
        this.mPaused = false;
        boolean z = (this.mStarted || this.mRunning) && this.mListeners != null;
        if (z && !this.mRunning) {
            notifyStartListeners();
        }
        this.mRunning = false;
        this.mStarted = false;
        this.mStartListenersCalled = false;
        this.mLastFrameTime = -1L;
        this.mStartTime = -1L;
        if (z && (arrayList = this.mListeners) != null) {
            ArrayList arrayList2 = (ArrayList) arrayList.clone();
            int size = arrayList2.size();
            for (int i = 0; i < size; i++) {
                ((Animator.AnimatorListener) arrayList2.get(i)).onAnimationEnd(this, this.mReversing);
            }
        }
        this.mReversing = false;
    }

    private int getCurrentIteration(float f) {
        float fClampFraction = clampFraction(f);
        double d = fClampFraction;
        double dFloor = Math.floor(d);
        if (d == dFloor && fClampFraction > 0.0f) {
            dFloor -= 1.0d;
        }
        return (int) dFloor;
    }

    private float getCurrentIterationFraction(float f, boolean z) {
        float fClampFraction = clampFraction(f);
        int currentIteration = getCurrentIteration(fClampFraction);
        float f2 = fClampFraction - currentIteration;
        return shouldPlayBackward(currentIteration, z) ? 1.0f - f2 : f2;
    }

    static float getDurationScale() {
        return sDurationScale;
    }

    private long getScaledDuration() {
        return (long) (this.mDuration * resolveDurationScale());
    }

    private boolean isPulsingInternal() {
        return this.mLastFrameTime >= 0;
    }

    private void notifyStartListeners() {
        ArrayList arrayList = this.mListeners;
        if (arrayList != null && !this.mStartListenersCalled) {
            ArrayList arrayList2 = (ArrayList) arrayList.clone();
            int size = arrayList2.size();
            for (int i = 0; i < size; i++) {
                ((Animator.AnimatorListener) arrayList2.get(i)).onAnimationStart(this, this.mReversing);
            }
        }
        this.mStartListenersCalled = true;
    }

    public static ValueAnimator ofFloat(float... fArr) {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setFloatValues(fArr);
        return valueAnimator;
    }

    private void removeAnimationCallback() {
        if (this.mSelfPulse) {
            Animator.removeAnimationCallback(this);
        }
    }

    private float resolveDurationScale() {
        float f = this.mDurationScale;
        return f >= 0.0f ? f : sDurationScale;
    }

    private boolean shouldPlayBackward(int i, boolean z) {
        if (i > 0 && this.mRepeatMode == 2) {
            int i2 = this.mRepeatCount;
            if (i < i2 + 1 || i2 == -1) {
                return z ? i % 2 == 0 : i % 2 != 0;
            }
        }
        return z;
    }

    private void start(boolean z) {
        if (Looper.myLooper() == null) {
            throw new AndroidRuntimeException("Animators may only be run on Looper threads");
        }
        this.mReversing = z;
        this.mSelfPulse = !this.mSuppressSelfPulseRequested;
        if (z) {
            float f = this.mSeekFraction;
            if (f != -1.0f && f != 0.0f) {
                if (this.mRepeatCount == -1) {
                    this.mSeekFraction = 1.0f - ((float) (((double) f) - Math.floor(f)));
                } else {
                    this.mSeekFraction = (r3 + 1) - f;
                }
            }
        }
        this.mStarted = true;
        this.mPaused = false;
        this.mRunning = false;
        this.mAnimationEndRequested = false;
        this.mLastFrameTime = -1L;
        this.mStartTime = -1L;
        if (this.mStartDelay == 0 || this.mSeekFraction >= 0.0f || this.mReversing) {
            startAnimation();
            float f2 = this.mSeekFraction;
            if (f2 == -1.0f) {
                setCurrentPlayTime(0L);
            } else {
                setCurrentFraction(f2);
            }
        }
        addAnimationCallback();
    }

    private void startAnimation() {
        this.mAnimationEndRequested = false;
        initAnimation();
        this.mRunning = true;
        float f = this.mSeekFraction;
        if (f >= 0.0f) {
            this.mOverallFraction = f;
        } else {
            this.mOverallFraction = 0.0f;
        }
        if (this.mListeners != null) {
            notifyStartListeners();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0033  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    boolean animateBasedOnTime(long r7) {
        /*
            r6 = this;
            boolean r0 = r6.mRunning
            r1 = 0
            if (r0 == 0) goto L64
            long r2 = r6.getScaledDuration()
            r4 = 0
            int r0 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r0 <= 0) goto L16
            long r4 = r6.mStartTime
            long r7 = r7 - r4
            float r7 = (float) r7
            float r8 = (float) r2
            float r7 = r7 / r8
            goto L18
        L16:
            r7 = 1065353216(0x3f800000, float:1.0)
        L18:
            float r8 = r6.mOverallFraction
            int r2 = (int) r7
            int r8 = (int) r8
            r3 = 1
            if (r2 <= r8) goto L21
            r8 = r3
            goto L22
        L21:
            r8 = r1
        L22:
            int r2 = r6.mRepeatCount
            int r4 = r2 + 1
            float r4 = (float) r4
            int r4 = (r7 > r4 ? 1 : (r7 == r4 ? 0 : -1))
            if (r4 < 0) goto L30
            r4 = -1
            if (r2 == r4) goto L30
            r2 = r3
            goto L31
        L30:
            r2 = r1
        L31:
            if (r0 != 0) goto L35
        L33:
            r1 = r3
            goto L55
        L35:
            if (r8 == 0) goto L52
            if (r2 != 0) goto L52
            java.util.ArrayList r8 = r6.mListeners
            if (r8 == 0) goto L55
            int r8 = r8.size()
            r0 = r1
        L42:
            if (r0 >= r8) goto L55
            java.util.ArrayList r2 = r6.mListeners
            java.lang.Object r2 = r2.get(r0)
            androidx.core.animation.Animator$AnimatorListener r2 = (androidx.core.animation.Animator.AnimatorListener) r2
            r2.onAnimationRepeat(r6)
            int r0 = r0 + 1
            goto L42
        L52:
            if (r2 == 0) goto L55
            goto L33
        L55:
            float r7 = r6.clampFraction(r7)
            r6.mOverallFraction = r7
            boolean r8 = r6.mReversing
            float r7 = r6.getCurrentIterationFraction(r7, r8)
            r6.animateValue(r7)
        L64:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.animation.ValueAnimator.animateBasedOnTime(long):boolean");
    }

    void animateValue(float f) {
        float interpolation = this.mInterpolator.getInterpolation(f);
        this.mCurrentFraction = interpolation;
        int length = this.mValues.length;
        for (int i = 0; i < length; i++) {
            this.mValues[i].calculateValue(interpolation);
        }
        ArrayList arrayList = this.mUpdateListeners;
        if (arrayList != null) {
            int size = arrayList.size();
            for (int i2 = 0; i2 < size; i2++) {
                ((Animator.AnimatorUpdateListener) this.mUpdateListeners.get(i2)).onAnimationUpdate(this);
            }
        }
    }

    @Override // androidx.core.animation.Animator
    public void cancel() {
        if (Looper.myLooper() == null) {
            throw new AndroidRuntimeException("Animators may only be run on Looper threads");
        }
        if (this.mAnimationEndRequested) {
            return;
        }
        if ((this.mStarted || this.mRunning) && this.mListeners != null) {
            if (!this.mRunning) {
                notifyStartListeners();
            }
            ArrayList arrayList = (ArrayList) this.mListeners.clone();
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                ((Animator.AnimatorListener) obj).onAnimationCancel(this);
            }
        }
        endAnimation();
    }

    @Override // 
    /* JADX INFO: renamed from: clone, reason: merged with bridge method [inline-methods] */
    public ValueAnimator mo1050clone() {
        ValueAnimator valueAnimator = (ValueAnimator) super.mo1050clone();
        if (this.mUpdateListeners != null) {
            valueAnimator.mUpdateListeners = new ArrayList(this.mUpdateListeners);
        }
        valueAnimator.mSeekFraction = -1.0f;
        valueAnimator.mReversing = false;
        valueAnimator.mInitialized = false;
        valueAnimator.mStarted = false;
        valueAnimator.mRunning = false;
        valueAnimator.mPaused = false;
        valueAnimator.mResumed = false;
        valueAnimator.mStartListenersCalled = false;
        valueAnimator.mStartTime = -1L;
        valueAnimator.mAnimationEndRequested = false;
        valueAnimator.mPauseTime = -1L;
        valueAnimator.mLastFrameTime = -1L;
        valueAnimator.mOverallFraction = 0.0f;
        valueAnimator.mCurrentFraction = 0.0f;
        valueAnimator.mSelfPulse = true;
        valueAnimator.mSuppressSelfPulseRequested = false;
        PropertyValuesHolder[] propertyValuesHolderArr = this.mValues;
        if (propertyValuesHolderArr != null) {
            int length = propertyValuesHolderArr.length;
            valueAnimator.mValues = new PropertyValuesHolder[length];
            valueAnimator.mValuesMap = new HashMap(length);
            for (int i = 0; i < length; i++) {
                PropertyValuesHolder propertyValuesHolderM1051clone = propertyValuesHolderArr[i].m1051clone();
                valueAnimator.mValues[i] = propertyValuesHolderM1051clone;
                valueAnimator.mValuesMap.put(propertyValuesHolderM1051clone.getPropertyName(), propertyValuesHolderM1051clone);
            }
        }
        return valueAnimator;
    }

    @Override // androidx.core.animation.AnimationHandler.AnimationFrameCallback
    public final boolean doAnimationFrame(long j) {
        if (this.mStartTime < 0) {
            this.mStartTime = this.mReversing ? j : ((long) (this.mStartDelay * resolveDurationScale())) + j;
        }
        if (this.mPaused) {
            this.mPauseTime = j;
            removeAnimationCallback();
            return false;
        }
        if (this.mResumed) {
            this.mResumed = false;
            long j2 = this.mPauseTime;
            if (j2 > 0) {
                this.mStartTime += j - j2;
            }
        }
        if (!this.mRunning) {
            if (this.mStartTime > j && this.mSeekFraction == -1.0f) {
                return false;
            }
            this.mRunning = true;
            startAnimation();
        }
        if (this.mLastFrameTime < 0 && this.mSeekFraction >= 0.0f) {
            this.mStartTime = j - ((long) (getScaledDuration() * this.mSeekFraction));
            this.mSeekFraction = -1.0f;
        }
        this.mLastFrameTime = j;
        boolean zAnimateBasedOnTime = animateBasedOnTime(Math.max(j, this.mStartTime));
        if (zAnimateBasedOnTime) {
            endAnimation();
        }
        return zAnimateBasedOnTime;
    }

    @Override // androidx.core.animation.Animator
    public void end() {
        if (Looper.myLooper() == null) {
            throw new AndroidRuntimeException("Animators may only be run on Looper threads");
        }
        if (!this.mRunning) {
            startAnimation();
            this.mStarted = true;
        } else if (!this.mInitialized) {
            initAnimation();
        }
        animateValue(shouldPlayBackward(this.mRepeatCount, this.mReversing) ? 0.0f : 1.0f);
        endAnimation();
    }

    public Object getAnimatedValue() {
        PropertyValuesHolder[] propertyValuesHolderArr = this.mValues;
        if (propertyValuesHolderArr == null || propertyValuesHolderArr.length <= 0) {
            return null;
        }
        return propertyValuesHolderArr[0].getAnimatedValue();
    }

    public long getDuration() {
        return this.mDuration;
    }

    @Override // androidx.core.animation.Animator
    public long getStartDelay() {
        return this.mStartDelay;
    }

    @Override // androidx.core.animation.Animator
    public long getTotalDuration() {
        int i = this.mRepeatCount;
        if (i == -1) {
            return -1L;
        }
        return this.mStartDelay + (this.mDuration * ((long) (i + 1)));
    }

    public PropertyValuesHolder[] getValues() {
        return this.mValues;
    }

    void initAnimation() {
        if (this.mInitialized) {
            return;
        }
        int length = this.mValues.length;
        for (int i = 0; i < length; i++) {
            this.mValues[i].init();
        }
        this.mInitialized = true;
    }

    @Override // androidx.core.animation.Animator
    boolean isInitialized() {
        return this.mInitialized;
    }

    public boolean isRunning() {
        return this.mRunning;
    }

    @Override // androidx.core.animation.Animator
    public boolean isStarted() {
        return this.mStarted;
    }

    @Override // androidx.core.animation.Animator
    boolean pulseAnimationFrame(long j) {
        if (this.mSelfPulse) {
            return false;
        }
        return doAnimationFrame(j);
    }

    @Override // androidx.core.animation.Animator
    public void reverse() {
        if (isPulsingInternal()) {
            long jCurrentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
            this.mStartTime = jCurrentAnimationTimeMillis - (getScaledDuration() - (jCurrentAnimationTimeMillis - this.mStartTime));
            this.mReversing = !this.mReversing;
            return;
        }
        if (!this.mStarted) {
            start(true);
        } else {
            this.mReversing = !this.mReversing;
            end();
        }
    }

    public void setCurrentFraction(float f) {
        initAnimation();
        float fClampFraction = clampFraction(f);
        if (isPulsingInternal()) {
            this.mStartTime = AnimationUtils.currentAnimationTimeMillis() - ((long) (getScaledDuration() * fClampFraction));
        } else {
            this.mSeekFraction = fClampFraction;
        }
        this.mOverallFraction = fClampFraction;
        animateValue(getCurrentIterationFraction(fClampFraction, this.mReversing));
    }

    public void setCurrentPlayTime(long j) {
        long j2 = this.mDuration;
        setCurrentFraction(j2 > 0 ? j / j2 : 1.0f);
    }

    @Override // androidx.core.animation.Animator
    public ValueAnimator setDuration(long j) {
        if (j >= 0) {
            this.mDuration = j;
            return this;
        }
        throw new IllegalArgumentException("Animators cannot have negative duration: " + j);
    }

    public void setFloatValues(float... fArr) {
        if (fArr == null || fArr.length == 0) {
            return;
        }
        PropertyValuesHolder[] propertyValuesHolderArr = this.mValues;
        if (propertyValuesHolderArr == null || propertyValuesHolderArr.length == 0) {
            setValues(PropertyValuesHolder.ofFloat("", fArr));
        } else {
            propertyValuesHolderArr[0].setFloatValues(fArr);
        }
        this.mInitialized = false;
    }

    @Override // androidx.core.animation.Animator
    public void setInterpolator(Interpolator interpolator) {
        if (interpolator != null) {
            this.mInterpolator = interpolator;
        } else {
            this.mInterpolator = new LinearInterpolator();
        }
    }

    @Override // androidx.core.animation.Animator
    public void setStartDelay(long j) {
        if (j < 0) {
            Log.w("ValueAnimator", "Start delay should always be non-negative");
            j = 0;
        }
        this.mStartDelay = j;
    }

    public void setValues(PropertyValuesHolder... propertyValuesHolderArr) {
        int length = propertyValuesHolderArr.length;
        this.mValues = propertyValuesHolderArr;
        this.mValuesMap = new HashMap(length);
        for (PropertyValuesHolder propertyValuesHolder : propertyValuesHolderArr) {
            this.mValuesMap.put(propertyValuesHolder.getPropertyName(), propertyValuesHolder);
        }
        this.mInitialized = false;
    }

    @Override // androidx.core.animation.Animator
    void skipToEndValue(boolean z) {
        initAnimation();
        animateValue((this.mRepeatCount % 2 == 1 && this.mRepeatMode == 2) ? 0.0f : z ? 0.0f : 1.0f);
    }

    @Override // androidx.core.animation.Animator
    public void start() {
        start(false);
    }

    @Override // androidx.core.animation.Animator
    void startWithoutPulsing(boolean z) {
        this.mSuppressSelfPulseRequested = true;
        if (z) {
            reverse();
        } else {
            start();
        }
        this.mSuppressSelfPulseRequested = false;
    }

    public String toString() {
        String str = "ValueAnimator@" + Integer.toHexString(hashCode());
        if (this.mValues != null) {
            for (int i = 0; i < this.mValues.length; i++) {
                str = str + "\n    " + this.mValues[i].toString();
            }
        }
        return str;
    }
}
