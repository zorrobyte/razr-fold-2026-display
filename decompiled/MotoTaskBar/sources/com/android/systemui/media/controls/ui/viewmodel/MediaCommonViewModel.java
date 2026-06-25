package com.android.systemui.media.controls.ui.viewmodel;

import com.android.internal.logging.InstanceId;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: MediaCommonViewModel.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class MediaCommonViewModel {

    /* JADX INFO: compiled from: MediaCommonViewModel.kt */
    public final class MediaControl extends MediaCommonViewModel {
        private final MediaControlViewModel controlViewModel;
        private final boolean immediatelyUpdateUi;
        private final InstanceId instanceId;
        private final Function1 onAdded;
        private final Function1 onRemoved;
        private final Function1 onUpdated;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public MediaControl(InstanceId instanceId, boolean z, MediaControlViewModel mediaControlViewModel, Function1 function1, Function1 function12, Function1 function13) {
            super(null);
            instanceId.getClass();
            mediaControlViewModel.getClass();
            function1.getClass();
            function12.getClass();
            function13.getClass();
            this.instanceId = instanceId;
            this.immediatelyUpdateUi = z;
            this.controlViewModel = mediaControlViewModel;
            this.onAdded = function1;
            this.onRemoved = function12;
            this.onUpdated = function13;
        }

        public static /* synthetic */ MediaControl copy$default(MediaControl mediaControl, InstanceId instanceId, boolean z, MediaControlViewModel mediaControlViewModel, Function1 function1, Function1 function12, Function1 function13, int i, Object obj) {
            if ((i & 1) != 0) {
                instanceId = mediaControl.instanceId;
            }
            if ((i & 2) != 0) {
                z = mediaControl.immediatelyUpdateUi;
            }
            if ((i & 4) != 0) {
                mediaControlViewModel = mediaControl.controlViewModel;
            }
            if ((i & 8) != 0) {
                function1 = mediaControl.onAdded;
            }
            if ((i & 16) != 0) {
                function12 = mediaControl.onRemoved;
            }
            if ((i & 32) != 0) {
                function13 = mediaControl.onUpdated;
            }
            Function1 function14 = function12;
            Function1 function15 = function13;
            return mediaControl.copy(instanceId, z, mediaControlViewModel, function1, function14, function15);
        }

        public final MediaControl copy(InstanceId instanceId, boolean z, MediaControlViewModel mediaControlViewModel, Function1 function1, Function1 function12, Function1 function13) {
            instanceId.getClass();
            mediaControlViewModel.getClass();
            function1.getClass();
            function12.getClass();
            function13.getClass();
            return new MediaControl(instanceId, z, mediaControlViewModel, function1, function12, function13);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof MediaControl)) {
                return false;
            }
            MediaControl mediaControl = (MediaControl) obj;
            return Intrinsics.areEqual(this.instanceId, mediaControl.instanceId) && this.immediatelyUpdateUi == mediaControl.immediatelyUpdateUi && Intrinsics.areEqual(this.controlViewModel, mediaControl.controlViewModel) && Intrinsics.areEqual(this.onAdded, mediaControl.onAdded) && Intrinsics.areEqual(this.onRemoved, mediaControl.onRemoved) && Intrinsics.areEqual(this.onUpdated, mediaControl.onUpdated);
        }

        public final MediaControlViewModel getControlViewModel() {
            return this.controlViewModel;
        }

        public final boolean getImmediatelyUpdateUi() {
            return this.immediatelyUpdateUi;
        }

        public final InstanceId getInstanceId() {
            return this.instanceId;
        }

        @Override // com.android.systemui.media.controls.ui.viewmodel.MediaCommonViewModel
        public Function1 getOnAdded() {
            return this.onAdded;
        }

        @Override // com.android.systemui.media.controls.ui.viewmodel.MediaCommonViewModel
        public Function1 getOnRemoved() {
            return this.onRemoved;
        }

        @Override // com.android.systemui.media.controls.ui.viewmodel.MediaCommonViewModel
        public Function1 getOnUpdated() {
            return this.onUpdated;
        }

        public int hashCode() {
            return (((((((((this.instanceId.hashCode() * 31) + Boolean.hashCode(this.immediatelyUpdateUi)) * 31) + this.controlViewModel.hashCode()) * 31) + this.onAdded.hashCode()) * 31) + this.onRemoved.hashCode()) * 31) + this.onUpdated.hashCode();
        }

        public String toString() {
            return "MediaControl(instanceId=" + this.instanceId + ", immediatelyUpdateUi=" + this.immediatelyUpdateUi + ", controlViewModel=" + this.controlViewModel + ", onAdded=" + this.onAdded + ", onRemoved=" + this.onRemoved + ", onUpdated=" + this.onUpdated + ")";
        }
    }

    private MediaCommonViewModel() {
    }

    public /* synthetic */ MediaCommonViewModel(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract Function1 getOnAdded();

    public abstract Function1 getOnRemoved();

    public abstract Function1 getOnUpdated();
}
