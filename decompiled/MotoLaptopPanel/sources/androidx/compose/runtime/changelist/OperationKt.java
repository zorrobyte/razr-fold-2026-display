package androidx.compose.runtime.changelist;

import androidx.compose.runtime.Anchor;
import androidx.compose.runtime.Applier;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.SlotWriter;
import androidx.compose.runtime.tooling.ComposeStackTraceBuilderKt;
import androidx.compose.runtime.tooling.ComposeStackTraceFrame;
import androidx.compose.runtime.tooling.ComposeStackTraceKt;
import java.util.Collection;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;

/* JADX INFO: compiled from: Operation.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class OperationKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final Throwable attachComposeStackTrace(Throwable th, final OperationErrorContext operationErrorContext, final SlotWriter slotWriter, final Anchor anchor) {
        return operationErrorContext == null ? th : ComposeStackTraceKt.attachComposeStackTrace(th, new Function0() { // from class: androidx.compose.runtime.changelist.OperationKt.attachComposeStackTrace.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List invoke() {
                Anchor anchor2 = anchor;
                if (anchor2 != null) {
                    slotWriter.seek(anchor2);
                }
                List listBuildTrace$default = ComposeStackTraceBuilderKt.buildTrace$default(slotWriter, null, 0, null, 7, null);
                ComposeStackTraceFrame composeStackTraceFrame = (ComposeStackTraceFrame) CollectionsKt.lastOrNull(listBuildTrace$default);
                Integer groupOffset = composeStackTraceFrame != null ? composeStackTraceFrame.getGroupOffset() : null;
                List listBuildStackTrace = operationErrorContext.buildStackTrace(groupOffset);
                if (groupOffset != null && !listBuildStackTrace.isEmpty()) {
                    listBuildStackTrace = CollectionsKt.plus((Collection) CollectionsKt.listOf(ComposeStackTraceFrame.copy$default((ComposeStackTraceFrame) CollectionsKt.first(listBuildStackTrace), null, groupOffset, 1, null)), (Iterable) CollectionsKt.drop(listBuildStackTrace, 1));
                }
                return CollectionsKt.plus((Collection) listBuildTrace$default, (Iterable) listBuildStackTrace);
            }
        });
    }

    private static final int currentNodeIndex(SlotWriter slotWriter) {
        int currentGroup = slotWriter.getCurrentGroup();
        int parent = slotWriter.getParent();
        while (parent >= 0 && !slotWriter.isNode(parent)) {
            parent = slotWriter.parent(parent);
        }
        int iGroupSize = parent + 1;
        int iNodeCount = 0;
        while (iGroupSize < currentGroup) {
            if (slotWriter.indexInGroup(currentGroup, iGroupSize)) {
                if (slotWriter.isNode(iGroupSize)) {
                    iNodeCount = 0;
                }
                iGroupSize++;
            } else {
                iNodeCount += slotWriter.isNode(iGroupSize) ? 1 : slotWriter.nodeCount(iGroupSize);
                iGroupSize += slotWriter.groupSize(iGroupSize);
            }
        }
        return iNodeCount;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int positionToInsert(SlotWriter slotWriter, Anchor anchor, Applier applier) {
        int iAnchorIndex = slotWriter.anchorIndex(anchor);
        if (!(slotWriter.getCurrentGroup() < iAnchorIndex)) {
            ComposerKt.composeImmediateRuntimeError("Check failed");
        }
        positionToParentOf(slotWriter, applier, iAnchorIndex);
        int iCurrentNodeIndex = currentNodeIndex(slotWriter);
        while (slotWriter.getCurrentGroup() < iAnchorIndex) {
            if (slotWriter.indexInCurrentGroup(iAnchorIndex)) {
                if (slotWriter.isNode()) {
                    applier.down(slotWriter.node(slotWriter.getCurrentGroup()));
                    iCurrentNodeIndex = 0;
                }
                slotWriter.startGroup();
            } else {
                iCurrentNodeIndex += slotWriter.skipGroup();
            }
        }
        if (!(slotWriter.getCurrentGroup() == iAnchorIndex)) {
            ComposerKt.composeImmediateRuntimeError("Check failed");
        }
        return iCurrentNodeIndex;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void positionToParentOf(SlotWriter slotWriter, Applier applier, int i) {
        while (!slotWriter.indexInParent(i)) {
            slotWriter.skipToGroupEnd();
            if (slotWriter.isNode(slotWriter.getParent())) {
                applier.up();
            }
            slotWriter.endGroup();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final OperationErrorContext withCurrentStackTrace(final OperationErrorContext operationErrorContext, final SlotWriter slotWriter) {
        return new OperationErrorContext() { // from class: androidx.compose.runtime.changelist.OperationKt.withCurrentStackTrace.1
            @Override // androidx.compose.runtime.changelist.OperationErrorContext
            public List buildStackTrace(Integer num) {
                List listBuildStackTrace = operationErrorContext.buildStackTrace(null);
                int parent = slotWriter.getParent();
                if (parent < 0) {
                    return listBuildStackTrace;
                }
                SlotWriter slotWriter2 = slotWriter;
                return CollectionsKt.plus((Collection) ComposeStackTraceBuilderKt.buildTrace(slotWriter2, num, parent, Integer.valueOf(slotWriter2.parent(parent))), (Iterable) listBuildStackTrace);
            }
        };
    }
}
