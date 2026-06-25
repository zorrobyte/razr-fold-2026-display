package androidx.transition;

import android.R;

/* JADX INFO: loaded from: classes.dex */
abstract class Styleable {
    static final int[] TRANSITION_TARGET = {R.attr.targetClass, R.attr.targetId, R.attr.excludeId, R.attr.excludeClass, R.attr.targetName, R.attr.excludeName};
    static final int[] TRANSITION_MANAGER = {R.attr.fromScene, R.attr.toScene, R.attr.transition};
    static final int[] TRANSITION = {R.attr.interpolator, R.attr.duration, R.attr.startDelay, R.attr.matchOrder};
    static final int[] CHANGE_BOUNDS = {R.attr.resizeClip};
    static final int[] VISIBILITY_TRANSITION = {R.attr.transitionVisibilityMode};
    static final int[] FADE = {R.attr.fadingMode};
    static final int[] CHANGE_TRANSFORM = {R.attr.reparent, R.attr.reparentWithOverlay};
    static final int[] SLIDE = {R.attr.slideEdge};
    static final int[] TRANSITION_SET = {R.attr.transitionOrdering};
    static final int[] ARC_MOTION = {R.attr.minimumHorizontalAngle, R.attr.minimumVerticalAngle, R.attr.maximumAngle};
    static final int[] PATTERN_PATH_MOTION = {R.attr.patternPathData};
}
