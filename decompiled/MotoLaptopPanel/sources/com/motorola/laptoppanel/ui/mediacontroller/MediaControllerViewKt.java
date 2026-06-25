package com.motorola.laptoppanel.ui.mediacontroller;

import androidx.compose.material3.MaterialTheme;
import androidx.compose.material3.SliderColors;
import androidx.compose.material3.SliderDefaults;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.MutableFloatState;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.State;
import androidx.compose.ui.Modifier;
import com.motorola.laptoppanel.ui.mediacontroller.MediaUiState;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: MediaControllerView.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class MediaControllerViewKt {
    /* JADX WARN: Removed duplicated region for block: B:79:0x021d  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x0242  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final void MediaControllerView(final com.motorola.laptoppanel.ui.mediacontroller.MediaControllerModel r22, androidx.compose.ui.Modifier r23, androidx.compose.runtime.Composer r24, final int r25, final int r26) {
        /*
            Method dump skipped, instruction units count: 657
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.motorola.laptoppanel.ui.mediacontroller.MediaControllerViewKt.MediaControllerView(com.motorola.laptoppanel.ui.mediacontroller.MediaControllerModel, androidx.compose.ui.Modifier, androidx.compose.runtime.Composer, int, int):void");
    }

    private static final MediaUiState MediaControllerView$lambda$0(State state) {
        return (MediaUiState) state.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit MediaControllerView$lambda$18$lambda$11$lambda$10(MediaControllerModel mediaControllerModel) {
        mediaControllerModel.skipToNext();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit MediaControllerView$lambda$18$lambda$13$lambda$12(MediaControllerModel mediaControllerModel) {
        mediaControllerModel.skipToPrevious();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit MediaControllerView$lambda$18$lambda$15$lambda$14(MutableState mutableState, MutableFloatState mutableFloatState, float f) {
        MediaControllerView$lambda$3(mutableState, true);
        mutableFloatState.setFloatValue(f);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit MediaControllerView$lambda$18$lambda$17$lambda$16(MediaControllerModel mediaControllerModel, MutableState mutableState, MutableFloatState mutableFloatState) {
        MediaControllerView$lambda$3(mutableState, false);
        mediaControllerModel.seekTo((long) mutableFloatState.getFloatValue());
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit MediaControllerView$lambda$18$lambda$9$lambda$8(MediaUiState mediaUiState, MediaControllerModel mediaControllerModel) {
        if (((MediaUiState.Ready) mediaUiState).isPlaying()) {
            mediaControllerModel.pause();
        } else {
            mediaControllerModel.play();
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit MediaControllerView$lambda$19(MediaControllerModel mediaControllerModel, Modifier modifier, int i, int i2, Composer composer, int i3) {
        MediaControllerView(mediaControllerModel, modifier, composer, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean MediaControllerView$lambda$2(MutableState mutableState) {
        return ((Boolean) mutableState.getValue()).booleanValue();
    }

    private static final void MediaControllerView$lambda$3(MutableState mutableState, boolean z) {
        mutableState.setValue(Boolean.valueOf(z));
    }

    /* JADX WARN: Removed duplicated region for block: B:101:0x0139  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x0150  */
    /* JADX WARN: Removed duplicated region for block: B:111:0x0157  */
    /* JADX WARN: Removed duplicated region for block: B:120:0x016f  */
    /* JADX WARN: Removed duplicated region for block: B:122:0x0174  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x0192  */
    /* JADX WARN: Removed duplicated region for block: B:137:0x01aa  */
    /* JADX WARN: Removed duplicated region for block: B:138:0x01ac  */
    /* JADX WARN: Removed duplicated region for block: B:139:0x01af  */
    /* JADX WARN: Removed duplicated region for block: B:142:0x01b7  */
    /* JADX WARN: Removed duplicated region for block: B:145:0x0240  */
    /* JADX WARN: Removed duplicated region for block: B:148:0x024c  */
    /* JADX WARN: Removed duplicated region for block: B:149:0x0250  */
    /* JADX WARN: Removed duplicated region for block: B:154:0x027d  */
    /* JADX WARN: Removed duplicated region for block: B:157:0x03c9  */
    /* JADX WARN: Removed duplicated region for block: B:160:0x03d5  */
    /* JADX WARN: Removed duplicated region for block: B:161:0x03d9  */
    /* JADX WARN: Removed duplicated region for block: B:166:0x0406  */
    /* JADX WARN: Removed duplicated region for block: B:169:0x0465  */
    /* JADX WARN: Removed duplicated region for block: B:172:0x0471  */
    /* JADX WARN: Removed duplicated region for block: B:173:0x0475  */
    /* JADX WARN: Removed duplicated region for block: B:178:0x04a2  */
    /* JADX WARN: Removed duplicated region for block: B:181:0x0573  */
    /* JADX WARN: Removed duplicated region for block: B:184:0x057f  */
    /* JADX WARN: Removed duplicated region for block: B:185:0x0583  */
    /* JADX WARN: Removed duplicated region for block: B:190:0x05b0  */
    /* JADX WARN: Removed duplicated region for block: B:193:0x06b1  */
    /* JADX WARN: Removed duplicated region for block: B:197:0x06bc  */
    /* JADX WARN: Removed duplicated region for block: B:199:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:57:0x00c1  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x00c8  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x00e1  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x00e6  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x00fc  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0101  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0117  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x011c  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x0133  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static final void MediaControls(final java.lang.String r86, boolean r87, final long r88, float r90, final boolean r91, final boolean r92, final kotlin.jvm.functions.Function0 r93, final kotlin.jvm.functions.Function0 r94, final kotlin.jvm.functions.Function0 r95, final kotlin.jvm.functions.Function1 r96, final kotlin.jvm.functions.Function0 r97, androidx.compose.ui.Modifier r98, androidx.compose.runtime.Composer r99, final int r100, final int r101, final int r102) {
        /*
            Method dump skipped, instruction units count: 1763
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.motorola.laptoppanel.ui.mediacontroller.MediaControllerViewKt.MediaControls(java.lang.String, boolean, long, float, boolean, boolean, kotlin.jvm.functions.Function0, kotlin.jvm.functions.Function0, kotlin.jvm.functions.Function0, kotlin.jvm.functions.Function1, kotlin.jvm.functions.Function0, androidx.compose.ui.Modifier, androidx.compose.runtime.Composer, int, int, int):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit MediaControls$lambda$26(String str, boolean z, long j, float f, boolean z2, boolean z3, Function0 function0, Function0 function02, Function0 function03, Function1 function1, Function0 function04, Modifier modifier, int i, int i2, int i3, Composer composer, int i4) {
        MediaControls(str, z, j, f, z2, z3, function0, function02, function03, function1, function04, modifier, composer, RecomposeScopeImplKt.updateChangedFlags(i | 1), RecomposeScopeImplKt.updateChangedFlags(i2), i3);
        return Unit.INSTANCE;
    }

    private static final SliderColors colors(Composer composer, int i) {
        composer.startReplaceGroup(531258960);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart(531258960, i, -1, "com.motorola.laptoppanel.ui.mediacontroller.colors (MediaControllerView.kt:243)");
        }
        SliderColors sliderColorsColors = SliderDefaults.INSTANCE.colors(composer, 6);
        MaterialTheme materialTheme = MaterialTheme.INSTANCE;
        int i2 = MaterialTheme.$stable;
        SliderColors sliderColorsM311copyK518z4$default = SliderColors.m311copyK518z4$default(sliderColorsColors, 0L, materialTheme.getColorScheme(composer, i2).m263getPrimary0d7_KjU(), 0L, materialTheme.getColorScheme(composer, i2).m277getSurfaceVariant0d7_KjU(), 0L, 0L, 0L, 0L, 0L, 0L, 1013, null);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composer.endReplaceGroup();
        return sliderColorsM311copyK518z4$default;
    }
}
