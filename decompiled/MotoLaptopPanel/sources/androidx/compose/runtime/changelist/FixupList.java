package androidx.compose.runtime.changelist;

import androidx.compose.runtime.Anchor;
import androidx.compose.runtime.Applier;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.RememberManager;
import androidx.compose.runtime.SlotWriter;
import androidx.compose.runtime.changelist.Operation;
import androidx.compose.runtime.changelist.Operations;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.TypeIntrinsics;

/* JADX INFO: compiled from: FixupList.kt */
/* JADX INFO: loaded from: classes.dex */
public final class FixupList extends OperationsDebugStringFormattable {
    private final Operations operations = new Operations();
    private final Operations pendingOperations = new Operations();

    public final void clear() {
        this.pendingOperations.clear();
        this.operations.clear();
    }

    public final void createAndInsertNode(Function0 function0, int i, Anchor anchor) {
        Operations operations = this.operations;
        Operation.InsertNodeFixup insertNodeFixup = Operation.InsertNodeFixup.INSTANCE;
        operations.pushOp(insertNodeFixup);
        Operations operationsM620constructorimpl = Operations.WriteScope.m620constructorimpl(operations);
        Operations.WriteScope.m621setObjectDKhxnng(operationsM620constructorimpl, Operation.ObjectParameter.m618constructorimpl(0), function0);
        operationsM620constructorimpl.intArgs[operationsM620constructorimpl.intArgsSize - operationsM620constructorimpl.opCodes[operationsM620constructorimpl.opCodesSize - 1].getInts()] = i;
        Operations.WriteScope.m621setObjectDKhxnng(operationsM620constructorimpl, Operation.ObjectParameter.m618constructorimpl(1), anchor);
        operations.ensureAllArgumentsPushedFor(insertNodeFixup);
        Operations operations2 = this.pendingOperations;
        Operation.PostInsertNodeFixup postInsertNodeFixup = Operation.PostInsertNodeFixup.INSTANCE;
        operations2.pushOp(postInsertNodeFixup);
        Operations operationsM620constructorimpl2 = Operations.WriteScope.m620constructorimpl(operations2);
        operationsM620constructorimpl2.intArgs[operationsM620constructorimpl2.intArgsSize - operationsM620constructorimpl2.opCodes[operationsM620constructorimpl2.opCodesSize - 1].getInts()] = i;
        Operations.WriteScope.m621setObjectDKhxnng(operationsM620constructorimpl2, Operation.ObjectParameter.m618constructorimpl(0), anchor);
        operations2.ensureAllArgumentsPushedFor(postInsertNodeFixup);
    }

    public final void endNodeInsert() {
        if (!this.pendingOperations.isNotEmpty()) {
            ComposerKt.composeImmediateRuntimeError("Cannot end node insertion, there are no pending operations that can be realized.");
        }
        this.pendingOperations.popInto(this.operations);
    }

    public final void executeAndFlushAllPendingFixups(Applier applier, SlotWriter slotWriter, RememberManager rememberManager, OperationErrorContext operationErrorContext) {
        if (!this.pendingOperations.isEmpty()) {
            ComposerKt.composeImmediateRuntimeError("FixupList has pending fixup operations that were not realized. Were there mismatched insertNode() and endNodeInsert() calls?");
        }
        this.operations.executeAndFlushAllPendingOperations(applier, slotWriter, rememberManager, operationErrorContext);
    }

    public final boolean isEmpty() {
        return this.operations.isEmpty();
    }

    public final void updateNode(Object obj, Function2 function2) {
        Operations operations = this.operations;
        Operation.UpdateNode updateNode = Operation.UpdateNode.INSTANCE;
        operations.pushOp(updateNode);
        Operations operationsM620constructorimpl = Operations.WriteScope.m620constructorimpl(operations);
        Operations.WriteScope.m621setObjectDKhxnng(operationsM620constructorimpl, Operation.ObjectParameter.m618constructorimpl(0), obj);
        int iM618constructorimpl = Operation.ObjectParameter.m618constructorimpl(1);
        function2.getClass();
        Operations.WriteScope.m621setObjectDKhxnng(operationsM620constructorimpl, iM618constructorimpl, (Function2) TypeIntrinsics.beforeCheckcastToFunctionOfArity(function2, 2));
        operations.ensureAllArgumentsPushedFor(updateNode);
    }
}
