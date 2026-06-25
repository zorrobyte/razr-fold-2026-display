package androidx.window.embedding;

import android.content.res.Configuration;
import androidx.window.core.Bounds;
import androidx.window.layout.WindowLayoutInfo;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ParentContainerInfo.kt */
/* JADX INFO: loaded from: classes.dex */
public final class ParentContainerInfo {
    private final Configuration configuration;
    private final float density;
    private final Bounds windowBounds;
    private final WindowLayoutInfo windowLayoutInfo;

    public ParentContainerInfo(Bounds bounds, WindowLayoutInfo windowLayoutInfo, Configuration configuration, float f) {
        bounds.getClass();
        windowLayoutInfo.getClass();
        configuration.getClass();
        this.windowBounds = bounds;
        this.windowLayoutInfo = windowLayoutInfo;
        this.configuration = configuration;
        this.density = f;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ParentContainerInfo)) {
            return false;
        }
        ParentContainerInfo parentContainerInfo = (ParentContainerInfo) obj;
        return Intrinsics.areEqual(this.windowBounds, parentContainerInfo.windowBounds) && Intrinsics.areEqual(this.windowLayoutInfo, parentContainerInfo.windowLayoutInfo) && Intrinsics.areEqual(this.configuration, parentContainerInfo.configuration) && Float.compare(this.density, parentContainerInfo.density) == 0;
    }

    public final Bounds getWindowBounds() {
        return this.windowBounds;
    }

    public final WindowLayoutInfo getWindowLayoutInfo() {
        return this.windowLayoutInfo;
    }

    public int hashCode() {
        return (((((this.windowBounds.hashCode() * 31) + this.windowLayoutInfo.hashCode()) * 31) + this.configuration.hashCode()) * 31) + Float.hashCode(this.density);
    }

    public String toString() {
        return "ParentContainerInfo(windowBounds=" + this.windowBounds + ", windowLayoutInfo=" + this.windowLayoutInfo + ", configuration=" + this.configuration + ", density=" + this.density + ')';
    }
}
