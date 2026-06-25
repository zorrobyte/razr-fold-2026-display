package com.motorola.taskbar.util;

import android.os.Looper;

/* JADX INFO: loaded from: classes2.dex */
public class MainThreadExecutor extends LooperExecutor {
    public MainThreadExecutor() {
        super(Looper.getMainLooper());
    }
}
