package com.google.android.setupcompat.util;

import android.content.Context;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;

/* JADX INFO: loaded from: classes.dex */
public abstract class ForceTwoPaneHelper {
    private static final Logger LOG = new Logger("ForceTwoPaneHelper");

    public static boolean isForceTwoPaneEnable(Context context) {
        return PartnerConfigHelper.isForceTwoPaneEnabled(context);
    }
}
