package com.airbnb.lottie.animation.keyframe;

import android.graphics.Path;
import android.graphics.PointF;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.utils.Utils;
import com.airbnb.lottie.value.Keyframe;

/* JADX INFO: loaded from: classes.dex */
public class PathKeyframe extends Keyframe {
    private Path path;
    private final Keyframe pointKeyFrame;

    public PathKeyframe(LottieComposition lottieComposition, Keyframe keyframe) {
        super(lottieComposition, (PointF) keyframe.startValue, (PointF) keyframe.endValue, keyframe.interpolator, keyframe.xInterpolator, keyframe.yInterpolator, keyframe.startFrame, keyframe.endFrame);
        this.pointKeyFrame = keyframe;
        createPath();
    }

    public void createPath() {
        Object obj;
        Object obj2;
        Object obj3 = this.endValue;
        boolean z = (obj3 == null || (obj2 = this.startValue) == null || !((PointF) obj2).equals(((PointF) obj3).x, ((PointF) obj3).y)) ? false : true;
        Object obj4 = this.startValue;
        if (obj4 == null || (obj = this.endValue) == null || z) {
            return;
        }
        Keyframe keyframe = this.pointKeyFrame;
        this.path = Utils.createPath((PointF) obj4, (PointF) obj, keyframe.pathCp1, keyframe.pathCp2);
    }

    Path getPath() {
        return this.path;
    }
}
