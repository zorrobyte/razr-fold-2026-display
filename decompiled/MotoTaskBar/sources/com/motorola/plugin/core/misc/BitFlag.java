package com.motorola.plugin.core.misc;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: BitFlag.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class BitFlag {
    public static final Companion Companion = new Companion(null);
    private int myFlags;

    /* JADX INFO: compiled from: BitFlag.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final BitFlag empty() {
            return wrap(0);
        }

        public final BitFlag wrap(int i) {
            return new BitFlag(i, null);
        }
    }

    private BitFlag(int i) {
        this.myFlags = i;
    }

    public /* synthetic */ BitFlag(int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(i);
    }

    public final void add(int i) {
        add(i, i);
    }

    public final void add(int i, int i2) {
        this.myFlags = (i & i2) | (this.myFlags & (~i2));
    }

    public final BitFlag copy() {
        return Companion.wrap(this.myFlags);
    }

    public final void del(int i) {
        this.myFlags = (~i) & this.myFlags;
    }

    public final boolean getAny() {
        return this.myFlags != 0;
    }

    public final boolean getEmpty() {
        return this.myFlags == 0;
    }

    public final int getMyFlags() {
        return this.myFlags;
    }

    public final boolean has(int i) {
        return (this.myFlags & i) != 0;
    }

    public final boolean only(int i) {
        return (this.myFlags & i) == i;
    }

    public final BitFlag reset() {
        this.myFlags = 0;
        return this;
    }

    public String toString() {
        return String.valueOf(this.myFlags);
    }
}
