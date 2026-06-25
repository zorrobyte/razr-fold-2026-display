package androidx.core.animation;

import android.util.Log;
import androidx.core.animation.Keyframe;
import java.util.Arrays;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
abstract class KeyframeSet implements Keyframes {
    TypeEvaluator mEvaluator;
    Keyframe mFirstKeyframe;
    Interpolator mInterpolator;
    List mKeyframes;
    Keyframe mLastKeyframe;
    int mNumKeyframes;

    KeyframeSet(Keyframe... keyframeArr) {
        this.mNumKeyframes = keyframeArr.length;
        this.mKeyframes = Arrays.asList(keyframeArr);
        this.mFirstKeyframe = keyframeArr[0];
        Keyframe keyframe = keyframeArr[this.mNumKeyframes - 1];
        this.mLastKeyframe = keyframe;
        this.mInterpolator = keyframe.getInterpolator();
    }

    static KeyframeSet ofFloat(float... fArr) {
        int length = fArr.length;
        Keyframe.FloatKeyframe[] floatKeyframeArr = new Keyframe.FloatKeyframe[Math.max(length, 2)];
        boolean z = false;
        if (length == 1) {
            floatKeyframeArr[0] = (Keyframe.FloatKeyframe) Keyframe.ofFloat(0.0f);
            floatKeyframeArr[1] = (Keyframe.FloatKeyframe) Keyframe.ofFloat(1.0f, fArr[0]);
            if (Float.isNaN(fArr[0])) {
                z = true;
            }
        } else {
            floatKeyframeArr[0] = (Keyframe.FloatKeyframe) Keyframe.ofFloat(0.0f, fArr[0]);
            for (int i = 1; i < length; i++) {
                floatKeyframeArr[i] = (Keyframe.FloatKeyframe) Keyframe.ofFloat(i / (length - 1), fArr[i]);
                if (Float.isNaN(fArr[i])) {
                    z = true;
                }
            }
        }
        if (z) {
            Log.w("Animator", "Bad value (NaN) in float animator");
        }
        return new FloatKeyframeSet(floatKeyframeArr);
    }

    @Override // androidx.core.animation.Keyframes
    public List getKeyframes() {
        return this.mKeyframes;
    }

    @Override // androidx.core.animation.Keyframes
    public void setEvaluator(TypeEvaluator typeEvaluator) {
        this.mEvaluator = typeEvaluator;
    }

    public String toString() {
        String str = " ";
        for (int i = 0; i < this.mNumKeyframes; i++) {
            str = str + ((Keyframe) this.mKeyframes.get(i)).getValue() + "  ";
        }
        return str;
    }
}
