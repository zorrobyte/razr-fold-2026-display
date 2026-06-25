package androidx.compose.runtime.snapshots;

import androidx.collection.MutableScatterSet;
import androidx.compose.runtime.snapshots.SnapshotApplyResult;
import androidx.compose.runtime.snapshots.tooling.SnapshotObserverKt;
import java.util.Map;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Snapshot.kt */
/* JADX INFO: loaded from: classes.dex */
public final class NestedMutableSnapshot extends MutableSnapshot {
    private boolean deactivated;
    private final MutableSnapshot parent;

    public NestedMutableSnapshot(long j, SnapshotIdSet snapshotIdSet, Function1 function1, Function1 function12, MutableSnapshot mutableSnapshot) {
        super(j, snapshotIdSet, function1, function12);
        this.parent = mutableSnapshot;
        mutableSnapshot.mo651nestedActivated$runtime_release(this);
    }

    private final void deactivate() {
        if (this.deactivated) {
            return;
        }
        this.deactivated = true;
        this.parent.mo652nestedDeactivated$runtime_release(this);
    }

    @Override // androidx.compose.runtime.snapshots.MutableSnapshot
    public SnapshotApplyResult apply() {
        NestedMutableSnapshot nestedMutableSnapshot;
        if (this.parent.getApplied$runtime_release() || this.parent.getDisposed$runtime_release()) {
            return new SnapshotApplyResult.Failure(this);
        }
        MutableScatterSet modified$runtime_release = getModified$runtime_release();
        long snapshotId = getSnapshotId();
        Map mapOptimisticMerges = modified$runtime_release != null ? SnapshotKt.optimisticMerges(this.parent.getSnapshotId(), this, this.parent.getInvalid$runtime_release()) : null;
        synchronized (SnapshotKt.getLock()) {
            try {
                SnapshotKt.validateOpen(this);
                if (modified$runtime_release == null || modified$runtime_release.getSize() == 0) {
                    nestedMutableSnapshot = this;
                    nestedMutableSnapshot.closeAndReleasePinning$runtime_release();
                } else {
                    nestedMutableSnapshot = this;
                    SnapshotApplyResult snapshotApplyResultInnerApplyLocked$runtime_release = nestedMutableSnapshot.innerApplyLocked$runtime_release(this.parent.getSnapshotId(), modified$runtime_release, mapOptimisticMerges, this.parent.getInvalid$runtime_release());
                    if (!Intrinsics.areEqual(snapshotApplyResultInnerApplyLocked$runtime_release, SnapshotApplyResult.Success.INSTANCE)) {
                        return snapshotApplyResultInnerApplyLocked$runtime_release;
                    }
                    MutableScatterSet modified$runtime_release2 = nestedMutableSnapshot.parent.getModified$runtime_release();
                    if (modified$runtime_release2 != null) {
                        modified$runtime_release2.addAll(modified$runtime_release);
                    } else {
                        nestedMutableSnapshot.parent.setModified(modified$runtime_release);
                        nestedMutableSnapshot.setModified(null);
                    }
                }
                if (nestedMutableSnapshot.parent.getSnapshotId() < snapshotId) {
                    nestedMutableSnapshot.parent.advance$runtime_release();
                }
                MutableSnapshot mutableSnapshot = nestedMutableSnapshot.parent;
                mutableSnapshot.setInvalid$runtime_release(mutableSnapshot.getInvalid$runtime_release().clear(snapshotId).andNot(nestedMutableSnapshot.getPreviousIds$runtime_release()));
                nestedMutableSnapshot.parent.recordPrevious$runtime_release(snapshotId);
                nestedMutableSnapshot.parent.recordPreviousPinnedSnapshot$runtime_release(nestedMutableSnapshot.takeoverPinnedSnapshot$runtime_release());
                nestedMutableSnapshot.parent.recordPreviousList$runtime_release(nestedMutableSnapshot.getPreviousIds$runtime_release());
                nestedMutableSnapshot.parent.recordPreviousPinnedSnapshots$runtime_release(nestedMutableSnapshot.getPreviousPinnedSnapshots$runtime_release());
                Unit unit = Unit.INSTANCE;
                nestedMutableSnapshot.setApplied$runtime_release(true);
                nestedMutableSnapshot.deactivate();
                SnapshotObserverKt.dispatchObserverOnApplied(nestedMutableSnapshot, modified$runtime_release);
                return SnapshotApplyResult.Success.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // androidx.compose.runtime.snapshots.MutableSnapshot, androidx.compose.runtime.snapshots.Snapshot
    public void dispose() {
        if (getDisposed$runtime_release()) {
            return;
        }
        super.dispose();
        deactivate();
    }
}
