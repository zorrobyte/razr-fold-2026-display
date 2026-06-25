package androidx.compose.animation;

import androidx.compose.animation.core.FiniteAnimationSpec;
import androidx.compose.animation.core.Transition;
import androidx.compose.runtime.State;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.layout.Measurable;
import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;
import androidx.compose.ui.unit.ConstraintsKt;
import androidx.compose.ui.unit.IntOffset;
import androidx.compose.ui.unit.IntSize;
import androidx.compose.ui.unit.LayoutDirection;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: EnterExitTransition.kt */
/* JADX INFO: loaded from: classes.dex */
final class EnterExitTransitionModifierNode extends LayoutModifierNodeWithPassThroughIntrinsics {
    private Alignment currentAlignment;
    private EnterTransition enter;
    private ExitTransition exit;
    private GraphicsLayerBlockForEnterExit graphicsLayerBlock;
    private Function0 isEnabled;
    private boolean lookaheadConstraintsAvailable;
    private Transition.DeferredAnimation offsetAnimation;
    private Transition.DeferredAnimation sizeAnimation;
    private Transition.DeferredAnimation slideAnimation;
    private Transition transition;
    private long lookaheadSize = AnimationModifierKt.getInvalidSize();
    private long lookaheadConstraints = ConstraintsKt.Constraints$default(0, 0, 0, 0, 15, null);
    private final Function1 sizeTransitionSpec = new Function1() { // from class: androidx.compose.animation.EnterExitTransitionModifierNode$sizeTransitionSpec$1
        {
            super(1);
        }

        @Override // kotlin.jvm.functions.Function1
        public final FiniteAnimationSpec invoke(Transition.Segment segment) {
            EnterExitState enterExitState = EnterExitState.PreEnter;
            EnterExitState enterExitState2 = EnterExitState.Visible;
            FiniteAnimationSpec animationSpec = null;
            if (segment.isTransitioningTo(enterExitState, enterExitState2)) {
                ChangeSize changeSize = this.this$0.getEnter().getData$animation().getChangeSize();
                if (changeSize != null) {
                    animationSpec = changeSize.getAnimationSpec();
                }
            } else if (segment.isTransitioningTo(enterExitState2, EnterExitState.PostExit)) {
                ChangeSize changeSize2 = this.this$0.getExit().getData$animation().getChangeSize();
                if (changeSize2 != null) {
                    animationSpec = changeSize2.getAnimationSpec();
                }
            } else {
                animationSpec = EnterExitTransitionKt.DefaultSizeAnimationSpec;
            }
            return animationSpec == null ? EnterExitTransitionKt.DefaultSizeAnimationSpec : animationSpec;
        }
    };
    private final Function1 slideSpec = new Function1() { // from class: androidx.compose.animation.EnterExitTransitionModifierNode$slideSpec$1
        {
            super(1);
        }

        @Override // kotlin.jvm.functions.Function1
        public final FiniteAnimationSpec invoke(Transition.Segment segment) {
            EnterExitState enterExitState = EnterExitState.PreEnter;
            EnterExitState enterExitState2 = EnterExitState.Visible;
            if (segment.isTransitioningTo(enterExitState, enterExitState2)) {
                this.this$0.getEnter().getData$animation().getSlide();
                return EnterExitTransitionKt.DefaultOffsetAnimationSpec;
            }
            if (!segment.isTransitioningTo(enterExitState2, EnterExitState.PostExit)) {
                return EnterExitTransitionKt.DefaultOffsetAnimationSpec;
            }
            this.this$0.getExit().getData$animation().getSlide();
            return EnterExitTransitionKt.DefaultOffsetAnimationSpec;
        }
    };

