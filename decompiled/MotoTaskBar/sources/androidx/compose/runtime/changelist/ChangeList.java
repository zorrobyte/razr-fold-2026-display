package androidx.compose.runtime.changelist;

import androidx.compose.runtime.Anchor;
import androidx.compose.runtime.Applier;
import androidx.compose.runtime.Composition;
import androidx.compose.runtime.CompositionContext;
import androidx.compose.runtime.MovableContentState;
import androidx.compose.runtime.MovableContentStateReference;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RememberManager;
import androidx.compose.runtime.RememberObserverHolder;
import androidx.compose.runtime.SlotTable;
import androidx.compose.runtime.SlotWriter;
import androidx.compose.runtime.changelist.Operation;
import androidx.compose.runtime.changelist.Operations;
import androidx.compose.runtime.internal.IntRef;
import java.util.List;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: ChangeList.kt */
/* JADX INFO: loaded from: classes.dex */
public final class ChangeList extends OperationsDebugStringFormattable {
    private final Operations operations = new Operations();

    public final void clear() {
        this.operations.clear();
    }

    public final void executeAndFlushAllPendingChanges(Applier applier, SlotWriter slotWriter, RememberManager rememberManager, OperationErrorContext operationErrorContext) {
        this.operations.executeAndFlushAllPendingOperations(applier, slotWriter, rememberManager, operationErrorContext);
    }

    public final boolean isEmpty() {
        return this.operations.isEmpty();
    }

    public final boolean isNotEmpty() {
        return this.operations.isNotEmpty();
    }

    public final void pushAdvanceSlotsBy(int i) {
        Operations operations = this.operations;
        Operation.AdvanceSlotsBy advanceSlotsBy = Operation.AdvanceSlotsBy.INSTANCE;
        operations.pushOp(advanceSlotsBy);
        Operations operationsM61constructorimpl = Operations.WriteScope.m61constructorimpl(operations);
        operationsM61constructorimpl.intArgs[operationsM61constructorimpl.intArgsSize - operationsM61constructorimpl.opCodes[operationsM61constructorimpl.opCodesSize - 1].getInts()] = i;
        operations.ensureAllArgumentsPushedFor(advanceSlotsBy);
    }

    public final void pushAppendValue(Anchor anchor, Object obj) {
        Operations operations = this.operations;
        Operation.AppendValue appendValue = Operation.AppendValue.INSTANCE;
        operations.pushOp(appendValue);
        Operations.WriteScope.m63setObjects4uCC6AY(Operations.WriteScope.m61constructorimpl(operations), Operation.ObjectParameter.m59constructorimpl(0), anchor, Operation.ObjectParameter.m59constructorimpl(1), obj);
        operations.ensureAllArgumentsPushedFor(appendValue);
    }

    public final void pushCopyNodesToNewAnchorLocation(List list, IntRef intRef) {
        if (list.isEmpty()) {
            return;
        }
        Operations operations = this.operations;
        Operation.CopyNodesToNewAnchorLocation copyNodesToNewAnchorLocation = Operation.CopyNodesToNewAnchorLocation.INSTANCE;
        operations.pushOp(copyNodesToNewAnchorLocation);
        Operations.WriteScope.m63setObjects4uCC6AY(Operations.WriteScope.m61constructorimpl(operations), Operation.ObjectParameter.m59constructorimpl(1), list, Operation.ObjectParameter.m59constructorimpl(0), intRef);
        operations.ensureAllArgumentsPushedFor(copyNodesToNewAnchorLocation);
    }

    public final void pushCopySlotTableToAnchorLocation(MovableContentState movableContentState, CompositionContext compositionContext, MovableContentStateReference movableContentStateReference, MovableContentStateReference movableContentStateReference2) {
        Operations operations = this.operations;
        Operation.CopySlotTableToAnchorLocation copySlotTableToAnchorLocation = Operation.CopySlotTableToAnchorLocation.INSTANCE;
        operations.pushOp(copySlotTableToAnchorLocation);
        Operations.WriteScope.m64setObjectsOGa0p1M(Operations.WriteScope.m61constructorimpl(operations), Operation.ObjectParameter.m59constructorimpl(0), movableContentState, Operation.ObjectParameter.m59constructorimpl(1), compositionContext, Operation.ObjectParameter.m59constructorimpl(3), movableContentStateReference2, Operation.ObjectParameter.m59constructorimpl(2), movableContentStateReference);
        operations.ensureAllArgumentsPushedFor(copySlotTableToAnchorLocation);
    }

