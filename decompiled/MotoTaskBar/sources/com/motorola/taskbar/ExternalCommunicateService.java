package com.motorola.taskbar;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import com.motorola.taskbar.util.DebugConfig;

/* JADX INFO: loaded from: classes2.dex */
public class ExternalCommunicateService extends Service {
    private static final boolean DEBUG = DebugConfig.DEBUG_COMMON;
    private static final String TAG = ExternalCommunicateService.class.getSimpleName();

    private void handleShowModeChooser(int i) {
        if (DEBUG) {
            Log.d(TAG, "handleShowModeChooser: " + i);
        }
        if (i == 0) {
            return;
        }
        ModeChooserActivityReceiver.requestShowDesktopSplashScreen(this, i);
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        if (DEBUG) {
            Log.d(TAG, "onCreate: ");
        }
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i, int i2) {
        if (intent == null) {
            Log.d(TAG, "onStartCommand intent is null");
            return 2;
        }
        int intExtra = intent.getIntExtra("action_type", 0);
        if (DEBUG) {
            Log.d(TAG, "onStartCommand actionType " + intExtra);
        }
        if (intExtra == 1) {
            handleShowModeChooser(intent.getIntExtra("display_id", 0));
        }
        return 2;
    }
}
