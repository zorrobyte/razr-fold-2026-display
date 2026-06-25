package com.motorola.plugin.core.misc;

import com.motorola.plugin.core.ExtensionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: BitFlag.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class MarkFlag {
    public static final Companion Companion;
    private static final MarkFlag EMPTY;
    private static final int OP_ADD = 1;
    private static final int OP_DELETE = 4;
    private static final int OP_UPDATE = 2;
    private final BitFlag flags;
    private final boolean readOnly;

    /* JADX INFO: compiled from: BitFlag.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final MarkFlag getEMPTY() {
            return MarkFlag.EMPTY;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final MarkFlag mark() {
            return new MarkFlag(0, 0 == true ? 1 : 0, 3, null);
        }
    }

    static {
        Companion companion = new Companion(null);
        Companion = companion;
        EMPTY = companion.mark();
    }

    private MarkFlag(int i, boolean z) {
        this.readOnly = z;
        this.flags = BitFlag.Companion.wrap(i);
    }

    /* synthetic */ MarkFlag(int i, boolean z, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 0 : i, (i2 & 2) != 0 ? false : z);
    }

    public static /* synthetic */ MarkFlag copy$default(MarkFlag markFlag, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        return markFlag.copy(z);
    }

    private final void throwIfReadOnly() {
        if (this.readOnly) {
            throw new UnsupportedOperationException("MarkFlag is in readonly mode");
        }
    }

    public final boolean added() {
        return this.flags.has(1);
    }

    public final MarkFlag copy(boolean z) {
        return new MarkFlag(this.flags.getMyFlags(), z);
    }

    public final boolean deleted() {
        return this.flags.has(4);
    }

    public final boolean isEmpty() {
        return this.flags.getEmpty();
    }

    public final MarkFlag markAdd() {
        throwIfReadOnly();
        this.flags.add(1);
        return this;
    }

    public final MarkFlag markDelete() {
        throwIfReadOnly();
        this.flags.add(4);
        return this;
    }

    public final MarkFlag markUpdate() {
        throwIfReadOnly();
        this.flags.add(2);
        return this;
    }

    public final MarkFlag reset() {
        throwIfReadOnly();
        this.flags.del(7);
        return this;
    }

    public String toString() {
        if (isEmpty()) {
            return "<>";
        }
        return ((String) ExtensionsKt.ifElse(added(), "-a", "")) + ((String) ExtensionsKt.ifElse(updated(), "-u", "")) + ((String) ExtensionsKt.ifElse(deleted(), "-d", ""));
    }

    public final MarkFlag unMarkAdd() {
        throwIfReadOnly();
        this.flags.del(1);
        return this;
    }

    public final MarkFlag unMarkDelete() {
        throwIfReadOnly();
        this.flags.del(4);
        return this;
    }

    public final MarkFlag unMarkUpdate() {
        throwIfReadOnly();
        this.flags.del(2);
        return this;
    }

    public final boolean updated() {
        return this.flags.has(2);
    }
}
