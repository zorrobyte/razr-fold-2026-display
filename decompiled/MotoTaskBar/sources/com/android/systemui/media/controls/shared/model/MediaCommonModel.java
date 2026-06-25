package com.android.systemui.media.controls.shared.model;

import com.android.systemui.media.controls.shared.model.MediaDataLoadingModel;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: MediaCommonModel.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class MediaCommonModel {

    /* JADX INFO: compiled from: MediaCommonModel.kt */
    public final class MediaControl extends MediaCommonModel {
        private final boolean canBeRemoved;
        private final MediaDataLoadingModel.Loaded mediaLoadedModel;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public MediaControl(MediaDataLoadingModel.Loaded loaded, boolean z) {
            super(null);
            loaded.getClass();
            this.mediaLoadedModel = loaded;
            this.canBeRemoved = z;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof MediaControl)) {
                return false;
            }
            MediaControl mediaControl = (MediaControl) obj;
            return Intrinsics.areEqual(this.mediaLoadedModel, mediaControl.mediaLoadedModel) && this.canBeRemoved == mediaControl.canBeRemoved;
        }

        public final boolean getCanBeRemoved() {
            return this.canBeRemoved;
        }

        public final MediaDataLoadingModel.Loaded getMediaLoadedModel() {
            return this.mediaLoadedModel;
        }

        public int hashCode() {
            return (this.mediaLoadedModel.hashCode() * 31) + Boolean.hashCode(this.canBeRemoved);
        }

        public String toString() {
            return "MediaControl(mediaLoadedModel=" + this.mediaLoadedModel + ", canBeRemoved=" + this.canBeRemoved + ")";
        }
    }

    private MediaCommonModel() {
    }

    public /* synthetic */ MediaCommonModel(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }
}
