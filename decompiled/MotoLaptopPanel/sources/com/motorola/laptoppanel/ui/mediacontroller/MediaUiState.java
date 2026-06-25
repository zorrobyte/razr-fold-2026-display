package com.motorola.laptoppanel.ui.mediacontroller;

import androidx.media3.common.MediaMetadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: MediaControllerModel.kt */
/* JADX INFO: loaded from: classes.dex */
public interface MediaUiState {

    /* JADX INFO: compiled from: MediaControllerModel.kt */
    public final class NoMedia implements MediaUiState {
        public static final NoMedia INSTANCE = new NoMedia();

        private NoMedia() {
        }

        public boolean equals(Object obj) {
            return this == obj || (obj instanceof NoMedia);
        }

        public int hashCode() {
            return 2117336640;
        }

        public String toString() {
            return "NoMedia";
        }
    }

    /* JADX INFO: compiled from: MediaControllerModel.kt */
    public final class Ready implements MediaUiState {
        private final long duration;
        private final boolean hasNext;
        private final boolean hasPrev;
        private final boolean isPlaying;
        private final MediaMetadata metadata;
        private final long progress;

        public Ready(boolean z, MediaMetadata mediaMetadata, long j, long j2, boolean z2, boolean z3) {
            mediaMetadata.getClass();
            this.isPlaying = z;
            this.metadata = mediaMetadata;
            this.duration = j;
            this.progress = j2;
            this.hasNext = z2;
            this.hasPrev = z3;
        }

        public static /* synthetic */ Ready copy$default(Ready ready, boolean z, MediaMetadata mediaMetadata, long j, long j2, boolean z2, boolean z3, int i, Object obj) {
            if ((i & 1) != 0) {
                z = ready.isPlaying;
            }
            if ((i & 2) != 0) {
                mediaMetadata = ready.metadata;
            }
            if ((i & 4) != 0) {
                j = ready.duration;
            }
            if ((i & 8) != 0) {
                j2 = ready.progress;
            }
            if ((i & 16) != 0) {
                z2 = ready.hasNext;
            }
            if ((i & 32) != 0) {
                z3 = ready.hasPrev;
            }
            long j3 = j2;
            long j4 = j;
            return ready.copy(z, mediaMetadata, j4, j3, z2, z3);
        }

        public final Ready copy(boolean z, MediaMetadata mediaMetadata, long j, long j2, boolean z2, boolean z3) {
            mediaMetadata.getClass();
            return new Ready(z, mediaMetadata, j, j2, z2, z3);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Ready)) {
                return false;
            }
            Ready ready = (Ready) obj;
            return this.isPlaying == ready.isPlaying && Intrinsics.areEqual(this.metadata, ready.metadata) && this.duration == ready.duration && this.progress == ready.progress && this.hasNext == ready.hasNext && this.hasPrev == ready.hasPrev;
        }

        public final long getDuration() {
            return this.duration;
        }

        public final boolean getHasNext() {
            return this.hasNext;
        }

        public final boolean getHasPrev() {
            return this.hasPrev;
        }

        public final MediaMetadata getMetadata() {
            return this.metadata;
        }

        public final long getProgress() {
            return this.progress;
        }

        public int hashCode() {
            return (((((((((Boolean.hashCode(this.isPlaying) * 31) + this.metadata.hashCode()) * 31) + Long.hashCode(this.duration)) * 31) + Long.hashCode(this.progress)) * 31) + Boolean.hashCode(this.hasNext)) * 31) + Boolean.hashCode(this.hasPrev);
        }

        public final boolean isPlaying() {
            return this.isPlaying;
        }

        public String toString() {
            String string;
            CharSequence charSequence = this.metadata.title;
            if (charSequence == null || (string = charSequence.toString()) == null) {
                string = "";
            }
            return "Ready(title=" + MediaControllerModelKt.truncate(string, 20) + ", isPlaying = " + this.isPlaying + " progress = " + MediaControllerModelKt.formatDuration(this.progress) + "/" + MediaControllerModelKt.formatDuration(this.duration) + " hasNext = " + this.hasNext + " hasPrev = " + this.hasPrev + ")";
        }
    }
}