    /* JADX INFO: compiled from: EnterExitTransition.kt */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[EnterExitState.values().length];
            try {
                iArr[EnterExitState.Visible.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[EnterExitState.PreEnter.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[EnterExitState.PostExit.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public EnterExitTransitionModifierNode(Transition transition, Transition.DeferredAnimation deferredAnimation, Transition.DeferredAnimation deferredAnimation2, Transition.DeferredAnimation deferredAnimation3, EnterTransition enterTransition, ExitTransition exitTransition, Function0 function0, GraphicsLayerBlockForEnterExit graphicsLayerBlockForEnterExit) {
        this.transition = transition;
        this.sizeAnimation = deferredAnimation;
        this.offsetAnimation = deferredAnimation2;
        this.slideAnimation = deferredAnimation3;
        this.enter = enterTransition;
        this.exit = exitTransition;
        this.isEnabled = function0;
        this.graphicsLayerBlock = graphicsLayerBlockForEnterExit;
    }

    /* JADX INFO: renamed from: setLookaheadConstraints-BRTryo0, reason: not valid java name */
    private final void m33setLookaheadConstraintsBRTryo0(long j) {
        this.lookaheadConstraintsAvailable = true;
        this.lookaheadConstraints = j;
    }

    public final Alignment getAlignment() {
        Alignment alignment;
        Alignment alignment2;
        if (this.transition.getSegment().isTransitioningTo(EnterExitState.PreEnter, EnterExitState.Visible)) {
            ChangeSize changeSize = this.enter.getData$animation().getChangeSize();
            if (changeSize != null && (alignment2 = changeSize.getAlignment()) != null) {
                return alignment2;
            }
            ChangeSize changeSize2 = this.exit.getData$animation().getChangeSize();
            if (changeSize2 != null) {
                return changeSize2.getAlignment();
            }
            return null;
        }
        ChangeSize changeSize3 = this.exit.getData$animation().getChangeSize();
        if (changeSize3 != null && (alignment = changeSize3.getAlignment()) != null) {
            return alignment;
        }
        ChangeSize changeSize4 = this.enter.getData$animation().getChangeSize();
        if (changeSize4 != null) {
            return changeSize4.getAlignment();
        }
        return null;
    }

    public final EnterTransition getEnter() {
        return this.enter;
    }

    public final ExitTransition getExit() {
        return this.exit;
    }

    @Override // androidx.compose.ui.node.LayoutModifierNode
    /* JADX INFO: renamed from: measure-3p2s80s, reason: not valid java name */
    public MeasureResult mo34measure3p2s80s(MeasureScope measureScope, Measurable measurable, long j) {
        State stateAnimate;
        State stateAnimate2;
        if (this.transition.getCurrentState() == this.transition.getTargetState()) {
            this.currentAlignment = null;
        } else if (this.currentAlignment == null) {
            Alignment alignment = getAlignment();
            if (alignment == null) {
                alignment = Alignment.Companion.getTopStart();
            }
            this.currentAlignment = alignment;
        }
        if (measureScope.isLookingAhead()) {
            final Placeable placeableMo1284measureBRTryo0 = measurable.mo1284measureBRTryo0(j);
            long jM1919constructorimpl = IntSize.m1919constructorimpl((((long) placeableMo1284measureBRTryo0.getWidth()) << 32) | (((long) placeableMo1284measureBRTryo0.getHeight()) & 4294967295L));
            this.lookaheadSize = jM1919constructorimpl;
            m33setLookaheadConstraintsBRTryo0(j);
            return MeasureScope.layout$default(measureScope, (int) (jM1919constructorimpl >> 32), (int) (jM1919constructorimpl & 4294967295L), null, new Function1() { // from class: androidx.compose.animation.EnterExitTransitionModifierNode$measure$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                    invoke((Placeable.PlacementScope) obj);
                    return Unit.INSTANCE;
                }

                public final void invoke(Placeable.PlacementScope placementScope) {
                    Placeable.PlacementScope.place$default(placementScope, placeableMo1284measureBRTryo0, 0, 0, 0.0f, 4, null);
                }
            }, 4, null);
        }
        if (!((Boolean) this.isEnabled.invoke()).booleanValue()) {
            final Placeable placeableMo1284measureBRTryo02 = measurable.mo1284measureBRTryo0(j);
            return MeasureScope.layout$default(measureScope, placeableMo1284measureBRTryo02.getWidth(), placeableMo1284measureBRTryo02.getHeight(), null, new Function1() { // from class: androidx.compose.animation.EnterExitTransitionModifierNode$measure$3$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                    invoke((Placeable.PlacementScope) obj);
                    return Unit.INSTANCE;
                }

                public final void invoke(Placeable.PlacementScope placementScope) {
                    Placeable.PlacementScope.place$default(placementScope, placeableMo1284measureBRTryo02, 0, 0, 0.0f, 4, null);
                }
            }, 4, null);
        }
        final Function1 function1Init = this.graphicsLayerBlock.init();
        final Placeable placeableMo1284measureBRTryo03 = measurable.mo1284measureBRTryo0(j);
        long jM1919constructorimpl2 = IntSize.m1919constructorimpl((((long) placeableMo1284measureBRTryo03.getWidth()) << 32) | (((long) placeableMo1284measureBRTryo03.getHeight()) & 4294967295L));
        final long j2 = AnimationModifierKt.m22isValidozmzZPI(this.lookaheadSize) ? this.lookaheadSize : jM1919constructorimpl2;
        Transition.DeferredAnimation deferredAnimation = this.sizeAnimation;
        State stateAnimate3 = deferredAnimation != null ? deferredAnimation.animate(this.sizeTransitionSpec, new Function1() { // from class: androidx.compose.animation.EnterExitTransitionModifierNode$measure$animSize$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                return IntSize.m1918boximpl(m38invokeYEO4UFw((EnterExitState) obj));
            }

            /* JADX INFO: renamed from: invoke-YEO4UFw, reason: not valid java name */
            public final long m38invokeYEO4UFw(EnterExitState enterExitState) {
                return this.this$0.m35sizeByStateUzc_VyU(enterExitState, j2);
            }
        }) : null;
        if (stateAnimate3 != null) {
            jM1919constructorimpl2 = ((IntSize) stateAnimate3.getValue()).m1926unboximpl();
        }
        long jM1869constrain4WqzIAM = ConstraintsKt.m1869constrain4WqzIAM(j, jM1919constructorimpl2);
        Transition.DeferredAnimation deferredAnimation2 = this.offsetAnimation;
        long jM1913getZeronOccac = (deferredAnimation2 == null || (stateAnimate2 = deferredAnimation2.animate(new Function1() { // from class: androidx.compose.animation.EnterExitTransitionModifierNode$measure$offsetDelta$1
            @Override // kotlin.jvm.functions.Function1
            public final FiniteAnimationSpec invoke(Transition.Segment segment) {
                return EnterExitTransitionKt.DefaultOffsetAnimationSpec;
            }
        }, new Function1() { // from class: androidx.compose.animation.EnterExitTransitionModifierNode$measure$offsetDelta$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                return IntOffset.m1901boximpl(m39invokeBjo55l4((EnterExitState) obj));
            }

            /* JADX INFO: renamed from: invoke-Bjo55l4, reason: not valid java name */
            public final long m39invokeBjo55l4(EnterExitState enterExitState) {
                return this.this$0.m37targetOffsetByStateoFUgxo0(enterExitState, j2);
            }
        })) == null) ? IntOffset.Companion.m1913getZeronOccac() : ((IntOffset) stateAnimate2.getValue()).m1911unboximpl();
        Transition.DeferredAnimation deferredAnimation3 = this.slideAnimation;
        long jM1913getZeronOccac2 = (deferredAnimation3 == null || (stateAnimate = deferredAnimation3.animate(this.slideSpec, new Function1() { // from class: androidx.compose.animation.EnterExitTransitionModifierNode$measure$slideOffset$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                return IntOffset.m1901boximpl(m40invokeBjo55l4((EnterExitState) obj));
            }

            /* JADX INFO: renamed from: invoke-Bjo55l4, reason: not valid java name */
            public final long m40invokeBjo55l4(EnterExitState enterExitState) {
                return this.this$0.m36slideTargetValueByStateoFUgxo0(enterExitState, j2);
            }
        })) == null) ? IntOffset.Companion.m1913getZeronOccac() : ((IntOffset) stateAnimate.getValue()).m1911unboximpl();
        Alignment alignment2 = this.currentAlignment;
        final long jM1909plusqkQi6aY = IntOffset.m1909plusqkQi6aY(alignment2 != null ? alignment2.mo662alignKFBX0sM(j2, jM1869constrain4WqzIAM, LayoutDirection.Ltr) : IntOffset.Companion.m1913getZeronOccac(), jM1913getZeronOccac2);
        final long j3 = jM1913getZeronOccac;
        return MeasureScope.layout$default(measureScope, (int) (jM1869constrain4WqzIAM >> 32), (int) (jM1869constrain4WqzIAM & 4294967295L), null, new Function1() { // from class: androidx.compose.animation.EnterExitTransitionModifierNode$measure$2
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
                placementScope.placeWithLayer(placeableMo1284measureBRTryo03, IntOffset.m1905getXimpl(j3) + IntOffset.m1905getXimpl(jM1909plusqkQi6aY), IntOffset.m1906getYimpl(j3) + IntOffset.m1906getYimpl(jM1909plusqkQi6aY), 0.0f, function1Init);
            }
        }, 4, null);
    }

    @Override // androidx.compose.ui.Modifier.Node
    public void onAttach() {
        super.onAttach();
        this.lookaheadConstraintsAvailable = false;
        this.lookaheadSize = AnimationModifierKt.getInvalidSize();
    }

    public final void setEnabled(Function0 function0) {
        this.isEnabled = function0;
    }

    public final void setEnter(EnterTransition enterTransition) {
        this.enter = enterTransition;
    }

    public final void setExit(ExitTransition exitTransition) {
        this.exit = exitTransition;
    }

    public final void setGraphicsLayerBlock(GraphicsLayerBlockForEnterExit graphicsLayerBlockForEnterExit) {
        this.graphicsLayerBlock = graphicsLayerBlockForEnterExit;
    }

    public final void setOffsetAnimation(Transition.DeferredAnimation deferredAnimation) {
        this.offsetAnimation = deferredAnimation;
    }

    public final void setSizeAnimation(Transition.DeferredAnimation deferredAnimation) {
        this.sizeAnimation = deferredAnimation;
    }

    public final void setSlideAnimation(Transition.DeferredAnimation deferredAnimation) {
        this.slideAnimation = deferredAnimation;
    }

    public final void setTransition(Transition transition) {
        this.transition = transition;
    }

    /* JADX INFO: renamed from: sizeByState-Uzc_VyU, reason: not valid java name */
    public final long m35sizeByStateUzc_VyU(EnterExitState enterExitState, long j) {
        Function1 size;
        Function1 size2;
        int i = WhenMappings.$EnumSwitchMapping$0[enterExitState.ordinal()];
        if (i != 1) {
            if (i == 2) {
                ChangeSize changeSize = this.enter.getData$animation().getChangeSize();
                if (changeSize != null && (size = changeSize.getSize()) != null) {
                    return ((IntSize) size.invoke(IntSize.m1918boximpl(j))).m1926unboximpl();
                }
            } else {
                if (i != 3) {
                    throw new NoWhenBranchMatchedException();
                }
                ChangeSize changeSize2 = this.exit.getData$animation().getChangeSize();
                if (changeSize2 != null && (size2 = changeSize2.getSize()) != null) {
                    return ((IntSize) size2.invoke(IntSize.m1918boximpl(j))).m1926unboximpl();
                }
            }
        }
        return j;
    }

    /* JADX INFO: renamed from: slideTargetValueByState-oFUgxo0, reason: not valid java name */
    public final long m36slideTargetValueByStateoFUgxo0(EnterExitState enterExitState, long j) {
        this.enter.getData$animation().getSlide();
        IntOffset.Companion companion = IntOffset.Companion;
        long jM1913getZeronOccac = companion.m1913getZeronOccac();
        this.exit.getData$animation().getSlide();
        long jM1913getZeronOccac2 = companion.m1913getZeronOccac();
        int i = WhenMappings.$EnumSwitchMapping$0[enterExitState.ordinal()];
        if (i == 1) {
            return companion.m1913getZeronOccac();
        }
        if (i == 2) {
            return jM1913getZeronOccac;
        }
        if (i == 3) {
            return jM1913getZeronOccac2;
        }
        throw new NoWhenBranchMatchedException();
    }

    /* JADX INFO: renamed from: targetOffsetByState-oFUgxo0, reason: not valid java name */
    public final long m37targetOffsetByStateoFUgxo0(EnterExitState enterExitState, long j) {
        int i;
        if (this.currentAlignment != null && getAlignment() != null && !Intrinsics.areEqual(this.currentAlignment, getAlignment()) && (i = WhenMappings.$EnumSwitchMapping$0[enterExitState.ordinal()]) != 1 && i != 2) {
            if (i != 3) {
                throw new NoWhenBranchMatchedException();
            }
            ChangeSize changeSize = this.exit.getData$animation().getChangeSize();
            if (changeSize == null) {
                return IntOffset.Companion.m1913getZeronOccac();
            }
            long jM1926unboximpl = ((IntSize) changeSize.getSize().invoke(IntSize.m1918boximpl(j))).m1926unboximpl();
            Alignment alignment = getAlignment();
            alignment.getClass();
            LayoutDirection layoutDirection = LayoutDirection.Ltr;
            long jMo662alignKFBX0sM = alignment.mo662alignKFBX0sM(j, jM1926unboximpl, layoutDirection);
            Alignment alignment2 = this.currentAlignment;
            alignment2.getClass();
            return IntOffset.m1908minusqkQi6aY(jMo662alignKFBX0sM, alignment2.mo662alignKFBX0sM(j, jM1926unboximpl, layoutDirection));
        }
        return IntOffset.Companion.m1913getZeronOccac();
    }
}
