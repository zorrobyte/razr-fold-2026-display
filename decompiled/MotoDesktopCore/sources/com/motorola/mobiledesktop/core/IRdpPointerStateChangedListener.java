package com.motorola.mobiledesktop.core;

import android.os.IInterface;

/* JADX INFO: loaded from: classes.dex */
public interface IRdpPointerStateChangedListener extends IInterface {
    public static final String DESCRIPTOR = "com.motorola.mobiledesktop.core.IRdpPointerStateChangedListener";

    void onRdpPointerDisplayChanged(int i2);

    void onRdpPointerIconTypeChanged(int i2);

    void onRdpPointerPositionReset(int i2, int i3);
}
