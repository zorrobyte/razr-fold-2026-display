package com.android.wm.shell.shared.animation;

import com.android.wm.shell.shared.animation.PhysicsAnimator;
import java.util.WeakHashMap;

/* JADX INFO: compiled from: PhysicsAnimator.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class PhysicsAnimatorKt {
    private static final float UNSET = -3.4028235E38f;
    private static boolean verboseLogging;
    private static final WeakHashMap animators = new WeakHashMap();
    private static final PhysicsAnimator.SpringConfig globalDefaultSpring = new PhysicsAnimator.SpringConfig(1500.0f, 0.5f);
    private static final PhysicsAnimator.FlingConfig globalDefaultFling = new PhysicsAnimator.FlingConfig(1.0f, -3.4028235E38f, Float.MAX_VALUE);

    public static final WeakHashMap getAnimators() {
        return animators;
    }
}
