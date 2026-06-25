package com.motorola.taskbar.bar;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import com.motorola.taskbar.ModeChooserActivityReceiver;

/* JADX INFO: loaded from: classes2.dex */
public class ExternalModeChooserActivity extends Activity {
    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        int displayId = getDisplay().getDisplayId();
        Log.d("ExternalModeChooserActivity", "onCreate: " + displayId);
        ModeChooserActivityReceiver.requestShowDesktopSplashScreen(this, displayId);
        finish();
    }
}
