package com.motorola.laptoppanel.ui.mediacontroller;

import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.foundation.layout.SpacerKt;
import androidx.compose.material3.IconKt;
import androidx.compose.material3.SliderState;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.res.PainterResources_androidKt;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.compose.ui.unit.Dp;
import com.motorola.laptoppanel.R;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;

/* JADX INFO: compiled from: MediaControllerView.kt */
/* JADX INFO: loaded from: classes.dex */
public final class ComposableSingletons$MediaControllerViewKt {
    public static final ComposableSingletons$MediaControllerViewKt INSTANCE = new ComposableSingletons$MediaControllerViewKt();

    /* JADX INFO: renamed from: lambda-1, reason: not valid java name */
    private static Function3 f3lambda1 = ComposableLambdaKt.composableLambdaInstance(1684424448, false, new Function3() { // from class: com.motorola.laptoppanel.ui.mediacontroller.ComposableSingletons$MediaControllerViewKt$lambda-1$1
        @Override // kotlin.jvm.functions.Function3
        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3) {
            invoke((SliderState) obj, (Composer) obj2, ((Number) obj3).intValue());
            return Unit.INSTANCE;
        }

        public final void invoke(SliderState sliderState, Composer composer, int i) {
            sliderState.getClass();
            if ((i & 17) == 16 && composer.getSkipping()) {
                composer.skipToGroupEnd();
                return;
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(1684424448, i, -1, "com.motorola.laptoppanel.ui.mediacontroller.ComposableSingletons$MediaControllerViewKt.lambda-1.<anonymous> (MediaControllerView.kt:125)");
            }
            SpacerKt.Spacer(SizeKt.m176size3ABfNKs(Modifier.Companion, Dp.m1877constructorimpl(0)), composer, 6);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
    });

    /* JADX INFO: renamed from: lambda-2, reason: not valid java name */
    private static Function2 f4lambda2 = ComposableLambdaKt.composableLambdaInstance(-1433952124, false, new Function2() { // from class: com.motorola.laptoppanel.ui.mediacontroller.ComposableSingletons$MediaControllerViewKt$lambda-2$1
        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            invoke((Composer) obj, ((Number) obj2).intValue());
            return Unit.INSTANCE;
        }

        public final void invoke(Composer composer, int i) {
            if ((i & 3) == 2 && composer.getSkipping()) {
                composer.skipToGroupEnd();
                return;
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(-1433952124, i, -1, "com.motorola.laptoppanel.ui.mediacontroller.ComposableSingletons$MediaControllerViewKt.lambda-2.<anonymous> (MediaControllerView.kt:203)");
            }
            IconKt.m305Iconww6aTOc(PainterResources_androidKt.painterResource(R.drawable.zz_moto_ic_media_previous, composer, 6), StringResources_androidKt.stringResource(R.string.media_controls_previous_description, composer, 6), SizeKt.m176size3ABfNKs(Modifier.Companion, Dp.m1877constructorimpl(40)), 0L, composer, 384, 8);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
    });

    /* JADX INFO: renamed from: lambda-3, reason: not valid java name */
    private static Function2 f5lambda3 = ComposableLambdaKt.composableLambdaInstance(1559463612, false, new Function2() { // from class: com.motorola.laptoppanel.ui.mediacontroller.ComposableSingletons$MediaControllerViewKt$lambda-3$1
        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            invoke((Composer) obj, ((Number) obj2).intValue());
            return Unit.INSTANCE;
        }

        public final void invoke(Composer composer, int i) {
            if ((i & 3) == 2 && composer.getSkipping()) {
                composer.skipToGroupEnd();
                return;
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(1559463612, i, -1, "com.motorola.laptoppanel.ui.mediacontroller.ComposableSingletons$MediaControllerViewKt.lambda-3.<anonymous> (MediaControllerView.kt:233)");
            }
            IconKt.m305Iconww6aTOc(PainterResources_androidKt.painterResource(R.drawable.zz_moto_ic_media_next, composer, 6), StringResources_androidKt.stringResource(R.string.media_controls_next_description, composer, 6), SizeKt.m176size3ABfNKs(Modifier.Companion, Dp.m1877constructorimpl(40)), 0L, composer, 384, 8);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
    });

    /* JADX INFO: renamed from: getLambda-1$motorola__packages__apps__MotoLaptopPanel__android_common__MotoLaptopPanel, reason: not valid java name */
    public final Function3 m2171x68564636() {
        return f3lambda1;
    }

    /* JADX INFO: renamed from: getLambda-2$motorola__packages__apps__MotoLaptopPanel__android_common__MotoLaptopPanel, reason: not valid java name */
    public final Function2 m2172xaa6d7395() {
        return f4lambda2;
    }

    /* JADX INFO: renamed from: getLambda-3$motorola__packages__apps__MotoLaptopPanel__android_common__MotoLaptopPanel, reason: not valid java name */
    public final Function2 m2173xec84a0f4() {
        return f5lambda3;
    }
}
