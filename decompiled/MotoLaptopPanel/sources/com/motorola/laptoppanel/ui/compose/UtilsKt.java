package com.motorola.laptoppanel.ui.compose;

import android.os.SystemProperties;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.Dp;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.jvm.functions.Function0;

/* JADX INFO: compiled from: Utils.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class UtilsKt {
    private static final Lazy physicalDensity$delegate = LazyKt.lazy(new Function0() { // from class: com.motorola.laptoppanel.ui.compose.UtilsKt$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return Float.valueOf(UtilsKt.physicalDensity_delegate$lambda$2());
        }
    });

    /* JADX INFO: renamed from: getDpValueForCurrentDensity-8Feqmps, reason: not valid java name */
    public static final float m2170getDpValueForCurrentDensity8Feqmps(float f, Composer composer, int i) {
        composer.startReplaceGroup(-281077246);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart(-281077246, i, -1, "com.motorola.laptoppanel.ui.compose.getDpValueForCurrentDensity (Utils.kt:16)");
        }
        Density density = (Density) composer.consume(CompositionLocalsKt.getLocalDensity());
        composer.startReplaceGroup(-243540286);
        boolean zChanged = ((((i & 14) ^ 6) > 4 && composer.changed(f)) || (i & 6) == 4) | composer.changed(density);
        Object objRememberedValue = composer.rememberedValue();
        if (zChanged || objRememberedValue == Composer.Companion.getEmpty()) {
            objRememberedValue = Dp.m1875boximpl(density.mo143toDpu2uoSUM(f * getPhysicalDensity()));
            composer.updateRememberedValue(objRememberedValue);
        }
        float fM1883unboximpl = ((Dp) objRememberedValue).m1883unboximpl();
        composer.endReplaceGroup();
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composer.endReplaceGroup();
        return fM1883unboximpl;
    }

    private static final float getPhysicalDensity() {
        return ((Number) physicalDensity$delegate.getValue()).floatValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final float physicalDensity_delegate$lambda$2() {
        int i = 480;
        try {
            i = SystemProperties.getInt("ro.sf.lcd_density", 480);
        } catch (Exception unused) {
        }
        return i / 160.0f;
    }
}
