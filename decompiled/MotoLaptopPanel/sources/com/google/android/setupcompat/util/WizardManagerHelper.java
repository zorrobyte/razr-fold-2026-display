package com.google.android.setupcompat.util;

import android.content.Intent;

/* JADX INFO: loaded from: classes.dex */
public abstract class WizardManagerHelper {
    public static boolean allowCustomizationInSetupFlow(Intent intent) {
        return intent.getBooleanExtra("allowCustomizationInSetupFlow", false);
    }

    public static boolean isAnySetupWizard(Intent intent) {
        if (intent == null) {
            return false;
        }
        return intent.getBooleanExtra("isSetupFlow", false);
    }
}
