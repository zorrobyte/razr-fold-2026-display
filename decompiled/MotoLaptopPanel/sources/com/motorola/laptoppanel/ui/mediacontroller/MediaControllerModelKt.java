package com.motorola.laptoppanel.ui.mediacontroller;

import java.util.Arrays;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: MediaControllerModel.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class MediaControllerModelKt {
    public static final String formatDuration(long j) {
        if (j < 0) {
            return "00:00";
        }
        long j2 = j / ((long) 1000);
        long j3 = 3600;
        long j4 = j2 / j3;
        long j5 = 60;
        long j6 = (j2 % j3) / j5;
        long j7 = j2 % j5;
        if (j4 > 0) {
            StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
            return String.format("%d:%02d:%02d", Arrays.copyOf(new Object[]{Long.valueOf(j4), Long.valueOf(j6), Long.valueOf(j7)}, 3));
        }
        StringCompanionObject stringCompanionObject2 = StringCompanionObject.INSTANCE;
        return String.format("%02d:%02d", Arrays.copyOf(new Object[]{Long.valueOf(j6), Long.valueOf(j7)}, 2));
    }

    public static final String truncate(String str, int i) {
        str.getClass();
        if (str.length() <= i) {
            return str;
        }
        return StringsKt.take(str, i) + "...";
    }
}
