package com.android.systemui.plugins.clocks;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.DisplayMetrics;
import android.view.View;
import androidx.constraintlayout.widget.ConstraintSet;
import com.android.internal.policy.SystemBarUtils;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: ClockFaceLayout.kt */
/* JADX INFO: loaded from: classes.dex */
public final class DefaultClockFaceLayout implements ClockFaceLayout {
    private final View view;
    private final List views;
    public static final Companion Companion = new Companion(null);
    public static final int $stable = 8;

    /* JADX INFO: compiled from: ClockFaceLayout.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final ConstraintSet applyDefaultPreviewConstraints(ClockPreviewConfig clockPreviewConfig, ConstraintSet constraintSet) throws PackageManager.NameNotFoundException {
            clockPreviewConfig.getClass();
            constraintSet.getClass();
            Context context = clockPreviewConfig.getContext();
            ContextExt contextExt = ContextExt.INSTANCE;
            int id = contextExt.getId(context, "lockscreen_clock_view_large");
            constraintSet.constrainWidth(id, -2);
            constraintSet.constrainHeight(id, -2);
            constraintSet.constrainMaxHeight(id, 0);
            constraintSet.connect(id, 3, 0, 3, SystemBarUtils.getStatusBarHeight(context) + contextExt.getDimen(context, "small_clock_padding_top") + contextExt.getDimen(context, "keyguard_smartspace_top_offset") + contextExt.getDimen(context, "date_weather_view_height") + contextExt.getDimen(context, "enhanced_smartspace_height"));
            constraintSet.connect(id, 6, 0, 6);
            constraintSet.connect(id, 7, 0, 7);
            Float udfpsTop = clockPreviewConfig.getUdfpsTop();
            if (udfpsTop != null) {
                constraintSet.connect(id, 4, 0, 4, (int) (context.getResources().getDisplayMetrics().heightPixels - udfpsTop.floatValue()));
            } else {
                Integer lockId = clockPreviewConfig.getLockId();
                if (lockId != null) {
                    constraintSet.connect(id, 4, lockId.intValue(), 3);
                } else {
                    constraintSet.connect(id, 4, 0, 4, contextExt.getDimen(context, "lock_icon_margin_bottom") + (((int) ((DisplayMetrics.DENSITY_DEVICE_STABLE / 160.0f) * 36)) * 2));
                }
            }
            int id2 = contextExt.getId(context, "lockscreen_clock_view");
            constraintSet.constrainWidth(id2, -2);
            constraintSet.constrainHeight(id2, contextExt.getDimen(context, "small_clock_height"));
            constraintSet.connect(id2, 6, 0, 6, contextExt.getDimen(context, "clock_padding_start") + contextExt.getDimen(context, "status_view_margin_horizontal"));
            constraintSet.connect(id2, 3, 0, 3, ClockPreviewConfig.getSmallClockTopPadding$default(clockPreviewConfig, 0, 1, null));
            return constraintSet;
        }
    }

    public DefaultClockFaceLayout(View view) {
        view.getClass();
        this.view = view;
        this.views = CollectionsKt.listOf(view);
    }

    @Override // com.android.systemui.plugins.clocks.ClockFaceLayout
    public void applyAodBurnIn(AodClockBurnInModel aodClockBurnInModel) {
        aodClockBurnInModel.getClass();
    }

    @Override // com.android.systemui.plugins.clocks.ClockFaceLayout
    public ConstraintSet applyConstraints(ConstraintSet constraintSet) {
        constraintSet.getClass();
        if (getViews().size() == 1) {
            return constraintSet;
        }
        throw new IllegalArgumentException("Should have only one container view when using DefaultClockFaceLayout");
    }

    @Override // com.android.systemui.plugins.clocks.ClockFaceLayout
    public ConstraintSet applyPreviewConstraints(ClockPreviewConfig clockPreviewConfig, ConstraintSet constraintSet) {
        clockPreviewConfig.getClass();
        constraintSet.getClass();
        return Companion.applyDefaultPreviewConstraints(clockPreviewConfig, constraintSet);
    }

    public final View getView() {
        return this.view;
    }

    @Override // com.android.systemui.plugins.clocks.ClockFaceLayout
    public List getViews() {
        return this.views;
    }
}
