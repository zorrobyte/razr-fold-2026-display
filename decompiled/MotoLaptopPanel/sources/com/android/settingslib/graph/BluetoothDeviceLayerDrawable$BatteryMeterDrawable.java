package com.android.settingslib.graph;

/* JADX INFO: loaded from: classes.dex */
class BluetoothDeviceLayerDrawable$BatteryMeterDrawable extends BatteryMeterDrawableBase {
    private final float mAspectRatio;
    int mFrameColor;

    @Override // com.android.settingslib.graph.BatteryMeterDrawableBase
    protected float getAspectRatio() {
        return this.mAspectRatio;
    }

    @Override // com.android.settingslib.graph.BatteryMeterDrawableBase
    protected float getRadiusRatio() {
        return 0.0f;
    }
}
