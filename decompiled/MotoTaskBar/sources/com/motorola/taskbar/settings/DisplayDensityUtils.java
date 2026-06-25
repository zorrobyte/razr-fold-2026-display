package com.motorola.taskbar.settings;

import android.content.Context;
import android.os.AsyncTask;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Log;
import android.view.WindowManagerGlobal;
import com.motorola.taskbar.guide.R$array;

/* JADX INFO: loaded from: classes2.dex */
class DisplayDensityUtils {
    private static final float[] sDisplayScales = {0.85f, 1.0f, 1.15f, 1.3f};
    private final int mDefaultDensity;
    private final String[] mEntries;

    public DisplayDensityUtils(Context context) {
        int displayId = context.getDisplay().getDisplayId();
        int defaultDisplayDensity = getDefaultDisplayDensity(displayId);
        if (defaultDisplayDensity == -1) {
            Log.e("DisplayDensityUtils", "invalid defaultDensity");
            this.mDefaultDensity = 160;
        } else {
            Log.d("DisplayDensityUtils", "defaultDensity = " + defaultDisplayDensity + " for display " + displayId);
            this.mDefaultDensity = defaultDisplayDensity;
        }
        this.mEntries = context.getResources().getStringArray(R$array.display_size_descriptions);
    }

    public static int getCurrentScaleIndex(Context context) {
        float displayScale = MotorolaSettingsSystem.getDisplayScale(context.getContentResolver(), SettingsActivity.sConnectType);
        int length = sDisplayScales.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                i = 1;
                break;
            }
            if (displayScale == sDisplayScales[i]) {
                break;
            }
            i++;
        }
        int i2 = length - 1;
        return displayScale > sDisplayScales[i2] ? i2 : i;
    }

    private static int getDefaultDisplayDensity(int i) {
        try {
            return WindowManagerGlobal.getWindowManagerService().getInitialDisplayDensity(i);
        } catch (RemoteException unused) {
            return -1;
        }
    }

    public static float getDefaultDisplayScale() {
        return sDisplayScales[1];
    }

    public static void setForcedDisplayDensity(final int i, final int i2) {
        final int iMyUserId = UserHandle.myUserId();
        AsyncTask.execute(new Runnable() { // from class: com.motorola.taskbar.settings.DisplayDensityUtils.2
            @Override // java.lang.Runnable
            public void run() {
                try {
                    WindowManagerGlobal.getWindowManagerService().setForcedDisplayDensityForUser(i, i2, iMyUserId);
                } catch (RemoteException unused) {
                    Log.w("DisplayDensityUtils", "Unable to save forced display density setting");
                }
            }
        });
    }

    public int getDefaultDensity() {
        return this.mDefaultDensity;
    }

    public String[] getEntries() {
        return this.mEntries;
    }

    public float[] getValues() {
        return sDisplayScales;
    }
}
