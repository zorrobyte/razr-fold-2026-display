package androidx.compose.material3;

import androidx.compose.foundation.FocusableKt;
import androidx.compose.foundation.ProgressSemanticsKt;
import androidx.compose.foundation.gestures.DraggableKt;
import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.foundation.gestures.PressGestureScope;
import androidx.compose.foundation.gestures.TapGestureDetectorKt;
import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.foundation.layout.BoxKt;
import androidx.compose.foundation.layout.BoxScopeInstance;
import androidx.compose.foundation.layout.SizeKt;
import androidx.compose.material3.internal.AccessibilityUtilKt;
import androidx.compose.material3.tokens.SliderTokens;
import androidx.compose.runtime.ComposablesKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalMap;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.ScopeUpdateScope;
import androidx.compose.runtime.Updater;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ComposedModifierKt;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.input.key.Key;
import androidx.compose.ui.input.key.KeyEvent;
import androidx.compose.ui.input.key.KeyEventType;
import androidx.compose.ui.input.key.KeyEvent_androidKt;
import androidx.compose.ui.input.key.KeyInputModifierKt;
import androidx.compose.ui.input.pointer.PointerInputScope;
import androidx.compose.ui.input.pointer.SuspendPointerInputElement;
import androidx.compose.ui.input.pointer.SuspendingPointerInputFilterKt$sam$androidx_compose_ui_input_pointer_PointerInputEventHandler$0;
import androidx.compose.ui.layout.LayoutIdKt;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasurePolicy;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.OnRemeasuredModifierKt;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.node.ComposeUiNode;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.semantics.SemanticsModifierKt;
import androidx.compose.ui.semantics.SemanticsPropertiesKt;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import androidx.compose.ui.unit.Constraints;
import androidx.compose.ui.unit.ConstraintsKt;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.DpKt;
import androidx.compose.ui.unit.IntSize;
import androidx.compose.ui.unit.LayoutDirection;
import androidx.compose.ui.util.MathHelpersKt;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.IntIterator;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$IntRef;
import kotlin.math.MathKt;
import kotlin.ranges.ClosedFloatingPointRange;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;

