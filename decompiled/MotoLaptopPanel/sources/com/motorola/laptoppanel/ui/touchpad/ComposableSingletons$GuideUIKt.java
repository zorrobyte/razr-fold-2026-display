package com.motorola.laptoppanel.ui.touchpad;

import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.material3.IconKt;
import androidx.compose.material3.MaterialTheme;
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

/* JADX INFO: compiled from: GuideUI.kt */
/* JADX INFO: loaded from: classes.dex */
public final class ComposableSingletons$GuideUIKt {
    public static final ComposableSingletons$GuideUIKt INSTANCE = new ComposableSingletons$GuideUIKt();

    /* JADX INFO: renamed from: lambda-1, reason: not valid java name */
    private static Function2 f6lambda1 = ComposableLambdaKt.composableLambdaInstance(-2133312405, false, new Function2() { // from class: com.motorola.laptoppanel.ui.touchpad.ComposableSingletons$GuideUIKt$lambda-1$1
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
                ComposerKt.traceEventStart(-2133312405, i, -1, "com.motorola.laptoppanel.ui.touchpad.ComposableSingletons$GuideUIKt.lambda-1.<anonymous> (GuideUI.kt:116)");
            }
            IconKt.m305Iconww6aTOc(PainterResources_androidKt.painterResource(R.drawable.zz_moto_ic_guide_close, composer, 6), StringResources_androidKt.stringResource(R.string.close_guideui_description, composer, 6), SizeKt.m176size3ABfNKs(Modifier.Companion, Dp.m1877constructorimpl(40)), MaterialTheme.INSTANCE.getColorScheme(composer, MaterialTheme.$stable).m261getOutline0d7_KjU(), composer, 384, 0);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
    });

    /* JADX INFO: renamed from: getLambda-1$motorola__packages__apps__MotoLaptopPanel__android_common__MotoLaptopPanel, reason: not valid java name */
    public final Function2 m2185x68564636() {
        return f6lambda1;
    }
}
