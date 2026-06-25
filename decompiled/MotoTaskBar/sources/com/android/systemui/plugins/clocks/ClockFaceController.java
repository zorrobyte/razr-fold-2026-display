package com.android.systemui.plugins.clocks;

import android.view.View;
import kotlin.Deprecated;

/* JADX INFO: compiled from: ClockFaceController.kt */
/* JADX INFO: loaded from: classes.dex */
public interface ClockFaceController {

    /* JADX INFO: compiled from: ClockFaceController.kt */
    public final class DefaultImpls {
        @Deprecated
        public static /* synthetic */ void getView$annotations() {
        }
    }

    ClockAnimations getAnimations();

    ClockFaceConfig getConfig();

    ClockFaceEvents getEvents();

    ClockFaceLayout getLayout();

    ThemeConfig getTheme();

    View getView();
}
