package com.android.systemui.plugins.clocks;

import androidx.constraintlayout.widget.ConstraintSet;
import java.util.List;

/* JADX INFO: compiled from: ClockFaceLayout.kt */
/* JADX INFO: loaded from: classes.dex */
public interface ClockFaceLayout {
    void applyAodBurnIn(AodClockBurnInModel aodClockBurnInModel);

    ConstraintSet applyConstraints(ConstraintSet constraintSet);

    ConstraintSet applyPreviewConstraints(ClockPreviewConfig clockPreviewConfig, ConstraintSet constraintSet);

    List getViews();
}
