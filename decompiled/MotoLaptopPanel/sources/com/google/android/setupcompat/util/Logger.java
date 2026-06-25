package com.google.android.setupcompat.util;

import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
public final class Logger {
    private final String prefix;

    public Logger(Class cls) {
        this(cls.getSimpleName());
    }

    public Logger(String str) {
        this.prefix = "[" + str + "] ";
    }

    public void atDebug(String str) {
        if (isD()) {
            Log.d("SetupLibrary", this.prefix.concat(str));
        }
    }

    public void atInfo(String str) {
        if (isI()) {
            Log.i("SetupLibrary", this.prefix.concat(str));
        }
    }

    public void atVerbose(String str) {
        if (isV()) {
            Log.v("SetupLibrary", this.prefix.concat(str));
        }
    }

    public void e(String str) {
        Log.e("SetupLibrary", this.prefix.concat(str));
    }

    public void e(String str, Throwable th) {
        Log.e("SetupLibrary", this.prefix.concat(str), th);
    }

    public boolean isD() {
        return Log.isLoggable("SetupLibrary", 3);
    }

    public boolean isI() {
        return Log.isLoggable("SetupLibrary", 4);
    }

    public boolean isV() {
        return Log.isLoggable("SetupLibrary", 2);
    }

    public void w(String str) {
        Log.w("SetupLibrary", this.prefix.concat(str));
    }
}
