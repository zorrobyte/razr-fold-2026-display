package com.android.systemui.statusbar.notification;

import com.android.systemui.Flags;
import java.util.Iterator;
import java.util.Map;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.ranges.IntRange;

/* JADX INFO: compiled from: Roundable.kt */
/* JADX INFO: loaded from: classes.dex */
public interface Roundable {
    default void applyRoundnessAndInvalidate() {
        getRoundableState().getTargetView$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core().invalidate();
    }

    default float getBottomCornerRadius() {
        return Flags.notificationsImprovedHunAnimation() ? getRoundableState().getBottomCornerRadius$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core() : getBottomRoundness() * getMaxRadius();
    }

    default float getBottomRoundness() {
        return getRoundableState().getBottomRoundness$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core();
    }

    int getClipHeight();

    default float getMaxRadius() {
        return getRoundableState().getMaxRadius$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core();
    }

    RoundableState getRoundableState();

    default float getTopCornerRadius() {
        return Flags.notificationsImprovedHunAnimation() ? getRoundableState().getTopCornerRadius$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core() : getTopRoundness() * getMaxRadius();
    }

    default float getTopRoundness() {
        return getRoundableState().getTopRoundness$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core();
    }

    default float[] getUpdatedRadii() {
        float[] radiiBuffer$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core = getRoundableState().getRadiiBuffer$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core();
        updateRadii(getTopCornerRadius(), getBottomCornerRadius(), radiiBuffer$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core);
        return radiiBuffer$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core;
    }

    default boolean hasRoundedCorner() {
        return (getTopRoundness() == 0.0f && getBottomRoundness() == 0.0f) ? false : true;
    }

    default boolean requestBottomRoundness(float f, SourceType sourceType) {
        sourceType.getClass();
        return requestBottomRoundness(f, sourceType, getRoundableState().getTargetView$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core().isShown());
    }

    default boolean requestBottomRoundness(float f, SourceType sourceType, boolean z) {
        sourceType.getClass();
        Map bottomRoundnessMap$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core = getRoundableState().getBottomRoundnessMap$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core();
        Float fMaxOrNull = CollectionsKt.maxOrNull(bottomRoundnessMap$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core.values());
        float fFloatValue = fMaxOrNull != null ? fMaxOrNull.floatValue() : 0.0f;
        if (f == 0.0f) {
            bottomRoundnessMap$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core.remove(sourceType);
        } else {
            bottomRoundnessMap$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core.put(sourceType, Float.valueOf(f));
        }
        Float fMaxOrNull2 = CollectionsKt.maxOrNull(bottomRoundnessMap$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core.values());
        float fFloatValue2 = fMaxOrNull2 != null ? fMaxOrNull2.floatValue() : 0.0f;
        if (fFloatValue == fFloatValue2) {
            return false;
        }
        getRoundableState().setBottomRoundness$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core(fFloatValue2, (getRoundableState().isBottomAnimating$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core() && (Math.abs(fFloatValue2 - fFloatValue) > 0.5f ? 1 : (Math.abs(fFloatValue2 - fFloatValue) == 0.5f ? 0 : -1)) > 0) || z);
        return true;
    }

    default boolean requestRoundness(float f, float f2, SourceType sourceType) {
        sourceType.getClass();
        return requestRoundness(f, f2, sourceType, getRoundableState().getTargetView$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core().isShown());
    }

    default boolean requestRoundness(float f, float f2, SourceType sourceType, boolean z) {
        sourceType.getClass();
        return requestTopRoundness(f, sourceType, z) || requestBottomRoundness(f2, sourceType, z);
    }

    default void requestRoundnessReset(SourceType sourceType) {
        sourceType.getClass();
        requestRoundnessReset(sourceType, getRoundableState().getTargetView$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core().isShown());
    }

    default void requestRoundnessReset(SourceType sourceType, boolean z) {
        sourceType.getClass();
        requestRoundness(0.0f, 0.0f, sourceType, z);
    }

    default boolean requestTopRoundness(float f, SourceType sourceType) {
        sourceType.getClass();
        return requestTopRoundness(f, sourceType, getRoundableState().getTargetView$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core().isShown());
    }

    default boolean requestTopRoundness(float f, SourceType sourceType, boolean z) {
        sourceType.getClass();
        Map topRoundnessMap$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core = getRoundableState().getTopRoundnessMap$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core();
        Float fMaxOrNull = CollectionsKt.maxOrNull(topRoundnessMap$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core.values());
        float fFloatValue = fMaxOrNull != null ? fMaxOrNull.floatValue() : 0.0f;
        if (f == 0.0f) {
            topRoundnessMap$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core.remove(sourceType);
        } else {
            topRoundnessMap$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core.put(sourceType, Float.valueOf(f));
        }
        Float fMaxOrNull2 = CollectionsKt.maxOrNull(topRoundnessMap$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core.values());
        float fFloatValue2 = fMaxOrNull2 != null ? fMaxOrNull2.floatValue() : 0.0f;
        if (fFloatValue == fFloatValue2) {
            return false;
        }
        getRoundableState().setTopRoundness$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core(fFloatValue2, (getRoundableState().isTopAnimating$motorola__packages__apps__MotoTaskBar__android_common__MotoTaskBar_core() && (Math.abs(fFloatValue2 - fFloatValue) > 0.5f ? 1 : (Math.abs(fFloatValue2 - fFloatValue) == 0.5f ? 0 : -1)) > 0) || z);
        return true;
    }

    default void updateRadii(float f, float f2, float[] fArr) {
        fArr.getClass();
        if (fArr.length != 8) {
            throw new IllegalStateException(("Unexpected radiiBuffer size " + fArr.length).toString());
        }
        if (fArr[0] == f && fArr[4] == f2) {
            return;
        }
        Iterator it = new IntRange(0, 3).iterator();
        while (it.hasNext()) {
            fArr[((IntIterator) it).nextInt()] = f;
        }
        Iterator it2 = new IntRange(4, 7).iterator();
        while (it2.hasNext()) {
            fArr[((IntIterator) it2).nextInt()] = f2;
        }
    }
}
