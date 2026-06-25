package androidx.window.layout;

import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: WindowLayoutInfo.kt */
/* JADX INFO: loaded from: classes.dex */
public final class WindowLayoutInfo {
    private final List displayFeatures;

    public WindowLayoutInfo(List list) {
        list.getClass();
        this.displayFeatures = list;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !Intrinsics.areEqual(WindowLayoutInfo.class, obj.getClass())) {
            return false;
        }
        return Intrinsics.areEqual(this.displayFeatures, ((WindowLayoutInfo) obj).displayFeatures);
    }

    public final List getDisplayFeatures() {
        return this.displayFeatures;
    }

    public int hashCode() {
        return this.displayFeatures.hashCode();
    }

    public String toString() {
        return CollectionsKt.joinToString$default(this.displayFeatures, ", ", "WindowLayoutInfo{ DisplayFeatures[", "] }", 0, null, null, 56, null);
    }
}
