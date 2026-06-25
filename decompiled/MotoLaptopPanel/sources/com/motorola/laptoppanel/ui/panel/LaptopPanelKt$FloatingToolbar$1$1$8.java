package com.motorola.laptoppanel.ui.panel;

import androidx.compose.animation.AnimatedVisibilityScope;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.ui.res.StringResources_androidKt;
import com.motorola.laptoppanel.R;
import com.motorola.laptoppanel.ui.panel.LaptopPanelModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;

/* JADX INFO: compiled from: LaptopPanel.kt */
/* JADX INFO: loaded from: classes.dex */
final class LaptopPanelKt$FloatingToolbar$1$1$8 implements Function3 {
    final /* synthetic */ Function1 $onAction;
    final /* synthetic */ LaptopPanelUiState $uiState;

    LaptopPanelKt$FloatingToolbar$1$1$8(Function1 function1, LaptopPanelUiState laptopPanelUiState) {
        this.$onAction = function1;
        this.$uiState = laptopPanelUiState;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit invoke$lambda$1$lambda$0(Function1 function1) {
        function1.invoke(ToolbarAction.ToggleMedia);
        return Unit.INSTANCE;
    }

    @Override // kotlin.jvm.functions.Function3
    public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3) {
        invoke((AnimatedVisibilityScope) obj, (Composer) obj2, ((Number) obj3).intValue());
        return Unit.INSTANCE;
    }

    public final void invoke(AnimatedVisibilityScope animatedVisibilityScope, Composer composer, int i) {
        animatedVisibilityScope.getClass();
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart(-1642832949, i, -1, "com.motorola.laptoppanel.ui.panel.FloatingToolbar.<anonymous>.<anonymous>.<anonymous> (LaptopPanel.kt:276)");
        }
        String strStringResource = StringResources_androidKt.stringResource(R.string.media_controls_description, composer, 6);
        composer.startReplaceGroup(59730635);
        boolean zChanged = composer.changed(this.$onAction);
        final Function1 function1 = this.$onAction;
        Object objRememberedValue = composer.rememberedValue();
        if (zChanged || objRememberedValue == Composer.Companion.getEmpty()) {
            objRememberedValue = new Function0() { // from class: com.motorola.laptoppanel.ui.panel.LaptopPanelKt$FloatingToolbar$1$1$8$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    return LaptopPanelKt$FloatingToolbar$1$1$8.invoke$lambda$1$lambda$0(function1);
                }
            };
            composer.updateRememberedValue(objRememberedValue);
        }
        Function0 function0 = (Function0) objRememberedValue;
        composer.endReplaceGroup();
        LaptopPanelKt.m2179ActionButtonWHejsw(R.drawable.zz_moto_ic_media_controller, strStringResource, function0, null, 0.0f, this.$uiState.getActivePanel() == LaptopPanelModel.PanelType.MEDIA_CONTROLLER, composer, 6, 24);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
    }
}