    public final void pushDetermineMovableContentNodeIndex(IntRef intRef, Anchor anchor) {
        Operations operations = this.operations;
        Operation.DetermineMovableContentNodeIndex determineMovableContentNodeIndex = Operation.DetermineMovableContentNodeIndex.INSTANCE;
        operations.pushOp(determineMovableContentNodeIndex);
        Operations.WriteScope.m63setObjects4uCC6AY(Operations.WriteScope.m61constructorimpl(operations), Operation.ObjectParameter.m59constructorimpl(0), intRef, Operation.ObjectParameter.m59constructorimpl(1), anchor);
        operations.ensureAllArgumentsPushedFor(determineMovableContentNodeIndex);
    }

    public final void pushDowns(Object[] objArr) {
        if (objArr.length == 0) {
            return;
        }
        Operations operations = this.operations;
        Operation.Downs downs = Operation.Downs.INSTANCE;
        operations.pushOp(downs);
        Operations.WriteScope.m62setObjectDKhxnng(Operations.WriteScope.m61constructorimpl(operations), Operation.ObjectParameter.m59constructorimpl(0), objArr);
        operations.ensureAllArgumentsPushedFor(downs);
    }

    public final void pushEndCompositionScope(Function1 function1, Composition composition) {
        Operations operations = this.operations;
        Operation.EndCompositionScope endCompositionScope = Operation.EndCompositionScope.INSTANCE;
        operations.pushOp(endCompositionScope);
        Operations.WriteScope.m63setObjects4uCC6AY(Operations.WriteScope.m61constructorimpl(operations), Operation.ObjectParameter.m59constructorimpl(0), function1, Operation.ObjectParameter.m59constructorimpl(1), composition);
        operations.ensureAllArgumentsPushedFor(endCompositionScope);
    }

    public final void pushEndCurrentGroup() {
        this.operations.push(Operation.EndCurrentGroup.INSTANCE);
    }

    public final void pushEndMovableContentPlacement() {
        this.operations.push(Operation.EndMovableContentPlacement.INSTANCE);
    }

    public final void pushEndResumingScope(RecomposeScopeImpl recomposeScopeImpl) {
        Operations operations = this.operations;
        Operation.EndResumingScope endResumingScope = Operation.EndResumingScope.INSTANCE;
        operations.pushOp(endResumingScope);
        Operations.WriteScope.m62setObjectDKhxnng(Operations.WriteScope.m61constructorimpl(operations), Operation.ObjectParameter.m59constructorimpl(0), recomposeScopeImpl);
        operations.ensureAllArgumentsPushedFor(endResumingScope);
    }

    public final void pushEnsureGroupStarted(Anchor anchor) {
        Operations operations = this.operations;
        Operation.EnsureGroupStarted ensureGroupStarted = Operation.EnsureGroupStarted.INSTANCE;
        operations.pushOp(ensureGroupStarted);
        Operations.WriteScope.m62setObjectDKhxnng(Operations.WriteScope.m61constructorimpl(operations), Operation.ObjectParameter.m59constructorimpl(0), anchor);
        operations.ensureAllArgumentsPushedFor(ensureGroupStarted);
    }

    public final void pushEnsureRootStarted() {
        this.operations.push(Operation.EnsureRootGroupStarted.INSTANCE);
    }

    public final void pushExecuteOperationsIn(ChangeList changeList, IntRef intRef) {
        if (changeList.isNotEmpty()) {
            Operations operations = this.operations;
            Operation.ApplyChangeList applyChangeList = Operation.ApplyChangeList.INSTANCE;
            operations.pushOp(applyChangeList);
            Operations.WriteScope.m63setObjects4uCC6AY(Operations.WriteScope.m61constructorimpl(operations), Operation.ObjectParameter.m59constructorimpl(0), changeList, Operation.ObjectParameter.m59constructorimpl(1), intRef);
            operations.ensureAllArgumentsPushedFor(applyChangeList);
        }
    }

