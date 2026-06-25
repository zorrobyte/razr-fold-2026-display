package com.android.systemui.statusbar;

import android.view.View;
import com.android.app.animation.Interpolators;
import com.android.systemui.res.R$id;

/* JADX INFO: loaded from: classes.dex */
public abstract class CrossFadeHelper {
    /* JADX INFO: renamed from: $r8$lambda$A2jLuQwKoW-z_ak1nsALvLfEBHQ, reason: not valid java name */
    public static /* synthetic */ void m1609$r8$lambda$A2jLuQwKoWz_ak1nsALvLfEBHQ(Runnable runnable, View view) {
        if (runnable != null) {
            runnable.run();
        }
        if (view.getVisibility() != 8) {
            view.setVisibility(4);
        }
    }

    public static void fadeIn(View view) {
        fadeIn(view, 210L, 0);
    }

    public static void fadeIn(View view, float f) {
        fadeIn(view, f, false);
    }

    public static void fadeIn(View view, float f, boolean z) {
        view.animate().cancel();
        if (view.getVisibility() == 4) {
            view.setVisibility(0);
        }
        if (z) {
            f = mapToFadeDuration(f);
        }
        float interpolation = Interpolators.ALPHA_IN.getInterpolation(f);
        view.setAlpha(interpolation);
        updateLayerType(view, interpolation);
    }

    public static void fadeIn(View view, long j, int i) {
        fadeIn(view, j, i, null);
    }

    public static void fadeIn(View view, long j, int i, Runnable runnable) {
        view.animate().cancel();
        if (view.getVisibility() == 4) {
            view.setAlpha(0.0f);
            view.setVisibility(0);
        }
        view.animate().alpha(1.0f).setDuration(j).setStartDelay(i).setInterpolator(Interpolators.ALPHA_IN).withEndAction(runnable);
        if (!view.hasOverlappingRendering() || view.getLayerType() == 2) {
            return;
        }
        view.animate().withLayer();
    }

    public static void fadeOut(View view, float f) {
        fadeOut(view, f, true);
    }

    public static void fadeOut(View view, float f, boolean z) {
        view.animate().cancel();
        if (f == 1.0f && view.getVisibility() != 8) {
            view.setVisibility(4);
        } else if (view.getVisibility() == 4) {
            view.setVisibility(0);
        }
        if (z) {
            f = mapToFadeDuration(f);
        }
        float interpolation = Interpolators.ALPHA_OUT.getInterpolation(1.0f - f);
        view.setAlpha(interpolation);
        updateLayerType(view, interpolation);
    }

    public static void fadeOut(final View view, long j, int i, final Runnable runnable) {
        view.animate().cancel();
        view.animate().alpha(0.0f).setDuration(j).setInterpolator(Interpolators.ALPHA_OUT).setStartDelay(i).withEndAction(new Runnable() { // from class: com.android.systemui.statusbar.CrossFadeHelper$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                CrossFadeHelper.m1609$r8$lambda$A2jLuQwKoWz_ak1nsALvLfEBHQ(runnable, view);
            }
        });
        if (view.hasOverlappingRendering()) {
            view.animate().withLayer();
        }
    }

    public static void fadeOut(View view, Runnable runnable) {
        fadeOut(view, 210L, 0, runnable);
    }

    private static float mapToFadeDuration(float f) {
        return Math.min(f / 0.5833333f, 1.0f);
    }

    private static void updateLayerType(View view, float f) {
        if (!view.hasOverlappingRendering() || f <= 0.0f || f >= 1.0f) {
            if (view.getLayerType() != 2 || view.getTag(R$id.cross_fade_layer_type_changed_tag) == null) {
                return;
            }
            view.setLayerType(0, null);
            return;
        }
        if (view.getLayerType() != 2) {
            view.setLayerType(2, null);
            view.setTag(R$id.cross_fade_layer_type_changed_tag, Boolean.TRUE);
        }
    }
}