/* JADX INFO: compiled from: Slider.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class SliderKt {
    private static final float ThumbHeight;
    private static final long ThumbSize;
    private static final float ThumbTrackGapSize;
    private static final float ThumbWidth;
    private static final float TrackHeight;
    private static final float TrackInsideCornerSize;
    private static final long VerticalThumbSize;

    /* JADX INFO: renamed from: androidx.compose.material3.SliderKt$sliderTapModifier$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: Slider.kt */
    final class C00481 extends SuspendLambda implements Function2 {
        final /* synthetic */ SliderState $state;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX INFO: renamed from: androidx.compose.material3.SliderKt$sliderTapModifier$1$1, reason: invalid class name and collision with other inner class name */
        /* JADX INFO: compiled from: Slider.kt */
        final class C00121 extends SuspendLambda implements Function3 {
            final /* synthetic */ SliderState $state;
            /* synthetic */ long J$0;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            C00121(SliderState sliderState, Continuation continuation) {
                super(3, continuation);
                this.$state = sliderState;
            }

            @Override // kotlin.jvm.functions.Function3
            public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3) {
                return m337invoked4ec7I((PressGestureScope) obj, ((Offset) obj2).m767unboximpl(), (Continuation) obj3);
            }

            /* JADX INFO: renamed from: invoke-d-4ec7I, reason: not valid java name */
            public final Object m337invoked4ec7I(PressGestureScope pressGestureScope, long j, Continuation continuation) {
                C00121 c00121 = new C00121(this.$state, continuation);
                c00121.J$0 = j;
                return c00121.invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                this.$state.m339onPressk4lQ0M$material3_release(this.J$0);
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C00481(SliderState sliderState, Continuation continuation) {
            super(2, continuation);
            this.$state = sliderState;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            C00481 c00481 = new C00481(this.$state, continuation);
            c00481.L$0 = obj;
            return c00481;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(PointerInputScope pointerInputScope, Continuation continuation) {
            return ((C00481) create(pointerInputScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                PointerInputScope pointerInputScope = (PointerInputScope) this.L$0;
                C00121 c00121 = new C00121(this.$state, null);
                final SliderState sliderState = this.$state;
                Function1 function1 = new Function1() { // from class: androidx.compose.material3.SliderKt.sliderTapModifier.1.2
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Object invoke(Object obj2) {
                        m338invokek4lQ0M(((Offset) obj2).m767unboximpl());
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke-k-4lQ0M, reason: not valid java name */
                    public final void m338invokek4lQ0M(long j) {
                        sliderState.dispatchRawDelta(0.0f);
                        sliderState.getGestureEndAction$material3_release().invoke();
                    }
                };
                this.label = 1;
                if (TapGestureDetectorKt.detectTapGestures$default(pointerInputScope, null, null, c00121, function1, this, 3, null) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    static {
        SliderTokens sliderTokens = SliderTokens.INSTANCE;
        TrackHeight = sliderTokens.m475getInactiveTrackHeightD9Ej5fM();
        float fM474getHandleWidthD9Ej5fM = sliderTokens.m474getHandleWidthD9Ej5fM();
        ThumbWidth = fM474getHandleWidthD9Ej5fM;
        float fM473getHandleHeightD9Ej5fM = sliderTokens.m473getHandleHeightD9Ej5fM();
        ThumbHeight = fM473getHandleHeightD9Ej5fM;
        ThumbSize = DpKt.m1886DpSizeYgX7TsA(fM474getHandleWidthD9Ej5fM, fM473getHandleHeightD9Ej5fM);
        VerticalThumbSize = DpKt.m1886DpSizeYgX7TsA(fM473getHandleHeightD9Ej5fM, fM474getHandleWidthD9Ej5fM);
        ThumbTrackGapSize = sliderTokens.m472getActiveHandleLeadingSpaceD9Ej5fM();
        TrackInsideCornerSize = Dp.m1877constructorimpl(2);
    }

    /* JADX WARN: Removed duplicated region for block: B:100:0x0113  */
    /* JADX WARN: Removed duplicated region for block: B:102:0x011a  */
    /* JADX WARN: Removed duplicated region for block: B:111:0x0133  */
    /* JADX WARN: Removed duplicated region for block: B:119:0x0149  */
    /* JADX WARN: Removed duplicated region for block: B:122:0x0159  */
    /* JADX WARN: Removed duplicated region for block: B:129:0x0177  */
    /* JADX WARN: Removed duplicated region for block: B:141:0x01a8  */
    /* JADX WARN: Removed duplicated region for block: B:142:0x01aa  */
    /* JADX WARN: Removed duplicated region for block: B:144:0x01af  */
    /* JADX WARN: Removed duplicated region for block: B:146:0x01b2  */
    /* JADX WARN: Removed duplicated region for block: B:149:0x01b8  */
    /* JADX WARN: Removed duplicated region for block: B:150:0x01c3  */
    /* JADX WARN: Removed duplicated region for block: B:152:0x01c7  */
    /* JADX WARN: Removed duplicated region for block: B:157:0x01df  */
    /* JADX WARN: Removed duplicated region for block: B:159:0x01e3  */
    /* JADX WARN: Removed duplicated region for block: B:160:0x01f2  */
    /* JADX WARN: Removed duplicated region for block: B:162:0x01f6  */
    /* JADX WARN: Removed duplicated region for block: B:163:0x0207  */
    /* JADX WARN: Removed duplicated region for block: B:166:0x020f  */
    /* JADX WARN: Removed duplicated region for block: B:168:0x021f  */
    /* JADX WARN: Removed duplicated region for block: B:171:0x022d  */
    /* JADX WARN: Removed duplicated region for block: B:172:0x0238  */
    /* JADX WARN: Removed duplicated region for block: B:175:0x0241  */
    /* JADX WARN: Removed duplicated region for block: B:176:0x0243  */
    /* JADX WARN: Removed duplicated region for block: B:186:0x0264  */
    /* JADX WARN: Removed duplicated region for block: B:188:0x026c  */
    /* JADX WARN: Removed duplicated region for block: B:191:0x02b9  */
    /* JADX WARN: Removed duplicated region for block: B:195:0x02cc  */
    /* JADX WARN: Removed duplicated region for block: B:197:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x005e  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0063  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0079  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x007e  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0095  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x00ab  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x00b3  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x00b8  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x00d1  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x00d6  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x00f1  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x00f8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final void Slider(final float r27, final kotlin.jvm.functions.Function1 r28, androidx.compose.ui.Modifier r29, boolean r30, kotlin.jvm.functions.Function0 r31, androidx.compose.material3.SliderColors r32, androidx.compose.foundation.interaction.MutableInteractionSource r33, int r34, kotlin.jvm.functions.Function3 r35, kotlin.jvm.functions.Function3 r36, kotlin.ranges.ClosedFloatingPointRange r37, androidx.compose.runtime.Composer r38, final int r39, final int r40, final int r41) {
        /*
            Method dump skipped, instruction units count: 727
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.material3.SliderKt.Slider(float, kotlin.jvm.functions.Function1, androidx.compose.ui.Modifier, boolean, kotlin.jvm.functions.Function0, androidx.compose.material3.SliderColors, androidx.compose.foundation.interaction.MutableInteractionSource, int, kotlin.jvm.functions.Function3, kotlin.jvm.functions.Function3, kotlin.ranges.ClosedFloatingPointRange, androidx.compose.runtime.Composer, int, int, int):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:100:0x010c  */
    /* JADX WARN: Removed duplicated region for block: B:103:0x0111  */
    /* JADX WARN: Removed duplicated region for block: B:105:0x011d  */
    /* JADX WARN: Removed duplicated region for block: B:111:0x0137  */
    /* JADX WARN: Removed duplicated region for block: B:113:0x0146  */
    /* JADX WARN: Removed duplicated region for block: B:117:0x0160  */
    /* JADX WARN: Removed duplicated region for block: B:120:0x016f  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x01a1  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x01ae  */
    /* JADX WARN: Removed duplicated region for block: B:130:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0045  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x004a  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0060  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0075  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x007b  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0080  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0098  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x009c  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x00b3  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x00b7  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x00d5  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x00e7  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x0103  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x0105  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x0108  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final void Slider(final androidx.compose.material3.SliderState r17, androidx.compose.ui.Modifier r18, boolean r19, androidx.compose.material3.SliderColors r20, androidx.compose.foundation.interaction.MutableInteractionSource r21, kotlin.jvm.functions.Function3 r22, kotlin.jvm.functions.Function3 r23, androidx.compose.runtime.Composer r24, final int r25, final int r26) {
        /*
            Method dump skipped, instruction units count: 438
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.material3.SliderKt.Slider(androidx.compose.material3.SliderState, androidx.compose.ui.Modifier, boolean, androidx.compose.material3.SliderColors, androidx.compose.foundation.interaction.MutableInteractionSource, kotlin.jvm.functions.Function3, kotlin.jvm.functions.Function3, androidx.compose.runtime.Composer, int, int):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void SliderImpl(final Modifier modifier, SliderState sliderState, final boolean z, MutableInteractionSource mutableInteractionSource, final Function3 function3, final Function3 function32, Composer composer, final int i) {
        int i2;
        MutableInteractionSource mutableInteractionSource2;
        final SliderState sliderState2;
        Composer composerStartRestartGroup = composer.startRestartGroup(1390990089);
        if ((i & 6) == 0) {
            i2 = (composerStartRestartGroup.changed(modifier) ? 4 : 2) | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            i2 |= composerStartRestartGroup.changedInstance(sliderState) ? 32 : 16;
        }
        if ((i & 384) == 0) {
            i2 |= composerStartRestartGroup.changed(z) ? 256 : 128;
        }
        if ((i & 3072) == 0) {
            i2 |= composerStartRestartGroup.changed(mutableInteractionSource) ? 2048 : 1024;
        }
        if ((i & 24576) == 0) {
            i2 |= composerStartRestartGroup.changedInstance(function3) ? 16384 : 8192;
        }
        if ((196608 & i) == 0) {
            i2 |= composerStartRestartGroup.changedInstance(function32) ? 131072 : 65536;
        }
        if ((74899 & i2) == 74898 && composerStartRestartGroup.getSkipping()) {
            composerStartRestartGroup.skipToGroupEnd();
            sliderState2 = sliderState;
            mutableInteractionSource2 = mutableInteractionSource;
        } else {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart(1390990089, i2, -1, "androidx.compose.material3.SliderImpl (Slider.kt:759)");
            }
            boolean z2 = true;
            sliderState.setRtl$material3_release(composerStartRestartGroup.consume(CompositionLocalsKt.getLocalLayoutDirection()) == LayoutDirection.Rtl);
            if ((sliderState.getOrientation$material3_release() != Orientation.Horizontal || !sliderState.isRtl$material3_release()) && (sliderState.getOrientation$material3_release() != Orientation.Vertical || !sliderState.getReverseVerticalDirection$material3_release())) {
                z2 = false;
            }
            Modifier.Companion companion = Modifier.Companion;
            Modifier modifierSliderTapModifier = sliderTapModifier(companion, sliderState, mutableInteractionSource, z);
            Orientation orientation$material3_release = sliderState.getOrientation$material3_release();
            int i3 = i2;
            boolean zIsDragging = sliderState.isDragging();
            boolean zChangedInstance = composerStartRestartGroup.changedInstance(sliderState);
            Object objRememberedValue = composerStartRestartGroup.rememberedValue();
            if (zChangedInstance || objRememberedValue == Composer.Companion.getEmpty()) {
                objRememberedValue = new SliderKt$SliderImpl$drag$1$1(sliderState, null);
                composerStartRestartGroup.updateRememberedValue(objRememberedValue);
            }
            Function3 function33 = (Function3) objRememberedValue;
            boolean z3 = z2;
            Modifier modifierDraggable$default = DraggableKt.draggable$default(companion, sliderState, orientation$material3_release, z, mutableInteractionSource, zIsDragging, null, function33, z3, 32, null);
            mutableInteractionSource2 = mutableInteractionSource;
            sliderState2 = sliderState;
            Orientation orientation$material3_release2 = sliderState2.getOrientation$material3_release();
            Orientation orientation = Orientation.Vertical;
            Modifier modifierWrapContentHeight$default = orientation$material3_release2 == orientation ? SizeKt.wrapContentHeight$default(LayoutIdKt.layoutId(companion, SliderComponents.THUMB), null, false, 3, null) : SizeKt.wrapContentWidth$default(LayoutIdKt.layoutId(companion, SliderComponents.THUMB), null, false, 3, null);
            Modifier modifierThen = slideOnKeyEvents(FocusableKt.focusable(sliderSemantics(SizeKt.m175requiredSizeInqDBjuR0$default(InteractiveComponentSizeKt.minimumInteractiveComponentSize(modifier), sliderState2.getOrientation$material3_release() == orientation ? TrackHeight : ThumbWidth, sliderState2.getOrientation$material3_release() == orientation ? ThumbWidth : TrackHeight, 0.0f, 0.0f, 12, null), sliderState2, z), z, mutableInteractionSource2), z, sliderState2.getSteps(), sliderState2.getValueRange(), sliderState2.getValue(), z3, sliderState2.getOnValueChange(), sliderState2.getOnValueChangeFinished()).then(modifierSliderTapModifier).then(modifierDraggable$default);
            boolean zChangedInstance2 = composerStartRestartGroup.changedInstance(sliderState2);
            Object objRememberedValue2 = composerStartRestartGroup.rememberedValue();
            if (zChangedInstance2 || objRememberedValue2 == Composer.Companion.getEmpty()) {
                objRememberedValue2 = new MeasurePolicy() { // from class: androidx.compose.material3.SliderKt$SliderImpl$2$1
                    @Override // androidx.compose.ui.layout.MeasurePolicy
                    /* JADX INFO: renamed from: measure-3p2s80s */
                    public final MeasureResult mo19measure3p2s80s(MeasureScope measureScope, List list, long j) {
                        int width;
                        int iMax;
                        int width2;
                        int height;
                        int iRoundToInt;
                        int size = list.size();
                        int i4 = 0;
                        for (int i5 = 0; i5 < size; i5++) {
                            Measurable measurable = (Measurable) list.get(i5);
                            if (LayoutIdKt.getLayoutId(measurable) == SliderComponents.THUMB) {
                                long j2 = j;
                                final Placeable placeableMo1284measureBRTryo0 = measurable.mo1284measureBRTryo0(j2);
                                int size2 = list.size();
                                while (i4 < size2) {
                                    Measurable measurable2 = (Measurable) list.get(i4);
                                    if (LayoutIdKt.getLayoutId(measurable2) == SliderComponents.TRACK) {
                                        Orientation orientation$material3_release3 = sliderState2.getOrientation$material3_release();
                                        Orientation orientation2 = Orientation.Vertical;
                                        final Placeable placeableMo1284measureBRTryo02 = orientation$material3_release3 == orientation2 ? measurable2.mo1284measureBRTryo0(Constraints.m1852copyZbe2FdA$default(ConstraintsKt.m1874offsetNN6EwU$default(j2, 0, -placeableMo1284measureBRTryo0.getHeight(), 1, null), 0, 0, 0, 0, 14, null)) : measurable2.mo1284measureBRTryo0(Constraints.m1852copyZbe2FdA$default(ConstraintsKt.m1874offsetNN6EwU$default(j, -placeableMo1284measureBRTryo0.getWidth(), 0, 2, null), 0, 0, 0, 0, 11, null));
                                        final Ref$IntRef ref$IntRef = new Ref$IntRef();
                                        if (sliderState2.getOrientation$material3_release() == orientation2) {
                                            width = Math.max(placeableMo1284measureBRTryo02.getWidth(), placeableMo1284measureBRTryo0.getWidth());
                                            iMax = placeableMo1284measureBRTryo0.getHeight() + placeableMo1284measureBRTryo02.getHeight();
                                            width2 = (width - placeableMo1284measureBRTryo02.getWidth()) / 2;
                                            height = placeableMo1284measureBRTryo0.getHeight() / 2;
                                            iRoundToInt = (width - placeableMo1284measureBRTryo0.getWidth()) / 2;
                                            ref$IntRef.element = MathKt.roundToInt(placeableMo1284measureBRTryo02.getHeight() * sliderState2.getCoercedValueAsFraction());
                                            if (sliderState2.getReverseVerticalDirection$material3_release()) {
                                                ref$IntRef.element = placeableMo1284measureBRTryo02.getHeight() - ref$IntRef.element;
                                            }
                                        } else {
                                            width = placeableMo1284measureBRTryo0.getWidth() + placeableMo1284measureBRTryo02.getWidth();
                                            iMax = Math.max(placeableMo1284measureBRTryo02.getHeight(), placeableMo1284measureBRTryo0.getHeight());
                                            width2 = placeableMo1284measureBRTryo0.getWidth() / 2;
                                            height = (iMax - placeableMo1284measureBRTryo02.getHeight()) / 2;
                                            iRoundToInt = MathKt.roundToInt(placeableMo1284measureBRTryo02.getWidth() * sliderState2.getCoercedValueAsFraction());
                                            ref$IntRef.element = (iMax - placeableMo1284measureBRTryo0.getHeight()) / 2;
                                        }
                                        int i6 = width;
                                        final int i7 = width2;
                                        final int i8 = height;
                                        final int i9 = iRoundToInt;
                                        sliderState2.updateDimensions$material3_release(placeableMo1284measureBRTryo02.getWidth(), placeableMo1284measureBRTryo02.getHeight(), i6, iMax);
                                        return MeasureScope.layout$default(measureScope, i6, iMax, null, new Function1() { // from class: androidx.compose.material3.SliderKt$SliderImpl$2$1.1
                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                            {
                                                super(1);
                                            }

                                            @Override // kotlin.jvm.functions.Function1
                                            public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                                                invoke((Placeable.PlacementScope) obj);
                                                return Unit.INSTANCE;
                                            }

                                            public final void invoke(Placeable.PlacementScope placementScope) {
                                                Placeable.PlacementScope.placeRelative$default(placementScope, placeableMo1284measureBRTryo02, i7, i8, 0.0f, 4, null);
                                                Placeable.PlacementScope.placeRelative$default(placementScope, placeableMo1284measureBRTryo0, i9, ref$IntRef.element, 0.0f, 4, null);
                                            }
                                        }, 4, null);
                                    }
                                    i4++;
                                    j2 = j;
                                }
                                throw new NoSuchElementException("Collection contains no element matching the predicate.");
                            }
                        }
                        throw new NoSuchElementException("Collection contains no element matching the predicate.");
                    }
                };
                composerStartRestartGroup.updateRememberedValue(objRememberedValue2);
            }
            MeasurePolicy measurePolicy = (MeasurePolicy) objRememberedValue2;
            int currentCompositeKeyHash = ComposablesKt.getCurrentCompositeKeyHash(composerStartRestartGroup, 0);
            CompositionLocalMap currentCompositionLocalMap = composerStartRestartGroup.getCurrentCompositionLocalMap();
            Modifier modifierMaterializeModifier = ComposedModifierKt.materializeModifier(composerStartRestartGroup, modifierThen);
            ComposeUiNode.Companion companion2 = ComposeUiNode.Companion;
            Function0 constructor = companion2.getConstructor();
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
            Updater.m617setimpl(composerM616constructorimpl, measurePolicy, companion2.getSetMeasurePolicy());
            Updater.m617setimpl(composerM616constructorimpl, currentCompositionLocalMap, companion2.getSetResolvedCompositionLocals());
            Function2 setCompositeKeyHash = companion2.getSetCompositeKeyHash();
            if (composerM616constructorimpl.getInserting() || !Intrinsics.areEqual(composerM616constructorimpl.rememberedValue(), Integer.valueOf(currentCompositeKeyHash))) {
                composerM616constructorimpl.updateRememberedValue(Integer.valueOf(currentCompositeKeyHash));
                composerM616constructorimpl.apply(Integer.valueOf(currentCompositeKeyHash), setCompositeKeyHash);
            }
            Updater.m617setimpl(composerM616constructorimpl, modifierMaterializeModifier, companion2.getSetModifier());
            boolean zChangedInstance3 = composerStartRestartGroup.changedInstance(sliderState2);
            Object objRememberedValue3 = composerStartRestartGroup.rememberedValue();
            if (zChangedInstance3 || objRememberedValue3 == Composer.Companion.getEmpty()) {
                objRememberedValue3 = new Function1() { // from class: androidx.compose.material3.SliderKt$SliderImpl$1$1$1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                        m335invokeozmzZPI(((IntSize) obj).m1926unboximpl());
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke-ozmzZPI, reason: not valid java name */
                    public final void m335invokeozmzZPI(long j) {
                        sliderState2.setThumbWidth$material3_release(IntSize.m1923getWidthimpl(j));
                        sliderState2.setThumbHeight$material3_release(IntSize.m1922getHeightimpl(j));
                    }
                };
                composerStartRestartGroup.updateRememberedValue(objRememberedValue3);
            }
            Modifier modifierOnSizeChanged = OnRemeasuredModifierKt.onSizeChanged(modifierWrapContentHeight$default, (Function1) objRememberedValue3);
            Alignment.Companion companion3 = Alignment.Companion;
            MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy = BoxKt.maybeCachedBoxMeasurePolicy(companion3.getTopStart(), false);
            int currentCompositeKeyHash2 = ComposablesKt.getCurrentCompositeKeyHash(composerStartRestartGroup, 0);
            CompositionLocalMap currentCompositionLocalMap2 = composerStartRestartGroup.getCurrentCompositionLocalMap();
            Modifier modifierMaterializeModifier2 = ComposedModifierKt.materializeModifier(composerStartRestartGroup, modifierOnSizeChanged);
            Function0 constructor2 = companion2.getConstructor();
            if (composerStartRestartGroup.getApplier() == null) {
                ComposablesKt.invalidApplier();
            }
            composerStartRestartGroup.startReusableNode();
            if (composerStartRestartGroup.getInserting()) {
                composerStartRestartGroup.createNode(constructor2);
            } else {
                composerStartRestartGroup.useNode();
            }
            Composer composerM616constructorimpl2 = Updater.m616constructorimpl(composerStartRestartGroup);
            Updater.m617setimpl(composerM616constructorimpl2, measurePolicyMaybeCachedBoxMeasurePolicy, companion2.getSetMeasurePolicy());
            Updater.m617setimpl(composerM616constructorimpl2, currentCompositionLocalMap2, companion2.getSetResolvedCompositionLocals());
            Function2 setCompositeKeyHash2 = companion2.getSetCompositeKeyHash();
            if (composerM616constructorimpl2.getInserting() || !Intrinsics.areEqual(composerM616constructorimpl2.rememberedValue(), Integer.valueOf(currentCompositeKeyHash2))) {
                composerM616constructorimpl2.updateRememberedValue(Integer.valueOf(currentCompositeKeyHash2));
                composerM616constructorimpl2.apply(Integer.valueOf(currentCompositeKeyHash2), setCompositeKeyHash2);
            }
            Updater.m617setimpl(composerM616constructorimpl2, modifierMaterializeModifier2, companion2.getSetModifier());
            BoxScopeInstance boxScopeInstance = BoxScopeInstance.INSTANCE;
            int i4 = (i3 >> 3) & 14;
            function3.invoke(sliderState2, composerStartRestartGroup, Integer.valueOf(((i3 >> 9) & 112) | i4));
            composerStartRestartGroup.endNode();
            Modifier modifierLayoutId = LayoutIdKt.layoutId(companion, SliderComponents.TRACK);
            MeasurePolicy measurePolicyMaybeCachedBoxMeasurePolicy2 = BoxKt.maybeCachedBoxMeasurePolicy(companion3.getTopStart(), false);
            int currentCompositeKeyHash3 = ComposablesKt.getCurrentCompositeKeyHash(composerStartRestartGroup, 0);
            CompositionLocalMap currentCompositionLocalMap3 = composerStartRestartGroup.getCurrentCompositionLocalMap();
            Modifier modifierMaterializeModifier3 = ComposedModifierKt.materializeModifier(composerStartRestartGroup, modifierLayoutId);
            Function0 constructor3 = companion2.getConstructor();
            if (composerStartRestartGroup.getApplier() == null) {
                ComposablesKt.invalidApplier();
            }
            composerStartRestartGroup.startReusableNode();
            if (composerStartRestartGroup.getInserting()) {
                composerStartRestartGroup.createNode(constructor3);
            } else {
                composerStartRestartGroup.useNode();
            }
            Composer composerM616constructorimpl3 = Updater.m616constructorimpl(composerStartRestartGroup);
            Updater.m617setimpl(composerM616constructorimpl3, measurePolicyMaybeCachedBoxMeasurePolicy2, companion2.getSetMeasurePolicy());
            Updater.m617setimpl(composerM616constructorimpl3, currentCompositionLocalMap3, companion2.getSetResolvedCompositionLocals());
            Function2 setCompositeKeyHash3 = companion2.getSetCompositeKeyHash();
            if (composerM616constructorimpl3.getInserting() || !Intrinsics.areEqual(composerM616constructorimpl3.rememberedValue(), Integer.valueOf(currentCompositeKeyHash3))) {
                composerM616constructorimpl3.updateRememberedValue(Integer.valueOf(currentCompositeKeyHash3));
                composerM616constructorimpl3.apply(Integer.valueOf(currentCompositeKeyHash3), setCompositeKeyHash3);
            }
            Updater.m617setimpl(composerM616constructorimpl3, modifierMaterializeModifier3, companion2.getSetModifier());
            function32.invoke(sliderState2, composerStartRestartGroup, Integer.valueOf(i4 | ((i3 >> 12) & 112)));
            composerStartRestartGroup.endNode();
            composerStartRestartGroup.endNode();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        }
        ScopeUpdateScope scopeUpdateScopeEndRestartGroup = composerStartRestartGroup.endRestartGroup();
        if (scopeUpdateScopeEndRestartGroup != null) {
            final SliderState sliderState3 = sliderState2;
            final MutableInteractionSource mutableInteractionSource3 = mutableInteractionSource2;
            scopeUpdateScopeEndRestartGroup.updateScope(new Function2() { // from class: androidx.compose.material3.SliderKt.SliderImpl.3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
                    invoke((Composer) obj, ((Number) obj2).intValue());
                    return Unit.INSTANCE;
                }

                public final void invoke(Composer composer2, int i5) {
                    SliderKt.SliderImpl(modifier, sliderState3, z, mutableInteractionSource3, function3, function32, composer2, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final float calcFraction(float f, float f2, float f3) {
        float f4 = f2 - f;
        return RangesKt.coerceIn(f4 == 0.0f ? 0.0f : (f3 - f) / f4, 0.0f, 1.0f);
    }

    public static final float getTrackHeight() {
        return TrackHeight;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final float scale(float f, float f2, float f3, float f4, float f5) {
        return MathHelpersKt.lerp(f4, f5, calcFraction(f, f2, f3));
    }

    private static final Modifier slideOnKeyEvents(Modifier modifier, final boolean z, final int i, final ClosedFloatingPointRange closedFloatingPointRange, final float f, final boolean z2, final Function1 function1, final Function0 function0) {
        if (i >= 0) {
            return KeyInputModifierKt.onKeyEvent(modifier, new Function1() { // from class: androidx.compose.material3.SliderKt.slideOnKeyEvents.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                    return m336invokeZmokQxo(((KeyEvent) obj).m1188unboximpl());
                }

                /* JADX INFO: renamed from: invoke-ZmokQxo, reason: not valid java name */
                public final Boolean m336invokeZmokQxo(android.view.KeyEvent keyEvent) {
                    if (z && function1 != null) {
                        int iM1195getTypeZmokQxo = KeyEvent_androidKt.m1195getTypeZmokQxo(keyEvent);
                        KeyEventType.Companion companion = KeyEventType.Companion;
                        boolean z3 = false;
                        if (KeyEventType.m1190equalsimpl0(iM1195getTypeZmokQxo, companion.m1191getKeyDownCS__XNY())) {
                            float fAbs = Math.abs(((Number) closedFloatingPointRange.getEndInclusive()).floatValue() - ((Number) closedFloatingPointRange.getStart()).floatValue());
                            int i2 = i;
                            float f2 = fAbs / (i2 > 0 ? i2 + 1 : 100);
                            int i3 = z2 ? -1 : 1;
                            long jM1194getKeyZmokQxo = KeyEvent_androidKt.m1194getKeyZmokQxo(keyEvent);
                            Key.Companion companion2 = Key.Companion;
                            if (Key.m1165equalsimpl0(jM1194getKeyZmokQxo, companion2.m1171getDirectionUpEK5gGoQ())) {
                                function1.invoke(RangesKt.coerceIn(Float.valueOf(f + (i3 * f2)), closedFloatingPointRange));
                            } else if (Key.m1165equalsimpl0(jM1194getKeyZmokQxo, companion2.m1168getDirectionDownEK5gGoQ())) {
                                function1.invoke(RangesKt.coerceIn(Float.valueOf(f - (i3 * f2)), closedFloatingPointRange));
                            } else if (Key.m1165equalsimpl0(jM1194getKeyZmokQxo, companion2.m1170getDirectionRightEK5gGoQ())) {
                                function1.invoke(RangesKt.coerceIn(Float.valueOf(f + (i3 * f2)), closedFloatingPointRange));
                            } else if (Key.m1165equalsimpl0(jM1194getKeyZmokQxo, companion2.m1169getDirectionLeftEK5gGoQ())) {
                                function1.invoke(RangesKt.coerceIn(Float.valueOf(f - (i3 * f2)), closedFloatingPointRange));
                            } else if (Key.m1165equalsimpl0(jM1194getKeyZmokQxo, companion2.m1175getMoveHomeEK5gGoQ())) {
                                function1.invoke(closedFloatingPointRange.getStart());
                            } else if (Key.m1165equalsimpl0(jM1194getKeyZmokQxo, companion2.m1174getMoveEndEK5gGoQ())) {
                                function1.invoke(closedFloatingPointRange.getEndInclusive());
                            } else if (Key.m1165equalsimpl0(jM1194getKeyZmokQxo, companion2.m1180getPageUpEK5gGoQ())) {
                                function1.invoke(RangesKt.coerceIn(Float.valueOf(f - (RangesKt.coerceIn(r1 / 10, 1, 10) * f2)), closedFloatingPointRange));
                            } else if (Key.m1165equalsimpl0(jM1194getKeyZmokQxo, companion2.m1179getPageDownEK5gGoQ())) {
                                function1.invoke(RangesKt.coerceIn(Float.valueOf(f + (RangesKt.coerceIn(r1 / 10, 1, 10) * f2)), closedFloatingPointRange));
                            }
                            z3 = true;
                        } else if (KeyEventType.m1190equalsimpl0(iM1195getTypeZmokQxo, companion.m1192getKeyUpCS__XNY())) {
                            long jM1194getKeyZmokQxo2 = KeyEvent_androidKt.m1194getKeyZmokQxo(keyEvent);
                            Key.Companion companion3 = Key.Companion;
                            if (Key.m1165equalsimpl0(jM1194getKeyZmokQxo2, companion3.m1171getDirectionUpEK5gGoQ()) ? true : Key.m1165equalsimpl0(jM1194getKeyZmokQxo2, companion3.m1168getDirectionDownEK5gGoQ()) ? true : Key.m1165equalsimpl0(jM1194getKeyZmokQxo2, companion3.m1170getDirectionRightEK5gGoQ()) ? true : Key.m1165equalsimpl0(jM1194getKeyZmokQxo2, companion3.m1169getDirectionLeftEK5gGoQ()) ? true : Key.m1165equalsimpl0(jM1194getKeyZmokQxo2, companion3.m1175getMoveHomeEK5gGoQ()) ? true : Key.m1165equalsimpl0(jM1194getKeyZmokQxo2, companion3.m1174getMoveEndEK5gGoQ()) ? true : Key.m1165equalsimpl0(jM1194getKeyZmokQxo2, companion3.m1180getPageUpEK5gGoQ()) ? true : Key.m1165equalsimpl0(jM1194getKeyZmokQxo2, companion3.m1179getPageDownEK5gGoQ())) {
                                Function0 function02 = function0;
                                if (function02 != null) {
                                    function02.invoke();
                                }
                                z3 = true;
                            }
                        }
                        return Boolean.valueOf(z3);
                    }
                    return Boolean.FALSE;
                }
            });
        }
        throw new IllegalArgumentException("steps should be >= 0");
    }

    private static final Modifier sliderSemantics(Modifier modifier, final SliderState sliderState, final boolean z) {
        return ProgressSemanticsKt.progressSemantics(SemanticsModifierKt.semantics$default(modifier, false, new Function1() { // from class: androidx.compose.material3.SliderKt.sliderSemantics.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                invoke((SemanticsPropertyReceiver) obj);
                return Unit.INSTANCE;
            }

            public final void invoke(SemanticsPropertyReceiver semanticsPropertyReceiver) {
                if (!z) {
                    SemanticsPropertiesKt.disabled(semanticsPropertyReceiver);
                }
                final SliderState sliderState2 = sliderState;
                SemanticsPropertiesKt.setProgress$default(semanticsPropertyReceiver, null, new Function1() { // from class: androidx.compose.material3.SliderKt.sliderSemantics.1.1
                    {
                        super(1);
                    }

                    public final Boolean invoke(float f) {
                        int steps;
                        float fCoerceIn = RangesKt.coerceIn(f, ((Number) sliderState2.getValueRange().getStart()).floatValue(), ((Number) sliderState2.getValueRange().getEndInclusive()).floatValue());
                        boolean z2 = false;
                        if (sliderState2.getSteps() > 0 && (steps = sliderState2.getSteps() + 1) >= 0) {
                            float fAbs = fCoerceIn;
                            float f2 = fAbs;
                            int i = 0;
                            while (true) {
                                float fLerp = MathHelpersKt.lerp(((Number) sliderState2.getValueRange().getStart()).floatValue(), ((Number) sliderState2.getValueRange().getEndInclusive()).floatValue(), i / (sliderState2.getSteps() + 1));
                                float f3 = fLerp - fCoerceIn;
                                if (Math.abs(f3) <= fAbs) {
                                    fAbs = Math.abs(f3);
                                    f2 = fLerp;
                                }
                                if (i == steps) {
                                    break;
                                }
                                i++;
                            }
                            fCoerceIn = f2;
                        }
                        if (fCoerceIn != sliderState2.getValue()) {
                            if (fCoerceIn != sliderState2.getValue()) {
                                if (sliderState2.getOnValueChange() != null) {
                                    Function1 onValueChange = sliderState2.getOnValueChange();
                                    if (onValueChange != null) {
                                        onValueChange.invoke(Float.valueOf(fCoerceIn));
                                    }
                                } else {
                                    sliderState2.setValue(fCoerceIn);
                                }
                            }
                            Function0 onValueChangeFinished = sliderState2.getOnValueChangeFinished();
                            if (onValueChangeFinished != null) {
                                onValueChangeFinished.invoke();
                            }
                            z2 = true;
                        }
                        return Boolean.valueOf(z2);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                        return invoke(((Number) obj).floatValue());
                    }
                }, 1, null);
            }
        }, 1, null).then(AccessibilityUtilKt.getIncreaseHorizontalSemanticsBounds()), sliderState.getValue(), RangesKt.rangeTo(((Number) sliderState.getValueRange().getStart()).floatValue(), ((Number) sliderState.getValueRange().getEndInclusive()).floatValue()), sliderState.getSteps());
    }

    private static final Modifier sliderTapModifier(Modifier modifier, SliderState sliderState, MutableInteractionSource mutableInteractionSource, boolean z) {
        return z ? modifier.then(new SuspendPointerInputElement(sliderState, mutableInteractionSource, null, new SuspendingPointerInputFilterKt$sam$androidx_compose_ui_input_pointer_PointerInputEventHandler$0(new C00481(sliderState, null)), 4, null)) : modifier;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final float snapValueToTick(float f, float[] fArr, float f2, float f3) {
        Float fValueOf;
        if (fArr.length == 0) {
            fValueOf = null;
        } else {
            float f4 = fArr[0];
            int lastIndex = ArraysKt.getLastIndex(fArr);
            if (lastIndex == 0) {
                fValueOf = Float.valueOf(f4);
            } else {
                float fAbs = Math.abs(MathHelpersKt.lerp(f2, f3, f4) - f);
                IntIterator it = new IntRange(1, lastIndex).iterator();
                while (it.hasNext()) {
                    float f5 = fArr[it.nextInt()];
                    float fAbs2 = Math.abs(MathHelpersKt.lerp(f2, f3, f5) - f);
                    if (Float.compare(fAbs, fAbs2) > 0) {
                        f4 = f5;
                        fAbs = fAbs2;
                    }
                }
                fValueOf = Float.valueOf(f4);
            }
        }
        return fValueOf != null ? MathHelpersKt.lerp(f2, f3, fValueOf.floatValue()) : f;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final float[] stepsToTickFractions(int i) {
        if (i == 0) {
            return new float[0];
        }
        int i2 = i + 2;
        float[] fArr = new float[i2];
        for (int i3 = 0; i3 < i2; i3++) {
            fArr[i3] = i3 / (i + 1);
        }
        return fArr;
    }
}
