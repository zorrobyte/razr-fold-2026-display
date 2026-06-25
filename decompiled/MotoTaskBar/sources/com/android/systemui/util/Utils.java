package com.android.systemui.util;

import android.content.Context;
import android.provider.Settings;

/* JADX INFO: loaded from: classes.dex */
public abstract class Utils {
    public static boolean useMediaResumption(Context context) {
        return useQsMediaPlayer(context) && Settings.Secure.getInt(context.getContentResolver(), "qs_media_resumption", 1) > 0;
    }

    public static boolean useQsMediaPlayer(Context context) {
        return true;
    }
}
