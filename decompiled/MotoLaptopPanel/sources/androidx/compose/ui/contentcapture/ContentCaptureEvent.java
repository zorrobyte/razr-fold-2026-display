package androidx.compose.ui.contentcapture;

import androidx.compose.ui.platform.coreshims.ViewStructureCompat;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: AndroidContentCaptureManager.android.kt */
/* JADX INFO: loaded from: classes.dex */
final class ContentCaptureEvent {
    private final int id;
    private final ViewStructureCompat structureCompat;
    private final long timestamp;
    private final ContentCaptureEventType type;

    public ContentCaptureEvent(int i, long j, ContentCaptureEventType contentCaptureEventType, ViewStructureCompat viewStructureCompat) {
        this.id = i;
        this.timestamp = j;
        this.type = contentCaptureEventType;
        this.structureCompat = viewStructureCompat;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ContentCaptureEvent)) {
            return false;
        }
        ContentCaptureEvent contentCaptureEvent = (ContentCaptureEvent) obj;
        return this.id == contentCaptureEvent.id && this.timestamp == contentCaptureEvent.timestamp && this.type == contentCaptureEvent.type && Intrinsics.areEqual(this.structureCompat, contentCaptureEvent.structureCompat);
    }

    public final int getId() {
        return this.id;
    }

    public final ViewStructureCompat getStructureCompat() {
        return this.structureCompat;
    }

    public final ContentCaptureEventType getType() {
        return this.type;
    }

    public int hashCode() {
        int iHashCode = ((((Integer.hashCode(this.id) * 31) + Long.hashCode(this.timestamp)) * 31) + this.type.hashCode()) * 31;
        ViewStructureCompat viewStructureCompat = this.structureCompat;
        return iHashCode + (viewStructureCompat == null ? 0 : viewStructureCompat.hashCode());
    }

    public String toString() {
        return "ContentCaptureEvent(id=" + this.id + ", timestamp=" + this.timestamp + ", type=" + this.type + ", structureCompat=" + this.structureCompat + ')';
    }
}
