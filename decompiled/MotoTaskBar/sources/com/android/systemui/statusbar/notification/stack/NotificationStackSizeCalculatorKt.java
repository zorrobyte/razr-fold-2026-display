package com.android.systemui.statusbar.notification.stack;

import android.util.Log;
import com.android.systemui.util.Compile;

/* JADX INFO: compiled from: NotificationStackSizeCalculator.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class NotificationStackSizeCalculatorKt {
    private static final boolean DEBUG;
    private static final boolean SPEW;

    static {
        boolean z = Compile.IS_DEBUG;
        boolean z2 = false;
        DEBUG = z && Log.isLoggable("NotifStackSizeCalc", 3);
        if (z && Log.isLoggable("NotifStackSizeCalc", 2)) {
            z2 = true;
        }
        SPEW = z2;
    }
}
