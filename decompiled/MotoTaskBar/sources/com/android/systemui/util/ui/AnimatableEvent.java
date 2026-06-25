package com.android.systemui.util.ui;

import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: AnimatedValue.kt */
/* JADX INFO: loaded from: classes.dex */
public final class AnimatableEvent {
    private final boolean startAnimating;
    private final Object value;

    public AnimatableEvent(Object obj, boolean z) {
        this.value = obj;
        this.startAnimating = z;
    }

    public final Object component1() {
        return this.value;
    }

    public final boolean component2() {
        return this.startAnimating;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AnimatableEvent)) {
            return false;
        }
        AnimatableEvent animatableEvent = (AnimatableEvent) obj;
        return Intrinsics.areEqual(this.value, animatableEvent.value) && this.startAnimating == animatableEvent.startAnimating;
    }

    public int hashCode() {
        Object obj = this.value;
        return ((obj == null ? 0 : obj.hashCode()) * 31) + Boolean.hashCode(this.startAnimating);
    }

    public String toString() {
        return "AnimatableEvent(value=" + this.value + ", startAnimating=" + this.startAnimating + ")";
    }
}
