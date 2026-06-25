package androidx.core.view;

import android.view.MotionEvent;
import android.view.VelocityTracker;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

/* JADX INFO: loaded from: classes.dex */
public abstract class VelocityTrackerCompat {
    private static Map sFallbackTrackers = Collections.synchronizedMap(new WeakHashMap());

    abstract class Api34Impl {
        static float getAxisVelocity(VelocityTracker velocityTracker, int i) {
            return velocityTracker.getAxisVelocity(i);
        }
    }

    public static void addMovement(VelocityTracker velocityTracker, MotionEvent motionEvent) {
        velocityTracker.addMovement(motionEvent);
    }

    public static void computeCurrentVelocity(VelocityTracker velocityTracker, int i) {
        computeCurrentVelocity(velocityTracker, i, Float.MAX_VALUE);
    }

    public static void computeCurrentVelocity(VelocityTracker velocityTracker, int i, float f) {
        velocityTracker.computeCurrentVelocity(i, f);
        getFallbackTrackerOrNull(velocityTracker);
    }

    public static float getAxisVelocity(VelocityTracker velocityTracker, int i) {
        return Api34Impl.getAxisVelocity(velocityTracker, i);
    }

    private static VelocityTrackerFallback getFallbackTrackerOrNull(VelocityTracker velocityTracker) {
        return (VelocityTrackerFallback) sFallbackTrackers.get(velocityTracker);
    }
}
