package com.motorola.taskbar.shortcut.record;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.UserHandle;
import android.util.Log;
import com.motorola.android.provider.MotorolaSettings;
import com.motorola.taskbar.util.DebugConfig;

/* JADX INFO: loaded from: classes2.dex */
public class RecordManager {
    private static final boolean DEBUG = DebugConfig.DEBUG_COMMON;
    private static final String TAG = RecordManager.class.getSimpleName();
    private final Context mContext;
    private ContentObserver mRecordingObserver = new ContentObserver(new Handler()) { // from class: com.motorola.taskbar.shortcut.record.RecordManager.1
        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            int screenRecordingStatus = RecordManager.this.getScreenRecordingStatus();
            Log.d(RecordManager.TAG, "status=" + screenRecordingStatus);
            if (RecordManager.this.mRecordingStatus != screenRecordingStatus) {
                RecordManager.this.mRecordingStatus = screenRecordingStatus;
            }
        }
    };
    private int mRecordingStatus;

    public RecordManager(Context context) {
        this.mRecordingStatus = 0;
        this.mContext = context;
        context.getContentResolver().registerContentObserver(MotorolaSettings.Secure.getUriFor("screen_recording_status"), false, this.mRecordingObserver, -1);
        this.mRecordingStatus = getScreenRecordingStatus();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getScreenRecordingStatus() {
        return MotorolaSettings.Secure.getIntForUser(this.mContext.getContentResolver(), "screen_recording_status", 0, -2);
    }

    private void startRecord(int i) {
        if (DEBUG) {
            Log.d(TAG, "startRecord: " + i);
        }
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.android.systemui", "com.android.systemui.screenrecord.RecordingService"));
        intent.setAction("com.android.systemui.screenrecord.START");
        intent.putExtra("extra_displayId", i);
        this.mContext.startServiceAsUser(intent, UserHandle.of(ActivityManager.getCurrentUser()));
    }

    private void stopRecord(int i, boolean z) {
        if (DEBUG) {
            Log.d(TAG, "stopRecord: " + i + " shortCutKey :" + z);
        }
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.android.systemui", "com.android.systemui.screenrecord.RecordingService"));
        intent.setAction("com.android.systemui.screenrecord.STOP");
        intent.putExtra("extra_displayId", i);
        intent.putExtra("extra_shortcut_key", z);
        this.mContext.startServiceAsUser(intent, UserHandle.of(ActivityManager.getCurrentUser()));
    }

    public void toggleRecord(int i, boolean z) {
        if (this.mRecordingStatus == 0) {
            startRecord(i);
        } else {
            stopRecord(i, z);
        }
    }
}
