package com.motorola.plugin.core.misc;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ISnapshot.kt */
/* JADX INFO: loaded from: classes2.dex */
public abstract class AbstractSnapshot implements ISnapshot {
    public static final EMPTY EMPTY = new EMPTY(null);
    private final ISnapshot mySuperState;

    /* JADX INFO: compiled from: ISnapshot.kt */
    public final class EMPTY implements ISnapshot {
        private EMPTY() {
        }

        public /* synthetic */ EMPTY(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @Override // com.motorola.plugin.core.misc.Disposable
        public void dispose() {
        }

        @Override // com.motorola.plugin.core.misc.ISnapshot
        public void onSnapshot(IPrinter iPrinter) {
            iPrinter.getClass();
        }
    }

    public AbstractSnapshot(ISnapshot iSnapshot) {
        iSnapshot.getClass();
        EMPTY empty = EMPTY;
        this.mySuperState = Intrinsics.areEqual(iSnapshot, empty) ? empty : iSnapshot;
    }

    @Override // com.motorola.plugin.core.misc.Disposable
    public void dispose() {
        this.mySuperState.dispose();
    }

    @Override // com.motorola.plugin.core.misc.ISnapshot
    public void onSnapshot(IPrinter iPrinter) {
        iPrinter.getClass();
        this.mySuperState.onSnapshot(iPrinter);
    }
}
