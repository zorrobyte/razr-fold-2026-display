package com.android.systemui.common.shared.model;

import android.graphics.drawable.Drawable;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Icon.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class Icon {

    /* JADX INFO: compiled from: Icon.kt */
    public final class Loaded extends Icon {
        private final Drawable drawable;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Loaded(Drawable drawable, ContentDescription contentDescription) {
            super(null);
            drawable.getClass();
            this.drawable = drawable;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Loaded) && Intrinsics.areEqual(this.drawable, ((Loaded) obj).drawable) && Intrinsics.areEqual(null, null);
        }

        public final Drawable getDrawable() {
            return this.drawable;
        }

        public int hashCode() {
            return this.drawable.hashCode() * 31;
        }

        public String toString() {
            return "Loaded(drawable=" + this.drawable + ", contentDescription=" + ((Object) null) + ")";
        }
    }

    /* JADX INFO: compiled from: Icon.kt */
    public final class Resource extends Icon {
        private final int res;

        public Resource(int i, ContentDescription contentDescription) {
            super(null);
            this.res = i;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Resource) && this.res == ((Resource) obj).res && Intrinsics.areEqual(null, null);
        }

        public final int getRes() {
            return this.res;
        }

        public int hashCode() {
            return Integer.hashCode(this.res) * 31;
        }

        public String toString() {
            return "Resource(res=" + this.res + ", contentDescription=" + ((Object) null) + ")";
        }
    }

    private Icon() {
    }

    public /* synthetic */ Icon(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }
}
