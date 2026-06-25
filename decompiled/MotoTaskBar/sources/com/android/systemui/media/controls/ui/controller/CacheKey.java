package com.android.systemui.media.controls.ui.controller;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: MediaViewController.kt */
/* JADX INFO: loaded from: classes.dex */
final class CacheKey {
    private float expansion;
    private boolean gutsVisible;
    private int heightMeasureSpec;
    private int widthMeasureSpec;

    public CacheKey(int i, int i2, float f, boolean z) {
        this.widthMeasureSpec = i;
        this.heightMeasureSpec = i2;
        this.expansion = f;
        this.gutsVisible = z;
    }

    public /* synthetic */ CacheKey(int i, int i2, float f, boolean z, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this((i3 & 1) != 0 ? -1 : i, (i3 & 2) != 0 ? -1 : i2, (i3 & 4) != 0 ? 0.0f : f, (i3 & 8) != 0 ? false : z);
    }

    public static /* synthetic */ CacheKey copy$default(CacheKey cacheKey, int i, int i2, float f, boolean z, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = cacheKey.widthMeasureSpec;
        }
        if ((i3 & 2) != 0) {
            i2 = cacheKey.heightMeasureSpec;
        }
        if ((i3 & 4) != 0) {
            f = cacheKey.expansion;
        }
        if ((i3 & 8) != 0) {
            z = cacheKey.gutsVisible;
        }
        return cacheKey.copy(i, i2, f, z);
    }

    public final CacheKey copy(int i, int i2, float f, boolean z) {
        return new CacheKey(i, i2, f, z);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CacheKey)) {
            return false;
        }
        CacheKey cacheKey = (CacheKey) obj;
        return this.widthMeasureSpec == cacheKey.widthMeasureSpec && this.heightMeasureSpec == cacheKey.heightMeasureSpec && Float.compare(this.expansion, cacheKey.expansion) == 0 && this.gutsVisible == cacheKey.gutsVisible;
    }

    public int hashCode() {
        return (((((Integer.hashCode(this.widthMeasureSpec) * 31) + Integer.hashCode(this.heightMeasureSpec)) * 31) + Float.hashCode(this.expansion)) * 31) + Boolean.hashCode(this.gutsVisible);
    }

    public final void setExpansion(float f) {
        this.expansion = f;
    }

    public final void setGutsVisible(boolean z) {
        this.gutsVisible = z;
    }

    public final void setHeightMeasureSpec(int i) {
        this.heightMeasureSpec = i;
    }

    public final void setWidthMeasureSpec(int i) {
        this.widthMeasureSpec = i;
    }

    public String toString() {
        return "CacheKey(widthMeasureSpec=" + this.widthMeasureSpec + ", heightMeasureSpec=" + this.heightMeasureSpec + ", expansion=" + this.expansion + ", gutsVisible=" + this.gutsVisible + ")";
    }
}
