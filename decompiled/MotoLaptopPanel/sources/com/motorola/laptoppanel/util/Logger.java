package com.motorola.laptoppanel.util;

import android.os.Build;
import android.util.Log;
import java.util.Arrays;
import java.util.IllegalFormatException;
import java.util.Locale;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: Logger.kt */
/* JADX INFO: loaded from: classes.dex */
public final class Logger {
    private static final boolean DEBUG_LOGGABLE;
    private static final boolean ERROR_LOGGABLE;
    private static final boolean INFO_LOGGABLE;
    public static final Logger INSTANCE;
    private static final boolean VERBOSE_LOGGABLE;
    private static final boolean WARN_LOGGABLE;

    static {
        Logger logger = new Logger();
        INSTANCE = logger;
        VERBOSE_LOGGABLE = logger.isLoggable(2);
        DEBUG_LOGGABLE = logger.isLoggable(3);
        INFO_LOGGABLE = logger.isLoggable(4);
        WARN_LOGGABLE = logger.isLoggable(5);
        ERROR_LOGGABLE = logger.isLoggable(6);
    }

    private Logger() {
    }

    private final String buildMessage(String str, String str2, Object... objArr) {
        try {
            if (objArr.length != 0) {
                StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
                Locale locale = Locale.US;
                Object[] objArrCopyOf = Arrays.copyOf(objArr, objArr.length);
                str2 = String.format(locale, str2, Arrays.copyOf(objArrCopyOf, objArrCopyOf.length));
            }
        } catch (IllegalFormatException e) {
            Log.e("Logger", "IllegalFormatException: formatString='" + str2 + "' numArgs=" + objArr.length, e);
            StringBuilder sb = new StringBuilder();
            sb.append(str2);
            sb.append(" (An error occurred while formatting the message.)");
            str2 = sb.toString();
        }
        return "[" + str + "] " + str2;
    }

    private final String getPrefixFromObject(Object obj) {
        return obj != null ? obj.getClass().getSimpleName() : "<null>";
    }

    private final String getTagFromObject(Object obj) {
        return "MotoLaptopPanel";
    }

    public final void d(Object obj, String str, Object... objArr) {
        obj.getClass();
        str.getClass();
        objArr.getClass();
        if (DEBUG_LOGGABLE) {
            Log.d(getTagFromObject(obj), buildMessage(getPrefixFromObject(obj), str, Arrays.copyOf(objArr, objArr.length)));
        }
    }

    public final void d(String str, String str2, Object... objArr) {
        str.getClass();
        str2.getClass();
        objArr.getClass();
        if (DEBUG_LOGGABLE) {
            Log.d("MotoLaptopPanel", buildMessage(str, str2, Arrays.copyOf(objArr, objArr.length)));
        }
    }

    public final void e(Object obj, Throwable th, String str, Object... objArr) {
        obj.getClass();
        th.getClass();
        str.getClass();
        objArr.getClass();
        if (ERROR_LOGGABLE) {
            Log.e(getTagFromObject(obj), buildMessage(getPrefixFromObject(obj), str, Arrays.copyOf(objArr, objArr.length)), th);
        }
    }

    public final void e(String str, String str2, Object... objArr) {
        str.getClass();
        str2.getClass();
        objArr.getClass();
        if (ERROR_LOGGABLE) {
            Log.e("MotoLaptopPanel", buildMessage(str, str2, Arrays.copyOf(objArr, objArr.length)));
        }
    }

    public final void e(String str, Throwable th, String str2, Object... objArr) {
        str.getClass();
        th.getClass();
        str2.getClass();
        objArr.getClass();
        if (ERROR_LOGGABLE) {
            Log.e("MotoLaptopPanel", buildMessage(str, str2, Arrays.copyOf(objArr, objArr.length)), th);
        }
    }

    public final void i(Object obj, String str, Object... objArr) {
        obj.getClass();
        str.getClass();
        objArr.getClass();
        if (INFO_LOGGABLE) {
            Log.i(getTagFromObject(obj), buildMessage(getPrefixFromObject(obj), str, Arrays.copyOf(objArr, objArr.length)));
        }
    }

    public final void i(String str, String str2, Object... objArr) {
        str.getClass();
        str2.getClass();
        objArr.getClass();
        if (INFO_LOGGABLE) {
            Log.i("MotoLaptopPanel", buildMessage(str, str2, Arrays.copyOf(objArr, objArr.length)));
        }
    }

    public final boolean isLoggable(int i) {
        String str = Build.TYPE;
        return (i >= 3 && (StringsKt.equals(str, "userdebug", true) || StringsKt.equals(str, "eng", true))) || Log.isLoggable("MotoLaptopPanel", i);
    }

    public final void v(String str, String str2, Object... objArr) {
        str.getClass();
        str2.getClass();
        objArr.getClass();
        if (VERBOSE_LOGGABLE) {
            Log.v("MotoLaptopPanel", buildMessage(str, str2, Arrays.copyOf(objArr, objArr.length)));
        }
    }

    public final void w(Object obj, String str, Object... objArr) {
        obj.getClass();
        str.getClass();
        objArr.getClass();
        if (WARN_LOGGABLE) {
            Log.w(getTagFromObject(obj), buildMessage(getPrefixFromObject(obj), str, Arrays.copyOf(objArr, objArr.length)));
        }
    }

    public final void w(String str, String str2, Object... objArr) {
        str.getClass();
        str2.getClass();
        objArr.getClass();
        if (WARN_LOGGABLE) {
            Log.w("MotoLaptopPanel", buildMessage(str, str2, Arrays.copyOf(objArr, objArr.length)));
        }
    }
}
