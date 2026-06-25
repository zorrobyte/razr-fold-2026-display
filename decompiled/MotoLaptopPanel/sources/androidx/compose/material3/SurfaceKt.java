package androidx.compose.material3;

import androidx.compose.foundation.BackgroundKt;
import androidx.compose.foundation.BorderStroke;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.CompositionLocalMap;
import androidx.compose.runtime.ProvidableCompositionLocal;
import androidx.compose.runtime.ProvidedValue;
import androidx.compose.runtime.Updater;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.draw.ClipKt;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.CompositingStrategy;
import androidx.compose.ui.graphics.GraphicsLayerModifierKt;
import androidx.compose.ui.graphics.GraphicsLayerScopeKt;
import androidx.compose.ui.graphics.RectangleShapeKt;
import androidx.compose.ui.graphics.Shape;
import androidx.compose.ui.graphics.TransformOrigin;
import androidx.compose.ui.input.pointer.PointerInputScope;
import androidx.compose.ui.input.pointer.SuspendPointerInputElement;
import androidx.compose.ui.input.pointer.SuspendingPointerInputFilterKt$sam$androidx_compose_ui_input_pointer_PointerInputEventHandler$0;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.semantics.SemanticsModifierKt;
import androidx.compose.ui.semantics.SemanticsPropertiesKt;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.Dp;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Surface.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class SurfaceKt {
    private static final ProvidableCompositionLocal LocalAbsoluteTonalElevation = CompositionLocalKt.compositionLocalOf$default(null, new Function0() { // from class: androidx.compose.material3.SurfaceKt$LocalAbsoluteTonalElevation$1
        @Override // kotlin.jvm.functions.Function0
        public /* bridge */ /* synthetic */ Object invoke() {
            return Dp.m1875boximpl(m346invokeD9Ej5fM());
        }

        /* JADX INFO: renamed from: invoke-D9Ej5fM, reason: not valid java name */
        public final float m346invokeD9Ej5fM() {
            return Dp.m1877constructorimpl(0);
        }
    }, 1, null);

    /* JADX INFO: renamed from: Surface-T9BRK9s, reason: not valid java name */
    public static final void m341SurfaceT9BRK9s(Modifier modifier, Shape shape, long j, long j2, float f, float f2, BorderStroke borderStroke, final Function2 function2, Composer composer, int i, int i2) {
        if ((i2 & 1) != 0) {
            modifier = Modifier.Companion;
        }
        if ((i2 & 2) != 0) {
            shape = RectangleShapeKt.getRectangleShape();
        }
        if ((i2 & 4) != 0) {
            j = MaterialTheme.INSTANCE.getColorScheme(composer, 6).m268getSurface0d7_KjU();
        }
        if ((i2 & 8) != 0) {
            j2 = ColorSchemeKt.m282contentColorForek8zF_U(j, composer, (i >> 6) & 14);
        }
        if ((i2 & 16) != 0) {
            f = Dp.m1877constructorimpl(0);
        }
        if ((i2 & 32) != 0) {
            f2 = Dp.m1877constructorimpl(0);
        }
        if ((i2 & 64) != 0) {
            borderStroke = null;
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart(-513881741, i, -1, "androidx.compose.material3.Surface (Surface.kt:104)");
        }
        ProvidableCompositionLocal providableCompositionLocal = LocalAbsoluteTonalElevation;
        final float fM1877constructorimpl = Dp.m1877constructorimpl(((Dp) composer.consume(providableCompositionLocal)).m1883unboximpl() + f);
        ProvidedValue[] providedValueArr = {ContentColorKt.getLocalContentColor().provides(Color.m876boximpl(j2)), providableCompositionLocal.provides(Dp.m1875boximpl(fM1877constructorimpl))};
        final long j3 = j;
        final Shape shape2 = shape;
        final BorderStroke borderStroke2 = borderStroke;
        final float f3 = f2;
        final Modifier modifier2 = modifier;
        CompositionLocalKt.CompositionLocalProvider(providedValueArr, ComposableLambdaKt.rememberComposableLambda(-70914509, true, new Function2(shape2, j3, fM1877constructorimpl, borderStroke2, f3, function2) { // from class: androidx.compose.material3.SurfaceKt$Surface$1
            final /* synthetic */ float $absoluteElevation;
            final /* synthetic */ BorderStroke $border;
            final /* synthetic */ long $color;
            final /* synthetic */ Function2 $content;
            final /* synthetic */ float $shadowElevation;
            final /* synthetic */ Shape $shape;

            /* JADX INFO: renamed from: androidx.compose.material3.SurfaceKt$Surface$1$3, reason: invalid class name */
            /* JADX INFO: compiled from: Surface.kt */
            final class AnonymousClass3 extends SuspendLambda implements Function2 {
                int label;

                AnonymousClass3(Continuation continuation) {
                    super(2, continuation);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new AnonymousClass3(continuation);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(PointerInputScope pointerInputScope, Continuation continuation) {
                    return ((AnonymousClass3) create(pointerInputScope, continuation)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    IntrinsicsKt.getCOROUTINE_SUSPENDED();
                    if (this.label != 0) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    return Unit.INSTANCE;
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
                this.$shadowElevation = f3;
                this.$content = function2;
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
                invoke((Composer) obj, ((Number) obj2).intValue());
                return Unit.INSTANCE;
            }

            public final void invoke(Composer composer2, int i3) {
                if ((i3 & 3) == 2 && composer2.getSkipping()) {
                    composer2.skipToGroupEnd();
                    return;
                }
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart(-70914509, i3, -1, "androidx.compose.material3.Surface.<anonymous> (Surface.kt:110)");
                }
                Modifier modifierThen = SemanticsModifierKt.semantics(SurfaceKt.m344surfaceXOJAsU(this.$modifier, this.$shape, SurfaceKt.m345surfaceColorAtElevationCLU3JFs(this.$color, this.$absoluteElevation, composer2, 0), null, ((Density) composer2.consume(CompositionLocalsKt.getLocalDensity())).mo146toPx0680j_4(this.$shadowElevation)), false, new Function1() { // from class: androidx.compose.material3.SurfaceKt$Surface$1.2
                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                        invoke((SemanticsPropertyReceiver) obj);
                        return Unit.INSTANCE;
                    }

                    public final void invoke(SemanticsPropertyReceiver semanticsPropertyReceiver) {
                        SemanticsPropertiesKt.setContainer(semanticsPropertyReceiver, true);
                    }
                }).then(new SuspendPointerInputElement(Unit.INSTANCE, null, null, new SuspendingPointerInputFilterKt$sam$androidx_compose_ui_input_pointer_PointerInputEventHandler$0(new AnonymousClass3(null)), 6, null));
                Function2 function22 = this.$content;
                MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(Alignment.Companion.getTopStart(), true);
                int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composer2, 0);
                CompositionLocalMap currentCompositionLocalMap = composer2.getCurrentCompositionLocalMap();
                Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composer2, modifierThen);
                ComposeUiNode.Companion companion = ComposeUiNode.Companion;
                Function0 constructor = companion.getConstructor();
                if (composer2.getApplier() == null) {
                    ComposablesKt.invalidApplier();
                }
                composer2.startReusableNode();
                if (composer2.getInserting()) {
                    composer2.createNode(constructor);
                } else {
                    composer2.useNode();
                }
                Composer composerM616constructorimpl = Updater.m616constructorimpl(composer2);
                Updater.m617setimpl(composerM616constructorimpl, measurePolicyMaybeCachedBoxMeasurePolicy, companion.getSetMeasurePolicy());
                Updater.m617setimpl(composerM616constructorimpl, currentCompositionLocalMap, companion.getSetResolvedCompositionLocals());
                Function2 setCompositeKeyHash = companion.getSetCompositeKeyHash();
                if (composerM616constructorimpl.getInserting() || !Intrinsics.areEqual(composerM616constructorimpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                    composerM616constructorimpl.updateRememberedValue(Integer.valueOf(currentCompositeKeyHash));
                    composerM616constructorimpl.apply(Integer.valueOf(currentCompositeKeyHash), setCompositeKeyHash);
                }
                Updater.m617setimpl(composerM616constructorimpl, modifierMaterializeModifier, companion.getSetModifier());
                BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
                function22.invoke(composer2, 0);
                composer2.endNode();
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
            }
        }, composer, 54), composer, ProvidedValue.$stable | 48);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: surface-XO-JAsU, reason: not valid java name */
    public static final Modifier m344surfaceXOJAsU(Modifier modifier, Shape shape, long j, BorderStroke borderStroke, float f) {
        Shape shape2;
        Modifier modifierM915graphicsLayerAp8cVGQ;
        if (f > 0.0f) {
            shape2 = shape;
            modifierM915graphicsLayerAp8cVGQ = GraphicsLayerModifierKt.m915graphicsLayerAp8cVGQ(Modifier.Companion, (124895 & 1) != 0 ? 1.0f : 0.0f, (124895 & 2) != 0 ? 1.0f : 0.0f, (124895 & 4) == 0 ? 0.0f : 1.0f, (124895 & 8) != 0 ? 0.0f : 0.0f, (124895 & 16) != 0 ? 0.0f : 0.0f, (124895 & 32) != 0 ? 0.0f : f, (124895 & 64) != 0 ? 0.0f : 0.0f, (124895 & 128) != 0 ? 0.0f : 0.0f, (124895 & 256) == 0 ? 0.0f : 0.0f, (124895 & 512) != 0 ? 8.0f : 0.0f, (124895 & 1024) != 0 ? TransformOrigin.Companion.m1017getCenterSzJe1aQ() : 0L, (124895 & 2048) != 0 ? RectangleShapeKt.getRectangleShape() : shape2, (124895 & 4096) != 0 ? false : false, (124895 & 8192) != 0 ? null : null, (124895 & 16384) != 0 ? GraphicsLayerScopeKt.getDefaultShadowColor() : 0L, (32768 & 124895) != 0 ? GraphicsLayerScopeKt.getDefaultShadowColor() : 0L, (124895 & 65536) != 0 ? CompositingStrategy.Companion.m906getAutoNrFUSI() : 0);
        } else {
            shape2 = shape;
            modifierM915graphicsLayerAp8cVGQ = Modifier.Companion;
        }
        return ClipKt.clip(BackgroundKt.m77backgroundbw27NRU(modifier.then(modifierM915graphicsLayerAp8cVGQ).then(Modifier.Companion), j, shape2), shape2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: surfaceColorAtElevation-CLU3JFs, reason: not valid java name */
    public static final long m345surfaceColorAtElevationCLU3JFs(long j, float f, Composer composer, int i) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart(-2079918090, i, -1, "androidx.compose.material3.surfaceColorAtElevation (Surface.kt:481)");
        }
        long jM280applyTonalElevationRFCenO8 = ColorSchemeKt.m280applyTonalElevationRFCenO8(MaterialTheme.INSTANCE.getColorScheme(composer, 6), j, f, composer, (i << 3) & 1008);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return jM280applyTonalElevationRFCenO8;
    }
}
