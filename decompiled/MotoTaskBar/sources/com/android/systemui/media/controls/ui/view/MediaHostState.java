package com.android.systemui.media.controls.ui.view;

import com.android.systemui.util.animation.DisappearParameters;
import com.android.systemui.util.animation.MeasurementInput;

/* JADX INFO: compiled from: MediaHost.kt */
/* JADX INFO: loaded from: classes.dex */
public interface MediaHostState {
    MediaHostState copy();

    DisappearParameters getDisappearParameters();

    boolean getExpandedMatchesParentHeight();

    float getExpansion();

    boolean getFalsingProtectionNeeded();

    MeasurementInput getMeasurementInput();

    boolean getShowsOnlyActiveMedia();

    float getSquishFraction();

    boolean getVisible();

    void setExpansion(float f);
}
