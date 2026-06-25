package com.android.systemui.dagger;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.android.internal.util.NotificationMessagingUtil;
import com.android.internal.widget.LockPatternUtils;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes.dex */
public class DependencyProvider {
    static boolean provideAllowNotificationLongPress() {
        return true;
    }

    public ConfigurationController provideConfigurationController(Context context) {
        return new ConfigurationControllerImpl(context);
    }

    public DisplayMetrics provideDisplayMetrics(Context context, WindowManager windowManager) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        context.getDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

    public LockPatternUtils provideLockPatternUtils(Context context) {
        return new LockPatternUtils(context);
    }

    public NotificationMessagingUtil provideNotificationMessagingUtil(Context context) {
        return new NotificationMessagingUtil(context, (Object) null);
    }

    public Handler provideTimeTickHandler() {
        HandlerThread handlerThread = new HandlerThread("TimeTick");
        handlerThread.start();
        return new Handler(handlerThread.getLooper());
    }

    public BroadcastDispatcher providesBroadcastDispatcher(Context context, Looper looper, Executor executor) {
        BroadcastDispatcher broadcastDispatcher = new BroadcastDispatcher(context, looper, executor);
        broadcastDispatcher.initialize();
        return broadcastDispatcher;
    }
}
