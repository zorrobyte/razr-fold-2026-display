package com.motorola.plugin.core.misc;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Checksum.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class Checksum {
    public static final Companion Companion = new Companion(null);
    public static final int TYPE_WHOLE_MD5 = 0;
    public static final int TYPE_WHOLE_SHA1 = 1;
    public static final int TYPE_WHOLE_SHA256 = 2;
    public static final int TYPE_WHOLE_SHA512 = 3;
    private final String hex;
    private final int type;

    /* JADX INFO: compiled from: Checksum.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public Checksum(int i, String str) {
        str.getClass();
        this.type = i;
        this.hex = str;
    }

    public static /* synthetic */ Checksum copy$default(Checksum checksum, int i, String str, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = checksum.type;
        }
        if ((i2 & 2) != 0) {
            str = checksum.hex;
        }
        return checksum.copy(i, str);
    }

    public final int component1() {
        return this.type;
    }

    public final String component2() {
        return this.hex;
    }

    public final Checksum copy(int i, String str) {
        str.getClass();
        return new Checksum(i, str);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Checksum)) {
            return false;
        }
        Checksum checksum = (Checksum) obj;
        return this.type == checksum.type && Intrinsics.areEqual(this.hex, checksum.hex);
    }

    public final String getHex() {
        return this.hex;
    }

    public final int getType() {
        return this.type;
    }

    public int hashCode() {
        return (Integer.hashCode(this.type) * 31) + this.hex.hashCode();
    }

    public String toString() {
        return "Checksum(type=" + this.type + ", hex=" + this.hex + ')';
    }
}
