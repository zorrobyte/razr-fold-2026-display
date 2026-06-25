package com.google.android.setupcompat.partnerconfig;

import android.content.res.Configuration;

/* JADX INFO: loaded from: classes.dex */
public abstract class Util {
    public static boolean isNightMode(Configuration configuration) {
        return (configuration.uiMode & 48) == 32;
    }
}
