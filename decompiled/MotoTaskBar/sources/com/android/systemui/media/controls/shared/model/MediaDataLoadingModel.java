package com.android.systemui.media.controls.shared.model;

import com.android.internal.logging.InstanceId;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: MediaDataLoadingModel.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class MediaDataLoadingModel {

    /* JADX INFO: compiled from: MediaDataLoadingModel.kt */
    public final class Loaded extends MediaDataLoadingModel {
        private final boolean immediatelyUpdateUi;
        private final InstanceId instanceId;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Loaded(InstanceId instanceId, boolean z) {
            super(null);
            instanceId.getClass();
            this.instanceId = instanceId;
            this.immediatelyUpdateUi = z;
        }

        public /* synthetic */ Loaded(InstanceId instanceId, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(instanceId, (i & 2) != 0 ? true : z);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Loaded)) {
                return false;
            }
            Loaded loaded = (Loaded) obj;
            return Intrinsics.areEqual(this.instanceId, loaded.instanceId) && this.immediatelyUpdateUi == loaded.immediatelyUpdateUi;
        }

        public final boolean getImmediatelyUpdateUi() {
            return this.immediatelyUpdateUi;
        }

        @Override // com.android.systemui.media.controls.shared.model.MediaDataLoadingModel
        public InstanceId getInstanceId() {
            return this.instanceId;
        }

        public int hashCode() {
            return (this.instanceId.hashCode() * 31) + Boolean.hashCode(this.immediatelyUpdateUi);
        }

        public String toString() {
            return "Loaded(instanceId=" + this.instanceId + ", immediatelyUpdateUi=" + this.immediatelyUpdateUi + ")";
        }
    }

    /* JADX INFO: compiled from: MediaDataLoadingModel.kt */
    public final class Removed extends MediaDataLoadingModel {
        private final InstanceId instanceId;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Removed(InstanceId instanceId) {
            super(null);
            instanceId.getClass();
            this.instanceId = instanceId;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Removed) && Intrinsics.areEqual(this.instanceId, ((Removed) obj).instanceId);
        }

        @Override // com.android.systemui.media.controls.shared.model.MediaDataLoadingModel
        public InstanceId getInstanceId() {
            return this.instanceId;
        }

        public int hashCode() {
            return this.instanceId.hashCode();
        }

        public String toString() {
            return "Removed(instanceId=" + this.instanceId + ")";
        }
    }

    private MediaDataLoadingModel() {
    }

    public /* synthetic */ MediaDataLoadingModel(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract InstanceId getInstanceId();
}