    public final void pushInsertSlots(Anchor anchor, SlotTable slotTable) {
        Operations operations = this.operations;
        Operation.InsertSlots insertSlots = Operation.InsertSlots.INSTANCE;
        operations.pushOp(insertSlots);
        Operations.WriteScope.m63setObjects4uCC6AY(Operations.WriteScope.m61constructorimpl(operations), Operation.ObjectParameter.m59constructorimpl(0), anchor, Operation.ObjectParameter.m59constructorimpl(1), slotTable);
        operations.ensureAllArgumentsPushedFor(insertSlots);
    }

    public final void pushInsertSlots(Anchor anchor, SlotTable slotTable, FixupList fixupList) {
        Operations operations = this.operations;
        Operation.InsertSlotsWithFixups insertSlotsWithFixups = Operation.InsertSlotsWithFixups.INSTANCE;
        operations.pushOp(insertSlotsWithFixups);
        Operations.WriteScope.m65setObjectst7hvbck(Operations.WriteScope.m61constructorimpl(operations), Operation.ObjectParameter.m59constructorimpl(0), anchor, Operation.ObjectParameter.m59constructorimpl(1), slotTable, Operation.ObjectParameter.m59constructorimpl(2), fixupList);
        operations.ensureAllArgumentsPushedFor(insertSlotsWithFixups);
    }

    public final void pushMoveCurrentGroup(int i) {
        Operations operations = this.operations;
        Operation.MoveCurrentGroup moveCurrentGroup = Operation.MoveCurrentGroup.INSTANCE;
        operations.pushOp(moveCurrentGroup);
        Operations operationsM61constructorimpl = Operations.WriteScope.m61constructorimpl(operations);
        operationsM61constructorimpl.intArgs[operationsM61constructorimpl.intArgsSize - operationsM61constructorimpl.opCodes[operationsM61constructorimpl.opCodesSize - 1].getInts()] = i;
        operations.ensureAllArgumentsPushedFor(moveCurrentGroup);
    }

    public final void pushMoveNode(int i, int i2, int i3) {
        Operations operations = this.operations;
        Operation.MoveNode moveNode = Operation.MoveNode.INSTANCE;
        operations.pushOp(moveNode);
        Operations operationsM61constructorimpl = Operations.WriteScope.m61constructorimpl(operations);
        int ints = operationsM61constructorimpl.intArgsSize - operationsM61constructorimpl.opCodes[operationsM61constructorimpl.opCodesSize - 1].getInts();
        int[] iArr = operationsM61constructorimpl.intArgs;
        iArr[ints + 1] = i;
        iArr[ints] = i2;
        iArr[ints + 2] = i3;
        operations.ensureAllArgumentsPushedFor(moveNode);
    }

    public final void pushRemember(RememberObserverHolder rememberObserverHolder) {
        Operations operations = this.operations;
        Operation.Remember remember = Operation.Remember.INSTANCE;
        operations.pushOp(remember);
        Operations.WriteScope.m62setObjectDKhxnng(Operations.WriteScope.m61constructorimpl(operations), Operation.ObjectParameter.m59constructorimpl(0), rememberObserverHolder);
        operations.ensureAllArgumentsPushedFor(remember);
    }

    public final void pushRemoveCurrentGroup() {
        this.operations.push(Operation.RemoveCurrentGroup.INSTANCE);
    }

    public final void pushRemoveNode(int i, int i2) {
        Operations operations = this.operations;
        Operation.RemoveNode removeNode = Operation.RemoveNode.INSTANCE;
        operations.pushOp(removeNode);
        Operations operationsM61constructorimpl = Operations.WriteScope.m61constructorimpl(operations);
        int ints = operationsM61constructorimpl.intArgsSize - operationsM61constructorimpl.opCodes[operationsM61constructorimpl.opCodesSize - 1].getInts();
        int[] iArr = operationsM61constructorimpl.intArgs;
        iArr[ints] = i;
        iArr[ints + 1] = i2;
        operations.ensureAllArgumentsPushedFor(removeNode);
    }

