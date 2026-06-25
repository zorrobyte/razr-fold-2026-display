package com.motorola.laptoppanel.ui.panel;

import android.app.StatusBarManager;
import android.content.Context;
import androidx.compose.animation.AnimatedVisibilityKt;
import androidx.compose.foundation.BackgroundKt;
import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.RowKt;
import androidx.compose.foundation.layout.RowScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.material3.MaterialTheme;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.ScopeUpdateScope;
import androidx.compose.runtime.State;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.compose.ui.unit.Dp;
import com.motorola.laptoppanel.R;
import com.motorola.laptoppanel.ui.panel.LaptopPanelKt;
import com.motorola.laptoppanel.ui.panel.LaptopPanelModel;
import com.motorola.laptoppanel.util.Logger;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: LaptopPanel.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class LaptopPanelKt {

    /* JADX INFO: renamed from: com.motorola.laptoppanel.ui.panel.LaptopPanelKt$FloatingToolbar$1, reason: invalid class name */
    /* JADX INFO: compiled from: LaptopPanel.kt */
    final class AnonymousClass1 implements Function2 {
        final /* synthetic */ Function1 $onAction;
        final /* synthetic */ LaptopPanelUiState $uiState;

        AnonymousClass1(Function1 function1, LaptopPanelUiState laptopPanelUiState) {
            this.$onAction = function1;
            this.$uiState = laptopPanelUiState;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit invoke$lambda$14$lambda$1$lambda$0(Function1 function1) {
            function1.invoke(ToolbarAction.SplitScreen);
            return Unit.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit invoke$lambda$14$lambda$11$lambda$10(Function1 function1) {
            function1.invoke(ToolbarAction.ToggleBrightness);
            return Unit.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit invoke$lambda$14$lambda$13$lambda$12(Function1 function1) {
            function1.invoke(ToolbarAction.ToggleVolume);
            return Unit.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit invoke$lambda$14$lambda$3$lambda$2(Function1 function1) {
            function1.invoke(ToolbarAction.QuickSettings);
            return Unit.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit invoke$lambda$14$lambda$5$lambda$4(Function1 function1) {
            function1.invoke(ToolbarAction.Notification);
            return Unit.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit invoke$lambda$14$lambda$7$lambda$6(Function1 function1) {
            function1.invoke(ToolbarAction.Notification);
            return Unit.INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit invoke$lambda$14$lambda$9$lambda$8(Function1 function1) {
            function1.invoke(ToolbarAction.Screenshot);
            return Unit.INSTANCE;
        }

        @Override // kotlin.jvm.functions.Function2
        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
            invoke((Composer) obj, ((Number) obj2).intValue());
            return Unit.INSTANCE;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r12v1 */
        /* JADX WARN: Type inference failed for: r12v2, types: [boolean, int] */
        /* JADX WARN: Type inference failed for: r12v3 */
        public final void invoke(Composer composer, int i) {
            ?? r12;
            if ((i & 3) == 2 && composer.getSkipping()) {
                composer.skipToGroupEnd();
                return;
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(-1194390385, i, -1, "com.motorola.laptoppanel.ui.panel.FloatingToolbar.<anonymous> (LaptopPanel.kt:220)");
            }
            Modifier modifierM162paddingVpY3zN4$default = PaddingKt.m162paddingVpY3zN4$default(Modifier.Companion, Dp.m1877constructorimpl(24), 0.0f, 2, null);
            Alignment.Vertical centerVertically = Alignment.Companion.getCenterVertically();
            Arrangement.HorizontalOrVertical horizontalOrVerticalM156spacedBy0680j_4 = Arrangement.INSTANCE.m156spacedBy0680j_4(Dp.m1877constructorimpl(20));
            final Function1 function1 = this.$onAction;
            LaptopPanelUiState laptopPanelUiState = this.$uiState;
            MeasurePolicy measurePolicyRowMeasurePolicy = RowKt.rowMeasurePolicy(horizontalOrVerticalM156spacedBy0680j_4, centerVertically, composer, 54);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer, 0);
            CompositionLocalMap currentCompositionLocalMap = composer.getCurrentCompositionLocalMap();
            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composer, modifierM162paddingVpY3zN4$default);
            ComposeUiNode.Companion companion = ComposeUiNode.Companion;
            Function0 constructor = companion.getConstructor();
            if (composer.getApplier() == null) {
                ComposablesKt.invalidApplier();
            }
            composer.startReusableNode();
            if (composer.getInserting()) {
                composer.createNode(constructor);
            } else {
                composer.useNode();
            }
            Composer composerM616constructorimpl = Updater.m616constructorimpl(composer);
            Updater.m617setimpl(composerM616constructorimpl, measurePolicyRowMeasurePolicy, companion.getSetMeasurePolicy());
            Updater.m617setimpl(composerM616constructorimpl, currentCompositionLocalMap, companion.getSetResolvedCompositionLocals());
            Function2 setCompositeKeyHash = companion.getSetCompositeKeyHash();
            if (composerM616constructorimpl.getInserting() || !Intrinsics.areEqual(composerM616constructorimpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                composerM616constructorimpl.updateRememberedValue(Integer.valueOf(currentCompositeKeyHash));
                composerM616constructorimpl.apply(Integer.valueOf(currentCompositeKeyHash), setCompositeKeyHash);
            }
            Updater.m617setimpl(composerM616constructorimpl, modifierMaterializeModifier, companion.getSetModifier());
            RowScopeInstance rowScopeInstance = RowScopeInstance.INSTANCE;
            String strStringResource = StringResources_androidKt.stringResource(R.string.splitscreen_description, composer, 6);
            composer.startReplaceGroup(-1204186484);
            boolean zChanged = composer.changed(function1);
            Object objRememberedValue = composer.rememberedValue();
            if (zChanged || objRememberedValue == Composer.Companion.getEmpty()) {
                objRememberedValue = new Function0() { // from class: com.motorola.laptoppanel.ui.panel.LaptopPanelKt$FloatingToolbar$1$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return LaptopPanelKt.AnonymousClass1.invoke$lambda$14$lambda$1$lambda$0(function1);
                    }
                };
                composer.updateRememberedValue(objRememberedValue);
            }
            composer.endReplaceGroup();
            LaptopPanelKt.m2179ActionButtonWHejsw(R.drawable.zz_moto_ic_split_screen, strStringResource, (Function0) objRememberedValue, null, 0.0f, false, composer, 6, 56);
            LaptopPanelKt.ToolbarSeparator(null, composer, 0, 1);
            if (laptopPanelUiState.isModernStyle()) {
                composer.startReplaceGroup(1325066177);
                String strStringResource2 = StringResources_androidKt.stringResource(R.string.quicksettings_description, composer, 6);
                composer.startReplaceGroup(-1204175346);
                boolean zChanged2 = composer.changed(function1);
                Object objRememberedValue2 = composer.rememberedValue();
                if (zChanged2 || objRememberedValue2 == Composer.Companion.getEmpty()) {
                    objRememberedValue2 = new Function0() { // from class: com.motorola.laptoppanel.ui.panel.LaptopPanelKt$FloatingToolbar$1$$ExternalSyntheticLambda1
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            return LaptopPanelKt.AnonymousClass1.invoke$lambda$14$lambda$3$lambda$2(function1);
                        }
                    };
                    composer.updateRememberedValue(objRememberedValue2);
                }
                composer.endReplaceGroup();
                r12 = 1;
                LaptopPanelKt.m2179ActionButtonWHejsw(R.drawable.zz_moto_ic_quick_setting, strStringResource2, (Function0) objRememberedValue2, null, 0.0f, false, composer, 6, 56);
                String strStringResource3 = StringResources_androidKt.stringResource(R.string.notification_description, composer, 6);
                composer.startReplaceGroup(-1204166451);
                boolean zChanged3 = composer.changed(function1);
                Object objRememberedValue3 = composer.rememberedValue();
                if (zChanged3 || objRememberedValue3 == Composer.Companion.getEmpty()) {
                    objRememberedValue3 = new Function0() { // from class: com.motorola.laptoppanel.ui.panel.LaptopPanelKt$FloatingToolbar$1$$ExternalSyntheticLambda2
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            return LaptopPanelKt.AnonymousClass1.invoke$lambda$14$lambda$5$lambda$4(function1);
                        }
                    };
                    composer.updateRememberedValue(objRememberedValue3);
                }
                composer.endReplaceGroup();
                LaptopPanelKt.m2179ActionButtonWHejsw(R.drawable.zz_moto_ic_notification, strStringResource3, (Function0) objRememberedValue3, null, 0.0f, false, composer, 6, 56);
                composer.endReplaceGroup();
            } else {
                r12 = 1;
                composer.startReplaceGroup(1325631059);
                String strStringResource4 = StringResources_androidKt.stringResource(R.string.qs_notification_description, composer, 6);
                composer.startReplaceGroup(-1204156723);
                boolean zChanged4 = composer.changed(function1);
                Object objRememberedValue4 = composer.rememberedValue();
                if (zChanged4 || objRememberedValue4 == Composer.Companion.getEmpty()) {
                    objRememberedValue4 = new Function0() { // from class: com.motorola.laptoppanel.ui.panel.LaptopPanelKt$FloatingToolbar$1$$ExternalSyntheticLambda3
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            return LaptopPanelKt.AnonymousClass1.invoke$lambda$14$lambda$7$lambda$6(function1);
                        }
                    };
                    composer.updateRememberedValue(objRememberedValue4);
                }
                composer.endReplaceGroup();
                LaptopPanelKt.m2179ActionButtonWHejsw(R.drawable.zz_moto_ic_qs_notificaiton, strStringResource4, (Function0) objRememberedValue4, null, 0.0f, false, composer, 6, 56);
                composer.endReplaceGroup();
            }
            LaptopPanelKt.ToolbarSeparator(null, composer, 0, r12);
            String strStringResource5 = StringResources_androidKt.stringResource(R.string.screenshot_description, composer, 6);
            composer.startReplaceGroup(-1204146997);
            boolean zChanged5 = composer.changed(function1);
            Object objRememberedValue5 = composer.rememberedValue();
            if (zChanged5 || objRememberedValue5 == Composer.Companion.getEmpty()) {
                objRememberedValue5 = new Function0() { // from class: com.motorola.laptoppanel.ui.panel.LaptopPanelKt$FloatingToolbar$1$$ExternalSyntheticLambda4
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return LaptopPanelKt.AnonymousClass1.invoke$lambda$14$lambda$9$lambda$8(function1);
                    }
                };
                composer.updateRememberedValue(objRememberedValue5);
            }
            composer.endReplaceGroup();
            LaptopPanelKt.m2179ActionButtonWHejsw(R.drawable.zz_moto_ic_screenshot, strStringResource5, (Function0) objRememberedValue5, null, 0.0f, false, composer, 6, 56);
            int brightnessIcon = laptopPanelUiState.getBrightnessIcon();
            String strStringResource6 = StringResources_androidKt.stringResource(R.string.brightness_description, composer, 6);
            composer.startReplaceGroup(-1204139247);
            boolean zChanged6 = composer.changed(function1);
            Object objRememberedValue6 = composer.rememberedValue();
            if (zChanged6 || objRememberedValue6 == Composer.Companion.getEmpty()) {
                objRememberedValue6 = new Function0() { // from class: com.motorola.laptoppanel.ui.panel.LaptopPanelKt$FloatingToolbar$1$$ExternalSyntheticLambda5
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return LaptopPanelKt.AnonymousClass1.invoke$lambda$14$lambda$11$lambda$10(function1);
                    }
                };
                composer.updateRememberedValue(objRememberedValue6);
            }
            Function0 function0 = (Function0) objRememberedValue6;
            composer.endReplaceGroup();
            LaptopPanelKt.m2179ActionButtonWHejsw(brightnessIcon, strStringResource6, function0, null, 0.0f, laptopPanelUiState.getActivePanel() == LaptopPanelModel.PanelType.BRIGHTNESS_SLIDER ? r12 : 0, composer, 0, 24);
            int volumeIcon = laptopPanelUiState.getVolumeIcon();
            String strStringResource7 = StringResources_androidKt.stringResource(R.string.volume_description, composer, 6);
            composer.startReplaceGroup(-1204128979);
            boolean zChanged7 = composer.changed(function1);
            Object objRememberedValue7 = composer.rememberedValue();
            if (zChanged7 || objRememberedValue7 == Composer.Companion.getEmpty()) {
                objRememberedValue7 = new Function0() { // from class: com.motorola.laptoppanel.ui.panel.LaptopPanelKt$FloatingToolbar$1$$ExternalSyntheticLambda6
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return LaptopPanelKt.AnonymousClass1.invoke$lambda$14$lambda$13$lambda$12(function1);
                    }
                };
                composer.updateRememberedValue(objRememberedValue7);
            }
            Function0 function02 = (Function0) objRememberedValue7;
            composer.endReplaceGroup();
            LaptopPanelKt.m2179ActionButtonWHejsw(volumeIcon, strStringResource7, function02, null, 0.0f, laptopPanelUiState.getActivePanel() == LaptopPanelModel.PanelType.VOLUME_SLIDER ? r12 : 0, composer, 0, 24);
            AnimatedVisibilityKt.AnimatedVisibility(rowScopeInstance, laptopPanelUiState.isMediaPlaying(), null, null, null, null, ComposableLambdaKt.rememberComposableLambda(-1642832949, r12, new LaptopPanelKt$FloatingToolbar$1$1$8(function1, laptopPanelUiState), composer, 54), composer, 1572870, 30);
            composer.endNode();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
    }

    /* JADX INFO: compiled from: LaptopPanel.kt */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[ToolbarAction.values().length];
            try {
                iArr[ToolbarAction.SplitScreen.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ToolbarAction.Screenshot.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ToolbarAction.ToggleBrightness.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[ToolbarAction.ToggleVolume.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[ToolbarAction.ToggleMedia.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[ToolbarAction.QuickSettings.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[ToolbarAction.Notification.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[LaptopPanelModel.PanelType.values().length];
            try {
                iArr2[LaptopPanelModel.PanelType.TOUCHPAD.ordinal()] = 1;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr2[LaptopPanelModel.PanelType.NONE.ordinal()] = 2;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr2[LaptopPanelModel.PanelType.BRIGHTNESS_SLIDER.ordinal()] = 3;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                iArr2[LaptopPanelModel.PanelType.VOLUME_SLIDER.ordinal()] = 4;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                iArr2[LaptopPanelModel.PanelType.MEDIA_CONTROLLER.ordinal()] = 5;
            } catch (NoSuchFieldError unused12) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:46:0x0081  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0086  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x009e  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00a2  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x00bc  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x00ca  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x00cc  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x00cf  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x00d2  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x00da  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x00dd  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x00e5  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x013e  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x014a  */
    /* JADX WARN: Removed duplicated region for block: B:91:? A[RETURN, SYNTHETIC] */
    /* JADX INFO: renamed from: ActionButton-WH-ejsw, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final void m2179ActionButtonWHejsw(final int r18, final java.lang.String r19, final kotlin.jvm.functions.Function0 r20, androidx.compose.ui.Modifier r21, float r22, boolean r23, androidx.compose.runtime.Composer r24, final int r25, final int r26) {
        /*
            Method dump skipped, instruction units count: 347
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.motorola.laptoppanel.ui.panel.LaptopPanelKt.m2179ActionButtonWHejsw(int, java.lang.String, kotlin.jvm.functions.Function0, androidx.compose.ui.Modifier, float, boolean, androidx.compose.runtime.Composer, int, int):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit ActionButton_WH_ejsw$lambda$15(int i, String str, Function0 function0, Modifier modifier, float f, boolean z, int i2, int i3, Composer composer, int i4) {
        m2179ActionButtonWHejsw(i, str, function0, modifier, f, z, composer, RecomposeScopeImplKt.updateChangedFlags(i2 | 1), i3);
        return Unit.INSTANCE;
    }

    /* JADX WARN: Removed duplicated region for block: B:40:0x0070  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0072  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0075  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x007c  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00bf  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00c9  */
    /* JADX WARN: Removed duplicated region for block: B:54:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final void FloatingToolbar(final com.motorola.laptoppanel.ui.panel.LaptopPanelUiState r18, final kotlin.jvm.functions.Function1 r19, androidx.compose.ui.Modifier r20, androidx.compose.runtime.Composer r21, final int r22, final int r23) {
        /*
            Method dump skipped, instruction units count: 212
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.motorola.laptoppanel.ui.panel.LaptopPanelKt.FloatingToolbar(com.motorola.laptoppanel.ui.panel.LaptopPanelUiState, kotlin.jvm.functions.Function1, androidx.compose.ui.Modifier, androidx.compose.runtime.Composer, int, int):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit FloatingToolbar$lambda$14(LaptopPanelUiState laptopPanelUiState, Function1 function1, Modifier modifier, int i, int i2, Composer composer, int i3) {
        FloatingToolbar(laptopPanelUiState, function1, modifier, composer, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
        return Unit.INSTANCE;
    }

    /* JADX WARN: Removed duplicated region for block: B:36:0x0072  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0077  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x008d  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0092  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x00aa  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00ae  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x00c8  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x00d5  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x00d7  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x00da  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x00e1  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x00ff  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x0103  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x014e  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0158  */
    /* JADX WARN: Removed duplicated region for block: B:91:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final void LaptopPanel(final com.motorola.laptoppanel.ui.panel.LaptopPanelModel r20, final kotlin.jvm.functions.Function0 r21, final kotlin.jvm.functions.Function0 r22, final kotlin.jvm.functions.Function0 r23, final kotlin.jvm.functions.Function0 r24, androidx.compose.ui.Modifier r25, androidx.compose.runtime.Composer r26, final int r27, final int r28) {
        /*
            Method dump skipped, instruction units count: 365
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.motorola.laptoppanel.ui.panel.LaptopPanelKt.LaptopPanel(com.motorola.laptoppanel.ui.panel.LaptopPanelModel, kotlin.jvm.functions.Function0, kotlin.jvm.functions.Function0, kotlin.jvm.functions.Function0, kotlin.jvm.functions.Function0, androidx.compose.ui.Modifier, androidx.compose.runtime.Composer, int, int):void");
    }

    private static final int LaptopPanel$lambda$0(State state) {
        return ((Number) state.getValue()).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit LaptopPanel$lambda$1(LaptopPanelModel laptopPanelModel, Function0 function0, Function0 function02, Function0 function03, Function0 function04, Modifier modifier, int i, int i2, Composer composer, int i3) {
        LaptopPanel(laptopPanelModel, function0, function02, function03, function04, modifier, composer, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
        return Unit.INSTANCE;
    }

    /* JADX WARN: Removed duplicated region for block: B:100:0x02ce  */
    /* JADX WARN: Removed duplicated region for block: B:105:0x02fb  */
    /* JADX WARN: Removed duplicated region for block: B:108:0x0320  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x0322  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x0329  */
    /* JADX WARN: Removed duplicated region for block: B:113:0x032b  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x034c  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x03d9  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x03dc  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x0441  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x044d  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x0451  */
    /* JADX WARN: Removed duplicated region for block: B:136:0x047e  */
    /* JADX WARN: Removed duplicated region for block: B:179:0x05a3  */
    /* JADX WARN: Removed duplicated region for block: B:183:0x05c9  */
    /* JADX WARN: Removed duplicated region for block: B:188:0x05ef  */
    /* JADX WARN: Removed duplicated region for block: B:192:0x0615  */
    /* JADX WARN: Removed duplicated region for block: B:196:0x0620  */
    /* JADX WARN: Removed duplicated region for block: B:198:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0058  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x005d  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0075  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x007a  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0091  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0096  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00af  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x00bd  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x00c1  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x00c6  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x00ce  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x017b  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0187  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x018b  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x01b8  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x0210  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x021c  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0220  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x024d  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x02be  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x02ca  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final void LaptopPanelView(com.motorola.laptoppanel.ui.panel.LaptopPanelModel r38, final kotlin.jvm.functions.Function0 r39, final kotlin.jvm.functions.Function0 r40, final kotlin.jvm.functions.Function0 r41, final kotlin.jvm.functions.Function0 r42, androidx.compose.ui.Modifier r43, androidx.compose.runtime.Composer r44, final int r45, final int r46) {
        /*
            Method dump skipped, instruction units count: 1587
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.motorola.laptoppanel.ui.panel.LaptopPanelKt.LaptopPanelView(com.motorola.laptoppanel.ui.panel.LaptopPanelModel, kotlin.jvm.functions.Function0, kotlin.jvm.functions.Function0, kotlin.jvm.functions.Function0, kotlin.jvm.functions.Function0, androidx.compose.ui.Modifier, androidx.compose.runtime.Composer, int, int):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit LaptopPanelView$lambda$12$lambda$11$lambda$10$lambda$9(LaptopPanelModel laptopPanelModel) {
        laptopPanelModel.setShowGuideUi(true, false);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit LaptopPanelView$lambda$12$lambda$11$lambda$8$lambda$7(LaptopPanelModel laptopPanelModel) {
        laptopPanelModel.setShowGuideUi(false, true);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit LaptopPanelView$lambda$12$lambda$6$lambda$5$lambda$4$lambda$3(Function0 function0, Function0 function02, LaptopPanelModel laptopPanelModel, Context context, State state, ToolbarAction toolbarAction) {
        toolbarAction.getClass();
        switch (WhenMappings.$EnumSwitchMapping$0[toolbarAction.ordinal()]) {
            case 1:
                function0.invoke();
                break;
            case 2:
                function02.invoke();
                break;
            case 3:
                togglePanel(laptopPanelModel, LaptopPanelView$lambda$2(state).getActivePanel(), LaptopPanelModel.PanelType.BRIGHTNESS_SLIDER);
                break;
            case 4:
                togglePanel(laptopPanelModel, LaptopPanelView$lambda$2(state).getActivePanel(), LaptopPanelModel.PanelType.VOLUME_SLIDER);
                break;
            case 5:
                togglePanel(laptopPanelModel, LaptopPanelView$lambda$2(state).getActivePanel(), LaptopPanelModel.PanelType.MEDIA_CONTROLLER);
                break;
            case 6:
                expandSettingsPanel(context);
                break;
            case 7:
                expandNotificationPanel(context);
                break;
            default:
                throw new NoWhenBranchMatchedException();
        }
        laptopPanelModel.logToolbarTap();
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit LaptopPanelView$lambda$13(LaptopPanelModel laptopPanelModel, Function0 function0, Function0 function02, Function0 function03, Function0 function04, Modifier modifier, int i, int i2, Composer composer, int i3) {
        LaptopPanelView(laptopPanelModel, function0, function02, function03, function04, modifier, composer, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
        return Unit.INSTANCE;
    }

    private static final LaptopPanelUiState LaptopPanelView$lambda$2(State state) {
        return (LaptopPanelUiState) state.getValue();
    }

    public static final void ToolbarSeparator(final Modifier modifier, Composer composer, final int i, final int i2) {
        int i3;
        Composer composerStartRestartGroup = composer.startRestartGroup(-315090459);
        int i4 = i2 & 1;
        if (i4 != 0) {
            i3 = i | 6;
        } else if ((i & 6) == 0) {
            i3 = (composerStartRestartGroup.changed(modifier) ? 4 : 2) | i;
        } else {
            i3 = i;
        }
        if ((i3 & 3) == 2 && composerStartRestartGroup.getSkipping()) {
            composerStartRestartGroup.skipToGroupEnd();
        } else {
            if (i4 != 0) {
                modifier = Modifier.Companion;
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(-315090459, i3, -1, "com.motorola.laptoppanel.ui.panel.ToolbarSeparator (LaptopPanel.kt:311)");
            }
            BoxKt.Box(BackgroundKt.m78backgroundbw27NRU$default(SizeKt.m172height3ABfNKs(SizeKt.m179width3ABfNKs(modifier, Dp.m1877constructorimpl(1)), Dp.m1877constructorimpl(24)), MaterialTheme.INSTANCE.getColorScheme(composerStartRestartGroup, MaterialTheme.$stable).m277getSurfaceVariant0d7_KjU(), null, 2, null), composerStartRestartGroup, 0);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        ScopeUpdateScope scopeUpdateScopeEndRestartGroup = composerStartRestartGroup.endRestartGroup();
        if (scopeUpdateScopeEndRestartGroup != null) {
            scopeUpdateScopeEndRestartGroup.updateScope(new Function2() { // from class: com.motorola.laptoppanel.ui.panel.LaptopPanelKt$$ExternalSyntheticLambda7
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return LaptopPanelKt.ToolbarSeparator$lambda$16(modifier, i, i2, (Composer) obj, ((Integer) obj2).intValue());
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit ToolbarSeparator$lambda$16(Modifier modifier, int i, int i2, Composer composer, int i3) {
        ToolbarSeparator(modifier, composer, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
        return Unit.INSTANCE;
    }

    private static final void expandNotificationPanel(Context context) {
        try {
            Object systemService = context.getSystemService("statusbar");
            systemService.getClass();
            ((StatusBarManager) systemService).expandNotificationsPanel();
        } catch (Exception e) {
            Logger.INSTANCE.e("LaptopPanel", "Failed to expand notification panel", e);
        }
    }

    private static final void expandSettingsPanel(Context context) {
        try {
            Object systemService = context.getSystemService("statusbar");
            systemService.getClass();
            ((StatusBarManager) systemService).expandSettingsPanel();
        } catch (Exception e) {
            Logger.INSTANCE.e("LaptopPanel", "Failed to expand settings panel", e);
        }
    }

    private static final void togglePanel(LaptopPanelModel laptopPanelModel, LaptopPanelModel.PanelType panelType, LaptopPanelModel.PanelType panelType2) {
        if (panelType == panelType2) {
            laptopPanelModel.showPanel(LaptopPanelModel.PanelType.TOUCHPAD);
        } else {
            laptopPanelModel.showPanel(panelType2);
        }
    }
}
