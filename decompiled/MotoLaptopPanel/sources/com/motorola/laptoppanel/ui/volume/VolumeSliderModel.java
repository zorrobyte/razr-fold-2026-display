package com.motorola.laptoppanel.ui.volume;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.motorola.laptoppanel.R;
import com.motorola.laptoppanel.ui.compose.SliderModel;
import com.motorola.laptoppanel.ui.panel.LaptopPanelModel;
import com.motorola.laptoppanel.util.Logger;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

/* JADX INFO: compiled from: VolumeSliderModel.kt */
/* JADX INFO: loaded from: classes.dex */
public final class VolumeSliderModel extends SliderModel {
    private AudioManager mAudioManager;
    private final VolumeSliderModel$volumeReceiver$1 volumeReceiver;
    public static final Companion Companion = new Companion(null);
    public static final int $stable = 8;

    /* JADX INFO: compiled from: VolumeSliderModel.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final ViewModelProvider.Factory provideFactory(final LaptopPanelModel laptopPanelModel, final Application application) {
            laptopPanelModel.getClass();
            application.getClass();
            return new ViewModelProvider.Factory() { // from class: com.motorola.laptoppanel.ui.volume.VolumeSliderModel$Companion$provideFactory$1
                @Override // androidx.lifecycle.ViewModelProvider.Factory
                public ViewModel create(Class cls) {
                    cls.getClass();
                    if (cls.isAssignableFrom(VolumeSliderModel.class)) {
                        return new VolumeSliderModel(application, laptopPanelModel);
                    }
                    throw new IllegalArgumentException("Unknown ViewModel class");
                }
            };
        }
    }

    /* JADX INFO: renamed from: com.motorola.laptoppanel.ui.volume.VolumeSliderModel$onDrag$1, reason: invalid class name */
    /* JADX INFO: compiled from: VolumeSliderModel.kt */
    final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return VolumeSliderModel.this.onDrag(null, this);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r8v4, types: [android.content.BroadcastReceiver, com.motorola.laptoppanel.ui.volume.VolumeSliderModel$volumeReceiver$1] */
    public VolumeSliderModel(Application application, LaptopPanelModel laptopPanelModel) {
        super(application, laptopPanelModel);
        application.getClass();
        laptopPanelModel.getClass();
        Object systemService = application.getSystemService("audio");
        systemService.getClass();
        this.mAudioManager = (AudioManager) systemService;
        ?? r8 = new BroadcastReceiver() { // from class: com.motorola.laptoppanel.ui.volume.VolumeSliderModel$volumeReceiver$1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                if (Intrinsics.areEqual(intent != null ? intent.getAction() : null, "android.media.VOLUME_CHANGED_ACTION")) {
                    Logger.INSTANCE.d("VolumeSliderModel", "System volume changed, updating slider.", new Object[0]);
                    this.this$0.mo2156getCurrentSliderValue();
                }
            }
        };
        this.volumeReceiver = r8;
        int streamMinVolume = this.mAudioManager.getStreamMinVolume(3);
        setMinSliderValue(streamMinVolume);
        int streamMaxVolume = this.mAudioManager.getStreamMaxVolume(3);
        setMaxSliderValue(streamMaxVolume);
        Logger.INSTANCE.d("VolumeSliderModel", "minVolume = " + streamMinVolume + ", maxVolume = " + streamMaxVolume, new Object[0]);
        setSliderSteps((streamMaxVolume - streamMinVolume) + (-1));
        mo2156getCurrentSliderValue();
        application.registerReceiver(r8, new IntentFilter("android.media.VOLUME_CHANGED_ACTION"), 4);
    }

    @Override // com.motorola.laptoppanel.ui.compose.SliderModel
    /* JADX INFO: renamed from: getCurrentSliderValue */
    public void mo2156getCurrentSliderValue() {
        int streamVolume = this.mAudioManager.getStreamVolume(3);
        getCurrentSliderValue().setValue(Float.valueOf(streamVolume));
        Logger.INSTANCE.d("VolumeSliderModel", "Get Music stream volume = " + streamVolume, new Object[0]);
    }

    @Override // com.motorola.laptoppanel.ui.compose.SliderModel
    public int getIconForPercentage(float f) {
        Logger.INSTANCE.d("VolumeSliderModel", "getIconForPercentage: " + f, new Object[0]);
        return f == 0.0f ? R.drawable.zz_moto_ic_volume_in_bar_mute : R.drawable.zz_moto_ic_volume_in_bar;
    }

    @Override // androidx.lifecycle.ViewModel
    protected void onCleared() {
        super.onCleared();
        try {
            getApplication().unregisterReceiver(this.volumeReceiver);
            Logger.INSTANCE.d("VolumeSliderModel", "Unregistered volume receiver", new Object[0]);
        } catch (IllegalArgumentException e) {
            Logger.INSTANCE.e("VolumeSliderModel", (Throwable) e, "Error unregistering volume receiver", new Object[0]);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // com.motorola.laptoppanel.ui.compose.SliderModel
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public java.lang.Object onDrag(com.motorola.laptoppanel.ui.compose.SliderModel.Drag r5, kotlin.coroutines.Continuation r6) {
        /*
            r4 = this;
            boolean r0 = r6 instanceof com.motorola.laptoppanel.ui.volume.VolumeSliderModel.AnonymousClass1
            if (r0 == 0) goto L13
            r0 = r6
            com.motorola.laptoppanel.ui.volume.VolumeSliderModel$onDrag$1 r0 = (com.motorola.laptoppanel.ui.volume.VolumeSliderModel.AnonymousClass1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.motorola.laptoppanel.ui.volume.VolumeSliderModel$onDrag$1 r0 = new com.motorola.laptoppanel.ui.volume.VolumeSliderModel$onDrag$1
            r0.<init>(r6)
        L18:
            java.lang.Object r6 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L3a
            if (r2 != r3) goto L32
            java.lang.Object r4 = r0.L$1
            r5 = r4
            com.motorola.laptoppanel.ui.compose.SliderModel$Drag r5 = (com.motorola.laptoppanel.ui.compose.SliderModel.Drag) r5
            java.lang.Object r4 = r0.L$0
            com.motorola.laptoppanel.ui.volume.VolumeSliderModel r4 = (com.motorola.laptoppanel.ui.volume.VolumeSliderModel) r4
            kotlin.ResultKt.throwOnFailure(r6)
            goto L4a
        L32:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L3a:
            kotlin.ResultKt.throwOnFailure(r6)
            r0.L$0 = r4
            r0.L$1 = r5
            r0.label = r3
            java.lang.Object r6 = super.onDrag(r5, r0)
            if (r6 != r1) goto L4a
            return r1
        L4a:
            float r6 = r5.getDragValue()
            kotlinx.coroutines.flow.MutableStateFlow r0 = r4.getCurrentSliderValue()
            java.lang.Object r0 = r0.getValue()
            java.lang.Number r0 = (java.lang.Number) r0
            float r0 = r0.floatValue()
            int r6 = (r6 > r0 ? 1 : (r6 == r0 ? 0 : -1))
            if (r6 != 0) goto L6f
            com.motorola.laptoppanel.util.Logger r4 = com.motorola.laptoppanel.util.Logger.INSTANCE
            r5 = 0
            java.lang.Object[] r5 = new java.lang.Object[r5]
            java.lang.String r6 = "VolumeSliderModel"
            java.lang.String r0 = "Same drag value, no operation!"
            r4.d(r6, r0, r5)
            kotlin.Unit r4 = kotlin.Unit.INSTANCE
            return r4
        L6f:
            kotlinx.coroutines.flow.MutableStateFlow r6 = r4.getCurrentSliderValue()
            float r0 = r5.getDragValue()
            java.lang.Float r0 = kotlin.coroutines.jvm.internal.Boxing.boxFloat(r0)
            r6.setValue(r0)
            boolean r6 = r5 instanceof com.motorola.laptoppanel.ui.compose.SliderModel.Drag.Dragging
            if (r6 == 0) goto L8c
            com.motorola.laptoppanel.ui.compose.SliderModel$Drag$Dragging r5 = (com.motorola.laptoppanel.ui.compose.SliderModel.Drag.Dragging) r5
            float r5 = r5.m2162unboximpl()
            r4.setStreamVolume(r5)
            goto L99
        L8c:
            boolean r6 = r5 instanceof com.motorola.laptoppanel.ui.compose.SliderModel.Drag.Stopped
            if (r6 == 0) goto L9c
            com.motorola.laptoppanel.ui.compose.SliderModel$Drag$Stopped r5 = (com.motorola.laptoppanel.ui.compose.SliderModel.Drag.Stopped) r5
            float r5 = r5.m2168unboximpl()
            r4.setStreamVolume(r5)
        L99:
            kotlin.Unit r4 = kotlin.Unit.INSTANCE
            return r4
        L9c:
            kotlin.NoWhenBranchMatchedException r4 = new kotlin.NoWhenBranchMatchedException
            r4.<init>()
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.motorola.laptoppanel.ui.volume.VolumeSliderModel.onDrag(com.motorola.laptoppanel.ui.compose.SliderModel$Drag, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public final void setStreamVolume(float f) {
        int iCoerceIn = (int) RangesKt.coerceIn(f, getMinSliderValue(), getMaxSliderValue());
        Logger.INSTANCE.d("VolumeSliderModel", "Set Music stream volume: " + f, new Object[0]);
        this.mAudioManager.setStreamVolume(3, iCoerceIn, 0);
    }
}