    public final void pushResetSlots() {
        this.operations.push(Operation.ResetSlots.INSTANCE);
    }

    public final void pushSkipToEndOfCurrentGroup() {
        this.operations.push(Operation.SkipToEndOfCurrentGroup.INSTANCE);
    }

    public final void pushStartResumingScope(RecomposeScopeImpl recomposeScopeImpl) {
        Operations operations = this.operations;
        Operation.StartResumingScope startResumingScope = Operation.StartResumingScope.INSTANCE;
        operations.pushOp(startResumingScope);
        Operations.WriteScope.m62setObjectDKhxnng(Operations.WriteScope.m61constructorimpl(operations), Operation.ObjectParameter.m59constructorimpl(0), recomposeScopeImpl);
        operations.ensureAllArgumentsPushedFor(startResumingScope);
    }

    public final void pushTrimValues(int i) {
        Operations operations = this.operations;
        Operation.TrimParentValues trimParentValues = Operation.TrimParentValues.INSTANCE;
        operations.pushOp(trimParentValues);
        Operations operationsM61constructorimpl = Operations.WriteScope.m61constructorimpl(operations);
        operationsM61constructorimpl.intArgs[operationsM61constructorimpl.intArgsSize - operationsM61constructorimpl.opCodes[operationsM61constructorimpl.opCodesSize - 1].getInts()] = i;
        operations.ensureAllArgumentsPushedFor(trimParentValues);
    }

    public final void pushUpdateAnchoredValue(Object obj, Anchor anchor, int i) {
        Operations operations = this.operations;
        Operation.UpdateAnchoredValue updateAnchoredValue = Operation.UpdateAnchoredValue.INSTANCE;
        operations.pushOp(updateAnchoredValue);
        Operations operationsM61constructorimpl = Operations.WriteScope.m61constructorimpl(operations);
        Operations.WriteScope.m63setObjects4uCC6AY(operationsM61constructorimpl, Operation.ObjectParameter.m59constructorimpl(0), obj, Operation.ObjectParameter.m59constructorimpl(1), anchor);
        operationsM61constructorimpl.intArgs[operationsM61constructorimpl.intArgsSize - operationsM61constructorimpl.opCodes[operationsM61constructorimpl.opCodesSize - 1].getInts()] = i;
        operations.ensureAllArgumentsPushedFor(updateAnchoredValue);
    }

    public final void pushUpdateAuxData(Object obj) {
        Operations operations = this.operations;
        Operation.UpdateAuxData updateAuxData = Operation.UpdateAuxData.INSTANCE;
        operations.pushOp(updateAuxData);
        Operations.WriteScope.m62setObjectDKhxnng(Operations.WriteScope.m61constructorimpl(operations), Operation.ObjectParameter.m59constructorimpl(0), obj);
        operations.ensureAllArgumentsPushedFor(updateAuxData);
    }

    public final void pushUpdateValue(Object obj, int i) {
        Operations operations = this.operations;
        Operation.UpdateValue updateValue = Operation.UpdateValue.INSTANCE;
        operations.pushOp(updateValue);
        Operations operationsM61constructorimpl = Operations.WriteScope.m61constructorimpl(operations);
        Operations.WriteScope.m62setObjectDKhxnng(operationsM61constructorimpl, Operation.ObjectParameter.m59constructorimpl(0), obj);
        operationsM61constructorimpl.intArgs[operationsM61constructorimpl.intArgsSize - operationsM61constructorimpl.opCodes[operationsM61constructorimpl.opCodesSize - 1].getInts()] = i;
        operations.ensureAllArgumentsPushedFor(updateValue);
    }

    public final void pushUps(int i) {
        Operations operations = this.operations;
        Operation.Ups ups = Operation.Ups.INSTANCE;
        operations.pushOp(ups);
        Operations operationsM61constructorimpl = Operations.WriteScope.m61constructorimpl(operations);
        operationsM61constructorimpl.intArgs[operationsM61constructorimpl.intArgsSize - operationsM61constructorimpl.opCodes[operationsM61constructorimpl.opCodesSize - 1].getInts()] = i;
        operations.ensureAllArgumentsPushedFor(ups);
    }
}
