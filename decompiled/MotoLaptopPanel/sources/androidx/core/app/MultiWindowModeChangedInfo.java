package androidx.core.app;

import android.content.res.Configuration;

/* JADX INFO: compiled from: MultiWindowModeChangedInfo.kt */
/* JADX INFO: loaded from: classes.dex */
public final class MultiWindowModeChangedInfo {
    private final boolean isInMultiWindowMode;
    private Configuration newConfiguration;

    public MultiWindowModeChangedInfo(boolean z) {
        this.isInMultiWindowMode = z;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public MultiWindowModeChangedInfo(boolean z, Configuration configuration) {
        this(z);
        configuration.getClass();
        this.newConfiguration = configuration;
    }

    public final boolean isInMultiWindowMode() {
        return this.isInMultiWindowMode;
    }
}
