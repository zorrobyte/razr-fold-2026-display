package com.motorola.plugin.core.misc;

/* JADX INFO: compiled from: ISnapshotAware.kt */
/* JADX INFO: loaded from: classes2.dex */
public interface ISnapshotAware {

    /* JADX INFO: compiled from: ISnapshotAware.kt */
    public final class DefaultImpls {
        public static /* synthetic */ ISnapshot snapshot$default(ISnapshotAware iSnapshotAware, ISnapshot iSnapshot, int i, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: snapshot");
            }
            if ((i & 1) != 0) {
                iSnapshot = AbstractSnapshot.EMPTY;
            }
            return iSnapshotAware.snapshot(iSnapshot);
        }
    }

    ISnapshot snapshot(ISnapshot iSnapshot);
}
