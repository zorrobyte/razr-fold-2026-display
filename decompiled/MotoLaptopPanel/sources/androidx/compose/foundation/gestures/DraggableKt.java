package androidx.compose.foundation.gestures;

import androidx.compose.foundation.interaction.MutableInteractionSource;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.unit.Velocity;
import androidx.compose.ui.unit.VelocityKt;
import kotlin.jvm.functions.Function3;

/* JADX INFO: compiled from: Draggable.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class DraggableKt {
    private static final Function3 NoOpOnDragStarted = new DraggableKt$NoOpOnDragStarted$1(null);
    private static final Function3 NoOpOnDragStopped = new DraggableKt$NoOpOnDragStopped$1(null);

    public static final Modifier draggable(Modifier modifier, DraggableState draggableState, Orientation orientation, boolean z, MutableInteractionSource mutableInteractionSource, boolean z2, Function3 function3, Function3 function32, boolean z3) {
        return modifier.then(new DraggableElement(draggableState, orientation, z, mutableInteractionSource, z2, function3, function32, z3));
    }

    public static /* synthetic */ Modifier draggable$default(Modifier modifier, DraggableState draggableState, Orientation orientation, boolean z, MutableInteractionSource mutableInteractionSource, boolean z2, Function3 function3, Function3 function32, boolean z3, int i, Object obj) {
        if ((i & 4) != 0) {
            z = true;
        }
        boolean z4 = z;
        if ((i & 8) != 0) {
            mutableInteractionSource = null;
        }
        return draggable(modifier, draggableState, orientation, z4, mutableInteractionSource, (i & 16) != 0 ? false : z2, (i & 32) != 0 ? NoOpOnDragStarted : function3, (i & 64) != 0 ? NoOpOnDragStopped : function32, (i & 128) != 0 ? false : z3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: toFloat-3MmeM6k, reason: not valid java name */
    public static final float m133toFloat3MmeM6k(long j, Orientation orientation) {
        return Float.intBitsToFloat((int) (orientation == Orientation.Vertical ? j & 4294967295L : j >> 32));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: toFloat-sF-c-tU, reason: not valid java name */
    public static final float m134toFloatsFctU(long j, Orientation orientation) {
        return orientation == Orientation.Vertical ? Velocity.m1956getYimpl(j) : Velocity.m1955getXimpl(j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: toValidVelocity-TH1AsA0, reason: not valid java name */
    public static final long m135toValidVelocityTH1AsA0(long j) {
        return VelocityKt.Velocity(Float.isNaN(Velocity.m1955getXimpl(j)) ? 0.0f : Velocity.m1955getXimpl(j), Float.isNaN(Velocity.m1956getYimpl(j)) ? 0.0f : Velocity.m1956getYimpl(j));
    }
}
