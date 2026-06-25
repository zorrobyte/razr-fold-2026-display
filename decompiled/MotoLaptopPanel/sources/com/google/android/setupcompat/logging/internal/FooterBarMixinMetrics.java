package com.google.android.setupcompat.logging.internal;

import android.os.PersistableBundle;
import com.google.android.setupcompat.util.Logger;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* JADX INFO: loaded from: classes.dex */
public class FooterBarMixinMetrics {
    public static final String EXTRA_PRIMARY_BUTTON_VISIBILITY = "PrimaryButtonVisibility";
    public static final String EXTRA_SECONDARY_BUTTON_VISIBILITY = "SecondaryButtonVisibility";
    private static final Logger LOG = new Logger("FooterBarMixinMetrics");
    public String primaryButtonVisibility = "Unknown";
    String secondaryButtonVisibility = "Unknown";

    @Retention(RetentionPolicy.SOURCE)
    public @interface FooterButtonVisibility {
    }

    static String updateButtonVisibilityState(String str, boolean z) {
        if (!"VisibleUsingXml".equals(str) && !"Visible".equals(str) && !"Invisible".equals(str)) {
            LOG.w("Illegal visibility state: " + str);
        }
        return (z && "Invisible".equals(str)) ? "Invisible_to_Visible" : !z ? "VisibleUsingXml".equals(str) ? "VisibleUsingXml_to_Invisible" : "Visible".equals(str) ? "Visible_to_Invisible" : str : str;
    }

    public String getInitialStateVisibility(boolean z, boolean z2) {
        return z ? z2 ? "VisibleUsingXml" : "Visible" : "Invisible";
    }

    public PersistableBundle getMetrics() {
        PersistableBundle persistableBundle = new PersistableBundle();
        persistableBundle.putString(EXTRA_PRIMARY_BUTTON_VISIBILITY, this.primaryButtonVisibility);
        persistableBundle.putString(EXTRA_SECONDARY_BUTTON_VISIBILITY, this.secondaryButtonVisibility);
        return persistableBundle;
    }

    public void logPrimaryButtonInitialStateVisibility(boolean z, boolean z2) {
        this.primaryButtonVisibility = this.primaryButtonVisibility.equals("Unknown") ? getInitialStateVisibility(z, z2) : this.primaryButtonVisibility;
    }

    public void logSecondaryButtonInitialStateVisibility(boolean z, boolean z2) {
        this.secondaryButtonVisibility = this.secondaryButtonVisibility.equals("Unknown") ? getInitialStateVisibility(z, z2) : this.secondaryButtonVisibility;
    }

    public void updateButtonVisibility(boolean z, boolean z2) {
        this.primaryButtonVisibility = updateButtonVisibilityState(this.primaryButtonVisibility, z);
        this.secondaryButtonVisibility = updateButtonVisibilityState(this.secondaryButtonVisibility, z2);
    }
}
