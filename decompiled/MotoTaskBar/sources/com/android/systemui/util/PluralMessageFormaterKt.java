package com.android.systemui.util;

import android.content.res.Resources;
import android.util.PluralsMessageFormatter;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;

/* JADX INFO: compiled from: PluralMessageFormater.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class PluralMessageFormaterKt {
    public static final String icuMessageFormat(Resources resources, int i, int i2) {
        resources.getClass();
        String str = PluralsMessageFormatter.format(resources, MapsKt.mapOf(TuplesKt.to("count", Integer.valueOf(i2))), i);
        str.getClass();
        return str;
    }
}
