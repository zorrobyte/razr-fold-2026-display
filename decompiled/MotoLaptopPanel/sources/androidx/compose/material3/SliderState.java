package androidx.compose.material3;

import androidx.compose.foundation.MutatePriority;
import androidx.compose.foundation.MutatorMutex;
import androidx.compose.foundation.gestures.DragScope;
import androidx.compose.foundation.gestures.DraggableState;
import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.runtime.MutableFloatState;
import androidx.compose.runtime.MutableIntState;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.PrimitiveSnapshotStateKt;
import androidx.compose.runtime.SnapshotIntStateKt;
import androidx.compose.runtime.SnapshotStateKt__SnapshotStateKt;
import androidx.compose.ui.geometry.Offset;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.ranges.ClosedFloatingPointRange;
import kotlin.ranges.RangesKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;

/* JADX INFO: compiled from: Slider.kt */
/* JADX INFO: loaded from: classes.dex */
public final class SliderState implements DraggableState {
    public static final int $stable = 8;
    private boolean isRtl;
    private Function1 onValueChange;
    private Function0 onValueChangeFinished;
    private final MutableFloatState rawOffset$delegate;
    private boolean reverseVerticalDirection;
    private final int steps;
    private final float[] tickFractions;
    private final ClosedFloatingPointRange valueRange;
    private final MutableFloatState valueState$delegate;
    private final MutableIntState totalWidth$delegate = SnapshotIntStateKt.mutableIntStateOf(0);
    private final MutableIntState totalHeight$delegate = SnapshotIntStateKt.mutableIntStateOf(0);
    private final MutableIntState thumbWidth$delegate = SnapshotIntStateKt.mutableIntStateOf(0);
    private final MutableIntState thumbHeight$delegate = SnapshotIntStateKt.mutableIntStateOf(0);
    private final MutableIntState trackWidth$delegate = SnapshotIntStateKt.mutableIntStateOf(0);
    private final MutableIntState trackHeight$delegate = SnapshotIntStateKt.mutableIntStateOf(0);
    private Orientation orientation = Orientation.Horizontal;
    private final MutableState isDragging$delegate = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default(Boolean.FALSE, null, 2, null);
    private final Function0 gestureEndAction = new Function0() { // from class: androidx.compose.material3.SliderState$gestureEndAction$1
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public /* bridge */ /* synthetic */ Object invoke() {
            m340invoke();
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: invoke, reason: collision with other method in class */
        public final void m340invoke() {
            Function0 onValueChangeFinished;
            if (this.this$0.isDragging() || (onValueChangeFinished = this.this$0.getOnValueChangeFinished()) == null) {
                return;
            }
            onValueChangeFinished.invoke();
        }
    };
    private final MutableFloatState pressOffset$delegate = PrimitiveSnapshotStateKt.mutableFloatStateOf(0.0f);
    private final DragScope dragScope = new DragScope() { // from class: androidx.compose.material3.SliderState$dragScope$1
        @Override // androidx.compose.foundation.gestures.DragScope
        public void dragBy(float f) {
            this.this$0.dispatchRawDelta(f);
        }
    };
    private final MutatorMutex scrollMutex = new MutatorMutex();

    /* JADX INFO: renamed from: androidx.compose.material3.SliderState$drag$2, reason: invalid class name */
    /* JADX INFO: compiled from: Slider.kt */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ Function2 $block;
        final /* synthetic */ MutatePriority $dragPriority;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass2(MutatePriority mutatePriority, Function2 function2, Continuation continuation) {
            super(2, continuation);
            this.$dragPriority = mutatePriority;
            this.$block = function2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return SliderState.this.new AnonymousClass2(this.$dragPriority, this.$block, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                SliderState.this.setDragging(true);
                MutatorMutex mutatorMutex = SliderState.this.scrollMutex;
                DragScope dragScope = SliderState.this.dragScope;
                MutatePriority mutatePriority = this.$dragPriority;
                Function2 function2 = this.$block;
                this.label = 1;
                if (mutatorMutex.mutateWith(dragScope, mutatePriority, function2, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            SliderState.this.setDragging(false);
            return Unit.INSTANCE;
        }
    }

    public SliderState(float f, int i, Function0 function0, ClosedFloatingPointRange closedFloatingPointRange) {
        this.steps = i;
        this.onValueChangeFinished = function0;
        this.valueRange = closedFloatingPointRange;
        this.valueState$delegate = PrimitiveSnapshotStateKt.mutableFloatStateOf(f);
        this.tickFractions = SliderKt.stepsToTickFractions(i);
        this.rawOffset$delegate = PrimitiveSnapshotStateKt.mutableFloatStateOf(scaleToOffset(0.0f, 0.0f, f));
    }

    private final float calculateSnappedValue(float f) {
        return SliderKt.snapValueToTick(RangesKt.coerceIn(f, ((Number) this.valueRange.getStart()).floatValue(), ((Number) this.valueRange.getEndInclusive()).floatValue()), this.tickFractions, ((Number) this.valueRange.getStart()).floatValue(), ((Number) this.valueRange.getEndInclusive()).floatValue());
    }

    private final float getPressOffset() {
        return this.pressOffset$delegate.getFloatValue();
    }

    private final float getRawOffset() {
        return this.rawOffset$delegate.getFloatValue();
    }

    private final int getTotalHeight() {
        return this.totalHeight$delegate.getIntValue();
    }

    private final int getTotalWidth() {
        return this.totalWidth$delegate.getIntValue();
    }

    private final float getValueState() {
        return this.valueState$delegate.getFloatValue();
    }

    private final float scaleToOffset(float f, float f2, float f3) {
        return SliderKt.scale(((Number) this.valueRange.getStart()).floatValue(), ((Number) this.valueRange.getEndInclusive()).floatValue(), f3, f, f2);
    }

    private final float scaleToUserValue(float f, float f2, float f3) {
        return SliderKt.scale(f, f2, f3, ((Number) this.valueRange.getStart()).floatValue(), ((Number) this.valueRange.getEndInclusive()).floatValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void setDragging(boolean z) {
        this.isDragging$delegate.setValue(Boolean.valueOf(z));
    }

    private final void setPressOffset(float f) {
        this.pressOffset$delegate.setFloatValue(f);
    }

    private final void setRawOffset(float f) {
        this.rawOffset$delegate.setFloatValue(f);
    }

    private final void setTotalHeight(int i) {
        this.totalHeight$delegate.setIntValue(i);
    }

    private final void setTotalWidth(int i) {
        this.totalWidth$delegate.setIntValue(i);
    }

    private final void setValueState(float f) {
        this.valueState$delegate.setFloatValue(f);
    }

    public void dispatchRawDelta(float f) {
        float fMax;
        float fMin;
        if (this.orientation == Orientation.Vertical) {
            fMax = Math.max(getTotalHeight() - (getThumbHeight$material3_release() / 2.0f), 0.0f);
            fMin = Math.min(getThumbHeight$material3_release() / 2.0f, fMax);
        } else {
            fMax = Math.max(getTotalWidth() - (getThumbWidth$material3_release() / 2.0f), 0.0f);
            fMin = Math.min(getThumbWidth$material3_release() / 2.0f, fMax);
        }
        setRawOffset(getRawOffset() + f + getPressOffset());
        setPressOffset(0.0f);
        float fScaleToUserValue = scaleToUserValue(fMin, fMax, SliderKt.snapValueToTick(getRawOffset(), this.tickFractions, fMin, fMax));
        if (fScaleToUserValue == getValue()) {
            return;
        }
        Function1 function1 = this.onValueChange;
        if (function1 == null) {
            setValue(fScaleToUserValue);
        } else if (function1 != null) {
            function1.invoke(Float.valueOf(fScaleToUserValue));
        }
    }

    @Override // androidx.compose.foundation.gestures.DraggableState
    public Object drag(MutatePriority mutatePriority, Function2 function2, Continuation continuation) {
        Object objCoroutineScope = CoroutineScopeKt.coroutineScope(new AnonymousClass2(mutatePriority, function2, null), continuation);
        return objCoroutineScope == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objCoroutineScope : Unit.INSTANCE;
    }

    public final float getCoercedValueAsFraction() {
        return SliderKt.calcFraction(((Number) this.valueRange.getStart()).floatValue(), ((Number) this.valueRange.getEndInclusive()).floatValue(), RangesKt.coerceIn(getValue(), ((Number) this.valueRange.getStart()).floatValue(), ((Number) this.valueRange.getEndInclusive()).floatValue()));
    }

    public final Function0 getGestureEndAction$material3_release() {
        return this.gestureEndAction;
    }

    public final Function1 getOnValueChange() {
        return this.onValueChange;
    }

    public final Function0 getOnValueChangeFinished() {
        return this.onValueChangeFinished;
    }

    public final Orientation getOrientation$material3_release() {
        return this.orientation;
    }

    public final boolean getReverseVerticalDirection$material3_release() {
        return this.reverseVerticalDirection;
    }

    public final int getSteps() {
        return this.steps;
    }

    public final int getThumbHeight$material3_release() {
        return this.thumbHeight$delegate.getIntValue();
    }

    public final int getThumbWidth$material3_release() {
        return this.thumbWidth$delegate.getIntValue();
    }

    public final float[] getTickFractions$material3_release() {
        return this.tickFractions;
    }

    public final int getTrackHeight$material3_release() {
        return this.trackHeight$delegate.getIntValue();
    }

    public final int getTrackWidth$material3_release() {
        return this.trackWidth$delegate.getIntValue();
    }

    public final float getValue() {
        return getValueState();
    }

    public final ClosedFloatingPointRange getValueRange() {
        return this.valueRange;
    }

    public final boolean isDragging() {
        return ((Boolean) this.isDragging$delegate.getValue()).booleanValue();
    }

    public final boolean isRtl$material3_release() {
        return this.isRtl;
    }

    /* JADX INFO: renamed from: onPress-k-4lQ0M$material3_release, reason: not valid java name */
    public final void m339onPressk4lQ0M$material3_release(long j) {
        float fM760getXimpl;
        float totalWidth;
        float fM760getXimpl2;
        if (this.orientation == Orientation.Vertical) {
            if (this.reverseVerticalDirection) {
                totalWidth = getTotalHeight();
                fM760getXimpl2 = Offset.m761getYimpl(j);
                fM760getXimpl = totalWidth - fM760getXimpl2;
            } else {
                fM760getXimpl = Offset.m761getYimpl(j);
            }
        } else if (this.isRtl) {
            totalWidth = getTotalWidth();
            fM760getXimpl2 = Offset.m760getXimpl(j);
            fM760getXimpl = totalWidth - fM760getXimpl2;
        } else {
            fM760getXimpl = Offset.m760getXimpl(j);
        }
        setPressOffset(fM760getXimpl - getRawOffset());
    }

    public final void setOnValueChange(Function1 function1) {
        this.onValueChange = function1;
    }

    public final void setOnValueChangeFinished(Function0 function0) {
        this.onValueChangeFinished = function0;
    }

    public final void setRtl$material3_release(boolean z) {
        this.isRtl = z;
    }

    public final void setThumbHeight$material3_release(int i) {
        this.thumbHeight$delegate.setIntValue(i);
    }

    public final void setThumbWidth$material3_release(int i) {
        this.thumbWidth$delegate.setIntValue(i);
    }

    public final void setTrackHeight$material3_release(int i) {
        this.trackHeight$delegate.setIntValue(i);
    }

    public final void setTrackWidth$material3_release(int i) {
        this.trackWidth$delegate.setIntValue(i);
    }

    public final void setValue(float f) {
        setValueState(calculateSnappedValue(f));
    }

    public final void updateDimensions$material3_release(int i, int i2, int i3, int i4) {
        setTrackWidth$material3_release(i);
        setTrackHeight$material3_release(i2);
        setTotalWidth(i3);
        setTotalHeight(i4);
    }
}
