package com.android.systemui.util.ui;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: AnimatedValue.kt */
/* JADX INFO: loaded from: classes.dex */
public interface AnimatedValue {

    /* JADX INFO: compiled from: AnimatedValue.kt */
    public final class Animating implements AnimatedValue {
        private final Function0 onStopAnimating;
        private final Object value;

        public Animating(Object obj, Function0 function0) {
            function0.getClass();
            this.value = obj;
            this.onStopAnimating = function0;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Animating)) {
                return false;
            }
            Animating animating = (Animating) obj;
            return Intrinsics.areEqual(this.value, animating.value) && Intrinsics.areEqual(this.onStopAnimating, animating.onStopAnimating);
        }

        public final Function0 getOnStopAnimating() {
            return this.onStopAnimating;
        }

        public final Object getValue() {
            return this.value;
        }

        public int hashCode() {
            Object obj = this.value;
            return ((obj == null ? 0 : obj.hashCode()) * 31) + this.onStopAnimating.hashCode();
        }

        public String toString() {
            return "Animating(value=" + this.value + ", onStopAnimating=" + this.onStopAnimating + ")";
        }
    }

    /* JADX INFO: compiled from: AnimatedValue.kt */
    public final class NotAnimating implements AnimatedValue {
        private final Object value;

        public NotAnimating(Object obj) {
            this.value = obj;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof NotAnimating) && Intrinsics.areEqual(this.value, ((NotAnimating) obj).value);
        }

        public final Object getValue() {
            return this.value;
        }

        public int hashCode() {
            Object obj = this.value;
            if (obj == null) {
                return 0;
            }
            return obj.hashCode();
        }

        public String toString() {
            return "NotAnimating(value=" + this.value + ")";
        }
    }
}
