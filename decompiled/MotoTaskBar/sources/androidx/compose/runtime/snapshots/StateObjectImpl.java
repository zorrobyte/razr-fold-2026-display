package androidx.compose.runtime.snapshots;

import androidx.compose.runtime.internal.AtomicInt;

/* JADX INFO: compiled from: StateObjectImpl.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class StateObjectImpl implements StateObject {
    private final AtomicInt readerKind = new AtomicInt(0);

    /* JADX INFO: renamed from: isReadIn-h_f27i8$runtime_release, reason: not valid java name */
    public final boolean m101isReadInh_f27i8$runtime_release(int i) {
        return (ReaderKind.m96constructorimpl(this.readerKind.get()) & i) != 0;
    }

    /* JADX INFO: renamed from: recordReadIn-h_f27i8$runtime_release, reason: not valid java name */
    public final void m102recordReadInh_f27i8$runtime_release(int i) {
        int iM96constructorimpl;
        do {
            iM96constructorimpl = ReaderKind.m96constructorimpl(this.readerKind.get());
            if ((iM96constructorimpl & i) != 0) {
                return;
            }
        } while (!this.readerKind.compareAndSet(iM96constructorimpl, ReaderKind.m96constructorimpl(iM96constructorimpl | i)));
    }
}
