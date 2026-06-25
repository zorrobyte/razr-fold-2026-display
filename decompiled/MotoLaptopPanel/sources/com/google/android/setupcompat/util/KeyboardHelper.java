package com.google.android.setupcompat.util;

import android.content.Context;
import android.content.res.Configuration;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;

/* JADX INFO: loaded from: classes.dex */
public abstract class KeyboardHelper {
    public static boolean hasHardwareKeyboard(Context context) {
        Configuration configuration = context.getResources().getConfiguration();
        return (configuration.keyboard == 1 || configuration.hardKeyboardHidden == 2) ? false : true;
    }

    public static boolean isKeyboardFocusEnhancementEnabled(Context context) {
        return PartnerConfigHelper.isKeyboardFocusEnhancementEnabled(context);
    }
}
