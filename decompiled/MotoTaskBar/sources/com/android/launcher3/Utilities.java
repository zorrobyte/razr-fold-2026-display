package com.android.launcher3;

import android.os.Handler;
import android.os.Message;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes.dex */
public abstract class Utilities {
    private static final Pattern sTrimPattern = Pattern.compile("^[\\s|\\p{javaSpaceChar}]*(.*)[\\s|\\p{javaSpaceChar}]*$");
    public static final boolean ATLEAST_Q = true;
    public static final boolean ATLEAST_P = true;
    public static final boolean ATLEAST_OREO_MR1 = true;
    public static final boolean ATLEAST_OREO = true;

    public static void postAsyncCallback(Handler handler, Runnable runnable) {
        Message messageObtain = Message.obtain(handler, runnable);
        messageObtain.setAsynchronous(true);
        handler.sendMessage(messageObtain);
    }

    public static String trim(CharSequence charSequence) {
        if (charSequence == null) {
            return null;
        }
        return sTrimPattern.matcher(charSequence).replaceAll("$1");
    }
}
