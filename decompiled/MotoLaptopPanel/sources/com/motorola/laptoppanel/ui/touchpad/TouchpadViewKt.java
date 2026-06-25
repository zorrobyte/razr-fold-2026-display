package com.motorola.laptoppanel.ui.touchpad;

import android.util.SparseIntArray;
import androidx.compose.foundation.ClickableKt;
import androidx.compose.foundation.interaction.InteractionSourceKt;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.material3.IconKt;
import androidx.compose.material3.MaterialTheme;
import androidx.compose.material3.RippleKt;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalMap;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.ScopeUpdateScope;
import androidx.compose.runtime.Updater;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.res.PainterResources_androidKt;
import androidx.compose.ui.res.StringResources_androidKt;
import androidx.compose.ui.semantics.Role;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.IntSize;
import com.motorola.laptoppanel.R;
import kotlin.Unit;
import kotlin.collections.ArrayDeque;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import motorola.core_services.virtualdevices.MotoVirtualDevicesManager;

/* JADX INFO: compiled from: TouchpadView.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class TouchpadViewKt {
    public static final void InfoButton(Function0 function0, final Modifier modifier, Composer composer, final int i) {
        int i2;
        final Function0 function02;
        Composer composer2;
        function0.getClass();
        modifier.getClass();
        Composer composerStartRestartGroup = composer.startRestartGroup(-1898948036);
        if ((i & 6) == 0) {
            i2 = (composerStartRestartGroup.changedInstance(function0) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerStartRestartGroup.changed(modifier) ? 32 : 16;
        }
        if ((i2 & 19) == 18 && composerStartRestartGroup.getSkipping()) {
            composerStartRestartGroup.skipToGroupEnd();
            function02 = function0;
            composer2 = composerStartRestartGroup;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(-1898948036, i2, -1, "com.motorola.laptoppanel.ui.touchpad.InfoButton (TouchpadView.kt:211)");
            }
            Modifier modifierM176size3ABfNKs = SizeKt.m176size3ABfNKs(modifier, Dp.m1877constructorimpl(40));
            int iM1489getButtono7Vup1c = Role.Companion.m1489getButtono7Vup1c();
            composerStartRestartGroup.startReplaceGroup(-484944387);
            Object objRememberedValue = composerStartRestartGroup.rememberedValue();
            if (objRememberedValue == Composer.Companion.getEmpty()) {
                objRememberedValue = InteractionSourceKt.MutableInteractionSource();
                composerStartRestartGroup.updateRememberedValue(objRememberedValue);
            }
            composerStartRestartGroup.endReplaceGroup();
            float f = 20;
            Modifier modifierM98clickableO2vRcR0$default = ClickableKt.m98clickableO2vRcR0$default(modifierM176size3ABfNKs, (MutableInteractionSource) objRememberedValue, RippleKt.m310rippleH2RKhps$default(false, Dp.m1877constructorimpl(f), 0L, 4, null), false, null, Role.m1482boximpl(iM1489getButtono7Vup1c), function0, 12, null);
            function02 = function0;
            MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.getCenter(), false);
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerStartRestartGroup, 0);
            CompositionLocalMap currentCompositionLocalMap = composerStartRestartGroup.getCurrentCompositionLocalMap();
            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerStartRestartGroup, modifierM98clickableO2vRcR0$default);
            ComposeUiNode.Companion companion = ComposeUiNode.Companion;
            Function0 constructor = companion.getConstructor();
            if (composerStartRestartGroup.getApplier() == null) {
                ComposablesKt.invalidApplier();
            }
            composerStartRestartGroup.startReusableNode();
            if (composerStartRestartGroup.getInserting()) {
                composerStartRestartGroup.createNode(constructor);
            } else {
                composerStartRestartGroup.useNode();
            }
            Composer composerM616constructorimpl = Updater.m616constructorimpl(composerStartRestartGroup);
            Updater.m617setimpl(composerM616constructorimpl, measurePolicyMaybeCachedBoxMeasurePolicy, companion.getSetMeasurePolicy());
            Updater.m617setimpl(composerM616constructorimpl, currentCompositionLocalMap, companion.getSetResolvedCompositionLocals());
            Function2 setCompositeKeyHash = companion.getSetCompositeKeyHash();
            if (composerM616constructorimpl.getInserting() || !Intrinsics.areEqual(composerM616constructorimpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                composerM616constructorimpl.updateRememberedValue(Integer.valueOf(currentCompositeKeyHash));
                composerM616constructorimpl.apply(Integer.valueOf(currentCompositeKeyHash), setCompositeKeyHash);
            }
            Updater.m617setimpl(composerM616constructorimpl, modifierMaterializeModifier, companion.getSetModifier());
            BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
            composer2 = composerStartRestartGroup;
            IconKt.m305Iconww6aTOc(PainterResources_androidKt.painterResource(R.drawable.zz_moto_ic_info, composerStartRestartGroup, 6), StringResources_androidKt.stringResource(R.string.info_description, composerStartRestartGroup, 6), SizeKt.m176size3ABfNKs(Modifier.Companion, Dp.m1877constructorimpl(f)), MaterialTheme.INSTANCE.getColorScheme(composerStartRestartGroup, MaterialTheme.$stable).m261getOutline0d7_KjU(), composer2, 384, 0);
            composer2.endNode();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        ScopeUpdateScope scopeUpdateScopeEndRestartGroup = composer2.endRestartGroup();
        if (scopeUpdateScopeEndRestartGroup != null) {
            scopeUpdateScopeEndRestartGroup.updateScope(new Function2() { // from class: com.motorola.laptoppanel.ui.touchpad.TouchpadViewKt$$ExternalSyntheticLambda5
                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return TouchpadViewKt.InfoButton$lambda$24(function02, modifier, i, (Composer) obj, ((Integer) obj2).intValue());
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit InfoButton$lambda$24(Function0 function0, Modifier modifier, int i, Composer composer, int i2) {
        InfoButton(function0, modifier, composer, RecomposeScopeImplKt.updateChangedFlags(i | 1));
        return Unit.INSTANCE;
    }

    /* JADX WARN: Removed duplicated region for block: B:100:0x0366  */
    /* JADX WARN: Removed duplicated region for block: B:103:0x0372  */
    /* JADX WARN: Removed duplicated region for block: B:104:0x0376  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x03a3  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x03e7  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x03fd  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x0408  */
    /* JADX WARN: Removed duplicated region for block: B:120:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0049  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x004e  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0066  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0076  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0078  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x007b  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x009d  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00c3  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00e6  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x010c  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x0128  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x0145  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x0165  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x019f  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x01bf  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x021a  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x0292  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x029e  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x02a2  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x02d1  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x0332  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final void TouchpadView(boolean r28, kotlin.jvm.functions.Function0 r29, kotlin.jvm.functions.Function0 r30, androidx.compose.runtime.Composer r31, final int r32, final int r33) {
        /*
            Method dump skipped, instruction units count: 1047
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.motorola.laptoppanel.ui.touchpad.TouchpadViewKt.TouchpadView(boolean, kotlin.jvm.functions.Function0, kotlin.jvm.functions.Function0, androidx.compose.runtime.Composer, int, int):void");
    }

    private static final void TouchpadView$down(SparseIntArray sparseIntArray, MotoVirtualDevicesManager motoVirtualDevicesManager, int i, int i2, int i3) {
        int i4 = 0;
        if (sparseIntArray.indexOfValue(0) >= 0) {
            i4 = 1;
            if (sparseIntArray.indexOfValue(1) >= 0) {
                return;
            }
        }
        sparseIntArray.put(i, i4);
        motoVirtualDevicesManager.mtDown(i4, i2, i3);
    }

    private static final int TouchpadView$ix(int i, float f) {
        return RangesKt.coerceIn((int) f, 0, i);
    }

    private static final int TouchpadView$iy(int i, float f) {
        return RangesKt.coerceIn((int) f, 0, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean TouchpadView$lambda$10(MutableState mutableState) {
        return ((Boolean) mutableState.getValue()).booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void TouchpadView$lambda$11(MutableState mutableState, boolean z) {
        mutableState.setValue(Boolean.valueOf(z));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:24:0x007a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final boolean TouchpadView$lambda$16$lambda$15(android.view.GestureDetector r2, com.motorola.laptoppanel.util.LaptopPrefs r3, motorola.core_services.virtualdevices.MotoVirtualDevicesManager r4, int r5, int r6, android.util.SparseIntArray r7, kotlin.collections.ArrayDeque r8, androidx.compose.runtime.MutableState r9, android.view.MotionEvent r10) {
        /*
            r10.getClass()
            r2.onTouchEvent(r10)
            int r2 = r10.getActionMasked()
            r0 = 1
            if (r2 == 0) goto L91
            r3 = 0
            if (r2 == r0) goto L7a
            r1 = 2
            if (r2 == r1) goto L50
            r1 = 3
            if (r2 == r1) goto L7a
            r1 = 5
            if (r2 == r1) goto L31
            r5 = 6
            if (r2 == r5) goto L1e
            goto Lb7
        L1e:
            int r2 = r10.getActionIndex()
            int r2 = r10.getPointerId(r2)
            TouchpadView$up(r7, r4, r8, r2)
            TouchpadView$lambda$11(r9, r3)
            r4.mtSendReport()
            goto Lb7
        L31:
            int r2 = r10.getActionIndex()
            int r3 = r10.getPointerId(r2)
            float r8 = r10.getX(r2)
            int r5 = TouchpadView$ix(r5, r8)
            float r2 = r10.getY(r2)
            int r2 = TouchpadView$iy(r6, r2)
            TouchpadView$down(r7, r4, r3, r5, r2)
            r4.mtSendReport()
            goto Lb7
        L50:
            int r2 = r10.getPointerCount()
        L54:
            if (r3 >= r2) goto L76
            int r8 = r10.getPointerId(r3)
            int r9 = r10.findPointerIndex(r8)
            if (r9 < 0) goto L73
            float r1 = r10.getX(r9)
            int r1 = TouchpadView$ix(r5, r1)
            float r9 = r10.getY(r9)
            int r9 = TouchpadView$iy(r6, r9)
            TouchpadView$move(r7, r4, r8, r1, r9)
        L73:
            int r3 = r3 + 1
            goto L54
        L76:
            r4.mtSendReport()
            goto Lb7
        L7a:
            TouchpadView$lambda$11(r9, r3)
            int r2 = r10.getPointerCount()
        L81:
            if (r3 >= r2) goto L8d
            int r5 = r10.getPointerId(r3)
            TouchpadView$up(r7, r4, r8, r5)
            int r3 = r3 + 1
            goto L81
        L8d:
            r4.mtSendReport()
            goto Lb7
        L91:
            int r2 = r3.getTapCount()
            int r2 = r2 + r0
            r3.setTapCount(r2)
            int r2 = r10.getActionIndex()
            int r3 = r10.getPointerId(r2)
            float r8 = r10.getX(r2)
            int r5 = TouchpadView$ix(r5, r8)
            float r2 = r10.getY(r2)
            int r2 = TouchpadView$iy(r6, r2)
            TouchpadView$down(r7, r4, r3, r5, r2)
            r4.mtSendReport()
        Lb7:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.motorola.laptoppanel.ui.touchpad.TouchpadViewKt.TouchpadView$lambda$16$lambda$15(android.view.GestureDetector, com.motorola.laptoppanel.util.LaptopPrefs, motorola.core_services.virtualdevices.MotoVirtualDevicesManager, int, int, android.util.SparseIntArray, kotlin.collections.ArrayDeque, androidx.compose.runtime.MutableState, android.view.MotionEvent):boolean");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit TouchpadView$lambda$20$lambda$18$lambda$17(MutableState mutableState, IntSize intSize) {
        TouchpadView$lambda$6(mutableState, intSize.m1926unboximpl());
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit TouchpadView$lambda$21(boolean z, Function0 function0, Function0 function02, int i, int i2, Composer composer, int i3) {
        TouchpadView(z, function0, function02, composer, RecomposeScopeImplKt.updateChangedFlags(i | 1), i2);
        return Unit.INSTANCE;
    }

    private static final void TouchpadView$lambda$6(MutableState mutableState, long j) {
        mutableState.setValue(IntSize.m1918boximpl(j));
    }

    private static final void TouchpadView$move(SparseIntArray sparseIntArray, MotoVirtualDevicesManager motoVirtualDevicesManager, int i, int i2, int i3) {
        int i4 = sparseIntArray.get(i, -1);
        if (i4 >= 0) {
            motoVirtualDevicesManager.mtMove(i4, i2, i3);
        }
    }

    private static final void TouchpadView$releaseSlot(ArrayDeque arrayDeque, int i) {
        arrayDeque.addLast(Integer.valueOf(i));
    }

    private static final void TouchpadView$up(SparseIntArray sparseIntArray, MotoVirtualDevicesManager motoVirtualDevicesManager, ArrayDeque arrayDeque, int i) {
        int i2 = sparseIntArray.get(i, -1);
        if (i2 >= 0) {
            motoVirtualDevicesManager.mtUp(i2);
            sparseIntArray.delete(i);
            TouchpadView$releaseSlot(arrayDeque, i2);
        }
    }
}
