package androidx.compose.ui.input.pointer;

import androidx.compose.ui.node.HitTestResult;
import androidx.compose.ui.node.LayoutNode;

/* JADX INFO: compiled from: PointerInputEventProcessor.kt */
/* JADX INFO: loaded from: classes.dex */
public final class PointerInputEventProcessor {
    private final HitPathTracker hitPathTracker;
    private boolean isProcessing;
    private final LayoutNode root;
    private final PointerInputChangeEventProducer pointerInputChangeEventProducer = new PointerInputChangeEventProducer();
    private final HitTestResult hitResult = new HitTestResult();

    public PointerInputEventProcessor(LayoutNode layoutNode) {
        this.root = layoutNode;
        this.hitPathTracker = new HitPathTracker(layoutNode.getCoordinates());
    }

    public final void clearPreviouslyHitModifierNodes() {
        this.hitPathTracker.clearPreviouslyHitModifierNodeCache();
    }

    /* JADX INFO: renamed from: process-BIzXfog, reason: not valid java name */
    public final int m1243processBIzXfog(PointerInputEvent pointerInputEvent, PositionCalculator positionCalculator, boolean z) {
        boolean z2;
        if (this.isProcessing) {
            return PointerInputEventProcessorKt.ProcessResult(false, false);
        }
        boolean z3 = true;
        try {
            this.isProcessing = true;
            InternalPointerEvent internalPointerEventProduce = this.pointerInputChangeEventProducer.produce(pointerInputEvent, positionCalculator);
            int size = internalPointerEventProduce.getChanges().size();
            for (int i = 0; i < size; i++) {
                PointerInputChange pointerInputChange = (PointerInputChange) internalPointerEventProduce.getChanges().valueAt(i);
                if (!pointerInputChange.getPressed() && !pointerInputChange.getPreviousPressed()) {
                }
                z2 = false;
                break;
            }
            z2 = true;
            int size2 = internalPointerEventProduce.getChanges().size();
            for (int i2 = 0; i2 < size2; i2++) {
                PointerInputChange pointerInputChange2 = (PointerInputChange) internalPointerEventProduce.getChanges().valueAt(i2);
                if (z2 || PointerEventKt.changedToDownIgnoreConsumed(pointerInputChange2)) {
                    LayoutNode.m1317hitTest6fMxITs$ui_release$default(this.root, pointerInputChange2.m1233getPositionF1C5BW0(), this.hitResult, pointerInputChange2.m1235getTypeT8wyACA(), false, 8, null);
                    if (!this.hitResult.isEmpty()) {
                        this.hitPathTracker.m1201addHitPathQJqDSyo(pointerInputChange2.m1231getIdJ3iCeTQ(), this.hitResult, PointerEventKt.changedToDownIgnoreConsumed(pointerInputChange2));
                        this.hitResult.clear();
                    }
                }
            }
            boolean zDispatchChanges = this.hitPathTracker.dispatchChanges(internalPointerEventProduce, z);
            if (internalPointerEventProduce.getSuppressMovementConsumption()) {
                z3 = false;
            } else {
                int size3 = internalPointerEventProduce.getChanges().size();
                for (int i3 = 0; i3 < size3; i3++) {
                    PointerInputChange pointerInputChange3 = (PointerInputChange) internalPointerEventProduce.getChanges().valueAt(i3);
                    if (PointerEventKt.positionChangedIgnoreConsumed(pointerInputChange3) && pointerInputChange3.isConsumed()) {
                        break;
                    }
                }
                z3 = false;
            }
            int iProcessResult = PointerInputEventProcessorKt.ProcessResult(zDispatchChanges, z3);
            this.isProcessing = false;
            return iProcessResult;
        } catch (Throwable th) {
            this.isProcessing = false;
            throw th;
        }
    }

    public final void processCancel() {
        if (this.isProcessing) {
            return;
        }
        this.pointerInputChangeEventProducer.clear();
        this.hitPathTracker.processCancel();
    }
}
