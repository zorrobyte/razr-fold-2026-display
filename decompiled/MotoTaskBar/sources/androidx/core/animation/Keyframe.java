package androidx.core.animation;

/* JADX INFO: loaded from: classes.dex */
public abstract class Keyframe implements Cloneable {
    float mFraction;
    boolean mHasValue;
    private Interpolator mInterpolator = null;
    Class mValueType;
    boolean mValueWasSetOnStart;

    class FloatKeyframe extends Keyframe {
        float mValue;

        FloatKeyframe(float f) {
            this.mFraction = f;
            this.mValueType = Float.TYPE;
        }

        FloatKeyframe(float f, float f2) {
            this.mFraction = f;
            this.mValue = f2;
            this.mValueType = Float.TYPE;
            this.mHasValue = true;
        }

        @Override // androidx.core.animation.Keyframe
        /* JADX INFO: renamed from: clone, reason: merged with bridge method [inline-methods] */
        public FloatKeyframe m1049clone() {
            FloatKeyframe floatKeyframe = this.mHasValue ? new FloatKeyframe(getFraction(), this.mValue) : new FloatKeyframe(getFraction());
            floatKeyframe.setInterpolator(getInterpolator());
            floatKeyframe.mValueWasSetOnStart = this.mValueWasSetOnStart;
            return floatKeyframe;
        }

        public float getFloatValue() {
            return this.mValue;
        }

        @Override // androidx.core.animation.Keyframe
        public Float getValue() {
            return Float.valueOf(this.mValue);
        }

        @Override // androidx.core.animation.Keyframe
        public void setValue(Float f) {
            if (f == null || f.getClass() != Float.class) {
                return;
            }
            this.mValue = f.floatValue();
            this.mHasValue = true;
        }
    }

    public static Keyframe ofFloat(float f) {
        return new FloatKeyframe(f);
    }

    public static Keyframe ofFloat(float f, float f2) {
        return new FloatKeyframe(f, f2);
    }

    /* JADX INFO: renamed from: clone */
    public abstract Keyframe m1049clone();

    public float getFraction() {
        return this.mFraction;
    }

    public Interpolator getInterpolator() {
        return this.mInterpolator;
    }

    public abstract Object getValue();

    public boolean hasValue() {
        return this.mHasValue;
    }

    public void setInterpolator(Interpolator interpolator) {
        this.mInterpolator = interpolator;
    }

    public abstract void setValue(Object obj);

    void setValueWasSetOnStart(boolean z) {
        this.mValueWasSetOnStart = z;
    }

    boolean valueWasSetOnStart() {
        return this.mValueWasSetOnStart;
    }
}
