package com.android.systemui.statusbar.notification;

import android.util.ArraySet;

/* JADX INFO: loaded from: classes.dex */
public class DynamicPrivacyController {
    private final ArraySet mListeners = new ArraySet();
    private boolean mLastDynamicUnlocked = isDynamicallyUnlocked();

    public interface Listener {
    }

    DynamicPrivacyController() {
    }

    public void addListener(Listener listener) {
        this.mListeners.add(listener);
    }

    boolean isDynamicPrivacyEnabled() {
        return true;
    }

    public boolean isDynamicallyUnlocked() {
        return false;
    }
}
