package com.android.systemui.media.controls.util;

/* JADX INFO: loaded from: classes.dex */
public abstract class SmallHash {
    public static int hash(int i) {
        return Math.abs(Math.floorMod(i, 8192));
    }
}
