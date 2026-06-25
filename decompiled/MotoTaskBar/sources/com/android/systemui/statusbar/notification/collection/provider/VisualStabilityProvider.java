package com.android.systemui.statusbar.notification.collection.provider;

import android.util.ArraySet;
import com.android.systemui.util.ListenerSet;

/* JADX INFO: compiled from: VisualStabilityProvider.kt */
/* JADX INFO: loaded from: classes.dex */
public final class VisualStabilityProvider {
    private final ListenerSet allListeners = new ListenerSet();
    private final ArraySet temporaryListeners = new ArraySet();
    private boolean isReorderingAllowed = true;

    public final void addPersistentReorderingAllowedListener(OnReorderingAllowedListener onReorderingAllowedListener) {
        onReorderingAllowedListener.getClass();
        this.temporaryListeners.remove(onReorderingAllowedListener);
        this.allListeners.addIfAbsent(onReorderingAllowedListener);
    }

    public final void addTemporaryReorderingAllowedListener(OnReorderingAllowedListener onReorderingAllowedListener) {
        onReorderingAllowedListener.getClass();
        if (this.allListeners.addIfAbsent(onReorderingAllowedListener)) {
            this.temporaryListeners.add(onReorderingAllowedListener);
        }
    }

    public final boolean isReorderingAllowed() {
        return this.isReorderingAllowed;
    }

    public final void removeReorderingAllowedListener(OnReorderingAllowedListener onReorderingAllowedListener) {
        onReorderingAllowedListener.getClass();
        this.temporaryListeners.remove(onReorderingAllowedListener);
        this.allListeners.remove(onReorderingAllowedListener);
    }
}
